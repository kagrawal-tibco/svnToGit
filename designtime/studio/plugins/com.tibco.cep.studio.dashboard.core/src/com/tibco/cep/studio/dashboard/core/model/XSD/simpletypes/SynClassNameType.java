package com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes;

import java.util.List;

import com.tibco.cep.studio.dashboard.core.util.BasicValidations;

/**
 * Name property must be validated against the name format
 *
 * @deprecated
 */
public class SynClassNameType extends SynStringType {

	public SynClassNameType() {
		super();
		setPattern(BasicValidations.REG_EX_PATTERN_JAVA_IDENTIFIER);
		setReadablePatternDescription("A class name must start with a character, may contain integers, and may contain non-repeating embedded period '.' and/or underscore '_'");
		setMaxLength(BasicValidations.MAX_NAME_LENGTH);
		setMinLength(1);
	}

	public SynClassNameType(List<Object> enumValues) {
		super(enumValues);
	}

	public SynClassNameType(String[] enumValues) {
		super(enumValues);
	}

	public Object cloneThis() throws Exception {
		SynClassNameType clone = new SynClassNameType();
		super.cloneThis(clone);
		return clone;
	}

}