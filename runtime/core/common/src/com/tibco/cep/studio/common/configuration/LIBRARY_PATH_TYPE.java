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
 * A representation of the literals of the enumeration '<em><b>BUILD PATH TYPE</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getLIBRARY_PATH_TYPE()
 * @model
 * @generated
 */
public enum LIBRARY_PATH_TYPE implements Enumerator {
	/**
	 * The '<em><b>PROJECT LIBRARY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROJECT_LIBRARY_VALUE
	 * @generated
	 * @ordered
	 */
	PROJECT_LIBRARY(0, "PROJECT_LIBRARY", "Project Library"),

	/**
	 * The '<em><b>CUSTOM FUNCTION LIBRARY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CUSTOM_FUNCTION_LIBRARY_VALUE
	 * @generated
	 * @ordered
	 */
	CUSTOM_FUNCTION_LIBRARY(1, "CUSTOM_FUNCTION_LIBRARY", "Custom Functions"), /**
	 * The '<em><b>THIRD PARTY LIBRARY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #THIRD_PARTY_LIBRARY_VALUE
	 * @generated
	 * @ordered
	 */
	THIRD_PARTY_LIBRARY(2, "THIRD_PARTY_LIBRARY", "Third Party"), /**
	 * The '<em><b>CORE INTERNAL LIBRARY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CORE_INTERNAL_LIBRARY_VALUE
	 * @generated
	 * @ordered
	 */
	CORE_INTERNAL_LIBRARY(3, "CORE_INTERNAL_LIBRARY", "Core"), /**
	 * The '<em><b>BPMN PALETTE LIBRARY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BPMN_PALETTE_LIBRARY_VALUE
	 * @generated
	 * @ordered
	 */
	BPMN_PALETTE_LIBRARY(4, "BPMN_PALETTE_LIBRARY", "Bpmn Palette"), /**
	 * The '<em><b>BPMN PROCESS PATH LIBRARY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BPMN_PROCESS_PATH_LIBRARY_VALUE
	 * @generated
	 * @ordered
	 */
	BPMN_PROCESS_PATH_LIBRARY(5, "BPMN_PROCESS_PATH_LIBRARY", "Bpmn Process Path"), /**
	 * The '<em><b>JAVA NATIVE LIBRARY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #JAVA_NATIVE_LIBRARY_VALUE
	 * @generated
	 * @ordered
	 */
	JAVA_NATIVE_LIBRARY(6, "JAVA_NATIVE_LIBRARY", "JNI Library Path"), /**
	 * The '<em><b>JAVA CLASSPATH ENTRY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #JAVA_CLASSPATH_ENTRY_VALUE
	 * @generated
	 * @ordered
	 */
	JAVA_CLASSPATH_ENTRY(7, "JAVA_CLASSPATH_ENTRY", "JAVA_CLASSPATH_ENTRY"), /**
	 * The '<em><b>JAVA SOURCE FOLDER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #JAVA_SOURCE_FOLDER_VALUE
	 * @generated
	 * @ordered
	 */
	JAVA_SOURCE_FOLDER(8, "JAVA_SOURCE_FOLDER", "JAVA_SOURCE_FOLDER");

	/**
	 * The '<em><b>PROJECT LIBRARY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROJECT LIBRARY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROJECT_LIBRARY
	 * @model literal="Project Library"
	 * @generated
	 * @ordered
	 */
	public static final int PROJECT_LIBRARY_VALUE = 0;

	/**
	 * The '<em><b>CUSTOM FUNCTION LIBRARY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CUSTOM FUNCTION LIBRARY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CUSTOM_FUNCTION_LIBRARY
	 * @model literal="Custom Functions"
	 * @generated
	 * @ordered
	 */
	public static final int CUSTOM_FUNCTION_LIBRARY_VALUE = 1;

	/**
	 * The '<em><b>THIRD PARTY LIBRARY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>THIRD PARTY LIBRARY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #THIRD_PARTY_LIBRARY
	 * @model literal="Third Party"
	 * @generated
	 * @ordered
	 */
	public static final int THIRD_PARTY_LIBRARY_VALUE = 2;

