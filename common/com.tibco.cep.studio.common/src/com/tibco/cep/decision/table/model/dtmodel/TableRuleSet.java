/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table Rule Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleSet#getRule <em>Rule</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleSet#getColumns <em>Columns</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRuleSet()
 * @model
 * @generated
 */
public interface TableRuleSet extends Metadatable {
	/**
	 * Returns the value of the '<em><b>Rule</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.decision.table.model.dtmodel.TableRule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule</em>' containment reference list.
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRuleSet_Rule()
	 * @model containment="true"
	 * @generated
	 */
	EList<TableRule> getRule();

	/**
	 * Returns the value of the '<em><b>Columns</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Columns</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Columns</em>' containment reference.
	 * @see #setColumns(Columns)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getTableRuleSet_Columns()
	 * @model containment="true"
	 * @generated
	 */
	Columns getColumns();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleSet#getColumns <em>Columns</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Columns</em>' containment reference.
	 * @see #getColumns()
	 * @generated
	 */
	void setColumns(Columns value);

} // TableRuleSet
