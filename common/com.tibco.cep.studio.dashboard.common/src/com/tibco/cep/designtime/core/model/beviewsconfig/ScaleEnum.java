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
 * A representation of the literals of the enumeration '<em><b>Scale Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 * 			Represents the enumeration of the values to be used as 'scale'
 *             
 * <!-- end-model-doc -->
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getScaleEnum()
 * @model extendedMetaData="name='ScaleEnum'"
 * @generated
 */
public enum ScaleEnum implements Enumerator {
	/**
	 * The '<em><b>Normal</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NORMAL_VALUE
	 * @generated
	 * @ordered
	 */
	NORMAL(0, "normal", "normal"),

	/**
	 * The '<em><b>None</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NONE_VALUE
	 * @generated
	 * @ordered
	 */
	NONE(1, "none", "none"),

	/**
	 * The '<em><b>Thousands</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #THOUSANDS_VALUE
	 * @generated
	 * @ordered
	 */
	THOUSANDS(2, "thousands", "thousands"),

	/**
	 * The '<em><b>Billions</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BILLIONS_VALUE
	 * @generated
	 * @ordered
	 */
	BILLIONS(3, "billions", "billions"),

	/**
	 * The '<em><b>Millions</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MILLIONS_VALUE
	 * @generated
	 * @ordered
	 */
	MILLIONS(4, "millions", "millions"),

	/**
	 * The '<em><b>Trillions</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TRILLIONS_VALUE
	 * @generated
	 * @ordered
	 */
	TRILLIONS(5, "trillions", "trillions"),

	/**
	 * The '<em><b>Quadrillions</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #QUADRILLIONS_VALUE
	 * @generated
	 * @ordered
	 */
	QUADRILLIONS(6, "quadrillions", "quadrillions"),

	/**
	 * The '<em><b>Logarithmic</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LOGARITHMIC_VALUE
	 * @generated
	 * @ordered
	 */
	LOGARITHMIC(7, "logarithmic", "logarithmic");

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
	public static final int NORMAL_VALUE = 0;

	/**
	 * The '<em><b>None</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>None</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NONE
	 * @model name="none"
	 * @generated
	 * @ordered
	 */
	public static final int NONE_VALUE = 1;

	/**
	 * The '<em><b>Thousands</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Thousands</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #THOUSANDS
	 * @model name="thousands"
	 * @generated
	 * @ordered
	 */
	public static final int THOUSANDS_VALUE = 2;

	/**
	 * The '<em><b>Billions</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Billions</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BILLIONS
	 * @model name="billions"
	 * @generated
	 * @ordered
	 */
	public static final int BILLIONS_VALUE = 3;

	/**
	 * The '<em><b>Millions</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Millions</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MILLIONS
	 * @model name="millions"
	 * @generated
	 * @ordered
	 */
	public static final int MILLIONS_VALUE = 4;

	/**
	 * The '<em><b>Trillions</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Trillions</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TRILLIONS
	 * @model name="trillions"
	 * @generated
	 * @ordered
	 */
	public static final int TRILLIONS_VALUE = 5;

	/**
	 * The '<em><b>Quadrillions</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Quadrillions</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #QUADRILLIONS
	 * @model name="quadrillions"
	 * @generated
	 * @ordered
	 */
	public static final int QUADRILLIONS_VALUE = 6;

	/**
	 * The '<em><b>Logarithmic</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Logarithmic</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LOGARITHMIC
	 * @model name="logarithmic"
	 * @generated
	 * @ordered
	 */
	public static final int LOGARITHMIC_VALUE = 7;

	/**
	 * An array of all the '<em><b>Scale Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ScaleEnum[] VALUES_ARRAY =
		new ScaleEnum[] {
			NORMAL,
			NONE,
			THOUSANDS,
			BILLIONS,
			MILLIONS,
			TRILLIONS,
			QUADRILLIONS,
			LOGARITHMIC,
		};

	/**
	 * A public read-only list of all the '<em><b>Scale Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ScaleEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Scale Enum</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ScaleEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ScaleEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Scale Enum</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ScaleEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ScaleEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Scale Enum</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ScaleEnum get(int value) {
		switch (value) {
			case NORMAL_VALUE: return NORMAL;
			case NONE_VALUE: return NONE;
			case THOUSANDS_VALUE: return THOUSANDS;
			case BILLIONS_VALUE: return BILLIONS;
			case MILLIONS_VALUE: return MILLIONS;
			case TRILLIONS_VALUE: return TRILLIONS;
			case QUADRILLIONS_VALUE: return QUADRILLIONS;
			case LOGARITHMIC_VALUE: return LOGARITHMIC;
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
	private ScaleEnum(int value, String name, String literal) {
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
	
} //ScaleEnum
