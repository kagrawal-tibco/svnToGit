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
import com.tibco.cep.designtime.core.model.beviewsconfig.HeaderLink;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Header Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.HeaderLinkImpl#getUrlName <em>Url Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.HeaderLinkImpl#getUrlLink <em>Url Link</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class HeaderLinkImpl extends BEViewsElementImpl implements HeaderLink {
	/**
	 * The default value of the '{@link #getUrlName() <em>Url Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrlName()
	 * @generated
	 * @ordered
	 */
	protected static final String URL_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUrlName() <em>Url Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrlName()
	 * @generated
	 * @ordered
	 */
	protected String urlName = URL_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getUrlLink() <em>Url Link</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrlLink()
	 * @generated
	 * @ordered
	 */
	protected static final String URL_LINK_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUrlLink() <em>Url Link</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrlLink()
	 * @generated
	 * @ordered
	 */
	protected String urlLink = URL_LINK_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HeaderLinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getHeaderLink();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUrlName() {
		return urlName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUrlName(String newUrlName) {
		String oldUrlName = urlName;
		urlName = newUrlName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.HEADER_LINK__URL_NAME, oldUrlName, urlName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUrlLink() {
		return urlLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUrlLink(String newUrlLink) {
		String oldUrlLink = urlLink;
		urlLink = newUrlLink;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.HEADER_LINK__URL_LINK, oldUrlLink, urlLink));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.HEADER_LINK__URL_NAME:
				return getUrlName();
			case BEViewsConfigurationPackage.HEADER_LINK__URL_LINK:
				return getUrlLink();
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
			case BEViewsConfigurationPackage.HEADER_LINK__URL_NAME:
				setUrlName((String)newValue);
				return;
			case BEViewsConfigurationPackage.HEADER_LINK__URL_LINK:
				setUrlLink((String)newValue);
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
			case BEViewsConfigurationPackage.HEADER_LINK__URL_NAME:
				setUrlName(URL_NAME_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.HEADER_LINK__URL_LINK:
				setUrlLink(URL_LINK_EDEFAULT);
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
			case BEViewsConfigurationPackage.HEADER_LINK__URL_NAME:
				return URL_NAME_EDEFAULT == null ? urlName != null : !URL_NAME_EDEFAULT.equals(urlName);
			case BEViewsConfigurationPackage.HEADER_LINK__URL_LINK:
				return URL_LINK_EDEFAULT == null ? urlLink != null : !URL_LINK_EDEFAULT.equals(urlLink);
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
		result.append(" (urlName: ");
		result.append(urlName);
		result.append(", urlLink: ");
		result.append(urlLink);
		result.append(')');
		return result.toString();
	}

} //HeaderLinkImpl
