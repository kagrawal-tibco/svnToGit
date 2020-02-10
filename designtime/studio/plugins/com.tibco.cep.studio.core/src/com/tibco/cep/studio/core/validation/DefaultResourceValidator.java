package com.tibco.cep.studio.core.validation;

import static com.tibco.cep.studio.core.util.CommonUtil.isNonEntityResource;
import static com.tibco.cep.studio.core.util.CommonUtil.showWarnings;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;

import com.tibco.be.model.util.EntityNameHelper;
import com.tibco.be.parser.tree.NodeType;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.core.builder.StudioBuilderProblemHandler;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.IResolutionContext;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.index.resolution.RuleElementResolutionContext;
import com.tibco.cep.studio.core.index.resolution.SimpleResolutionContext;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;
import com.tibco.cep.studio.core.rules.IResolutionContextProviderExtension;
import com.tibco.cep.studio.core.rules.IRulesProblem;
import com.tibco.cep.studio.core.rules.IRulesSourceTypes;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.rules.RulesSemanticASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.util.Messages;

public class DefaultResourceValidator implements IResourceValidator {

	protected class ResourceResolutionContextProvider implements IResolutionContextProvider, IResolutionContextProviderExtension {
		
		private List<GlobalVariableDef> gvdList;
		private NodeType returnType;

		public ResourceResolutionContextProvider(List<GlobalVariableDef> gvdList, NodeType returnType) {
			this.gvdList = gvdList;
			this.returnType = returnType;
		}

		@Override
		public IResolutionContext getResolutionContext(ElementReference reference,
				ScopeBlock scope) {

			SimpleResolutionContext context = new SimpleResolutionContext(scope);
			for (GlobalVariableDef globalVariableDef : gvdList) {
				context.addGlobalVariable(globalVariableDef);
			}
			return context;
		}
	
		@Override
		public IResolutionContext getResolutionContext(
				ElementReference elementReference) {
			ScopeBlock scope = RuleGrammarUtils.getScope(elementReference);
			SimpleResolutionContext context = new SimpleResolutionContext(scope);
			for (GlobalVariableDef globalVariableDef : gvdList) {
				context.addGlobalVariable(globalVariableDef);
			}
			return context;
		}

		@Override
		public NodeType getReturnType() {
			return returnType != null ? returnType : NodeType.VOID;
		}
		
	};

	public boolean canContinue() {
		return true;
	}


	public boolean enablesFor(IResource resource) {
		return true;
	}

	/**
	 * it does some basic validation for the name of the resources and the folder
	 */