	/**
	 * The '<em><b>CORE INTERNAL LIBRARY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CORE INTERNAL LIBRARY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CORE_INTERNAL_LIBRARY
	 * @model literal="Core"
	 * @generated
	 * @ordered
	 */
	public static final int CORE_INTERNAL_LIBRARY_VALUE = 3;

	/**
	 * The '<em><b>BPMN PALETTE LIBRARY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BPMN PALETTE LIBRARY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BPMN_PALETTE_LIBRARY
	 * @model literal="Bpmn Palette"
	 * @generated
	 * @ordered
	 */
	public static final int BPMN_PALETTE_LIBRARY_VALUE = 4;

	/**
	 * The '<em><b>BPMN PROCESS PATH LIBRARY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BPMN PROCESS PATH LIBRARY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BPMN_PROCESS_PATH_LIBRARY
	 * @model literal="Bpmn Process Path"
	 * @generated
	 * @ordered
	 */
	public static final int BPMN_PROCESS_PATH_LIBRARY_VALUE = 5;

	/**
	 * The '<em><b>JAVA NATIVE LIBRARY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>JAVA NATIVE LIBRARY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #JAVA_NATIVE_LIBRARY
	 * @model literal="JNI Library Path"
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_NATIVE_LIBRARY_VALUE = 6;

	/**
	 * The '<em><b>JAVA CLASSPATH ENTRY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>JAVA CLASSPATH ENTRY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #JAVA_CLASSPATH_ENTRY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_CLASSPATH_ENTRY_VALUE = 7;

	/**
	 * The '<em><b>JAVA SOURCE FOLDER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>JAVA SOURCE FOLDER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #JAVA_SOURCE_FOLDER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int JAVA_SOURCE_FOLDER_VALUE = 8;

	/**
	 * An array of all the '<em><b>LIBRARY PATH TYPE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final LIBRARY_PATH_TYPE[] VALUES_ARRAY =
		new LIBRARY_PATH_TYPE[] {
			PROJECT_LIBRARY,
			CUSTOM_FUNCTION_LIBRARY,
			THIRD_PARTY_LIBRARY,
			CORE_INTERNAL_LIBRARY,
			BPMN_PALETTE_LIBRARY,
			BPMN_PROCESS_PATH_LIBRARY,
			JAVA_NATIVE_LIBRARY,
			JAVA_CLASSPATH_ENTRY,
			JAVA_SOURCE_FOLDER,
		};

	/**
	 * A public read-only list of all the '<em><b>LIBRARY PATH TYPE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<LIBRARY_PATH_TYPE> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>LIBRARY PATH TYPE</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LIBRARY_PATH_TYPE get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			LIBRARY_PATH_TYPE result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>LIBRARY PATH TYPE</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LIBRARY_PATH_TYPE getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			LIBRARY_PATH_TYPE result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>LIBRARY PATH TYPE</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LIBRARY_PATH_TYPE get(int value) {
		switch (value) {
			case PROJECT_LIBRARY_VALUE: return PROJECT_LIBRARY;
			case CUSTOM_FUNCTION_LIBRARY_VALUE: return CUSTOM_FUNCTION_LIBRARY;
			case THIRD_PARTY_LIBRARY_VALUE: return THIRD_PARTY_LIBRARY;
			case CORE_INTERNAL_LIBRARY_VALUE: return CORE_INTERNAL_LIBRARY;
			case BPMN_PALETTE_LIBRARY_VALUE: return BPMN_PALETTE_LIBRARY;
			case BPMN_PROCESS_PATH_LIBRARY_VALUE: return BPMN_PROCESS_PATH_LIBRARY;
			case JAVA_NATIVE_LIBRARY_VALUE: return JAVA_NATIVE_LIBRARY;
			case JAVA_CLASSPATH_ENTRY_VALUE: return JAVA_CLASSPATH_ENTRY;
			case JAVA_SOURCE_FOLDER_VALUE: return JAVA_SOURCE_FOLDER;
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
	private LIBRARY_PATH_TYPE(int value, String name, String literal) {
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
	
} //BUILD_PATH_TYPE
