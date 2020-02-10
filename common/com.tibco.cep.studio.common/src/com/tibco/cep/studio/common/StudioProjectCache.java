package com.tibco.cep.studio.common;

import com.tibco.cep.designtime.model.IndexCache;
import com.tibco.cep.studio.core.index.model.DesignerProject;

public class StudioProjectCache extends IndexCache<DesignerProject> {

//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -8546377412931718529L;
	private static StudioProjectCache fInstance;
//	private HashMap<String,List<ProjectCacheLock>> fIndexJobs = new HashMap<String,List<ProjectCacheLock>>();
//	private static final boolean DEBUG = false;
//	
//	public static final String ALL_KEY = "#ALL";
//	public static final String UPDATE_KEY = "#UPDATE";
//	
//	public static interface ProjectCacheLock {
//		public EObject waitForCompletion();
//
//		public boolean isComplete();
//	}
	
	public static StudioProjectCache getInstance() {
		if (fInstance == null) {
			fInstance = new StudioProjectCache();
		}
		return fInstance;
	}
	
//	
//	public DesignerProject getIndex(String projectName) {
//		printDebug("getting index for "+projectName);
//		if (super.containsKey(projectName)) {
//			// do we want to first check whether an update job is scheduled?
//			return super.get(projectName);
//		}
//		DesignerProject index = null;
//		if (fIndexJobs.containsKey(projectName)) {
//			index = (DesignerProject) waitForJobCompletion(projectName);
//		} else if (fIndexJobs.containsKey(ALL_KEY)) {
//			index = (DesignerProject) waitForAllJobCompletion();
//		} 
//		if(index != null) {
//			return index;
//		}
//		return super.get(projectName);
//	}
//
//	private EObject waitForAllJobCompletion() {
//		printDebug("waiting for all job completion");
//		return waitForJobCompletion(ALL_KEY);
//	}
//
//	@Override
//	public DesignerProject get(Object key) {
//		return getIndex((String) key);
//	}
//	
//	public DesignerProject putIndex(String key, DesignerProject value) {
//		return put(key, value);
//	}
//
//	@Override
//	public DesignerProject put(String key, DesignerProject value) {
//		removeIndexJob(key);
//		return super.put(key, value);
//	}
//
//	private EObject waitForJobCompletion(String projectName) {
//		printDebug("waiting for job completion for "+projectName);
//		List<ProjectCacheLock> locks = null;
//		synchronized(fIndexJobs) {
//			 locks = fIndexJobs.get(projectName);
//		}
//		for(int i = locks.size(); i > 0 ; i--) {
//			ProjectCacheLock lock = locks.get(i-1);
//			if(lock != null) {
//				return lock.waitForCompletion();
//			}
//		}
//		return null;
//	}
//
//	public void addIndexJob(String projectName,ProjectCacheLock lock) {
//		printDebug("adding index job for "+projectName);
//		synchronized (fIndexJobs) {
//			List<ProjectCacheLock> locks = fIndexJobs.get(projectName);
//			if(locks == null) {
//				locks = new ArrayList<ProjectCacheLock>();
//				fIndexJobs.put(projectName,locks);
//			}
//			locks.add(lock);
//			
//		}
//	}
//	
//	public void removeIndexJob(String projectName) {
//		printDebug("removing index job for "+projectName);
//		synchronized (fIndexJobs) {
//			List<ProjectCacheLock> locks = fIndexJobs.get(projectName);
//			if(locks != null) {
//				for(int i=locks.size(); i > 0 ; i--){
//					ProjectCacheLock lock = locks.get(i-1);
//					if(lock != null && lock.isComplete()) {
//						locks.remove(lock);
//					}
//				}
//				if(locks.isEmpty()) {
//					fIndexJobs.remove(projectName);
//				}
//			} 
//		}
//	}
//	
//
//	public boolean isIndexCached(String projectName) {
//		return containsKey(projectName);
//	}
//
//	public void allJobFinished() {
//		removeIndexJob(ALL_KEY);
//	}
//
//	private void printDebug(String message) {
//		if (DEBUG) {
//			System.out.println(message);
//		}
//	}
}
