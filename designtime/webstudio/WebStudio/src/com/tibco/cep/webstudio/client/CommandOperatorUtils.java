package com.tibco.cep.webstudio.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.types.FieldType;
import com.tibco.cep.webstudio.client.preferences.ApplicationPreferenceHelper;
import com.tibco.cep.webstudio.model.rule.instance.operators.CommandOperator;
import com.tibco.cep.webstudio.model.rule.instance.operators.IBuilderOperator;

public class CommandOperatorUtils {

	private static Map<FieldType, List<IBuilderOperator>> TYPE_TO_OPERATORS;
	
	public static List<IBuilderOperator> getOperators(FieldType type) {
		if (type == FieldType.CUSTOM) {
	            type = null;
	    }
		List<IBuilderOperator> operators = ApplicationPreferenceHelper.getInstance().getOperators(type, ApplicationPreferenceHelper.COMMAND_OPERATOR);
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
		CommandOperator[] values = CommandOperator.values();
		for (CommandOperator CommandOperator : values) {
			if (isTypeValidFor(fieldType, CommandOperator)) {
				operators.add(CommandOperator);
			}
		}
		return operators;
	}

	private static boolean isTypeValidFor(FieldType fieldType,
			CommandOperator commandOperator) {
		if (fieldType == null) {
			// for 'Concept' and 'Event' types
			return commandOperator == CommandOperator.SET_TO
			|| commandOperator == CommandOperator.SET_TO_FIELD
			|| commandOperator == CommandOperator.SET_TO_NULL;
		}
		switch (fieldType) {
		case ANY:
			return true;
			
		case BINARY:
		case BOOLEAN:
			return commandOperator == CommandOperator.SET_TO_TRUE
			|| commandOperator == CommandOperator.SET_TO_FALSE
			|| commandOperator == CommandOperator.SET_TO_FIELD;
			
		case DATE:
		case TIME:
		case DATETIME:
			return commandOperator == CommandOperator.SET_TO
			|| commandOperator == CommandOperator.SET_TO_FIELD
			|| commandOperator == CommandOperator.SET_TO_NULL;
			/*|| commandOperator == CommandOperator.SET_TO_DATE;*/
			
		case FLOAT:
		case INTEGER:
			return commandOperator == CommandOperator.SET_TO
			|| commandOperator == CommandOperator.INCREMENT_BY
			|| commandOperator == CommandOperator.DECREMENT_BY
			|| commandOperator == CommandOperator.SET_TO_FIELD
			|| commandOperator == CommandOperator.INCREMENT_BY_FIELD
			|| commandOperator == CommandOperator.DECREMENT_BY_FIELD;

		case PASSWORD:
		case TEXT:
			return commandOperator == CommandOperator.SET_TO
			|| commandOperator == CommandOperator.SET_TO_FIELD
			|| commandOperator == CommandOperator.SET_TO_NULL;
			
		default:
			break;
		}
		return false;
	}
	
}
