package com.tibco.cep.studio.debug.core.model.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.ITerminate;

import com.sun.jdi.ThreadGroupReference;
import com.sun.jdi.ThreadReference;
import com.tibco.cep.studio.debug.core.model.IRuleDebugThread;
import com.tibco.cep.studio.debug.core.model.IRuleDebugThreadGroup;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.core.model.RuleDebugElement;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;
@SuppressWarnings({"rawtypes","unchecked"})
public class RuleDebugThreadGroup extends RuleDebugElement implements IRuleDebugThreadGroup, ITerminate {
	
	private ThreadGroupReference fGroup = null;
	private String fName = null;

	/**
	 * Constructs a new thread group in the given target based on the underlying
	 * thread group reference.
	 *  
	 * @param target debug target
	 * @param group thread group reference
	 */
	public RuleDebugThreadGroup(IRuleRunTarget target, ThreadGroupReference group) {
		super(target);
		fGroup = group;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IRuleDebugThreadGroup#getThreads()
	 */
	public synchronized IRuleDebugThread[] getThreads() throws DebugException {
		try {
			List threads = fGroup.threads();
			List modelThreads = new ArrayList(threads.size());
			Iterator iterator = threads.iterator();
			while (iterator.hasNext()) {
				ThreadReference ref = (ThreadReference) iterator.next();
				RuleDebugThread thread = ((IRuleRunTarget)getDebugTarget()).findThread(ref);
				if (thread != null) {
					modelThreads.add(thread);
				}
			}
			return (IRuleDebugThread[]) modelThreads.toArray(new IRuleDebugThread[modelThreads.size()]);
		} catch (RuntimeException e) {
			targetRequestFailed("Error retrieving threads in thread group", e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IRuleDebugThreadGroup#getThreadGroup()
	 */
	public IRuleDebugThreadGroup getThreadGroup() throws DebugException {
		try {
			ThreadGroupReference reference = fGroup.parent();
			if (reference != null) {
				return ((IRuleRunTarget)getDebugTarget()).findThreadGroup(reference);
			}
		} catch (RuntimeException e) {
			targetRequestFailed("Error retrieving parent of thread group", e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IRuleDebugThreadGroup#getThreadGroups()
	 */
	public IRuleDebugThreadGroup[] getThreadGroups() throws DebugException {
		try {
			List groups = fGroup.threadGroups();
			List modelGroups = new ArrayList(groups.size());
			Iterator iterator = groups.iterator();
			while (iterator.hasNext()) {
				ThreadGroupReference ref = (ThreadGroupReference) iterator.next();
				IRuleDebugThreadGroup group = ((IRuleRunTarget)getDebugTarget()).findThreadGroup(ref);
				if (group != null) {
					modelGroups.add(group);
				}
			}
			return (IRuleDebugThreadGroup[]) modelGroups.toArray(new IRuleDebugThreadGroup[modelGroups.size()]);
		} catch (RuntimeException e) {
			targetRequestFailed("Error retrieving groups in thread group", e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IRuleDebugThreadGroup#getName()
	 */
	public synchronized String getName() throws DebugException {
		if (fName == null) {
			try {
				fName = fGroup.name();
			} catch (RuntimeException e) {
				targetRequestFailed("Error retrieving thread group name", e);
			}
		}
		return fName;
	}
	
	public ThreadGroupReference getUnderlyingThreadGroup() {
		return fGroup;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IRuleDebugThreadGroup#hasThreadGroups()
	 */
	public boolean hasThreadGroups() throws DebugException {
		try {
			List groups = fGroup.threadGroups();
			return groups.size() > 0;
		} catch (RuntimeException e) {
			targetRequestFailed("Error retrieving groups in thread group", e);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.debug.core.IRuleDebugThreadGroup#hasThreads()
	 */
	public boolean hasThreads() throws DebugException {
		try {
			List threads = fGroup.threads();
			return threads.size() > 0;
		} catch (RuntimeException e) {
			targetRequestFailed("Error retrieving threads in thread group", e);
		}
		return false;
	}

	/**
	 * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
	 */
	public boolean canTerminate() {
		//the group can terminate if the target can terminate
		return getDebugTarget().canTerminate();
	}

	/**
	 * @see org.eclipse.debug.core.model.ITerminate#isTerminated()
	 */
	public boolean isTerminated() {
		return getDebugTarget().isTerminated();
	}

	/**
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	public void terminate() throws DebugException {
		getDebugTarget().terminate();
	}

}