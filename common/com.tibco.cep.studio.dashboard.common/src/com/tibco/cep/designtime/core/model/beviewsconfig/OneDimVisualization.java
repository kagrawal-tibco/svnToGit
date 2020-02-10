/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>One Dim Visualization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the concrete extension of the visualization to handle single dimensional visualizations.
 * 			Should contain ONLY one series config of type OneDimSeriesConfig
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.OneDimVisualization#getValueGuidelineConfig <em>Value Guideline Config</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getOneDimVisualization()
 * @model extendedMetaData="name='OneDimVisualization' kind='elementOnly'"
 * @generated
 */
public interface OneDimVisualization extends Visualization {
	/**
	 * Returns the value of the '<em><b>Value Guideline Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Guideline Config</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Guideline Config</em>' containment reference.
	 * @see #setValueGuidelineConfig(ValueGuidelineConfig)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getOneDimVisualization_ValueGuidelineConfig()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='valueGuidelineConfig'"
	 * @generated
	 */
	ValueGuidelineConfig getValueGuidelineConfig();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.OneDimVisualization#getValueGuidelineConfig <em>Value Guideline Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Guideline Config</em>' containment reference.
	 * @see #getValueGuidelineConfig()
	 * @generated
	 */
	void setValueGuidelineConfig(ValueGuidelineConfig value);

} // OneDimVisualization
