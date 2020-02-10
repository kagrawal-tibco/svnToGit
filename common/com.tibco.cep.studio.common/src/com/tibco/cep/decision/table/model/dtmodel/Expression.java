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
 * A representation of the model object '<em><b>Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Expression#getVarString <em>Var String</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getExpression()
 * @model extendedMetaData="name='Expression' kind='simple'"
 * @generated
 */
public interface Expression extends EObject {
	/**
	 * Returns the value of the '<em><b>Var String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Var String</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Var String</em>' attribute.
	 * @see #setVarString(String)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getExpression_VarString()
	 * @model
	 * @generated
	 */
	String getVarString();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.Expression#getVarString <em>Var String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Var String</em>' attribute.
	 * @see #getVarString()
	 * @generated
	 */
	void setVarString(String value);

} // Expression
