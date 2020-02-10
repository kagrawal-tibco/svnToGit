/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.RuleSet#getRules <em>Rules</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleSet()
 * @model
 * @generated
 */
public interface RuleSet extends Entity {
	/**
	 * Returns the value of the '<em><b>Rules</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.rule.Rule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleSet_Rules()
	 * @model
	 * @generated
	 */
	EList<Rule> getRules();

} // RuleSet
