/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.event;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.event.EventPackage
 * @generated
 */
public interface EventFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EventFactory eINSTANCE = com.tibco.cep.designtime.core.model.event.impl.EventFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Namespace Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Namespace Entry</em>'.
	 * @generated
	 */
	NamespaceEntry createNamespaceEntry();

	/**
	 * Returns a new object of class '<em>Import Registry Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Import Registry Entry</em>'.
	 * @generated
	 */
	ImportRegistryEntry createImportRegistryEntry();

	/**
	 * Returns a new object of class '<em>Simple Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simple Event</em>'.
	 * @generated
	 */
	SimpleEvent createSimpleEvent();

	/**
	 * Returns a new object of class '<em>Time Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Time Event</em>'.
	 * @generated
	 */
	TimeEvent createTimeEvent();

	/**
	 * Returns a new object of class '<em>User Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>User Property</em>'.
	 * @generated
	 */
	UserProperty createUserProperty();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	EventPackage getEventPackage();

} //EventFactory
