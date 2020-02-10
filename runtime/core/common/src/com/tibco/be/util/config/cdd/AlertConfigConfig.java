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
 * A representation of the model object '<em><b>Alert Config Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.AlertConfigConfig#getCondition <em>Condition</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.AlertConfigConfig#getProjection <em>Projection</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.AlertConfigConfig#getAlertName <em>Alert Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getAlertConfigConfig()
 * @model extendedMetaData="name='alert-config-type' kind='elementOnly'"
 * @generated
 */
public interface AlertConfigConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(ConditionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getAlertConfigConfig_Condition()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='condition' namespace='##targetNamespace'"
	 * @generated
	 */
	ConditionConfig getCondition();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.AlertConfigConfig#getCondition <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(ConditionConfig value);

	/**
	 * Returns the value of the '<em><b>Projection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Projection</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Projection</em>' containment reference.
	 * @see #setProjection(ProjectionConfig)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getAlertConfigConfig_Projection()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='projection' namespace='##targetNamespace'"
	 * @generated
	 */
	ProjectionConfig getProjection();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.AlertConfigConfig#getProjection <em>Projection</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Projection</em>' containment reference.
	 * @see #getProjection()
	 * @generated
	 */
	void setProjection(ProjectionConfig value);

	/**
	 * Returns the value of the '<em><b>Alert Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alert Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alert Name</em>' attribute.
	 * @see #setAlertName(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getAlertConfigConfig_AlertName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='alert-name'"
	 * @generated
	 */
	String getAlertName();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.AlertConfigConfig#getAlertName <em>Alert Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alert Name</em>' attribute.
	 * @see #getAlertName()
	 * @generated
	 */
	void setAlertName(String value);

} // AlertConfigConfig
