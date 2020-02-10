/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Property Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getPropertyType()
 * @model
 * @generated
 */
public enum PropertyType implements Enumerator {
	/**
	 * The '<em><b>UNDEFINED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNDEFINED_VALUE
	 * @generated
	 * @ordered
	 */
	UNDEFINED(-1, "UNDEFINED", "UNDEFINED"),

	/**
	 * The '<em><b>PROPERTY TYPE REAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_REAL_VALUE
	 * @generated
	 * @ordered
	 */
	PROPERTY_TYPE_REAL(3, "PROPERTY_TYPE_REAL", "PROPERTY_TYPE_REAL"),

	/**
	 * The '<em><b>PROPERTY TYPE STRING</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_STRING_VALUE
	 * @generated
	 * @ordered
	 */
	PROPERTY_TYPE_STRING(0, "PROPERTY_TYPE_STRING", "PROPERTY_TYPE_STRING"),

	/**
	 * The '<em><b>PROPERTY TYPE INTEGER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_INTEGER_VALUE
	 * @generated
	 * @ordered
	 */
	PROPERTY_TYPE_INTEGER(1, "PROPERTY_TYPE_INTEGER", "PROPERTY_TYPE_INTEGER"),

	/**
	 * The '<em><b>PROPERTY TYPE BOOLEAN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_BOOLEAN_VALUE
	 * @generated
	 * @ordered
	 */
	PROPERTY_TYPE_BOOLEAN(4, "PROPERTY_TYPE_BOOLEAN", "PROPERTY_TYPE_BOOLEAN"),

	/**
	 * The '<em><b>PROPERTY TYPE LONG</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_LONG_VALUE
	 * @generated
	 * @ordered
	 */
	PROPERTY_TYPE_LONG(2, "PROPERTY_TYPE_LONG", "PROPERTY_TYPE_LONG"),

	/**
	 * The '<em><b>PROPERTY TYPE DATETIME</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_DATETIME_VALUE
	 * @generated
	 * @ordered
	 */
	PROPERTY_TYPE_DATETIME(5, "PROPERTY_TYPE_DATETIME", "PROPERTY_TYPE_DATETIME"),

	/**
	 * The '<em><b>PROPERTY TYPE CONCEPT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_CONCEPT_VALUE
	 * @generated
	 * @ordered
	 */
	PROPERTY_TYPE_CONCEPT(6, "PROPERTY_TYPE_CONCEPT", "PROPERTY_TYPE_CONCEPT"),

	/**
	 * The '<em><b>PROPERTY TYPE CONCEPTREFERENCE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_CONCEPTREFERENCE_VALUE
	 * @generated
	 * @ordered
	 */
	PROPERTY_TYPE_CONCEPTREFERENCE(7, "PROPERTY_TYPE_CONCEPTREFERENCE", "PROPERTY_TYPE_CONCEPTREFERENCE"), /**
	 * The '<em><b>PROPERTY TYPE TUPLE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_TUPLE_VALUE
	 * @generated
	 * @ordered
	 */
	PROPERTY_TYPE_TUPLE(8, "PROPERTY_TYPE_TUPLE", "PROPERTY_TYPE_TUPLE"), /**
	 * The '<em><b>PROPERTY TYPE BLOB</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_BLOB_VALUE
	 * @generated
	 * @ordered
	 */
	PROPERTY_TYPE_BLOB(9, "PROPERTY_TYPE_BLOB", "PROPERTY_TYPE_BLOB"), /**
	 * The '<em><b>PROPERTY TYPE LIST</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_LIST_VALUE
	 * @generated
	 * @ordered
	 */
	PROPERTY_TYPE_LIST(10, "PROPERTY_TYPE_LIST", "PROPERTY_TYPE_LIST");

