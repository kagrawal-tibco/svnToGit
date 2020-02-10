/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType;
import com.tibco.be.baas.security.authn.saml.model.metadata.AdditionalMetadataLocationType;
import com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType;
import com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.ContactType;
import com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot;
import com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType;
import com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.ExtensionsType;
import com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.IndexedEndpointType;
import com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedNameType;
import com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedURIType;
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;
import com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType;
import com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.RequestedAttributeType;
import com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage
 * @generated
 */
public class MetadataSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static MetadataPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetadataSwitch() {
		if (modelPackage == null) {
			modelPackage = MetadataPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case MetadataPackage.ADDITIONAL_METADATA_LOCATION_TYPE: {
				AdditionalMetadataLocationType additionalMetadataLocationType = (AdditionalMetadataLocationType)theEObject;
				T result = caseAdditionalMetadataLocationType(additionalMetadataLocationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE: {
				AffiliationDescriptorType affiliationDescriptorType = (AffiliationDescriptorType)theEObject;
				T result = caseAffiliationDescriptorType(affiliationDescriptorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE: {
				AttributeAuthorityDescriptorType attributeAuthorityDescriptorType = (AttributeAuthorityDescriptorType)theEObject;
				T result = caseAttributeAuthorityDescriptorType(attributeAuthorityDescriptorType);
				if (result == null) result = caseRoleDescriptorType(attributeAuthorityDescriptorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE: {
				AttributeConsumingServiceType attributeConsumingServiceType = (AttributeConsumingServiceType)theEObject;
				T result = caseAttributeConsumingServiceType(attributeConsumingServiceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE: {
				AuthnAuthorityDescriptorType authnAuthorityDescriptorType = (AuthnAuthorityDescriptorType)theEObject;
				T result = caseAuthnAuthorityDescriptorType(authnAuthorityDescriptorType);
				if (result == null) result = caseRoleDescriptorType(authnAuthorityDescriptorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.CONTACT_TYPE: {
				ContactType contactType = (ContactType)theEObject;
				T result = caseContactType(contactType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.DOCUMENT_ROOT: {
				DocumentRoot documentRoot = (DocumentRoot)theEObject;
				T result = caseDocumentRoot(documentRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.ENDPOINT_TYPE: {
				EndpointType endpointType = (EndpointType)theEObject;
				T result = caseEndpointType(endpointType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE: {
				EntitiesDescriptorType entitiesDescriptorType = (EntitiesDescriptorType)theEObject;
				T result = caseEntitiesDescriptorType(entitiesDescriptorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE: {
				EntityDescriptorType entityDescriptorType = (EntityDescriptorType)theEObject;
				T result = caseEntityDescriptorType(entityDescriptorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.EXTENSIONS_TYPE: {
				ExtensionsType extensionsType = (ExtensionsType)theEObject;
				T result = caseExtensionsType(extensionsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE: {
				IDPSSODescriptorType idpssoDescriptorType = (IDPSSODescriptorType)theEObject;
				T result = caseIDPSSODescriptorType(idpssoDescriptorType);
				if (result == null) result = caseSSODescriptorType(idpssoDescriptorType);
				if (result == null) result = caseRoleDescriptorType(idpssoDescriptorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.INDEXED_ENDPOINT_TYPE: {
				IndexedEndpointType indexedEndpointType = (IndexedEndpointType)theEObject;
				T result = caseIndexedEndpointType(indexedEndpointType);
				if (result == null) result = caseEndpointType(indexedEndpointType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.LOCALIZED_NAME_TYPE: {
				LocalizedNameType localizedNameType = (LocalizedNameType)theEObject;
				T result = caseLocalizedNameType(localizedNameType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.LOCALIZED_URI_TYPE: {
				LocalizedURIType localizedURIType = (LocalizedURIType)theEObject;
				T result = caseLocalizedURIType(localizedURIType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.ORGANIZATION_TYPE: {
				OrganizationType organizationType = (OrganizationType)theEObject;
				T result = caseOrganizationType(organizationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.PDP_DESCRIPTOR_TYPE: {
				PDPDescriptorType pdpDescriptorType = (PDPDescriptorType)theEObject;
				T result = casePDPDescriptorType(pdpDescriptorType);
				if (result == null) result = caseRoleDescriptorType(pdpDescriptorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.REQUESTED_ATTRIBUTE_TYPE: {
				RequestedAttributeType requestedAttributeType = (RequestedAttributeType)theEObject;
				T result = caseRequestedAttributeType(requestedAttributeType);
				if (result == null) result = caseAttributeType(requestedAttributeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE: {
				RoleDescriptorType roleDescriptorType = (RoleDescriptorType)theEObject;
				T result = caseRoleDescriptorType(roleDescriptorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE: {
				SPSSODescriptorType spssoDescriptorType = (SPSSODescriptorType)theEObject;
				T result = caseSPSSODescriptorType(spssoDescriptorType);
				if (result == null) result = caseSSODescriptorType(spssoDescriptorType);
				if (result == null) result = caseRoleDescriptorType(spssoDescriptorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MetadataPackage.SSO_DESCRIPTOR_TYPE: {
				SSODescriptorType ssoDescriptorType = (SSODescriptorType)theEObject;
				T result = caseSSODescriptorType(ssoDescriptorType);
				if (result == null) result = caseRoleDescriptorType(ssoDescriptorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Additional Metadata Location Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Additional Metadata Location Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdditionalMetadataLocationType(AdditionalMetadataLocationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Affiliation Descriptor Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Affiliation Descriptor Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAffiliationDescriptorType(AffiliationDescriptorType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Authority Descriptor Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Authority Descriptor Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeAuthorityDescriptorType(AttributeAuthorityDescriptorType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Consuming Service Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Consuming Service Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeConsumingServiceType(AttributeConsumingServiceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Authn Authority Descriptor Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Authn Authority Descriptor Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAuthnAuthorityDescriptorType(AuthnAuthorityDescriptorType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Contact Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Contact Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContactType(ContactType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDocumentRoot(DocumentRoot object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Endpoint Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Endpoint Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEndpointType(EndpointType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entities Descriptor Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entities Descriptor Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntitiesDescriptorType(EntitiesDescriptorType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity Descriptor Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity Descriptor Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntityDescriptorType(EntityDescriptorType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Extensions Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Extensions Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExtensionsType(ExtensionsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IDPSSO Descriptor Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IDPSSO Descriptor Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIDPSSODescriptorType(IDPSSODescriptorType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Indexed Endpoint Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Indexed Endpoint Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIndexedEndpointType(IndexedEndpointType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Localized Name Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Localized Name Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLocalizedNameType(LocalizedNameType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Localized URI Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Localized URI Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLocalizedURIType(LocalizedURIType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Organization Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Organization Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOrganizationType(OrganizationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>PDP Descriptor Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>PDP Descriptor Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePDPDescriptorType(PDPDescriptorType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Requested Attribute Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Requested Attribute Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRequestedAttributeType(RequestedAttributeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Role Descriptor Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Role Descriptor Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRoleDescriptorType(RoleDescriptorType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>SPSSO Descriptor Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>SPSSO Descriptor Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSPSSODescriptorType(SPSSODescriptorType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>SSO Descriptor Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>SSO Descriptor Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSSODescriptorType(SSODescriptorType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Attribute Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Attribute Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAttributeType(AttributeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} //MetadataSwitch
