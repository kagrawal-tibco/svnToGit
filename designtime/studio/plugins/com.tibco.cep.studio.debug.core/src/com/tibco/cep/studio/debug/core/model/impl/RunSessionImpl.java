package com.tibco.cep.studio.debug.core.model.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IProcess;

import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.ExceptionRequest;
import com.sun.jdi.request.ThreadDeathRequest;
import com.sun.jdi.request.ThreadStartRequest;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.RuleRunTarget;
import com.tibco.cep.studio.debug.core.model.RunSession;

public class RunSessionImpl extends AbstractSession implements RunSession {

	private boolean suspended;

	public RunSessionImpl(RuleRunTarget ruleRunTarget, ILaunch launch, VirtualMachine virtualMachine) {
		super(ruleRunTarget, launch,virtualMachine);
		setLaunch(launch);
	}
	
	public RuleRunTarget getRuleRunTarget() {
		return (RuleRunTarget) getDebugTarget();
	}

	@SuppressWarnings("rawtypes")
	public void init() {
		try {
			super.init();
			start(new HashMap());
		} catch (DebugException e) {
			StudioDebugCorePlugin.log(e);
		}
	}
	
	/**
	 * @return the suspended
	 */
	public boolean isSuspended() {
		return suspended;
	}
	
	@Override
	public boolean isStepping() {
		return false;
	}

	/**
	 * @param suspended the suspended to set
	 */
	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

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
			setSuspended(true);
			getRuleRunTarget().fireSuspendEvent(DebugEvent.CLIENT_REQUEST);
		} catch (RuntimeException e) {
			getRuleRunTarget().fireResumeEvent(DebugEvent.CLIENT_REQUEST);
			targetRequestFailed(MessageFormat.format("{0} occurred suspending VM.", new Object[] {e.toString()}), e); 
		}
	}

	public void resume() throws DebugException {
		resumeThreads();
		StudioDebugCorePlugin.debug("Session.resume() ..");
		VirtualMachine vm = getVM();
		if (vm != null) {
			vm.resume();
		}	
		getRuleRunTarget().fireResumeEvent(DebugEvent.CLIENT_REQUEST);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.debug.core.model.RunSession#start(java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	public void start(Map props) throws DebugException {
		super.start(props);
		
		EventRequestManager em = getEventRequestManager();
		
		createDebuggerServiceClassPrepareRequest(em);
		createDebugTaskFactoryClassPrepareRequest(em);
		
		ThreadStartRequest threadStartRequest = em
		.createThreadStartRequest();
		
		threadStartRequest.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
		threadStartRequest.enable();
		
		ThreadDeathRequest threadDeathRequest = em
		.createThreadDeathRequest();
		
		threadDeathRequest.setSuspendPolicy(EventRequest.SUSPEND_NONE);
		threadDeathRequest.enable();
		
		ExceptionRequest exceptionRequest = em.createExceptionRequest(null,
				false, true);
		
		exceptionRequest.setSuspendPolicy(EventRequest.SUSPEND_ALL);
		exceptionRequest.enable();
		
		getVM().resume();
		
	}

	public void stop() throws DebugException {
		if(!isConnected()) {
            return;
        }
        try {
        	if(eventDispatcher != null && eventDispatcher.isRunning()) {
        		eventDispatcher.stop();
        	}
            if(getVM() != null) {
                getVM().exit(1);
            }

            IProcess process= getDebugTarget().getProcess();
			if (process != null) {
				process.terminate();
			}
//			getThreadsCache().removeAllThreads();
//            getThreadsCache().cleanup();
        } catch (VMDisconnectedException e) {
            abort("Failed to stop run session.",e);
        } catch (RuntimeException e) {
        	targetRequestFailed("Failed to stop run session", e);
        }
        finally {
            vm = null;
            eventDispatcher = null;
        }
		stopDispatcher();
	}

	@Override
	public void addInputVmTask(Object key) throws DebugException {
		try {
			super.addInputVmTask(key);
		} catch (CoreException e) {
			abort("Failed to execute input task", e);
		}
	}


}
