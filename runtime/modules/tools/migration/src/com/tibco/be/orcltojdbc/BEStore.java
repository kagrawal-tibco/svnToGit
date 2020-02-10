/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.orcltojdbc;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkTuple;


public abstract class BEStore {

    protected ClassRegistry classRegistry;

    public abstract Map loadClassRegistry()throws Exception;  
    public void setClassRegistry(ClassRegistry classRegistry) {
        this.classRegistry = classRegistry;
    }
	public abstract int countObjects(int typeId, long maxEntityId) throws Exception;
	public abstract Iterator getObjects(int typeId, long maxEntityId) throws Exception;
	public abstract Iterator getWorkItems() throws Exception;
	public abstract void insertEvents(Map<Integer, Map<Long, Event>> entries) throws Exception;
	public abstract void insertConcepts(Map<Integer, Map<Long, Concept>> entries) throws Exception;
	public abstract void saveWorkItem(WorkTuple item) throws Exception;
	
	/**
	 * This implementation returns all the entities defined in the model as possible
	 * migration target.
	 * If a back-end storage based migration status tracking is intended (for recovery 
	 * of the migration process in case of failure); then provide an implementation as
	 * done in BEJdbcStore.
	 */
	public Map getMigrationProgress() throws Exception {
		Map migrationProgress = new TreeMap();
		for (Iterator itr = classRegistry.clzNmToTypeId.keySet().iterator(); itr.hasNext(); ) {
			String className = (String)itr.next();
			MigrationProgress mp = new MigrationProgress();
			mp.className = className;
			mp.typeId = classRegistry.getTypeId(className);
			mp.maxEntityId = Long.MIN_VALUE;
			mp.totalEntityCnt = 0;
			mp.done = 0;
			mp.isPersisted = false;
			migrationProgress.put(mp.className, mp);
		}
		return migrationProgress;
	}
	public void markTypeAsDone(int typeId) throws Exception {
		// Do nothing;
	}
	public abstract String getConnectionPoolState();
}
