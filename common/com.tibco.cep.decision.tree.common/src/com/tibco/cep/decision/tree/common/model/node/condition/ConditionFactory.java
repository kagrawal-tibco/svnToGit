/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.condition;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage
 * @generated
 */
public interface ConditionFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ConditionFactory eINSTANCE = com.tibco.cep.decision.tree.common.model.node.condition.impl.ConditionFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Cond</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Cond</em>'.
	 * @generated
	 */
	Cond createCond();

	/**
	 * Returns a new object of class '<em>Bool Cond</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bool Cond</em>'.
	 * @generated
	 */
	BoolCond createBoolCond();

	/**
	 * Returns a new object of class '<em>Switch Cond</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Switch Cond</em>'.
	 * @generated
	 */
	SwitchCond createSwitchCond();

	/**
	 * Returns a new object of class '<em>Case</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Case</em>'.
	 * @generated
	 */
	Case createCase();

	/**
	 * Returns a new object of class '<em>Case Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Case Group</em>'.
	 * @generated
	 */
	CaseGroup createCaseGroup();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ConditionPackage getConditionPackage();

} //ConditionFactory
