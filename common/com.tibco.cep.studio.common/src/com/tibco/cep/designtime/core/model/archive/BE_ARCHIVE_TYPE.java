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
 * A representation of the literals of the enumeration '<em><b>BE ARCHIVE TYPE</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getBE_ARCHIVE_TYPE()
 * @model
 * @generated
 */
public enum BE_ARCHIVE_TYPE implements Enumerator {
	/**
	 * The '<em><b>Inference</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INFERENCE_VALUE
	 * @generated
	 * @ordered
	 */
	INFERENCE(0, "Inference", "Inference"),

	/**
	 * The '<em><b>Query</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #QUERY_VALUE
	 * @generated
	 * @ordered
	 */
	QUERY(1, "Query", "Query");

	/**
	 * The '<em><b>Inference</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Inference</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INFERENCE
	 * @model name="Inference"
	 * @generated
	 * @ordered
	 */
	public static final int INFERENCE_VALUE = 0;

	/**
	 * The '<em><b>Query</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Query</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #QUERY
	 * @model name="Query"
	 * @generated
	 * @ordered
	 */
	public static final int QUERY_VALUE = 1;

	/**
	 * An array of all the '<em><b>BE ARCHIVE TYPE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final BE_ARCHIVE_TYPE[] VALUES_ARRAY =
		new BE_ARCHIVE_TYPE[] {
			INFERENCE,
			QUERY,
		};

	/**
	 * A public read-only list of all the '<em><b>BE ARCHIVE TYPE</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<BE_ARCHIVE_TYPE> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>BE ARCHIVE TYPE</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static BE_ARCHIVE_TYPE get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			BE_ARCHIVE_TYPE result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>BE ARCHIVE TYPE</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static BE_ARCHIVE_TYPE getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			BE_ARCHIVE_TYPE result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>BE ARCHIVE TYPE</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static BE_ARCHIVE_TYPE get(int value) {
		switch (value) {
			case INFERENCE_VALUE: return INFERENCE;
			case QUERY_VALUE: return QUERY;
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
	private BE_ARCHIVE_TYPE(int value, String name, String literal) {
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
	
} //BE_ARCHIVE_TYPE
