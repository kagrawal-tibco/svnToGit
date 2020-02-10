/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.model.registry;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Add On Types</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getAddOnType()
 * @model extendedMetaData="name='AddOnTypes'"
 * @generated
 */
public enum AddOnTypes implements Enumerator {
	/**
	 * The '<em><b>CORE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CORE_VALUE
	 * @generated
	 * @ordered
	 */
	CORE(0, "CORE", "CORE"),

	/**
	 * The '<em><b>DATAMODELLING</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DATAMODELLING_VALUE
	 * @generated
	 * @ordered
	 */
	DATAMODELLING(1, "DATAMODELLING", "DATA_MODELLING"),

	/**
	 * The '<em><b>EVENTSTREAMPROCESSING</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EVENTSTREAMPROCESSING_VALUE
	 * @generated
	 * @ordered
	 */
	EVENTSTREAMPROCESSING(2, "EVENTSTREAMPROCESSING", "EVENTSTREAM_PROCESSING"),

	/**
	 * The '<em><b>DASHBOARD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DASHBOARD_VALUE
	 * @generated
	 * @ordered
	 */
	DASHBOARD(3, "DASHBOARD", "DASHBOARD"),

	/**
	 * The '<em><b>PROCESS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROCESS_VALUE
	 * @generated
	 * @ordered
	 */
	PROCESS(4, "PROCESS", "PROCESS");

	/**
	 * The '<em><b>CORE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CORE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CORE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CORE_VALUE = 0;

	/**
	 * The '<em><b>DATAMODELLING</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DATAMODELLING</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DATAMODELLING
	 * @model literal="DATA_MODELLING"
	 * @generated
	 * @ordered
	 */
	public static final int DATAMODELLING_VALUE = 1;

	/**
	 * The '<em><b>EVENTSTREAMPROCESSING</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>EVENTSTREAMPROCESSING</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EVENTSTREAMPROCESSING
	 * @model literal="EVENTSTREAM_PROCESSING"
	 * @generated
	 * @ordered
	 */
	public static final int EVENTSTREAMPROCESSING_VALUE = 2;

	/**
	 * The '<em><b>DASHBOARD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DASHBOARD</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DASHBOARD
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DASHBOARD_VALUE = 3;

	/**
	 * The '<em><b>PROCESS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PROCESS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROCESS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PROCESS_VALUE = 4;

	/**
	 * An array of all the '<em><b>Add On Types</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final AddOnTypes[] VALUES_ARRAY =
		new AddOnTypes[] {
			CORE,
			DATAMODELLING,
			EVENTSTREAMPROCESSING,
			DASHBOARD,
			PROCESS,
		};

	/**
	 * A public read-only list of all the '<em><b>Add On Types</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<AddOnTypes> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Add On Types</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AddOnTypes get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AddOnTypes result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Add On Types</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AddOnTypes getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AddOnTypes result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Add On Types</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AddOnTypes get(int value) {
		switch (value) {
			case CORE_VALUE: return CORE;
			case DATAMODELLING_VALUE: return DATAMODELLING;
			case EVENTSTREAMPROCESSING_VALUE: return EVENTSTREAMPROCESSING;
			case DASHBOARD_VALUE: return DASHBOARD;
			case PROCESS_VALUE: return PROCESS;
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
	private AddOnTypes(int value, String name, String literal) {
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
	
} //AddOnTypes
