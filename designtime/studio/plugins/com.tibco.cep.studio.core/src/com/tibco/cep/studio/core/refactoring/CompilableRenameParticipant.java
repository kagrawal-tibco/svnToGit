package com.tibco.cep.studio.core.refactoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;

import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.be.model.functions.impl.JavaStaticFunctionWithXSLT;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.studio.core.builder.StudioBuilderProblemHandler;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.search.RuleSearchParticipant;
import com.tibco.cep.studio.core.search.SearchResult;
import com.tibco.cep.studio.core.validation.ValidationUtils;

public class CompilableRenameParticipant {

	private boolean fMoveRefactor;
	private String fOldName;
	private String fOldPath;
	private RuleRefactoringHelper fHelper;

	public CompilableRenameParticipant(boolean isMoveRefactor, String oldName, String oldPath) {
		this.fMoveRefactor = isMoveRefactor;
		this.fOldName = oldName;
		this.fOldPath = oldPath;
		this.fHelper = new RuleRefactoringHelper(fMoveRefactor, fOldName, fOldPath);
	}

	public boolean processRuleFunction(RuleFunction ruleFunction,
			Object entity, String newFolder, String newName) {
		if (ruleFunction == null) {
			return false;
		}
		boolean changed = processCompilable(ruleFunction, entity, newFolder, newName);
		return changed;
	}

	public boolean processCompilable(Compilable compilable, Object obj,
			String newFolder, String newName) {
		if (compilable == null) {
			return false;
		}
		boolean changed = false;
		Symbols symbols = compilable.getSymbols();
		EList<Symbol> symbolList = symbols.getSymbolList();
		for (Symbol symbol : symbolList) {
			if (obj instanceof Entity) {
				Entity entity = (Entity) obj;
				if (symbol.getType() != null && 
						symbol.getType().equals(entity.getFullPath())) {
					if (newFolder != null) {
						symbol.setType(newFolder+newName);
					} else {
						symbol.setType(entity.getFolder()+newName);
					}
					changed = true;
				}
			} else if (obj instanceof IFolder) {
				IFolder folder = (IFolder) obj;
				if (IndexUtils.startsWithPath(symbol.getType(), folder)) {
					String newType = getNewPath(symbol.getType(), folder, newFolder, newName);
					symbol.setType(newType);
					changed = true;
				}
			}
		}
		if (processCompilableSource(compilable, obj, newFolder, newName)) {
			changed = true;
		}
		return changed;
	}

	public boolean processRule(Rule rule, Object obj,
			String newFolder, String newName) {
		if (rule == null) {
			return false;
		}
		boolean changed = processCompilable(rule, obj, newFolder, newName);
		return changed;
	}

