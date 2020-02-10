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
 * A representation of the literals of the enumeration '<em><b>Plot Shape Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 * 			Represents the enumeration of the values to be used as 'plotshape'
 *             
 * <!-- end-model-doc -->
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPlotShapeEnum()
 * @model extendedMetaData="name='PlotShapeEnum'"
 * @generated
 */
public enum PlotShapeEnum implements Enumerator {
	/**
	 * The '<em><b>Square</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SQUARE_VALUE
	 * @generated
	 * @ordered
	 */
	SQUARE(0, "square", "square"),

	/**
	 * The '<em><b>Circle</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CIRCLE_VALUE
	 * @generated
	 * @ordered
	 */
	CIRCLE(1, "circle", "circle"),

	/**
	 * The '<em><b>Triangle</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TRIANGLE_VALUE
	 * @generated
	 * @ordered
	 */
	TRIANGLE(2, "triangle", "triangle"),

	/**
	 * The '<em><b>Cross</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CROSS_VALUE
	 * @generated
	 * @ordered
	 */
	CROSS(3, "cross", "cross"),

	 /**
	 * The '<em><b>Diamond</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DIAMOND_VALUE
	 * @generated
	 * @ordered
	 */
	DIAMOND(4, "diamond", "diamond");

	/**
	 * The '<em><b>Square</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Square</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SQUARE
	 * @model name="square"
	 * @generated
	 * @ordered
	 */
	public static final int SQUARE_VALUE = 0;

	/**
	 * The '<em><b>Circle</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Circle</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CIRCLE
	 * @model name="circle"
	 * @generated
	 * @ordered
	 */
	public static final int CIRCLE_VALUE = 1;

	/**
	 * The '<em><b>Triangle</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Triangle</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TRIANGLE
	 * @model name="triangle"
	 * @generated
	 * @ordered
	 */
	public static final int TRIANGLE_VALUE = 2;

	/**
	 * The '<em><b>Cross</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Cross</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CROSS
	 * @model name="cross"
	 * @generated
	 * @ordered
	 */
	public static final int CROSS_VALUE = 3;

	/**
	 * The '<em><b>Diamond</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Diamond</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DIAMOND
	 * @model name="diamond"
	 * @generated
	 * @ordered
	 */
	public static final int DIAMOND_VALUE = 4;

	/**
	 * An array of all the '<em><b>Plot Shape Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PlotShapeEnum[] VALUES_ARRAY =
		new PlotShapeEnum[] {
			SQUARE,
			CIRCLE,
			TRIANGLE,
			CROSS,
			DIAMOND,
		};

	/**
	 * A public read-only list of all the '<em><b>Plot Shape Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<PlotShapeEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Plot Shape Enum</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PlotShapeEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PlotShapeEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Plot Shape Enum</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PlotShapeEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PlotShapeEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Plot Shape Enum</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PlotShapeEnum get(int value) {
		switch (value) {
			case SQUARE_VALUE: return SQUARE;
			case CIRCLE_VALUE: return CIRCLE;
			case TRIANGLE_VALUE: return TRIANGLE;
			case CROSS_VALUE: return CROSS;
			case DIAMOND_VALUE: return DIAMOND;
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
	private PlotShapeEnum(int value, String name, String literal) {
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
	
} //PlotShapeEnum
