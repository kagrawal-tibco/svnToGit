package com.tibco.cep.webstudio.client.decisiontable.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author sasahoo
 *
 */
public enum ColumnType {

	UNDEFINED(0, "UNDEFINED", "UNDEFINED"),

	CONDITION(1, "CONDITION", "CONDITION"),

	ACTION(2, "ACTION", "ACTION"), 

	CUSTOM_CONDITION(3, "CUSTOM_CONDITION", "CUSTOM_CONDITION"),
	
	CUSTOM_ACTION(4, "CUSTOM_ACTION", "CUSTOM_ACTION");

	public static final int UNDEFINED_VALUE = 0;

	public static final int CONDITION_VALUE = 1;

	public static final int ACTION_VALUE = 2;

	public static final int CUSTOM_CONDITION_VALUE = 3;

	public static final int CUSTOM_ACTION_VALUE = 4;

	private static final ColumnType[] VALUES_ARRAY =
		new ColumnType[] {
			UNDEFINED,
			CONDITION,
			ACTION,
			CUSTOM_CONDITION,
			CUSTOM_ACTION,
		};

	public static final List<ColumnType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	public static ColumnType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ColumnType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static ColumnType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ColumnType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	public static ColumnType get(int value) {
		switch (value) {
			case UNDEFINED_VALUE: return UNDEFINED;
			case CONDITION_VALUE: return CONDITION;
			case ACTION_VALUE: return ACTION;
			case CUSTOM_CONDITION_VALUE: return CUSTOM_CONDITION;
			case CUSTOM_ACTION_VALUE: return CUSTOM_ACTION;
		}
		return null;
	}

	private final int value;

	private final String name;

	private final String literal;

	private ColumnType(int value, String name, String literal) {
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
	  return literal;
	}

	@Override
	public String toString() {
		return literal;
	}
	
	public boolean isCustom() {
		return value == CUSTOM_ACTION_VALUE || value == CUSTOM_CONDITION_VALUE;
	}
	
	public boolean isConditon() {
		return equals(CONDITION) || equals(CUSTOM_CONDITION); 
	}
	
	public boolean isAction() {
		return equals(ACTION) || equals(CUSTOM_ACTION);
	}
	
	public int massagedOrdinal() {
		int ordinal = ordinal();
		if ((ordinal % 2) == 0) {
			ordinal = (2 ^ (ordinal >> ordinal));
		} else {
			ordinal = (1 ^ (ordinal >> ordinal));
		}
		return ordinal;
	}
	
} 
