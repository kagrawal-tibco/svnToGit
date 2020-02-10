package com.tibco.cep.sharedresource.validation;

import com.tibco.cep.studio.core.validation.ValidationContext;

/*
@author Huabin Zhang (huzhang@tibco-support.com)
@date Feb 23, 2012 9:09:21 PM
 */

public class ASConnectionSharedResourceValidator extends SharedResourceValidator {

	@Override
	public boolean canContinue() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#validate(com.tibco.cep.studio.core.validation.ValidationContext)
	 */
	@Override
	public boolean validate(ValidationContext validationContext) {
		return true;
	}

}
