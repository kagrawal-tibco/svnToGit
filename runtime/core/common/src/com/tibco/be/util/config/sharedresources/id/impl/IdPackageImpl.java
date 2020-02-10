/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.id.impl;

import com.tibco.be.util.config.sharedresources.basetypes.BasetypesPackage;

import com.tibco.be.util.config.sharedresources.id.Designer;
import com.tibco.be.util.config.sharedresources.id.FileTypeEnum;
import com.tibco.be.util.config.sharedresources.id.IdFactory;
import com.tibco.be.util.config.sharedresources.id.IdPackage;
import com.tibco.be.util.config.sharedresources.id.IdRoot;
import com.tibco.be.util.config.sharedresources.id.Identity;
import com.tibco.be.util.config.sharedresources.id.Node;
import com.tibco.be.util.config.sharedresources.id.ObjectType;
import com.tibco.be.util.config.sharedresources.id.ObjectTypeEnum;
import com.tibco.be.util.config.sharedresources.id.Repository;
import com.tibco.be.util.config.sharedresources.id.ResourceDescriptions;

import com.tibco.be.util.config.sharedresources.id.util.IdValidator;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IdPackageImpl extends EPackageImpl implements IdPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass designerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass identityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass idRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass repositoryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceDescriptionsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum fileTypeEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum objectTypeEnumEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType fileTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType fileTypeEnumObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType objectTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType objectTypeEnumObjectEDataType = null;

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
	 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private IdPackageImpl() {
		super(eNS_URI, IdFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link IdPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IdPackage init() {
		if (isInited) return (IdPackage)EPackage.Registry.INSTANCE.getEPackage(IdPackage.eNS_URI);

		// Obtain or create and register package
		IdPackageImpl theIdPackage = (IdPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof IdPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new IdPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		BasetypesPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theIdPackage.createPackageContents();

		// Initialize created meta-data
		theIdPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theIdPackage, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return IdValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theIdPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IdPackage.eNS_URI, theIdPackage);
		return theIdPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDesigner() {
		return designerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getDesigner_LockedProperties()
    {
		return (EAttribute)designerEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getDesigner_FixedChildren()
    {
		return (EAttribute)designerEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDesigner_ResourceDescriptions() {
		return (EReference)designerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIdentity() {
		return identityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIdentity_Designer() {
		return (EReference)identityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIdentity_Name() {
		return (EAttribute)identityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIdentity_Description() {
		return (EAttribute)identityEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIdentity_ObjectType() {
		return (EAttribute)identityEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIdentity_Username() {
		return (EAttribute)identityEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIdentity_Password() {
		return (EAttribute)identityEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIdentity_FileType() {
		return (EAttribute)identityEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIdentity_Url() {
		return (EAttribute)identityEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIdentity_PassPhrase() {
		return (EAttribute)identityEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIdentity_CertUrl() {
		return (EAttribute)identityEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIdentity_PrivateKeyUrl() {
		return (EAttribute)identityEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIdRoot() {
		return idRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIdRoot_Mixed() {
		return (EAttribute)idRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIdRoot_XMLNSPrefixMap() {
		return (EReference)idRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIdRoot_XSISchemaLocation() {
		return (EReference)idRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIdRoot_Repository() {
		return (EReference)idRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNode() {
		return nodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNode_Description() {
		return (EAttribute)nodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRepository() {
		return repositoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRepository_Identity() {
		return (EReference)repositoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getResourceDescriptions() {
		return resourceDescriptionsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceDescriptions_Node() {
		return (EReference)resourceDescriptionsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getFileTypeEnum() {
		return fileTypeEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getObjectTypeEnum() {
		return objectTypeEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getObjectType() {
		return objectTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getObjectTypeEnumObject() {
		return objectTypeEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getFileType() {
		return fileTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getFileTypeEnumObject() {
		return fileTypeEnumObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IdFactory getIdFactory() {
		return (IdFactory)getEFactoryInstance();
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
		designerEClass = createEClass(DESIGNER);
		createEAttribute(designerEClass, DESIGNER__LOCKED_PROPERTIES);
		createEAttribute(designerEClass, DESIGNER__FIXED_CHILDREN);
		createEReference(designerEClass, DESIGNER__RESOURCE_DESCRIPTIONS);

		identityEClass = createEClass(IDENTITY);
		createEReference(identityEClass, IDENTITY__DESIGNER);
		createEAttribute(identityEClass, IDENTITY__NAME);
		createEAttribute(identityEClass, IDENTITY__DESCRIPTION);
		createEAttribute(identityEClass, IDENTITY__OBJECT_TYPE);
		createEAttribute(identityEClass, IDENTITY__USERNAME);
		createEAttribute(identityEClass, IDENTITY__PASSWORD);
		createEAttribute(identityEClass, IDENTITY__FILE_TYPE);
		createEAttribute(identityEClass, IDENTITY__URL);
		createEAttribute(identityEClass, IDENTITY__PASS_PHRASE);
		createEAttribute(identityEClass, IDENTITY__CERT_URL);
		createEAttribute(identityEClass, IDENTITY__PRIVATE_KEY_URL);

		idRootEClass = createEClass(ID_ROOT);
		createEAttribute(idRootEClass, ID_ROOT__MIXED);
		createEReference(idRootEClass, ID_ROOT__XMLNS_PREFIX_MAP);
		createEReference(idRootEClass, ID_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(idRootEClass, ID_ROOT__REPOSITORY);

		nodeEClass = createEClass(NODE);
		createEAttribute(nodeEClass, NODE__DESCRIPTION);

		repositoryEClass = createEClass(REPOSITORY);
		createEReference(repositoryEClass, REPOSITORY__IDENTITY);

		resourceDescriptionsEClass = createEClass(RESOURCE_DESCRIPTIONS);
		createEReference(resourceDescriptionsEClass, RESOURCE_DESCRIPTIONS__NODE);

		// Create enums
		fileTypeEnumEEnum = createEEnum(FILE_TYPE_ENUM);
		objectTypeEnumEEnum = createEEnum(OBJECT_TYPE_ENUM);

		// Create data types
		fileTypeEDataType = createEDataType(FILE_TYPE);
		fileTypeEnumObjectEDataType = createEDataType(FILE_TYPE_ENUM_OBJECT);
		objectTypeEDataType = createEDataType(OBJECT_TYPE);
		objectTypeEnumObjectEDataType = createEDataType(OBJECT_TYPE_ENUM_OBJECT);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(designerEClass, Designer.class, "Designer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDesigner_LockedProperties(), theXMLTypePackage.getString(), "lockedProperties", null, 0, 1, Designer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDesigner_FixedChildren(), theXMLTypePackage.getString(), "fixedChildren", null, 0, 1, Designer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDesigner_ResourceDescriptions(), this.getResourceDescriptions(), null, "resourceDescriptions", null, 0, 1, Designer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(identityEClass, Identity.class, "Identity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIdentity_Designer(), this.getDesigner(), null, "designer", null, 0, 1, Identity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentity_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, Identity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentity_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, Identity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentity_ObjectType(), this.getObjectType(), "objectType", null, 0, 1, Identity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentity_Username(), theXMLTypePackage.getString(), "username", null, 0, 1, Identity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentity_Password(), theXMLTypePackage.getString(), "password", null, 0, 1, Identity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentity_FileType(), this.getFileType(), "fileType", null, 0, 1, Identity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentity_Url(), theXMLTypePackage.getString(), "url", null, 0, 1, Identity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentity_PassPhrase(), theXMLTypePackage.getString(), "passPhrase", null, 0, 1, Identity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentity_CertUrl(), theXMLTypePackage.getString(), "certUrl", null, 0, 1, Identity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIdentity_PrivateKeyUrl(), theXMLTypePackage.getString(), "privateKeyUrl", null, 0, 1, Identity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(idRootEClass, IdRoot.class, "IdRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIdRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIdRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIdRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIdRoot_Repository(), this.getRepository(), null, "repository", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(nodeEClass, Node.class, "Node", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNode_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(repositoryEClass, Repository.class, "Repository", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRepository_Identity(), this.getIdentity(), null, "identity", null, 0, 1, Repository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(resourceDescriptionsEClass, ResourceDescriptions.class, "ResourceDescriptions", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getResourceDescriptions_Node(), this.getNode(), null, "node", null, 0, 1, ResourceDescriptions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(fileTypeEnumEEnum, FileTypeEnum.class, "FileTypeEnum");
		addEEnumLiteral(fileTypeEnumEEnum, FileTypeEnum.ENTRUST);
		addEEnumLiteral(fileTypeEnumEEnum, FileTypeEnum.JCEKS);
		addEEnumLiteral(fileTypeEnumEEnum, FileTypeEnum.JKS);
		addEEnumLiteral(fileTypeEnumEEnum, FileTypeEnum.PEM);
		addEEnumLiteral(fileTypeEnumEEnum, FileTypeEnum.PKCS12);
		addEEnumLiteral(fileTypeEnumEEnum, FileTypeEnum._);
		addEEnumLiteral(fileTypeEnumEEnum, FileTypeEnum.SSO);

		initEEnum(objectTypeEnumEEnum, ObjectTypeEnum.class, "ObjectTypeEnum");
		addEEnumLiteral(objectTypeEnumEEnum, ObjectTypeEnum.URL);
		addEEnumLiteral(objectTypeEnumEEnum, ObjectTypeEnum.CERT_PLUS_KEY_URL);
		addEEnumLiteral(objectTypeEnumEEnum, ObjectTypeEnum.USERNAME_PASSWORD);

		// Initialize data types
		initEDataType(fileTypeEDataType, Object.class, "FileType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(fileTypeEnumObjectEDataType, FileTypeEnum.class, "FileTypeEnumObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(objectTypeEDataType, Object.class, "ObjectType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(objectTypeEnumObjectEDataType, ObjectTypeEnum.class, "ObjectTypeEnumObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.w3.org/XML/1998/namespace
		createNamespaceAnnotations();
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.w3.org/XML/1998/namespace</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createNamespaceAnnotations() {
		String source = "http://www.w3.org/XML/1998/namespace";	
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "lang", "en"
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
		  (designerEClass, 
		   source, 
		   new String[] {
			 "name", "designer-type",
			 "kind", "elementOnly"
		   });	
		addAnnotation
		  (getDesigner_LockedProperties(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "lockedProperties"
		   });	
		addAnnotation
		  (getDesigner_FixedChildren(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "fixedChildren"
		   });	
		addAnnotation
		  (getDesigner_ResourceDescriptions(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "resourceDescriptions"
		   });	
		addAnnotation
		  (fileTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "fileType-type",
			 "memberTypes", "http://tibco.com/be/basetypes#gv-type fileType-type_._member_._1"
		   });	
		addAnnotation
		  (fileTypeEnumEEnum, 
		   source, 
		   new String[] {
			 "name", "fileType-type_._member_._1"
		   });	
		addAnnotation
		  (fileTypeEnumObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "fileType-type_._member_._1:Object",
			 "baseType", "fileType-type_._member_._1"
		   });	
		addAnnotation
		  (identityEClass, 
		   source, 
		   new String[] {
			 "name", "identity-type",
			 "kind", "elementOnly"
		   });	
		addAnnotation
		  (getIdentity_Designer(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "designer"
		   });	
		addAnnotation
		  (getIdentity_Name(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "name"
		   });	
		addAnnotation
		  (getIdentity_Description(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "description"
		   });	
		addAnnotation
		  (getIdentity_ObjectType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "objectType"
		   });	
		addAnnotation
		  (getIdentity_Username(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "username"
		   });	
		addAnnotation
		  (getIdentity_Password(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "password"
		   });	
		addAnnotation
		  (getIdentity_FileType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "fileType"
		   });	
		addAnnotation
		  (getIdentity_Url(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "url"
		   });	
		addAnnotation
		  (getIdentity_PassPhrase(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "passPhrase"
		   });	
		addAnnotation
		  (getIdentity_CertUrl(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "certUrl"
		   });	
		addAnnotation
		  (getIdentity_PrivateKeyUrl(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "privateKeyUrl"
		   });	
		addAnnotation
		  (idRootEClass, 
		   source, 
		   new String[] {
			 "name", "",
			 "kind", "mixed"
		   });	
		addAnnotation
		  (getIdRoot_Mixed(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "name", ":mixed"
		   });	
		addAnnotation
		  (getIdRoot_XMLNSPrefixMap(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xmlns:prefix"
		   });	
		addAnnotation
		  (getIdRoot_XSISchemaLocation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xsi:schemaLocation"
		   });	
		addAnnotation
		  (getIdRoot_Repository(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "repository",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (nodeEClass, 
		   source, 
		   new String[] {
			 "name", "node-type",
			 "kind", "elementOnly"
		   });	
		addAnnotation
		  (getNode_Description(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "description"
		   });	
		addAnnotation
		  (objectTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "objectType-type",
			 "memberTypes", "http://tibco.com/be/basetypes#gv-type objectType-type_._member_._1"
		   });	
		addAnnotation
		  (objectTypeEnumEEnum, 
		   source, 
		   new String[] {
			 "name", "objectType-type_._member_._1"
		   });	
		addAnnotation
		  (objectTypeEnumObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "objectType-type_._member_._1:Object",
			 "baseType", "objectType-type_._member_._1"
		   });	
		addAnnotation
		  (repositoryEClass, 
		   source, 
		   new String[] {
			 "name", "repository-type",
			 "kind", "elementOnly"
		   });	
		addAnnotation
		  (getRepository_Identity(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "identity"
		   });	
		addAnnotation
		  (resourceDescriptionsEClass, 
		   source, 
		   new String[] {
			 "name", "resourceDescriptions-type",
			 "kind", "elementOnly"
		   });	
		addAnnotation
		  (getResourceDescriptions_Node(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "node"
		   });
	}

} //IdPackageImpl
