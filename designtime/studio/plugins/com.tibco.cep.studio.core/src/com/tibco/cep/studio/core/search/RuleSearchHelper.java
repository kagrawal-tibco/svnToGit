package com.tibco.cep.studio.core.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.IResolutionContext;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.StringArgumentEntityFinder;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public class RuleSearchHelper {
	
	public static final String[] targetMethodNames = new String[] { "Event.assertEvent", "Event.routeTo", 
		"Event.sendEvent", "Event.replyEvent", "Event.createEvent", "Instance.createInstance",
		"XPath.evalAsBoolean", "XPath.evalAsDateTime", "XPath.evalAsDouble", "XPath.evalAsInt", 
		"XPath.evalAsLong", "XPath.evalAsString", "VRF.invokeVRFImplByName"};

	public RuleSearchHelper() {
		super();
	}

	public void processFunctionsFromNode(String projectName, SearchResult result, EObject entity,
			EObject container, RulesASTNode node) {
		if (node == null) {
			return;
		}
		IResolutionContextProvider provider  = new IResolutionContextProvider() {
		
			@Override
			public IResolutionContext getResolutionContext(ElementReference reference,
					ScopeBlock scope) {
				if (scope != null) {
					return ElementReferenceResolver.createResolutionContext(scope);
				}
				return ElementReferenceResolver.createResolutionContext(RuleGrammarUtils.getScope(reference));
			}
		
			@Override
			public IResolutionContext getResolutionContext(
					ElementReference elementReference) {
				return ElementReferenceResolver.createResolutionContext(RuleGrammarUtils.getScope(elementReference));
			}
		};
		
		List<EObject> targetEntities = new ArrayList<EObject>();
		targetEntities.add(entity);
		StringArgumentEntityFinder finder = 
			new StringArgumentEntityFinder(projectName, provider, targetEntities, targetMethodNames, true);
		node.accept(finder);
		List<RulesASTNode> foundArguments = finder.getFoundArguments();
		for (RulesASTNode rulesASTNode : foundArguments) {
			ElementReference reference = (ElementReference) RuleGrammarUtils.getElementReference(RuleGrammarUtils.getMethodCallNode(rulesASTNode));
			Object element = ElementReferenceResolver.resolveElement(reference, ElementReferenceResolver.createResolutionContext(RuleGrammarUtils.getScope(reference)));
			if (element instanceof JavaStaticFunction) {
				SearchUtils.addArgumentMatch((JavaStaticFunction) element, result, container, rulesASTNode);
			}
		}
		List<RulesASTNode> foundLiterals = finder.getFoundLiterals();
		for (RulesASTNode rulesASTNode : foundLiterals) {
			SearchUtils.addLiteralMatch(result, container, rulesASTNode);
		}
	}

	public void processScope(List<EObject> foundDefinitions, List<ElementReference> foundReferences,
			ScopeBlock scope, String nameToFind) {
		if (scope == null) {
			System.out.println("Scope is null, scope not processed");
			return;
		}
		EList<LocalVariableDef> defs = scope.getDefs();
		for (LocalVariableDef localVariableDef : defs) {
			if (localVariableDef.getName().equals(nameToFind)) {
				foundDefinitions.add(localVariableDef);
			}
		}
		EList<ElementReference> refs = scope.getRefs();
		for (ElementReference elementReference : refs) {
			if (elementReference.getName().equals(nameToFind)) {
				foundReferences.add(elementReference);
			} else if (elementReference.isMethod()) {
				// add it to the found references list, so we can later check whether we care about its arguments (i.e. mapper functions)
				foundReferences.add(elementReference);
			}
			ElementReference qualifier = elementReference.getQualifier();
			while (qualifier != null) {
				if (qualifier.getName().equals(nameToFind)) {
					foundReferences.add(qualifier);
				}
				qualifier = qualifier.getQualifier();
			}
		}
		EList<ScopeBlock> childScopeDefs = scope.getChildScopeDefs();
		for (ScopeBlock scopedDef : childScopeDefs) {
			processScope(foundDefinitions, foundReferences, scopedDef, nameToFind);
		}
	}

}
