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
 * A representation of the model object '<em><b>Object Management Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.ObjectManagementConfig#getMemoryManager <em>Memory Manager</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ObjectManagementConfig#getCacheManager <em>Cache Manager</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.ObjectManagementConfig#getDbConcepts <em>Db Concepts</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getObjectManagementConfig()
 * @model extendedMetaData="name='object-management-type' kind='elementOnly'"
 * @generated
 */
public interface ObjectManagementConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Memory Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Memory Manager</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Memory Manager</em>' containment reference.
	 * @see #setMemoryManager(MemoryManagerConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getObjectManagementConfig_MemoryManager()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='memory-manager' namespace='##targetNamespace'"
	 * @generated
	 */
	MemoryManagerConfig getMemoryManager();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ObjectManagementConfig#getMemoryManager <em>Memory Manager</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Memory Manager</em>' containment reference.
	 * @see #getMemoryManager()
	 * @generated
	 */
	void setMemoryManager(MemoryManagerConfig value);

	/**
	 * Returns the value of the '<em><b>Cache Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Manager</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Manager</em>' containment reference.
	 * @see #setCacheManager(CacheManagerConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getObjectManagementConfig_CacheManager()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='cache-manager' namespace='##targetNamespace'"
	 * @generated
	 */
	CacheManagerConfig getCacheManager();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ObjectManagementConfig#getCacheManager <em>Cache Manager</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Manager</em>' containment reference.
	 * @see #getCacheManager()
	 * @generated
	 */
	void setCacheManager(CacheManagerConfig value);

	/**
	 * Returns the value of the '<em><b>Db Concepts</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Db Concepts</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Db Concepts</em>' containment reference.
	 * @see #setDbConcepts(DbConceptsConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getObjectManagementConfig_DbConcepts()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='db-concepts' namespace='##targetNamespace'"
	 * @generated
	 */
	DbConceptsConfig getDbConcepts();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.ObjectManagementConfig#getDbConcepts <em>Db Concepts</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Db Concepts</em>' containment reference.
	 * @see #getDbConcepts()
	 * @generated
	 */
	void setDbConcepts(DbConceptsConfig value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Map<Object, Object> toProperties();

} // ObjectManagementConfig
