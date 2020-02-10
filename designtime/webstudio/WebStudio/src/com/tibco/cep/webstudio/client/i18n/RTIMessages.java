package com.tibco.cep.webstudio.client.i18n;

import java.util.Map;

/**
 * This class routes all RTIMessages properties. It enables the use of dynamic(created post war compile) properties files.<br/>
 * If the requested property is not found in custom properties it simply uses the property strings from locale property files.
 * 
 * @author moshaikh
 *
 */
public class RTIMessages implements I18nMessages {
	
	private Map<String, String> rtiMessages;

	public RTIMessages(Map<String, String> rtiMessages) {
		this.rtiMessages = rtiMessages;
	}

	private String getPropertyValue(String key, String... replaceValues) {
		return GlobalMessages.getPropertyValue(rtiMessages, key, replaceValues);
	}
	
	public String rtiSave_title() {
		return getPropertyValue("rtiSave_title");
	}
	
	public String rtiSave_message() {
		return getPropertyValue("rtiSave_message");
	}
	
	public String rtiCommand_action() {
		return getPropertyValue("rtiCommand_action");
	}
	
	public String rtiCommand_create() {
		return getPropertyValue("rtiCommand_create");
	}
	
	public String rtiCommand_modify() {
		return getPropertyValue("rtiCommand_modify");
	}
	
	public String rtiCommand_call() {
		return getPropertyValue("rtiCommand_call");
	}
	
	public String rtiCommand_call_ruleFunction() {
		return getPropertyValue("rtiCommand_call_ruleFunction");
	}
	
	public String rticommand_add_parameters() {
		return getPropertyValue("rticommand_add_parameters");
	}
	
	public String rtiCommand_disclaimer() {
		return getPropertyValue("rtiCommand_disclaimer");
	}
	
	public String rtiEditor_when() {
		return getPropertyValue("rtiEditor_when");
	}
	
	public String rtiEditor_then() {
		return getPropertyValue("rtiEditor_then");
	}
	
	public String rtiOperators() {
		return getPropertyValue("rtiOperators");
	}
	
	public String rtiOperator_between() {
		return getPropertyValue("rtiOperator_between");
	}
	
	public String rtiOperator_between_inc() {
		return getPropertyValue("rtiOperator_between_inc");
	}
	
	public String rtiOperator_contains() {
		return getPropertyValue("rtiOperator_contains");
	}
	
	public String rtiOperator_differs() {
		return getPropertyValue("rtiOperator_differs");
	}
	
	public String rtiOperator_not_contain() {
		return getPropertyValue("rtiOperator_not_contain");
	}
	
	public String rtiOperator_ends_with() {
		return getPropertyValue("rtiOperator_ends_with");
	}
	
	public String rtiOperator_equals() {
		return getPropertyValue("rtiOperator_equals");
	}
	
	public String rtiOperator_is_false() {
		return getPropertyValue("rtiOperator_is_false");
	}
	
	public String rtiOperator_equals_ignore_case() {
		return getPropertyValue("rtiOperator_equals_ignore_case");
	}
	
	public String rtiOperator_is_true() {
		return getPropertyValue("rtiOperator_is_true");
	}
	
	public String rtiOperator_greater_than() {
		return getPropertyValue("rtiOperator_greater_than");
	}
	
	public String rtiOperator_greater_than_field() {
		return getPropertyValue("rtiOperator_greater_than_field");
	}
	
	public String rtiOperator_greater_than_equal() {
		return getPropertyValue("rtiOperator_greater_than_equal");
	}
	
	public String rtiOperator_greater_than_equal_field() {
		return getPropertyValue("rtiOperator_greater_than_equal_field");
	}
	
	public String rtiOperator_not_null() {
		return getPropertyValue("rtiOperator_not_null");
	}
	
	public String rtiOperator_null() {
		return getPropertyValue("rtiOperator_null");
	}
	
