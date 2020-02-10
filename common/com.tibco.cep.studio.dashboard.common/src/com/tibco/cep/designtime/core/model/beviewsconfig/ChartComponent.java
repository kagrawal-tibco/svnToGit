/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Chart Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the concrete extension of the component which can handle chart generation.
 * 			Contains ONLY ChartVisualization(s) and MAX of TWO
 * 			Single ChartVisualization means a simple chart visualization
 * 			TWO ChartVisualizations means a overlaid chart visualizaton [currently supports only bar + line, cannot combine pie with bar or line]
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent#getPlotArea <em>Plot Area</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent#getLegend <em>Legend</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent#getCategoryGuidelineConfig <em>Category Guideline Config</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent#getValueGuidelineConfig <em>Value Guideline Config</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartComponent()
 * @model extendedMetaData="name='ChartComponent' kind='elementOnly'"
 * @generated
 */
public interface ChartComponent extends Component {
	/**
	 * Returns the value of the '<em><b>Plot Area</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plot Area</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plot Area</em>' containment reference.
	 * @see #setPlotArea(PlotAreaFormat)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartComponent_PlotArea()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='plotArea'"
	 * @generated
	 */
	PlotAreaFormat getPlotArea();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent#getPlotArea <em>Plot Area</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Plot Area</em>' containment reference.
	 * @see #getPlotArea()
	 * @generated
	 */
	void setPlotArea(PlotAreaFormat value);

	/**
	 * Returns the value of the '<em><b>Legend</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Legend</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Legend</em>' containment reference.
	 * @see #setLegend(LegendFormat)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartComponent_Legend()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='legend'"
	 * @generated
	 */
	LegendFormat getLegend();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent#getLegend <em>Legend</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Legend</em>' containment reference.
	 * @see #getLegend()
	 * @generated
	 */
	void setLegend(LegendFormat value);

	/**
	 * Returns the value of the '<em><b>Category Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Category Guideline Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Category Guideline Config</em>' containment reference.
	 * @see #setCategoryGuidelineConfig(ChartCategoryGuidelineConfig)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartComponent_CategoryGuidelineConfig()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='categoryGuidelineConfig'"
	 * @generated
	 */
	ChartCategoryGuidelineConfig getCategoryGuidelineConfig();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent#getCategoryGuidelineConfig <em>Category Guideline Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Category Guideline Config</em>' containment reference.
	 * @see #getCategoryGuidelineConfig()
	 * @generated
	 */
	void setCategoryGuidelineConfig(ChartCategoryGuidelineConfig value);

	/**
	 * Returns the value of the '<em><b>Value Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Guideline Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Guideline Config</em>' containment reference.
	 * @see #setValueGuidelineConfig(ChartValueGuidelineConfig)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartComponent_ValueGuidelineConfig()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='valueGuidelineConfig'"
	 * @generated
	 */
	ChartValueGuidelineConfig getValueGuidelineConfig();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent#getValueGuidelineConfig <em>Value Guideline Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Guideline Config</em>' containment reference.
	 * @see #getValueGuidelineConfig()
	 * @generated
	 */
	void setValueGuidelineConfig(ChartValueGuidelineConfig value);

} // ChartComponent
