/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.event;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>EVENT TYPE</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEVENT_TYPE()
 * @model
 * @generated
 */
public enum EVENT_TYPE implements Enumerator {
	/**
	 * The '<em><b>Simple Event</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SIMPLE_EVENT_VALUE
	 * @generated
	 * @ordered
	 */
	SIMPLE_EVENT(0, "SimpleEvent", "SimpleEvent"),

	/**
	 * The '<em><b>Time Event</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TIME_EVENT_VALUE
	 * @generated
	 * @ordered
	 */
	TIME_EVENT(1, "TimeEvent", "TimeEvent"), /**
	 * The '<em><b>Soap Event</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SOAP_EVENT_VALUE
	 * @generated
	 * @ordered
	 */
	SOAP_EVENT(2, "SoapEvent", "SoapEvent"), /**
	 * The '<em><b>Advisory Event</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ADVISORY_EVENT_VALUE
	 * @generated
	 * @ordered
	 */
	ADVISORY_EVENT(3, "AdvisoryEvent", "AdvisoryEvent");

	/**
	 * The '<em><b>Simple Event</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Simple Event</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SIMPLE_EVENT
	 * @model name="SimpleEvent"
	 * @generated
	 * @ordered
	 */
	public static final int SIMPLE_EVENT_VALUE = 0;

	/**
	 * The '<em><b>Time Event</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Time Event</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TIME_EVENT
	 * @model name="TimeEvent"
	 * @generated
	 * @ordered
	 */
	public static final int TIME_EVENT_VALUE = 1;

	/**
	 * The '<em><b>Soap Event</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Soap Event</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SOAP_EVENT
	 * @model name="SoapEvent"
	 * @generated
	 * @ordered
	 */
	public static final int SOAP_EVENT_VALUE = 2;

	/**
	 * The '<em><b>Advisory Event</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Advisory Event</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ADVISORY_EVENT
	 * @model name="AdvisoryEvent"
	 * @generated
	 * @ordered
	 */
	public static final int ADVISORY_EVENT_VALUE = 3;

	/**
	 * An array of all the '<em><b>EVENT TYPE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final EVENT_TYPE[] VALUES_ARRAY =
		new EVENT_TYPE[] {
			SIMPLE_EVENT,
			TIME_EVENT,
			SOAP_EVENT,
			ADVISORY_EVENT,
		};

	/**
	 * A public read-only list of all the '<em><b>EVENT TYPE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<EVENT_TYPE> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>EVENT TYPE</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EVENT_TYPE get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EVENT_TYPE result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>EVENT TYPE</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EVENT_TYPE getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EVENT_TYPE result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>EVENT TYPE</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EVENT_TYPE get(int value) {
		switch (value) {
			case SIMPLE_EVENT_VALUE: return SIMPLE_EVENT;
			case TIME_EVENT_VALUE: return TIME_EVENT;
			case SOAP_EVENT_VALUE: return SOAP_EVENT;
			case ADVISORY_EVENT_VALUE: return ADVISORY_EVENT;
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
	private EVENT_TYPE(int value, String name, String literal) {
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
	
} //EVENT_TYPE
