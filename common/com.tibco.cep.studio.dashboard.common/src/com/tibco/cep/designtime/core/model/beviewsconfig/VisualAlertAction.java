/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Visual Alert Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFillColor <em>Fill Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFontSize <em>Font Size</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFontStyle <em>Font Style</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFontColor <em>Font Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getDisplayFormat <em>Display Format</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getTooltipFormat <em>Tooltip Format</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getVisualAlertAction()
 * @model
 * @generated
 */
public interface VisualAlertAction extends AlertAction {
	/**
	 * Returns the value of the '<em><b>Fill Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fill Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fill Color</em>' attribute.
	 * @see #setFillColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getVisualAlertAction_FillColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getFillColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFillColor <em>Fill Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fill Color</em>' attribute.
	 * @see #getFillColor()
	 * @generated
	 */
	void setFillColor(String value);

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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getVisualAlertAction_FontSize()
	 * @model default="11" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 *        extendedMetaData="kind='element' name='fontSize'"
	 * @generated
	 */
	int getFontSize();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFontSize <em>Font Size</em>}' attribute.
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
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFontSize <em>Font Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetFontSize()
	 * @see #getFontSize()
	 * @see #setFontSize(int)
	 * @generated
	 */
	void unsetFontSize();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFontSize <em>Font Size</em>}' attribute is set.
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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getVisualAlertAction_FontStyle()
	 * @model default="normal" unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='fontStyle'"
	 * @generated
	 */
	FontStyleEnum getFontStyle();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFontStyle <em>Font Style</em>}' attribute.
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
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFontStyle <em>Font Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetFontStyle()
	 * @see #getFontStyle()
	 * @see #setFontStyle(FontStyleEnum)
	 * @generated
	 */
	void unsetFontStyle();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFontStyle <em>Font Style</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Font Style</em>' attribute is set.
	 * @see #unsetFontStyle()
	 * @see #getFontStyle()
	 * @see #setFontStyle(FontStyleEnum)
	 * @generated
	 */
	boolean isSetFontStyle();

	/**
	 * Returns the value of the '<em><b>Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Font Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Font Color</em>' attribute.
	 * @see #setFontColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getVisualAlertAction_FontColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getFontColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getFontColor <em>Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Font Color</em>' attribute.
	 * @see #getFontColor()
	 * @generated
	 */
	void setFontColor(String value);

	/**
	 * Returns the value of the '<em><b>Display Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display Format</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Format</em>' attribute.
	 * @see #setDisplayFormat(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getVisualAlertAction_DisplayFormat()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getDisplayFormat();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getDisplayFormat <em>Display Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Format</em>' attribute.
	 * @see #getDisplayFormat()
	 * @generated
	 */
	void setDisplayFormat(String value);

	/**
	 * Returns the value of the '<em><b>Tooltip Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tooltip Format</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tooltip Format</em>' attribute.
	 * @see #setTooltipFormat(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getVisualAlertAction_TooltipFormat()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getTooltipFormat();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction#getTooltipFormat <em>Tooltip Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tooltip Format</em>' attribute.
	 * @see #getTooltipFormat()
	 * @generated
	 */
	void setTooltipFormat(String value);

} // VisualAlertAction
