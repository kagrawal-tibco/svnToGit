/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.artifacts.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.rms.artifacts.ArtifactsPackage;
import com.tibco.cep.studio.rms.artifacts.RMSRepo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>RMS Repo</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.impl.RMSRepoImpl#getRepoURL <em>Repo URL</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.impl.RMSRepoImpl#getRmsProject <em>Rms Project</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RMSRepoImpl extends EObjectImpl implements RMSRepo {
	/**
	 * The default value of the '{@link #getRepoURL() <em>Repo URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepoURL()
	 * @generated
	 * @ordered
	 */
	protected static final String REPO_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRepoURL() <em>Repo URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepoURL()
	 * @generated
	 * @ordered
	 */
	protected String repoURL = REPO_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getRmsProject() <em>Rms Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRmsProject()
	 * @generated
	 * @ordered
	 */
	protected static final String RMS_PROJECT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRmsProject() <em>Rms Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRmsProject()
	 * @generated
	 * @ordered
	 */
	protected String rmsProject = RMS_PROJECT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RMSRepoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArtifactsPackage.Literals.RMS_REPO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRepoURL() {
		return repoURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRepoURL(String newRepoURL) {
		String oldRepoURL = repoURL;
		repoURL = newRepoURL;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArtifactsPackage.RMS_REPO__REPO_URL, oldRepoURL, repoURL));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRmsProject() {
		return rmsProject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRmsProject(String newRmsProject) {
		String oldRmsProject = rmsProject;
		rmsProject = newRmsProject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArtifactsPackage.RMS_REPO__RMS_PROJECT, oldRmsProject, rmsProject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ArtifactsPackage.RMS_REPO__REPO_URL:
				return getRepoURL();
			case ArtifactsPackage.RMS_REPO__RMS_PROJECT:
				return getRmsProject();
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
			case ArtifactsPackage.RMS_REPO__REPO_URL:
				setRepoURL((String)newValue);
				return;
			case ArtifactsPackage.RMS_REPO__RMS_PROJECT:
				setRmsProject((String)newValue);
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
			case ArtifactsPackage.RMS_REPO__REPO_URL:
				setRepoURL(REPO_URL_EDEFAULT);
				return;
			case ArtifactsPackage.RMS_REPO__RMS_PROJECT:
				setRmsProject(RMS_PROJECT_EDEFAULT);
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
			case ArtifactsPackage.RMS_REPO__REPO_URL:
				return REPO_URL_EDEFAULT == null ? repoURL != null : !REPO_URL_EDEFAULT.equals(repoURL);
			case ArtifactsPackage.RMS_REPO__RMS_PROJECT:
				return RMS_PROJECT_EDEFAULT == null ? rmsProject != null : !RMS_PROJECT_EDEFAULT.equals(rmsProject);
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
		result.append(" (repoURL: ");
		result.append(repoURL);
		result.append(", rmsProject: ");
		result.append(rmsProject);
		result.append(')');
		return result.toString();
	}

} //RMSRepoImpl
