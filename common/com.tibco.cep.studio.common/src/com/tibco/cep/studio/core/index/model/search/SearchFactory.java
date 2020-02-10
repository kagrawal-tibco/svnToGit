/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.search;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.core.index.model.search.SearchPackage
 * @generated
 */
public interface SearchFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SearchFactory eINSTANCE = com.tibco.cep.studio.core.index.model.search.impl.SearchFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Element Match</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Element Match</em>'.
	 * @generated
	 */
	ElementMatch createElementMatch();

	/**
	 * Returns a new object of class '<em>Method Argument Match</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Method Argument Match</em>'.
	 * @generated
	 */
	MethodArgumentMatch createMethodArgumentMatch();

	/**
	 * Returns a new object of class '<em>String Literal Match</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String Literal Match</em>'.
	 * @generated
	 */
	StringLiteralMatch createStringLiteralMatch();

	/**
	 * Returns a new object of class '<em>Non Entity Match</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Non Entity Match</em>'.
	 * @generated
	 */
	NonEntityMatch createNonEntityMatch();

	/**
	 * Returns a new object of class '<em>Rule Source Match</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule Source Match</em>'.
	 * @generated
	 */
	RuleSourceMatch createRuleSourceMatch();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SearchPackage getSearchPackage();

} //SearchFactory
