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
 * A representation of the model object '<em><b>Case Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.tree.common.model.node.condition.CaseGroup#getCases <em>Cases</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage#getCaseGroup()
 * @model
 * @generated
 */
public interface CaseGroup extends Case {
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
	 * @see com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage#getCaseGroup_Cases()
	 * @model
	 * @generated
	 */
	EList<Case> getCases();

} // CaseGroup
