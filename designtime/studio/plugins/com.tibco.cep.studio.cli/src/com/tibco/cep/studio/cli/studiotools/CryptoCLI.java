package com.tibco.cep.studio.cli.studiotools;

import java.util.Map;

public abstract class CryptoCLI extends AbstractCLI {

	public static final String OPERATION_CATEGORY_CRYPTO = "-crypto";
	
	@Override
	public String getOperationCategory() {
		// TODO Auto-generated method stub
		return OPERATION_CATEGORY_CRYPTO;
	}
}
