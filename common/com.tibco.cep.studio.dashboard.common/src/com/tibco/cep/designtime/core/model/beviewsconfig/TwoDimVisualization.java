/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Two Dim Visualization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the concrete extension of the visualization to handle two dimensional visualizations.
 * 			Should contain 'n' series configs of type TwoDimSeriesConfig
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimVisualization#getCategoryGuidelineConfig <em>Category Guideline Config</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTwoDimVisualization()
 * @model extendedMetaData="name='TwoDimVisualization' kind='elementOnly'"
 * @generated
 */
public interface TwoDimVisualization extends OneDimVisualization {
	/**
	 * Returns the value of the '<em><b>Category Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Category Guideline Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Category Guideline Config</em>' containment reference.
	 * @see #setCategoryGuidelineConfig(CategoryGuidelineConfig)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTwoDimVisualization_CategoryGuidelineConfig()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='categoryGuidelineConfig'"
	 * @generated
	 */
	CategoryGuidelineConfig getCategoryGuidelineConfig();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimVisualization#getCategoryGuidelineConfig <em>Category Guideline Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Category Guideline Config</em>' containment reference.
	 * @see #getCategoryGuidelineConfig()
	 * @generated
	 */
	void setCategoryGuidelineConfig(CategoryGuidelineConfig value);

} // TwoDimVisualization
