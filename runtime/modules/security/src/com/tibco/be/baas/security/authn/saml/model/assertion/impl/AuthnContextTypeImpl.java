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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Authn Context Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnContextTypeImpl#getAuthnContextClassRef <em>Authn Context Class Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnContextTypeImpl#getAuthnContextDecl <em>Authn Context Decl</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnContextTypeImpl#getAuthnContextDeclRef <em>Authn Context Decl Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnContextTypeImpl#getAuthnContextDecl1 <em>Authn Context Decl1</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnContextTypeImpl#getAuthnContextDeclRef1 <em>Authn Context Decl Ref1</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnContextTypeImpl#getAuthenticatingAuthority <em>Authenticating Authority</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AuthnContextTypeImpl extends EObjectImpl implements AuthnContextType {
	/**
	 * The default value of the '{@link #getAuthnContextClassRef() <em>Authn Context Class Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthnContextClassRef()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTHN_CONTEXT_CLASS_REF_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAuthnContextClassRef() <em>Authn Context Class Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthnContextClassRef()
	 * @generated
	 * @ordered
	 */
	protected String authnContextClassRef = AUTHN_CONTEXT_CLASS_REF_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAuthnContextDecl() <em>Authn Context Decl</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthnContextDecl()
	 * @generated
	 * @ordered
	 */
	protected EObject authnContextDecl;

	/**
	 * The default value of the '{@link #getAuthnContextDeclRef() <em>Authn Context Decl Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthnContextDeclRef()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTHN_CONTEXT_DECL_REF_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAuthnContextDeclRef() <em>Authn Context Decl Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthnContextDeclRef()
	 * @generated
	 * @ordered
	 */
	protected String authnContextDeclRef = AUTHN_CONTEXT_DECL_REF_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAuthnContextDecl1() <em>Authn Context Decl1</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthnContextDecl1()
	 * @generated
	 * @ordered
	 */
	protected EObject authnContextDecl1;

	/**
	 * The default value of the '{@link #getAuthnContextDeclRef1() <em>Authn Context Decl Ref1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthnContextDeclRef1()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTHN_CONTEXT_DECL_REF1_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAuthnContextDeclRef1() <em>Authn Context Decl Ref1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthnContextDeclRef1()
	 * @generated
	 * @ordered
	 */
	protected String authnContextDeclRef1 = AUTHN_CONTEXT_DECL_REF1_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAuthenticatingAuthority() <em>Authenticating Authority</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthenticatingAuthority()
	 * @generated
	 * @ordered
	 */
	protected EList<String> authenticatingAuthority;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AuthnContextTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AssertionPackage.Literals.AUTHN_CONTEXT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAuthnContextClassRef() {
		return authnContextClassRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthnContextClassRef(String newAuthnContextClassRef) {
		String oldAuthnContextClassRef = authnContextClassRef;
		authnContextClassRef = newAuthnContextClassRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_CLASS_REF, oldAuthnContextClassRef, authnContextClassRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getAuthnContextDecl() {
		return authnContextDecl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthnContextDecl(EObject newAuthnContextDecl, NotificationChain msgs) {
		EObject oldAuthnContextDecl = authnContextDecl;
		authnContextDecl = newAuthnContextDecl;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL, oldAuthnContextDecl, newAuthnContextDecl);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthnContextDecl(EObject newAuthnContextDecl) {
		if (newAuthnContextDecl != authnContextDecl) {
			NotificationChain msgs = null;
			if (authnContextDecl != null)
				msgs = ((InternalEObject)authnContextDecl).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL, null, msgs);
			if (newAuthnContextDecl != null)
				msgs = ((InternalEObject)newAuthnContextDecl).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL, null, msgs);
			msgs = basicSetAuthnContextDecl(newAuthnContextDecl, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL, newAuthnContextDecl, newAuthnContextDecl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAuthnContextDeclRef() {
		return authnContextDeclRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthnContextDeclRef(String newAuthnContextDeclRef) {
		String oldAuthnContextDeclRef = authnContextDeclRef;
		authnContextDeclRef = newAuthnContextDeclRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF, oldAuthnContextDeclRef, authnContextDeclRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getAuthnContextDecl1() {
		return authnContextDecl1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthnContextDecl1(EObject newAuthnContextDecl1, NotificationChain msgs) {
		EObject oldAuthnContextDecl1 = authnContextDecl1;
		authnContextDecl1 = newAuthnContextDecl1;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL1, oldAuthnContextDecl1, newAuthnContextDecl1);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthnContextDecl1(EObject newAuthnContextDecl1) {
		if (newAuthnContextDecl1 != authnContextDecl1) {
			NotificationChain msgs = null;
			if (authnContextDecl1 != null)
				msgs = ((InternalEObject)authnContextDecl1).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL1, null, msgs);
			if (newAuthnContextDecl1 != null)
				msgs = ((InternalEObject)newAuthnContextDecl1).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL1, null, msgs);
			msgs = basicSetAuthnContextDecl1(newAuthnContextDecl1, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL1, newAuthnContextDecl1, newAuthnContextDecl1));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAuthnContextDeclRef1() {
		return authnContextDeclRef1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthnContextDeclRef1(String newAuthnContextDeclRef1) {
		String oldAuthnContextDeclRef1 = authnContextDeclRef1;
		authnContextDeclRef1 = newAuthnContextDeclRef1;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF1, oldAuthnContextDeclRef1, authnContextDeclRef1));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getAuthenticatingAuthority() {
		if (authenticatingAuthority == null) {
			authenticatingAuthority = new EDataTypeEList<String>(String.class, this, AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHENTICATING_AUTHORITY);
		}
		return authenticatingAuthority;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL:
				return basicSetAuthnContextDecl(null, msgs);
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL1:
				return basicSetAuthnContextDecl1(null, msgs);
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
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_CLASS_REF:
				return getAuthnContextClassRef();
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL:
				return getAuthnContextDecl();
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF:
				return getAuthnContextDeclRef();
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL1:
				return getAuthnContextDecl1();
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF1:
				return getAuthnContextDeclRef1();
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHENTICATING_AUTHORITY:
				return getAuthenticatingAuthority();
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
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_CLASS_REF:
				setAuthnContextClassRef((String)newValue);
				return;
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL:
				setAuthnContextDecl((EObject)newValue);
				return;
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF:
				setAuthnContextDeclRef((String)newValue);
				return;
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL1:
				setAuthnContextDecl1((EObject)newValue);
				return;
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF1:
				setAuthnContextDeclRef1((String)newValue);
				return;
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHENTICATING_AUTHORITY:
				getAuthenticatingAuthority().clear();
				getAuthenticatingAuthority().addAll((Collection<? extends String>)newValue);
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
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_CLASS_REF:
				setAuthnContextClassRef(AUTHN_CONTEXT_CLASS_REF_EDEFAULT);
				return;
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL:
				setAuthnContextDecl((EObject)null);
				return;
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF:
				setAuthnContextDeclRef(AUTHN_CONTEXT_DECL_REF_EDEFAULT);
				return;
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL1:
				setAuthnContextDecl1((EObject)null);
				return;
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF1:
				setAuthnContextDeclRef1(AUTHN_CONTEXT_DECL_REF1_EDEFAULT);
				return;
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHENTICATING_AUTHORITY:
				getAuthenticatingAuthority().clear();
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
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_CLASS_REF:
				return AUTHN_CONTEXT_CLASS_REF_EDEFAULT == null ? authnContextClassRef != null : !AUTHN_CONTEXT_CLASS_REF_EDEFAULT.equals(authnContextClassRef);
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL:
				return authnContextDecl != null;
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF:
				return AUTHN_CONTEXT_DECL_REF_EDEFAULT == null ? authnContextDeclRef != null : !AUTHN_CONTEXT_DECL_REF_EDEFAULT.equals(authnContextDeclRef);
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL1:
				return authnContextDecl1 != null;
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF1:
				return AUTHN_CONTEXT_DECL_REF1_EDEFAULT == null ? authnContextDeclRef1 != null : !AUTHN_CONTEXT_DECL_REF1_EDEFAULT.equals(authnContextDeclRef1);
			case AssertionPackage.AUTHN_CONTEXT_TYPE__AUTHENTICATING_AUTHORITY:
				return authenticatingAuthority != null && !authenticatingAuthority.isEmpty();
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
		result.append(" (authnContextClassRef: ");
		result.append(authnContextClassRef);
		result.append(", authnContextDeclRef: ");
		result.append(authnContextDeclRef);
		result.append(", authnContextDeclRef1: ");
		result.append(authnContextDeclRef1);
		result.append(", authenticatingAuthority: ");
		result.append(authenticatingAuthority);
		result.append(')');
		return result.toString();
	}

} //AuthnContextTypeImpl
