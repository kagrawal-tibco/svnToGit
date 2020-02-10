/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decision.tree.common.model.node.NodePackage
 * @generated
 */
public interface NodeFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NodeFactory eINSTANCE = com.tibco.cep.decision.tree.common.model.node.impl.NodeFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Element</em>'.
	 * @generated
	 */
	NodeElement createNodeElement();

	/**
	 * Returns a new object of class '<em>Loop</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Loop</em>'.
	 * @generated
	 */
	Loop createLoop();

	/**
	 * Returns a new object of class '<em>Description Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Description Element</em>'.
	 * @generated
	 */
	DescriptionElement createDescriptionElement();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	NodePackage getNodePackage();

} //NodeFactory
