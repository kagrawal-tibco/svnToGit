package com.tibco.cep.studio.debug.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IThread;

import com.sun.jdi.ObjectCollectedException;
import com.sun.jdi.ThreadGroupReference;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.ThreadDeathEvent;
import com.sun.jdi.event.ThreadStartEvent;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.ThreadDeathRequest;
import com.sun.jdi.request.ThreadStartRequest;
import com.tibco.cep.studio.debug.core.model.impl.AbstractSession;
import com.tibco.cep.studio.debug.core.model.impl.RuleDebugThreadGroup;

/*
@author ssailapp
@date Jul 21, 2009
*/

public class DebugThreadsCache extends RuleDebugElement {


    private VirtualMachine vm;
    
    private AbstractSession session;
    /**
	 * Threads contained in this debug target. When a thread
	 * starts it is added to the list. When a thread ends it
	 * is removed from the list.
	 */
	private ArrayList<IRuleDebugThread> fThreads;
	
	/**
	 * List of thread groups in this target.
	 */
	private ArrayList<IRuleDebugThreadGroup> fGroups;
	
	private boolean fSupportsDisableGC;

    public DebugThreadsCache(IDebugTarget target, AbstractSession ds) {
    	super(target);
        this.session = ds;
        setThreadList(new ArrayList<IRuleDebugThread>(5));
        fGroups = new ArrayList<IRuleDebugThreadGroup>(5);
        VirtualMachine vm = session.getVM();
        if (vm != null) {
            setVM(vm);
        }
    }
    
    

    /**
	 * @return the vm
	 */
	public VirtualMachine getVM() {
		return vm;
	}



	/**
	 * @param vm the vm to set
	 */
	public void setVm(VirtualMachine vm) {
		this.vm = vm;
	}



	public synchronized void setVM(VirtualMachine vm) {
        if (this.vm == vm) {
            return;
        }
        this.vm = vm;
    }
    
    
    
    public void init() {
		VirtualMachine vm = getVM();
		if (vm != null) {
			try {
				String name = vm.name();
				fSupportsDisableGC = !name.equals("Classic VM");
			} catch (RuntimeException e) {
				internalError(e);
			}
		}
	}
    
    @SuppressWarnings("rawtypes")
	public void start(Map props) {
    	List<ThreadReference> threads= null;
    	createThreadRequests(props);
		VirtualMachine vm = getVM();
		if (vm != null) {
			try {
				String name = vm.name();
				fSupportsDisableGC = !name.equals("Classic VM");
			} catch (RuntimeException e) {
				internalError(e);
			}
			try {
				threads= vm.allThreads();
			} catch (RuntimeException e) {
				internalError(e);
			}
			if (threads != null) {
				Iterator<ThreadReference> initialThreads= threads.iterator();
				while (initialThreads.hasNext()) {
					ThreadReference thread = initialThreads.next();
					if(findThread(thread) == null) {
						createThread(thread);
					}
				}
			}			
		}		
	}



    @SuppressWarnings("rawtypes")
	private void createThreadRequests(Map props) {
		ThreadStartRequest threadStartRequest = session.getEventRequestManager().createThreadStartRequest();
		threadStartRequest.setSuspendPolicy(EventRequest.SUSPEND_NONE);
		for (Iterator<?> it = props.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			threadStartRequest.putProperty(entry.getKey(), entry.getValue());
		}
		threadStartRequest.putProperty(
				DebuggerConstants.THREAD_START_REQUEST_TYPE,
				DebuggerConstants.THREAD_START_APP);
		threadStartRequest.enable();
		ThreadDeathRequest threadDeathRequest = session.getEventRequestManager().createThreadDeathRequest();
		threadDeathRequest.setSuspendPolicy(EventRequest.SUSPEND_NONE);
		threadDeathRequest.enable();

	}



	public synchronized boolean onThreadDeath(ThreadDeathEvent tde){
    	ThreadReference ref= ((ThreadDeathEvent)tde).thread();
		RuleDebugThread thread= findThread(ref);
		if (thread != null) {
			synchronized (fThreads) {
				fThreads.remove(thread);
			}
			thread.terminated();
		}
		return true;

    }

    public synchronized boolean onThreadStart(ThreadStartEvent tse) {
    	ThreadReference thread= ((ThreadStartEvent)tse).thread();
		try {
			if (thread.isCollected()) {
				return false;
			}
		} catch (VMDisconnectedException exception) {
			return false;
		} catch (ObjectCollectedException e) {
			return false;
		} 
		RuleDebugThread tinfo= findThread(thread);
		if (tinfo == null) {
			tinfo = createThread(thread);
			if (tinfo == null) {
				return false;
			}
		} else {
			tinfo.disposeStackFrames();
			tinfo.fireChangeEvent(DebugEvent.CONTENT);
		}
		return !tinfo.isSuspended();
    	
    }
    



	public synchronized void cleanup() {
		try {
			fThreads.clear();
			fGroups.clear();
		} finally {
			fThreads = new ArrayList<IRuleDebugThread>(5);
			fGroups = new ArrayList<IRuleDebugThreadGroup>(5);			
		}
	
		
	}
	
	
	/**
	 * Returns an iterator over the collection of threads. The
	 * returned iterator is made on a copy of the thread list
	 * so that it is thread safe. This method should always be
	 * used instead of getThreadList().iterator()
	 * @return an iterator over the collection of threads
	 */
	@SuppressWarnings("unchecked")
	public Iterator<IRuleDebugThread> getThreadIterator() {
		List<IRuleDebugThread> threadList;
		synchronized (fThreads) {
			threadList= (List<IRuleDebugThread>) fThreads.clone();
		}
		return threadList.iterator();
	}
	
	/**
	 * Sets the list of threads contained in this debug target.
	 * Set to an empty collection on creation. Threads are
	 * added and removed as they start and end. On termination
	 * this collection is set to the immutable singleton empty list.
	 * 
	 * @param threads empty list
	 */
	public void setThreadList(ArrayList<IRuleDebugThread> threads) {
		fThreads = threads;
	}
	
	/**
	 * Creates, adds and returns a thread for the given
	 * underlying thread reference. A creation event
	 * is fired for the thread.
	 * Returns <code>null</code> if during the creation of the thread this target
	 * is set to the disconnected state.
	 * 
	 * @param thread underlying thread
	 * @return model thread
	 */
	public RuleDebugThread createThread(ThreadReference thread) {
		RuleDebugThread jdiThread= newThread(thread);
		if (jdiThread == null) {
			return null;
		}
		if (getDebugTarget().isDisconnected()) {
			return null;
		}
		synchronized (fThreads) {
			fThreads.add(jdiThread);
		}
		jdiThread.fireCreationEvent();
		return jdiThread;
	}
	
	/**
	 * Factory method for creating new threads. Creates and returns a new thread
	 * object for the underlying thread reference, or <code>null</code> if none
	 * 
	 * @param reference thread reference
	 * @return JDI model thread
	 */
	public RuleDebugThread newThread(ThreadReference reference) {
		try {
			return new RuleDebugThread(getDebugTarget(), reference);
		} catch (ObjectCollectedException exception) {
			// ObjectCollectionException can be thrown if the thread has already
			// completed (exited) in the VM.
		}
		return null;
	}
	
	/**
	 * @see IDebugTarget#getThreads()
	 */
	public IThread[] getThreads() {
		synchronized (fThreads) {
			return fThreads.toArray(new IThread[0]);
		}
	}
	
