/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Text Value Guideline Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the concrete extension of value guide line config.
 * 			Provides global header styling configuration
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueGuidelineConfig#getHeaderAlignment <em>Header Alignment</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextValueGuidelineConfig()
 * @model extendedMetaData="name='TextValueGuidelineConfig' kind='elementOnly'"
 * @generated
 */
public interface TextValueGuidelineConfig extends ValueGuidelineConfig {
	/**
	 * Returns the value of the '<em><b>Header Alignment</b></em>' attribute.
	 * The default value is <code>"center"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Header Alignment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Header Alignment</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum
	 * @see #isSetHeaderAlignment()
	 * @see #unsetHeaderAlignment()
	 * @see #setHeaderAlignment(FieldAlignmentEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextValueGuidelineConfig_HeaderAlignment()
	 * @model default="center" unsettable="true"
	 *        extendedMetaData="kind='element' name='headerAlignment'"
	 * @generated
	 */
	FieldAlignmentEnum getHeaderAlignment();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueGuidelineConfig#getHeaderAlignment <em>Header Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header Alignment</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum
	 * @see #isSetHeaderAlignment()
	 * @see #unsetHeaderAlignment()
	 * @see #getHeaderAlignment()
	 * @generated
	 */
	void setHeaderAlignment(FieldAlignmentEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueGuidelineConfig#getHeaderAlignment <em>Header Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetHeaderAlignment()
	 * @see #getHeaderAlignment()
	 * @see #setHeaderAlignment(FieldAlignmentEnum)
	 * @generated
	 */
	void unsetHeaderAlignment();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextValueGuidelineConfig#getHeaderAlignment <em>Header Alignment</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Header Alignment</em>' attribute is set.
	 * @see #unsetHeaderAlignment()
	 * @see #getHeaderAlignment()
	 * @see #setHeaderAlignment(FieldAlignmentEnum)
	 * @generated
	 */
	boolean isSetHeaderAlignment();

} // TextValueGuidelineConfig
