package com.tibco.cep.runtime.service.cluster.backingstore.recovery.impl;

import java.io.Serializable;

public class PartitionedEntity implements Serializable {
	private int typeId;
	private int partition;
	private int partitionCount;
	private int retryCount = 0;
	private String typeName;
	private String cacheName;
	private BatchStatus status = BatchStatus.READY;
	private boolean loadHandles = false;
	
	private long loadCount = 0;
	
	public PartitionedEntity(int typeId, String typeName, int partition, int partitionCount) {
		this.typeId = typeId;
		this.typeName = typeName;
		this.partition = partition;
		this.partitionCount = partitionCount;
	}
	
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getPartition() {
		return partition;
	}
	public void setPartition(int partition) {
		this.partition = partition;
	}
	public int getRetryCount() {
		return retryCount;
	}
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	
	public Integer getKey(){
		return partition;
	}
	public BatchStatus getStatus() {
		return status;
	}
	public void setStatus(BatchStatus status) {
		this.status = status;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setLoadCount(long count) {
		this.loadCount = count;
	}
	public long getLoadCount() {
		return this.loadCount;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	public String getCacheName() {
		return this.cacheName;
	}
	public int getPartitionCount() {
		return partitionCount;
	}
	public void setPartitionCount(int partitionCount) {
		this.partitionCount = partitionCount;
	}
	
	public boolean isLoadHandles() {
		return loadHandles;
	}
	public void setLoadHandles(boolean loadHandles) {
		this.loadHandles = loadHandles;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(typeName).append(":").append(partition);
		return builder.toString();
	}
}