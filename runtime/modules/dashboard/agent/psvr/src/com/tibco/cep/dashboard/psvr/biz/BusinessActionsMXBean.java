package com.tibco.cep.dashboard.psvr.biz;

import javax.management.MBeanOperationInfo;

import com.tibco.cep.runtime.jmx.Description;
import com.tibco.cep.runtime.jmx.Impact;

@Description("Provides business actions related information for dashboard agent")
public interface BusinessActionsMXBean {

	@Description("Cumulative average time(msecs) spent in executing a business action")
	@Impact(MBeanOperationInfo.INFO)
	public double getCumulativeAverageRequestTime();

	@Description("Cumulative count of business action requests")
	@Impact(MBeanOperationInfo.INFO)
	public long getCumulativeRequestCount();

	@Description("Cumulative count of failed business action requests")
	@Impact(MBeanOperationInfo.INFO)
	public long getCumulativeFailedRequestCount();

	@Description("Business action request traffic information snapshot. The snapshot resets after each invokation")
	@Impact(MBeanOperationInfo.ACTION_INFO)
	public TrafficInfo getTrafficInfo();

	@Description("Snapshot count of business action requests. The snapshot is reset after each invokation")
	@Impact(MBeanOperationInfo.ACTION_INFO)
	public long getRequestCount();

	@Description("Snapshot count of failed business action requests. The snapshot is reset after each invokation")
	@Impact(MBeanOperationInfo.ACTION_INFO)
	public long getFailedRequestCount();

	@Description("Resets all the stats")
	@Impact(MBeanOperationInfo.ACTION)
	public void resetStats();

}
