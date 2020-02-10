/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.palette.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.cep.studio.common.palette.DocumentRoot;
import com.tibco.cep.studio.common.palette.EmfType;
import com.tibco.cep.studio.common.palette.Help;
import com.tibco.cep.studio.common.palette.JavaType;
import com.tibco.cep.studio.common.palette.ModelType;
import com.tibco.cep.studio.common.palette.PaletteFactory;
import com.tibco.cep.studio.common.palette.PaletteGroup;
import com.tibco.cep.studio.common.palette.PaletteItem;
import com.tibco.cep.studio.common.palette.PaletteModel;
import com.tibco.cep.studio.common.palette.PalettePackage;
import com.tibco.cep.studio.common.palette.Tab;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PalettePackageImpl extends EPackageImpl implements PalettePackage {
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
	private EClass emfTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass helpEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass paletteGroupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass paletteItemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass paletteModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum tabEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType tabObjectEDataType = null;

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
	 * @see com.tibco.cep.studio.common.palette.PalettePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PalettePackageImpl() {
		super(eNS_URI, PaletteFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link PalettePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PalettePackage init() {
		if (isInited) return (PalettePackage)EPackage.Registry.INSTANCE.getEPackage(PalettePackage.eNS_URI);

		// Obtain or create and register package
		PalettePackageImpl thePalettePackage = (PalettePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof PalettePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new PalettePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		thePalettePackage.createPackageContents();

		// Initialize created meta-data
		thePalettePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePalettePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PalettePackage.eNS_URI, thePalettePackage);
		return thePalettePackage;
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
	public EReference getDocumentRoot_Model() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEmfType() {
		return emfTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEmfType_EmfType() {
		return (EAttribute)emfTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEmfType_ExtendedType() {
		return (EAttribute)emfTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHelp() {
		return helpEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHelp_Content() {
		return (EAttribute)helpEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHelp_Tab() {
		return (EAttribute)helpEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJavaType() {
		return javaTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getJavaType_Type() {
		return (EAttribute)javaTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModelType() {
		return modelTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModelType_Type() {
		return (EAttribute)modelTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPaletteGroup() {
		return paletteGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteGroup_TitleRef() {
		return (EAttribute)paletteGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteGroup_TooltipRef() {
		return (EAttribute)paletteGroupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteGroup_IconRef() {
		return (EAttribute)paletteGroupEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteGroup_Title() {
		return (EAttribute)paletteGroupEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteGroup_Tooltip() {
		return (EAttribute)paletteGroupEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteGroup_Icon() {
		return (EAttribute)paletteGroupEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaletteGroup_PaletteItem() {
		return (EReference)paletteGroupEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaletteGroup_PaletteToolSet() {
		return (EReference)paletteGroupEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteGroup_Color() {
		return (EAttribute)paletteGroupEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteGroup_Id() {
		return (EAttribute)paletteGroupEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteGroup_Internal() {
		return (EAttribute)paletteGroupEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteGroup_Visible() {
		return (EAttribute)paletteGroupEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPaletteItem() {
		return paletteItemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaletteItem_EmfItemType() {
		return (EReference)paletteItemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaletteItem_JavaItemType() {
		return (EReference)paletteItemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaletteItem_ModelItemType() {
		return (EReference)paletteItemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteItem_TitleRef() {
		return (EAttribute)paletteItemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteItem_TooltipRef() {
		return (EAttribute)paletteItemEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteItem_IconRef() {
		return (EAttribute)paletteItemEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteItem_Title() {
		return (EAttribute)paletteItemEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteItem_Tooltip() {
		return (EAttribute)paletteItemEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteItem_Icon() {
		return (EAttribute)paletteItemEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteItem_AttachedResource() {
		return (EAttribute)paletteItemEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaletteItem_HelpContent() {
		return (EReference)paletteItemEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteItem_Color() {
		return (EAttribute)paletteItemEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteItem_Id() {
		return (EAttribute)paletteItemEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteItem_Internal() {
		return (EAttribute)paletteItemEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteItem_Visible() {
		return (EAttribute)paletteItemEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPaletteModel() {
		return paletteModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaletteModel_PaletteToolSet() {
		return (EReference)paletteModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaletteModel_Name() {
		return (EAttribute)paletteModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTab() {
		return tabEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getTabObject() {
		return tabObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PaletteFactory getPaletteFactory() {
		return (PaletteFactory)getEFactoryInstance();
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
		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MODEL);

		emfTypeEClass = createEClass(EMF_TYPE);
		createEAttribute(emfTypeEClass, EMF_TYPE__EMF_TYPE);
		createEAttribute(emfTypeEClass, EMF_TYPE__EXTENDED_TYPE);

		helpEClass = createEClass(HELP);
		createEAttribute(helpEClass, HELP__TAB);
		createEAttribute(helpEClass, HELP__CONTENT);

		javaTypeEClass = createEClass(JAVA_TYPE);
		createEAttribute(javaTypeEClass, JAVA_TYPE__TYPE);

		modelTypeEClass = createEClass(MODEL_TYPE);
		createEAttribute(modelTypeEClass, MODEL_TYPE__TYPE);

		paletteGroupEClass = createEClass(PALETTE_GROUP);
		createEAttribute(paletteGroupEClass, PALETTE_GROUP__TITLE_REF);
		createEAttribute(paletteGroupEClass, PALETTE_GROUP__TOOLTIP_REF);
		createEAttribute(paletteGroupEClass, PALETTE_GROUP__ICON_REF);
		createEAttribute(paletteGroupEClass, PALETTE_GROUP__TITLE);
		createEAttribute(paletteGroupEClass, PALETTE_GROUP__TOOLTIP);
		createEAttribute(paletteGroupEClass, PALETTE_GROUP__ICON);
		createEReference(paletteGroupEClass, PALETTE_GROUP__PALETTE_ITEM);
		createEReference(paletteGroupEClass, PALETTE_GROUP__PALETTE_TOOL_SET);
		createEAttribute(paletteGroupEClass, PALETTE_GROUP__COLOR);
		createEAttribute(paletteGroupEClass, PALETTE_GROUP__ID);
		createEAttribute(paletteGroupEClass, PALETTE_GROUP__INTERNAL);
		createEAttribute(paletteGroupEClass, PALETTE_GROUP__VISIBLE);

		paletteItemEClass = createEClass(PALETTE_ITEM);
		createEReference(paletteItemEClass, PALETTE_ITEM__EMF_ITEM_TYPE);
		createEReference(paletteItemEClass, PALETTE_ITEM__JAVA_ITEM_TYPE);
		createEReference(paletteItemEClass, PALETTE_ITEM__MODEL_ITEM_TYPE);
		createEAttribute(paletteItemEClass, PALETTE_ITEM__TITLE_REF);
		createEAttribute(paletteItemEClass, PALETTE_ITEM__TOOLTIP_REF);
		createEAttribute(paletteItemEClass, PALETTE_ITEM__ICON_REF);
		createEAttribute(paletteItemEClass, PALETTE_ITEM__TITLE);
		createEAttribute(paletteItemEClass, PALETTE_ITEM__TOOLTIP);
		createEAttribute(paletteItemEClass, PALETTE_ITEM__ICON);
		createEAttribute(paletteItemEClass, PALETTE_ITEM__ATTACHED_RESOURCE);
		createEReference(paletteItemEClass, PALETTE_ITEM__HELP_CONTENT);
		createEAttribute(paletteItemEClass, PALETTE_ITEM__COLOR);
		createEAttribute(paletteItemEClass, PALETTE_ITEM__ID);
		createEAttribute(paletteItemEClass, PALETTE_ITEM__INTERNAL);
		createEAttribute(paletteItemEClass, PALETTE_ITEM__VISIBLE);

		paletteModelEClass = createEClass(PALETTE_MODEL);
		createEReference(paletteModelEClass, PALETTE_MODEL__PALETTE_TOOL_SET);
		createEAttribute(paletteModelEClass, PALETTE_MODEL__NAME);

		// Create enums
		tabEEnum = createEEnum(TAB);

		// Create data types
		tabObjectEDataType = createEDataType(TAB_OBJECT);
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
		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Model(), this.getPaletteModel(), null, "model", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(emfTypeEClass, EmfType.class, "EmfType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEmfType_EmfType(), theXMLTypePackage.getString(), "emfType", "", 1, 1, EmfType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEmfType_ExtendedType(), theXMLTypePackage.getString(), "extendedType", "", 0, 1, EmfType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(helpEClass, Help.class, "Help", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getHelp_Tab(), this.getTab(), "tab", null, 1, 1, Help.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHelp_Content(), theXMLTypePackage.getString(), "content", "", 0, 1, Help.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(javaTypeEClass, JavaType.class, "JavaType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getJavaType_Type(), theXMLTypePackage.getString(), "type", "", 1, -1, JavaType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(modelTypeEClass, ModelType.class, "ModelType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModelType_Type(), theXMLTypePackage.getString(), "type", "", 1, -1, ModelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(paletteGroupEClass, PaletteGroup.class, "PaletteGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPaletteGroup_TitleRef(), theXMLTypePackage.getString(), "titleRef", "", 0, 1, PaletteGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteGroup_TooltipRef(), theXMLTypePackage.getString(), "tooltipRef", "", 0, 1, PaletteGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteGroup_IconRef(), theXMLTypePackage.getString(), "iconRef", "", 0, 1, PaletteGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteGroup_Title(), theXMLTypePackage.getString(), "title", "", 0, 1, PaletteGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteGroup_Tooltip(), theXMLTypePackage.getString(), "tooltip", "", 0, 1, PaletteGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteGroup_Icon(), theXMLTypePackage.getString(), "icon", "", 0, 1, PaletteGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaletteGroup_PaletteItem(), this.getPaletteItem(), null, "paletteItem", null, 0, -1, PaletteGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaletteGroup_PaletteToolSet(), this.getPaletteGroup(), null, "paletteToolSet", null, 0, -1, PaletteGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteGroup_Color(), theXMLTypePackage.getString(), "color", null, 0, 1, PaletteGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteGroup_Id(), theXMLTypePackage.getID(), "id", null, 0, 1, PaletteGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteGroup_Internal(), theXMLTypePackage.getBoolean(), "internal", "false", 0, 1, PaletteGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteGroup_Visible(), theXMLTypePackage.getBoolean(), "visible", "true", 0, 1, PaletteGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(paletteItemEClass, PaletteItem.class, "PaletteItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPaletteItem_EmfItemType(), this.getEmfType(), null, "emfItemType", null, 0, 1, PaletteItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaletteItem_JavaItemType(), this.getJavaType(), null, "javaItemType", null, 0, 1, PaletteItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaletteItem_ModelItemType(), this.getModelType(), null, "modelItemType", null, 0, 1, PaletteItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteItem_TitleRef(), theXMLTypePackage.getString(), "titleRef", "", 0, 1, PaletteItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteItem_TooltipRef(), theXMLTypePackage.getString(), "tooltipRef", "", 0, 1, PaletteItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteItem_IconRef(), theXMLTypePackage.getString(), "iconRef", "", 0, 1, PaletteItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteItem_Title(), theXMLTypePackage.getString(), "title", "", 0, 1, PaletteItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteItem_Tooltip(), theXMLTypePackage.getString(), "tooltip", "", 0, 1, PaletteItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteItem_Icon(), theXMLTypePackage.getString(), "icon", "", 0, 1, PaletteItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteItem_AttachedResource(), theXMLTypePackage.getString(), "attachedResource", "", 0, 1, PaletteItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaletteItem_HelpContent(), this.getHelp(), null, "helpContent", null, 0, -1, PaletteItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteItem_Color(), theXMLTypePackage.getString(), "color", null, 0, 1, PaletteItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteItem_Id(), theXMLTypePackage.getID(), "id", null, 0, 1, PaletteItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteItem_Internal(), theXMLTypePackage.getBoolean(), "internal", "false", 0, 1, PaletteItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteItem_Visible(), theXMLTypePackage.getBoolean(), "visible", "true", 0, 1, PaletteItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(paletteModelEClass, PaletteModel.class, "PaletteModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPaletteModel_PaletteToolSet(), this.getPaletteGroup(), null, "paletteToolSet", null, 0, -1, PaletteModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaletteModel_Name(), theXMLTypePackage.getString(), "name", "", 0, 1, PaletteModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(tabEEnum, Tab.class, "Tab");
		addEEnumLiteral(tabEEnum, Tab.GENERAL);
		addEEnumLiteral(tabEEnum, Tab.LOOP_CHARACTERISTICS);
		addEEnumLiteral(tabEEnum, Tab.REPLY_EVENT);
		addEEnumLiteral(tabEEnum, Tab.TIMEOUT);

		// Initialize data types
		initEDataType(tabObjectEDataType, Tab.class, "TabObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

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
		  (getDocumentRoot_Model(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "model",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (emfTypeEClass, 
		   source, 
		   new String[] {
			 "name", "emfType",
			 "kind", "elementOnly"
		   });	
		addAnnotation
		  (getEmfType_EmfType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "emfType",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getEmfType_ExtendedType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "extendedType",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (helpEClass, 
		   source, 
		   new String[] {
			 "name", "help",
			 "kind", "elementOnly"
		   });	
		addAnnotation
		  (getHelp_Tab(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "tab",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getHelp_Content(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "content"
		   });	
		addAnnotation
		  (javaTypeEClass, 
		   source, 
		   new String[] {
			 "name", "javaType",
			 "kind", "elementOnly"
		   });	
		addAnnotation
		  (getJavaType_Type(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "type",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (modelTypeEClass, 
		   source, 
		   new String[] {
			 "name", "modelType",
			 "kind", "elementOnly"
		   });	
		addAnnotation
		  (getModelType_Type(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "type",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (paletteGroupEClass, 
		   source, 
		   new String[] {
			 "name", "paletteGroup",
			 "kind", "elementOnly"
		   });	
		addAnnotation
		  (getPaletteGroup_TitleRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "titleRef",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteGroup_TooltipRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "tooltipRef",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteGroup_IconRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "iconRef",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteGroup_Title(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "title",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteGroup_Tooltip(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "tooltip",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteGroup_Icon(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "icon",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteGroup_PaletteItem(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "paletteItem",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteGroup_PaletteToolSet(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "paletteToolSet",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteGroup_Color(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "color"
		   });	
		addAnnotation
		  (getPaletteGroup_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id"
		   });	
		addAnnotation
		  (getPaletteGroup_Internal(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "internal"
		   });	
		addAnnotation
		  (getPaletteGroup_Visible(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "visible"
		   });	
		addAnnotation
		  (paletteItemEClass, 
		   source, 
		   new String[] {
			 "name", "paletteItem",
			 "kind", "elementOnly"
		   });	
		addAnnotation
		  (getPaletteItem_EmfItemType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "emfItemType",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteItem_JavaItemType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "javaItemType",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteItem_ModelItemType(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "modelItemType",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteItem_TitleRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "titleRef",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteItem_TooltipRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "tooltipRef",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteItem_IconRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "iconRef",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteItem_Title(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "title",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteItem_Tooltip(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "tooltip",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteItem_Icon(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "icon",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteItem_AttachedResource(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "attachedResource",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteItem_HelpContent(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "helpContent",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteItem_Color(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "color"
		   });	
		addAnnotation
		  (getPaletteItem_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id"
		   });	
		addAnnotation
		  (getPaletteItem_Internal(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "internal"
		   });	
		addAnnotation
		  (getPaletteItem_Visible(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "visible"
		   });	
		addAnnotation
		  (paletteModelEClass, 
		   source, 
		   new String[] {
			 "name", "paletteModel",
			 "kind", "elementOnly"
		   });	
		addAnnotation
		  (getPaletteModel_PaletteToolSet(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "paletteToolSet",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getPaletteModel_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });	
		addAnnotation
		  (tabEEnum, 
		   source, 
		   new String[] {
			 "name", "Tab"
		   });	
		addAnnotation
		  (tabObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "Tab:Object",
			 "baseType", "Tab"
		   });
	}

} //PalettePackageImpl
