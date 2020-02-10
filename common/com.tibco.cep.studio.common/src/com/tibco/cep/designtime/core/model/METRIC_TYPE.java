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
 * A representation of the literals of the enumeration '<em><b>METRIC TYPE</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.ModelPackage#getMETRIC_TYPE()
 * @model
 * @generated
 */
public enum METRIC_TYPE implements Enumerator {
	/**
	 * The '<em><b>Regular</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REGULAR_VALUE
	 * @generated
	 * @ordered
	 */
	REGULAR(0, "Regular", "Regular"),

	/**
	 * The '<em><b>Rolling Time</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ROLLING_TIME_VALUE
	 * @generated
	 * @ordered
	 */
	ROLLING_TIME(1, "RollingTime", "RollingTime"),

	/**
	 * The '<em><b>Rolling Count</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ROLLING_COUNT_VALUE
	 * @generated
	 * @ordered
	 */
	ROLLING_COUNT(2, "RollingCount", "RollingCount"),

	/**
	 * The '<em><b>Periodic Time</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PERIODIC_TIME_VALUE
	 * @generated
	 * @ordered
	 */
	PERIODIC_TIME(3, "PeriodicTime", "PeriodicTime"),

	/**
	 * The '<em><b>Periodic Count</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PERIODIC_COUNT_VALUE
	 * @generated
	 * @ordered
	 */
	PERIODIC_COUNT(4, "PeriodicCount", "PeriodicCount"),

	/**
	 * The '<em><b>Window Time</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WINDOW_TIME_VALUE
	 * @generated
	 * @ordered
	 */
	WINDOW_TIME(5, "WindowTime", "WindowTime"),

	/**
	 * The '<em><b>Window Count</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WINDOW_COUNT_VALUE
	 * @generated
	 * @ordered
	 */
	WINDOW_COUNT(6, "WindowCount", "WindowCount");

	/**
	 * The '<em><b>Regular</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Regular</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REGULAR
	 * @model name="Regular"
	 * @generated
	 * @ordered
	 */
	public static final int REGULAR_VALUE = 0;

	/**
	 * The '<em><b>Rolling Time</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Rolling Time</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ROLLING_TIME
	 * @model name="RollingTime"
	 * @generated
	 * @ordered
	 */
	public static final int ROLLING_TIME_VALUE = 1;

	/**
	 * The '<em><b>Rolling Count</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Rolling Count</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ROLLING_COUNT
	 * @model name="RollingCount"
	 * @generated
	 * @ordered
	 */
	public static final int ROLLING_COUNT_VALUE = 2;

	/**
	 * The '<em><b>Periodic Time</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Periodic Time</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PERIODIC_TIME
	 * @model name="PeriodicTime"
	 * @generated
	 * @ordered
	 */
	public static final int PERIODIC_TIME_VALUE = 3;

	/**
	 * The '<em><b>Periodic Count</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Periodic Count</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PERIODIC_COUNT
	 * @model name="PeriodicCount"
	 * @generated
	 * @ordered
	 */
	public static final int PERIODIC_COUNT_VALUE = 4;

	/**
	 * The '<em><b>Window Time</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Window Time</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WINDOW_TIME
	 * @model name="WindowTime"
	 * @generated
	 * @ordered
	 */
	public static final int WINDOW_TIME_VALUE = 5;

	/**
	 * The '<em><b>Window Count</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Window Count</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WINDOW_COUNT
	 * @model name="WindowCount"
	 * @generated
	 * @ordered
	 */
	public static final int WINDOW_COUNT_VALUE = 6;

	/**
	 * An array of all the '<em><b>METRIC TYPE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final METRIC_TYPE[] VALUES_ARRAY =
		new METRIC_TYPE[] {
			REGULAR,
			ROLLING_TIME,
			ROLLING_COUNT,
			PERIODIC_TIME,
			PERIODIC_COUNT,
			WINDOW_TIME,
			WINDOW_COUNT,
		};

	/**
	 * A public read-only list of all the '<em><b>METRIC TYPE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<METRIC_TYPE> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>METRIC TYPE</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static METRIC_TYPE get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			METRIC_TYPE result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>METRIC TYPE</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static METRIC_TYPE getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			METRIC_TYPE result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>METRIC TYPE</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static METRIC_TYPE get(int value) {
		switch (value) {
			case REGULAR_VALUE: return REGULAR;
			case ROLLING_TIME_VALUE: return ROLLING_TIME;
			case ROLLING_COUNT_VALUE: return ROLLING_COUNT;
			case PERIODIC_TIME_VALUE: return PERIODIC_TIME;
			case PERIODIC_COUNT_VALUE: return PERIODIC_COUNT;
			case WINDOW_TIME_VALUE: return WINDOW_TIME;
			case WINDOW_COUNT_VALUE: return WINDOW_COUNT;
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
	private METRIC_TYPE(int value, String name, String literal) {
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
	
} //METRIC_TYPE
