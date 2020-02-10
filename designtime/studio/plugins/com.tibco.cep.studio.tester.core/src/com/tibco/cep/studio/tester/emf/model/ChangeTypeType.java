/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Change Type Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getChangeTypeType()
 * @model extendedMetaData="name='ChangeType_._type'"
 * @generated
 */
public enum ChangeTypeType implements Enumerator {
	/**
	 * The '<em><b>ASSERT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ASSERT_VALUE
	 * @generated
	 * @ordered
	 */
	ASSERT(0, "ASSERT", "ASSERT"),

	/**
	 * The '<em><b>MODIFY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODIFY_VALUE
	 * @generated
	 * @ordered
	 */
	MODIFY(1, "MODIFY", "MODIFY"),

	/**
	 * The '<em><b>RETRACT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RETRACT_VALUE
	 * @generated
	 * @ordered
	 */
	RETRACT(2, "RETRACT", "RETRACT");

	/**
	 * The '<em><b>ASSERT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ASSERT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ASSERT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ASSERT_VALUE = 0;

	/**
	 * The '<em><b>MODIFY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>MODIFY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MODIFY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MODIFY_VALUE = 1;

	/**
	 * The '<em><b>RETRACT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RETRACT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RETRACT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int RETRACT_VALUE = 2;

	/**
	 * An array of all the '<em><b>Change Type Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ChangeTypeType[] VALUES_ARRAY =
		new ChangeTypeType[] {
			ASSERT,
			MODIFY,
			RETRACT,
		};

	/**
	 * A public read-only list of all the '<em><b>Change Type Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ChangeTypeType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Change Type Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ChangeTypeType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ChangeTypeType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Change Type Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ChangeTypeType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ChangeTypeType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Change Type Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ChangeTypeType get(int value) {
		switch (value) {
			case ASSERT_VALUE: return ASSERT;
			case MODIFY_VALUE: return MODIFY;
			case RETRACT_VALUE: return RETRACT;
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
	private ChangeTypeType(int value, String name, String literal) {
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
	
} //ChangeTypeType
