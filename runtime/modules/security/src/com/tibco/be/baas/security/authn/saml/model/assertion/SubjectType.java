/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subject Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType#getBaseID <em>Base ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType#getNameID <em>Name ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType#getSubjectConfirmation <em>Subject Confirmation</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType#getSubjectConfirmation1 <em>Subject Confirmation1</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectType()
 * @model extendedMetaData="name='SubjectType' kind='elementOnly'"
 * @generated NOT
 */
public interface SubjectType extends EObject, ISAMLObject {
	/**
	 * Returns the value of the '<em><b>Base ID</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base ID</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base ID</em>' containment reference.
	 * @see #setBaseID(BaseIDAbstractType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectType_BaseID()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='BaseID' namespace='##targetNamespace'"
	 * @generated
	 */
	BaseIDAbstractType getBaseID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType#getBaseID <em>Base ID</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base ID</em>' containment reference.
	 * @see #getBaseID()
	 * @generated
	 */
	void setBaseID(BaseIDAbstractType value);

	/**
	 * Returns the value of the '<em><b>Name ID</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name ID</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name ID</em>' containment reference.
	 * @see #setNameID(NameIDType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectType_NameID()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='NameID' namespace='##targetNamespace'"
	 * @generated
	 */
	NameIDType getNameID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectType#getNameID <em>Name ID</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name ID</em>' containment reference.
	 * @see #getNameID()
	 * @generated
	 */
	void setNameID(NameIDType value);

	/**
	 * Returns the value of the '<em><b>Subject Confirmation</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subject Confirmation</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subject Confirmation</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectType_SubjectConfirmation()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='SubjectConfirmation' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<SubjectConfirmationType> getSubjectConfirmation();

	/**
	 * Returns the value of the '<em><b>Subject Confirmation1</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subject Confirmation1</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subject Confirmation1</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectType_SubjectConfirmation1()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='SubjectConfirmation' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<SubjectConfirmationType> getSubjectConfirmation1();

} // SubjectType
