/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import org.eclipse.emf.common.util.EList;

import com.tibco.be.baas.security.authn.saml.model.assertion.ActionType;
import com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Authz Decision Query Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthzDecisionQueryType#getAction <em>Action</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthzDecisionQueryType#getEvidence <em>Evidence</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthzDecisionQueryType#getResource <em>Resource</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthzDecisionQueryType()
 * @model extendedMetaData="name='AuthzDecisionQueryType' kind='elementOnly'"
 * @generated
 */
public interface AuthzDecisionQueryType extends SubjectQueryAbstractType {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthzDecisionQueryType_Action()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Action' namespace='urn:oasis:names:tc:SAML:2.0:assertion'"
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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthzDecisionQueryType_Evidence()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Evidence' namespace='urn:oasis:names:tc:SAML:2.0:assertion'"
	 * @generated
	 */
	EvidenceType getEvidence();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthzDecisionQueryType#getEvidence <em>Evidence</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Evidence</em>' containment reference.
	 * @see #getEvidence()
	 * @generated
	 */
	void setEvidence(EvidenceType value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getAuthzDecisionQueryType_Resource()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI" required="true"
	 *        extendedMetaData="kind='attribute' name='Resource'"
	 * @generated
	 */
	String getResource();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.AuthzDecisionQueryType#getResource <em>Resource</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource</em>' attribute.
	 * @see #getResource()
	 * @generated
	 */
	void setResource(String value);

} // AuthzDecisionQueryType
