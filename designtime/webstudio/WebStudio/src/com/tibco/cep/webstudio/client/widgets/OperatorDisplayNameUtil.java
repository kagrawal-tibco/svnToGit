package com.tibco.cep.webstudio.client.widgets;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RTIMessages;
import com.tibco.cep.webstudio.model.rule.instance.operators.CommandOperator;
import com.tibco.cep.webstudio.model.rule.instance.operators.FilterOperator;

/**
 * Utility to get RTI operator name from properties file. 
 * @author Aparajita
 */
public class OperatorDisplayNameUtil {

	private static Map<String,String> operatorMap = new HashMap<String, String>();
	
	private static RTIMessages rtiMessages = (RTIMessages)I18nRegistry.getResourceBundle(I18nRegistry.RTI_MESSAGES);
	
	static {
		operatorMap.put(FilterOperator.BETWEEN.getValue(), rtiMessages.rtiOperator_between());
		operatorMap.put(FilterOperator.BETWEEN_INCLUSIVE.getValue(), rtiMessages.rtiOperator_between_inc());
		operatorMap.put(FilterOperator.CONTAINS.getValue(), rtiMessages.rtiOperator_contains());
		operatorMap.put(FilterOperator.DIFFERS_FROM_FIELD.getValue(), rtiMessages.rtiOperator_differs());
		operatorMap.put(FilterOperator.DOES_NOT_CONTAIN.getValue(), rtiMessages.rtiOperator_not_contain());
		operatorMap.put(FilterOperator.ENDS_WITH.getValue(), rtiMessages.rtiOperator_ends_with());
		operatorMap.put(FilterOperator.EQUALS.getValue(), rtiMessages.rtiOperator_equals());
		operatorMap.put(FilterOperator.EQUALS_FALSE.getValue(), rtiMessages.rtiOperator_is_false());
		operatorMap.put(FilterOperator.EQUALS_IGNORE_CASE.getValue(), rtiMessages.rtiOperator_equals_ignore_case());
		operatorMap.put(FilterOperator.EQUALS_TRUE.getValue(), rtiMessages.rtiOperator_is_true());
		operatorMap.put(FilterOperator.GREATER_THAN.getValue(), rtiMessages.rtiOperator_greater_than());
		operatorMap.put(FilterOperator.GREATER_THAN_FIELD.getValue(), rtiMessages.rtiOperator_greater_than_field());
		operatorMap.put(FilterOperator.GREATER_THAN_OR_EQUAL_TO.getValue(), rtiMessages.rtiOperator_greater_than_equal());
		operatorMap.put(FilterOperator.GREATER_THAN_OR_EQUAL_TO_FIELD.getValue(), rtiMessages.rtiOperator_greater_than_equal_field());
		operatorMap.put(FilterOperator.IS_NOT_NULL.getValue(), rtiMessages.rtiOperator_not_null());
		operatorMap.put(FilterOperator.IS_NULL.getValue(), rtiMessages.rtiOperator_null());
		operatorMap.put(FilterOperator.LESS_THAN.getValue(), rtiMessages.rtiOperator_less_than());
		operatorMap.put(FilterOperator.LESS_THAN_FIELD.getValue(), rtiMessages.rtiOperator_less_than_field());
		operatorMap.put(FilterOperator.LESS_THAN_OR_EQUAL_TO.getValue(), rtiMessages.rtiOperator_less_than_equal());
		operatorMap.put(FilterOperator.LESS_THAN_OR_EQUAL_TO_FIELD.getValue(), rtiMessages.rtiOperator_less_than_equal_field());
		operatorMap.put(FilterOperator.MATCHES_OTHER_FIELD.getValue(), rtiMessages.rtiOperator_matches_field());
		operatorMap.put(FilterOperator.NOT_EQUALS.getValue(), rtiMessages.rtiOperator_not_equals());
		operatorMap.put(FilterOperator.NOT_EQUALS_IGNORE_CASE.getValue(), rtiMessages.rtiOperator_not_equals_ignore_case());
		operatorMap.put(FilterOperator.STARTS_WITH.getValue(), rtiMessages.rtiOperator_starts_with());
		operatorMap.put(CommandOperator.DECREMENT_BY.getValue(), rtiMessages.rtiOperator_decrement_by());
		operatorMap.put(CommandOperator.INCREMENT_BY.getValue(), rtiMessages.rtiOperator_increment_by());
		operatorMap.put(CommandOperator.DECREMENT_BY_FIELD.getValue(), rtiMessages.rtiOperator_decrement_by_field());
		operatorMap.put(CommandOperator.INCREMENT_BY_FIELD.getValue(), rtiMessages.rtiOperator_increment_by_field());
		operatorMap.put(CommandOperator.SET_TO.getValue(), rtiMessages.rtiOperator_set_to());
		/*operatorMap.put(CommandOperator.SET_TO_DATE.getValue(), rtiMessages.rtiOperator_set_to_date());*/
		operatorMap.put(CommandOperator.SET_TO_FALSE.getValue(), rtiMessages.rtiOperator_set_to_false());
		operatorMap.put(CommandOperator.SET_TO_FIELD.getValue(), rtiMessages.rtiOperator_set_to_field());
		operatorMap.put(CommandOperator.SET_TO_NULL.getValue(), rtiMessages.rtiOperator_set_to_null());
		operatorMap.put(CommandOperator.SET_TO_TRUE.getValue(), rtiMessages.rtiOperator_set_to_true());
		operatorMap.put("Match Any", rtiMessages.rtiOperator_match_any());
		operatorMap.put("Match All", rtiMessages.rtiOperator_match_all());
		operatorMap.put("Match None", rtiMessages.rtiOperator_match_none());
		operatorMap.put("Operators", rtiMessages.rtiOperators());
	}
	
	public static String getOperator(String operator) {
		String operatorValue = operatorMap.get(operator);
		if(null != operatorValue) {
			return operatorValue;
		}
		return operator;
	}
}
