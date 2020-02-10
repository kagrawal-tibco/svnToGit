/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Legend Format</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.LegendFormat#getOrientation <em>Orientation</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.LegendFormat#getAnchor <em>Anchor</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getLegendFormat()
 * @model extendedMetaData="name='LegendFormat' kind='elementOnly'"
 * @generated
 */
public interface LegendFormat extends BEViewsElement {
	/**
	 * Returns the value of the '<em><b>Orientation</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Orientation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Orientation</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum
	 * @see #isSetOrientation()
	 * @see #unsetOrientation()
	 * @see #setOrientation(OrientationEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getLegendFormat_Orientation()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='orientation'"
	 * @generated
	 */
	OrientationEnum getOrientation();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LegendFormat#getOrientation <em>Orientation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Orientation</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum
	 * @see #isSetOrientation()
	 * @see #unsetOrientation()
	 * @see #getOrientation()
	 * @generated
	 */
	void setOrientation(OrientationEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LegendFormat#getOrientation <em>Orientation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOrientation()
	 * @see #getOrientation()
	 * @see #setOrientation(OrientationEnum)
	 * @generated
	 */
	void unsetOrientation();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LegendFormat#getOrientation <em>Orientation</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Orientation</em>' attribute is set.
	 * @see #unsetOrientation()
	 * @see #getOrientation()
	 * @see #setOrientation(OrientationEnum)
	 * @generated
	 */
	boolean isSetOrientation();

	/**
	 * Returns the value of the '<em><b>Anchor</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.AnchorEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Anchor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Anchor</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AnchorEnum
	 * @see #isSetAnchor()
	 * @see #unsetAnchor()
	 * @see #setAnchor(AnchorEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getLegendFormat_Anchor()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='anchor'"
	 * @generated
	 */
	AnchorEnum getAnchor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LegendFormat#getAnchor <em>Anchor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Anchor</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.AnchorEnum
	 * @see #isSetAnchor()
	 * @see #unsetAnchor()
	 * @see #getAnchor()
	 * @generated
	 */
	void setAnchor(AnchorEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LegendFormat#getAnchor <em>Anchor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAnchor()
	 * @see #getAnchor()
	 * @see #setAnchor(AnchorEnum)
	 * @generated
	 */
	void unsetAnchor();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LegendFormat#getAnchor <em>Anchor</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Anchor</em>' attribute is set.
	 * @see #unsetAnchor()
	 * @see #getAnchor()
	 * @see #setAnchor(AnchorEnum)
	 * @generated
	 */
	boolean isSetAnchor();

} // LegendFormat
