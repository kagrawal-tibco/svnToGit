package com.tibco.cep.studio.debug.core.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchListener;
import org.eclipse.debug.core.model.IDebugModelProvider;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IDisconnect;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.impl.RunSessionImpl;
import com.tibco.cep.studio.debug.input.VmTask;
import com.tibco.cep.studio.debug.smap.SourceMapperImpl;

public class RuleRunTarget extends AbstractDebugTarget implements IDebugTarget,
		IRuleRunTarget, ILaunchListener, IDebugModelProvider,IDisconnect {
	
	
	public RuleRunTarget(ILaunch launch, VirtualMachine vm, String name, IProcess process, String hostName, String port, boolean supportsDisconnect, boolean supportsTerminate, boolean resumeOnStartup) {
		super(null,vm);
		setName(name);
		setLaunch(launch);
		setProcess(process);
		setHostName(hostName);
		setSupportsDisconnect(supportsDisconnect);
		setSupportsTerminate(supportsTerminate);
		setResumeOnStartup(resumeOnStartup);
		setPort(port);
		setDebugTarget(this);
		setSourceMapper(new SourceMapperImpl(this));
		setInputQueue(new LinkedBlockingQueue<VmTask>(100));
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
		DebugPlugin.getDefault().getLaunchManager().addLaunchListener(this);		
	}
	
	
	
	

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.ILaunchListener#launchAdded(org.eclipse.debug.core.ILaunch)
	 */
	@Override
	public void launchAdded(ILaunch launch) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.ILaunchListener#launchChanged(org.eclipse.debug.core.ILaunch)
	 */
	@Override
	public void launchChanged(ILaunch launch) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.ILaunchListener#launchRemoved(org.eclipse.debug.core.ILaunch)
	 */
	@Override
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
	 * @see org.eclipse.debug.core.model.ISuspendResume#canResume()
	 */
	@Override
	public boolean canResume() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ISuspendResume#canSuspend()
	 */
	@Override
	public boolean canSuspend() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ISuspendResume#isSuspended()
	 */
	@Override
	public boolean isSuspended() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ISuspendResume#resume()
	 */
//	@Override
//	public void resume() throws DebugException {
//		abort("Resume should not be called in a Run session", null);
//	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ISuspendResume#suspend()
	 */
//	@Override
//	public void suspend() throws DebugException {
//		abort("Suspend should not be called in a Run session", null);		
//	}



	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IDisconnect#canDisconnect()
	 */
	@Override
	public boolean canDisconnect() {
		return supportsDisconnect() && !isDisconnected();
	}



	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IDisconnect#disconnect()
	 */
	@Override
	public void disconnect() throws DebugException {
	}






	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IDebugTarget#hasThreads()
	 */
	@Override
	public boolean hasThreads() throws DebugException {
		if(getSession() == null || !getSession().isConnected()) {
			return false;
		} else
			return getSession().getThreadsCache().getThreads().length > 0;
	}




	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
	 */
	@Override
	public boolean canTerminate() {
		return supportsTerminate() && isAvailable() ;
	}

	


	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.core.model.IRuleRunTarget#getAgendaItem()
	 */
	@Override
	public ObjectReference getAgendaItem(RuleDebugThread rdt) {
		return null;
	}





	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.core.model.IRuleRunTarget#started()
	 */
	public void started() {
		StudioDebugCorePlugin.debug("Rule Run Target Started");
		fireCreationEvent();
	}
	
	

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	@Override
	public void terminate() throws DebugException {
		try {
			if (!supportsTerminate()) {
				notSupported("VM does not support termination."); 
			}
			if(getSession() != null) {
				synchronized(getSession()){
					if(getSession().isConnected()) {
						getSession().stop();
					}
					if (getSession().getVM() != null) {
						getSession().getVM().exit(0);
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
	 * @see org.eclipse.debug.core.model.IMemoryBlockRetrieval#getMemoryBlock(long, long)
	 */
	@Override
	public IMemoryBlock getMemoryBlock(long startAddress, long length)
			throws DebugException {
		return null;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.IMemoryBlockRetrieval#supportsStorageRetrieval()
	 */
	@Override
	public boolean supportsStorageRetrieval() {
		return false;
	}



	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.core.model.IRuleRunTarget#init()
	 */
	@Override
	public void init() throws DebugException {
    	try {
			super.init();
			
			if (supportsDisconnect()) {
				abort("Remote application cannot be run inside the debugger",
						null);
			} else {
				if (initStudioProject()) {
					setSession(new RunSessionImpl(this, getLaunch(),getVM()));
					getSession().init();
				}
			}
		} catch (CoreException e) {
			abort(e.getMessage(), e);
		}
		
	}



	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.core.model.IRuleRunTarget#start()
	 */
	@Override
	public void start() throws DebugException {
    	Map<String,Integer> props = new HashMap<String,Integer>();
    	getSession().start(props);
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
	

}
