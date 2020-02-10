package com.tibco.cep.studio.core.validation;

import static com.tibco.cep.studio.core.util.CommonUtil.showWarnings;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.tibco.be.parser.tree.NodeType;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.core.builder.StudioBuilderProblemHandler;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.IRulesProblem;
import com.tibco.cep.studio.core.rules.IRulesSourceTypes;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.rules.RulesSemanticASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public class DefaultStateModelResourceValidator extends DefaultResourceValidator{

	protected Set<String> stateGraphPathErrorSet = new HashSet<String>();

	/**
	 * @param resource
	 * @param message
	 * @param location
	 * @param stateGraphPath
	 * @param isTransition
	 * @param severity
	 */
	protected void reportProblem(IResource resource, String message, String location, String stateGraphPath, boolean isTransition, int severity) {
		if (!showWarnings(severity, resource.getFileExtension())) {
			return;
		}
		addMarker(resource, message, location, stateGraphPath, isTransition, -1, -1, -1, severity);
	}
	
	/**
	 * @param resource
	 * @param message
	 * @param location
	 * @param stateGraphPath
	 * @param isTransition
	 * @param lineNumber
	 * @param start
	 * @param length
	 * @param severity
	 */
	protected void reportProblem(IResource resource, String message, String location, String stateGraphPath,  boolean isTransition, int lineNumber, int start, int length,int severity) {
		if (!showWarnings(severity, resource.getFileExtension())) {
			return;
		}
		addMarker(resource, message, location, stateGraphPath, isTransition, lineNumber,start, length, severity);
	}
	
	/**
	 * @param resource
	 * @param message
	 * @param location
	 * @param stateGraphPath
	 * @param lineNumber
	 * @param start
	 * @param length
	 * @param severity
	 */
	private void addMarker(IResource resource, String message, String location, String stateGraphPath,  boolean isTransition, int lineNumber, int start, int length,
			int severity) {
		try {
			IMarker marker = addMarker(resource, message, location, lineNumber, start, length, severity);
			if(stateGraphPath != null){
				int len = (IndexUtils.getFullPath(resource)+"."+resource.getFileExtension()).length();
				String res = stateGraphPath.substring(len);
				if(!isTransition){
					if(res.trim().equalsIgnoreCase("")){
						res = "root state";
					}
					marker.setAttribute(IMarker.LOCATION, res);
				}
				if(isTransition){
					marker.setAttribute(MARKER_STATE_GRAPH_PATH_ATTRIBUTE, location);
				}else{
					marker.setAttribute(MARKER_STATE_GRAPH_PATH_ATTRIBUTE, stateGraphPath);
				}
				marker.setAttribute(MARKER_IS_TRANSITION_STATE_ATTRIBUTE, isTransition);
			}
		} catch (CoreException e) {
		}
	}
	
	/**
	 * @param resource
	 * @param location
	 * @param stateGraphPath
	 * @param isTransition
	 * @param errors
	 * @param isSemantic
	 */
	protected void processErrors(IResource resource, 
			                     String location, 
			                     String stateGraphPath, 
			                     boolean isTransition, 
			                     List<? extends IRulesProblem> errors, 
			                     boolean isSemantic) {
		for (IRulesProblem problem : errors) {
			int index = problem.getOffset();
			int length = problem.getLength();
			int severity = problem.getSeverity();
//			reportProblem(resource, problem.getErrorMessage(), problem.getLine(), index, length, IMarker.SEVERITY_ERROR);
			reportProblem(resource, problem.getErrorMessage(), location, stateGraphPath, isTransition,problem.getLine(), index, length, severity);

		}
	}
	
	/**
	 * @param resource
	 * @param location
	 * @param stateGraphPath
	 * @param errors
	 */
	protected void processErrors(IResource resource, String location, String stateGraphPath, boolean isTransition, List<IRulesProblem> errors) {
		for (IRulesProblem problem : errors) {
			int index = problem.getOffset();
			int length = problem.getLength();
			reportProblem(resource, problem.getErrorMessage(), location, stateGraphPath, isTransition,problem.getLine(), index, length, problem.getSeverity());
		}
	}
	
	/**
	 * @param resource
	 * @param location
	 * @param stateGraphPath
	 * @param isTransition
	 * @param compilable
	 * @return
	 */
	protected boolean validateCompilable(final IResource resource, String location, String stateGraphPath, boolean isTransition,Compilable compilable) {
		return validateCompilable(resource, location, stateGraphPath, isTransition, compilable, null);
	}
	
	/**
	 * @param resource
	 * @param location
	 * @param stateGraphPath
	 * @param isTransition
	 * @param compilable
	 * @param returnType
	 * @return
	 */
	protected boolean validateCompilable(final IResource resource, String location, String stateGraphPath, boolean isTransition,Compilable compilable, NodeType returnType) {
		try {
			if (compilable == null) return true;
			String actionText = compilable.getActionText();
			String conditionText = compilable.getConditionText();
//			Collection<Symbol> symbolList = compilable.getSymbols().getSymbolMap().values();
			// create Global Variable definitions for Symbols
			final List<GlobalVariableDef> gvdList = new ArrayList<GlobalVariableDef>();
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
			ResourceResolutionContextProvider provider = new ResourceResolutionContextProvider(gvdList, returnType);
			if (conditionText != null && conditionText.trim().length() != 0){
				node = (RulesASTNode) RulesParserManager.parseConditionsBlockString(compilable.getOwnerProjectName(), conditionText, collector);
				// process all syntax problems 
				if (collector.getErrors() != null && collector.getErrors().size() > 0){
					if(isTransition) stateGraphPathErrorSet.add(stateGraphPath);
					processErrors(resource, location, stateGraphPath, isTransition,collector.getErrors());
				}
				if (node != null){
					Object data = node.getData("element");
					if (data instanceof RuleElement && true){
						RuleElement re = (RuleElement)data;
						re.getGlobalVariables().addAll(gvdList);
//						resolveElementReferences(resource, re);
						resolveElementReferences(resource, location, stateGraphPath, isTransition, re);
//						checkRuleSemantics(resource, node, provider, IRulesSourceTypes.CONDITION_SOURCE);
						checkRuleSemantics(resource, node, provider, location, stateGraphPath, isTransition, IRulesSourceTypes.CONDITION_SOURCE);
					}
				}
			} 
			collector = new StudioBuilderProblemHandler();
			if (actionText != null && actionText.trim().length() != 0){
				node = (RulesASTNode) RulesParserManager.parseActionBlockString(compilable.getOwnerProjectName(), actionText, collector);
				if (collector.getErrors() != null && collector.getErrors().size() > 0){
					if(isTransition) stateGraphPathErrorSet.add(stateGraphPath);
					processErrors(resource, location, stateGraphPath, isTransition,collector.getErrors());
				}
				if (node != null){
					Object data = node.getData("element");
					if (data instanceof RuleElement && true){
						RuleElement re = (RuleElement)data;
						re.getGlobalVariables().addAll(gvdList);
//						resolveElementReferences(resource, re);
						resolveElementReferences(resource, location, stateGraphPath, isTransition, re);
//						checkRuleSemantics(resource, node, provider, IRulesSourceTypes.ACTION_SOURCE);
						checkRuleSemantics(resource, node, provider, location, stateGraphPath, isTransition, IRulesSourceTypes.ACTION_SOURCE);

					}
				}
			} else {
				// check return type
				if (returnType != null && !returnType.isVoid()) {
					reportProblem(resource, "Rule must return a value of type '"+returnType.getDisplayName()+"'", location, stateGraphPath, false, IMarker.SEVERITY_ERROR);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}

		return true;
	}
	
	/**
	 * @param file
	 * @param rulesAST
	 * @param provider
	 * @param location
	 * @param stateGraphPath
	 * @param isTransition
	 * @param sourceType
	 */
	private void checkRuleSemantics(IResource file, 
			                        RulesASTNode rulesAST, 
			                        IResolutionContextProvider provider, 
			                        String location, 
			                        String stateGraphPath, 
			                        boolean isTransition,
			                        int sourceType) {
		try {
			RulesSemanticASTVisitor visitor = new RulesSemanticASTVisitor(provider, file.getProject().getName(), sourceType);
			rulesAST.accept(visitor);
			List<IRulesProblem> semanticErrors = visitor.getSemanticErrors();
			if(isTransition && semanticErrors.size() >0) {
				stateGraphPathErrorSet.add(stateGraphPath);
			}
//			processErrors(file, semanticErrors);
			processErrors(file, location, stateGraphPath, isTransition, semanticErrors, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * For State Modeler
	 * @param file
	 * @param location
	 * @param stateGraphPath
	 * @param isTransition
	 * @param ruleElement
	 */
	protected void resolveElementReferences(IResource file, 
			                                String location, 
			                                String stateGraphPath, 
			                                boolean isTransition,
			                                RuleElement ruleElement) {
		Date start = new Date();
		ScopeBlock scope = ruleElement.getScope();
		List<ElementReference> unresolvedReferences = new ArrayList<ElementReference>();
		resolveElementReferencesInScope(scope, unresolvedReferences, ruleElement);
		Date end = new Date();
		reportUnresolvedReferenceErrors(file, location, stateGraphPath, isTransition, unresolvedReferences);
		if(isTransition && unresolvedReferences.size() >0) {
			stateGraphPathErrorSet.add(stateGraphPath);
		}
	}
	
	/**
	 * @param file
	 * @param location
	 * @param stateGraphPath
	 * @param isTransition
	 * @param unresolvedReferences
	 */
	protected void reportUnresolvedReferenceErrors(IResource file, 
			                                       String location, 
			                                       String stateGraphPath, 
			                                       boolean isTransition,
			                                       List<ElementReference> unresolvedReferences) {
		for (ElementReference elementReference : unresolvedReferences) {
			int index = elementReference.getOffset();
			int length = elementReference.getLength();
			reportProblem(file, "Unable to resolve "+elementReference.getName(), location, stateGraphPath, isTransition, -1, index, length, IMarker.SEVERITY_ERROR);
		}
	}
	
	
}
