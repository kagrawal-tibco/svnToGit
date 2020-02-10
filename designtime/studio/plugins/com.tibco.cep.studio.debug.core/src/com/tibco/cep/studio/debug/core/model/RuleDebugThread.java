package com.tibco.cep.studio.debug.core.model;

import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_STATUS_COMPLETED;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_STATUS_MASK;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_TYPE_MASK;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.ISuspendResume;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.BooleanValue;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.ClassType;
import com.sun.jdi.Field;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.IntegerValue;
import com.sun.jdi.InvalidTypeException;
import com.sun.jdi.InvocationException;
import com.sun.jdi.Location;
import com.sun.jdi.Method;
import com.sun.jdi.ObjectCollectedException;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.StackFrame;
import com.sun.jdi.ThreadGroupReference;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.Value;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.StepRequest;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.impl.DebugSessionImpl;
import com.tibco.cep.studio.debug.core.model.impl.RuleStepHandler;
import com.tibco.cep.studio.debug.util.LocationComparator;

/*
@author ssailapp
@date Jul 21, 2009
*/

public class RuleDebugThread extends RuleDebugElement implements IRuleDebugThread{
    private VirtualMachine vm;
    private long uniqueId;
    private ThreadReference fThread;
//    private boolean suspended = false;
	private boolean fIsSystemThread;
	private boolean fIsDaemon;
	private ThreadGroupReference fThreadGroup;
	private boolean fStepping;
	private int fProcessStepping;
	/**
	 * The collection of breakpoints that caused the last suspend, or 
	 * an empty collection if the thread is not suspended or was not
	 * suspended by any breakpoint(s).
	 */
	private List<IBreakpoint> fCurrentBreakpoints = Collections.synchronizedList(new ArrayList<IBreakpoint>(2));
	/**
	 * Whether children need to be refreshed. Set to
	 * <code>true</code> when stack frames are re-used
	 * on the next suspend.
	 */
	private boolean fRefreshChildren = true;
	private List<RuleDebugStackFrame> fStackFrames;
	private boolean fIsInvokingMethod;
	private boolean fTerminated;
	private boolean fRunning;
	
	private int lastActionState = -1;
    private int debugActionState = -1;
    protected IResourcePosition lastPosition;
	private String previousName;
	//private boolean fEvaluationInterrupted;
	private boolean fIsSuspending;
	
	
	
    
	static Map<Integer,String> statusMap = new HashMap<Integer,String>();
    static {
         statusMap.put(ThreadReference.THREAD_STATUS_UNKNOWN,"THREAD_STATUS_UNKNOWN");
         statusMap.put(ThreadReference.THREAD_STATUS_ZOMBIE,"THREAD_STATUS_ZOMBIE");
         statusMap.put(ThreadReference.THREAD_STATUS_RUNNING,"THREAD_STATUS_RUNNING");
         statusMap.put(ThreadReference.THREAD_STATUS_SLEEPING,"THREAD_STATUS_SLEEPING");
         statusMap.put(ThreadReference.THREAD_STATUS_MONITOR,"THREAD_STATUS_MONITOR");
         statusMap.put(ThreadReference.THREAD_STATUS_WAIT,"THREAD_STATUS_WAIT");
         statusMap.put(ThreadReference.THREAD_STATUS_NOT_STARTED,"THREAD_STATUS_NOT_STARTED");
    }

    public RuleDebugThread(IDebugTarget target, ThreadReference thread) {
    	super(target);
        this.fThread = thread;
        this.vm = thread.virtualMachine();
        this.uniqueId = ((ObjectReference)thread).uniqueID();
        initialize();
    }
    
    private void initialize() {
    	fStackFrames= new ArrayList<RuleDebugStackFrame>();
    	// system thread
		try {
			determineIfSystemThread();
		} catch (DebugException e) {
			Throwable underlyingException= e.getStatus().getException();
			if (underlyingException instanceof VMDisconnectedException) {
				// Threads may be created by the VM at shutdown
				// as finalizers. The VM may be disconnected by
				// the time we hear about the thread creation.
				disconnected();
				return;
			}	
			if (underlyingException instanceof ObjectCollectedException) {
				throw (ObjectCollectedException)underlyingException;
			}		
			logError(e);
		}
		
		try {
			determineIfDaemonThread();
		} catch (DebugException e) {
			Throwable underlyingException= e.getStatus().getException();
			if (underlyingException instanceof VMDisconnectedException) {
				// Threads may be created by the VM at shutdown
				// as finalizers. The VM may be disconnected by
				// the time we hear about the thread creation.
				disconnected();
				return;
			}
			logError(e);
		}
		setTerminated(false);
		setRunning(false);
		try {
			// see bug 30816
			if (fThread.status() == ThreadReference.THREAD_STATUS_UNKNOWN) {
				setRunning(true);
				return;
			}
		} catch (VMDisconnectedException e) {
			disconnected();
			return;
		} catch (ObjectCollectedException e){
			throw e;
		} catch (RuntimeException e) {
			logError(e);
		} 
		
		try {
			// This may be a transient suspend state (for example, a thread is handling a
			// class prepare event quietly). The class prepare event handler will notify
			// this thread when it resumes
			setRunning(!fThread.isSuspended());
		} catch (VMDisconnectedException e) {
			disconnected();
			return;
		} catch (ObjectCollectedException e){
			throw e;
		} catch (RuntimeException e) {
			logError(e);
		}
    	
    }
    
    

	/**
	 * Determines and sets whether this thread represents a system thread.
	 * 
	 * @exception DebugException if this method fails.  Reasons include:
	 * <ul>
	 * <li>Failure communicating with the VM.  The DebugException's
	 * status code contains the underlying exception responsible for
	 * the failure.</li>
	 * </ul>
	 */
	protected void determineIfSystemThread() throws DebugException {
		fIsSystemThread= false;
		ThreadGroupReference tgr= getUnderlyingThreadGroup();
		fIsSystemThread = tgr != null;
		while (tgr != null) {
			String tgn= null;
			try {
				tgn= tgr.name();
				tgr= tgr.parent();
			} catch (UnsupportedOperationException e) {
				fIsSystemThread = false;
				break;
			} catch (RuntimeException e) {
				targetRequestFailed(MessageFormat.format("{0} occurred while determining if thread is a ''System'' thread.", new Object[] {e.toString()}), e); 
				// execution will not reach this line, as
				// #targetRequestFailed will throw an exception				
				return;
			}
			if (tgn != null && tgn.equals("main")) {
				fIsSystemThread= false;
				break;
			}
		}
	}
	
	public boolean isSystemThread() {
		return fIsSystemThread;
	}
	
	/**
	 * Determines whether this is a daemon thread.
	 * 
	 * @throws DebugException on failure
	 */
	protected void determineIfDaemonThread() throws DebugException {
		fIsDaemon = false;
		try {
			ReferenceType referenceType = getUnderlyingThread().referenceType();
			Field field = referenceType.fieldByName("daemon"); //$NON-NLS-1$
			if (field == null) {
				field = referenceType.fieldByName("isDaemon"); //$NON-NLS-1$
			}
			if (field != null) {
				if (field.signature().equals("Z")) {
					Value value = getUnderlyingThread().getValue(field);
					if (value instanceof BooleanValue) {
						fIsDaemon = ((BooleanValue)value).booleanValue();
					}
				}
			}
		}
		catch(ObjectCollectedException oce) {/*do nothing thread does not exist*/}
		catch (RuntimeException e) {
			targetRequestFailed("Unable to determine thread daemon status", e);
		}
	}
	
