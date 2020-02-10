/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.archive;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.archive.ArchivePackage
 * @generated
 */
public interface ArchiveFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ArchiveFactory eINSTANCE = com.tibco.cep.designtime.core.model.archive.impl.ArchiveFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Enterprise Archive</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Enterprise Archive</em>'.
	 * @generated
	 */
	EnterpriseArchive createEnterpriseArchive();

	/**
	 * Returns a new object of class '<em>BE Archive Resource</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>BE Archive Resource</em>'.
	 * @generated
	 */
	BEArchiveResource createBEArchiveResource();

	/**
	 * Returns a new object of class '<em>Business Events Archive Resource</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Business Events Archive Resource</em>'.
	 * @generated
	 */
	BusinessEventsArchiveResource createBusinessEventsArchiveResource();

	/**
	 * Returns a new object of class '<em>Input Destination</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Input Destination</em>'.
	 * @generated
	 */
	InputDestination createInputDestination();

	/**
	 * Returns a new object of class '<em>Advanced Entity Setting</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Advanced Entity Setting</em>'.
	 * @generated
	 */
	AdvancedEntitySetting createAdvancedEntitySetting();

	/**
	 * Returns a new object of class '<em>Shared Archive</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shared Archive</em>'.
	 * @generated
	 */
	SharedArchive createSharedArchive();

	/**
	 * Returns a new object of class '<em>Process Archive</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Process Archive</em>'.
	 * @generated
	 */
	ProcessArchive createProcessArchive();

	/**
	 * Returns a new object of class '<em>Adapter Archive</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Adapter Archive</em>'.
	 * @generated
	 */
	AdapterArchive createAdapterArchive();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ArchivePackage getArchivePackage();

} //ArchiveFactory
