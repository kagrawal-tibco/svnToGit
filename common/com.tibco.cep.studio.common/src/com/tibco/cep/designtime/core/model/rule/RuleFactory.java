/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.rule.RulePackage
 * @generated
 */
public interface RuleFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RuleFactory eINSTANCE = com.tibco.cep.designtime.core.model.rule.impl.RuleFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule</em>'.
	 * @generated
	 */
	Rule createRule();

	/**
	 * Returns a new object of class '<em>Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function</em>'.
	 * @generated
	 */
	RuleFunction createRuleFunction();

	/**
	 * Returns a new object of class '<em>Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Set</em>'.
	 * @generated
	 */
	RuleSet createRuleSet();

	/**
	 * Returns a new object of class '<em>Symbol</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Symbol</em>'.
	 * @generated
	 */
	Symbol createSymbol();

	/**
	 * Returns a new object of class '<em>Function Symbol</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function Symbol</em>'.
	 * @generated
	 */
	RuleFunctionSymbol createRuleFunctionSymbol();

	/**
	 * Returns a new object of class '<em>Symbols</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Symbols</em>'.
	 * @generated
	 */
	Symbols createSymbols();

	/**
	 * Returns a new object of class '<em>Template Symbol</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Template Symbol</em>'.
	 * @generated
	 */
	RuleTemplateSymbol createRuleTemplateSymbol();

	/**
	 * Returns a new object of class '<em>Action Context Symbol</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Action Context Symbol</em>'.
	 * @generated
	 */
	ActionContextSymbol createActionContextSymbol();

	/**
	 * Returns a new object of class '<em>Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Template</em>'.
	 * @generated
	 */
	RuleTemplate createRuleTemplate();

	/**
	 * Returns a new object of class '<em>Action Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Action Context</em>'.
	 * @generated
	 */
	ActionContext createActionContext();

	/**
	 * Returns a new object of class '<em>Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Binding</em>'.
	 * @generated
	 */
	Binding createBinding();

	/**
	 * Returns a new object of class '<em>Template View</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Template View</em>'.
	 * @generated
	 */
	RuleTemplateView createRuleTemplateView();

	/**
	 * Returns a new object of class '<em>Xslt Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Xslt Function</em>'.
	 * @generated
	 */
	XsltFunction createXsltFunction();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RulePackage getRulePackage();

} //RuleFactory
