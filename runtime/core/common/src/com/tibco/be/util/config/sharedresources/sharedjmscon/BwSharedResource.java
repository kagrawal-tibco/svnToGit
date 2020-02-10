/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjmscon;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bw Shared Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource#getResourceType <em>Resource Type</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource#getConfig <em>Config</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getBwSharedResource()
 * @model extendedMetaData="name='BWSharedResource-type' kind='elementOnly'"
 * @generated
 */
public interface BwSharedResource extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getBwSharedResource_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Resource Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.be.util.config.sharedresources.sharedjmscon.ResourceType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource Type</em>' attribute.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.ResourceType
	 * @see #isSetResourceType()
	 * @see #unsetResourceType()
	 * @see #setResourceType(ResourceType)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getBwSharedResource_ResourceType()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='resourceType' namespace='##targetNamespace'"
	 * @generated
	 */
	ResourceType getResourceType();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource#getResourceType <em>Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource Type</em>' attribute.
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.ResourceType
	 * @see #isSetResourceType()
	 * @see #unsetResourceType()
	 * @see #getResourceType()
	 * @generated
	 */
	void setResourceType(ResourceType value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource#getResourceType <em>Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetResourceType()
	 * @see #getResourceType()
	 * @see #setResourceType(ResourceType)
	 * @generated
	 */
	void unsetResourceType();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource#getResourceType <em>Resource Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Resource Type</em>' attribute is set.
	 * @see #unsetResourceType()
	 * @see #getResourceType()
	 * @see #setResourceType(ResourceType)
	 * @generated
	 */
	boolean isSetResourceType();

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getBwSharedResource_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='description' namespace='##targetNamespace'"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Config</em>' containment reference.
	 * @see #setConfig(Config)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getBwSharedResource_Config()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='config' namespace='##targetNamespace'"
	 * @generated
	 */
	Config getConfig();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.BwSharedResource#getConfig <em>Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Config</em>' containment reference.
	 * @see #getConfig()
	 * @generated
	 */
	void setConfig(Config value);

} // BwSharedResource
