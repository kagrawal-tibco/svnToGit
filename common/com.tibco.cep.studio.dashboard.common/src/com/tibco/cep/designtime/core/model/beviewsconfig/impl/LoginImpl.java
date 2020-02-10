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
import com.tibco.cep.designtime.core.model.beviewsconfig.Login;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Login</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.LoginImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.LoginImpl#getImageURL <em>Image URL</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LoginImpl extends BEViewsElementImpl implements Login {
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
	 * The default value of the '{@link #getImageURL() <em>Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImageURL()
	 * @generated
	 * @ordered
	 */
	protected static final String IMAGE_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImageURL() <em>Image URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImageURL()
	 * @generated
	 * @ordered
	 */
	protected String imageURL = IMAGE_URL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LoginImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getLogin();
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.LOGIN__TITLE, oldTitle, title));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImageURL(String newImageURL) {
		String oldImageURL = imageURL;
		imageURL = newImageURL;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.LOGIN__IMAGE_URL, oldImageURL, imageURL));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.LOGIN__TITLE:
				return getTitle();
			case BEViewsConfigurationPackage.LOGIN__IMAGE_URL:
				return getImageURL();
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
			case BEViewsConfigurationPackage.LOGIN__TITLE:
				setTitle((String)newValue);
				return;
			case BEViewsConfigurationPackage.LOGIN__IMAGE_URL:
				setImageURL((String)newValue);
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
			case BEViewsConfigurationPackage.LOGIN__TITLE:
				setTitle(TITLE_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.LOGIN__IMAGE_URL:
				setImageURL(IMAGE_URL_EDEFAULT);
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
			case BEViewsConfigurationPackage.LOGIN__TITLE:
				return TITLE_EDEFAULT == null ? title != null : !TITLE_EDEFAULT.equals(title);
			case BEViewsConfigurationPackage.LOGIN__IMAGE_URL:
				return IMAGE_URL_EDEFAULT == null ? imageURL != null : !IMAGE_URL_EDEFAULT.equals(imageURL);
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
		result.append(", imageURL: ");
		result.append(imageURL);
		result.append(')');
		return result.toString();
	}

} //LoginImpl
