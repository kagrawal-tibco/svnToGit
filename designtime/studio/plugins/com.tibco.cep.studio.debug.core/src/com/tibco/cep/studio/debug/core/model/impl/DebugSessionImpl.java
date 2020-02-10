package com.tibco.cep.studio.debug.core.model.impl;


import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_RESUME;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_STATUS_COMPLETED;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_ACTION_STATUS_INVOKE_USER;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.ISuspendResume;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassType;
import com.sun.jdi.Location;
import com.sun.jdi.Method;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.LocatableEvent;
import com.sun.jdi.event.StepEvent;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.ClassUnloadRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.ExceptionRequest;
import com.sun.jdi.request.StepRequest;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineState;
import com.tibco.cep.runtime.model.element.stategraph.impl.StateTransitionRule;
import com.tibco.cep.runtime.service.debug.SmartStepInto;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.AbstractDebugTarget;
import com.tibco.cep.studio.debug.core.model.DebugSession;
import com.tibco.cep.studio.debug.core.model.DebuggerConstants;
import com.tibco.cep.studio.debug.core.model.DebuggerSupport;
import com.tibco.cep.studio.debug.core.model.IRuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.IRuleDebugThread;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.core.model.IntermediateBreakpoint;
import com.tibco.cep.studio.debug.core.model.RuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.RuleDebugModel;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;
import com.tibco.cep.studio.debug.smap.SourceMap;
import com.tibco.cep.studio.debug.smap.SourceMapper;
import com.tibco.cep.studio.debug.util.LocationComparator;

/**
 * @author pdhar
 *
 */
public class DebugSessionImpl extends AbstractSession implements DebugSession {

    private ReferenceType smartStepClass;
    /**
     * @param target
     * @param launch
     * @param vm
     */
    public DebugSessionImpl(AbstractDebugTarget target, ILaunch launch,VirtualMachine vm) {
        super(target,launch,vm);
        this.fLaunch = launch;
    }

    /**
	 * @param classFilterSet the classFilterSet to set
	 */
	public void setClassFilterSet(HashSet<ReferenceType> classFilterSet) {
		this.classFilterSet = classFilterSet;
	}

	/**
	 * @param classExclusionFilterSet the classExclusionFilterSet to set
	 */
	public void setClassExclusionFilterSet(
			HashSet<ReferenceType> classExclusionFilterSet) {
		this.classExclusionFilterSet = classExclusionFilterSet;
	}

	/**
	 * @return the debugStateListeners
	 */
	public PropertyChangeSupport getDebugStateListeners() {
		return debugStateListeners;
	}

	/**
	 * @param debugStateListeners the debugStateListeners to set
	 */
	public void setDebugStateListeners(PropertyChangeSupport debugStateListeners) {
		this.debugStateListeners = debugStateListeners;
	}

	/**
	 * @return the smartStepClass
	 */
	public ReferenceType getSmartStepClass() {
		return smartStepClass;
	}

	/**
	 * @param smartStepClass the smartStepClass to set
	 */
	public void setSmartStepClass(ReferenceType smartStepClass) {
		this.smartStepClass = smartStepClass;
	}

	/**
	 * @param l
	 */
	public void addPropertyChangeListener(PropertyChangeListener l) {
        debugStateListeners.addPropertyChangeListener(l);
    }

    /**
     * @param l
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        debugStateListeners.removePropertyChangeListener(l);
    }

    @SuppressWarnings("rawtypes")
	public void start(Map props) throws DebugException {
		try {
			setDebugState(STATE_STARTING);
			super.start(props);
			EventRequestManager em = getEventRequestManager();
			createBEGenClassPrepareRequest(em);
			createRSPClassPrepareRequest(em);
			createWorkingMemoryClassPrepareRequest(em);
			createWmStartClassPrepareRequest(em);
			createSmartStepClassPrepareRequest(em);
			createDebuggerServiceClassPrepareRequest(em);
			createDebugTaskFactoryClassPrepareRequest(em);
			createStateTransitionClassPrepareRequest(em);
			createPropertyStateMachineStateClassPrepareRequest(em);
//			boolean isAttaching = profile
//					.getLaunchConfiguration()
//					.getAttribute(
//							IStudioDebugLaunchConfigurationConstants.ATTR_ISATTACH,
//							false);
			if (getDebugTarget() instanceof IRuleRunTarget) {
				IRuleRunTarget target = (IRuleRunTarget) getDebugTarget();
				if(target.supportsDisconnect()) {
					ReferenceType classRef = DebuggerSupport.findClass(getVM(),
							SmartStepInto.class.getName());
					if (classRef != null) {
						setSmartStepClass(classRef);
					}
					classRef = DebuggerSupport.findClass(getVM(),
							WorkingMemoryImpl.class.getName());
					if (classRef != null) {
						createRTCBreakPoint(classRef);
					}
					classRef = DebuggerSupport.findClass(getVM(),
							RuleServiceProviderImpl.class.getName());
					if (classRef != null) {
						createInitProjectBreakPoint(classRef);
					}
					if (AddonUtil.isProcessAddonInstalled()) {
						classRef = DebuggerSupport.findClass(getVM(), "com.tibco.cep.bpmn.runtime.activity.TaskRegistry");
						if (classRef != null) {
							setTaskRegistryType(classRef);
							createTaskRegistryInitBreakPoint(classRef);
						}
					}
//					MOVED to super class					
//					classRef = DebuggerSupport.findClass(getVM(),
//							DebuggerService.class.getName());
//					if (classRef != null) {
//						setDebuggerServiceType(classRef);
//						// do not create debugger service break point at startup and keep it running
//						// since breakpoints can be inserted or removed at any time after the referencetype
//						// has been loaded, so it should be created on user input in the following manner
//						// 1. let the user set the input and issue the command
//						// 2. set the break point 
//						// 3. on breakpoint hit execute the task
//						// 4. remove the break point
//						// 5. resume the vm
////					createDebuggerServiceBreakPoint(debuggerServiceType);
//					}
//					if(getDebugTarget() instanceof IRuleDebugTarget) {
//						List<ReferenceType> clazzez = getVM().allClasses();
//						for (ReferenceType clazz : clazzez) {
//							setBreakPointsForReferenceType(clazz);
//						}
//					}
				}
			}

			ClassUnloadRequest classUnloadRequest = em
					.createClassUnloadRequest();
			classUnloadRequest.setSuspendPolicy(EventRequest.SUSPEND_NONE);
			classUnloadRequest.enable();

//			ThreadStartRequest threadStartRequest = em
//					.createThreadStartRequest();
//			threadStartRequest.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
//			for (Iterator it = props.entrySet().iterator(); it.hasNext();) {
//				Map.Entry entry = (Map.Entry) it.next();
//				threadStartRequest
//						.putProperty(entry.getKey(), entry.getValue());
//			}
//			threadStartRequest.putProperty(DebuggerConstants.THREAD_START_REQUEST_TYPE,DebuggerConstants.THREAD_START_APP);
//			threadStartRequest.enable();
//			ThreadDeathRequest threadDeathRequest = em
//					.createThreadDeathRequest();
//			threadDeathRequest.setSuspendPolicy(EventRequest.SUSPEND_NONE);
//			threadDeathRequest.enable();
			ExceptionRequest exceptionRequest = em.createExceptionRequest(null,
					false, true);
			exceptionRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
			exceptionRequest.enable();
			
			
			
//			validateThreadInfo();
//			this.interrupted = true;
//			setDebugState(STATE_RUNNING);
//			getVM().resume();
//			resume();
		} catch (CoreException e) {
			abort("Failed to attach to the debugee JVM",e);
		}
		
	}

	

    /**
     * @param em
     */
    private void createBEGenClassPrepareRequest(EventRequestManager em) {
        ClassPrepareRequest classPrepareRequest = em.createClassPrepareRequest();
        classPrepareRequest.addClassExclusionFilter("java.*");
        classPrepareRequest.addClassExclusionFilter("javax.*");
        classPrepareRequest.addClassExclusionFilter("org.*");
        classPrepareRequest.addClassExclusionFilter("com.*");
        classPrepareRequest.addClassExclusionFilter("sun.*");
        classPrepareRequest.addClassExclusionFilter("iaik.*");
        classPrepareRequest.addClassExclusionFilter("javassist.*");
        classPrepareRequest.addClassExclusionFilter("COM.TIBCO.*");
        classPrepareRequest.addClassExclusionFilter("{.*}");
//        //### We must allow the deferred breakpoints to be resolved before
//        //### we continue executing the class.  We could optimize if there
//        //### were no deferred breakpoints outstanding for a particular class.
//        //### Can we do this with JDI?
        classPrepareRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        classPrepareRequest.enable();
    }



