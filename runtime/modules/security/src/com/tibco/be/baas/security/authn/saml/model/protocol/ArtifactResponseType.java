/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Artifact Response Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResponseType#getAny <em>Any</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getArtifactResponseType()
 * @model extendedMetaData="name='ArtifactResponseType' kind='elementOnly'"
 * @generated
 */
public interface ArtifactResponseType extends StatusResponseType {
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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getArtifactResponseType_Any()
	 * @model dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="false"
	 *        extendedMetaData="kind='elementWildcard' wildcards='##any' name=':9' processing='lax'"
	 * @generated
	 */
	FeatureMap getAny();

} // ArtifactResponseType
