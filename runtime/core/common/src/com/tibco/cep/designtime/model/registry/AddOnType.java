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
 * A representation of the literals of the enumeration '<em><b>Add On Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getAddOnType()
 * @model extendedMetaData="name='AddOnType'"
 * @generated
 */
public enum AddOnType implements Enumerator {
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
	 * The '<em><b>DECISIONMANAGER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DECISIONMANAGER_VALUE
	 * @generated
	 * @ordered
	 */
	DECISIONMANAGER(3, "DECISIONMANAGER", "DECISION_MANAGER"), /**
	 * The '<em><b>VIEWS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VIEWS_VALUE
	 * @generated
	 * @ordered
	 */
	VIEWS(4, "VIEWS", "VIEWS"), /**
	 * The '<em><b>PROCESS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROCESS_VALUE
	 * @generated
	 * @ordered
	 */
	PROCESS(5, "PROCESS", "PROCESS"), /**
	* The '<em><b>LIVEVIEW</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LIVEVIEW_VALUE
	 * @generated
	 * @ordered
	 */
	LIVEVIEW(6, "LIVEVIEW", "LIVEVIEW");

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
	 * The '<em><b>DECISIONMANAGER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DECISIONMANAGER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DECISIONMANAGER
	 * @model literal="DECISION_MANAGER"
	 * @generated
	 * @ordered
	 */
	public static final int DECISIONMANAGER_VALUE = 3;

	/**
	 * The '<em><b>VIEWS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>VIEWS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #VIEWS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int VIEWS_VALUE = 4;

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
	public static final int PROCESS_VALUE = 5;
	
	/**
	 * The '<em><b>LIVEVIEW</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>LIVEVIEW</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LIVEVIEW
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LIVEVIEW_VALUE = 6;

	/**
	 * An array of all the '<em><b>Add On Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final AddOnType[] VALUES_ARRAY =
		new AddOnType[] {
			CORE,
			DATAMODELLING,
			EVENTSTREAMPROCESSING,
			DECISIONMANAGER,
			VIEWS,
			PROCESS,
			LIVEVIEW
		};

	/**
	 * A public read-only list of all the '<em><b>Add On Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<AddOnType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Add On Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AddOnType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AddOnType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Add On Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AddOnType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AddOnType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Add On Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AddOnType get(int value) {
		switch (value) {
			case CORE_VALUE: return CORE;
			case DATAMODELLING_VALUE: return DATAMODELLING;
			case EVENTSTREAMPROCESSING_VALUE: return EVENTSTREAMPROCESSING;
			case DECISIONMANAGER_VALUE: return DECISIONMANAGER;
			case VIEWS_VALUE: return VIEWS;
			case PROCESS_VALUE: return PROCESS;
			case LIVEVIEW_VALUE: return LIVEVIEW;
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
	private AddOnType(int value, String name, String literal) {
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
	
} //AddOnType
