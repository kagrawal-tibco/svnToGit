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

import com.tibco.cep.studio.core.model.topology.DeployedFiles;
import com.tibco.cep.studio.core.model.topology.TopologyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Deployed Files</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DeployedFilesImpl#getCddDeployed <em>Cdd Deployed</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DeployedFilesImpl#getEarDeployed <em>Ear Deployed</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DeployedFilesImpl extends EObjectImpl implements DeployedFiles {
	/**
	 * The default value of the '{@link #getCddDeployed() <em>Cdd Deployed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCddDeployed()
	 * @generated
	 * @ordered
	 */
	protected static final String CDD_DEPLOYED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCddDeployed() <em>Cdd Deployed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCddDeployed()
	 * @generated
	 * @ordered
	 */
	protected String cddDeployed = CDD_DEPLOYED_EDEFAULT;

	/**
	 * The default value of the '{@link #getEarDeployed() <em>Ear Deployed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEarDeployed()
	 * @generated
	 * @ordered
	 */
	protected static final String EAR_DEPLOYED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEarDeployed() <em>Ear Deployed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEarDeployed()
	 * @generated
	 * @ordered
	 */
	protected String earDeployed = EAR_DEPLOYED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeployedFilesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TopologyPackage.Literals.DEPLOYED_FILES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCddDeployed() {
		return cddDeployed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCddDeployed(String newCddDeployed) {
		String oldCddDeployed = cddDeployed;
		cddDeployed = newCddDeployed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.DEPLOYED_FILES__CDD_DEPLOYED, oldCddDeployed, cddDeployed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEarDeployed() {
		return earDeployed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEarDeployed(String newEarDeployed) {
		String oldEarDeployed = earDeployed;
		earDeployed = newEarDeployed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.DEPLOYED_FILES__EAR_DEPLOYED, oldEarDeployed, earDeployed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TopologyPackage.DEPLOYED_FILES__CDD_DEPLOYED:
				return getCddDeployed();
			case TopologyPackage.DEPLOYED_FILES__EAR_DEPLOYED:
				return getEarDeployed();
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
			case TopologyPackage.DEPLOYED_FILES__CDD_DEPLOYED:
				setCddDeployed((String)newValue);
				return;
			case TopologyPackage.DEPLOYED_FILES__EAR_DEPLOYED:
				setEarDeployed((String)newValue);
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
			case TopologyPackage.DEPLOYED_FILES__CDD_DEPLOYED:
				setCddDeployed(CDD_DEPLOYED_EDEFAULT);
				return;
			case TopologyPackage.DEPLOYED_FILES__EAR_DEPLOYED:
				setEarDeployed(EAR_DEPLOYED_EDEFAULT);
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
			case TopologyPackage.DEPLOYED_FILES__CDD_DEPLOYED:
				return CDD_DEPLOYED_EDEFAULT == null ? cddDeployed != null : !CDD_DEPLOYED_EDEFAULT.equals(cddDeployed);
			case TopologyPackage.DEPLOYED_FILES__EAR_DEPLOYED:
				return EAR_DEPLOYED_EDEFAULT == null ? earDeployed != null : !EAR_DEPLOYED_EDEFAULT.equals(earDeployed);
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
		result.append(" (cddDeployed: ");
		result.append(cddDeployed);
		result.append(", earDeployed: ");
		result.append(earDeployed);
		result.append(')');
		return result.toString();
	}

} //DeployedFilesImpl
