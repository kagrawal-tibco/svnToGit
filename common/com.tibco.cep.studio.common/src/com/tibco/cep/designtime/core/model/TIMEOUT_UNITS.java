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
 * A representation of the literals of the enumeration '<em><b>TIMEOUT UNITS</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.ModelPackage#getTIMEOUT_UNITS()
 * @model
 * @generated
 */
public enum TIMEOUT_UNITS implements Enumerator {
	/**
	 * The '<em><b>Milliseconds</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MILLISECONDS_VALUE
	 * @generated
	 * @ordered
	 */
	MILLISECONDS(0, "Milliseconds", "Milliseconds"),

	/**
	 * The '<em><b>Seconds</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SECONDS_VALUE
	 * @generated
	 * @ordered
	 */
	SECONDS(1, "Seconds", "Seconds"),

	/**
	 * The '<em><b>Minutes</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MINUTES_VALUE
	 * @generated
	 * @ordered
	 */
	MINUTES(2, "Minutes", "Minutes"),

	/**
	 * The '<em><b>Hours</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HOURS_VALUE
	 * @generated
	 * @ordered
	 */
	HOURS(3, "Hours", "Hours"),

	/**
	 * The '<em><b>Days</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DAYS_VALUE
	 * @generated
	 * @ordered
	 */
	DAYS(4, "Days", "Days"), /**
	 * The '<em><b>Week Days</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WEEK_DAYS_VALUE
	 * @generated
	 * @ordered
	 */
	WEEK_DAYS(5, "WeekDays", "WeekDays"), /**
	 * The '<em><b>Weeks</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WEEKS_VALUE
	 * @generated
	 * @ordered
	 */
	WEEKS(6, "Weeks", "Weeks"), /**
	 * The '<em><b>Months</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MONTHS_VALUE
	 * @generated
	 * @ordered
	 */
	MONTHS(7, "Months", "Months"), /**
	 * The '<em><b>Years</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #YEARS_VALUE
	 * @generated
	 * @ordered
	 */
	YEARS(8, "Years", "Years");

	/**
	 * The '<em><b>Milliseconds</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Milliseconds</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MILLISECONDS
	 * @model name="Milliseconds"
	 * @generated
	 * @ordered
	 */
	public static final int MILLISECONDS_VALUE = 0;

	/**
	 * The '<em><b>Seconds</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Seconds</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SECONDS
	 * @model name="Seconds"
	 * @generated
	 * @ordered
	 */
	public static final int SECONDS_VALUE = 1;

	/**
	 * The '<em><b>Minutes</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Minutes</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MINUTES
	 * @model name="Minutes"
	 * @generated
	 * @ordered
	 */
	public static final int MINUTES_VALUE = 2;

	/**
	 * The '<em><b>Hours</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Hours</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #HOURS
	 * @model name="Hours"
	 * @generated
	 * @ordered
	 */
	public static final int HOURS_VALUE = 3;

	/**
	 * The '<em><b>Days</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Days</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DAYS
	 * @model name="Days"
	 * @generated
	 * @ordered
	 */
	public static final int DAYS_VALUE = 4;

	/**
	 * The '<em><b>Week Days</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Week Days</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WEEK_DAYS
	 * @model name="WeekDays"
	 * @generated
	 * @ordered
	 */
	public static final int WEEK_DAYS_VALUE = 5;

	/**
	 * The '<em><b>Weeks</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Weeks</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WEEKS
	 * @model name="Weeks"
	 * @generated
	 * @ordered
	 */
	public static final int WEEKS_VALUE = 6;

	/**
	 * The '<em><b>Months</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Months</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MONTHS
	 * @model name="Months"
	 * @generated
	 * @ordered
	 */
	public static final int MONTHS_VALUE = 7;

	/**
	 * The '<em><b>Years</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Years</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #YEARS
	 * @model name="Years"
	 * @generated
	 * @ordered
	 */
	public static final int YEARS_VALUE = 8;

	/**
	 * An array of all the '<em><b>TIMEOUT UNITS</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final TIMEOUT_UNITS[] VALUES_ARRAY =
		new TIMEOUT_UNITS[] {
			MILLISECONDS,
			SECONDS,
			MINUTES,
			HOURS,
			DAYS,
			WEEK_DAYS,
			WEEKS,
			MONTHS,
			YEARS,
		};

	/**
	 * A public read-only list of all the '<em><b>TIMEOUT UNITS</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<TIMEOUT_UNITS> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>TIMEOUT UNITS</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TIMEOUT_UNITS get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TIMEOUT_UNITS result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>TIMEOUT UNITS</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TIMEOUT_UNITS getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TIMEOUT_UNITS result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>TIMEOUT UNITS</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TIMEOUT_UNITS get(int value) {
		switch (value) {
			case MILLISECONDS_VALUE: return MILLISECONDS;
			case SECONDS_VALUE: return SECONDS;
			case MINUTES_VALUE: return MINUTES;
			case HOURS_VALUE: return HOURS;
			case DAYS_VALUE: return DAYS;
			case WEEK_DAYS_VALUE: return WEEK_DAYS;
			case WEEKS_VALUE: return WEEKS;
			case MONTHS_VALUE: return MONTHS;
			case YEARS_VALUE: return YEARS;
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
	private TIMEOUT_UNITS(int value, String name, String literal) {
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
	
} //TIMEOUT_UNITS