	public boolean validate(ValidationContext validationContext) {		
		IResource resource = validationContext.getResource();
		if (resource == null) return true;		
		deleteMarkers(resource);
		int modificationType = validationContext.getModificationType();		
		if (IResourceDelta.REMOVED == modificationType) {
			// if resource is deleted then return , there is no need to perform basic validation for Folder , name , duplicate name etc
			return true;
		}
		List<IValidationError> vldErrorList = new ArrayList<IValidationError>();
		IValidationError vldError = null;
		String resourceNameWithExt = resource.getName();
		int index = resourceNameWithExt.lastIndexOf('.');
		String resName = resourceNameWithExt.substring(0, index);
		// check if the resource name is valid
		boolean containsInvalidChar = EntityNameHelper.containsInvalidNameChars(resName, false);
		List<Object> args = new ArrayList<Object>();
		args.add(resName);
		if (containsInvalidChar && ! isNonEntityResource(resource)){
			String msg = Messages.getString("Resource.name.hasInvalidChar");
			String  formattedMsg = MessageFormat.format(msg, args.toArray());
			vldError = new ValidationError(resource,formattedMsg);
			vldErrorList.add(vldError);
		}
			
		boolean isKeyWord = EntityNameHelper.isKeyword(resName);
		if (isKeyWord && !isNonEntityResource(resource)){
			String msg = Messages.getString("Resource.name.isAKeyWord");
			String  formattedMsg = MessageFormat.format(msg, args.toArray());
			vldError = new ValidationError(resource,formattedMsg);
			vldErrorList.add(vldError);
		}
		boolean isValidEntityIdentifier = EntityNameHelper.isValidEntityIdentifier(resName);
		if (!isValidEntityIdentifier && !isNonEntityResource(resource)){
			String msg = Messages.getString("Resource.name.isAKeyWord");
			String  formattedMsg = MessageFormat.format(msg, args.toArray());
			vldError = new ValidationError(resource,formattedMsg);
			vldErrorList.add(vldError);
		}
		
		// check if folder is right
		IResource parent = resource.getParent();
		if (parent instanceof IFolder){
			IFolder folder = (IFolder)parent;
			// remove the first segment, which is the project name, as it can contain '.' chars, etc, and not validate
			String folderPath = folder.getFullPath().removeFirstSegments(1).toOSString();
			folderPath = folderPath.replace(File.separatorChar, '/');
			if (!folderPath.startsWith("/")) {
				folderPath = "/"+folderPath;
			}
			boolean allowKeyWords = false;
			boolean isLinkedResource = folder.isLinked(IResource.CHECK_ANCESTORS);
			boolean isPathValid = !EntityNameHelper.containsInvalidNameChars(folderPath, true, false,isLinkedResource);
			String keywordIdentifier = null;
			if (isPathValid) {
				keywordIdentifier = EntityNameHelper.isValidEntityPath(folderPath, allowKeyWords,isLinkedResource);
				isPathValid = (keywordIdentifier == null);
			}
			
			args.clear();
			args.add(resource.getProjectRelativePath().toString());
			if (!isPathValid && !isNonEntityResource(resource)){
				String msg = Messages.getString("Resource.folder.bad");
				String formattedMsg = MessageFormat.format(msg, args.toArray());
				if (keywordIdentifier != null && !keywordIdentifier.isEmpty()) formattedMsg += (" '"+keywordIdentifier+"'");
				vldError = new ValidationError(resource, formattedMsg);
				vldErrorList.add(vldError);
			}
			
			// check for duplicate elements in referenced projects (i.e. project libraries)
			if (isDuplicateResource(resource)) {
				String msg = Messages.getString("Resource.element.duplicate");
				String  formattedMsg = MessageFormat.format(msg, args.toArray());
				vldError = new ValidationError(resource,formattedMsg);
				vldError.setWarning(true);
				vldErrorList.add(vldError);
			}
			
			
			// check for duplicate resources inside a folder: Not Required.
//			try {
//				boolean foundOnce = false;
//				boolean isDuplicate = false;
//				//if (IResourceDelta.REMOVED != modificationType){
//					IResource[] members = folder.members();
//					for (IResource res : members){
//						if (res instanceof IFolder) continue;
//						
//						//following check for not to check duplicate validate for test data files  
//						if(res instanceof IFile && CommonUtil.getTestDataExtensions().contains(res.getFileExtension()) ) continue;
//						
//						String name = res.getName();
//						index = name.indexOf('.');
//						if(index != -1){
//							name = name.substring(0,index);							
//						}
//						if (resName.equals(name)){
//							if (!foundOnce){
//								foundOnce = true;
//							} else {
//								isDuplicate = true;
//								break;
//							}
//						}
//					}
//					if (isDuplicate){
//						// Resource with the same name already exists
//						args.clear();
//						args.add(resourceNameWithExt);
//						args.add(folderPath);
//						String msg = Messages.getString("Resource.name.duplicate");
//						String  formattedMsg = MessageFormat.format(msg, args.toArray());
//						vldError = new ValidationError(resource,formattedMsg);
//						vldErrorList.add(vldError);
//					}
//			//	}
//			} catch (CoreException ce){
//				ce.printStackTrace();
//			}
			
		}
		// post all problems
		for (IValidationError error : vldErrorList){
			int severity = error.isWarning() ? IMarker.SEVERITY_WARNING : IMarker.SEVERITY_ERROR;
			reportProblem(resource, error.getMessage() , severity);
		}
		
		return true;
	}


	private boolean isDuplicateResource(IResource resource) {
		DesignerProject index = IndexUtils.getIndex(resource);
		EList<DesignerProject> refProjects = index.getReferencedProjects();
		IPath path = resource.getProjectRelativePath().removeFileExtension();
		for (int i=0; i<refProjects.size(); i++) {
			DesignerProject designerProject = refProjects.get(i);
			if (isDuplicateResource(path, designerProject)) {
				return true;
			}
		}
		return false;
	}


