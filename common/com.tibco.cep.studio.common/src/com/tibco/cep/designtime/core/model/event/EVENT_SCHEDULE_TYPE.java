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
 * A representation of the literals of the enumeration '<em><b>EVENT SCHEDULE TYPE</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getEVENT_SCHEDULE_TYPE()
 * @model
 * @generated
 */
public enum EVENT_SCHEDULE_TYPE implements Enumerator {
	/**
	 * The '<em><b>Rule Based</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RULE_BASED_VALUE
	 * @generated
	 * @ordered
	 */
	RULE_BASED(0, "RuleBased", "ruleBased"),

	/**
	 * The '<em><b>Repeat</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REPEAT_VALUE
	 * @generated
	 * @ordered
	 */
	REPEAT(1, "Repeat", "repeat"), /**
	 * The '<em><b>Run Once</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RUN_ONCE_VALUE
	 * @generated
	 * @ordered
	 */
	RUN_ONCE(2, "RunOnce", "runOnce");

	/**
	 * The '<em><b>Rule Based</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Rule Based</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RULE_BASED
	 * @model name="RuleBased" literal="ruleBased"
	 * @generated
	 * @ordered
	 */
	public static final int RULE_BASED_VALUE = 0;

	/**
	 * The '<em><b>Repeat</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Repeat</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REPEAT
	 * @model name="Repeat" literal="repeat"
	 * @generated
	 * @ordered
	 */
	public static final int REPEAT_VALUE = 1;

	/**
	 * The '<em><b>Run Once</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Run Once</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RUN_ONCE
	 * @model name="RunOnce" literal="runOnce"
	 * @generated
	 * @ordered
	 */
	public static final int RUN_ONCE_VALUE = 2;

	/**
	 * An array of all the '<em><b>EVENT SCHEDULE TYPE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final EVENT_SCHEDULE_TYPE[] VALUES_ARRAY =
		new EVENT_SCHEDULE_TYPE[] {
			RULE_BASED,
			REPEAT,
			RUN_ONCE,
		};

	/**
	 * A public read-only list of all the '<em><b>EVENT SCHEDULE TYPE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<EVENT_SCHEDULE_TYPE> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>EVENT SCHEDULE TYPE</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EVENT_SCHEDULE_TYPE get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EVENT_SCHEDULE_TYPE result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>EVENT SCHEDULE TYPE</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EVENT_SCHEDULE_TYPE getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EVENT_SCHEDULE_TYPE result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>EVENT SCHEDULE TYPE</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EVENT_SCHEDULE_TYPE get(int value) {
		switch (value) {
			case RULE_BASED_VALUE: return RULE_BASED;
			case REPEAT_VALUE: return REPEAT;
			case RUN_ONCE_VALUE: return RUN_ONCE;
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
	private EVENT_SCHEDULE_TYPE(int value, String name, String literal) {
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
	
} //EVENT_SCHEDULE_TYPE
