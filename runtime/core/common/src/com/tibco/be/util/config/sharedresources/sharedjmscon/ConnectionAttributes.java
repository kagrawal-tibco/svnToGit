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
 * A representation of the model object '<em><b>Connection Attributes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes#getUsername <em>Username</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes#getPassword <em>Password</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes#getClientId <em>Client Id</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes#getAutoGenClientID <em>Auto Gen Client ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getConnectionAttributes()
 * @model extendedMetaData="name='ConnectionAttributes-type' kind='elementOnly'"
 * @generated
 */
public interface ConnectionAttributes extends EObject {
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
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getConnectionAttributes_Username()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='username' namespace='##targetNamespace'"
	 * @generated
	 */
	String getUsername();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes#getUsername <em>Username</em>}' attribute.
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
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getConnectionAttributes_Password()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='password' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPassword();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes#getPassword <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Password</em>' attribute.
	 * @see #getPassword()
	 * @generated
	 */
	void setPassword(String value);

	/**
	 * Returns the value of the '<em><b>Client Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Client Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Client Id</em>' attribute.
	 * @see #setClientId(String)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getConnectionAttributes_ClientId()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='clientID' namespace='##targetNamespace'"
	 * @generated
	 */
	String getClientId();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes#getClientId <em>Client Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Client Id</em>' attribute.
	 * @see #getClientId()
	 * @generated
	 */
	void setClientId(String value);

	/**
	 * Returns the value of the '<em><b>Auto Gen Client ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auto Gen Client ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auto Gen Client ID</em>' attribute.
	 * @see #setAutoGenClientID(Object)
	 * @see com.tibco.be.util.config.sharedresources.sharedjmscon.SharedjmsconPackage#getConnectionAttributes_AutoGenClientID()
	 * @model dataType="com.tibco.be.util.config.sharedresources.basetypes.BooleanOrGvs" required="true"
	 *        extendedMetaData="kind='element' name='autoGenClientID' namespace='##targetNamespace'"
	 * @generated
	 */
	Object getAutoGenClientID();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.sharedjmscon.ConnectionAttributes#getAutoGenClientID <em>Auto Gen Client ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auto Gen Client ID</em>' attribute.
	 * @see #getAutoGenClientID()
	 * @generated
	 */
	void setAutoGenClientID(Object value);

} // ConnectionAttributes
