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

import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactsPackage;
import com.tibco.cep.studio.rms.artifacts.ArtifactsType;
import java.util.Date;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Artifact</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactImpl#getArtifactPath <em>Artifact Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactImpl#getArtifactType <em>Artifact Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactImpl#getArtifactContent <em>Artifact Content</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactImpl#getArtifactExtension <em>Artifact Extension</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactImpl#getContainerPath <em>Container Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactImpl#getUpdateTime <em>Update Time</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactImpl#getCommittedVersion <em>Committed Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ArtifactImpl extends EObjectImpl implements Artifact {
	/**
	 * The default value of the '{@link #getArtifactPath() <em>Artifact Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtifactPath()
	 * @generated
	 * @ordered
	 */
	protected static final String ARTIFACT_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getArtifactPath() <em>Artifact Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtifactPath()
	 * @generated
	 * @ordered
	 */
	protected String artifactPath = ARTIFACT_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getArtifactType() <em>Artifact Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtifactType()
	 * @generated
	 * @ordered
	 */
	protected static final ArtifactsType ARTIFACT_TYPE_EDEFAULT = ArtifactsType.DECISIONTABLE;

	/**
	 * The cached value of the '{@link #getArtifactType() <em>Artifact Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtifactType()
	 * @generated
	 * @ordered
	 */
	protected ArtifactsType artifactType = ARTIFACT_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getArtifactContent() <em>Artifact Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtifactContent()
	 * @generated
	 * @ordered
	 */
	protected static final String ARTIFACT_CONTENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getArtifactContent() <em>Artifact Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtifactContent()
	 * @generated
	 * @ordered
	 */
	protected String artifactContent = ARTIFACT_CONTENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getArtifactExtension() <em>Artifact Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtifactExtension()
	 * @generated
	 * @ordered
	 */
	protected static final String ARTIFACT_EXTENSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getArtifactExtension() <em>Artifact Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtifactExtension()
	 * @generated
	 * @ordered
	 */
	protected String artifactExtension = ARTIFACT_EXTENSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getContainerPath() <em>Container Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainerPath()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTAINER_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContainerPath() <em>Container Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainerPath()
	 * @generated
	 * @ordered
	 */
	protected String containerPath = CONTAINER_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getUpdateTime() <em>Update Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdateTime()
	 * @generated
	 * @ordered
	 */
	protected static final Date UPDATE_TIME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUpdateTime() <em>Update Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpdateTime()
	 * @generated
	 * @ordered
	 */
	protected Date updateTime = UPDATE_TIME_EDEFAULT;

	/**
	 * The default value of the '{@link #getCommittedVersion() <em>Committed Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommittedVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMITTED_VERSION_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getCommittedVersion() <em>Committed Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommittedVersion()
	 * @generated
	 * @ordered
	 */
	protected String committedVersion = COMMITTED_VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArtifactImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArtifactsPackage.Literals.ARTIFACT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getArtifactPath() {
		return artifactPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArtifactPath(String newArtifactPath) {
		String oldArtifactPath = artifactPath;
		artifactPath = newArtifactPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArtifactsPackage.ARTIFACT__ARTIFACT_PATH, oldArtifactPath, artifactPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactsType getArtifactType() {
		return artifactType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArtifactType(ArtifactsType newArtifactType) {
		ArtifactsType oldArtifactType = artifactType;
		artifactType = newArtifactType == null ? ARTIFACT_TYPE_EDEFAULT : newArtifactType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArtifactsPackage.ARTIFACT__ARTIFACT_TYPE, oldArtifactType, artifactType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getArtifactContent() {
		return artifactContent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArtifactContent(String newArtifactContent) {
		String oldArtifactContent = artifactContent;
		artifactContent = newArtifactContent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArtifactsPackage.ARTIFACT__ARTIFACT_CONTENT, oldArtifactContent, artifactContent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getArtifactExtension() {
		return artifactExtension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArtifactExtension(String newArtifactExtension) {
		String oldArtifactExtension = artifactExtension;
		artifactExtension = newArtifactExtension;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArtifactsPackage.ARTIFACT__ARTIFACT_EXTENSION, oldArtifactExtension, artifactExtension));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getContainerPath() {
		return containerPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContainerPath(String newContainerPath) {
		String oldContainerPath = containerPath;
		containerPath = newContainerPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArtifactsPackage.ARTIFACT__CONTAINER_PATH, oldContainerPath, containerPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUpdateTime(Date newUpdateTime) {
		Date oldUpdateTime = updateTime;
		updateTime = newUpdateTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArtifactsPackage.ARTIFACT__UPDATE_TIME, oldUpdateTime, updateTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCommittedVersion() {
		return committedVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommittedVersion(String newCommittedVersion) {
		String oldCommittedVersion = committedVersion;
		committedVersion = newCommittedVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArtifactsPackage.ARTIFACT__COMMITTED_VERSION, oldCommittedVersion, committedVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ArtifactsPackage.ARTIFACT__ARTIFACT_PATH:
				return getArtifactPath();
			case ArtifactsPackage.ARTIFACT__ARTIFACT_TYPE:
				return getArtifactType();
			case ArtifactsPackage.ARTIFACT__ARTIFACT_CONTENT:
				return getArtifactContent();
			case ArtifactsPackage.ARTIFACT__ARTIFACT_EXTENSION:
				return getArtifactExtension();
			case ArtifactsPackage.ARTIFACT__CONTAINER_PATH:
				return getContainerPath();
			case ArtifactsPackage.ARTIFACT__UPDATE_TIME:
				return getUpdateTime();
			case ArtifactsPackage.ARTIFACT__COMMITTED_VERSION:
				return getCommittedVersion();
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
			case ArtifactsPackage.ARTIFACT__ARTIFACT_PATH:
				setArtifactPath((String)newValue);
				return;
			case ArtifactsPackage.ARTIFACT__ARTIFACT_TYPE:
				setArtifactType((ArtifactsType)newValue);
				return;
			case ArtifactsPackage.ARTIFACT__ARTIFACT_CONTENT:
				setArtifactContent((String)newValue);
				return;
			case ArtifactsPackage.ARTIFACT__ARTIFACT_EXTENSION:
				setArtifactExtension((String)newValue);
				return;
			case ArtifactsPackage.ARTIFACT__CONTAINER_PATH:
				setContainerPath((String)newValue);
				return;
			case ArtifactsPackage.ARTIFACT__UPDATE_TIME:
				setUpdateTime((Date)newValue);
				return;
			case ArtifactsPackage.ARTIFACT__COMMITTED_VERSION:
				setCommittedVersion((String)newValue);
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
			case ArtifactsPackage.ARTIFACT__ARTIFACT_PATH:
				setArtifactPath(ARTIFACT_PATH_EDEFAULT);
				return;
			case ArtifactsPackage.ARTIFACT__ARTIFACT_TYPE:
				setArtifactType(ARTIFACT_TYPE_EDEFAULT);
				return;
			case ArtifactsPackage.ARTIFACT__ARTIFACT_CONTENT:
				setArtifactContent(ARTIFACT_CONTENT_EDEFAULT);
				return;
			case ArtifactsPackage.ARTIFACT__ARTIFACT_EXTENSION:
				setArtifactExtension(ARTIFACT_EXTENSION_EDEFAULT);
				return;
			case ArtifactsPackage.ARTIFACT__CONTAINER_PATH:
				setContainerPath(CONTAINER_PATH_EDEFAULT);
				return;
			case ArtifactsPackage.ARTIFACT__UPDATE_TIME:
				setUpdateTime(UPDATE_TIME_EDEFAULT);
				return;
			case ArtifactsPackage.ARTIFACT__COMMITTED_VERSION:
				setCommittedVersion(COMMITTED_VERSION_EDEFAULT);
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
			case ArtifactsPackage.ARTIFACT__ARTIFACT_PATH:
				return ARTIFACT_PATH_EDEFAULT == null ? artifactPath != null : !ARTIFACT_PATH_EDEFAULT.equals(artifactPath);
			case ArtifactsPackage.ARTIFACT__ARTIFACT_TYPE:
				return artifactType != ARTIFACT_TYPE_EDEFAULT;
			case ArtifactsPackage.ARTIFACT__ARTIFACT_CONTENT:
				return ARTIFACT_CONTENT_EDEFAULT == null ? artifactContent != null : !ARTIFACT_CONTENT_EDEFAULT.equals(artifactContent);
			case ArtifactsPackage.ARTIFACT__ARTIFACT_EXTENSION:
				return ARTIFACT_EXTENSION_EDEFAULT == null ? artifactExtension != null : !ARTIFACT_EXTENSION_EDEFAULT.equals(artifactExtension);
			case ArtifactsPackage.ARTIFACT__CONTAINER_PATH:
				return CONTAINER_PATH_EDEFAULT == null ? containerPath != null : !CONTAINER_PATH_EDEFAULT.equals(containerPath);
			case ArtifactsPackage.ARTIFACT__UPDATE_TIME:
				return UPDATE_TIME_EDEFAULT == null ? updateTime != null : !UPDATE_TIME_EDEFAULT.equals(updateTime);
			case ArtifactsPackage.ARTIFACT__COMMITTED_VERSION:
				return COMMITTED_VERSION_EDEFAULT == null ? committedVersion != null : !COMMITTED_VERSION_EDEFAULT.equals(committedVersion);
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
		result.append(" (artifactPath: ");
		result.append(artifactPath);
		result.append(", artifactType: ");
		result.append(artifactType);
		result.append(", artifactContent: ");
		result.append(artifactContent);
		result.append(", artifactExtension: ");
		result.append(artifactExtension);
		result.append(", containerPath: ");
		result.append(containerPath);
		result.append(", updateTime: ");
		result.append(updateTime);
		result.append(", committedVersion: ");
		result.append(committedVersion);
		result.append(')');
		return result.toString();
	}

} //ArtifactImpl
