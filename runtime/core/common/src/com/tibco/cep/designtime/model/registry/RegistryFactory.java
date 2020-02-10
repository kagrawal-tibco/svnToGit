/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.model.registry;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.model.registry.RegistryPackage
 * @generated
 */
public interface RegistryFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RegistryFactory eINSTANCE = com.tibco.cep.designtime.model.registry.impl.RegistryFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Add On</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Add On</em>'.
	 * @generated
	 */
	AddOn createAddOn();

	/**
	 * Returns a new object of class '<em>File Extension Map Item Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>File Extension Map Item Type</em>'.
	 * @generated
	 */
	FileExtensionMapItemType createFileExtensionMapItemType();

	/**
	 * Returns a new object of class '<em>File Extension Map Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>File Extension Map Type</em>'.
	 * @generated
	 */
	FileExtensionMapType createFileExtensionMapType();

	/**
	 * Returns a new object of class '<em>Registry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Registry</em>'.
	 * @generated
	 */
	Registry createRegistry();

	/**
	 * Returns a new object of class '<em>Supported Element Types</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Supported Element Types</em>'.
	 * @generated
	 */
	SupportedElementTypes createSupportedElementTypes();

	/**
	 * Returns a new object of class '<em>Supported Extensions</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Supported Extensions</em>'.
	 * @generated
	 */
	SupportedExtensions createSupportedExtensions();

	/**
	 * Returns a new object of class '<em>Tns Entity Extensions</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tns Entity Extensions</em>'.
	 * @generated
	 */
	TnsEntityExtensions createTnsEntityExtensions();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RegistryPackage getRegistryPackage();

} //RegistryFactory
