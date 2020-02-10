/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Argument</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Argument#getProperty <em>Property</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Argument#getDirection <em>Direction</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getArgument()
 * @model
 * @generated
 */
public interface Argument extends EObject {
	/**
	 * Returns the value of the '<em><b>Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' containment reference.
	 * @see #setProperty(ArgumentProperty)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getArgument_Property()
	 * @model containment="true"
	 * @generated
	 */
	ArgumentProperty getProperty();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.Argument#getProperty <em>Property</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property</em>' containment reference.
	 * @see #getProperty()
	 * @generated
	 */
	void setProperty(ArgumentProperty value);

	/**
	 * Returns the value of the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see #setDirection(String)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getArgument_Direction()
	 * @model
	 * @generated
	 */
	String getDirection();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.Argument#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see #getDirection()
	 * @generated
	 */
	void setDirection(String value);

} // Argument
