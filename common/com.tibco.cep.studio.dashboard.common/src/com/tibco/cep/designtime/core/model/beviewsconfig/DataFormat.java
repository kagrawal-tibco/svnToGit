/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Format</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the base for format configuration.
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#getToolTipFormat <em>Tool Tip Format</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#getDisplayFormat <em>Display Format</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#getFormatStyle <em>Format Style</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#getActionConfig <em>Action Config</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#isShowLabel <em>Show Label</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDataFormat()
 * @model abstract="true"
 *        extendedMetaData="name='DataFormat' kind='elementOnly'"
 * @generated
 */
public interface DataFormat extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Tool Tip Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tool Tip Format</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tool Tip Format</em>' attribute.
	 * @see #setToolTipFormat(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDataFormat_ToolTipFormat()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='toolTipFormat'"
	 * @generated
	 */
	String getToolTipFormat();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#getToolTipFormat <em>Tool Tip Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tool Tip Format</em>' attribute.
	 * @see #getToolTipFormat()
	 * @generated
	 */
	void setToolTipFormat(String value);

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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDataFormat_DisplayFormat()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='displayFormat'"
	 * @generated
	 */
	String getDisplayFormat();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#getDisplayFormat <em>Display Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Format</em>' attribute.
	 * @see #getDisplayFormat()
	 * @generated
	 */
	void setDisplayFormat(String value);

	/**
	 * Returns the value of the '<em><b>Format Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Format Style</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Format Style</em>' containment reference.
	 * @see #setFormatStyle(FormatStyle)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDataFormat_FormatStyle()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='formatStyle'"
	 * @generated
	 */
	FormatStyle getFormatStyle();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#getFormatStyle <em>Format Style</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Format Style</em>' containment reference.
	 * @see #getFormatStyle()
	 * @generated
	 */
	void setFormatStyle(FormatStyle value);

	/**
	 * Returns the value of the '<em><b>Action Config</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action Config</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action Config</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDataFormat_ActionConfig()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='actionConfig'"
	 * @generated
	 */
	EList<EObject> getActionConfig();

	/**
	 * Returns the value of the '<em><b>Show Label</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Label</em>' attribute.
	 * @see #isSetShowLabel()
	 * @see #unsetShowLabel()
	 * @see #setShowLabel(boolean)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getDataFormat_ShowLabel()
	 * @model default="true" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isShowLabel();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#isShowLabel <em>Show Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Label</em>' attribute.
	 * @see #isSetShowLabel()
	 * @see #unsetShowLabel()
	 * @see #isShowLabel()
	 * @generated
	 */
	void setShowLabel(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#isShowLabel <em>Show Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetShowLabel()
	 * @see #isShowLabel()
	 * @see #setShowLabel(boolean)
	 * @generated
	 */
	void unsetShowLabel();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.DataFormat#isShowLabel <em>Show Label</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Show Label</em>' attribute is set.
	 * @see #unsetShowLabel()
	 * @see #isShowLabel()
	 * @see #setShowLabel(boolean)
	 * @generated
	 */
	boolean isSetShowLabel();

} // DataFormat
