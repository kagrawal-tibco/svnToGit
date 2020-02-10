/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage
 * @generated
 */
public interface DtmodelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DtmodelFactory eINSTANCE = com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Argument</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Argument</em>'.
	 * @generated
	 */
	Argument createArgument();

	/**
	 * Returns a new object of class '<em>Table Rule Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Table Rule Set</em>'.
	 * @generated
	 */
	TableRuleSet createTableRuleSet();

	/**
	 * Returns a new object of class '<em>Table Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Table Rule</em>'.
	 * @generated
	 */
	TableRule createTableRule();

	/**
	 * Returns a new object of class '<em>Argument Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Argument Property</em>'.
	 * @generated
	 */
	ArgumentProperty createArgumentProperty();

	/**
	 * Returns a new object of class '<em>Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Expression</em>'.
	 * @generated
	 */
	Expression createExpression();

	/**
	 * Returns a new object of class '<em>Table</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Table</em>'.
	 * @generated
	 */
	Table createTable();

	/**
	 * Returns a new object of class '<em>Meta Data</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Meta Data</em>'.
	 * @generated
	 */
	MetaData createMetaData();

	/**
	 * Returns a new object of class '<em>Table Rule Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Table Rule Variable</em>'.
	 * @generated
	 */
	TableRuleVariable createTableRuleVariable();

	/**
	 * Returns a new object of class '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property</em>'.
	 * @generated
	 */
	Property createProperty();

	/**
	 * Returns a new object of class '<em>Columns</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Columns</em>'.
	 * @generated
	 */
	Columns createColumns();

	/**
	 * Returns a new object of class '<em>Column</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Column</em>'.
	 * @generated
	 */
	Column createColumn();

	/**
	 * Returns a new object of class '<em>Metadatable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metadatable</em>'.
	 * @generated
	 */
	Metadatable createMetadatable();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DtmodelPackage getDtmodelPackage();

} //DtmodelFactory
