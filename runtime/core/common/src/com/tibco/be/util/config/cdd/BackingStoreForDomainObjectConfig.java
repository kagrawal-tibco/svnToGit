/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Backing Store For Domain Object Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.BackingStoreForDomainObjectConfig#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.BackingStoreForDomainObjectConfig#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.BackingStoreForDomainObjectConfig#getTableName <em>Table Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreForDomainObjectConfig()
 * @model extendedMetaData="name='backing-store-for-domain-object-type' kind='elementOnly'"
 * @generated
 */
public interface BackingStoreForDomainObjectConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Backing store configuration for the domain
	 * 						object's properties.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference.
	 * @see #setProperties(BackingStoreForPropertiesConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreForDomainObjectConfig_Properties()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='properties' namespace='##targetNamespace'"
	 * @generated
	 */
	BackingStoreForPropertiesConfig getProperties();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.BackingStoreForDomainObjectConfig#getProperties <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Properties</em>' containment reference.
	 * @see #getProperties()
	 * @generated
	 */
	void setProperties(BackingStoreForPropertiesConfig value);

	/**
	 * Returns the value of the '<em><b>Enabled</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 						Enables storage in the backing store, if true.
	 * 					
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Enabled</em>' containment reference.
	 * @see #setEnabled(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreForDomainObjectConfig_Enabled()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='enabled' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getEnabled();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.BackingStoreForDomainObjectConfig#getEnabled <em>Enabled</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enabled</em>' containment reference.
	 * @see #getEnabled()
	 * @generated
	 */
	void setEnabled(OverrideConfig value);

	/**
	 * Returns the value of the '<em><b>Table Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table Name</em>' containment reference.
	 * @see #setTableName(OverrideConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getBackingStoreForDomainObjectConfig_TableName()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='table-name' namespace='##targetNamespace'"
	 * @generated
	 */
	OverrideConfig getTableName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.BackingStoreForDomainObjectConfig#getTableName <em>Table Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table Name</em>' containment reference.
	 * @see #getTableName()
	 * @generated
	 */
	void setTableName(OverrideConfig value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return new java.util.Properties();'"
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // BackingStoreForDomainObjectConfig
