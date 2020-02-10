/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule.impl;

import com.tibco.cep.designtime.core.model.rule.*;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.designtime.core.model.rule.ActionContext;
import com.tibco.cep.designtime.core.model.rule.ActionContextSymbol;
import com.tibco.cep.designtime.core.model.rule.ActionType;
import com.tibco.cep.designtime.core.model.rule.Binding;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.rule.RuleSet;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.designtime.core.model.rule.RuleTemplateSymbol;
import com.tibco.cep.designtime.core.model.rule.RuleTemplateView;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.designtime.core.model.rule.Validity;
import com.tibco.cep.designtime.core.model.rule.XsltFunction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RuleFactoryImpl extends EFactoryImpl implements RuleFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static RuleFactory init() {
		try {
			RuleFactory theRuleFactory = (RuleFactory)EPackage.Registry.INSTANCE.getEFactory(RulePackage.eNS_URI);
			if (theRuleFactory != null) {
				return theRuleFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new RuleFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case RulePackage.RULE: return createRule();
			case RulePackage.RULE_FUNCTION: return createRuleFunction();
			case RulePackage.RULE_SET: return createRuleSet();
			case RulePackage.SYMBOL: return createSymbol();
			case RulePackage.RULE_FUNCTION_SYMBOL: return createRuleFunctionSymbol();
			case RulePackage.SYMBOLS: return createSymbols();
			case RulePackage.SYMBOL_MAP_ENTRY: return (EObject)createSymbolMapEntry();
			case RulePackage.RULE_TEMPLATE_SYMBOL: return createRuleTemplateSymbol();
			case RulePackage.ACTION_CONTEXT_SYMBOL: return createActionContextSymbol();
			case RulePackage.RULE_TEMPLATE: return createRuleTemplate();
			case RulePackage.ACTION_CONTEXT: return createActionContext();
			case RulePackage.BINDING: return createBinding();
			case RulePackage.RULE_TEMPLATE_VIEW: return createRuleTemplateView();
			case RulePackage.XSLT_FUNCTION: return createXsltFunction();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case RulePackage.VALIDITY:
				return createValidityFromString(eDataType, initialValue);
			case RulePackage.ACTION_TYPE:
				return createActionTypeFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case RulePackage.VALIDITY:
				return convertValidityToString(eDataType, instanceValue);
			case RulePackage.ACTION_TYPE:
				return convertActionTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Rule createRule() {
		RuleImpl rule = new RuleImpl();
		rule.setActionText("");
		rule.setConditionText("");
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public RuleFunction createRuleFunction() {
		RuleFunctionImpl ruleFunction = new RuleFunctionImpl();
		ruleFunction.setActionText("");
		ruleFunction.setConditionText("");
		return ruleFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleSet createRuleSet() {
		RuleSetImpl ruleSet = new RuleSetImpl();
		return ruleSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Symbol createSymbol() {
		SymbolImpl symbol = new SymbolImpl();
		return symbol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleFunctionSymbol createRuleFunctionSymbol() {
		RuleFunctionSymbolImpl ruleFunctionSymbol = new RuleFunctionSymbolImpl();
		return ruleFunctionSymbol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Symbols createSymbols() {
		SymbolsImpl symbols = new SymbolsImpl();
		return symbols;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, Symbol> createSymbolMapEntry() {
		SymbolMapEntryImpl symbolMapEntry = new SymbolMapEntryImpl();
		return symbolMapEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleTemplateSymbol createRuleTemplateSymbol() {
		RuleTemplateSymbolImpl ruleTemplateSymbol = new RuleTemplateSymbolImpl();
		return ruleTemplateSymbol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionContextSymbol createActionContextSymbol() {
		ActionContextSymbolImpl actionContextSymbol = new ActionContextSymbolImpl();
		return actionContextSymbol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleTemplate createRuleTemplate() {
		RuleTemplateImpl ruleTemplate = new RuleTemplateImpl();
		return ruleTemplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionContext createActionContext() {
		ActionContextImpl actionContext = new ActionContextImpl();
		return actionContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Binding createBinding() {
		BindingImpl binding = new BindingImpl();
		return binding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleTemplateView createRuleTemplateView() {
		RuleTemplateViewImpl ruleTemplateView = new RuleTemplateViewImpl();
		return ruleTemplateView;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XsltFunction createXsltFunction() {
		XsltFunctionImpl xsltFunction = new XsltFunctionImpl();
		return xsltFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Validity createValidityFromString(EDataType eDataType, String initialValue) {
		Validity result = Validity.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertValidityToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionType createActionTypeFromString(EDataType eDataType, String initialValue) {
		ActionType result = ActionType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertActionTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulePackage getRulePackage() {
		return (RulePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static RulePackage getPackage() {
		return RulePackage.eINSTANCE;
	}

} //RuleFactoryImpl
