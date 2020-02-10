package com.tibco.cep.studio.cli.studiotools;

import java.util.Map;

/*
@author ssailapp
@date Sep 2, 2011
 */

public abstract class AbstractCLI implements ICommandLineInterpreter {

	@Override
	public boolean checkIfExcludeOperation(Map<String, String> argsMap) {
        if (argsMap.containsKey(getOperationCategory())) {
            String opName = argsMap.get(getOperationCategory());
            if (getOperationFlag().equalsIgnoreCase(opName)) {
                return false;
            }            	
        }
        return true;
	}
	
	@Override
    public String getUsage() {
    	return (getOperationName() + " - studio-tools " + getOperationCategory() + " " + getOperationFlag() + " " + getUsageFlags());
    }

}
