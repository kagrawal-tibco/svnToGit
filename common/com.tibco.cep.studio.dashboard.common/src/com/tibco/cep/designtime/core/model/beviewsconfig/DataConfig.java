/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the base for all data configuration.
 * 			This includes extraction and formatting of the data to generate values with appropriate formatting
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig#getExtractor <em>Extractor</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig#getFormatter <em>Formatter</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDataConfig()
 * @model abstract="true"
 *        extendedMetaData="name='DataConfig' kind='elementOnly'"
 * @generated
 */
public interface DataConfig extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Extractor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extractor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extractor</em>' containment reference.
	 * @see #setExtractor(DataExtractor)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDataConfig_Extractor()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='extractor'"
	 * @generated
	 */
	DataExtractor getExtractor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig#getExtractor <em>Extractor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extractor</em>' containment reference.
	 * @see #getExtractor()
	 * @generated
	 */
	void setExtractor(DataExtractor value);

	/**
	 * Returns the value of the '<em><b>Formatter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Formatter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Formatter</em>' containment reference.
	 * @see #setFormatter(DataFormat)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDataConfig_Formatter()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='formatter'"
	 * @generated
	 */
	DataFormat getFormatter();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig#getFormatter <em>Formatter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Formatter</em>' containment reference.
	 * @see #getFormatter()
	 * @generated
	 */
	void setFormatter(DataFormat value);

} // DataConfig