	/**
	 * Removes all threads from this target's collection
	 * of threads, firing a terminate event for each.
	 */
	public void removeAllThreads() {
		Iterator<IRuleDebugThread> itr= getThreadIterator();
		while (itr.hasNext()) {
			RuleDebugThread child= (RuleDebugThread) itr.next();
			child.terminated();
		}
		synchronized (fThreads) {
		    fThreads.clear();
		}
	}
	
	
	/**
	 * Finds and returns the JDI thread for the associated thread reference, 
	 * or <code>null</code> if not found.
	 * 
	 * @param the underlying thread reference
	 * @return the associated model thread
	 */
	public RuleDebugThread findThread(ThreadReference tr) {
		Iterator<IRuleDebugThread> iter= getThreadIterator();
		while (iter.hasNext()) {
			RuleDebugThread thread = (RuleDebugThread) iter.next();
			if (thread.getUnderlyingThread().equals(tr))
				return thread;
		}
		return null;
	}
	
	
	/**
	 * @see IDebugTarget#hasThreads()
	 */
	public boolean hasThreads() {
		return fThreads.size() > 0;
	}


	 /**
     * Adds the given thread group to the list of known thread groups.  Also adds any parent thread groups
     * that have not already been added to the list.
     * 
     * @param group thread group to add
     */
    void addThreadGroup(ThreadGroupReference group) {
    	ThreadGroupReference currentGroup = group;
    	while(currentGroup != null){
	    	synchronized (fGroups) {
	    		if (findThreadGroup(currentGroup) == null) {
	    			RuleDebugThreadGroup modelGroup = new RuleDebugThreadGroup((IRuleRunTarget) getDebugTarget(), currentGroup);
	        		fGroups.add(modelGroup);
	        		currentGroup = currentGroup.parent();
	        		// TODO: create event?
	    		} else {
	    			currentGroup = null;
	    		}
			}
    	}
    }
    
    RuleDebugThreadGroup findThreadGroup(ThreadGroupReference group) {
    	synchronized (fGroups) {
    		Iterator<IRuleDebugThreadGroup> groups = fGroups.iterator();
    		while (groups.hasNext()) {
    			RuleDebugThreadGroup modelGroup = (RuleDebugThreadGroup) groups.next();
    			if (modelGroup.getUnderlyingThreadGroup().equals(group)) {
    				return modelGroup;
    			}
    		}
    	}
    	return null;
    }

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IJavaDebugTarget#getThreadGroups()
	 */
	public IRuleDebugThreadGroup[] getRootThreadGroups() throws DebugException {
		try {
			VirtualMachine vm = getVM();
			if (vm == null) {
				return new IRuleDebugThreadGroup[0];
			}
			List<ThreadGroupReference> groups = vm.topLevelThreadGroups();
			List<RuleDebugThreadGroup> modelGroups = new ArrayList<RuleDebugThreadGroup>(groups.size());
			Iterator<ThreadGroupReference> iterator = groups.iterator();
			while (iterator.hasNext()) {
				ThreadGroupReference ref = iterator.next();
				RuleDebugThreadGroup group = findThreadGroup(ref);
				if (group != null) {
					modelGroups.add(group);
				}
			}
			return modelGroups.toArray(new IRuleDebugThreadGroup[modelGroups.size()]);
		} catch (RuntimeException e) {
			targetRequestFailed("Error retrieving top level thread groups", e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IJavaDebugTarget#getAllThreadGroups()
	 */
	public IRuleDebugThreadGroup[] getAllThreadGroups() throws DebugException {
		synchronized (fGroups) {
			return fGroups.toArray(new IRuleDebugThreadGroup[fGroups.size()]);
		}
	}



	/**
	 * @return the fSupportsDisableGC
	 */
	public boolean isFSupportsDisableGC() {
		return fSupportsDisableGC;
	}



	/**
	 * @param supportsDisableGC the fSupportsDisableGC to set
	 */
	public void setFSupportsDisableGC(boolean supportsDisableGC) {
		fSupportsDisableGC = supportsDisableGC;
	}
	
	


	
}
