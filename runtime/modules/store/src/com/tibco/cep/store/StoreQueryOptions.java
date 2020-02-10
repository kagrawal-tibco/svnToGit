/**
 * 
 */
package com.tibco.cep.store;

/**
 * @author vpatil
 *
 */
public class StoreQueryOptions {
	private long prefetchSize;
	private boolean reuse;
	
	public void setPrefetchSize(long prefetchSize) {
		this.prefetchSize = prefetchSize;
	}
	
	public long getPrefetchSize() {
		return prefetchSize;
	}

	public boolean isReuse() {
		return reuse;
	}

	public void setReuse(boolean reuse) {
		this.reuse = reuse;
	}
}
