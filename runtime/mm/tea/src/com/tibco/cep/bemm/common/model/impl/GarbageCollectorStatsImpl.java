package com.tibco.cep.bemm.common.model.impl;

import com.tibco.cep.bemm.common.model.GarbageCollectorStats;

public class GarbageCollectorStatsImpl implements GarbageCollectorStats {

	private String poolName = null;
	private long poolCollectionCount;
	private long rawPoolCollectionTime;
	private String poolCollectionTime;
	private long rawUpTime;
	private String upTime;
	
	public GarbageCollectorStatsImpl(String poolName, long poolCollectionCount, long rawPoolCollectionTime, long rawUpTime) {
		this.poolName = poolName;
		this.poolCollectionCount = poolCollectionCount;
		this.rawPoolCollectionTime = rawPoolCollectionTime;
		this.rawUpTime = rawUpTime;
	}
	
	@Override
	public String getPoolName() {
		return this.poolName;
	}

	@Override
	public long getPoolCollectionCount() {
		return poolCollectionCount;
	}

	@Override
	public long getRawPoolCollectionTime() {
		return rawPoolCollectionTime;
	}

	@Override
	public String getPoolCollectionTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getRawUpTime() {
		return rawUpTime;
	}

	@Override
	public String getUpTime() {
		// TODO Auto-generated method stub
		return null;
	}

}
