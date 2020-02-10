/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive.config.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.config.Archives;
import com.tibco.cep.designtime.core.model.archive.config.ConfigPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Archives</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.archive.config.impl.ArchivesImpl#getBeArchive <em>Be Archive</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ArchivesImpl extends EObjectImpl implements Archives {
	/**
	 * The cached value of the '{@link #getBeArchive() <em>Be Archive</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBeArchive()
	 * @generated
	 * @ordered
	 */
	protected EList<BusinessEventsArchiveResource> beArchive;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArchivesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigPackage.Literals.ARCHIVES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BusinessEventsArchiveResource> getBeArchive() {
		if (beArchive == null) {
			beArchive = new EObjectContainmentEList<BusinessEventsArchiveResource>(BusinessEventsArchiveResource.class, this, ConfigPackage.ARCHIVES__BE_ARCHIVE);
		}
		return beArchive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ConfigPackage.ARCHIVES__BE_ARCHIVE:
				return ((InternalEList<?>)getBeArchive()).basicRemove(otherEnd, msgs);
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
			case ConfigPackage.ARCHIVES__BE_ARCHIVE:
				return getBeArchive();
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
			case ConfigPackage.ARCHIVES__BE_ARCHIVE:
				getBeArchive().clear();
				getBeArchive().addAll((Collection<? extends BusinessEventsArchiveResource>)newValue);
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
			case ConfigPackage.ARCHIVES__BE_ARCHIVE:
				getBeArchive().clear();
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
			case ConfigPackage.ARCHIVES__BE_ARCHIVE:
				return beArchive != null && !beArchive.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ArchivesImpl
