/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.terminal;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import com.tibco.cep.decision.tree.common.model.node.NodePackage;

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
 * @see com.tibco.cep.decision.tree.common.model.node.terminal.TerminalFactory
 * @model kind="package"
 * @generated
 */
public interface TerminalPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "terminal";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/decision/tree/model/terminal";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "terminal";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TerminalPackage eINSTANCE = com.tibco.cep.decision.tree.common.model.node.terminal.impl.TerminalPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.node.terminal.impl.TerminalImpl <em>Terminal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.node.terminal.impl.TerminalImpl
	 * @see com.tibco.cep.decision.tree.common.model.node.terminal.impl.TerminalPackageImpl#getTerminal()
	 * @generated
	 */
	int TERMINAL = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL__ID = NodePackage.NODE_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL__NAME = NodePackage.NODE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL__DESCRIPTION = NodePackage.NODE_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL__DATA = NodePackage.NODE_ELEMENT__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL__ASSOC_EDGE = NodePackage.NODE_ELEMENT__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>In Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL__IN_EDGES = NodePackage.NODE_ELEMENT__IN_EDGES;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL__OUT_EDGE = NodePackage.NODE_ELEMENT__OUT_EDGE;

	/**
	 * The number of structural features of the '<em>Terminal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_FEATURE_COUNT = NodePackage.NODE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.node.terminal.impl.StartImpl <em>Start</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.node.terminal.impl.StartImpl
	 * @see com.tibco.cep.decision.tree.common.model.node.terminal.impl.TerminalPackageImpl#getStart()
	 * @generated
	 */
	int START = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START__ID = TERMINAL__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START__NAME = TERMINAL__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START__DESCRIPTION = TERMINAL__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START__DATA = TERMINAL__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START__ASSOC_EDGE = TERMINAL__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>In Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START__IN_EDGES = TERMINAL__IN_EDGES;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START__OUT_EDGE = TERMINAL__OUT_EDGE;

	/**
	 * The number of structural features of the '<em>Start</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_FEATURE_COUNT = TERMINAL_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.node.terminal.impl.EndImpl <em>End</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.node.terminal.impl.EndImpl
	 * @see com.tibco.cep.decision.tree.common.model.node.terminal.impl.TerminalPackageImpl#getEnd()
	 * @generated
	 */
	int END = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END__ID = TERMINAL__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END__NAME = TERMINAL__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END__DESCRIPTION = TERMINAL__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END__DATA = TERMINAL__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END__ASSOC_EDGE = TERMINAL__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>In Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END__IN_EDGES = TERMINAL__IN_EDGES;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END__OUT_EDGE = TERMINAL__OUT_EDGE;

	/**
	 * The number of structural features of the '<em>End</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int END_FEATURE_COUNT = TERMINAL_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.tree.common.model.node.terminal.impl.BreakImpl <em>Break</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.tree.common.model.node.terminal.impl.BreakImpl
	 * @see com.tibco.cep.decision.tree.common.model.node.terminal.impl.TerminalPackageImpl#getBreak()
	 * @generated
	 */
	int BREAK = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BREAK__ID = TERMINAL__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BREAK__NAME = TERMINAL__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BREAK__DESCRIPTION = TERMINAL__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BREAK__DATA = TERMINAL__DATA;

	/**
	 * The feature id for the '<em><b>Assoc Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BREAK__ASSOC_EDGE = TERMINAL__ASSOC_EDGE;

	/**
	 * The feature id for the '<em><b>In Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BREAK__IN_EDGES = TERMINAL__IN_EDGES;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BREAK__OUT_EDGE = TERMINAL__OUT_EDGE;

	/**
	 * The number of structural features of the '<em>Break</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BREAK_FEATURE_COUNT = TERMINAL_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.node.terminal.Terminal <em>Terminal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Terminal</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.terminal.Terminal
	 * @generated
	 */
	EClass getTerminal();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.node.terminal.Start <em>Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Start</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.terminal.Start
	 * @generated
	 */
	EClass getStart();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.node.terminal.End <em>End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>End</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.terminal.End
	 * @generated
	 */
	EClass getEnd();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.tree.common.model.node.terminal.Break <em>Break</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Break</em>'.
	 * @see com.tibco.cep.decision.tree.common.model.node.terminal.Break
	 * @generated
	 */
	EClass getBreak();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TerminalFactory getTerminalFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.node.terminal.impl.TerminalImpl <em>Terminal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.node.terminal.impl.TerminalImpl
		 * @see com.tibco.cep.decision.tree.common.model.node.terminal.impl.TerminalPackageImpl#getTerminal()
		 * @generated
		 */
		EClass TERMINAL = eINSTANCE.getTerminal();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.node.terminal.impl.StartImpl <em>Start</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.node.terminal.impl.StartImpl
		 * @see com.tibco.cep.decision.tree.common.model.node.terminal.impl.TerminalPackageImpl#getStart()
		 * @generated
		 */
		EClass START = eINSTANCE.getStart();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.node.terminal.impl.EndImpl <em>End</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.node.terminal.impl.EndImpl
		 * @see com.tibco.cep.decision.tree.common.model.node.terminal.impl.TerminalPackageImpl#getEnd()
		 * @generated
		 */
		EClass END = eINSTANCE.getEnd();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.tree.common.model.node.terminal.impl.BreakImpl <em>Break</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.tree.common.model.node.terminal.impl.BreakImpl
		 * @see com.tibco.cep.decision.tree.common.model.node.terminal.impl.TerminalPackageImpl#getBreak()
		 * @generated
		 */
		EClass BREAK = eINSTANCE.getBreak();

	}

} //TerminalPackage
