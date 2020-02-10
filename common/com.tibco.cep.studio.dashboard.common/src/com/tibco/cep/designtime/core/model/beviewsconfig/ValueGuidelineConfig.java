/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Value Guideline Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the base for all value guideline config. This can manifests as 2nd to 'n' columns or the chart value axis
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ValueGuidelineConfig#getHeaderFormatStyle <em>Header Format Style</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getValueGuidelineConfig()
 * @model abstract="true"
 *        extendedMetaData="name='ValueGuidelineConfig' kind='elementOnly'"
 * @generated
 */
public interface ValueGuidelineConfig extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Header Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Header Format Style</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Header Format Style</em>' containment reference.
	 * @see #setHeaderFormatStyle(FormatStyle)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getValueGuidelineConfig_HeaderFormatStyle()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='headerFormatStyle'"
	 * @generated
	 */
	FormatStyle getHeaderFormatStyle();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ValueGuidelineConfig#getHeaderFormatStyle <em>Header Format Style</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header Format Style</em>' containment reference.
	 * @see #getHeaderFormatStyle()
	 * @generated
	 */
	void setHeaderFormatStyle(FormatStyle value);

} // ValueGuidelineConfig
