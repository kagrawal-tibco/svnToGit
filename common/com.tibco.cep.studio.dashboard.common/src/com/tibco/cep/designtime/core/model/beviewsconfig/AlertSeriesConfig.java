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
 * A representation of the model object '<em><b>Alert Series Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertSeriesConfig#getIndicatorStateEnumeration <em>Indicator State Enumeration</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getAlertSeriesConfig()
 * @model extendedMetaData="name='AlertSeriesConfig' kind='elementOnly'"
 * @generated
 */
public interface AlertSeriesConfig extends ClassifierSeriesConfig {
	/**
	 * Returns the value of the '<em><b>Indicator State Enumeration</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateEnumeration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Indicator State Enumeration</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Indicator State Enumeration</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getAlertSeriesConfig_IndicatorStateEnumeration()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='indicatorStateEnumeration'"
	 * @generated
	 */
	EList<AlertIndicatorStateEnumeration> getIndicatorStateEnumeration();

} // AlertSeriesConfig
