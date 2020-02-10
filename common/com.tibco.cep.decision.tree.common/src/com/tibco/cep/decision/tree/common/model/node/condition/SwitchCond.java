/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.condition;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Switch Cond</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.condition.SwitchCond#getCases <em>Cases</em>}</li>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.condition.SwitchCond#getCasegroups <em>Casegroups</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage#getSwitchCond()
 * @model
 * @generated
 */
public interface SwitchCond extends Cond {

	/**
	 * Returns the value of the '<em><b>Cases</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.decision.tree.common.model.node.condition.Case}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cases</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cases</em>' reference list.
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage#getSwitchCond_Cases()
	 * @model
	 * @generated
	 */
	EList<Case> getCases();

	/**
	 * Returns the value of the '<em><b>Casegroups</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.decision.tree.common.model.node.condition.CaseGroup}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Casegroups</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Casegroups</em>' reference list.
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage#getSwitchCond_Casegroups()
	 * @model
	 * @generated
	 */
	EList<CaseGroup> getCasegroups();
} // SwitchCond
