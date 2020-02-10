package com.tibco.cep.webstudio.client.decisiontable.constraint;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.tibco.cep.webstudio.client.i18n.DTMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;

/**
 * 
 * @author sasahoo
 *
 */
public enum ANALYZE_PROBLEM_TYPES {
	
	ANALYZE_FIXABLE_MARKER_NAME(0, "ANALYZE_FIXABLE_MARKER_NAME", "Decision table analyze problem"),

	ANALYZE_RULECOMB_MARKER_NAME(1, "ANALYZE_RULECOMB_MARKER_NAME", "Decision table Rule Combination Problem");
	
	public static final int ANALYZE_FIXABLE_MARKER_NAME_VALUE = 0;

	public static final int ANALYZE_RULECOMB_MARKER_NAME_VALUE = 1;
	
	public static final String WS_DECISION_TABLE_ID = "com.tibco.cep.webstudio.decisiontable";
	
	public static final String ANALYZE_FIXABLE_MARKER_ID = WS_DECISION_TABLE_ID + ".analyzeProblem";
	
	public static final String ANALYZE_RULECOMB_MARKER_ID = WS_DECISION_TABLE_ID + ".analyzeCombinationProblem";

	private static final ANALYZE_PROBLEM_TYPES[] VALUES_ARRAY =
		new ANALYZE_PROBLEM_TYPES[] {
		ANALYZE_FIXABLE_MARKER_NAME,
		ANALYZE_RULECOMB_MARKER_NAME
		};

	public static final List<ANALYZE_PROBLEM_TYPES> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	public static ANALYZE_PROBLEM_TYPES get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ANALYZE_PROBLEM_TYPES result = VALUES_ARRAY[i];
			if (result.toString().equalsIgnoreCase(literal)) {
				return result;
			}
		}
		return null;
	}

	public static ANALYZE_PROBLEM_TYPES getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ANALYZE_PROBLEM_TYPES result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	public static ANALYZE_PROBLEM_TYPES get(int value) {
		switch (value) {
			case ANALYZE_FIXABLE_MARKER_NAME_VALUE: return ANALYZE_FIXABLE_MARKER_NAME;
			case ANALYZE_RULECOMB_MARKER_NAME_VALUE: return ANALYZE_RULECOMB_MARKER_NAME;
		}
		return null;
	}

	private final int value;

	private final String name;

	private final String literal;

	private DTMessages dtMsgBundle = (DTMessages)I18nRegistry.getResourceBundle(I18nRegistry.DT_MESSAGES);
	private ANALYZE_PROBLEM_TYPES(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		if(this.value == 0){
			this.literal = dtMsgBundle.dt_analyze_problem();
		}
		else if(this.value == 1){
			this.literal = dtMsgBundle.dt_ruleCombination_problem();
		}
		else{
			this.literal = literal;
		}
	}

	public int getValue() {
	  return value;
	}

	public String getName() {
	  return name;
	}

	public String getLiteral() {
	  return literal;
	}

		@Override
	public String toString() {
		return literal;
	}
}

