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
 * A representation of the literals of the enumeration '<em><b>Panel State Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 * 			Represents the enumeration of the values to be used as 'state' in a 'panel'
 *             
 * <!-- end-model-doc -->
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPanelStateEnum()
 * @model extendedMetaData="name='PanelStateEnum'"
 * @generated
 */
public enum PanelStateEnum implements Enumerator {
	/**
	 * The '<em><b>Maximized</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MAXIMIZED_VALUE
	 * @generated
	 * @ordered
	 */
	MAXIMIZED(0, "maximized", "maximized"),

	/**
	 * The '<em><b>Minimized</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MINIMIZED_VALUE
	 * @generated
	 * @ordered
	 */
	MINIMIZED(1, "minimized", "minimized"),

	/**
	 * The '<em><b>Normal</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NORMAL_VALUE
	 * @generated
	 * @ordered
	 */
	NORMAL(2, "normal", "normal");

	/**
	 * The '<em><b>Maximized</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Maximized</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MAXIMIZED
	 * @model name="maximized"
	 * @generated
	 * @ordered
	 */
	public static final int MAXIMIZED_VALUE = 0;

	/**
	 * The '<em><b>Minimized</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Minimized</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MINIMIZED
	 * @model name="minimized"
	 * @generated
	 * @ordered
	 */
	public static final int MINIMIZED_VALUE = 1;

	/**
	 * The '<em><b>Normal</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Normal</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NORMAL
	 * @model name="normal"
	 * @generated
	 * @ordered
	 */
	public static final int NORMAL_VALUE = 2;

	/**
	 * An array of all the '<em><b>Panel State Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PanelStateEnum[] VALUES_ARRAY =
		new PanelStateEnum[] {
			MAXIMIZED,
			MINIMIZED,
			NORMAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Panel State Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<PanelStateEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Panel State Enum</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PanelStateEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PanelStateEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Panel State Enum</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PanelStateEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PanelStateEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Panel State Enum</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PanelStateEnum get(int value) {
		switch (value) {
			case MAXIMIZED_VALUE: return MAXIMIZED;
			case MINIMIZED_VALUE: return MINIMIZED;
			case NORMAL_VALUE: return NORMAL;
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
	private PanelStateEnum(int value, String name, String literal) {
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
	
} //PanelStateEnum
