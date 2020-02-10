package com.tibco.cep.dashboard.psvr.streaming;

import com.tibco.cep.dashboard.psvr.util.HistoricalValue;

public class ChannelRuntimeInfo {
	
	private String userID;
	
	private String name;
	
	private String streamingMode;
	
	private String state;
	
	private String[] subscriptions;
	
	private double averageUpdateProcessingTime;

	private double averageMarshallingTime;
	
	private double averageStreamingTime;

	private HistoricalValue peakUpdateProcessingTime;

	private HistoricalValue peakMarshallingProcessingTime;

	private HistoricalValue peakStreamingTime;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userid) {
		this.userID = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreamingMode() {
		return streamingMode;
	}

	public void setStreamingMode(String streamingMode) {
		this.streamingMode = streamingMode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String[] getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(String[] subscriptions) {
		this.subscriptions = subscriptions;
	}

	public double getAverageUpdateProcessingTime() {
		return averageUpdateProcessingTime;
	}

	public void setAverageUpdateProcessingTime(double averageUpdateProcessingTime) {
		this.averageUpdateProcessingTime = averageUpdateProcessingTime;
	}

	public double getAverageMarshallingTime() {
		return averageMarshallingTime;
	}

	public void setAverageMarshallingTime(double averageMarshallingTime) {
		this.averageMarshallingTime = averageMarshallingTime;
	}

	public double getAverageStreamingTime() {
		return averageStreamingTime;
	}

	public void setAverageStreamingTime(double averageStreamingTime) {
		this.averageStreamingTime = averageStreamingTime;
	}

	public HistoricalValue getPeakUpdateProcessingTime() {
		return peakUpdateProcessingTime;
	}

	public void setPeakUpdateProcessingTime(HistoricalValue peakUpdateProcessingTime) {
		this.peakUpdateProcessingTime = peakUpdateProcessingTime;
	}

	public HistoricalValue getPeakMarshallingProcessingTime() {
		return peakMarshallingProcessingTime;
	}

	public void setPeakMarshallingTime(HistoricalValue peakMarshallingProcessingTime) {
		this.peakMarshallingProcessingTime = peakMarshallingProcessingTime;
	}

	public HistoricalValue getPeakStreamingTime() {
		return peakStreamingTime;
	}

	public void setPeakStreamingTime(HistoricalValue peakStreamingTime) {
		this.peakStreamingTime = peakStreamingTime;
	}
	
}