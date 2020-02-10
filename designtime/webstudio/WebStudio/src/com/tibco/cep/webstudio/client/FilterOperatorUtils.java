package com.tibco.cep.webstudio.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.types.FieldType;
import com.tibco.cep.webstudio.client.preferences.ApplicationPreferenceHelper;
import com.tibco.cep.webstudio.model.rule.instance.operators.FilterOperator;
import com.tibco.cep.webstudio.model.rule.instance.operators.IBuilderOperator;

public class FilterOperatorUtils {

	private static Map<FieldType, List<IBuilderOperator>> TYPE_TO_OPERATORS;
	
	public static List<IBuilderOperator> getOperators(FieldType type) {
		if (type == FieldType.CUSTOM) {
			type = null;
		}
		List<IBuilderOperator> operators = ApplicationPreferenceHelper.getInstance().getOperators(type, ApplicationPreferenceHelper.FILTER_OPERATOR);
		if(operators != null && operators.size() >0) {
			return operators;
		}
		return getTypeToOperators().get(type);
	}
	
	public static List<IBuilderOperator> getDefaultOperators(FieldType fieldType) {
		return getTypeToOperators().get(fieldType);
	}

	private synchronized static Map<FieldType, List<IBuilderOperator>> getTypeToOperators() {
		if (TYPE_TO_OPERATORS == null) {
			TYPE_TO_OPERATORS = new HashMap<FieldType, List<IBuilderOperator>>();
			initializeMap(TYPE_TO_OPERATORS);
		}
		return TYPE_TO_OPERATORS;
	}

	private static void initializeMap(
			Map<FieldType, List<IBuilderOperator>> map) {
		FieldType[] values = FieldType.values();
		for (FieldType fieldType : values) {
			map.put(fieldType, getOperatorsForType(fieldType));
		}
		map.put(null, getOperatorsForType(null));
	}

	public static List<IBuilderOperator> getOperatorsForType(FieldType fieldType) {
		List<IBuilderOperator> operators = new ArrayList<IBuilderOperator>();
		FilterOperator[] values = FilterOperator.values();
		for (FilterOperator filterOperator : values) {
			if (isTypeValidFor(fieldType, filterOperator)) {
				operators.add(filterOperator);
			}
		}
		return operators;
	}

	private static boolean isTypeValidFor(FieldType fieldType,
			FilterOperator filterOperator) {
		if (fieldType == null) {
			// for 'Concept' and 'Event' types
			return filterOperator == FilterOperator.DIFFERS_FROM_FIELD
			|| filterOperator == FilterOperator.EQUALS
			|| filterOperator == FilterOperator.IS_NOT_NULL
			|| filterOperator == FilterOperator.IS_NULL
			|| filterOperator == FilterOperator.MATCHES_OTHER_FIELD
			|| filterOperator == FilterOperator.NOT_EQUALS;
		}
		switch (fieldType) {
		case ANY:
			return true;
			
		case BINARY:
			return false;
			
		case BOOLEAN:
			return filterOperator == FilterOperator.DIFFERS_FROM_FIELD
			|| filterOperator == FilterOperator.MATCHES_OTHER_FIELD
			|| filterOperator == FilterOperator.EQUALS_TRUE
			|| filterOperator == FilterOperator.EQUALS_FALSE;
			
		case DATE:
		case TIME:
		case DATETIME:
			return 
//			filterOperator == FilterOperator.BETWEEN
//			|| filterOperator == FilterOperator.BETWEEN_INCLUSIVE
//			|| filterOperator == FilterOperator.DIFFERS_FROM_FIELD
			filterOperator == FilterOperator.EQUALS
			|| filterOperator == FilterOperator.IS_NOT_NULL
			|| filterOperator == FilterOperator.IS_NULL
			|| filterOperator == FilterOperator.MATCHES_OTHER_FIELD
			|| filterOperator == FilterOperator.NOT_EQUALS
			|| filterOperator == FilterOperator.GREATER_THAN
			|| filterOperator == FilterOperator.GREATER_THAN_FIELD
			|| filterOperator == FilterOperator.GREATER_THAN_OR_EQUAL_TO
			|| filterOperator == FilterOperator.GREATER_THAN_OR_EQUAL_TO_FIELD
			|| filterOperator == FilterOperator.LESS_THAN
			|| filterOperator == FilterOperator.LESS_THAN_FIELD
			|| filterOperator == FilterOperator.LESS_THAN_OR_EQUAL_TO
			|| filterOperator == FilterOperator.LESS_THAN_OR_EQUAL_TO_FIELD;
			
		case FLOAT:
		case INTEGER:
			return 
//			filterOperator == FilterOperator.BETWEEN
//			|| filterOperator == FilterOperator.BETWEEN_INCLUSIVE
			filterOperator == FilterOperator.DIFFERS_FROM_FIELD
			|| filterOperator == FilterOperator.EQUALS
			|| filterOperator == FilterOperator.NOT_EQUALS
//			|| filterOperator == FilterOperator.IS_NOT_NULL
//			|| filterOperator == FilterOperator.IS_NULL
			|| filterOperator == FilterOperator.MATCHES_OTHER_FIELD
			|| filterOperator == FilterOperator.GREATER_THAN
			|| filterOperator == FilterOperator.GREATER_THAN_FIELD
			|| filterOperator == FilterOperator.GREATER_THAN_OR_EQUAL_TO
			|| filterOperator == FilterOperator.GREATER_THAN_OR_EQUAL_TO_FIELD
			|| filterOperator == FilterOperator.LESS_THAN
			|| filterOperator == FilterOperator.LESS_THAN_FIELD
			|| filterOperator == FilterOperator.LESS_THAN_OR_EQUAL_TO
			|| filterOperator == FilterOperator.LESS_THAN_OR_EQUAL_TO_FIELD;

		case PASSWORD:
		case TEXT:
			return 
			filterOperator == FilterOperator.CONTAINS
			|| filterOperator == FilterOperator.DIFFERS_FROM_FIELD
//			|| filterOperator == FilterOperator.DOES_NOT_CONTAIN
			|| filterOperator == FilterOperator.ENDS_WITH
			|| filterOperator == FilterOperator.EQUALS
//			|| filterOperator == FilterOperator.EQUALS_IGNORE_CASE
			|| filterOperator == FilterOperator.IS_NOT_NULL
			|| filterOperator == FilterOperator.IS_NULL
			|| filterOperator == FilterOperator.MATCHES_OTHER_FIELD
			|| filterOperator == FilterOperator.NOT_EQUALS
//			|| filterOperator == FilterOperator.NOT_EQUALS_IGNORE_CASE
			|| filterOperator == FilterOperator.STARTS_WITH;
			
		default:
			break;
		}
		return false;
	}
	
}
