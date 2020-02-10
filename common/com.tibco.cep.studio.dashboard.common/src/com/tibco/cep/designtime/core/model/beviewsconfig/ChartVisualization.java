/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Chart Visualization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *             The base type to handle chart visualizations
 *             
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartVisualization#getSharedCategoryDataConfig <em>Shared Category Data Config</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartVisualization()
 * @model abstract="true"
 *        extendedMetaData="name='ChartVisualization' kind='elementOnly'"
 * @generated
 */
public interface ChartVisualization extends TwoDimVisualization {
	/**
	 * Returns the value of the '<em><b>Shared Category Data Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shared Category Data Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shared Category Data Config</em>' containment reference.
	 * @see #setSharedCategoryDataConfig(ChartCategoryDataConfig)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartVisualization_SharedCategoryDataConfig()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='sharedCategoryDataConfig'"
	 * @generated
	 */
	ChartCategoryDataConfig getSharedCategoryDataConfig();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartVisualization#getSharedCategoryDataConfig <em>Shared Category Data Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shared Category Data Config</em>' containment reference.
	 * @see #getSharedCategoryDataConfig()
	 * @generated
	 */
	void setSharedCategoryDataConfig(ChartCategoryDataConfig value);

} // ChartVisualization
