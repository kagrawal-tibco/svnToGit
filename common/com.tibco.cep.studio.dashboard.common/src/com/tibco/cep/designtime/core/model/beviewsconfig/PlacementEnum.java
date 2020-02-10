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
 * A representation of the literals of the enumeration '<em><b>Placement Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 * 			Represents the enumeration of the values to be used as 'placement'
 *             
 * <!-- end-model-doc -->
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getPlacementEnum()
 * @model extendedMetaData="name='PlacementEnum'"
 * @generated
 */
public enum PlacementEnum implements Enumerator {
	/**
	 * The '<em><b>Automatic</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AUTOMATIC_VALUE
	 * @generated
	 * @ordered
	 */
	AUTOMATIC(0, "automatic", "automatic"),

	/**
	 * The '<em><b>Continous</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONTINOUS_VALUE
	 * @generated
	 * @ordered
	 */
	CONTINOUS(1, "continous", "continous"),

	/**
	 * The '<em><b>Staggered</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STAGGERED_VALUE
	 * @generated
	 * @ordered
	 */
	STAGGERED(2, "staggered", "staggered"),

	/**
	 * The '<em><b>Skipped</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SKIPPED_VALUE
	 * @generated
	 * @ordered
	 */
	SKIPPED(3, "skipped", "skipped");

	/**
	 * The '<em><b>Automatic</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Automatic</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #AUTOMATIC
	 * @model name="automatic"
	 * @generated
	 * @ordered
	 */
	public static final int AUTOMATIC_VALUE = 0;

	/**
	 * The '<em><b>Continous</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Continous</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CONTINOUS
	 * @model name="continous"
	 * @generated
	 * @ordered
	 */
	public static final int CONTINOUS_VALUE = 1;

	/**
	 * The '<em><b>Staggered</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Staggered</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STAGGERED
	 * @model name="staggered"
	 * @generated
	 * @ordered
	 */
	public static final int STAGGERED_VALUE = 2;

	/**
	 * The '<em><b>Skipped</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Skipped</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SKIPPED
	 * @model name="skipped"
	 * @generated
	 * @ordered
	 */
	public static final int SKIPPED_VALUE = 3;

	/**
	 * An array of all the '<em><b>Placement Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final PlacementEnum[] VALUES_ARRAY =
		new PlacementEnum[] {
			AUTOMATIC,
			CONTINOUS,
			STAGGERED,
			SKIPPED,
		};

	/**
	 * A public read-only list of all the '<em><b>Placement Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<PlacementEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Placement Enum</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PlacementEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PlacementEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Placement Enum</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PlacementEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			PlacementEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Placement Enum</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PlacementEnum get(int value) {
		switch (value) {
			case AUTOMATIC_VALUE: return AUTOMATIC;
			case CONTINOUS_VALUE: return CONTINOUS;
			case STAGGERED_VALUE: return STAGGERED;
			case SKIPPED_VALUE: return SKIPPED;
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
	private PlacementEnum(int value, String name, String literal) {
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
	
} //PlacementEnum
