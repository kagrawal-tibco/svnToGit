/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Template Symbol</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.RuleTemplateSymbol#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleTemplateSymbol()
 * @model
 * @generated
 */
public interface RuleTemplateSymbol extends Symbol {
	/**
	 * Returns the value of the '<em><b>Expression</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' attribute.
	 * @see #setExpression(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleTemplateSymbol_Expression()
	 * @model default=""
	 * @generated
	 */
	String getExpression();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplateSymbol#getExpression <em>Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' attribute.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(String value);

} // RuleTemplateSymbol
