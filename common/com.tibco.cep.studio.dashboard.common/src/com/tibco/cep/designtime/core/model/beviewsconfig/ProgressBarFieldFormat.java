/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Progress Bar Field Format</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *             Represents the concrete extension of the data format which can handle progress bar formatting
 *             Depends on visual derivation specs to derive the colors of the progress bar
 *             
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ProgressBarFieldFormat#getMinValue <em>Min Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ProgressBarFieldFormat#getMaxValue <em>Max Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getProgressBarFieldFormat()
 * @model extendedMetaData="name='ProgressBarFieldFormat' kind='elementOnly'"
 * @generated
 */
public interface ProgressBarFieldFormat extends IndicatorFieldFormat {
	/**
	 * Returns the value of the '<em><b>Max Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Value</em>' containment reference.
	 * @see #setMaxValue(ValueOption)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getProgressBarFieldFormat_MaxValue()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ValueOption getMaxValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ProgressBarFieldFormat#getMaxValue <em>Max Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Value</em>' containment reference.
	 * @see #getMaxValue()
	 * @generated
	 */
	void setMaxValue(ValueOption value);

	/**
	 * Returns the value of the '<em><b>Min Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Value</em>' containment reference.
	 * @see #setMinValue(ValueOption)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getProgressBarFieldFormat_MinValue()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ValueOption getMinValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ProgressBarFieldFormat#getMinValue <em>Min Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Value</em>' containment reference.
	 * @see #getMinValue()
	 * @generated
	 */
	void setMinValue(ValueOption value);

} // ProgressBarFieldFormat
