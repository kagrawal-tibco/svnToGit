package com.tibco.cep.dashboard.plugin.beviews.streaming;

import javax.management.MBeanOperationInfo;

import com.tibco.cep.runtime.jmx.Description;
import com.tibco.cep.runtime.jmx.Impact;

@Description("Provides incoming notifications related information for dashboard agent")
public interface BENotificationsMXBean {

	@Description("Count of accepted notifications. The count resets after each invokation")
	@Impact(MBeanOperationInfo.INFO)
	public long getAcceptedNotificationCount();

	@Description("Count of rejected notifications. The count resets after each invokation")
	@Impact(MBeanOperationInfo.INFO)
	public long getRefusedNotificationCount();

	@Description("Average notification processing time")
	@Impact(MBeanOperationInfo.INFO)
	public double getAverageNotificationProcessingTime();

	@Description("Peak notification processing time")
	public double getPeakNotificationProcessingTime();

	@Description("Average notification processing wait time")
	public double getAverageNotificationProcessingWaitTime();

	@Description("Peak notification processing wait time")
	public double getPeakNotificationProcessingWaitTime();


}