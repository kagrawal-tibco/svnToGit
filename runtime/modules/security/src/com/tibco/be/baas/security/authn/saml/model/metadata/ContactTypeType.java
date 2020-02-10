/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Contact Type Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getContactTypeType()
 * @model extendedMetaData="name='ContactTypeType'"
 * @generated
 */
public enum ContactTypeType implements Enumerator {
	/**
	 * The '<em><b>Technical</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TECHNICAL_VALUE
	 * @generated
	 * @ordered
	 */
	TECHNICAL(0, "technical", "technical"),

	/**
	 * The '<em><b>Support</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SUPPORT_VALUE
	 * @generated
	 * @ordered
	 */
	SUPPORT(1, "support", "support"),

	/**
	 * The '<em><b>Administrative</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ADMINISTRATIVE_VALUE
	 * @generated
	 * @ordered
	 */
	ADMINISTRATIVE(2, "administrative", "administrative"),

	/**
	 * The '<em><b>Billing</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BILLING_VALUE
	 * @generated
	 * @ordered
	 */
	BILLING(3, "billing", "billing"),

	/**
	 * The '<em><b>Other</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OTHER_VALUE
	 * @generated
	 * @ordered
	 */
	OTHER(4, "other", "other");

	/**
	 * The '<em><b>Technical</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Technical</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TECHNICAL
	 * @model name="technical"
	 * @generated
	 * @ordered
	 */
	public static final int TECHNICAL_VALUE = 0;

	/**
	 * The '<em><b>Support</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Support</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SUPPORT
	 * @model name="support"
	 * @generated
	 * @ordered
	 */
	public static final int SUPPORT_VALUE = 1;

	/**
	 * The '<em><b>Administrative</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Administrative</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ADMINISTRATIVE
	 * @model name="administrative"
	 * @generated
	 * @ordered
	 */
	public static final int ADMINISTRATIVE_VALUE = 2;

	/**
	 * The '<em><b>Billing</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Billing</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BILLING
	 * @model name="billing"
	 * @generated
	 * @ordered
	 */
	public static final int BILLING_VALUE = 3;

	/**
	 * The '<em><b>Other</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Other</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OTHER
	 * @model name="other"
	 * @generated
	 * @ordered
	 */
	public static final int OTHER_VALUE = 4;

	/**
	 * An array of all the '<em><b>Contact Type Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ContactTypeType[] VALUES_ARRAY =
		new ContactTypeType[] {
			TECHNICAL,
			SUPPORT,
			ADMINISTRATIVE,
			BILLING,
			OTHER,
		};

	/**
	 * A public read-only list of all the '<em><b>Contact Type Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ContactTypeType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Contact Type Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ContactTypeType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ContactTypeType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Contact Type Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ContactTypeType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ContactTypeType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Contact Type Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ContactTypeType get(int value) {
		switch (value) {
			case TECHNICAL_VALUE: return TECHNICAL;
			case SUPPORT_VALUE: return SUPPORT;
			case ADMINISTRATIVE_VALUE: return ADMINISTRATIVE;
			case BILLING_VALUE: return BILLING;
			case OTHER_VALUE: return OTHER;
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
	private ContactTypeType(int value, String name, String literal) {
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
	
} //ContactTypeType
