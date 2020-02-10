package com.tibco.cep.studio.core.index.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedStateMachineElement;
import com.tibco.cep.studio.core.index.model.StateMachineElement;
import com.tibco.cep.studio.core.index.model.scope.CompilableScope;
import com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry;
import com.tibco.cep.studio.core.index.model.scope.ScopeFactory;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public class BaseStateMachineElementCreator {

	public BaseStateMachineElementCreator() {
	}
	
	public StateMachineElement createStateMachineElement(String projectName, StateMachine stateMachine) {
		return createStateMachineElement(projectName, stateMachine, false);
	}
	
	public StateMachineElement createStateMachineElement(String projectName, StateMachine stateMachine, boolean shared) {
		
		StateMachineElement element = null;
		if (shared) {
			element = IndexFactory.eINSTANCE.createSharedStateMachineElement();
			((SharedStateMachineElement) element).setSharedEntity(stateMachine);
		} else {
			element = IndexFactory.eINSTANCE.createStateMachineElement();
			element.setEntity(stateMachine);
		}
		element.setName(stateMachine.getName());
		element.setFolder(stateMachine.getFolder());
		element.setElementType(CommonIndexUtils.getElementType(stateMachine));
		element.setIndexName(projectName);

		List<Compilable> compilables = new ArrayList<Compilable>();
		EList<StateTransition> stateTransitions = stateMachine.getStateTransitions();
		for (StateTransition stateTransition : stateTransitions) {
			Rule guardRule = stateTransition.getGuardRule();
			if (guardRule != null) {
				compilables.add(guardRule);
			}
		}
		collectCompilables(stateMachine, compilables);
		for (Compilable compilable : compilables) {
			CompilableScopeEntry entry = ScopeFactory.eINSTANCE.createCompilableScopeEntry();
			CompilableScope scope = ScopeFactory.eINSTANCE.createCompilableScope();
			entry.setScope(scope);
			entry.setRuleName(compilable.getName()); // TODO : create unique name for compilable
			processSymbols(compilable.getSymbols().getSymbolList(), scope);
			processConditionBlock(projectName, compilable, scope);
			processActionBlock(projectName, compilable, scope);
			element.getCompilableScopes().add(entry);
		}
		
		return element;
	}

	private void processSymbols(Collection<Symbol> symbols, CompilableScope scope) {
		for (Symbol symbol : symbols) {
			GlobalVariableDef globalVariableDef = IndexFactory.eINSTANCE.createGlobalVariableDef();
			globalVariableDef.setName(symbol.getIdName());
			globalVariableDef.setType(symbol.getType());
			scope.getGlobalVariables().add(globalVariableDef);
		}
	}

	private void processActionBlock(String projectName, Compilable compilable, CompilableScope scope) {
		if (compilable.getActionText() == null
				|| compilable.getActionText().length() == 0) {
			return;
		}
		RulesASTNode tree = (RulesASTNode) BaseRulesParserManager.parseActionBlockString(projectName, compilable.getActionText(), null);
		if (tree == null) {
			return;
		}
		RuleElement ruleElement = (RuleElement) tree.getData("element");
		if (ruleElement != null && ruleElement.getScope() != null) {
			scope.setActionScope(ruleElement.getScope());
		} else {
			System.out.println("Rule Element or scope was null");
		}
	}

	private void processConditionBlock(String projectName, Compilable compilable, CompilableScope scope) {
		if (compilable.getConditionText() == null
				|| compilable.getConditionText().length() == 0) {
			return;
		}
		RulesASTNode tree = (RulesASTNode) BaseRulesParserManager.parseConditionsBlockString(projectName, compilable.getConditionText(), null);
		if (tree == null) {
			return;
		}
		RuleElement ruleElement = (RuleElement) tree.getData("element");
		if (ruleElement != null && ruleElement.getScope() != null) {
			scope.setConditionScope(ruleElement.getScope());
		} else {
			System.out.println("Rule Element or scope was null");
		}
	}

	/*
	 * Collect all compilables that need to be indexed
	 */
	private void collectCompilables(State composite,
			List<Compilable> compilables) {
		if (composite.getEntryAction() != null) {
			compilables.add(composite.getEntryAction());
		}
		if (composite.getExitAction() != null) {
			compilables.add(composite.getExitAction());
		}
		if (composite.getInternalTransitionRule() != null) {
			compilables.add(composite.getInternalTransitionRule());
		}
		if (composite.getTimeoutAction() != null) {
			compilables.add(composite.getTimeoutAction());
		}
		if (composite.getTimeoutExpression() != null) {
			compilables.add(composite.getTimeoutExpression());
		}
		if (composite instanceof StateComposite) {
			EList<StateEntity> stateEntities = ((StateComposite) composite).getStateEntities();
			for (StateEntity stateEntity : stateEntities) {
				if (stateEntity instanceof State) {
					collectCompilables((State) stateEntity, compilables);
				} else {
					System.out.println("something else");
				}
			}
		}
	}
	
}
