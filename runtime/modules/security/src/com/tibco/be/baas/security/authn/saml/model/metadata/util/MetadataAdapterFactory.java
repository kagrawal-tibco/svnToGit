/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage
 * @generated
 */
public class MetadataAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static MetadataPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetadataAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = MetadataPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetadataSwitch<Adapter> modelSwitch =
		new MetadataSwitch<Adapter>() {
			@Override
			public Adapter caseAdditionalMetadataLocationType(AdditionalMetadataLocationType object) {
				return createAdditionalMetadataLocationTypeAdapter();
			}
			@Override
			public Adapter caseAffiliationDescriptorType(AffiliationDescriptorType object) {
				return createAffiliationDescriptorTypeAdapter();
			}
			@Override
			public Adapter caseAttributeAuthorityDescriptorType(AttributeAuthorityDescriptorType object) {
				return createAttributeAuthorityDescriptorTypeAdapter();
			}
			@Override
			public Adapter caseAttributeConsumingServiceType(AttributeConsumingServiceType object) {
				return createAttributeConsumingServiceTypeAdapter();
			}
			@Override
			public Adapter caseAuthnAuthorityDescriptorType(AuthnAuthorityDescriptorType object) {
				return createAuthnAuthorityDescriptorTypeAdapter();
			}
			@Override
			public Adapter caseContactType(ContactType object) {
				return createContactTypeAdapter();
			}
			@Override
			public Adapter caseDocumentRoot(DocumentRoot object) {
				return createDocumentRootAdapter();
			}
			@Override
			public Adapter caseEndpointType(EndpointType object) {
				return createEndpointTypeAdapter();
			}
			@Override
			public Adapter caseEntitiesDescriptorType(EntitiesDescriptorType object) {
				return createEntitiesDescriptorTypeAdapter();
			}
			@Override
			public Adapter caseEntityDescriptorType(EntityDescriptorType object) {
				return createEntityDescriptorTypeAdapter();
			}
			@Override
			public Adapter caseExtensionsType(ExtensionsType object) {
				return createExtensionsTypeAdapter();
			}
			@Override
			public Adapter caseIDPSSODescriptorType(IDPSSODescriptorType object) {
				return createIDPSSODescriptorTypeAdapter();
			}
			@Override
			public Adapter caseIndexedEndpointType(IndexedEndpointType object) {
				return createIndexedEndpointTypeAdapter();
			}
			@Override
			public Adapter caseLocalizedNameType(LocalizedNameType object) {
				return createLocalizedNameTypeAdapter();
			}
			@Override
			public Adapter caseLocalizedURIType(LocalizedURIType object) {
				return createLocalizedURITypeAdapter();
			}
			@Override
			public Adapter caseOrganizationType(OrganizationType object) {
				return createOrganizationTypeAdapter();
			}
			@Override
			public Adapter casePDPDescriptorType(PDPDescriptorType object) {
				return createPDPDescriptorTypeAdapter();
			}
			@Override
			public Adapter caseRequestedAttributeType(RequestedAttributeType object) {
				return createRequestedAttributeTypeAdapter();
			}
			@Override
			public Adapter caseRoleDescriptorType(RoleDescriptorType object) {
				return createRoleDescriptorTypeAdapter();
			}
			@Override
			public Adapter caseSPSSODescriptorType(SPSSODescriptorType object) {
				return createSPSSODescriptorTypeAdapter();
			}
			@Override
			public Adapter caseSSODescriptorType(SSODescriptorType object) {
				return createSSODescriptorTypeAdapter();
			}
			@Override
			public Adapter caseAttributeType(AttributeType object) {
				return createAttributeTypeAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AdditionalMetadataLocationType <em>Additional Metadata Location Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AdditionalMetadataLocationType
	 * @generated
	 */
	public Adapter createAdditionalMetadataLocationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType <em>Affiliation Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType
	 * @generated
	 */
	public Adapter createAffiliationDescriptorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType <em>Attribute Authority Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType
	 * @generated
	 */
	public Adapter createAttributeAuthorityDescriptorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType <em>Attribute Consuming Service Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType
	 * @generated
	 */
	public Adapter createAttributeConsumingServiceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType <em>Authn Authority Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType
	 * @generated
	 */
	public Adapter createAuthnAuthorityDescriptorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ContactType <em>Contact Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ContactType
	 * @generated
	 */
	public Adapter createContactTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.DocumentRoot
	 * @generated
	 */
	public Adapter createDocumentRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType <em>Endpoint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType
	 * @generated
	 */
	public Adapter createEndpointTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType <em>Entities Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType
	 * @generated
	 */
	public Adapter createEntitiesDescriptorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType <em>Entity Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType
	 * @generated
	 */
	public Adapter createEntityDescriptorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.ExtensionsType <em>Extensions Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.ExtensionsType
	 * @generated
	 */
	public Adapter createExtensionsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType <em>IDPSSO Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType
	 * @generated
	 */
	public Adapter createIDPSSODescriptorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.IndexedEndpointType <em>Indexed Endpoint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.IndexedEndpointType
	 * @generated
	 */
	public Adapter createIndexedEndpointTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedNameType <em>Localized Name Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedNameType
	 * @generated
	 */
	public Adapter createLocalizedNameTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedURIType <em>Localized URI Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedURIType
	 * @generated
	 */
	public Adapter createLocalizedURITypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType <em>Organization Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType
	 * @generated
	 */
	public Adapter createOrganizationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType <em>PDP Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType
	 * @generated
	 */
	public Adapter createPDPDescriptorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RequestedAttributeType <em>Requested Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.RequestedAttributeType
	 * @generated
	 */
	public Adapter createRequestedAttributeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType <em>Role Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType
	 * @generated
	 */
	public Adapter createRoleDescriptorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType <em>SPSSO Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType
	 * @generated
	 */
	public Adapter createSPSSODescriptorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType <em>SSO Descriptor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType
	 * @generated
	 */
	public Adapter createSSODescriptorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType
	 * @generated
	 */
	public Adapter createAttributeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //MetadataAdapterFactory
