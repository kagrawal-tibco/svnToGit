/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Text Category Guideline Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the concrete extension of category guide line config.
 * 			Manifests as the first column in a text metric
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig#getHeaderAlignment <em>Header Alignment</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig#getLabelAlignment <em>Label Alignment</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig#getWidth <em>Width</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextCategoryGuidelineConfig()
 * @model extendedMetaData="name='TextCategoryGuidelineConfig' kind='elementOnly'"
 * @generated
 */
public interface TextCategoryGuidelineConfig extends CategoryGuidelineConfig {
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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextCategoryGuidelineConfig_HeaderAlignment()
	 * @model default="center" unsettable="true"
	 *        extendedMetaData="kind='element' name='headerAlignment'"
	 * @generated
	 */
	FieldAlignmentEnum getHeaderAlignment();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig#getHeaderAlignment <em>Header Alignment</em>}' attribute.
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
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig#getHeaderAlignment <em>Header Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetHeaderAlignment()
	 * @see #getHeaderAlignment()
	 * @see #setHeaderAlignment(FieldAlignmentEnum)
	 * @generated
	 */
	void unsetHeaderAlignment();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig#getHeaderAlignment <em>Header Alignment</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Header Alignment</em>' attribute is set.
	 * @see #unsetHeaderAlignment()
	 * @see #getHeaderAlignment()
	 * @see #setHeaderAlignment(FieldAlignmentEnum)
	 * @generated
	 */
	boolean isSetHeaderAlignment();

	/**
	 * Returns the value of the '<em><b>Label Alignment</b></em>' attribute.
	 * The default value is <code>"center"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label Alignment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label Alignment</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum
	 * @see #isSetLabelAlignment()
	 * @see #unsetLabelAlignment()
	 * @see #setLabelAlignment(FieldAlignmentEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextCategoryGuidelineConfig_LabelAlignment()
	 * @model default="center" unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='labelAlignment'"
	 * @generated
	 */
	FieldAlignmentEnum getLabelAlignment();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig#getLabelAlignment <em>Label Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label Alignment</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum
	 * @see #isSetLabelAlignment()
	 * @see #unsetLabelAlignment()
	 * @see #getLabelAlignment()
	 * @generated
	 */
	void setLabelAlignment(FieldAlignmentEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig#getLabelAlignment <em>Label Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLabelAlignment()
	 * @see #getLabelAlignment()
	 * @see #setLabelAlignment(FieldAlignmentEnum)
	 * @generated
	 */
	void unsetLabelAlignment();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig#getLabelAlignment <em>Label Alignment</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Label Alignment</em>' attribute is set.
	 * @see #unsetLabelAlignment()
	 * @see #getLabelAlignment()
	 * @see #setLabelAlignment(FieldAlignmentEnum)
	 * @generated
	 */
	boolean isSetLabelAlignment();

	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Width</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #setWidth(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getTextCategoryGuidelineConfig_Width()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='width'"
	 * @generated
	 */
	String getWidth();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(String value);

} // TextCategoryGuidelineConfig
