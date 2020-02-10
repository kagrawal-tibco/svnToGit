/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.rms.artifacts.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.studio.rms.artifacts.ArtifactSummaryEntry;
import com.tibco.cep.studio.rms.artifacts.ArtifactsPackage;
import com.tibco.cep.studio.rms.artifacts.ArtifactsSummary;
import com.tibco.cep.studio.rms.artifacts.RMSRepo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Summary</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactsSummaryImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactsSummaryImpl#getAddedArtifacts <em>Added Artifacts</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactsSummaryImpl#getModifiedArtifacts <em>Modified Artifacts</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactsSummaryImpl#getDeletedArtifacts <em>Deleted Artifacts</em>}</li>
 *   <li>{@link com.tibco.cep.studio.rms.artifacts.impl.ArtifactsSummaryImpl#getRmsRepo <em>Rms Repo</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ArtifactsSummaryImpl extends EObjectImpl implements ArtifactsSummary {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAddedArtifacts() <em>Added Artifacts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddedArtifacts()
	 * @generated
	 * @ordered
	 */
	protected EList<ArtifactSummaryEntry> addedArtifacts;

	/**
	 * The cached value of the '{@link #getModifiedArtifacts() <em>Modified Artifacts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModifiedArtifacts()
	 * @generated
	 * @ordered
	 */
	protected EList<ArtifactSummaryEntry> modifiedArtifacts;

	/**
	 * The cached value of the '{@link #getDeletedArtifacts() <em>Deleted Artifacts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeletedArtifacts()
	 * @generated
	 * @ordered
	 */
	protected EList<ArtifactSummaryEntry> deletedArtifacts;

	/**
	 * The cached value of the '{@link #getRmsRepo() <em>Rms Repo</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRmsRepo()
	 * @generated
	 * @ordered
	 */
	protected RMSRepo rmsRepo;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArtifactsSummaryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArtifactsPackage.Literals.ARTIFACTS_SUMMARY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArtifactsPackage.ARTIFACTS_SUMMARY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ArtifactSummaryEntry> getAddedArtifacts() {
		if (addedArtifacts == null) {
			addedArtifacts = new EObjectContainmentEList<ArtifactSummaryEntry>(ArtifactSummaryEntry.class, this, ArtifactsPackage.ARTIFACTS_SUMMARY__ADDED_ARTIFACTS);
		}
		return addedArtifacts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ArtifactSummaryEntry> getModifiedArtifacts() {
		if (modifiedArtifacts == null) {
			modifiedArtifacts = new EObjectContainmentEList<ArtifactSummaryEntry>(ArtifactSummaryEntry.class, this, ArtifactsPackage.ARTIFACTS_SUMMARY__MODIFIED_ARTIFACTS);
		}
		return modifiedArtifacts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ArtifactSummaryEntry> getDeletedArtifacts() {
		if (deletedArtifacts == null) {
			deletedArtifacts = new EObjectContainmentEList<ArtifactSummaryEntry>(ArtifactSummaryEntry.class, this, ArtifactsPackage.ARTIFACTS_SUMMARY__DELETED_ARTIFACTS);
		}
		return deletedArtifacts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RMSRepo getRmsRepo() {
		return rmsRepo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRmsRepo(RMSRepo newRmsRepo, NotificationChain msgs) {
		RMSRepo oldRmsRepo = rmsRepo;
		rmsRepo = newRmsRepo;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ArtifactsPackage.ARTIFACTS_SUMMARY__RMS_REPO, oldRmsRepo, newRmsRepo);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRmsRepo(RMSRepo newRmsRepo) {
		if (newRmsRepo != rmsRepo) {
			NotificationChain msgs = null;
			if (rmsRepo != null)
				msgs = ((InternalEObject)rmsRepo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ArtifactsPackage.ARTIFACTS_SUMMARY__RMS_REPO, null, msgs);
			if (newRmsRepo != null)
				msgs = ((InternalEObject)newRmsRepo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ArtifactsPackage.ARTIFACTS_SUMMARY__RMS_REPO, null, msgs);
			msgs = basicSetRmsRepo(newRmsRepo, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArtifactsPackage.ARTIFACTS_SUMMARY__RMS_REPO, newRmsRepo, newRmsRepo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ArtifactsPackage.ARTIFACTS_SUMMARY__ADDED_ARTIFACTS:
				return ((InternalEList<?>)getAddedArtifacts()).basicRemove(otherEnd, msgs);
			case ArtifactsPackage.ARTIFACTS_SUMMARY__MODIFIED_ARTIFACTS:
				return ((InternalEList<?>)getModifiedArtifacts()).basicRemove(otherEnd, msgs);
			case ArtifactsPackage.ARTIFACTS_SUMMARY__DELETED_ARTIFACTS:
				return ((InternalEList<?>)getDeletedArtifacts()).basicRemove(otherEnd, msgs);
			case ArtifactsPackage.ARTIFACTS_SUMMARY__RMS_REPO:
				return basicSetRmsRepo(null, msgs);
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
			case ArtifactsPackage.ARTIFACTS_SUMMARY__NAME:
				return getName();
			case ArtifactsPackage.ARTIFACTS_SUMMARY__ADDED_ARTIFACTS:
				return getAddedArtifacts();
			case ArtifactsPackage.ARTIFACTS_SUMMARY__MODIFIED_ARTIFACTS:
				return getModifiedArtifacts();
			case ArtifactsPackage.ARTIFACTS_SUMMARY__DELETED_ARTIFACTS:
				return getDeletedArtifacts();
			case ArtifactsPackage.ARTIFACTS_SUMMARY__RMS_REPO:
				return getRmsRepo();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ArtifactsPackage.ARTIFACTS_SUMMARY__NAME:
				setName((String)newValue);
				return;
			case ArtifactsPackage.ARTIFACTS_SUMMARY__ADDED_ARTIFACTS:
				getAddedArtifacts().clear();
				getAddedArtifacts().addAll((Collection<? extends ArtifactSummaryEntry>)newValue);
				return;
			case ArtifactsPackage.ARTIFACTS_SUMMARY__MODIFIED_ARTIFACTS:
				getModifiedArtifacts().clear();
				getModifiedArtifacts().addAll((Collection<? extends ArtifactSummaryEntry>)newValue);
				return;
			case ArtifactsPackage.ARTIFACTS_SUMMARY__DELETED_ARTIFACTS:
				getDeletedArtifacts().clear();
				getDeletedArtifacts().addAll((Collection<? extends ArtifactSummaryEntry>)newValue);
				return;
			case ArtifactsPackage.ARTIFACTS_SUMMARY__RMS_REPO:
				setRmsRepo((RMSRepo)newValue);
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
			case ArtifactsPackage.ARTIFACTS_SUMMARY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ArtifactsPackage.ARTIFACTS_SUMMARY__ADDED_ARTIFACTS:
				getAddedArtifacts().clear();
				return;
			case ArtifactsPackage.ARTIFACTS_SUMMARY__MODIFIED_ARTIFACTS:
				getModifiedArtifacts().clear();
				return;
			case ArtifactsPackage.ARTIFACTS_SUMMARY__DELETED_ARTIFACTS:
				getDeletedArtifacts().clear();
				return;
			case ArtifactsPackage.ARTIFACTS_SUMMARY__RMS_REPO:
				setRmsRepo((RMSRepo)null);
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
			case ArtifactsPackage.ARTIFACTS_SUMMARY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ArtifactsPackage.ARTIFACTS_SUMMARY__ADDED_ARTIFACTS:
				return addedArtifacts != null && !addedArtifacts.isEmpty();
			case ArtifactsPackage.ARTIFACTS_SUMMARY__MODIFIED_ARTIFACTS:
				return modifiedArtifacts != null && !modifiedArtifacts.isEmpty();
			case ArtifactsPackage.ARTIFACTS_SUMMARY__DELETED_ARTIFACTS:
				return deletedArtifacts != null && !deletedArtifacts.isEmpty();
			case ArtifactsPackage.ARTIFACTS_SUMMARY__RMS_REPO:
				return rmsRepo != null;
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //ArtifactsSummaryImpl
