package com.tibco.cep.runtime.service.cluster.backingstore.recovery.impl;

import java.io.Serializable;
import java.text.MessageFormat;

public class DistributedBatch implements Serializable {
	
	private int typeId;
	private long startId;
	private long endId;
	private BatchStatus status = BatchStatus.READY;
	private long loadCount = 0;
	
	public DistributedBatch(int typeId, long startId, long endId) {
		super();
		this.typeId = typeId;
		this.startId = startId;
		this.endId = endId;
	}
	
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public long getStartId() {
		return startId;
	}
	public void setStartId(long startId) {
		this.startId = startId;
	}
	public long getEndId() {
		return endId;
	}
	public void setEndId(long endId) {
		this.endId = endId;
	}
	public BatchStatus getStatus() {
		return status;
	}
	public void setStatus(BatchStatus status) {
		this.status = status;
	}
	public void setLoadCount(long count) {
		this.loadCount = count;
	}
	public long getLoadCount() {
		return this.loadCount;
	}
	
    public String getKey() {
    	StringBuilder builder = new StringBuilder(256);
    	builder.append(typeId);
    	builder.append(":");
    	builder.append(String.valueOf(startId));
    	builder.append(":");
    	builder.append(String.valueOf(endId));
	    return builder.toString();
    }
    
    public String toString() {
    	return MessageFormat.format("TypeId={0} TypeName={1} StartId={2} EndId={3} RetryCount={4} Status={5} OwnerMemberUID={6}", 
    			String.valueOf(typeId),
    			String.valueOf(startId),
    			String.valueOf(endId),
    			String.valueOf(3),
    			status.name());
    	// TODO: UID is not provided!
    }
    
    public boolean equals(Object obj) {
    	if (obj == null || !(obj instanceof DistributedBatch)) {
    		return false;
    	}
    	DistributedBatch batch = (DistributedBatch) obj;
    	return getKey().equals(batch.getKey());
    }
}
