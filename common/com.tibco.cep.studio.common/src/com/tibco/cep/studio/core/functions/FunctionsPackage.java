/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.functions;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * <!-- begin-model-doc -->
 * 
 *             Describes a function in a registry. Functions are written in Java Language
 *             The functions have public static modifiers.
 *             The return type of the function are limited to
 *                 a.&gt; primitives of Java Language - char, short, int, float, double, boolean, byte
 *                 b.&gt; Entity Model Object - such as Concept, Event, or well defined entities in the Model of that type
 *                 c.&gt; Java Object. Any other type are widened to Java Object.
 *                 d.&gt; Arrays are treated as Java Object.
 * 
 *             An xml instance of this schema has to be present in the root folder of an Java Archive resource (jar).
 *             The document itself must be named "functions.catalog" with catalog as the root element.
 * 
 *             The jar file must be in the class path of engine, or workbench.
 * 
 *         
 * 
 *                     Category of function. It is a list of functions that are categorically organized
 *                 
 * 
 *                     A catalog of categories.
 *                 
 * <!-- end-model-doc -->
 * @see com.tibco.cep.studio.core.functions.FunctionsFactory
 * @model kind="package"
 * @generated
 */
public interface FunctionsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "functions";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.tibco.com/com.tibco.be.functions#function_catalog.xsd";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "functions";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FunctionsPackage eINSTANCE = com.tibco.cep.studio.core.functions.impl.FunctionsPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.functions.impl.CatalogImpl <em>Catalog</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.functions.impl.CatalogImpl
	 * @see com.tibco.cep.studio.core.functions.impl.FunctionsPackageImpl#getCatalog()
	 * @generated
	 */
	int CATALOG = 0;

	/**
	 * The feature id for the '<em><b>Category</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATALOG__CATEGORY = 0;

	/**
	 * The number of structural features of the '<em>Catalog</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATALOG_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.functions.impl.CategoryImpl <em>Category</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.functions.impl.CategoryImpl
	 * @see com.tibco.cep.studio.core.functions.impl.FunctionsPackageImpl#getCategory()
	 * @generated
	 */
	int CATEGORY = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Valid In Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__VALID_IN_QUERY = 1;

	/**
	 * The feature id for the '<em><b>Valid In BUI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__VALID_IN_BUI = 2;

	/**
	 * The feature id for the '<em><b>Functions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__FUNCTIONS = 3;

	/**
	 * The feature id for the '<em><b>Categories</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__CATEGORIES = 4;

	/**
	 * The number of structural features of the '<em>Category</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.functions.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.functions.impl.DocumentRootImpl
	 * @see com.tibco.cep.studio.core.functions.impl.FunctionsPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 2;

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
	 * The feature id for the '<em><b>Catalog</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CATALOG = 3;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.functions.impl.FunctionImpl <em>Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.functions.impl.FunctionImpl
	 * @see com.tibco.cep.studio.core.functions.impl.FunctionsPackageImpl#getFunction()
	 * @generated
	 */
	int FUNCTION = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__CLASS = 1;

	/**
	 * The feature id for the '<em><b>Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__DESC = 2;

	/**
	 * The feature id for the '<em><b>Help Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__HELP_URL = 3;

	/**
	 * The feature id for the '<em><b>Mapper</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__MAPPER = 4;

	/**
	 * The feature id for the '<em><b>Async</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__ASYNC = 5;

	/**
	 * The feature id for the '<em><b>Timesensitive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__TIMESENSITIVE = 6;

	/**
	 * The feature id for the '<em><b>Modify</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__MODIFY = 7;

	/**
	 * The number of structural features of the '<em>Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FEATURE_COUNT = 8;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.functions.Catalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Catalog</em>'.
	 * @see com.tibco.cep.studio.core.functions.Catalog
	 * @generated
	 */
	EClass getCatalog();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.core.functions.Catalog#getCategory <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Category</em>'.
	 * @see com.tibco.cep.studio.core.functions.Catalog#getCategory()
	 * @see #getCatalog()
	 * @generated
	 */
	EReference getCatalog_Category();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.functions.Category <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Category</em>'.
	 * @see com.tibco.cep.studio.core.functions.Category
	 * @generated
	 */
	EClass getCategory();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.functions.Category#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.studio.core.functions.Category#getName()
	 * @see #getCategory()
	 * @generated
	 */
	EAttribute getCategory_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.functions.Category#isValidInQuery <em>Valid In Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Valid In Query</em>'.
	 * @see com.tibco.cep.studio.core.functions.Category#isValidInQuery()
	 * @see #getCategory()
	 * @generated
	 */
	EAttribute getCategory_ValidInQuery();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.functions.Category#isValidInBUI <em>Valid In BUI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Valid In BUI</em>'.
	 * @see com.tibco.cep.studio.core.functions.Category#isValidInBUI()
	 * @see #getCategory()
	 * @generated
	 */
	EAttribute getCategory_ValidInBUI();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.core.functions.Category#getFunctions <em>Functions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Functions</em>'.
	 * @see com.tibco.cep.studio.core.functions.Category#getFunctions()
	 * @see #getCategory()
	 * @generated
	 */
	EReference getCategory_Functions();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.core.functions.Category#getCategories <em>Categories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Categories</em>'.
	 * @see com.tibco.cep.studio.core.functions.Category#getCategories()
	 * @see #getCategory()
	 * @generated
	 */
	EReference getCategory_Categories();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.functions.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.tibco.cep.studio.core.functions.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.tibco.cep.studio.core.functions.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.tibco.cep.studio.core.functions.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.tibco.cep.studio.core.functions.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.tibco.cep.studio.core.functions.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.tibco.cep.studio.core.functions.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.tibco.cep.studio.core.functions.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.functions.DocumentRoot#getCatalog <em>Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Catalog</em>'.
	 * @see com.tibco.cep.studio.core.functions.DocumentRoot#getCatalog()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Catalog();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.functions.Function <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function</em>'.
	 * @see com.tibco.cep.studio.core.functions.Function
	 * @generated
	 */
	EClass getFunction();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.functions.Function#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.studio.core.functions.Function#getName()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.functions.Function#getClass_ <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class</em>'.
	 * @see com.tibco.cep.studio.core.functions.Function#getClass_()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_Class();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.functions.Function#getDesc <em>Desc</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Desc</em>'.
	 * @see com.tibco.cep.studio.core.functions.Function#getDesc()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_Desc();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.functions.Function#getHelpUrl <em>Help Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Help Url</em>'.
	 * @see com.tibco.cep.studio.core.functions.Function#getHelpUrl()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_HelpUrl();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.functions.Function#isMapper <em>Mapper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mapper</em>'.
	 * @see com.tibco.cep.studio.core.functions.Function#isMapper()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_Mapper();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.functions.Function#isAsync <em>Async</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Async</em>'.
	 * @see com.tibco.cep.studio.core.functions.Function#isAsync()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_Async();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.functions.Function#isTimesensitive <em>Timesensitive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timesensitive</em>'.
	 * @see com.tibco.cep.studio.core.functions.Function#isTimesensitive()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_Timesensitive();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.functions.Function#isModify <em>Modify</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Modify</em>'.
	 * @see com.tibco.cep.studio.core.functions.Function#isModify()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_Modify();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	FunctionsFactory getFunctionsFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.functions.impl.CatalogImpl <em>Catalog</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.functions.impl.CatalogImpl
		 * @see com.tibco.cep.studio.core.functions.impl.FunctionsPackageImpl#getCatalog()
		 * @generated
		 */
		EClass CATALOG = eINSTANCE.getCatalog();

		/**
		 * The meta object literal for the '<em><b>Category</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CATALOG__CATEGORY = eINSTANCE.getCatalog_Category();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.functions.impl.CategoryImpl <em>Category</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.functions.impl.CategoryImpl
		 * @see com.tibco.cep.studio.core.functions.impl.FunctionsPackageImpl#getCategory()
		 * @generated
		 */
		EClass CATEGORY = eINSTANCE.getCategory();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CATEGORY__NAME = eINSTANCE.getCategory_Name();

		/**
		 * The meta object literal for the '<em><b>Valid In Query</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CATEGORY__VALID_IN_QUERY = eINSTANCE.getCategory_ValidInQuery();

		/**
		 * The meta object literal for the '<em><b>Valid In BUI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CATEGORY__VALID_IN_BUI = eINSTANCE.getCategory_ValidInBUI();

		/**
		 * The meta object literal for the '<em><b>Functions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CATEGORY__FUNCTIONS = eINSTANCE.getCategory_Functions();

		/**
		 * The meta object literal for the '<em><b>Categories</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CATEGORY__CATEGORIES = eINSTANCE.getCategory_Categories();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.functions.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.functions.impl.DocumentRootImpl
		 * @see com.tibco.cep.studio.core.functions.impl.FunctionsPackageImpl#getDocumentRoot()
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
		 * The meta object literal for the '<em><b>Catalog</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__CATALOG = eINSTANCE.getDocumentRoot_Catalog();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.functions.impl.FunctionImpl <em>Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.functions.impl.FunctionImpl
		 * @see com.tibco.cep.studio.core.functions.impl.FunctionsPackageImpl#getFunction()
		 * @generated
		 */
		EClass FUNCTION = eINSTANCE.getFunction();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__NAME = eINSTANCE.getFunction_Name();

		/**
		 * The meta object literal for the '<em><b>Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__CLASS = eINSTANCE.getFunction_Class();

		/**
		 * The meta object literal for the '<em><b>Desc</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__DESC = eINSTANCE.getFunction_Desc();

		/**
		 * The meta object literal for the '<em><b>Help Url</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__HELP_URL = eINSTANCE.getFunction_HelpUrl();

		/**
		 * The meta object literal for the '<em><b>Mapper</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__MAPPER = eINSTANCE.getFunction_Mapper();

		/**
		 * The meta object literal for the '<em><b>Async</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__ASYNC = eINSTANCE.getFunction_Async();

		/**
		 * The meta object literal for the '<em><b>Timesensitive</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__TIMESENSITIVE = eINSTANCE.getFunction_Timesensitive();

		/**
		 * The meta object literal for the '<em><b>Modify</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION__MODIFY = eINSTANCE.getFunction_Modify();

	}

} //FunctionsPackage
