/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Alert Indicator State Map</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateMap#getFieldValue <em>Field Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateMap#getIndicatorState <em>Indicator State</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getAlertIndicatorStateMap()
 * @model extendedMetaData="name='AlertIndicatorStateMap' kind='elementOnly'"
 * @generated
 */
public interface AlertIndicatorStateMap extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Field Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Field Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Field Value</em>' attribute.
	 * @see #setFieldValue(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getAlertIndicatorStateMap_FieldValue()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='fieldValue'"
	 * @generated
	 */
	String getFieldValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateMap#getFieldValue <em>Field Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Field Value</em>' attribute.
	 * @see #getFieldValue()
	 * @generated
	 */
	void setFieldValue(String value);

	/**
	 * Returns the value of the '<em><b>Indicator State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Indicator State</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Indicator State</em>' attribute.
	 * @see #setIndicatorState(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getAlertIndicatorStateMap_IndicatorState()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='indicatorState'"
	 * @generated
	 */
	String getIndicatorState();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateMap#getIndicatorState <em>Indicator State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Indicator State</em>' attribute.
	 * @see #getIndicatorState()
	 * @generated
	 */
	void setIndicatorState(String value);

} // AlertIndicatorStateMap