	public boolean isDaemon() {
		return fIsDaemon;
	}
	
	
	/** 
	 * Returns this thread's underlying thread group.
	 * 
	 * @return thread group
	 * @exception DebugException if this method fails.  Reasons include:
	 * <ul>
	 * <li>Failure communicating with the VM.  The DebugException's
	 * status code contains the underlying exception responsible for
	 * the failure.</li>
	 * <li>Retrieving the underlying thread group is not supported
	 * on the underlying VM</li>
	 * </ul>
	 */
	protected ThreadGroupReference getUnderlyingThreadGroup() throws DebugException {
		if (fThreadGroup == null) {
			try {
				fThreadGroup = fThread.threadGroup();
			} catch (UnsupportedOperationException e) {
				requestFailed(MessageFormat.format("{0} occurred retrieving thread group name.", new Object[] {e.toString()}), e); 
				// execution will not reach this line, as
				// #requestFailed will throw an exception				
				return null;
			} catch (VMDisconnectedException e) {
				// ignore disconnect
				return null;
			} catch (ObjectCollectedException e) {
				// ignore object collected
				return null;
			} catch (RuntimeException e) {
				targetRequestFailed(MessageFormat.format("{0} occurred retrieving thread group name.", new Object[] {e.toString()}), e); 
				// execution will not reach this line, as
				// #targetRequestFailed will throw an exception				
				return null;
			}
		}
		return fThreadGroup;
	}
    



    public ThreadReference getUnderlyingThread() {
        return fThread;
    }

    public long getUniqueId() {
        return uniqueId;
    }

    public VirtualMachine getVM() {
        return vm;
    }
    
//    private void assureInterrupted() throws DebugSessionNotInterruptedException {
//        if (!suspended) {
//            throw new DebugSessionNotInterruptedException();
//        }
//    }

//    public void validate() {
//        suspended = true;
//        fireEvent(new DebugEvent(this,DebugEvent.CHANGE));
//    }

//    public void invalidate() {
//        suspended = false;
//        fireEvent(new DebugEvent(this,DebugEvent.CHANGE));
//    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RuleDebugThread that = (RuleDebugThread) o;

