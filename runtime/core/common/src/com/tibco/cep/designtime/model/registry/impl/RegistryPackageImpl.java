/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.model.registry.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.designtime.model.registry.ElementTypes;
import com.tibco.cep.designtime.model.registry.FileExtensionMapItemType;
import com.tibco.cep.designtime.model.registry.FileExtensionMapType;
import com.tibco.cep.designtime.model.registry.FileExtensionTypes;
import com.tibco.cep.designtime.model.registry.RegistryFactory;
import com.tibco.cep.designtime.model.registry.RegistryPackage;
import com.tibco.cep.designtime.model.registry.SupportedElementTypes;
import com.tibco.cep.designtime.model.registry.SupportedExtensions;
import com.tibco.cep.designtime.model.registry.TnsEntityExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RegistryPackageImpl extends EPackageImpl implements RegistryPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass addOnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fileExtensionMapItemTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fileExtensionMapTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass registryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass supportedElementTypesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass supportedExtensionsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tnsEntityExtensionsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum addOnTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum elementTypesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum fileExtensionTypesEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType addOnTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType elementTypesObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType fileExtensionTypesObjectEDataType = null;

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
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RegistryPackageImpl() {
		super(eNS_URI, RegistryFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link RegistryPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static RegistryPackage init() {
		if (isInited) return (RegistryPackage)EPackage.Registry.INSTANCE.getEPackage(RegistryPackage.eNS_URI);

		// Obtain or create and register package
		RegistryPackageImpl theRegistryPackage = (RegistryPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof RegistryPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new RegistryPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theRegistryPackage.createPackageContents();

		// Initialize created meta-data
		theRegistryPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRegistryPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RegistryPackage.eNS_URI, theRegistryPackage);
		return theRegistryPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAddOn() {
		return addOnEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAddOn_Type() {
		return (EAttribute)addOnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAddOn_IndexResourceProviderClass() {
		return (EAttribute)addOnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAddOn_IndexCacheClass() {
		return (EAttribute)addOnEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAddOn_OntologyAdapterClass() {
		return (EAttribute)addOnEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAddOn_AdapterFactoryClass() {
		return (EAttribute)addOnEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAddOn_TnsEntityHandlerClass() {
		return (EAttribute)addOnEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAddOn_CddAgentClass() {
		return (EAttribute)addOnEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAddOn_SupportedEntityTypes() {
		return (EReference)addOnEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAddOn_SupportedExtensions() {
		return (EReference)addOnEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAddOn_FileExtensionMap() {
		return (EReference)addOnEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAddOn_TnsEntityExtensions() {
		return (EReference)addOnEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAddOn_FunctionCatalogProvider() {
		return (EAttribute)addOnEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFileExtensionMapItemType() {
		return fileExtensionMapItemTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFileExtensionMapItemType_Extension() {
		return (EAttribute)fileExtensionMapItemTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFileExtensionMapItemType_ElementType() {
		return (EReference)fileExtensionMapItemTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFileExtensionMapType() {
		return fileExtensionMapTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFileExtensionMapType_Item() {
		return (EReference)fileExtensionMapTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRegistry() {
		return registryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRegistry_Mixed() {
		return (EAttribute)registryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRegistry_XMLNSPrefixMap() {
		return (EReference)registryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRegistry_XSISchemaLocation() {
		return (EReference)registryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRegistry_AddOn() {
		return (EReference)registryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSupportedElementTypes() {
		return supportedElementTypesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSupportedElementTypes_ElementType() {
		return (EAttribute)supportedElementTypesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSupportedExtensions() {
		return supportedExtensionsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSupportedExtensions_Extension() {
		return (EAttribute)supportedExtensionsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTnsEntityExtensions() {
		return tnsEntityExtensionsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTnsEntityExtensions_Extension() {
		return (EAttribute)tnsEntityExtensionsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getAddOnType() {
		return addOnTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getElementTypes() {
		return elementTypesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getFileExtensionTypes() {
		return fileExtensionTypesEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getAddOnTypeObject() {
		return addOnTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getElementTypesObject() {
		return elementTypesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getFileExtensionTypesObject() {
		return fileExtensionTypesObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RegistryFactory getRegistryFactory() {
		return (RegistryFactory)getEFactoryInstance();
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
		addOnEClass = createEClass(ADD_ON);
		createEAttribute(addOnEClass, ADD_ON__TYPE);
		createEAttribute(addOnEClass, ADD_ON__INDEX_RESOURCE_PROVIDER_CLASS);
		createEAttribute(addOnEClass, ADD_ON__INDEX_CACHE_CLASS);
		createEAttribute(addOnEClass, ADD_ON__ONTOLOGY_ADAPTER_CLASS);
		createEAttribute(addOnEClass, ADD_ON__ADAPTER_FACTORY_CLASS);
		createEAttribute(addOnEClass, ADD_ON__TNS_ENTITY_HANDLER_CLASS);
		createEAttribute(addOnEClass, ADD_ON__CDD_AGENT_CLASS);
		createEReference(addOnEClass, ADD_ON__SUPPORTED_ENTITY_TYPES);
		createEReference(addOnEClass, ADD_ON__SUPPORTED_EXTENSIONS);
		createEReference(addOnEClass, ADD_ON__FILE_EXTENSION_MAP);
		createEReference(addOnEClass, ADD_ON__TNS_ENTITY_EXTENSIONS);
		createEAttribute(addOnEClass, ADD_ON__FUNCTION_CATALOG_PROVIDER);

		fileExtensionMapItemTypeEClass = createEClass(FILE_EXTENSION_MAP_ITEM_TYPE);
		createEAttribute(fileExtensionMapItemTypeEClass, FILE_EXTENSION_MAP_ITEM_TYPE__EXTENSION);
		createEReference(fileExtensionMapItemTypeEClass, FILE_EXTENSION_MAP_ITEM_TYPE__ELEMENT_TYPE);

		fileExtensionMapTypeEClass = createEClass(FILE_EXTENSION_MAP_TYPE);
		createEReference(fileExtensionMapTypeEClass, FILE_EXTENSION_MAP_TYPE__ITEM);

		registryEClass = createEClass(REGISTRY);
		createEAttribute(registryEClass, REGISTRY__MIXED);
		createEReference(registryEClass, REGISTRY__XMLNS_PREFIX_MAP);
		createEReference(registryEClass, REGISTRY__XSI_SCHEMA_LOCATION);
		createEReference(registryEClass, REGISTRY__ADD_ON);

		supportedElementTypesEClass = createEClass(SUPPORTED_ELEMENT_TYPES);
		createEAttribute(supportedElementTypesEClass, SUPPORTED_ELEMENT_TYPES__ELEMENT_TYPE);

		supportedExtensionsEClass = createEClass(SUPPORTED_EXTENSIONS);
		createEAttribute(supportedExtensionsEClass, SUPPORTED_EXTENSIONS__EXTENSION);

		tnsEntityExtensionsEClass = createEClass(TNS_ENTITY_EXTENSIONS);
		createEAttribute(tnsEntityExtensionsEClass, TNS_ENTITY_EXTENSIONS__EXTENSION);

		// Create enums
		addOnTypeEEnum = createEEnum(ADD_ON_TYPE);
		elementTypesEEnum = createEEnum(ELEMENT_TYPES);
		fileExtensionTypesEEnum = createEEnum(FILE_EXTENSION_TYPES);

		// Create data types
		addOnTypeObjectEDataType = createEDataType(ADD_ON_TYPE_OBJECT);
		elementTypesObjectEDataType = createEDataType(ELEMENT_TYPES_OBJECT);
		fileExtensionTypesObjectEDataType = createEDataType(FILE_EXTENSION_TYPES_OBJECT);
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
		initEClass(addOnEClass, AddOn.class, "AddOn", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAddOn_Type(), this.getAddOnType(), "type", null, 1, 1, AddOn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddOn_IndexResourceProviderClass(), theXMLTypePackage.getString(), "indexResourceProviderClass", null, 1, 1, AddOn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddOn_IndexCacheClass(), theXMLTypePackage.getString(), "indexCacheClass", null, 1, 1, AddOn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddOn_OntologyAdapterClass(), theXMLTypePackage.getString(), "ontologyAdapterClass", null, 1, 1, AddOn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddOn_AdapterFactoryClass(), theXMLTypePackage.getString(), "adapterFactoryClass", null, 1, 1, AddOn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddOn_TnsEntityHandlerClass(), theXMLTypePackage.getString(), "tnsEntityHandlerClass", null, 1, 1, AddOn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddOn_CddAgentClass(), theXMLTypePackage.getString(), "cddAgentClass", null, 1, 1, AddOn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAddOn_SupportedEntityTypes(), this.getSupportedElementTypes(), null, "supportedEntityTypes", null, 1, 1, AddOn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAddOn_SupportedExtensions(), this.getSupportedExtensions(), null, "supportedExtensions", null, 1, 1, AddOn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAddOn_FileExtensionMap(), this.getFileExtensionMapType(), null, "fileExtensionMap", null, 1, -1, AddOn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAddOn_TnsEntityExtensions(), this.getTnsEntityExtensions(), null, "tnsEntityExtensions", null, 1, 1, AddOn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAddOn_FunctionCatalogProvider(), theXMLTypePackage.getString(), "functionCatalogProvider", null, 1, 1, AddOn.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fileExtensionMapItemTypeEClass, FileExtensionMapItemType.class, "FileExtensionMapItemType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFileExtensionMapItemType_Extension(), this.getFileExtensionTypes(), "extension", null, 1, 1, FileExtensionMapItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFileExtensionMapItemType_ElementType(), this.getSupportedElementTypes(), null, "elementType", null, 1, 1, FileExtensionMapItemType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fileExtensionMapTypeEClass, FileExtensionMapType.class, "FileExtensionMapType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFileExtensionMapType_Item(), this.getFileExtensionMapItemType(), null, "item", null, 0, -1, FileExtensionMapType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(registryEClass, com.tibco.cep.designtime.model.registry.Registry.class, "Registry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRegistry_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRegistry_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRegistry_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRegistry_AddOn(), this.getAddOn(), null, "addOn", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(supportedElementTypesEClass, SupportedElementTypes.class, "SupportedElementTypes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSupportedElementTypes_ElementType(), this.getElementTypes(), "elementType", null, 1, -1, SupportedElementTypes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(supportedExtensionsEClass, SupportedExtensions.class, "SupportedExtensions", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSupportedExtensions_Extension(), this.getFileExtensionTypes(), "extension", null, 1, -1, SupportedExtensions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tnsEntityExtensionsEClass, TnsEntityExtensions.class, "TnsEntityExtensions", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTnsEntityExtensions_Extension(), this.getFileExtensionTypes(), "extension", null, 0, -1, TnsEntityExtensions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(addOnTypeEEnum, AddOnType.class, "AddOnType");
		addEEnumLiteral(addOnTypeEEnum, AddOnType.CORE);
		addEEnumLiteral(addOnTypeEEnum, AddOnType.DATAMODELLING);
		addEEnumLiteral(addOnTypeEEnum, AddOnType.EVENTSTREAMPROCESSING);
		addEEnumLiteral(addOnTypeEEnum, AddOnType.DECISIONMANAGER);
		addEEnumLiteral(addOnTypeEEnum, AddOnType.VIEWS);
		addEEnumLiteral(addOnTypeEEnum, AddOnType.PROCESS);

		initEEnum(elementTypesEEnum, ElementTypes.class, "ElementTypes");
		addEEnumLiteral(elementTypesEEnum, ElementTypes.UNKNOWN);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.CONCEPT);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.SCORECARD);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.INSTANCE);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.SIMPLEEVENT);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.METRIC);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.CHANNEL);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.DESTINATION);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.RULE);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.RULEFUNCTION);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.XSLT_FUNCTION);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.RULESET);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.STATEMACHINE);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.TIMEEVENT);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.ENTERPRISEARCHIVE);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.BEARCHIVERESOURCE);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.DOMAIN);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.DECISIONTABLE);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.FOLDER);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.RULETEMPLATE);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.QUERY);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.VIEW);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.PROCESS);
		addEEnumLiteral(elementTypesEEnum, ElementTypes.JAVA_SOURCE);

		initEEnum(fileExtensionTypesEEnum, FileExtensionTypes.class, "FileExtensionTypes");
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.RULE_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.RULEFUNCTION_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.XSLT_FUNCTION_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.RULE_FN_IMPL_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.RULE_TEMPLATE_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.RULE_TEMPLATE_VIEW_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.EAR_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.TIME_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.STATEMACHINE_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.JAVA_SOURCE_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.CONCEPT_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.EVENT_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.SCORECARD_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.AESCHEMA_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.XSD_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.DTD_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.WSDL_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.DOMAIN_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.CHANNEL_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.GLOBAL_VAR_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.QUERY_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.BE_QUERY_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.METRIC_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.PROCESS_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.SITE_TOPOLOGY_EXTENSION);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.CHART);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.SMCOMPONENT);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.PAGE_SELECTOR);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.ALERT);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.CONTEXT_ACTION_RULESET);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.BLUEPRINT);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.QUERY_MANAGER);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.SEARCH_VIEW);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.RELATED_ASSETS);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.DRILLDOWN);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.VIEW);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.DASHBOARDPAGE);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.ASSETPAGE);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.SEARCHPAGE);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.PAGESET);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.SERIES_COLOR);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.TEXT_COLORSET);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.CHART_COLORSET);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.HEADER);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.LOGIN);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.SKIN);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.ROLEPREFERENCE);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.DATASOURCE);
		addEEnumLiteral(fileExtensionTypesEEnum, FileExtensionTypes.SYSTEM);

		// Initialize data types
		initEDataType(addOnTypeObjectEDataType, AddOnType.class, "AddOnTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(elementTypesObjectEDataType, ElementTypes.class, "ElementTypesObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(fileExtensionTypesObjectEDataType, FileExtensionTypes.class, "FileExtensionTypesObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
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
		  (addOnEClass, 
		   source, 
		   new String[] {
			 "name", "addon",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getAddOn_Type(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "type"
		   });		
		addAnnotation
		  (getAddOn_IndexResourceProviderClass(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "indexResourceProviderClass"
		   });		
		addAnnotation
		  (getAddOn_IndexCacheClass(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "indexCacheClass"
		   });		
		addAnnotation
		  (getAddOn_OntologyAdapterClass(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ontologyAdapterClass"
		   });		
		addAnnotation
		  (getAddOn_AdapterFactoryClass(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "adapterFactoryClass"
		   });		
		addAnnotation
		  (getAddOn_TnsEntityHandlerClass(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "tnsEntityHandlerClass"
		   });		
		addAnnotation
		  (getAddOn_CddAgentClass(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "cddAgentClass"
		   });		
		addAnnotation
		  (getAddOn_SupportedEntityTypes(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "supportedTypes"
		   });		
		addAnnotation
		  (getAddOn_SupportedExtensions(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "supportedExtensions"
		   });		
		addAnnotation
		  (getAddOn_FileExtensionMap(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "fileExtensionMap"
		   });		
		addAnnotation
		  (getAddOn_TnsEntityExtensions(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "tnsEntityExtensions"
		   });		
		addAnnotation
		  (getAddOn_FunctionCatalogProvider(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "functionCatalogProvider"
		   });		
		addAnnotation
		  (addOnTypeEEnum, 
		   source, 
		   new String[] {
			 "name", "AddOnType"
		   });		
		addAnnotation
		  (addOnTypeObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "AddOnType:Object",
			 "baseType", "AddOnType"
		   });		
		addAnnotation
		  (elementTypesEEnum, 
		   source, 
		   new String[] {
			 "name", "ElementTypes"
		   });		
		addAnnotation
		  (elementTypesObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "ElementTypes:Object",
			 "baseType", "ElementTypes"
		   });		
		addAnnotation
		  (fileExtensionMapItemTypeEClass, 
		   source, 
		   new String[] {
			 "name", "FileExtensionMapItemType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getFileExtensionMapItemType_Extension(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "key"
		   });		
		addAnnotation
		  (getFileExtensionMapItemType_ElementType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "value"
		   });		
		addAnnotation
		  (fileExtensionMapTypeEClass, 
		   source, 
		   new String[] {
			 "name", "fileExtensionMapType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getFileExtensionMapType_Item(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "item"
		   });		
		addAnnotation
		  (fileExtensionTypesEEnum, 
		   source, 
		   new String[] {
			 "name", "FileExtensionTypes"
		   });		
		addAnnotation
		  (fileExtensionTypesObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "FileExtensionTypes:Object",
			 "baseType", "FileExtensionTypes"
		   });		
		addAnnotation
		  (registryEClass, 
		   source, 
		   new String[] {
			 "name", "",
			 "kind", "mixed"
		   });		
		addAnnotation
		  (getRegistry_Mixed(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "name", ":mixed"
		   });		
		addAnnotation
		  (getRegistry_XMLNSPrefixMap(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xmlns:prefix"
		   });		
		addAnnotation
		  (getRegistry_XSISchemaLocation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xsi:schemaLocation"
		   });		
		addAnnotation
		  (getRegistry_AddOn(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "addon",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (supportedElementTypesEClass, 
		   source, 
		   new String[] {
			 "name", "supportedElementTypes",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getSupportedElementTypes_ElementType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "elementType"
		   });		
		addAnnotation
		  (supportedExtensionsEClass, 
		   source, 
		   new String[] {
			 "name", "supportedExtensions",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getSupportedExtensions_Extension(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "extension"
		   });		
		addAnnotation
		  (tnsEntityExtensionsEClass, 
		   source, 
		   new String[] {
			 "name", "tnsEntityExtensions",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getTnsEntityExtensions_Extension(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "extension"
		   });
	}

} //RegistryPackageImpl
