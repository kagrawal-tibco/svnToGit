package com.tibco.cep.studio.ui.editors.globalvar;

import java.util.ArrayList;

import com.tibco.cep.repo.GlobalVariableDescriptor;

/*
@author ssailapp
@date Dec 29, 2009 11:29:55 PM
 */

public class GlobalVariablesModel {

	public static final String FIELD_NAME = "name";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_ISDEPLOY = "deploymentSettable";
	public static final String FIELD_ISSERVICE = "serviceSettable";
	public static final String FIELD_DESCRIPTION = "description";
	public static final String FIELD_CONSTRAINT = "constraint";
	public static final String FIELD_MODTIME = "modTime";
	
	public static final String TYPE_INTEGER = "Integer";
	public static final String TYPE_STRING = "String";
	public static final String TYPE_BOOLEAN = "Boolean";
	public static final String TYPE_PASSWORD = "Password";
	
	GlobalVariablesGroup gvGrp;
	
	ProjectGroup curPrGrp;
		
	protected interface IGlobalVariables {
		 abstract boolean isGroup();
		 abstract boolean isProjectLib();
	}

	protected class GlobalVariablesGroup implements IGlobalVariables {
		String path;	// full path
		String name;	// name of the group
		GlobalVariablesGroup parentGrp = null;
		ArrayList<GlobalVariablesDescriptor> gvs;
		ArrayList<GlobalVariablesGroup> gvGrps;
		boolean projectLib = false;
		
		public GlobalVariablesGroup(String gvpath, boolean projectLib) {
			this.path = gvpath.replaceAll("/defaultVars.substvar", "");
			this.name = this.path.substring(path.lastIndexOf("/")+1);
			this.projectLib = projectLib;
			gvs = new ArrayList<GlobalVariablesDescriptor>();
			gvGrps = new ArrayList<GlobalVariablesGroup>();
		}

		@Override
		public boolean isGroup() {
			return true;
		}

		@Override
		public boolean isProjectLib() {
			return projectLib;
		}
		
		public String getPath() {
			return path;
		}
	}
	
	protected class GlobalVariablesDescriptor extends GlobalVariableDescriptor implements IGlobalVariables {
		GlobalVariablesGroup parentGrp = null;
		boolean projectLib = false;
		
		public GlobalVariablesDescriptor(boolean projectLib) {
			super();
			this.projectLib = projectLib;
		}

		@Override
		public boolean isGroup() {
			return false;
		}

		@Override
		public boolean isProjectLib() {
			return projectLib;
		}
	}
	
	public GlobalVariablesModel() {
		gvGrp = new GlobalVariablesGroup("/", false);
	}
}