    /**
     * @param em
     */
    private void createSmartStepClassPrepareRequest(EventRequestManager em) {
        ClassPrepareRequest classPrepareRequest;
        classPrepareRequest = em.createClassPrepareRequest();
        classPrepareRequest.addClassFilter(SmartStepInto.class.getName());
        classPrepareRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        classPrepareRequest.enable();
    }

    /**
     * @param em
     */
    private void createStateTransitionClassPrepareRequest(EventRequestManager em) {
        ClassPrepareRequest classPrepareRequest;
        classPrepareRequest = em.createClassPrepareRequest();
        classPrepareRequest.addClassFilter(StateTransitionRule.class.getName());
        classPrepareRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        classPrepareRequest.enable();
    }

    /**
     * @param em
     */
    private void createPropertyStateMachineStateClassPrepareRequest(EventRequestManager em) {
        ClassPrepareRequest classPrepareRequest;
        classPrepareRequest = em.createClassPrepareRequest();
        classPrepareRequest.addClassFilter(PropertyStateMachineState.class.getName());
        classPrepareRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        classPrepareRequest.enable();
    }

    /**
     * @param em
     */
    private void createWorkingMemoryClassPrepareRequest(EventRequestManager em) {
        ClassPrepareRequest classPrepareRequest;
        classPrepareRequest = em.createClassPrepareRequest();
        classPrepareRequest.addClassFilter(WorkingMemoryImpl.class.getName());
        classPrepareRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        classPrepareRequest.enable();
    }
    
    /**
     * @param em
     */
    private void createWmStartClassPrepareRequest(EventRequestManager em) {
        ClassPrepareRequest classPrepareRequest;
        classPrepareRequest = em.createClassPrepareRequest();
        classPrepareRequest.addClassFilter(ReteWM.class.getName());
        classPrepareRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        classPrepareRequest.enable();
    }
    
    /**
     * @param em
     */
    private void createRSPClassPrepareRequest(EventRequestManager em) {
        ClassPrepareRequest classPrepareRequest;
        classPrepareRequest = em.createClassPrepareRequest();
        classPrepareRequest.addClassFilter(RuleServiceProviderImpl.class.getName());
        classPrepareRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
        classPrepareRequest.enable();
    }


//    public void suspendOld() throws DebugException {
//        try {
//			ensureActiveSession();
//			getVM().suspend();
//			List<ThreadReference> threads = getVM().allThreads();
//			for (ThreadReference t : threads) {
//				try {
//					while (t.suspendCount() > 1) {
//						t.resume();
//					}
//				} catch (IllegalThreadStateException e) {
//				} catch (ObjectCollectedException e) {
//				} catch (InternalException e) {
//				}
//			}
//			validateThreadInfo();
//			setDebugState(STATE_SUSPENDED);        
//		} catch (CoreException e1) {
//			getRuleDebugTarget().abort("Failed to pause debug session", e1);
//		}
//    }
    
    /**
	 * @see ISuspendResume
	 */
	public void suspend() throws DebugException {
		if (isSuspended()) {
			return;
		}
		try {
			VirtualMachine vm = getVM();
			if (vm != null) {
				vm.suspend();
			}
			suspendThreads();
			setDebugState(STATE_SUSPENDED);
			getRuleDebugTarget().fireSuspendEvent(DebugEvent.CLIENT_REQUEST);
		} catch (RuntimeException e) {
			getRuleDebugTarget().fireResumeEvent(DebugEvent.CLIENT_REQUEST);
			targetRequestFailed(MessageFormat.format("{0} occurred suspending VM.", new Object[] {e.toString()}), e); 
		}
		
	}

    /* (non-Javadoc)
     * @see com.tibco.cep.studio.debug.core.model.RunSession#resume()
     */
    public void resume() throws DebugException{
        resume(true);
    }
    
    /**
     * @param fireNotification
     * @throws DebugException
     */
    public void resume(boolean fireNotification) throws DebugException{
    	try {
        	if (getDebugState() > DebugSession.STATE_STARTING && ((!isSuspended() && !hasSuspendedThreads()) 
        			|| !getRuleDebugTarget().isAvailable())) {
        			return;
        	}
			ensureActiveSession();
			setDebugState(STATE_RUNNING);
			RuleDebugThread rdt = getDebugThreadInfo();
			if(rdt != null) {
				synchronized (rdt) {
					rdt.setLastPosition(null);
					rdt.preserveStackFrames();
					if (rdt.getDebugActionType() != DEBUG_ACTION_RESUME) {
						rdt.setDebugActionStateFlags(DEBUG_ACTION_RESUME | DEBUG_ACTION_STATUS_INVOKE_USER | DEBUG_ACTION_STATUS_COMPLETED);
					}
				}
			}
			resumeThreads();

			VirtualMachine vm = getVM();
			if (vm != null) {
				vm.resume();
			}
			if (fireNotification) {
				getRuleDebugTarget().fireResumeEvent(DebugEvent.CLIENT_REQUEST);
			} else {
				StudioDebugCorePlugin.debug("Resuming session without notification.");
			}
		}catch (VMDisconnectedException e) {
			disconnected();
			return;
		} catch (RuntimeException e) {
//			setSuspended(true);
			fireSuspendEvent(DebugEvent.CLIENT_REQUEST);
			targetRequestFailed(MessageFormat.format("{0} occurred resuming VM.", new Object[] {e.toString()}), e); 
		} catch (CoreException e) {
			abort("Failed to resume debug session", e);
		}
    }
    
