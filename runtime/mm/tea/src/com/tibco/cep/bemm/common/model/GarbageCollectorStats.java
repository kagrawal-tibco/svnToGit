package com.tibco.cep.bemm.common.model;

public interface GarbageCollectorStats {

	String getPoolName();
	
	long getPoolCollectionCount();
	
	long getRawPoolCollectionTime();
	
	String getPoolCollectionTime();
	
	long getRawUpTime();
	
	String getUpTime();
}
