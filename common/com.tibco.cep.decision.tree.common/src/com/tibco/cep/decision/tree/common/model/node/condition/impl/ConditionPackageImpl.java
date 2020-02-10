/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.condition.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.decision.tree.common.model.ModelPackage;
import com.tibco.cep.decision.tree.common.model.edge.EdgePackage;
import com.tibco.cep.decision.tree.common.model.edge.impl.EdgePackageImpl;
import com.tibco.cep.decision.tree.common.model.impl.ModelPackageImpl;
import com.tibco.cep.decision.tree.common.model.node.NodePackage;
import com.tibco.cep.decision.tree.common.model.node.action.ActionPackage;
import com.tibco.cep.decision.tree.common.model.node.action.impl.ActionPackageImpl;
import com.tibco.cep.decision.tree.common.model.node.condition.BoolCond;
import com.tibco.cep.decision.tree.common.model.node.condition.Case;
import com.tibco.cep.decision.tree.common.model.node.condition.CaseGroup;
import com.tibco.cep.decision.tree.common.model.node.condition.Cond;
import com.tibco.cep.decision.tree.common.model.node.condition.ConditionFactory;
import com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage;
import com.tibco.cep.decision.tree.common.model.node.condition.SwitchCond;
import com.tibco.cep.decision.tree.common.model.node.impl.NodePackageImpl;
import com.tibco.cep.decision.tree.common.model.node.terminal.TerminalPackage;
import com.tibco.cep.decision.tree.common.model.node.terminal.impl.TerminalPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ConditionPackageImpl extends EPackageImpl implements ConditionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass condEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass boolCondEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass switchCondEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass caseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass caseGroupEClass = null;

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
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ConditionPackageImpl() {
		super(eNS_URI, ConditionFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ConditionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ConditionPackage init() {
		if (isInited) return (ConditionPackage)EPackage.Registry.INSTANCE.getEPackage(ConditionPackage.eNS_URI);

		// Obtain or create and register package
		ConditionPackageImpl theConditionPackage = (ConditionPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ConditionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ConditionPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) : ModelPackage.eINSTANCE);
		NodePackageImpl theNodePackage = (NodePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(NodePackage.eNS_URI) instanceof NodePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(NodePackage.eNS_URI) : NodePackage.eINSTANCE);
		ActionPackageImpl theActionPackage = (ActionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ActionPackage.eNS_URI) instanceof ActionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ActionPackage.eNS_URI) : ActionPackage.eINSTANCE);
		TerminalPackageImpl theTerminalPackage = (TerminalPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TerminalPackage.eNS_URI) instanceof TerminalPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TerminalPackage.eNS_URI) : TerminalPackage.eINSTANCE);
		EdgePackageImpl theEdgePackage = (EdgePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EdgePackage.eNS_URI) instanceof EdgePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EdgePackage.eNS_URI) : EdgePackage.eINSTANCE);

		// Create package meta-data objects
		theConditionPackage.createPackageContents();
		theModelPackage.createPackageContents();
		theNodePackage.createPackageContents();
		theActionPackage.createPackageContents();
		theTerminalPackage.createPackageContents();
		theEdgePackage.createPackageContents();

		// Initialize created meta-data
		theConditionPackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theNodePackage.initializePackageContents();
		theActionPackage.initializePackageContents();
		theTerminalPackage.initializePackageContents();
		theEdgePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theConditionPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ConditionPackage.eNS_URI, theConditionPackage);
		return theConditionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCond() {
		return condEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBoolCond() {
		return boolCondEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBoolCond_FalseEdge() {
		return (EReference)boolCondEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSwitchCond() {
		return switchCondEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSwitchCond_Cases() {
		return (EReference)switchCondEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSwitchCond_Casegroups() {
		return (EReference)switchCondEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCase() {
		return caseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCase_OutEdge() {
		return (EReference)caseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCaseGroup() {
		return caseGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCaseGroup_Cases() {
		return (EReference)caseGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionFactory getConditionFactory() {
		return (ConditionFactory)getEFactoryInstance();
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
		condEClass = createEClass(COND);

		boolCondEClass = createEClass(BOOL_COND);
		createEReference(boolCondEClass, BOOL_COND__FALSE_EDGE);

		switchCondEClass = createEClass(SWITCH_COND);
		createEReference(switchCondEClass, SWITCH_COND__CASES);
		createEReference(switchCondEClass, SWITCH_COND__CASEGROUPS);

		caseEClass = createEClass(CASE);
		createEReference(caseEClass, CASE__OUT_EDGE);

		caseGroupEClass = createEClass(CASE_GROUP);
		createEReference(caseGroupEClass, CASE_GROUP__CASES);
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
		NodePackage theNodePackage = (NodePackage)EPackage.Registry.INSTANCE.getEPackage(NodePackage.eNS_URI);
		EdgePackage theEdgePackage = (EdgePackage)EPackage.Registry.INSTANCE.getEPackage(EdgePackage.eNS_URI);
		ModelPackage theModelPackage = (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		condEClass.getESuperTypes().add(theNodePackage.getNodeElement());
		boolCondEClass.getESuperTypes().add(this.getCond());
		switchCondEClass.getESuperTypes().add(this.getCond());
		caseEClass.getESuperTypes().add(theModelPackage.getFlowElement());
		caseGroupEClass.getESuperTypes().add(this.getCase());

		// Initialize classes and features; add operations and parameters
		initEClass(condEClass, Cond.class, "Cond", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(boolCondEClass, BoolCond.class, "BoolCond", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBoolCond_FalseEdge(), theEdgePackage.getEdge(), null, "falseEdge", null, 0, 1, BoolCond.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(switchCondEClass, SwitchCond.class, "SwitchCond", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSwitchCond_Cases(), this.getCase(), null, "cases", null, 0, -1, SwitchCond.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSwitchCond_Casegroups(), this.getCaseGroup(), null, "casegroups", null, 0, -1, SwitchCond.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(caseEClass, Case.class, "Case", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCase_OutEdge(), theEdgePackage.getEdge(), null, "outEdge", null, 0, 1, Case.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(caseGroupEClass, CaseGroup.class, "CaseGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCaseGroup_Cases(), this.getCase(), null, "cases", null, 0, -1, CaseGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //ConditionPackageImpl
