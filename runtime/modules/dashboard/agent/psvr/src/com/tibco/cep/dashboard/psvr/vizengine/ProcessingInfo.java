package com.tibco.cep.dashboard.psvr.vizengine;

public final class ProcessingInfo {
	
	private long time;
	
	private long totalRequest;
	
	private long modelRequest;
	
	private long dataRequest;
	
	private double average;
	
	private double averageModelRequestTime;
	
	private double averageDataRequestTime;
	
	ProcessingInfo(long totalRequest, long modelRequest, long dataRequest, double average, double averageModelRequestTime, double averageDataRequestTime) {
		super();
		this.time = System.currentTimeMillis();
		this.modelRequest = modelRequest;
		this.dataRequest = dataRequest;
		this.totalRequest = totalRequest;
		this.average = average;
		this.averageModelRequestTime = averageModelRequestTime;
		this.averageDataRequestTime = averageDataRequestTime;
	}

	public long getTime() {
		return time;
	}

	public long getTotalRequest() {
		return totalRequest;
	}

	public void setTotalRequest(long totalRequest) {
		this.totalRequest = totalRequest;
	}

	public long getModelRequest() {
		return modelRequest;
	}

	public void setModelRequest(long modelRequest) {
		this.modelRequest = modelRequest;
	}

	public long getDataRequest() {
		return dataRequest;
	}

	public void setDataRequest(long dataRequest) {
		this.dataRequest = dataRequest;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public double getAverageModelRequestTime() {
		return averageModelRequestTime;
	}

	public void setAverageModelRequestTime(double averageModelRequestTime) {
		this.averageModelRequestTime = averageModelRequestTime;
	}

	public double getAverageDataRequestTime() {
		return averageDataRequestTime;
	}

	public void setAverageDataRequestTime(double averageDataRequestTime) {
		this.averageDataRequestTime = averageDataRequestTime;
	}
	
}