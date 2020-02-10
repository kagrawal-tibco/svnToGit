/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.palette;

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
 * @see com.tibco.cep.studio.common.palette.PaletteFactory
 * @model kind="package"
 * @generated
 */
public interface PalettePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "palette";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.tibco.com/cep/studio/common/palette";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "palette";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PalettePackage eINSTANCE = com.tibco.cep.studio.common.palette.impl.PalettePackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.palette.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.palette.impl.DocumentRootImpl
	 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 0;

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
	 * The feature id for the '<em><b>Model</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MODEL = 3;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.palette.impl.EmfTypeImpl <em>Emf Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.palette.impl.EmfTypeImpl
	 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getEmfType()
	 * @generated
	 */
	int EMF_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Emf Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMF_TYPE__EMF_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Extended Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMF_TYPE__EXTENDED_TYPE = 1;

	/**
	 * The number of structural features of the '<em>Emf Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMF_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.palette.impl.HelpImpl <em>Help</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.palette.impl.HelpImpl
	 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getHelp()
	 * @generated
	 */
	int HELP = 2;

	/**
	 * The feature id for the '<em><b>Tab</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HELP__TAB = 0;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HELP__CONTENT = 1;

	/**
	 * The number of structural features of the '<em>Help</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HELP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.palette.impl.JavaTypeImpl <em>Java Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.palette.impl.JavaTypeImpl
	 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getJavaType()
	 * @generated
	 */
	int JAVA_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_TYPE__TYPE = 0;

	/**
	 * The number of structural features of the '<em>Java Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.palette.impl.ModelTypeImpl <em>Model Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.palette.impl.ModelTypeImpl
	 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getModelType()
	 * @generated
	 */
	int MODEL_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_TYPE__TYPE = 0;

	/**
	 * The number of structural features of the '<em>Model Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.palette.impl.PaletteGroupImpl <em>Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.palette.impl.PaletteGroupImpl
	 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getPaletteGroup()
	 * @generated
	 */
	int PALETTE_GROUP = 5;

	/**
	 * The feature id for the '<em><b>Title Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP__TITLE_REF = 0;

	/**
	 * The feature id for the '<em><b>Tooltip Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP__TOOLTIP_REF = 1;

	/**
	 * The feature id for the '<em><b>Icon Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP__ICON_REF = 2;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP__TITLE = 3;

	/**
	 * The feature id for the '<em><b>Tooltip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP__TOOLTIP = 4;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP__ICON = 5;

	/**
	 * The feature id for the '<em><b>Palette Item</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP__PALETTE_ITEM = 6;

	/**
	 * The feature id for the '<em><b>Palette Tool Set</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP__PALETTE_TOOL_SET = 7;

	/**
	 * The feature id for the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP__COLOR = 8;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP__ID = 9;

	/**
	 * The feature id for the '<em><b>Internal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP__INTERNAL = 10;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP__VISIBLE = 11;

	/**
	 * The number of structural features of the '<em>Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_GROUP_FEATURE_COUNT = 12;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.palette.impl.PaletteItemImpl <em>Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.palette.impl.PaletteItemImpl
	 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getPaletteItem()
	 * @generated
	 */
	int PALETTE_ITEM = 6;

	/**
	 * The feature id for the '<em><b>Emf Item Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ITEM__EMF_ITEM_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Java Item Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ITEM__JAVA_ITEM_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Model Item Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ITEM__MODEL_ITEM_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Title Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ITEM__TITLE_REF = 3;

	/**
	 * The feature id for the '<em><b>Tooltip Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ITEM__TOOLTIP_REF = 4;

	/**
	 * The feature id for the '<em><b>Icon Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ITEM__ICON_REF = 5;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ITEM__TITLE = 6;

	/**
	 * The feature id for the '<em><b>Tooltip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ITEM__TOOLTIP = 7;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ITEM__ICON = 8;

	/**
	 * The feature id for the '<em><b>Attached Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ITEM__ATTACHED_RESOURCE = 9;

	/**
	 * The feature id for the '<em><b>Help Content</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ITEM__HELP_CONTENT = 10;

	/**
	 * The feature id for the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ITEM__COLOR = 11;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ITEM__ID = 12;

	/**
	 * The feature id for the '<em><b>Internal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ITEM__INTERNAL = 13;

	/**
	 * The feature id for the '<em><b>Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ITEM__VISIBLE = 14;

	/**
	 * The number of structural features of the '<em>Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_ITEM_FEATURE_COUNT = 15;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.palette.impl.PaletteModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.palette.impl.PaletteModelImpl
	 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getPaletteModel()
	 * @generated
	 */
	int PALETTE_MODEL = 7;

	/**
	 * The feature id for the '<em><b>Palette Tool Set</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_MODEL__PALETTE_TOOL_SET = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_MODEL__NAME = 1;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_MODEL_FEATURE_COUNT = 2;


	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.common.palette.Tab <em>Tab</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.palette.Tab
	 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getTab()
	 * @generated
	 */
	int TAB = 8;

	/**
	 * The meta object id for the '<em>Tab Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.common.palette.Tab
	 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getTabObject()
	 * @generated
	 */
	int TAB_OBJECT = 9;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.palette.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.tibco.cep.studio.common.palette.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.studio.common.palette.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.cep.studio.common.palette.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.tibco.cep.studio.common.palette.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.tibco.cep.studio.common.palette.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.tibco.cep.studio.common.palette.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.tibco.cep.studio.common.palette.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.common.palette.DocumentRoot#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Model</em>'.
	 * @see com.tibco.cep.studio.common.palette.DocumentRoot#getModel()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Model();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.palette.EmfType <em>Emf Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Emf Type</em>'.
	 * @see com.tibco.cep.studio.common.palette.EmfType
	 * @generated
	 */
	EClass getEmfType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.EmfType#getEmfType <em>Emf Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Emf Type</em>'.
	 * @see com.tibco.cep.studio.common.palette.EmfType#getEmfType()
	 * @see #getEmfType()
	 * @generated
	 */
	EAttribute getEmfType_EmfType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.EmfType#getExtendedType <em>Extended Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Extended Type</em>'.
	 * @see com.tibco.cep.studio.common.palette.EmfType#getExtendedType()
	 * @see #getEmfType()
	 * @generated
	 */
	EAttribute getEmfType_ExtendedType();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.palette.Help <em>Help</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Help</em>'.
	 * @see com.tibco.cep.studio.common.palette.Help
	 * @generated
	 */
	EClass getHelp();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.Help#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Content</em>'.
	 * @see com.tibco.cep.studio.common.palette.Help#getContent()
	 * @see #getHelp()
	 * @generated
	 */
	EAttribute getHelp_Content();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.Help#getTab <em>Tab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tab</em>'.
	 * @see com.tibco.cep.studio.common.palette.Help#getTab()
	 * @see #getHelp()
	 * @generated
	 */
	EAttribute getHelp_Tab();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.palette.JavaType <em>Java Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Type</em>'.
	 * @see com.tibco.cep.studio.common.palette.JavaType
	 * @generated
	 */
	EClass getJavaType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.studio.common.palette.JavaType#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Type</em>'.
	 * @see com.tibco.cep.studio.common.palette.JavaType#getType()
	 * @see #getJavaType()
	 * @generated
	 */
	EAttribute getJavaType_Type();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.palette.ModelType <em>Model Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Type</em>'.
	 * @see com.tibco.cep.studio.common.palette.ModelType
	 * @generated
	 */
	EClass getModelType();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.studio.common.palette.ModelType#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Type</em>'.
	 * @see com.tibco.cep.studio.common.palette.ModelType#getType()
	 * @see #getModelType()
	 * @generated
	 */
	EAttribute getModelType_Type();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.palette.PaletteGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Group</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteGroup
	 * @generated
	 */
	EClass getPaletteGroup();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTitleRef <em>Title Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title Ref</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteGroup#getTitleRef()
	 * @see #getPaletteGroup()
	 * @generated
	 */
	EAttribute getPaletteGroup_TitleRef();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTooltipRef <em>Tooltip Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tooltip Ref</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteGroup#getTooltipRef()
	 * @see #getPaletteGroup()
	 * @generated
	 */
	EAttribute getPaletteGroup_TooltipRef();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getIconRef <em>Icon Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Icon Ref</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteGroup#getIconRef()
	 * @see #getPaletteGroup()
	 * @generated
	 */
	EAttribute getPaletteGroup_IconRef();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteGroup#getTitle()
	 * @see #getPaletteGroup()
	 * @generated
	 */
	EAttribute getPaletteGroup_Title();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getTooltip <em>Tooltip</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tooltip</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteGroup#getTooltip()
	 * @see #getPaletteGroup()
	 * @generated
	 */
	EAttribute getPaletteGroup_Tooltip();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getIcon <em>Icon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Icon</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteGroup#getIcon()
	 * @see #getPaletteGroup()
	 * @generated
	 */
	EAttribute getPaletteGroup_Icon();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getPaletteItem <em>Palette Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Palette Item</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteGroup#getPaletteItem()
	 * @see #getPaletteGroup()
	 * @generated
	 */
	EReference getPaletteGroup_PaletteItem();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getPaletteToolSet <em>Palette Tool Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Palette Tool Set</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteGroup#getPaletteToolSet()
	 * @see #getPaletteGroup()
	 * @generated
	 */
	EReference getPaletteGroup_PaletteToolSet();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getColor <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Color</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteGroup#getColor()
	 * @see #getPaletteGroup()
	 * @generated
	 */
	EAttribute getPaletteGroup_Color();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteGroup#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteGroup#getId()
	 * @see #getPaletteGroup()
	 * @generated
	 */
	EAttribute getPaletteGroup_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteGroup#isInternal <em>Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Internal</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteGroup#isInternal()
	 * @see #getPaletteGroup()
	 * @generated
	 */
	EAttribute getPaletteGroup_Internal();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteGroup#isVisible <em>Visible</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visible</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteGroup#isVisible()
	 * @see #getPaletteGroup()
	 * @generated
	 */
	EAttribute getPaletteGroup_Visible();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.palette.PaletteItem <em>Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Item</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteItem
	 * @generated
	 */
	EClass getPaletteItem();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.common.palette.PaletteItem#getEmfItemType <em>Emf Item Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Emf Item Type</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteItem#getEmfItemType()
	 * @see #getPaletteItem()
	 * @generated
	 */
	EReference getPaletteItem_EmfItemType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.common.palette.PaletteItem#getJavaItemType <em>Java Item Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Java Item Type</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteItem#getJavaItemType()
	 * @see #getPaletteItem()
	 * @generated
	 */
	EReference getPaletteItem_JavaItemType();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.common.palette.PaletteItem#getModelItemType <em>Model Item Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Model Item Type</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteItem#getModelItemType()
	 * @see #getPaletteItem()
	 * @generated
	 */
	EReference getPaletteItem_ModelItemType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteItem#getTitleRef <em>Title Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title Ref</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteItem#getTitleRef()
	 * @see #getPaletteItem()
	 * @generated
	 */
	EAttribute getPaletteItem_TitleRef();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteItem#getTooltipRef <em>Tooltip Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tooltip Ref</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteItem#getTooltipRef()
	 * @see #getPaletteItem()
	 * @generated
	 */
	EAttribute getPaletteItem_TooltipRef();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteItem#getIconRef <em>Icon Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Icon Ref</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteItem#getIconRef()
	 * @see #getPaletteItem()
	 * @generated
	 */
	EAttribute getPaletteItem_IconRef();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteItem#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteItem#getTitle()
	 * @see #getPaletteItem()
	 * @generated
	 */
	EAttribute getPaletteItem_Title();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteItem#getTooltip <em>Tooltip</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tooltip</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteItem#getTooltip()
	 * @see #getPaletteItem()
	 * @generated
	 */
	EAttribute getPaletteItem_Tooltip();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteItem#getIcon <em>Icon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Icon</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteItem#getIcon()
	 * @see #getPaletteItem()
	 * @generated
	 */
	EAttribute getPaletteItem_Icon();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteItem#getAttachedResource <em>Attached Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Attached Resource</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteItem#getAttachedResource()
	 * @see #getPaletteItem()
	 * @generated
	 */
	EAttribute getPaletteItem_AttachedResource();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.common.palette.PaletteItem#getHelpContent <em>Help Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Help Content</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteItem#getHelpContent()
	 * @see #getPaletteItem()
	 * @generated
	 */
	EReference getPaletteItem_HelpContent();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteItem#getColor <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Color</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteItem#getColor()
	 * @see #getPaletteItem()
	 * @generated
	 */
	EAttribute getPaletteItem_Color();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteItem#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteItem#getId()
	 * @see #getPaletteItem()
	 * @generated
	 */
	EAttribute getPaletteItem_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteItem#isInternal <em>Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Internal</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteItem#isInternal()
	 * @see #getPaletteItem()
	 * @generated
	 */
	EAttribute getPaletteItem_Internal();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteItem#isVisible <em>Visible</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Visible</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteItem#isVisible()
	 * @see #getPaletteItem()
	 * @generated
	 */
	EAttribute getPaletteItem_Visible();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.common.palette.PaletteModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteModel
	 * @generated
	 */
	EClass getPaletteModel();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.common.palette.PaletteModel#getPaletteToolSet <em>Palette Tool Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Palette Tool Set</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteModel#getPaletteToolSet()
	 * @see #getPaletteModel()
	 * @generated
	 */
	EReference getPaletteModel_PaletteToolSet();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.common.palette.PaletteModel#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.studio.common.palette.PaletteModel#getName()
	 * @see #getPaletteModel()
	 * @generated
	 */
	EAttribute getPaletteModel_Name();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.studio.common.palette.Tab <em>Tab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Tab</em>'.
	 * @see com.tibco.cep.studio.common.palette.Tab
	 * @generated
	 */
	EEnum getTab();

	/**
	 * Returns the meta object for data type '{@link com.tibco.cep.studio.common.palette.Tab <em>Tab Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Tab Object</em>'.
	 * @see com.tibco.cep.studio.common.palette.Tab
	 * @model instanceClass="com.tibco.cep.studio.common.palette.Tab"
	 *        extendedMetaData="name='Tab:Object' baseType='Tab'"
	 * @generated
	 */
	EDataType getTabObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PaletteFactory getPaletteFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.palette.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.palette.impl.DocumentRootImpl
		 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getDocumentRoot()
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
		 * The meta object literal for the '<em><b>Model</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MODEL = eINSTANCE.getDocumentRoot_Model();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.palette.impl.EmfTypeImpl <em>Emf Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.palette.impl.EmfTypeImpl
		 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getEmfType()
		 * @generated
		 */
		EClass EMF_TYPE = eINSTANCE.getEmfType();

		/**
		 * The meta object literal for the '<em><b>Emf Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EMF_TYPE__EMF_TYPE = eINSTANCE.getEmfType_EmfType();

		/**
		 * The meta object literal for the '<em><b>Extended Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EMF_TYPE__EXTENDED_TYPE = eINSTANCE.getEmfType_ExtendedType();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.palette.impl.HelpImpl <em>Help</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.palette.impl.HelpImpl
		 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getHelp()
		 * @generated
		 */
		EClass HELP = eINSTANCE.getHelp();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HELP__CONTENT = eINSTANCE.getHelp_Content();

		/**
		 * The meta object literal for the '<em><b>Tab</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HELP__TAB = eINSTANCE.getHelp_Tab();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.palette.impl.JavaTypeImpl <em>Java Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.palette.impl.JavaTypeImpl
		 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getJavaType()
		 * @generated
		 */
		EClass JAVA_TYPE = eINSTANCE.getJavaType();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_TYPE__TYPE = eINSTANCE.getJavaType_Type();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.palette.impl.ModelTypeImpl <em>Model Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.palette.impl.ModelTypeImpl
		 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getModelType()
		 * @generated
		 */
		EClass MODEL_TYPE = eINSTANCE.getModelType();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL_TYPE__TYPE = eINSTANCE.getModelType_Type();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.palette.impl.PaletteGroupImpl <em>Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.palette.impl.PaletteGroupImpl
		 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getPaletteGroup()
		 * @generated
		 */
		EClass PALETTE_GROUP = eINSTANCE.getPaletteGroup();

		/**
		 * The meta object literal for the '<em><b>Title Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_GROUP__TITLE_REF = eINSTANCE.getPaletteGroup_TitleRef();

		/**
		 * The meta object literal for the '<em><b>Tooltip Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_GROUP__TOOLTIP_REF = eINSTANCE.getPaletteGroup_TooltipRef();

		/**
		 * The meta object literal for the '<em><b>Icon Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_GROUP__ICON_REF = eINSTANCE.getPaletteGroup_IconRef();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_GROUP__TITLE = eINSTANCE.getPaletteGroup_Title();

		/**
		 * The meta object literal for the '<em><b>Tooltip</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_GROUP__TOOLTIP = eINSTANCE.getPaletteGroup_Tooltip();

		/**
		 * The meta object literal for the '<em><b>Icon</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_GROUP__ICON = eINSTANCE.getPaletteGroup_Icon();

		/**
		 * The meta object literal for the '<em><b>Palette Item</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE_GROUP__PALETTE_ITEM = eINSTANCE.getPaletteGroup_PaletteItem();

		/**
		 * The meta object literal for the '<em><b>Palette Tool Set</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE_GROUP__PALETTE_TOOL_SET = eINSTANCE.getPaletteGroup_PaletteToolSet();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_GROUP__COLOR = eINSTANCE.getPaletteGroup_Color();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_GROUP__ID = eINSTANCE.getPaletteGroup_Id();

		/**
		 * The meta object literal for the '<em><b>Internal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_GROUP__INTERNAL = eINSTANCE.getPaletteGroup_Internal();

		/**
		 * The meta object literal for the '<em><b>Visible</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_GROUP__VISIBLE = eINSTANCE.getPaletteGroup_Visible();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.palette.impl.PaletteItemImpl <em>Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.palette.impl.PaletteItemImpl
		 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getPaletteItem()
		 * @generated
		 */
		EClass PALETTE_ITEM = eINSTANCE.getPaletteItem();

		/**
		 * The meta object literal for the '<em><b>Emf Item Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE_ITEM__EMF_ITEM_TYPE = eINSTANCE.getPaletteItem_EmfItemType();

		/**
		 * The meta object literal for the '<em><b>Java Item Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE_ITEM__JAVA_ITEM_TYPE = eINSTANCE.getPaletteItem_JavaItemType();

		/**
		 * The meta object literal for the '<em><b>Model Item Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE_ITEM__MODEL_ITEM_TYPE = eINSTANCE.getPaletteItem_ModelItemType();

		/**
		 * The meta object literal for the '<em><b>Title Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_ITEM__TITLE_REF = eINSTANCE.getPaletteItem_TitleRef();

		/**
		 * The meta object literal for the '<em><b>Tooltip Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_ITEM__TOOLTIP_REF = eINSTANCE.getPaletteItem_TooltipRef();

		/**
		 * The meta object literal for the '<em><b>Icon Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_ITEM__ICON_REF = eINSTANCE.getPaletteItem_IconRef();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_ITEM__TITLE = eINSTANCE.getPaletteItem_Title();

		/**
		 * The meta object literal for the '<em><b>Tooltip</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_ITEM__TOOLTIP = eINSTANCE.getPaletteItem_Tooltip();

		/**
		 * The meta object literal for the '<em><b>Icon</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_ITEM__ICON = eINSTANCE.getPaletteItem_Icon();

		/**
		 * The meta object literal for the '<em><b>Attached Resource</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_ITEM__ATTACHED_RESOURCE = eINSTANCE.getPaletteItem_AttachedResource();

		/**
		 * The meta object literal for the '<em><b>Help Content</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE_ITEM__HELP_CONTENT = eINSTANCE.getPaletteItem_HelpContent();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_ITEM__COLOR = eINSTANCE.getPaletteItem_Color();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_ITEM__ID = eINSTANCE.getPaletteItem_Id();

		/**
		 * The meta object literal for the '<em><b>Internal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_ITEM__INTERNAL = eINSTANCE.getPaletteItem_Internal();

		/**
		 * The meta object literal for the '<em><b>Visible</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_ITEM__VISIBLE = eINSTANCE.getPaletteItem_Visible();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.palette.impl.PaletteModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.palette.impl.PaletteModelImpl
		 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getPaletteModel()
		 * @generated
		 */
		EClass PALETTE_MODEL = eINSTANCE.getPaletteModel();

		/**
		 * The meta object literal for the '<em><b>Palette Tool Set</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PALETTE_MODEL__PALETTE_TOOL_SET = eINSTANCE.getPaletteModel_PaletteToolSet();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_MODEL__NAME = eINSTANCE.getPaletteModel_Name();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.common.palette.Tab <em>Tab</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.palette.Tab
		 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getTab()
		 * @generated
		 */
		EEnum TAB = eINSTANCE.getTab();

		/**
		 * The meta object literal for the '<em>Tab Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.common.palette.Tab
		 * @see com.tibco.cep.studio.common.palette.impl.PalettePackageImpl#getTabObject()
		 * @generated
		 */
		EDataType TAB_OBJECT = eINSTANCE.getTabObject();

	}

} //PalettePackage
