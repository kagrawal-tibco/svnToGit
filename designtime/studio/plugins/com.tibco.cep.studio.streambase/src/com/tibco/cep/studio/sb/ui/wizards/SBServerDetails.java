package com.tibco.cep.studio.sb.ui.wizards;

import com.tibco.cep.repo.GlobalVariables;

public class SBServerDetails {
	
	public static final int SRC_SERVER = 0;
	public static final int SRC_NAMED_SCHEMA = 1;

	private String sbServerURI;
	private String username;
	private String password;
	private String namedSchemaPath;
	private int configType;
	private GlobalVariables gvars;
	
	public int getConfigType() {
		return configType;
	}
	public String getNamedSchemaPath() {
		return namedSchemaPath;
	}
	public void setNamedSchemaPath(String namedSchemaPath) {
		this.namedSchemaPath = namedSchemaPath;
	}
	public String getSbServerURI(boolean resolveGlobalVar) {
		if (resolveGlobalVar && gvars != null) {
			return gvars.substituteVariables(sbServerURI).toString();
		}
		return sbServerURI;
	}
	public void setSbServerURI(String sbServerURI) {
		this.sbServerURI = sbServerURI;
	}
	public String getUsername(boolean resolveGlobalVar) {
		if (resolveGlobalVar && gvars != null) {
			return gvars.substituteVariables(username).toString();
		}
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword(boolean resolveGlobalVar) {
		if (resolveGlobalVar && gvars != null) {
			return gvars.substituteVariables(password).toString();
		}
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setConfigType(int schemaSource) {
		this.configType = schemaSource;
	}
	public void setGlobalVariables(GlobalVariables gvars) {
		this.gvars = gvars;
	}

}
