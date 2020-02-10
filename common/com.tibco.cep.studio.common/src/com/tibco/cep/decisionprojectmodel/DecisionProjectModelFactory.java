/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionprojectmodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage
 * @generated
 */
public interface DecisionProjectModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DecisionProjectModelFactory eINSTANCE = com.tibco.cep.decisionprojectmodel.impl.DecisionProjectModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Decision Project</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Decision Project</em>'.
	 * @generated
	 */
	DecisionProject createDecisionProject();

	/**
	 * Returns a new object of class '<em>Domain Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Domain Model</em>'.
	 * @generated
	 */
	DomainModel createDomainModel();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DecisionProjectModelPackage getDecisionProjectModelPackage();

} //DecisionProjectModelFactory
