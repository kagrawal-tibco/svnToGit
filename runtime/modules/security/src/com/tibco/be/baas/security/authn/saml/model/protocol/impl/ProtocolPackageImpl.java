/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage;
import com.tibco.be.baas.security.authn.saml.model.assertion.impl.AssertionPackageImpl;
import com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResolveType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ArtifactResponseType;
import com.tibco.be.baas.security.authn.saml.model.protocol.AssertionIDRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.AttributeQueryType;
import com.tibco.be.baas.security.authn.saml.model.protocol.AuthnContextComparisonType;
import com.tibco.be.baas.security.authn.saml.model.protocol.AuthnQueryType;
import com.tibco.be.baas.security.authn.saml.model.protocol.AuthnRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.AuthzDecisionQueryType;
import com.tibco.be.baas.security.authn.saml.model.protocol.DocumentRoot;
import com.tibco.be.baas.security.authn.saml.model.protocol.ExtensionsType;
import com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType;
import com.tibco.be.baas.security.authn.saml.model.protocol.IDPListType;
import com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingResponseType;
import com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolFactory;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;
import com.tibco.be.baas.security.authn.saml.model.protocol.RequestAbstractType;
import com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ResponseType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType;
import com.tibco.be.baas.security.authn.saml.model.protocol.StatusCodeType;
import com.tibco.be.baas.security.authn.saml.model.protocol.StatusDetailType;
import com.tibco.be.baas.security.authn.saml.model.protocol.StatusResponseType;
import com.tibco.be.baas.security.authn.saml.model.protocol.StatusType;
import com.tibco.be.baas.security.authn.saml.model.protocol.SubjectQueryAbstractType;
import com.tibco.be.baas.security.authn.saml.model.protocol.TerminateType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProtocolPackageImpl extends EPackageImpl implements ProtocolPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass artifactResolveTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass artifactResponseTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass assertionIDRequestTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeQueryTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass authnQueryTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass authnRequestTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass authzDecisionQueryTypeEClass = null;

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
	private EClass extensionsTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass idpEntryTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass idpListTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass logoutRequestTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass manageNameIDRequestTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nameIDMappingRequestTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nameIDMappingResponseTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nameIDPolicyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass requestAbstractTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass requestedAuthnContextTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass responseTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass scopingTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass statusCodeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass statusDetailTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass statusResponseTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass statusTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass subjectQueryAbstractTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass terminateTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum authnContextComparisonTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType authnContextComparisonTypeObjectEDataType = null;

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
	 * @see com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ProtocolPackageImpl() {
		super(eNS_URI, ProtocolFactory.eINSTANCE);
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
	public static ProtocolPackage init() {
		if (isInited) return (ProtocolPackage)EPackage.Registry.INSTANCE.getEPackage(ProtocolPackage.eNS_URI);

		// Obtain or create and register package
		ProtocolPackageImpl theProtocolPackage = (ProtocolPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof ProtocolPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new ProtocolPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		AssertionPackageImpl theAssertionPackage = (AssertionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AssertionPackage.eNS_URI) instanceof AssertionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AssertionPackage.eNS_URI) : AssertionPackage.eINSTANCE);

		// Create package meta-data objects
		theProtocolPackage.createPackageContents();
		theAssertionPackage.createPackageContents();

		// Initialize created meta-data
		theProtocolPackage.initializePackageContents();
		theAssertionPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theProtocolPackage.freeze();

		return theProtocolPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArtifactResolveType() {
		return artifactResolveTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArtifactResolveType_Artifact() {
		return (EAttribute)artifactResolveTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArtifactResponseType() {
		return artifactResponseTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArtifactResponseType_Any() {
		return (EAttribute)artifactResponseTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAssertionIDRequestType() {
		return assertionIDRequestTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAssertionIDRequestType_AssertionIDRef() {
		return (EAttribute)assertionIDRequestTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAttributeQueryType() {
		return attributeQueryTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAttributeQueryType_Attribute() {
		return (EReference)attributeQueryTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAuthnQueryType() {
		return authnQueryTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthnQueryType_RequestedAuthnContext() {
		return (EReference)authnQueryTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthnQueryType_SessionIndex() {
		return (EAttribute)authnQueryTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAuthnRequestType() {
		return authnRequestTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthnRequestType_Subject() {
		return (EReference)authnRequestTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthnRequestType_NameIDPolicy() {
		return (EReference)authnRequestTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthnRequestType_Conditions() {
		return (EReference)authnRequestTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthnRequestType_RequestedAuthnContext() {
		return (EReference)authnRequestTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthnRequestType_Scoping() {
		return (EReference)authnRequestTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthnRequestType_AssertionConsumerServiceIndex() {
		return (EAttribute)authnRequestTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthnRequestType_AssertionConsumerServiceURL() {
		return (EAttribute)authnRequestTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthnRequestType_AttributeConsumingServiceIndex() {
		return (EAttribute)authnRequestTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthnRequestType_ForceAuthn() {
		return (EAttribute)authnRequestTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthnRequestType_IsPassive() {
		return (EAttribute)authnRequestTypeEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthnRequestType_ProtocolBinding() {
		return (EAttribute)authnRequestTypeEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthnRequestType_ProviderName() {
		return (EAttribute)authnRequestTypeEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAuthzDecisionQueryType() {
		return authzDecisionQueryTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthzDecisionQueryType_Action() {
		return (EReference)authzDecisionQueryTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAuthzDecisionQueryType_Evidence() {
		return (EReference)authzDecisionQueryTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAuthzDecisionQueryType_Resource() {
		return (EAttribute)authzDecisionQueryTypeEClass.getEStructuralFeatures().get(2);
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
	public EAttribute getDocumentRoot_Artifact() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ArtifactResolve() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ArtifactResponse() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AssertionIDRequest() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AttributeQuery() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AuthnQuery() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AuthnRequest() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AuthzDecisionQuery() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Extensions() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_GetComplete() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_IDPEntry() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_IDPList() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_LogoutRequest() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_LogoutResponse() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ManageNameIDRequest() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ManageNameIDResponse() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_NameIDMappingRequest() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_NameIDMappingResponse() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_NameIDPolicy() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_NewID() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_RequestedAuthnContext() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_RequesterID() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Response() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Scoping() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_SessionIndex() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Status() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_StatusCode() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_StatusDetail() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_StatusMessage() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(31);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_SubjectQuery() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(32);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Terminate() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(33);
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
	public EClass getIDPEntryType() {
		return idpEntryTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIDPEntryType_Loc() {
		return (EAttribute)idpEntryTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIDPEntryType_Name() {
		return (EAttribute)idpEntryTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIDPEntryType_ProviderID() {
		return (EAttribute)idpEntryTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIDPListType() {
		return idpListTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIDPListType_IDPEntry() {
		return (EReference)idpListTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIDPListType_GetComplete() {
		return (EAttribute)idpListTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLogoutRequestType() {
		return logoutRequestTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLogoutRequestType_BaseID() {
		return (EReference)logoutRequestTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLogoutRequestType_NameID() {
		return (EReference)logoutRequestTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogoutRequestType_SessionIndex() {
		return (EAttribute)logoutRequestTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogoutRequestType_NotOnOrAfter() {
		return (EAttribute)logoutRequestTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogoutRequestType_Reason() {
		return (EAttribute)logoutRequestTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getManageNameIDRequestType() {
		return manageNameIDRequestTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getManageNameIDRequestType_NameID() {
		return (EReference)manageNameIDRequestTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getManageNameIDRequestType_NewID() {
		return (EAttribute)manageNameIDRequestTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getManageNameIDRequestType_Terminate() {
		return (EReference)manageNameIDRequestTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNameIDMappingRequestType() {
		return nameIDMappingRequestTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNameIDMappingRequestType_BaseID() {
		return (EReference)nameIDMappingRequestTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNameIDMappingRequestType_NameID() {
		return (EReference)nameIDMappingRequestTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNameIDMappingRequestType_NameIDPolicy() {
		return (EReference)nameIDMappingRequestTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNameIDMappingResponseType() {
		return nameIDMappingResponseTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNameIDMappingResponseType_NameID() {
		return (EReference)nameIDMappingResponseTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNameIDPolicyType() {
		return nameIDPolicyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNameIDPolicyType_AllowCreate() {
		return (EAttribute)nameIDPolicyTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNameIDPolicyType_Format() {
		return (EAttribute)nameIDPolicyTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNameIDPolicyType_SPNameQualifier() {
		return (EAttribute)nameIDPolicyTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRequestAbstractType() {
		return requestAbstractTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRequestAbstractType_Issuer() {
		return (EReference)requestAbstractTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRequestAbstractType_Extensions() {
		return (EReference)requestAbstractTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRequestAbstractType_Consent() {
		return (EAttribute)requestAbstractTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRequestAbstractType_Destination() {
		return (EAttribute)requestAbstractTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRequestAbstractType_ID() {
		return (EAttribute)requestAbstractTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRequestAbstractType_IssueInstant() {
		return (EAttribute)requestAbstractTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRequestAbstractType_Version() {
		return (EAttribute)requestAbstractTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRequestedAuthnContextType() {
		return requestedAuthnContextTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRequestedAuthnContextType_AuthnContextClassRef() {
		return (EAttribute)requestedAuthnContextTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRequestedAuthnContextType_AuthnContextDeclRef() {
		return (EAttribute)requestedAuthnContextTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRequestedAuthnContextType_Comparison() {
		return (EAttribute)requestedAuthnContextTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getResponseType() {
		return responseTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getResponseType_Group() {
		return (EAttribute)responseTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResponseType_Assertion() {
		return (EReference)responseTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getScopingType() {
		return scopingTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScopingType_IDPList() {
		return (EReference)scopingTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScopingType_RequesterID() {
		return (EAttribute)scopingTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScopingType_ProxyCount() {
		return (EAttribute)scopingTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStatusCodeType() {
		return statusCodeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStatusCodeType_StatusCode() {
		return (EReference)statusCodeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStatusCodeType_Value() {
		return (EAttribute)statusCodeTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStatusDetailType() {
		return statusDetailTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStatusDetailType_Any() {
		return (EAttribute)statusDetailTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStatusResponseType() {
		return statusResponseTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStatusResponseType_Issuer() {
		return (EReference)statusResponseTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStatusResponseType_Extensions() {
		return (EReference)statusResponseTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStatusResponseType_Status() {
		return (EReference)statusResponseTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStatusResponseType_Consent() {
		return (EAttribute)statusResponseTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStatusResponseType_Destination() {
		return (EAttribute)statusResponseTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStatusResponseType_ID() {
		return (EAttribute)statusResponseTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStatusResponseType_InResponseTo() {
		return (EAttribute)statusResponseTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStatusResponseType_IssueInstant() {
		return (EAttribute)statusResponseTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStatusResponseType_Version() {
		return (EAttribute)statusResponseTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStatusType() {
		return statusTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStatusType_StatusCode() {
		return (EReference)statusTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStatusType_StatusMessage() {
		return (EAttribute)statusTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStatusType_StatusDetail() {
		return (EReference)statusTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSubjectQueryAbstractType() {
		return subjectQueryAbstractTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubjectQueryAbstractType_Subject() {
		return (EReference)subjectQueryAbstractTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTerminateType() {
		return terminateTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getAuthnContextComparisonType() {
		return authnContextComparisonTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getAuthnContextComparisonTypeObject() {
		return authnContextComparisonTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProtocolFactory getProtocolFactory() {
		return (ProtocolFactory)getEFactoryInstance();
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
		artifactResolveTypeEClass = createEClass(ARTIFACT_RESOLVE_TYPE);
		createEAttribute(artifactResolveTypeEClass, ARTIFACT_RESOLVE_TYPE__ARTIFACT);

		artifactResponseTypeEClass = createEClass(ARTIFACT_RESPONSE_TYPE);
		createEAttribute(artifactResponseTypeEClass, ARTIFACT_RESPONSE_TYPE__ANY);

		assertionIDRequestTypeEClass = createEClass(ASSERTION_ID_REQUEST_TYPE);
		createEAttribute(assertionIDRequestTypeEClass, ASSERTION_ID_REQUEST_TYPE__ASSERTION_ID_REF);

		attributeQueryTypeEClass = createEClass(ATTRIBUTE_QUERY_TYPE);
		createEReference(attributeQueryTypeEClass, ATTRIBUTE_QUERY_TYPE__ATTRIBUTE);

		authnQueryTypeEClass = createEClass(AUTHN_QUERY_TYPE);
		createEReference(authnQueryTypeEClass, AUTHN_QUERY_TYPE__REQUESTED_AUTHN_CONTEXT);
		createEAttribute(authnQueryTypeEClass, AUTHN_QUERY_TYPE__SESSION_INDEX);

		authnRequestTypeEClass = createEClass(AUTHN_REQUEST_TYPE);
		createEReference(authnRequestTypeEClass, AUTHN_REQUEST_TYPE__SUBJECT);
		createEReference(authnRequestTypeEClass, AUTHN_REQUEST_TYPE__NAME_ID_POLICY);
		createEReference(authnRequestTypeEClass, AUTHN_REQUEST_TYPE__CONDITIONS);
		createEReference(authnRequestTypeEClass, AUTHN_REQUEST_TYPE__REQUESTED_AUTHN_CONTEXT);
		createEReference(authnRequestTypeEClass, AUTHN_REQUEST_TYPE__SCOPING);
		createEAttribute(authnRequestTypeEClass, AUTHN_REQUEST_TYPE__ASSERTION_CONSUMER_SERVICE_INDEX);
		createEAttribute(authnRequestTypeEClass, AUTHN_REQUEST_TYPE__ASSERTION_CONSUMER_SERVICE_URL);
		createEAttribute(authnRequestTypeEClass, AUTHN_REQUEST_TYPE__ATTRIBUTE_CONSUMING_SERVICE_INDEX);
		createEAttribute(authnRequestTypeEClass, AUTHN_REQUEST_TYPE__FORCE_AUTHN);
		createEAttribute(authnRequestTypeEClass, AUTHN_REQUEST_TYPE__IS_PASSIVE);
		createEAttribute(authnRequestTypeEClass, AUTHN_REQUEST_TYPE__PROTOCOL_BINDING);
		createEAttribute(authnRequestTypeEClass, AUTHN_REQUEST_TYPE__PROVIDER_NAME);

		authzDecisionQueryTypeEClass = createEClass(AUTHZ_DECISION_QUERY_TYPE);
		createEReference(authzDecisionQueryTypeEClass, AUTHZ_DECISION_QUERY_TYPE__ACTION);
		createEReference(authzDecisionQueryTypeEClass, AUTHZ_DECISION_QUERY_TYPE__EVIDENCE);
		createEAttribute(authzDecisionQueryTypeEClass, AUTHZ_DECISION_QUERY_TYPE__RESOURCE);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__ARTIFACT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ARTIFACT_RESOLVE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ARTIFACT_RESPONSE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ASSERTION_ID_REQUEST);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ATTRIBUTE_QUERY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__AUTHN_QUERY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__AUTHN_REQUEST);
		createEReference(documentRootEClass, DOCUMENT_ROOT__AUTHZ_DECISION_QUERY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__EXTENSIONS);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__GET_COMPLETE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__IDP_ENTRY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__IDP_LIST);
		createEReference(documentRootEClass, DOCUMENT_ROOT__LOGOUT_REQUEST);
		createEReference(documentRootEClass, DOCUMENT_ROOT__LOGOUT_RESPONSE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MANAGE_NAME_ID_REQUEST);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MANAGE_NAME_ID_RESPONSE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__NAME_ID_MAPPING_REQUEST);
		createEReference(documentRootEClass, DOCUMENT_ROOT__NAME_ID_MAPPING_RESPONSE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__NAME_ID_POLICY);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__NEW_ID);
		createEReference(documentRootEClass, DOCUMENT_ROOT__REQUESTED_AUTHN_CONTEXT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__REQUESTER_ID);
		createEReference(documentRootEClass, DOCUMENT_ROOT__RESPONSE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SCOPING);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__SESSION_INDEX);
		createEReference(documentRootEClass, DOCUMENT_ROOT__STATUS);
		createEReference(documentRootEClass, DOCUMENT_ROOT__STATUS_CODE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__STATUS_DETAIL);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__STATUS_MESSAGE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SUBJECT_QUERY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__TERMINATE);

		extensionsTypeEClass = createEClass(EXTENSIONS_TYPE);
		createEAttribute(extensionsTypeEClass, EXTENSIONS_TYPE__ANY);

		idpEntryTypeEClass = createEClass(IDP_ENTRY_TYPE);
		createEAttribute(idpEntryTypeEClass, IDP_ENTRY_TYPE__LOC);
		createEAttribute(idpEntryTypeEClass, IDP_ENTRY_TYPE__NAME);
		createEAttribute(idpEntryTypeEClass, IDP_ENTRY_TYPE__PROVIDER_ID);

		idpListTypeEClass = createEClass(IDP_LIST_TYPE);
		createEReference(idpListTypeEClass, IDP_LIST_TYPE__IDP_ENTRY);
		createEAttribute(idpListTypeEClass, IDP_LIST_TYPE__GET_COMPLETE);

		logoutRequestTypeEClass = createEClass(LOGOUT_REQUEST_TYPE);
		createEReference(logoutRequestTypeEClass, LOGOUT_REQUEST_TYPE__BASE_ID);
		createEReference(logoutRequestTypeEClass, LOGOUT_REQUEST_TYPE__NAME_ID);
		createEAttribute(logoutRequestTypeEClass, LOGOUT_REQUEST_TYPE__SESSION_INDEX);
		createEAttribute(logoutRequestTypeEClass, LOGOUT_REQUEST_TYPE__NOT_ON_OR_AFTER);
		createEAttribute(logoutRequestTypeEClass, LOGOUT_REQUEST_TYPE__REASON);

		manageNameIDRequestTypeEClass = createEClass(MANAGE_NAME_ID_REQUEST_TYPE);
		createEReference(manageNameIDRequestTypeEClass, MANAGE_NAME_ID_REQUEST_TYPE__NAME_ID);
		createEAttribute(manageNameIDRequestTypeEClass, MANAGE_NAME_ID_REQUEST_TYPE__NEW_ID);
		createEReference(manageNameIDRequestTypeEClass, MANAGE_NAME_ID_REQUEST_TYPE__TERMINATE);

		nameIDMappingRequestTypeEClass = createEClass(NAME_ID_MAPPING_REQUEST_TYPE);
		createEReference(nameIDMappingRequestTypeEClass, NAME_ID_MAPPING_REQUEST_TYPE__BASE_ID);
		createEReference(nameIDMappingRequestTypeEClass, NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID);
		createEReference(nameIDMappingRequestTypeEClass, NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID_POLICY);

		nameIDMappingResponseTypeEClass = createEClass(NAME_ID_MAPPING_RESPONSE_TYPE);
		createEReference(nameIDMappingResponseTypeEClass, NAME_ID_MAPPING_RESPONSE_TYPE__NAME_ID);

		nameIDPolicyTypeEClass = createEClass(NAME_ID_POLICY_TYPE);
		createEAttribute(nameIDPolicyTypeEClass, NAME_ID_POLICY_TYPE__ALLOW_CREATE);
		createEAttribute(nameIDPolicyTypeEClass, NAME_ID_POLICY_TYPE__FORMAT);
		createEAttribute(nameIDPolicyTypeEClass, NAME_ID_POLICY_TYPE__SP_NAME_QUALIFIER);

		requestAbstractTypeEClass = createEClass(REQUEST_ABSTRACT_TYPE);
		createEReference(requestAbstractTypeEClass, REQUEST_ABSTRACT_TYPE__ISSUER);
		createEReference(requestAbstractTypeEClass, REQUEST_ABSTRACT_TYPE__EXTENSIONS);
		createEAttribute(requestAbstractTypeEClass, REQUEST_ABSTRACT_TYPE__CONSENT);
		createEAttribute(requestAbstractTypeEClass, REQUEST_ABSTRACT_TYPE__DESTINATION);
		createEAttribute(requestAbstractTypeEClass, REQUEST_ABSTRACT_TYPE__ID);
		createEAttribute(requestAbstractTypeEClass, REQUEST_ABSTRACT_TYPE__ISSUE_INSTANT);
		createEAttribute(requestAbstractTypeEClass, REQUEST_ABSTRACT_TYPE__VERSION);

		requestedAuthnContextTypeEClass = createEClass(REQUESTED_AUTHN_CONTEXT_TYPE);
		createEAttribute(requestedAuthnContextTypeEClass, REQUESTED_AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_CLASS_REF);
		createEAttribute(requestedAuthnContextTypeEClass, REQUESTED_AUTHN_CONTEXT_TYPE__AUTHN_CONTEXT_DECL_REF);
		createEAttribute(requestedAuthnContextTypeEClass, REQUESTED_AUTHN_CONTEXT_TYPE__COMPARISON);

		responseTypeEClass = createEClass(RESPONSE_TYPE);
		createEAttribute(responseTypeEClass, RESPONSE_TYPE__GROUP);
		createEReference(responseTypeEClass, RESPONSE_TYPE__ASSERTION);

		scopingTypeEClass = createEClass(SCOPING_TYPE);
		createEReference(scopingTypeEClass, SCOPING_TYPE__IDP_LIST);
		createEAttribute(scopingTypeEClass, SCOPING_TYPE__REQUESTER_ID);
		createEAttribute(scopingTypeEClass, SCOPING_TYPE__PROXY_COUNT);

		statusCodeTypeEClass = createEClass(STATUS_CODE_TYPE);
		createEReference(statusCodeTypeEClass, STATUS_CODE_TYPE__STATUS_CODE);
		createEAttribute(statusCodeTypeEClass, STATUS_CODE_TYPE__VALUE);

		statusDetailTypeEClass = createEClass(STATUS_DETAIL_TYPE);
		createEAttribute(statusDetailTypeEClass, STATUS_DETAIL_TYPE__ANY);

		statusResponseTypeEClass = createEClass(STATUS_RESPONSE_TYPE);
		createEReference(statusResponseTypeEClass, STATUS_RESPONSE_TYPE__ISSUER);
		createEReference(statusResponseTypeEClass, STATUS_RESPONSE_TYPE__EXTENSIONS);
		createEReference(statusResponseTypeEClass, STATUS_RESPONSE_TYPE__STATUS);
		createEAttribute(statusResponseTypeEClass, STATUS_RESPONSE_TYPE__CONSENT);
		createEAttribute(statusResponseTypeEClass, STATUS_RESPONSE_TYPE__DESTINATION);
		createEAttribute(statusResponseTypeEClass, STATUS_RESPONSE_TYPE__ID);
		createEAttribute(statusResponseTypeEClass, STATUS_RESPONSE_TYPE__IN_RESPONSE_TO);
		createEAttribute(statusResponseTypeEClass, STATUS_RESPONSE_TYPE__ISSUE_INSTANT);
		createEAttribute(statusResponseTypeEClass, STATUS_RESPONSE_TYPE__VERSION);

		statusTypeEClass = createEClass(STATUS_TYPE);
		createEReference(statusTypeEClass, STATUS_TYPE__STATUS_CODE);
		createEAttribute(statusTypeEClass, STATUS_TYPE__STATUS_MESSAGE);
		createEReference(statusTypeEClass, STATUS_TYPE__STATUS_DETAIL);

		subjectQueryAbstractTypeEClass = createEClass(SUBJECT_QUERY_ABSTRACT_TYPE);
		createEReference(subjectQueryAbstractTypeEClass, SUBJECT_QUERY_ABSTRACT_TYPE__SUBJECT);

		terminateTypeEClass = createEClass(TERMINATE_TYPE);

		// Create enums
		authnContextComparisonTypeEEnum = createEEnum(AUTHN_CONTEXT_COMPARISON_TYPE);

		// Create data types
		authnContextComparisonTypeObjectEDataType = createEDataType(AUTHN_CONTEXT_COMPARISON_TYPE_OBJECT);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		artifactResolveTypeEClass.getESuperTypes().add(this.getRequestAbstractType());
		artifactResponseTypeEClass.getESuperTypes().add(this.getStatusResponseType());
		assertionIDRequestTypeEClass.getESuperTypes().add(this.getRequestAbstractType());
		attributeQueryTypeEClass.getESuperTypes().add(this.getSubjectQueryAbstractType());
		authnQueryTypeEClass.getESuperTypes().add(this.getSubjectQueryAbstractType());
		authnRequestTypeEClass.getESuperTypes().add(this.getRequestAbstractType());
		authzDecisionQueryTypeEClass.getESuperTypes().add(this.getSubjectQueryAbstractType());
		logoutRequestTypeEClass.getESuperTypes().add(this.getRequestAbstractType());
		manageNameIDRequestTypeEClass.getESuperTypes().add(this.getRequestAbstractType());
		nameIDMappingRequestTypeEClass.getESuperTypes().add(this.getRequestAbstractType());
		nameIDMappingResponseTypeEClass.getESuperTypes().add(this.getStatusResponseType());
		responseTypeEClass.getESuperTypes().add(this.getStatusResponseType());
		subjectQueryAbstractTypeEClass.getESuperTypes().add(this.getRequestAbstractType());

		// Initialize classes and features; add operations and parameters
		initEClass(artifactResolveTypeEClass, ArtifactResolveType.class, "ArtifactResolveType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getArtifactResolveType_Artifact(), theXMLTypePackage.getString(), "artifact", null, 1, 1, ArtifactResolveType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(artifactResponseTypeEClass, ArtifactResponseType.class, "ArtifactResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getArtifactResponseType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, 1, ArtifactResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(assertionIDRequestTypeEClass, AssertionIDRequestType.class, "AssertionIDRequestType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAssertionIDRequestType_AssertionIDRef(), theXMLTypePackage.getNCName(), "assertionIDRef", null, 1, -1, AssertionIDRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeQueryTypeEClass, AttributeQueryType.class, "AttributeQueryType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributeQueryType_Attribute(), theAssertionPackage.getAttributeType(), null, "attribute", null, 0, -1, AttributeQueryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(authnQueryTypeEClass, AuthnQueryType.class, "AuthnQueryType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAuthnQueryType_RequestedAuthnContext(), this.getRequestedAuthnContextType(), null, "requestedAuthnContext", null, 0, 1, AuthnQueryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthnQueryType_SessionIndex(), theXMLTypePackage.getString(), "sessionIndex", null, 0, 1, AuthnQueryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(authnRequestTypeEClass, AuthnRequestType.class, "AuthnRequestType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAuthnRequestType_Subject(), theAssertionPackage.getSubjectType(), null, "subject", null, 0, 1, AuthnRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthnRequestType_NameIDPolicy(), this.getNameIDPolicyType(), null, "nameIDPolicy", null, 0, 1, AuthnRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthnRequestType_Conditions(), theAssertionPackage.getConditionsType(), null, "conditions", null, 0, 1, AuthnRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthnRequestType_RequestedAuthnContext(), this.getRequestedAuthnContextType(), null, "requestedAuthnContext", null, 0, 1, AuthnRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthnRequestType_Scoping(), this.getScopingType(), null, "scoping", null, 0, 1, AuthnRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthnRequestType_AssertionConsumerServiceIndex(), theXMLTypePackage.getUnsignedShort(), "assertionConsumerServiceIndex", null, 0, 1, AuthnRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthnRequestType_AssertionConsumerServiceURL(), theXMLTypePackage.getAnyURI(), "assertionConsumerServiceURL", null, 0, 1, AuthnRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthnRequestType_AttributeConsumingServiceIndex(), theXMLTypePackage.getUnsignedShort(), "attributeConsumingServiceIndex", null, 0, 1, AuthnRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthnRequestType_ForceAuthn(), theXMLTypePackage.getBoolean(), "forceAuthn", null, 0, 1, AuthnRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthnRequestType_IsPassive(), theXMLTypePackage.getBoolean(), "isPassive", null, 0, 1, AuthnRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthnRequestType_ProtocolBinding(), theXMLTypePackage.getAnyURI(), "protocolBinding", null, 0, 1, AuthnRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthnRequestType_ProviderName(), theXMLTypePackage.getString(), "providerName", null, 0, 1, AuthnRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(authzDecisionQueryTypeEClass, AuthzDecisionQueryType.class, "AuthzDecisionQueryType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAuthzDecisionQueryType_Action(), theAssertionPackage.getActionType(), null, "action", null, 1, -1, AuthzDecisionQueryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAuthzDecisionQueryType_Evidence(), theAssertionPackage.getEvidenceType(), null, "evidence", null, 0, 1, AuthzDecisionQueryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAuthzDecisionQueryType_Resource(), theXMLTypePackage.getAnyURI(), "resource", null, 1, 1, AuthzDecisionQueryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Artifact(), theXMLTypePackage.getString(), "artifact", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ArtifactResolve(), this.getArtifactResolveType(), null, "artifactResolve", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ArtifactResponse(), this.getArtifactResponseType(), null, "artifactResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AssertionIDRequest(), this.getAssertionIDRequestType(), null, "assertionIDRequest", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AttributeQuery(), this.getAttributeQueryType(), null, "attributeQuery", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AuthnQuery(), this.getAuthnQueryType(), null, "authnQuery", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AuthnRequest(), this.getAuthnRequestType(), null, "authnRequest", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AuthzDecisionQuery(), this.getAuthzDecisionQueryType(), null, "authzDecisionQuery", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Extensions(), this.getExtensionsType(), null, "extensions", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_GetComplete(), theXMLTypePackage.getAnyURI(), "getComplete", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_IDPEntry(), this.getIDPEntryType(), null, "iDPEntry", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_IDPList(), this.getIDPListType(), null, "iDPList", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_LogoutRequest(), this.getLogoutRequestType(), null, "logoutRequest", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_LogoutResponse(), this.getStatusResponseType(), null, "logoutResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ManageNameIDRequest(), this.getManageNameIDRequestType(), null, "manageNameIDRequest", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ManageNameIDResponse(), this.getStatusResponseType(), null, "manageNameIDResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_NameIDMappingRequest(), this.getNameIDMappingRequestType(), null, "nameIDMappingRequest", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_NameIDMappingResponse(), this.getNameIDMappingResponseType(), null, "nameIDMappingResponse", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_NameIDPolicy(), this.getNameIDPolicyType(), null, "nameIDPolicy", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_NewID(), theXMLTypePackage.getString(), "newID", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_RequestedAuthnContext(), this.getRequestedAuthnContextType(), null, "requestedAuthnContext", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_RequesterID(), theXMLTypePackage.getAnyURI(), "requesterID", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Response(), this.getResponseType(), null, "response", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Scoping(), this.getScopingType(), null, "scoping", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_SessionIndex(), theXMLTypePackage.getString(), "sessionIndex", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Status(), this.getStatusType(), null, "status", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_StatusCode(), this.getStatusCodeType(), null, "statusCode", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_StatusDetail(), this.getStatusDetailType(), null, "statusDetail", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_StatusMessage(), theXMLTypePackage.getString(), "statusMessage", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_SubjectQuery(), this.getSubjectQueryAbstractType(), null, "subjectQuery", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Terminate(), this.getTerminateType(), null, "terminate", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(extensionsTypeEClass, ExtensionsType.class, "ExtensionsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExtensionsType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 1, -1, ExtensionsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(idpEntryTypeEClass, IDPEntryType.class, "IDPEntryType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIDPEntryType_Loc(), theXMLTypePackage.getAnyURI(), "loc", null, 0, 1, IDPEntryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIDPEntryType_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, IDPEntryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIDPEntryType_ProviderID(), theXMLTypePackage.getAnyURI(), "providerID", null, 1, 1, IDPEntryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(idpListTypeEClass, IDPListType.class, "IDPListType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIDPListType_IDPEntry(), this.getIDPEntryType(), null, "iDPEntry", null, 1, -1, IDPListType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIDPListType_GetComplete(), theXMLTypePackage.getAnyURI(), "getComplete", null, 0, 1, IDPListType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(logoutRequestTypeEClass, LogoutRequestType.class, "LogoutRequestType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLogoutRequestType_BaseID(), theAssertionPackage.getBaseIDAbstractType(), null, "baseID", null, 0, 1, LogoutRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLogoutRequestType_NameID(), theAssertionPackage.getNameIDType(), null, "nameID", null, 0, 1, LogoutRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogoutRequestType_SessionIndex(), theXMLTypePackage.getString(), "sessionIndex", null, 0, -1, LogoutRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogoutRequestType_NotOnOrAfter(), theXMLTypePackage.getDateTime(), "notOnOrAfter", null, 0, 1, LogoutRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogoutRequestType_Reason(), theXMLTypePackage.getString(), "reason", null, 0, 1, LogoutRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(manageNameIDRequestTypeEClass, ManageNameIDRequestType.class, "ManageNameIDRequestType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getManageNameIDRequestType_NameID(), theAssertionPackage.getNameIDType(), null, "nameID", null, 0, 1, ManageNameIDRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getManageNameIDRequestType_NewID(), theXMLTypePackage.getString(), "newID", null, 0, 1, ManageNameIDRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getManageNameIDRequestType_Terminate(), this.getTerminateType(), null, "terminate", null, 0, 1, ManageNameIDRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nameIDMappingRequestTypeEClass, NameIDMappingRequestType.class, "NameIDMappingRequestType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNameIDMappingRequestType_BaseID(), theAssertionPackage.getBaseIDAbstractType(), null, "baseID", null, 0, 1, NameIDMappingRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNameIDMappingRequestType_NameID(), theAssertionPackage.getNameIDType(), null, "nameID", null, 0, 1, NameIDMappingRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNameIDMappingRequestType_NameIDPolicy(), this.getNameIDPolicyType(), null, "nameIDPolicy", null, 1, 1, NameIDMappingRequestType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nameIDMappingResponseTypeEClass, NameIDMappingResponseType.class, "NameIDMappingResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNameIDMappingResponseType_NameID(), theAssertionPackage.getNameIDType(), null, "nameID", null, 0, 1, NameIDMappingResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nameIDPolicyTypeEClass, NameIDPolicyType.class, "NameIDPolicyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNameIDPolicyType_AllowCreate(), theXMLTypePackage.getBoolean(), "allowCreate", null, 0, 1, NameIDPolicyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNameIDPolicyType_Format(), theXMLTypePackage.getAnyURI(), "format", null, 0, 1, NameIDPolicyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNameIDPolicyType_SPNameQualifier(), theXMLTypePackage.getString(), "sPNameQualifier", null, 0, 1, NameIDPolicyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(requestAbstractTypeEClass, RequestAbstractType.class, "RequestAbstractType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRequestAbstractType_Issuer(), theAssertionPackage.getNameIDType(), null, "issuer", null, 0, 1, RequestAbstractType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRequestAbstractType_Extensions(), this.getExtensionsType(), null, "extensions", null, 0, 1, RequestAbstractType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRequestAbstractType_Consent(), theXMLTypePackage.getAnyURI(), "consent", null, 0, 1, RequestAbstractType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRequestAbstractType_Destination(), theXMLTypePackage.getAnyURI(), "destination", null, 0, 1, RequestAbstractType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRequestAbstractType_ID(), theXMLTypePackage.getID(), "iD", null, 1, 1, RequestAbstractType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRequestAbstractType_IssueInstant(), theXMLTypePackage.getDateTime(), "issueInstant", null, 1, 1, RequestAbstractType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRequestAbstractType_Version(), theXMLTypePackage.getString(), "version", null, 1, 1, RequestAbstractType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(requestedAuthnContextTypeEClass, RequestedAuthnContextType.class, "RequestedAuthnContextType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRequestedAuthnContextType_AuthnContextClassRef(), theXMLTypePackage.getAnyURI(), "authnContextClassRef", null, 0, -1, RequestedAuthnContextType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRequestedAuthnContextType_AuthnContextDeclRef(), theXMLTypePackage.getAnyURI(), "authnContextDeclRef", null, 0, -1, RequestedAuthnContextType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRequestedAuthnContextType_Comparison(), this.getAuthnContextComparisonType(), "comparison", null, 0, 1, RequestedAuthnContextType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(responseTypeEClass, ResponseType.class, "ResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getResponseType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, ResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getResponseType_Assertion(), theAssertionPackage.getAssertionType(), null, "assertion", null, 0, -1, ResponseType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(scopingTypeEClass, ScopingType.class, "ScopingType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getScopingType_IDPList(), this.getIDPListType(), null, "iDPList", null, 0, 1, ScopingType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScopingType_RequesterID(), theXMLTypePackage.getAnyURI(), "requesterID", null, 0, -1, ScopingType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScopingType_ProxyCount(), theXMLTypePackage.getNonNegativeInteger(), "proxyCount", null, 0, 1, ScopingType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(statusCodeTypeEClass, StatusCodeType.class, "StatusCodeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStatusCodeType_StatusCode(), this.getStatusCodeType(), null, "statusCode", null, 0, 1, StatusCodeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStatusCodeType_Value(), theXMLTypePackage.getAnyURI(), "value", null, 1, 1, StatusCodeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(statusDetailTypeEClass, StatusDetailType.class, "StatusDetailType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStatusDetailType_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, -1, StatusDetailType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(statusResponseTypeEClass, StatusResponseType.class, "StatusResponseType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStatusResponseType_Issuer(), theAssertionPackage.getNameIDType(), null, "issuer", null, 0, 1, StatusResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStatusResponseType_Extensions(), this.getExtensionsType(), null, "extensions", null, 0, 1, StatusResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStatusResponseType_Status(), this.getStatusType(), null, "status", null, 1, 1, StatusResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStatusResponseType_Consent(), theXMLTypePackage.getAnyURI(), "consent", null, 0, 1, StatusResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStatusResponseType_Destination(), theXMLTypePackage.getAnyURI(), "destination", null, 0, 1, StatusResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStatusResponseType_ID(), theXMLTypePackage.getID(), "iD", null, 1, 1, StatusResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStatusResponseType_InResponseTo(), theXMLTypePackage.getNCName(), "inResponseTo", null, 0, 1, StatusResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStatusResponseType_IssueInstant(), theXMLTypePackage.getDateTime(), "issueInstant", null, 1, 1, StatusResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStatusResponseType_Version(), theXMLTypePackage.getString(), "version", null, 1, 1, StatusResponseType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(statusTypeEClass, StatusType.class, "StatusType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStatusType_StatusCode(), this.getStatusCodeType(), null, "statusCode", null, 1, 1, StatusType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStatusType_StatusMessage(), theXMLTypePackage.getString(), "statusMessage", null, 0, 1, StatusType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStatusType_StatusDetail(), this.getStatusDetailType(), null, "statusDetail", null, 0, 1, StatusType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(subjectQueryAbstractTypeEClass, SubjectQueryAbstractType.class, "SubjectQueryAbstractType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSubjectQueryAbstractType_Subject(), theAssertionPackage.getSubjectType(), null, "subject", null, 1, 1, SubjectQueryAbstractType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(terminateTypeEClass, TerminateType.class, "TerminateType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(authnContextComparisonTypeEEnum, AuthnContextComparisonType.class, "AuthnContextComparisonType");
		addEEnumLiteral(authnContextComparisonTypeEEnum, AuthnContextComparisonType.EXACT);
		addEEnumLiteral(authnContextComparisonTypeEEnum, AuthnContextComparisonType.MINIMUM);
		addEEnumLiteral(authnContextComparisonTypeEEnum, AuthnContextComparisonType.MAXIMUM);
		addEEnumLiteral(authnContextComparisonTypeEEnum, AuthnContextComparisonType.BETTER);

		// Initialize data types
		initEDataType(authnContextComparisonTypeObjectEDataType, AuthnContextComparisonType.class, "AuthnContextComparisonTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

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
			 "appinfo", "\r\n\t    <jxb:schemaBindings xmlns:jxb=\"http://java.sun.com/xml/ns/jaxb\">\r\n\t      <jxb:package name=\"com.tibco.be.baas.security.authn.saml.model\">\r\n\t\t <jxb:javadoc>\r\n\t   <![CDATA[<body> Package level documentation for \r\n\tgenerated package com.tibco.be.baas.security.authn.saml.model</body>]]>\r\n\t\t </jxb:javadoc>\r\n\t      </jxb:package>\r\n\t    </jxb:schemaBindings>\r\n\t  \r\n\r\n\t    <jxb:schemaBindings xmlns:jxb=\"http://java.sun.com/xml/ns/jaxb\">\r\n\t      <jxb:package name=\"com.tibco.be.baas.security.authn.saml.model\">\r\n\t\t <jxb:javadoc>\r\n\t   <![CDATA[<body> Package level documentation for \r\n\tgenerated package com.tibco.be.baas.security.authn.saml.model</body>]]>\r\n\t\t </jxb:javadoc>\r\n\t      </jxb:package>\r\n\t    </jxb:schemaBindings>\r\n\t  "
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
		  (artifactResolveTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ArtifactResolveType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getArtifactResolveType_Artifact(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Artifact",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (artifactResponseTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ArtifactResponseType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getArtifactResponseType_Any(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "wildcards", "##any",
			 "name", ":9",
			 "processing", "lax"
		   });		
		addAnnotation
		  (assertionIDRequestTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AssertionIDRequestType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAssertionIDRequestType_AssertionIDRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AssertionIDRef",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (attributeQueryTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AttributeQueryType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAttributeQueryType_Attribute(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Attribute",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (authnContextComparisonTypeEEnum, 
		   source, 
		   new String[] {
			 "name", "AuthnContextComparisonType"
		   });		
		addAnnotation
		  (authnContextComparisonTypeObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "AuthnContextComparisonType:Object",
			 "baseType", "AuthnContextComparisonType"
		   });		
		addAnnotation
		  (authnQueryTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AuthnQueryType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAuthnQueryType_RequestedAuthnContext(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "RequestedAuthnContext",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAuthnQueryType_SessionIndex(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "SessionIndex"
		   });		
		addAnnotation
		  (authnRequestTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AuthnRequestType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAuthnRequestType_Subject(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Subject",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (getAuthnRequestType_NameIDPolicy(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameIDPolicy",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAuthnRequestType_Conditions(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Conditions",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (getAuthnRequestType_RequestedAuthnContext(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "RequestedAuthnContext",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAuthnRequestType_Scoping(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Scoping",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getAuthnRequestType_AssertionConsumerServiceIndex(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "AssertionConsumerServiceIndex"
		   });		
		addAnnotation
		  (getAuthnRequestType_AssertionConsumerServiceURL(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "AssertionConsumerServiceURL"
		   });		
		addAnnotation
		  (getAuthnRequestType_AttributeConsumingServiceIndex(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "AttributeConsumingServiceIndex"
		   });		
		addAnnotation
		  (getAuthnRequestType_ForceAuthn(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ForceAuthn"
		   });		
		addAnnotation
		  (getAuthnRequestType_IsPassive(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "IsPassive"
		   });		
		addAnnotation
		  (getAuthnRequestType_ProtocolBinding(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ProtocolBinding"
		   });		
		addAnnotation
		  (getAuthnRequestType_ProviderName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ProviderName"
		   });		
		addAnnotation
		  (authzDecisionQueryTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AuthzDecisionQueryType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAuthzDecisionQueryType_Action(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Action",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (getAuthzDecisionQueryType_Evidence(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Evidence",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (getAuthzDecisionQueryType_Resource(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Resource"
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
		  (getDocumentRoot_Artifact(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Artifact",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_ArtifactResolve(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ArtifactResolve",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_ArtifactResponse(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ArtifactResponse",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AssertionIDRequest(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AssertionIDRequest",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AttributeQuery(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AttributeQuery",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AuthnQuery(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnQuery",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AuthnRequest(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnRequest",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AuthzDecisionQuery(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthzDecisionQuery",
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
		  (getDocumentRoot_GetComplete(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "GetComplete",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_IDPEntry(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "IDPEntry",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_IDPList(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "IDPList",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_LogoutRequest(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "LogoutRequest",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_LogoutResponse(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "LogoutResponse",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_ManageNameIDRequest(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ManageNameIDRequest",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_ManageNameIDResponse(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ManageNameIDResponse",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_NameIDMappingRequest(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameIDMappingRequest",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_NameIDMappingResponse(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameIDMappingResponse",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_NameIDPolicy(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameIDPolicy",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_NewID(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NewID",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_RequestedAuthnContext(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "RequestedAuthnContext",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_RequesterID(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "RequesterID",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Response(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Response",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Scoping(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Scoping",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_SessionIndex(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SessionIndex",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Status(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Status",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_StatusCode(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "StatusCode",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_StatusDetail(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "StatusDetail",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_StatusMessage(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "StatusMessage",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_SubjectQuery(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SubjectQuery",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Terminate(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Terminate",
			 "namespace", "##targetNamespace"
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
		  (idpEntryTypeEClass, 
		   source, 
		   new String[] {
			 "name", "IDPEntryType",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getIDPEntryType_Loc(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Loc"
		   });		
		addAnnotation
		  (getIDPEntryType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Name"
		   });		
		addAnnotation
		  (getIDPEntryType_ProviderID(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ProviderID"
		   });		
		addAnnotation
		  (idpListTypeEClass, 
		   source, 
		   new String[] {
			 "name", "IDPListType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getIDPListType_IDPEntry(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "IDPEntry",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getIDPListType_GetComplete(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "GetComplete",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (logoutRequestTypeEClass, 
		   source, 
		   new String[] {
			 "name", "LogoutRequestType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getLogoutRequestType_BaseID(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "BaseID",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (getLogoutRequestType_NameID(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameID",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (getLogoutRequestType_SessionIndex(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SessionIndex",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getLogoutRequestType_NotOnOrAfter(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "NotOnOrAfter"
		   });		
		addAnnotation
		  (getLogoutRequestType_Reason(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Reason"
		   });		
		addAnnotation
		  (manageNameIDRequestTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ManageNameIDRequestType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getManageNameIDRequestType_NameID(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameID",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (getManageNameIDRequestType_NewID(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NewID",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getManageNameIDRequestType_Terminate(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Terminate",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (nameIDMappingRequestTypeEClass, 
		   source, 
		   new String[] {
			 "name", "NameIDMappingRequestType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getNameIDMappingRequestType_BaseID(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "BaseID",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (getNameIDMappingRequestType_NameID(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameID",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (getNameIDMappingRequestType_NameIDPolicy(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameIDPolicy",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (nameIDMappingResponseTypeEClass, 
		   source, 
		   new String[] {
			 "name", "NameIDMappingResponseType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getNameIDMappingResponseType_NameID(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NameID",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (nameIDPolicyTypeEClass, 
		   source, 
		   new String[] {
			 "name", "NameIDPolicyType",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getNameIDPolicyType_AllowCreate(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "AllowCreate"
		   });		
		addAnnotation
		  (getNameIDPolicyType_Format(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Format"
		   });		
		addAnnotation
		  (getNameIDPolicyType_SPNameQualifier(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "SPNameQualifier"
		   });		
		addAnnotation
		  (requestAbstractTypeEClass, 
		   source, 
		   new String[] {
			 "name", "RequestAbstractType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getRequestAbstractType_Issuer(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Issuer",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (getRequestAbstractType_Extensions(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Extensions",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getRequestAbstractType_Consent(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Consent"
		   });		
		addAnnotation
		  (getRequestAbstractType_Destination(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Destination"
		   });		
		addAnnotation
		  (getRequestAbstractType_ID(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ID"
		   });		
		addAnnotation
		  (getRequestAbstractType_IssueInstant(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "IssueInstant"
		   });		
		addAnnotation
		  (getRequestAbstractType_Version(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Version"
		   });		
		addAnnotation
		  (requestedAuthnContextTypeEClass, 
		   source, 
		   new String[] {
			 "name", "RequestedAuthnContextType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getRequestedAuthnContextType_AuthnContextClassRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnContextClassRef",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (getRequestedAuthnContextType_AuthnContextDeclRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnContextDeclRef",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (getRequestedAuthnContextType_Comparison(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Comparison"
		   });		
		addAnnotation
		  (responseTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ResponseType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getResponseType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:9"
		   });		
		addAnnotation
		  (getResponseType_Assertion(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Assertion",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion",
			 "group", "#group:9"
		   });		
		addAnnotation
		  (scopingTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ScopingType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getScopingType_IDPList(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "IDPList",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getScopingType_RequesterID(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "RequesterID",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getScopingType_ProxyCount(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ProxyCount"
		   });		
		addAnnotation
		  (statusCodeTypeEClass, 
		   source, 
		   new String[] {
			 "name", "StatusCodeType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getStatusCodeType_StatusCode(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "StatusCode",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getStatusCodeType_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Value"
		   });		
		addAnnotation
		  (statusDetailTypeEClass, 
		   source, 
		   new String[] {
			 "name", "StatusDetailType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getStatusDetailType_Any(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "wildcards", "##any",
			 "name", ":0",
			 "processing", "lax"
		   });		
		addAnnotation
		  (statusResponseTypeEClass, 
		   source, 
		   new String[] {
			 "name", "StatusResponseType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getStatusResponseType_Issuer(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Issuer",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (getStatusResponseType_Extensions(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Extensions",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getStatusResponseType_Status(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Status",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getStatusResponseType_Consent(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Consent"
		   });		
		addAnnotation
		  (getStatusResponseType_Destination(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Destination"
		   });		
		addAnnotation
		  (getStatusResponseType_ID(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ID"
		   });		
		addAnnotation
		  (getStatusResponseType_InResponseTo(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "InResponseTo"
		   });		
		addAnnotation
		  (getStatusResponseType_IssueInstant(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "IssueInstant"
		   });		
		addAnnotation
		  (getStatusResponseType_Version(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "Version"
		   });		
		addAnnotation
		  (statusTypeEClass, 
		   source, 
		   new String[] {
			 "name", "StatusType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getStatusType_StatusCode(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "StatusCode",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getStatusType_StatusMessage(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "StatusMessage",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getStatusType_StatusDetail(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "StatusDetail",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (subjectQueryAbstractTypeEClass, 
		   source, 
		   new String[] {
			 "name", "SubjectQueryAbstractType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getSubjectQueryAbstractType_Subject(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Subject",
			 "namespace", "urn:oasis:names:tc:SAML:2.0:assertion"
		   });		
		addAnnotation
		  (terminateTypeEClass, 
		   source, 
		   new String[] {
			 "name", "TerminateType",
			 "kind", "empty"
		   });
	}

} //ProtocolPackageImpl
