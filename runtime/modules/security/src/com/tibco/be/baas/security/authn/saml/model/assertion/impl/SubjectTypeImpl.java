/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion.impl;

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

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage;
import com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType;
import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subject Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectTypeImpl#getBaseID <em>Base ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectTypeImpl#getNameID <em>Name ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectTypeImpl#getSubjectConfirmation <em>Subject Confirmation</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectTypeImpl#getSubjectConfirmation1 <em>Subject Confirmation1</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SubjectTypeImpl extends EObjectImpl implements SubjectType {
	/**
	 * The cached value of the '{@link #getBaseID() <em>Base ID</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseID()
	 * @generated
	 * @ordered
	 */
	protected BaseIDAbstractType baseID;

	/**
	 * The cached value of the '{@link #getNameID() <em>Name ID</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNameID()
	 * @generated
	 * @ordered
	 */
	protected NameIDType nameID;

	/**
	 * The cached value of the '{@link #getSubjectConfirmation() <em>Subject Confirmation</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubjectConfirmation()
	 * @generated
	 * @ordered
	 */
	protected EList<SubjectConfirmationType> subjectConfirmation;

	/**
	 * The cached value of the '{@link #getSubjectConfirmation1() <em>Subject Confirmation1</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubjectConfirmation1()
	 * @generated
	 * @ordered
	 */
	protected EList<SubjectConfirmationType> subjectConfirmation1;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubjectTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AssertionPackage.Literals.SUBJECT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseIDAbstractType getBaseID() {
		return baseID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBaseID(BaseIDAbstractType newBaseID, NotificationChain msgs) {
		BaseIDAbstractType oldBaseID = baseID;
		baseID = newBaseID;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AssertionPackage.SUBJECT_TYPE__BASE_ID, oldBaseID, newBaseID);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBaseID(BaseIDAbstractType newBaseID) {
		if (newBaseID != baseID) {
			NotificationChain msgs = null;
			if (baseID != null)
				msgs = ((InternalEObject)baseID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.SUBJECT_TYPE__BASE_ID, null, msgs);
			if (newBaseID != null)
				msgs = ((InternalEObject)newBaseID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.SUBJECT_TYPE__BASE_ID, null, msgs);
			msgs = basicSetBaseID(newBaseID, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.SUBJECT_TYPE__BASE_ID, newBaseID, newBaseID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameIDType getNameID() {
		return nameID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNameID(NameIDType newNameID, NotificationChain msgs) {
		NameIDType oldNameID = nameID;
		nameID = newNameID;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AssertionPackage.SUBJECT_TYPE__NAME_ID, oldNameID, newNameID);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNameID(NameIDType newNameID) {
		if (newNameID != nameID) {
			NotificationChain msgs = null;
			if (nameID != null)
				msgs = ((InternalEObject)nameID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.SUBJECT_TYPE__NAME_ID, null, msgs);
			if (newNameID != null)
				msgs = ((InternalEObject)newNameID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.SUBJECT_TYPE__NAME_ID, null, msgs);
			msgs = basicSetNameID(newNameID, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.SUBJECT_TYPE__NAME_ID, newNameID, newNameID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SubjectConfirmationType> getSubjectConfirmation() {
		if (subjectConfirmation == null) {
			subjectConfirmation = new EObjectContainmentEList<SubjectConfirmationType>(SubjectConfirmationType.class, this, AssertionPackage.SUBJECT_TYPE__SUBJECT_CONFIRMATION);
		}
		return subjectConfirmation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SubjectConfirmationType> getSubjectConfirmation1() {
		if (subjectConfirmation1 == null) {
			subjectConfirmation1 = new EObjectContainmentEList<SubjectConfirmationType>(SubjectConfirmationType.class, this, AssertionPackage.SUBJECT_TYPE__SUBJECT_CONFIRMATION1);
		}
		return subjectConfirmation1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AssertionPackage.SUBJECT_TYPE__BASE_ID:
				return basicSetBaseID(null, msgs);
			case AssertionPackage.SUBJECT_TYPE__NAME_ID:
				return basicSetNameID(null, msgs);
			case AssertionPackage.SUBJECT_TYPE__SUBJECT_CONFIRMATION:
				return ((InternalEList<?>)getSubjectConfirmation()).basicRemove(otherEnd, msgs);
			case AssertionPackage.SUBJECT_TYPE__SUBJECT_CONFIRMATION1:
				return ((InternalEList<?>)getSubjectConfirmation1()).basicRemove(otherEnd, msgs);
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
			case AssertionPackage.SUBJECT_TYPE__BASE_ID:
				return getBaseID();
			case AssertionPackage.SUBJECT_TYPE__NAME_ID:
				return getNameID();
			case AssertionPackage.SUBJECT_TYPE__SUBJECT_CONFIRMATION:
				return getSubjectConfirmation();
			case AssertionPackage.SUBJECT_TYPE__SUBJECT_CONFIRMATION1:
				return getSubjectConfirmation1();
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
			case AssertionPackage.SUBJECT_TYPE__BASE_ID:
				setBaseID((BaseIDAbstractType)newValue);
				return;
			case AssertionPackage.SUBJECT_TYPE__NAME_ID:
				setNameID((NameIDType)newValue);
				return;
			case AssertionPackage.SUBJECT_TYPE__SUBJECT_CONFIRMATION:
				getSubjectConfirmation().clear();
				getSubjectConfirmation().addAll((Collection<? extends SubjectConfirmationType>)newValue);
				return;
			case AssertionPackage.SUBJECT_TYPE__SUBJECT_CONFIRMATION1:
				getSubjectConfirmation1().clear();
				getSubjectConfirmation1().addAll((Collection<? extends SubjectConfirmationType>)newValue);
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
			case AssertionPackage.SUBJECT_TYPE__BASE_ID:
				setBaseID((BaseIDAbstractType)null);
				return;
			case AssertionPackage.SUBJECT_TYPE__NAME_ID:
				setNameID((NameIDType)null);
				return;
			case AssertionPackage.SUBJECT_TYPE__SUBJECT_CONFIRMATION:
				getSubjectConfirmation().clear();
				return;
			case AssertionPackage.SUBJECT_TYPE__SUBJECT_CONFIRMATION1:
				getSubjectConfirmation1().clear();
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
			case AssertionPackage.SUBJECT_TYPE__BASE_ID:
				return baseID != null;
			case AssertionPackage.SUBJECT_TYPE__NAME_ID:
				return nameID != null;
			case AssertionPackage.SUBJECT_TYPE__SUBJECT_CONFIRMATION:
				return subjectConfirmation != null && !subjectConfirmation.isEmpty();
			case AssertionPackage.SUBJECT_TYPE__SUBJECT_CONFIRMATION1:
				return subjectConfirmation1 != null && !subjectConfirmation1.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SubjectTypeImpl