	private boolean isDuplicateResource(IPath path,
			ElementContainer container) {
		if (path.segmentCount() > 0) {
			String segment = path.segment(0);
			EList<DesignerElement> entries = container.getEntries();
			for (int i = 0; i < entries.size(); i++) {
				DesignerElement designerElement = entries.get(i);
				if (path.segmentCount() == 1 && segment.equals(designerElement.getName())) {
					return true;
				}
				if (designerElement instanceof ElementContainer && segment.equals(designerElement.getName())) {
					return isDuplicateResource(path.removeFirstSegments(1), (ElementContainer) designerElement);
				}
			}
		}
		return false;
	}


	protected void reportProblem(IResource resource, String message, int lineNumber, int start, int length, int severity) {
		if (!showWarnings(severity, resource.getFileExtension())) {
			return;
		}
		addMarker(resource, message,null, lineNumber, start, length, severity);
	}

	protected void reportProblem(IResource resource, String message) {
		addMarker(resource, message, null, -1, -1, -1, IMarker.SEVERITY_ERROR);
	}
	
	protected void reportProblem(IResource resource, String message, int severity) {
		if (!showWarnings(severity, resource.getFileExtension())) {
			return;
		}
		addMarker(resource, message, null, -1, -1, -1, severity);
	}
	
	protected void reportProblem(IResource resource, String message, int severity, String type) {
		if (!showWarnings(severity, resource.getFileExtension())) {
			return;
		}
		addMarker(resource, message, null, -1, -1, -1, severity, type);
	}
	
	public void reportProblem(IResource resource, String message, int severity, String type, boolean status) {
		if (!showWarnings(severity, resource.getFileExtension())) {
			return;
		}
		addMarker(resource, message, null, -1, -1, -1, severity, type);
	}
	
	public void reportProblem(IResource resource, String message, int lineNo, int severity, String type, boolean status) {
		if (!showWarnings(severity, resource.getFileExtension())) {
			return;
		}
		addMarker(resource, message, null, lineNo, -1, -1, severity, type);
	}
	
	protected void reportProblem(IResource resource, String message, String location, int severity) {
		if (!showWarnings(severity, resource.getFileExtension())) {
			return;
		}
		addMarker(resource, message, location, -1, -1, -1, severity);
	}
	
	protected void reportProblem(IResource resource, String message,String location, int severity, String type) {
		if (!showWarnings(severity, resource.getFileExtension())) {
			return;
		}
		addMarker(resource, message, location, -1, -1, -1, severity, type);
	}
	
	/**
	 * @param resource
	 * @param message
	 * @param location
	 * @param lineNumber
	 * @param start
	 * @param length
	 * @param severity
	 * @return
	 */
	//FIXME we should delegate to com.tibco.cep.studio.core.validation.DefaultResourceValidator.addMarker(IResource, String, String, int, int, int, int, String, Map<String, String>) - Anand 10/04/2010
	protected IMarker addMarker(IResource resource, String message, String location, int lineNumber, int start, int length,
			int severity) {
		try {
			IMarker marker = resource.createMarker(VALIDATION_MARKER_TYPE);
			Map attributes = new HashMap();
			attributes.put(IMarker.MESSAGE, message);
			attributes.put(IMarker.SEVERITY, severity);
			if (lineNumber >= 0) {
				attributes.put(IMarker.LINE_NUMBER, lineNumber);
			}
			if (start >= 0) {
				attributes.put(IMarker.CHAR_START, start);
				if (length <= 0) {
					length = 1;
				}
				attributes.put(IMarker.CHAR_END, start+length);
			}
			if(location != null){
				attributes.put(IMarker.LOCATION, location);
			}
			marker.setAttributes(attributes);
			return marker;
		} catch (CoreException e) {
		}
		return null;
	}
	
