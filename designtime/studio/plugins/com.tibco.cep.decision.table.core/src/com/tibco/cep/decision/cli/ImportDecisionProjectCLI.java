package com.tibco.cep.decision.cli;

import java.util.Map;

/*
@author ssailapp
@date Jan 16, 2010 10:53:06 AM
 */

public class ImportDecisionProjectCLI extends DecisionTableCLI {

	private final static String OPERATION_IMPORT = "importDecisionProject";
	private final static String FLAG_IMPORT_DECISION_PROJECT = "-decisionProj";	// Location of the Decision Project
	private final static String FLAG_STUDIO_PROJECT = "-studioProj";	// Path of new project in Studio
	
	public ImportDecisionProjectCLI() {
	}
	
	@Override
	public String[] getFlags() {
		return new String[] {
			FLAG_IMPORT_DECISION_PROJECT,
			FLAG_STUDIO_PROJECT
		};
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return ("");
	}

	@Override
	public String getOperationFlag() {
		return OPERATION_IMPORT;
	}

	@Override
	public String getOperationName() {
		// TODO Auto-generated method stub
		return ("Import Decision Project");
	}

	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getUsageFlags() {
		return "";
	}

}
