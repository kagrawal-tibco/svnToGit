package com.tibco.cep.studio.core.search;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.StateMachineElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class StateMachineSearchParticipant extends CompilableContainerSearchParticipant {

	@Override
	protected void searchEntity(EObject resolvedElement, DesignerProject index,
			String nameToFind, IProgressMonitor monitor, SearchResult result) {
		List<DesignerElement> allEntities = IndexUtils.getAllElements(index.getName(), new ELEMENT_TYPES[] { ELEMENT_TYPES.STATE_MACHINE });

		for (DesignerElement entity : allEntities) {
			StateMachineElement stateMachine = (StateMachineElement) entity;
			if (isEqual(stateMachine.getEntity(), resolvedElement)) {
				result.addExactMatch(createElementMatch(DEFINITION_FEATURE, stateMachine.eClass(), resolvedElement));
			}
			EList<CompilableScopeEntry> compilableScopes = stateMachine.getCompilableScopes();
			for (CompilableScopeEntry compilableScopeEntry : compilableScopes) {
				Compilable rule = ModelUtils.findCompilable((State) stateMachine.getEntity(), compilableScopeEntry.getRuleName());
				processCompilableScopeEntry(index.getName(), resolvedElement, compilableScopeEntry, nameToFind, result, rule);
			}
			EObject eObj = resolvedElement;
			if (resolvedElement instanceof EntityElement) {
				eObj = ((EntityElement) resolvedElement).getEntity();
			}
			if (eObj instanceof Concept) {
				StateMachine mach = (StateMachine) stateMachine.getEntity();
				if (((Concept) eObj).getFullPath().equals(mach.getOwnerConceptPath())) {
					result.addExactMatch(createElementMatch(StatesPackage.STATE_MACHINE__OWNER_CONCEPT_PATH, mach.eClass(), mach));
				}
			}
		}
	}
	
	@Override
	protected boolean isEqual(Object element, Object resolvedElement) {
		if (element instanceof Destination
				&& resolvedElement instanceof Destination) {
			Destination dest1 = (Destination) element;
			Destination dest2 = (Destination) resolvedElement;
			if (dest1.getName().equals(dest2.getName())
					&& dest1.getEventURI().equals(dest2.getEventURI())
					&& dest1.getFolder().equals(dest2.getFolder())) {
				return true;
			}
		}
		if (element instanceof VariableDefinition
				&& resolvedElement instanceof VariableDefinition) {
			VariableDefinition def1 = (VariableDefinition) element;
			VariableDefinition def2 = (VariableDefinition) resolvedElement;
			if (def1.getName().equals(def2.getName())
					&& def1.getType().equals(def2.getType())) {
				return true; // TODO : this is an insufficient 'equals' check -- need to check scope as well
			}
		}
		return super.isEqual(element, resolvedElement);
	}

}
