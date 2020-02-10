package com.tibco.cep.studio.debug.core.process;

import com.tibco.cep.studio.debug.core.process.IProcessBreakpoint.TASK_BREAKPOINT_LOCATION;

public interface IProcessBreakpointInfo {

	void setLocation(TASK_BREAKPOINT_LOCATION location);

	void setGraphObjectId(String graphObjectId);

	TASK_BREAKPOINT_LOCATION getLocation();

	 String getGraphObjectId();
	 
	 String getTaskType();
	 
	 String getProcessUri();

	int getUniqueId();

}
