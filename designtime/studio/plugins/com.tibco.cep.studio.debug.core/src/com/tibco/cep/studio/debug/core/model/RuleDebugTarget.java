package com.tibco.cep.studio.debug.core.model;

import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_START;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_STATUS_INVOKE_USER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.IBreakpointManagerListener;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchListener;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugModelProvider;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.jdt.debug.core.IJavaLineBreakpoint;
import org.eclipse.jdt.debug.core.JDIDebugModel;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.impl.DebugSessionImpl;
import com.tibco.cep.studio.debug.core.model.var.RuleAgendaVariable;
import com.tibco.cep.studio.debug.input.VmTask;
import com.tibco.cep.studio.debug.smap.SourceMapperImpl;

/**
 * @author pdhar
 *
 */
public class RuleDebugTarget 
	extends AbstractDebugTarget 
	implements IRuleDebugTarget,
			ILaunchListener,
			IDebugModelProvider,
			IBreakpointManagerListener,
			IDebugEventSetListener {


	public RuleDebugTarget(ILaunch launch, VirtualMachine vm,String name, IProcess process, String hostName, String port, boolean supportsDisconnect, boolean supportsTerminate, boolean resumeOnStartup) {
		super(null,vm);
		setName(name);
		setLaunch(launch);
		setProcess(process);
		setHostName(hostName);
		setPort(port);
		setDebugTarget(this);
		setSourceMapper(new SourceMapperImpl(this));
		setResumeOnStartup(resumeOnStartup);
		setSupportsDisconnect(supportsDisconnect);
		setSupportsTerminate(supportsTerminate);
		setInputQueue(new LinkedBlockingQueue<VmTask>(100));
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointManagerListener(this);
		DebugPlugin.getDefault().getLaunchManager().addLaunchListener(this);		
	}
	

	

	//Life cycle methods
    /**
     * intialize the debug target
     * @return
     * @throws CoreException
     */
    public synchronized void init() throws DebugException {
    	try {
    		super.init(); 
    		
			if (initStudioProject()) {
				try {
					setSession(new DebugSessionImpl(this, getLaunch(),getVM()));
					getSession().init();						
				} catch (Exception e) {
					abort("Failed to parse debug source line map.", e);
				}
			}
		} catch (CoreException e) {
			abort(e.getMessage(), e);
		}

    }	
	
    public void start() throws DebugException {

    	Map<String,Integer> props = new HashMap<String,Integer>();
    	props.put(DebuggerConstants.ACTION_FLAGS, DEBUG_ACTION_START | DEBUG_ACTION_STATUS_INVOKE_USER);
    	getSession().start(props);
  
    }
    
    
    
    @Override
	public DebugSession getSession() {
		return (DebugSession) super.getSession();
	}

	

    
	@Override
	public boolean hasThreads() throws DebugException {
		if(getSession() == null || getSession().getDebugState() < DebugSession.STATE_RUNNING) {
			return false;
		} else
			return getSession().getThreadsCache().getThreads().length > 0;
	}
	
	/**
	 * Installs all Java breakpoints that currently exist in
	 * the breakpoint manager
	 */
	public void initializeBreakpoints() {
		if(!isInitializedBreakpoints()){
			
			IBreakpointManager manager= DebugPlugin.getDefault().getBreakpointManager();
			manager.addBreakpointListener(this);
			IBreakpoint[] bps = manager.getBreakpoints(RuleDebugModel.getModelIdentifier());
			for (int i = 0; i < bps.length; i++) {
				if (bps[i] instanceof IRuleBreakpoint) {
					breakpointAdded(bps[i]);
				}
			}
			IBreakpoint[] jbps = manager.getBreakpoints(JDIDebugModel.getPluginIdentifier());
			for (int i = 0; i < jbps.length; i++) {
				if (jbps[i] instanceof IJavaLineBreakpoint) {
					breakpointAdded(jbps[i]);
				}
			}
			setInitializedBreakpoints();
		}
	}

	@Override
	public boolean canTerminate() {
		return supportsTerminate() && isAvailable() ;
	}
	
	public boolean isStepping() {
		if(getSession() == null) {
			return false;
		}
		return getSession().isStepping();
	}
	


	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	public void terminate() throws DebugException {
		try {
			if (!supportsTerminate()) {
				notSupported("VM does not support termination."); 
			}
			DebugSession debug_session = getSession();
			if( debug_session != null) {
				setTerminating(true);
				synchronized(debug_session){
					// Do we need to set thread flags on terminate, the thread is going to get cleaned up in terminated().
//					RuleDebugThread rdt = getSession().getDebugThreadInfo();
//					if(rdt != null){
//						synchronized (rdt) {
//							rdt.setLastPosition(null);
//							rdt.setDebugActionStateFlags( DEBUG_ACTION_STOP | DEBUG_ACTION_STATUS_INVOKE_USER | DEBUG_ACTION_STATUS_COMPLETED );
//						}
//					}
					if(debug_session.getDebugState() != DebugSession.STATE_DISCONNECTED) {
						debug_session.stop();
					}
					if (debug_session.getVM() != null) {
						debug_session.getVM().exit(1);
					}
					IProcess process= getProcess();
					if (process != null) {
						process.terminate();
					}
				}
			} 
			
		} catch (VMDisconnectedException e) {
			// if the VM disconnects while exiting, perform 
			// normal termination processing
			terminated();
		}  finally {
			terminated();
		}
	}
	
	
	


	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.core.model.IRuleRunTarget#getAgendaItem()
	 */
	@Override
	public ObjectReference getAgendaItem(RuleDebugThread rdt) throws DebugException {
		RuleDebugStackFrame topFrame = (RuleDebugStackFrame) rdt.getTopStackFrame();
		RtcAgendaFrame agendaFrame = new RtcAgendaFrame(rdt,topFrame.getUnderlyingStackFrame(),-2);
		IVariable[] agendaVars = agendaFrame.getVariables();
		if(agendaVars.length > 0) {
			RuleAgendaVariable av = (RuleAgendaVariable) agendaVars[0];
			ObjectReference ruleMirror = DebuggerSupport.getAgendaRule(rdt,(ObjectReference) av.getJdiValue());
			return ruleMirror;
		}
		return null;
	}





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
	
	/*public void stepOver(RuleDebugThread rdt) throws DebugException  {
		ISchedulingRule rule = getThreadRule();
		try {
			Job.getJobManager().beginRule(rule, null);
			synchronized (rdt) {
				RuleDebugStackFrame frame = (RuleDebugStackFrame) rdt.getTopStackFrame();
				if (frame == null) {
					return;
				}
				final Map<String,Integer> props = new HashMap<String,Integer>();
				rdt.setDebugActionStateFlags(DEBUG_ACTION_STEP_OVER | DEBUG_ACTION_STATUS_INVOKE_USER);
				props.put(DebuggerConstants.ACTION_FLAGS, DEBUG_ACTION_STEP_OVER | DEBUG_ACTION_STATUS_INVOKE_USER);
				final Location stackLocation = frame.getUnderlyingStackFrame().location();
				MappedResourcePosition pos = sourceMapper.getBEPosition(stackLocation.declaringType().name(), stackLocation.lineNumber());
				if(pos != null && (pos.getLineMask() & MappedResourcePosition.LAST_LINE) != 0) { // code pointer is on the last line
					getSession().stepInto(rdt, props);
				} else {
					getSession().stepOver(rdt, props);
				}
				rdt.setRunning(true);
				rdt.preserveStackFrames();
				rdt.fireResumeEvent(DebugEvent.STEP_OVER);
				rdt.invokeThread();
			}
		} finally {
			Job.getJobManager().endRule(rule);
		}
		
    }*/

    /*public void stepInto(RuleDebugThread rdt) throws DebugException  {
    	ISchedulingRule rule = getThreadRule();
		try {
			Job.getJobManager().beginRule(rule, null);
			synchronized (rdt) {
				RuleDebugStackFrame frame = (RuleDebugStackFrame) rdt.getTopStackFrame();
				if (frame == null) {
					return;
				}
				rdt.setDebugActionStateFlags(DEBUG_ACTION_STEP_IN | DEBUG_ACTION_STATUS_INVOKE_USER);
				Map<String,Integer> props = new HashMap<String,Integer>();
				props.put(DebuggerConstants.ACTION_FLAGS, DEBUG_ACTION_STEP_IN | DEBUG_ACTION_STATUS_INVOKE_USER);
				getSession().stepInto(rdt, props);
				rdt.setRunning(true);
				rdt.preserveStackFrames();
				rdt.fireResumeEvent(DebugEvent.STEP_INTO);
				rdt.invokeThread();
			}
		} finally {
			Job.getJobManager().endRule(rule);
		}
    	
    }*/

    /*public void stepReturn(RuleDebugThread rdt) throws DebugException  {
    	ISchedulingRule rule = getThreadRule();
		try {
			Job.getJobManager().beginRule(rule, null);
			synchronized (rdt) {
				
				// step out needs to be handled differently because the caller function is in BE 
				// rete code and may not map to a rule or rulefunction code always therefore it is
				// is neccessary to find the next step location in the following manner
				// check if the previous stack frame from the top most stack frame maps to a
				// displayable line in a rule or rulefunction , if it does then call the step out
				// otherwise follow this method
				// 1. goto the end of the current function
				// 2. put hidden break points on all possible BE code location entry points where it
				//    can hit next and wait for the hit
				// 3. once the hit happens ,remove the hidden breakpoints
				IStackFrame[] frames = rdt.getStackFrames();
				Map<Object,Object> props = new HashMap<Object,Object>();
				props.put(DebuggerConstants.ACTION_FLAGS, DEBUG_ACTION_STEP_OUT | DEBUG_ACTION_STATUS_INVOKE_USER);
				if(frames.length > 0 && ((RuleDebugStackFrame)frames[1]).getMappedSourceLine() != -1) {
					rdt.setDebugActionStateFlags(DEBUG_ACTION_STEP_OUT | DEBUG_ACTION_STATUS_INVOKE_USER);
					getSession().stepReturn(rdt, props);
					rdt.setRunning(true);
					rdt.preserveStackFrames();
					rdt.fireResumeEvent(DebugEvent.STEP_RETURN);
					rdt.invokeThread();
				} else {
					rdt.setDebugActionStateFlags(DEBUG_ACTION_STEP_IN | DEBUG_ACTION_STATUS_INVOKE_USER);
					getSession().createStepOutBreakPoints(rdt, props);
					rdt.setRunning(true);
					rdt.preserveStackFrames();
					rdt.fireResumeEvent(DebugEvent.STEP_INTO);
					rdt.invokeThread();
				}// end else
			}
		}  catch (CoreException e) {
 			abort("Failed to step out", e);
 		}  finally {
			Job.getJobManager().endRule(rule);
		}    	
    }*/
    
    @Override
	protected void cleanup() {
		super.cleanup();
		removeAllBreakpoints();
		DebugPlugin plugin = DebugPlugin.getDefault();
		plugin.getBreakpointManager().removeBreakpointManagerListener(this);
        plugin.removeDebugEventListener(this);
	}

	
	
	/**
	 * Returns whether this debug target supports popping stack frames.
	 * 
	 * @return whether this debug target supports popping stack frames.
	 */
	public boolean canPopFrames() {
		if (isAvailable() && DebuggerSupport.isJdiVersionGreaterThanOrEqual(new int[] {1,4})) {
			VirtualMachine vm = getSession().getVM();
			if (vm != null) {
				return vm.canPopFrames();
			}
		}
		return false;
	}

	
	
	/**
	 * Updates the state of this target for disconnection
	 * from the VM.
	 */
	public void disconnected() {
		if (!isDisconnected()) {
			setDisconnected(true);
			cleanup();
			fireTerminateEvent();
		}
	}

	@Override
	public IMemoryBlock getMemoryBlock(long startAddress, long length)
			throws DebugException {
		return null;
	}

	@Override
	public boolean supportsStorageRetrieval() {
		return false;
	}

	public void started() {
		StudioDebugCorePlugin.debug("Rule Debug Target Started");
		fireCreationEvent();
		
	}	
	
    /* (non-Javadoc)
     * @see org.eclipse.debug.core.ILaunchListener#launchAdded(org.eclipse.debug.core.ILaunch)
     */
    public void launchAdded(ILaunch launch) {
    	
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.debug.core.ILaunchListener#launchChanged(org.eclipse.debug.core.ILaunch)
     */
    public void launchChanged(ILaunch launch) {
    }

    /* (non-Javadoc)
     * @see org.eclipse.debug.core.ILaunchListener#launchRemoved(org.eclipse.debug.core.ILaunch)
     */
    public void launchRemoved(ILaunch launch) {
    	if (!isTerminated()) {
			return;
		}
		if (launch.equals(getLaunch())) {
			// This target has been de-registered, but it hasn't successfully terminated.
			// Update internal state to reflect that it is disconnected
			disconnected();
		}
    	
    }
    
    


	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.core.model.AbstractDebugTarget#getBreakPointListener()
	 */
	@Override
	public IBreakpointListener getBreakPointListener() {
		return this;
	}




	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.core.model.AbstractDebugTarget#getLaunchListener()
	 */
	@Override
	public ILaunchListener getLaunchListener() {
		return this;
	}




	/**
	 * The StudioDebugCorePlugin is shutting down.
	 * Shutdown the event dispatcher and do local
	 * cleanup.
	 */
	public void shutdown() {
		try {
			if(getSession() != null) {
				getSession().stop();
			}
			if (supportsTerminate()) {
				terminate();
			} else if (supportsDisconnect()) {
				disconnect();
			}
		} catch (DebugException e) {
			StudioDebugCorePlugin.log(e);
		} finally {
			cleanup();
		}
	}


	/**
	 * When the breakpoint manager disables, remove all registered breakpoints
	 * requests from the VM. When it enables, reinstall them.
	 */
	@SuppressWarnings("unchecked")
	public void breakpointManagerEnablementChanged(boolean enabled) {
		if (!isAvailable()) {
			return;
		}
		Iterator<IRuleBreakpoint> breakpoints= ((ArrayList<IRuleBreakpoint>)((ArrayList<IRuleBreakpoint>)getBreakpoints()).clone()).iterator();
		while (breakpoints.hasNext()) {
			IRuleBreakpoint breakpoint= (IRuleBreakpoint) breakpoints.next();
			try {
				if (enabled) {
					breakpoint.addToTarget(this);
				} else if (breakpoint.shouldSkipBreakpoint()) {
					breakpoint.removeFromTarget(this);
				}
			} catch (CoreException e) {
				logError(e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.IDebugEventSetListener#handleDebugEvents(org.eclipse.debug.core.DebugEvent[])
	 */
	@Override
	public void handleDebugEvents(DebugEvent[] events) {
		if (events.length == 1) {
            DebugEvent event = events[0];
            if (event.getSource().equals(getProcess()) && event.getKind() == DebugEvent.TERMINATE) {
            	new CleanUpJob().schedule(3000);
            }
        }		
	}
	
	class CleanUpJob extends Job {

		/**
		 * Constructs a job to cleanup a hanging target.
		 */
		public CleanUpJob() {
			super("Disconnecting Java Debug Target");
			setSystem(true);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.core.internal.jobs.InternalJob#run(org.eclipse.core.runtime.IProgressMonitor)
		 */
		protected IStatus run(IProgressMonitor monitor) {
			if (isAvailable()) {
				if (getSession() != null) {
					try {
						getSession().stop();
					} catch (DebugException e) {
						StudioDebugCorePlugin.log(e);
					} finally {
						cleanup();
					}
				}
				disconnected();
			}
			return Status.OK_STATUS;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.core.runtime.jobs.Job#shouldRun()
		 */
		public boolean shouldRun() {
			return isAvailable();
		}

		/* (non-Javadoc)
		 * @see org.eclipse.core.internal.jobs.InternalJob#shouldSchedule()
		 */
		public boolean shouldSchedule() {
			return isAvailable();
		}
		
		
	}
}
