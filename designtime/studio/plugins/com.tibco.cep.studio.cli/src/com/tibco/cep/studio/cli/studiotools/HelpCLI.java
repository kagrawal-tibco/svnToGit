package com.tibco.cep.studio.cli.studiotools;

import java.util.Map;

/*
@author ssailapp
@date Sep 9, 2009 12:15:18 AM
 */

public class HelpCLI extends AbstractCLI implements ICommandLineInterpreter{

	public final static String OPERATION_HELP = "-help";
	
	@Override
	public String[] getFlags() {
		return null;
	}

	@Override
	public String getHelp() {
		return ("");
	}

	@Override
	public String getOperationFlag() {
		return "";
	}

	@Override
	public String getUsage() {
		return ("Display help             - studio-tools " + getOperationCategory());
	}

	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		if (checkIfExcludeOperation(argsMap))
			return true;
		
		// do nothing, as caller will print usage
		return false;
	}

	@Override
	public String getOperationCategory() {
		return OPERATION_HELP;
	}

	@Override
	public String getOperationName() {
		return ("");
	}

	@Override
	public String getUsageFlags() {
		return ("");
	}

}
