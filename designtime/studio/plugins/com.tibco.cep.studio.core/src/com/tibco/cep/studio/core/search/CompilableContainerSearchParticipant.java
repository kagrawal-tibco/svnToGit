package com.tibco.cep.studio.core.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.be.model.functions.impl.JavaStaticFunctionWithXSLT;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.scope.CompilableScope;
import com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public abstract class CompilableContainerSearchParticipant extends
		EntitySearchParticipant implements ISourceSearchParticipant {

	private RuleSearchHelper fHelper;

	public CompilableContainerSearchParticipant() {
		super();
		this.fHelper = new RuleSearchHelper();
	}

	@Override
	public SearchResult searchSource(Object resolvedElement,
			String projectName, String nameToFind, IProgressMonitor monitor) {
		return search(resolvedElement, projectName, nameToFind, monitor);
	}

	protected void processCompilableScopeEntry(String projectName, EObject resolvedElement,
			CompilableScopeEntry compilableScopeEntry, String nameToFind, 
			SearchResult result, Compilable compilable) {

		CompilableScope compilableScope = compilableScopeEntry.getScope();
		EList<GlobalVariableDef> globalVariables = compilableScope.getGlobalVariables();
		for (GlobalVariableDef globalVariableDef : globalVariables) {
			// need to resolve this and check it against the resolved type
			Object entity = ElementReferenceResolver.resolveVariableDefinitionType(globalVariableDef);
			if (entity instanceof EntityElement) {
				entity = ((EntityElement) entity).getEntity();
			}
			if (isEqual(entity, resolvedElement)) {
				result.addExactMatch(globalVariableDef);
			}
		}

		List<EObject> foundDefinitions = new ArrayList<EObject>();
		List<ElementReference> foundReferences = new ArrayList<ElementReference>();

		ScopeBlock scope = compilableScope.getConditionScope();
		if (scope != null) {
			fHelper.processScope(foundDefinitions, foundReferences, scope, nameToFind);
		}

		scope = compilableScope.getActionScope();
		if (scope != null) {
			fHelper.processScope(foundDefinitions, foundReferences, scope, nameToFind);
		}

		for (EObject definition : foundDefinitions) {
			if (isEqual(definition, resolvedElement)) {
				result.addExactMatch(definition);
			}
		}

		List<Compilable> processedCompilables = new ArrayList<Compilable>();
		// resolve all element references
		for (ElementReference reference : foundReferences) {
			Object element = ElementReferenceResolver.resolveElement(reference, ElementReferenceResolver.createResolutionContext(RuleGrammarUtils.getScope(reference)));
			if (element != null && isEqual(element, resolvedElement)) {
				result.addExactMatch(reference);
			} else if (element instanceof JavaStaticFunctionWithXSLT) {
				processFunction(reference, (JavaStaticFunction)element, resolvedElement, projectName, result, processedCompilables, compilable);
			} else if (element == null) {
				if (reference.getName().equals(nameToFind)) {
					result.addInexactMatch(reference);
				}
			}
		}

	}

	public void processFunction(ElementReference reference, JavaStaticFunction element,
			Object resolvedElement, String projectName, SearchResult result, List<Compilable> processedCompilables, Compilable compilable) {
		
		if (compilable == null) {
			return;
		}
		if (resolvedElement instanceof EntityElement) {
			resolvedElement = ((EntityElement) resolvedElement).getEntity();
		}
		if (!(resolvedElement instanceof Entity)) {
			return;
		}
		Entity entity = (Entity) resolvedElement;
		DesignerElement variableContext = IndexUtils.getVariableContext(reference);
		if (!(variableContext instanceof EObject)) {
			return;
		}
		EObject container = (EObject) variableContext;
		if (processedCompilables.contains(compilable)) {
			return;
		}
		processedCompilables.add(compilable);
		
		if (compilable.getConditionText() != null && compilable.getConditionText().length() > 0) {
			RulesASTNode node = (RulesASTNode) RulesParserManager.parseConditionsBlockString(projectName, compilable.getConditionText(), null);
			fHelper.processFunctionsFromNode(projectName, result, entity,
					container, node);
		}
		
		if (compilable.getActionText() != null && compilable.getActionText().length() > 0) {
			RulesASTNode node = (RulesASTNode) RulesParserManager.parseActionBlockString(projectName, compilable.getActionText(), null);
			fHelper.processFunctionsFromNode(projectName, result, entity,
					container, node);
		}
	}

}