	/**
	 * The '<em><b>UNDEFINED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>UNDEFINED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UNDEFINED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int UNDEFINED_VALUE = -1;

	/**
	 * The '<em><b>PROPERTY TYPE REAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROPERTY TYPE REAL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_REAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTY_TYPE_REAL_VALUE = 3;

	/**
	 * The '<em><b>PROPERTY TYPE STRING</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROPERTY TYPE STRING</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_STRING
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTY_TYPE_STRING_VALUE = 0;

	/**
	 * The '<em><b>PROPERTY TYPE INTEGER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROPERTY TYPE INTEGER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_INTEGER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTY_TYPE_INTEGER_VALUE = 1;

	/**
	 * The '<em><b>PROPERTY TYPE BOOLEAN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROPERTY TYPE BOOLEAN</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_BOOLEAN
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTY_TYPE_BOOLEAN_VALUE = 4;

	/**
	 * The '<em><b>PROPERTY TYPE LONG</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROPERTY TYPE LONG</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_LONG
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTY_TYPE_LONG_VALUE = 2;

	/**
	 * The '<em><b>PROPERTY TYPE DATETIME</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROPERTY TYPE DATETIME</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_DATETIME
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTY_TYPE_DATETIME_VALUE = 5;

	/**
	 * The '<em><b>PROPERTY TYPE CONCEPT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROPERTY TYPE CONCEPT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_CONCEPT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTY_TYPE_CONCEPT_VALUE = 6;

	/**
	 * The '<em><b>PROPERTY TYPE CONCEPTREFERENCE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROPERTY TYPE CONCEPTREFERENCE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_CONCEPTREFERENCE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTY_TYPE_CONCEPTREFERENCE_VALUE = 7;

	/**
	 * The '<em><b>PROPERTY TYPE TUPLE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROPERTY TYPE TUPLE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_TUPLE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTY_TYPE_TUPLE_VALUE = 8;

	/**
	 * The '<em><b>PROPERTY TYPE BLOB</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROPERTY TYPE BLOB</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_BLOB
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTY_TYPE_BLOB_VALUE = 9;

	/**
	 * The '<em><b>PROPERTY TYPE LIST</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROPERTY TYPE LIST</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROPERTY_TYPE_LIST
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTY_TYPE_LIST_VALUE = 10;

	/**
	 * An array of all the '<em><b>Property Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PropertyType[] VALUES_ARRAY =
		new PropertyType[] {
			UNDEFINED,
			PROPERTY_TYPE_REAL,
			PROPERTY_TYPE_STRING,
			PROPERTY_TYPE_INTEGER,
			PROPERTY_TYPE_BOOLEAN,
			PROPERTY_TYPE_LONG,
			PROPERTY_TYPE_DATETIME,
			PROPERTY_TYPE_CONCEPT,
			PROPERTY_TYPE_CONCEPTREFERENCE,
			PROPERTY_TYPE_TUPLE,
			PROPERTY_TYPE_BLOB,
			PROPERTY_TYPE_LIST,
		};

	/**
	 * A public read-only list of all the '<em><b>Property Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<PropertyType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Property Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PropertyType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PropertyType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Property Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PropertyType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PropertyType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Property Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PropertyType get(int value) {
		switch (value) {
			case UNDEFINED_VALUE: return UNDEFINED;
			case PROPERTY_TYPE_REAL_VALUE: return PROPERTY_TYPE_REAL;
			case PROPERTY_TYPE_STRING_VALUE: return PROPERTY_TYPE_STRING;
			case PROPERTY_TYPE_INTEGER_VALUE: return PROPERTY_TYPE_INTEGER;
			case PROPERTY_TYPE_BOOLEAN_VALUE: return PROPERTY_TYPE_BOOLEAN;
			case PROPERTY_TYPE_LONG_VALUE: return PROPERTY_TYPE_LONG;
			case PROPERTY_TYPE_DATETIME_VALUE: return PROPERTY_TYPE_DATETIME;
			case PROPERTY_TYPE_CONCEPT_VALUE: return PROPERTY_TYPE_CONCEPT;
			case PROPERTY_TYPE_CONCEPTREFERENCE_VALUE: return PROPERTY_TYPE_CONCEPTREFERENCE;
			case PROPERTY_TYPE_TUPLE_VALUE: return PROPERTY_TYPE_TUPLE;
			case PROPERTY_TYPE_BLOB_VALUE: return PROPERTY_TYPE_BLOB;
			case PROPERTY_TYPE_LIST_VALUE: return PROPERTY_TYPE_LIST;
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
	private PropertyType(int value, String name, String literal) {
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
	
} //PropertyType
