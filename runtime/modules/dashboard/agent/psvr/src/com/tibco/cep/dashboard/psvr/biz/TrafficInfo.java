package com.tibco.cep.dashboard.psvr.biz;

public final class TrafficInfo {

	private long time;

	private long requestCount;

	private long failedRequestCount;

	private double averageRequestTime;
	
	TrafficInfo(long requestCount, long failedRequestCount, double averageRequestTime) {
		super();
		this.time = System.currentTimeMillis();
		this.requestCount = requestCount;
		this.failedRequestCount = failedRequestCount;
		this.averageRequestTime = averageRequestTime;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(long requestCount) {
		this.requestCount = requestCount;
	}

	public long getFailedRequestCount() {
		return failedRequestCount;
	}

	public void setFailedRequestCount(long failedRequestCount) {
		this.failedRequestCount = failedRequestCount;
	}

	public double getAverageRequestTime() {
		return averageRequestTime;
	}

	public void setAverageRequestTime(double averageRequestTime) {
		this.averageRequestTime = averageRequestTime;
	}

}