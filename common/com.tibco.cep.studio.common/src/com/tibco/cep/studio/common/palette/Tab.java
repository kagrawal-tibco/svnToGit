/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.palette;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Tab</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.common.palette.PalettePackage#getTab()
 * @model extendedMetaData="name='Tab'"
 * @generated
 */
public enum Tab implements Enumerator {
	/**
	 * The '<em><b>General</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GENERAL_VALUE
	 * @generated
	 * @ordered
	 */
	GENERAL(0, "General", "General"),

	/**
	 * The '<em><b>Loop Characteristics</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LOOP_CHARACTERISTICS_VALUE
	 * @generated
	 * @ordered
	 */
	LOOP_CHARACTERISTICS(1, "LoopCharacteristics", "Loop Characteristics"), /**
	 * The '<em><b>Reply Event</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REPLY_EVENT_VALUE
	 * @generated
	 * @ordered
	 */
	REPLY_EVENT(2, "ReplyEvent", "Reply Event"), /**
	 * The '<em><b>Timeout</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TIMEOUT_VALUE
	 * @generated
	 * @ordered
	 */
	TIMEOUT(3, "Timeout", "Timeout");

	/**
	 * The '<em><b>General</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>General</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #GENERAL
	 * @model name="General"
	 * @generated
	 * @ordered
	 */
	public static final int GENERAL_VALUE = 0;

	/**
	 * The '<em><b>Loop Characteristics</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Loop Characteristics</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LOOP_CHARACTERISTICS
	 * @model name="LoopCharacteristics" literal="Loop Characteristics"
	 * @generated
	 * @ordered
	 */
	public static final int LOOP_CHARACTERISTICS_VALUE = 1;

	/**
	 * The '<em><b>Reply Event</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Reply Event</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REPLY_EVENT
	 * @model name="ReplyEvent" literal="Reply Event"
	 * @generated
	 * @ordered
	 */
	public static final int REPLY_EVENT_VALUE = 2;

	/**
	 * The '<em><b>Timeout</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Timeout</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TIMEOUT
	 * @model name="Timeout"
	 * @generated
	 * @ordered
	 */
	public static final int TIMEOUT_VALUE = 3;

	/**
	 * An array of all the '<em><b>Tab</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final Tab[] VALUES_ARRAY =
		new Tab[] {
			GENERAL,
			LOOP_CHARACTERISTICS,
			REPLY_EVENT,
			TIMEOUT,
		};

	/**
	 * A public read-only list of all the '<em><b>Tab</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<Tab> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Tab</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Tab get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Tab result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Tab</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Tab getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Tab result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Tab</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Tab get(int value) {
		switch (value) {
			case GENERAL_VALUE: return GENERAL;
			case LOOP_CHARACTERISTICS_VALUE: return LOOP_CHARACTERISTICS;
			case REPLY_EVENT_VALUE: return REPLY_EVENT;
			case TIMEOUT_VALUE: return TIMEOUT;
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
	private Tab(int value, String name, String literal) {
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
	
} //Tab
