/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Notification Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.NotificationConfig#getProperty <em>Property</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.NotificationConfig#getRange <em>Range</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.NotificationConfig#getTolerance <em>Tolerance</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getNotificationConfig()
 * @model extendedMetaData="name='notification-type' kind='elementOnly'"
 * @generated
 */
public interface NotificationConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Property</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.cdd.PropertyConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' containment reference list.
	 * @see com.tibco.be.util.config.cdd.CddPackage#getNotificationConfig_Property()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='property' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<PropertyConfig> getProperty();

	/**
	 * Returns the value of the '<em><b>Range</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Range</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Range</em>' attribute.
	 * @see #setRange(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getNotificationConfig_Range()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='range'"
	 * @generated
	 */
	String getRange();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.NotificationConfig#getRange <em>Range</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Range</em>' attribute.
	 * @see #getRange()
	 * @generated
	 */
	void setRange(String value);

	/**
	 * Returns the value of the '<em><b>Tolerance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tolerance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tolerance</em>' attribute.
	 * @see #setTolerance(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getNotificationConfig_Tolerance()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='tolerance'"
	 * @generated
	 */
	String getTolerance();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.NotificationConfig#getTolerance <em>Tolerance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tolerance</em>' attribute.
	 * @see #getTolerance()
	 * @generated
	 */
	void setTolerance(String value);

} // NotificationConfig
