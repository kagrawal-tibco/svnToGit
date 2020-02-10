/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Authz Decision Statement Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType#getAction <em>Action</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType#getEvidence <em>Evidence</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType#getDecision <em>Decision</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType#getResource <em>Resource</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAuthzDecisionStatementType()
 * @model extendedMetaData="name='AuthzDecisionStatementType' kind='elementOnly'"
 * @generated
 */
public interface AuthzDecisionStatementType extends StatementAbstractType {
	/**
	 * Returns the value of the '<em><b>Action</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.assertion.ActionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAuthzDecisionStatementType_Action()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Action' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ActionType> getAction();

	/**
	 * Returns the value of the '<em><b>Evidence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Evidence</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Evidence</em>' containment reference.
	 * @see #setEvidence(EvidenceType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAuthzDecisionStatementType_Evidence()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Evidence' namespace='##targetNamespace'"
	 * @generated
	 */
	EvidenceType getEvidence();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType#getEvidence <em>Evidence</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Evidence</em>' containment reference.
	 * @see #getEvidence()
	 * @generated
	 */
	void setEvidence(EvidenceType value);

	/**
	 * Returns the value of the '<em><b>Decision</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.be.baas.security.authn.saml.model.assertion.DecisionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Decision</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Decision</em>' attribute.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DecisionType
	 * @see #isSetDecision()
	 * @see #unsetDecision()
	 * @see #setDecision(DecisionType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAuthzDecisionStatementType_Decision()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='Decision'"
	 * @generated
	 */
	DecisionType getDecision();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType#getDecision <em>Decision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Decision</em>' attribute.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.DecisionType
	 * @see #isSetDecision()
	 * @see #unsetDecision()
	 * @see #getDecision()
	 * @generated
	 */
	void setDecision(DecisionType value);

	/**
	 * Unsets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType#getDecision <em>Decision</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDecision()
	 * @see #getDecision()
	 * @see #setDecision(DecisionType)
	 * @generated
	 */
	void unsetDecision();

	/**
	 * Returns whether the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType#getDecision <em>Decision</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Decision</em>' attribute is set.
	 * @see #unsetDecision()
	 * @see #getDecision()
	 * @see #setDecision(DecisionType)
	 * @generated
	 */
	boolean isSetDecision();

	/**
	 * Returns the value of the '<em><b>Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource</em>' attribute.
	 * @see #setResource(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAuthzDecisionStatementType_Resource()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI" required="true"
	 *        extendedMetaData="kind='attribute' name='Resource'"
	 * @generated
	 */
	String getResource();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AuthzDecisionStatementType#getResource <em>Resource</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource</em>' attribute.
	 * @see #getResource()
	 * @generated
	 */
	void setResource(String value);

} // AuthzDecisionStatementType
