package com.tibco.cep.studio.wizard.as.commons.beans.databinding.validators;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.fieldassist.ControlDecoration;

abstract public class AValidatorWithMessageAndControlDecoration implements IValidator {

	protected final ControlDecoration controlDecoration;

	protected AValidatorWithMessageAndControlDecoration(ControlDecoration controlDecoration) {
		this.controlDecoration = controlDecoration;
	}

	protected final IStatus doValidate(IStatus status) {
		if (status.isOK()) {
    		if (null != controlDecoration) {
    			controlDecoration.hide();
    		}
		} else {
			if (null != controlDecoration) {
    			controlDecoration.show();
    		} 
		}
		return status;
	}

}
