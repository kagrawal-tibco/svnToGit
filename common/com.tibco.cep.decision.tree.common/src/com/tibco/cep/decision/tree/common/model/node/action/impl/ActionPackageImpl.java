/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.action.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.decision.tree.common.model.ModelPackage;
import com.tibco.cep.decision.tree.common.model.edge.EdgePackage;
import com.tibco.cep.decision.tree.common.model.edge.impl.EdgePackageImpl;
import com.tibco.cep.decision.tree.common.model.impl.ModelPackageImpl;
import com.tibco.cep.decision.tree.common.model.node.NodePackage;
import com.tibco.cep.decision.tree.common.model.node.action.Action;
import com.tibco.cep.decision.tree.common.model.node.action.ActionFactory;
import com.tibco.cep.decision.tree.common.model.node.action.ActionPackage;
import com.tibco.cep.decision.tree.common.model.node.action.Assignment;
import com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTableAction;
import com.tibco.cep.decision.tree.common.model.node.action.CallDecisionTreeAction;
import com.tibco.cep.decision.tree.common.model.node.action.CallRF;
import com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage;
import com.tibco.cep.decision.tree.common.model.node.condition.impl.ConditionPackageImpl;
import com.tibco.cep.decision.tree.common.model.node.impl.NodePackageImpl;
import com.tibco.cep.decision.tree.common.model.node.terminal.TerminalPackage;
import com.tibco.cep.decision.tree.common.model.node.terminal.impl.TerminalPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ActionPackageImpl extends EPackageImpl implements ActionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass assignmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass callRFEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass callDecisionTreeActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass callDecisionTableActionEClass = null;

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
	 * @see com.tibco.cep.decision.tree.common.model.node.action.ActionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ActionPackageImpl() {
		super(eNS_URI, ActionFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ActionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ActionPackage init() {
		if (isInited) return (ActionPackage)EPackage.Registry.INSTANCE.getEPackage(ActionPackage.eNS_URI);

		// Obtain or create and register package
		ActionPackageImpl theActionPackage = (ActionPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ActionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ActionPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) : ModelPackage.eINSTANCE);
		NodePackageImpl theNodePackage = (NodePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(NodePackage.eNS_URI) instanceof NodePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(NodePackage.eNS_URI) : NodePackage.eINSTANCE);
		ConditionPackageImpl theConditionPackage = (ConditionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ConditionPackage.eNS_URI) instanceof ConditionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ConditionPackage.eNS_URI) : ConditionPackage.eINSTANCE);
		TerminalPackageImpl theTerminalPackage = (TerminalPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TerminalPackage.eNS_URI) instanceof TerminalPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TerminalPackage.eNS_URI) : TerminalPackage.eINSTANCE);
		EdgePackageImpl theEdgePackage = (EdgePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EdgePackage.eNS_URI) instanceof EdgePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EdgePackage.eNS_URI) : EdgePackage.eINSTANCE);

		// Create package meta-data objects
		theActionPackage.createPackageContents();
		theModelPackage.createPackageContents();
		theNodePackage.createPackageContents();
		theConditionPackage.createPackageContents();
		theTerminalPackage.createPackageContents();
		theEdgePackage.createPackageContents();

		// Initialize created meta-data
		theActionPackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theNodePackage.initializePackageContents();
		theConditionPackage.initializePackageContents();
		theTerminalPackage.initializePackageContents();
		theEdgePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theActionPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ActionPackage.eNS_URI, theActionPackage);
		return theActionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAction() {
		return actionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAction_Text() {
		return (EAttribute)actionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAssignment() {
		return assignmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCallRF() {
		return callRFEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCallDecisionTreeAction() {
		return callDecisionTreeActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCallDecisionTreeAction_CallTree() {
		return (EReference)callDecisionTreeActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCallDecisionTableAction() {
		return callDecisionTableActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionFactory getActionFactory() {
		return (ActionFactory)getEFactoryInstance();
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
		actionEClass = createEClass(ACTION);
		createEAttribute(actionEClass, ACTION__TEXT);

		assignmentEClass = createEClass(ASSIGNMENT);

		callRFEClass = createEClass(CALL_RF);

		callDecisionTreeActionEClass = createEClass(CALL_DECISION_TREE_ACTION);
		createEReference(callDecisionTreeActionEClass, CALL_DECISION_TREE_ACTION__CALL_TREE);

		callDecisionTableActionEClass = createEClass(CALL_DECISION_TABLE_ACTION);
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
		ModelPackage theModelPackage = (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		actionEClass.getESuperTypes().add(theNodePackage.getNodeElement());
		assignmentEClass.getESuperTypes().add(this.getAction());
		callRFEClass.getESuperTypes().add(this.getAction());
		callDecisionTreeActionEClass.getESuperTypes().add(this.getAction());
		callDecisionTableActionEClass.getESuperTypes().add(this.getAction());

		// Initialize classes and features; add operations and parameters
		initEClass(actionEClass, Action.class, "Action", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAction_Text(), ecorePackage.getEString(), "text", null, 1, 1, Action.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(assignmentEClass, Assignment.class, "Assignment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(callRFEClass, CallRF.class, "CallRF", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(callDecisionTreeActionEClass, CallDecisionTreeAction.class, "CallDecisionTreeAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCallDecisionTreeAction_CallTree(), theModelPackage.getDecisionTree(), null, "callTree", null, 1, 1, CallDecisionTreeAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(callDecisionTableActionEClass, CallDecisionTableAction.class, "CallDecisionTableAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
	}

} //ActionPackageImpl
