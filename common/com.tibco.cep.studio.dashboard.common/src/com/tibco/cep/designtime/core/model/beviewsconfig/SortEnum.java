/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Sort Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getSortEnum()
 * @model extendedMetaData="name='SortEnum'"
 * @generated
 */
public enum SortEnum implements Enumerator {
	/**
	 * The '<em><b>Unsorted</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNSORTED_VALUE
	 * @generated
	 * @ordered
	 */
	UNSORTED(0, "unsorted", "unsorted"),

	/**
	 * The '<em><b>Ascending</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ASCENDING_VALUE
	 * @generated
	 * @ordered
	 */
	ASCENDING(1, "ascending", "ascending"),

	/**
	 * The '<em><b>Descending</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DESCENDING_VALUE
	 * @generated
	 * @ordered
	 */
	DESCENDING(2, "descending", "descending");

	/**
	 * The '<em><b>Unsorted</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Unsorted</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UNSORTED
	 * @model name="unsorted"
	 * @generated
	 * @ordered
	 */
	public static final int UNSORTED_VALUE = 0;

	/**
	 * The '<em><b>Ascending</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Ascending</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ASCENDING
	 * @model name="ascending"
	 * @generated
	 * @ordered
	 */
	public static final int ASCENDING_VALUE = 1;

	/**
	 * The '<em><b>Descending</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Descending</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DESCENDING
	 * @model name="descending"
	 * @generated
	 * @ordered
	 */
	public static final int DESCENDING_VALUE = 2;

	/**
	 * An array of all the '<em><b>Sort Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final SortEnum[] VALUES_ARRAY =
		new SortEnum[] {
			UNSORTED,
			ASCENDING,
			DESCENDING,
		};

	/**
	 * A public read-only list of all the '<em><b>Sort Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<SortEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Sort Enum</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SortEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SortEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Sort Enum</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SortEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SortEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Sort Enum</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SortEnum get(int value) {
		switch (value) {
			case UNSORTED_VALUE: return UNSORTED;
			case ASCENDING_VALUE: return ASCENDING;
			case DESCENDING_VALUE: return DESCENDING;
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
	private SortEnum(int value, String name, String literal) {
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
	
} //SortEnum
