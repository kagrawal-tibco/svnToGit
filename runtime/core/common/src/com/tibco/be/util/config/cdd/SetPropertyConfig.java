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
 * A representation of the model object '<em><b>Set Property Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.SetPropertyConfig#getChildClusterMember <em>Child Cluster Member</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.SetPropertyConfig#getNotification <em>Notification</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.SetPropertyConfig#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.SetPropertyConfig#getSetPropertyName <em>Set Property Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.SetPropertyConfig#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getSetPropertyConfig()
 * @model extendedMetaData="name='set-property-type' kind='elementOnly'"
 * @generated
 */
public interface SetPropertyConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Child Cluster Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Child Cluster Member</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child Cluster Member</em>' containment reference.
	 * @see #setChildClusterMember(ChildClusterMemberConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSetPropertyConfig_ChildClusterMember()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='child-cluster-member' namespace='##targetNamespace'"
	 * @generated
	 */
	ChildClusterMemberConfig getChildClusterMember();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SetPropertyConfig#getChildClusterMember <em>Child Cluster Member</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Child Cluster Member</em>' containment reference.
	 * @see #getChildClusterMember()
	 * @generated
	 */
	void setChildClusterMember(ChildClusterMemberConfig value);

	/**
	 * Returns the value of the '<em><b>Notification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Notification</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Notification</em>' containment reference.
	 * @see #setNotification(NotificationConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSetPropertyConfig_Notification()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='notification' namespace='##targetNamespace'"
	 * @generated
	 */
	NotificationConfig getNotification();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SetPropertyConfig#getNotification <em>Notification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Notification</em>' containment reference.
	 * @see #getNotification()
	 * @generated
	 */
	void setNotification(NotificationConfig value);

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
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSetPropertyConfig_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SetPropertyConfig#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Set Property Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Set Property Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Set Property Name</em>' attribute.
	 * @see #setSetPropertyName(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSetPropertyConfig_SetPropertyName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='set-property-name'"
	 * @generated
	 */
	String getSetPropertyName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SetPropertyConfig#getSetPropertyName <em>Set Property Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Set Property Name</em>' attribute.
	 * @see #getSetPropertyName()
	 * @generated
	 */
	void setSetPropertyName(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getSetPropertyConfig_Value()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='value'"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.SetPropertyConfig#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // SetPropertyConfig
