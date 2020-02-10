/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion.impl;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthnContextType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectLocalityType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Authn Statement Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnStatementTypeImpl#getSubjectLocality <em>Subject Locality</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnStatementTypeImpl#getAuthnContext <em>Authn Context</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnStatementTypeImpl#getAuthnInstant <em>Authn Instant</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnStatementTypeImpl#getSessionIndex <em>Session Index</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthnStatementTypeImpl#getSessionNotOnOrAfter <em>Session Not On Or After</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AuthnStatementTypeImpl extends StatementAbstractTypeImpl implements AuthnStatementType {
	/**
	 * The cached value of the '{@link #getSubjectLocality() <em>Subject Locality</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubjectLocality()
	 * @generated
	 * @ordered
	 */
	protected SubjectLocalityType subjectLocality;

	/**
	 * The cached value of the '{@link #getAuthnContext() <em>Authn Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthnContext()
	 * @generated
	 * @ordered
	 */
	protected AuthnContextType authnContext;

	/**
	 * The default value of the '{@link #getAuthnInstant() <em>Authn Instant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthnInstant()
	 * @generated
	 * @ordered
	 */
	protected static final XMLGregorianCalendar AUTHN_INSTANT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAuthnInstant() <em>Authn Instant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthnInstant()
	 * @generated
	 * @ordered
	 */
	protected XMLGregorianCalendar authnInstant = AUTHN_INSTANT_EDEFAULT;

	/**
	 * The default value of the '{@link #getSessionIndex() <em>Session Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSessionIndex()
	 * @generated
	 * @ordered
	 */
	protected static final String SESSION_INDEX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSessionIndex() <em>Session Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSessionIndex()
	 * @generated
	 * @ordered
	 */
	protected String sessionIndex = SESSION_INDEX_EDEFAULT;

	/**
	 * The default value of the '{@link #getSessionNotOnOrAfter() <em>Session Not On Or After</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSessionNotOnOrAfter()
	 * @generated
	 * @ordered
	 */
	protected static final XMLGregorianCalendar SESSION_NOT_ON_OR_AFTER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSessionNotOnOrAfter() <em>Session Not On Or After</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSessionNotOnOrAfter()
	 * @generated
	 * @ordered
	 */
	protected XMLGregorianCalendar sessionNotOnOrAfter = SESSION_NOT_ON_OR_AFTER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AuthnStatementTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AssertionPackage.Literals.AUTHN_STATEMENT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SubjectLocalityType getSubjectLocality() {
		return subjectLocality;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubjectLocality(SubjectLocalityType newSubjectLocality, NotificationChain msgs) {
		SubjectLocalityType oldSubjectLocality = subjectLocality;
		subjectLocality = newSubjectLocality;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AssertionPackage.AUTHN_STATEMENT_TYPE__SUBJECT_LOCALITY, oldSubjectLocality, newSubjectLocality);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubjectLocality(SubjectLocalityType newSubjectLocality) {
		if (newSubjectLocality != subjectLocality) {
			NotificationChain msgs = null;
			if (subjectLocality != null)
				msgs = ((InternalEObject)subjectLocality).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.AUTHN_STATEMENT_TYPE__SUBJECT_LOCALITY, null, msgs);
			if (newSubjectLocality != null)
				msgs = ((InternalEObject)newSubjectLocality).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.AUTHN_STATEMENT_TYPE__SUBJECT_LOCALITY, null, msgs);
			msgs = basicSetSubjectLocality(newSubjectLocality, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.AUTHN_STATEMENT_TYPE__SUBJECT_LOCALITY, newSubjectLocality, newSubjectLocality));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthnContextType getAuthnContext() {
		return authnContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAuthnContext(AuthnContextType newAuthnContext, NotificationChain msgs) {
		AuthnContextType oldAuthnContext = authnContext;
		authnContext = newAuthnContext;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AssertionPackage.AUTHN_STATEMENT_TYPE__AUTHN_CONTEXT, oldAuthnContext, newAuthnContext);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthnContext(AuthnContextType newAuthnContext) {
		if (newAuthnContext != authnContext) {
			NotificationChain msgs = null;
			if (authnContext != null)
				msgs = ((InternalEObject)authnContext).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.AUTHN_STATEMENT_TYPE__AUTHN_CONTEXT, null, msgs);
			if (newAuthnContext != null)
				msgs = ((InternalEObject)newAuthnContext).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.AUTHN_STATEMENT_TYPE__AUTHN_CONTEXT, null, msgs);
			msgs = basicSetAuthnContext(newAuthnContext, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.AUTHN_STATEMENT_TYPE__AUTHN_CONTEXT, newAuthnContext, newAuthnContext));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XMLGregorianCalendar getAuthnInstant() {
		return authnInstant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthnInstant(XMLGregorianCalendar newAuthnInstant) {
		XMLGregorianCalendar oldAuthnInstant = authnInstant;
		authnInstant = newAuthnInstant;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.AUTHN_STATEMENT_TYPE__AUTHN_INSTANT, oldAuthnInstant, authnInstant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSessionIndex() {
		return sessionIndex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSessionIndex(String newSessionIndex) {
		String oldSessionIndex = sessionIndex;
		sessionIndex = newSessionIndex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.AUTHN_STATEMENT_TYPE__SESSION_INDEX, oldSessionIndex, sessionIndex));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XMLGregorianCalendar getSessionNotOnOrAfter() {
		return sessionNotOnOrAfter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSessionNotOnOrAfter(XMLGregorianCalendar newSessionNotOnOrAfter) {
		XMLGregorianCalendar oldSessionNotOnOrAfter = sessionNotOnOrAfter;
		sessionNotOnOrAfter = newSessionNotOnOrAfter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.AUTHN_STATEMENT_TYPE__SESSION_NOT_ON_OR_AFTER, oldSessionNotOnOrAfter, sessionNotOnOrAfter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AssertionPackage.AUTHN_STATEMENT_TYPE__SUBJECT_LOCALITY:
				return basicSetSubjectLocality(null, msgs);
			case AssertionPackage.AUTHN_STATEMENT_TYPE__AUTHN_CONTEXT:
				return basicSetAuthnContext(null, msgs);
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
			case AssertionPackage.AUTHN_STATEMENT_TYPE__SUBJECT_LOCALITY:
				return getSubjectLocality();
			case AssertionPackage.AUTHN_STATEMENT_TYPE__AUTHN_CONTEXT:
				return getAuthnContext();
			case AssertionPackage.AUTHN_STATEMENT_TYPE__AUTHN_INSTANT:
				return getAuthnInstant();
			case AssertionPackage.AUTHN_STATEMENT_TYPE__SESSION_INDEX:
				return getSessionIndex();
			case AssertionPackage.AUTHN_STATEMENT_TYPE__SESSION_NOT_ON_OR_AFTER:
				return getSessionNotOnOrAfter();
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
			case AssertionPackage.AUTHN_STATEMENT_TYPE__SUBJECT_LOCALITY:
				setSubjectLocality((SubjectLocalityType)newValue);
				return;
			case AssertionPackage.AUTHN_STATEMENT_TYPE__AUTHN_CONTEXT:
				setAuthnContext((AuthnContextType)newValue);
				return;
			case AssertionPackage.AUTHN_STATEMENT_TYPE__AUTHN_INSTANT:
				setAuthnInstant((XMLGregorianCalendar)newValue);
				return;
			case AssertionPackage.AUTHN_STATEMENT_TYPE__SESSION_INDEX:
				setSessionIndex((String)newValue);
				return;
			case AssertionPackage.AUTHN_STATEMENT_TYPE__SESSION_NOT_ON_OR_AFTER:
				setSessionNotOnOrAfter((XMLGregorianCalendar)newValue);
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
			case AssertionPackage.AUTHN_STATEMENT_TYPE__SUBJECT_LOCALITY:
				setSubjectLocality((SubjectLocalityType)null);
				return;
			case AssertionPackage.AUTHN_STATEMENT_TYPE__AUTHN_CONTEXT:
				setAuthnContext((AuthnContextType)null);
				return;
			case AssertionPackage.AUTHN_STATEMENT_TYPE__AUTHN_INSTANT:
				setAuthnInstant(AUTHN_INSTANT_EDEFAULT);
				return;
			case AssertionPackage.AUTHN_STATEMENT_TYPE__SESSION_INDEX:
				setSessionIndex(SESSION_INDEX_EDEFAULT);
				return;
			case AssertionPackage.AUTHN_STATEMENT_TYPE__SESSION_NOT_ON_OR_AFTER:
				setSessionNotOnOrAfter(SESSION_NOT_ON_OR_AFTER_EDEFAULT);
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
			case AssertionPackage.AUTHN_STATEMENT_TYPE__SUBJECT_LOCALITY:
				return subjectLocality != null;
			case AssertionPackage.AUTHN_STATEMENT_TYPE__AUTHN_CONTEXT:
				return authnContext != null;
			case AssertionPackage.AUTHN_STATEMENT_TYPE__AUTHN_INSTANT:
				return AUTHN_INSTANT_EDEFAULT == null ? authnInstant != null : !AUTHN_INSTANT_EDEFAULT.equals(authnInstant);
			case AssertionPackage.AUTHN_STATEMENT_TYPE__SESSION_INDEX:
				return SESSION_INDEX_EDEFAULT == null ? sessionIndex != null : !SESSION_INDEX_EDEFAULT.equals(sessionIndex);
			case AssertionPackage.AUTHN_STATEMENT_TYPE__SESSION_NOT_ON_OR_AFTER:
				return SESSION_NOT_ON_OR_AFTER_EDEFAULT == null ? sessionNotOnOrAfter != null : !SESSION_NOT_ON_OR_AFTER_EDEFAULT.equals(sessionNotOnOrAfter);
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
		result.append(" (authnInstant: ");
		result.append(authnInstant);
		result.append(", sessionIndex: ");
		result.append(sessionIndex);
		result.append(", sessionNotOnOrAfter: ");
		result.append(sessionNotOnOrAfter);
		result.append(')');
		return result.toString();
	}

} //AuthnStatementTypeImpl
