/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.terminal.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.decision.tree.common.model.node.terminal.Break;
import com.tibco.cep.decision.tree.common.model.node.terminal.End;
import com.tibco.cep.decision.tree.common.model.node.terminal.Start;
import com.tibco.cep.decision.tree.common.model.node.terminal.Terminal;
import com.tibco.cep.decision.tree.common.model.node.terminal.TerminalFactory;
import com.tibco.cep.decision.tree.common.model.node.terminal.TerminalPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TerminalFactoryImpl extends EFactoryImpl implements TerminalFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TerminalFactory init() {
		try {
			TerminalFactory theTerminalFactory = (TerminalFactory)EPackage.Registry.INSTANCE.getEFactory("http:///com/tibco/cep/decision/tree/model/terminal"); 
			if (theTerminalFactory != null) {
				return theTerminalFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TerminalFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TerminalFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TerminalPackage.TERMINAL: return createTerminal();
			case TerminalPackage.START: return createStart();
			case TerminalPackage.END: return createEnd();
			case TerminalPackage.BREAK: return createBreak();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Terminal createTerminal() {
		TerminalImpl terminal = new TerminalImpl();
		return terminal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Start createStart() {
		StartImpl start = new StartImpl();
		return start;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public End createEnd() {
		EndImpl end = new EndImpl();
		return end;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Break createBreak() {
		BreakImpl break_ = new BreakImpl();
		return break_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TerminalPackage getTerminalPackage() {
		return (TerminalPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TerminalPackage getPackage() {
		return TerminalPackage.eINSTANCE;
	}

} //TerminalFactoryImpl
