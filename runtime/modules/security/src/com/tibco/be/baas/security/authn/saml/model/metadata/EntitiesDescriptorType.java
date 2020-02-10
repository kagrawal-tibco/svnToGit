/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entities Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getEntityDescriptor <em>Entity Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getEntitiesDescriptor <em>Entities Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getCacheDuration <em>Cache Duration</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getID <em>ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getValidUntil <em>Valid Until</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntitiesDescriptorType()
 * @model extendedMetaData="name='EntitiesDescriptorType' kind='elementOnly'"
 * @generated
 */
public interface EntitiesDescriptorType extends EObject {
	/**
	 * Returns the value of the '<em><b>Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extensions</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extensions</em>' containment reference.
	 * @see #setExtensions(ExtensionsType)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntitiesDescriptorType_Extensions()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Extensions' namespace='##targetNamespace'"
	 * @generated
	 */
	ExtensionsType getExtensions();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getExtensions <em>Extensions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extensions</em>' containment reference.
	 * @see #getExtensions()
	 * @generated
	 */
	void setExtensions(ExtensionsType value);

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
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntitiesDescriptorType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:1'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Entity Descriptor</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Descriptor</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Descriptor</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntitiesDescriptorType_EntityDescriptor()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='EntityDescriptor' namespace='##targetNamespace' group='#group:1'"
	 * @generated
	 */
	EList<EntityDescriptorType> getEntityDescriptor();

	/**
	 * Returns the value of the '<em><b>Entities Descriptor</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entities Descriptor</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entities Descriptor</em>' containment reference list.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntitiesDescriptorType_EntitiesDescriptor()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='EntitiesDescriptor' namespace='##targetNamespace' group='#group:1'"
	 * @generated
	 */
	EList<EntitiesDescriptorType> getEntitiesDescriptor();

	/**
	 * Returns the value of the '<em><b>Cache Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Duration</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache Duration</em>' attribute.
	 * @see #setCacheDuration(Duration)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntitiesDescriptorType_CacheDuration()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Duration"
	 *        extendedMetaData="kind='attribute' name='cacheDuration'"
	 * @generated
	 */
	Duration getCacheDuration();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getCacheDuration <em>Cache Duration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache Duration</em>' attribute.
	 * @see #getCacheDuration()
	 * @generated
	 */
	void setCacheDuration(Duration value);

	/**
	 * Returns the value of the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>ID</em>' attribute.
	 * @see #setID(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntitiesDescriptorType_ID()
	 * @model id="true" dataType="org.eclipse.emf.ecore.xml.type.ID"
	 *        extendedMetaData="kind='attribute' name='ID'"
	 * @generated
	 */
	String getID();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getID <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ID</em>' attribute.
	 * @see #getID()
	 * @generated
	 */
	void setID(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntitiesDescriptorType_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='Name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Valid Until</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Valid Until</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Valid Until</em>' attribute.
	 * @see #setValidUntil(XMLGregorianCalendar)
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#getEntitiesDescriptorType_ValidUntil()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime"
	 *        extendedMetaData="kind='attribute' name='validUntil'"
	 * @generated
	 */
	XMLGregorianCalendar getValidUntil();

	/**
	 * Sets the value of the '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType#getValidUntil <em>Valid Until</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Valid Until</em>' attribute.
	 * @see #getValidUntil()
	 * @generated
	 */
	void setValidUntil(XMLGregorianCalendar value);

} // EntitiesDescriptorType
