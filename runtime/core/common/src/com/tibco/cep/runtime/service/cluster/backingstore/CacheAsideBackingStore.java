package com.tibco.cep.runtime.service.cluster.backingstore;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.runtime.service.cluster.scheduler.WorkTuple;
import com.tibco.cep.runtime.service.cluster.scheduler.impl.DefaultScheduler.WorkTupleDBId;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransactionRO;

/**
 * @author bala
 * 
 * Interface for backing stores. Eg: JdbcBackingStore, AS30Store
 * 
 * 
 */
public interface CacheAsideBackingStore extends GenericBackingStore {


	/**
	 * Save RtcTransaction
	 * 
	 * @param txn
	 * @throws Exception
	 */
	void saveTransaction(RtcTransactionRO txn) throws Exception;

	
	/**
	 * ClassRegistry crud
	 * @param classRegistry
	 * @throws Exception
	 */

	void saveClassRegistry(Map<String, Integer> classRegistry) throws Exception;

	void saveClassRegistration(String className, int typeId) throws Exception;

	Map getClassRegistry() throws Exception;
	
	
	/**
	 * WorkItems (Scheduler) crud
	 * @param item
	 * @throws Exception
	 */

	void saveWorkItem(WorkTuple item) throws Exception;

	void updateWorkItem(WorkTuple item) throws Exception;

	WorkTuple getWorkItem(String schedulerId, String workKey);
	List<WorkTuple> getWorkItems(String workQueue, long time, int status) throws Exception;

	void removeWorkItem(String key) throws Exception;

	void removeWorkItems(Set<WorkTupleDBId> keysAndScheduledTimes) throws Exception;
	
	

	
	/**
	 * Sequences crud
	 * @param sequenceName
	 * @param min
	 * @param max
	 * @param start
	 * @param batchSize
	 * @throws Exception
	 */

	void createSequence(String sequenceName, long min, long max, long start, int batchSize) throws Exception;

	void removeSequence(String sequenceName) throws Exception;

	long nextSequence(String sequenceName) throws Exception;

	int getBlockSize(String sequenceName);

}
