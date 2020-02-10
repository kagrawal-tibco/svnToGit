/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.edge;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decision.tree.common.model.edge.EdgePackage
 * @generated
 */
public interface EdgeFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EdgeFactory eINSTANCE = com.tibco.cep.decision.tree.common.model.edge.impl.EdgeFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Edge</em>'.
	 * @generated
	 */
	Edge createEdge();

	/**
	 * Returns a new object of class '<em>Association</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Association</em>'.
	 * @generated
	 */
	Association createAssociation();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	EdgePackage getEdgePackage();

} //EdgeFactory
