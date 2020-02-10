/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.element.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.designtime.core.model.element.BaseInstance;
import com.tibco.cep.designtime.core.model.element.ElementPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Base Instance</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.BaseInstanceImpl#getResourcePath <em>Resource Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BaseInstanceImpl extends EObjectImpl implements BaseInstance {
	/**
	 * The default value of the '{@link #getResourcePath() <em>Resource Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourcePath()
	 * @generated
	 * @ordered
	 */
	protected static final String RESOURCE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResourcePath() <em>Resource Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourcePath()
	 * @generated
	 * @ordered
	 */
	protected String resourcePath = RESOURCE_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BaseInstanceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ElementPackage.Literals.BASE_INSTANCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getResourcePath() {
		return resourcePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourcePath(String newResourcePath) {
		String oldResourcePath = resourcePath;
		resourcePath = newResourcePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.BASE_INSTANCE__RESOURCE_PATH, oldResourcePath, resourcePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ElementPackage.BASE_INSTANCE__RESOURCE_PATH:
				return getResourcePath();
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
			case ElementPackage.BASE_INSTANCE__RESOURCE_PATH:
				setResourcePath((String)newValue);
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
			case ElementPackage.BASE_INSTANCE__RESOURCE_PATH:
				setResourcePath(RESOURCE_PATH_EDEFAULT);
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
			case ElementPackage.BASE_INSTANCE__RESOURCE_PATH:
				return RESOURCE_PATH_EDEFAULT == null ? resourcePath != null : !RESOURCE_PATH_EDEFAULT.equals(resourcePath);
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
		result.append(" (resourcePath: ");
		result.append(resourcePath);
		result.append(')');
		return result.toString();
	}

} //BaseInstanceImpl