	/**
	 * @param resource
	 * @param message
	 * @param location
	 * @param lineNumber
	 * @param start
	 * @param length
	 * @param severity
	 * @return
	 */
	//FIXME we should delegate to com.tibco.cep.studio.core.validation.DefaultResourceValidator.addMarker(IResource, String, String, int, int, int, int, String, Map<String, String>) - Anand 10/04/2010
	protected IMarker addMarker(IResource resource, String message, String location, int lineNumber, int start, int length,
			int severity, String type) {
		try {
			IMarker marker = resource.createMarker(type);
			Map attributes = new HashMap();
			attributes.put(IMarker.MESSAGE, message);
			attributes.put(IMarker.SEVERITY, severity);
			if (lineNumber >= 0) {
				attributes.put(IMarker.LINE_NUMBER, lineNumber);
			}
			if (start >= 0) {
				attributes.put(IMarker.CHAR_START, start);
				if (length <= 0) {
					length = 1;
				}
				attributes.put(IMarker.CHAR_END, start+length);
			}
			if(location != null){
				attributes.put(IMarker.LOCATION, location);
			}
			marker.setAttributes(attributes);
			return marker;
		} catch (CoreException e) {
		}
		return null;
	}
	
	//Added to fix BE-8264 - Anand 10/04/2010
	protected IMarker addMarker(IResource resource, String message, String location, int lineNumber, int start, int length,
			int severity, String type, Map<String,Object> customAttributes) {
		try {
			IMarker marker = resource.createMarker(type);
			Map attributes = new HashMap();
			attributes.put(IMarker.MESSAGE, message);
			attributes.put(IMarker.SEVERITY, severity);
			if (lineNumber >= 0) {
				attributes.put(IMarker.LINE_NUMBER, lineNumber);
			}
			if (start >= 0) {
				attributes.put(IMarker.CHAR_START, start);
				if (length <= 0) {
					length = 1;
				}
				attributes.put(IMarker.CHAR_END, start+length);
			}
			if(location != null){
				attributes.put(IMarker.LOCATION, location);
			}
			if (customAttributes != null && customAttributes.isEmpty() == false) {
				for (Map.Entry<String, Object> entry : customAttributes.entrySet()) {
					attributes.put(entry.getKey(), entry.getValue());
				}
			}
			marker.setAttributes(attributes);
			return marker;
		} catch (CoreException e) {
		}
		return null;
	}
	
	
	/**
	 * returns Model Object for an IResource
	 * @param resource
	 * @return
	 */
	protected DesignerElement getModelObject(IResource resource){
		String resourcePath = IndexUtils.getFullPath(resource);
		DesignerElement designerElement = 
			IndexUtils.getElement(resource.getProject().getName(), resourcePath);
		return designerElement;
		
	}
	/**
	 * deletes Validation Error Markers on Resource
	 * @param resource
	 */
	protected void deleteMarkers(IResource resource) {
		if (resource == null) {
			return;
		}
		try {
			resource.deleteMarkers(VALIDATION_MARKER_TYPE, true, IResource.DEPTH_ZERO);
		} catch (CoreException ce) {
		}
	}
	
	/**
	 * deletes Validation Error Markers on Resource
	 * @param resource
	 * @param type
	 */
	protected void deleteMarkers(IResource resource, String type) {
		if (resource == null) {
			return;
		}
		try {
			resource.deleteMarkers(type, false, IResource.DEPTH_ZERO);
		} catch (CoreException ce) {
		}
	}
	
	//Added to fix BE-8264 - Anand 10/04/2010
	protected void deleteMarkers(IResource resource, String type, Map<String,String> customAttributes) {
		IMarker[] markers = findMarkers(resource, type, customAttributes);
		for (IMarker marker : markers) {
			try {
				marker.delete();
			} catch (CoreException e) {
			}
		}
	}
	
	//Added to fix BE-8264 - Anand 10/04/2010
	protected IMarker[] findMarkers(IResource resource, String type, Map<String,String> customAttributes){
		if (resource == null) {
			return new IMarker[0];
		}
		try {
			IMarker[] markers = resource.findMarkers(type, false, IResource.DEPTH_ZERO);
			if (customAttributes == null || customAttributes.isEmpty() == true) {
				return markers;
			}
			List<IMarker> markerList = new ArrayList<IMarker>();
			for (IMarker marker : markers) {
				int matched = customAttributes.size();
				for (Map.Entry<String, String> entry : customAttributes.entrySet()) {
					if (entry.getValue().equals(marker.getAttribute(entry.getKey())) == true) {
						matched--;
					}
				}
				if (matched == 0) {
					markerList.add(marker);
				}
			}
			return markerList.toArray(new IMarker[markerList.size()]);
		} catch (CoreException e) {
			//do nothing
			return new IMarker[0];
		}		
	}
	
