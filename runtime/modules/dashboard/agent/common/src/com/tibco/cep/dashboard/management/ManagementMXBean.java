package com.tibco.cep.dashboard.management;

import javax.management.MBeanOperationInfo;

import com.tibco.cep.runtime.jmx.Description;
import com.tibco.cep.runtime.jmx.Impact;

@Description("Provides life cycle management for dashboard agent")
public interface ManagementMXBean {

	@Description("Suspends the dashboard agent if running")
	@Impact(MBeanOperationInfo.ACTION)
	public void suspend();

	@Description("Resumes the dashboard agent if suspended")
	@Impact(MBeanOperationInfo.ACTION)
	public void resume();

	@Description("Stops the dashboard agent if running")
	@Impact(MBeanOperationInfo.ACTION)
	public void stop();
}
