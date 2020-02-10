/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>CONFIG METHOD</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getCONFIG_METHOD()
 * @model
 * @generated
 */
public enum CONFIG_METHOD implements Enumerator {
	/**
	 * The '<em><b>PROPERTIES</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROPERTIES_VALUE
	 * @generated
	 * @ordered
	 */
	PROPERTIES(0, "PROPERTIES", "PROPERTIES"),

	/**
	 * The '<em><b>REFERENCE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REFERENCE_VALUE
	 * @generated
	 * @ordered
	 */
	REFERENCE(1, "REFERENCE", "REFERENCE");

	/**
	 * The '<em><b>PROPERTIES</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROPERTIES</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROPERTIES
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROPERTIES_VALUE = 0;

	/**
	 * The '<em><b>REFERENCE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>REFERENCE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REFERENCE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int REFERENCE_VALUE = 1;

	/**
	 * An array of all the '<em><b>CONFIG METHOD</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final CONFIG_METHOD[] VALUES_ARRAY =
		new CONFIG_METHOD[] {
			PROPERTIES,
			REFERENCE,
		};

	/**
	 * A public read-only list of all the '<em><b>CONFIG METHOD</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<CONFIG_METHOD> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>CONFIG METHOD</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CONFIG_METHOD get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CONFIG_METHOD result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>CONFIG METHOD</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CONFIG_METHOD getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CONFIG_METHOD result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>CONFIG METHOD</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CONFIG_METHOD get(int value) {
		switch (value) {
			case PROPERTIES_VALUE: return PROPERTIES;
			case REFERENCE_VALUE: return REFERENCE;
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
	private CONFIG_METHOD(int value, String name, String literal) {
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
	
} //CONFIG_METHOD
