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

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.FontStyleEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Format Style</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.FormatStyleImpl#getFontSize <em>Font Size</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.FormatStyleImpl#getFontStyle <em>Font Style</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FormatStyleImpl extends BEViewsElementImpl implements FormatStyle {
	/**
	 * The default value of the '{@link #getFontSize() <em>Font Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontSize()
	 * @generated
	 * @ordered
	 */
	protected static final int FONT_SIZE_EDEFAULT = 11;

	/**
	 * The cached value of the '{@link #getFontSize() <em>Font Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontSize()
	 * @generated
	 * @ordered
	 */
	protected int fontSize = FONT_SIZE_EDEFAULT;

	/**
	 * This is true if the Font Size attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean fontSizeESet;

	/**
	 * The default value of the '{@link #getFontStyle() <em>Font Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontStyle()
	 * @generated
	 * @ordered
	 */
	protected static final FontStyleEnum FONT_STYLE_EDEFAULT = FontStyleEnum.NORMAL;

	/**
	 * The cached value of the '{@link #getFontStyle() <em>Font Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontStyle()
	 * @generated
	 * @ordered
	 */
	protected FontStyleEnum fontStyle = FONT_STYLE_EDEFAULT;

	/**
	 * This is true if the Font Style attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean fontStyleESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FormatStyleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getFormatStyle();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFontSize(int newFontSize) {
		int oldFontSize = fontSize;
		fontSize = newFontSize;
		boolean oldFontSizeESet = fontSizeESet;
		fontSizeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.FORMAT_STYLE__FONT_SIZE, oldFontSize, fontSize, !oldFontSizeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetFontSize() {
		int oldFontSize = fontSize;
		boolean oldFontSizeESet = fontSizeESet;
		fontSize = FONT_SIZE_EDEFAULT;
		fontSizeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.FORMAT_STYLE__FONT_SIZE, oldFontSize, FONT_SIZE_EDEFAULT, oldFontSizeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetFontSize() {
		return fontSizeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FontStyleEnum getFontStyle() {
		return fontStyle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFontStyle(FontStyleEnum newFontStyle) {
		FontStyleEnum oldFontStyle = fontStyle;
		fontStyle = newFontStyle == null ? FONT_STYLE_EDEFAULT : newFontStyle;
		boolean oldFontStyleESet = fontStyleESet;
		fontStyleESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.FORMAT_STYLE__FONT_STYLE, oldFontStyle, fontStyle, !oldFontStyleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetFontStyle() {
		FontStyleEnum oldFontStyle = fontStyle;
		boolean oldFontStyleESet = fontStyleESet;
		fontStyle = FONT_STYLE_EDEFAULT;
		fontStyleESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.FORMAT_STYLE__FONT_STYLE, oldFontStyle, FONT_STYLE_EDEFAULT, oldFontStyleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetFontStyle() {
		return fontStyleESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.FORMAT_STYLE__FONT_SIZE:
				return getFontSize();
			case BEViewsConfigurationPackage.FORMAT_STYLE__FONT_STYLE:
				return getFontStyle();
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
			case BEViewsConfigurationPackage.FORMAT_STYLE__FONT_SIZE:
				setFontSize((Integer)newValue);
				return;
			case BEViewsConfigurationPackage.FORMAT_STYLE__FONT_STYLE:
				setFontStyle((FontStyleEnum)newValue);
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
			case BEViewsConfigurationPackage.FORMAT_STYLE__FONT_SIZE:
				unsetFontSize();
				return;
			case BEViewsConfigurationPackage.FORMAT_STYLE__FONT_STYLE:
				unsetFontStyle();
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
			case BEViewsConfigurationPackage.FORMAT_STYLE__FONT_SIZE:
				return isSetFontSize();
			case BEViewsConfigurationPackage.FORMAT_STYLE__FONT_STYLE:
				return isSetFontStyle();
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
		result.append(" (fontSize: ");
		if (fontSizeESet) result.append(fontSize); else result.append("<unset>");
		result.append(", fontStyle: ");
		if (fontStyleESet) result.append(fontStyle); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //FormatStyleImpl
