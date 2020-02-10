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
 * A representation of the literals of the enumeration '<em><b>Display Mode Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 * 			Represents the enumeration of the values to be used as 'displayMode'
 *             
 * <!-- end-model-doc -->
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDisplayModeEnum()
 * @model extendedMetaData="name='DisplayModeEnum'"
 * @generated
 */
public enum DisplayModeEnum implements Enumerator {
	/**
	 * The '<em><b>Inplace</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INPLACE_VALUE
	 * @generated
	 * @ordered
	 */
	INPLACE(0, "inplace", "inplace"),

	/**
	 * The '<em><b>Modaldialog</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODALDIALOG_VALUE
	 * @generated
	 * @ordered
	 */
	MODALDIALOG(1, "modaldialog", "modaldialog"),

	/**
	 * The '<em><b>Nonmodaldialog</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NONMODALDIALOG_VALUE
	 * @generated
	 * @ordered
	 */
	NONMODALDIALOG(2, "nonmodaldialog", "nonmodaldialog");

	/**
	 * The '<em><b>Inplace</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Inplace</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INPLACE
	 * @model name="inplace"
	 * @generated
	 * @ordered
	 */
	public static final int INPLACE_VALUE = 0;

	/**
	 * The '<em><b>Modaldialog</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Modaldialog</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MODALDIALOG
	 * @model name="modaldialog"
	 * @generated
	 * @ordered
	 */
	public static final int MODALDIALOG_VALUE = 1;

	/**
	 * The '<em><b>Nonmodaldialog</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Nonmodaldialog</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NONMODALDIALOG
	 * @model name="nonmodaldialog"
	 * @generated
	 * @ordered
	 */
	public static final int NONMODALDIALOG_VALUE = 2;

	/**
	 * An array of all the '<em><b>Display Mode Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final DisplayModeEnum[] VALUES_ARRAY =
		new DisplayModeEnum[] {
			INPLACE,
			MODALDIALOG,
			NONMODALDIALOG,
		};

	/**
	 * A public read-only list of all the '<em><b>Display Mode Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<DisplayModeEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Display Mode Enum</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DisplayModeEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DisplayModeEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Display Mode Enum</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DisplayModeEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DisplayModeEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Display Mode Enum</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DisplayModeEnum get(int value) {
		switch (value) {
			case INPLACE_VALUE: return INPLACE;
			case MODALDIALOG_VALUE: return MODALDIALOG;
			case NONMODALDIALOG_VALUE: return NONMODALDIALOG;
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
	private DisplayModeEnum(int value, String name, String literal) {
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
	
} //DisplayModeEnum
