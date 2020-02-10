/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionprojectmodel.impl;

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

import com.tibco.cep.decision.table.model.domainmodel.Domain;
import com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage;
import com.tibco.cep.decisionprojectmodel.DomainModel;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Domain Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.impl.DomainModelImpl#isModified <em>Modified</em>}</li>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.impl.DomainModelImpl#getDomain <em>Domain</em>}</li>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.impl.DomainModelImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.impl.DomainModelImpl#getLastModifiedBy <em>Last Modified By</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DomainModelImpl extends EObjectImpl implements DomainModel {
	/**
	 * The default value of the '{@link #isModified() <em>Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isModified()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MODIFIED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isModified() <em>Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isModified()
	 * @generated
	 * @ordered
	 */
	protected boolean modified = MODIFIED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDomain() <em>Domain</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomain()
	 * @generated
	 * @ordered
	 */
	protected EList<Domain> domain;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final double VERSION_EDEFAULT = 0.0;
	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected double version = VERSION_EDEFAULT;
	/**
	 * The default value of the '{@link #getLastModifiedBy() <em>Last Modified By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastModifiedBy()
	 * @generated
	 * @ordered
	 */
	protected static final String LAST_MODIFIED_BY_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getLastModifiedBy() <em>Last Modified By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastModifiedBy()
	 * @generated
	 * @ordered
	 */
	protected String lastModifiedBy = LAST_MODIFIED_BY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DomainModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DecisionProjectModelPackage.Literals.DOMAIN_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isModified() {
		return modified;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModified(boolean newModified) {
		boolean oldModified = modified;
		modified = newModified;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DecisionProjectModelPackage.DOMAIN_MODEL__MODIFIED, oldModified, modified));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Domain> getDomain() {
		if (domain == null) {
			domain = new EObjectContainmentEList<Domain>(Domain.class, this, DecisionProjectModelPackage.DOMAIN_MODEL__DOMAIN);
		}
		return domain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(double newVersion) {
		double oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DecisionProjectModelPackage.DOMAIN_MODEL__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastModifiedBy(String newLastModifiedBy) {
		String oldLastModifiedBy = lastModifiedBy;
		lastModifiedBy = newLastModifiedBy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DecisionProjectModelPackage.DOMAIN_MODEL__LAST_MODIFIED_BY, oldLastModifiedBy, lastModifiedBy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DecisionProjectModelPackage.DOMAIN_MODEL__DOMAIN:
				return ((InternalEList<?>)getDomain()).basicRemove(otherEnd, msgs);
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
			case DecisionProjectModelPackage.DOMAIN_MODEL__MODIFIED:
				return isModified() ? Boolean.TRUE : Boolean.FALSE;
			case DecisionProjectModelPackage.DOMAIN_MODEL__DOMAIN:
				return getDomain();
			case DecisionProjectModelPackage.DOMAIN_MODEL__VERSION:
				return new Double(getVersion());
			case DecisionProjectModelPackage.DOMAIN_MODEL__LAST_MODIFIED_BY:
				return getLastModifiedBy();
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
			case DecisionProjectModelPackage.DOMAIN_MODEL__MODIFIED:
				setModified(((Boolean)newValue).booleanValue());
				return;
			case DecisionProjectModelPackage.DOMAIN_MODEL__DOMAIN:
				getDomain().clear();
				getDomain().addAll((Collection<? extends Domain>)newValue);
				return;
			case DecisionProjectModelPackage.DOMAIN_MODEL__VERSION:
				setVersion(((Double)newValue).doubleValue());
				return;
			case DecisionProjectModelPackage.DOMAIN_MODEL__LAST_MODIFIED_BY:
				setLastModifiedBy((String)newValue);
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
			case DecisionProjectModelPackage.DOMAIN_MODEL__MODIFIED:
				setModified(MODIFIED_EDEFAULT);
				return;
			case DecisionProjectModelPackage.DOMAIN_MODEL__DOMAIN:
				getDomain().clear();
				return;
			case DecisionProjectModelPackage.DOMAIN_MODEL__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case DecisionProjectModelPackage.DOMAIN_MODEL__LAST_MODIFIED_BY:
				setLastModifiedBy(LAST_MODIFIED_BY_EDEFAULT);
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
			case DecisionProjectModelPackage.DOMAIN_MODEL__MODIFIED:
				return modified != MODIFIED_EDEFAULT;
			case DecisionProjectModelPackage.DOMAIN_MODEL__DOMAIN:
				return domain != null && !domain.isEmpty();
			case DecisionProjectModelPackage.DOMAIN_MODEL__VERSION:
				return version != VERSION_EDEFAULT;
			case DecisionProjectModelPackage.DOMAIN_MODEL__LAST_MODIFIED_BY:
				return LAST_MODIFIED_BY_EDEFAULT == null ? lastModifiedBy != null : !LAST_MODIFIED_BY_EDEFAULT.equals(lastModifiedBy);
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
		result.append(" (modified: ");
		result.append(modified);
		result.append(", version: ");
		result.append(version);
		result.append(", lastModifiedBy: ");
		result.append(lastModifiedBy);
		result.append(')');
		return result.toString();
	}

} //DomainModelImpl
