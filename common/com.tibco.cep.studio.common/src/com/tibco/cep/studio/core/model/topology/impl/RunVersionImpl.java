/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.core.model.topology.BeRuntime;
import com.tibco.cep.studio.core.model.topology.RunVersion;
import com.tibco.cep.studio.core.model.topology.TopologyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Run Version</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.RunVersionImpl#getBeRuntime <em>Be Runtime</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RunVersionImpl extends EObjectImpl implements RunVersion {
	/**
	 * The cached value of the '{@link #getBeRuntime() <em>Be Runtime</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBeRuntime()
	 * @generated
	 * @ordered
	 */
	protected BeRuntime beRuntime;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RunVersionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TopologyPackage.Literals.RUN_VERSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BeRuntime getBeRuntime() {
		return beRuntime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBeRuntime(BeRuntime newBeRuntime, NotificationChain msgs) {
		BeRuntime oldBeRuntime = beRuntime;
		beRuntime = newBeRuntime;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TopologyPackage.RUN_VERSION__BE_RUNTIME, oldBeRuntime, newBeRuntime);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBeRuntime(BeRuntime newBeRuntime) {
		if (newBeRuntime != beRuntime) {
			NotificationChain msgs = null;
			if (beRuntime != null)
				msgs = ((InternalEObject)beRuntime).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.RUN_VERSION__BE_RUNTIME, null, msgs);
			if (newBeRuntime != null)
				msgs = ((InternalEObject)newBeRuntime).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.RUN_VERSION__BE_RUNTIME, null, msgs);
			msgs = basicSetBeRuntime(newBeRuntime, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.RUN_VERSION__BE_RUNTIME, newBeRuntime, newBeRuntime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TopologyPackage.RUN_VERSION__BE_RUNTIME:
				return basicSetBeRuntime(null, msgs);
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
			case TopologyPackage.RUN_VERSION__BE_RUNTIME:
				return getBeRuntime();
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
			case TopologyPackage.RUN_VERSION__BE_RUNTIME:
				setBeRuntime((BeRuntime)newValue);
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
			case TopologyPackage.RUN_VERSION__BE_RUNTIME:
				setBeRuntime((BeRuntime)null);
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
			case TopologyPackage.RUN_VERSION__BE_RUNTIME:
				return beRuntime != null;
		}
		return super.eIsSet(featureID);
	}

} //RunVersionImpl
