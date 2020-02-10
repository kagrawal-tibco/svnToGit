/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Endpoint Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType#getAny <em>Any</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType#getBinding <em>Binding</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType#getResponseLocation <em>Response Location</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEndpointType()
 * @model extendedMetaData="name='EndpointType' kind='elementOnly'"
 * @generated
 */
public interface EndpointType extends EObject {
	/**
	 * Returns the value of the '<em><b>Any</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Any</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEndpointType_Any()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' wildcards='##other' name=':0' processing='lax'"
	 * @generated
	 */
	FeatureMap getAny();

	/**
	 * Returns the value of the '<em><b>Binding</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Binding</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Binding</em>' attribute.
	 * @see #setBinding(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEndpointType_Binding()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI" required="true"
	 *        extendedMetaData="kind='attribute' name='Binding'"
	 * @generated
	 */
	String getBinding();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType#getBinding <em>Binding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Binding</em>' attribute.
	 * @see #getBinding()
	 * @generated
	 */
	void setBinding(String value);

	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see #setLocation(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEndpointType_Location()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI" required="true"
	 *        extendedMetaData="kind='attribute' name='Location'"
	 * @generated
	 */
	String getLocation();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' attribute.
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(String value);

	/**
	 * Returns the value of the '<em><b>Response Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Response Location</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Response Location</em>' attribute.
	 * @see #setResponseLocation(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEndpointType_ResponseLocation()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
	 *        extendedMetaData="kind='attribute' name='ResponseLocation'"
	 * @generated
	 */
	String getResponseLocation();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType#getResponseLocation <em>Response Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Response Location</em>' attribute.
	 * @see #getResponseLocation()
	 * @generated
	 */
	void setResponseLocation(String value);

	/**
	 * Returns the value of the '<em><b>Any Attribute</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Any Attribute</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any Attribute</em>' attribute list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEndpointType_AnyAttribute()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':4' processing='lax'"
	 * @generated
	 */
	FeatureMap getAnyAttribute();

} // EndpointType
