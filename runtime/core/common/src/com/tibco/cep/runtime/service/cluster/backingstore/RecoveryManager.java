/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.backingstore;

import java.util.Iterator;

import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.runtime.service.cluster.Cluster;

public interface RecoveryManager {

	public void init(Cluster cluster) throws Exception;
    
	public void registerTypeForPreload(String typeName);
	
	public void registerTypeForRecovery(String typeName);
	
	public void start() throws Exception;
	
	public void shutdown();
	
	public String[] getRecoveryStatus();
	
	public String[] getPreloadStatus();
	
    public boolean isLoadAndRecoveryComplete();
	
	public boolean resetRecoveryTable(UID memberLeftId);
	
	public boolean resetLoadTable(UID memberLeftId);

	public boolean isLoaded(int typeId) throws Exception;
	
	public Iterator getObjects(int typeId) throws Exception;
}
