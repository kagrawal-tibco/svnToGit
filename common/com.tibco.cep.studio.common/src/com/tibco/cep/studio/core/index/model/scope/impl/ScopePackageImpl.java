/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.scope.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl;
import com.tibco.cep.studio.core.index.model.scope.CompilableScope;
import com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry;
import com.tibco.cep.studio.core.index.model.scope.RootScopeBlock;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.model.scope.ScopeFactory;
import com.tibco.cep.studio.core.index.model.scope.ScopePackage;
import com.tibco.cep.studio.core.index.model.search.SearchPackage;
import com.tibco.cep.studio.core.index.model.search.impl.SearchPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ScopePackageImpl extends EPackageImpl implements ScopePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass scopeBlockEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rootScopeBlockEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compilableScopeEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compilableScopeEntryEClass = null;

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
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ScopePackageImpl() {
		super(eNS_URI, ScopeFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ScopePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ScopePackage init() {
		if (isInited) return (ScopePackage)EPackage.Registry.INSTANCE.getEPackage(ScopePackage.eNS_URI);

		// Obtain or create and register package
		ScopePackageImpl theScopePackage = (ScopePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ScopePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ScopePackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ModelPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		IndexPackageImpl theIndexPackage = (IndexPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(IndexPackage.eNS_URI) instanceof IndexPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(IndexPackage.eNS_URI) : IndexPackage.eINSTANCE);
		SearchPackageImpl theSearchPackage = (SearchPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SearchPackage.eNS_URI) instanceof SearchPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SearchPackage.eNS_URI) : SearchPackage.eINSTANCE);

		// Create package meta-data objects
		theScopePackage.createPackageContents();
		theIndexPackage.createPackageContents();
		theSearchPackage.createPackageContents();

		// Initialize created meta-data
		theScopePackage.initializePackageContents();
		theIndexPackage.initializePackageContents();
		theSearchPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theScopePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ScopePackage.eNS_URI, theScopePackage);
		return theScopePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getScopeBlock() {
		return scopeBlockEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScopeBlock_Type() {
		return (EAttribute)scopeBlockEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScopeBlock_ParentScopeDef() {
		return (EReference)scopeBlockEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScopeBlock_ChildScopeDefs() {
		return (EReference)scopeBlockEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScopeBlock_Defs() {
		return (EReference)scopeBlockEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScopeBlock_Refs() {
		return (EReference)scopeBlockEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScopeBlock_Offset() {
		return (EAttribute)scopeBlockEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScopeBlock_Length() {
		return (EAttribute)scopeBlockEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRootScopeBlock() {
		return rootScopeBlockEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRootScopeBlock_DefinitionRef() {
		return (EReference)rootScopeBlockEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompilableScope() {
		return compilableScopeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompilableScope_GlobalVariables() {
		return (EReference)compilableScopeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompilableScope_ConditionScope() {
		return (EReference)compilableScopeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompilableScope_ActionScope() {
		return (EReference)compilableScopeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompilableScopeEntry() {
		return compilableScopeEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompilableScopeEntry_RuleName() {
		return (EAttribute)compilableScopeEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompilableScopeEntry_Scope() {
		return (EReference)compilableScopeEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScopeFactory getScopeFactory() {
		return (ScopeFactory)getEFactoryInstance();
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
		scopeBlockEClass = createEClass(SCOPE_BLOCK);
		createEAttribute(scopeBlockEClass, SCOPE_BLOCK__TYPE);
		createEReference(scopeBlockEClass, SCOPE_BLOCK__PARENT_SCOPE_DEF);
		createEReference(scopeBlockEClass, SCOPE_BLOCK__CHILD_SCOPE_DEFS);
		createEReference(scopeBlockEClass, SCOPE_BLOCK__DEFS);
		createEReference(scopeBlockEClass, SCOPE_BLOCK__REFS);
		createEAttribute(scopeBlockEClass, SCOPE_BLOCK__OFFSET);
		createEAttribute(scopeBlockEClass, SCOPE_BLOCK__LENGTH);

		rootScopeBlockEClass = createEClass(ROOT_SCOPE_BLOCK);
		createEReference(rootScopeBlockEClass, ROOT_SCOPE_BLOCK__DEFINITION_REF);

		compilableScopeEClass = createEClass(COMPILABLE_SCOPE);
		createEReference(compilableScopeEClass, COMPILABLE_SCOPE__GLOBAL_VARIABLES);
		createEReference(compilableScopeEClass, COMPILABLE_SCOPE__CONDITION_SCOPE);
		createEReference(compilableScopeEClass, COMPILABLE_SCOPE__ACTION_SCOPE);

		compilableScopeEntryEClass = createEClass(COMPILABLE_SCOPE_ENTRY);
		createEAttribute(compilableScopeEntryEClass, COMPILABLE_SCOPE_ENTRY__RULE_NAME);
		createEReference(compilableScopeEntryEClass, COMPILABLE_SCOPE_ENTRY__SCOPE);
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
		IndexPackage theIndexPackage = (IndexPackage)EPackage.Registry.INSTANCE.getEPackage(IndexPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		scopeBlockEClass.getESuperTypes().add(theIndexPackage.getStructuredElement());
		rootScopeBlockEClass.getESuperTypes().add(this.getScopeBlock());

		// Initialize classes and features; add operations and parameters
		initEClass(scopeBlockEClass, ScopeBlock.class, "ScopeBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getScopeBlock_Type(), ecorePackage.getEInt(), "type", null, 0, 1, ScopeBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScopeBlock_ParentScopeDef(), this.getScopeBlock(), this.getScopeBlock_ChildScopeDefs(), "parentScopeDef", null, 0, 1, ScopeBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScopeBlock_ChildScopeDefs(), this.getScopeBlock(), this.getScopeBlock_ParentScopeDef(), "childScopeDefs", null, 0, -1, ScopeBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScopeBlock_Defs(), theIndexPackage.getLocalVariableDef(), null, "defs", null, 0, -1, ScopeBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScopeBlock_Refs(), theIndexPackage.getElementReference(), null, "refs", null, 0, -1, ScopeBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScopeBlock_Offset(), ecorePackage.getEInt(), "offset", null, 1, 1, ScopeBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScopeBlock_Length(), ecorePackage.getEInt(), "length", null, 1, 1, ScopeBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(scopeBlockEClass, null, "accept", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theIndexPackage.getIStructuredElementVisitor(), "visitor", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(scopeBlockEClass, null, "doVisitChildren", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theIndexPackage.getIStructuredElementVisitor(), "visitor", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(rootScopeBlockEClass, RootScopeBlock.class, "RootScopeBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRootScopeBlock_DefinitionRef(), theIndexPackage.getElementReference(), null, "definitionRef", null, 1, 1, RootScopeBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compilableScopeEClass, CompilableScope.class, "CompilableScope", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompilableScope_GlobalVariables(), theIndexPackage.getGlobalVariableDef(), null, "globalVariables", null, 0, -1, CompilableScope.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompilableScope_ConditionScope(), this.getScopeBlock(), null, "conditionScope", null, 0, 1, CompilableScope.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompilableScope_ActionScope(), this.getScopeBlock(), null, "actionScope", null, 0, 1, CompilableScope.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compilableScopeEntryEClass, CompilableScopeEntry.class, "CompilableScopeEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCompilableScopeEntry_RuleName(), ecorePackage.getEString(), "ruleName", null, 1, 1, CompilableScopeEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompilableScopeEntry_Scope(), this.getCompilableScope(), null, "scope", null, 1, 1, CompilableScopeEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //ScopePackageImpl
