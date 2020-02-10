/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.agent.mm;

/**
 * 
 * @author ggrigore
 *
 */
public interface InferenceAgentReteNetworkMBean {

	public boolean getConcurrent();
	public long getTotalNumberRulesFired();
	
	public void saveReteNetworkToXML();
	public void saveReteNetworkToXML(String filename);
	
	public void saveReteNetworkToString();
	public void saveReteNetworkToString(String filename);
}