	/**
	 * Processes the Error for Compilable
	 * @param compilable
	 * @param resource
	 * @param projectName
	 */
	protected void processRuleError(Compilable compilable , IResource resource , String projectName){
		if (compilable == null) return;
		String condText = compilable.getConditionText();
		String actText = compilable.getActionText();
		StudioBuilderProblemHandler collector = new StudioBuilderProblemHandler();
		try {
			RulesASTNode node = null;
			if (condText != null && !condText.trim().equals("")){
				node = (RulesASTNode) RulesParserManager.parseConditionsBlockString(projectName, condText, collector);
				processErrors(resource, collector.getErrors());
			}
			
			if (actText != null && !actText.trim().equals("")){
				node = (RulesASTNode) RulesParserManager.parseActionBlockString(projectName, actText, collector);			
				processErrors(resource, collector.getErrors());
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	protected void processErrors(IResource resource, List<? extends IRulesProblem> errors) {
		for (IRulesProblem problem : errors) {
			int index = problem.getOffset();
			int length = problem.getLength();
			reportProblem(resource, problem.getErrorMessage(), problem.getLine(), index, length, IMarker.SEVERITY_ERROR);
		}
	}
	
	
	
	private void checkRuleSemantics(IResource file, RulesASTNode rulesAST, IResolutionContextProvider provider, int sourceType) {
		try {
			RulesSemanticASTVisitor visitor = new RulesSemanticASTVisitor(provider, file.getProject().getName(), sourceType);
			rulesAST.accept(visitor);
			List<IRulesProblem> semanticErrors = visitor.getSemanticErrors();
			processErrors(file, semanticErrors);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	/**
	 * validates a Rule Element
	 * @param resource
	 * @param compilable
	 * @return
	 * @throws Exception
	 */
	protected boolean validateCompilable(final IResource resource , final Compilable compilable) {
		try {
			if (compilable == null) return true;
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
			ResourceResolutionContextProvider provider = new ResourceResolutionContextProvider(gvdList, NodeType.VOID);
			StudioBuilderProblemHandler collector = new StudioBuilderProblemHandler();
			RulesASTNode node = null;
			if (conditionText != null && conditionText.trim().length() != 0){
				node = (RulesASTNode) RulesParserManager.parseConditionsBlockString(compilable.getOwnerProjectName(), conditionText, collector);
				// process all syntax problems 
				if (collector.getErrors() != null && collector.getErrors().size() > 0){
					processErrors(resource, collector.getErrors());
				}
				if (node != null){
					Object data = node.getData("element");
					if (data instanceof RuleElement && true){
						RuleElement re = (RuleElement)data;
						re.getGlobalVariables().addAll(gvdList);
						resolveElementReferences(resource, re);
						checkRuleSemantics(resource, node, provider, IRulesSourceTypes.CONDITION_SOURCE);
					}
				}
			} 
			collector = new StudioBuilderProblemHandler();
			if (actionText != null && actionText.trim().length() != 0){
				node = (RulesASTNode) RulesParserManager.parseActionBlockString(compilable.getOwnerProjectName(), actionText, collector);
				if (collector.getErrors() != null && collector.getErrors().size() > 0){
					processErrors(resource, collector.getErrors());
				}
				if (node != null){
					Object data = node.getData("element");
					if (data instanceof RuleElement && true){
						RuleElement re = (RuleElement)data;
						re.getGlobalVariables().addAll(gvdList);
						resolveElementReferences(resource, re);
						checkRuleSemantics(resource, node, provider, IRulesSourceTypes.ACTION_SOURCE);
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 
	 * @param file
	 * @param ruleElement
	 */
	protected void resolveElementReferences(IResource file, RuleElement ruleElement) {
		ScopeBlock scope = ruleElement.getScope();
		List<ElementReference> unresolvedReferences = new ArrayList<ElementReference>();
		resolveElementReferencesInScope(scope, unresolvedReferences, ruleElement);
		reportUnresolvedReferenceErrors(ruleElement, file, unresolvedReferences);
	}
	
	/**
	 * 
	 * @param scope
	 * @param unresolvedReferences
	 * @param ruleElement
	 */
	protected void resolveElementReferencesInScope(ScopeBlock scope,
											       List<ElementReference> unresolvedReferences, 
											       RuleElement ruleElement) {
		EList<ElementReference> refs = scope.getRefs();
		for (ElementReference elementReference : refs) {
			ScopeBlock scopeBlock = RuleGrammarUtils.getScope(elementReference);
			RuleElementResolutionContext context = new RuleElementResolutionContext(scopeBlock, ruleElement);
			Object resolveElement = ElementReferenceResolver.resolveElement(elementReference, context);
			if (resolveElement == null) {
				// determine what link in the chain could not be resolved
				// TODO: NOT EFFICIENT.  Figure out a better way to do this
				ElementReference qualifier = elementReference;
				while (qualifier.getQualifier() != null) {
					context = new RuleElementResolutionContext(scopeBlock, ruleElement);
					Object resolved = ElementReferenceResolver.resolveElement(qualifier.getQualifier(), context);
					if (resolved == null) {
						// could not resolve this link, try the qualifier
						qualifier = qualifier.getQualifier();
					} else {
						// qualifier is the issue
						break;
					}
				}
				unresolvedReferences.add(qualifier);
			}
		}
		EList<ScopeBlock> childScopeDefs = scope.getChildScopeDefs();
		for (ScopeBlock scopeBlock : childScopeDefs) {
			resolveElementReferencesInScope(scopeBlock, unresolvedReferences, ruleElement);
		}
	}
	/**
	 * 
	 * @param ruleElement 
	 * @param file
	 * @param unresolvedReferences
	 */
	protected void reportUnresolvedReferenceErrors(RuleElement ruleElement, IResource file, List<ElementReference> unresolvedReferences) {
		for (ElementReference elementReference : unresolvedReferences) {
			int index = elementReference.getOffset();
			int length = elementReference.getLength();
			reportProblem(file, "Unable to resolve "+elementReference.getName(), -1, index, length, IMarker.SEVERITY_ERROR);
		}
	}
	
	/**
	 * @param projectName
	 * @return
	 */
	protected Map<String, String> getGlobalVariableNameTypes(String projectName) {
		GlobalVariablesProvider provider = GlobalVariablesMananger.getInstance().getProvider(projectName);
		Map<String, String> gvn = new HashMap<String, String>();
		for (GlobalVariableDescriptor descriptor : provider.getVariables()) {
			gvn.put(descriptor.getFullName(), descriptor.getType());
		}
		return gvn;
	}

	/**
	 * @param projectName
	 * @return
	 */
	public static Map<String, GlobalVariableDescriptor> getGlobalVariableNameValues(String projectName) {
		GlobalVariablesProvider provider = GlobalVariablesMananger.getInstance().getProvider(projectName);
		Collection<GlobalVariableDescriptor> gVars = provider.getVariables();
		Map<String, GlobalVariableDescriptor> globalVarsMap = new LinkedHashMap<String, GlobalVariableDescriptor>(gVars.size());
		for (GlobalVariableDescriptor descriptor : gVars) {
			globalVarsMap.put(descriptor.getFullName(), descriptor);
		}
		return globalVarsMap;
	}
	
	/**
	 * @param projectName
	 * @return
	 */
	public static Map<String, GlobalVariableDescriptor> getGlobalVariableDescriptors(String projectName) {
		GlobalVariablesProvider provider = GlobalVariablesMananger.getInstance().getProvider(projectName);
		Map<String, GlobalVariableDescriptor> gvn = new HashMap<String, GlobalVariableDescriptor>();
		for(GlobalVariableDescriptor descriptor:provider.getVariables()){
			gvn.put(descriptor.getFullName(), descriptor);
		}
		return gvn;
	}

}