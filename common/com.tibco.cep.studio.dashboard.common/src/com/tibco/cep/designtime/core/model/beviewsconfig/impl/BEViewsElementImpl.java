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
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.impl.EntityImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>BE Views Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsElementImpl#getOriginalElementIdentifier <em>Original Element Identifier</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsElementImpl#getOriginalElementVersion <em>Original Element Version</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.BEViewsElementImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BEViewsElementImpl extends EntityImpl implements BEViewsElement {
	/**
	 * The default value of the '{@link #getOriginalElementIdentifier() <em>Original Element Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalElementIdentifier()
	 * @generated
	 * @ordered
	 */
	protected static final String ORIGINAL_ELEMENT_IDENTIFIER_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getOriginalElementIdentifier() <em>Original Element Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalElementIdentifier()
	 * @generated
	 * @ordered
	 */
	protected String originalElementIdentifier = ORIGINAL_ELEMENT_IDENTIFIER_EDEFAULT;
	/**
	 * The default value of the '{@link #getOriginalElementVersion() <em>Original Element Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalElementVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String ORIGINAL_ELEMENT_VERSION_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getOriginalElementVersion() <em>Original Element Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalElementVersion()
	 * @generated
	 * @ordered
	 */
	protected String originalElementVersion = ORIGINAL_ELEMENT_VERSION_EDEFAULT;
	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BEViewsElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getBEViewsElement();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOriginalElementIdentifier() {
		return originalElementIdentifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOriginalElementIdentifier(String newOriginalElementIdentifier) {
		String oldOriginalElementIdentifier = originalElementIdentifier;
		originalElementIdentifier = newOriginalElementIdentifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER, oldOriginalElementIdentifier, originalElementIdentifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOriginalElementVersion() {
		return originalElementVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOriginalElementVersion(String newOriginalElementVersion) {
		String oldOriginalElementVersion = originalElementVersion;
		originalElementVersion = newOriginalElementVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION, oldOriginalElementVersion, originalElementVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.BE_VIEWS_ELEMENT__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER:
				return getOriginalElementIdentifier();
			case BEViewsConfigurationPackage.BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION:
				return getOriginalElementVersion();
			case BEViewsConfigurationPackage.BE_VIEWS_ELEMENT__VERSION:
				return getVersion();
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
			case BEViewsConfigurationPackage.BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER:
				setOriginalElementIdentifier((String)newValue);
				return;
			case BEViewsConfigurationPackage.BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION:
				setOriginalElementVersion((String)newValue);
				return;
			case BEViewsConfigurationPackage.BE_VIEWS_ELEMENT__VERSION:
				setVersion((String)newValue);
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
			case BEViewsConfigurationPackage.BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER:
				setOriginalElementIdentifier(ORIGINAL_ELEMENT_IDENTIFIER_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION:
				setOriginalElementVersion(ORIGINAL_ELEMENT_VERSION_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.BE_VIEWS_ELEMENT__VERSION:
				setVersion(VERSION_EDEFAULT);
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
			case BEViewsConfigurationPackage.BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_IDENTIFIER:
				return ORIGINAL_ELEMENT_IDENTIFIER_EDEFAULT == null ? originalElementIdentifier != null : !ORIGINAL_ELEMENT_IDENTIFIER_EDEFAULT.equals(originalElementIdentifier);
			case BEViewsConfigurationPackage.BE_VIEWS_ELEMENT__ORIGINAL_ELEMENT_VERSION:
				return ORIGINAL_ELEMENT_VERSION_EDEFAULT == null ? originalElementVersion != null : !ORIGINAL_ELEMENT_VERSION_EDEFAULT.equals(originalElementVersion);
			case BEViewsConfigurationPackage.BE_VIEWS_ELEMENT__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
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
		result.append(" (originalElementIdentifier: ");
		result.append(originalElementIdentifier);
		result.append(", originalElementVersion: ");
		result.append(originalElementVersion);
		result.append(", version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

} //BEViewsElementImpl
