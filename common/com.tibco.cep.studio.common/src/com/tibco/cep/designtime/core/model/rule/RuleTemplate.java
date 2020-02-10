/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule;

import com.tibco.cep.designtime.core.model.SimpleProperty;
import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Template</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.RuleTemplate#getBindings <em>Bindings</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.RuleTemplate#getDisplayProperties <em>Display Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.RuleTemplate#getActionContext <em>Action Context</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.RuleTemplate#getViews <em>Views</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleTemplate()
 * @model
 * @generated
 */
public interface RuleTemplate extends Rule {
	/**
	 * Returns the value of the '<em><b>Bindings</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.rule.Binding}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bindings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bindings</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleTemplate_Bindings()
	 * @model containment="true"
	 * @generated
	 */
	EList<Binding> getBindings();

	/**
	 * Returns the value of the '<em><b>Display Properties</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.SimpleProperty}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display Properties</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Properties</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleTemplate_DisplayProperties()
	 * @model containment="true"
	 * @generated
	 */
	EList<SimpleProperty> getDisplayProperties();

	/**
	 * Returns the value of the '<em><b>Action Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action Context</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action Context</em>' containment reference.
	 * @see #setActionContext(ActionContext)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleTemplate_ActionContext()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ActionContext getActionContext();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplate#getActionContext <em>Action Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action Context</em>' containment reference.
	 * @see #getActionContext()
	 * @generated
	 */
	void setActionContext(ActionContext value);

	/**
	 * Returns the value of the '<em><b>Views</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Views</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Views</em>' attribute list.
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleTemplate_Views()
	 * @model
	 * @generated
	 */
	EList<String> getViews();

} // RuleTemplate
