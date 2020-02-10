package com.tibco.cep.webstudio.client.decisiontable.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author sasahoo
 */
public enum PROPERTY_TYPES {
	
	STRING(0, "String", "String"),

	INTEGER(1, "int", "int"),

	LONG(2, "long", "long"),

	DOUBLE(3, "double", "double"), 
	
	BOOLEAN(4, "boolean", "boolean"),

	DATE_TIME(5, "DateTime", "DateTime"),
	
	CONCEPT(6, "ContainedConcept", "ContainedConcept"),
	
	CONCEPT_REFERENCE(7, "ConceptReference", "ConceptReference"),
	
	CONCEPT_ENTITY(8, "Concept", "Concept"),
	
	EVENT_ENTITY(9, "Event", "Event");

	public static final int STRING_VALUE = 0;

	public static final int INTEGER_VALUE = 1;

	public static final int LONG_VALUE = 2;

	public static final int DOUBLE_VALUE = 3;

	public static final int BOOLEAN_VALUE = 4;

	public static final int DATE_TIME_VALUE = 5;

	public static final int CONCEPT_VALUE = 6;

	public static final int CONCEPT_REFERENCE_VALUE = 7;
	
	public static final int CONCEPT_ENTITY_VALUE = 8;
	
	public static final int EVENT_ENTITY_VALUE = 9;

	private static final PROPERTY_TYPES[] VALUES_ARRAY =
		new PROPERTY_TYPES[] {
			STRING,
			INTEGER,
			LONG,
			DOUBLE,
			BOOLEAN,
			DATE_TIME,
			CONCEPT,
			CONCEPT_REFERENCE, 
			CONCEPT_ENTITY, 
			EVENT_ENTITY
		};

	public static final List<PROPERTY_TYPES> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	public static PROPERTY_TYPES get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PROPERTY_TYPES result = VALUES_ARRAY[i];
			if (result.toString().equalsIgnoreCase(literal)) {
				return result;
			}
		}
		return null;
	}

	public static PROPERTY_TYPES getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PROPERTY_TYPES result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	public static PROPERTY_TYPES get(int value) {
		switch (value) {
			case STRING_VALUE: return STRING;
			case INTEGER_VALUE: return INTEGER;
			case LONG_VALUE: return LONG;
			case DOUBLE_VALUE: return DOUBLE;
			case BOOLEAN_VALUE: return BOOLEAN;
			case DATE_TIME_VALUE: return DATE_TIME;
			case CONCEPT_VALUE: return CONCEPT;
			case CONCEPT_REFERENCE_VALUE: return CONCEPT_REFERENCE;
			case CONCEPT_ENTITY_VALUE: return CONCEPT_ENTITY;
			case EVENT_ENTITY_VALUE: return EVENT_ENTITY;
		}
		return null;
	}

	private final int value;

	private final String name;

	private final String literal;

	private PROPERTY_TYPES(int value, String name, String literal) {
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
