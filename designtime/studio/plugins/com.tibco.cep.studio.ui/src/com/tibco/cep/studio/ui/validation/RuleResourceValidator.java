package com.tibco.cep.studio.ui.validation;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.tibco.cep.studio.core.builder.StudioBuilderProblemHandler;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.IResolutionContext;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.index.resolution.RuleElementResolutionContext;
import com.tibco.cep.studio.core.index.resolution.SimpleResolutionContext;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.IRulesProblem;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.rules.RulesSemanticASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.core.validation.IResourceValidatorExtension;
import com.tibco.cep.studio.core.validation.SharedElementValidationContext;
import com.tibco.cep.studio.core.validation.ValidationContext;

public class RuleResourceValidator extends DefaultResourceValidator implements IResolutionContextProvider, IResourceValidatorExtension {

	private static final String ATTR_SHARED_PATH = "SharedPath";
	private RuleElement fRuleElement;
	
	public RuleResourceValidator() {
	}

	@Override
	public boolean canContinue() {
		// we can continue to the next rule if there was an error with this one
		return true;
	}

	@Override
	public boolean validate(ValidationContext validationContext) {
		if (validationContext == null) return true;
		IResource resource = validationContext.getResource();
		if (resource == null) return true;
		IFile file = (IFile) resource;
		deleteMarkers(file);
		super.validate(validationContext);
		if (!file.exists()) {
			return true; // file was deleted
		}
		StudioBuilderProblemHandler collector = new StudioBuilderProblemHandler();
		try {
			RulesASTNode node = (RulesASTNode) RulesParserManager.parseRuleFile(file, collector);
			processErrors(file, collector.getErrors());
			if (node == null) {
				return false;
			}
			Object data = node.getData("element");
			if (data instanceof RuleElement ) {
				fRuleElement = (RuleElement) data;
				resolveElementReferences(file, fRuleElement);
				List<IRulesProblem> semanticErrors = checkRuleSemantics(file.getProject().getName(), file, node, fRuleElement);
				reportSemanticErrors(file, semanticErrors);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return true;
	}

	private List<IRulesProblem> checkRuleSemantics(String projectName, IFile file, RulesASTNode rulesAST, RuleElement ruleElement) {
		RulesSemanticASTVisitor visitor = new RulesSemanticASTVisitor(this, projectName, file);
		rulesAST.accept(visitor);
		List<IRulesProblem> semanticErrors = visitor.getSemanticErrors();
		return semanticErrors;
	}

	private void reportSemanticErrors(IFile file, List<IRulesProblem> collectedProblems) {
		for (IRulesProblem problem : collectedProblems) {
			reportProblem(file, problem.getErrorMessage(), problem.getLine(), problem.getOffset(), problem.getLength(), problem.getSeverity());
		}
	}

	private void deleteMarkers(IFile file) {
		try {
			file.deleteMarkers(VALIDATION_MARKER_TYPE, false, IResource.DEPTH_ZERO);
		} catch (CoreException ce) {
		}
	}

	@Override
	public IResolutionContext getResolutionContext(
			ElementReference elementReference) {
		ScopeBlock scope = RuleGrammarUtils.getScope(elementReference);
		SimpleResolutionContext context = new SimpleResolutionContext(scope);
		List<GlobalVariableDef> globalDefs = fRuleElement.getGlobalVariables();
		for (GlobalVariableDef globalVariableDef : globalDefs) {
			context.addGlobalVariable(globalVariableDef);
		}
		return context;
	}

	@Override
	public IResolutionContext getResolutionContext(ElementReference reference,
			ScopeBlock scope) {
		if (fRuleElement != null) {
			return new RuleElementResolutionContext(scope, fRuleElement);
		}
		return null;
	}

	@Override
	public boolean enablesFor(SharedElement element) {
		return (element.getElementType() == ELEMENT_TYPES.RULE 
				|| element.getElementType() == ELEMENT_TYPES.RULE_FUNCTION
				|| element.getElementType() == ELEMENT_TYPES.RULE_TEMPLATE);
	}

	@Override
	public boolean validate(SharedElementValidationContext validationContext) {
		if (validationContext == null) return true;
		IProject project = validationContext.getProject();
		if (project == null) return true;
		SharedElement element = validationContext.getElement();
		deleteMarkers(project, element);
		if (!project.exists()) {
			return true; // project was deleted
		}
		StudioBuilderProblemHandler collector = new StudioBuilderProblemHandler();
		InputStream inputStream = null;
		try {
			inputStream = IndexUtils.getInputStream(element);
			RulesASTNode node = (RulesASTNode) RulesParserManager.parseRuleInputStream(project.getName(), inputStream, collector, false, false);
			processSharedElementErrors(project, element, collector.getErrors());
			if (node == null) {
				return false;
			}
//			Object data = node.getData("element");
			if (element instanceof RuleElement ) {
				fRuleElement = (RuleElement) element;
				resolveElementReferences(project, fRuleElement);
				List<IRulesProblem> semanticErrors = checkRuleSemantics(project.getName(), null, node, fRuleElement);
				reportSemanticErrors(project, element, semanticErrors);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	@Override
	protected void reportUnresolvedReferenceErrors(RuleElement ruleElement,
			IResource file, List<ElementReference> unresolvedReferences) {
		if (!(ruleElement instanceof SharedElement)) {
			super.reportUnresolvedReferenceErrors(ruleElement, file, unresolvedReferences);
			return;
		}
		String elPath = getElementPath((SharedElement) ruleElement);
		for (ElementReference elementReference : unresolvedReferences) {
			int index = elementReference.getOffset();
			int length = elementReference.getLength();
			IMarker marker = addMarker(file, SHARED_ELEMENT_PREFIX + "Unable to resolve "+elementReference.getName(), elPath, -1, index, length, IMarker.SEVERITY_ERROR, SHARED_ELEMENT_VALIDATION_MARKER_TYPE);
			setSharedPathAttr(elPath, marker);
		}
	}

	private void setSharedPathAttr(String elPath, IMarker marker) {
		try {
			marker.setAttribute(ATTR_SHARED_PATH, elPath);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private void reportSemanticErrors(IProject project, SharedElement element,
			List<IRulesProblem> semanticErrors) {
		String elPath = getElementPath(element);
		for (IRulesProblem rulesProblem : semanticErrors) {
			String message = SHARED_ELEMENT_PREFIX + rulesProblem.getErrorMessage();
			IMarker marker = addMarker(project, message, elPath, rulesProblem.getLine(), rulesProblem.getOffset(), rulesProblem.getLength(), rulesProblem.getSeverity(), SHARED_ELEMENT_VALIDATION_MARKER_TYPE);
			setSharedPathAttr(elPath, marker);
		}
	}

	private void processSharedElementErrors(IProject project,
			SharedElement element, List<IRulesProblem> errors) {
		String elPath = getElementPath(element);
		for (IRulesProblem rulesSyntaxProblem : errors) {
			String message = SHARED_ELEMENT_PREFIX + rulesSyntaxProblem.getErrorMessage();
			IMarker marker = addMarker(project, message, elPath, rulesSyntaxProblem.getLine(), rulesSyntaxProblem.getOffset(), rulesSyntaxProblem.getLength(), rulesSyntaxProblem.getSeverity(), SHARED_ELEMENT_VALIDATION_MARKER_TYPE);
			setSharedPathAttr(elPath, marker);
		}
	}

	private void deleteMarkers(IProject project, SharedElement element) {
		try {
			IMarker[] existingMarkers = project.findMarkers(SHARED_ELEMENT_VALIDATION_MARKER_TYPE, false, IResource.DEPTH_INFINITE);
			String elPath = getElementPath(element);
			for (IMarker marker : existingMarkers) {
				String sharedPath = (String) marker.getAttribute(ATTR_SHARED_PATH);
				if (sharedPath != null && sharedPath.equals(elPath)) {
					marker.delete();
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private String getElementPath(SharedElement element) {
		return element.getArchivePath()+"/"+element.getEntryPath() + element.getFileName();
	}

}
