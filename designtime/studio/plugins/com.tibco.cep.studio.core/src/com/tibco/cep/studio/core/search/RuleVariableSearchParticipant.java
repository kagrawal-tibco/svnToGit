package com.tibco.cep.studio.core.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;

public class RuleVariableSearchParticipant extends RuleSearchParticipant {

	public RuleVariableSearchParticipant() {
		super();
	}

	public SearchResult search(EObject resolvedElement, String projectName,
			String nameToFind, IProgressMonitor monitor) {
		ScopeBlock searchScope = null;
		if (resolvedElement.eContainer() instanceof ScopeBlock) {
			// local variable definition
			searchScope = (ScopeBlock) resolvedElement.eContainer();
		} else if (resolvedElement.eContainer() instanceof RuleElement) {
			// global variable definition
			RuleElement ruleElement = (RuleElement) resolvedElement.eContainer();
			searchScope = ruleElement.getScope();
		}
		return search(resolvedElement, projectName, nameToFind, monitor, searchScope);
	}
	
	public SearchResult search(EObject resolvedElement, String projectName,
			String nameToFind, IProgressMonitor monitor, ScopeBlock searchScope) {
		return search(resolvedElement, projectName, nameToFind, monitor, searchScope, new ArrayList<EObject>());
	}
	
	public SearchResult search(EObject resolvedElement, String projectName,
			String nameToFind, IProgressMonitor monitor, ScopeBlock searchScope, List<EObject> foundDefinitions) {
		
		SearchResult result = new SearchResult();
		List<ElementReference> foundReferences = new ArrayList<ElementReference>();
		fHelper.processScope(foundDefinitions, foundReferences, searchScope, nameToFind);
		monitor.beginTask("Searching locally...", foundReferences.size());

		result.addExactMatch(resolvedElement);
		
		monitor.subTask("Filtering references");
		for (ElementReference reference : foundReferences) {
			Object element = ElementReferenceResolver.resolveElement(reference, ElementReferenceResolver.createResolutionContext(RuleGrammarUtils.getScope(reference)));
			if (element != null && isEqual(element, resolvedElement)) {
				result.addExactMatch(reference);
			} else if (element == null) {
				// could not resolve.  This is a potential match
				result.addInexactMatch(reference);
			}
			monitor.worked(1);
		}

		return result;
	}

}
