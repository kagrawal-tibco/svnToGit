package com.tibco.cep.studio.wizard.as.commons.beans.databinding.validators;

import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.fieldassist.ControlDecoration;

import com.tibco.cep.studio.wizard.as.commons.utils.StringUtils;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;

public class StringNotEmptyValidator extends AValidatorWithMessageAndControlDecoration {

	private String message;

	public StringNotEmptyValidator(String message) {
		this(message, null);
	}

	public StringNotEmptyValidator(String message, ControlDecoration controlDecoration) {
		super(controlDecoration);
		this.message = message;
	}

	@Override
	public IStatus validate(Object value) {
		if (null == value) {
			return doValidate(ValidationStatus.warning(message));
		}
		else if (value instanceof String) {
			String s = (String) value;
			if (StringUtils.isNotEmpty(s)) {
				return doValidate(Status.OK_STATUS);
			}
			else {
				return doValidate(ValidationStatus.warning(message));
			}
		}
		else {
			throw new RuntimeException(Messages.getString("StringNotEmptyValidator.wrong_type")); //$NON-NLS-1$
		}
	}

}
