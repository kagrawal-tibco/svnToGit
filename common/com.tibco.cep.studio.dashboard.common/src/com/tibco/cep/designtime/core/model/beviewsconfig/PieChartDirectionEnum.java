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
 * A representation of the literals of the enumeration '<em><b>Pie Chart Direction Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 * 			Represents the enumeration of the values to be used as 'direction'
 *             
 * <!-- end-model-doc -->
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPieChartDirectionEnum()
 * @model extendedMetaData="name='PieChartDirectionEnum'"
 * @generated
 */
public enum PieChartDirectionEnum implements Enumerator {
	/**
	 * The '<em><b>Clockwise</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CLOCKWISE_VALUE
	 * @generated
	 * @ordered
	 */
	CLOCKWISE(0, "clockwise", "clockwise"),

	/**
	 * The '<em><b>Counterclockwise</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #COUNTERCLOCKWISE_VALUE
	 * @generated
	 * @ordered
	 */
	COUNTERCLOCKWISE(1, "counterclockwise", "counterclockwise");

	/**
	 * The '<em><b>Clockwise</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Clockwise</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CLOCKWISE
	 * @model name="clockwise"
	 * @generated
	 * @ordered
	 */
	public static final int CLOCKWISE_VALUE = 0;

	/**
	 * The '<em><b>Counterclockwise</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Counterclockwise</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #COUNTERCLOCKWISE
	 * @model name="counterclockwise"
	 * @generated
	 * @ordered
	 */
	public static final int COUNTERCLOCKWISE_VALUE = 1;

	/**
	 * An array of all the '<em><b>Pie Chart Direction Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PieChartDirectionEnum[] VALUES_ARRAY =
		new PieChartDirectionEnum[] {
			CLOCKWISE,
			COUNTERCLOCKWISE,
		};

	/**
	 * A public read-only list of all the '<em><b>Pie Chart Direction Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<PieChartDirectionEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Pie Chart Direction Enum</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PieChartDirectionEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PieChartDirectionEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Pie Chart Direction Enum</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PieChartDirectionEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PieChartDirectionEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Pie Chart Direction Enum</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PieChartDirectionEnum get(int value) {
		switch (value) {
			case CLOCKWISE_VALUE: return CLOCKWISE;
			case COUNTERCLOCKWISE_VALUE: return COUNTERCLOCKWISE;
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
	private PieChartDirectionEnum(int value, String name, String literal) {
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
	
} //PieChartDirectionEnum
