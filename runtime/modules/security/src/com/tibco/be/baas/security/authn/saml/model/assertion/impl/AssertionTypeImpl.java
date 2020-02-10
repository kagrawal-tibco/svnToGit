/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion.impl;

import java.util.Collection;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.assertion.AdviceType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AttributeStatementType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthnStatementType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType;
import com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType;
import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;
import com.tibco.be.baas.security.authn.saml.model.assertion.StatementAbstractType;
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionTypeImpl#getIssuer <em>Issuer</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionTypeImpl#getSubject <em>Subject</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionTypeImpl#getConditions <em>Conditions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionTypeImpl#getAdvice <em>Advice</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionTypeImpl#getStatement <em>Statement</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionTypeImpl#getAuthnStatement <em>Authn Statement</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionTypeImpl#getAuthzDecisionStatement <em>Authz Decision Statement</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionTypeImpl#getAttributeStatement <em>Attribute Statement</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionTypeImpl#getID <em>ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionTypeImpl#getIssueInstant <em>Issue Instant</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionTypeImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AssertionTypeImpl extends EObjectImpl implements AssertionType {
	/**
	 * The cached value of the '{@link #getIssuer() <em>Issuer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIssuer()
	 * @generated
	 * @ordered
	 */
	protected NameIDType issuer;

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
	 * The cached value of the '{@link #getConditions() <em>Conditions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConditions()
	 * @generated
	 * @ordered
	 */
	protected ConditionsType conditions;

	/**
	 * The cached value of the '{@link #getAdvice() <em>Advice</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdvice()
	 * @generated
	 * @ordered
	 */
	protected AdviceType advice;

	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap group;

	/**
	 * The default value of the '{@link #getID() <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getID()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getID() <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getID()
	 * @generated
	 * @ordered
	 */
	protected String iD = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getIssueInstant() <em>Issue Instant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIssueInstant()
	 * @generated
	 * @ordered
	 */
	protected static final XMLGregorianCalendar ISSUE_INSTANT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIssueInstant() <em>Issue Instant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIssueInstant()
	 * @generated
	 * @ordered
	 */
	protected XMLGregorianCalendar issueInstant = ISSUE_INSTANT_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AssertionTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AssertionPackage.Literals.ASSERTION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameIDType getIssuer() {
		return issuer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIssuer(NameIDType newIssuer, NotificationChain msgs) {
		NameIDType oldIssuer = issuer;
		issuer = newIssuer;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AssertionPackage.ASSERTION_TYPE__ISSUER, oldIssuer, newIssuer);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIssuer(NameIDType newIssuer) {
		if (newIssuer != issuer) {
			NotificationChain msgs = null;
			if (issuer != null)
				msgs = ((InternalEObject)issuer).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.ASSERTION_TYPE__ISSUER, null, msgs);
			if (newIssuer != null)
				msgs = ((InternalEObject)newIssuer).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.ASSERTION_TYPE__ISSUER, null, msgs);
			msgs = basicSetIssuer(newIssuer, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.ASSERTION_TYPE__ISSUER, newIssuer, newIssuer));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AssertionPackage.ASSERTION_TYPE__SUBJECT, oldSubject, newSubject);
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
				msgs = ((InternalEObject)subject).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.ASSERTION_TYPE__SUBJECT, null, msgs);
			if (newSubject != null)
				msgs = ((InternalEObject)newSubject).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.ASSERTION_TYPE__SUBJECT, null, msgs);
			msgs = basicSetSubject(newSubject, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.ASSERTION_TYPE__SUBJECT, newSubject, newSubject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionsType getConditions() {
		return conditions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConditions(ConditionsType newConditions, NotificationChain msgs) {
		ConditionsType oldConditions = conditions;
		conditions = newConditions;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AssertionPackage.ASSERTION_TYPE__CONDITIONS, oldConditions, newConditions);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConditions(ConditionsType newConditions) {
		if (newConditions != conditions) {
			NotificationChain msgs = null;
			if (conditions != null)
				msgs = ((InternalEObject)conditions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.ASSERTION_TYPE__CONDITIONS, null, msgs);
			if (newConditions != null)
				msgs = ((InternalEObject)newConditions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.ASSERTION_TYPE__CONDITIONS, null, msgs);
			msgs = basicSetConditions(newConditions, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.ASSERTION_TYPE__CONDITIONS, newConditions, newConditions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdviceType getAdvice() {
		return advice;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAdvice(AdviceType newAdvice, NotificationChain msgs) {
		AdviceType oldAdvice = advice;
		advice = newAdvice;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AssertionPackage.ASSERTION_TYPE__ADVICE, oldAdvice, newAdvice);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdvice(AdviceType newAdvice) {
		if (newAdvice != advice) {
			NotificationChain msgs = null;
			if (advice != null)
				msgs = ((InternalEObject)advice).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.ASSERTION_TYPE__ADVICE, null, msgs);
			if (newAdvice != null)
				msgs = ((InternalEObject)newAdvice).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.ASSERTION_TYPE__ADVICE, null, msgs);
			msgs = basicSetAdvice(newAdvice, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.ASSERTION_TYPE__ADVICE, newAdvice, newAdvice));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, AssertionPackage.ASSERTION_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<StatementAbstractType> getStatement() {
		return getGroup().list(AssertionPackage.Literals.ASSERTION_TYPE__STATEMENT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AuthnStatementType> getAuthnStatement() {
		return getGroup().list(AssertionPackage.Literals.ASSERTION_TYPE__AUTHN_STATEMENT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AuthzDecisionStatementType> getAuthzDecisionStatement() {
		return getGroup().list(AssertionPackage.Literals.ASSERTION_TYPE__AUTHZ_DECISION_STATEMENT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AttributeStatementType> getAttributeStatement() {
		return getGroup().list(AssertionPackage.Literals.ASSERTION_TYPE__ATTRIBUTE_STATEMENT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getID() {
		return iD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setID(String newID) {
		String oldID = iD;
		iD = newID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.ASSERTION_TYPE__ID, oldID, iD));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XMLGregorianCalendar getIssueInstant() {
		return issueInstant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIssueInstant(XMLGregorianCalendar newIssueInstant) {
		XMLGregorianCalendar oldIssueInstant = issueInstant;
		issueInstant = newIssueInstant;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.ASSERTION_TYPE__ISSUE_INSTANT, oldIssueInstant, issueInstant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.ASSERTION_TYPE__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AssertionPackage.ASSERTION_TYPE__ISSUER:
				return basicSetIssuer(null, msgs);
			case AssertionPackage.ASSERTION_TYPE__SUBJECT:
				return basicSetSubject(null, msgs);
			case AssertionPackage.ASSERTION_TYPE__CONDITIONS:
				return basicSetConditions(null, msgs);
			case AssertionPackage.ASSERTION_TYPE__ADVICE:
				return basicSetAdvice(null, msgs);
			case AssertionPackage.ASSERTION_TYPE__GROUP:
				return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
			case AssertionPackage.ASSERTION_TYPE__STATEMENT:
				return ((InternalEList<?>)getStatement()).basicRemove(otherEnd, msgs);
			case AssertionPackage.ASSERTION_TYPE__AUTHN_STATEMENT:
				return ((InternalEList<?>)getAuthnStatement()).basicRemove(otherEnd, msgs);
			case AssertionPackage.ASSERTION_TYPE__AUTHZ_DECISION_STATEMENT:
				return ((InternalEList<?>)getAuthzDecisionStatement()).basicRemove(otherEnd, msgs);
			case AssertionPackage.ASSERTION_TYPE__ATTRIBUTE_STATEMENT:
				return ((InternalEList<?>)getAttributeStatement()).basicRemove(otherEnd, msgs);
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
			case AssertionPackage.ASSERTION_TYPE__ISSUER:
				return getIssuer();
			case AssertionPackage.ASSERTION_TYPE__SUBJECT:
				return getSubject();
			case AssertionPackage.ASSERTION_TYPE__CONDITIONS:
				return getConditions();
			case AssertionPackage.ASSERTION_TYPE__ADVICE:
				return getAdvice();
			case AssertionPackage.ASSERTION_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case AssertionPackage.ASSERTION_TYPE__STATEMENT:
				return getStatement();
			case AssertionPackage.ASSERTION_TYPE__AUTHN_STATEMENT:
				return getAuthnStatement();
			case AssertionPackage.ASSERTION_TYPE__AUTHZ_DECISION_STATEMENT:
				return getAuthzDecisionStatement();
			case AssertionPackage.ASSERTION_TYPE__ATTRIBUTE_STATEMENT:
				return getAttributeStatement();
			case AssertionPackage.ASSERTION_TYPE__ID:
				return getID();
			case AssertionPackage.ASSERTION_TYPE__ISSUE_INSTANT:
				return getIssueInstant();
			case AssertionPackage.ASSERTION_TYPE__VERSION:
				return getVersion();
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
			case AssertionPackage.ASSERTION_TYPE__ISSUER:
				setIssuer((NameIDType)newValue);
				return;
			case AssertionPackage.ASSERTION_TYPE__SUBJECT:
				setSubject((SubjectType)newValue);
				return;
			case AssertionPackage.ASSERTION_TYPE__CONDITIONS:
				setConditions((ConditionsType)newValue);
				return;
			case AssertionPackage.ASSERTION_TYPE__ADVICE:
				setAdvice((AdviceType)newValue);
				return;
			case AssertionPackage.ASSERTION_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case AssertionPackage.ASSERTION_TYPE__STATEMENT:
				getStatement().clear();
				getStatement().addAll((Collection<? extends StatementAbstractType>)newValue);
				return;
			case AssertionPackage.ASSERTION_TYPE__AUTHN_STATEMENT:
				getAuthnStatement().clear();
				getAuthnStatement().addAll((Collection<? extends AuthnStatementType>)newValue);
				return;
			case AssertionPackage.ASSERTION_TYPE__AUTHZ_DECISION_STATEMENT:
				getAuthzDecisionStatement().clear();
				getAuthzDecisionStatement().addAll((Collection<? extends AuthzDecisionStatementType>)newValue);
				return;
			case AssertionPackage.ASSERTION_TYPE__ATTRIBUTE_STATEMENT:
				getAttributeStatement().clear();
				getAttributeStatement().addAll((Collection<? extends AttributeStatementType>)newValue);
				return;
			case AssertionPackage.ASSERTION_TYPE__ID:
				setID((String)newValue);
				return;
			case AssertionPackage.ASSERTION_TYPE__ISSUE_INSTANT:
				setIssueInstant((XMLGregorianCalendar)newValue);
				return;
			case AssertionPackage.ASSERTION_TYPE__VERSION:
				setVersion((String)newValue);
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
			case AssertionPackage.ASSERTION_TYPE__ISSUER:
				setIssuer((NameIDType)null);
				return;
			case AssertionPackage.ASSERTION_TYPE__SUBJECT:
				setSubject((SubjectType)null);
				return;
			case AssertionPackage.ASSERTION_TYPE__CONDITIONS:
				setConditions((ConditionsType)null);
				return;
			case AssertionPackage.ASSERTION_TYPE__ADVICE:
				setAdvice((AdviceType)null);
				return;
			case AssertionPackage.ASSERTION_TYPE__GROUP:
				getGroup().clear();
				return;
			case AssertionPackage.ASSERTION_TYPE__STATEMENT:
				getStatement().clear();
				return;
			case AssertionPackage.ASSERTION_TYPE__AUTHN_STATEMENT:
				getAuthnStatement().clear();
				return;
			case AssertionPackage.ASSERTION_TYPE__AUTHZ_DECISION_STATEMENT:
				getAuthzDecisionStatement().clear();
				return;
			case AssertionPackage.ASSERTION_TYPE__ATTRIBUTE_STATEMENT:
				getAttributeStatement().clear();
				return;
			case AssertionPackage.ASSERTION_TYPE__ID:
				setID(ID_EDEFAULT);
				return;
			case AssertionPackage.ASSERTION_TYPE__ISSUE_INSTANT:
				setIssueInstant(ISSUE_INSTANT_EDEFAULT);
				return;
			case AssertionPackage.ASSERTION_TYPE__VERSION:
				setVersion(VERSION_EDEFAULT);
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
			case AssertionPackage.ASSERTION_TYPE__ISSUER:
				return issuer != null;
			case AssertionPackage.ASSERTION_TYPE__SUBJECT:
				return subject != null;
			case AssertionPackage.ASSERTION_TYPE__CONDITIONS:
				return conditions != null;
			case AssertionPackage.ASSERTION_TYPE__ADVICE:
				return advice != null;
			case AssertionPackage.ASSERTION_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case AssertionPackage.ASSERTION_TYPE__STATEMENT:
				return !getStatement().isEmpty();
			case AssertionPackage.ASSERTION_TYPE__AUTHN_STATEMENT:
				return !getAuthnStatement().isEmpty();
			case AssertionPackage.ASSERTION_TYPE__AUTHZ_DECISION_STATEMENT:
				return !getAuthzDecisionStatement().isEmpty();
			case AssertionPackage.ASSERTION_TYPE__ATTRIBUTE_STATEMENT:
				return !getAttributeStatement().isEmpty();
			case AssertionPackage.ASSERTION_TYPE__ID:
				return ID_EDEFAULT == null ? iD != null : !ID_EDEFAULT.equals(iD);
			case AssertionPackage.ASSERTION_TYPE__ISSUE_INSTANT:
				return ISSUE_INSTANT_EDEFAULT == null ? issueInstant != null : !ISSUE_INSTANT_EDEFAULT.equals(issueInstant);
			case AssertionPackage.ASSERTION_TYPE__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
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
		result.append(" (group: ");
		result.append(group);
		result.append(", iD: ");
		result.append(iD);
		result.append(", issueInstant: ");
		result.append(issueInstant);
		result.append(", version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

} //AssertionTypeImpl
