package com.tibco.cep.studio.ui.editors.globalvar;

import com.tibco.cep.studio.ui.editors.globalvar.GlobalVariablesModel.GlobalVariablesGroup;
import com.tibco.cep.studio.ui.editors.globalvar.GlobalVariablesModel.IGlobalVariables;

public class ProjectGroup implements IGVProjectGroup {

	private String projectName;
	private String path;
	private boolean projectLib;
	private IGlobalVariables gvGrpRoot;
	private GlobalVariablesModelMgr manager;
	
	/**
	 * @param projectName
	 * @param path
	 * @param projectLib
	 * @param gvGrpRoot
	 */
	public ProjectGroup(String projectName, 
			            String path, 
			            boolean projectLib, 
			            IGlobalVariables gvGrpRoot,
			            GlobalVariablesModelMgr manager) {
		this.projectName = projectName;
		this.path = path;
		this.projectLib = projectLib;
		this.gvGrpRoot = gvGrpRoot;
		this.manager = manager;
	}
	
	public IGlobalVariables getGrpVarRoot() {
		return gvGrpRoot;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getPath() {
		return path;
	}

	public boolean isProjectLib() {
		return projectLib;
	}
	
	public IGlobalVariables[] getGlobalVariables() {
		return manager.getRootGvs((GlobalVariablesGroup)gvGrpRoot);
	}

	@Override
	public boolean isProjectLibGroup() {
		return false;
	}
}