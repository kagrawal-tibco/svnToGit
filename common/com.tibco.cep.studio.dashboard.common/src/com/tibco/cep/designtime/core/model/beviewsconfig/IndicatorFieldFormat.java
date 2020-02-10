/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Indicator Field Format</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *             Represents the concrete extension of the data format which can handle indicator formatting
 *             Depends on visual derivation specs to derive the colors/states of the indicator
 *             
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.IndicatorFieldFormat#isShowTextValue <em>Show Text Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.IndicatorFieldFormat#getTextValueAnchor <em>Text Value Anchor</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getIndicatorFieldFormat()
 * @model extendedMetaData="name='IndicatorFieldFormat' kind='elementOnly'"
 * @generated
 */
public interface IndicatorFieldFormat extends TextFieldFormat {
	/**
	 * Returns the value of the '<em><b>Show Text Value</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Text Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Text Value</em>' attribute.
	 * @see #isSetShowTextValue()
	 * @see #unsetShowTextValue()
	 * @see #setShowTextValue(boolean)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getIndicatorFieldFormat_ShowTextValue()
	 * @model default="true" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 *        extendedMetaData="kind='element' name='showTextValue'"
	 * @generated
	 */
	boolean isShowTextValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.IndicatorFieldFormat#isShowTextValue <em>Show Text Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Text Value</em>' attribute.
	 * @see #isSetShowTextValue()
	 * @see #unsetShowTextValue()
	 * @see #isShowTextValue()
	 * @generated
	 */
	void setShowTextValue(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.IndicatorFieldFormat#isShowTextValue <em>Show Text Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetShowTextValue()
	 * @see #isShowTextValue()
	 * @see #setShowTextValue(boolean)
	 * @generated
	 */
	void unsetShowTextValue();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.IndicatorFieldFormat#isShowTextValue <em>Show Text Value</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Show Text Value</em>' attribute is set.
	 * @see #unsetShowTextValue()
	 * @see #isShowTextValue()
	 * @see #setShowTextValue(boolean)
	 * @generated
	 */
	boolean isSetShowTextValue();

	/**
	 * Returns the value of the '<em><b>Text Value Anchor</b></em>' attribute.
	 * The default value is <code>"east"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.AnchorPositionEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text Value Anchor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text Value Anchor</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AnchorPositionEnum
	 * @see #isSetTextValueAnchor()
	 * @see #unsetTextValueAnchor()
	 * @see #setTextValueAnchor(AnchorPositionEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getIndicatorFieldFormat_TextValueAnchor()
	 * @model default="east" unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='textValueAnchor'"
	 * @generated
	 */
	AnchorPositionEnum getTextValueAnchor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.IndicatorFieldFormat#getTextValueAnchor <em>Text Value Anchor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text Value Anchor</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AnchorPositionEnum
	 * @see #isSetTextValueAnchor()
	 * @see #unsetTextValueAnchor()
	 * @see #getTextValueAnchor()
	 * @generated
	 */
	void setTextValueAnchor(AnchorPositionEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.IndicatorFieldFormat#getTextValueAnchor <em>Text Value Anchor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTextValueAnchor()
	 * @see #getTextValueAnchor()
	 * @see #setTextValueAnchor(AnchorPositionEnum)
	 * @generated
	 */
	void unsetTextValueAnchor();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.IndicatorFieldFormat#getTextValueAnchor <em>Text Value Anchor</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Text Value Anchor</em>' attribute is set.
	 * @see #unsetTextValueAnchor()
	 * @see #getTextValueAnchor()
	 * @see #setTextValueAnchor(AnchorPositionEnum)
	 * @generated
	 */
	boolean isSetTextValueAnchor();

} // IndicatorFieldFormat
