/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.terminal.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.decision.tree.common.model.ModelPackage;
import com.tibco.cep.decision.tree.common.model.edge.EdgePackage;
import com.tibco.cep.decision.tree.common.model.edge.impl.EdgePackageImpl;
import com.tibco.cep.decision.tree.common.model.impl.ModelPackageImpl;
import com.tibco.cep.decision.tree.common.model.node.NodePackage;
import com.tibco.cep.decision.tree.common.model.node.action.ActionPackage;
import com.tibco.cep.decision.tree.common.model.node.action.impl.ActionPackageImpl;
import com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage;
import com.tibco.cep.decision.tree.common.model.node.condition.impl.ConditionPackageImpl;
import com.tibco.cep.decision.tree.common.model.node.impl.NodePackageImpl;
import com.tibco.cep.decision.tree.common.model.node.terminal.Break;
import com.tibco.cep.decision.tree.common.model.node.terminal.End;
import com.tibco.cep.decision.tree.common.model.node.terminal.Start;
import com.tibco.cep.decision.tree.common.model.node.terminal.Terminal;
import com.tibco.cep.decision.tree.common.model.node.terminal.TerminalFactory;
import com.tibco.cep.decision.tree.common.model.node.terminal.TerminalPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TerminalPackageImpl extends EPackageImpl implements TerminalPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass terminalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass startEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass endEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass breakEClass = null;

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
	 * @see com.tibco.cep.decision.tree.common.model.node.terminal.TerminalPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TerminalPackageImpl() {
		super(eNS_URI, TerminalFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link TerminalPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TerminalPackage init() {
		if (isInited) return (TerminalPackage)EPackage.Registry.INSTANCE.getEPackage(TerminalPackage.eNS_URI);

		// Obtain or create and register package
		TerminalPackageImpl theTerminalPackage = (TerminalPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TerminalPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TerminalPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) : ModelPackage.eINSTANCE);
		NodePackageImpl theNodePackage = (NodePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(NodePackage.eNS_URI) instanceof NodePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(NodePackage.eNS_URI) : NodePackage.eINSTANCE);
		ConditionPackageImpl theConditionPackage = (ConditionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ConditionPackage.eNS_URI) instanceof ConditionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ConditionPackage.eNS_URI) : ConditionPackage.eINSTANCE);
		ActionPackageImpl theActionPackage = (ActionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ActionPackage.eNS_URI) instanceof ActionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ActionPackage.eNS_URI) : ActionPackage.eINSTANCE);
		EdgePackageImpl theEdgePackage = (EdgePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(EdgePackage.eNS_URI) instanceof EdgePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(EdgePackage.eNS_URI) : EdgePackage.eINSTANCE);

		// Create package meta-data objects
		theTerminalPackage.createPackageContents();
		theModelPackage.createPackageContents();
		theNodePackage.createPackageContents();
		theConditionPackage.createPackageContents();
		theActionPackage.createPackageContents();
		theEdgePackage.createPackageContents();

		// Initialize created meta-data
		theTerminalPackage.initializePackageContents();
		theModelPackage.initializePackageContents();
		theNodePackage.initializePackageContents();
		theConditionPackage.initializePackageContents();
		theActionPackage.initializePackageContents();
		theEdgePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTerminalPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TerminalPackage.eNS_URI, theTerminalPackage);
		return theTerminalPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTerminal() {
		return terminalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStart() {
		return startEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnd() {
		return endEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBreak() {
		return breakEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TerminalFactory getTerminalFactory() {
		return (TerminalFactory)getEFactoryInstance();
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
		terminalEClass = createEClass(TERMINAL);

		startEClass = createEClass(START);

		endEClass = createEClass(END);

		breakEClass = createEClass(BREAK);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		terminalEClass.getESuperTypes().add(theNodePackage.getNodeElement());
		startEClass.getESuperTypes().add(this.getTerminal());
		endEClass.getESuperTypes().add(this.getTerminal());
		breakEClass.getESuperTypes().add(this.getTerminal());

		// Initialize classes and features; add operations and parameters
		initEClass(terminalEClass, Terminal.class, "Terminal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(startEClass, Start.class, "Start", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(endEClass, End.class, "End", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(breakEClass, Break.class, "Break", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
	}

} //TerminalPackageImpl
