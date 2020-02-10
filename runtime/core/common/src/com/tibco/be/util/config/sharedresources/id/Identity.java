/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.id;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Identity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.Identity#getDesigner <em>Designer</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.Identity#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.Identity#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.Identity#getObjectType <em>Object Type</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.Identity#getUsername <em>Username</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.Identity#getPassword <em>Password</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.Identity#getFileType <em>File Type</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.Identity#getUrl <em>Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.Identity#getPassPhrase <em>Pass Phrase</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.Identity#getCertUrl <em>Cert Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.Identity#getPrivateKeyUrl <em>Private Key Url</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getIdentity()
 * @model extendedMetaData="name='identity-type' kind='elementOnly'"
 * @generated
 */
public interface Identity extends EObject {
	/**
	 * Returns the value of the '<em><b>Designer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Designer</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Designer</em>' containment reference.
	 * @see #setDesigner(Designer)
	 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getIdentity_Designer()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='designer'"
	 * @generated
	 */
	Designer getDesigner();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.id.Identity#getDesigner <em>Designer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Designer</em>' containment reference.
	 * @see #getDesigner()
	 * @generated
	 */
	void setDesigner(Designer value);

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
	 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getIdentity_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.id.Identity#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

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
	 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getIdentity_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='description'"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.id.Identity#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Object Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Type</em>' attribute.
	 * @see #setObjectType(Object)
	 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getIdentity_ObjectType()
	 * @model dataType="com.tibco.be.util.config.sharedresources.id.ObjectType"
	 *        extendedMetaData="kind='element' name='objectType'"
	 * @generated
	 */
	Object getObjectType();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.id.Identity#getObjectType <em>Object Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Type</em>' attribute.
	 * @see #getObjectType()
	 * @generated
	 */
	void setObjectType(Object value);

	/**
	 * Returns the value of the '<em><b>Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Username</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Username</em>' attribute.
	 * @see #setUsername(String)
	 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getIdentity_Username()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='username'"
	 * @generated
	 */
	String getUsername();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.id.Identity#getUsername <em>Username</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Username</em>' attribute.
	 * @see #getUsername()
	 * @generated
	 */
	void setUsername(String value);

	/**
	 * Returns the value of the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Password</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Password</em>' attribute.
	 * @see #setPassword(String)
	 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getIdentity_Password()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='password'"
	 * @generated
	 */
	String getPassword();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.id.Identity#getPassword <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Password</em>' attribute.
	 * @see #getPassword()
	 * @generated
	 */
	void setPassword(String value);

	/**
	 * Returns the value of the '<em><b>File Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Type</em>' attribute.
	 * @see #setFileType(Object)
	 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getIdentity_FileType()
	 * @model dataType="com.tibco.be.util.config.sharedresources.id.FileType"
	 *        extendedMetaData="kind='element' name='fileType'"
	 * @generated
	 */
	Object getFileType();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.id.Identity#getFileType <em>File Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Type</em>' attribute.
	 * @see #getFileType()
	 * @generated
	 */
	void setFileType(Object value);

	/**
	 * Returns the value of the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Url</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Url</em>' attribute.
	 * @see #setUrl(String)
	 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getIdentity_Url()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='url'"
	 * @generated
	 */
	String getUrl();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.id.Identity#getUrl <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Url</em>' attribute.
	 * @see #getUrl()
	 * @generated
	 */
	void setUrl(String value);

	/**
	 * Returns the value of the '<em><b>Pass Phrase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pass Phrase</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pass Phrase</em>' attribute.
	 * @see #setPassPhrase(String)
	 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getIdentity_PassPhrase()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='passPhrase'"
	 * @generated
	 */
	String getPassPhrase();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.id.Identity#getPassPhrase <em>Pass Phrase</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pass Phrase</em>' attribute.
	 * @see #getPassPhrase()
	 * @generated
	 */
	void setPassPhrase(String value);

	/**
	 * Returns the value of the '<em><b>Cert Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cert Url</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cert Url</em>' attribute.
	 * @see #setCertUrl(String)
	 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getIdentity_CertUrl()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='certUrl'"
	 * @generated
	 */
	String getCertUrl();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.id.Identity#getCertUrl <em>Cert Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cert Url</em>' attribute.
	 * @see #getCertUrl()
	 * @generated
	 */
	void setCertUrl(String value);

	/**
	 * Returns the value of the '<em><b>Private Key Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Private Key Url</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Private Key Url</em>' attribute.
	 * @see #setPrivateKeyUrl(String)
	 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getIdentity_PrivateKeyUrl()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='privateKeyUrl'"
	 * @generated
	 */
	String getPrivateKeyUrl();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.id.Identity#getPrivateKeyUrl <em>Private Key Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Private Key Url</em>' attribute.
	 * @see #getPrivateKeyUrl()
	 * @generated
	 */
	void setPrivateKeyUrl(String value);

} // Identity
