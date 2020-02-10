/**
 */
package com.tibco.cep.studio.common.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>XPATH VERSION</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getXPATH_VERSION()
 * @model
 * @generated
 */
public enum XPATH_VERSION implements Enumerator {
	/**
	 * The '<em><b>XPATH 10</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #XPATH_10_VALUE
	 * @generated
	 * @ordered
	 */
	XPATH_10(0, "XPATH_1_0", "1.0"),

	/**
	 * The '<em><b>XPATH 20</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #XPATH_20_VALUE
	 * @generated
	 * @ordered
	 */
	XPATH_20(1, "XPATH_2_0", "2.0");

	/**
	 * The '<em><b>XPATH 10</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>XPATH 10</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #XPATH_10
	 * @model name="XPATH_1_0" literal="1.0"
	 * @generated
	 * @ordered
	 */
	public static final int XPATH_10_VALUE = 0;

	/**
	 * The '<em><b>XPATH 20</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>XPATH 20</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #XPATH_20
	 * @model name="XPATH_2_0" literal="2.0"
	 * @generated
	 * @ordered
	 */
	public static final int XPATH_20_VALUE = 1;

	/**
	 * An array of all the '<em><b>XPATH VERSION</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final XPATH_VERSION[] VALUES_ARRAY =
		new XPATH_VERSION[] {
			XPATH_10,
			XPATH_20,
		};

	/**
	 * A public read-only list of all the '<em><b>XPATH VERSION</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<XPATH_VERSION> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>XPATH VERSION</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static XPATH_VERSION get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			XPATH_VERSION result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>XPATH VERSION</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static XPATH_VERSION getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			XPATH_VERSION result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>XPATH VERSION</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static XPATH_VERSION get(int value) {
		switch (value) {
			case XPATH_10_VALUE: return XPATH_10;
			case XPATH_20_VALUE: return XPATH_20;
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
	private XPATH_VERSION(int value, String name, String literal) {
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
	
} //XPATH_VERSION
