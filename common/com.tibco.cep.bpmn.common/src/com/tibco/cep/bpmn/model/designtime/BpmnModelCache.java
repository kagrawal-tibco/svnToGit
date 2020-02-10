package com.tibco.cep.bpmn.model.designtime;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.model.IndexCache;

public class BpmnModelCache extends IndexCache<EObject> {

	/**
	 * 
	 */
//	private static final long serialVersionUID = -8546377412931718529L;
	private static BpmnModelCache fInstance;
//	private HashMap<String,List<IBpmnProjectCacheLock>> fIndexJobs = new HashMap<String,List<IBpmnProjectCacheLock>>();
//	private static final boolean DEBUG = false;
//	
//	public static final String ALL_KEY = "#BPMN_ALL";
//	public static final String UPDATE_KEY = "#BPMN_UPDATE";
//	
	public static BpmnModelCache getInstance() {
		if (fInstance == null) {
			synchronized (BpmnModelCache.class) {
				
				fInstance = new BpmnModelCache();
			}
		}
		return fInstance;
	}
//	public static interface IBpmnProjectCacheLock {
//		public EObject waitForCompletion();
//
//		public boolean isComplete();
//	}
//	
//	public EObject getIndex(String projectName) {
//		printDebug("getting index for "+projectName);
//		if (super.containsKey(projectName)) {
//			// do we want to first check whether an update job is scheduled?
//			return super.get(projectName);
//		}
//		EObject index = null;
//		if (fIndexJobs.containsKey(ALL_KEY)) {
//			waitForAllJobCompletion();
//			index = super.get(projectName);
//		}
//		
//		if (index == null && fIndexJobs.containsKey(projectName)) {
//			index = waitForJobCompletion(projectName);
//		}  
//		
//		if(index != null) {
//			return index;
//		}
////		else if (fIndexJobs.containsKey(UPDATE_KEY)) {
////			waitForJobCompletion(UPDATE_KEY);
////		}
//		return super.get(projectName);
//	}
//
//	private EObject waitForAllJobCompletion() {
//		printDebug("waiting for all job completion");
//		return waitForJobCompletion(ALL_KEY);
//	}
//
//	@Override
//	public EObject get(Object key) {
//		return getIndex((String) key);
//	}
//
//	@Override
//	public EObject put(String key, EObject value) {
//		removeIndexJob(key);
//		return super.put(key, value);
//	}
//
//	private EObject waitForJobCompletion(String projectName) {
//		printDebug("waiting for job completion for "+projectName);
//		List<IBpmnProjectCacheLock> locks = null;
//		synchronized(fIndexJobs) {
//			 locks = fIndexJobs.get(projectName);
//		}
//		for(int i = locks.size(); i > 0 ; i--) {
//			IBpmnProjectCacheLock lock = locks.get(i-1);
//			if(lock != null) {// shall not we wait for all job completion of that project
//				return lock.waitForCompletion();
//			}
//		}
//		return null;
//	}
//
//	public void addIndexJob(String projectName,IBpmnProjectCacheLock lock) {
//		printDebug("adding index job for "+projectName);
//		synchronized (fIndexJobs) {
//			List<IBpmnProjectCacheLock> locks = fIndexJobs.get(projectName);
//			if(locks == null) {
//				locks = new ArrayList<IBpmnProjectCacheLock>();
//				fIndexJobs.put(projectName,locks);
//			}
//			locks.add(lock);
//			
//		}
//	}
//	
//	
//	public void removeIndexJob(String projectName) {
//		printDebug("removing index job for "+projectName);
//		synchronized (fIndexJobs) {
//			List<IBpmnProjectCacheLock> locks = fIndexJobs.get(projectName);
//			if(locks != null) {
//				for(int i=locks.size(); i > 0 ; i--){
//					IBpmnProjectCacheLock lock = locks.get(i-1);
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
