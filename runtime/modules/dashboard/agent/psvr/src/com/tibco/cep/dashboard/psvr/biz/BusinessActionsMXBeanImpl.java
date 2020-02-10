package com.tibco.cep.dashboard.psvr.biz;

import com.tibco.cep.dashboard.psvr.util.PerfMeasurementTracker;

public class BusinessActionsMXBeanImpl implements BusinessActionsMXBean {
	
	private static final Object LOCK = new Object();
	
	private PerfMeasurementTracker requestCountTracker;
	
	private PerfMeasurementTracker failedRequestCountTracker;
	
	public BusinessActionsMXBeanImpl() {
		requestCountTracker = new PerfMeasurementTracker(BusinessActionsController.getInstance().getRequestCount());
		failedRequestCountTracker = new PerfMeasurementTracker(BusinessActionsController.getInstance().getFailureRequestCount());
	}

	@Override
	public double getCumulativeAverageRequestTime() {
		double requestAvg = BusinessActionsController.getInstance().getRequestCount().getAverage();
		double failedRequestAvg = BusinessActionsController.getInstance().getFailureRequestCount().getAverage();
		if (failedRequestAvg > 0){
			return (requestAvg + failedRequestAvg)/2;
		}
		return requestAvg;
	}

	@Override
	public long getCumulativeFailedRequestCount() {
		return BusinessActionsController.getInstance().getFailureRequestCount().getCount();
	}

	@Override
	public long getCumulativeRequestCount() {
		return BusinessActionsController.getInstance().getRequestCount().getCount();
	}
	
	@Override
	public long getFailedRequestCount() {
		synchronized (LOCK) {
			long count = failedRequestCountTracker.getCount();
			failedRequestCountTracker.reset();
			return count;
		}
	}

	@Override
	public long getRequestCount() {
		synchronized (LOCK) {
			long count = requestCountTracker.getCount();
			requestCountTracker.reset();
			return count;
		}
	}	

	@Override
	public void resetStats() {
		BusinessActionsController.getInstance().getRequestCount().reset();
		BusinessActionsController.getInstance().getFailureRequestCount().reset();
		synchronized (LOCK) {
			requestCountTracker.reset();
			failedRequestCountTracker.reset();
		}
	}

	@Override
	public TrafficInfo getTrafficInfo() {
		synchronized (LOCK) {
			long count = requestCountTracker.getCount();
			long failedCount = failedRequestCountTracker.getCount();
			double avg = requestCountTracker.getAverage();
			double failedCountAvg = failedRequestCountTracker.getAverage();
			if (failedCountAvg > 0) {
				avg = (avg + failedCountAvg) / 2;
			}
			TrafficInfo info = new TrafficInfo(count, failedCount, avg);
			requestCountTracker.reset();
			failedRequestCountTracker.reset();
			return info;
		}
	}

}