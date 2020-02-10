/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.id;

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
 * @see com.tibco.be.util.config.sharedresources.id.IdFactory
 * @model kind="package"
 *        annotation="http://www.w3.org/XML/1998/namespace lang='en'"
 * @generated
 */
public interface IdPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "id";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.tibco.com/xmlns/repo/types/2002";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "id";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IdPackage eINSTANCE = com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.id.impl.DesignerImpl <em>Designer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.id.impl.DesignerImpl
	 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getDesigner()
	 * @generated
	 */
	int DESIGNER = 0;

	/**
	 * The feature id for the '<em><b>Locked Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DESIGNER__LOCKED_PROPERTIES = 0;

    /**
	 * The feature id for the '<em><b>Fixed Children</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DESIGNER__FIXED_CHILDREN = 1;

    /**
	 * The feature id for the '<em><b>Resource Descriptions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER__RESOURCE_DESCRIPTIONS = 2;

	/**
	 * The number of structural features of the '<em>Designer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.id.impl.IdentityImpl <em>Identity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.id.impl.IdentityImpl
	 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getIdentity()
	 * @generated
	 */
	int IDENTITY = 1;

	/**
	 * The feature id for the '<em><b>Designer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY__DESIGNER = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY__NAME = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Object Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY__OBJECT_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY__USERNAME = 4;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY__PASSWORD = 5;

	/**
	 * The feature id for the '<em><b>File Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY__FILE_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY__URL = 7;

	/**
	 * The feature id for the '<em><b>Pass Phrase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY__PASS_PHRASE = 8;

	/**
	 * The feature id for the '<em><b>Cert Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY__CERT_URL = 9;

	/**
	 * The feature id for the '<em><b>Private Key Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY__PRIVATE_KEY_URL = 10;

	/**
	 * The number of structural features of the '<em>Identity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTITY_FEATURE_COUNT = 11;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.id.impl.IdRootImpl <em>Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.id.impl.IdRootImpl
	 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getIdRoot()
	 * @generated
	 */
	int ID_ROOT = 2;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Repository</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_ROOT__REPOSITORY = 3;

	/**
	 * The number of structural features of the '<em>Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_ROOT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.id.impl.NodeImpl <em>Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.id.impl.NodeImpl
	 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getNode()
	 * @generated
	 */
	int NODE = 3;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__DESCRIPTION = 0;

	/**
	 * The number of structural features of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.id.impl.RepositoryImpl <em>Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.id.impl.RepositoryImpl
	 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getRepository()
	 * @generated
	 */
	int REPOSITORY = 4;

	/**
	 * The feature id for the '<em><b>Identity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY__IDENTITY = 0;

	/**
	 * The number of structural features of the '<em>Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPOSITORY_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.id.impl.ResourceDescriptionsImpl <em>Resource Descriptions</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.id.impl.ResourceDescriptionsImpl
	 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getResourceDescriptions()
	 * @generated
	 */
	int RESOURCE_DESCRIPTIONS = 5;

	/**
	 * The feature id for the '<em><b>Node</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_DESCRIPTIONS__NODE = 0;

	/**
	 * The number of structural features of the '<em>Resource Descriptions</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_DESCRIPTIONS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.id.FileTypeEnum <em>File Type Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.id.FileTypeEnum
	 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getFileTypeEnum()
	 * @generated
	 */
	int FILE_TYPE_ENUM = 6;

	/**
	 * The meta object id for the '{@link com.tibco.be.util.config.sharedresources.id.ObjectTypeEnum <em>Object Type Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.id.ObjectTypeEnum
	 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getObjectTypeEnum()
	 * @generated
	 */
	int OBJECT_TYPE_ENUM = 7;

	/**
	 * The meta object id for the '<em>Object Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Object
	 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getObjectType()
	 * @generated
	 */
	int OBJECT_TYPE = 10;

	/**
	 * The meta object id for the '<em>Object Type Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.id.ObjectTypeEnum
	 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getObjectTypeEnumObject()
	 * @generated
	 */
	int OBJECT_TYPE_ENUM_OBJECT = 11;

	/**
	 * The meta object id for the '<em>File Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Object
	 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getFileType()
	 * @generated
	 */
	int FILE_TYPE = 8;

	/**
	 * The meta object id for the '<em>File Type Enum Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.be.util.config.sharedresources.id.FileTypeEnum
	 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getFileTypeEnumObject()
	 * @generated
	 */
	int FILE_TYPE_ENUM_OBJECT = 9;

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.id.Designer <em>Designer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Designer</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Designer
	 * @generated
	 */
	EClass getDesigner();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.id.Designer#getLockedProperties <em>Locked Properties</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Locked Properties</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Designer#getLockedProperties()
	 * @see #getDesigner()
	 * @generated
	 */
    EAttribute getDesigner_LockedProperties();

    /**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.id.Designer#getFixedChildren <em>Fixed Children</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fixed Children</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Designer#getFixedChildren()
	 * @see #getDesigner()
	 * @generated
	 */
    EAttribute getDesigner_FixedChildren();

    /**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.id.Designer#getResourceDescriptions <em>Resource Descriptions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Resource Descriptions</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Designer#getResourceDescriptions()
	 * @see #getDesigner()
	 * @generated
	 */
	EReference getDesigner_ResourceDescriptions();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.id.Identity <em>Identity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identity</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Identity
	 * @generated
	 */
	EClass getIdentity();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.id.Identity#getDesigner <em>Designer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Designer</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Identity#getDesigner()
	 * @see #getIdentity()
	 * @generated
	 */
	EReference getIdentity_Designer();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.id.Identity#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Identity#getName()
	 * @see #getIdentity()
	 * @generated
	 */
	EAttribute getIdentity_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.id.Identity#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Identity#getDescription()
	 * @see #getIdentity()
	 * @generated
	 */
	EAttribute getIdentity_Description();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.id.Identity#getObjectType <em>Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Type</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Identity#getObjectType()
	 * @see #getIdentity()
	 * @generated
	 */
	EAttribute getIdentity_ObjectType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.id.Identity#getUsername <em>Username</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Username</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Identity#getUsername()
	 * @see #getIdentity()
	 * @generated
	 */
	EAttribute getIdentity_Username();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.id.Identity#getPassword <em>Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Password</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Identity#getPassword()
	 * @see #getIdentity()
	 * @generated
	 */
	EAttribute getIdentity_Password();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.id.Identity#getFileType <em>File Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Type</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Identity#getFileType()
	 * @see #getIdentity()
	 * @generated
	 */
	EAttribute getIdentity_FileType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.id.Identity#getUrl <em>Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Url</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Identity#getUrl()
	 * @see #getIdentity()
	 * @generated
	 */
	EAttribute getIdentity_Url();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.id.Identity#getPassPhrase <em>Pass Phrase</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pass Phrase</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Identity#getPassPhrase()
	 * @see #getIdentity()
	 * @generated
	 */
	EAttribute getIdentity_PassPhrase();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.id.Identity#getCertUrl <em>Cert Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cert Url</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Identity#getCertUrl()
	 * @see #getIdentity()
	 * @generated
	 */
	EAttribute getIdentity_CertUrl();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.id.Identity#getPrivateKeyUrl <em>Private Key Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Private Key Url</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Identity#getPrivateKeyUrl()
	 * @see #getIdentity()
	 * @generated
	 */
	EAttribute getIdentity_PrivateKeyUrl();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.id.IdRoot <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Root</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.IdRoot
	 * @generated
	 */
	EClass getIdRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.be.util.config.sharedresources.id.IdRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.IdRoot#getMixed()
	 * @see #getIdRoot()
	 * @generated
	 */
	EAttribute getIdRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.tibco.be.util.config.sharedresources.id.IdRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.IdRoot#getXMLNSPrefixMap()
	 * @see #getIdRoot()
	 * @generated
	 */
	EReference getIdRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.tibco.be.util.config.sharedresources.id.IdRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.IdRoot#getXSISchemaLocation()
	 * @see #getIdRoot()
	 * @generated
	 */
	EReference getIdRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.id.IdRoot#getRepository <em>Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Repository</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.IdRoot#getRepository()
	 * @see #getIdRoot()
	 * @generated
	 */
	EReference getIdRoot_Repository();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.id.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Node
	 * @generated
	 */
	EClass getNode();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.be.util.config.sharedresources.id.Node#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Node#getDescription()
	 * @see #getNode()
	 * @generated
	 */
	EAttribute getNode_Description();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.id.Repository <em>Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Repository</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Repository
	 * @generated
	 */
	EClass getRepository();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.id.Repository#getIdentity <em>Identity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Identity</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.Repository#getIdentity()
	 * @see #getRepository()
	 * @generated
	 */
	EReference getRepository_Identity();

	/**
	 * Returns the meta object for class '{@link com.tibco.be.util.config.sharedresources.id.ResourceDescriptions <em>Resource Descriptions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Descriptions</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.ResourceDescriptions
	 * @generated
	 */
	EClass getResourceDescriptions();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.be.util.config.sharedresources.id.ResourceDescriptions#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Node</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.ResourceDescriptions#getNode()
	 * @see #getResourceDescriptions()
	 * @generated
	 */
	EReference getResourceDescriptions_Node();

	/**
	 * Returns the meta object for enum '{@link com.tibco.be.util.config.sharedresources.id.FileTypeEnum <em>File Type Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>File Type Enum</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.FileTypeEnum
	 * @generated
	 */
	EEnum getFileTypeEnum();

	/**
	 * Returns the meta object for enum '{@link com.tibco.be.util.config.sharedresources.id.ObjectTypeEnum <em>Object Type Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Object Type Enum</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.ObjectTypeEnum
	 * @generated
	 */
	EEnum getObjectTypeEnum();

	/**
	 * Returns the meta object for data type '{@link java.lang.Object <em>Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Object Type</em>'.
	 * @see java.lang.Object
	 * @model instanceClass="java.lang.Object"
	 *        extendedMetaData="name='objectType-type' memberTypes='http://tibco.com/be/basetypes#gv-type objectType-type_._member_._1'"
	 * @generated
	 */
	EDataType getObjectType();

	/**
	 * Returns the meta object for data type '{@link com.tibco.be.util.config.sharedresources.id.ObjectTypeEnum <em>Object Type Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Object Type Enum Object</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.ObjectTypeEnum
	 * @model instanceClass="com.tibco.be.util.config.sharedresources.id.ObjectTypeEnum"
	 *        extendedMetaData="name='objectType-type_._member_._1:Object' baseType='objectType-type_._member_._1'"
	 * @generated
	 */
	EDataType getObjectTypeEnumObject();

	/**
	 * Returns the meta object for data type '{@link java.lang.Object <em>File Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>File Type</em>'.
	 * @see java.lang.Object
	 * @model instanceClass="java.lang.Object"
	 *        extendedMetaData="name='fileType-type' memberTypes='http://tibco.com/be/basetypes#gv-type fileType-type_._member_._1'"
	 * @generated
	 */
	EDataType getFileType();

	/**
	 * Returns the meta object for data type '{@link com.tibco.be.util.config.sharedresources.id.FileTypeEnum <em>File Type Enum Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>File Type Enum Object</em>'.
	 * @see com.tibco.be.util.config.sharedresources.id.FileTypeEnum
	 * @model instanceClass="com.tibco.be.util.config.sharedresources.id.FileTypeEnum"
	 *        extendedMetaData="name='fileType-type_._member_._1:Object' baseType='fileType-type_._member_._1'"
	 * @generated
	 */
	EDataType getFileTypeEnumObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IdFactory getIdFactory();

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
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.id.impl.DesignerImpl <em>Designer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.id.impl.DesignerImpl
		 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getDesigner()
		 * @generated
		 */
		EClass DESIGNER = eINSTANCE.getDesigner();

		/**
		 * The meta object literal for the '<em><b>Locked Properties</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute DESIGNER__LOCKED_PROPERTIES = eINSTANCE.getDesigner_LockedProperties();

        /**
		 * The meta object literal for the '<em><b>Fixed Children</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
		 * @generated
		 */
        EAttribute DESIGNER__FIXED_CHILDREN = eINSTANCE.getDesigner_FixedChildren();

        /**
		 * The meta object literal for the '<em><b>Resource Descriptions</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DESIGNER__RESOURCE_DESCRIPTIONS = eINSTANCE.getDesigner_ResourceDescriptions();

		/**
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.id.impl.IdentityImpl <em>Identity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.id.impl.IdentityImpl
		 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getIdentity()
		 * @generated
		 */
		EClass IDENTITY = eINSTANCE.getIdentity();

		/**
		 * The meta object literal for the '<em><b>Designer</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IDENTITY__DESIGNER = eINSTANCE.getIdentity_Designer();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTITY__NAME = eINSTANCE.getIdentity_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTITY__DESCRIPTION = eINSTANCE.getIdentity_Description();

		/**
		 * The meta object literal for the '<em><b>Object Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTITY__OBJECT_TYPE = eINSTANCE.getIdentity_ObjectType();

		/**
		 * The meta object literal for the '<em><b>Username</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTITY__USERNAME = eINSTANCE.getIdentity_Username();

		/**
		 * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTITY__PASSWORD = eINSTANCE.getIdentity_Password();

		/**
		 * The meta object literal for the '<em><b>File Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTITY__FILE_TYPE = eINSTANCE.getIdentity_FileType();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTITY__URL = eINSTANCE.getIdentity_Url();

		/**
		 * The meta object literal for the '<em><b>Pass Phrase</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTITY__PASS_PHRASE = eINSTANCE.getIdentity_PassPhrase();

		/**
		 * The meta object literal for the '<em><b>Cert Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTITY__CERT_URL = eINSTANCE.getIdentity_CertUrl();

		/**
		 * The meta object literal for the '<em><b>Private Key Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTITY__PRIVATE_KEY_URL = eINSTANCE.getIdentity_PrivateKeyUrl();

		/**
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.id.impl.IdRootImpl <em>Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.id.impl.IdRootImpl
		 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getIdRoot()
		 * @generated
		 */
		EClass ID_ROOT = eINSTANCE.getIdRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ID_ROOT__MIXED = eINSTANCE.getIdRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ID_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getIdRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ID_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getIdRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Repository</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ID_ROOT__REPOSITORY = eINSTANCE.getIdRoot_Repository();

		/**
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.id.impl.NodeImpl <em>Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.id.impl.NodeImpl
		 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getNode()
		 * @generated
		 */
		EClass NODE = eINSTANCE.getNode();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE__DESCRIPTION = eINSTANCE.getNode_Description();

		/**
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.id.impl.RepositoryImpl <em>Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.id.impl.RepositoryImpl
		 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getRepository()
		 * @generated
		 */
		EClass REPOSITORY = eINSTANCE.getRepository();

		/**
		 * The meta object literal for the '<em><b>Identity</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPOSITORY__IDENTITY = eINSTANCE.getRepository_Identity();

		/**
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.id.impl.ResourceDescriptionsImpl <em>Resource Descriptions</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.id.impl.ResourceDescriptionsImpl
		 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getResourceDescriptions()
		 * @generated
		 */
		EClass RESOURCE_DESCRIPTIONS = eINSTANCE.getResourceDescriptions();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_DESCRIPTIONS__NODE = eINSTANCE.getResourceDescriptions_Node();

		/**
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.id.FileTypeEnum <em>File Type Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.id.FileTypeEnum
		 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getFileTypeEnum()
		 * @generated
		 */
		EEnum FILE_TYPE_ENUM = eINSTANCE.getFileTypeEnum();

		/**
		 * The meta object literal for the '{@link com.tibco.be.util.config.sharedresources.id.ObjectTypeEnum <em>Object Type Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.id.ObjectTypeEnum
		 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getObjectTypeEnum()
		 * @generated
		 */
		EEnum OBJECT_TYPE_ENUM = eINSTANCE.getObjectTypeEnum();

		/**
		 * The meta object literal for the '<em>Object Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Object
		 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getObjectType()
		 * @generated
		 */
		EDataType OBJECT_TYPE = eINSTANCE.getObjectType();

		/**
		 * The meta object literal for the '<em>Object Type Enum Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.id.ObjectTypeEnum
		 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getObjectTypeEnumObject()
		 * @generated
		 */
		EDataType OBJECT_TYPE_ENUM_OBJECT = eINSTANCE.getObjectTypeEnumObject();

		/**
		 * The meta object literal for the '<em>File Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Object
		 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getFileType()
		 * @generated
		 */
		EDataType FILE_TYPE = eINSTANCE.getFileType();

		/**
		 * The meta object literal for the '<em>File Type Enum Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.be.util.config.sharedresources.id.FileTypeEnum
		 * @see com.tibco.be.util.config.sharedresources.id.impl.IdPackageImpl#getFileTypeEnumObject()
		 * @generated
		 */
		EDataType FILE_TYPE_ENUM_OBJECT = eINSTANCE.getFileTypeEnumObject();

	}

} //IdPackage
