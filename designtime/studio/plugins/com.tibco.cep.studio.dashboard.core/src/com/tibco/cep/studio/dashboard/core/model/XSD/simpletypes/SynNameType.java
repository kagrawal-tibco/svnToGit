package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import java.util.List;

import com.tibco.cep.studio.common.util.EntityNameHelper;
import com.tibco.cep.studio.dashboard.core.exception.SynValidationErrorMessage;
import com.tibco.cep.studio.dashboard.core.util.BasicValidations;

/**
 * Name property must be validated against the name format
 *
 */
public class SynNameType extends SynStringType {

	public SynNameType() {
		super();
		setPattern(BasicValidations.REG_EX_PATTERN_NAME);
		setReadablePatternDescription("An element name must start with a character, may contain integers, and may contain non-repeating embedded underscore '_' or period '.'");
		setMaxLength(BasicValidations.MAX_STRNG_LENGTH);
		setMinLength(1);
	}

	public SynNameType(List<Object> enumValues) {
		super(enumValues);
	}

	public SynNameType(String[] enumValues) {
		super(enumValues);
	}

	public Object cloneThis() throws Exception {
		SynNameType clone = new SynNameType();
		super.cloneThis(clone);
		return clone;
	}

	@Override
	protected boolean isStringValid(String value) {
		if (value != null) {
			boolean valid = EntityNameHelper.isValidBEEntityIdentifier(value);
			if (valid == false) {
				setValidationMessage(new SynValidationErrorMessage("Invalid Name"));
				return false;
			}
		}
		return true;
	}

}