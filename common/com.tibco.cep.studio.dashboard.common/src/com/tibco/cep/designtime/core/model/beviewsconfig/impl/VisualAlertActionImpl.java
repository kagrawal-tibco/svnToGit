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
import com.tibco.cep.designtime.core.model.beviewsconfig.VisualAlertAction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Visual Alert Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.VisualAlertActionImpl#getFillColor <em>Fill Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.VisualAlertActionImpl#getFontSize <em>Font Size</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.VisualAlertActionImpl#getFontStyle <em>Font Style</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.VisualAlertActionImpl#getFontColor <em>Font Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.VisualAlertActionImpl#getDisplayFormat <em>Display Format</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.VisualAlertActionImpl#getTooltipFormat <em>Tooltip Format</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VisualAlertActionImpl extends AlertActionImpl implements VisualAlertAction {
	/**
	 * The default value of the '{@link #getFillColor() <em>Fill Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFillColor()
	 * @generated
	 * @ordered
	 */
	protected static final String FILL_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFillColor() <em>Fill Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFillColor()
	 * @generated
	 * @ordered
	 */
	protected String fillColor = FILL_COLOR_EDEFAULT;

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
	 * The default value of the '{@link #getFontColor() <em>Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontColor()
	 * @generated
	 * @ordered
	 */
	protected static final String FONT_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFontColor() <em>Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFontColor()
	 * @generated
	 * @ordered
	 */
	protected String fontColor = FONT_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getDisplayFormat() <em>Display Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayFormat()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_FORMAT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayFormat() <em>Display Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayFormat()
	 * @generated
	 * @ordered
	 */
	protected String displayFormat = DISPLAY_FORMAT_EDEFAULT;

	/**
	 * The default value of the '{@link #getTooltipFormat() <em>Tooltip Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTooltipFormat()
	 * @generated
	 * @ordered
	 */
	protected static final String TOOLTIP_FORMAT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTooltipFormat() <em>Tooltip Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTooltipFormat()
	 * @generated
	 * @ordered
	 */
	protected String tooltipFormat = TOOLTIP_FORMAT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VisualAlertActionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getVisualAlertAction();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFillColor() {
		return fillColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFillColor(String newFillColor) {
		String oldFillColor = fillColor;
		fillColor = newFillColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FILL_COLOR, oldFillColor, fillColor));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FONT_SIZE, oldFontSize, fontSize, !oldFontSizeESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FONT_SIZE, oldFontSize, FONT_SIZE_EDEFAULT, oldFontSizeESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FONT_STYLE, oldFontStyle, fontStyle, !oldFontStyleESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FONT_STYLE, oldFontStyle, FONT_STYLE_EDEFAULT, oldFontStyleESet));
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
	public String getFontColor() {
		return fontColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFontColor(String newFontColor) {
		String oldFontColor = fontColor;
		fontColor = newFontColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FONT_COLOR, oldFontColor, fontColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDisplayFormat() {
		return displayFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplayFormat(String newDisplayFormat) {
		String oldDisplayFormat = displayFormat;
		displayFormat = newDisplayFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__DISPLAY_FORMAT, oldDisplayFormat, displayFormat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTooltipFormat() {
		return tooltipFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTooltipFormat(String newTooltipFormat) {
		String oldTooltipFormat = tooltipFormat;
		tooltipFormat = newTooltipFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__TOOLTIP_FORMAT, oldTooltipFormat, tooltipFormat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FILL_COLOR:
				return getFillColor();
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FONT_SIZE:
				return getFontSize();
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FONT_STYLE:
				return getFontStyle();
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FONT_COLOR:
				return getFontColor();
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__DISPLAY_FORMAT:
				return getDisplayFormat();
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__TOOLTIP_FORMAT:
				return getTooltipFormat();
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
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FILL_COLOR:
				setFillColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FONT_SIZE:
				setFontSize((Integer)newValue);
				return;
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FONT_STYLE:
				setFontStyle((FontStyleEnum)newValue);
				return;
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FONT_COLOR:
				setFontColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__DISPLAY_FORMAT:
				setDisplayFormat((String)newValue);
				return;
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__TOOLTIP_FORMAT:
				setTooltipFormat((String)newValue);
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
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FILL_COLOR:
				setFillColor(FILL_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FONT_SIZE:
				unsetFontSize();
				return;
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FONT_STYLE:
				unsetFontStyle();
				return;
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FONT_COLOR:
				setFontColor(FONT_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__DISPLAY_FORMAT:
				setDisplayFormat(DISPLAY_FORMAT_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__TOOLTIP_FORMAT:
				setTooltipFormat(TOOLTIP_FORMAT_EDEFAULT);
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
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FILL_COLOR:
				return FILL_COLOR_EDEFAULT == null ? fillColor != null : !FILL_COLOR_EDEFAULT.equals(fillColor);
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FONT_SIZE:
				return isSetFontSize();
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FONT_STYLE:
				return isSetFontStyle();
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__FONT_COLOR:
				return FONT_COLOR_EDEFAULT == null ? fontColor != null : !FONT_COLOR_EDEFAULT.equals(fontColor);
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__DISPLAY_FORMAT:
				return DISPLAY_FORMAT_EDEFAULT == null ? displayFormat != null : !DISPLAY_FORMAT_EDEFAULT.equals(displayFormat);
			case BEViewsConfigurationPackage.VISUAL_ALERT_ACTION__TOOLTIP_FORMAT:
				return TOOLTIP_FORMAT_EDEFAULT == null ? tooltipFormat != null : !TOOLTIP_FORMAT_EDEFAULT.equals(tooltipFormat);
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
		result.append(" (fillColor: ");
		result.append(fillColor);
		result.append(", fontSize: ");
		if (fontSizeESet) result.append(fontSize); else result.append("<unset>");
		result.append(", fontStyle: ");
		if (fontStyleESet) result.append(fontStyle); else result.append("<unset>");
		result.append(", fontColor: ");
		result.append(fontColor);
		result.append(", displayFormat: ");
		result.append(displayFormat);
		result.append(", tooltipFormat: ");
		result.append(tooltipFormat);
		result.append(')');
		return result.toString();
	}

} //VisualAlertActionImpl
