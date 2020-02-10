/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.decisionproject.ontology.Implementation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Table#getDecisionTable <em>Decision Table</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Table#getExceptionTable <em>Exception Table</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Table#getArgument <em>Argument</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Table#getSince <em>Since</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTable()
 * @model
 * @generated
 */
public interface Table extends Implementation, Metadatable {
	/**
	 * Returns the value of the '<em><b>Decision Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Decision Table</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Decision Table</em>' containment reference.
	 * @see #setDecisionTable(TableRuleSet)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTable_DecisionTable()
	 * @model containment="true"
	 * @generated
	 */
	TableRuleSet getDecisionTable();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.Table#getDecisionTable <em>Decision Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Decision Table</em>' containment reference.
	 * @see #getDecisionTable()
	 * @generated
	 */
	void setDecisionTable(TableRuleSet value);

	/**
	 * Returns the value of the '<em><b>Exception Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exception Table</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exception Table</em>' containment reference.
	 * @see #setExceptionTable(TableRuleSet)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTable_ExceptionTable()
	 * @model containment="true"
	 * @generated
	 */
	TableRuleSet getExceptionTable();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.Table#getExceptionTable <em>Exception Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exception Table</em>' containment reference.
	 * @see #getExceptionTable()
	 * @generated
	 */
	void setExceptionTable(TableRuleSet value);

	/**
	 * Returns the value of the '<em><b>Argument</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.decision.table.model.dtmodel.Argument}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Argument</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Argument</em>' containment reference list.
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTable_Argument()
	 * @model containment="true"
	 * @generated
	 */
	EList<Argument> getArgument();

	/**
	 * Returns the value of the '<em><b>Since</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Since</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Since</em>' attribute.
	 * @see #setSince(String)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTable_Since()
	 * @model
	 * @generated
	 */
	String getSince();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.Table#getSince <em>Since</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Since</em>' attribute.
	 * @see #getSince()
	 * @generated
	 */
	void setSince(String value);

	/**
	 * @return the value of the 'deployed'
	 * @see #setDeployed(boolean deployed)
	 * @generated Not
	 */
	boolean isDeployed();

	/**
	 * @param value the new value of the 'deployed'.
	 * @see #isDeployed()
	 * @generated Not
	 */
	void setDeployed(boolean deployed);

} // Table
