/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Metadatable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metadatable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.MetadatableImpl#getMd <em>Md</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MetadatableImpl extends EObjectImpl implements Metadatable {
	/**
	 * The cached value of the '{@link #getMd() <em>Md</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMd()
	 * @generated
	 * @ordered
	 */
	protected MetaData md;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetadatableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DtmodelPackage.Literals.METADATABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetaData getMd() {
		return md;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMd(MetaData newMd, NotificationChain msgs) {
		MetaData oldMd = md;
		md = newMd;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DtmodelPackage.METADATABLE__MD, oldMd, newMd);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMd(MetaData newMd) {
		if (newMd != md) {
			NotificationChain msgs = null;
			if (md != null)
				msgs = ((InternalEObject)md).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DtmodelPackage.METADATABLE__MD, null, msgs);
			if (newMd != null)
				msgs = ((InternalEObject)newMd).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DtmodelPackage.METADATABLE__MD, null, msgs);
			msgs = basicSetMd(newMd, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.METADATABLE__MD, newMd, newMd));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DtmodelPackage.METADATABLE__MD:
				return basicSetMd(null, msgs);
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
			case DtmodelPackage.METADATABLE__MD:
				return getMd();
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
			case DtmodelPackage.METADATABLE__MD:
				setMd((MetaData)newValue);
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
			case DtmodelPackage.METADATABLE__MD:
				setMd((MetaData)null);
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
			case DtmodelPackage.METADATABLE__MD:
				return md != null;
		}
		return super.eIsSet(featureID);
	}

} //MetadatableImpl
