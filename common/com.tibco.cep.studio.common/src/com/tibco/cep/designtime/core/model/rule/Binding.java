/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Binding#getExpression <em>Expression</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Binding#getDomainModelPath <em>Domain Model Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getBinding()
 * @model
 * @generated
 */
public interface Binding extends Symbol {
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
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getBinding_Expression()
	 * @model default=""
	 * @generated
	 */
	String getExpression();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Binding#getExpression <em>Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' attribute.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(String value);

	/**
	 * Returns the value of the '<em><b>Domain Model Path</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Model Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Model Path</em>' attribute.
	 * @see #setDomainModelPath(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getBinding_DomainModelPath()
	 * @model default=""
	 * @generated
	 */
	String getDomainModelPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Binding#getDomainModelPath <em>Domain Model Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain Model Path</em>' attribute.
	 * @see #getDomainModelPath()
	 * @generated
	 */
	void setDomainModelPath(String value);

} // Binding
