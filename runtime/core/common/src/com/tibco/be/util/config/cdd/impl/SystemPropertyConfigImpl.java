/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.SystemPropertyConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>System Property Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SystemPropertyConfigImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.SystemPropertyConfigImpl#getSystemProperty <em>System Property</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SystemPropertyConfigImpl extends EObjectImpl implements SystemPropertyConfig {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * The default value of the '{@link #getSystemProperty() <em>System Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSystemProperty()
	 * @generated
	 * @ordered
	 */
	protected static final String SYSTEM_PROPERTY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSystemProperty() <em>System Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSystemProperty()
	 * @generated
	 * @ordered
	 */
	protected String systemProperty = SYSTEM_PROPERTY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SystemPropertyConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getSystemPropertyConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, CddPackage.SYSTEM_PROPERTY_CONFIG__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSystemProperty() {
		return systemProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSystemProperty(String newSystemProperty) {
		String oldSystemProperty = systemProperty;
		systemProperty = newSystemProperty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.SYSTEM_PROPERTY_CONFIG__SYSTEM_PROPERTY, oldSystemProperty, systemProperty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.SYSTEM_PROPERTY_CONFIG__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
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
			case CddPackage.SYSTEM_PROPERTY_CONFIG__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case CddPackage.SYSTEM_PROPERTY_CONFIG__SYSTEM_PROPERTY:
				return getSystemProperty();
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
			case CddPackage.SYSTEM_PROPERTY_CONFIG__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case CddPackage.SYSTEM_PROPERTY_CONFIG__SYSTEM_PROPERTY:
				setSystemProperty((String)newValue);
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
			case CddPackage.SYSTEM_PROPERTY_CONFIG__MIXED:
				getMixed().clear();
				return;
			case CddPackage.SYSTEM_PROPERTY_CONFIG__SYSTEM_PROPERTY:
				setSystemProperty(SYSTEM_PROPERTY_EDEFAULT);
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
			case CddPackage.SYSTEM_PROPERTY_CONFIG__MIXED:
				return mixed != null && !mixed.isEmpty();
			case CddPackage.SYSTEM_PROPERTY_CONFIG__SYSTEM_PROPERTY:
				return SYSTEM_PROPERTY_EDEFAULT == null ? systemProperty != null : !SYSTEM_PROPERTY_EDEFAULT.equals(systemProperty);
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
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(", systemProperty: ");
		result.append(systemProperty);
		result.append(')');
		return result.toString();
	}

} //SystemPropertyConfigImpl
