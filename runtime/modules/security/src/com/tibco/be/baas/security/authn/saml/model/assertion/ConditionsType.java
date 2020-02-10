/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

import com.tibco.be.baas.security.authn.saml.common.ISAMLObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Conditions Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getCondition <em>Condition</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getAudienceRestriction <em>Audience Restriction</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getOneTimeUse <em>One Time Use</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getProxyRestriction <em>Proxy Restriction</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getNotBefore <em>Not Before</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getNotOnOrAfter <em>Not On Or After</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getConditionsType()
 * @model extendedMetaData="name='ConditionsType' kind='elementOnly'"
 * @generated
 */
public interface ConditionsType extends EObject, ISAMLObject {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getConditionsType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionAbstractType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getConditionsType_Condition()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Condition' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<ConditionAbstractType> getCondition();

	/**
	 * Returns the value of the '<em><b>Audience Restriction</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.assertion.AudienceRestrictionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Audience Restriction</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Audience Restriction</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getConditionsType_AudienceRestriction()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='AudienceRestriction' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<AudienceRestrictionType> getAudienceRestriction();

	/**
	 * Returns the value of the '<em><b>One Time Use</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.assertion.OneTimeUseType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>One Time Use</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>One Time Use</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getConditionsType_OneTimeUse()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='OneTimeUse' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<OneTimeUseType> getOneTimeUse();

	/**
	 * Returns the value of the '<em><b>Proxy Restriction</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.assertion.ProxyRestrictionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Proxy Restriction</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Proxy Restriction</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getConditionsType_ProxyRestriction()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ProxyRestriction' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<ProxyRestrictionType> getProxyRestriction();

	/**
	 * Returns the value of the '<em><b>Not Before</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Not Before</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Not Before</em>' attribute.
	 * @see #setNotBefore(XMLGregorianCalendar)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getConditionsType_NotBefore()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
	 *        extendedMetaData="kind='attribute' name='NotBefore'"
	 * @generated
	 */
	XMLGregorianCalendar getNotBefore();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getNotBefore <em>Not Before</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Not Before</em>' attribute.
	 * @see #getNotBefore()
	 * @generated
	 */
	void setNotBefore(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>Not On Or After</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Not On Or After</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Not On Or After</em>' attribute.
	 * @see #setNotOnOrAfter(XMLGregorianCalendar)
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getConditionsType_NotOnOrAfter()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
	 *        extendedMetaData="kind='attribute' name='NotOnOrAfter'"
	 * @generated
	 */
	XMLGregorianCalendar getNotOnOrAfter();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.assertion.ConditionsType#getNotOnOrAfter <em>Not On Or After</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Not On Or After</em>' attribute.
	 * @see #getNotOnOrAfter()
	 * @generated
	 */
	void setNotOnOrAfter(XMLGregorianCalendar value);

} // ConditionsType
