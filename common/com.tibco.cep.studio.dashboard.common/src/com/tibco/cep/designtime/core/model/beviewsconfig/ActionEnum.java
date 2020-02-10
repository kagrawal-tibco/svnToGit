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
 * A representation of the literals of the enumeration '<em><b>Action Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * 
 * 			Represents the enumeration of the values to be used as 'type' in a 'action'
 *             
 * <!-- end-model-doc -->
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getActionEnum()
 * @model extendedMetaData="name='ActionEnum'"
 * @generated
 */
public enum ActionEnum implements Enumerator {
	/**
	 * The '<em><b>Launchquery</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LAUNCHQUERY_VALUE
	 * @generated
	 * @ordered
	 */
	LAUNCHQUERY(0, "launchquery", "launchquery"),

	/**
	 * The '<em><b>Launchvisualization</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LAUNCHVISUALIZATION_VALUE
	 * @generated
	 * @ordered
	 */
	LAUNCHVISUALIZATION(1, "launchvisualization", "launchvisualization");

	/**
	 * The '<em><b>Launchquery</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates a action which launches a query (using the search/drilldown interface)
	 * <!-- end-model-doc -->
	 * @see #LAUNCHQUERY
	 * @model name="launchquery"
	 * @generated
	 * @ordered
	 */
	public static final int LAUNCHQUERY_VALUE = 0;

	/**
	 * The '<em><b>Launchvisualization</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates a action which launches a visualization (using a visualization holding panel)
	 * <!-- end-model-doc -->
	 * @see #LAUNCHVISUALIZATION
	 * @model name="launchvisualization"
	 * @generated
	 * @ordered
	 */
	public static final int LAUNCHVISUALIZATION_VALUE = 1;

	/**
	 * An array of all the '<em><b>Action Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ActionEnum[] VALUES_ARRAY =
		new ActionEnum[] {
			LAUNCHQUERY,
			LAUNCHVISUALIZATION,
		};

	/**
	 * A public read-only list of all the '<em><b>Action Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ActionEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Action Enum</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ActionEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ActionEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Action Enum</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ActionEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ActionEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Action Enum</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ActionEnum get(int value) {
		switch (value) {
			case LAUNCHQUERY_VALUE: return LAUNCHQUERY;
			case LAUNCHVISUALIZATION_VALUE: return LAUNCHVISUALIZATION;
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
	private ActionEnum(int value, String name, String literal) {
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
	
} //ActionEnum
