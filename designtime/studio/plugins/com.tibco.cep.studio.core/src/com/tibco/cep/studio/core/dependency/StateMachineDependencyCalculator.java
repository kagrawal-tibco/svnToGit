package com.tibco.cep.studio.core.dependency;

import java.io.File;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.StateMachineElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class StateMachineDependencyCalculator extends EntityDependencyCalculator {

	@Override
	protected void processEntityElement(File projectDir, String projectName,
			EntityElement element, List<Object> dependencies) {
		if (!(element.getEntity() instanceof StateMachine)) {
			return;
		}
		StateMachine stateMachine = (StateMachine) element.getEntity();
		Concept ownerConcept = stateMachine.getOwnerConcept();
		if (ownerConcept != null) {
			processAndAddDependency(dependencies, projectName, ownerConcept);
		}

		DesignerElement evElem = IndexUtils.getElement(projectName, stateMachine.getFullPath());
		if (evElem instanceof StateMachineElement) {
			EList<CompilableScopeEntry> compilableScopes = ((StateMachineElement) evElem).getCompilableScopes();
			for (int i = 0; i < compilableScopes.size(); i++) {
				CompilableScopeEntry entry = compilableScopes.get(i);
				if (entry != null && entry.getScope() != null) {
					new RulesDependencyCalculator().processScope(projectDir, projectName, entry.getScope().getActionScope(), dependencies);
				}
			}
		}
		
		EList<StateTransition> transitions = stateMachine.getStateTransitions();
		for (int i = 0; i < transitions.size(); i++) {
			StateTransition stateTransition = transitions.get(i);
			Rule rule = stateTransition.getGuardRule();
			Symbols symbols = rule.getSymbols();
			EList<Symbol> symbolList = symbols.getSymbolList();
			for (int j = 0; j < symbolList.size(); j++) {
				Symbol symbol = symbolList.get(j);
				String type = symbol.getType();
				DesignerElement symbolElement = IndexUtils.getElement(projectName, type);
				if (symbolElement instanceof TypeElement) {
					processAndAddDependency(projectDir, dependencies, projectName, (TypeElement) symbolElement);
				}
			}
		}
		
		// process state entities (for Symbols, CallSMs, etc)
		// nothing (yet) to process.  Uncomment if needed
//		processStateEntity(projectName, stateMachine, dependencies);
	}

//	private void processStateEntity(String projectName, StateEntity stateEntity, List<IResource> dependencies) {
//		
//		if (stateEntity instanceof StateComposite) {
//			EList<StateEntity> stateEntities = ((StateComposite) stateEntity).getStateEntities();
//			for (int i = 0; i < stateEntities.size(); i++) {
//				processStateEntity(projectName, stateEntities.get(i), dependencies);
//			}
//		}
//	}

}
