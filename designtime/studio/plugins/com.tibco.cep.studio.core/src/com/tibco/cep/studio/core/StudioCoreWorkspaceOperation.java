package com.tibco.cep.studio.core;

import java.util.ArrayList;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

public abstract class StudioCoreWorkspaceOperation implements IWorkspaceRunnable,IProgressMonitor {
	
	/*
	 * A per thread stack of studio model operations (PerThreadObject of ArrayList).
	 */
	protected static ThreadLocal operationStacks = new ThreadLocal();
	private IProgressMonitor progressMonitor;
	
	protected StudioCoreWorkspaceOperation() {
		
	}
	
	/**
	 * Returns <code>true</code> if this operation performs no resource modifications,
	 * otherwise <code>false</code>. Subclasses must override.
	 */
	public boolean isReadOnly() {
		return false;
	}
	
	/**
	 * Main entry point for Java Model operations. Runs a Java Model Operation as an IWorkspaceRunnable
	 * if not read-only.
	 */
	public void runOperation(IProgressMonitor monitor) throws CoreException {
		if (isReadOnly()) {
			run(monitor);
		} else {
			// Use IWorkspace.run(...) to ensure that a build will be done in autobuild mode.
			// Note that if the tree is locked, this will throw a CoreException, but this is ok
			// as this operation is modifying the tree (not read-only) and a CoreException will be thrown anyway.
			ResourcesPlugin.getWorkspace().run(this, getSchedulingRule(), IWorkspace.AVOID_UPDATE, monitor);
		}
	}

	private ISchedulingRule getSchedulingRule() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}
	
	/*
	 * Returns the stack of operations running in the current thread.
	 * Returns an empty stack if no operations are currently running in this thread. 
	 */
	protected static ArrayList getCurrentOperationStack() {
		ArrayList stack = (ArrayList)operationStacks.get();
		if (stack == null) {
			stack = new ArrayList();
			operationStacks.set(stack);
		}
		return stack;
	}
	
	/*
	 * Pushes the given operation on the stack of operations currently running in this thread.
	 */
	protected void pushOperation(StudioCoreWorkspaceOperation operation) {
		getCurrentOperationStack().add(operation);
	}
	
	/*
	 * Removes the last pushed operation from the stack of running operations.
	 * Returns the poped operation or null if the stack was empty.
	 */
	protected StudioCoreWorkspaceOperation popOperation() {
		ArrayList stack = getCurrentOperationStack();
		int size = stack.size();
		if (size > 0) {
			if (size == 1) { // top level operation 
				operationStacks.set(null); // release reference (see http://bugs.eclipse.org/bugs/show_bug.cgi?id=33927)
			}
			return (StudioCoreWorkspaceOperation)stack.remove(size-1);
		} else {
			return null;
		}
	}

	@Override
	public void run(IProgressMonitor monitor) throws CoreException {
		try {
			this.progressMonitor = monitor;
			executeOperation();
		} finally {
			popOperation();
		}

	}
	
	/**
	 * Performs the operation specific behavior. Subclasses must override.
	 */
	protected abstract void executeOperation() throws CoreException;

	@Override
	public void beginTask(String name, int totalWork) {
		if (progressMonitor != null) {
			progressMonitor.beginTask(name, totalWork);
		}
		
	}

	@Override
	public void done() {
		if (progressMonitor != null) {
			progressMonitor.done();
		}		
	}

	@Override
	public void internalWorked(double work) {
		if (progressMonitor != null) {
			progressMonitor.internalWorked(work);
		}		
	}

	@Override
	public boolean isCanceled() {
		if (progressMonitor != null) {
			return progressMonitor.isCanceled();
		}
		return false;
	}

	@Override
	public void setCanceled(boolean b) {
		if (progressMonitor != null) {
			progressMonitor.setCanceled(b);
		}		
	}

	@Override
	public void setTaskName(String name) {
		if (progressMonitor != null) {
			progressMonitor.setTaskName(name);
		}		
	}

	@Override
	public void subTask(String name) {
		if (progressMonitor != null) {
			progressMonitor.subTask(name);
		}		
	}

	@Override
	public void worked(int work) {
		if (progressMonitor != null) {
			progressMonitor.worked(work);
			checkCanceled();
		}
		
	}

	protected void checkCanceled() {
		if (isCanceled()) {
			throw new OperationCanceledException("Operation cancelled.."); 
		}
		
	}

}
