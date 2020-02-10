/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;
import com.tibco.be.baas.security.authn.saml.model.protocol.SubjectQueryAbstractType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subject Query Abstract Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.SubjectQueryAbstractTypeImpl#getSubject <em>Subject</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class SubjectQueryAbstractTypeImpl extends RequestAbstractTypeImpl implements SubjectQueryAbstractType {
	/**
	 * The cached value of the '{@link #getSubject() <em>Subject</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubject()
	 * @generated
	 * @ordered
	 */
	protected SubjectType subject;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubjectQueryAbstractTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProtocolPackage.Literals.SUBJECT_QUERY_ABSTRACT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubjectType getSubject() {
		return subject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubject(SubjectType newSubject, NotificationChain msgs) {
		SubjectType oldSubject = subject;
		subject = newSubject;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.SUBJECT_QUERY_ABSTRACT_TYPE__SUBJECT, oldSubject, newSubject);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubject(SubjectType newSubject) {
		if (newSubject != subject) {
			NotificationChain msgs = null;
			if (subject != null)
				msgs = ((InternalEObject)subject).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.SUBJECT_QUERY_ABSTRACT_TYPE__SUBJECT, null, msgs);
			if (newSubject != null)
				msgs = ((InternalEObject)newSubject).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.SUBJECT_QUERY_ABSTRACT_TYPE__SUBJECT, null, msgs);
			msgs = basicSetSubject(newSubject, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.SUBJECT_QUERY_ABSTRACT_TYPE__SUBJECT, newSubject, newSubject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProtocolPackage.SUBJECT_QUERY_ABSTRACT_TYPE__SUBJECT:
				return basicSetSubject(null, msgs);
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
			case ProtocolPackage.SUBJECT_QUERY_ABSTRACT_TYPE__SUBJECT:
				return getSubject();
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
			case ProtocolPackage.SUBJECT_QUERY_ABSTRACT_TYPE__SUBJECT:
				setSubject((SubjectType)newValue);
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
			case ProtocolPackage.SUBJECT_QUERY_ABSTRACT_TYPE__SUBJECT:
				setSubject((SubjectType)null);
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
			case ProtocolPackage.SUBJECT_QUERY_ABSTRACT_TYPE__SUBJECT:
				return subject != null;
		}
		return super.eIsSet(featureID);
	}

} //SubjectQueryAbstractTypeImpl
