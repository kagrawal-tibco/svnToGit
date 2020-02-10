/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata.impl;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage;
import com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl;
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
import com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.SSODescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.util.MetadataValidator;
import com.tibco.be.baas.security.authn.saml.model.namespace.NamespacePackage;
import com.tibco.be.baas.security.authn.saml.model.namespace.impl.NamespacePackageImpl;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage;
import com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MetadataPackageImpl extends EPackageImpl implements MetadataPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass additionalMetadataLocationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass affiliationDescriptorTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeAuthorityDescriptorTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeConsumingServiceTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass authnAuthorityDescriptorTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contactTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass endpointTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entitiesDescriptorTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityDescriptorTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass extensionsTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass idpssoDescriptorTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass indexedEndpointTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass localizedNameTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass localizedURITypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass organizationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pdpDescriptorTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass requestedAttributeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass roleDescriptorTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass spssoDescriptorTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ssoDescriptorTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum contactTypeTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType anyURIListTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType contactTypeTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType entityIDTypeEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MetadataPackageImpl() {
		super(eNS_URI, MetadataFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MetadataPackage init() {
		if (isInited) return (MetadataPackage)EPackage.Registry.INSTANCE.getEPackage(MetadataPackage.eNS_URI);

		// Obtain or create and register package
		MetadataPackageImpl theMetadataPackage = (MetadataPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof MetadataPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new MetadataPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		AssertionPackageImpl theAssertionPackage = (AssertionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AssertionPackage.eNS_URI) instanceof AssertionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AssertionPackage.eNS_URI) : AssertionPackage.eINSTANCE);
		NamespacePackageImpl theNamespacePackage = (NamespacePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(NamespacePackage.eNS_URI) instanceof NamespacePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(NamespacePackage.eNS_URI) : NamespacePackage.eINSTANCE);
		PolicyPackageImpl thePolicyPackage = (PolicyPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(PolicyPackage.eNS_URI) instanceof PolicyPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(PolicyPackage.eNS_URI) : PolicyPackage.eINSTANCE);

		// Create package meta-data objects
		theMetadataPackage.createPackageContents();
		theAssertionPackage.createPackageContents();
		theNamespacePackage.createPackageContents();
		thePolicyPackage.createPackageContents();

		// Initialize created meta-data
		theMetadataPackage.initializePackageContents();
		theAssertionPackage.initializePackageContents();
		theNamespacePackage.initializePackageContents();
		thePolicyPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theMetadataPackage, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return MetadataValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theMetadataPackage.freeze();

		return theMetadataPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAdditionalMetadataLocationType() {
		return additionalMetadataLocationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdditionalMetadataLocationType_Value() {
		return (EAttribute)additionalMetadataLocationTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdditionalMetadataLocationType_Namespace() {
		return (EAttribute)additionalMetadataLocationTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAffiliationDescriptorType() {
		return affiliationDescriptorTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAffiliationDescriptorType_Extensions() {
		return (EReference)affiliationDescriptorTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAffiliationDescriptorType_AffiliateMember() {
		return (EAttribute)affiliationDescriptorTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAffiliationDescriptorType_AffiliationOwnerID() {
		return (EAttribute)affiliationDescriptorTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAffiliationDescriptorType_CacheDuration() {
		return (EAttribute)affiliationDescriptorTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAffiliationDescriptorType_ID() {
		return (EAttribute)affiliationDescriptorTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAffiliationDescriptorType_ValidUntil() {
		return (EAttribute)affiliationDescriptorTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAffiliationDescriptorType_AnyAttribute() {
		return (EAttribute)affiliationDescriptorTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeAuthorityDescriptorType() {
		return attributeAuthorityDescriptorTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeAuthorityDescriptorType_AttributeService() {
		return (EReference)attributeAuthorityDescriptorTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeAuthorityDescriptorType_AssertionIDRequestService() {
		return (EReference)attributeAuthorityDescriptorTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeAuthorityDescriptorType_NameIDFormat() {
		return (EAttribute)attributeAuthorityDescriptorTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeAuthorityDescriptorType_AttributeProfile() {
		return (EAttribute)attributeAuthorityDescriptorTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeAuthorityDescriptorType_Attribute() {
		return (EReference)attributeAuthorityDescriptorTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeConsumingServiceType() {
		return attributeConsumingServiceTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeConsumingServiceType_ServiceName() {
		return (EReference)attributeConsumingServiceTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeConsumingServiceType_ServiceDescription() {
		return (EReference)attributeConsumingServiceTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeConsumingServiceType_RequestedAttribute() {
		return (EReference)attributeConsumingServiceTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeConsumingServiceType_Index() {
		return (EAttribute)attributeConsumingServiceTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttributeConsumingServiceType_IsDefault() {
		return (EAttribute)attributeConsumingServiceTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAuthnAuthorityDescriptorType() {
		return authnAuthorityDescriptorTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthnAuthorityDescriptorType_AuthnQueryService() {
		return (EReference)authnAuthorityDescriptorTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthnAuthorityDescriptorType_AssertionIDRequestService() {
		return (EReference)authnAuthorityDescriptorTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthnAuthorityDescriptorType_NameIDFormat() {
		return (EAttribute)authnAuthorityDescriptorTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getContactType() {
		return contactTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getContactType_Extensions() {
		return (EReference)contactTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContactType_Company() {
		return (EAttribute)contactTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContactType_GivenName() {
		return (EAttribute)contactTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContactType_SurName() {
		return (EAttribute)contactTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContactType_EmailAddress() {
		return (EAttribute)contactTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContactType_TelephoneNumber() {
		return (EAttribute)contactTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContactType_ContactType() {
		return (EAttribute)contactTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContactType_AnyAttribute() {
		return (EAttribute)contactTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDocumentRoot() {
		return documentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Mixed() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XMLNSPrefixMap() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XSISchemaLocation() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AdditionalMetadataLocation() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_AffiliateMember() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AffiliationDescriptor() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ArtifactResolutionService() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AssertionConsumerService() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AssertionIDRequestService() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AttributeAuthorityDescriptor() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AttributeConsumingService() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_AttributeProfile() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AttributeService() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AuthnAuthorityDescriptor() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AuthnQueryService() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AuthzService() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Company() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ContactPerson() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_EmailAddress() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_EntitiesDescriptor() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_EntityDescriptor() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Extensions() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_GivenName() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_IDPSSODescriptor() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ManageNameIDService() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_NameIDFormat() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_NameIDMappingService() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Organization() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_OrganizationDisplayName() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_OrganizationName() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_OrganizationURL() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_PDPDescriptor() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(31);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_RequestedAttribute() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(32);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_RoleDescriptor() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(33);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ServiceDescription() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(34);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ServiceName() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(35);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_SingleLogoutService() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(36);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_SingleSignOnService() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(37);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_SPSSODescriptor() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(38);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_SurName() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(39);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_TelephoneNumber() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(40);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEndpointType() {
		return endpointTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEndpointType_Any() {
		return (EAttribute)endpointTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEndpointType_Binding() {
		return (EAttribute)endpointTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEndpointType_Location() {
		return (EAttribute)endpointTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEndpointType_ResponseLocation() {
		return (EAttribute)endpointTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEndpointType_AnyAttribute() {
		return (EAttribute)endpointTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEntitiesDescriptorType() {
		return entitiesDescriptorTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntitiesDescriptorType_Extensions() {
		return (EReference)entitiesDescriptorTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntitiesDescriptorType_Group() {
		return (EAttribute)entitiesDescriptorTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntitiesDescriptorType_EntityDescriptor() {
		return (EReference)entitiesDescriptorTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntitiesDescriptorType_EntitiesDescriptor() {
		return (EReference)entitiesDescriptorTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntitiesDescriptorType_CacheDuration() {
		return (EAttribute)entitiesDescriptorTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntitiesDescriptorType_ID() {
		return (EAttribute)entitiesDescriptorTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntitiesDescriptorType_Name() {
		return (EAttribute)entitiesDescriptorTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntitiesDescriptorType_ValidUntil() {
		return (EAttribute)entitiesDescriptorTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEntityDescriptorType() {
		return entityDescriptorTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityDescriptorType_Extensions() {
		return (EReference)entityDescriptorTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntityDescriptorType_Group() {
		return (EAttribute)entityDescriptorTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityDescriptorType_RoleDescriptor() {
		return (EReference)entityDescriptorTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityDescriptorType_IDPSSODescriptor() {
		return (EReference)entityDescriptorTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityDescriptorType_SPSSODescriptor() {
		return (EReference)entityDescriptorTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityDescriptorType_AuthnAuthorityDescriptor() {
		return (EReference)entityDescriptorTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityDescriptorType_AttributeAuthorityDescriptor() {
		return (EReference)entityDescriptorTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityDescriptorType_PDPDescriptor() {
		return (EReference)entityDescriptorTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityDescriptorType_AffiliationDescriptor() {
		return (EReference)entityDescriptorTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityDescriptorType_Organization() {
		return (EReference)entityDescriptorTypeEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityDescriptorType_ContactPerson() {
		return (EReference)entityDescriptorTypeEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityDescriptorType_AdditionalMetadataLocation() {
		return (EReference)entityDescriptorTypeEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntityDescriptorType_CacheDuration() {
		return (EAttribute)entityDescriptorTypeEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntityDescriptorType_EntityID() {
		return (EAttribute)entityDescriptorTypeEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntityDescriptorType_ID() {
		return (EAttribute)entityDescriptorTypeEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntityDescriptorType_ValidUntil() {
		return (EAttribute)entityDescriptorTypeEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntityDescriptorType_AnyAttribute() {
		return (EAttribute)entityDescriptorTypeEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExtensionsType() {
		return extensionsTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExtensionsType_Any() {
		return (EAttribute)extensionsTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIDPSSODescriptorType() {
		return idpssoDescriptorTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIDPSSODescriptorType_SingleSignOnService() {
		return (EReference)idpssoDescriptorTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIDPSSODescriptorType_NameIDMappingService() {
		return (EReference)idpssoDescriptorTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIDPSSODescriptorType_AssertionIDRequestService() {
		return (EReference)idpssoDescriptorTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIDPSSODescriptorType_AttributeProfile() {
		return (EAttribute)idpssoDescriptorTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIDPSSODescriptorType_Attribute() {
		return (EReference)idpssoDescriptorTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIDPSSODescriptorType_WantAuthnRequestsSigned() {
		return (EAttribute)idpssoDescriptorTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIndexedEndpointType() {
		return indexedEndpointTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIndexedEndpointType_Index() {
		return (EAttribute)indexedEndpointTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIndexedEndpointType_IsDefault() {
		return (EAttribute)indexedEndpointTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLocalizedNameType() {
		return localizedNameTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocalizedNameType_Value() {
		return (EAttribute)localizedNameTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocalizedNameType_Lang() {
		return (EAttribute)localizedNameTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLocalizedURIType() {
		return localizedURITypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocalizedURIType_Value() {
		return (EAttribute)localizedURITypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocalizedURIType_Lang() {
		return (EAttribute)localizedURITypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOrganizationType() {
		return organizationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOrganizationType_Extensions() {
		return (EReference)organizationTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOrganizationType_OrganizationName() {
		return (EReference)organizationTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOrganizationType_OrganizationDisplayName() {
		return (EReference)organizationTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOrganizationType_OrganizationURL() {
		return (EReference)organizationTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOrganizationType_AnyAttribute() {
		return (EAttribute)organizationTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPDPDescriptorType() {
		return pdpDescriptorTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPDPDescriptorType_AuthzService() {
		return (EReference)pdpDescriptorTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPDPDescriptorType_AssertionIDRequestService() {
		return (EReference)pdpDescriptorTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPDPDescriptorType_NameIDFormat() {
		return (EAttribute)pdpDescriptorTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRequestedAttributeType() {
		return requestedAttributeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRequestedAttributeType_IsRequired() {
		return (EAttribute)requestedAttributeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRoleDescriptorType() {
		return roleDescriptorTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRoleDescriptorType_Extensions() {
		return (EReference)roleDescriptorTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRoleDescriptorType_Organization() {
		return (EReference)roleDescriptorTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRoleDescriptorType_ContactPerson() {
		return (EReference)roleDescriptorTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRoleDescriptorType_CacheDuration() {
		return (EAttribute)roleDescriptorTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRoleDescriptorType_ErrorURL() {
		return (EAttribute)roleDescriptorTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRoleDescriptorType_ID() {
		return (EAttribute)roleDescriptorTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRoleDescriptorType_ProtocolSupportEnumeration() {
		return (EAttribute)roleDescriptorTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRoleDescriptorType_ValidUntil() {
		return (EAttribute)roleDescriptorTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRoleDescriptorType_AnyAttribute() {
		return (EAttribute)roleDescriptorTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSPSSODescriptorType() {
		return spssoDescriptorTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSPSSODescriptorType_AssertionConsumerService() {
		return (EReference)spssoDescriptorTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSPSSODescriptorType_AttributeConsumingService() {
		return (EReference)spssoDescriptorTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSPSSODescriptorType_AuthnPolicy() {
		return (EReference)spssoDescriptorTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSPSSODescriptorType_AuthnRequestsSigned() {
		return (EAttribute)spssoDescriptorTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSPSSODescriptorType_WantAssertionsSigned() {
		return (EAttribute)spssoDescriptorTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSSODescriptorType() {
		return ssoDescriptorTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSSODescriptorType_ArtifactResolutionService() {
		return (EReference)ssoDescriptorTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSSODescriptorType_SingleLogoutService() {
		return (EReference)ssoDescriptorTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSSODescriptorType_ManageNameIDService() {
		return (EReference)ssoDescriptorTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSSODescriptorType_NameIDFormat() {
		return (EAttribute)ssoDescriptorTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getContactTypeType() {
		return contactTypeTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getAnyURIListType() {
		return anyURIListTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getContactTypeTypeObject() {
		return contactTypeTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getEntityIDType() {
		return entityIDTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetadataFactory getMetadataFactory() {
		return (MetadataFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		additionalMetadataLocationTypeEClass = createEClass(ADDITIONAL_METADATA_LOCATION_TYPE);
		createEAttribute(additionalMetadataLocationTypeEClass, ADDITIONAL_METADATA_LOCATION_TYPE__VALUE);
		createEAttribute(additionalMetadataLocationTypeEClass, ADDITIONAL_METADATA_LOCATION_TYPE__NAMESPACE);

		affiliationDescriptorTypeEClass = createEClass(AFFILIATION_DESCRIPTOR_TYPE);
		createEReference(affiliationDescriptorTypeEClass, AFFILIATION_DESCRIPTOR_TYPE__EXTENSIONS);
		createEAttribute(affiliationDescriptorTypeEClass, AFFILIATION_DESCRIPTOR_TYPE__AFFILIATE_MEMBER);
		createEAttribute(affiliationDescriptorTypeEClass, AFFILIATION_DESCRIPTOR_TYPE__AFFILIATION_OWNER_ID);
		createEAttribute(affiliationDescriptorTypeEClass, AFFILIATION_DESCRIPTOR_TYPE__CACHE_DURATION);
		createEAttribute(affiliationDescriptorTypeEClass, AFFILIATION_DESCRIPTOR_TYPE__ID);
		createEAttribute(affiliationDescriptorTypeEClass, AFFILIATION_DESCRIPTOR_TYPE__VALID_UNTIL);
		createEAttribute(affiliationDescriptorTypeEClass, AFFILIATION_DESCRIPTOR_TYPE__ANY_ATTRIBUTE);

		attributeAuthorityDescriptorTypeEClass = createEClass(ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE);
		createEReference(attributeAuthorityDescriptorTypeEClass, ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE_SERVICE);
		createEReference(attributeAuthorityDescriptorTypeEClass, ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE);
		createEAttribute(attributeAuthorityDescriptorTypeEClass, ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__NAME_ID_FORMAT);
		createEAttribute(attributeAuthorityDescriptorTypeEClass, ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE_PROFILE);
		createEReference(attributeAuthorityDescriptorTypeEClass, ATTRIBUTE_AUTHORITY_DESCRIPTOR_TYPE__ATTRIBUTE);

		attributeConsumingServiceTypeEClass = createEClass(ATTRIBUTE_CONSUMING_SERVICE_TYPE);
		createEReference(attributeConsumingServiceTypeEClass, ATTRIBUTE_CONSUMING_SERVICE_TYPE__SERVICE_NAME);
		createEReference(attributeConsumingServiceTypeEClass, ATTRIBUTE_CONSUMING_SERVICE_TYPE__SERVICE_DESCRIPTION);
		createEReference(attributeConsumingServiceTypeEClass, ATTRIBUTE_CONSUMING_SERVICE_TYPE__REQUESTED_ATTRIBUTE);
		createEAttribute(attributeConsumingServiceTypeEClass, ATTRIBUTE_CONSUMING_SERVICE_TYPE__INDEX);
		createEAttribute(attributeConsumingServiceTypeEClass, ATTRIBUTE_CONSUMING_SERVICE_TYPE__IS_DEFAULT);

		authnAuthorityDescriptorTypeEClass = createEClass(AUTHN_AUTHORITY_DESCRIPTOR_TYPE);
		createEReference(authnAuthorityDescriptorTypeEClass, AUTHN_AUTHORITY_DESCRIPTOR_TYPE__AUTHN_QUERY_SERVICE);
		createEReference(authnAuthorityDescriptorTypeEClass, AUTHN_AUTHORITY_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE);
		createEAttribute(authnAuthorityDescriptorTypeEClass, AUTHN_AUTHORITY_DESCRIPTOR_TYPE__NAME_ID_FORMAT);

		contactTypeEClass = createEClass(CONTACT_TYPE);
		createEReference(contactTypeEClass, CONTACT_TYPE__EXTENSIONS);
		createEAttribute(contactTypeEClass, CONTACT_TYPE__COMPANY);
		createEAttribute(contactTypeEClass, CONTACT_TYPE__GIVEN_NAME);
		createEAttribute(contactTypeEClass, CONTACT_TYPE__SUR_NAME);
		createEAttribute(contactTypeEClass, CONTACT_TYPE__EMAIL_ADDRESS);
		createEAttribute(contactTypeEClass, CONTACT_TYPE__TELEPHONE_NUMBER);
		createEAttribute(contactTypeEClass, CONTACT_TYPE__CONTACT_TYPE);
		createEAttribute(contactTypeEClass, CONTACT_TYPE__ANY_ATTRIBUTE);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ADDITIONAL_METADATA_LOCATION);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__AFFILIATE_MEMBER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__AFFILIATION_DESCRIPTOR);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ARTIFACT_RESOLUTION_SERVICE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ASSERTION_CONSUMER_SERVICE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ASSERTION_ID_REQUEST_SERVICE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ATTRIBUTE_AUTHORITY_DESCRIPTOR);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ATTRIBUTE_CONSUMING_SERVICE);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__ATTRIBUTE_PROFILE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ATTRIBUTE_SERVICE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__AUTHN_AUTHORITY_DESCRIPTOR);
		createEReference(documentRootEClass, DOCUMENT_ROOT__AUTHN_QUERY_SERVICE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__AUTHZ_SERVICE);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__COMPANY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__CONTACT_PERSON);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__EMAIL_ADDRESS);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ENTITIES_DESCRIPTOR);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ENTITY_DESCRIPTOR);
		createEReference(documentRootEClass, DOCUMENT_ROOT__EXTENSIONS);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__GIVEN_NAME);
		createEReference(documentRootEClass, DOCUMENT_ROOT__IDPSSO_DESCRIPTOR);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MANAGE_NAME_ID_SERVICE);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__NAME_ID_FORMAT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__NAME_ID_MAPPING_SERVICE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ORGANIZATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ORGANIZATION_DISPLAY_NAME);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ORGANIZATION_NAME);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ORGANIZATION_URL);
		createEReference(documentRootEClass, DOCUMENT_ROOT__PDP_DESCRIPTOR);
		createEReference(documentRootEClass, DOCUMENT_ROOT__REQUESTED_ATTRIBUTE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ROLE_DESCRIPTOR);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SERVICE_DESCRIPTION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SERVICE_NAME);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SINGLE_LOGOUT_SERVICE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SINGLE_SIGN_ON_SERVICE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SPSSO_DESCRIPTOR);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__SUR_NAME);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__TELEPHONE_NUMBER);

		endpointTypeEClass = createEClass(ENDPOINT_TYPE);
		createEAttribute(endpointTypeEClass, ENDPOINT_TYPE__ANY);
		createEAttribute(endpointTypeEClass, ENDPOINT_TYPE__BINDING);
		createEAttribute(endpointTypeEClass, ENDPOINT_TYPE__LOCATION);
		createEAttribute(endpointTypeEClass, ENDPOINT_TYPE__RESPONSE_LOCATION);
		createEAttribute(endpointTypeEClass, ENDPOINT_TYPE__ANY_ATTRIBUTE);

		entitiesDescriptorTypeEClass = createEClass(ENTITIES_DESCRIPTOR_TYPE);
		createEReference(entitiesDescriptorTypeEClass, ENTITIES_DESCRIPTOR_TYPE__EXTENSIONS);
		createEAttribute(entitiesDescriptorTypeEClass, ENTITIES_DESCRIPTOR_TYPE__GROUP);
		createEReference(entitiesDescriptorTypeEClass, ENTITIES_DESCRIPTOR_TYPE__ENTITY_DESCRIPTOR);
		createEReference(entitiesDescriptorTypeEClass, ENTITIES_DESCRIPTOR_TYPE__ENTITIES_DESCRIPTOR);
		createEAttribute(entitiesDescriptorTypeEClass, ENTITIES_DESCRIPTOR_TYPE__CACHE_DURATION);
		createEAttribute(entitiesDescriptorTypeEClass, ENTITIES_DESCRIPTOR_TYPE__ID);
		createEAttribute(entitiesDescriptorTypeEClass, ENTITIES_DESCRIPTOR_TYPE__NAME);
		createEAttribute(entitiesDescriptorTypeEClass, ENTITIES_DESCRIPTOR_TYPE__VALID_UNTIL);

		entityDescriptorTypeEClass = createEClass(ENTITY_DESCRIPTOR_TYPE);
		createEReference(entityDescriptorTypeEClass, ENTITY_DESCRIPTOR_TYPE__EXTENSIONS);
		createEAttribute(entityDescriptorTypeEClass, ENTITY_DESCRIPTOR_TYPE__GROUP);
		createEReference(entityDescriptorTypeEClass, ENTITY_DESCRIPTOR_TYPE__ROLE_DESCRIPTOR);
		createEReference(entityDescriptorTypeEClass, ENTITY_DESCRIPTOR_TYPE__IDPSSO_DESCRIPTOR);
		createEReference(entityDescriptorTypeEClass, ENTITY_DESCRIPTOR_TYPE__SPSSO_DESCRIPTOR);
		createEReference(entityDescriptorTypeEClass, ENTITY_DESCRIPTOR_TYPE__AUTHN_AUTHORITY_DESCRIPTOR);
		createEReference(entityDescriptorTypeEClass, ENTITY_DESCRIPTOR_TYPE__ATTRIBUTE_AUTHORITY_DESCRIPTOR);
		createEReference(entityDescriptorTypeEClass, ENTITY_DESCRIPTOR_TYPE__PDP_DESCRIPTOR);
		createEReference(entityDescriptorTypeEClass, ENTITY_DESCRIPTOR_TYPE__AFFILIATION_DESCRIPTOR);
		createEReference(entityDescriptorTypeEClass, ENTITY_DESCRIPTOR_TYPE__ORGANIZATION);
		createEReference(entityDescriptorTypeEClass, ENTITY_DESCRIPTOR_TYPE__CONTACT_PERSON);
		createEReference(entityDescriptorTypeEClass, ENTITY_DESCRIPTOR_TYPE__ADDITIONAL_METADATA_LOCATION);
		createEAttribute(entityDescriptorTypeEClass, ENTITY_DESCRIPTOR_TYPE__CACHE_DURATION);
		createEAttribute(entityDescriptorTypeEClass, ENTITY_DESCRIPTOR_TYPE__ENTITY_ID);
		createEAttribute(entityDescriptorTypeEClass, ENTITY_DESCRIPTOR_TYPE__ID);
		createEAttribute(entityDescriptorTypeEClass, ENTITY_DESCRIPTOR_TYPE__VALID_UNTIL);
		createEAttribute(entityDescriptorTypeEClass, ENTITY_DESCRIPTOR_TYPE__ANY_ATTRIBUTE);

		extensionsTypeEClass = createEClass(EXTENSIONS_TYPE);
		createEAttribute(extensionsTypeEClass, EXTENSIONS_TYPE__ANY);

		idpssoDescriptorTypeEClass = createEClass(IDPSSO_DESCRIPTOR_TYPE);
		createEReference(idpssoDescriptorTypeEClass, IDPSSO_DESCRIPTOR_TYPE__SINGLE_SIGN_ON_SERVICE);
		createEReference(idpssoDescriptorTypeEClass, IDPSSO_DESCRIPTOR_TYPE__NAME_ID_MAPPING_SERVICE);
		createEReference(idpssoDescriptorTypeEClass, IDPSSO_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE);
		createEAttribute(idpssoDescriptorTypeEClass, IDPSSO_DESCRIPTOR_TYPE__ATTRIBUTE_PROFILE);
		createEReference(idpssoDescriptorTypeEClass, IDPSSO_DESCRIPTOR_TYPE__ATTRIBUTE);
		createEAttribute(idpssoDescriptorTypeEClass, IDPSSO_DESCRIPTOR_TYPE__WANT_AUTHN_REQUESTS_SIGNED);

		indexedEndpointTypeEClass = createEClass(INDEXED_ENDPOINT_TYPE);
		createEAttribute(indexedEndpointTypeEClass, INDEXED_ENDPOINT_TYPE__INDEX);
		createEAttribute(indexedEndpointTypeEClass, INDEXED_ENDPOINT_TYPE__IS_DEFAULT);

		localizedNameTypeEClass = createEClass(LOCALIZED_NAME_TYPE);
		createEAttribute(localizedNameTypeEClass, LOCALIZED_NAME_TYPE__VALUE);
		createEAttribute(localizedNameTypeEClass, LOCALIZED_NAME_TYPE__LANG);

		localizedURITypeEClass = createEClass(LOCALIZED_URI_TYPE);
		createEAttribute(localizedURITypeEClass, LOCALIZED_URI_TYPE__VALUE);
		createEAttribute(localizedURITypeEClass, LOCALIZED_URI_TYPE__LANG);

		organizationTypeEClass = createEClass(ORGANIZATION_TYPE);
		createEReference(organizationTypeEClass, ORGANIZATION_TYPE__EXTENSIONS);
		createEReference(organizationTypeEClass, ORGANIZATION_TYPE__ORGANIZATION_NAME);
		createEReference(organizationTypeEClass, ORGANIZATION_TYPE__ORGANIZATION_DISPLAY_NAME);
		createEReference(organizationTypeEClass, ORGANIZATION_TYPE__ORGANIZATION_URL);
		createEAttribute(organizationTypeEClass, ORGANIZATION_TYPE__ANY_ATTRIBUTE);

		pdpDescriptorTypeEClass = createEClass(PDP_DESCRIPTOR_TYPE);
		createEReference(pdpDescriptorTypeEClass, PDP_DESCRIPTOR_TYPE__AUTHZ_SERVICE);
		createEReference(pdpDescriptorTypeEClass, PDP_DESCRIPTOR_TYPE__ASSERTION_ID_REQUEST_SERVICE);
		createEAttribute(pdpDescriptorTypeEClass, PDP_DESCRIPTOR_TYPE__NAME_ID_FORMAT);

		requestedAttributeTypeEClass = createEClass(REQUESTED_ATTRIBUTE_TYPE);
		createEAttribute(requestedAttributeTypeEClass, REQUESTED_ATTRIBUTE_TYPE__IS_REQUIRED);

		roleDescriptorTypeEClass = createEClass(ROLE_DESCRIPTOR_TYPE);
		createEReference(roleDescriptorTypeEClass, ROLE_DESCRIPTOR_TYPE__EXTENSIONS);
		createEReference(roleDescriptorTypeEClass, ROLE_DESCRIPTOR_TYPE__ORGANIZATION);
		createEReference(roleDescriptorTypeEClass, ROLE_DESCRIPTOR_TYPE__CONTACT_PERSON);
		createEAttribute(roleDescriptorTypeEClass, ROLE_DESCRIPTOR_TYPE__CACHE_DURATION);
		createEAttribute(roleDescriptorTypeEClass, ROLE_DESCRIPTOR_TYPE__ERROR_URL);
		createEAttribute(roleDescriptorTypeEClass, ROLE_DESCRIPTOR_TYPE__ID);
		createEAttribute(roleDescriptorTypeEClass, ROLE_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION);
		createEAttribute(roleDescriptorTypeEClass, ROLE_DESCRIPTOR_TYPE__VALID_UNTIL);
		createEAttribute(roleDescriptorTypeEClass, ROLE_DESCRIPTOR_TYPE__ANY_ATTRIBUTE);

		spssoDescriptorTypeEClass = createEClass(SPSSO_DESCRIPTOR_TYPE);
		createEReference(spssoDescriptorTypeEClass, SPSSO_DESCRIPTOR_TYPE__ASSERTION_CONSUMER_SERVICE);
		createEReference(spssoDescriptorTypeEClass, SPSSO_DESCRIPTOR_TYPE__ATTRIBUTE_CONSUMING_SERVICE);
		createEReference(spssoDescriptorTypeEClass, SPSSO_DESCRIPTOR_TYPE__AUTHN_POLICY);
		createEAttribute(spssoDescriptorTypeEClass, SPSSO_DESCRIPTOR_TYPE__AUTHN_REQUESTS_SIGNED);
		createEAttribute(spssoDescriptorTypeEClass, SPSSO_DESCRIPTOR_TYPE__WANT_ASSERTIONS_SIGNED);

		ssoDescriptorTypeEClass = createEClass(SSO_DESCRIPTOR_TYPE);
		createEReference(ssoDescriptorTypeEClass, SSO_DESCRIPTOR_TYPE__ARTIFACT_RESOLUTION_SERVICE);
		createEReference(ssoDescriptorTypeEClass, SSO_DESCRIPTOR_TYPE__SINGLE_LOGOUT_SERVICE);
		createEReference(ssoDescriptorTypeEClass, SSO_DESCRIPTOR_TYPE__MANAGE_NAME_ID_SERVICE);
		createEAttribute(ssoDescriptorTypeEClass, SSO_DESCRIPTOR_TYPE__NAME_ID_FORMAT);

		// Create enums
		contactTypeTypeEEnum = createEEnum(CONTACT_TYPE_TYPE);

		// Create data types
		anyURIListTypeEDataType = createEDataType(ANY_URI_LIST_TYPE);
		contactTypeTypeObjectEDataType = createEDataType(CONTACT_TYPE_TYPE_OBJECT);
		entityIDTypeEDataType = createEDataType(ENTITY_ID_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);
		AssertionPackage theAssertionPackage = (AssertionPackage)EPackage.Registry.INSTANCE.getEPackage(AssertionPackage.eNS_URI);
		NamespacePackage theNamespacePackage = (NamespacePackage)EPackage.Registry.INSTANCE.getEPackage(NamespacePackage.eNS_URI);
		PolicyPackage thePolicyPackage = (PolicyPackage)EPackage.Registry.INSTANCE.getEPackage(PolicyPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		attributeAuthorityDescriptorTypeEClass.getESuperTypes().add(this.getRoleDescriptorType());
		authnAuthorityDescriptorTypeEClass.getESuperTypes().add(this.getRoleDescriptorType());
		idpssoDescriptorTypeEClass.getESuperTypes().add(this.getSSODescriptorType());
		indexedEndpointTypeEClass.getESuperTypes().add(this.getEndpointType());
		pdpDescriptorTypeEClass.getESuperTypes().add(this.getRoleDescriptorType());
		requestedAttributeTypeEClass.getESuperTypes().add(theAssertionPackage.getAttributeType());
		spssoDescriptorTypeEClass.getESuperTypes().add(this.getSSODescriptorType());
		ssoDescriptorTypeEClass.getESuperTypes().add(this.getRoleDescriptorType());

		// Initialize classes and features; add operations and parameters
		initEClass(additionalMetadataLocationTypeEClass, AdditionalMetadataLocationType.class, "AdditionalMetadataLocationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAdditionalMetadataLocationType_Value(), theXMLTypePackage.getAnyURI(), "value", null, 0, 1, AdditionalMetadataLocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdditionalMetadataLocationType_Namespace(), theXMLTypePackage.getAnyURI(), "namespace", null, 1, 1, AdditionalMetadataLocationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(affiliationDescriptorTypeEClass, AffiliationDescriptorType.class, "AffiliationDescriptorType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAffiliationDescriptorType_Extensions(), this.getExtensionsType(), null, "extensions", null, 0, 1, AffiliationDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAffiliationDescriptorType_AffiliateMember(), this.getEntityIDType(), "affiliateMember", null, 1, -1, AffiliationDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAffiliationDescriptorType_AffiliationOwnerID(), this.getEntityIDType(), "affiliationOwnerID", null, 1, 1, AffiliationDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAffiliationDescriptorType_CacheDuration(), theXMLTypePackage.getDuration(), "cacheDuration", null, 0, 1, AffiliationDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAffiliationDescriptorType_ID(), theXMLTypePackage.getID(), "iD", null, 0, 1, AffiliationDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAffiliationDescriptorType_ValidUntil(), theXMLTypePackage.getDateTime(), "validUntil", null, 0, 1, AffiliationDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAffiliationDescriptorType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, AffiliationDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeAuthorityDescriptorTypeEClass, AttributeAuthorityDescriptorType.class, "AttributeAuthorityDescriptorType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributeAuthorityDescriptorType_AttributeService(), this.getEndpointType(), null, "attributeService", null, 1, -1, AttributeAuthorityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributeAuthorityDescriptorType_AssertionIDRequestService(), this.getEndpointType(), null, "assertionIDRequestService", null, 0, -1, AttributeAuthorityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributeAuthorityDescriptorType_NameIDFormat(), theXMLTypePackage.getAnyURI(), "nameIDFormat", null, 0, -1, AttributeAuthorityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributeAuthorityDescriptorType_AttributeProfile(), theXMLTypePackage.getAnyURI(), "attributeProfile", null, 0, -1, AttributeAuthorityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributeAuthorityDescriptorType_Attribute(), theAssertionPackage.getAttributeType(), null, "attribute", null, 0, -1, AttributeAuthorityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeConsumingServiceTypeEClass, AttributeConsumingServiceType.class, "AttributeConsumingServiceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributeConsumingServiceType_ServiceName(), this.getLocalizedNameType(), null, "serviceName", null, 1, -1, AttributeConsumingServiceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributeConsumingServiceType_ServiceDescription(), this.getLocalizedNameType(), null, "serviceDescription", null, 0, -1, AttributeConsumingServiceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributeConsumingServiceType_RequestedAttribute(), this.getRequestedAttributeType(), null, "requestedAttribute", null, 1, -1, AttributeConsumingServiceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributeConsumingServiceType_Index(), theXMLTypePackage.getUnsignedShort(), "index", null, 1, 1, AttributeConsumingServiceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAttributeConsumingServiceType_IsDefault(), theXMLTypePackage.getBoolean(), "isDefault", null, 0, 1, AttributeConsumingServiceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(authnAuthorityDescriptorTypeEClass, AuthnAuthorityDescriptorType.class, "AuthnAuthorityDescriptorType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAuthnAuthorityDescriptorType_AuthnQueryService(), this.getEndpointType(), null, "authnQueryService", null, 1, -1, AuthnAuthorityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthnAuthorityDescriptorType_AssertionIDRequestService(), this.getEndpointType(), null, "assertionIDRequestService", null, 0, -1, AuthnAuthorityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthnAuthorityDescriptorType_NameIDFormat(), theXMLTypePackage.getAnyURI(), "nameIDFormat", null, 0, -1, AuthnAuthorityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(contactTypeEClass, ContactType.class, "ContactType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContactType_Extensions(), this.getExtensionsType(), null, "extensions", null, 0, 1, ContactType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getContactType_Company(), theXMLTypePackage.getString(), "company", null, 0, 1, ContactType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getContactType_GivenName(), theXMLTypePackage.getString(), "givenName", null, 0, 1, ContactType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getContactType_SurName(), theXMLTypePackage.getString(), "surName", null, 0, 1, ContactType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getContactType_EmailAddress(), theXMLTypePackage.getAnyURI(), "emailAddress", null, 0, -1, ContactType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getContactType_TelephoneNumber(), theXMLTypePackage.getString(), "telephoneNumber", null, 0, -1, ContactType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getContactType_ContactType(), this.getContactTypeType(), "contactType", null, 1, 1, ContactType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getContactType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, ContactType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AdditionalMetadataLocation(), this.getAdditionalMetadataLocationType(), null, "additionalMetadataLocation", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_AffiliateMember(), this.getEntityIDType(), "affiliateMember", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AffiliationDescriptor(), this.getAffiliationDescriptorType(), null, "affiliationDescriptor", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ArtifactResolutionService(), this.getIndexedEndpointType(), null, "artifactResolutionService", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AssertionConsumerService(), this.getIndexedEndpointType(), null, "assertionConsumerService", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AssertionIDRequestService(), this.getEndpointType(), null, "assertionIDRequestService", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AttributeAuthorityDescriptor(), this.getAttributeAuthorityDescriptorType(), null, "attributeAuthorityDescriptor", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AttributeConsumingService(), this.getAttributeConsumingServiceType(), null, "attributeConsumingService", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_AttributeProfile(), theXMLTypePackage.getAnyURI(), "attributeProfile", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AttributeService(), this.getEndpointType(), null, "attributeService", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AuthnAuthorityDescriptor(), this.getAuthnAuthorityDescriptorType(), null, "authnAuthorityDescriptor", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AuthnQueryService(), this.getEndpointType(), null, "authnQueryService", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AuthzService(), this.getEndpointType(), null, "authzService", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Company(), theXMLTypePackage.getString(), "company", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ContactPerson(), this.getContactType(), null, "contactPerson", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_EmailAddress(), theXMLTypePackage.getAnyURI(), "emailAddress", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_EntitiesDescriptor(), this.getEntitiesDescriptorType(), null, "entitiesDescriptor", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_EntityDescriptor(), this.getEntityDescriptorType(), null, "entityDescriptor", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Extensions(), this.getExtensionsType(), null, "extensions", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_GivenName(), theXMLTypePackage.getString(), "givenName", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_IDPSSODescriptor(), this.getIDPSSODescriptorType(), null, "iDPSSODescriptor", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ManageNameIDService(), this.getEndpointType(), null, "manageNameIDService", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_NameIDFormat(), theXMLTypePackage.getAnyURI(), "nameIDFormat", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_NameIDMappingService(), this.getEndpointType(), null, "nameIDMappingService", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Organization(), this.getOrganizationType(), null, "organization", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_OrganizationDisplayName(), this.getLocalizedNameType(), null, "organizationDisplayName", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_OrganizationName(), this.getLocalizedNameType(), null, "organizationName", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_OrganizationURL(), this.getLocalizedURIType(), null, "organizationURL", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_PDPDescriptor(), this.getPDPDescriptorType(), null, "pDPDescriptor", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_RequestedAttribute(), this.getRequestedAttributeType(), null, "requestedAttribute", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_RoleDescriptor(), this.getRoleDescriptorType(), null, "roleDescriptor", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ServiceDescription(), this.getLocalizedNameType(), null, "serviceDescription", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ServiceName(), this.getLocalizedNameType(), null, "serviceName", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_SingleLogoutService(), this.getEndpointType(), null, "singleLogoutService", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_SingleSignOnService(), this.getEndpointType(), null, "singleSignOnService", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_SPSSODescriptor(), this.getSPSSODescriptorType(), null, "sPSSODescriptor", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_SurName(), theXMLTypePackage.getString(), "surName", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_TelephoneNumber(), theXMLTypePackage.getString(), "telephoneNumber", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(endpointTypeEClass, EndpointType.class, "EndpointType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEndpointType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, -1, EndpointType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEndpointType_Binding(), theXMLTypePackage.getAnyURI(), "binding", null, 1, 1, EndpointType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEndpointType_Location(), theXMLTypePackage.getAnyURI(), "location", null, 1, 1, EndpointType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEndpointType_ResponseLocation(), theXMLTypePackage.getAnyURI(), "responseLocation", null, 0, 1, EndpointType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEndpointType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, EndpointType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entitiesDescriptorTypeEClass, EntitiesDescriptorType.class, "EntitiesDescriptorType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEntitiesDescriptorType_Extensions(), this.getExtensionsType(), null, "extensions", null, 0, 1, EntitiesDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntitiesDescriptorType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, EntitiesDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntitiesDescriptorType_EntityDescriptor(), this.getEntityDescriptorType(), null, "entityDescriptor", null, 0, -1, EntitiesDescriptorType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getEntitiesDescriptorType_EntitiesDescriptor(), this.getEntitiesDescriptorType(), null, "entitiesDescriptor", null, 0, -1, EntitiesDescriptorType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntitiesDescriptorType_CacheDuration(), theXMLTypePackage.getDuration(), "cacheDuration", null, 0, 1, EntitiesDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntitiesDescriptorType_ID(), theXMLTypePackage.getID(), "iD", null, 0, 1, EntitiesDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntitiesDescriptorType_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, EntitiesDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntitiesDescriptorType_ValidUntil(), theXMLTypePackage.getDateTime(), "validUntil", null, 0, 1, EntitiesDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityDescriptorTypeEClass, EntityDescriptorType.class, "EntityDescriptorType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEntityDescriptorType_Extensions(), this.getExtensionsType(), null, "extensions", null, 0, 1, EntityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityDescriptorType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, EntityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityDescriptorType_RoleDescriptor(), this.getRoleDescriptorType(), null, "roleDescriptor", null, 0, -1, EntityDescriptorType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getEntityDescriptorType_IDPSSODescriptor(), this.getIDPSSODescriptorType(), null, "iDPSSODescriptor", null, 0, -1, EntityDescriptorType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getEntityDescriptorType_SPSSODescriptor(), this.getSPSSODescriptorType(), null, "sPSSODescriptor", null, 0, -1, EntityDescriptorType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getEntityDescriptorType_AuthnAuthorityDescriptor(), this.getAuthnAuthorityDescriptorType(), null, "authnAuthorityDescriptor", null, 0, -1, EntityDescriptorType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getEntityDescriptorType_AttributeAuthorityDescriptor(), this.getAttributeAuthorityDescriptorType(), null, "attributeAuthorityDescriptor", null, 0, -1, EntityDescriptorType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getEntityDescriptorType_PDPDescriptor(), this.getPDPDescriptorType(), null, "pDPDescriptor", null, 0, -1, EntityDescriptorType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getEntityDescriptorType_AffiliationDescriptor(), this.getAffiliationDescriptorType(), null, "affiliationDescriptor", null, 0, 1, EntityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityDescriptorType_Organization(), this.getOrganizationType(), null, "organization", null, 0, 1, EntityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityDescriptorType_ContactPerson(), this.getContactType(), null, "contactPerson", null, 0, -1, EntityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEntityDescriptorType_AdditionalMetadataLocation(), this.getAdditionalMetadataLocationType(), null, "additionalMetadataLocation", null, 0, -1, EntityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityDescriptorType_CacheDuration(), theXMLTypePackage.getDuration(), "cacheDuration", null, 0, 1, EntityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityDescriptorType_EntityID(), this.getEntityIDType(), "entityID", null, 1, 1, EntityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityDescriptorType_ID(), theXMLTypePackage.getID(), "iD", null, 0, 1, EntityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityDescriptorType_ValidUntil(), theXMLTypePackage.getDateTime(), "validUntil", null, 0, 1, EntityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEntityDescriptorType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, EntityDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(extensionsTypeEClass, ExtensionsType.class, "ExtensionsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExtensionsType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 1, -1, ExtensionsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(idpssoDescriptorTypeEClass, IDPSSODescriptorType.class, "IDPSSODescriptorType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIDPSSODescriptorType_SingleSignOnService(), this.getEndpointType(), null, "singleSignOnService", null, 1, -1, IDPSSODescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIDPSSODescriptorType_NameIDMappingService(), this.getEndpointType(), null, "nameIDMappingService", null, 0, -1, IDPSSODescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIDPSSODescriptorType_AssertionIDRequestService(), this.getEndpointType(), null, "assertionIDRequestService", null, 0, -1, IDPSSODescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIDPSSODescriptorType_AttributeProfile(), theXMLTypePackage.getAnyURI(), "attributeProfile", null, 0, -1, IDPSSODescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIDPSSODescriptorType_Attribute(), theAssertionPackage.getAttributeType(), null, "attribute", null, 0, -1, IDPSSODescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIDPSSODescriptorType_WantAuthnRequestsSigned(), theXMLTypePackage.getBoolean(), "wantAuthnRequestsSigned", null, 0, 1, IDPSSODescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(indexedEndpointTypeEClass, IndexedEndpointType.class, "IndexedEndpointType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIndexedEndpointType_Index(), theXMLTypePackage.getUnsignedShort(), "index", null, 1, 1, IndexedEndpointType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIndexedEndpointType_IsDefault(), theXMLTypePackage.getBoolean(), "isDefault", null, 0, 1, IndexedEndpointType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(localizedNameTypeEClass, LocalizedNameType.class, "LocalizedNameType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLocalizedNameType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, LocalizedNameType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocalizedNameType_Lang(), theNamespacePackage.getLangType(), "lang", null, 1, 1, LocalizedNameType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(localizedURITypeEClass, LocalizedURIType.class, "LocalizedURIType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLocalizedURIType_Value(), theXMLTypePackage.getAnyURI(), "value", null, 0, 1, LocalizedURIType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocalizedURIType_Lang(), theNamespacePackage.getLangType(), "lang", null, 1, 1, LocalizedURIType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(organizationTypeEClass, OrganizationType.class, "OrganizationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOrganizationType_Extensions(), this.getExtensionsType(), null, "extensions", null, 0, 1, OrganizationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOrganizationType_OrganizationName(), this.getLocalizedNameType(), null, "organizationName", null, 1, -1, OrganizationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOrganizationType_OrganizationDisplayName(), this.getLocalizedNameType(), null, "organizationDisplayName", null, 1, -1, OrganizationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOrganizationType_OrganizationURL(), this.getLocalizedURIType(), null, "organizationURL", null, 1, -1, OrganizationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOrganizationType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, OrganizationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(pdpDescriptorTypeEClass, PDPDescriptorType.class, "PDPDescriptorType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPDPDescriptorType_AuthzService(), this.getEndpointType(), null, "authzService", null, 1, -1, PDPDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPDPDescriptorType_AssertionIDRequestService(), this.getEndpointType(), null, "assertionIDRequestService", null, 0, -1, PDPDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPDPDescriptorType_NameIDFormat(), theXMLTypePackage.getAnyURI(), "nameIDFormat", null, 0, -1, PDPDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(requestedAttributeTypeEClass, RequestedAttributeType.class, "RequestedAttributeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRequestedAttributeType_IsRequired(), theXMLTypePackage.getBoolean(), "isRequired", null, 0, 1, RequestedAttributeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(roleDescriptorTypeEClass, RoleDescriptorType.class, "RoleDescriptorType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRoleDescriptorType_Extensions(), this.getExtensionsType(), null, "extensions", null, 0, 1, RoleDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoleDescriptorType_Organization(), this.getOrganizationType(), null, "organization", null, 0, 1, RoleDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoleDescriptorType_ContactPerson(), this.getContactType(), null, "contactPerson", null, 0, -1, RoleDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRoleDescriptorType_CacheDuration(), theXMLTypePackage.getDuration(), "cacheDuration", null, 0, 1, RoleDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRoleDescriptorType_ErrorURL(), theXMLTypePackage.getAnyURI(), "errorURL", null, 0, 1, RoleDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRoleDescriptorType_ID(), theXMLTypePackage.getID(), "iD", null, 0, 1, RoleDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRoleDescriptorType_ProtocolSupportEnumeration(), this.getAnyURIListType(), "protocolSupportEnumeration", null, 1, 1, RoleDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRoleDescriptorType_ValidUntil(), theXMLTypePackage.getDateTime(), "validUntil", null, 0, 1, RoleDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRoleDescriptorType_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, RoleDescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(spssoDescriptorTypeEClass, SPSSODescriptorType.class, "SPSSODescriptorType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSPSSODescriptorType_AssertionConsumerService(), this.getIndexedEndpointType(), null, "assertionConsumerService", null, 1, -1, SPSSODescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSPSSODescriptorType_AttributeConsumingService(), this.getAttributeConsumingServiceType(), null, "attributeConsumingService", null, 0, -1, SPSSODescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSPSSODescriptorType_AuthnPolicy(), thePolicyPackage.getPolicyTemplateType(), null, "authnPolicy", null, 0, -1, SPSSODescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSPSSODescriptorType_AuthnRequestsSigned(), theXMLTypePackage.getBoolean(), "authnRequestsSigned", null, 0, 1, SPSSODescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSPSSODescriptorType_WantAssertionsSigned(), theXMLTypePackage.getBoolean(), "wantAssertionsSigned", null, 0, 1, SPSSODescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ssoDescriptorTypeEClass, SSODescriptorType.class, "SSODescriptorType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSSODescriptorType_ArtifactResolutionService(), this.getIndexedEndpointType(), null, "artifactResolutionService", null, 0, -1, SSODescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSSODescriptorType_SingleLogoutService(), this.getEndpointType(), null, "singleLogoutService", null, 0, -1, SSODescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSSODescriptorType_ManageNameIDService(), this.getEndpointType(), null, "manageNameIDService", null, 0, -1, SSODescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSSODescriptorType_NameIDFormat(), theXMLTypePackage.getAnyURI(), "nameIDFormat", null, 0, -1, SSODescriptorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(contactTypeTypeEEnum, ContactTypeType.class, "ContactTypeType");
		addEEnumLiteral(contactTypeTypeEEnum, ContactTypeType.TECHNICAL);
		addEEnumLiteral(contactTypeTypeEEnum, ContactTypeType.SUPPORT);
		addEEnumLiteral(contactTypeTypeEEnum, ContactTypeType.ADMINISTRATIVE);
		addEEnumLiteral(contactTypeTypeEEnum, ContactTypeType.BILLING);
		addEEnumLiteral(contactTypeTypeEEnum, ContactTypeType.OTHER);

		// Initialize data types
		initEDataType(anyURIListTypeEDataType, List.class, "AnyURIListType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(contactTypeTypeObjectEDataType, ContactTypeType.class, "ContactTypeTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(entityIDTypeEDataType, String.class, "EntityIDType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// null
		createNullAnnotations();
		// http://java.sun.com/xml/ns/jaxb
		createJaxbAnnotations();
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>null</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createNullAnnotations() {
		String source = null;		
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "appinfo", "\r\n\t    <jxb:schemaBindings xmlns:jxb=\"http://java.sun.com/xml/ns/jaxb\">\r\n\t      <jxb:package name=\"com.tibco.be.baas.security.authn.saml.metadata.model\">\r\n\t\t <jxb:javadoc>\r\n\t   <![CDATA[<body> Package level documentation for \r\n\tgenerated package com.tibco.be.baas.security.authn.saml.metadata.model</body>]]>\r\n\t\t </jxb:javadoc>\r\n\t      </jxb:package>\r\n\t    </jxb:schemaBindings>\r\n\t  \r\n\r\n\t    <jxb:schemaBindings xmlns:jxb=\"http://java.sun.com/xml/ns/jaxb\">\r\n\t      <jxb:package name=\"com.tibco.be.baas.security.authn.saml.model\">\r\n\t\t <jxb:javadoc>\r\n\t   <![CDATA[<body> Package level documentation for \r\n\tgenerated package com.tibco.be.baas.security.authn.saml.model</body>]]>\r\n\t\t </jxb:javadoc>\r\n\t      </jxb:package>\r\n\t    </jxb:schemaBindings>\r\n\t  \r\n\r\n\t    <jxb:schemaBindings xmlns:jxb=\"http://java.sun.com/xml/ns/jaxb\">\r\n\t      <jxb:package name=\"com.tibco.be.baas.security.authn.saml.metadata.policy.model\">\r\n\t\t <jxb:javadoc>\r\n\t   <![CDATA[<body> Package level documentation for \r\n\tgenerated package com.tibco.be.baas.security.authn.saml.metadata.policy.model</body>]]>\r\n\t\t </jxb:javadoc>\r\n\t      </jxb:package>\r\n\t    </jxb:schemaBindings>\r\n\t  "
		   });																																																																																																																																																																								
	}

	/**
	 * Initializes the annotations for <b>http://java.sun.com/xml/ns/jaxb</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createJaxbAnnotations() {
		String source = "http://java.sun.com/xml/ns/jaxb";				
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "version", "2.0"
		   });																																																																																																																																																																						
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";					
		addAnnotation
		  (additionalMetadataLocationTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AdditionalMetadataLocationType",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getAdditionalMetadataLocationType_Value(), 
		   source, 
		   new String[] {
			 "name", ":0",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getAdditionalMetadataLocationType_Namespace(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "namespace"
		   });		
		addAnnotation
		  (affiliationDescriptorTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AffiliationDescriptorType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAffiliationDescriptorType_Extensions(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Extensions",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAffiliationDescriptorType_AffiliateMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AffiliateMember",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAffiliationDescriptorType_AffiliationOwnerID(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "affiliationOwnerID"
		   });		
		addAnnotation
		  (getAffiliationDescriptorType_CacheDuration(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "cacheDuration"
		   });		
		addAnnotation
		  (getAffiliationDescriptorType_ID(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ID"
		   });		
		addAnnotation
		  (getAffiliationDescriptorType_ValidUntil(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "validUntil"
		   });		
		addAnnotation
		  (getAffiliationDescriptorType_AnyAttribute(), 
		   source, 
		   new String[] {
			 "kind", "attributeWildcard",
			 "wildcards", "##other",
			 "name", ":6",
			 "processing", "lax"
		   });		
		addAnnotation
		  (anyURIListTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "anyURIListType",
			 "itemType", "http://www.eclipse.org/emf/2003/XMLType#anyURI"
		   });		
		addAnnotation
		  (attributeAuthorityDescriptorTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AttributeAuthorityDescriptorType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAttributeAuthorityDescriptorType_AttributeService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AttributeService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAttributeAuthorityDescriptorType_AssertionIDRequestService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AssertionIDRequestService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAttributeAuthorityDescriptorType_NameIDFormat(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameIDFormat",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAttributeAuthorityDescriptorType_AttributeProfile(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AttributeProfile",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAttributeAuthorityDescriptorType_Attribute(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Attribute",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (attributeConsumingServiceTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AttributeConsumingServiceType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAttributeConsumingServiceType_ServiceName(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ServiceName",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAttributeConsumingServiceType_ServiceDescription(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ServiceDescription",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAttributeConsumingServiceType_RequestedAttribute(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "RequestedAttribute",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAttributeConsumingServiceType_Index(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "index"
		   });		
		addAnnotation
		  (getAttributeConsumingServiceType_IsDefault(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "isDefault"
		   });		
		addAnnotation
		  (authnAuthorityDescriptorTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AuthnAuthorityDescriptorType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAuthnAuthorityDescriptorType_AuthnQueryService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnQueryService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAuthnAuthorityDescriptorType_AssertionIDRequestService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AssertionIDRequestService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAuthnAuthorityDescriptorType_NameIDFormat(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameIDFormat",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (contactTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ContactType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getContactType_Extensions(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Extensions",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getContactType_Company(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Company",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getContactType_GivenName(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "GivenName",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getContactType_SurName(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SurName",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getContactType_EmailAddress(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "EmailAddress",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getContactType_TelephoneNumber(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "TelephoneNumber",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getContactType_ContactType(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "contactType"
		   });		
		addAnnotation
		  (getContactType_AnyAttribute(), 
		   source, 
		   new String[] {
			 "kind", "attributeWildcard",
			 "wildcards", "##other",
			 "name", ":7",
			 "processing", "lax"
		   });		
		addAnnotation
		  (contactTypeTypeEEnum, 
		   source, 
		   new String[] {
			 "name", "ContactTypeType"
		   });		
		addAnnotation
		  (contactTypeTypeObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "ContactTypeType:Object",
			 "baseType", "ContactTypeType"
		   });		
		addAnnotation
		  (documentRootEClass, 
		   source, 
		   new String[] {
			 "name", "",
			 "kind", "mixed"
		   });		
		addAnnotation
		  (getDocumentRoot_Mixed(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "name", ":mixed"
		   });		
		addAnnotation
		  (getDocumentRoot_XMLNSPrefixMap(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xmlns:prefix"
		   });		
		addAnnotation
		  (getDocumentRoot_XSISchemaLocation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xsi:schemaLocation"
		   });		
		addAnnotation
		  (getDocumentRoot_AdditionalMetadataLocation(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AdditionalMetadataLocation",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AffiliateMember(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AffiliateMember",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AffiliationDescriptor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AffiliationDescriptor",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_ArtifactResolutionService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ArtifactResolutionService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AssertionConsumerService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AssertionConsumerService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AssertionIDRequestService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AssertionIDRequestService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AttributeAuthorityDescriptor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AttributeAuthorityDescriptor",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AttributeConsumingService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AttributeConsumingService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AttributeProfile(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AttributeProfile",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AttributeService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AttributeService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AuthnAuthorityDescriptor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnAuthorityDescriptor",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AuthnQueryService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnQueryService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AuthzService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthzService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Company(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Company",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_ContactPerson(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ContactPerson",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_EmailAddress(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "EmailAddress",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_EntitiesDescriptor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "EntitiesDescriptor",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_EntityDescriptor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "EntityDescriptor",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Extensions(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Extensions",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_GivenName(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "GivenName",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_IDPSSODescriptor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "IDPSSODescriptor",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_ManageNameIDService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ManageNameIDService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_NameIDFormat(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameIDFormat",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_NameIDMappingService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameIDMappingService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Organization(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Organization",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_OrganizationDisplayName(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "OrganizationDisplayName",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_OrganizationName(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "OrganizationName",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_OrganizationURL(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "OrganizationURL",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_PDPDescriptor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "PDPDescriptor",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_RequestedAttribute(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "RequestedAttribute",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_RoleDescriptor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "RoleDescriptor",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_ServiceDescription(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ServiceDescription",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_ServiceName(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ServiceName",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_SingleLogoutService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SingleLogoutService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_SingleSignOnService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SingleSignOnService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_SPSSODescriptor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SPSSODescriptor",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_SurName(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SurName",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_TelephoneNumber(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "TelephoneNumber",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (endpointTypeEClass, 
		   source, 
		   new String[] {
			 "name", "EndpointType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getEndpointType_Any(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "wildcards", "##other",
			 "name", ":0",
			 "processing", "lax"
		   });		
		addAnnotation
		  (getEndpointType_Binding(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Binding"
		   });		
		addAnnotation
		  (getEndpointType_Location(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Location"
		   });		
		addAnnotation
		  (getEndpointType_ResponseLocation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ResponseLocation"
		   });		
		addAnnotation
		  (getEndpointType_AnyAttribute(), 
		   source, 
		   new String[] {
			 "kind", "attributeWildcard",
			 "wildcards", "##other",
			 "name", ":4",
			 "processing", "lax"
		   });		
		addAnnotation
		  (entitiesDescriptorTypeEClass, 
		   source, 
		   new String[] {
			 "name", "EntitiesDescriptorType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getEntitiesDescriptorType_Extensions(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Extensions",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getEntitiesDescriptorType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:1"
		   });		
		addAnnotation
		  (getEntitiesDescriptorType_EntityDescriptor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "EntityDescriptor",
			 "namespace", "##targetNamespace",
			 "group", "#group:1"
		   });		
		addAnnotation
		  (getEntitiesDescriptorType_EntitiesDescriptor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "EntitiesDescriptor",
			 "namespace", "##targetNamespace",
			 "group", "#group:1"
		   });		
		addAnnotation
		  (getEntitiesDescriptorType_CacheDuration(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "cacheDuration"
		   });		
		addAnnotation
		  (getEntitiesDescriptorType_ID(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ID"
		   });		
		addAnnotation
		  (getEntitiesDescriptorType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Name"
		   });		
		addAnnotation
		  (getEntitiesDescriptorType_ValidUntil(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "validUntil"
		   });		
		addAnnotation
		  (entityDescriptorTypeEClass, 
		   source, 
		   new String[] {
			 "name", "EntityDescriptorType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getEntityDescriptorType_Extensions(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Extensions",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getEntityDescriptorType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:1"
		   });		
		addAnnotation
		  (getEntityDescriptorType_RoleDescriptor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "RoleDescriptor",
			 "namespace", "##targetNamespace",
			 "group", "#group:1"
		   });		
		addAnnotation
		  (getEntityDescriptorType_IDPSSODescriptor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "IDPSSODescriptor",
			 "namespace", "##targetNamespace",
			 "group", "#group:1"
		   });		
		addAnnotation
		  (getEntityDescriptorType_SPSSODescriptor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SPSSODescriptor",
			 "namespace", "##targetNamespace",
			 "group", "#group:1"
		   });		
		addAnnotation
		  (getEntityDescriptorType_AuthnAuthorityDescriptor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnAuthorityDescriptor",
			 "namespace", "##targetNamespace",
			 "group", "#group:1"
		   });		
		addAnnotation
		  (getEntityDescriptorType_AttributeAuthorityDescriptor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AttributeAuthorityDescriptor",
			 "namespace", "##targetNamespace",
			 "group", "#group:1"
		   });		
		addAnnotation
		  (getEntityDescriptorType_PDPDescriptor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "PDPDescriptor",
			 "namespace", "##targetNamespace",
			 "group", "#group:1"
		   });		
		addAnnotation
		  (getEntityDescriptorType_AffiliationDescriptor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AffiliationDescriptor",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getEntityDescriptorType_Organization(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Organization",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getEntityDescriptorType_ContactPerson(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ContactPerson",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getEntityDescriptorType_AdditionalMetadataLocation(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AdditionalMetadataLocation",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getEntityDescriptorType_CacheDuration(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "cacheDuration"
		   });		
		addAnnotation
		  (getEntityDescriptorType_EntityID(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "entityID"
		   });		
		addAnnotation
		  (getEntityDescriptorType_ID(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ID"
		   });		
		addAnnotation
		  (getEntityDescriptorType_ValidUntil(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "validUntil"
		   });		
		addAnnotation
		  (getEntityDescriptorType_AnyAttribute(), 
		   source, 
		   new String[] {
			 "kind", "attributeWildcard",
			 "wildcards", "##other",
			 "name", ":16",
			 "processing", "lax"
		   });		
		addAnnotation
		  (entityIDTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "entityIDType",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#anyURI",
			 "maxLength", "1024"
		   });		
		addAnnotation
		  (extensionsTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ExtensionsType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getExtensionsType_Any(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "wildcards", "##other",
			 "name", ":0",
			 "processing", "lax"
		   });		
		addAnnotation
		  (idpssoDescriptorTypeEClass, 
		   source, 
		   new String[] {
			 "name", "IDPSSODescriptorType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getIDPSSODescriptorType_SingleSignOnService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SingleSignOnService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getIDPSSODescriptorType_NameIDMappingService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameIDMappingService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getIDPSSODescriptorType_AssertionIDRequestService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AssertionIDRequestService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getIDPSSODescriptorType_AttributeProfile(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AttributeProfile",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getIDPSSODescriptorType_Attribute(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Attribute",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (getIDPSSODescriptorType_WantAuthnRequestsSigned(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "WantAuthnRequestsSigned"
		   });		
		addAnnotation
		  (indexedEndpointTypeEClass, 
		   source, 
		   new String[] {
			 "name", "IndexedEndpointType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getIndexedEndpointType_Index(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "index"
		   });		
		addAnnotation
		  (getIndexedEndpointType_IsDefault(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "isDefault"
		   });		
		addAnnotation
		  (localizedNameTypeEClass, 
		   source, 
		   new String[] {
			 "name", "localizedNameType",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getLocalizedNameType_Value(), 
		   source, 
		   new String[] {
			 "name", ":0",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getLocalizedNameType_Lang(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "lang",
			 "namespace", "http://www.w3.org/XML/1998/namespace"
		   });		
		addAnnotation
		  (localizedURITypeEClass, 
		   source, 
		   new String[] {
			 "name", "localizedURIType",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getLocalizedURIType_Value(), 
		   source, 
		   new String[] {
			 "name", ":0",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getLocalizedURIType_Lang(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "lang",
			 "namespace", "http://www.w3.org/XML/1998/namespace"
		   });		
		addAnnotation
		  (organizationTypeEClass, 
		   source, 
		   new String[] {
			 "name", "OrganizationType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getOrganizationType_Extensions(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Extensions",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getOrganizationType_OrganizationName(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "OrganizationName",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getOrganizationType_OrganizationDisplayName(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "OrganizationDisplayName",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getOrganizationType_OrganizationURL(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "OrganizationURL",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getOrganizationType_AnyAttribute(), 
		   source, 
		   new String[] {
			 "kind", "attributeWildcard",
			 "wildcards", "##other",
			 "name", ":4",
			 "processing", "lax"
		   });		
		addAnnotation
		  (pdpDescriptorTypeEClass, 
		   source, 
		   new String[] {
			 "name", "PDPDescriptorType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getPDPDescriptorType_AuthzService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthzService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getPDPDescriptorType_AssertionIDRequestService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AssertionIDRequestService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getPDPDescriptorType_NameIDFormat(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameIDFormat",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (requestedAttributeTypeEClass, 
		   source, 
		   new String[] {
			 "name", "RequestedAttributeType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getRequestedAttributeType_IsRequired(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "isRequired"
		   });		
		addAnnotation
		  (roleDescriptorTypeEClass, 
		   source, 
		   new String[] {
			 "name", "RoleDescriptorType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getRoleDescriptorType_Extensions(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Extensions",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getRoleDescriptorType_Organization(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Organization",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getRoleDescriptorType_ContactPerson(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ContactPerson",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getRoleDescriptorType_CacheDuration(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "cacheDuration"
		   });		
		addAnnotation
		  (getRoleDescriptorType_ErrorURL(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "errorURL"
		   });		
		addAnnotation
		  (getRoleDescriptorType_ID(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ID"
		   });		
		addAnnotation
		  (getRoleDescriptorType_ProtocolSupportEnumeration(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "protocolSupportEnumeration"
		   });		
		addAnnotation
		  (getRoleDescriptorType_ValidUntil(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "validUntil"
		   });		
		addAnnotation
		  (getRoleDescriptorType_AnyAttribute(), 
		   source, 
		   new String[] {
			 "kind", "attributeWildcard",
			 "wildcards", "##other",
			 "name", ":8",
			 "processing", "lax"
		   });		
		addAnnotation
		  (spssoDescriptorTypeEClass, 
		   source, 
		   new String[] {
			 "name", "SPSSODescriptorType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getSPSSODescriptorType_AssertionConsumerService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AssertionConsumerService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSPSSODescriptorType_AttributeConsumingService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AttributeConsumingService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSPSSODescriptorType_AuthnPolicy(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnPolicy",
			 "namespace", "http://www.tibco.com/be/baas/authn/PolicyTemplateSchema"
		   });		
		addAnnotation
		  (getSPSSODescriptorType_AuthnRequestsSigned(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "AuthnRequestsSigned"
		   });		
		addAnnotation
		  (getSPSSODescriptorType_WantAssertionsSigned(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "WantAssertionsSigned"
		   });		
		addAnnotation
		  (ssoDescriptorTypeEClass, 
		   source, 
		   new String[] {
			 "name", "SSODescriptorType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getSSODescriptorType_ArtifactResolutionService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ArtifactResolutionService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSSODescriptorType_SingleLogoutService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SingleLogoutService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSSODescriptorType_ManageNameIDService(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ManageNameIDService",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSSODescriptorType_NameIDFormat(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameIDFormat",
			 "namespace", "##targetNamespace"
		   });
	}

} //MetadataPackageImpl
