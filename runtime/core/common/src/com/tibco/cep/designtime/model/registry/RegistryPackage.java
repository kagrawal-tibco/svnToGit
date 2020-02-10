/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.model.registry;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.model.registry.RegistryFactory
 * @model kind="package"
 * @generated
 */
public interface RegistryPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "registry";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///www.tibco.com/be/addon_registry.xsd";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "reg";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RegistryPackage eINSTANCE = com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.model.registry.impl.AddOnImpl <em>Add On</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.model.registry.impl.AddOnImpl
	 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getAddOn()
	 * @generated
	 */
	int ADD_ON = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_ON__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Index Resource Provider Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_ON__INDEX_RESOURCE_PROVIDER_CLASS = 1;

	/**
	 * The feature id for the '<em><b>Index Cache Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_ON__INDEX_CACHE_CLASS = 2;

	/**
	 * The feature id for the '<em><b>Ontology Adapter Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_ON__ONTOLOGY_ADAPTER_CLASS = 3;

	/**
	 * The feature id for the '<em><b>Adapter Factory Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_ON__ADAPTER_FACTORY_CLASS = 4;

	/**
	 * The feature id for the '<em><b>Tns Entity Handler Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_ON__TNS_ENTITY_HANDLER_CLASS = 5;

	/**
	 * The feature id for the '<em><b>Cdd Agent Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_ON__CDD_AGENT_CLASS = 6;

	/**
	 * The feature id for the '<em><b>Supported Entity Types</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_ON__SUPPORTED_ENTITY_TYPES = 7;

	/**
	 * The feature id for the '<em><b>Supported Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_ON__SUPPORTED_EXTENSIONS = 8;

	/**
	 * The feature id for the '<em><b>File Extension Map</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_ON__FILE_EXTENSION_MAP = 9;

	/**
	 * The feature id for the '<em><b>Tns Entity Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_ON__TNS_ENTITY_EXTENSIONS = 10;

	/**
	 * The feature id for the '<em><b>Function Catalog Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_ON__FUNCTION_CATALOG_PROVIDER = 11;

	/**
	 * The number of structural features of the '<em>Add On</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_ON_FEATURE_COUNT = 12;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.model.registry.impl.FileExtensionMapItemTypeImpl <em>File Extension Map Item Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.model.registry.impl.FileExtensionMapItemTypeImpl
	 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getFileExtensionMapItemType()
	 * @generated
	 */
	int FILE_EXTENSION_MAP_ITEM_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_EXTENSION_MAP_ITEM_TYPE__EXTENSION = 0;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_EXTENSION_MAP_ITEM_TYPE__ELEMENT_TYPE = 1;

	/**
	 * The number of structural features of the '<em>File Extension Map Item Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_EXTENSION_MAP_ITEM_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.model.registry.impl.FileExtensionMapTypeImpl <em>File Extension Map Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.model.registry.impl.FileExtensionMapTypeImpl
	 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getFileExtensionMapType()
	 * @generated
	 */
	int FILE_EXTENSION_MAP_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Item</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_EXTENSION_MAP_TYPE__ITEM = 0;

	/**
	 * The number of structural features of the '<em>File Extension Map Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_EXTENSION_MAP_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.model.registry.impl.RegistryImpl <em>Registry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.model.registry.impl.RegistryImpl
	 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getRegistry()
	 * @generated
	 */
	int REGISTRY = 3;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGISTRY__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGISTRY__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGISTRY__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Add On</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGISTRY__ADD_ON = 3;

	/**
	 * The number of structural features of the '<em>Registry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGISTRY_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.model.registry.impl.SupportedElementTypesImpl <em>Supported Element Types</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.model.registry.impl.SupportedElementTypesImpl
	 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getSupportedElementTypes()
	 * @generated
	 */
	int SUPPORTED_ELEMENT_TYPES = 4;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPPORTED_ELEMENT_TYPES__ELEMENT_TYPE = 0;

	/**
	 * The number of structural features of the '<em>Supported Element Types</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPPORTED_ELEMENT_TYPES_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.model.registry.impl.SupportedExtensionsImpl <em>Supported Extensions</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.model.registry.impl.SupportedExtensionsImpl
	 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getSupportedExtensions()
	 * @generated
	 */
	int SUPPORTED_EXTENSIONS = 5;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPPORTED_EXTENSIONS__EXTENSION = 0;

	/**
	 * The number of structural features of the '<em>Supported Extensions</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPPORTED_EXTENSIONS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.model.registry.impl.TnsEntityExtensionsImpl <em>Tns Entity Extensions</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.model.registry.impl.TnsEntityExtensionsImpl
	 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getTnsEntityExtensions()
	 * @generated
	 */
	int TNS_ENTITY_EXTENSIONS = 6;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TNS_ENTITY_EXTENSIONS__EXTENSION = 0;

	/**
	 * The number of structural features of the '<em>Tns Entity Extensions</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TNS_ENTITY_EXTENSIONS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.model.registry.AddOnType <em>Add On Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.model.registry.AddOnType
	 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getAddOnType()
	 * @generated
	 */
	int ADD_ON_TYPE = 7;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.model.registry.ElementTypes <em>Element Types</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.model.registry.ElementTypes
	 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getElementTypes()
	 * @generated
	 */
	int ELEMENT_TYPES = 8;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.model.registry.FileExtensionTypes <em>File Extension Types</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.model.registry.FileExtensionTypes
	 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getFileExtensionTypes()
	 * @generated
	 */
	int FILE_EXTENSION_TYPES = 9;

	/**
	 * The meta object id for the '<em>Add On Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.model.registry.AddOnType
	 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getAddOnTypeObject()
	 * @generated
	 */
	int ADD_ON_TYPE_OBJECT = 10;

	/**
	 * The meta object id for the '<em>Element Types Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.model.registry.ElementTypes
	 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getElementTypesObject()
	 * @generated
	 */
	int ELEMENT_TYPES_OBJECT = 11;

	/**
	 * The meta object id for the '<em>File Extension Types Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.model.registry.FileExtensionTypes
	 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getFileExtensionTypesObject()
	 * @generated
	 */
	int FILE_EXTENSION_TYPES_OBJECT = 12;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.model.registry.AddOn <em>Add On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Add On</em>'.
	 * @see com.tibco.cep.designtime.model.registry.AddOn
	 * @generated
	 */
	EClass getAddOn();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.model.registry.AddOn#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.cep.designtime.model.registry.AddOn#getType()
	 * @see #getAddOn()
	 * @generated
	 */
	EAttribute getAddOn_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.model.registry.AddOn#getIndexResourceProviderClass <em>Index Resource Provider Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index Resource Provider Class</em>'.
	 * @see com.tibco.cep.designtime.model.registry.AddOn#getIndexResourceProviderClass()
	 * @see #getAddOn()
	 * @generated
	 */
	EAttribute getAddOn_IndexResourceProviderClass();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.model.registry.AddOn#getIndexCacheClass <em>Index Cache Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index Cache Class</em>'.
	 * @see com.tibco.cep.designtime.model.registry.AddOn#getIndexCacheClass()
	 * @see #getAddOn()
	 * @generated
	 */
	EAttribute getAddOn_IndexCacheClass();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.model.registry.AddOn#getOntologyAdapterClass <em>Ontology Adapter Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ontology Adapter Class</em>'.
	 * @see com.tibco.cep.designtime.model.registry.AddOn#getOntologyAdapterClass()
	 * @see #getAddOn()
	 * @generated
	 */
	EAttribute getAddOn_OntologyAdapterClass();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.model.registry.AddOn#getAdapterFactoryClass <em>Adapter Factory Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Adapter Factory Class</em>'.
	 * @see com.tibco.cep.designtime.model.registry.AddOn#getAdapterFactoryClass()
	 * @see #getAddOn()
	 * @generated
	 */
	EAttribute getAddOn_AdapterFactoryClass();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.model.registry.AddOn#getTnsEntityHandlerClass <em>Tns Entity Handler Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tns Entity Handler Class</em>'.
	 * @see com.tibco.cep.designtime.model.registry.AddOn#getTnsEntityHandlerClass()
	 * @see #getAddOn()
	 * @generated
	 */
	EAttribute getAddOn_TnsEntityHandlerClass();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.model.registry.AddOn#getCddAgentClass <em>Cdd Agent Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cdd Agent Class</em>'.
	 * @see com.tibco.cep.designtime.model.registry.AddOn#getCddAgentClass()
	 * @see #getAddOn()
	 * @generated
	 */
	EAttribute getAddOn_CddAgentClass();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.model.registry.AddOn#getSupportedEntityTypes <em>Supported Entity Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Supported Entity Types</em>'.
	 * @see com.tibco.cep.designtime.model.registry.AddOn#getSupportedEntityTypes()
	 * @see #getAddOn()
	 * @generated
	 */
	EReference getAddOn_SupportedEntityTypes();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.model.registry.AddOn#getSupportedExtensions <em>Supported Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Supported Extensions</em>'.
	 * @see com.tibco.cep.designtime.model.registry.AddOn#getSupportedExtensions()
	 * @see #getAddOn()
	 * @generated
	 */
	EReference getAddOn_SupportedExtensions();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.model.registry.AddOn#getFileExtensionMap <em>File Extension Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>File Extension Map</em>'.
	 * @see com.tibco.cep.designtime.model.registry.AddOn#getFileExtensionMap()
	 * @see #getAddOn()
	 * @generated
	 */
	EReference getAddOn_FileExtensionMap();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.model.registry.AddOn#getTnsEntityExtensions <em>Tns Entity Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Tns Entity Extensions</em>'.
	 * @see com.tibco.cep.designtime.model.registry.AddOn#getTnsEntityExtensions()
	 * @see #getAddOn()
	 * @generated
	 */
	EReference getAddOn_TnsEntityExtensions();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.model.registry.AddOn#getFunctionCatalogProvider <em>Function Catalog Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Function Catalog Provider</em>'.
	 * @see com.tibco.cep.designtime.model.registry.AddOn#getFunctionCatalogProvider()
	 * @see #getAddOn()
	 * @generated
	 */
	EAttribute getAddOn_FunctionCatalogProvider();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.model.registry.FileExtensionMapItemType <em>File Extension Map Item Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>File Extension Map Item Type</em>'.
	 * @see com.tibco.cep.designtime.model.registry.FileExtensionMapItemType
	 * @generated
	 */
	EClass getFileExtensionMapItemType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.model.registry.FileExtensionMapItemType#getExtension <em>Extension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Extension</em>'.
	 * @see com.tibco.cep.designtime.model.registry.FileExtensionMapItemType#getExtension()
	 * @see #getFileExtensionMapItemType()
	 * @generated
	 */
	EAttribute getFileExtensionMapItemType_Extension();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.model.registry.FileExtensionMapItemType#getElementType <em>Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Element Type</em>'.
	 * @see com.tibco.cep.designtime.model.registry.FileExtensionMapItemType#getElementType()
	 * @see #getFileExtensionMapItemType()
	 * @generated
	 */
	EReference getFileExtensionMapItemType_ElementType();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.model.registry.FileExtensionMapType <em>File Extension Map Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>File Extension Map Type</em>'.
	 * @see com.tibco.cep.designtime.model.registry.FileExtensionMapType
	 * @generated
	 */
	EClass getFileExtensionMapType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.model.registry.FileExtensionMapType#getItem <em>Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Item</em>'.
	 * @see com.tibco.cep.designtime.model.registry.FileExtensionMapType#getItem()
	 * @see #getFileExtensionMapType()
	 * @generated
	 */
	EReference getFileExtensionMapType_Item();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.model.registry.Registry <em>Registry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Registry</em>'.
	 * @see com.tibco.cep.designtime.model.registry.Registry
	 * @generated
	 */
	EClass getRegistry();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.model.registry.Registry#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.cep.designtime.model.registry.Registry#getMixed()
	 * @see #getRegistry()
	 * @generated
	 */
	EAttribute getRegistry_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.tibco.cep.designtime.model.registry.Registry#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.tibco.cep.designtime.model.registry.Registry#getXMLNSPrefixMap()
	 * @see #getRegistry()
	 * @generated
	 */
	EReference getRegistry_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.tibco.cep.designtime.model.registry.Registry#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.tibco.cep.designtime.model.registry.Registry#getXSISchemaLocation()
	 * @see #getRegistry()
	 * @generated
	 */
	EReference getRegistry_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.model.registry.Registry#getAddOn <em>Add On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Add On</em>'.
	 * @see com.tibco.cep.designtime.model.registry.Registry#getAddOn()
	 * @see #getRegistry()
	 * @generated
	 */
	EReference getRegistry_AddOn();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.model.registry.SupportedElementTypes <em>Supported Element Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Supported Element Types</em>'.
	 * @see com.tibco.cep.designtime.model.registry.SupportedElementTypes
	 * @generated
	 */
	EClass getSupportedElementTypes();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.model.registry.SupportedElementTypes#getElementType <em>Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Element Type</em>'.
	 * @see com.tibco.cep.designtime.model.registry.SupportedElementTypes#getElementType()
	 * @see #getSupportedElementTypes()
	 * @generated
	 */
	EAttribute getSupportedElementTypes_ElementType();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.model.registry.SupportedExtensions <em>Supported Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Supported Extensions</em>'.
	 * @see com.tibco.cep.designtime.model.registry.SupportedExtensions
	 * @generated
	 */
	EClass getSupportedExtensions();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.model.registry.SupportedExtensions#getExtension <em>Extension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Extension</em>'.
	 * @see com.tibco.cep.designtime.model.registry.SupportedExtensions#getExtension()
	 * @see #getSupportedExtensions()
	 * @generated
	 */
	EAttribute getSupportedExtensions_Extension();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.model.registry.TnsEntityExtensions <em>Tns Entity Extensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tns Entity Extensions</em>'.
	 * @see com.tibco.cep.designtime.model.registry.TnsEntityExtensions
	 * @generated
	 */
	EClass getTnsEntityExtensions();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.designtime.model.registry.TnsEntityExtensions#getExtension <em>Extension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Extension</em>'.
	 * @see com.tibco.cep.designtime.model.registry.TnsEntityExtensions#getExtension()
	 * @see #getTnsEntityExtensions()
	 * @generated
	 */
	EAttribute getTnsEntityExtensions_Extension();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.model.registry.AddOnType <em>Add On Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Add On Type</em>'.
	 * @see com.tibco.cep.designtime.model.registry.AddOnType
	 * @generated
	 */
	EEnum getAddOnType();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.model.registry.ElementTypes <em>Element Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Element Types</em>'.
	 * @see com.tibco.cep.designtime.model.registry.ElementTypes
	 * @generated
	 */
	EEnum getElementTypes();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.model.registry.FileExtensionTypes <em>File Extension Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>File Extension Types</em>'.
	 * @see com.tibco.cep.designtime.model.registry.FileExtensionTypes
	 * @generated
	 */
	EEnum getFileExtensionTypes();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.model.registry.AddOnType <em>Add On Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Add On Type Object</em>'.
	 * @see com.tibco.cep.designtime.model.registry.AddOnType
	 * @model instanceClass="com.tibco.cep.designtime.model.registry.AddOnType"
	 *        extendedMetaData="name='AddOnType:Object' baseType='AddOnType'"
	 * @generated
	 */
	EDataType getAddOnTypeObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.model.registry.ElementTypes <em>Element Types Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Element Types Object</em>'.
	 * @see com.tibco.cep.designtime.model.registry.ElementTypes
	 * @model instanceClass="com.tibco.cep.designtime.model.registry.ElementTypes"
	 *        extendedMetaData="name='ElementTypes:Object' baseType='ElementTypes'"
	 * @generated
	 */
	EDataType getElementTypesObject();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.designtime.model.registry.FileExtensionTypes <em>File Extension Types Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>File Extension Types Object</em>'.
	 * @see com.tibco.cep.designtime.model.registry.FileExtensionTypes
	 * @model instanceClass="com.tibco.cep.designtime.model.registry.FileExtensionTypes"
	 *        extendedMetaData="name='FileExtensionTypes:Object' baseType='FileExtensionTypes'"
	 * @generated
	 */
	EDataType getFileExtensionTypesObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RegistryFactory getRegistryFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.model.registry.impl.AddOnImpl <em>Add On</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.model.registry.impl.AddOnImpl
		 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getAddOn()
		 * @generated
		 */
		EClass ADD_ON = eINSTANCE.getAddOn();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADD_ON__TYPE = eINSTANCE.getAddOn_Type();

		/**
		 * The meta object literal for the '<em><b>Index Resource Provider Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADD_ON__INDEX_RESOURCE_PROVIDER_CLASS = eINSTANCE.getAddOn_IndexResourceProviderClass();

		/**
		 * The meta object literal for the '<em><b>Index Cache Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADD_ON__INDEX_CACHE_CLASS = eINSTANCE.getAddOn_IndexCacheClass();

		/**
		 * The meta object literal for the '<em><b>Ontology Adapter Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADD_ON__ONTOLOGY_ADAPTER_CLASS = eINSTANCE.getAddOn_OntologyAdapterClass();

		/**
		 * The meta object literal for the '<em><b>Adapter Factory Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADD_ON__ADAPTER_FACTORY_CLASS = eINSTANCE.getAddOn_AdapterFactoryClass();

		/**
		 * The meta object literal for the '<em><b>Tns Entity Handler Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADD_ON__TNS_ENTITY_HANDLER_CLASS = eINSTANCE.getAddOn_TnsEntityHandlerClass();

		/**
		 * The meta object literal for the '<em><b>Cdd Agent Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADD_ON__CDD_AGENT_CLASS = eINSTANCE.getAddOn_CddAgentClass();

		/**
		 * The meta object literal for the '<em><b>Supported Entity Types</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADD_ON__SUPPORTED_ENTITY_TYPES = eINSTANCE.getAddOn_SupportedEntityTypes();

		/**
		 * The meta object literal for the '<em><b>Supported Extensions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADD_ON__SUPPORTED_EXTENSIONS = eINSTANCE.getAddOn_SupportedExtensions();

		/**
		 * The meta object literal for the '<em><b>File Extension Map</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADD_ON__FILE_EXTENSION_MAP = eINSTANCE.getAddOn_FileExtensionMap();

		/**
		 * The meta object literal for the '<em><b>Tns Entity Extensions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADD_ON__TNS_ENTITY_EXTENSIONS = eINSTANCE.getAddOn_TnsEntityExtensions();

		/**
		 * The meta object literal for the '<em><b>Function Catalog Provider</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADD_ON__FUNCTION_CATALOG_PROVIDER = eINSTANCE.getAddOn_FunctionCatalogProvider();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.model.registry.impl.FileExtensionMapItemTypeImpl <em>File Extension Map Item Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.model.registry.impl.FileExtensionMapItemTypeImpl
		 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getFileExtensionMapItemType()
		 * @generated
		 */
		EClass FILE_EXTENSION_MAP_ITEM_TYPE = eINSTANCE.getFileExtensionMapItemType();

		/**
		 * The meta object literal for the '<em><b>Extension</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE_EXTENSION_MAP_ITEM_TYPE__EXTENSION = eINSTANCE.getFileExtensionMapItemType_Extension();

		/**
		 * The meta object literal for the '<em><b>Element Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FILE_EXTENSION_MAP_ITEM_TYPE__ELEMENT_TYPE = eINSTANCE.getFileExtensionMapItemType_ElementType();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.model.registry.impl.FileExtensionMapTypeImpl <em>File Extension Map Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.model.registry.impl.FileExtensionMapTypeImpl
		 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getFileExtensionMapType()
		 * @generated
		 */
		EClass FILE_EXTENSION_MAP_TYPE = eINSTANCE.getFileExtensionMapType();

		/**
		 * The meta object literal for the '<em><b>Item</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FILE_EXTENSION_MAP_TYPE__ITEM = eINSTANCE.getFileExtensionMapType_Item();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.model.registry.impl.RegistryImpl <em>Registry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.model.registry.impl.RegistryImpl
		 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getRegistry()
		 * @generated
		 */
		EClass REGISTRY = eINSTANCE.getRegistry();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REGISTRY__MIXED = eINSTANCE.getRegistry_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REGISTRY__XMLNS_PREFIX_MAP = eINSTANCE.getRegistry_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REGISTRY__XSI_SCHEMA_LOCATION = eINSTANCE.getRegistry_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Add On</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REGISTRY__ADD_ON = eINSTANCE.getRegistry_AddOn();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.model.registry.impl.SupportedElementTypesImpl <em>Supported Element Types</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.model.registry.impl.SupportedElementTypesImpl
		 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getSupportedElementTypes()
		 * @generated
		 */
		EClass SUPPORTED_ELEMENT_TYPES = eINSTANCE.getSupportedElementTypes();

		/**
		 * The meta object literal for the '<em><b>Element Type</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUPPORTED_ELEMENT_TYPES__ELEMENT_TYPE = eINSTANCE.getSupportedElementTypes_ElementType();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.model.registry.impl.SupportedExtensionsImpl <em>Supported Extensions</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.model.registry.impl.SupportedExtensionsImpl
		 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getSupportedExtensions()
		 * @generated
		 */
		EClass SUPPORTED_EXTENSIONS = eINSTANCE.getSupportedExtensions();

		/**
		 * The meta object literal for the '<em><b>Extension</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUPPORTED_EXTENSIONS__EXTENSION = eINSTANCE.getSupportedExtensions_Extension();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.model.registry.impl.TnsEntityExtensionsImpl <em>Tns Entity Extensions</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.model.registry.impl.TnsEntityExtensionsImpl
		 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getTnsEntityExtensions()
		 * @generated
		 */
		EClass TNS_ENTITY_EXTENSIONS = eINSTANCE.getTnsEntityExtensions();

		/**
		 * The meta object literal for the '<em><b>Extension</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TNS_ENTITY_EXTENSIONS__EXTENSION = eINSTANCE.getTnsEntityExtensions_Extension();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.model.registry.AddOnType <em>Add On Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.model.registry.AddOnType
		 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getAddOnType()
		 * @generated
		 */
		EEnum ADD_ON_TYPE = eINSTANCE.getAddOnType();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.model.registry.ElementTypes <em>Element Types</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.model.registry.ElementTypes
		 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getElementTypes()
		 * @generated
		 */
		EEnum ELEMENT_TYPES = eINSTANCE.getElementTypes();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.model.registry.FileExtensionTypes <em>File Extension Types</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.model.registry.FileExtensionTypes
		 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getFileExtensionTypes()
		 * @generated
		 */
		EEnum FILE_EXTENSION_TYPES = eINSTANCE.getFileExtensionTypes();

		/**
		 * The meta object literal for the '<em>Add On Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.model.registry.AddOnType
		 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getAddOnTypeObject()
		 * @generated
		 */
		EDataType ADD_ON_TYPE_OBJECT = eINSTANCE.getAddOnTypeObject();

		/**
		 * The meta object literal for the '<em>Element Types Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.model.registry.ElementTypes
		 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getElementTypesObject()
		 * @generated
		 */
		EDataType ELEMENT_TYPES_OBJECT = eINSTANCE.getElementTypesObject();

		/**
		 * The meta object literal for the '<em>File Extension Types Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.model.registry.FileExtensionTypes
		 * @see com.tibco.cep.designtime.model.registry.impl.RegistryPackageImpl#getFileExtensionTypesObject()
		 * @generated
		 */
		EDataType FILE_EXTENSION_TYPES_OBJECT = eINSTANCE.getFileExtensionTypesObject();

	}

} //RegistryPackage
