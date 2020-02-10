/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.Page;
import com.tibco.cep.designtime.core.model.beviewsconfig.Skin;
import com.tibco.cep.designtime.core.model.beviewsconfig.View;
import com.tibco.cep.designtime.core.model.beviewsconfig.ViewAttribute;
import com.tibco.cep.designtime.core.model.beviewsconfig.ViewLocale;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>View</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewImpl#getDefaultPage <em>Default Page</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewImpl#getAccessiblePage <em>Accessible Page</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewImpl#getSkin <em>Skin</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewImpl#getLocale <em>Locale</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ViewImpl#getAttribute <em>Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ViewImpl extends BEViewsElementImpl implements View {
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
	 * The cached value of the '{@link #getDefaultPage() <em>Default Page</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultPage()
	 * @generated
	 * @ordered
	 */
	protected Page defaultPage;

	/**
	 * The cached value of the '{@link #getAccessiblePage() <em>Accessible Page</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccessiblePage()
	 * @generated
	 * @ordered
	 */
	protected EList<Page> accessiblePage;

	/**
	 * The cached value of the '{@link #getSkin() <em>Skin</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSkin()
	 * @generated
	 * @ordered
	 */
	protected Skin skin;

	/**
	 * The cached value of the '{@link #getLocale() <em>Locale</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocale()
	 * @generated
	 * @ordered
	 */
	protected ViewLocale locale;

	/**
	 * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttribute()
	 * @generated
	 * @ordered
	 */
	protected EList<ViewAttribute> attribute;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ViewImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getView();
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VIEW__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Page getDefaultPage() {
		return defaultPage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultPage(Page newDefaultPage) {
		Page oldDefaultPage = defaultPage;
		defaultPage = newDefaultPage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VIEW__DEFAULT_PAGE, oldDefaultPage, defaultPage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Page> getAccessiblePage() {
		if (accessiblePage == null) {
			accessiblePage = new EObjectEList<Page>(Page.class, this, BEViewsConfigurationPackage.VIEW__ACCESSIBLE_PAGE);
		}
		return accessiblePage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Skin getSkin() {
		return skin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSkin(Skin newSkin) {
		Skin oldSkin = skin;
		skin = newSkin;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VIEW__SKIN, oldSkin, skin));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ViewLocale getLocale() {
		return locale;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLocale(ViewLocale newLocale, NotificationChain msgs) {
		ViewLocale oldLocale = locale;
		locale = newLocale;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VIEW__LOCALE, oldLocale, newLocale);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocale(ViewLocale newLocale) {
		if (newLocale != locale) {
			NotificationChain msgs = null;
			if (locale != null)
				msgs = ((InternalEObject)locale).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.VIEW__LOCALE, null, msgs);
			if (newLocale != null)
				msgs = ((InternalEObject)newLocale).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.VIEW__LOCALE, null, msgs);
			msgs = basicSetLocale(newLocale, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.VIEW__LOCALE, newLocale, newLocale));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ViewAttribute> getAttribute() {
		if (attribute == null) {
			attribute = new EObjectContainmentEList<ViewAttribute>(ViewAttribute.class, this, BEViewsConfigurationPackage.VIEW__ATTRIBUTE);
		}
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.VIEW__LOCALE:
				return basicSetLocale(null, msgs);
			case BEViewsConfigurationPackage.VIEW__ATTRIBUTE:
				return ((InternalEList<?>)getAttribute()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.VIEW__DISPLAY_NAME:
				return getDisplayName();
			case BEViewsConfigurationPackage.VIEW__DEFAULT_PAGE:
				return getDefaultPage();
			case BEViewsConfigurationPackage.VIEW__ACCESSIBLE_PAGE:
				return getAccessiblePage();
			case BEViewsConfigurationPackage.VIEW__SKIN:
				return getSkin();
			case BEViewsConfigurationPackage.VIEW__LOCALE:
				return getLocale();
			case BEViewsConfigurationPackage.VIEW__ATTRIBUTE:
				return getAttribute();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BEViewsConfigurationPackage.VIEW__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case BEViewsConfigurationPackage.VIEW__DEFAULT_PAGE:
				setDefaultPage((Page)newValue);
				return;
			case BEViewsConfigurationPackage.VIEW__ACCESSIBLE_PAGE:
				getAccessiblePage().clear();
				getAccessiblePage().addAll((Collection<? extends Page>)newValue);
				return;
			case BEViewsConfigurationPackage.VIEW__SKIN:
				setSkin((Skin)newValue);
				return;
			case BEViewsConfigurationPackage.VIEW__LOCALE:
				setLocale((ViewLocale)newValue);
				return;
			case BEViewsConfigurationPackage.VIEW__ATTRIBUTE:
				getAttribute().clear();
				getAttribute().addAll((Collection<? extends ViewAttribute>)newValue);
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
			case BEViewsConfigurationPackage.VIEW__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.VIEW__DEFAULT_PAGE:
				setDefaultPage((Page)null);
				return;
			case BEViewsConfigurationPackage.VIEW__ACCESSIBLE_PAGE:
				getAccessiblePage().clear();
				return;
			case BEViewsConfigurationPackage.VIEW__SKIN:
				setSkin((Skin)null);
				return;
			case BEViewsConfigurationPackage.VIEW__LOCALE:
				setLocale((ViewLocale)null);
				return;
			case BEViewsConfigurationPackage.VIEW__ATTRIBUTE:
				getAttribute().clear();
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
			case BEViewsConfigurationPackage.VIEW__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case BEViewsConfigurationPackage.VIEW__DEFAULT_PAGE:
				return defaultPage != null;
			case BEViewsConfigurationPackage.VIEW__ACCESSIBLE_PAGE:
				return accessiblePage != null && !accessiblePage.isEmpty();
			case BEViewsConfigurationPackage.VIEW__SKIN:
				return skin != null;
			case BEViewsConfigurationPackage.VIEW__LOCALE:
				return locale != null;
			case BEViewsConfigurationPackage.VIEW__ATTRIBUTE:
				return attribute != null && !attribute.isEmpty();
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
		result.append(')');
		return result.toString();
	}

} //ViewImpl
