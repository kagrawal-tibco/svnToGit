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
 * A representation of the literals of the enumeration '<em><b>Series Anchor Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 * 			Represents the enumeration of the values to be used as 'anchor' in a 'seriesconfig'
 *             
 * <!-- end-model-doc -->
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSeriesAnchorEnum()
 * @model extendedMetaData="name='SeriesAnchorEnum'"
 * @generated
 */
public enum SeriesAnchorEnum implements Enumerator {
	/**
	 * The '<em><b>Q1</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #Q1_VALUE
	 * @generated
	 * @ordered
	 */
	Q1(0, "Q1", "Q1"),

	/**
	 * The '<em><b>Q2</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #Q2_VALUE
	 * @generated
	 * @ordered
	 */
	Q2(1, "Q2", "Q2"),

	/**
	 * The '<em><b>Q3</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #Q3_VALUE
	 * @generated
	 * @ordered
	 */
	Q3(2, "Q3", "Q3"),

	/**
	 * The '<em><b>Q4</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #Q4_VALUE
	 * @generated
	 * @ordered
	 */
	Q4(3, "Q4", "Q4");

	/**
	 * The '<em><b>Q1</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Q1</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #Q1
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int Q1_VALUE = 0;

	/**
	 * The '<em><b>Q2</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Q2</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #Q2
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int Q2_VALUE = 1;

	/**
	 * The '<em><b>Q3</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Q3</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #Q3
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int Q3_VALUE = 2;

	/**
	 * The '<em><b>Q4</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Q4</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #Q4
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int Q4_VALUE = 3;

	/**
	 * An array of all the '<em><b>Series Anchor Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final SeriesAnchorEnum[] VALUES_ARRAY =
		new SeriesAnchorEnum[] {
			Q1,
			Q2,
			Q3,
			Q4,
		};

	/**
	 * A public read-only list of all the '<em><b>Series Anchor Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<SeriesAnchorEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Series Anchor Enum</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SeriesAnchorEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SeriesAnchorEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Series Anchor Enum</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SeriesAnchorEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SeriesAnchorEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Series Anchor Enum</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SeriesAnchorEnum get(int value) {
		switch (value) {
			case Q1_VALUE: return Q1;
			case Q2_VALUE: return Q2;
			case Q3_VALUE: return Q3;
			case Q4_VALUE: return Q4;
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
	private SeriesAnchorEnum(int value, String name, String literal) {
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
	
} //SeriesAnchorEnum
