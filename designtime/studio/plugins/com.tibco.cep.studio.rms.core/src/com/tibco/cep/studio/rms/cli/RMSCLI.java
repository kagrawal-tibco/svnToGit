package com.tibco.cep.studio.rms.cli;

import com.tibco.cep.studio.cli.studiotools.AbstractCLI;

/*
@author ssailapp
@date Sep 2, 2011
 */

public abstract class RMSCLI extends AbstractCLI {

	public static final String OPERATION_CATEGORY_RMS = "-rms";

	public String getOperationCategory() {
		return OPERATION_CATEGORY_RMS;
	}
}
