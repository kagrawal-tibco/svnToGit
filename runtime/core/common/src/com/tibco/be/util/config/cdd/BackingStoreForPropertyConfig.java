/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Backing Store For Property Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.BackingStoreForPropertyConfig#getReverseReferences <em>Reverse References</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.BackingStoreForPropertyConfig#getMaxSize <em>Max Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.BackingStoreForPropertyConfig#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreForPropertyConfig()
 * @model extendedMetaData="name='backing-store-for-property-type' kind='elementOnly'"
 * @generated
 */
public interface BackingStoreForPropertyConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Reverse References</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Reverse References</em>' containment reference.
	 * @see #setReverseReferences(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreForPropertyConfig_ReverseReferences()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='reverse-references' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getReverseReferences();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.BackingStoreForPropertyConfig#getReverseReferences <em>Reverse References</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reverse References</em>' containment reference.
	 * @see #getReverseReferences()
	 * @generated
	 */
	void setReverseReferences(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Max Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max Size</em>' containment reference.
	 * @see #setMaxSize(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreForPropertyConfig_MaxSize()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='max-size' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getMaxSize();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.BackingStoreForPropertyConfig#getMaxSize <em>Max Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Size</em>' containment reference.
	 * @see #getMaxSize()
	 * @generated
	 */
	void setMaxSize(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' containment reference.
	 * @see #setName(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreForPropertyConfig_Name()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.BackingStoreForPropertyConfig#getName <em>Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' containment reference.
	 * @see #getName()
	 * @generated
	 */
	void setName(OverrideConfig value);

} // BackingStoreForPropertyConfig
