package com.tibco.cep.dashboard.security;

import javax.management.MBeanOperationInfo;

import com.tibco.cep.runtime.jmx.Description;
import com.tibco.cep.runtime.jmx.Impact;
import com.tibco.cep.runtime.jmx.Parameter;

@Description("Provides security related information for dashboard agent")
public interface SecurityMXBean {

	@Description("Total number of login attempts")
	@Impact(MBeanOperationInfo.INFO)
	public int getLoginAttempts();

	@Description("Total number of successful login attempts")
	@Impact(MBeanOperationInfo.INFO)
	public int getSuccessfulLoginAttempts();

	@Description("Total number of failed login attempts")
	@Impact(MBeanOperationInfo.INFO)
	public int getFailedLoginAttempts();

	@Description("Total number of currently logged in users")
	@Impact(MBeanOperationInfo.INFO)
	public int getLoggedInUserCount();

	@Description("Average duration(msecs) of a logged in session")
	@Impact(MBeanOperationInfo.INFO)
	public double getAverageLoggedInTime();

	@Description("User logged in for longest duration")
	@Impact(MBeanOperationInfo.INFO)
	public UserRuntimeInfo getMaxLoggedInUser();

	@Description("User logged in for shortest duration")
	@Impact(MBeanOperationInfo.INFO)
	public UserRuntimeInfo getMinLoggedInUser();

	@Description("Logs out all users with provided user id. Returns the total number of logged in sessions closed")
	@Impact(MBeanOperationInfo.ACTION)
	public int logout(@Parameter("userid") @Description("The user id") String userid);

	@Description("Total number of logout attempts")
	@Impact(MBeanOperationInfo.INFO)
	public int getLogoutAttempts();

	@Description("Total number of successful logout attempts")
	@Impact(MBeanOperationInfo.INFO)
	public int getSuccessfulLogoutAttempts();

	@Description("Total number of failed logout attempts")
	@Impact(MBeanOperationInfo.INFO)
	public int getFailedLogoutAttempts();

	@Description("Resets all the stats")
	@Impact(MBeanOperationInfo.ACTION)
	public void resetStats();

}
