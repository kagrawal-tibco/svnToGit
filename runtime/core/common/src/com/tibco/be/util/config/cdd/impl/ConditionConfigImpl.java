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

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.ConditionConfig;
import com.tibco.be.util.config.cdd.GetPropertyConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Condition Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ConditionConfigImpl#getGetProperty <em>Get Property</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConditionConfigImpl extends EObjectImpl implements ConditionConfig {
	/**
	 * The cached value of the '{@link #getGetProperty() <em>Get Property</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGetProperty()
	 * @generated
	 * @ordered
	 */
	protected GetPropertyConfig getProperty;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConditionConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getConditionConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GetPropertyConfig getGetProperty() {
		return getProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGetProperty(GetPropertyConfig newGetProperty, NotificationChain msgs) {
		GetPropertyConfig oldGetProperty = getProperty;
		getProperty = newGetProperty;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CONDITION_CONFIG__GET_PROPERTY, oldGetProperty, newGetProperty);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGetProperty(GetPropertyConfig newGetProperty) {
		if (newGetProperty != getProperty) {
			NotificationChain msgs = null;
			if (getProperty != null)
				msgs = ((InternalEObject)getProperty).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CONDITION_CONFIG__GET_PROPERTY, null, msgs);
			if (newGetProperty != null)
				msgs = ((InternalEObject)newGetProperty).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CONDITION_CONFIG__GET_PROPERTY, null, msgs);
			msgs = basicSetGetProperty(newGetProperty, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CONDITION_CONFIG__GET_PROPERTY, newGetProperty, newGetProperty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.CONDITION_CONFIG__GET_PROPERTY:
				return basicSetGetProperty(null, msgs);
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
			case CddPackage.CONDITION_CONFIG__GET_PROPERTY:
				return getGetProperty();
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
			case CddPackage.CONDITION_CONFIG__GET_PROPERTY:
				setGetProperty((GetPropertyConfig)newValue);
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
			case CddPackage.CONDITION_CONFIG__GET_PROPERTY:
				setGetProperty((GetPropertyConfig)null);
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
			case CddPackage.CONDITION_CONFIG__GET_PROPERTY:
				return getProperty != null;
		}
		return super.eIsSet(featureID);
	}

} //ConditionConfigImpl