    /* (non-Javadoc)
     * @see com.tibco.cep.studio.debug.core.model.RunSession#isSuspended()
     */
    public boolean isSuspended() {
    	switch(getDebugState()) {
    	case STATE_BREAKPOINT:
    	case STATE_SUSPENDED:
    	case STATE_STEPPING:
    		 return true;
    	default:
    		return false;
    	}
	}
    
    @Override
    public boolean isStepping() {
    	return getDebugState() == DebugSession.STATE_STEPPING;
    }
    
    /**
	 * Returns whether this target has any threads which are suspended.
	 * @return true if any thread is suspended, false otherwise
	 * @since 4.0
	 */
	private boolean hasSuspendedThreads() {
		Iterator<IRuleDebugThread> it = getThreadsCache().getThreadIterator();
		while(it.hasNext()){
			RuleDebugThread thread = (RuleDebugThread) it.next();
			if(thread.isSuspended())
				return true;
		}
		return false;
	}

    /* (non-Javadoc)
     * @see com.tibco.cep.studio.debug.core.model.RunSession#stop(boolean)
     */
    public void stop() throws DebugException  {
        if(getDebugState() == STATE_DISCONNECTED) {
            return;
        }
        try {
        	if(getEventDispatcher() != null && getEventDispatcher().isRunning()) {
        		getEventDispatcher().stop();
        	}
//            getThreadsCache().removeAllThreads();
//            getThreadsCache().cleanup();
            getClassFilterSet().clear();
            getClassExclusionFilterSet().clear();
        } catch (VMDisconnectedException e) {
            abort("Failed to stop debug session.",e);
        } catch (RuntimeException e) {
        	targetRequestFailed("Failed to stop debug session", e);
        }
        finally {
            setEventDispatcher(null);
            setDebugState(STATE_DISCONNECTED);
        }
    }    

