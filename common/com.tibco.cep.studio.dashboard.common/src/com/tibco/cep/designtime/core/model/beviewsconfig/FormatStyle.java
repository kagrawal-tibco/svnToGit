/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Format Style</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the visual formatting information for any object being rendered. The font color should be picked up from either the BEViewsSkin
 * 			or ComponentColorSet
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle#getFontSize <em>Font Size</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle#getFontStyle <em>Font Style</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getFormatStyle()
 * @model extendedMetaData="name='FormatStyle' kind='elementOnly'"
 * @generated
 */
public interface FormatStyle extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Font Size</b></em>' attribute.
	 * The default value is <code>"11"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Font Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Font Size</em>' attribute.
	 * @see #isSetFontSize()
	 * @see #unsetFontSize()
	 * @see #setFontSize(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getFormatStyle_FontSize()
	 * @model default="11" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 *        extendedMetaData="kind='element' name='fontSize'"
	 * @generated
	 */
	int getFontSize();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle#getFontSize <em>Font Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Font Size</em>' attribute.
	 * @see #isSetFontSize()
	 * @see #unsetFontSize()
	 * @see #getFontSize()
	 * @generated
	 */
	void setFontSize(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle#getFontSize <em>Font Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetFontSize()
	 * @see #getFontSize()
	 * @see #setFontSize(int)
	 * @generated
	 */
	void unsetFontSize();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle#getFontSize <em>Font Size</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Font Size</em>' attribute is set.
	 * @see #unsetFontSize()
	 * @see #getFontSize()
	 * @see #setFontSize(int)
	 * @generated
	 */
	boolean isSetFontSize();

	/**
	 * Returns the value of the '<em><b>Font Style</b></em>' attribute.
	 * The default value is <code>"normal"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.FontStyleEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Font Style</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Font Style</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FontStyleEnum
	 * @see #isSetFontStyle()
	 * @see #unsetFontStyle()
	 * @see #setFontStyle(FontStyleEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getFormatStyle_FontStyle()
	 * @model default="normal" unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='fontStyle'"
	 * @generated
	 */
	FontStyleEnum getFontStyle();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle#getFontStyle <em>Font Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Font Style</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.FontStyleEnum
	 * @see #isSetFontStyle()
	 * @see #unsetFontStyle()
	 * @see #getFontStyle()
	 * @generated
	 */
	void setFontStyle(FontStyleEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle#getFontStyle <em>Font Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetFontStyle()
	 * @see #getFontStyle()
	 * @see #setFontStyle(FontStyleEnum)
	 * @generated
	 */
	void unsetFontStyle();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle#getFontStyle <em>Font Style</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Font Style</em>' attribute is set.
	 * @see #unsetFontStyle()
	 * @see #getFontStyle()
	 * @see #setFontStyle(FontStyleEnum)
	 * @generated
	 */
	boolean isSetFontStyle();

} // FormatStyle