	public String rtiOperator_less_than() {
		return getPropertyValue("rtiOperator_less_than");
	}
	
	public String rtiOperator_less_than_field() {
		return getPropertyValue("rtiOperator_less_than_field");
	}
	
	public String rtiOperator_less_than_equal() {
		return getPropertyValue("rtiOperator_less_than_equal");
	}
	
	public String rtiOperator_less_than_equal_field() {
		return getPropertyValue("rtiOperator_less_than_equal_field");
	}
	
	public String rtiOperator_matches_field() {
		return getPropertyValue("rtiOperator_matches_field");
	}
	
	public String rtiOperator_not_equals() {
		return getPropertyValue("rtiOperator_not_equals");
	}
	
	public String rtiOperator_not_equals_ignore_case() {
		return getPropertyValue("rtiOperator_not_equals_ignore_case");
	}
	
	public String rtiOperator_starts_with() {
		return getPropertyValue("rtiOperator_starts_with");
	}
	
	public String rtiOperator_match_any() {
		return getPropertyValue("rtiOperator_match_any");
	}
	
	public String rtiOperator_match_all() {
		return getPropertyValue("rtiOperator_match_all");
	}
	
	public String rtiOperator_match_none() {
		return getPropertyValue("rtiOperator_match_none");
	}
	
	public String rtiOperator_set_to() {
		return getPropertyValue("rtiOperator_set_to");
	}
	
	public String rtiOperator_set_to_date() {
		return getPropertyValue("rtiOperator_set_to_date");
	}
	
	public String rtiOperator_set_to_false() {
		return getPropertyValue("rtiOperator_set_to_false");
	}
	
	public String rtiOperator_set_to_field() {
		return getPropertyValue("rtiOperator_set_to_field");
	}
	
	public String rtiOperator_set_to_null() {
		return getPropertyValue("rtiOperator_set_to_null");
	}
	
	public String rtiOperator_set_to_true() {
		return getPropertyValue("rtiOperator_set_to_true");
	}
	
	public String rtiOperator_increment_by() {
		return getPropertyValue("rtiOperator_increment_by");
	}
	
	public String rtiOperator_increment_by_field() {
		return getPropertyValue("rtiOperator_increment_by_field");
	}
	
	public String rtiOperator_decrement_by() {
		return getPropertyValue("rtiOperator_decrement_by");
	}
	
	public String rtiOperator_decrement_by_field() {
		return getPropertyValue("rtiOperator_decrement_by_field");
	}
	
	public String rtiValidation_missingActions() {
		return getPropertyValue("rtiValidation_missingActions");
	}
	
	public String rtiValidation_missingActions_valueMismatch() {
		return getPropertyValue("rtiValidation_missingActions_valueMismatch");
	}
	
	public String rtiValidation_missingActions_valueMismatch_Format() {
		return getPropertyValue("rtiValidation_missingActions_valueMismatch_Format");
	}
	
	public String rtiValidation_missingActions_typeMismatch() {
		return getPropertyValue("rtiValidation_missingActions_typeMismatch");
	}
	
	public String rtiValidation_missingActions_operatorMismatch() {
		return getPropertyValue("rtiValidation_missingActions_operatorMismatch");
	}
	
	public String rtiValidation_missingBindings() {
		return getPropertyValue("rtiValidation_missingBindings");
	}
	
	public String rtiValidation_missingConditions() {
		return getPropertyValue("rtiValidation_missingConditions");
	}
	
	public String rtiPropertyTab_General() {
		return getPropertyValue("rtiPropertyTab_titleGeneral");
	}
	
	public String rtiPropertyTab_Name() {
		return getPropertyValue("rtiPropertyTab_name");
	}
	
	public String rtiPropertyTab_Description() {
		return getPropertyValue("rtiPropertyTab_description");
	}
	
	public String rtiPropertyTab_Priority() {
		return getPropertyValue("rtiPropertyTab_priority");
	}
}
