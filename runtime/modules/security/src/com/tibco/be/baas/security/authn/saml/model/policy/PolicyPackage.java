/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.policy;

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
 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyFactory
 * @model kind="package"
 *        annotation="http://java.sun.com/xml/ns/jaxb version='2.0'"
 * @generated
 */
public interface PolicyPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "policy";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.tibco.com/be/baas/authn/PolicyTemplateSchema";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "policy";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PolicyPackage eINSTANCE = com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.ConfigPropertyTypeImpl <em>Config Property Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.ConfigPropertyTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getConfigPropertyType()
	 * @generated
	 */
	int CONFIG_PROPERTY_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_PROPERTY_TYPE__DATA_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_PROPERTY_TYPE__NAME = 1;

	/**
	 * The feature id for the '<em><b>Property Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_PROPERTY_TYPE__PROPERTY_VALUE = 2;

	/**
	 * The number of structural features of the '<em>Config Property Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_PROPERTY_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.DocumentRootImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 1;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Authn Policy</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUTHN_POLICY = 3;

	/**
	 * The feature id for the '<em><b>Authn Policy Template</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUTHN_POLICY_TEMPLATE = 4;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyConfigTypeImpl <em>Config Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyConfigTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getPolicyConfigType()
	 * @generated
	 */
	int POLICY_CONFIG_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Config Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_CONFIG_TYPE__CONFIG_PROPERTY = 0;

	/**
	 * The number of structural features of the '<em>Config Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_CONFIG_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyTemplateTypeImpl <em>Template Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyTemplateTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getPolicyTemplateType()
	 * @generated
	 */
	int POLICY_TEMPLATE_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Policy Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_TEMPLATE_TYPE__POLICY_CONFIG = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_TEMPLATE_TYPE__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_TEMPLATE_TYPE__ID = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_TEMPLATE_TYPE__NAME = 3;

	/**
	 * The number of structural features of the '<em>Template Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_TEMPLATE_TYPE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyTypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyTypeImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getPolicyType()
	 * @generated
	 */
	int POLICY_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_TYPE__MIXED = 0;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POLICY_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.ConfigPropertyValueImpl <em>Config Property Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.ConfigPropertyValueImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getConfigPropertyValue()
	 * @generated
	 */
	int CONFIG_PROPERTY_VALUE = 5;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_PROPERTY_VALUE__VALUE = 0;

	/**
	 * The number of structural features of the '<em>Config Property Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_PROPERTY_VALUE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.AuthnPolicyImpl <em>Authn Policy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.AuthnPolicyImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getAuthnPolicy()
	 * @generated
	 */
	int AUTHN_POLICY = 6;

	/**
	 * The feature id for the '<em><b>Policy Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_POLICY__POLICY_CONFIG = POLICY_TEMPLATE_TYPE__POLICY_CONFIG;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_POLICY__DESCRIPTION = POLICY_TEMPLATE_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_POLICY__ID = POLICY_TEMPLATE_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_POLICY__NAME = POLICY_TEMPLATE_TYPE__NAME;

	/**
	 * The number of structural features of the '<em>Authn Policy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_POLICY_FEATURE_COUNT = POLICY_TEMPLATE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.AuthnPolicyTemplateImpl <em>Authn Policy Template</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.AuthnPolicyTemplateImpl
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getAuthnPolicyTemplate()
	 * @generated
	 */
	int AUTHN_POLICY_TEMPLATE = 7;

	/**
	 * The feature id for the '<em><b>Policy Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_POLICY_TEMPLATE__POLICY_CONFIG = POLICY_TEMPLATE_TYPE__POLICY_CONFIG;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_POLICY_TEMPLATE__DESCRIPTION = POLICY_TEMPLATE_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_POLICY_TEMPLATE__ID = POLICY_TEMPLATE_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_POLICY_TEMPLATE__NAME = POLICY_TEMPLATE_TYPE__NAME;

	/**
	 * The number of structural features of the '<em>Authn Policy Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTHN_POLICY_TEMPLATE_FEATURE_COUNT = POLICY_TEMPLATE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.be.baas.security.authn.saml.model.policy.DataTypeType <em>Data Type Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.DataTypeType
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getDataTypeType()
	 * @generated
	 */
	int DATA_TYPE_TYPE = 8;

	/**
	 * The meta object id for the '<em>Data Type Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.DataTypeType
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getDataTypeTypeObject()
	 * @generated
	 */
	int DATA_TYPE_TYPE_OBJECT = 9;

	/**
	 * The meta object id for the '<em>Name Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getNameType()
	 * @generated
	 */
	int NAME_TYPE = 10;

	/**
	 * The meta object id for the '<em>Name Type1</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getNameType1()
	 * @generated
	 */
	int NAME_TYPE1 = 11;


	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType <em>Config Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Config Property Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType
	 * @generated
	 */
	EClass getConfigPropertyType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType#getDataType()
	 * @see #getConfigPropertyType()
	 * @generated
	 */
	EAttribute getConfigPropertyType_DataType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType#getName()
	 * @see #getConfigPropertyType()
	 * @generated
	 */
	EAttribute getConfigPropertyType_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType#getPropertyValue <em>Property Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property Value</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType#getPropertyValue()
	 * @see #getConfigPropertyType()
	 * @generated
	 */
	EReference getConfigPropertyType_PropertyValue();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot#getAuthnPolicy <em>Authn Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Authn Policy</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot#getAuthnPolicy()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AuthnPolicy();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot#getAuthnPolicyTemplate <em>Authn Policy Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Authn Policy Template</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.DocumentRoot#getAuthnPolicyTemplate()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_AuthnPolicyTemplate();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyConfigType <em>Config Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Config Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyConfigType
	 * @generated
	 */
	EClass getPolicyConfigType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyConfigType#getConfigProperty <em>Config Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Config Property</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyConfigType#getConfigProperty()
	 * @see #getPolicyConfigType()
	 * @generated
	 */
	EReference getPolicyConfigType_ConfigProperty();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType <em>Template Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Template Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType
	 * @generated
	 */
	EClass getPolicyTemplateType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType#getPolicyConfig <em>Policy Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Policy Config</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType#getPolicyConfig()
	 * @see #getPolicyTemplateType()
	 * @generated
	 */
	EReference getPolicyTemplateType_PolicyConfig();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType#getDescription()
	 * @see #getPolicyTemplateType()
	 * @generated
	 */
	EAttribute getPolicyTemplateType_Description();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType#getID()
	 * @see #getPolicyTemplateType()
	 * @generated
	 */
	EAttribute getPolicyTemplateType_ID();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType#getName()
	 * @see #getPolicyTemplateType()
	 * @generated
	 */
	EAttribute getPolicyTemplateType_Name();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyType
	 * @generated
	 */
	EClass getPolicyType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.baas.security.authn.saml.model.policy.PolicyType#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.PolicyType#getMixed()
	 * @see #getPolicyType()
	 * @generated
	 */
	EAttribute getPolicyType_Mixed();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyValue <em>Config Property Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Config Property Value</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyValue
	 * @generated
	 */
	EClass getConfigPropertyValue();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyValue#getValue()
	 * @see #getConfigPropertyValue()
	 * @generated
	 */
	EAttribute getConfigPropertyValue_Value();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.policy.AuthnPolicy <em>Authn Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Authn Policy</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.AuthnPolicy
	 * @generated
	 */
	EClass getAuthnPolicy();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.baas.security.authn.saml.model.policy.AuthnPolicyTemplate <em>Authn Policy Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Authn Policy Template</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.AuthnPolicyTemplate
	 * @generated
	 */
	EClass getAuthnPolicyTemplate();

	/**
	 * Returns the meta object for enum '{@link com.tibco.be.baas.security.authn.saml.model.policy.DataTypeType <em>Data Type Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Data Type Type</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.DataTypeType
	 * @generated
	 */
	EEnum getDataTypeType();

	/**
	 * Returns the meta object for data type '{@link com.tibco.be.baas.security.authn.saml.model.policy.DataTypeType <em>Data Type Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Data Type Type Object</em>'.
	 * @see com.tibco.be.baas.security.authn.saml.model.policy.DataTypeType
	 * @model instanceClass="com.tibco.be.baas.security.authn.saml.model.policy.DataTypeType"
	 *        extendedMetaData="name='dataType_._type:Object' baseType='dataType_._type'"
	 * @generated
	 */
	EDataType getDataTypeTypeObject();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Name Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Name Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='name_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#Name' whiteSpace='collapse'"
	 * @generated
	 */
	EDataType getNameType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Name Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Name Type1</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='name_._1_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#Name' whiteSpace='collapse'"
	 * @generated
	 */
	EDataType getNameType1();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PolicyFactory getPolicyFactory();

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
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.ConfigPropertyTypeImpl <em>Config Property Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.ConfigPropertyTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getConfigPropertyType()
		 * @generated
		 */
		EClass CONFIG_PROPERTY_TYPE = eINSTANCE.getConfigPropertyType();

		/**
		 * The meta object literal for the '<em><b>Data Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG_PROPERTY_TYPE__DATA_TYPE = eINSTANCE.getConfigPropertyType_DataType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG_PROPERTY_TYPE__NAME = eINSTANCE.getConfigPropertyType_Name();

		/**
		 * The meta object literal for the '<em><b>Property Value</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIG_PROPERTY_TYPE__PROPERTY_VALUE = eINSTANCE.getConfigPropertyType_PropertyValue();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.DocumentRootImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Authn Policy</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__AUTHN_POLICY = eINSTANCE.getDocumentRoot_AuthnPolicy();

		/**
		 * The meta object literal for the '<em><b>Authn Policy Template</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__AUTHN_POLICY_TEMPLATE = eINSTANCE.getDocumentRoot_AuthnPolicyTemplate();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyConfigTypeImpl <em>Config Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyConfigTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getPolicyConfigType()
		 * @generated
		 */
		EClass POLICY_CONFIG_TYPE = eINSTANCE.getPolicyConfigType();

		/**
		 * The meta object literal for the '<em><b>Config Property</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POLICY_CONFIG_TYPE__CONFIG_PROPERTY = eINSTANCE.getPolicyConfigType_ConfigProperty();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyTemplateTypeImpl <em>Template Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyTemplateTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getPolicyTemplateType()
		 * @generated
		 */
		EClass POLICY_TEMPLATE_TYPE = eINSTANCE.getPolicyTemplateType();

		/**
		 * The meta object literal for the '<em><b>Policy Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POLICY_TEMPLATE_TYPE__POLICY_CONFIG = eINSTANCE.getPolicyTemplateType_PolicyConfig();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POLICY_TEMPLATE_TYPE__DESCRIPTION = eINSTANCE.getPolicyTemplateType_Description();

		/**
		 * The meta object literal for the '<em><b>ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POLICY_TEMPLATE_TYPE__ID = eINSTANCE.getPolicyTemplateType_ID();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POLICY_TEMPLATE_TYPE__NAME = eINSTANCE.getPolicyTemplateType_Name();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyTypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyTypeImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getPolicyType()
		 * @generated
		 */
		EClass POLICY_TYPE = eINSTANCE.getPolicyType();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POLICY_TYPE__MIXED = eINSTANCE.getPolicyType_Mixed();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.ConfigPropertyValueImpl <em>Config Property Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.ConfigPropertyValueImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getConfigPropertyValue()
		 * @generated
		 */
		EClass CONFIG_PROPERTY_VALUE = eINSTANCE.getConfigPropertyValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG_PROPERTY_VALUE__VALUE = eINSTANCE.getConfigPropertyValue_Value();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.AuthnPolicyImpl <em>Authn Policy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.AuthnPolicyImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getAuthnPolicy()
		 * @generated
		 */
		EClass AUTHN_POLICY = eINSTANCE.getAuthnPolicy();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.AuthnPolicyTemplateImpl <em>Authn Policy Template</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.AuthnPolicyTemplateImpl
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getAuthnPolicyTemplate()
		 * @generated
		 */
		EClass AUTHN_POLICY_TEMPLATE = eINSTANCE.getAuthnPolicyTemplate();

		/**
		 * The meta object literal for the '{@link com.tibco.be.baas.security.authn.saml.model.policy.DataTypeType <em>Data Type Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.DataTypeType
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getDataTypeType()
		 * @generated
		 */
		EEnum DATA_TYPE_TYPE = eINSTANCE.getDataTypeType();

		/**
		 * The meta object literal for the '<em>Data Type Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.DataTypeType
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getDataTypeTypeObject()
		 * @generated
		 */
		EDataType DATA_TYPE_TYPE_OBJECT = eINSTANCE.getDataTypeTypeObject();

		/**
		 * The meta object literal for the '<em>Name Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getNameType()
		 * @generated
		 */
		EDataType NAME_TYPE = eINSTANCE.getNameType();

		/**
		 * The meta object literal for the '<em>Name Type1</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.tibco.be.baas.security.authn.saml.model.policy.impl.PolicyPackageImpl#getNameType1()
		 * @generated
		 */
		EDataType NAME_TYPE1 = eINSTANCE.getNameType1();

	}

} //PolicyPackage
