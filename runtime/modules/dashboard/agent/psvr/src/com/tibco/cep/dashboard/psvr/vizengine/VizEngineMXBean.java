package com.tibco.cep.dashboard.psvr.vizengine;

import javax.management.MBeanOperationInfo;

import com.tibco.cep.runtime.jmx.Description;
import com.tibco.cep.runtime.jmx.Impact;

@Description("Provides data cache related information for dashboard agent")
public interface VizEngineMXBean {

	@Description("Cumulative count of visualization engine requests")
	@Impact(MBeanOperationInfo.INFO)
	public long getCumulativeTotalRequestCount();

	@Description("Cumulative count of visualization engine model requests")
	@Impact(MBeanOperationInfo.INFO)
	public long getCumulativeModelRequestCount();

	@Description("Cumulative count of visualization engine data requests")
	@Impact(MBeanOperationInfo.INFO)
	public long getCumulativeDataRequestCount();

	@Description("Cumulative average time(msecs) spent in executing a visualization engine model request")
	@Impact(MBeanOperationInfo.INFO)
	public double getCumulativeAverageModelRequestTime();

	@Description("Cumulative average time(msecs) spent in executing a visualization engine data request")
	@Impact(MBeanOperationInfo.INFO)
	public double getCumulativeAverageDataRequestTime();

	@Description("Cumulative average time(msecs) spent in executing a visualization engine request")
	@Impact(MBeanOperationInfo.INFO)
	public double getCumulativeAverageRequestTime();

	@Description("Visaulization engine request processing information snapshot. The processing information resets after each invokation")
	@Impact(MBeanOperationInfo.ACTION_INFO)
	public ProcessingInfo getProcessingInfo();

	@Description("Snapshot count of visualization engine requests. The snapshot is reset after each invokation")
	@Impact(MBeanOperationInfo.ACTION_INFO)
	public long getTotalRequestCount();

	@Description("Snapshot count of visualization engine model requests. The snapshot is reset after each invokation")
	@Impact(MBeanOperationInfo.ACTION_INFO)
	public long getModelRequestCount();

	@Description("Snapshot count of visualization engine data requests. The snapshot is reset after each invokation")
	@Impact(MBeanOperationInfo.ACTION_INFO)
	public long getDataRequestCount();

	@Description("Resets all the stats")
	@Impact(MBeanOperationInfo.ACTION)
	public void resetStats();

}
