/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.domainmodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage
 * @generated
 */
public interface DomainModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DomainModelFactory eINSTANCE = com.tibco.cep.decision.table.model.domainmodel.impl.DomainModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Domain Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Domain Entry</em>'.
	 * @generated
	 */
	DomainEntry createDomainEntry();

	/**
	 * Returns a new object of class '<em>Range Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Range Info</em>'.
	 * @generated
	 */
	RangeInfo createRangeInfo();

	/**
	 * Returns a new object of class '<em>Single Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Single Value</em>'.
	 * @generated
	 */
	SingleValue createSingleValue();

	/**
	 * Returns a new object of class '<em>Domain</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Domain</em>'.
	 * @generated
	 */
	Domain createDomain();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DomainModelPackage getDomainModelPackage();

} //DomainModelFactory
