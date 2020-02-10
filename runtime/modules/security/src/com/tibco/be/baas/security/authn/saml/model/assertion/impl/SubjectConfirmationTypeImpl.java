/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage;
import com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType;
import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationDataType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subject Confirmation Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationTypeImpl#getBaseID <em>Base ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationTypeImpl#getNameID <em>Name ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationTypeImpl#getSubjectConfirmationData <em>Subject Confirmation Data</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectConfirmationTypeImpl#getMethod <em>Method</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SubjectConfirmationTypeImpl extends EObjectImpl implements SubjectConfirmationType {
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
	 * The cached value of the '{@link #getSubjectConfirmationData() <em>Subject Confirmation Data</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubjectConfirmationData()
	 * @generated
	 * @ordered
	 */
	protected SubjectConfirmationDataType subjectConfirmationData;

	/**
	 * The default value of the '{@link #getMethod() <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethod()
	 * @generated
	 * @ordered
	 */
	protected static final String METHOD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMethod() <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMethod()
	 * @generated
	 * @ordered
	 */
	protected String method = METHOD_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubjectConfirmationTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AssertionPackage.Literals.SUBJECT_CONFIRMATION_TYPE;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AssertionPackage.SUBJECT_CONFIRMATION_TYPE__BASE_ID, oldBaseID, newBaseID);
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
				msgs = ((InternalEObject)baseID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.SUBJECT_CONFIRMATION_TYPE__BASE_ID, null, msgs);
			if (newBaseID != null)
				msgs = ((InternalEObject)newBaseID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.SUBJECT_CONFIRMATION_TYPE__BASE_ID, null, msgs);
			msgs = basicSetBaseID(newBaseID, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.SUBJECT_CONFIRMATION_TYPE__BASE_ID, newBaseID, newBaseID));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AssertionPackage.SUBJECT_CONFIRMATION_TYPE__NAME_ID, oldNameID, newNameID);
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
				msgs = ((InternalEObject)nameID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.SUBJECT_CONFIRMATION_TYPE__NAME_ID, null, msgs);
			if (newNameID != null)
				msgs = ((InternalEObject)newNameID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.SUBJECT_CONFIRMATION_TYPE__NAME_ID, null, msgs);
			msgs = basicSetNameID(newNameID, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.SUBJECT_CONFIRMATION_TYPE__NAME_ID, newNameID, newNameID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubjectConfirmationDataType getSubjectConfirmationData() {
		return subjectConfirmationData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubjectConfirmationData(SubjectConfirmationDataType newSubjectConfirmationData, NotificationChain msgs) {
		SubjectConfirmationDataType oldSubjectConfirmationData = subjectConfirmationData;
		subjectConfirmationData = newSubjectConfirmationData;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AssertionPackage.SUBJECT_CONFIRMATION_TYPE__SUBJECT_CONFIRMATION_DATA, oldSubjectConfirmationData, newSubjectConfirmationData);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubjectConfirmationData(SubjectConfirmationDataType newSubjectConfirmationData) {
		if (newSubjectConfirmationData != subjectConfirmationData) {
			NotificationChain msgs = null;
			if (subjectConfirmationData != null)
				msgs = ((InternalEObject)subjectConfirmationData).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.SUBJECT_CONFIRMATION_TYPE__SUBJECT_CONFIRMATION_DATA, null, msgs);
			if (newSubjectConfirmationData != null)
				msgs = ((InternalEObject)newSubjectConfirmationData).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.SUBJECT_CONFIRMATION_TYPE__SUBJECT_CONFIRMATION_DATA, null, msgs);
			msgs = basicSetSubjectConfirmationData(newSubjectConfirmationData, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.SUBJECT_CONFIRMATION_TYPE__SUBJECT_CONFIRMATION_DATA, newSubjectConfirmationData, newSubjectConfirmationData));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMethod(String newMethod) {
		String oldMethod = method;
		method = newMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.SUBJECT_CONFIRMATION_TYPE__METHOD, oldMethod, method));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__BASE_ID:
				return basicSetBaseID(null, msgs);
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__NAME_ID:
				return basicSetNameID(null, msgs);
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__SUBJECT_CONFIRMATION_DATA:
				return basicSetSubjectConfirmationData(null, msgs);
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
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__BASE_ID:
				return getBaseID();
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__NAME_ID:
				return getNameID();
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__SUBJECT_CONFIRMATION_DATA:
				return getSubjectConfirmationData();
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__METHOD:
				return getMethod();
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
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__BASE_ID:
				setBaseID((BaseIDAbstractType)newValue);
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__NAME_ID:
				setNameID((NameIDType)newValue);
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__SUBJECT_CONFIRMATION_DATA:
				setSubjectConfirmationData((SubjectConfirmationDataType)newValue);
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__METHOD:
				setMethod((String)newValue);
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
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__BASE_ID:
				setBaseID((BaseIDAbstractType)null);
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__NAME_ID:
				setNameID((NameIDType)null);
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__SUBJECT_CONFIRMATION_DATA:
				setSubjectConfirmationData((SubjectConfirmationDataType)null);
				return;
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__METHOD:
				setMethod(METHOD_EDEFAULT);
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
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__BASE_ID:
				return baseID != null;
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__NAME_ID:
				return nameID != null;
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__SUBJECT_CONFIRMATION_DATA:
				return subjectConfirmationData != null;
			case AssertionPackage.SUBJECT_CONFIRMATION_TYPE__METHOD:
				return METHOD_EDEFAULT == null ? method != null : !METHOD_EDEFAULT.equals(method);
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
		result.append(" (method: ");
		result.append(method);
		result.append(')');
		return result.toString();
	}

} //SubjectConfirmationTypeImpl
