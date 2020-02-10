/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Artifact Resolve Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResolveType#getArtifact <em>Artifact</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getArtifactResolveType()
 * @model extendedMetaData="name='ArtifactResolveType' kind='elementOnly'"
 * @generated
 */
public interface ArtifactResolveType extends RequestAbstractType {
	/**
	 * Returns the value of the '<em><b>Artifact</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Artifact</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifact</em>' attribute.
	 * @see #setArtifact(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#getArtifactResolveType_Artifact()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='Artifact' namespace='##targetNamespace'"
	 * @generated
	 */
	String getArtifact();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResolveType#getArtifact <em>Artifact</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Artifact</em>' attribute.
	 * @see #getArtifact()
	 * @generated
	 */
	void setArtifact(String value);

} // ArtifactResolveType
