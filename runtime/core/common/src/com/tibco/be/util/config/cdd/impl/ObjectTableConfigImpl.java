/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.ObjectTableConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Object Table Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ObjectTableConfigImpl#getMaxSize <em>Max Size</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ObjectTableConfigImpl extends EObjectImpl implements ObjectTableConfig {
	/**
	 * The cached value of the '{@link #getMaxSize() <em>Max Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxSize()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig maxSize;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ObjectTableConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getObjectTableConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getMaxSize() {
		return maxSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaxSize(OverrideConfig newMaxSize, NotificationChain msgs) {
		OverrideConfig oldMaxSize = maxSize;
		maxSize = newMaxSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.OBJECT_TABLE_CONFIG__MAX_SIZE, oldMaxSize, newMaxSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxSize(OverrideConfig newMaxSize) {
		if (newMaxSize != maxSize) {
			NotificationChain msgs = null;
			if (maxSize != null)
				msgs = ((InternalEObject)maxSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.OBJECT_TABLE_CONFIG__MAX_SIZE, null, msgs);
			if (newMaxSize != null)
				msgs = ((InternalEObject)newMaxSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.OBJECT_TABLE_CONFIG__MAX_SIZE, null, msgs);
			msgs = basicSetMaxSize(newMaxSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.OBJECT_TABLE_CONFIG__MAX_SIZE, newMaxSize, newMaxSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.OBJECT_TABLE_CONFIG__MAX_SIZE:
				return basicSetMaxSize(null, msgs);
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
			case CddPackage.OBJECT_TABLE_CONFIG__MAX_SIZE:
				return getMaxSize();
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
			case CddPackage.OBJECT_TABLE_CONFIG__MAX_SIZE:
				setMaxSize((OverrideConfig)newValue);
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
			case CddPackage.OBJECT_TABLE_CONFIG__MAX_SIZE:
				setMaxSize((OverrideConfig)null);
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
			case CddPackage.OBJECT_TABLE_CONFIG__MAX_SIZE:
				return maxSize != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public Map<Object, Object> toProperties() {
        final Properties props = new Properties();
        CddTools.addEntryFromMixed(props, SystemProperty.OBJECT_TABLE_BACK_SIZE_LIMIT.getPropertyName(), this.maxSize, true); 
        return props;
	}

} //ObjectTableConfigImpl
