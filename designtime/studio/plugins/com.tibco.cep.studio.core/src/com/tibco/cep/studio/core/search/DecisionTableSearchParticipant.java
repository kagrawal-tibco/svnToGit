package com.tibco.cep.studio.core.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.be.model.functions.impl.JavaStaticFunctionWithXSLT;
import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.search.ElementMatch;
import com.tibco.cep.studio.core.index.model.search.SearchFactory;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public class DecisionTableSearchParticipant implements ISourceSearchParticipant {

	protected RuleSearchHelper fHelper;

	public DecisionTableSearchParticipant() {
		super();
		this.fHelper = new RuleSearchHelper();
	}

	@Override
	public SearchResult searchSource(Object resolvedElement,
			String projectName, String nameToFind, IProgressMonitor monitor) {
		return search(resolvedElement, projectName, nameToFind, monitor);
	}

	@Override
	public SearchResult search(Object resolvedElement, String projectName,
			String nameToFind, IProgressMonitor monitor) {
		
		SearchResult result = new SearchResult();
		List<DesignerElement> dtElements = IndexUtils.getAllElements(projectName, new ELEMENT_TYPES[] { ELEMENT_TYPES.DECISION_TABLE });
		monitor.beginTask("Searching index...", dtElements.size()*3);
		List<ElementReference> foundReferences = new ArrayList<ElementReference>();
		List<EObject> foundDefinitions = new ArrayList<EObject>();
		for (DesignerElement desElement : dtElements) {
			DecisionTableElement tableElement = (DecisionTableElement) desElement;
			searchTableElement(projectName, resolvedElement, nameToFind, result,
					tableElement);
			monitor.worked(1);
		}
		monitor.subTask("Filtering definitions");
		for (EObject definition : foundDefinitions) {
			if (isEqual(definition, resolvedElement)) {
				result.addExactMatch(definition);
			}
			monitor.worked(1);
		}
		monitor.subTask("Filtering references");
		List<IFile> processedFiles = new ArrayList<IFile>();
		for (ElementReference reference : foundReferences) {
			Object element = ElementReferenceResolver.resolveElement(reference, ElementReferenceResolver.createResolutionContext(RuleGrammarUtils.getScope(reference)));
			if (element != null && isEqual(element, resolvedElement)) {
				result.addExactMatch(reference);
			} else if (element instanceof JavaStaticFunctionWithXSLT) {
				processFunction(reference, (JavaStaticFunction)element, resolvedElement, projectName, result, processedFiles);
			} else if (element == null) {
				if (resolvedElement instanceof RuleElement) {
					if (((RuleElement) resolvedElement).getScope() != null && ((RuleElement) resolvedElement).getScope().getDefinitionRef() == reference) {
						result.addExactMatch(reference);
					} else {
						result.addInexactMatch(reference);
					}
				} else if (reference.getName().equals(nameToFind)){
					result.addInexactMatch(reference);
				}
			}
			monitor.worked(1);
		}
		return result;
	}

	public void processFunctionFromCompilable(ElementReference reference, JavaStaticFunction element,
			Object resolvedElement, String projectName, SearchResult result, List<Compilable> processedCompilables, Compilable compilable) {
		
		if (resolvedElement instanceof EntityElement) {
			resolvedElement = ((EntityElement) resolvedElement).getEntity();
		}
		if (!(resolvedElement instanceof Entity)) {
			return;
		}
		Entity entity = (Entity) resolvedElement;
		DesignerElement variableContext = IndexUtils.getVariableContext(reference);
		if (!(variableContext instanceof RuleElement)) {
			return;
		}
		RuleElement ruleElement = (RuleElement) variableContext;
		if (processedCompilables.contains(compilable)) {
			return;
		}
		processedCompilables.add(compilable);
		
		if (compilable.getConditionText() != null && compilable.getConditionText().length() > 0) {
			RulesASTNode node = (RulesASTNode) RulesParserManager.parseConditionsBlockString(projectName, compilable.getConditionText(), null);
			fHelper.processFunctionsFromNode(projectName, result, entity,
					ruleElement, node);
		}

		if (compilable.getActionText() != null && compilable.getActionText().length() > 0) {
			RulesASTNode node = (RulesASTNode) RulesParserManager.parseActionBlockString(projectName, compilable.getActionText(), null);
			fHelper.processFunctionsFromNode(projectName, result, entity,
					ruleElement, node);
		}
		
	}
	
	public void processFunction(ElementReference reference, JavaStaticFunction element,
			Object resolvedElement, String projectName, SearchResult result, List<IFile> processedFiles) {
		
		if (resolvedElement instanceof EntityElement) {
			resolvedElement = ((EntityElement) resolvedElement).getEntity();
		}
		if (!(resolvedElement instanceof Entity)) {
			return;
		}
		Entity entity = (Entity) resolvedElement;
		DesignerElement variableContext = IndexUtils.getVariableContext(reference);
		if (!(variableContext instanceof RuleElement)) {
			return;
		}
		RuleElement ruleElement = (RuleElement) variableContext;
		IFile file = IndexUtils.getFile(projectName, ruleElement);
		if (file == null || processedFiles.contains(file) || !file.exists()) {
			return;
		}
		processedFiles.add(file);
		RulesASTNode node = (RulesASTNode) RulesParserManager.parseRuleFile(file);
		
		fHelper.processFunctionsFromNode(projectName, result, entity,
				ruleElement, node);
	}

	public void searchTableElement(String projectName, Object resolvedElement, String nameToFind,
			SearchResult result, DecisionTableElement tableElement) {
		if (tableElement == null) {
			return;
		}
		Table table = (Table) tableElement.getImplementation();			
		if(nameToFind.equalsIgnoreCase(table.getName())){
			ElementMatch match = createElementMatch(DtmodelPackage.TABLE__ARGUMENT, table.eClass(), tableElement);
			result.addExactMatch(match);
		} else{
			//RuleElement ruleFunctionNewElement = IndexUtils.getRuleElement(projectName, table.getImplements(), ELEMENT_TYPES.RULE_FUNCTION);
			int idx = table.getImplements().lastIndexOf('/');
			idx++;
			if(nameToFind.equals(table.getImplements().substring(idx))){
				ElementMatch match = createElementMatch(DtmodelPackage.TABLE__ARGUMENT, table.eClass(), tableElement);
				result.addExactMatch(match);
			}

		}
			
		// TODO : process individual rules to check for references within each cell
		//		EList<TableRule> rule = table.getDecisionTable().getRule();
		//		EList<TableRule> rule2 = table.getExceptionTable().getRule();
		
	}
	
	protected ElementMatch createElementMatch(int featureId,
			EClass class1, EObject matchedElement) {
		ElementMatch match = SearchFactory.eINSTANCE.createElementMatch();
		if (featureId == DEFINITION_FEATURE) {
			match.setFeature(matchedElement);
		} else {
			EStructuralFeature feature = class1.getEStructuralFeature(featureId);
			match.setFeature(feature);
		}
		match.setMatchedElement(matchedElement);
		return match;
	}

//	private void createAndAddMatch(IFile file, String projectName,
//			String label, String pathToEntity, SearchResult result) {
//		NonEntityMatch match = SearchFactory.eINSTANCE.createNonEntityMatch();
//		match.setProjectName(projectName);
//		IPath path = file.getFullPath().removeFirstSegments(1);
//		match.setFilePath(path.toString());
//		match.setMatch(pathToEntity);
//		ElementMatch elementMatch = SearchFactory.eINSTANCE.createElementMatch();
//		elementMatch.setMatchedElement(match);
//		elementMatch.setLabel(label);
//		result.addExactMatch(elementMatch);
//	}

	public boolean isEqual(Object element, Object resolvedElement) {
		// need to fill this out, or add "equals" method to all possible comparables
		if (element == null) {
			return false;
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
		if (resolvedElement instanceof EntityElement && (element instanceof Entity || element instanceof Argument)) {
			resolvedElement = ((EntityElement)resolvedElement).getEntity();
		}
		if (element instanceof EntityElement && resolvedElement instanceof Entity) {
			element = ((EntityElement)element).getEntity();
		}
		if (element instanceof Entity && resolvedElement instanceof Entity) {
			return IndexUtils.areEqual((Entity)element, (Entity)resolvedElement);
		}
		if (element instanceof RuleElement && resolvedElement instanceof Compilable) {
			return ((RuleElement) element).getRule() == resolvedElement;
		}
		if (resolvedElement instanceof RuleElement && element instanceof Compilable) {
			return ((RuleElement) resolvedElement).getRule() == element;
		}
		if (element instanceof ElementContainer
				&& resolvedElement instanceof ElementContainer) {
			ElementContainer folder1 = (ElementContainer) element;
			ElementContainer folder2 = (ElementContainer) resolvedElement;
			if (folder1.getName().equals(folder2.getName())
					&& areParentsEqual(folder1, folder2)) {
				return true;
			}
		}
		if (element instanceof Argument && resolvedElement instanceof Entity) {
			String argPath = ((Argument) element).getProperty().getPath();
			String entityPath = ((Entity) resolvedElement).getFullPath();
			return entityPath.equals(argPath);
		}
		return element.equals(resolvedElement);
	}

	private boolean areParentsEqual(ElementContainer folder1,
			ElementContainer folder2) {
		if (folder1.eContainer() == null && folder2.eContainer() == null) {
			return true;
		}
		return isEqual(folder1.eContainer(), folder2.eContainer());
	}

}