	private boolean processRuleElement(String projectName, RuleElement ruleElement, Object elementToRefactor, String newFolder, String newName, boolean isActionText, Compilable compilable) {

		String nameToFind = getElementName(elementToRefactor);
		List<ElementReference> foundReferences = new ArrayList<ElementReference>();
		List<EObject> foundDefinitions = new ArrayList<EObject>();
		RuleSearchParticipant participant = new RuleSearchParticipant();
		participant.searchRuleElement(elementToRefactor, nameToFind, foundReferences, foundDefinitions, ruleElement);
		
		// filter results
		List<ElementReference> inexactReferences = new ArrayList<ElementReference>();
		List<ElementReference> filteredReferences = new ArrayList<ElementReference>();
		List<EObject> filteredDefinitions = new ArrayList<EObject>();
		for (EObject definition : foundDefinitions) {
			if (participant.isEqual(definition, elementToRefactor)) {
				filteredDefinitions.add(definition);
			}
		}
		SearchResult result = new SearchResult();
		List<Compilable> processedCompilables = new ArrayList<Compilable>();

		for (ElementReference reference : foundReferences) {
			Object element = ElementReferenceResolver.resolveElement(reference, ElementReferenceResolver.createResolutionContext(RuleGrammarUtils.getScope(reference)));
			if (element != null && participant.isEqual(element, elementToRefactor)) {
				filteredReferences.add(reference);
			} else if (element instanceof JavaStaticFunctionWithXSLT) {
				participant.processFunctionFromCompilable(reference, (JavaStaticFunction)element, elementToRefactor, projectName, result, processedCompilables, compilable);
			} else if (element == null) {
				inexactReferences.add(reference);
			}
		}
		
		MultiTextEdit edit = new MultiTextEdit();
		boolean changed = false;
		if (fHelper.processDefinitions(filteredDefinitions, edit, ruleElement, newFolder, newName)) changed = true;
		if (fHelper.processReferences(filteredReferences, edit, ruleElement, newFolder, newName, elementToRefactor)) changed = true;
		if (result.getExactMatches().size() > 0) {
			if (fHelper.processSearchResult(result, edit, ruleElement, newFolder, newName, elementToRefactor)) changed = true;
		}
//		if (processReferences(inexactReferences, edit, ruleElement, newName)) changed = true;
		if (changed) {
			String docContents;
			if (isActionText) {
				docContents = compilable.getActionText();
			} else {
				docContents = compilable.getConditionText();
			}
			IDocument doc = new Document(docContents);
			try {
				edit.apply(doc);
				if (isActionText) {
					compilable.setActionText(doc.get());
				} else {
					compilable.setConditionText(doc.get());
				}
			} catch (MalformedTreeException e) {
				e.printStackTrace();
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		return changed;
	}

	private String getElementName(Object elementToRefactor) {
		if (elementToRefactor instanceof DesignerElement) {
			return ((DesignerElement) elementToRefactor).getName();
		}
		if (elementToRefactor instanceof Entity) {
			return ((Entity) elementToRefactor).getName();
		}
		return "";
	}

	private boolean processCompilableSource(Compilable compilable, Object obj, String newFolder, String newName) {

		boolean changed = false;
		
		try {
			if (compilable == null) return false;
			String actionText = compilable.getActionText();
			String conditionText = compilable.getConditionText();
//			Collection<Symbol> symbolList = compilable.getSymbols().getSymbolMap().values();
			// create Global Variable definitions for Symbols
			List<GlobalVariableDef> gvdList = new ArrayList<GlobalVariableDef>();
			for (Symbol symbol : compilable.getSymbols().getSymbolList()){
				String id = symbol.getIdName();
				String type = symbol.getType();
				if (id == null || type  == null || id.trim().length() == 0 || type.trim().length() == 0) continue;
				int index = type.indexOf('/');
				if (index != -1){
					type = type.replace('/', '.');
					if (type.startsWith(".")){
						type = type.substring(1);						
					}
					GlobalVariableDef gvd = ValidationUtils.createGVDefinition(id, type, -1, -1, false);			
					gvdList.add(gvd);			
				}
			}
			StudioBuilderProblemHandler collector = new StudioBuilderProblemHandler();
			RulesASTNode node = null;
			String projectName = "";
			if (obj instanceof Entity) {
				projectName = ((Entity) obj).getOwnerProjectName();
			} else if (obj instanceof IContainer) {
				projectName = ((IContainer) obj).getProject().getName();
			}

			if (conditionText != null && conditionText.trim().length() != 0){
				node = (RulesASTNode) RulesParserManager.parseConditionsBlockString(compilable.getOwnerProjectName(), conditionText, collector);
				// process all syntax problems 
				if (collector.getErrors() != null && collector.getErrors().size() > 0){
					return false; // TODO : report syntax error, unable to refactor
				}
				if (node != null){
					Object data = node.getData("element");
					if (data instanceof RuleElement){
						RuleElement re = (RuleElement)data;
						re.getGlobalVariables().addAll(gvdList);
						if (re.getName() == null) {
							re.setName(compilable.getName());
						}
						if (processRuleElement(projectName, re, obj, newFolder, newName, false, compilable)) changed = true;
					}
				}
			} 
			collector = new StudioBuilderProblemHandler();
			if (actionText != null && actionText.trim().length() != 0){
				node = (RulesASTNode) RulesParserManager.parseActionBlockString(compilable.getOwnerProjectName(), actionText, collector);
				if (collector.getErrors() != null && collector.getErrors().size() > 0){
					return false; // TODO : report syntax error, unable to refactor
				}
				if (node != null){
					Object data = node.getData("element");
					if (data instanceof RuleElement){
						RuleElement re = (RuleElement)data;
						re.getGlobalVariables().addAll(gvdList);
						if (processRuleElement(projectName, re, obj, newFolder, newName, true, compilable)) changed = true;
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}

		return changed;
	}
	
	protected String getNewPath(String elementPath, IFolder folder, String newFolder, String newName) {
		int offset = 0;
		String initChar = "";
		if (elementPath.startsWith("/")) {
			initChar = "/";
			offset = 1;
		}
		String oldPath = folder.getProjectRelativePath().toString();
		if (newFolder != null) {
			return newFolder + folder.getName() + elementPath.substring(oldPath.length()+offset);
		} else {
			if (folder.getParent() instanceof IFolder) {
				String parentPath = folder.getParent().getProjectRelativePath().toString();
				return initChar + parentPath + "/" + newName + elementPath.substring(oldPath.length()+offset);
			}
			return initChar + newName + elementPath.substring(oldPath.length()+offset);
		}
	}

}
