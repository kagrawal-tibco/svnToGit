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
 * A representation of the literals of the enumeration '<em><b>CACHE MODE</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getCACHE_MODE()
 * @model
 * @generated
 */
public enum CACHE_MODE implements Enumerator {
	/**
	 * The '<em><b>Cache And Memory</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CACHE_AND_MEMORY_VALUE
	 * @generated
	 * @ordered
	 */
	CACHE_AND_MEMORY(0, "CacheAndMemory", "CacheAndMemory"),

	/**
	 * The '<em><b>Cache Only</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CACHE_ONLY_VALUE
	 * @generated
	 * @ordered
	 */
	CACHE_ONLY(1, "CacheOnly", "CacheOnly"),

	/**
	 * The '<em><b>Memory Only</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MEMORY_ONLY_VALUE
	 * @generated
	 * @ordered
	 */
	MEMORY_ONLY(2, "MemoryOnly", "MemoryOnly");

	/**
	 * The '<em><b>Cache And Memory</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Cache And Memory</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CACHE_AND_MEMORY
	 * @model name="CacheAndMemory"
	 * @generated
	 * @ordered
	 */
	public static final int CACHE_AND_MEMORY_VALUE = 0;

	/**
	 * The '<em><b>Cache Only</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Cache Only</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CACHE_ONLY
	 * @model name="CacheOnly"
	 * @generated
	 * @ordered
	 */
	public static final int CACHE_ONLY_VALUE = 1;

	/**
	 * The '<em><b>Memory Only</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Memory Only</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MEMORY_ONLY
	 * @model name="MemoryOnly"
	 * @generated
	 * @ordered
	 */
	public static final int MEMORY_ONLY_VALUE = 2;

	/**
	 * An array of all the '<em><b>CACHE MODE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final CACHE_MODE[] VALUES_ARRAY =
		new CACHE_MODE[] {
			CACHE_AND_MEMORY,
			CACHE_ONLY,
			MEMORY_ONLY,
		};

	/**
	 * A public read-only list of all the '<em><b>CACHE MODE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<CACHE_MODE> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>CACHE MODE</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CACHE_MODE get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CACHE_MODE result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>CACHE MODE</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CACHE_MODE getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CACHE_MODE result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>CACHE MODE</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CACHE_MODE get(int value) {
		switch (value) {
			case CACHE_AND_MEMORY_VALUE: return CACHE_AND_MEMORY;
			case CACHE_ONLY_VALUE: return CACHE_ONLY;
			case MEMORY_ONLY_VALUE: return MEMORY_ONLY;
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
	private CACHE_MODE(int value, String name, String literal) {
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
	
} //CACHE_MODE
