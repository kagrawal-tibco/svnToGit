/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Background Format</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 			Represents the background color for all visualization.
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.BackgroundFormat#getGradientDirection <em>Gradient Direction</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getBackgroundFormat()
 * @model extendedMetaData="name='BackgroundFormat' kind='elementOnly'"
 * @generated
 */
public interface BackgroundFormat extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Gradient Direction</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.GradientDirectionEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gradient Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gradient Direction</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.GradientDirectionEnum
	 * @see #isSetGradientDirection()
	 * @see #unsetGradientDirection()
	 * @see #setGradientDirection(GradientDirectionEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getBackgroundFormat_GradientDirection()
	 * @model unsettable="true"
	 *        extendedMetaData="kind='element' name='gradientDirection'"
	 * @generated
	 */
	GradientDirectionEnum getGradientDirection();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BackgroundFormat#getGradientDirection <em>Gradient Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gradient Direction</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.GradientDirectionEnum
	 * @see #isSetGradientDirection()
	 * @see #unsetGradientDirection()
	 * @see #getGradientDirection()
	 * @generated
	 */
	void setGradientDirection(GradientDirectionEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BackgroundFormat#getGradientDirection <em>Gradient Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetGradientDirection()
	 * @see #getGradientDirection()
	 * @see #setGradientDirection(GradientDirectionEnum)
	 * @generated
	 */
	void unsetGradientDirection();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BackgroundFormat#getGradientDirection <em>Gradient Direction</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Gradient Direction</em>' attribute is set.
	 * @see #unsetGradientDirection()
	 * @see #getGradientDirection()
	 * @see #setGradientDirection(GradientDirectionEnum)
	 * @generated
	 */
	boolean isSetGradientDirection();

} // BackgroundFormat
