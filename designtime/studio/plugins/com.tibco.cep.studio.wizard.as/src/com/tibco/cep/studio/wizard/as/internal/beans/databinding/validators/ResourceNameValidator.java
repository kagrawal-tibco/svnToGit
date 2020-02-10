package com.tibco.cep.studio.wizard.as.internal.beans.databinding.validators;

import static com.tibco.cep.studio.common.util.ModelNameUtil.isValidSharedResourceIdentifier;
import static org.eclipse.core.runtime.Status.OK_STATUS;

import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.fieldassist.ControlDecoration;

import com.tibco.cep.studio.wizard.as.commons.beans.databinding.validators.AValidatorWithMessageAndControlDecoration;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;

public class ResourceNameValidator extends AValidatorWithMessageAndControlDecoration {

	private String messageI18NKey;

	public ResourceNameValidator(String messageI18NKey) {
		this(messageI18NKey, null);
	}

	public ResourceNameValidator(String messageI18NKey, ControlDecoration controlDecoration) {
		super(controlDecoration);
		this.messageI18NKey = messageI18NKey;
	}

	@Override
	public IStatus validate(Object value) {
		if (value instanceof String) {
			String s = (String) value;
			boolean valid = isValidSharedResourceIdentifier(s);
			if (valid) {
				return doValidate(OK_STATUS);
			}
			else {
				String message = Messages.getString(messageI18NKey, value);
				return doValidate(ValidationStatus.warning(message));
			}
		}
		else {
			throw new RuntimeException(Messages.getString("ResourceNameValidator.wrong_type")); //$NON-NLS-1$
		}
	}

}
