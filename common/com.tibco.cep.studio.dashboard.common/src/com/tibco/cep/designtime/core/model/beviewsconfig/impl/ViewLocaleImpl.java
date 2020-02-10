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
import com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>View Locale</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewLocaleImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewLocaleImpl#getLocale <em>Locale</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewLocaleImpl#getTimeZone <em>Time Zone</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewLocaleImpl#getTimeFormat <em>Time Format</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ViewLocaleImpl extends BEViewsElementImpl implements ViewLocale {
	/**
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected String displayName = DISPLAY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getLocale() <em>Locale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocale()
	 * @generated
	 * @ordered
	 */
	protected static final String LOCALE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLocale() <em>Locale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocale()
	 * @generated
	 * @ordered
	 */
	protected String locale = LOCALE_EDEFAULT;

	/**
	 * The default value of the '{@link #getTimeZone() <em>Time Zone</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeZone()
	 * @generated
	 * @ordered
	 */
	protected static final String TIME_ZONE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTimeZone() <em>Time Zone</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeZone()
	 * @generated
	 * @ordered
	 */
	protected String timeZone = TIME_ZONE_EDEFAULT;

	/**
	 * The default value of the '{@link #getTimeFormat() <em>Time Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeFormat()
	 * @generated
	 * @ordered
	 */
	protected static final String TIME_FORMAT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTimeFormat() <em>Time Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeFormat()
	 * @generated
	 * @ordered
	 */
	protected String timeFormat = TIME_FORMAT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ViewLocaleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getViewLocale();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplayName(String newDisplayName) {
		String oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VIEW_LOCALE__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocale(String newLocale) {
		String oldLocale = locale;
		locale = newLocale;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VIEW_LOCALE__LOCALE, oldLocale, locale));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeZone(String newTimeZone) {
		String oldTimeZone = timeZone;
		timeZone = newTimeZone;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VIEW_LOCALE__TIME_ZONE, oldTimeZone, timeZone));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTimeFormat() {
		return timeFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeFormat(String newTimeFormat) {
		String oldTimeFormat = timeFormat;
		timeFormat = newTimeFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VIEW_LOCALE__TIME_FORMAT, oldTimeFormat, timeFormat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.VIEW_LOCALE__DISPLAY_NAME:
				return getDisplayName();
			case BEViewsConfigurationPackage.VIEW_LOCALE__LOCALE:
				return getLocale();
			case BEViewsConfigurationPackage.VIEW_LOCALE__TIME_ZONE:
				return getTimeZone();
			case BEViewsConfigurationPackage.VIEW_LOCALE__TIME_FORMAT:
				return getTimeFormat();
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
			case BEViewsConfigurationPackage.VIEW_LOCALE__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case BEViewsConfigurationPackage.VIEW_LOCALE__LOCALE:
				setLocale((String)newValue);
				return;
			case BEViewsConfigurationPackage.VIEW_LOCALE__TIME_ZONE:
				setTimeZone((String)newValue);
				return;
			case BEViewsConfigurationPackage.VIEW_LOCALE__TIME_FORMAT:
				setTimeFormat((String)newValue);
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
			case BEViewsConfigurationPackage.VIEW_LOCALE__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.VIEW_LOCALE__LOCALE:
				setLocale(LOCALE_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.VIEW_LOCALE__TIME_ZONE:
				setTimeZone(TIME_ZONE_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.VIEW_LOCALE__TIME_FORMAT:
				setTimeFormat(TIME_FORMAT_EDEFAULT);
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
			case BEViewsConfigurationPackage.VIEW_LOCALE__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case BEViewsConfigurationPackage.VIEW_LOCALE__LOCALE:
				return LOCALE_EDEFAULT == null ? locale != null : !LOCALE_EDEFAULT.equals(locale);
			case BEViewsConfigurationPackage.VIEW_LOCALE__TIME_ZONE:
				return TIME_ZONE_EDEFAULT == null ? timeZone != null : !TIME_ZONE_EDEFAULT.equals(timeZone);
			case BEViewsConfigurationPackage.VIEW_LOCALE__TIME_FORMAT:
				return TIME_FORMAT_EDEFAULT == null ? timeFormat != null : !TIME_FORMAT_EDEFAULT.equals(timeFormat);
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
		result.append(" (displayName: ");
		result.append(displayName);
		result.append(", locale: ");
		result.append(locale);
		result.append(", timeZone: ");
		result.append(timeZone);
		result.append(", timeFormat: ");
		result.append(timeFormat);
		result.append(')');
		return result.toString();
	}

} //ViewLocaleImpl
