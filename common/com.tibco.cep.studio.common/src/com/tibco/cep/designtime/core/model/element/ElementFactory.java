/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.element;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.element.ElementPackage
 * @generated
 */
public interface ElementFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ElementFactory eINSTANCE = com.tibco.cep.designtime.core.model.element.impl.ElementFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Concept</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Concept</em>'.
	 * @generated
	 */
	Concept createConcept();

	/**
	 * Returns a new object of class '<em>Scorecard</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Scorecard</em>'.
	 * @generated
	 */
	Scorecard createScorecard();

	/**
	 * Returns a new object of class '<em>Property Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Definition</em>'.
	 * @generated
	 */
	PropertyDefinition createPropertyDefinition();

	/**
	 * Returns a new object of class '<em>Attribute Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Definition</em>'.
	 * @generated
	 */
	AttributeDefinition createAttributeDefinition();

	/**
	 * Returns a new object of class '<em>Metric</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metric</em>'.
	 * @generated
	 */
	Metric createMetric();

	/**
	 * Returns a new object of class '<em>Base Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Base Instance</em>'.
	 * @generated
	 */
	BaseInstance createBaseInstance();

	/**
	 * Returns a new object of class '<em>Process Entity</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Process Entity</em>'.
	 * @generated
	 */
	ProcessEntity createProcessEntity();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ElementPackage getElementPackage();

} //ElementFactory
