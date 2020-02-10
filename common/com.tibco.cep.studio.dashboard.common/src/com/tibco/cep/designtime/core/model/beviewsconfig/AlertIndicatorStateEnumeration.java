/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Alert Indicator State Enumeration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateEnumeration#getFieldName <em>Field Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateEnumeration#getMapping <em>Mapping</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getAlertIndicatorStateEnumeration()
 * @model extendedMetaData="name='AlertIndicatorStateEnumeration' kind='elementOnly'"
 * @generated
 */
public interface AlertIndicatorStateEnumeration extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Field Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Field Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Field Name</em>' attribute.
	 * @see #setFieldName(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getAlertIndicatorStateEnumeration_FieldName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='fieldName'"
	 * @generated
	 */
	String getFieldName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateEnumeration#getFieldName <em>Field Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Field Name</em>' attribute.
	 * @see #getFieldName()
	 * @generated
	 */
	void setFieldName(String value);

	/**
	 * Returns the value of the '<em><b>Mapping</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateMap}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapping</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getAlertIndicatorStateEnumeration_Mapping()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='mapping'"
	 * @generated
	 */
	EList<AlertIndicatorStateMap> getMapping();

} // AlertIndicatorStateEnumeration
