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
 * A representation of the literals of the enumeration '<em><b>HISTORY POLICY</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.ModelPackage#getHISTORY_POLICY()
 * @model
 * @generated
 */
public enum HISTORY_POLICY implements Enumerator {
	/**
	 * The '<em><b>Changes Only</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHANGES_ONLY_VALUE
	 * @generated
	 * @ordered
	 */
	CHANGES_ONLY(0, "ChangesOnly", "ChangesOnly"),

	/**
	 * The '<em><b>All Values</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ALL_VALUES_VALUE
	 * @generated
	 * @ordered
	 */
	ALL_VALUES(1, "AllValues", "AllValues");

	/**
	 * The '<em><b>Changes Only</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Changes Only</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHANGES_ONLY
	 * @model name="ChangesOnly"
	 * @generated
	 * @ordered
	 */
	public static final int CHANGES_ONLY_VALUE = 0;

	/**
	 * The '<em><b>All Values</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>All Values</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ALL_VALUES
	 * @model name="AllValues"
	 * @generated
	 * @ordered
	 */
	public static final int ALL_VALUES_VALUE = 1;

	/**
	 * An array of all the '<em><b>HISTORY POLICY</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final HISTORY_POLICY[] VALUES_ARRAY =
		new HISTORY_POLICY[] {
			CHANGES_ONLY,
			ALL_VALUES,
		};

	/**
	 * A public read-only list of all the '<em><b>HISTORY POLICY</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<HISTORY_POLICY> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>HISTORY POLICY</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static HISTORY_POLICY get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			HISTORY_POLICY result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>HISTORY POLICY</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static HISTORY_POLICY getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			HISTORY_POLICY result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>HISTORY POLICY</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static HISTORY_POLICY get(int value) {
		switch (value) {
			case CHANGES_ONLY_VALUE: return CHANGES_ONLY;
			case ALL_VALUES_VALUE: return ALL_VALUES;
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
	private HISTORY_POLICY(int value, String name, String literal) {
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
	
} //HISTORY_POLICY
