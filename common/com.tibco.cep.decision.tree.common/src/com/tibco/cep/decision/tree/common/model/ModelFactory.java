/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decision.tree.common.model.ModelPackage
 * @generated
 */
public interface ModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelFactory eINSTANCE = com.tibco.cep.decision.tree.common.model.impl.ModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Decision Tree</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Decision Tree</em>'.
	 * @generated
	 */
	DecisionTree createDecisionTree();

	/**
	 * Returns a new object of class '<em>Flow Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Flow Element</em>'.
	 * @generated
	 */
	FlowElement createFlowElement();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ModelPackage getModelPackage();

} //ModelFactory
