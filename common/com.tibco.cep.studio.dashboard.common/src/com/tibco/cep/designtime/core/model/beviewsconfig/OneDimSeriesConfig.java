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
 * A representation of the model object '<em><b>One Dim Series Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the concrete extension of the series config to handle single dimensional series configs.
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.OneDimSeriesConfig#getValueDataConfig <em>Value Data Config</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getOneDimSeriesConfig()
 * @model extendedMetaData="name='OneDimSeriesConfig' kind='elementOnly'"
 * @generated
 */
public interface OneDimSeriesConfig extends SeriesConfig {
	/**
	 * Returns the value of the '<em><b>Value Data Config</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Data Config</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Data Config</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getOneDimSeriesConfig_ValueDataConfig()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='valueDataConfig'"
	 * @generated
	 */
	EList<DataConfig> getValueDataConfig();

} // OneDimSeriesConfig
