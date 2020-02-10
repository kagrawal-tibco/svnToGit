/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule.util;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.*;
import com.tibco.cep.designtime.core.model.rule.ActionContext;
import com.tibco.cep.designtime.core.model.rule.ActionContextSymbol;
import com.tibco.cep.designtime.core.model.rule.Binding;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.rule.RuleSet;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.designtime.core.model.rule.RuleTemplateSymbol;
import com.tibco.cep.designtime.core.model.rule.RuleTemplateView;
import com.tibco.cep.designtime.core.model.rule.ScopeContainer;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.designtime.core.model.rule.XsltFunction;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.rule.RulePackage
 * @generated
 */
public class RuleSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static RulePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleSwitch() {
		if (modelPackage == null) {
			modelPackage = RulePackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case RulePackage.SCOPE_CONTAINER: {
				ScopeContainer scopeContainer = (ScopeContainer)theEObject;
				T result = caseScopeContainer(scopeContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulePackage.COMPILABLE: {
				Compilable compilable = (Compilable)theEObject;
				T result = caseCompilable(compilable);
				if (result == null) result = caseEntity(compilable);
				if (result == null) result = caseScopeContainer(compilable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulePackage.RULE: {
				Rule rule = (Rule)theEObject;
				T result = caseRule(rule);
				if (result == null) result = caseCompilable(rule);
				if (result == null) result = caseEntity(rule);
				if (result == null) result = caseScopeContainer(rule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulePackage.RULE_FUNCTION: {
				RuleFunction ruleFunction = (RuleFunction)theEObject;
				T result = caseRuleFunction(ruleFunction);
				if (result == null) result = caseCompilable(ruleFunction);
				if (result == null) result = caseEntity(ruleFunction);
				if (result == null) result = caseScopeContainer(ruleFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulePackage.RULE_SET: {
				RuleSet ruleSet = (RuleSet)theEObject;
				T result = caseRuleSet(ruleSet);
				if (result == null) result = caseEntity(ruleSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulePackage.SYMBOL: {
				Symbol symbol = (Symbol)theEObject;
				T result = caseSymbol(symbol);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulePackage.RULE_FUNCTION_SYMBOL: {
				RuleFunctionSymbol ruleFunctionSymbol = (RuleFunctionSymbol)theEObject;
				T result = caseRuleFunctionSymbol(ruleFunctionSymbol);
				if (result == null) result = caseSymbol(ruleFunctionSymbol);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulePackage.SYMBOLS: {
				Symbols symbols = (Symbols)theEObject;
				T result = caseSymbols(symbols);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulePackage.SYMBOL_MAP_ENTRY: {
				@SuppressWarnings("unchecked") Map.Entry<String, Symbol> symbolMapEntry = (Map.Entry<String, Symbol>)theEObject;
				T result = caseSymbolMapEntry(symbolMapEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulePackage.RULE_TEMPLATE_SYMBOL: {
				RuleTemplateSymbol ruleTemplateSymbol = (RuleTemplateSymbol)theEObject;
				T result = caseRuleTemplateSymbol(ruleTemplateSymbol);
				if (result == null) result = caseSymbol(ruleTemplateSymbol);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulePackage.ACTION_CONTEXT_SYMBOL: {
				ActionContextSymbol actionContextSymbol = (ActionContextSymbol)theEObject;
				T result = caseActionContextSymbol(actionContextSymbol);
				if (result == null) result = caseSymbol(actionContextSymbol);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulePackage.RULE_TEMPLATE: {
				RuleTemplate ruleTemplate = (RuleTemplate)theEObject;
				T result = caseRuleTemplate(ruleTemplate);
				if (result == null) result = caseRule(ruleTemplate);
				if (result == null) result = caseCompilable(ruleTemplate);
				if (result == null) result = caseEntity(ruleTemplate);
				if (result == null) result = caseScopeContainer(ruleTemplate);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulePackage.ACTION_CONTEXT: {
				ActionContext actionContext = (ActionContext)theEObject;
				T result = caseActionContext(actionContext);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulePackage.BINDING: {
				Binding binding = (Binding)theEObject;
				T result = caseBinding(binding);
				if (result == null) result = caseSymbol(binding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulePackage.RULE_TEMPLATE_VIEW: {
				RuleTemplateView ruleTemplateView = (RuleTemplateView)theEObject;
				T result = caseRuleTemplateView(ruleTemplateView);
				if (result == null) result = caseEntity(ruleTemplateView);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RulePackage.XSLT_FUNCTION: {
				XsltFunction xsltFunction = (XsltFunction)theEObject;
				T result = caseXsltFunction(xsltFunction);
				if (result == null) result = caseRuleFunction(xsltFunction);
				if (result == null) result = caseCompilable(xsltFunction);
				if (result == null) result = caseEntity(xsltFunction);
				if (result == null) result = caseScopeContainer(xsltFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Scope Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Scope Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseScopeContainer(ScopeContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Compilable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Compilable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompilable(Compilable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRule(Rule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleFunction(RuleFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleSet(RuleSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Symbol</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Symbol</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSymbol(Symbol object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Function Symbol</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Function Symbol</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleFunctionSymbol(RuleFunctionSymbol object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Symbols</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Symbols</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSymbols(Symbols object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Symbol Map Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Symbol Map Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSymbolMapEntry(Map.Entry<String, Symbol> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Template Symbol</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Template Symbol</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleTemplateSymbol(RuleTemplateSymbol object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action Context Symbol</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action Context Symbol</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActionContextSymbol(ActionContextSymbol object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Template</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Template</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleTemplate(RuleTemplate object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action Context</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action Context</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActionContext(ActionContext object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBinding(Binding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Template View</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Template View</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleTemplateView(RuleTemplateView object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Xslt Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Xslt Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseXsltFunction(XsltFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntity(Entity object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //RuleSwitch
