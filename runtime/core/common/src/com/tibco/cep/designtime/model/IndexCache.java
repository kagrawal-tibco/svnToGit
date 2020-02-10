package com.tibco.cep.designtime.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

public class IndexCache<X extends EObject> {	
	
	private static final boolean DEBUG = false;
	public static final String ALL_KEY = "#ALL";
	public static final String UPDATE_KEY = "#UPDATE";
	
	protected Map<String,X> projectIndexCache = new HashMap<String,X>();
	protected HashMap<String,List<ProjectCacheLock<X>>> fIndexJobs = new HashMap<String,List<ProjectCacheLock<X>>>();
	
	public static interface ProjectCacheLock<X extends EObject> {
		
		public X waitForCompletion();

		public boolean isComplete();
	}

	
	public X getIndex(String projectName) {
		printDebug("getting index for "+projectName);
		if (projectIndexCache.containsKey(projectName)) {
			// do we want to first check whether an update job is scheduled?
			return projectIndexCache.get(projectName);
		}
		X index = null;
		if (fIndexJobs.containsKey(projectName)) {
			index = waitForJobCompletion(projectName);
		} else if (fIndexJobs.containsKey(ALL_KEY)) {
			index = waitForAllJobCompletion();
		} 
		if(index != null) {
			return index;
		}
		return projectIndexCache.get(projectName);
	}

	private X waitForAllJobCompletion() {
		printDebug("waiting for all job completion");
		return waitForJobCompletion(ALL_KEY);
	}


	
	public X putIndex(String projectName,X index) {
		removeIndexJob(projectName);
		return projectIndexCache.put(projectName, index);			
	}

	

	private X waitForJobCompletion(String projectName) {
		printDebug("waiting for job completion for "+projectName);
		List<ProjectCacheLock<X>> locks = null;
		synchronized(fIndexJobs) {
			 locks = fIndexJobs.get(projectName);
		}
		for(int i = locks.size(); i > 0 ; i--) {
			ProjectCacheLock<X> lock = locks.get(i-1);
			if(lock != null) {
				return lock.waitForCompletion();
			}
		}
		return null;
	}

	public void addIndexJob(String projectName,ProjectCacheLock<X> lock) {
		printDebug("adding index job for "+projectName);
		synchronized (fIndexJobs) {
			List<ProjectCacheLock<X>> locks = fIndexJobs.get(projectName);
			if(locks == null) {
				locks = new ArrayList<ProjectCacheLock<X>>();
				fIndexJobs.put(projectName,locks);
			}
			locks.add(lock);
			
		}
	}
	
	public void removeIndexJob(String projectName) {
		printDebug("removing index job for "+projectName);
		synchronized (fIndexJobs) {
			List<ProjectCacheLock<X>> locks = fIndexJobs.get(projectName);
			if(locks != null) {
				for(int i=locks.size(); i > 0 ; i--){
					ProjectCacheLock<X> lock = locks.get(i-1);
					if(lock != null && lock.isComplete()) {
						locks.remove(lock);
					}
				}
				if(locks.isEmpty()) {
					fIndexJobs.remove(projectName);
				}
			} 
		}
	}
	

	public boolean isIndexCached(String projectName) {
		return projectIndexCache.containsKey(projectName);
	}

	public void allJobFinished() {
		removeIndexJob(ALL_KEY);
	}

	private void printDebug(String message) {
		if (DEBUG) {
			System.out.println(message);
		}
	}
	
	public Collection<X> values() {
		return projectIndexCache.values();
	}
	
	public boolean containsKey(String key) {
		return projectIndexCache.containsKey(key);
	}
	
	public X remove(String key) {
		return projectIndexCache.remove(key);
	}

}
