/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.policy.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.be.baas.security.authn.saml.model.policy.AuthnPolicy;
import com.tibco.be.baas.security.authn.saml.model.policy.AuthnPolicyTemplate;
import com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType;
import com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyValue;
import com.tibco.be.baas.security.authn.saml.model.policy.DataTypeType;
import com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyConfigType;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyFactory;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PolicyPackageImpl extends EPackageImpl implements PolicyPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass configPropertyTypeEClass = null;

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
	private EClass policyConfigTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass policyTemplateTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass policyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass configPropertyValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass authnPolicyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass authnPolicyTemplateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum dataTypeTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType dataTypeTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType nameTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType nameType1EDataType = null;

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
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PolicyPackageImpl() {
		super(eNS_URI, PolicyFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link PolicyPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PolicyPackage init() {
		if (isInited) return (PolicyPackage)EPackage.Registry.INSTANCE.getEPackage(PolicyPackage.eNS_URI);

		// Obtain or create and register package
		PolicyPackageImpl thePolicyPackage = (PolicyPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof PolicyPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new PolicyPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		thePolicyPackage.createPackageContents();

		// Initialize created meta-data
		thePolicyPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePolicyPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PolicyPackage.eNS_URI, thePolicyPackage);
		return thePolicyPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConfigPropertyType() {
		return configPropertyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfigPropertyType_DataType() {
		return (EAttribute)configPropertyTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfigPropertyType_Name() {
		return (EAttribute)configPropertyTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConfigPropertyType_PropertyValue() {
		return (EReference)configPropertyTypeEClass.getEStructuralFeatures().get(2);
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
	public EReference getDocumentRoot_AuthnPolicy() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_AuthnPolicyTemplate() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPolicyConfigType() {
		return policyConfigTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPolicyConfigType_ConfigProperty() {
		return (EReference)policyConfigTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPolicyTemplateType() {
		return policyTemplateTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPolicyTemplateType_PolicyConfig() {
		return (EReference)policyTemplateTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPolicyTemplateType_Description() {
		return (EAttribute)policyTemplateTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPolicyTemplateType_ID() {
		return (EAttribute)policyTemplateTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPolicyTemplateType_Name() {
		return (EAttribute)policyTemplateTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPolicyType() {
		return policyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPolicyType_Mixed() {
		return (EAttribute)policyTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConfigPropertyValue() {
		return configPropertyValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfigPropertyValue_Value() {
		return (EAttribute)configPropertyValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAuthnPolicy() {
		return authnPolicyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAuthnPolicyTemplate() {
		return authnPolicyTemplateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDataTypeType() {
		return dataTypeTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDataTypeTypeObject() {
		return dataTypeTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getNameType() {
		return nameTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getNameType1() {
		return nameType1EDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PolicyFactory getPolicyFactory() {
		return (PolicyFactory)getEFactoryInstance();
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
		configPropertyTypeEClass = createEClass(CONFIG_PROPERTY_TYPE);
		createEAttribute(configPropertyTypeEClass, CONFIG_PROPERTY_TYPE__DATA_TYPE);
		createEAttribute(configPropertyTypeEClass, CONFIG_PROPERTY_TYPE__NAME);
		createEReference(configPropertyTypeEClass, CONFIG_PROPERTY_TYPE__PROPERTY_VALUE);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__AUTHN_POLICY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__AUTHN_POLICY_TEMPLATE);

		policyConfigTypeEClass = createEClass(POLICY_CONFIG_TYPE);
		createEReference(policyConfigTypeEClass, POLICY_CONFIG_TYPE__CONFIG_PROPERTY);

		policyTemplateTypeEClass = createEClass(POLICY_TEMPLATE_TYPE);
		createEReference(policyTemplateTypeEClass, POLICY_TEMPLATE_TYPE__POLICY_CONFIG);
		createEAttribute(policyTemplateTypeEClass, POLICY_TEMPLATE_TYPE__DESCRIPTION);
		createEAttribute(policyTemplateTypeEClass, POLICY_TEMPLATE_TYPE__ID);
		createEAttribute(policyTemplateTypeEClass, POLICY_TEMPLATE_TYPE__NAME);

		policyTypeEClass = createEClass(POLICY_TYPE);
		createEAttribute(policyTypeEClass, POLICY_TYPE__MIXED);

		configPropertyValueEClass = createEClass(CONFIG_PROPERTY_VALUE);
		createEAttribute(configPropertyValueEClass, CONFIG_PROPERTY_VALUE__VALUE);

		authnPolicyEClass = createEClass(AUTHN_POLICY);

		authnPolicyTemplateEClass = createEClass(AUTHN_POLICY_TEMPLATE);

		// Create enums
		dataTypeTypeEEnum = createEEnum(DATA_TYPE_TYPE);

		// Create data types
		dataTypeTypeObjectEDataType = createEDataType(DATA_TYPE_TYPE_OBJECT);
		nameTypeEDataType = createEDataType(NAME_TYPE);
		nameType1EDataType = createEDataType(NAME_TYPE1);
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
		authnPolicyEClass.getESuperTypes().add(this.getPolicyTemplateType());
		authnPolicyTemplateEClass.getESuperTypes().add(this.getPolicyTemplateType());

		// Initialize classes and features; add operations and parameters
		initEClass(configPropertyTypeEClass, ConfigPropertyType.class, "ConfigPropertyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConfigPropertyType_DataType(), this.getDataTypeType(), "dataType", null, 1, 1, ConfigPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfigPropertyType_Name(), this.getNameType1(), "name", null, 1, 1, ConfigPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConfigPropertyType_PropertyValue(), this.getConfigPropertyValue(), null, "propertyValue", null, 0, -1, ConfigPropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AuthnPolicy(), this.getPolicyTemplateType(), null, "authnPolicy", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_AuthnPolicyTemplate(), this.getPolicyTemplateType(), null, "authnPolicyTemplate", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(policyConfigTypeEClass, PolicyConfigType.class, "PolicyConfigType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPolicyConfigType_ConfigProperty(), this.getConfigPropertyType(), null, "configProperty", null, 0, -1, PolicyConfigType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(policyTemplateTypeEClass, PolicyTemplateType.class, "PolicyTemplateType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPolicyTemplateType_PolicyConfig(), this.getPolicyConfigType(), null, "policyConfig", null, 1, 1, PolicyTemplateType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPolicyTemplateType_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, PolicyTemplateType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPolicyTemplateType_ID(), theXMLTypePackage.getID(), "ID", null, 1, 1, PolicyTemplateType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPolicyTemplateType_Name(), this.getNameType(), "name", null, 1, 1, PolicyTemplateType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(policyTypeEClass, PolicyType.class, "PolicyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPolicyType_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, PolicyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(configPropertyValueEClass, ConfigPropertyValue.class, "ConfigPropertyValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConfigPropertyValue_Value(), ecorePackage.getEString(), "value", null, 0, 1, ConfigPropertyValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(authnPolicyEClass, AuthnPolicy.class, "AuthnPolicy", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(authnPolicyTemplateEClass, AuthnPolicyTemplate.class, "AuthnPolicyTemplate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(dataTypeTypeEEnum, DataTypeType.class, "DataTypeType");
		addEEnumLiteral(dataTypeTypeEEnum, DataTypeType.INTEGER);
		addEEnumLiteral(dataTypeTypeEEnum, DataTypeType.BOOLEAN);
		addEEnumLiteral(dataTypeTypeEEnum, DataTypeType.DOUBLE);
		addEEnumLiteral(dataTypeTypeEEnum, DataTypeType.FLOAT);
		addEEnumLiteral(dataTypeTypeEEnum, DataTypeType.DATETIME);
		addEEnumLiteral(dataTypeTypeEEnum, DataTypeType.STRING);
		addEEnumLiteral(dataTypeTypeEEnum, DataTypeType.LONG);

		// Initialize data types
		initEDataType(dataTypeTypeObjectEDataType, DataTypeType.class, "DataTypeTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(nameTypeEDataType, String.class, "NameType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(nameType1EDataType, String.class, "NameType1", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// null
		createNullAnnotations();
		// http://java.sun.com/xml/ns/jaxb
		createJaxbAnnotations();
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
		// http:///org/eclipse/emf/mapping/xsd2ecore/XSD2Ecore
		createXSD2EcoreAnnotations();
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
			 "appinfo", "\r\n\t    <jxb:schemaBindings xmlns:jxb=\"http://java.sun.com/xml/ns/jaxb\">\r\n\t      <jxb:package name=\"com.tibco.be.baas.security.authn.saml.metadata.policy.model\">\r\n\t\t <jxb:javadoc>\r\n\t   <![CDATA[<body> Package level documentation for \r\n\tgenerated package com.tibco.be.baas.security.authn.saml.metadata.policy.model</body>]]>\r\n\t\t </jxb:javadoc>\r\n\t      </jxb:package>\r\n\t    </jxb:schemaBindings>\r\n\t  "
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
		  (configPropertyTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ConfigPropertyType",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getConfigPropertyType_DataType(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "dataType"
		   });			
		addAnnotation
		  (getConfigPropertyType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });		
		addAnnotation
		  (dataTypeTypeEEnum, 
		   source, 
		   new String[] {
			 "name", "dataType_._type"
		   });		
		addAnnotation
		  (dataTypeTypeObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "dataType_._type:Object",
			 "baseType", "dataType_._type"
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
		  (getDocumentRoot_AuthnPolicy(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnPolicy",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_AuthnPolicyTemplate(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "AuthnPolicyTemplate",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (nameTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "name_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#Name",
			 "whiteSpace", "collapse"
		   });		
		addAnnotation
		  (nameType1EDataType, 
		   source, 
		   new String[] {
			 "name", "name_._1_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#Name",
			 "whiteSpace", "collapse"
		   });			
		addAnnotation
		  (policyConfigTypeEClass, 
		   source, 
		   new String[] {
			 "name", "PolicyConfigType",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getPolicyConfigType_ConfigProperty(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "configProperty",
			 "namespace", "##targetNamespace"
		   });			
		addAnnotation
		  (policyTemplateTypeEClass, 
		   source, 
		   new String[] {
			 "name", "PolicyTemplateType",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (policyTemplateTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AuthnPolicy",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (policyTemplateTypeEClass, 
		   source, 
		   new String[] {
			 "name", "AuthnPolicyTemplate",
			 "kind", "elementOnly"
		   });				
		addAnnotation
		  (getPolicyTemplateType_PolicyConfig(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "policyConfig",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getPolicyTemplateType_Description(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "description"
		   });			
		addAnnotation
		  (getPolicyTemplateType_ID(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ID"
		   });		
		addAnnotation
		  (getPolicyTemplateType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });		
		addAnnotation
		  (policyTypeEClass, 
		   source, 
		   new String[] {
			 "name", "PolicyType",
			 "kind", "mixed"
		   });		
		addAnnotation
		  (getPolicyType_Mixed(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "name", ":mixed"
		   });	
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/mapping/xsd2ecore/XSD2Ecore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createXSD2EcoreAnnotations() {
		String source = "http:///org/eclipse/emf/mapping/xsd2ecore/XSD2Ecore";					
		addAnnotation
		  (configPropertyTypeEClass, 
		   source, 
		   new String[] {
			 "targetNamespace", "http://www.tibco.com/be/baas/authn/PolicyTemplateSchema",
			 "name", "configProperty"
		   });																	
		addAnnotation
		  (policyConfigTypeEClass, 
		   source, 
		   new String[] {
			 "targetNamespace", "http://www.tibco.com/be/baas/authn/PolicyTemplateSchema",
			 "name", "policyConfig"
		   });							
		addAnnotation
		  (policyTemplateTypeEClass, 
		   source, 
		   new String[] {
			 "targetNamespace", "http://www.tibco.com/be/baas/authn/PolicyTemplateSchema",
			 "name", "AuthnPolicy"
		   });		
		addAnnotation
		  (policyTemplateTypeEClass, 
		   source, 
		   new String[] {
			 "targetNamespace", "http://www.tibco.com/be/baas/authn/PolicyTemplateSchema",
			 "name", "AuthnPolicyTemplate"
		   });									
		addAnnotation
		  (configPropertyValueEClass, 
		   source, 
		   new String[] {
			 "targetNamespace", "http://www.tibco.com/be/baas/authn/PolicyTemplateSchema",
			 "name", "propertyValue"
		   });
	}

} //PolicyPackageImpl
