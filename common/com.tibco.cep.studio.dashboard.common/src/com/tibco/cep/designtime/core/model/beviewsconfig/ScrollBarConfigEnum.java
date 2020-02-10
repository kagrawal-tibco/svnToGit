/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Scroll Bar Config Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 * 			Represents the enumeration of the values to be used as 'scrollbar'
 *             
 * <!-- end-model-doc -->
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getScrollBarConfigEnum()
 * @model extendedMetaData="name='ScrollBarConfigEnum'"
 * @generated
 */
public enum ScrollBarConfigEnum implements Enumerator {
	/**
	 * The '<em><b>Auto</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AUTO_VALUE
	 * @generated
	 * @ordered
	 */
	AUTO(0, "auto", "auto"),

	/**
	 * The '<em><b>Never</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NEVER_VALUE
	 * @generated
	 * @ordered
	 */
	NEVER(1, "never", "never"),

	/**
	 * The '<em><b>Yes</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #YES_VALUE
	 * @generated
	 * @ordered
	 */
	YES(2, "yes", "yes");

	/**
	 * The '<em><b>Auto</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Auto</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #AUTO
	 * @model name="auto"
	 * @generated
	 * @ordered
	 */
	public static final int AUTO_VALUE = 0;

	/**
	 * The '<em><b>Never</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Never</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NEVER
	 * @model name="never"
	 * @generated
	 * @ordered
	 */
	public static final int NEVER_VALUE = 1;

	/**
	 * The '<em><b>Yes</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Yes</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #YES
	 * @model name="yes"
	 * @generated
	 * @ordered
	 */
	public static final int YES_VALUE = 2;

	/**
	 * An array of all the '<em><b>Scroll Bar Config Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ScrollBarConfigEnum[] VALUES_ARRAY =
		new ScrollBarConfigEnum[] {
			AUTO,
			NEVER,
			YES,
		};

	/**
	 * A public read-only list of all the '<em><b>Scroll Bar Config Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ScrollBarConfigEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Scroll Bar Config Enum</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ScrollBarConfigEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ScrollBarConfigEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Scroll Bar Config Enum</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ScrollBarConfigEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ScrollBarConfigEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Scroll Bar Config Enum</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ScrollBarConfigEnum get(int value) {
		switch (value) {
			case AUTO_VALUE: return AUTO;
			case NEVER_VALUE: return NEVER;
			case YES_VALUE: return YES;
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
	private ScrollBarConfigEnum(int value, String name, String literal) {
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
	
} //ScrollBarConfigEnum
