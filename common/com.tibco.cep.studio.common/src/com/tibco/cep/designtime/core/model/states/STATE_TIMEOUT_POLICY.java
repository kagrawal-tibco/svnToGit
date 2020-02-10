/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>STATE TIMEOUT POLICY</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.states.StatesPackage#getSTATE_TIMEOUT_POLICY()
 * @model
 * @generated
 */
public enum STATE_TIMEOUT_POLICY implements Enumerator {
	/**
	 * The '<em><b>Deterministic State Policy</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DETERMINISTIC_STATE_POLICY_VALUE
	 * @generated
	 * @ordered
	 */
	DETERMINISTIC_STATE_POLICY(2, "DeterministicStatePolicy", "DeterministicStatePolicy"),

	/**
	 * The '<em><b>Non Deterministic State Policy</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NON_DETERMINISTIC_STATE_POLICY_VALUE
	 * @generated
	 * @ordered
	 */
	NON_DETERMINISTIC_STATE_POLICY(1, "NonDeterministicStatePolicy", "NonDeterministicStatePolicy"), /**
	 * The '<em><b>No Action Timeout Policy</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NO_ACTION_TIMEOUT_POLICY_VALUE
	 * @generated
	 * @ordered
	 */
	NO_ACTION_TIMEOUT_POLICY(0, "NoActionTimeoutPolicy", "NoActionTimeoutPolicy");

	/**
	 * The '<em><b>Deterministic State Policy</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Deterministic State Policy</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DETERMINISTIC_STATE_POLICY
	 * @model name="DeterministicStatePolicy"
	 * @generated
	 * @ordered
	 */
	public static final int DETERMINISTIC_STATE_POLICY_VALUE = 2;

	/**
	 * The '<em><b>Non Deterministic State Policy</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Non Deterministic State Policy</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NON_DETERMINISTIC_STATE_POLICY
	 * @model name="NonDeterministicStatePolicy"
	 * @generated
	 * @ordered
	 */
	public static final int NON_DETERMINISTIC_STATE_POLICY_VALUE = 1;

	/**
	 * The '<em><b>No Action Timeout Policy</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>No Action Timeout Policy</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NO_ACTION_TIMEOUT_POLICY
	 * @model name="NoActionTimeoutPolicy"
	 * @generated
	 * @ordered
	 */
	public static final int NO_ACTION_TIMEOUT_POLICY_VALUE = 0;

	/**
	 * An array of all the '<em><b>STATE TIMEOUT POLICY</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final STATE_TIMEOUT_POLICY[] VALUES_ARRAY =
		new STATE_TIMEOUT_POLICY[] {
			DETERMINISTIC_STATE_POLICY,
			NON_DETERMINISTIC_STATE_POLICY,
			NO_ACTION_TIMEOUT_POLICY,
		};

	/**
	 * A public read-only list of all the '<em><b>STATE TIMEOUT POLICY</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<STATE_TIMEOUT_POLICY> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>STATE TIMEOUT POLICY</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static STATE_TIMEOUT_POLICY get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			STATE_TIMEOUT_POLICY result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>STATE TIMEOUT POLICY</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static STATE_TIMEOUT_POLICY getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			STATE_TIMEOUT_POLICY result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>STATE TIMEOUT POLICY</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static STATE_TIMEOUT_POLICY get(int value) {
		switch (value) {
			case DETERMINISTIC_STATE_POLICY_VALUE: return DETERMINISTIC_STATE_POLICY;
			case NON_DETERMINISTIC_STATE_POLICY_VALUE: return NON_DETERMINISTIC_STATE_POLICY;
			case NO_ACTION_TIMEOUT_POLICY_VALUE: return NO_ACTION_TIMEOUT_POLICY;
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
	private STATE_TIMEOUT_POLICY(int value, String name, String literal) {
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
	
} //STATE_TIMEOUT_POLICY
