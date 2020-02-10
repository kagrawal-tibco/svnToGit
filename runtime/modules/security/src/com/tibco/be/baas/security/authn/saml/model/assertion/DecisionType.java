/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Decision Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getDecisionType()
 * @model extendedMetaData="name='DecisionType'"
 * @generated
 */
public enum DecisionType implements Enumerator {
	/**
	 * The '<em><b>Permit</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PERMIT_VALUE
	 * @generated
	 * @ordered
	 */
	PERMIT(0, "Permit", "Permit"),

	/**
	 * The '<em><b>Deny</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DENY_VALUE
	 * @generated
	 * @ordered
	 */
	DENY(1, "Deny", "Deny"),

	/**
	 * The '<em><b>Indeterminate</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INDETERMINATE_VALUE
	 * @generated
	 * @ordered
	 */
	INDETERMINATE(2, "Indeterminate", "Indeterminate");

	/**
	 * The '<em><b>Permit</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Permit</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PERMIT
	 * @model name="Permit"
	 * @generated
	 * @ordered
	 */
	public static final int PERMIT_VALUE = 0;

	/**
	 * The '<em><b>Deny</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Deny</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DENY
	 * @model name="Deny"
	 * @generated
	 * @ordered
	 */
	public static final int DENY_VALUE = 1;

	/**
	 * The '<em><b>Indeterminate</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Indeterminate</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INDETERMINATE
	 * @model name="Indeterminate"
	 * @generated
	 * @ordered
	 */
	public static final int INDETERMINATE_VALUE = 2;

	/**
	 * An array of all the '<em><b>Decision Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final DecisionType[] VALUES_ARRAY =
		new DecisionType[] {
			PERMIT,
			DENY,
			INDETERMINATE,
		};

	/**
	 * A public read-only list of all the '<em><b>Decision Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<DecisionType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Decision Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DecisionType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DecisionType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Decision Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DecisionType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DecisionType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Decision Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DecisionType get(int value) {
		switch (value) {
			case PERMIT_VALUE: return PERMIT;
			case DENY_VALUE: return DENY;
			case INDETERMINATE_VALUE: return INDETERMINATE;
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
	private DecisionType(int value, String name, String literal) {
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
	
} //DecisionType
