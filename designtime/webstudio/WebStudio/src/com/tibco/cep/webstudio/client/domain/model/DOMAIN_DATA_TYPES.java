package com.tibco.cep.webstudio.client.domain.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public enum DOMAIN_DATA_TYPES {

	STRING(0, "String", "String"),

	INTEGER(1, "int", "int"),

	LONG(2, "long", "long"),

	DOUBLE(3, "double", "double"),

	BOOLEAN(4, "boolean", "boolean"),

	DATE_TIME(5, "DateTime", "DateTime");

	public static final int STRING_VALUE = 0;
	public static final int INTEGER_VALUE = 1;
	public static final int LONG_VALUE = 2;
	public static final int DOUBLE_VALUE = 3;
	public static final int BOOLEAN_VALUE = 4;
	public static final int DATE_TIME_VALUE = 5;

	private static final DOMAIN_DATA_TYPES[] VALUES_ARRAY =
		new DOMAIN_DATA_TYPES[] {
			STRING,
			INTEGER,
			LONG,
			DOUBLE,
			BOOLEAN,
			DATE_TIME
		};

	public static final List<DOMAIN_DATA_TYPES> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	public static DOMAIN_DATA_TYPES get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DOMAIN_DATA_TYPES result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static DOMAIN_DATA_TYPES getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DOMAIN_DATA_TYPES result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	public static DOMAIN_DATA_TYPES get(int value) {
		switch (value) {
			case STRING_VALUE: return STRING;
			case INTEGER_VALUE: return INTEGER;
			case LONG_VALUE: return LONG;
			case DOUBLE_VALUE: return DOUBLE;
			case BOOLEAN_VALUE: return BOOLEAN;
			case DATE_TIME_VALUE: return DATE_TIME;
		}
		return null;
	}

	private final int value;
	private final String name;
	private final String literal;

	private DOMAIN_DATA_TYPES(int value, String name, String literal) {
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
	
}
