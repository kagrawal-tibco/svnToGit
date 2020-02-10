package com.tibco.cep.dashboard.plugin.beviews.streaming;

import com.tibco.cep.dashboard.psvr.util.HistoricalValue;
import com.tibco.cep.dashboard.psvr.util.PerfMeasurementTracker;

public class BENotificationsMXBeanImpl implements BENotificationsMXBean {

	private PerfMeasurementTracker acceptedNotificationCountTracker;

	private PerfMeasurementTracker failedNotificationCountTracker;

	public BENotificationsMXBeanImpl() {
		acceptedNotificationCountTracker = new PerfMeasurementTracker(NotificationPerformanceStats.ACCEPTED_NOTIFICATIONS_COUNTER);
		failedNotificationCountTracker = new PerfMeasurementTracker(NotificationPerformanceStats.REFUSED_NOTIFICATIONS_COUNTER);
	}

	@Override
	public long getAcceptedNotificationCount() {
		synchronized (acceptedNotificationCountTracker) {
			long count = acceptedNotificationCountTracker.getCount();
			acceptedNotificationCountTracker.reset();
			return count;
		}
	}

	@Override
	public double getAverageNotificationProcessingTime() {
		return NotificationPerformanceStats.NOTIFICATIONS_PROCESSING_TIME.getAverage();
	}

	@Override
	public double getPeakNotificationProcessingTime() {
		HistoricalValue highestPeak = NotificationPerformanceStats.NOTIFICATIONS_PROCESSING_TIME.getHighestPeak();
		if (highestPeak == null) {
			return -1;
		}
		return highestPeak.getValue();
	}

	@Override
	public long getRefusedNotificationCount() {
		synchronized (failedNotificationCountTracker) {
			long count = failedNotificationCountTracker.getCount();
			failedNotificationCountTracker.reset();
			return count;
		}
	}

	@Override
	public double getAverageNotificationProcessingWaitTime() {
		return NotificationPerformanceStats.NOTIFICATIONS_PROCESSING_WAIT_TIME.getAverage();
	}

	@Override
	public double getPeakNotificationProcessingWaitTime() {
		HistoricalValue highestPeak = NotificationPerformanceStats.NOTIFICATIONS_PROCESSING_WAIT_TIME.getHighestPeak();
		if (highestPeak == null) {
			return -1;
		}
		return highestPeak.getValue();
	}

}