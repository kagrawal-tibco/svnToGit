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
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Series Color</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SeriesColorImpl#getBaseColor <em>Base Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.SeriesColorImpl#getHighlightColor <em>Highlight Color</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SeriesColorImpl extends BEViewsElementImpl implements SeriesColor {
	/**
	 * The default value of the '{@link #getBaseColor() <em>Base Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseColor()
	 * @generated
	 * @ordered
	 */
	protected static final String BASE_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBaseColor() <em>Base Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseColor()
	 * @generated
	 * @ordered
	 */
	protected String baseColor = BASE_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getHighlightColor() <em>Highlight Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHighlightColor()
	 * @generated
	 * @ordered
	 */
	protected static final String HIGHLIGHT_COLOR_EDEFAULT = "FFFFFF";

	/**
	 * The cached value of the '{@link #getHighlightColor() <em>Highlight Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHighlightColor()
	 * @generated
	 * @ordered
	 */
	protected String highlightColor = HIGHLIGHT_COLOR_EDEFAULT;

	/**
	 * This is true if the Highlight Color attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean highlightColorESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SeriesColorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getSeriesColor();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBaseColor() {
		return baseColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBaseColor(String newBaseColor) {
		String oldBaseColor = baseColor;
		baseColor = newBaseColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SERIES_COLOR__BASE_COLOR, oldBaseColor, baseColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHighlightColor() {
		return highlightColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHighlightColor(String newHighlightColor) {
		String oldHighlightColor = highlightColor;
		highlightColor = newHighlightColor;
		boolean oldHighlightColorESet = highlightColorESet;
		highlightColorESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SERIES_COLOR__HIGHLIGHT_COLOR, oldHighlightColor, highlightColor, !oldHighlightColorESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetHighlightColor() {
		String oldHighlightColor = highlightColor;
		boolean oldHighlightColorESet = highlightColorESet;
		highlightColor = HIGHLIGHT_COLOR_EDEFAULT;
		highlightColorESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.SERIES_COLOR__HIGHLIGHT_COLOR, oldHighlightColor, HIGHLIGHT_COLOR_EDEFAULT, oldHighlightColorESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetHighlightColor() {
		return highlightColorESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.SERIES_COLOR__BASE_COLOR:
				return getBaseColor();
			case BEViewsConfigurationPackage.SERIES_COLOR__HIGHLIGHT_COLOR:
				return getHighlightColor();
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
			case BEViewsConfigurationPackage.SERIES_COLOR__BASE_COLOR:
				setBaseColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.SERIES_COLOR__HIGHLIGHT_COLOR:
				setHighlightColor((String)newValue);
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
			case BEViewsConfigurationPackage.SERIES_COLOR__BASE_COLOR:
				setBaseColor(BASE_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.SERIES_COLOR__HIGHLIGHT_COLOR:
				unsetHighlightColor();
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
			case BEViewsConfigurationPackage.SERIES_COLOR__BASE_COLOR:
				return BASE_COLOR_EDEFAULT == null ? baseColor != null : !BASE_COLOR_EDEFAULT.equals(baseColor);
			case BEViewsConfigurationPackage.SERIES_COLOR__HIGHLIGHT_COLOR:
				return isSetHighlightColor();
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
		result.append(" (baseColor: ");
		result.append(baseColor);
		result.append(", highlightColor: ");
		if (highlightColorESet) result.append(highlightColor); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //SeriesColorImpl
