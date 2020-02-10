package com.tibco.cep.studio.ui.editors.globalvar;

import java.util.ArrayList;
import java.util.List;

public class ProjectLibGroup implements IGVProjectGroup {
	
	private List<IGVProjectGroup> projectGroup;
	
	public List<IGVProjectGroup> getProjectGroup() {
		if (projectGroup == null) {
			projectGroup = new ArrayList<IGVProjectGroup>();
		}
		return projectGroup;
	}
	
	public ProjectGroup[] getProjectGroupArray() {
		getProjectGroup();
		ProjectGroup[] grp = new ProjectGroup[projectGroup.size()];
		return projectGroup.toArray(grp);
	}
	
	@Override
	public boolean isProjectLibGroup() {
		return true;
	}
}
