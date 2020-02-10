package com.tibco.cep.dashboard.plugin.beviews.streaming;

import com.tibco.cep.dashboard.psvr.util.PerformanceMeasurement;

public class NotificationPerformanceStats {

	static PerformanceMeasurement ACCEPTED_NOTIFICATIONS_COUNTER = new PerformanceMeasurement("Accepted Notification Count");;

	static PerformanceMeasurement REFUSED_NOTIFICATIONS_COUNTER = new PerformanceMeasurement("Refused Notification Count");;

	static PerformanceMeasurement NOTIFICATIONS_PROCESSING_WAIT_TIME = new PerformanceMeasurement("Notification Processing Wait Time");;

	static PerformanceMeasurement NOTIFICATIONS_PROCESSING_TIME = new PerformanceMeasurement("Notification Processing Time");;

}
