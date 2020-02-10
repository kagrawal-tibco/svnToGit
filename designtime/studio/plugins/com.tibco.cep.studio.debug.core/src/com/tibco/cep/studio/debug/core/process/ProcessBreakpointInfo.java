package com.tibco.cep.studio.debug.core.process;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tibco.cep.studio.debug.core.process.IProcessBreakpoint.TASK_BREAKPOINT_LOCATION;

public class ProcessBreakpointInfo implements IProcessBreakpointInfo {
	String processUri = null;
	String graphObjectId = null;
	String taskType = null;
	TASK_BREAKPOINT_LOCATION location = TASK_BREAKPOINT_LOCATION.START;
	private int uniqueId;
	public ProcessBreakpointInfo(String processUri, String graphObjectId,String taskType, TASK_BREAKPOINT_LOCATION location, int uniqueId) {
		super();
		this.processUri = processUri;
		this.graphObjectId = graphObjectId;
		this.location = location;
		this.taskType = taskType;
		this.uniqueId = uniqueId;
	}
	
	public String getProcessUri() {
		return processUri;
	}
	
	@Override
	public String getGraphObjectId() {
		return graphObjectId;
	}
	
	@Override
	public TASK_BREAKPOINT_LOCATION getLocation() {
		return location;
	}
	
	@Override
	public String getTaskType() {
		return taskType;
	}
	@Override
	public int getUniqueId() {
		return uniqueId;
	}
	
	@Override
	public void setGraphObjectId(String graphObjectId) {
		this.graphObjectId = graphObjectId;
	}
	
	@Override
	public void setLocation(TASK_BREAKPOINT_LOCATION location) {
		this.location = location;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((graphObjectId == null) ? 0 : graphObjectId.hashCode());
//		result = prime * result + ((location == null) ? 0 : location.name().hashCode());
//		result = prime * result + ((processUri == null) ? 0 : processUri.hashCode());
//		result = prime * result + ((taskType == null) ? 0 : taskType.hashCode());
//		return result;
		HashCodeBuilder hcb = new HashCodeBuilder(83,7);
		if(graphObjectId != null) {
			hcb.append(graphObjectId);
		}
		if(location != null) {
			hcb.append(location.ordinal());
		}
		if(processUri != null) {
			hcb.append(processUri);
		}
		if(taskType != null) {
			hcb.append(taskType);
		}
	    return hcb.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ProcessBreakpointInfo)) {
			return false;
		}
		ProcessBreakpointInfo other = (ProcessBreakpointInfo) obj;
		if (graphObjectId == null) {
			if (other.graphObjectId != null) {
				return false;
			}
		} else if (!graphObjectId.equals(other.graphObjectId)) {
			return false;
		}
		if (location != other.location) {
			return false;
		}
		if (processUri == null) {
			if (other.processUri != null) {
				return false;
			}
		} else if (!processUri.equals(other.processUri)) {
			return false;
		}
		if (taskType == null) {
			if (other.taskType != null) {
				return false;
			}
		} else if (!taskType.equals(other.taskType)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProcessBreakpointInfo [" + (processUri != null ? "processUri=" + processUri + ", " : "")
				+ (graphObjectId != null ? "graphObjectId=" + graphObjectId + ", " : "") + (taskType != null ? "taskType=" + taskType + ", " : "")
				+ (location != null ? "location=" + location : "") + "]";
	}

		


}
