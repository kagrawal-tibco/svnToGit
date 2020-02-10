/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.beviewsconfig.AnchorPositionEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.IndicatorFieldFormat;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Indicator Field Format</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.IndicatorFieldFormatImpl#isShowTextValue <em>Show Text Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.IndicatorFieldFormatImpl#getTextValueAnchor <em>Text Value Anchor</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IndicatorFieldFormatImpl extends TextFieldFormatImpl implements IndicatorFieldFormat {
	/**
	 * The default value of the '{@link #isShowTextValue() <em>Show Text Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowTextValue()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_TEXT_VALUE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isShowTextValue() <em>Show Text Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowTextValue()
	 * @generated
	 * @ordered
	 */
	protected boolean showTextValue = SHOW_TEXT_VALUE_EDEFAULT;

	/**
	 * This is true if the Show Text Value attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean showTextValueESet;

	/**
	 * The default value of the '{@link #getTextValueAnchor() <em>Text Value Anchor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTextValueAnchor()
	 * @generated
	 * @ordered
	 */
	protected static final AnchorPositionEnum TEXT_VALUE_ANCHOR_EDEFAULT = AnchorPositionEnum.EAST;

	/**
	 * The cached value of the '{@link #getTextValueAnchor() <em>Text Value Anchor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTextValueAnchor()
	 * @generated
	 * @ordered
	 */
	protected AnchorPositionEnum textValueAnchor = TEXT_VALUE_ANCHOR_EDEFAULT;

	/**
	 * This is true if the Text Value Anchor attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean textValueAnchorESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IndicatorFieldFormatImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getIndicatorFieldFormat();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowTextValue() {
		return showTextValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowTextValue(boolean newShowTextValue) {
		boolean oldShowTextValue = showTextValue;
		showTextValue = newShowTextValue;
		boolean oldShowTextValueESet = showTextValueESet;
		showTextValueESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.INDICATOR_FIELD_FORMAT__SHOW_TEXT_VALUE, oldShowTextValue, showTextValue, !oldShowTextValueESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetShowTextValue() {
		boolean oldShowTextValue = showTextValue;
		boolean oldShowTextValueESet = showTextValueESet;
		showTextValue = SHOW_TEXT_VALUE_EDEFAULT;
		showTextValueESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.INDICATOR_FIELD_FORMAT__SHOW_TEXT_VALUE, oldShowTextValue, SHOW_TEXT_VALUE_EDEFAULT, oldShowTextValueESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetShowTextValue() {
		return showTextValueESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnchorPositionEnum getTextValueAnchor() {
		return textValueAnchor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTextValueAnchor(AnchorPositionEnum newTextValueAnchor) {
		AnchorPositionEnum oldTextValueAnchor = textValueAnchor;
		textValueAnchor = newTextValueAnchor == null ? TEXT_VALUE_ANCHOR_EDEFAULT : newTextValueAnchor;
		boolean oldTextValueAnchorESet = textValueAnchorESet;
		textValueAnchorESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.INDICATOR_FIELD_FORMAT__TEXT_VALUE_ANCHOR, oldTextValueAnchor, textValueAnchor, !oldTextValueAnchorESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTextValueAnchor() {
		AnchorPositionEnum oldTextValueAnchor = textValueAnchor;
		boolean oldTextValueAnchorESet = textValueAnchorESet;
		textValueAnchor = TEXT_VALUE_ANCHOR_EDEFAULT;
		textValueAnchorESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.INDICATOR_FIELD_FORMAT__TEXT_VALUE_ANCHOR, oldTextValueAnchor, TEXT_VALUE_ANCHOR_EDEFAULT, oldTextValueAnchorESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTextValueAnchor() {
		return textValueAnchorESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.INDICATOR_FIELD_FORMAT__SHOW_TEXT_VALUE:
				return isShowTextValue();
			case BEViewsConfigurationPackage.INDICATOR_FIELD_FORMAT__TEXT_VALUE_ANCHOR:
				return getTextValueAnchor();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BEViewsConfigurationPackage.INDICATOR_FIELD_FORMAT__SHOW_TEXT_VALUE:
				setShowTextValue((Boolean)newValue);
				return;
			case BEViewsConfigurationPackage.INDICATOR_FIELD_FORMAT__TEXT_VALUE_ANCHOR:
				setTextValueAnchor((AnchorPositionEnum)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case BEViewsConfigurationPackage.INDICATOR_FIELD_FORMAT__SHOW_TEXT_VALUE:
				unsetShowTextValue();
				return;
			case BEViewsConfigurationPackage.INDICATOR_FIELD_FORMAT__TEXT_VALUE_ANCHOR:
				unsetTextValueAnchor();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BEViewsConfigurationPackage.INDICATOR_FIELD_FORMAT__SHOW_TEXT_VALUE:
				return isSetShowTextValue();
			case BEViewsConfigurationPackage.INDICATOR_FIELD_FORMAT__TEXT_VALUE_ANCHOR:
				return isSetTextValueAnchor();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (showTextValue: ");
		if (showTextValueESet) result.append(showTextValue); else result.append("<unset>");
		result.append(", textValueAnchor: ");
		if (textValueAnchorESet) result.append(textValueAnchor); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //IndicatorFieldFormatImpl
