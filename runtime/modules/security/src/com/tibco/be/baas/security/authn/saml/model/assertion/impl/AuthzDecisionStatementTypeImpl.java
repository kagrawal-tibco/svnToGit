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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.assertion.ActionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage;
import com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType;
import com.tibco.be.baas.security.authn.saml.model.assertion.DecisionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Authz Decision Statement Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthzDecisionStatementTypeImpl#getAction <em>Action</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthzDecisionStatementTypeImpl#getEvidence <em>Evidence</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthzDecisionStatementTypeImpl#getDecision <em>Decision</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AuthzDecisionStatementTypeImpl#getResource <em>Resource</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AuthzDecisionStatementTypeImpl extends StatementAbstractTypeImpl implements AuthzDecisionStatementType {
	/**
	 * The cached value of the '{@link #getAction() <em>Action</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAction()
	 * @generated
	 * @ordered
	 */
	protected EList<ActionType> action;

	/**
	 * The cached value of the '{@link #getEvidence() <em>Evidence</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvidence()
	 * @generated
	 * @ordered
	 */
	protected EvidenceType evidence;

	/**
	 * The default value of the '{@link #getDecision() <em>Decision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDecision()
	 * @generated
	 * @ordered
	 */
	protected static final DecisionType DECISION_EDEFAULT = DecisionType.PERMIT;

	/**
	 * The cached value of the '{@link #getDecision() <em>Decision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDecision()
	 * @generated
	 * @ordered
	 */
	protected DecisionType decision = DECISION_EDEFAULT;

	/**
	 * This is true if the Decision attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean decisionESet;

	/**
	 * The default value of the '{@link #getResource() <em>Resource</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResource()
	 * @generated
	 * @ordered
	 */
	protected static final String RESOURCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResource() <em>Resource</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResource()
	 * @generated
	 * @ordered
	 */
	protected String resource = RESOURCE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AuthzDecisionStatementTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AssertionPackage.Literals.AUTHZ_DECISION_STATEMENT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ActionType> getAction() {
		if (action == null) {
			action = new EObjectContainmentEList<ActionType>(ActionType.class, this, AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__ACTION);
		}
		return action;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EvidenceType getEvidence() {
		return evidence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEvidence(EvidenceType newEvidence, NotificationChain msgs) {
		EvidenceType oldEvidence = evidence;
		evidence = newEvidence;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__EVIDENCE, oldEvidence, newEvidence);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEvidence(EvidenceType newEvidence) {
		if (newEvidence != evidence) {
			NotificationChain msgs = null;
			if (evidence != null)
				msgs = ((InternalEObject)evidence).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__EVIDENCE, null, msgs);
			if (newEvidence != null)
				msgs = ((InternalEObject)newEvidence).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__EVIDENCE, null, msgs);
			msgs = basicSetEvidence(newEvidence, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__EVIDENCE, newEvidence, newEvidence));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DecisionType getDecision() {
		return decision;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDecision(DecisionType newDecision) {
		DecisionType oldDecision = decision;
		decision = newDecision == null ? DECISION_EDEFAULT : newDecision;
		boolean oldDecisionESet = decisionESet;
		decisionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__DECISION, oldDecision, decision, !oldDecisionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDecision() {
		DecisionType oldDecision = decision;
		boolean oldDecisionESet = decisionESet;
		decision = DECISION_EDEFAULT;
		decisionESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__DECISION, oldDecision, DECISION_EDEFAULT, oldDecisionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDecision() {
		return decisionESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getResource() {
		return resource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResource(String newResource) {
		String oldResource = resource;
		resource = newResource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__RESOURCE, oldResource, resource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__ACTION:
				return ((InternalEList<?>)getAction()).basicRemove(otherEnd, msgs);
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__EVIDENCE:
				return basicSetEvidence(null, msgs);
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
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__ACTION:
				return getAction();
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__EVIDENCE:
				return getEvidence();
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__DECISION:
				return getDecision();
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__RESOURCE:
				return getResource();
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
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__ACTION:
				getAction().clear();
				getAction().addAll((Collection<? extends ActionType>)newValue);
				return;
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__EVIDENCE:
				setEvidence((EvidenceType)newValue);
				return;
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__DECISION:
				setDecision((DecisionType)newValue);
				return;
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__RESOURCE:
				setResource((String)newValue);
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
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__ACTION:
				getAction().clear();
				return;
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__EVIDENCE:
				setEvidence((EvidenceType)null);
				return;
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__DECISION:
				unsetDecision();
				return;
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__RESOURCE:
				setResource(RESOURCE_EDEFAULT);
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
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__ACTION:
				return action != null && !action.isEmpty();
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__EVIDENCE:
				return evidence != null;
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__DECISION:
				return isSetDecision();
			case AssertionPackage.AUTHZ_DECISION_STATEMENT_TYPE__RESOURCE:
				return RESOURCE_EDEFAULT == null ? resource != null : !RESOURCE_EDEFAULT.equals(resource);
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
		result.append(" (decision: ");
		if (decisionESet) result.append(decision); else result.append("<unset>");
		result.append(", resource: ");
		result.append(resource);
		result.append(')');
		return result.toString();
	}

} //AuthzDecisionStatementTypeImpl
