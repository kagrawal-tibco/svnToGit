/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>JAVA CLASSPATH ENTRY TYPE</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getJAVA_CLASSPATH_ENTRY_TYPE()
 * @model
 * @generated
 */
public enum JAVA_CLASSPATH_ENTRY_TYPE implements Enumerator {
	/**
	 * The '<em><b>CPE SOURCE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CPE_SOURCE_VALUE
	 * @generated
	 * @ordered
	 */
	CPE_SOURCE(0, "CPE_SOURCE", "CPE_SOURCE"),

	/**
	 * The '<em><b>CPE LIBRARY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CPE_LIBRARY_VALUE
	 * @generated
	 * @ordered
	 */
	CPE_LIBRARY(1, "CPE_LIBRARY", "CPE_LIBRARY"),

	/**
	 * The '<em><b>CPE VARIABLE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CPE_VARIABLE_VALUE
	 * @generated
	 * @ordered
	 */
	CPE_VARIABLE(2, "CPE_VARIABLE", "CPE_VARIABLE"),

	/**
	 * The '<em><b>CPE PROJECT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CPE_PROJECT_VALUE
	 * @generated
	 * @ordered
	 */
	CPE_PROJECT(3, "CPE_PROJECT", "CPE_PROJECT"),

	/**
	 * The '<em><b>CPE CONTAINER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CPE_CONTAINER_VALUE
	 * @generated
	 * @ordered
	 */
	CPE_CONTAINER(4, "CPE_CONTAINER", "CPE_CONTAINER");

	/**
	 * The '<em><b>CPE SOURCE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CPE SOURCE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CPE_SOURCE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CPE_SOURCE_VALUE = 0;

	/**
	 * The '<em><b>CPE LIBRARY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CPE LIBRARY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CPE_LIBRARY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CPE_LIBRARY_VALUE = 1;

	/**
	 * The '<em><b>CPE VARIABLE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CPE VARIABLE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CPE_VARIABLE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CPE_VARIABLE_VALUE = 2;

	/**
	 * The '<em><b>CPE PROJECT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CPE PROJECT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CPE_PROJECT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CPE_PROJECT_VALUE = 3;

	/**
	 * The '<em><b>CPE CONTAINER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CPE CONTAINER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CPE_CONTAINER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CPE_CONTAINER_VALUE = 4;

	/**
	 * An array of all the '<em><b>JAVA CLASSPATH ENTRY TYPE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final JAVA_CLASSPATH_ENTRY_TYPE[] VALUES_ARRAY =
		new JAVA_CLASSPATH_ENTRY_TYPE[] {
			CPE_SOURCE,
			CPE_LIBRARY,
			CPE_VARIABLE,
			CPE_PROJECT,
			CPE_CONTAINER,
		};

	/**
	 * A public read-only list of all the '<em><b>JAVA CLASSPATH ENTRY TYPE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<JAVA_CLASSPATH_ENTRY_TYPE> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>JAVA CLASSPATH ENTRY TYPE</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static JAVA_CLASSPATH_ENTRY_TYPE get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			JAVA_CLASSPATH_ENTRY_TYPE result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>JAVA CLASSPATH ENTRY TYPE</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static JAVA_CLASSPATH_ENTRY_TYPE getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			JAVA_CLASSPATH_ENTRY_TYPE result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>JAVA CLASSPATH ENTRY TYPE</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static JAVA_CLASSPATH_ENTRY_TYPE get(int value) {
		switch (value) {
			case CPE_SOURCE_VALUE: return CPE_SOURCE;
			case CPE_LIBRARY_VALUE: return CPE_LIBRARY;
			case CPE_VARIABLE_VALUE: return CPE_VARIABLE;
			case CPE_PROJECT_VALUE: return CPE_PROJECT;
			case CPE_CONTAINER_VALUE: return CPE_CONTAINER;
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
	private JAVA_CLASSPATH_ENTRY_TYPE(int value, String name, String literal) {
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
	
} //JAVA_CLASSPATH_ENTRY_TYPE
