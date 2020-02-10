/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjndiconfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Resource Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage#getResourceType()
 * @model extendedMetaData="name='resourceType_._type'"
 * @generated
 */
public enum ResourceType implements Enumerator {
	/**
	 * The '<em><b>Ae Shared JNDI Shared Configuration</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AE_SHARED_JNDI_SHARED_CONFIGURATION_VALUE
	 * @generated
	 * @ordered
	 */
	AE_SHARED_JNDI_SHARED_CONFIGURATION(0, "aeSharedJNDISharedConfiguration", "ae.shared.JNDISharedConfiguration");

	/**
	 * The '<em><b>Ae Shared JNDI Shared Configuration</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Ae Shared JNDI Shared Configuration</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #AE_SHARED_JNDI_SHARED_CONFIGURATION
	 * @model name="aeSharedJNDISharedConfiguration" literal="ae.shared.JNDISharedConfiguration"
	 * @generated
	 * @ordered
	 */
	public static final int AE_SHARED_JNDI_SHARED_CONFIGURATION_VALUE = 0;

	/**
	 * An array of all the '<em><b>Resource Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ResourceType[] VALUES_ARRAY =
		new ResourceType[] {
			AE_SHARED_JNDI_SHARED_CONFIGURATION,
		};

	/**
	 * A public read-only list of all the '<em><b>Resource Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ResourceType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Resource Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ResourceType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ResourceType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Resource Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ResourceType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ResourceType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Resource Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ResourceType get(int value) {
		switch (value) {
			case AE_SHARED_JNDI_SHARED_CONFIGURATION_VALUE: return AE_SHARED_JNDI_SHARED_CONFIGURATION;
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
	private ResourceType(int value, String name, String literal) {
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
	
} //ResourceType
