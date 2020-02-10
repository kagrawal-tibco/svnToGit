/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Advanced Entity Setting</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting#getEntity <em>Entity</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting#isDeployed <em>Deployed</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting#getRecoveryFunction <em>Recovery Function</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting#getCacheMode <em>Cache Mode</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getAdvancedEntitySetting()
 * @model
 * @generated
 */
public interface AdvancedEntitySetting extends EObject {
	/**
	 * Returns the value of the '<em><b>Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity</em>' attribute.
	 * @see #setEntity(String)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getAdvancedEntitySetting_Entity()
	 * @model
	 * @generated
	 */
	String getEntity();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting#getEntity <em>Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entity</em>' attribute.
	 * @see #getEntity()
	 * @generated
	 */
	void setEntity(String value);

	/**
	 * Returns the value of the '<em><b>Deployed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deployed</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deployed</em>' attribute.
	 * @see #setDeployed(boolean)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getAdvancedEntitySetting_Deployed()
	 * @model
	 * @generated
	 */
	boolean isDeployed();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting#isDeployed <em>Deployed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deployed</em>' attribute.
	 * @see #isDeployed()
	 * @generated
	 */
	void setDeployed(boolean value);

	/**
	 * Returns the value of the '<em><b>Recovery Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recovery Function</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Recovery Function</em>' attribute.
	 * @see #setRecoveryFunction(String)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getAdvancedEntitySetting_RecoveryFunction()
	 * @model
	 * @generated
	 */
	String getRecoveryFunction();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting#getRecoveryFunction <em>Recovery Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Recovery Function</em>' attribute.
	 * @see #getRecoveryFunction()
	 * @generated
	 */
	void setRecoveryFunction(String value);

	/**
	 * Returns the value of the '<em><b>Cache Mode</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.archive.CACHE_MODE}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Mode</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.archive.CACHE_MODE
	 * @see #setCacheMode(CACHE_MODE)
	 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage#getAdvancedEntitySetting_CacheMode()
	 * @model
	 * @generated
	 */
	CACHE_MODE getCacheMode();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting#getCacheMode <em>Cache Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Mode</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.archive.CACHE_MODE
	 * @see #getCacheMode()
	 * @generated
	 */
	void setCacheMode(CACHE_MODE value);

} // AdvancedEntitySetting
