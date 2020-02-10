package com.tibco.cep.sharedresource.validation;

import com.tibco.cep.studio.core.validation.ValidationContext;

public class HawkSharedResourceValidator extends SharedResourceValidator {

	public boolean canContinue() {
		return true;
	}

	public boolean validate(ValidationContext validationContext) {
		return true;
	}
}