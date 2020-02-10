package com.tibco.cep.decision.cli;

import com.tibco.cep.studio.cli.studiotools.AbstractCLI;

/*
 @author ssailapp
 @date Sep 2, 2011
 */

public abstract class DecisionTableCLI extends AbstractCLI {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

	public static final String OPERATION_CATEGORY_DT = "-dt";

	public String getOperationCategory() {
		return OPERATION_CATEGORY_DT;
	}
}
