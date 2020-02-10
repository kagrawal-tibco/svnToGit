/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>PROPERTY TYPES</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.ModelPackage#getPROPERTY_TYPES()
 * @model
 * @generated
 */
public enum PROPERTY_TYPES implements Enumerator {
	/**
	 * The '<em><b>String</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STRING_VALUE
	 * @generated
	 * @ordered
	 */
	STRING(0, "String", "String"),

	/**
	 * The '<em><b>Integer</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INTEGER_VALUE
	 * @generated NOT
	 * @ordered
	 */
	INTEGER(1, "int", "int"),

	/**
	 * The '<em><b>Long</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LONG_VALUE
	 * @generated NOT
	 * @ordered
	 */
	LONG(2, "long", "long"),

	/**
	 * The '<em><b>Double</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DOUBLE_VALUE
	 * @generated NOT
	 * @ordered
	 */
	DOUBLE(3, "double", "double"), /**
	 * The '<em><b>Boolean</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BOOLEAN_VALUE
	 * @generated NOT
	 * @ordered
	 */
	BOOLEAN(4, "boolean", "boolean"),

	/**
	 * The '<em><b>Date Time</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DATE_TIME_VALUE
	 * @generated
	 * @ordered
	 */
	DATE_TIME(5, "DateTime", "DateTime"),

	/**
	 * The '<em><b>Concept</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONCEPT_VALUE
	 * @generated NOT
	 * @ordered
	 */
	CONCEPT(6, "ContainedConcept", "ContainedConcept"),

	/**
	 * The '<em><b>Concept Reference</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONCEPT_REFERENCE_VALUE
	 * @generated
	 * @ordered
	 */
	CONCEPT_REFERENCE(7, "ConceptReference", "ConceptReference");

	/**
	 * The '<em><b>String</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>String</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STRING
	 * @model name="String"
	 * @generated
	 * @ordered
	 */
	public static final int STRING_VALUE = 0;

	/**
	 * The '<em><b>Integer</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Integer</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INTEGER
	 * @model name="Integer" literal="int"
	 * @generated
	 * @ordered
	 */
	public static final int INTEGER_VALUE = 1;

	/**
	 * The '<em><b>Long</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Long</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LONG
	 * @model name="Long" literal="long"
	 * @generated
	 * @ordered
	 */
	public static final int LONG_VALUE = 2;

	/**
	 * The '<em><b>Double</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Double</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DOUBLE
	 * @model name="Double" literal="double"
	 * @generated
	 * @ordered
	 */
	public static final int DOUBLE_VALUE = 3;

	/**
	 * The '<em><b>Boolean</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Boolean</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BOOLEAN
	 * @model name="Boolean" literal="boolean"
	 * @generated
	 * @ordered
	 */
	public static final int BOOLEAN_VALUE = 4;

	/**
	 * The '<em><b>Date Time</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Date Time</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DATE_TIME
	 * @model name="DateTime"
	 * @generated
	 * @ordered
	 */
	public static final int DATE_TIME_VALUE = 5;

	/**
	 * The '<em><b>Concept</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Concept</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONCEPT
	 * @model name="Concept"
	 * @generated
	 * @ordered
	 */
	public static final int CONCEPT_VALUE = 6;

	/**
	 * The '<em><b>Concept Reference</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Concept Reference</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONCEPT_REFERENCE
	 * @model name="ConceptReference"
	 * @generated
	 * @ordered
	 */
	public static final int CONCEPT_REFERENCE_VALUE = 7;

	/**
	 * An array of all the '<em><b>PROPERTY TYPES</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
		};

	/**
	 * A public read-only list of all the '<em><b>PROPERTY TYPES</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<PROPERTY_TYPES> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>PROPERTY TYPES</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public static PROPERTY_TYPES get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PROPERTY_TYPES result = VALUES_ARRAY[i];
			if (result.toString().equalsIgnoreCase(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>PROPERTY TYPES</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PROPERTY_TYPES getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PROPERTY_TYPES result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>PROPERTY TYPES</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private PROPERTY_TYPES(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //PROPERTY_TYPES
