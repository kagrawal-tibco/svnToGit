/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion;

import org.eclipse.emf.ecore.EObject;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Subject Confirmation Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType#getBaseID <em>Base ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType#getNameID <em>Name ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType#getSubjectConfirmationData <em>Subject Confirmation Data</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType#getMethod <em>Method</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectConfirmationType()
 * @model extendedMetaData="name='SubjectConfirmationType' kind='elementOnly'"
 * @generated NOT
 */
public interface SubjectConfirmationType extends EObject, ISAMLObject {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectConfirmationType_BaseID()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='BaseID' namespace='##targetNamespace'"
	 * @generated
	 */
	BaseIDAbstractType getBaseID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType#getBaseID <em>Base ID</em>}' containment reference.
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
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectConfirmationType_NameID()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='NameID' namespace='##targetNamespace'"
	 * @generated
	 */
	NameIDType getNameID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType#getNameID <em>Name ID</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name ID</em>' containment reference.
	 * @see #getNameID()
	 * @generated
	 */
	void setNameID(NameIDType value);

	/**
	 * Returns the value of the '<em><b>Subject Confirmation Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subject Confirmation Data</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subject Confirmation Data</em>' containment reference.
	 * @see #setSubjectConfirmationData(SubjectConfirmationDataType)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectConfirmationType_SubjectConfirmationData()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='SubjectConfirmationData' namespace='##targetNamespace'"
	 * @generated
	 */
	SubjectConfirmationDataType getSubjectConfirmationData();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType#getSubjectConfirmationData <em>Subject Confirmation Data</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subject Confirmation Data</em>' containment reference.
	 * @see #getSubjectConfirmationData()
	 * @generated
	 */
	void setSubjectConfirmationData(SubjectConfirmationDataType value);

	/**
	 * Returns the value of the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Method</em>' attribute.
	 * @see #setMethod(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getSubjectConfirmationType_Method()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI" required="true"
	 *        extendedMetaData="kind='attribute' name='Method'"
	 * @generated
	 */
	String getMethod();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.SubjectConfirmationType#getMethod <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Method</em>' attribute.
	 * @see #getMethod()
	 * @generated
	 */
	void setMethod(String value);

} // SubjectConfirmationType