        if (uniqueId != that.uniqueId) return false;
        if (!fThread.equals(that.fThread)) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (int) (uniqueId ^ (uniqueId >>> 32));
        result = 31 * result + fThread.hashCode();
        return result;
    }

    public String getThreadStatus() {
        if(fThread.isSuspended()) {
            return statusMap.get(fThread.status());
        } else {
            return statusMap.get(ThreadReference.THREAD_STATUS_UNKNOWN);
        }
    }

    @Override
	public synchronized IStackFrame[] getStackFrames() throws DebugException {
    	List<RuleDebugStackFrame> list = computeStackFrames();
		return (IStackFrame[])list.toArray(new IStackFrame[list.size()]);
	}
    
    
    protected synchronized List<RuleDebugStackFrame> computeStackFrames(boolean refreshChildren) throws DebugException {
		if (isSuspended()) {
			if (isTerminated()) {
				fStackFrames.clear();
			} else if (refreshChildren) {
				List<StackFrame> frames = getUnderlyingFrames();
				int oldSize = fStackFrames.size();
				int newSize = frames.size();
				int discard = oldSize - newSize; // number of old frames to discard, if any
				for (int i = 0; i < discard; i++) {
					RuleDebugStackFrame invalid = (RuleDebugStackFrame) fStackFrames.remove(0);
					invalid.bind(null, -1);
				}
				int newFrames = newSize - oldSize; // number of frames to create, if any
				int depth = oldSize;
				for (int i = newFrames - 1; i >= 0; i--) {
					fStackFrames.add(0, new RuleDebugStackFrame(this, (StackFrame) frames.get(i), depth));
					depth++;
				}
				int numToRebind = Math.min(newSize, oldSize); // number of frames to attempt to rebind
				int offset = newSize - 1;
				for (depth = 0; depth < numToRebind; depth++) {
					RuleDebugStackFrame oldFrame = (RuleDebugStackFrame) fStackFrames.get(offset);
					StackFrame frame = (StackFrame) frames.get(offset);
					RuleDebugStackFrame newFrame = oldFrame.bind(frame, depth);
					if (newFrame != oldFrame) {
						fStackFrames.set(offset, newFrame);
					}
					offset--;
				}
			}
			fRefreshChildren = false;
		} else {
			return Collections.emptyList();
		}
		return fStackFrames;
	}
    
    /**
	 * Returns this thread's current stack frames as a list, computing
	 * them if required. Returns an empty collection if this thread is
	 * not currently suspended, or this thread is terminated. This
	 * method should be used internally to get the current stack frames,
	 * instead of calling <code>#getStackFrames()</code>, which makes a
	 * copy of the current list.
	 * <p>
	 * Before a thread is resumed a call must be made to one of:<ul>
	 * <li><code>preserveStackFrames()</code></li>
	 * <li><code>disposeStackFrames()</code></li>
	 * </ul>
	 * If stack frames are disposed before a thread is resumed, stack frames
	 * are completely re-computed on the next call to this method. If stack
	 * frames are to be preserved, this method will attempt to re-use any stack
	 * frame objects which represent the same stack frame as on the previous
	 * suspend. Stack frames are cached until a subsequent call to preserve
	 * or dispose stack frames.
	 * </p>
	 * 
	 * @return list of <code>IJavaStackFrame</code>
	 * @exception DebugException if this method fails.  Reasons include:
	 * <ul>
	 * <li>Failure communicating with the VM.  The DebugException's
	 * status code contains the underlying exception responsible for
	 * the failure.</li>
	 * </ul>
	 */	
	public synchronized List<RuleDebugStackFrame> computeStackFrames() throws DebugException {
		return computeStackFrames(fRefreshChildren);
	}
	
	/**
	 * Request new stack frames
	 */
	public List<RuleDebugStackFrame> computeNewStackFrames() throws DebugException {
		return computeStackFrames(true);
	}

	private List<StackFrame> getUnderlyingFrames() throws DebugException {
		if (!isSuspended()) {
			// Checking isSuspended here eliminates a race condition in resume
			// between the time stack frames are preserved and the time the
			// underlying thread is actually resumed.
			requestFailed("Unable to retrieve stack frame - thread not suspended.", null, IRuleDebugThread.DEBUG_THREAD_NOT_SUSPENDED); 
		}
		try {
			return getUnderlyingThread().frames();
		} catch (IncompatibleThreadStateException e) {
			requestFailed("Unable to retrieve stack frame - thread not suspended.", e, IRuleDebugThread.DEBUG_THREAD_NOT_SUSPENDED); 
		} catch (RuntimeException e) {
			targetRequestFailed(MessageFormat.format("{0} occurred retrieving stack frames.", new Object[] {e.toString()}), e); 
		} catch (InternalError e) {
			targetRequestFailed(MessageFormat.format("{0} occurred retrieving stack frames.", new Object[] {e.toString()}), e); 
		}
		// execution will not reach this line, as
		// #targetRequestFailed will thrown an exception
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IJavaThread#getFrameCount()
	 */
	public int getFrameCount() throws DebugException {
		return getUnderlyingFrameCount();
	}
	
	/**
	 * Returns the number of frames on the stack from the
	 * underlying thread.
	 * 
	 * @return number of frames on the stack
	 * @exception DebugException if this method fails.  Reasons include:
	 * <ul>
	 * <li>Failure communicating with the VM.  The DebugException's
	 * status code contains the underlying exception responsible for
	 * the failure.</li>
	 * <li>This thread is not suspended</li>
	 * </ul>
	 */
	protected int getUnderlyingFrameCount() throws DebugException {
		try {
			return getUnderlyingThread().frameCount();
		} catch (RuntimeException e) {
			targetRequestFailed(MessageFormat.format("{0} occurred retrieving frame count.", new Object[] {e.toString()}), e); 
		} catch (IncompatibleThreadStateException e) {
			requestFailed(MessageFormat.format("{0} occurred retrieving frame count.", new Object[] {e.toString()}), e, -1); 
		}
		// execution will not reach here - try block will either
		// return or exception will be thrown
		return -1;
	}
	
	/**
	 * Preserves stack frames to be used on the next suspend event.
	 * Iterates through all current stack frames, setting their
	 * state as invalid. This method should be called before this thread
	 * is resumed, when stack frames are to be re-used when it later
	 * suspends.
	 * 
	 * @see computeStackFrames()
	 */
	public synchronized void preserveStackFrames() {
		if(fStackFrames == null) {
			return;
		}
		fRefreshChildren = true;
		Iterator<RuleDebugStackFrame> frames = fStackFrames.iterator();
		while (frames.hasNext()) {
			((RuleDebugStackFrame)frames.next()).setUnderlyingStackFrame(null);
		}
	}
    
	@Override
	public synchronized IStackFrame getTopStackFrame() throws DebugException {
		List<RuleDebugStackFrame> c= computeStackFrames();
		if (c.isEmpty()) {
			return null;
		}
		return (IStackFrame) c.get(0);
	}
	
	@Override
	public String getName() throws DebugException {
		try {
			previousName = getUnderlyingThread().name();
		} catch (RuntimeException e) {
			if (e instanceof ObjectCollectedException) {
				if (previousName == null) {
					previousName= "Garbage collected"; 
				}
			} else if (e instanceof VMDisconnectedException) {
				if (previousName == null) {
					previousName= "disconnected"; 
				}
			} else {
				targetRequestFailed(MessageFormat.format("{0} occurred retrieving thread name.", new Object[] {e.toString()}), e); 
			}
		}
		return previousName;
	}
	
	

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IThread#getPriority()
	 */
	@Override
	public int getPriority() throws DebugException {
		// to get the priority, we must get the value from the "priority" field
		Field p= null;
		try {
			p= getUnderlyingThread().referenceType().fieldByName("priority"); //$NON-NLS-1$
			if (p == null) {
				requestFailed("Unable to find 'priority' field in thread.", null); 
			}
			Value v= getUnderlyingThread().getValue(p);
			if (v instanceof IntegerValue) {
				return ((IntegerValue)v).value();
			}
			requestFailed("Value of thread 'priority' is not an integer.", null); 
		} catch (RuntimeException e) {
			targetRequestFailed(MessageFormat.format("{0} occurred retrieving thread priority.", new Object[] {e.toString()}), e); 
		}
		return -1;
	}
	
	/**
	 * @see org.eclipse.jdt.debug.core.IJavaThread#hasOwnedMonitors()
	 */
	public boolean hasOwnedMonitors() throws DebugException {
		return isSuspended() && getOwnedMonitors().length > 0;
	}
	
	

	public ObjectReference[] getOwnedMonitors() throws DebugException {
		try {
			//IDebugTarget target= getDebugTarget();
			List<ObjectReference> ownedMonitors = fThread.ownedMonitors();
			return ownedMonitors.toArray(new ObjectReference[ownedMonitors.size()]);
		} catch (IncompatibleThreadStateException e) {
			targetRequestFailed("Failed to retrieve owned monitor", e); 
		} catch (RuntimeException e) {
			targetRequestFailed("Failed to retrieve owned monitor", e); 
		}
		return null;
	}


	public ObjectReference getContendedMonitor() throws DebugException {
		try {
			
			return fThread.currentContendedMonitor();
			
		} catch (IncompatibleThreadStateException e) {
			targetRequestFailed("Failed to retrieve contended monitor", e); 
		} catch (RuntimeException e) {
			targetRequestFailed("Failed to retrieve contended monitor", e); 
		}
		
		return null;
	}

	@Override
	public boolean hasStackFrames() throws DebugException {
		return isSuspended();
	}

	@Override
	public boolean canResume() {
		return isSuspended() && (!isPerformingEvaluation() || isInvokingMethod());
	}

	@Override
	public boolean canSuspend() {
		return !isSuspended() || (isPerformingEvaluation() && !isInvokingMethod());
	}

	@Override
	public boolean isSuspended() {
		return
				!fRunning && 
				!fTerminated;
		
	}

	@Override
	public synchronized void resume() throws DebugException {
		if (getDebugTarget().isSuspended()) {
			getDebugTarget().resume();
		} else {
			resumeThread(true);
		}
	}
	
	/**
	 * Notifies this thread that it has been suspended due
	 * to a VM suspend.
	 */	
	public synchronized void suspendedByVM() {
		setRunning(false);
//		setSuspendedQuiet(false);
	}
	
	/**
	 * Notifies this thread that is about to be resumed due
	 * to a VM resume.
	 */
	public synchronized void resumedByVM() throws DebugException {
		StudioDebugCorePlugin.debug("Resumed by VM:"+getName());
		setRunning(true);
		setStepping(false);
		preserveStackFrames();
		// This method is called *before* the VM is actually resumed.
		// To ensure that all threads will fully resume when the VM
		// is resumed, make sure the suspend count of each thread
		// is no greater than 1. 
		ThreadReference thread= getUnderlyingThread();
		while (thread.suspendCount() > 1) {
			try {
				thread.resume();
			} catch (ObjectCollectedException e) {
			} catch (VMDisconnectedException e) {
				disconnected();
			}catch (RuntimeException e) {
				setRunning(false);
				fireSuspendEvent(DebugEvent.CLIENT_REQUEST);
				targetRequestFailed(MessageFormat.format("Exception resuming thread {0}", new Object[] {e.toString()}), e); //				
			}
		}
	}

	@Override
	public synchronized void suspend() throws DebugException {
//		getDebugTarget().suspend();
		try {
			// Abort any pending step request
//			abortStep();
//			setSuspendedQuiet(false);
			//fEvaluationInterrupted = isPerformingEvaluation();
			suspendUnderlyingThread();
		} catch (RuntimeException e) {
			setRunning(true);
			targetRequestFailed(MessageFormat.format(
					"{0} occurred suspending thread.",
					new Object[] { e.toString() }), e);
		}
	}
	
	public boolean isUnderlyingThreadSuspended() {
		return fThread.isSuspended();
	}
	
	/**
	 * Suspends the underlying thread asynchronously and fires notification when
	 * the underlying thread is suspended.
	 */
	protected synchronized void suspendUnderlyingThread() {
		if (fIsSuspending) {
			return;
		}
		if (isSuspended()) {
			fireSuspendEvent(DebugEvent.CLIENT_REQUEST);
			return;
		}
		fIsSuspending= true;
		Thread rthread= new Thread(new Runnable() {
			public void run() {
				try {
					fThread.suspend();
					int timeout= RuleDebugModel.getPreferences().getInt(RuleDebugModel.PREF_REQUEST_TIMEOUT,RuleDebugModel.DEF_REQUEST_TIMEOUT);
					long stop= System.currentTimeMillis() + timeout;
					boolean suspended= isUnderlyingThreadSuspended();
					while (System.currentTimeMillis() < stop && !suspended) {
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
						}
						suspended= isUnderlyingThreadSuspended();
						if (suspended) {
							break;
						}
					}
					if (!suspended) {
						IStatus status = new Status(
								IStatus.ERROR,
								StudioDebugCorePlugin.getUniqueIdentifier(),
								-1,
								MessageFormat
										.format(
												"Thread did not suspend within specified timeout: {0} (ms)",
												new Object[] { new Integer(
														timeout).toString() }),
								null);
						IStatusHandler handler = DebugPlugin.getDefault()
								.getStatusHandler(status);
						if (handler != null) {
							try {
								handler.handleStatus(status, RuleDebugThread.this);
							} catch (CoreException e) {
							}
						}
					}
					setRunning(false);
					fireSuspendEvent(DebugEvent.CLIENT_REQUEST);
				} catch (RuntimeException exception) {
				} finally {
					fIsSuspending= false;
				}
			}
		});
		rthread.setDaemon(true);
		rthread.start();
	}
	
	public void setProcessStepping(int f) {
		fProcessStepping = f;
	}
	
	
	private boolean isProcessCanStepInto() {
		return (fProcessStepping & PROCESS_STEP_INTO) == PROCESS_STEP_INTO;
	}
	private boolean isProcessCanStepOver() {
		return (fProcessStepping & PROCESS_STEP_OVER) == PROCESS_STEP_OVER;
	}
	
	private boolean isProcessCanStepReturn() {
		return (fProcessStepping & PROCESS_STEP_RETURN) == PROCESS_STEP_RETURN;
	}
	

	@Override
	public boolean canStepInto() {
		return canStep() && 
				((isProcessTarget() && isProcessCanStepInto())
						|| (isProcessTarget() && isJavaTask() && isJavaTaskStepInto())
						||(!isProcessTarget()));
	}

	
	private boolean isJavaTask() {
		
		try {
			return DebuggerSupport.isProcessJavaTaskContext(this);
		} catch (DebugException e) {
			DebugPlugin.log(e);
			return false;
		}
	}
	
	
	private boolean isJavaTaskStepInto() {
		return true;
	}

	

	@Override
	public boolean canStepOver() {
		return canStep() && 
				((isProcessTarget() && isProcessCanStepOver())
						|| (isProcessTarget() && isJavaTask() && isJavaTaskStepOver())
						||(!isProcessTarget()));
	}

	

	private boolean isJavaTaskStepOver() {
		return true;
	}

	@Override
	public boolean canStepReturn() {
		return canStep() && 
				((isProcessTarget() && isProcessCanStepReturn())
						|| (isProcessTarget() && isJavaTask() && isJavaTaskStepReturn())
						||(!isProcessTarget()));
	}
	
	

	private boolean isJavaTaskStepReturn() {
		try {
			List<RuleDebugStackFrame> frames = computeStackFrames();
			if (frames.size() > 1) {
				RuleDebugStackFrame prevFrame = frames.get(0);
				Location stackLocation = prevFrame.getUnderlyingStackFrame().location();
				Method currentMethod = stackLocation.method();
				LinkedList<? extends Comparable> allLines = new LinkedList<Location>(currentMethod.allLineLocations());
				return allLines.size() > 0;
			}
			
		} catch (Exception e) {
			StudioDebugCorePlugin.log(e);
		}
		return false;
	}

	/**
	 * Returns whether this thread is in a valid state to
	 * step.
	 * 
	 * @return whether this thread is in a valid state to
	 * step
	 */
	protected boolean canStep() {
		try {
			return (isSuspended() || isStepping()) && 
					(!isPerformingEvaluation() || isInvokingMethod()) 
					&& (getTopStackFrame() != null);
		} catch (DebugException e) {
			return false;
		}
	}
	
	

	private boolean isProcessTarget() {
		if (getTargetAgentMap() == null) {
			return false;
		}
		return getTargetAgentMap().get(getCurrentRuleSessionName()) == CacheAgent.Type.PROCESS;
//		return getTargetSessionMap().containsValue("com.tibco.cep.bpmn.runtime.agent.ProcessRuleSession");
	}
	
	protected Map<String,String> getTargetSessionMap() {
		IRuleRunTarget rt = (IRuleRunTarget) getAdapter(IRuleRunTarget.class);
		return rt.getRuleSessionMap();
	}
	
	protected Map<String,CacheAgent.Type> getTargetAgentMap() {
		IRuleRunTarget rt = (IRuleRunTarget) getAdapter(IRuleRunTarget.class);
		return rt.getAgentTypeMap();
	}
	protected String getCurrentRuleSessionName() {
		IRuleRunTarget rt = (IRuleRunTarget) getAdapter(IRuleRunTarget.class);
		try {
			return rt.getCurrentRuleSessionName(this);
		} catch (DebugException e) {
			StudioDebugCorePlugin.log(e);
			return null;
		}
	}

	public synchronized void setStepping(boolean stepping) {
		fStepping = stepping;
	}

	@Override
	public boolean isStepping() {
//		return ((RuleDebugTarget)getDebugTarget()).isStepping();
		return fStepping;
	}

	/*@Override
	public void stepInto() throws DebugException {
		synchronized(this) {
			if (!canStepInto()) {
				return;
			}
			((RuleDebugTarget) getDebugTarget()).stepInto(this);
		}
	}

	@Override
	public void stepOver() throws DebugException {
		synchronized(this){
			if (!canStepOver()) {
				return;
			}
		}
		((RuleDebugTarget) getDebugTarget()).stepOver(this);
	}

	@Override
	public void stepReturn() throws DebugException {
		synchronized(this) {
			if (!canStepReturn()) {
				return;
			}
		}
		((RuleDebugTarget) getDebugTarget()).stepReturn(this);
	}*/

	/**
	 * Implementation of a scheduling rule for this thread, which defines how it should behave 
	 * when a request for content job tries to run while the thread is evaluating
	 */
	class SerialPerObjectRule implements ISchedulingRule {
    	
    	private Object fObject = null;
    	
    	public SerialPerObjectRule(Object lock) {
    		fObject = lock;
    	}

		/* (non-Javadoc)
		 * @see org.eclipse.core.runtime.jobs.ISchedulingRule#contains(org.eclipse.core.runtime.jobs.ISchedulingRule)
		 */
		public boolean contains(ISchedulingRule rule) {
			return rule == this;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.core.runtime.jobs.ISchedulingRule#isConflicting(org.eclipse.core.runtime.jobs.ISchedulingRule)
		 */
		public boolean isConflicting(ISchedulingRule rule) {
			if (rule instanceof SerialPerObjectRule) {
				SerialPerObjectRule vup = (SerialPerObjectRule) rule;
				return fObject == vup.fObject;
			}
			return false;
		}
    	
    }	
	
	/**
	 * returns the scheduling rule for getting content while evaluations are running
	 * @return the <code>ISchedulingRule</code> for this thread 
	 */
	public ISchedulingRule getThreadRule() {
		return new SerialPerObjectRule(this);
	}
	
	public void stepOver() throws DebugException  {
		synchronized(this){
			if (!canStepOver()) {
				return;
			}
		}
		
		ISchedulingRule rule = getThreadRule();
		try {
			Job.getJobManager().beginRule(rule, null);
			synchronized (this) {
				StudioDebugCorePlugin.debug("Step Over");
				RuleDebugStackFrame frame = (RuleDebugStackFrame) getTopStackFrame();
				if (frame == null) {
					return;
				}
				RunSession session =  (RunSession) getDebugTarget().getAdapter(RunSession.class);
				List<IStepHandler> stepHandlers = session.getStepHandlers();
				for(IStepHandler s: stepHandlers) {
					if(s.handlesSession(getCurrentRuleSessionName(),getTargetAgentMap())) {
						s.stepOver(this,frame);
						break;
					} else if (getCurrentRuleSessionName() == null && s.getClass().equals(RuleStepHandler.class)) {
						// BE-26117 this can happen for rule functions that are still executing outside of a rule session/post RTC (i.e thread affinity functions)
						s.stepOver(this,frame);
					}
				}
				/**
				 * moved to RuleStepHandler which handles stepping in rule code
				 */
//				final Map<Object,Object> props = new HashMap<Object,Object>();
//				setDebugActionStateFlags(DEBUG_ACTION_STEP_OVER | DEBUG_ACTION_STATUS_INVOKE_USER);
//				props.put(DebuggerConstants.ACTION_FLAGS, DEBUG_ACTION_STEP_OVER | DEBUG_ACTION_STATUS_INVOKE_USER);
//				final Location stackLocation = frame.getUnderlyingStackFrame().location();
//				MappedResourcePosition pos = ((RuleDebugTarget) getDebugTarget())
//						.getSourceMapper().getBEPosition(
//								stackLocation.declaringType().name(),
//								stackLocation.lineNumber());
//				preserveStackFrames();
//				if(pos != null && (pos.getLineMask() & MappedResourcePosition.LAST_LINE) != 0) { // code pointer is on the last line
//					createStep(StepRequest.STEP_LINE, StepRequest.STEP_INTO, props, true);
//				} else {
//					createStep(StepRequest.STEP_LINE, StepRequest.STEP_OVER, props, true);
//				}
//				setRunning(true);
//				fireResumeEvent(DebugEvent.STEP_OVER);
//				invokeThread();
			}
		} finally {
			Job.getJobManager().endRule(rule);
		}
		
    }
	
	/*boolean isLastLine(List srcMaps, Integer lineNo){
		Iterator<SourceMap> iter = srcMaps.iterator();
		while (iter.hasNext()) {
			SourceMap sourceMap = (SourceMap) iter.next();
			Entry e = sourceMap.getLastLineEntry();
			return ((Integer)e.getKey() == lineNo);
		}
		return false;
	}*/

    public void stepInto() throws DebugException  {
		synchronized(this) {
			if (!canStepInto()) {
				return;
			}
		}
    	ISchedulingRule rule = getThreadRule();
		try {
			Job.getJobManager().beginRule(rule, null);
			synchronized (this) {
				StudioDebugCorePlugin.debug("Step Into");
				RuleDebugStackFrame frame = (RuleDebugStackFrame) getTopStackFrame();
				if (frame == null) {
					return;
				}
				RunSession session =  (RunSession) getDebugTarget().getAdapter(RunSession.class);
				List<IStepHandler> stepHandlers = session.getStepHandlers();
				for(IStepHandler s: stepHandlers) {
					if(s.handlesSession(getCurrentRuleSessionName(),getTargetAgentMap())) {
						s.stepInto(this,frame);
						break;
					} else if (getCurrentRuleSessionName() == null && s.getClass().equals(RuleStepHandler.class)) {
						// BE-26117 this can happen for rule functions that are still executing outside of a rule session/post RTC (i.e thread affinity functions)
						s.stepInto(this, frame);
					}
				}
				/**
				 * moved to RuleStepHandler which handles stepping in rule code
				 */
//				setDebugActionStateFlags(DEBUG_ACTION_STEP_IN | DEBUG_ACTION_STATUS_INVOKE_USER);
//				Map<Object,Object> props = new HashMap<Object,Object>();
//				props.put(DebuggerConstants.ACTION_FLAGS, DEBUG_ACTION_STEP_IN | DEBUG_ACTION_STATUS_INVOKE_USER);
//				createStep(StepRequest.STEP_LINE, StepRequest.STEP_INTO, props, true);
//				setRunning(true);
//				preserveStackFrames();
//				fireResumeEvent(DebugEvent.STEP_INTO);
//				invokeThread();
			}
		} finally {
			Job.getJobManager().endRule(rule);
		}
    }

    public void stepReturn() throws DebugException  {
    	
		synchronized(this) {
			if (!canStepReturn()) {
				return;
			}
		}
		
    	ISchedulingRule rule = getThreadRule();
		try {
			Job.getJobManager().beginRule(rule, null);
			synchronized (this) {
				StudioDebugCorePlugin.debug("Step Return");
				RuleDebugStackFrame frame = (RuleDebugStackFrame) getTopStackFrame();
				if (frame == null) {
					return;
				}
				RunSession session =  (RunSession) getDebugTarget().getAdapter(RunSession.class);
				List<IStepHandler> stepHandlers = session.getStepHandlers();
				for(IStepHandler s: stepHandlers) {
					if(s.handlesSession(getCurrentRuleSessionName(),getTargetAgentMap())) {
						s.stepReturn(this,frame);
						break;
					} else if (getCurrentRuleSessionName() == null && s.getClass().equals(RuleStepHandler.class)) {
						// BE-26117 this can happen for rule functions that are still executing outside of a rule session/post RTC (i.e thread affinity functions)
						s.stepReturn(this,frame);
					}
				}
				/**
				 * moved to RuleStepHandler which handles stepping in rule code
				 */
				// step out needs to be handled differently because the caller function is in BE 
				// rete code and may not map to a rule or rule-function code always therefore it is
				// is necessary to find the next step location in the following manner
				// check if the previous stack frame from the top most stack frame maps to a
				// displayable line in a rule or rule-function, if it does then call the step out
				// otherwise follow this method
				// 1. goto the end of the current function
				// 2. put hidden break points on all possible BE code location entry points where it
				//    can hit next and wait for the hit
				// 3. once the hit happens, remove the hidden breakpoints
//				IStackFrame[] frames = getStackFrames();
//				Map<Object,Object> props = new HashMap<Object,Object>();
//				props.put(DebuggerConstants.ACTION_FLAGS, DEBUG_ACTION_STEP_OUT | DEBUG_ACTION_STATUS_INVOKE_USER);
//				if(frames.length > 0 && ((RuleDebugStackFrame)frames[1]).getMappedSourceLine() != -1) {
//					setDebugActionStateFlags(DEBUG_ACTION_STEP_OUT | DEBUG_ACTION_STATUS_INVOKE_USER);
//					createStep(StepRequest.STEP_LINE, StepRequest.STEP_OUT, props,true);
//					setRunning(true);
//					preserveStackFrames();
//					fireResumeEvent(DebugEvent.STEP_RETURN);
//					invokeThread();
//				} else {
//					setDebugActionStateFlags(DEBUG_ACTION_STEP_IN | DEBUG_ACTION_STATUS_INVOKE_USER);
//					createStepOutBreakPoints(props);
//					setRunning(true);
//					preserveStackFrames();
//					fireResumeEvent(DebugEvent.STEP_INTO);
//					invokeThread();
//				}// end else
			}
		} finally {
			Job.getJobManager().endRule(rule);
		}    	
    }
    
    /**
     * @param stepSize
     * @param detail  <code>StepRequest.STEP_IN,StepRequest.STEP_OUT</code>
     * @param propertyMap
     * @param resume
     * @param isJavaSource TODO
     * @return
     * @throws CoreException
     */
    public boolean createStep(int stepSize, int detail, Map<Object,Object> propertyMap,
			boolean resume, boolean isJavaSource) throws DebugException {
    	try {
			ensureActiveVM();
			synchronized (this) {
				clearPreviousStep();
				StepRequest stepReq = getEventRequestManager().createStepRequest(
						getUnderlyingThread(), stepSize, detail);
				ReferenceType smartStepInto = ((DebugSessionImpl)((RuleDebugTarget)getDebugTarget()).getSession()).getSmartStepClass();
				if(!isJavaSource)
					stepReq.addClassFilter(smartStepInto);
				stepReq.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
				for (Iterator<Entry<Object, Object>> it = propertyMap.entrySet().iterator(); it.hasNext();) {
					Entry<Object, Object> entry =  it.next();
					stepReq.putProperty(entry.getKey(), entry.getValue());
				}
				stepReq.enable();
				setRunning(true);
				setStepping(true);
			}
			return resume;
		} catch (CoreException e) {
			abort("Failed to step over", e);
			return false;
		}
	}
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void createStepOutBreakPoints(Map props) throws CoreException {
        Location stackLocation = null;
        ensureActiveVM();
        try {
        	RuleDebugStackFrame frame = (RuleDebugStackFrame) getTopStackFrame();
            stackLocation = frame.getUnderlyingStackFrame().location();
            Method currentMethod = stackLocation.method();
            LinkedList<? extends Comparable> allLines = new LinkedList<Location>(currentMethod.allLineLocations());
            Collections.sort((List)allLines,new LocationComparator());
            Location lastLine = (Location) allLines.getLast();
            if(lastLine.lineNumber() == stackLocation.lineNumber()) { // code pointer is on the last line
//                createBreakPointOnAllLocations(props);
            	createStep(StepRequest.STEP_LINE, StepRequest.STEP_INTO, props, true, false);
            } else { // resume upto the end of method breakpoint
                Map propertyMap = new HashMap(props);
                propertyMap.put(DebuggerConstants.BREAKPOINT_TYPE, DebuggerConstants.BREAKPOINT_ENDOFMETHOD);
                StudioDebugCorePlugin.debug("Adding breakpoint request [type:"
						+ DebuggerConstants.BREAKPOINT_ENDOFMETHOD + ":"
						+ lastLine.declaringType().name() + ":"
						+ lastLine.lineNumber() + "]");
                final String projectName = ((IRuleRunTarget)getDebugTarget()).getProjectName();
                IResource resource = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName).findMember(frame.getMappedSourcePath());
                if(resource == null) {
                	resource = ResourcesPlugin.getWorkspace().getRoot();
                }
    			IntermediateBreakpoint bp = RuleDebugModel.createIntermediateBreakpoint(resource, 
    					lastLine.declaringType().name(), 
    					lastLine.lineNumber(),
    					-1,
    					-1, 
    					0, 
    					false, 
    					propertyMap);
    			getDebugTarget().breakpointAdded(bp);
            }
        }
        catch (AbsentInformationException e) {
            StudioDebugCorePlugin.log(e);
        }
    }
    
	public void clearPreviousStep() {
		EventRequestManager erm = getEventRequestManager();
		if(erm == null) return;
		List<EventRequest> requests = new ArrayList<EventRequest>(1);
		List<StepRequest> steps = erm.stepRequests();
		for (StepRequest request : steps) {
			if (request.thread().equals(getUnderlyingThread())) {
				requests.add(request);
			}
		}
		erm.deleteEventRequests(requests);
	}
    
	@Override
	public boolean canTerminate() {
		return getDebugTarget().canTerminate();
	}

	@Override
	public boolean isTerminated() {
		return fTerminated;
	}

	@Override
	public void terminate() throws DebugException {
		getDebugTarget().terminate();
	}
	
	@Override
	public synchronized boolean hasAgendaItem() throws DebugException {
		return ((IRuleRunTarget)getDebugTarget()).getAgendaItem(this) != null;
	}
	
	public synchronized void addCurrentBreakpoint(IBreakpoint bp) {
		if(!fCurrentBreakpoints.contains(bp)){
			fCurrentBreakpoints.add(bp);
		}
	}
	
	public synchronized void removeCurrentBreakpoint(IBreakpoint bp) {
		fCurrentBreakpoints.remove(bp);
	}
	
	@Override
	public synchronized IBreakpoint[] getBreakpoints() {
		return (IBreakpoint[])fCurrentBreakpoints.toArray(new IBreakpoint[fCurrentBreakpoints.size()]);
	}
	
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		if (adapter == IRuleDebugThread.class) {
			return this;
		} else if( adapter == RuleDebugThread.class) {
			return this;
		} else	if (adapter == RuleDebugStackFrame.class) {
			try {
				return getTopStackFrame();
			} catch (DebugException e) {
				// do nothing if not able to get frame
			} 
		}
		return super.getAdapter(adapter);
	}

	public boolean isPerformingEvaluation() {
		return false;
	}

	public synchronized void disposeStackFrames() {
		fStackFrames.clear();
		fRefreshChildren = true;		
	}
	
	
	/**
	 * Resumes the underlying thread to initiate the step.
	 * By default the thread is resumed. Step handlers that
	 * require other actions can override this method.
	 * 
	 * @exception DebugException if this method fails.  Reasons include:
	 * <ul>
	 * <li>Failure communicating with the VM.  The DebugException's
	 * status code contains the underlying exception responsible for
	 * the failure.</li>
	 * </ul>
	 */
	public void invokeThread() throws DebugException {
		try {
			fThread.resume();
		} catch (RuntimeException e) {
//			stepEnd();
			targetRequestFailed(MessageFormat.format("{0} occurred stepping in thread.", new Object[] {e.toString()}), e); 
		}
	}
	
	/**
	 * Invokes a constructor in this thread, creating a new instance of the given
	 * class, and returns the result as an object reference.
	 * This thread is left suspended after the invocation
	 * is complete.
	 * <p>
	 * Method invocations cannot be nested. That is, this method must
	 * return before another call to this method can be made. This
	 * method does not return until the invocation is complete.
	 * Breakpoints can suspend a method invocation, and it is possible
	 * that an invocation will not complete due to an infinite loop
	 * or deadlock.
	 * </p>
	 * <p>
	 * Stack frames are preserved during method invocations, unless
	 * a timeout occurs. Although this thread's state is updated to
	 * running while performing an evaluation, no debug events are
	 * fired unless this invocation is interrupted by a breakpoint,
	 * or the invocation times out.
	 * </p>
	 * <p>
	 * When performing an invocation, the communication timeout with
	 * the target VM is set to infinite, as the invocation may not 
	 * complete in a timely fashion, if at all. The timeout value
	 * is reset to its original value when the invocation completes.
	 * </p>
	 * 
	 * @param receiverClass the class in the target representing the receiver
	 * 	of the 'new' message send
	 * @param constructor the underlying constructor to be invoked
	 * @param args the arguments to invoke the constructor with (an empty list
	 *  if none) 
	 * @return a new object reference
	 * @exception DebugException if this method fails.  Reasons include:
	 * <ul>
	 * <li>Failure communicating with the VM.  The DebugException's
	 * status code contains the underlying exception responsible for
	 * the failure.</li>
	 * </ul>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public synchronized ObjectReference newInstance(ClassType receiverClass, Method constructor, List args) throws DebugException {
		if (isInvokingMethod()) {
			requestFailed("Rule Debug Thread cannot perform nested evaluations", null); 
		}
		ObjectReference result= null;
//		int timeout= getRequestTimeout();
		try {
			// set the request timeout to be infinite
//			setRequestTimeout(Integer.MAX_VALUE);
			setRunning(true);
			setInvokingMethod(true);
			preserveStackFrames();
			result= receiverClass.newInstance(getUnderlyingThread(), constructor, args, ClassType.INVOKE_SINGLE_THREADED);
		} catch (InvalidTypeException e) {
			invokeFailed(e);
		} catch (ClassNotLoadedException e) {
			invokeFailed(e);
		} catch (IncompatibleThreadStateException e) {
			invokeFailed(e);
		} catch (InvocationException e) {
			invokeFailed(e);
		} catch (RuntimeException e) {
			invokeFailed(e);
		}

		invokeComplete();
		return result;
	}
	
	/**
	 * Invokes a method on the target, in this thread, and returns the result. Only
	 * one receiver may be specified - either a class or an object, the other must
	 * be <code>null</code>. This thread is left suspended after the invocation
	 * is complete, unless a call is made to <code>abortEvaluation<code> while
	 * performing a method invocation. In that case, this thread is automatically
	 * resumed when/if this invocation (eventually) completes.
	 * <p>
	 * Method invocations cannot be nested. That is, this method must
	 * return before another call to this method can be made. This
	 * method does not return until the invocation is complete.
	 * Breakpoints can suspend a method invocation, and it is possible
	 * that an invocation will not complete due to an infinite loop
	 * or deadlock.
	 * </p>
	 * <p>
	 * Stack frames are preserved during method invocations, unless
	 * a timeout occurs. Although this thread's state is updated to
	 * running while performing an evaluation, no debug events are
	 * fired unless this invocation is interrupted by a breakpoint,
	 * or the invocation times out.
	 * </p>
	 * <p>
	 * When performing an invocation, the communication timeout with
	 * the target VM is set to infinite, as the invocation may not 
	 * complete in a timely fashion, if at all. The timeout value
	 * is reset to its original value when the invocation completes.
	 * </p>
	 * 
	 * @param receiverClass the class in the target representing the receiver
	 * 	of a static message send, or <code>null</code>
	 * @param receiverObject the object in the target to be the receiver of
	 * 	the message send, or <code>null</code>
	 * @param method the underlying method to be invoked
	 * @param args the arguments to invoke the method with (an empty list
	 *  if none) 
	 * @return the result of the method, as an underlying value
	 * @exception DebugException if this method fails.  Reasons include:
	 * <ul>
	 * <li>Failure communicating with the VM.  The DebugException's
	 * status code contains the underlying exception responsible for
	 * the failure.</li>
	 * <li>This thread is not suspended
	 * 	(status code <code>IJavaThread.ERR_THREAD_NOT_SUSPENDED</code>)</li>
	 * <li>This thread is already invoking a method
	 * 	(status code <code>IJavaThread.ERR_NESTED_METHOD_INVOCATION</code>)</li>
	 * <li>This thread is not suspended by a JDI request
	 * 	(status code <code>IJavaThread.ERR_INCOMPATIBLE_THREAD_STATE</code>)</li>
	 * </ul>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public synchronized Value invokeMethod(ClassType receiverClass, ObjectReference receiverObject, Method method, List args, boolean invokeNonvirtual) throws DebugException {
		if (receiverClass != null && receiverObject != null) {
			throw new IllegalArgumentException("Rule Debug Thread can only specify one receiver for a method invocation"); 
		}
		Value result= null;
		//int timeout= getRequestTimeout();
		try {
			// this is synchronized such that any other operation that
			// might be resuming this thread has a chance to complete before
			// we determine if it is safe to continue with a method invocation.
			// See bugs 6518, 14069
			synchronized (this) {
				if (!isSuspended()) {
					requestFailed("Rule Debug Thread Evaluation failed thread not suspended", null, IRuleDebugThread.DEBUG_THREAD_NOT_SUSPENDED);
				}
				if (isInvokingMethod()) {
					requestFailed("Rule Debug Thread cannot perform nested evaluations", null, IRuleDebugThread.DEBUG_THREAD_INVOCATION_INPROGRESS); 
				}
				// set the request timeout to be infinite
				//setRequestTimeout(Integer.MAX_VALUE);
				setRunning(true);
				setInvokingMethod(true);				
			}
			preserveStackFrames();
			int flags= ClassType.INVOKE_SINGLE_THREADED;
			if (invokeNonvirtual) {
				// Superclass method invocation must be performed nonvirtual.
				flags |= ObjectReference.INVOKE_NONVIRTUAL;
			}
			if (receiverClass == null) {
				result= receiverObject.invokeMethod(getUnderlyingThread(), method, args, flags);
			} else {
				result= receiverClass.invokeMethod(getUnderlyingThread(), method, args, flags);
			}
			
		} catch (InvalidTypeException e) {
			invokeFailed(e);
		} catch (ClassNotLoadedException e) {
			invokeFailed(e);
		} catch (IncompatibleThreadStateException e) {
			invokeComplete();
//			invokeFailed("Thread must be suspended by step or breakpoint to perform method invocation",IRuleDebugThread.DEBUG_THREAD_NOT_SUSPENDED,e); 
		} catch (InvocationException e) {
			invokeFailed(e);
		} catch (ObjectCollectedException e) {
			invokeFailed("Object garbage collected before invoking method: " + method , -1, e);
		} catch (RuntimeException e) { 
			invokeFailed(e);
		}

		invokeComplete();
		return result;
	}
		
		/**
		 * Called when an invocation fails. Performs cleanup
		 * and throws an exception.
		 * 
		 * @param e the exception that caused the failure
		 * @param restoreTimeout the communication timeout value,
		 * 	in milliseconds, that should be reset
		 * @see #invokeComplete(int)
		 * @exception DebugException.  Reasons include:
		 * <ul>
		 * <li>Failure communicating with the VM.  The DebugException's
		 * status code contains the underlying exception responsible for
		 * the failure.</li>
		 * </ul>
		 */
		protected void invokeFailed(Throwable e) throws DebugException {
			invokeFailed(MessageFormat.format("Exception invoking method {0}", new Object[] {e.toString()}), DebugException.TARGET_REQUEST_FAILED, e); 
		}
		
		/**
		 * Called when an invocation fails. Performs cleanup
		 * and throws an exception.
		 * 
		 * @param message error message
		 * @param code status code
		 * @param e the exception that caused the failure
		 * @param restoreTimeout the communication timeout value,
		 * 	in milliseconds, that should be reset
		 * @see #invokeComplete(int)
		 * @exception DebugException.  Reasons include:
		 * <ul>
		 * <li>Failure communicating with the VM.  The DebugException's
		 * status code contains the underlying exception responsible for
		 * the failure.</li>
		 * </ul>
		 */
		protected void invokeFailed(String message, int code, Throwable e) throws DebugException {
			invokeComplete();
			requestFailed(message, e, code);
		}	
		
		/**
		 * Called when a method invocation has returned, successfully
		 * or not. This method performs cleanup:<ul>
		 * <li>Resets the state of this thread to suspended</li>
		 * <li>Restores the communication timeout value</li>
		 * <li>Computes the new set of stack frames for this thread</code>
		 * </ul>
		 * 
		 * @param restoreTimeout the communication timeout value,
		 * 	in milliseconds, that should be reset
		 * @see #invokeMethod(ClassType, ObjectReference, Method, List)
	 	 * @see #newInstance(ClassType, Method, List)
		 */
		protected synchronized void invokeComplete() {
//	        if (!fIsEvaluatingConditionalBreakpoint) {
//	            abortStep();
//	        }
			setInvokingMethod(false);
			setRunning(false);
//			setRequestTimeout(restoreTimeout);
			// update preserved stack frames
			try {
				computeStackFrames();
			} catch (DebugException e) {
				logError(e);
			}
		}
		
		/**
		 * Sets whether this thread is currently invoking a method
		 * 
		 * @param evaluating whether this thread is currently
		 *  invoking a method
		 */
		protected void setInvokingMethod(boolean invoking) {
			fIsInvokingMethod= invoking;
		}
		
		/**
		 * Returns whether this thread is currently performing
		 * a method invocation 
		 */
		public boolean isInvokingMethod() {
			return fIsInvokingMethod;
		}
		
		
		/**
		 * @see ISuspendResume#resume()
		 * 
		 * Updates the state of this thread, but only fires
		 * notification to listeners if <code>fireNotification</code>
		 * is <code>true</code>.
		 */
		private synchronized void resumeThread(boolean fireNotification) throws DebugException {
			if (!isSuspended() || (isPerformingEvaluation() && !isInvokingMethod())) {
				StudioDebugCorePlugin.debug("Failed to resume thread, it is not suspended or invoking method or performing eval:"+getName());
				return;
			}
			try {
				setRunning(true);
				setStepping(false);
//				setSuspendedQuiet(false);
				if (fireNotification) {
					fireResumeEvent(DebugEvent.CLIENT_REQUEST);
				}
				preserveStackFrames();
				StudioDebugCorePlugin.debug("Resuming Thread:"+getName());
				getUnderlyingThread().resume();
			} catch (VMDisconnectedException e) {
				disconnected();
			} catch (RuntimeException e) {
				setRunning(false);
				fireSuspendEvent(DebugEvent.CLIENT_REQUEST);
				targetRequestFailed(MessageFormat.format("Exception resuming thread {0}", new Object[] {e.toString()}), e); 
			}
		}

		public void terminated() {
			setTerminated(true);
			setRunning(false);	
			fireTerminateEvent();
			
		}


		private void setTerminated(boolean terminated) {
			fTerminated= terminated;
			
		}
		
		/**
		 * Sets whether this thread is currently executing.
		 * When set to <code>true</code>, this thread's current
		 * breakpoints are cleared.
		 * 
		 * @param running whether this thread is executing
		 */
		public synchronized void setRunning(boolean running) {
			fRunning = running;
			if (running) {
				fCurrentBreakpoints.clear();
			} 
		}
		
		
		
		
		/**
		 * @return the lastActionState
		 */
		public int getLastActionState() {
			return lastActionState;
		}

		/**
		 * @param lastActionState the lastActionState to set
		 */
		public synchronized void setLastActionState(int lastActionState) {
			this.lastActionState = lastActionState;
		}

		/**
		 * @return the debugActionState
		 */
		public int getDebugActionState() {
			return debugActionState;
		}

		/**
		 * @param debugActionState the debugActionState to set
		 */
		public synchronized void setDebugActionState(int debugActionState) {
			this.debugActionState = debugActionState;
		}


		/**
		 * @return the lastPosition
		 */
		public IResourcePosition getLastPosition() {
			return lastPosition;
		}



		/**
		 * @param lastPosition the lastPosition to set
		 */
		public synchronized void setLastPosition(IResourcePosition lastPosition) {
			this.lastPosition = lastPosition;
		}

		public synchronized void setDebugActionStateFlags(int flags) {
	        if (flags != getDebugActionState()) {
	            setLastActionState(getDebugActionState());
	            setDebugActionState(flags);
	            if((getActionStatus(flags)& DEBUG_ACTION_STATUS_COMPLETED) != 0) {
	            	//TODO - Update menu items
	            	//updateActionMenuItems(getActionType(flags));
	            }  else {
	                //disableActionMenuItems(getActionType(flags));
	            }
	        }
		}
		
		public static int getActionStatus(int val) { 
	    	return val & DEBUG_ACTION_STATUS_MASK; 
	    }
	    
	    public static int getActionType(int val) { 
	    	return val & DEBUG_ACTION_TYPE_MASK; 
	    }
	    
	    public int getDebugActionType() {
	        return getActionType(debugActionState) ;
	    }

	    public int getDebugActionStatus() {
	        return getActionStatus(debugActionState) ;
	    }
	    
	    public String toString() {
	    	try{ 
		    	if(fThread != null) {
		    		return fThread.name();
		    	}
	    	} catch(VMDisconnectedException e) {
	    		// do nothing
	    	}
	    	return super.toString();
	    }

		
    
}
