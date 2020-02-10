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
 * A representation of the model object '<em><b>Audience Restriction Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.AudienceRestrictionType#getAudience <em>Audience</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAudienceRestrictionType()
 * @model extendedMetaData="name='AudienceRestrictionType' kind='elementOnly'"
 * @generated
 */
public interface AudienceRestrictionType extends ConditionAbstractType {
	/**
	 * Returns the value of the '<em><b>Audience</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Audience</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Audience</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage#getAudienceRestrictionType_Audience()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI" required="true"
	 *        extendedMetaData="kind='element' name='Audience' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<String> getAudience();

} // AudienceRestrictionType
