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
 * A representation of the literals of the enumeration '<em><b>Column Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getColumnType()
 * @model
 * @generated
 */
public enum ColumnType implements Enumerator {
	/**
	 * The '<em><b>UNDEFINED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNDEFINED_VALUE
	 * @generated
	 * @ordered
	 */
	UNDEFINED(0, "UNDEFINED", "UNDEFINED"),

	/**
	 * The '<em><b>CONDITION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONDITION_VALUE
	 * @generated
	 * @ordered
	 */
	CONDITION(1, "CONDITION", "CONDITION"),

	/**
	 * The '<em><b>ACTION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ACTION_VALUE
	 * @generated
	 * @ordered
	 */
	ACTION(2, "ACTION", "ACTION"), /**
	 * The '<em><b>CUSTOM CONDITION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CUSTOM_CONDITION_VALUE
	 * @generated
	 * @ordered
	 */
	CUSTOM_CONDITION(3, "CUSTOM_CONDITION", "CUSTOM_CONDITION"), /**
	 * The '<em><b>CUSTOM ACTION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CUSTOM_ACTION_VALUE
	 * @generated
	 * @ordered
	 */
	CUSTOM_ACTION(4, "CUSTOM_ACTION", "CUSTOM_ACTION");

	/**
	 * The '<em><b>UNDEFINED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Undefined</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UNDEFINED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int UNDEFINED_VALUE = 0;

	/**
	 * The '<em><b>CONDITION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Condition</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONDITION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CONDITION_VALUE = 1;

	/**
	 * The '<em><b>ACTION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Action</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ACTION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ACTION_VALUE = 2;

	/**
	 * The '<em><b>CUSTOM CONDITION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Custom Condition</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CUSTOM_CONDITION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CUSTOM_CONDITION_VALUE = 3;

	/**
	 * The '<em><b>CUSTOM ACTION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Custom Action</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CUSTOM_ACTION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CUSTOM_ACTION_VALUE = 4;

	/**
	 * An array of all the '<em><b>Column Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ColumnType[] VALUES_ARRAY =
		new ColumnType[] {
			UNDEFINED,
			CONDITION,
			ACTION,
			CUSTOM_CONDITION,
			CUSTOM_ACTION,
		};

	/**
	 * A public read-only list of all the '<em><b>Column Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ColumnType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Column Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ColumnType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ColumnType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Column Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ColumnType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ColumnType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Column Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
	private ColumnType(int value, String name, String literal) {
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
	
} //ColumnType
