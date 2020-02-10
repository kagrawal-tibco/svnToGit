/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.core.model.topology.MasterFiles;
import com.tibco.cep.studio.core.model.topology.TopologyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Master Files</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.MasterFilesImpl#getCddMaster <em>Cdd Master</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.MasterFilesImpl#getEarMaster <em>Ear Master</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MasterFilesImpl extends EObjectImpl implements MasterFiles {
	/**
	 * The default value of the '{@link #getCddMaster() <em>Cdd Master</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCddMaster()
	 * @generated
	 * @ordered
	 */
	protected static final String CDD_MASTER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCddMaster() <em>Cdd Master</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCddMaster()
	 * @generated
	 * @ordered
	 */
	protected String cddMaster = CDD_MASTER_EDEFAULT;

	/**
	 * The default value of the '{@link #getEarMaster() <em>Ear Master</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEarMaster()
	 * @generated
	 * @ordered
	 */
	protected static final String EAR_MASTER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEarMaster() <em>Ear Master</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEarMaster()
	 * @generated
	 * @ordered
	 */
	protected String earMaster = EAR_MASTER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MasterFilesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TopologyPackage.Literals.MASTER_FILES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCddMaster() {
		return cddMaster;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCddMaster(String newCddMaster) {
		String oldCddMaster = cddMaster;
		cddMaster = newCddMaster;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.MASTER_FILES__CDD_MASTER, oldCddMaster, cddMaster));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEarMaster() {
		return earMaster;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEarMaster(String newEarMaster) {
		String oldEarMaster = earMaster;
		earMaster = newEarMaster;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.MASTER_FILES__EAR_MASTER, oldEarMaster, earMaster));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TopologyPackage.MASTER_FILES__CDD_MASTER:
				return getCddMaster();
			case TopologyPackage.MASTER_FILES__EAR_MASTER:
				return getEarMaster();
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
			case TopologyPackage.MASTER_FILES__CDD_MASTER:
				setCddMaster((String)newValue);
				return;
			case TopologyPackage.MASTER_FILES__EAR_MASTER:
				setEarMaster((String)newValue);
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
			case TopologyPackage.MASTER_FILES__CDD_MASTER:
				setCddMaster(CDD_MASTER_EDEFAULT);
				return;
			case TopologyPackage.MASTER_FILES__EAR_MASTER:
				setEarMaster(EAR_MASTER_EDEFAULT);
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
			case TopologyPackage.MASTER_FILES__CDD_MASTER:
				return CDD_MASTER_EDEFAULT == null ? cddMaster != null : !CDD_MASTER_EDEFAULT.equals(cddMaster);
			case TopologyPackage.MASTER_FILES__EAR_MASTER:
				return EAR_MASTER_EDEFAULT == null ? earMaster != null : !EAR_MASTER_EDEFAULT.equals(earMaster);
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
		result.append(" (cddMaster: ");
		result.append(cddMaster);
		result.append(", earMaster: ");
		result.append(earMaster);
		result.append(')');
		return result.toString();
	}

} //MasterFilesImpl
