/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.decision.tree.common.model.DecisionTree;
import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.ModelFactory;
import com.tibco.cep.decision.tree.common.model.ModelPackage;
import com.tibco.cep.decision.tree.common.model.edge.EdgePackage;
import com.tibco.cep.decision.tree.common.model.edge.impl.EdgePackageImpl;
import com.tibco.cep.decision.tree.common.model.node.NodePackage;
import com.tibco.cep.decision.tree.common.model.node.action.ActionPackage;
import com.tibco.cep.decision.tree.common.model.node.action.impl.ActionPackageImpl;
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
public class ModelPackageImpl extends EPackageImpl implements ModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass decisionTreeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass flowElementEClass = null;

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
	 * @see com.tibco.cep.decision.tree.common.model.ModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ModelPackageImpl() {
		super(eNS_URI, ModelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ModelPackage init() {
		if (isInited) return (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Obtain or create and register package
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ModelPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		NodePackageImpl theNodePackage = (NodePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(NodePackage.eNS_URI) instanceof NodePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(NodePackage.eNS_URI) : NodePackage.eINSTANCE);
		ConditionPackageImpl theConditionPackage = (ConditionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ConditionPackage.eNS_URI) instanceof ConditionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ConditionPackage.eNS_URI) : ConditionPackage.eINSTANCE);
		ActionPackageImpl theActionPackage = (ActionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ActionPackage.eNS_URI) instanceof ActionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ActionPackage.eNS_URI) : ActionPackage.eINSTANCE);
		TerminalPackageImpl theTerminalPackage = (TerminalPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TerminalPackage.eNS_URI) instanceof TerminalPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TerminalPackage.eNS_URI) : TerminalPackage.eINSTANCE);
		EdgePackageImpl theEdgePackage = (EdgePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EdgePackage.eNS_URI) instanceof EdgePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EdgePackage.eNS_URI) : EdgePackage.eINSTANCE);

		// Create package meta-data objects
		theModelPackage.createPackageContents();
		theNodePackage.createPackageContents();
		theConditionPackage.createPackageContents();
		theActionPackage.createPackageContents();
		theTerminalPackage.createPackageContents();
		theEdgePackage.createPackageContents();

		// Initialize created meta-data
		theModelPackage.initializePackageContents();
		theNodePackage.initializePackageContents();
		theConditionPackage.initializePackageContents();
		theActionPackage.initializePackageContents();
		theTerminalPackage.initializePackageContents();
		theEdgePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theModelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ModelPackage.eNS_URI, theModelPackage);
		return theModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDecisionTree() {
		return decisionTreeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDecisionTree_Folder() {
		return (EAttribute)decisionTreeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDecisionTree_OwnerProject() {
		return (EAttribute)decisionTreeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDecisionTree_CreationDate() {
		return (EAttribute)decisionTreeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDecisionTree_LastModified() {
		return (EAttribute)decisionTreeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDecisionTree_Nodes() {
		return (EReference)decisionTreeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDecisionTree_Edges() {
		return (EReference)decisionTreeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDecisionTree_StartNode() {
		return (EReference)decisionTreeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFlowElement() {
		return flowElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFlowElement_Id() {
		return (EAttribute)flowElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFlowElement_Name() {
		return (EAttribute)flowElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFlowElement_Description() {
		return (EAttribute)flowElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFlowElement_Data() {
		return (EAttribute)flowElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getFlowElement_AssocEdge() {
		return (EReference)flowElementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelFactory getModelFactory() {
		return (ModelFactory)getEFactoryInstance();
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
		decisionTreeEClass = createEClass(DECISION_TREE);
		createEAttribute(decisionTreeEClass, DECISION_TREE__FOLDER);
		createEAttribute(decisionTreeEClass, DECISION_TREE__OWNER_PROJECT);
		createEAttribute(decisionTreeEClass, DECISION_TREE__CREATION_DATE);
		createEAttribute(decisionTreeEClass, DECISION_TREE__LAST_MODIFIED);
		createEReference(decisionTreeEClass, DECISION_TREE__NODES);
		createEReference(decisionTreeEClass, DECISION_TREE__EDGES);
		createEReference(decisionTreeEClass, DECISION_TREE__START_NODE);

		flowElementEClass = createEClass(FLOW_ELEMENT);
		createEAttribute(flowElementEClass, FLOW_ELEMENT__ID);
		createEAttribute(flowElementEClass, FLOW_ELEMENT__NAME);
		createEAttribute(flowElementEClass, FLOW_ELEMENT__DESCRIPTION);
		createEAttribute(flowElementEClass, FLOW_ELEMENT__DATA);
		createEReference(flowElementEClass, FLOW_ELEMENT__ASSOC_EDGE);
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

		// Add subpackages
		getESubpackages().add(theNodePackage);
		getESubpackages().add(theEdgePackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(decisionTreeEClass, DecisionTree.class, "DecisionTree", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDecisionTree_Folder(), ecorePackage.getEString(), "folder", null, 0, 1, DecisionTree.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDecisionTree_OwnerProject(), ecorePackage.getEString(), "ownerProject", null, 0, 1, DecisionTree.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDecisionTree_CreationDate(), ecorePackage.getEDate(), "creationDate", null, 0, 1, DecisionTree.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDecisionTree_LastModified(), ecorePackage.getEDate(), "lastModified", null, 0, 1, DecisionTree.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDecisionTree_Nodes(), theNodePackage.getNodeElement(), null, "nodes", null, 0, -1, DecisionTree.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDecisionTree_Edges(), theEdgePackage.getEdge(), null, "edges", null, 0, -1, DecisionTree.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDecisionTree_StartNode(), theNodePackage.getNodeElement(), null, "startNode", null, 0, 1, DecisionTree.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(flowElementEClass, FlowElement.class, "FlowElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFlowElement_Id(), ecorePackage.getELong(), "id", null, 1, 1, FlowElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFlowElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, FlowElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFlowElement_Description(), ecorePackage.getEString(), "description", null, 0, 1, FlowElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFlowElement_Data(), ecorePackage.getEJavaObject(), "data", null, 0, 1, FlowElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFlowElement_AssocEdge(), theEdgePackage.getAssociation(), null, "assocEdge", null, 0, 1, FlowElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //ModelPackageImpl
