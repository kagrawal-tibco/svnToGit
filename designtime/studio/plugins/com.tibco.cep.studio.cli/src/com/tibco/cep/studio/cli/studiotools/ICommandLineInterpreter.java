package com.tibco.cep.studio.cli.studiotools;

import java.util.Map;

/*
@author ssailapp
@date Sep 8, 2009 5:20:17 PM
 */

public interface ICommandLineInterpreter {

	public String[] getFlags();

	public String getOperationCategory(); // -core, -rms, -dt
	
	public String getOperationFlag();
	
	public String getOperationName();
	
	public String getUsage();
	
	public String getUsageFlags();
	
	public String getHelp();
	
	public boolean runOperation(Map<String, String> argsMap) throws Exception;
	
	public boolean checkIfExcludeOperation(Map<String, String> argsMap);
}
