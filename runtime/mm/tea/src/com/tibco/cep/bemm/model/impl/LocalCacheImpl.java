/**
 * 
 */
package com.tibco.cep.bemm.model.impl;

import com.tibco.cep.bemm.model.LocalCache;

/**
 * @author dijadhav
 *
 */
public class LocalCacheImpl implements LocalCache {
	private long maxSize;
	private long evictionTime;

	/* (non-Javadoc)
	 * @see com.tibco.cep.bemm.model.impl.LocalCache#getMaxSize()
	 */
	@Override
	public long getMaxSize() {
		return maxSize;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.bemm.model.impl.LocalCache#setMaxSize(long)
	 */
	@Override
	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.bemm.model.impl.LocalCache#getEvictionTime()
	 */
	@Override
	public long getEvictionTime() {
		return evictionTime;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.bemm.model.impl.LocalCache#setEvictionTime(long)
	 */
	@Override
	public void setEvictionTime(long evictionTime) {
		this.evictionTime = evictionTime;
	}

}
