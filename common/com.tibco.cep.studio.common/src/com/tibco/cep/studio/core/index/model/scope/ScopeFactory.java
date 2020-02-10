/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.scope;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.core.index.model.scope.ScopePackage
 * @generated
 */
public interface ScopeFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ScopeFactory eINSTANCE = com.tibco.cep.studio.core.index.model.scope.impl.ScopeFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Block</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Block</em>'.
	 * @generated
	 */
	ScopeBlock createScopeBlock();

	/**
	 * Returns a new object of class '<em>Root Scope Block</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Root Scope Block</em>'.
	 * @generated
	 */
	RootScopeBlock createRootScopeBlock();

	/**
	 * Returns a new object of class '<em>Compilable Scope</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Compilable Scope</em>'.
	 * @generated
	 */
	CompilableScope createCompilableScope();

	/**
	 * Returns a new object of class '<em>Compilable Scope Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Compilable Scope Entry</em>'.
	 * @generated
	 */
	CompilableScopeEntry createCompilableScopeEntry();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ScopePackage getScopePackage();

} //ScopeFactory
