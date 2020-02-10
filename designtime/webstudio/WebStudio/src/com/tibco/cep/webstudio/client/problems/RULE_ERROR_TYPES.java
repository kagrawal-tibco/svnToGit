package com.tibco.cep.webstudio.client.problems;

import com.tibco.cep.webstudio.client.i18n.DTMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;

/**
 * This Enum indicates various types of problems encountered during artifact
 * validation
 * 
 * @author sasahoo
 */
public enum RULE_ERROR_TYPES {
	
	SYNTAX_TYPE(0, "Syntax", "Syntax"),
	SEMANTIC_TYPE(1, "Semantic", "Semantic"),
	WARNING_TYPE(2, "Warning", "Warning"),
	INTERNAL_TYPE(3, "Internal", "Internal");

	private static final RULE_ERROR_TYPES[] VALUES_ARRAY =
		new RULE_ERROR_TYPES[] {
			SYNTAX_TYPE,
			SEMANTIC_TYPE,
			WARNING_TYPE,
			INTERNAL_TYPE,
		
		};

	public static RULE_ERROR_TYPES get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			RULE_ERROR_TYPES result = VALUES_ARRAY[i];
			if (result.toString().equalsIgnoreCase(literal)) {
				return result;
			}
		}
		return null;
	}

	public static RULE_ERROR_TYPES getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			RULE_ERROR_TYPES result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	public static RULE_ERROR_TYPES get(int value) {
		for (int i = 0; i < VALUES_ARRAY.length; i++) {
			RULE_ERROR_TYPES result = VALUES_ARRAY[i];
			if (result.getValue() == value) {
				return result;
			}
		}
		return null;
	}

	private final int value;
	private final String name;
	private final String literal;
	
	private static DTMessages dtMsgs = (DTMessages) I18nRegistry.getResourceBundle(I18nRegistry.DT_MESSAGES);

	private RULE_ERROR_TYPES(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;		
	}

	public int getValue() {
	  return value;
	}

	public String getName() {
	  return name;
	}

	public String getLiteral() {
	  return getLocalizedLiteral(literal);
	}

		@Override
	public String toString() {
		return getLocalizedLiteral(literal);
	}
		
	private String getLocalizedLiteral(String literal) {
		if (literal.equalsIgnoreCase("Syntax")) return dtMsgs.dt_validation_errorType_Syntax();
		if (literal.equalsIgnoreCase("Semantic")) return dtMsgs.dt_validation_errorType_Semantic();
		if (literal.equalsIgnoreCase("Warning")) return dtMsgs.dt_validation_errorType_Warning();
		if (literal.equalsIgnoreCase("Internal")) return dtMsgs.dt_validation_errorType_Internal();
		return literal;
	}
}
