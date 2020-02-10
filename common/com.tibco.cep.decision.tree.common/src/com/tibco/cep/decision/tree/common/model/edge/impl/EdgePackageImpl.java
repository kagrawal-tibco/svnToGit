/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.edge.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.decision.tree.common.model.ModelPackage;
import com.tibco.cep.decision.tree.common.model.edge.Association;
import com.tibco.cep.decision.tree.common.model.edge.Edge;
import com.tibco.cep.decision.tree.common.model.edge.EdgeFactory;
import com.tibco.cep.decision.tree.common.model.edge.EdgePackage;
import com.tibco.cep.decision.tree.common.model.impl.ModelPackageImpl;
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
public class EdgePackageImpl extends EPackageImpl implements EdgePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass edgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass associationEClass = null;

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
	 * @see com.tibco.cep.decision.tree.common.model.edge.EdgePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EdgePackageImpl() {
		super(eNS_URI, EdgeFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link EdgePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EdgePackage init() {
		if (isInited) return (EdgePackage)EPackage.Registry.INSTANCE.getEPackage(EdgePackage.eNS_URI);

		// Obtain or create and register package
		EdgePackageImpl theEdgePackage = (EdgePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EdgePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EdgePackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) : ModelPackage.eINSTANCE);
		NodePackageImpl theNodePackage = (NodePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(NodePackage.eNS_URI) instanceof NodePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(NodePackage.eNS_URI) : NodePackage.eINSTANCE);
		ConditionPackageImpl theConditionPackage = (ConditionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ConditionPackage.eNS_URI) instanceof ConditionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ConditionPackage.eNS_URI) : ConditionPackage.eINSTANCE);
		ActionPackageImpl theActionPackage = (ActionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ActionPackage.eNS_URI) instanceof ActionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ActionPackage.eNS_URI) : ActionPackage.eINSTANCE);
		TerminalPackageImpl theTerminalPackage = (TerminalPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TerminalPackage.eNS_URI) instanceof TerminalPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TerminalPackage.eNS_URI) : TerminalPackage.eINSTANCE);

		// Create package meta-data objects
		theEdgePackage.createPackageContents();
		theModelPackage.createPackageContents();
		theNodePackage.createPackageContents();
		theConditionPackage.createPackageContents();
		theActionPackage.createPackageContents();
		theTerminalPackage.createPackageContents();

		// Initialize created meta-data
		theEdgePackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theNodePackage.initializePackageContents();
		theConditionPackage.initializePackageContents();
		theActionPackage.initializePackageContents();
		theTerminalPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEdgePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EdgePackage.eNS_URI, theEdgePackage);
		return theEdgePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEdge() {
		return edgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEdge_Src() {
		return (EReference)edgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEdge_Tgt() {
		return (EReference)edgeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAssociation() {
		return associationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EdgeFactory getEdgeFactory() {
		return (EdgeFactory)getEFactoryInstance();
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
		edgeEClass = createEClass(EDGE);
		createEReference(edgeEClass, EDGE__SRC);
		createEReference(edgeEClass, EDGE__TGT);

		associationEClass = createEClass(ASSOCIATION);
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
		ModelPackage theModelPackage = (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);
		NodePackage theNodePackage = (NodePackage)EPackage.Registry.INSTANCE.getEPackage(NodePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		edgeEClass.getESuperTypes().add(theModelPackage.getFlowElement());
		associationEClass.getESuperTypes().add(this.getEdge());

		// Initialize classes and features; add operations and parameters
		initEClass(edgeEClass, Edge.class, "Edge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEdge_Src(), theNodePackage.getNodeElement(), null, "src", null, 1, 1, Edge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEdge_Tgt(), theNodePackage.getNodeElement(), null, "tgt", null, 1, 1, Edge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(associationEClass, Association.class, "Association", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
	}

} //EdgePackageImpl
