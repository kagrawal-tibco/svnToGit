/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Authn Context Comparison Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthnContextComparisonType()
 * @model extendedMetaData="name='AuthnContextComparisonType'"
 * @generated
 */
public enum AuthnContextComparisonType implements Enumerator {
	/**
	 * The '<em><b>Exact</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXACT_VALUE
	 * @generated
	 * @ordered
	 */
	EXACT(0, "exact", "exact"),

	/**
	 * The '<em><b>Minimum</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MINIMUM_VALUE
	 * @generated
	 * @ordered
	 */
	MINIMUM(1, "minimum", "minimum"),

	/**
	 * The '<em><b>Maximum</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MAXIMUM_VALUE
	 * @generated
	 * @ordered
	 */
	MAXIMUM(2, "maximum", "maximum"),

	/**
	 * The '<em><b>Better</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BETTER_VALUE
	 * @generated
	 * @ordered
	 */
	BETTER(3, "better", "better");

	/**
	 * The '<em><b>Exact</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Exact</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EXACT
	 * @model name="exact"
	 * @generated
	 * @ordered
	 */
	public static final int EXACT_VALUE = 0;

	/**
	 * The '<em><b>Minimum</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Minimum</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MINIMUM
	 * @model name="minimum"
	 * @generated
	 * @ordered
	 */
	public static final int MINIMUM_VALUE = 1;

	/**
	 * The '<em><b>Maximum</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Maximum</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MAXIMUM
	 * @model name="maximum"
	 * @generated
	 * @ordered
	 */
	public static final int MAXIMUM_VALUE = 2;

	/**
	 * The '<em><b>Better</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Better</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BETTER
	 * @model name="better"
	 * @generated
	 * @ordered
	 */
	public static final int BETTER_VALUE = 3;

	/**
	 * An array of all the '<em><b>Authn Context Comparison Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final AuthnContextComparisonType[] VALUES_ARRAY =
		new AuthnContextComparisonType[] {
			EXACT,
			MINIMUM,
			MAXIMUM,
			BETTER,
		};

	/**
	 * A public read-only list of all the '<em><b>Authn Context Comparison Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<AuthnContextComparisonType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Authn Context Comparison Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AuthnContextComparisonType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AuthnContextComparisonType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Authn Context Comparison Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AuthnContextComparisonType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AuthnContextComparisonType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Authn Context Comparison Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AuthnContextComparisonType get(int value) {
		switch (value) {
			case EXACT_VALUE: return EXACT;
			case MINIMUM_VALUE: return MINIMUM;
			case MAXIMUM_VALUE: return MAXIMUM;
			case BETTER_VALUE: return BETTER;
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
	private AuthnContextComparisonType(int value, String name, String literal) {
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
	
} //AuthnContextComparisonType
