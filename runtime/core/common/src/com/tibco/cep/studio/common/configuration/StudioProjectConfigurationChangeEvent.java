package com.tibco.cep.studio.common.configuration;

import com.tibco.cep.studio.common.configuration.update.StudioProjectConfigurationDelta;


public class StudioProjectConfigurationChangeEvent {
	
//	public static final int ADDED 	= 0;
//	public static final int REMOVED = 1;
//	public static final int CHANGED = 2;
	
//	private int fType;
//	private String fProjectName;
	private StudioProjectConfigurationDelta fDelta;

	public StudioProjectConfigurationChangeEvent(StudioProjectConfigurationDelta configuration) {
		this.fDelta = configuration;
//		this.fProjectName = projectName;
//		this.fType = changeType;
	}

//	public String getProjectName() {
//		return fProjectName;
//	}

	public StudioProjectConfigurationDelta getDelta() {
		return fDelta;
	}

//	public int getChangeType() {
//		return fType;
//	}
}
