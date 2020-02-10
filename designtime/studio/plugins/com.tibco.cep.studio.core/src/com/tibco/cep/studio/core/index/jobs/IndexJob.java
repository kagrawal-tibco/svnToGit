package com.tibco.cep.studio.core.index.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.util.GUIDGenerator;

public abstract class IndexJob<X extends EObject> extends Job{
	
	ILock lock;
	QualifiedName key;

	public IndexJob(String name) {
		super(name);
		key = new QualifiedName(GUIDGenerator.getGUID(),name);
		setLock(Job.getJobManager().newLock());
	}
	
	
	/**
	 * @return the lock
	 */
	public ILock getLock() {
		return lock;
	}


	/**
	 * @param lock the lock to set
	 */
	public void setLock(ILock lock) {
		this.lock = lock;
	}


	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			lock.acquire();
			return runJob(monitor);
		} finally {
			while(lock.getDepth() > 0) {
				lock.release();
			}
		}
	}
	
	public QualifiedName getKey() {
		return key;
	}
	
	public abstract IStatus runJob(IProgressMonitor monitor);
	
	public X  waitForCompletion() {
		try {
			lock.acquire();	
			X index  = (X) getProperty(getKey());
			return index;
		} finally {
			lock.release();
		}
	}
	


}
