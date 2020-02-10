/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.search.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl;
import com.tibco.cep.studio.core.index.model.scope.ScopePackage;
import com.tibco.cep.studio.core.index.model.scope.impl.ScopePackageImpl;
import com.tibco.cep.studio.core.index.model.search.ElementMatch;
import com.tibco.cep.studio.core.index.model.search.MethodArgumentMatch;
import com.tibco.cep.studio.core.index.model.search.NonEntityMatch;
import com.tibco.cep.studio.core.index.model.search.RuleSourceMatch;
import com.tibco.cep.studio.core.index.model.search.SearchFactory;
import com.tibco.cep.studio.core.index.model.search.SearchPackage;
import com.tibco.cep.studio.core.index.model.search.StringLiteralMatch;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SearchPackageImpl extends EPackageImpl implements SearchPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass elementMatchEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass methodArgumentMatchEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringLiteralMatchEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nonEntityMatchEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleSourceMatchEClass = null;

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
	 * @see com.tibco.cep.studio.core.index.model.search.SearchPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SearchPackageImpl() {
		super(eNS_URI, SearchFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link SearchPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SearchPackage init() {
		if (isInited) return (SearchPackage)EPackage.Registry.INSTANCE.getEPackage(SearchPackage.eNS_URI);

		// Obtain or create and register package
		SearchPackageImpl theSearchPackage = (SearchPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SearchPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SearchPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ModelPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		IndexPackageImpl theIndexPackage = (IndexPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(IndexPackage.eNS_URI) instanceof IndexPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(IndexPackage.eNS_URI) : IndexPackage.eINSTANCE);
		ScopePackageImpl theScopePackage = (ScopePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ScopePackage.eNS_URI) instanceof ScopePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ScopePackage.eNS_URI) : ScopePackage.eINSTANCE);

		// Create package meta-data objects
		theSearchPackage.createPackageContents();
		theIndexPackage.createPackageContents();
		theScopePackage.createPackageContents();

		// Initialize created meta-data
		theSearchPackage.initializePackageContents();
		theIndexPackage.initializePackageContents();
		theScopePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSearchPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SearchPackage.eNS_URI, theSearchPackage);
		return theSearchPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getElementMatch() {
		return elementMatchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElementMatch_Feature() {
		return (EReference)elementMatchEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElementMatch_MatchedElement() {
		return (EReference)elementMatchEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElementMatch_Label() {
		return (EAttribute)elementMatchEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMethodArgumentMatch() {
		return methodArgumentMatchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMethodArgumentMatch_Function() {
		return (EAttribute)methodArgumentMatchEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMethodArgumentMatch_ArgNode() {
		return (EAttribute)methodArgumentMatchEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringLiteralMatch() {
		return stringLiteralMatchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNonEntityMatch() {
		return nonEntityMatchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNonEntityMatch_ProjectName() {
		return (EAttribute)nonEntityMatchEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNonEntityMatch_FilePath() {
		return (EAttribute)nonEntityMatchEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNonEntityMatch_Match() {
		return (EAttribute)nonEntityMatchEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleSourceMatch() {
		return ruleSourceMatchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleSourceMatch_Offset() {
		return (EAttribute)ruleSourceMatchEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleSourceMatch_Length() {
		return (EAttribute)ruleSourceMatchEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleSourceMatch_ContainingRule() {
		return (EReference)ruleSourceMatchEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SearchFactory getSearchFactory() {
		return (SearchFactory)getEFactoryInstance();
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
		elementMatchEClass = createEClass(ELEMENT_MATCH);
		createEReference(elementMatchEClass, ELEMENT_MATCH__FEATURE);
		createEReference(elementMatchEClass, ELEMENT_MATCH__MATCHED_ELEMENT);
		createEAttribute(elementMatchEClass, ELEMENT_MATCH__LABEL);

		methodArgumentMatchEClass = createEClass(METHOD_ARGUMENT_MATCH);
		createEAttribute(methodArgumentMatchEClass, METHOD_ARGUMENT_MATCH__FUNCTION);
		createEAttribute(methodArgumentMatchEClass, METHOD_ARGUMENT_MATCH__ARG_NODE);

		stringLiteralMatchEClass = createEClass(STRING_LITERAL_MATCH);

		nonEntityMatchEClass = createEClass(NON_ENTITY_MATCH);
		createEAttribute(nonEntityMatchEClass, NON_ENTITY_MATCH__PROJECT_NAME);
		createEAttribute(nonEntityMatchEClass, NON_ENTITY_MATCH__FILE_PATH);
		createEAttribute(nonEntityMatchEClass, NON_ENTITY_MATCH__MATCH);

		ruleSourceMatchEClass = createEClass(RULE_SOURCE_MATCH);
		createEAttribute(ruleSourceMatchEClass, RULE_SOURCE_MATCH__OFFSET);
		createEAttribute(ruleSourceMatchEClass, RULE_SOURCE_MATCH__LENGTH);
		createEReference(ruleSourceMatchEClass, RULE_SOURCE_MATCH__CONTAINING_RULE);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		methodArgumentMatchEClass.getESuperTypes().add(this.getRuleSourceMatch());
		stringLiteralMatchEClass.getESuperTypes().add(this.getRuleSourceMatch());

		// Initialize classes and features; add operations and parameters
		initEClass(elementMatchEClass, ElementMatch.class, "ElementMatch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getElementMatch_Feature(), ecorePackage.getEObject(), null, "feature", null, 0, 1, ElementMatch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getElementMatch_MatchedElement(), ecorePackage.getEObject(), null, "matchedElement", null, 0, 1, ElementMatch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getElementMatch_Label(), ecorePackage.getEString(), "label", null, 0, 1, ElementMatch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(methodArgumentMatchEClass, MethodArgumentMatch.class, "MethodArgumentMatch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMethodArgumentMatch_Function(), ecorePackage.getEJavaObject(), "function", null, 0, 1, MethodArgumentMatch.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMethodArgumentMatch_ArgNode(), ecorePackage.getEJavaObject(), "argNode", null, 0, 1, MethodArgumentMatch.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringLiteralMatchEClass, StringLiteralMatch.class, "StringLiteralMatch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(nonEntityMatchEClass, NonEntityMatch.class, "NonEntityMatch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNonEntityMatch_ProjectName(), ecorePackage.getEString(), "projectName", null, 0, 1, NonEntityMatch.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNonEntityMatch_FilePath(), ecorePackage.getEString(), "filePath", null, 0, 1, NonEntityMatch.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNonEntityMatch_Match(), ecorePackage.getEJavaObject(), "match", null, 0, 1, NonEntityMatch.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleSourceMatchEClass, RuleSourceMatch.class, "RuleSourceMatch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRuleSourceMatch_Offset(), ecorePackage.getEInt(), "offset", null, 0, 1, RuleSourceMatch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleSourceMatch_Length(), ecorePackage.getEInt(), "length", null, 0, 1, RuleSourceMatch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleSourceMatch_ContainingRule(), ecorePackage.getEObject(), null, "containingRule", null, 0, 1, RuleSourceMatch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //SearchPackageImpl
