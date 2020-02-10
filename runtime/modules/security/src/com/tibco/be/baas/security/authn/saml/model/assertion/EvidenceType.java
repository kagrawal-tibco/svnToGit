/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Evidence Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType#getAssertionIDRef <em>Assertion ID Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType#getAssertionURIRef <em>Assertion URI Ref</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.EvidenceType#getAssertion <em>Assertion</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getEvidenceType()
 * @model extendedMetaData="name='EvidenceType' kind='elementOnly'"
 * @generated
 */
public interface EvidenceType extends EObject, ISAMLObject {
	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getEvidenceType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Assertion ID Ref</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assertion ID Ref</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assertion ID Ref</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getEvidenceType_AssertionIDRef()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NCName" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AssertionIDRef' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<String> getAssertionIDRef();

	/**
	 * Returns the value of the '<em><b>Assertion URI Ref</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assertion URI Ref</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assertion URI Ref</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getEvidenceType_AssertionURIRef()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AssertionURIRef' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<String> getAssertionURIRef();

	/**
	 * Returns the value of the '<em><b>Assertion</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.assertion.AssertionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assertion</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assertion</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getEvidenceType_Assertion()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Assertion' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<AssertionType> getAssertion();

} // EvidenceType