    /*
     * Handle LocatableEvent by verifying availability if BE code to step or suspend
     */
	public boolean onLocatableEvent(final LocatableEvent le) {

		final RuleDebugThread rdt = getDebugThreadInfo(le.thread());
		
		final Location location = le.location();
        
        final String javaName = location.declaringType().name();
        
        final int lineNo = location.lineNumber();
        
        SourceMapper sourceMapper = getRuleDebugTarget().getSourceMapper();
        
        final IMappedResourcePosition loc = sourceMapper.getBEPosition(javaName, lineNo);
        
        synchronized (rdt) {
        	try {
        		if (loc != null) {
        			if (!loc.equals(rdt.getLastPosition())) {
						rdt.setLastPosition(loc);
					} else {
						// check if the same line match caused by step,breakpoint event pair then the 
						// first event in the pair must have suspended it already
						if(!rdt.isSuspended()) {
								// do not remove the existing step request and let it continue
								rdt.setRunning(true);
								rdt.resumedByVM();
								return true;
						}
					}
        			int lastActionType = rdt.getDebugActionType();
        			if (le instanceof BreakpointEvent) {
        				IRuleBreakpoint bp = getBreakPointFromEvent((BreakpointEvent) le);
        				if (bp != null) {  
        					// this is user defined breakpoint                  	
        					StudioDebugCorePlugin.debug("Suspending at Location:" + location.toString());
        					rdt.clearPreviousStep();
        					rdt.addCurrentBreakpoint(bp);
        					rdt.setRunning(false);
        					rdt.queueSuspendEvent(DebugEvent.BREAKPOINT);
        					rdt.setDebugActionStateFlags(lastActionType | DEBUG_ACTION_STATUS_COMPLETED);        					
        				} 
        			} else if (le instanceof StepEvent) {
        				// found the next step line
        				StudioDebugCorePlugin.debug("Stepping on line: " + location);
        				rdt.setRunning(false);
        				rdt.setStepping(false);
        				rdt.queueSuspendEvent(DebugEvent.STEP_END);
        				rdt.setDebugActionStateFlags(lastActionType | DEBUG_ACTION_STATUS_COMPLETED);
        			}
        		} else {
        			// no displayable location found in rule code 
        			// therefore continue the last step request again
        			// by not removing it from the EventRequestManager
        			if(le instanceof BreakpointEvent) {
        				rdt.setRunning(false);
        				rdt.addCurrentBreakpoint(getBreakPointFromEvent((BreakpointEvent) le));
        			}
        			rdt.setRunning(true);
        			rdt.resumedByVM();
        			return true;
        		}
        	} catch (Exception e) {
        		StudioDebugCorePlugin.log(e);
        	}
		} // end sync block
        return false;
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.studio.debug.core.model.impl.AbstractSession#onBreakpoint(com.sun.jdi.event.BreakpointEvent)
     */
//    public EventState onBreakpoint(BreakpointEvent bpe){
//    	String source;
//		int line;
//		try {
//			source = bpe.location().sourceName();
//			line = bpe.location().lineNumber();
//			
//		} catch (AbsentInformationException e) {
//			source = "Unknown Source";
//			line = -1;
//		}
//        EventState eventState =  super.onBreakpoint(bpe);
//        RuleDebugThread rdt = getDebugThreadInfo(bpe.thread());
//        try {
//
//        	if(!eventState.isHandled()) {
//        		IRuleBreakpoint bp = getBreakPointFromEvent((BreakpointEvent) bpe);;
//        		String bpType = bp.getBreakpointType();
//        		if (bpType != null) {
//        			StudioDebugCorePlugin.debug("Breakpoint["+ bpType+":"+source+":"+line+"]");
//        			if (bpType.equals(DebuggerConstants.BREAKPOINT_ENDOFMETHOD)) {
//        				synchronized (rdt) {
//        					rdt.clearPreviousStep();
//        					rdt.setRunning(false);
//        					rdt.addCurrentBreakpoint(bp);
//        					rdt.fireSuspendEvent(DebugEvent.BREAKPOINT);
//						}
//        				cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_ENDOFMETHOD);
//        				
//        				//Map propertyMap = new HashMap();
//        				//propertyMap.put(DebuggerConstants.ACTION_FLAGS, rdt.getDebugActionState());
//        				rdt.stepInto();
//        				eventState.setResume(true);
//        				eventState.setHandled(true);
//        				synchronized (rdt) {
//        					rdt.setRunning(true);
//        					rdt.preserveStackFrames();
//        					rdt.fireResumeEvent(DebugEvent.CLIENT_REQUEST);
//        					rdt.resumedByVM();
//						}
//        				StudioDebugCorePlugin.debug("Resuming End of Method.");
//        			} else if (bpType.equals(DebuggerConstants.BREAKPOINT_STEPOUT)) {
//        				synchronized (rdt) {
//        					rdt.setRunning(false);
//        					rdt.addCurrentBreakpoint(bp);
//        					cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_STEPOUT);
//        					eventState.setResume(onLocatableEvent(bpe));
//        					eventState.setHandled(true);
//						}
//        			} else if (bpType.equals(DebuggerConstants.BREAKPOINT_PROCESS_RECORDED)) {
//        				rdt.clearPreviousStep();
//        				cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_STEPOUT);
//        				cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_ENDOFMETHOD);
//        				eventState.setHandled(true);
//        				eventState.setResume(getRuleDebugTarget().onEndOfRTC(bpe));
//        				setDebugState(STATE_RUNNING);
//        				StudioDebugCorePlugin.debug("Resuming End of RTC.");
//        			} else if(bpType.equals(DebuggerConstants.BREAKPOINT_PROCESS)){
//        				
//        			}else {
//        				cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_STEPOUT);
//        				cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_ENDOFMETHOD);
//        				eventState.setResume(onLocatableEvent(bpe));
//        				eventState.setHandled(true);
//        			}
//        		} else { // unknown type
//        			eventState.setResume(onLocatableEvent(bpe));
//        			eventState.setHandled(false);
//        			StudioDebugCorePlugin.log("Breakpoint type not found. Breakpoint encountered on line " + source + ":" + line );
//        		}
//        		
//        		if(!eventState.canResume()) {
//        			rdt.clearPreviousStep();
//        			setDebugState(STATE_BREAKPOINT);
//        		} 
//        	}
//        	
//        } catch(CoreException e) {
//        	logError(e);
//        }
//        return eventState;
//    }
    
   



    /* (non-Javadoc)
     * @see com.tibco.cep.studio.debug.core.model.impl.AbstractSession#onStep(com.sun.jdi.event.StepEvent)
     */
    public EventState onStep(EventSet eventSet,StepEvent se){

    	EventState eventState = super.onStep(eventSet,se);
    	if(!eventState.isHandled()) {
    		boolean resume = onLocatableEvent(se);
    		eventState.setResume(resume);
    		eventState.setHandled(true);
    		if(!eventState.canResume()) {
    			setDebugState(STATE_STEPPING);
    			deleteStepRequest((StepRequest) se.request());
    		}
    	}

    	return eventState;
    }
    
    /**
	 * Deletes this handler's step request from the underlying VM
	 * and removes this handler as an event listener.
	 */
	protected void deleteStepRequest(StepRequest request) {
//		removeJDIEventListener(this, getStepRequest());
		try {
			EventRequestManager manager = getEventRequestManager();
			if (manager != null) {
				manager.deleteEventRequest(request);
			}				
//			setStepRequest(null);
		} catch (RuntimeException e) {
			logError(e);
		}
	}

    /* (non-Javadoc)
     * @see com.tibco.cep.studio.debug.core.model.DebugSession#stepOver(java.util.Map)
     */
    /*public boolean stepOver(RuleDebugThread rdt, Map propertyMap) throws DebugException {
        try {
			return createStep(rdt, StepRequest.STEP_LINE, StepRequest.STEP_OVER, propertyMap,true);
		} catch (CoreException e) {
			abort("Failed to step over", e);
			return false;
		}
    }*/

    /* (non-Javadoc)
     * @see com.tibco.cep.studio.debug.core.model.DebugSession#stepInto(java.util.Map)
     */
    /*public boolean stepInto(RuleDebugThread rdt, Map propertyMap) throws DebugException{
        try {
			return createStep(rdt, StepRequest.STEP_LINE, StepRequest.STEP_INTO, propertyMap,true);
		} catch (CoreException e) {
			abort("Failed to step into", e);
			return false;
		}
    }*/

    /* (non-Javadoc)
     * @see com.tibco.cep.studio.debug.core.model.DebugSession#stepOut(java.util.Map)
     */
    /*public boolean stepReturn(RuleDebugThread rdt, Map propertyMap) throws DebugException{
    	try {
			return createStep(rdt, StepRequest.STEP_LINE, StepRequest.STEP_OUT, propertyMap,true);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			abort("Failed to step out.",e);
			return false;
		}
    }*/

    /**
     * @param stepSize
     * @param detail  <code>StepRequest.STEP_IN,StepRequest.STEP_OUT</code>
     * @param propertyMap
     * @param resume
     * @return
     * @throws CoreException
     */
    private boolean createStep(RuleDebugThread rdt, int stepSize, int detail, Map<Object,Object> propertyMap,
			boolean resume) throws CoreException {
    	ensureActiveSession();

		synchronized (rdt) {
			rdt.clearPreviousStep();
			StepRequest stepReq = getEventRequestManager().createStepRequest(
					rdt.getUnderlyingThread(), stepSize, detail);
			stepReq.addClassFilter(smartStepClass);
			stepReq.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
			for (Iterator<Entry<Object, Object>> it = propertyMap.entrySet().iterator(); it.hasNext();) {
				Entry<Object, Object> entry =  it.next();
				stepReq.putProperty(entry.getKey(), entry.getValue());
			}
			stepReq.enable();
			rdt.setRunning(true);
			rdt.setStepping(true);
		}
		return resume;
		
	}
    
    /* (non-Javadoc)
     * @see com.tibco.cep.studio.debug.core.model.impl.AbstractSession#inputAction()
     */
    @Override
    public void addInputVmTask(Object key) throws DebugException {
    	try {
			super.addInputVmTask(key);
		} catch (CoreException e) {
			abort("Failed to execute input task", e);
		}
    }

//    /* (non-Javadoc)
//     * @see com.tibco.cep.studio.debug.core.model.DebugSession#setBreakPoint(java.lang.String, int, java.util.Map)
//     */
//    public void setBreakPoint(String className, int lineNumber, Map propertyMap) throws CoreException {
//        ensureActiveSession();
//        try {
//            List<ReferenceType> references = getVM().classesByName(className);
//            for (ReferenceType ref : references) {
//                List<Location> lines = ref.locationsOfLine(lineNumber);
//                if (lines.size() > 0) {
//                    BreakpointRequest bpr = getEventRequestManager().createBreakpointRequest(lines.get(0));
//                    bpr.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
//                    for (Iterator it = propertyMap.entrySet().iterator(); it.hasNext();) {
//                        Map.Entry entry = (Map.Entry) it.next();
//                        bpr.putProperty(entry.getKey(), entry.getValue());
//                    }                    
//                    bpr.enable();
//                    synchronized (breakpoints) {
//                        breakpoints.add(bpr);
//                    }
////                    StudioDebugCorePlugin.debug("Breakpoint set on :"+className+":"+lineNumber);
//                }
//
//            }
//        }
//        catch (Exception e) {
//        	StudioDebugCorePlugin.log("Breakpoint exception...", e);
//        }
//    }

    /* (non-Javadoc)
     * @see com.tibco.cep.studio.debug.core.model.DebugSession#hasBreakPoint(com.sun.jdi.Location)
     */
    public boolean hasBreakPoint(Location location) throws CoreException {
        ensureActiveSession();
        List<BreakpointRequest> bprs = getEventRequestManager().breakpointRequests();
        for(BreakpointRequest bpr:bprs) {
            if(bpr.location().equals(location)) {
                return true;
            }
        }
        return false;
    }
    /**
     * attach with the target vm
     * @param hostname
     * @param port
     * @return
     * @throws CoreException
     */
    /*private VirtualMachine attach(String hostname, String port)
			throws CoreException {
		VirtualMachine vm = null;
		VirtualMachineManager vmm = Bootstrap.virtualMachineManager();
		List attachingConnectors = vmm.attachingConnectors();
		Iterator it = attachingConnectors.iterator();
		while (it.hasNext()) {
			this.attachingConnector = (AttachingConnector) it.next();
			if (!attachingConnector.transport().name().equals("dt_socket")) {
				continue;
			}

			Map args = attachingConnector.defaultArguments();
			Connector.StringArgument hostnameArg = (Connector.StringArgument) args
					.get("hostname");
			hostnameArg.setValue(hostname);

			Connector.IntegerArgument portArg = (Connector.IntegerArgument) args
					.get("port");
			portArg.setValue(port);
			try {
				vm = attachingConnector.attach(args);
			} catch (IOException e) {
			} catch (IllegalConnectorArgumentsException e) {
			}
			if (vm == null) {
				throw new CoreException(new Status(IStatus.ERROR,
						StudioDebugCorePlugin.getUniqueIdentifier(),
						"Failed to attach with the target VM, address:"
								+ hostname + ":" + port));
			} else {
				StudioDebugCorePlugin
						.debug("Connected to the target VM, address : "
								+ hostname + ":" + port + "\n");
			}
			break;
		}
		return vm;
	}*/

    /**
     * @param eventRequest
     * @param type
     * @return
     */
    public boolean getSmartStepTypeRef(EventRequest eventRequest, ReferenceType type) {
        this.smartStepClass = type;
        getEventRequestManager().deleteEventRequest(eventRequest);
        return true;
    }

    /**
     * @param referenceType
     * @return
     * @throws CoreException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean createRTCBreakPoint(ReferenceType referenceType)
			throws CoreException {
		try {
			cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_PROCESS_RECORDED);

			ReferenceType workingMemoryType = referenceType;
			if (workingMemoryType == null) {
				workingMemoryType = DebuggerSupport.findClass(getVM(),
						WorkingMemoryImpl.class.getName());
			}
			String signature = DebuggerSupport.getMethodSignature(
					WorkingMemoryImpl.class, "processRecorded");
			Method processRecordedMethod = DebuggerSupport
					.findMethodBySignature((ClassType) workingMemoryType,
							"processRecorded", signature);
			LinkedList<? extends Comparable> allLines = new LinkedList<Location>(
					processRecordedMethod.allLineLocations());
			Collections.sort((List)allLines,new LocationComparator());
			Location lastLine = (Location) allLines.getLast();
//			BreakpointRequest bpr = getEventRequestManager()
//					.createBreakpointRequest(lastLine);
//			bpr.putProperty(DebuggerConstants.BREAKPOINT_TYPE,
//					DebuggerConstants.BREAKPOINT_PROCESS_RECORDED);
//			bpr.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
//			bpr.enable();
//			synchronized (breakpoints) {
//				breakpoints.add(bpr);
//			}
			Map attributes = new HashMap();
			attributes.put(DebuggerConstants.BREAKPOINT_TYPE,DebuggerConstants.BREAKPOINT_PROCESS_RECORDED);
			IntermediateBreakpoint bp = RuleDebugModel.createIntermediateBreakpoint(ResourcesPlugin.getWorkspace().getRoot(), 
					workingMemoryType.name(), 
					lastLine.lineNumber(),
					-1,
					-1, 
					0, 
					false, 
					attributes,
					RuleBreakpoint.SUSPEND_NONE);
			getDebugTarget().breakpointAdded(bp);
			return true;

		} catch (NoSuchMethodException e) {
			throw new CoreException(new Status(IStatus.ERROR,
					StudioDebugCorePlugin.getUniqueIdentifier(),
					"Failed to create RTC breakpoint", e));
		} catch (AbsentInformationException e) {
			throw new CoreException(new Status(IStatus.ERROR,
					StudioDebugCorePlugin.getUniqueIdentifier(),
					"Failed to create RTC breakpoint", e));
		}
	}
    
    /**
     * @param referenceType
     * @return
     * @throws CoreException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean createInitProjectBreakPoint(ReferenceType referenceType)
			throws CoreException {
		try {
			cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_INIT_PROJECT);

			ReferenceType workingMemoryType = referenceType;
			if (workingMemoryType == null) {
				workingMemoryType = DebuggerSupport.findClass(getVM(),
						RuleServiceProviderImpl.class.getName());
			}
			String signature = DebuggerSupport.getMethodSignature(
					RuleServiceProviderImpl.class, "initProject");
			Method initProjectMethod = DebuggerSupport
					.findMethodBySignature((ClassType) workingMemoryType,
							"initProject", signature);
			LinkedList<? extends Comparable> allLines = new LinkedList<Location>(
					initProjectMethod.allLineLocations());
			Collections.sort((List)allLines,new LocationComparator());
			Location lastLine = (Location) allLines.getLast();
			Map attributes = new HashMap();
			attributes.put(DebuggerConstants.BREAKPOINT_TYPE,DebuggerConstants.BREAKPOINT_INIT_PROJECT);
			IntermediateBreakpoint bp = RuleDebugModel.createIntermediateBreakpoint(ResourcesPlugin.getWorkspace().getRoot(), 
					workingMemoryType.name(), 
					lastLine.lineNumber(),
					-1,
					-1, 
					0, 
					false, 
					attributes);
			getDebugTarget().breakpointAdded(bp);
			return true;

		} catch (NoSuchMethodException e) {
			throw new CoreException(new Status(IStatus.ERROR,
					StudioDebugCorePlugin.getUniqueIdentifier(),
					"Failed to create RTC breakpoint", e));
		} 
		catch (AbsentInformationException e) {
			throw new CoreException(new Status(IStatus.ERROR,
					StudioDebugCorePlugin.getUniqueIdentifier(),
					"Failed to create RTC breakpoint", e));
		}
	}
    
    

    /**
     * @throws CoreException
     */
//    private void createEndOfMethodBreakPoint() throws CoreException {
//        cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_ENDOFMETHOD);
//        try {
//        	RuleDebugStackFrame frame = (RuleDebugStackFrame) getDebugThreadInfo().getTopStackFrame();
////        	Location currentBreakLocation = getDebugThreadInfo().getFrame(0).location();
//            Location currentBreakLocation = frame.getUnderlyingStackFrame().location();
//            Method m = currentBreakLocation.method();
//            LinkedList<Location> locs = new LinkedList<Location>(m.allLineLocations());
//            Collections.sort(locs);
//            Location lastLine = locs.getLast();
//            Map<String,String> propertyMap = new HashMap<String,String>();
//            propertyMap.put(DebuggerConstants.BREAKPOINT_TYPE, DebuggerConstants.BREAKPOINT_ENDOFMETHOD);
//            StudioDebugCorePlugin.debug("Adding breakpoint request [type:"
//					+ DebuggerConstants.BREAKPOINT_ENDOFMETHOD + ":"
//					+ lastLine.declaringType().name() + ":"
//					+ lastLine.lineNumber() + "]");
//            //setBreakPoint(lastLine.declaringType().name(),lastLine.lineNumber(),propertyMap);
//            final String projectName = ((IRuleRunTarget)getDebugTarget()).getProjectName();
//            IResource resource = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName).findMember(frame.getMappedSourcePath());
//            if(resource == null) {
//            	resource = ResourcesPlugin.getWorkspace().getRoot();
//            }
//			IntermediateBreakpoint bp = RuleDebugModel.createIntermediateBreakpoint(resource, 
//					lastLine.declaringType().name(), 
//					lastLine.lineNumber(),
//					-1,
//					-1, 
//					0, 
//					false, 
//					propertyMap);
//			getDebugTarget().breakpointAdded(bp);
//        }  catch (AbsentInformationException e) {
//            e.printStackTrace();
//        }
//    }

    /* (non-Javadoc)
     * @see com.tibco.cep.studio.debug.core.model.DebugSession#addStepOutBreakPoints(java.util.Map)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void createStepOutBreakPoints(RuleDebugThread rdt, Map props) throws CoreException {
        Location stackLocation = null;
        ensureActiveSession();
        try {
        	RuleDebugStackFrame frame = (RuleDebugStackFrame) rdt.getTopStackFrame();
            stackLocation = frame.getUnderlyingStackFrame().location();
            Method currentMethod = stackLocation.method();
            LinkedList<? extends Comparable> allLines = new LinkedList<Location>(currentMethod.allLineLocations());
            Collections.sort((List)allLines,new LocationComparator());
            Location lastLine = (Location) allLines.getLast();
            if(lastLine.lineNumber() == stackLocation.lineNumber()) { // code pointer is on the last line
//                createBreakPointOnAllLocations(props);
            	createStep(rdt, StepRequest.STEP_LINE, StepRequest.STEP_INTO, props,true);
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


//    /**
//     * @param props
//     * @throws CoreException
//     */
//    public void addStepOutBreakPointsOld(Map props) throws CoreException {
//        Location stackLocation = null;
//        Location nextLineLocation = null;
//        boolean breakOnAllLoc = false;
//        ensureActiveSession();
//        SourceMapper smapper = getRuleDebugTarget().getSourceMapper();
//        try {
//        	IStackFrame[] frames = getDebugThreadInfo().getStackFrames();
//            for(int i = 1; i < frames.length; i++) { // start one level up in the stack frame
//            	RuleDebugStackFrame sf = (RuleDebugStackFrame) frames[i];//getDebugThreadInfo().getFrame(i);
//                final Location location = sf.getUnderlyingStackFrame().location();//location();
//                MappedResourcePosition bePos = smapper.getBEPosition(location.declaringType().name(),location.lineNumber());
//                if(bePos != null) {
//                    stackLocation = location;
//                    break;
//                }
//            }
//            // now check if this location is the last line or not
//            if(stackLocation != null) {
////                stepOut(props);
//                int stackLine = stackLocation.lineNumber();
//                ReferenceType rtype = stackLocation.declaringType();
//                MappedResourcePosition nextPos = smapper.getBEPosition(rtype.name(),stackLine + 1);
//                if (nextPos != null) { // is a valid mapped line
//                    nextLineLocation = (Location)(rtype.locationsOfLine(stackLine + 1).get(0));
//                    Map<String,String> propertyMap = new HashMap<String,String>();
//                    propertyMap.put(DebuggerConstants.BREAKPOINT_TYPE, DebuggerConstants.BREAKPOINT_STEPOUT);
//                    if (nextLineLocation != null) {
//                        setBreakPoint(nextLineLocation.declaringType().name(), nextLineLocation.lineNumber(), propertyMap);
//                    } else {
//                        breakOnAllLoc = true;
//                    }
//                }
//
////                List<Location> methodLines = new LinkedList(stackLocation.method().allLineLocations());
////                Collections.sort(methodLines);
////                int size = methodLines.size();
////                for (int x = 0; x < size; x++) {
////                    if (stackLocation.lineNumber() == methodLines.get(x).lineNumber()) {
////                        if (x == (size - 1)) {
////                            isLastLine = true;
////                        }
////                        if ((x + 1) == (size - 1)) {
////                            isNextLastLine = true;
////                        }
////                        if (!isLastLine && !isNextLastLine) {
////                            nextLineLocation = methodLines.get(x+1);
////                        }
////                        break;
////                    }
////                }
////
////                if (isLastLine == true) { // if the current location is the last line check if the next line is also not the last line
////                    breakOnAllLoc = true;
////                } else {
////                    if (isNextLastLine == true) {
////                        breakOnAllLoc = true;
////                    } else {
////                        Map propertyMap = new HashMap();
////                        propertyMap.put(DebuggerConstants.BREAKPOINT_TYPE, DebuggerConstants.BREAKPOINT_STEPOUT);
////                        if (nextLineLocation != null) {
////                            setBreakPoint(nextLineLocation.declaringType().name(), nextLineLocation.lineNumber(), propertyMap);
////                        }
////                    }
////                }
//            } else { // SmartStep location not in stack hence put a break point on all possible locations
//               breakOnAllLoc = true;
//            }
//
//            if(breakOnAllLoc ) {
//                createEndOfMethodBreakPoint();
//            }
//        }
//        catch (AbsentInformationException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * @param propertyMap
     * @throws CoreException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void createBreakPointOnAllLocations(Map propertyMap) throws CoreException {
        SourceMapper sourceMapper = getRuleDebugTarget().getSourceMapper();
        propertyMap.put(DebuggerConstants.BREAKPOINT_TYPE, DebuggerConstants.BREAKPOINT_STEPOUT);
        Set<BreakpointLocation> allBreakpoints = new LinkedHashSet<BreakpointLocation>();
        for (Iterator<ReferenceType> it = getClassFilterSet().iterator(); it.hasNext();) {
            ReferenceType rtype = it.next();
            if (rtype == null) 
            	continue;
            List<SourceMap> sourceMaps = sourceMapper.getJavaMaps(rtype.name());
            for (Iterator<SourceMap> its = sourceMaps.iterator(); its.hasNext();) {
                SourceMap s = its.next();
                if (s == null) 
                	continue;
                Map.Entry<Integer, Integer> mapEntry = s.getFirstLineEntry();
                if(mapEntry != null) {
                	int breakpoint = mapEntry.getValue();
                	String entityName = sourceMapper.getEntityName(rtype.name());
                	allBreakpoints.add(new BreakpointLocation(rtype.name(), entityName,breakpoint));
//                	setBreakPoint(rtype.name(), breakpoint, propertyMap);
                }
            }
        }
        for (BreakpointLocation breakpointLocation : allBreakpoints) {
        	StudioDebugCorePlugin.debug("Adding breakpoint request [type:"
					+ DebuggerConstants.BREAKPOINT_STEPOUT + ":"
					+ breakpointLocation.getType() + ":"
					+ breakpointLocation.getLine() + "]");
			//setBreakPoint(breakpointLocation.getType(), breakpointLocation.getLine(), propertyMap);
            IResource resource = ((AbstractDebugTarget)getDebugTarget()).getEntityResource(breakpointLocation.getEntityName());
            if(resource == null) {
            	resource = ResourcesPlugin.getWorkspace().getRoot();
            }
			IntermediateBreakpoint bp = RuleDebugModel.createIntermediateBreakpoint(resource, 
					breakpointLocation.getType(), 
					breakpointLocation.getLine(),
					-1,
					-1, 
					0, 
					false, 
					propertyMap);
			getDebugTarget().breakpointAdded(bp);
			
		}
        
    }
    
    class BreakpointLocation {
    	String type;
    	int line;
    	String entityName;
    	
		/**
		 * @param type
		 * @param entityName 
		 * @param line
		 */
		public BreakpointLocation(String type, String entityName, int line) {
			super();
			this.type = type;
			this.line = line;
			this.entityName = entityName;
		}
		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}
		/**
		 * @param type the type to set
		 */
		public void setType(String type) {
			this.type = type;
		}
		/**
		 * @return the line
		 */
		public int getLine() {
			return line;
		}
		/**
		 * @param line the line to set
		 */
		public void setLine(int line) {
			this.line = line;
		}
		
		/**
		 * @return the entityName
		 */
		public String getEntityName() {
			return entityName;
		}
		/**
		 * @param entityName the entityName to set
		 */
		public void setEntityName(String entityName) {
			this.entityName = entityName;
		}
		
		
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((entityName == null) ? 0 : entityName.hashCode());
			result = prime * result + line;
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BreakpointLocation other = (BreakpointLocation) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (entityName == null) {
				if (other.entityName != null)
					return false;
			} else if (!entityName.equals(other.entityName))
				return false;
			if (line != other.line)
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			return true;
		}
		@Override
		public String toString() {
			return getType()+":"+getLine() +":"+getEntityName();
		}
		private AbstractSession getOuterType() {
			return DebugSessionImpl.this;
		}
    	
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.studio.debug.core.model.impl.AbstractSession#onClassPrepare(com.sun.jdi.event.ClassPrepareEvent)
     */
    public EventState onClassPrepare(EventSet eventSet,ClassPrepareEvent cpe) {
		EventState eventState = super.onClassPrepare(eventSet,cpe);
		try {
			if (!eventState.isHandled()) {
				if (cpe.referenceType().name().equals(
						SmartStepInto.class.getName())) {
					eventState.setResume(getSmartStepTypeRef(cpe.request(), cpe
							.referenceType()));
					eventState.setHandled(true);
				} else if (cpe.referenceType().name().equals(
						WorkingMemoryImpl.class.getName())) {
					eventState.setResume(createRTCBreakPoint(cpe
							.referenceType()));
					eventState.setHandled(true);
				} else if(cpe.referenceType().name().equals(RuleServiceProviderImpl.class.getName())) {
					eventState.setResume(createInitProjectBreakPoint(cpe
							.referenceType()));	
					eventState.setHandled(true);
				} else if(cpe.referenceType().name().equals(ReteWM.class.getName())) {
					eventState.setResume(createWmStartBreakPoint(cpe
							.referenceType()));	
					eventState.setHandled(true);
				}
//				else if (cpe.referenceType().name().equals(
//						StateTransitionRule.class.getName())) {
//					 createStateTransitionBreakPoints(cpe.request(),cpe.referenceType());
//				} else if (cpe.referenceType().name().equals(
//						PropertyStateMachineState.class.getName())) {
//					 createPropertyStateMachineBreakPoints(cpe.request(),cpe.referenceType());
//				} 
				else {
					eventState.setResume(setBreakPointsForReferenceType(cpe.referenceType(),IRuleBreakpoint.RULEBREAKPOINT_MARKER_TYPE));
					eventState.setHandled(true);
				}
			}
		} catch (CoreException e) {
			logError(e);
		}
		// return true;
		return eventState;
	}

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean createWmStartBreakPoint(ReferenceType referenceType) throws CoreException {
		try {
			cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_WM_START);

			ReferenceType workingMemoryType = referenceType;
			if (workingMemoryType == null) {
				workingMemoryType = DebuggerSupport.findClass(getVM(),
						ReteWM.class.getName());
			}
			String signature = DebuggerSupport.getMethodSignature(
					ReteWM.class, "start",boolean.class);
			Method wmStartMethod = DebuggerSupport
					.findMethodBySignature((ClassType) workingMemoryType,
							"start", signature);
			LinkedList<? extends Comparable> allLines = new LinkedList<Location>(
					wmStartMethod.allLineLocations());
			Collections.sort((List)allLines,new LocationComparator());
			Location lastLine = (Location) allLines.getFirst();
//			BreakpointRequest bpr = getEventRequestManager()
//					.createBreakpointRequest(lastLine);
//			bpr.putProperty(DebuggerConstants.BREAKPOINT_TYPE,
//					DebuggerConstants.BREAKPOINT_WM_START);
//			bpr.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
//			bpr.enable();
//			synchronized (breakpoints) {
//				breakpoints.add(bpr);
//			}
			Map attributes = new HashMap();
			attributes.put(DebuggerConstants.BREAKPOINT_TYPE,DebuggerConstants.BREAKPOINT_WM_START);
			IntermediateBreakpoint bp = RuleDebugModel.createIntermediateBreakpoint(ResourcesPlugin.getWorkspace().getRoot(), 
					workingMemoryType.name(), 
					lastLine.lineNumber(),
					-1,
					-1, 
					0, 
					false, 
					attributes);
			getDebugTarget().breakpointAdded(bp);
			return true;

		} catch (NoSuchMethodException e) {
			throw new CoreException(new Status(IStatus.ERROR,
					StudioDebugCorePlugin.getUniqueIdentifier(),
					"Failed to create ReteWM.start breakpoint", e));
		} 
		catch (AbsentInformationException e) {
			throw new CoreException(new Status(IStatus.ERROR,
					StudioDebugCorePlugin.getUniqueIdentifier(),
					"Failed to create RTC breakpoint", e));
		}
	}

	/**
     * @param eventRequest
     * @param referenceType
     * @throws CoreException
     */
