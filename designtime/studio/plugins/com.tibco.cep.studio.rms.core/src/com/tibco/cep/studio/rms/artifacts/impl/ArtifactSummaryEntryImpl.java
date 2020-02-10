/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.artifacts.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.rms.artifacts.Artifact;
import com.tibco.cep.studio.rms.artifacts.ArtifactChanger;
import com.tibco.cep.studio.rms.artifacts.ArtifactOperation;
import com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry;
import com.tibco.cep.studio.rms.artifacts.ArtifactsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Artifact Summary Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactSummaryEntryImpl#getArtifact <em>Artifact</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactSummaryEntryImpl#getOperationType <em>Operation Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactSummaryEntryImpl#getArtifactChanger <em>Artifact Changer</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactSummaryEntryImpl#isCommitStatus <em>Commit Status</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ArtifactSummaryEntryImpl extends EObjectImpl implements ArtifactSummaryEntry {
	/**
	 * The cached value of the '{@link #getArtifact() <em>Artifact</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtifact()
	 * @generated
	 * @ordered
	 */
	protected Artifact artifact;

	/**
	 * The default value of the '{@link #getOperationType() <em>Operation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationType()
	 * @generated
	 * @ordered
	 */
	protected static final ArtifactOperation OPERATION_TYPE_EDEFAULT = ArtifactOperation.ADD;

	/**
	 * The cached value of the '{@link #getOperationType() <em>Operation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationType()
	 * @generated
	 * @ordered
	 */
	protected ArtifactOperation operationType = OPERATION_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getArtifactChanger() <em>Artifact Changer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtifactChanger()
	 * @generated
	 * @ordered
	 */
	protected static final ArtifactChanger ARTIFACT_CHANGER_EDEFAULT = ArtifactChanger.LOCAL;

	/**
	 * The cached value of the '{@link #getArtifactChanger() <em>Artifact Changer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArtifactChanger()
	 * @generated
	 * @ordered
	 */
	protected ArtifactChanger artifactChanger = ARTIFACT_CHANGER_EDEFAULT;

	/**
	 * The default value of the '{@link #isCommitStatus() <em>Commit Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCommitStatus()
	 * @generated
	 * @ordered
	 */
	protected static final boolean COMMIT_STATUS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCommitStatus() <em>Commit Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCommitStatus()
	 * @generated
	 * @ordered
	 */
	protected boolean commitStatus = COMMIT_STATUS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArtifactSummaryEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArtifactsPackage.Literals.ARTIFACT_SUMMARY_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Artifact getArtifact() {
		return artifact;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArtifact(Artifact newArtifact, NotificationChain msgs) {
		Artifact oldArtifact = artifact;
		artifact = newArtifact;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__ARTIFACT, oldArtifact, newArtifact);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArtifact(Artifact newArtifact) {
		if (newArtifact != artifact) {
			NotificationChain msgs = null;
			if (artifact != null)
				msgs = ((InternalEObject)artifact).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__ARTIFACT, null, msgs);
			if (newArtifact != null)
				msgs = ((InternalEObject)newArtifact).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__ARTIFACT, null, msgs);
			msgs = basicSetArtifact(newArtifact, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__ARTIFACT, newArtifact, newArtifact));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactOperation getOperationType() {
		return operationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperationType(ArtifactOperation newOperationType) {
		ArtifactOperation oldOperationType = operationType;
		operationType = newOperationType == null ? OPERATION_TYPE_EDEFAULT : newOperationType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__OPERATION_TYPE, oldOperationType, operationType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtifactChanger getArtifactChanger() {
		return artifactChanger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArtifactChanger(ArtifactChanger newArtifactChanger) {
		ArtifactChanger oldArtifactChanger = artifactChanger;
		artifactChanger = newArtifactChanger == null ? ARTIFACT_CHANGER_EDEFAULT : newArtifactChanger;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__ARTIFACT_CHANGER, oldArtifactChanger, artifactChanger));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCommitStatus() {
		return commitStatus;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommitStatus(boolean newCommitStatus) {
		boolean oldCommitStatus = commitStatus;
		commitStatus = newCommitStatus;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__COMMIT_STATUS, oldCommitStatus, commitStatus));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__ARTIFACT:
				return basicSetArtifact(null, msgs);
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
			case ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__ARTIFACT:
				return getArtifact();
			case ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__OPERATION_TYPE:
				return getOperationType();
			case ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__ARTIFACT_CHANGER:
				return getArtifactChanger();
			case ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__COMMIT_STATUS:
				return isCommitStatus() ? Boolean.TRUE : Boolean.FALSE;
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
			case ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__ARTIFACT:
				setArtifact((Artifact)newValue);
				return;
			case ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__OPERATION_TYPE:
				setOperationType((ArtifactOperation)newValue);
				return;
			case ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__ARTIFACT_CHANGER:
				setArtifactChanger((ArtifactChanger)newValue);
				return;
			case ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__COMMIT_STATUS:
				setCommitStatus(((Boolean)newValue).booleanValue());
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
			case ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__ARTIFACT:
				setArtifact((Artifact)null);
				return;
			case ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__OPERATION_TYPE:
				setOperationType(OPERATION_TYPE_EDEFAULT);
				return;
			case ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__ARTIFACT_CHANGER:
				setArtifactChanger(ARTIFACT_CHANGER_EDEFAULT);
				return;
			case ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__COMMIT_STATUS:
				setCommitStatus(COMMIT_STATUS_EDEFAULT);
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
			case ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__ARTIFACT:
				return artifact != null;
			case ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__OPERATION_TYPE:
				return operationType != OPERATION_TYPE_EDEFAULT;
			case ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__ARTIFACT_CHANGER:
				return artifactChanger != ARTIFACT_CHANGER_EDEFAULT;
			case ArtifactsPackage.ARTIFACT_SUMMARY_ENTRY__COMMIT_STATUS:
				return commitStatus != COMMIT_STATUS_EDEFAULT;
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
		result.append(" (operationType: ");
		result.append(operationType);
		result.append(", artifactChanger: ");
		result.append(artifactChanger);
		result.append(", commitStatus: ");
		result.append(commitStatus);
		result.append(')');
		return result.toString();
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return operationType.hashCode() * this.artifact.getArtifactPath().hashCode() * this.artifact.getContainerPath().hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object otherObject) {
		if (otherObject == this) {
			return true;
		}
		if (!(otherObject instanceof ArtifactSummaryEntry)) {
			return false;
		}
		ArtifactSummaryEntry otherSummaryEntry = (ArtifactSummaryEntry)otherObject;
		//Check artifact operation
		if (otherSummaryEntry.getOperationType() != this.operationType) {
			return false;
		}
		//Check artifact path
		if (!otherSummaryEntry.getArtifact().getArtifactPath().equals(this.artifact.getArtifactPath())) {
			return false;
		}
		//Check artifact container path
		if (!(otherSummaryEntry.getArtifact().getContainerPath().equals(this.artifact.getContainerPath()))) {
			return false;
		}
		return true;
	}
} //ArtifactSummaryEntryImpl
