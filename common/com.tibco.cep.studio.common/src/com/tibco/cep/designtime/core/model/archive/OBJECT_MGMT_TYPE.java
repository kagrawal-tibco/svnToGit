/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>OBJECT MGMT TYPE</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getOBJECT_MGMT_TYPE()
 * @model
 * @generated
 */
public enum OBJECT_MGMT_TYPE implements Enumerator {
	/**
	 * The '<em><b>In Memory</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #IN_MEMORY_VALUE
	 * @generated
	 * @ordered
	 */
	IN_MEMORY(0, "InMemory", "InMemory"),

	/**
	 * The '<em><b>Cache</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CACHE_VALUE
	 * @generated
	 * @ordered
	 */
	CACHE(1, "Cache", "Cache"),

	/**
	 * The '<em><b>Persistence</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PERSISTENCE_VALUE
	 * @generated
	 * @ordered
	 */
	PERSISTENCE(2, "Persistence", "Persistence");

	/**
	 * The '<em><b>In Memory</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>In Memory</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #IN_MEMORY
	 * @model name="InMemory"
	 * @generated
	 * @ordered
	 */
	public static final int IN_MEMORY_VALUE = 0;

	/**
	 * The '<em><b>Cache</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Cache</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CACHE
	 * @model name="Cache"
	 * @generated
	 * @ordered
	 */
	public static final int CACHE_VALUE = 1;

	/**
	 * The '<em><b>Persistence</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Persistence</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PERSISTENCE
	 * @model name="Persistence"
	 * @generated
	 * @ordered
	 */
	public static final int PERSISTENCE_VALUE = 2;

	/**
	 * An array of all the '<em><b>OBJECT MGMT TYPE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final OBJECT_MGMT_TYPE[] VALUES_ARRAY =
		new OBJECT_MGMT_TYPE[] {
			IN_MEMORY,
			CACHE,
			PERSISTENCE,
		};

	/**
	 * A public read-only list of all the '<em><b>OBJECT MGMT TYPE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<OBJECT_MGMT_TYPE> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>OBJECT MGMT TYPE</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OBJECT_MGMT_TYPE get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OBJECT_MGMT_TYPE result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>OBJECT MGMT TYPE</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OBJECT_MGMT_TYPE getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OBJECT_MGMT_TYPE result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>OBJECT MGMT TYPE</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OBJECT_MGMT_TYPE get(int value) {
		switch (value) {
			case IN_MEMORY_VALUE: return IN_MEMORY;
			case CACHE_VALUE: return CACHE;
			case PERSISTENCE_VALUE: return PERSISTENCE;
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
	private OBJECT_MGMT_TYPE(int value, String name, String literal) {
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
	
} //OBJECT_MGMT_TYPE
