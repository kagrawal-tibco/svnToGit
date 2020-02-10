package com.tibco.cep.dashboard.psvr.streaming;

import com.tibco.cep.dashboard.psvr.util.HistoricalValue;

public class DataSourceUpdaterRuntimeInfo {

	private String subscriptionName;

	private String source;

	private String subscription;

	private boolean subscribed;

	private String state;

	private String[] subscribers;

	private double averageUpdateProcessingTime;

	private double averageResetProcessingTime;

	private HistoricalValue peakUpdateProcessingTime;

	private HistoricalValue peakResetProcessingTime;

	public String getSubscriptionName() {
		return subscriptionName;
	}

	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSubscription() {
		return subscription;
	}

	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}

	public boolean isSubscribed() {
		return subscribed;
	}

	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String[] getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(String[] subscribers) {
		this.subscribers = subscribers;
	}

	public double getAverageUpdateProcessingTime() {
		return averageUpdateProcessingTime;
	}

	public void setAverageUpdateProcessingTime(double avgUpdateProcessingTime) {
		this.averageUpdateProcessingTime = avgUpdateProcessingTime;
	}

	public double getAverageResetProcessingTime() {
		return averageResetProcessingTime;
	}

	public void setAverageResetProcessingTime(double avgResetProcessingTime) {
		this.averageResetProcessingTime = avgResetProcessingTime;
	}

	public HistoricalValue getPeakUpdateProcessingTime() {
		return peakUpdateProcessingTime;
	}

	public void setPeakUpdateProcessingTime(HistoricalValue peakUpdateProcessingTime) {
		this.peakUpdateProcessingTime = peakUpdateProcessingTime;
	}

	public HistoricalValue getPeakResetProcessingTime() {
		return peakResetProcessingTime;
	}

	public void setPeakResetProcessingTime(HistoricalValue peakResetProcessingTime) {
		this.peakResetProcessingTime = peakResetProcessingTime;
	}

}