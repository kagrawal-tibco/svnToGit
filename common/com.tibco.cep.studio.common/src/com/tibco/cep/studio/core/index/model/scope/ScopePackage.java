/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.scope;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import com.tibco.cep.studio.core.index.model.IndexPackage;

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
 * @see com.tibco.cep.studio.core.index.model.scope.ScopeFactory
 * @model kind="package"
 * @generated
 */
public interface ScopePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "scope";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/designer/index/core/model/scope/ontology_index.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "scope";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ScopePackage eINSTANCE = com.tibco.cep.studio.core.index.model.scope.impl.ScopePackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.scope.impl.ScopeBlockImpl <em>Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.scope.impl.ScopeBlockImpl
	 * @see com.tibco.cep.studio.core.index.model.scope.impl.ScopePackageImpl#getScopeBlock()
	 * @generated
	 */
	int SCOPE_BLOCK = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPE_BLOCK__TYPE = IndexPackage.STRUCTURED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parent Scope Def</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPE_BLOCK__PARENT_SCOPE_DEF = IndexPackage.STRUCTURED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Child Scope Defs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPE_BLOCK__CHILD_SCOPE_DEFS = IndexPackage.STRUCTURED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Defs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPE_BLOCK__DEFS = IndexPackage.STRUCTURED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPE_BLOCK__REFS = IndexPackage.STRUCTURED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPE_BLOCK__OFFSET = IndexPackage.STRUCTURED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPE_BLOCK__LENGTH = IndexPackage.STRUCTURED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCOPE_BLOCK_FEATURE_COUNT = IndexPackage.STRUCTURED_ELEMENT_FEATURE_COUNT + 7;


	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.scope.impl.RootScopeBlockImpl <em>Root Scope Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.scope.impl.RootScopeBlockImpl
	 * @see com.tibco.cep.studio.core.index.model.scope.impl.ScopePackageImpl#getRootScopeBlock()
	 * @generated
	 */
	int ROOT_SCOPE_BLOCK = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_SCOPE_BLOCK__TYPE = SCOPE_BLOCK__TYPE;

	/**
	 * The feature id for the '<em><b>Parent Scope Def</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_SCOPE_BLOCK__PARENT_SCOPE_DEF = SCOPE_BLOCK__PARENT_SCOPE_DEF;

	/**
	 * The feature id for the '<em><b>Child Scope Defs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_SCOPE_BLOCK__CHILD_SCOPE_DEFS = SCOPE_BLOCK__CHILD_SCOPE_DEFS;

	/**
	 * The feature id for the '<em><b>Defs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_SCOPE_BLOCK__DEFS = SCOPE_BLOCK__DEFS;

	/**
	 * The feature id for the '<em><b>Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_SCOPE_BLOCK__REFS = SCOPE_BLOCK__REFS;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_SCOPE_BLOCK__OFFSET = SCOPE_BLOCK__OFFSET;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_SCOPE_BLOCK__LENGTH = SCOPE_BLOCK__LENGTH;

	/**
	 * The feature id for the '<em><b>Definition Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_SCOPE_BLOCK__DEFINITION_REF = SCOPE_BLOCK_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Root Scope Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_SCOPE_BLOCK_FEATURE_COUNT = SCOPE_BLOCK_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.scope.impl.CompilableScopeImpl <em>Compilable Scope</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.scope.impl.CompilableScopeImpl
	 * @see com.tibco.cep.studio.core.index.model.scope.impl.ScopePackageImpl#getCompilableScope()
	 * @generated
	 */
	int COMPILABLE_SCOPE = 2;

	/**
	 * The feature id for the '<em><b>Global Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE_SCOPE__GLOBAL_VARIABLES = 0;

	/**
	 * The feature id for the '<em><b>Condition Scope</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE_SCOPE__CONDITION_SCOPE = 1;

	/**
	 * The feature id for the '<em><b>Action Scope</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE_SCOPE__ACTION_SCOPE = 2;

	/**
	 * The number of structural features of the '<em>Compilable Scope</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE_SCOPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.scope.impl.CompilableScopeEntryImpl <em>Compilable Scope Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.scope.impl.CompilableScopeEntryImpl
	 * @see com.tibco.cep.studio.core.index.model.scope.impl.ScopePackageImpl#getCompilableScopeEntry()
	 * @generated
	 */
	int COMPILABLE_SCOPE_ENTRY = 3;

	/**
	 * The feature id for the '<em><b>Rule Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE_SCOPE_ENTRY__RULE_NAME = 0;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE_SCOPE_ENTRY__SCOPE = 1;

	/**
	 * The number of structural features of the '<em>Compilable Scope Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE_SCOPE_ENTRY_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock <em>Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Block</em>'.
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopeBlock
	 * @generated
	 */
	EClass getScopeBlock();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getType()
	 * @see #getScopeBlock()
	 * @generated
	 */
	EAttribute getScopeBlock_Type();

	/**
	 * Returns the meta object for the container reference '{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getParentScopeDef <em>Parent Scope Def</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent Scope Def</em>'.
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getParentScopeDef()
	 * @see #getScopeBlock()
	 * @generated
	 */
	EReference getScopeBlock_ParentScopeDef();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getChildScopeDefs <em>Child Scope Defs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Child Scope Defs</em>'.
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getChildScopeDefs()
	 * @see #getScopeBlock()
	 * @generated
	 */
	EReference getScopeBlock_ChildScopeDefs();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getDefs <em>Defs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Defs</em>'.
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getDefs()
	 * @see #getScopeBlock()
	 * @generated
	 */
	EReference getScopeBlock_Defs();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getRefs <em>Refs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Refs</em>'.
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getRefs()
	 * @see #getScopeBlock()
	 * @generated
	 */
	EReference getScopeBlock_Refs();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getOffset <em>Offset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Offset</em>'.
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getOffset()
	 * @see #getScopeBlock()
	 * @generated
	 */
	EAttribute getScopeBlock_Offset();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see com.tibco.cep.studio.core.index.model.scope.ScopeBlock#getLength()
	 * @see #getScopeBlock()
	 * @generated
	 */
	EAttribute getScopeBlock_Length();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.scope.RootScopeBlock <em>Root Scope Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Root Scope Block</em>'.
	 * @see com.tibco.cep.studio.core.index.model.scope.RootScopeBlock
	 * @generated
	 */
	EClass getRootScopeBlock();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.index.model.scope.RootScopeBlock#getDefinitionRef <em>Definition Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Definition Ref</em>'.
	 * @see com.tibco.cep.studio.core.index.model.scope.RootScopeBlock#getDefinitionRef()
	 * @see #getRootScopeBlock()
	 * @generated
	 */
	EReference getRootScopeBlock_DefinitionRef();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.scope.CompilableScope <em>Compilable Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compilable Scope</em>'.
	 * @see com.tibco.cep.studio.core.index.model.scope.CompilableScope
	 * @generated
	 */
	EClass getCompilableScope();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.core.index.model.scope.CompilableScope#getGlobalVariables <em>Global Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Global Variables</em>'.
	 * @see com.tibco.cep.studio.core.index.model.scope.CompilableScope#getGlobalVariables()
	 * @see #getCompilableScope()
	 * @generated
	 */
	EReference getCompilableScope_GlobalVariables();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.index.model.scope.CompilableScope#getConditionScope <em>Condition Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition Scope</em>'.
	 * @see com.tibco.cep.studio.core.index.model.scope.CompilableScope#getConditionScope()
	 * @see #getCompilableScope()
	 * @generated
	 */
	EReference getCompilableScope_ConditionScope();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.index.model.scope.CompilableScope#getActionScope <em>Action Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Action Scope</em>'.
	 * @see com.tibco.cep.studio.core.index.model.scope.CompilableScope#getActionScope()
	 * @see #getCompilableScope()
	 * @generated
	 */
	EReference getCompilableScope_ActionScope();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry <em>Compilable Scope Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compilable Scope Entry</em>'.
	 * @see com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry
	 * @generated
	 */
	EClass getCompilableScopeEntry();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry#getRuleName <em>Rule Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rule Name</em>'.
	 * @see com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry#getRuleName()
	 * @see #getCompilableScopeEntry()
	 * @generated
	 */
	EAttribute getCompilableScopeEntry_RuleName();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry#getScope <em>Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Scope</em>'.
	 * @see com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry#getScope()
	 * @see #getCompilableScopeEntry()
	 * @generated
	 */
	EReference getCompilableScopeEntry_Scope();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ScopeFactory getScopeFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.scope.impl.ScopeBlockImpl <em>Block</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.scope.impl.ScopeBlockImpl
		 * @see com.tibco.cep.studio.core.index.model.scope.impl.ScopePackageImpl#getScopeBlock()
		 * @generated
		 */
		EClass SCOPE_BLOCK = eINSTANCE.getScopeBlock();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCOPE_BLOCK__TYPE = eINSTANCE.getScopeBlock_Type();

		/**
		 * The meta object literal for the '<em><b>Parent Scope Def</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCOPE_BLOCK__PARENT_SCOPE_DEF = eINSTANCE.getScopeBlock_ParentScopeDef();

		/**
		 * The meta object literal for the '<em><b>Child Scope Defs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCOPE_BLOCK__CHILD_SCOPE_DEFS = eINSTANCE.getScopeBlock_ChildScopeDefs();

		/**
		 * The meta object literal for the '<em><b>Defs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCOPE_BLOCK__DEFS = eINSTANCE.getScopeBlock_Defs();

		/**
		 * The meta object literal for the '<em><b>Refs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCOPE_BLOCK__REFS = eINSTANCE.getScopeBlock_Refs();

		/**
		 * The meta object literal for the '<em><b>Offset</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCOPE_BLOCK__OFFSET = eINSTANCE.getScopeBlock_Offset();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCOPE_BLOCK__LENGTH = eINSTANCE.getScopeBlock_Length();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.scope.impl.RootScopeBlockImpl <em>Root Scope Block</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.scope.impl.RootScopeBlockImpl
		 * @see com.tibco.cep.studio.core.index.model.scope.impl.ScopePackageImpl#getRootScopeBlock()
		 * @generated
		 */
		EClass ROOT_SCOPE_BLOCK = eINSTANCE.getRootScopeBlock();

		/**
		 * The meta object literal for the '<em><b>Definition Ref</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_SCOPE_BLOCK__DEFINITION_REF = eINSTANCE.getRootScopeBlock_DefinitionRef();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.scope.impl.CompilableScopeImpl <em>Compilable Scope</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.scope.impl.CompilableScopeImpl
		 * @see com.tibco.cep.studio.core.index.model.scope.impl.ScopePackageImpl#getCompilableScope()
		 * @generated
		 */
		EClass COMPILABLE_SCOPE = eINSTANCE.getCompilableScope();

		/**
		 * The meta object literal for the '<em><b>Global Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPILABLE_SCOPE__GLOBAL_VARIABLES = eINSTANCE.getCompilableScope_GlobalVariables();

		/**
		 * The meta object literal for the '<em><b>Condition Scope</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPILABLE_SCOPE__CONDITION_SCOPE = eINSTANCE.getCompilableScope_ConditionScope();

		/**
		 * The meta object literal for the '<em><b>Action Scope</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPILABLE_SCOPE__ACTION_SCOPE = eINSTANCE.getCompilableScope_ActionScope();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.scope.impl.CompilableScopeEntryImpl <em>Compilable Scope Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.scope.impl.CompilableScopeEntryImpl
		 * @see com.tibco.cep.studio.core.index.model.scope.impl.ScopePackageImpl#getCompilableScopeEntry()
		 * @generated
		 */
		EClass COMPILABLE_SCOPE_ENTRY = eINSTANCE.getCompilableScopeEntry();

		/**
		 * The meta object literal for the '<em><b>Rule Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILABLE_SCOPE_ENTRY__RULE_NAME = eINSTANCE.getCompilableScopeEntry_RuleName();

		/**
		 * The meta object literal for the '<em><b>Scope</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPILABLE_SCOPE_ENTRY__SCOPE = eINSTANCE.getCompilableScopeEntry_Scope();

	}

} //ScopePackage
