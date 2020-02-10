/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule.util;

import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.rule.RulePackage
 * @generated
 */
public class RuleAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static RulePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = RulePackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleSwitch<Adapter> modelSwitch =
		new RuleSwitch<Adapter>() {
			@Override
			public Adapter caseScopeContainer(ScopeContainer object) {
				return createScopeContainerAdapter();
			}
			@Override
			public Adapter caseCompilable(Compilable object) {
				return createCompilableAdapter();
			}
			@Override
			public Adapter caseRule(Rule object) {
				return createRuleAdapter();
			}
			@Override
			public Adapter caseRuleFunction(RuleFunction object) {
				return createRuleFunctionAdapter();
			}
			@Override
			public Adapter caseRuleSet(RuleSet object) {
				return createRuleSetAdapter();
			}
			@Override
			public Adapter caseSymbol(Symbol object) {
				return createSymbolAdapter();
			}
			@Override
			public Adapter caseRuleFunctionSymbol(RuleFunctionSymbol object) {
				return createRuleFunctionSymbolAdapter();
			}
			@Override
			public Adapter caseSymbols(Symbols object) {
				return createSymbolsAdapter();
			}
			@Override
			public Adapter caseSymbolMapEntry(Map.Entry<String, Symbol> object) {
				return createSymbolMapEntryAdapter();
			}
			@Override
			public Adapter caseRuleTemplateSymbol(RuleTemplateSymbol object) {
				return createRuleTemplateSymbolAdapter();
			}
			@Override
			public Adapter caseActionContextSymbol(ActionContextSymbol object) {
				return createActionContextSymbolAdapter();
			}
			@Override
			public Adapter caseRuleTemplate(RuleTemplate object) {
				return createRuleTemplateAdapter();
			}
			@Override
			public Adapter caseActionContext(ActionContext object) {
				return createActionContextAdapter();
			}
			@Override
			public Adapter caseBinding(Binding object) {
				return createBindingAdapter();
			}
			@Override
			public Adapter caseRuleTemplateView(RuleTemplateView object) {
				return createRuleTemplateViewAdapter();
			}
			@Override
			public Adapter caseXsltFunction(XsltFunction object) {
				return createXsltFunctionAdapter();
			}
			@Override
			public Adapter caseEntity(Entity object) {
				return createEntityAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.rule.ScopeContainer <em>Scope Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.rule.ScopeContainer
	 * @generated
	 */
	public Adapter createScopeContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.rule.Compilable <em>Compilable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.rule.Compilable
	 * @generated
	 */
	public Adapter createCompilableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.rule.Rule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.rule.Rule
	 * @generated
	 */
	public Adapter createRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.rule.RuleFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleFunction
	 * @generated
	 */
	public Adapter createRuleFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.rule.RuleSet <em>Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleSet
	 * @generated
	 */
	public Adapter createRuleSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.rule.Symbol <em>Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.rule.Symbol
	 * @generated
	 */
	public Adapter createSymbolAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol <em>Function Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol
	 * @generated
	 */
	public Adapter createRuleFunctionSymbolAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.rule.Symbols <em>Symbols</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.rule.Symbols
	 * @generated
	 */
	public Adapter createSymbolsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>Symbol Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createSymbolMapEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplateSymbol <em>Template Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleTemplateSymbol
	 * @generated
	 */
	public Adapter createRuleTemplateSymbolAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.rule.ActionContextSymbol <em>Action Context Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.rule.ActionContextSymbol
	 * @generated
	 */
	public Adapter createActionContextSymbolAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplate <em>Template</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleTemplate
	 * @generated
	 */
	public Adapter createRuleTemplateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.rule.ActionContext <em>Action Context</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.rule.ActionContext
	 * @generated
	 */
	public Adapter createActionContextAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.rule.Binding <em>Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.rule.Binding
	 * @generated
	 */
	public Adapter createBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.rule.RuleTemplateView <em>Template View</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.rule.RuleTemplateView
	 * @generated
	 */
	public Adapter createRuleTemplateViewAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.rule.XsltFunction <em>Xslt Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.rule.XsltFunction
	 * @generated
	 */
	public Adapter createXsltFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.designtime.core.model.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.designtime.core.model.Entity
	 * @generated
	 */
	public Adapter createEntityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //RuleAdapterFactory
