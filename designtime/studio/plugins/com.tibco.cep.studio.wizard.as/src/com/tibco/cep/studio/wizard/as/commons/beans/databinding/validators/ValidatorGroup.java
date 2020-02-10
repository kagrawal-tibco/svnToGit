package com.tibco.cep.studio.wizard.as.commons.beans.databinding.validators;

import java.util.List;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class ValidatorGroup implements IValidator {

	private List<IValidator> validators;

	public ValidatorGroup(List<IValidator> validators) {
		this.validators = validators;
	}

	@Override
	public IStatus validate(Object value) {
		IStatus status = Status.OK_STATUS;
		for (IValidator validator : validators) {
			status = validator.validate(value);
			if (false == status.isOK()) {
				break;
			}
		}
		return status;
	}

}
