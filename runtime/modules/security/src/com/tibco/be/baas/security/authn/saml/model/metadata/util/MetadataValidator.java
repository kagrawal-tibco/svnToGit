/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.ecore.xml.type.util.XMLTypeValidator;

import com.tibco.be.baas.security.authn.saml.model.metadata.AdditionalMetadataLocationType;
import com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType;
import com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.ContactType;
import com.tibco.be.baas.security.authn.saml.model.metadata.ContactTypeType;
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
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage
 * @generated
 */
public class MetadataValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final MetadataValidator INSTANCE = new MetadataValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "com.tibco.be.baas.security.authn.saml.model.metadata";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * The cached base package validator.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XMLTypeValidator xmlTypeValidator;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetadataValidator() {
		super();
		xmlTypeValidator = XMLTypeValidator.INSTANCE;
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return MetadataPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case MetadataPackage.ADDITIONAL_METADATA_LOCATION_TYPE:
				return validateAdditionalMetadataLocationType((AdditionalMetadataLocationType)value, diagnostics, context);
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE:
				return validateAffiliationDescriptorType((AffiliationDescriptorType)value, diagnostics, context);
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE:
				return validateAttributeAuthorityDescriptorType((AttributeAuthorityDescriptorType)value, diagnostics, context);
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE:
				return validateAttributeConsumingServiceType((AttributeConsumingServiceType)value, diagnostics, context);
			case MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE:
				return validateAuthnAuthorityDescriptorType((AuthnAuthorityDescriptorType)value, diagnostics, context);
			case MetadataPackage.CONTACT_TYPE:
				return validateContactType((ContactType)value, diagnostics, context);
			case MetadataPackage.DOCUMENT_ROOT:
				return validateDocumentRoot((DocumentRoot)value, diagnostics, context);
			case MetadataPackage.ENDPOINT_TYPE:
				return validateEndpointType((EndpointType)value, diagnostics, context);
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE:
				return validateEntitiesDescriptorType((EntitiesDescriptorType)value, diagnostics, context);
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE:
				return validateEntityDescriptorType((EntityDescriptorType)value, diagnostics, context);
			case MetadataPackage.EXTENSIONS_TYPE:
				return validateExtensionsType((ExtensionsType)value, diagnostics, context);
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE:
				return validateIDPSSODescriptorType((IDPSSODescriptorType)value, diagnostics, context);
			case MetadataPackage.INDEXED_ENDPOINT_TYPE:
				return validateIndexedEndpointType((IndexedEndpointType)value, diagnostics, context);
			case MetadataPackage.LOCALIZED_NAME_TYPE:
				return validateLocalizedNameType((LocalizedNameType)value, diagnostics, context);
			case MetadataPackage.LOCALIZED_URI_TYPE:
				return validateLocalizedURIType((LocalizedURIType)value, diagnostics, context);
			case MetadataPackage.ORGANIZATION_TYPE:
				return validateOrganizationType((OrganizationType)value, diagnostics, context);
			case MetadataPackage.PDP_DESCRIPTOR_TYPE:
				return validatePDPDescriptorType((PDPDescriptorType)value, diagnostics, context);
			case MetadataPackage.REQUESTED_ATTRIBUTE_TYPE:
				return validateRequestedAttributeType((RequestedAttributeType)value, diagnostics, context);
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE:
				return validateRoleDescriptorType((RoleDescriptorType)value, diagnostics, context);
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE:
				return validateSPSSODescriptorType((SPSSODescriptorType)value, diagnostics, context);
			case MetadataPackage.SSO_DESCRIPTOR_TYPE:
				return validateSSODescriptorType((SSODescriptorType)value, diagnostics, context);
			case MetadataPackage.CONTACT_TYPE_TYPE:
				return validateContactTypeType((ContactTypeType)value, diagnostics, context);
			case MetadataPackage.ANY_URI_LIST_TYPE:
				return validateAnyURIListType((List<?>)value, diagnostics, context);
			case MetadataPackage.CONTACT_TYPE_TYPE_OBJECT:
				return validateContactTypeTypeObject((ContactTypeType)value, diagnostics, context);
			case MetadataPackage.ENTITY_ID_TYPE:
				return validateEntityIDType((String)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAdditionalMetadataLocationType(AdditionalMetadataLocationType additionalMetadataLocationType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(additionalMetadataLocationType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAffiliationDescriptorType(AffiliationDescriptorType affiliationDescriptorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(affiliationDescriptorType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttributeAuthorityDescriptorType(AttributeAuthorityDescriptorType attributeAuthorityDescriptorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(attributeAuthorityDescriptorType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttributeConsumingServiceType(AttributeConsumingServiceType attributeConsumingServiceType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(attributeConsumingServiceType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAuthnAuthorityDescriptorType(AuthnAuthorityDescriptorType authnAuthorityDescriptorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(authnAuthorityDescriptorType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateContactType(ContactType contactType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(contactType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDocumentRoot(DocumentRoot documentRoot, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(documentRoot, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEndpointType(EndpointType endpointType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(endpointType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEntitiesDescriptorType(EntitiesDescriptorType entitiesDescriptorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(entitiesDescriptorType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEntityDescriptorType(EntityDescriptorType entityDescriptorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(entityDescriptorType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExtensionsType(ExtensionsType extensionsType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(extensionsType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIDPSSODescriptorType(IDPSSODescriptorType idpssoDescriptorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(idpssoDescriptorType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIndexedEndpointType(IndexedEndpointType indexedEndpointType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(indexedEndpointType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLocalizedNameType(LocalizedNameType localizedNameType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(localizedNameType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLocalizedURIType(LocalizedURIType localizedURIType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(localizedURIType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOrganizationType(OrganizationType organizationType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(organizationType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePDPDescriptorType(PDPDescriptorType pdpDescriptorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(pdpDescriptorType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRequestedAttributeType(RequestedAttributeType requestedAttributeType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(requestedAttributeType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRoleDescriptorType(RoleDescriptorType roleDescriptorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(roleDescriptorType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSPSSODescriptorType(SPSSODescriptorType spssoDescriptorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(spssoDescriptorType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSSODescriptorType(SSODescriptorType ssoDescriptorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(ssoDescriptorType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateContactTypeType(ContactTypeType contactTypeType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAnyURIListType(List<?> anyURIListType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateAnyURIListType_ItemType(anyURIListType, diagnostics, context);
		return result;
	}

	/**
	 * Validates the ItemType constraint of '<em>Any URI List Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAnyURIListType_ItemType(List<?> anyURIListType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = true;
		for (Iterator<?> i = anyURIListType.iterator(); i.hasNext() && (result || diagnostics != null); ) {
			Object item = i.next();
			if (XMLTypePackage.Literals.ANY_URI.isInstance(item)) {
				result &= xmlTypeValidator.validateAnyURI((String)item, diagnostics, context);
			}
			else {
				result = false;
				reportDataValueTypeViolation(XMLTypePackage.Literals.ANY_URI, item, diagnostics, context);
			}
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateContactTypeTypeObject(ContactTypeType contactTypeTypeObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEntityIDType(String entityIDType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateEntityIDType_MaxLength(entityIDType, diagnostics, context);
		return result;
	}

	/**
	 * Validates the MaxLength constraint of '<em>Entity ID Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEntityIDType_MaxLength(String entityIDType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		int length = entityIDType.length();
		boolean result = length <= 1024;
		if (!result && diagnostics != null)
			reportMaxLengthViolation(MetadataPackage.Literals.ENTITY_ID_TYPE, entityIDType, length, 1024, diagnostics, context);
		return result;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //MetadataValidator
