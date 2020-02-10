/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule;

import com.tibco.cep.designtime.core.model.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Template View</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.RuleTemplateView#getRuleTemplatePath <em>Rule Template Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.RuleTemplateView#getPresentationText <em>Presentation Text</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.RuleTemplateView#getHtmlFile <em>Html File</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleTemplateView()
 * @model
 * @generated
 */
public interface RuleTemplateView extends Entity {
	/**
	 * Returns the value of the '<em><b>Rule Template Path</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Template Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Template Path</em>' attribute.
	 * @see #setRuleTemplatePath(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleTemplateView_RuleTemplatePath()
	 * @model default=""
	 * @generated
	 */
	String getRuleTemplatePath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplateView#getRuleTemplatePath <em>Rule Template Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Template Path</em>' attribute.
	 * @see #getRuleTemplatePath()
	 * @generated
	 */
	void setRuleTemplatePath(String value);

	/**
	 * Returns the value of the '<em><b>Presentation Text</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Presentation Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Presentation Text</em>' attribute.
	 * @see #setPresentationText(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleTemplateView_PresentationText()
	 * @model default=""
	 * @generated
	 */
	String getPresentationText();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplateView#getPresentationText <em>Presentation Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Presentation Text</em>' attribute.
	 * @see #getPresentationText()
	 * @generated
	 */
	void setPresentationText(String value);

	/**
	 * Returns the value of the '<em><b>Html File</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Html File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Html File</em>' attribute.
	 * @see #setHtmlFile(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleTemplateView_HtmlFile()
	 * @model default=""
	 * @generated
	 */
	String getHtmlFile();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplateView#getHtmlFile <em>Html File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Html File</em>' attribute.
	 * @see #getHtmlFile()
	 * @generated
	 */
	void setHtmlFile(String value);

} // RuleTemplateView
