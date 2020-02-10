/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>METRIC AGGR TYPE</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.ModelPackage#getMETRIC_AGGR_TYPE()
 * @model
 * @generated
 */
public enum METRIC_AGGR_TYPE implements Enumerator {
	/**
	 * The '<em><b>Set</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SET_VALUE
	 * @generated
	 * @ordered
	 */
	SET(0, "Set", "Set"),

	/**
	 * The '<em><b>Count</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #COUNT_VALUE
	 * @generated
	 * @ordered
	 */
	COUNT(1, "Count", "Count"),

	/**
	 * The '<em><b>Maximum</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MAXIMUM_VALUE
	 * @generated
	 * @ordered
	 */
	MAXIMUM(2, "Maximum", "Maximum"),

	/**
	 * The '<em><b>Minimum</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MINIMUM_VALUE
	 * @generated
	 * @ordered
	 */
	MINIMUM(3, "Minimum", "Minimum"),

	/**
	 * The '<em><b>Sum</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SUM_VALUE
	 * @generated
	 * @ordered
	 */
	SUM(4, "Sum", "Sum"),

	/**
	 * The '<em><b>Sum Of Squares</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SUM_OF_SQUARES_VALUE
	 * @generated
	 * @ordered
	 */
	SUM_OF_SQUARES(5, "SumOfSquares", "SumOfSquares"),

	/**
	 * The '<em><b>Average</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AVERAGE_VALUE
	 * @generated
	 * @ordered
	 */
	AVERAGE(6, "Average", "Average"),

	/**
	 * The '<em><b>Variance</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VARIANCE_VALUE
	 * @generated
	 * @ordered
	 */
	VARIANCE(7, "Variance", "Variance"),

	/**
	 * The '<em><b>Standard Deviation</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STANDARD_DEVIATION_VALUE
	 * @generated
	 * @ordered
	 */
	STANDARD_DEVIATION(8, "StandardDeviation", "StandardDeviation");

	/**
	 * The '<em><b>Set</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Set</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SET
	 * @model name="Set"
	 * @generated
	 * @ordered
	 */
	public static final int SET_VALUE = 0;

	/**
	 * The '<em><b>Count</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Count</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #COUNT
	 * @model name="Count"
	 * @generated
	 * @ordered
	 */
	public static final int COUNT_VALUE = 1;

	/**
	 * The '<em><b>Maximum</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Maximum</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MAXIMUM
	 * @model name="Maximum"
	 * @generated
	 * @ordered
	 */
	public static final int MAXIMUM_VALUE = 2;

	/**
	 * The '<em><b>Minimum</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Minimum</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MINIMUM
	 * @model name="Minimum"
	 * @generated
	 * @ordered
	 */
	public static final int MINIMUM_VALUE = 3;

	/**
	 * The '<em><b>Sum</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Sum</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SUM
	 * @model name="Sum"
	 * @generated
	 * @ordered
	 */
	public static final int SUM_VALUE = 4;

	/**
	 * The '<em><b>Sum Of Squares</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Sum Of Squares</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SUM_OF_SQUARES
	 * @model name="SumOfSquares"
	 * @generated
	 * @ordered
	 */
	public static final int SUM_OF_SQUARES_VALUE = 5;

	/**
	 * The '<em><b>Average</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Average</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #AVERAGE
	 * @model name="Average"
	 * @generated
	 * @ordered
	 */
	public static final int AVERAGE_VALUE = 6;

	/**
	 * The '<em><b>Variance</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Variance</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #VARIANCE
	 * @model name="Variance"
	 * @generated
	 * @ordered
	 */
	public static final int VARIANCE_VALUE = 7;

	/**
	 * The '<em><b>Standard Deviation</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Standard Deviation</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STANDARD_DEVIATION
	 * @model name="StandardDeviation"
	 * @generated
	 * @ordered
	 */
	public static final int STANDARD_DEVIATION_VALUE = 8;

	/**
	 * An array of all the '<em><b>METRIC AGGR TYPE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final METRIC_AGGR_TYPE[] VALUES_ARRAY =
		new METRIC_AGGR_TYPE[] {
			SET,
			COUNT,
			MAXIMUM,
			MINIMUM,
			SUM,
			SUM_OF_SQUARES,
			AVERAGE,
			VARIANCE,
			STANDARD_DEVIATION,
		};

	/**
	 * A public read-only list of all the '<em><b>METRIC AGGR TYPE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<METRIC_AGGR_TYPE> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>METRIC AGGR TYPE</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static METRIC_AGGR_TYPE get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			METRIC_AGGR_TYPE result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>METRIC AGGR TYPE</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static METRIC_AGGR_TYPE getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			METRIC_AGGR_TYPE result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>METRIC AGGR TYPE</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static METRIC_AGGR_TYPE get(int value) {
		switch (value) {
			case SET_VALUE: return SET;
			case COUNT_VALUE: return COUNT;
			case MAXIMUM_VALUE: return MAXIMUM;
			case MINIMUM_VALUE: return MINIMUM;
			case SUM_VALUE: return SUM;
			case SUM_OF_SQUARES_VALUE: return SUM_OF_SQUARES;
			case AVERAGE_VALUE: return AVERAGE;
			case VARIANCE_VALUE: return VARIANCE;
			case STANDARD_DEVIATION_VALUE: return STANDARD_DEVIATION;
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
	private METRIC_AGGR_TYPE(int value, String name, String literal) {
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
	
} //METRIC_AGGR_TYPE
