/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.palette;

import org.eclipse.emf.ecore.EFactory;


/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.common.palette.PalettePackage
 * @generated
 */
public interface PaletteFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PaletteFactory eINSTANCE = com.tibco.cep.studio.common.palette.impl.PaletteFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	DocumentRoot createDocumentRoot();

	/**
	 * Returns a new object of class '<em>Emf Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Emf Type</em>'.
	 * @generated
	 */
	EmfType createEmfType();

	/**
	 * Returns a new object of class '<em>Help</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Help</em>'.
	 * @generated
	 */
	Help createHelp();

	/**
	 * Returns a new object of class '<em>Java Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Java Type</em>'.
	 * @generated
	 */
	JavaType createJavaType();

	/**
	 * Returns a new object of class '<em>Model Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model Type</em>'.
	 * @generated
	 */
	ModelType createModelType();

	/**
	 * Returns a new object of class '<em>Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Group</em>'.
	 * @generated
	 */
	PaletteGroup createPaletteGroup();

	/**
	 * Returns a new object of class '<em>Item</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Item</em>'.
	 * @generated
	 */
	PaletteItem createPaletteItem();

	/**
	 * Returns a new object of class '<em>Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model</em>'.
	 * @generated
	 */
	PaletteModel createPaletteModel();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	PalettePackage getPalettePackage();

} //PaletteFactory
