/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.decision.tree.common.model.ModelPackage;
import com.tibco.cep.decision.tree.common.model.edge.EdgePackage;
import com.tibco.cep.decision.tree.common.model.edge.impl.EdgePackageImpl;
import com.tibco.cep.decision.tree.common.model.impl.ModelPackageImpl;
import com.tibco.cep.decision.tree.common.model.node.DescriptionElement;
import com.tibco.cep.decision.tree.common.model.node.Loop;
import com.tibco.cep.decision.tree.common.model.node.NodeElement;
import com.tibco.cep.decision.tree.common.model.node.NodeFactory;
import com.tibco.cep.decision.tree.common.model.node.NodePackage;
import com.tibco.cep.decision.tree.common.model.node.action.ActionPackage;
import com.tibco.cep.decision.tree.common.model.node.action.impl.ActionPackageImpl;
import com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage;
import com.tibco.cep.decision.tree.common.model.node.condition.impl.ConditionPackageImpl;
import com.tibco.cep.decision.tree.common.model.node.terminal.TerminalPackage;
import com.tibco.cep.decision.tree.common.model.node.terminal.impl.TerminalPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NodePackageImpl extends EPackageImpl implements NodePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nodeElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass loopEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass descriptionElementEClass = null;

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
	 * @see com.tibco.cep.decision.tree.common.model.node.NodePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private NodePackageImpl() {
		super(eNS_URI, NodeFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link NodePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static NodePackage init() {
		if (isInited) return (NodePackage)EPackage.Registry.INSTANCE.getEPackage(NodePackage.eNS_URI);

		// Obtain or create and register package
		NodePackageImpl theNodePackage = (NodePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof NodePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new NodePackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) : ModelPackage.eINSTANCE);
		ConditionPackageImpl theConditionPackage = (ConditionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ConditionPackage.eNS_URI) instanceof ConditionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ConditionPackage.eNS_URI) : ConditionPackage.eINSTANCE);
		ActionPackageImpl theActionPackage = (ActionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ActionPackage.eNS_URI) instanceof ActionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ActionPackage.eNS_URI) : ActionPackage.eINSTANCE);
		TerminalPackageImpl theTerminalPackage = (TerminalPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TerminalPackage.eNS_URI) instanceof TerminalPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TerminalPackage.eNS_URI) : TerminalPackage.eINSTANCE);
		EdgePackageImpl theEdgePackage = (EdgePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EdgePackage.eNS_URI) instanceof EdgePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EdgePackage.eNS_URI) : EdgePackage.eINSTANCE);

		// Create package meta-data objects
		theNodePackage.createPackageContents();
		theModelPackage.createPackageContents();
		theConditionPackage.createPackageContents();
		theActionPackage.createPackageContents();
		theTerminalPackage.createPackageContents();
		theEdgePackage.createPackageContents();

		// Initialize created meta-data
		theNodePackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theConditionPackage.initializePackageContents();
		theActionPackage.initializePackageContents();
		theTerminalPackage.initializePackageContents();
		theEdgePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theNodePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(NodePackage.eNS_URI, theNodePackage);
		return theNodePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNodeElement() {
		return nodeElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeElement_InEdges() {
		return (EReference)nodeElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeElement_OutEdge() {
		return (EReference)nodeElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLoop() {
		return loopEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLoop_LoopType() {
		return (EAttribute)loopEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLoop_LoopCondition() {
		return (EAttribute)loopEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoop_Elements() {
		return (EReference)loopEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoop_StartNode() {
		return (EReference)loopEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLoop_EndNodes() {
		return (EReference)loopEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDescriptionElement() {
		return descriptionElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDescriptionElement_AssocElement() {
		return (EReference)descriptionElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeFactory getNodeFactory() {
		return (NodeFactory)getEFactoryInstance();
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
		nodeElementEClass = createEClass(NODE_ELEMENT);
		createEReference(nodeElementEClass, NODE_ELEMENT__IN_EDGES);
		createEReference(nodeElementEClass, NODE_ELEMENT__OUT_EDGE);

		loopEClass = createEClass(LOOP);
		createEAttribute(loopEClass, LOOP__LOOP_TYPE);
		createEAttribute(loopEClass, LOOP__LOOP_CONDITION);
		createEReference(loopEClass, LOOP__ELEMENTS);
		createEReference(loopEClass, LOOP__START_NODE);
		createEReference(loopEClass, LOOP__END_NODES);

		descriptionElementEClass = createEClass(DESCRIPTION_ELEMENT);
		createEReference(descriptionElementEClass, DESCRIPTION_ELEMENT__ASSOC_ELEMENT);
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
		ConditionPackage theConditionPackage = (ConditionPackage)EPackage.Registry.INSTANCE.getEPackage(ConditionPackage.eNS_URI);
		ActionPackage theActionPackage = (ActionPackage)EPackage.Registry.INSTANCE.getEPackage(ActionPackage.eNS_URI);
		TerminalPackage theTerminalPackage = (TerminalPackage)EPackage.Registry.INSTANCE.getEPackage(TerminalPackage.eNS_URI);
		ModelPackage theModelPackage = (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);
		EdgePackage theEdgePackage = (EdgePackage)EPackage.Registry.INSTANCE.getEPackage(EdgePackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theConditionPackage);
		getESubpackages().add(theActionPackage);
		getESubpackages().add(theTerminalPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		nodeElementEClass.getESuperTypes().add(theModelPackage.getFlowElement());
		loopEClass.getESuperTypes().add(this.getNodeElement());
		descriptionElementEClass.getESuperTypes().add(this.getNodeElement());

		// Initialize classes and features; add operations and parameters
		initEClass(nodeElementEClass, NodeElement.class, "NodeElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNodeElement_InEdges(), theEdgePackage.getEdge(), null, "inEdges", null, 0, -1, NodeElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeElement_OutEdge(), theEdgePackage.getEdge(), null, "outEdge", null, 0, 1, NodeElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(loopEClass, Loop.class, "Loop", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLoop_LoopType(), ecorePackage.getEEnumerator(), "loopType", null, 0, 1, Loop.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLoop_LoopCondition(), ecorePackage.getEString(), "loopCondition", null, 0, 1, Loop.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLoop_Elements(), theModelPackage.getFlowElement(), null, "elements", null, 0, -1, Loop.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLoop_StartNode(), this.getNodeElement(), null, "startNode", null, 1, 1, Loop.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLoop_EndNodes(), this.getNodeElement(), null, "endNodes", null, 0, -1, Loop.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(descriptionElementEClass, DescriptionElement.class, "DescriptionElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDescriptionElement_AssocElement(), theModelPackage.getFlowElement(), null, "assocElement", null, 1, 1, DescriptionElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //NodePackageImpl
