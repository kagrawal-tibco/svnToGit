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
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.Header;
import com.tibco.cep.designtime.core.model.beviewsconfig.HeaderLink;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Header</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.HeaderImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.HeaderImpl#getBrandingImage <em>Branding Image</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.HeaderImpl#getBrandingText <em>Branding Text</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.HeaderImpl#getLink <em>Link</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.HeaderImpl#getHeaderLink <em>Header Link</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class HeaderImpl extends BEViewsElementImpl implements Header {
	/**
	 * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected static final String TITLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected String title = TITLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getBrandingImage() <em>Branding Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBrandingImage()
	 * @generated
	 * @ordered
	 */
	protected static final String BRANDING_IMAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBrandingImage() <em>Branding Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBrandingImage()
	 * @generated
	 * @ordered
	 */
	protected String brandingImage = BRANDING_IMAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getBrandingText() <em>Branding Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBrandingText()
	 * @generated
	 * @ordered
	 */
	protected static final String BRANDING_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBrandingText() <em>Branding Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBrandingText()
	 * @generated
	 * @ordered
	 */
	protected String brandingText = BRANDING_TEXT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLink() <em>Link</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLink()
	 * @generated
	 * @ordered
	 */
	protected EList<String> link;

	/**
	 * The cached value of the '{@link #getHeaderLink() <em>Header Link</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderLink()
	 * @generated
	 * @ordered
	 */
	protected EList<HeaderLink> headerLink;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HeaderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getHeader();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTitle(String newTitle) {
		String oldTitle = title;
		title = newTitle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.HEADER__TITLE, oldTitle, title));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBrandingImage() {
		return brandingImage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBrandingImage(String newBrandingImage) {
		String oldBrandingImage = brandingImage;
		brandingImage = newBrandingImage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.HEADER__BRANDING_IMAGE, oldBrandingImage, brandingImage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBrandingText() {
		return brandingText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBrandingText(String newBrandingText) {
		String oldBrandingText = brandingText;
		brandingText = newBrandingText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.HEADER__BRANDING_TEXT, oldBrandingText, brandingText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getLink() {
		if (link == null) {
			link = new EDataTypeEList<String>(String.class, this, BEViewsConfigurationPackage.HEADER__LINK);
		}
		return link;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<HeaderLink> getHeaderLink() {
		if (headerLink == null) {
			headerLink = new EObjectContainmentEList<HeaderLink>(HeaderLink.class, this, BEViewsConfigurationPackage.HEADER__HEADER_LINK);
		}
		return headerLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.HEADER__HEADER_LINK:
				return ((InternalEList<?>)getHeaderLink()).basicRemove(otherEnd, msgs);
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
			case BEViewsConfigurationPackage.HEADER__TITLE:
				return getTitle();
			case BEViewsConfigurationPackage.HEADER__BRANDING_IMAGE:
				return getBrandingImage();
			case BEViewsConfigurationPackage.HEADER__BRANDING_TEXT:
				return getBrandingText();
			case BEViewsConfigurationPackage.HEADER__LINK:
				return getLink();
			case BEViewsConfigurationPackage.HEADER__HEADER_LINK:
				return getHeaderLink();
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
			case BEViewsConfigurationPackage.HEADER__TITLE:
				setTitle((String)newValue);
				return;
			case BEViewsConfigurationPackage.HEADER__BRANDING_IMAGE:
				setBrandingImage((String)newValue);
				return;
			case BEViewsConfigurationPackage.HEADER__BRANDING_TEXT:
				setBrandingText((String)newValue);
				return;
			case BEViewsConfigurationPackage.HEADER__LINK:
				getLink().clear();
				getLink().addAll((Collection<? extends String>)newValue);
				return;
			case BEViewsConfigurationPackage.HEADER__HEADER_LINK:
				getHeaderLink().clear();
				getHeaderLink().addAll((Collection<? extends HeaderLink>)newValue);
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
			case BEViewsConfigurationPackage.HEADER__TITLE:
				setTitle(TITLE_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.HEADER__BRANDING_IMAGE:
				setBrandingImage(BRANDING_IMAGE_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.HEADER__BRANDING_TEXT:
				setBrandingText(BRANDING_TEXT_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.HEADER__LINK:
				getLink().clear();
				return;
			case BEViewsConfigurationPackage.HEADER__HEADER_LINK:
				getHeaderLink().clear();
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
			case BEViewsConfigurationPackage.HEADER__TITLE:
				return TITLE_EDEFAULT == null ? title != null : !TITLE_EDEFAULT.equals(title);
			case BEViewsConfigurationPackage.HEADER__BRANDING_IMAGE:
				return BRANDING_IMAGE_EDEFAULT == null ? brandingImage != null : !BRANDING_IMAGE_EDEFAULT.equals(brandingImage);
			case BEViewsConfigurationPackage.HEADER__BRANDING_TEXT:
				return BRANDING_TEXT_EDEFAULT == null ? brandingText != null : !BRANDING_TEXT_EDEFAULT.equals(brandingText);
			case BEViewsConfigurationPackage.HEADER__LINK:
				return link != null && !link.isEmpty();
			case BEViewsConfigurationPackage.HEADER__HEADER_LINK:
				return headerLink != null && !headerLink.isEmpty();
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
		result.append(" (title: ");
		result.append(title);
		result.append(", brandingImage: ");
		result.append(brandingImage);
		result.append(", brandingText: ");
		result.append(brandingText);
		result.append(", link: ");
		result.append(link);
		result.append(')');
		return result.toString();
	}

} //HeaderImpl
