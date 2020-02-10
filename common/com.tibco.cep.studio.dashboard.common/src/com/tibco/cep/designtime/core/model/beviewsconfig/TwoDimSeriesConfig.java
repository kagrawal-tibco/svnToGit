/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Two Dim Series Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the concrete extension of the series config to handle two dimensional series configs.
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimSeriesConfig#getCategoryDataConfig <em>Category Data Config</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTwoDimSeriesConfig()
 * @model extendedMetaData="name='TwoDimSeriesConfig' kind='elementOnly'"
 * @generated
 */
public interface TwoDimSeriesConfig extends OneDimSeriesConfig {
	/**
	 * Returns the value of the '<em><b>Category Data Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Category Data Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Category Data Config</em>' containment reference.
	 * @see #setCategoryDataConfig(DataConfig)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTwoDimSeriesConfig_CategoryDataConfig()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='categoryDataConfig'"
	 * @generated
	 */
	DataConfig getCategoryDataConfig();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimSeriesConfig#getCategoryDataConfig <em>Category Data Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Category Data Config</em>' containment reference.
	 * @see #getCategoryDataConfig()
	 * @generated
	 */
	void setCategoryDataConfig(DataConfig value);

} // TwoDimSeriesConfig