//    private void createStateTransitionBreakPoints(EventRequest eventRequest, ReferenceType referenceType) throws CoreException {
//        getEventRequestManager().deleteEventRequest(eventRequest);
//        createStateTransitionBreakPoints(referenceType);
//    }

//    /**
//     * @param referenceType
//     * @throws CoreException
//     */
//    private void createStateTransitionBreakPoints(ReferenceType referenceType) throws CoreException {
//        try {
//            cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_STATE_TRANSITION_RULE);
//
//            ReferenceType stateTransitionRuleType = referenceType;
//            Class clazz = StateTransitionRule.class;
//            if(stateTransitionRuleType == null) {
//                stateTransitionRuleType = DebuggerSupport.findClass(getVM(), clazz.getName());
//            }
//            String methodName = "execute";
//            String signature = DebuggerSupport.getMethodSignature(clazz,methodName,Object[].class);
//            Method processRecordedMethod = DebuggerSupport.findMethodBySignature((ClassType)stateTransitionRuleType
//                    ,methodName
//                    ,signature);
//            LinkedList<Location> allLines = new LinkedList(processRecordedMethod.allLineLocations());
//            Collections.sort(allLines);
//            Location lastLine = allLines.getLast();
//            if(!processRecordedMethod.declaringType().name().equals(StateTransitionRule.class.getName())) {
//                BreakpointRequest bpr = getEventRequestManager().createBreakpointRequest(lastLine);
//                bpr.putProperty(DebuggerConstants.BREAKPOINT_TYPE,DebuggerConstants.BREAKPOINT_STATE_TRANSITION_RULE);
//                bpr.setSuspendPolicy(EventRequest.SUSPEND_NONE);
//                bpr.enable();
//                synchronized (breakpoints) {
//                    breakpoints.add(bpr);
//                }
//            }
//
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (AbsentInformationException e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * @param eventRequest
     * @param referenceType
     * @throws CoreException
     */
//    private void createPropertyStateMachineBreakPoints(EventRequest eventRequest, ReferenceType referenceType) throws CoreException {
//        getEventRequestManager().deleteEventRequest(eventRequest);
//        createPropertyStateMachineBreakPoints(referenceType);
//    }

//    /**
//     * @param referenceType
//     * @throws CoreException
//     */
//    private void createPropertyStateMachineBreakPoints(ReferenceType referenceType) throws CoreException {
//        try {
//            cleanBreakPointRequestsByType(DebuggerConstants.BREAKPOINT_PROPERTY_STATE_MACHINE);
//            Map<Method,Location> bpLocations = new HashMap<Method,Location>();
//            ReferenceType propertyStateMachineType = referenceType;
//            Class clazz = PropertyStateMachineState.class;
//            if(propertyStateMachineType == null) {
//                propertyStateMachineType = DebuggerSupport.findClass(getVM(),clazz.getName());
//            }
//            //////////////// onEntry
//            String methodName = "onEntry";
//            String signature = DebuggerSupport.getMethodSignature(clazz,methodName,Object[].class);
//            Method processRecordedMethod = DebuggerSupport.findMethodBySignature((ClassType)propertyStateMachineType
//                    ,methodName
//                    ,signature);
//            LinkedList<Location> allLines = new LinkedList(processRecordedMethod.allLineLocations());
//            Collections.sort(allLines);
//            bpLocations.put(processRecordedMethod,allLines.getLast());
//            ////////// onExit
//            methodName = "onExit";
//            signature = DebuggerSupport.getMethodSignature(clazz,methodName,Object[].class);
//            processRecordedMethod = DebuggerSupport.findMethodBySignature((ClassType)propertyStateMachineType
//                    ,methodName
//                    ,signature);
//            allLines = new LinkedList(processRecordedMethod.allLineLocations());
//            Collections.sort(allLines);
//            bpLocations.put(processRecordedMethod,allLines.getLast());
//            ////////// onTimeout
//            methodName = "onTimeout";
//            signature = DebuggerSupport.getMethodSignature(clazz,methodName,Object[].class);
//            processRecordedMethod = DebuggerSupport.findMethodBySignature((ClassType)propertyStateMachineType
//                    ,methodName
//                    ,signature);
//            allLines = new LinkedList(processRecordedMethod.allLineLocations());
//            Collections.sort(allLines);
//            bpLocations.put(processRecordedMethod,allLines.getLast());
//            for(Map.Entry<Method,Location> loc: bpLocations.entrySet()) {
//                Method m = loc.getKey();
//                // make sure you put breakpoints on the generated implementation class and not on the PropertyStatMachinceState class
//                if(!m.declaringType().name().equals(PropertyStateMachineState.class.getName())) {
//                    BreakpointRequest bpr = getEventRequestManager().createBreakpointRequest(loc.getValue());
//                    bpr.putProperty(DebuggerConstants.BREAKPOINT_TYPE,DebuggerConstants.BREAKPOINT_PROPERTY_STATE_MACHINE);
//                    bpr.setSuspendPolicy(EventRequest.SUSPEND_NONE);
//                    bpr.enable();
//                    synchronized (breakpoints) {
//                        breakpoints.add(bpr);
//                    }
//                }
//            }
//
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (AbsentInformationException e) {
//            e.printStackTrace();
//        }
//    }
   
	
    
    
}


