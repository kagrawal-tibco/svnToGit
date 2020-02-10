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

import com.tibco.be.util.config.cdd.BackingStoreForPropertyConfig;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.OverrideConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Backing Store For Property Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreForPropertyConfigImpl#getReverseReferences <em>Reverse References</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreForPropertyConfigImpl#getMaxSize <em>Max Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.BackingStoreForPropertyConfigImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BackingStoreForPropertyConfigImpl extends EObjectImpl implements BackingStoreForPropertyConfig {
	/**
	 * The cached value of the '{@link #getReverseReferences() <em>Reverse References</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReverseReferences()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig reverseReferences;

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
	 * The cached value of the '{@link #getName() <em>Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BackingStoreForPropertyConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getBackingStoreForPropertyConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getReverseReferences() {
		return reverseReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReverseReferences(OverrideConfig newReverseReferences, NotificationChain msgs) {
		OverrideConfig oldReverseReferences = reverseReferences;
		reverseReferences = newReverseReferences;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__REVERSE_REFERENCES, oldReverseReferences, newReverseReferences);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReverseReferences(OverrideConfig newReverseReferences) {
		if (newReverseReferences != reverseReferences) {
			NotificationChain msgs = null;
			if (reverseReferences != null)
				msgs = ((InternalEObject)reverseReferences).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__REVERSE_REFERENCES, null, msgs);
			if (newReverseReferences != null)
				msgs = ((InternalEObject)newReverseReferences).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__REVERSE_REFERENCES, null, msgs);
			msgs = basicSetReverseReferences(newReverseReferences, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__REVERSE_REFERENCES, newReverseReferences, newReverseReferences));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__MAX_SIZE, oldMaxSize, newMaxSize);
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
				msgs = ((InternalEObject)maxSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__MAX_SIZE, null, msgs);
			if (newMaxSize != null)
				msgs = ((InternalEObject)newMaxSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__MAX_SIZE, null, msgs);
			msgs = basicSetMaxSize(newMaxSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__MAX_SIZE, newMaxSize, newMaxSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetName(OverrideConfig newName, NotificationChain msgs) {
		OverrideConfig oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__NAME, oldName, newName);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(OverrideConfig newName) {
		if (newName != name) {
			NotificationChain msgs = null;
			if (name != null)
				msgs = ((InternalEObject)name).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__NAME, null, msgs);
			if (newName != null)
				msgs = ((InternalEObject)newName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__NAME, null, msgs);
			msgs = basicSetName(newName, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__NAME, newName, newName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__REVERSE_REFERENCES:
				return basicSetReverseReferences(null, msgs);
			case CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__MAX_SIZE:
				return basicSetMaxSize(null, msgs);
			case CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__NAME:
				return basicSetName(null, msgs);
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
			case CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__REVERSE_REFERENCES:
				return getReverseReferences();
			case CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__MAX_SIZE:
				return getMaxSize();
			case CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__NAME:
				return getName();
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
			case CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__REVERSE_REFERENCES:
				setReverseReferences((OverrideConfig)newValue);
				return;
			case CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__MAX_SIZE:
				setMaxSize((OverrideConfig)newValue);
				return;
			case CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__NAME:
				setName((OverrideConfig)newValue);
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
			case CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__REVERSE_REFERENCES:
				setReverseReferences((OverrideConfig)null);
				return;
			case CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__MAX_SIZE:
				setMaxSize((OverrideConfig)null);
				return;
			case CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__NAME:
				setName((OverrideConfig)null);
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
			case CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__REVERSE_REFERENCES:
				return reverseReferences != null;
			case CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__MAX_SIZE:
				return maxSize != null;
			case CddPackage.BACKING_STORE_FOR_PROPERTY_CONFIG__NAME:
				return name != null;
		}
		return super.eIsSet(featureID);
	}

} //BackingStoreForPropertyConfigImpl
