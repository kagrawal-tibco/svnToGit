package com.tibco.cep.studio.cli.studiotools;

/*
@author ssailapp
@date Sep 2, 2011
 */

public abstract class CoreCLI extends AbstractCLI {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    
    
	public static final String OPERATION_CATEGORY_CORE = "-core";
	
	public String getOperationCategory() {
		return OPERATION_CATEGORY_CORE;
	}
}
