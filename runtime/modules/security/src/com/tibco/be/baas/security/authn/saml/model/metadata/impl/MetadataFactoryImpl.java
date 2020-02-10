/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

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
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataFactory;
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;
import com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType;
import com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.RequestedAttributeType;
import com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MetadataFactoryImpl extends EFactoryImpl implements MetadataFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static MetadataFactory init() {
		try {
			MetadataFactory theMetadataFactory = (MetadataFactory)EPackage.Registry.INSTANCE.getEFactory("urn:oasis:names:tc:SAML:2.0:metadata"); 
			if (theMetadataFactory != null) {
				return theMetadataFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MetadataFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetadataFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case MetadataPackage.ADDITIONAL_METADATA_LOCATION_TYPE: return createAdditionalMetadataLocationType();
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE: return createAffiliationDescriptorType();
			case MetadataPackage.ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE: return createAttributeAuthorityDescriptorType();
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE: return createAttributeConsumingServiceType();
			case MetadataPackage.AUTHN_AUTHORITY_DESCRIPTOR_TYPE: return createAuthnAuthorityDescriptorType();
			case MetadataPackage.CONTACT_TYPE: return createContactType();
			case MetadataPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case MetadataPackage.ENDPOINT_TYPE: return createEndpointType();
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE: return createEntitiesDescriptorType();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE: return createEntityDescriptorType();
			case MetadataPackage.EXTENSIONS_TYPE: return createExtensionsType();
			case MetadataPackage.IDPSSO_DESCRIPTOR_TYPE: return createIDPSSODescriptorType();
			case MetadataPackage.INDEXED_ENDPOINT_TYPE: return createIndexedEndpointType();
			case MetadataPackage.LOCALIZED_NAME_TYPE: return createLocalizedNameType();
			case MetadataPackage.LOCALIZED_URI_TYPE: return createLocalizedURIType();
			case MetadataPackage.ORGANIZATION_TYPE: return createOrganizationType();
			case MetadataPackage.PDP_DESCRIPTOR_TYPE: return createPDPDescriptorType();
			case MetadataPackage.REQUESTED_ATTRIBUTE_TYPE: return createRequestedAttributeType();
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE: return createSPSSODescriptorType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case MetadataPackage.CONTACT_TYPE_TYPE:
				return createContactTypeTypeFromString(eDataType, initialValue);
			case MetadataPackage.ANY_URI_LIST_TYPE:
				return createAnyURIListTypeFromString(eDataType, initialValue);
			case MetadataPackage.CONTACT_TYPE_TYPE_OBJECT:
				return createContactTypeTypeObjectFromString(eDataType, initialValue);
			case MetadataPackage.ENTITY_ID_TYPE:
				return createEntityIDTypeFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case MetadataPackage.CONTACT_TYPE_TYPE:
				return convertContactTypeTypeToString(eDataType, instanceValue);
			case MetadataPackage.ANY_URI_LIST_TYPE:
				return convertAnyURIListTypeToString(eDataType, instanceValue);
			case MetadataPackage.CONTACT_TYPE_TYPE_OBJECT:
				return convertContactTypeTypeObjectToString(eDataType, instanceValue);
			case MetadataPackage.ENTITY_ID_TYPE:
				return convertEntityIDTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdditionalMetadataLocationType createAdditionalMetadataLocationType() {
		AdditionalMetadataLocationTypeImpl additionalMetadataLocationType = new AdditionalMetadataLocationTypeImpl();
		return additionalMetadataLocationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AffiliationDescriptorType createAffiliationDescriptorType() {
		AffiliationDescriptorTypeImpl affiliationDescriptorType = new AffiliationDescriptorTypeImpl();
		return affiliationDescriptorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeAuthorityDescriptorType createAttributeAuthorityDescriptorType() {
		AttributeAuthorityDescriptorTypeImpl attributeAuthorityDescriptorType = new AttributeAuthorityDescriptorTypeImpl();
		return attributeAuthorityDescriptorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeConsumingServiceType createAttributeConsumingServiceType() {
		AttributeConsumingServiceTypeImpl attributeConsumingServiceType = new AttributeConsumingServiceTypeImpl();
		return attributeConsumingServiceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AuthnAuthorityDescriptorType createAuthnAuthorityDescriptorType() {
		AuthnAuthorityDescriptorTypeImpl authnAuthorityDescriptorType = new AuthnAuthorityDescriptorTypeImpl();
		return authnAuthorityDescriptorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContactType createContactType() {
		ContactTypeImpl contactType = new ContactTypeImpl();
		return contactType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndpointType createEndpointType() {
		EndpointTypeImpl endpointType = new EndpointTypeImpl();
		return endpointType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntitiesDescriptorType createEntitiesDescriptorType() {
		EntitiesDescriptorTypeImpl entitiesDescriptorType = new EntitiesDescriptorTypeImpl();
		return entitiesDescriptorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntityDescriptorType createEntityDescriptorType() {
		EntityDescriptorTypeImpl entityDescriptorType = new EntityDescriptorTypeImpl();
		return entityDescriptorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtensionsType createExtensionsType() {
		ExtensionsTypeImpl extensionsType = new ExtensionsTypeImpl();
		return extensionsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IDPSSODescriptorType createIDPSSODescriptorType() {
		IDPSSODescriptorTypeImpl idpssoDescriptorType = new IDPSSODescriptorTypeImpl();
		return idpssoDescriptorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndexedEndpointType createIndexedEndpointType() {
		IndexedEndpointTypeImpl indexedEndpointType = new IndexedEndpointTypeImpl();
		return indexedEndpointType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalizedNameType createLocalizedNameType() {
		LocalizedNameTypeImpl localizedNameType = new LocalizedNameTypeImpl();
		return localizedNameType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalizedURIType createLocalizedURIType() {
		LocalizedURITypeImpl localizedURIType = new LocalizedURITypeImpl();
		return localizedURIType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrganizationType createOrganizationType() {
		OrganizationTypeImpl organizationType = new OrganizationTypeImpl();
		return organizationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PDPDescriptorType createPDPDescriptorType() {
		PDPDescriptorTypeImpl pdpDescriptorType = new PDPDescriptorTypeImpl();
		return pdpDescriptorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequestedAttributeType createRequestedAttributeType() {
		RequestedAttributeTypeImpl requestedAttributeType = new RequestedAttributeTypeImpl();
		return requestedAttributeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SPSSODescriptorType createSPSSODescriptorType() {
		SPSSODescriptorTypeImpl spssoDescriptorType = new SPSSODescriptorTypeImpl();
		return spssoDescriptorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContactTypeType createContactTypeTypeFromString(EDataType eDataType, String initialValue) {
		ContactTypeType result = ContactTypeType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertContactTypeTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<String> createAnyURIListTypeFromString(EDataType eDataType, String initialValue) {
		if (initialValue == null) return null;
		List<String> result = new ArrayList<String>();
		for (StringTokenizer stringTokenizer = new StringTokenizer(initialValue); stringTokenizer.hasMoreTokens(); ) {
			String item = stringTokenizer.nextToken();
			result.add((String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.ANY_URI, item));
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAnyURIListTypeToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue == null) return null;
		List<?> list = (List<?>)instanceValue;
		if (list.isEmpty()) return "";
		StringBuffer result = new StringBuffer();
		for (Object item : list) {
			result.append(XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.ANY_URI, item));
			result.append(' ');
		}
		return result.substring(0, result.length() - 1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContactTypeType createContactTypeTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createContactTypeTypeFromString(MetadataPackage.Literals.CONTACT_TYPE_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertContactTypeTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertContactTypeTypeToString(MetadataPackage.Literals.CONTACT_TYPE_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createEntityIDTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.ANY_URI, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEntityIDTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.ANY_URI, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetadataPackage getMetadataPackage() {
		return (MetadataPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static MetadataPackage getPackage() {
		return MetadataPackage.eINSTANCE;
	}

} //MetadataFactoryImpl
