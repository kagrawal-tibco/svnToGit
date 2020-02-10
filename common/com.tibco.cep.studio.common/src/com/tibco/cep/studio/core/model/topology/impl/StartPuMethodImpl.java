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

import com.tibco.cep.studio.core.model.topology.Hawk;
import com.tibco.cep.studio.core.model.topology.Pstools;
import com.tibco.cep.studio.core.model.topology.Ssh;
import com.tibco.cep.studio.core.model.topology.StartPuMethod;
import com.tibco.cep.studio.core.model.topology.TopologyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Start Pu Method</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.StartPuMethodImpl#getHawk <em>Hawk</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.StartPuMethodImpl#getSsh <em>Ssh</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.StartPuMethodImpl#getPstools <em>Pstools</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StartPuMethodImpl extends EObjectImpl implements StartPuMethod {
	/**
	 * The cached value of the '{@link #getHawk() <em>Hawk</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHawk()
	 * @generated
	 * @ordered
	 */
	protected Hawk hawk;

	/**
	 * The cached value of the '{@link #getSsh() <em>Ssh</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSsh()
	 * @generated
	 * @ordered
	 */
	protected Ssh ssh;

	/**
	 * The cached value of the '{@link #getPstools() <em>Pstools</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPstools()
	 * @generated
	 * @ordered
	 */
	protected Pstools pstools;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StartPuMethodImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TopologyPackage.Literals.START_PU_METHOD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Hawk getHawk() {
		return hawk;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHawk(Hawk newHawk, NotificationChain msgs) {
		Hawk oldHawk = hawk;
		hawk = newHawk;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TopologyPackage.START_PU_METHOD__HAWK, oldHawk, newHawk);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHawk(Hawk newHawk) {
		if (newHawk != hawk) {
			NotificationChain msgs = null;
			if (hawk != null)
				msgs = ((InternalEObject)hawk).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.START_PU_METHOD__HAWK, null, msgs);
			if (newHawk != null)
				msgs = ((InternalEObject)newHawk).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.START_PU_METHOD__HAWK, null, msgs);
			msgs = basicSetHawk(newHawk, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.START_PU_METHOD__HAWK, newHawk, newHawk));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Ssh getSsh() {
		return ssh;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSsh(Ssh newSsh, NotificationChain msgs) {
		Ssh oldSsh = ssh;
		ssh = newSsh;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TopologyPackage.START_PU_METHOD__SSH, oldSsh, newSsh);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSsh(Ssh newSsh) {
		if (newSsh != ssh) {
			NotificationChain msgs = null;
			if (ssh != null)
				msgs = ((InternalEObject)ssh).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.START_PU_METHOD__SSH, null, msgs);
			if (newSsh != null)
				msgs = ((InternalEObject)newSsh).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.START_PU_METHOD__SSH, null, msgs);
			msgs = basicSetSsh(newSsh, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.START_PU_METHOD__SSH, newSsh, newSsh));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pstools getPstools() {
		return pstools;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPstools(Pstools newPstools, NotificationChain msgs) {
		Pstools oldPstools = pstools;
		pstools = newPstools;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TopologyPackage.START_PU_METHOD__PSTOOLS, oldPstools, newPstools);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPstools(Pstools newPstools) {
		if (newPstools != pstools) {
			NotificationChain msgs = null;
			if (pstools != null)
				msgs = ((InternalEObject)pstools).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.START_PU_METHOD__PSTOOLS, null, msgs);
			if (newPstools != null)
				msgs = ((InternalEObject)newPstools).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.START_PU_METHOD__PSTOOLS, null, msgs);
			msgs = basicSetPstools(newPstools, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.START_PU_METHOD__PSTOOLS, newPstools, newPstools));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TopologyPackage.START_PU_METHOD__HAWK:
				return basicSetHawk(null, msgs);
			case TopologyPackage.START_PU_METHOD__SSH:
				return basicSetSsh(null, msgs);
			case TopologyPackage.START_PU_METHOD__PSTOOLS:
				return basicSetPstools(null, msgs);
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
			case TopologyPackage.START_PU_METHOD__HAWK:
				return getHawk();
			case TopologyPackage.START_PU_METHOD__SSH:
				return getSsh();
			case TopologyPackage.START_PU_METHOD__PSTOOLS:
				return getPstools();
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
			case TopologyPackage.START_PU_METHOD__HAWK:
				setHawk((Hawk)newValue);
				return;
			case TopologyPackage.START_PU_METHOD__SSH:
				setSsh((Ssh)newValue);
				return;
			case TopologyPackage.START_PU_METHOD__PSTOOLS:
				setPstools((Pstools)newValue);
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
			case TopologyPackage.START_PU_METHOD__HAWK:
				setHawk((Hawk)null);
				return;
			case TopologyPackage.START_PU_METHOD__SSH:
				setSsh((Ssh)null);
				return;
			case TopologyPackage.START_PU_METHOD__PSTOOLS:
				setPstools((Pstools)null);
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
			case TopologyPackage.START_PU_METHOD__HAWK:
				return hawk != null;
			case TopologyPackage.START_PU_METHOD__SSH:
				return ssh != null;
			case TopologyPackage.START_PU_METHOD__PSTOOLS:
				return pstools != null;
		}
		return super.eIsSet(featureID);
	}

} //StartPuMethodImpl
