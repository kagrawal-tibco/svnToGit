package com.tibco.cep.bpmn.ui.validation;


import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.Annotation;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.index.BpmnNameGenerator;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.BpmnModelCache;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.symbols.SymbolEntryImpl;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.bpmn.runtime.activity.tasks.ModelType;
import com.tibco.cep.bpmn.runtime.utils.ModelTypeHelper;
import com.tibco.cep.bpmn.runtime.utils.TaskFunctionModel.TypeSymbol;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tibco.cep.bpmn.ui.graph.model.SupportedProcessPropertiesType;
import com.tibco.cep.bpmn.ui.graph.properties.GeneralJavaTaskPropertySection;
import com.tibco.cep.bpmn.ui.validation.resolution.BpmnProcessRefreshVisitor;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.StudioJavaUtil;
import com.tibco.cep.studio.core.validation.IValidationError;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.xml.data.primitive.ExpandedName;

public class BpmnProcessValidator extends DefaultBpmnResourceValidator {
	
	public static final String PROCESS_OWNER_MARKER_TYPE = BpmnCorePlugin.PLUGIN_ID + ".bpmnProcessOwnerProjectProblem";
	public static final String INDEX_FORMAT_MARKER_TYPE = BpmnCorePlugin.PLUGIN_ID + ".indexFormatProblem";
	public static final String MISSING_EXTDEF_MARKER_TYPE = BpmnCorePlugin.PLUGIN_ID + ".missingExtDefProblem";
	public static final String WRONG_FOLDER_MARKER_TYPE = BpmnCorePlugin.PLUGIN_ID + ".processFolderProblem";
	public static final String MISSING_RESOURCE_MARKER_TYPE = BpmnCorePlugin.PLUGIN_ID + ".missingResourceProblem";
	public static final String INCORRECT_PROCESS_MARKER_TYPE = BpmnCorePlugin.PLUGIN_ID + ".incorrectProcessProblem";
	public static final String PROCESS_UNIQUEID_NOTGENERATED = BpmnCorePlugin.PLUGIN_ID + ".bpmnProcessuniqueIdNotGenerated";
	public static final String PROCESS_OBJECT_TYPE_MARKER_ATTRIB = BpmnCorePlugin.PLUGIN_ID + ".attr.type";
	
	private Map<String, IMethod> signMethodMap = new HashMap<String, IMethod>();
	private Map<String, String> displaySignMap = new HashMap<String, String>();
	
	public BpmnProcessValidator() {
		
	}

	@Override
	public boolean canContinue() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean enablesFor(IResource resource) {
		return BpmnIndexUtils.isBpmnProcess(resource);
	}

	@Override
	public boolean validate(ValidationContext validationContext) {
		IResource resource = validationContext.getResource();	
		if (resource == null ) return true;
		setResource(resource);
				
		EObject index = BpmnModelCache.getInstance().getIndex(resource.getProject().getName());
		if(index == null){
			BpmnCorePlugin.getDefault();// this is hint to load all the index
			index = BpmnModelCache.getInstance().getIndex(resource.getProject().getName());
			if(index == null)
				return true;//if still null, no need to validate the process
		}
		super.validate(validationContext);
		try {
			EObject modelObj = getBpmnModelObject(resource);
			if(modelObj == null)
				modelObj = loadBpmnProcess(resource);
			if (modelObj != null) {
				EObjectWrapper<EClass, EObject> modelObjWrapper = EObjectWrapper
						.wrap(modelObj);
				if (modelObjWrapper.isInstanceOf(BpmnModelClass.PROCESS)) {
					validateProcess(resource, modelObjWrapper);
				}
			}
			
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		
		return true;
	}
	
	
	private void validateProcess(IResource resource, EObjectWrapper<EClass, EObject> modelObjWrapper) throws Exception {
		@SuppressWarnings("unused")
		IValidationError vldError = null;
		Map<String,Object> customAttribMap = new HashMap<String,Object>();
		URI uri = URI.createPlatformResourceURI(resource.getFullPath().toPortableString(), false); 
		ResourceSet rset = ECoreHelper.getModelResourceSet(uri);
		ValidationURIHandler uriHandler = new ValidationURIHandler(resource,BpmnIndexUtils.getIndexLocationMap());
		EList<EObject> eObjList = ECoreHelper.deserializeModelXMI(rset,resource, true, uriHandler);
		if(eObjList.isEmpty()) {
			throw new Exception (
					Messages.getString("process.validate.notfound",
							resource.getFullPath().toPortableString()));
		}
		EObject process = eObjList.get(0);
		ExpandedName en = BpmnMetaModel.getInstance().getExpandedName(modelObjWrapper.getEClassType());
		boolean projectNameError =false;
		String ownerProjectName = modelObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
		String locationID = modelObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		int unique_id = modelObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
		if(!ownerProjectName.equals(resource.getProject().getName())) {
			projectNameError = true;
			reportGraphProblem(resource, 
					Messages.getString("process.validate.owner.project.error",
							ownerProjectName),locationID,
							IMarker.SEVERITY_ERROR,
							PROCESS_OWNER_MARKER_TYPE,en.toString(), unique_id,customAttribMap);
		}
		
		String folderName = modelObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
		String folderNameInresource = resource.getParent().getProjectRelativePath().makeAbsolute().toPortableString();
		if(!folderName.equals(folderNameInresource)) {
			reportGraphProblem(resource, 
					Messages.getString("process.validate.folder.error",
							folderName),locationID,
							IMarker.SEVERITY_ERROR,
							WRONG_FOLDER_MARKER_TYPE,en.toString(), unique_id, customAttribMap);
		}
		
		if(!uriHandler.getInvalidURIs().isEmpty()) {
			projectNameError = true;
			reportGraphProblem(resource, 
					Messages.getString("process.validate.invalid.project.reference.error",
							uriHandler.getInvalidProjectNames()),locationID,
							IMarker.SEVERITY_ERROR,
							PROCESS_OWNER_MARKER_TYPE,en.toString(),unique_id, customAttribMap);
		}
		
		if(uriHandler.hasExtensions()) {
			if(uriHandler.isOldKey()){
				reportGraphProblem(resource, 
						Messages.getString("process.validate.index.oldkey.error"),locationID,
						IMarker.SEVERITY_ERROR,
						INDEX_FORMAT_MARKER_TYPE,en.toString(),unique_id, customAttribMap);
			}
		}
		//validate uniqueId generation
		
		BpmnNameGenerator nameGenerator = BpmnCorePlugin.getDefault().getBpmnModelManager().getNameGenerator(ownerProjectName);
		nameGenerator.createUidList(process);
		if(nameGenerator.getuIds().isEmpty() || nameGenerator.getuIds().contains(0))
			reportGraphProblem(resource, BpmnMessages.getString("bpmnProcessValidator_uniqueIdValidation"), locationID, IMarker.SEVERITY_ERROR,
					PROCESS_UNIQUEID_NOTGENERATED,en.toString(), unique_id, customAttribMap);
		
		validateForFlowElementContainer(resource, process);
		

		rset = BpmnCorePlugin.getDefault().getBpmnModelManager()
				.getModelResourceSet(resource.getProject());
		eObjList = ECoreHelper.deserializeModelXMI(rset,resource, true, ECoreHelper.getURIHandler(resource.getProject()));
		if (eObjList.isEmpty()) {
			throw new Exception(Messages.getString("process.validate.notfound",
					resource.getFullPath().toPortableString()));
		}

		process = eObjList.get(0);
		BpmnProcessRefreshVisitor visitor = new BpmnProcessRefreshVisitor(false);
		EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper
				.wrap(process);
		wrapper.accept(visitor);
		if (visitor.isMissingExtensionData()) {
			reportGraphProblem(resource, Messages
					.getString("process.validate.missing.extdata.error"),
					resource.getFullPath().makeAbsolute().toPortableString(),
					IMarker.SEVERITY_ERROR, MISSING_EXTDEF_MARKER_TYPE, en.toString(),-1, customAttribMap);
		}
		validateForProcessProperties(resource, process);
		//check following error if  index correctly referred
		if (!projectNameError) {
			// validation for missing attached resource
			String projectName = resource.getProject().getName();
			List<EObject> allFlowNodes = BpmnModelUtils
					.getAllFlowNodes(process);
//			StringBuilder builder = new StringBuilder();
			BpmnXsltValidationHelper bpmnXsltValidationHelper = new BpmnXsltValidationHelper(resource.getProject(), EObjectWrapper.wrap(process));
			for ( EObject flowNode : allFlowNodes ) {
				Object resReferenced = BpmnModelUtils
						.getAttachedResource( flowNode );
				String name = EObjectWrapper.wrap(flowNode ).getAttribute(
						BpmnMetaModelConstants.E_ATTR_NAME );
				locationID = EObjectWrapper.wrap(flowNode).getAttribute(
						BpmnMetaModelConstants.E_ATTR_ID);
				unique_id = EObjectWrapper.wrap(flowNode).getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
				 en = BpmnMetaModel.getInstance().getExpandedName(flowNode.eClass());
				 
				 
				if ( resReferenced != null ) {
					if (resReferenced instanceof EObject) {
						EObjectWrapper< EClass, EObject > resReferencedWrapper = EObjectWrapper.wrap( ( EObject ) resReferenced );
						
						//check if process is from projLib
						IFile linkFile = BpmnIndexUtils.getFile( projectName,
								( EObject ) resReferenced );
					
							if ((linkFile == null) || IndexUtils.isProjectLibType( linkFile.getFullPath().toPortableString() ) && ! linkFile.exists() ) {
								String msg = Messages
										.getString(
												 "process.validate.referenced.entity.error",
												name);
								reportProbForMissingResource( msg, resource,
										name, locationID, en.toString(),
										unique_id );
								continue;
							}
							
						String refProject =resReferencedWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
						if ( refProject != null && !refProject.equals(projectName ) ) {
							reportGraphProblem(resource, Messages.getString(
									"process.validate.different_project_referenced.entity.error",
									name, refProject), locationID, IMarker.SEVERITY_ERROR,
									VALIDATION_MARKER_TYPE,en.toString(), unique_id, customAttribMap);
							
						} else{
							IFile file = BpmnIndexUtils.getFile(projectName,
									(EObject) resReferenced);
							if ((file== null) ||!file.exists()) {
								String msg = Messages.getString(
										"process.validate.referenced.entity.error",
										name);
								reportProbForMissingResource(msg, resource, name, locationID, en.toString(), unique_id);
							}
						}
					} else if (resReferenced instanceof String) {
						if(!((String) resReferenced).isEmpty()){
							
							DesignerElement element = IndexUtils.getElement(projectName, (String) resReferenced);
							if(element == null){
								if(!flowNode.eClass().equals(BpmnModelClass.SERVICE_TASK)){
									String msg = Messages.getString(
											"process.validate.referenced.entity.error",
											name);
									reportProbForMissingResource(msg,resource, name, locationID, en.toString(), unique_id);
								}else{
									String wsdlPath = resReferenced+".wsdl";
									IFile file = resource.getProject().getFile(wsdlPath);
									if(file == null || !file.exists()){
										String msg = Messages.getString(
												"process.validate.referenced.entity.error",
												name);
										reportProbForMissingResource(msg,resource, name, locationID, en.toString(), unique_id);
									}
									
								}
							}
							
							if(flowNode.eClass().equals(BpmnModelClass.JAVA_TASK)){
								if (element == null){
									String msg = Messages.getString(
											"process.validate.referenced.entity.error",
											name);
								reportProbForMissingResource(msg,resource, name, locationID, en.toString(), unique_id);
								}
							}
							
							if(flowNode.eClass().equals(BpmnModelClass.BUSINESS_RULE_TASK))
							{	
								if((element instanceof RuleElement))
								{	
									RuleElement ruleElement = (RuleElement)element;
									RuleFunction ruleFunction = (RuleFunction) ruleElement.getRule();
									if (!ruleFunction.isVirtual()) 
									{
										String msg = Messages.getString(
												"process.validate.referenced.entity.business_rule.error",
												name);
										reportProbForMissingResource(msg, resource, name, locationID, en.toString(), unique_id);
									}
								}
							}
						}else if(BpmnModelClass.EVENT.isSuperTypeOf(flowNode.eClass())){
							EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(flowNode);
							EList<EObject> listAttribute = wrap.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
							if(listAttribute.size()>0){
								EObject eObject = listAttribute.get(0);
								if(!eObject.eClass().equals(BpmnModelClass.TIMER_EVENT_DEFINITION)){
									String msg = Messages.getString(
											"process.validate.referenced.entity.error",
											name);
									reportProbForMissingResource(msg, resource, name, locationID, en.toString(), unique_id);
								}
							}
						}else{
							String msg = Messages.getString(
									"process.validate.referenced.entity.error",
									name);
							reportProbForMissingResource(msg, resource, name, locationID, en.toString(), unique_id);
						}
					} else if (resReferenced instanceof Collection) {
						@SuppressWarnings("unchecked")
						Collection<String> rules = (Collection<String>) resReferenced;
						if (rules.size() == 0) {
							String msg = Messages.getString(
									"process.validate.referenced.entity.error",
									name);
							reportProbForMissingResource(msg,resource, name, locationID, en.toString(), unique_id);
						}
						int emptyRuleName = 0;
						for (String ruleName : rules) {
							// find corresponding object (Rule)
							if(ruleName != null && !ruleName.trim().isEmpty()){
								DesignerElement re = IndexUtils.getElement(
										projectName, (String) ruleName);
								if (re == null ){
									String msg = Messages.getString(
											"process.validate.referenced.entity.error",
											name);
									reportProbForMissingResource(msg, resource, name, locationID, en.toString(), unique_id);
								}
							}else
								emptyRuleName++;
						}
						if(emptyRuleName == rules.size()){
							String msg = Messages.getString(
									"process.validate.referenced.entity.error",
									name);
							reportProbForMissingResource(msg, resource, name, locationID, en.toString(), unique_id);
						}
					}
				} else {
					if (flowNode.eClass().equals(BpmnModelClass.CALL_ACTIVITY) ||
							flowNode.eClass().equals(BpmnModelClass.RULE_FUNCTION_TASK)||
							flowNode.eClass().equals(BpmnModelClass.INFERENCE_TASK)||
							flowNode.eClass().equals(BpmnModelClass.BUSINESS_RULE_TASK)||
							flowNode.eClass().equals(BpmnModelClass.RECEIVE_TASK)||
							flowNode.eClass().equals(BpmnModelClass.SEND_TASK)) {
						String msg = Messages.getString(
								"process.validate.referenced.entity.error",
								name);
						reportProbForMissingResource(msg, resource, name, locationID, en.toString(), unique_id);
					} else if (BpmnModelClass.EVENT.isSuperTypeOf(flowNode
							.eClass())) {
						EObjectWrapper<EClass, EObject> eventWrap = EObjectWrapper
								.wrap(flowNode);
						EList<EObject> listAttribute = eventWrap
								.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
						if (listAttribute.size() > 0) {
							EObject eObject = listAttribute.get(0);
							if (!eObject.eClass().equals(
									BpmnModelClass.TIMER_EVENT_DEFINITION)) {
								String msg = Messages.getString(
										"process.validate.referenced.entity.error",
										name);
								reportProbForMissingResource(msg, resource, name, locationID, en.toString(), unique_id);
							}
						}
					}
				}
				
				validateGateways(resource, flowNode);
				validateRecieveEvent(resource, flowNode);
				validateDecisionTable(resource, flowNode);
				validateWebServiceTask(flowNode, resource);
				validateTimeTask(flowNode, resource);
				validateLoopCharacteristic(flowNode,resource);
				validateJavaTask(flowNode, resource);
				
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
						.wrap(flowNode);
				if (ExtensionHelper.isValidDataExtensionAttribute(wrap,
						BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION)) {
					EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
							.getAddDataExtensionValueWrapper(wrap);
					if (valueWrapper != null) {
						String destPath = valueWrapper
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION);
						if (destPath != null && !destPath.trim().isEmpty()) {
							int lastIndexOf = destPath.lastIndexOf("/");
							String channel = destPath.substring(0, lastIndexOf)+".channel";
							String destination = destPath.substring(lastIndexOf);
							Destination dest = IndexUtils.getDestination(projectName, channel+destination);
							if(dest == null){
								String msg = Messages.getString(
										"process.validate.referenced.destination.error",
										name);
								reportProbForMissingResource(msg, resource, name, locationID, en.toString(), unique_id);
							}
						}
					}
				}
				
				List<String> errors = bpmnXsltValidationHelper.validateMapperFunctions(EObjectWrapper.wrap(flowNode));
				for (String error : errors) {
					reportProbForMissingResource(error, resource, name, locationID, en.toString(), unique_id);
				}
				errors = BpmnXpathValidationHelper.validateXPathFunction(flowNode, process, resource.getProject());
				for (String error : errors) {
					reportProbForMissingResource(error, resource, name, locationID, en.toString(), unique_id);
				}
			}
			// validate condition expression of xpath
			List<EObject> allSequenceFlows = BpmnModelUtils.getAllSequenceFlows(process);
			for (EObject eObject : allSequenceFlows) {
				String name = EObjectWrapper.wrap(eObject)
						.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
				List<String> errors = BpmnXpathValidationHelper.validateXPathFunction(eObject, process, resource.getProject());
				locationID = EObjectWrapper.wrap(eObject)
						.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				unique_id = EObjectWrapper.wrap(eObject).getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
				for (String error : errors) {
					reportProbForMissingResource(error, resource, name, locationID, en.toString(), unique_id);
				}
			}
		}
		// TODO: further model validation can be done here
		
			
	}
	
	
	private void reportProbForMissingResource(String msg, IResource resource, String nodeName, String nodeId, String expandedname, int uniqueId) {
		Map<String,Object> customAttribMap = new HashMap<String,Object>();
		reportGraphProblem(resource, msg, nodeId, IMarker.SEVERITY_ERROR, VALIDATION_MARKER_TYPE, expandedname.toString(), uniqueId, customAttribMap);
	}
	
	/**
	 * @param resource
	 * @param container
	 */
	private void validateProcessBasedOnBpmnSpecConstraint(IResource resource, EObject container){
		Map<String,Object> customAttribMap = new HashMap<String,Object>();
		EList<EObject> contents = container.eContents();
		for (EObject object : contents) {
			String error = null;
			if(BpmnModelClass.ASSOCIATION.isSuperTypeOf(object.eClass())){
				 error = BpmnProcessValidationHelper.checkForValidAssociation(object);
			}else if(BpmnModelClass.SEQUENCE_FLOW.isSuperTypeOf(object.eClass())){
				 error = BpmnProcessValidationHelper.checkForValidSequence(object);
			}else if(BpmnModelClass.FLOW_ELEMENT.isSuperTypeOf(object.eClass())){
				 error = BpmnProcessValidationHelper.checkForValidNodeInGraph(object);
			}
			if (error != null) {
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(object);
				String locId = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				int unique_id = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
				ExpandedName en = BpmnMetaModel.getInstance().getExpandedName(wrap.getEClassType());
				reportGraphProblem(resource, error, locId, IMarker.SEVERITY_ERROR,
					INCORRECT_PROCESS_MARKER_TYPE, en.toString(), unique_id, customAttribMap);
			}
			if(BpmnModelClass.FLOW_ELEMENTS_CONTAINER.isSuperTypeOf(object.eClass())){
				validateForFlowElementContainer(resource, object);
			}
		}
	}
	
	/**
	 * @param resource
	 * @param object
	 */
	private void validateForFlowElementContainer(IResource resource, EObject object){
		if(BpmnModelClass.FLOW_ELEMENTS_CONTAINER.isSuperTypeOf(object.eClass())){
			 String error = null;
			if(BpmnModelClass.PROCESS.isSuperTypeOf(object.eClass())){
//				validateForProcessProperties(resource, object);
				error = BpmnProcessValidationHelper.checkForStartEventInProcess(object);
				reportArtifactAbsenseError(error, resource, object);
				
				error = BpmnProcessValidationHelper.checkForEndEventInProcess(object);
				reportArtifactAbsenseError(error, resource, object);
					
			}else if(BpmnModelClass.SUB_PROCESS.isSuperTypeOf(object.eClass())){
//				validateForProcessProperties(resource, object);
				error = BpmnProcessValidationHelper.checkForStartEventInEventSubProcess(object);	
				reportArtifactAbsenseError(error, resource, object);
			}
			validateProcessBasedOnBpmnSpecConstraint(resource, object);
		}
	}
	
	private void reportArtifactAbsenseError(String error, IResource resource, EObject object){
		Map<String,Object> customAttribMap = new HashMap<String,Object>();
		if(error != null){
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(object);
			String locId = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			int uniqueId = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
			ExpandedName en = BpmnMetaModel.getInstance().getExpandedName(wrap.getEClassType());
			reportGraphProblem(resource, error, locId, IMarker.SEVERITY_ERROR,
				INCORRECT_PROCESS_MARKER_TYPE, en.toString(),uniqueId, customAttribMap);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#deleteMarkers(org.eclipse.core.resources.IResource)
	 */
	protected void deleteMarkers(IResource file){
		super.deleteMarkers(file);
		try {
			file.deleteMarkers(PROCESS_OWNER_MARKER_TYPE, false, IResource.DEPTH_ZERO);
			file.deleteMarkers(MISSING_EXTDEF_MARKER_TYPE, false, IResource.DEPTH_ZERO);
			file.deleteMarkers(MISSING_RESOURCE_MARKER_TYPE, false, IResource.DEPTH_ZERO);
			file.deleteMarkers(WRONG_FOLDER_MARKER_TYPE, false, IResource.DEPTH_ZERO);
			file.deleteMarkers(INDEX_FORMAT_MARKER_TYPE, false, IResource.DEPTH_ZERO);
			file.deleteMarkers(INCORRECT_PROCESS_MARKER_TYPE, false, IResource.DEPTH_ZERO);
			file.deleteMarkers(PROCESS_UNIQUEID_NOTGENERATED, false, IResource.DEPTH_ZERO);
		} catch (CoreException ce) {
		}
	}
	
	
	/**
	 * @param resource
	 * @param object
	 */
	private void validateGateways(IResource resource, EObject object) { 
		if(BpmnModelClass.PARALLEL_GATEWAY.isSuperTypeOf(object.eClass()) || BpmnModelClass.INCLUSIVE_GATEWAY.isSuperTypeOf(object.eClass())) {
			String projectName = resource.getProject().getName();
			EObjectWrapper<EClass, EObject> gatewayWrapper = EObjectWrapper.wrap(object);
			
			String location_id = gatewayWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			int unique_id = gatewayWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
			ExpandedName en = BpmnMetaModel.getInstance().getExpandedName(gatewayWrapper.getEClassType());
			String type = BpmnModelClass.PARALLEL_GATEWAY.isSuperTypeOf(object.eClass()) ? BpmnMetaModelConstants.PARALLEL_GATEWAY.getExpandedForm() 
					: BpmnMetaModelConstants.INCLUSIVE_GATEWAY.getExpandedForm();
			
			List<String> errors = new ArrayList<String>();
			EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(gatewayWrapper);
			if (valueWrapper != null && (gatewayWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING).size() > 1 
					|| gatewayWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING).size() > 1 )) {
//				String merge_expression = valueWrapper.getAttribute( BpmnMetaModelExtensionConstants.E_ATTR_MERGE_EXPRESSION);
				String fork_rule_function = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_FORK_RULEFUNCTION);
				String join_rule_function = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOIN_RULEFUNCTION);
//				if (merge_expression.isEmpty()) {
//					String error = Messages.getString("empty_element_property", BpmnMetaModelExtensionConstants.E_ATTR_MERGE_EXPRESSION, id);
//					if(error != null) {
//						errors.add(error);
//					}
//				}
				if(gatewayWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING).size() > 1)
					errors = BpmnProcessValidationHelper.checkForValidForkRuleFunction(errors, projectName, BpmnMetaModelExtensionConstants.E_ATTR_FORK_RULEFUNCTION, fork_rule_function, location_id);
				if(gatewayWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING).size() > 1)
					errors = BpmnProcessValidationHelper.checkForValidJoinRuleFunction(errors, projectName, BpmnMetaModelExtensionConstants.E_ATTR_JOIN_RULEFUNCTION, join_rule_function, location_id);
			}
			for (String error : errors) {
				customeAttribMap.put(PROCESS_OBJECT_TYPE_MARKER_ATTRIB, type);
				reportGraphProblem(resource, error, location_id , IMarker.SEVERITY_ERROR, VALIDATION_MARKER_TYPE,en.toString(), unique_id, customeAttribMap);
			}
		} 
	}
	
	
	/**
	 * 
	 * @param resource
	 * @param object
	 */
	private void validateRecieveEvent(IResource resource, EObject object) {
		if(BpmnModelClass.RECEIVE_TASK.isSuperTypeOf(object.eClass())){
			EObjectWrapper<EClass, EObject> taskWrapper = EObjectWrapper.wrap(object);
			String location_id = taskWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			String type = BpmnMetaModelConstants.RECEIVE_TASK.getExpandedForm();
			ExpandedName en = BpmnMetaModel.getInstance().getExpandedName(taskWrapper.getEClassType());
			EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(taskWrapper);
			if (valueWrapper != null) {
				String key_expression = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOB_KEY);
				if (key_expression.isEmpty()) {
					String error = Messages.getString("empty_element_property", "Key Expression", location_id);
					if(error != null) {
						customeAttribMap.put(PROCESS_OBJECT_TYPE_MARKER_ATTRIB, type);
//						reportGraphProblem(resource, error,location_id, IMarker.SEVERITY_ERROR, VALIDATION_MARKER_TYPE, en.toString(), customeAttribMap);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param resource
	 * @param object
	 */
	private Map<String,String> validateContainedConcept(String projectName,EObjectWrapper<EClass, EObject> useInstanceCurr){
		
		Map<String ,Set<String>> mapContainedConceptinProcoess = new HashMap<String,Set<String>>();
		Set<String> setContainedConceptinProcoess=new HashSet<String>();
		Set<String> setContainedConceptinConcept=new HashSet<String>();
		Map<String,Set<String>> mapContainedConceptinConcept=new HashMap<String,Set<String>>();
		Map<String,String> mapInvalidContainedConcept=new HashMap<String,String>();
		List<Entity> list = IndexUtils.getAllEntities(projectName, ELEMENT_TYPES.CONCEPT);
		for(Entity entity:list){
			Concept concept = (Concept)entity;	
			for(PropertyDefinition propertyDefinition:concept.getAllPropertyDefinitions()){
				setContainedConceptinConcept=new HashSet<String>();
				if(propertyDefinition.getType()== PROPERTY_TYPES.CONCEPT){
					setContainedConceptinConcept.add(propertyDefinition.getConceptTypePath());
					
			}
			}
			if(setContainedConceptinConcept.size()!=0)
			mapContainedConceptinConcept.put(concept.getFullPath(), setContainedConceptinConcept);
		}
		
		List<EObject> arrProcess=BpmnCommonIndexUtils.getAllProcesses(projectName);
		for(EObject pr:arrProcess){
			EObjectWrapper<EClass, EObject> useInstance=EObjectWrapper.wrap(pr);
		String id = useInstance.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		mapContainedConceptinProcoess.put(id,getContainedConceptinProcess(useInstance) );
		}
		String idCurr=useInstanceCurr.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		setContainedConceptinProcoess=mapContainedConceptinProcoess.get(idCurr);
		if(setContainedConceptinProcoess == null)
			setContainedConceptinProcoess=new HashSet<String>();// BE-17743, don't know why getting NPE. need to analyse further
		for(String prConcept:setContainedConceptinProcoess ){
			/*if(setContainedConceptinConcept.contains(prConcept))
			{
				//mapInvalidContainedConcept.put(key, prConcept);
				setInvalidContainedConcept.add(prConcept);
				//flag=true;
			}*/
			for(Map.Entry<String ,Set<String>> entry:mapContainedConceptinConcept.entrySet() ){
					
				if(entry.getValue().contains(prConcept))
				{	
					//setInvalidContainedConcept.add(prConcept);
					mapInvalidContainedConcept.put(entry.getKey(),prConcept);
				}
			}
			
			for(Map.Entry<String ,Set<String>> entry:mapContainedConceptinProcoess.entrySet() ){
				if(!idCurr.equals(entry.getKey())){
					if(entry.getValue().contains(prConcept))
						//flag=true;
						mapInvalidContainedConcept.put(entry.getKey(),prConcept);
						//setInvalidContainedConcept.add(prConcept);
				}
			}
		}
		
		return mapInvalidContainedConcept;
	}
	
	
	private Set<String> getContainedConceptinProcess(EObjectWrapper<EClass, EObject> useInstance){
		
		String id="";
		String strUriPath="";
		String strUri="www.tibco.com/be/ontology";
		String strConceptCons="Concept";
		String strConceptPath="";
		//Set<String> setContainedConceptinConcept=VariablePropertySelector.getProcessContainedConcept(projectName);
		Set<String> setContainedConceptinProcoess=new HashSet<String>();
		if(useInstance != null){
			
			id = useInstance.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			List<EObject> propDefs = new ArrayList<EObject>(useInstance.getListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES));
			for(EObject propDef:propDefs){
				
				EObjectWrapper<EClass, EObject> propDefWrapper=EObjectWrapper.wrap(propDef);
				EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(propDefWrapper);
				EEnumLiteral propType= addDataExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE);
				EObjectWrapper<EClass, EObject> itemDef = EObjectWrapper.wrap((EObject)propDefWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF));
				if(itemDef != null && propType.getName().equals(strConceptCons)){
					
					ExpandedName itemDefinitionType = SupportedProcessPropertiesType.getItemDefinitionType(itemDef);
					if(itemDefinitionType != null && itemDefinitionType.getExpandedForm()!=null){
						
						strUriPath=itemDefinitionType.getExpandedForm().substring(1,(itemDefinitionType.getExpandedForm().lastIndexOf('}')));
						strConceptPath=strUriPath.substring(strUri.length(), strUriPath.length());
						setContainedConceptinProcoess.add(strConceptPath);
						
					}
				}
		}		
		}
		
		return setContainedConceptinProcoess;
		
	}
	private void validateForProcessProperties(IResource resource, EObject object) {
		Map<String,Object> customAttribMap = new HashMap<String,Object>();
		//boolean flag=false;
		Map<String,String> mapInvalidContainedConcept=new HashMap<String,String>();
		if (BpmnModelClass.PROCESS.equals(object.eClass())
				|| BpmnModelClass.SUB_PROCESS.equals(object.eClass())) {
			EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper
					.wrap(object);
			String projectName = resource.getProject().getName();
			
			List<EObject> listAttribute = processWrapper
					.getListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES);
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(object);
			String locId = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			int unique_id = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
			mapInvalidContainedConcept=validateContainedConcept(projectName,wrap);
			
			if(!mapInvalidContainedConcept.isEmpty())
				for(Map.Entry<String, String> mp:mapInvalidContainedConcept.entrySet()){
				String strConcept= mp.getValue().substring( mp.getValue().lastIndexOf('/')+1,mp.getValue().length());
				reportProblem(resource,BpmnMessages.getString("bpmnProcessValidator_invalidContainedConcept_para1_error_message") +"\'" + strConcept +"\'"+ BpmnMessages.getString("bpmnProcessValidator_invalidContainedConcept_para2_error_message")
																	+ mp.getValue() +BpmnMessages.getString("bpmnProcessValidator_invalidContainedConcept_para3_error_message") +mp.getKey() , IMarker.SEVERITY_WARNING);
				}
			
			
			ExpandedName en = BpmnMetaModel.getInstance().getExpandedName(wrap.getEClassType());
			String nodeName = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			for (EObject eObject : listAttribute) {
				EObjectWrapper<EClass, EObject> propDef = EObjectWrapper
						.wrap(eObject);

				String name = propDef
						.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
				EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(propDef);
				EEnumLiteral propType = addDataExtensionValueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE);
				EObjectWrapper<EClass, EObject> itemDef = EObjectWrapper
						.wrap((EObject) propDef
								.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF));
				String itemDefId = itemDef
						.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				if (itemDefId == null
						&& (propType
								.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_Concept) || propType
								.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_ConceptReference))) {
					
					reportGraphProblem(resource, Messages.getString(
							"process.properties.validate.entity.error", "\'"
									+ name + "\'"), locId,
							IMarker.SEVERITY_ERROR, VALIDATION_MARKER_TYPE,en.toString(),unique_id, customAttribMap);
				} else {
					if (propType
							.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_Concept)
							|| propType
									.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_ConceptReference)) {
						String propertyFullPath = getPropertyFullPath(itemDef);
						if (propertyFullPath != null
								&& !propertyFullPath.trim().isEmpty()) {
							DesignerElement re = IndexUtils.getElement(
									projectName, propertyFullPath);
							if (re == null) {
								String msg = Messages.getString(
										"process.properties.validate.referenced.entity.error",
										name);
								reportProbForMissingResource(msg, resource, nodeName, locId, en.toString(), unique_id);
							}
						} else {
							reportGraphProblem(resource, Messages.getString(
									"process.properties.validate.entity.error", "\'"
											+ name + "\'"), locId,
									IMarker.SEVERITY_ERROR, VALIDATION_MARKER_TYPE, en.toString(), unique_id, customAttribMap);
						}
						// }

					}
				}

			}
		}
	}
	
	private String getPropertyFullPath(ROEObjectWrapper<EClass, EObject> itemDef){
		String id = itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		if(id!= null){
			ExpandedName parse = ExpandedName.parse(id);
			String localName = parse.localName;
			PROPERTY_TYPES type = PROPERTY_TYPES.get(localName);
			EObject attr = itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
			
			if(type == null && attr != null){
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(attr);
				String attribute = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_LOCATION);
				return attribute;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param resource
	 * @param object
	 */
	private void validateDecisionTable(IResource resource, EObject object) {
		if(BpmnModelClass.BUSINESS_RULE_TASK.isSuperTypeOf(object.eClass())){
			EObjectWrapper<EClass, EObject> decisionTableWrapper = EObjectWrapper.wrap(object);
			EObjectWrapper<EClass,EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(decisionTableWrapper);
			EList<EObject> listAttribute = valueWrapper.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS);
			String location_id = decisionTableWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			int unique_id = decisionTableWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
			ExpandedName en = BpmnMetaModel.getInstance().getExpandedName(decisionTableWrapper.getEClassType());
			String type = BpmnMetaModelConstants.BUSINESS_RULE_TASK.getExpandedForm();
			String projectName = resource.getProject().getName();
			StringBuilder builder = new StringBuilder();
			for (EObject eObject : listAttribute) {
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(eObject);
				String uri = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_URI);
				boolean deployed  = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DEPLOYED);
				DesignerElement re = IndexUtils.getElement(projectName, uri);
				if (re == null && deployed) {
					builder.append("\"" + uri + "\", ");
				}
			}
			String faultNodeNames = builder.toString();
			if (faultNodeNames.endsWith(","))
				faultNodeNames = faultNodeNames.substring(0, faultNodeNames
						.length() - 1);

			if (!faultNodeNames.isEmpty()) {
				customeAttribMap.put(PROCESS_OBJECT_TYPE_MARKER_ATTRIB, type);
				reportGraphProblem(resource, Messages.getString(
						"decisiontable.validate.referenced.entity.error",
						location_id), location_id, IMarker.SEVERITY_ERROR,
						VALIDATION_MARKER_TYPE, en.toString(),unique_id, customeAttribMap);
			}
		}
	}
	
	private void validateTimeTask(EObject object, IResource resource){
		if(BpmnModelClass.ACTIVITY.isSuperTypeOf(object.eClass())){
			EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper
					.wrap(object);
			String location_id = wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			int unique_id  = wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
			ExpandedName en = BpmnMetaModel.getInstance().getExpandedName(wrapper.getEClassType());
			EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(wrapper);
			if (valueWrapper != null) {
				if (valueWrapper
						.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_ENABLED)) {
					Boolean enabled = valueWrapper
							.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_ENABLED);
					if(enabled){
						EObject timerData = valueWrapper
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_DATA);
						if (timerData != null) {
							EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
									.wrap(timerData);
							String event = wrap
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
							if(event == null || event.trim().isEmpty())
								reportGraphProblem(resource,BpmnMessages.getString("bpmnProcessValidator_eventNotSpecified_error_message"), location_id, IMarker.SEVERITY_ERROR,
										VALIDATION_MARKER_TYPE, en.toString(), unique_id, new HashMap<String, Object>());
							else{
								Event element = IndexUtils.getTimeEvent(resource.getProject().getName(), event);
								if(element != null){
										if(element instanceof TimeEvent){
											TimeEvent timeEvent = (TimeEvent) element;
											EVENT_SCHEDULE_TYPE scheduleType = timeEvent.getScheduleType();
											if(scheduleType != EVENT_SCHEDULE_TYPE.RULE_BASED)
											reportGraphProblem(resource,BpmnMessages.getString("bpmnProcessValidator_eventSpecifiedNotRuleBased_error_message"), location_id, IMarker.SEVERITY_ERROR,
													VALIDATION_MARKER_TYPE, en.toString(), unique_id, new HashMap<String, Object>());
										}
								}
							}
							
							String expression = wrap
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION);
							if(expression == null || expression.trim().isEmpty())
								reportGraphProblem(resource,BpmnMessages.getString("bpmnProcessValidator_expressionNotSpecified_error_message"), location_id, IMarker.SEVERITY_ERROR,
										VALIDATION_MARKER_TYPE, en.toString(), unique_id, new HashMap<String, Object>());
						}else{
							reportGraphProblem(resource,BpmnMessages.getString("bpmnProcessValidator_eventNotSpecified_error_message"), location_id, IMarker.SEVERITY_ERROR,
									VALIDATION_MARKER_TYPE, en.toString(), unique_id, new HashMap<String, Object>());
						}
					}
				}
			}
			
		}
	}
	
	private void validateWebServiceTask(EObject object, IResource resource){
		if(BpmnModelClass.SERVICE_TASK.equals(object.eClass())){
			EObjectWrapper<EClass, EObject> serviceTaskWrapper = EObjectWrapper.wrap(object);
			String location_id = serviceTaskWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			int unique_id = serviceTaskWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
			ExpandedName en = BpmnMetaModel.getInstance().getExpandedName(serviceTaskWrapper.getEClassType());
			EObjectWrapper<EClass,EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(serviceTaskWrapper);
			String soapAction = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SOAP_ACTION);
			if(soapAction == null || soapAction.trim().isEmpty())
				reportGraphProblem(resource,BpmnMessages.getString("bpmnProcessValidator_SoapActionNotSpecified_error_message"), location_id, IMarker.SEVERITY_ERROR,
						VALIDATION_MARKER_TYPE, en.toString(), unique_id, new HashMap<String, Object>());
			
			EEnumLiteral propType = valueWrapper
					.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_BINDING_TYPE);
			if (propType == null
					|| propType.equals(BpmnModelClass.ENUM_WS_BINDING_HTTP)) {
				String endpointurl = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_END_POINT_URL);
				if(endpointurl == null || endpointurl.isEmpty())
					reportGraphProblem(resource,BpmnMessages.getString("bpmnProcessValidator_EndPointNotSpecified_error_message"), location_id, IMarker.SEVERITY_ERROR,
							VALIDATION_MARKER_TYPE, en.toString(), unique_id, new HashMap<String, Object>());
				else{
					String upperCase = endpointurl.toUpperCase();
					if(upperCase.startsWith("HTTPS")){
						EObject sslConfig = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_HTTP_SSL_CONFIG);
						boolean valid = true;
						if(sslConfig == null)
							valid = false;
						else{
							EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(sslConfig);
							String certificate = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CERTIFICATE_FOLDER);
							if(certificate == null || certificate.trim().isEmpty())
								valid = false;
							String folderName = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CERTIFICATE_FOLDER);
							folderName=folderName.substring(0, folderName.lastIndexOf("/"));
							IFolder folderNameInresource = resource.getProject().getFolder(folderName);
							if(folderNameInresource==null || !folderNameInresource.exists()) {
								String msg = BpmnMessages.getString("bpmnProcessValidator_ssl.invalid.folder",folderName);
								reportProbForMissingResource(msg,resource, folderName, location_id, en.toString(), unique_id);
							}
							
							String identity=wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IDENTITY);
							if(identity !=null &&  !(identity.isEmpty())){
								IFile file = resource.getProject().getFile(identity);
								if(file == null || !file.exists()){
								String msg = BpmnMessages.getString("bpmnProcessValidator_ssl.invalid.identity",identity);
								reportProbForMissingResource(msg,resource, identity, location_id, en.toString(), unique_id);
								}
							}
						}
						
						if(!valid){
							reportGraphProblem(resource,BpmnMessages.getString("bpmnProcessValidator_TrsutedCertificateNotSpecified_error_message"), location_id, IMarker.SEVERITY_ERROR,
									VALIDATION_MARKER_TYPE, en.toString(), unique_id, new HashMap<String, Object>());
						}
					}
				}
			} else {
				EObject jmsConfig = valueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG);
				if (jmsConfig != null) {
					EObjectWrapper<EClass, EObject> jmsConfigWrapper = EObjectWrapper.wrap(jmsConfig);
					String url = jmsConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_URL);
					if(url == null || url.trim().isEmpty())
						reportGraphProblem(resource,BpmnMessages.getString("bpmnProcessValidator_JndiContextNotSpecified_error_message"), location_id, IMarker.SEVERITY_ERROR,
								VALIDATION_MARKER_TYPE, en.toString(), unique_id, new HashMap<String, Object>());
					
					boolean useJmsSsl = (Boolean)jmsConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_USE_JMS_SSL);
					if(useJmsSsl){
						EObject sslConfig = jmsConfigWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_SSL_CONFIG);
						boolean valid = true;
						if(sslConfig == null)
							valid = false;
						else{
							EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(sslConfig);
							String certificate = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CERTIFICATE_FOLDER);
							if(certificate == null || certificate.trim().isEmpty())
								valid = false;
							String folderName = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CERTIFICATE_FOLDER);
							folderName=folderName.substring(0, folderName.lastIndexOf("/"));
							IFolder folderNameInresource = resource.getProject().getFolder(folderName);
							if(folderNameInresource==null || !folderNameInresource.exists()) {
								String msg = BpmnMessages.getString("bpmnProcessValidator_ssl.invalid.folder",folderName);
								reportProbForMissingResource(msg,resource, folderName, location_id, en.toString(), unique_id);
							}
							String identity=wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IDENTITY);
							if(identity !=null &&  !(identity.isEmpty())){
								IFile file = resource.getProject().getFile(identity);
								if(file == null || !file.exists()){
								String msg = BpmnMessages.getString("bpmnProcessValidator_ssl.invalid.identity",identity);
								reportProbForMissingResource(msg,resource, identity, location_id, en.toString(), unique_id);
								}
							}
						}
						
						if(!valid){
							reportGraphProblem(resource,BpmnMessages.getString("bpmnProcessValidator_trsutedCertificateNotSpecified_error_message"), location_id, IMarker.SEVERITY_ERROR,
									VALIDATION_MARKER_TYPE, en.toString(), unique_id, new HashMap<String, Object>());
						}
					}
				}
			}
			
		}
	}
	
	
	private void validateLoopCharacteristic(EObject object, IResource resource) {
		EObjectWrapper<EClass, EObject> objectWrapper = EObjectWrapper
				.wrap(object);
		if(!BpmnModelClass.ACTIVITY.isSuperTypeOf(objectWrapper.getEClassType()) && !(objectWrapper.isInstanceOf(BpmnModelClass.SUB_PROCESS)) ) {
			return ;
		}
		String location_id = objectWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		int unique_id = objectWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
		ExpandedName en = BpmnMetaModel.getInstance().getExpandedName(objectWrapper.getEClassType());
//		EEnumLiteral loopMode = (EEnumLiteral )objectWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_TYPE) ;
//		BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS
		Object attribute = objectWrapper
				.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS);
		EObjectWrapper<EClass, EObject> loopWrapper = null ;
		if ( attribute != null )
			loopWrapper = EObjectWrapper
				.wrap((EObject) attribute);
		if ( BpmnModelClass.MULTI_INSTANCE_LOOP_CHARACTERISTICS.isInstance(attribute)) {
			attribute = loopWrapper
					.getAttribute(BpmnMetaModelConstants.E_ATTR_ITERATOR_XSLT);
			if (attribute == null || attribute.toString().isEmpty()) {
				reportGraphProblem(resource,BpmnMessages.getString("bpmnProcessLoopValidator_multiLoopVar"), location_id, IMarker.SEVERITY_ERROR,
						VALIDATION_MARKER_TYPE, en.toString(), unique_id,new HashMap<String, Object>());
				return ;
			}
			
		}
		
		if ( BpmnModelClass.STANDARD_LOOP_CHARACTERISTICS.isInstance(attribute)) {
			attribute = loopWrapper
					.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_MAXIMUM);
			int uid = loopWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
			if (attribute == null || attribute.toString().isEmpty()) {
				reportGraphProblem(resource,BpmnMessages.getString("bpmnProcessValidator_invalid_JavaResource_error_message"), location_id, IMarker.SEVERITY_ERROR,
						VALIDATION_MARKER_TYPE, en.toString(), uid, new HashMap<String, Object>());
				return ;
			}
			
		}
	}

	private void validateJavaTask(EObject object, IResource resource) {
//		String projectname = resource.getProject().getName();
		if (BpmnModelClass.JAVA_TASK.equals(object.eClass())) {
			
			// Check if the Java file exists or not 
			// Check for the method name
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(object);
			ExpandedName en = BpmnMetaModel.getInstance().getExpandedName(userObjWrapper.getEClassType());
			String location_id = userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			int uid = userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
			if (userObjWrapper != null ) {
				if (ExtensionHelper.isValidDataExtensionAttribute(userObjWrapper,BpmnMetaModelExtensionConstants.E_ATTR_JAVA_FILE_PATH)) {
					EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(userObjWrapper);
					String attribute = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JAVA_FILE_PATH);
					IFile file = resource.getProject().getFile(attribute+".java");
//					Entity ent = CommonIndexUtils.getEntity(projectname, attribute);
					if(!IndexUtils.isJavaResource(file)){
						reportGraphProblem(resource,BpmnMessages.getString("bpmnProcessValidator_invalid_JavaResource_error_message"), location_id, IMarker.SEVERITY_ERROR,
								VALIDATION_MARKER_TYPE, en.toString(), uid, new HashMap<String, Object>());
					}
					String methodName =  valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_METHOD_NAME);
					boolean methodValid = false;
					if(methodName != null)
						methodValid = BpmnGraphUtils.isMethodPresentInJavaSrc(file, methodName);
					if(!methodValid){
						reportGraphProblem(resource,BpmnMessages.getString("bpmnProcessValidator_invalid_JavaResource_invalid_method_message"), location_id, IMarker.SEVERITY_ERROR,
								VALIDATION_MARKER_TYPE, en.toString(), uid, new HashMap<String, Object>());
						return;
					}
					String functionSignature = GeneralJavaTaskPropertySection.createJavaTaskSignature(userObjWrapper);
					displaySignMap.clear();
					signMethodMap.clear();
					getJavaTaskMethods(file, BpmnCommonIndexUtils.ANNOTATION_BPMN_JAVA_CLASS_METHOD_TASK);
					if(displaySignMap.isEmpty()){
						reportGraphProblem(resource,BpmnMessages.getString("bpmnProcessValidator_invalid_JavaResource_noannotated_method_warning_message"), location_id, IMarker.SEVERITY_ERROR,
								VALIDATION_MARKER_TYPE, en.toString(), uid, new HashMap<String, Object>());
						return;
					}
					else if(!displaySignMap.containsKey(functionSignature)){
						reportGraphProblem(resource,BpmnMessages.getString("bpmnProcessValidator_invalid_JavaResource_method_error_message"), location_id, IMarker.SEVERITY_ERROR,
								VALIDATION_MARKER_TYPE, en.toString(), uid, new HashMap<String, Object>());
						return;
					}
					// Validate args URI
					IMethod method = getJavaTaskMethods(file, BpmnCommonIndexUtils.ANNOTATION_BPMN_JAVA_CLASS_METHOD_TASK,methodName);
					Map<String,String> argsUriMap = validateJavaArgsUri(method);
					List<SymbolEntryImpl> argsSymbol = BpmnModelUtils.getMethodArguments(userObjWrapper.getEInstance());
					SymbolEntryImpl retSymbol =BpmnModelUtils.getMethodReturnType(userObjWrapper.getEInstance());
					if(retSymbol != null)
						argsSymbol.add(retSymbol);
					
					for(SymbolEntryImpl symbol: argsSymbol){
						String name = symbol.getKey();
						String uri = symbol.getPath();
						if(argsUriMap.containsKey(name)){
							String javaUri = argsUriMap.get(name);
							if(!uri.equals(javaUri)){
								reportGraphProblem(resource,BpmnMessages.getString("bpmnProcessValidator_invalid_JavaResource_invalid_method_argsURI"), location_id, IMarker.SEVERITY_ERROR,
										VALIDATION_MARKER_TYPE, en.toString(), uid, new HashMap<String, Object>());
								break;
							}
						}
					}
				}
			}
			
			
		}
		
	}

	/**
	 * 
	 * @param file
	 */
	public IMethod getJavaTaskMethods(IFile file, String annotationName,Object ...arg) {
		String methodName = null;
		if(arg != null ){
			Object[] args =  arg;
			if( args!= null && args.length>0)
			methodName = (String) args[0];
		}
		try {
			ICompilationUnit cu = JavaCore.createCompilationUnitFrom(file);
			if (cu != null ) {
				cu.getImports();
				IType[] types = cu.getTypes();
				for (IType iType : types) {
					IMethod[] methods = iType.getMethods();
					for (IMethod iMethod : methods) {
						int modifier= iMethod.getFlags();
						if (Modifier.isPublic(modifier)) {
							
							boolean flag = false;
							List<Annotation> annotationList = StudioJavaUtil.getAnnotations(iMethod);	
							for (Annotation annotation : annotationList) {
								if (StudioJavaUtil.getAnnotationName(annotation).equals(annotationName)) {
									flag =  true;
								}
							}
							
							if (flag/*iMethod.getAnnotation(annotationName) != null*/) {
								String displaySign = GeneralJavaTaskPropertySection.getJavaMethodSignature(iMethod);
								if(methodName != null && !methodName.isEmpty() && methodName.equals(iMethod.getElementName())){
									return iMethod;
								}
								signMethodMap.put(displaySign, iMethod);
								displaySignMap.put(displaySign, iMethod.getElementName());
							}
						}
					}
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return null;
	}

	public TypeSymbol getJavaTaskReturnTypes(IMethod iMethod) {
		String returnType = "";
		boolean isPrimitiveReturnType = false;
		TypeSymbol ts = new TypeSymbol();
		ts.setName(iMethod.getElementName());
		IAnnotation annotation = iMethod.getAnnotation(BpmnCommonIndexUtils.JAVA_TASK_TYPE_ANNOTATION);
		if (annotation.getElementName().equals(BpmnCommonIndexUtils.JAVA_TASK_MODEL_TYPE_MAP)) {
			try {
				if (StudioJavaUtil.isJavaArray(iMethod.getReturnType())) {
					ts.setArray(true);
//					isArrayReturnType = true;
				}

				if (StudioJavaUtil.isJavaPrimitive(iMethod.getReturnType())) {
					ts.setPrimitive(true);
					isPrimitiveReturnType = true;
				}

				for (IMemberValuePair pair :annotation.getMemberValuePairs()) {
					if (pair.getMemberName().equals("type")) {
						returnType = pair.getValue().toString();

					}
				}
				returnType = returnType.replace(BpmnCommonIndexUtils.JAVA_TASK_MODEL_TYPE + BpmnIndexUtils.DOT, "");
				if (ModelType.valueOf(returnType) == ModelType.CONCEPT_REFERENCE
						|| ModelType.valueOf(returnType) == ModelType.CONTAINED_CONCEPT
						|| ModelType.valueOf(returnType) == ModelType.PROCESS) {
					for (IMemberValuePair pair :annotation.getMemberValuePairs()) {
						if (pair.getMemberName().equals("uri")) {
//							returnTypeURI = pair.getValue().toString();
							ts.setUri(pair.getValue().toString());
						}
					}
				}

				ts.setPropertyType(ModelTypeHelper.getPropertyType(ModelType.valueOf(returnType), isPrimitiveReturnType));

			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} 
		return ts;
	}

	protected void getJavataskFunction(EObjectWrapper<EClass, EObject> userObjWrapper) {

		String methodName = null;
		String attrName = BpmnMetaModelExtensionConstants.E_ATTR_METHOD_NAME;

		if (userObjWrapper != null && attrName != null) {
			if (ExtensionHelper.isValidDataExtensionAttribute(userObjWrapper, attrName)) {
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(userObjWrapper);
				if (valueWrapper != null) {
					Object attribute = valueWrapper.getAttribute(attrName);
					if (attribute instanceof String)
						methodName = (String) attribute;
					if (methodName == null)
						methodName = "";
				}
			} else if (userObjWrapper.containsAttribute(attrName)) {
				methodName = BpmnModelUtils.getAttributeValue(userObjWrapper,  attrName);
			}
		}

		List<SymbolEntryImpl> symbols = BpmnModelUtils.getMethodArguments(userObjWrapper.getEInstance());

		List<String> parameterTypes = new ArrayList<String>();
		List<String> parameterNames = new ArrayList<String>();
		List<Boolean> parameterArrays = new ArrayList<Boolean>();
		List<Boolean> parameterIsPrimitivies = new ArrayList<Boolean>();

		for (SymbolEntryImpl symbol: symbols) {
			EObjectWrapper<EClass, EObject> typeWrapper = EObjectWrapper.wrap(symbol.getType());
			String type = typeWrapper.toString();

			parameterTypes.add(type);
			String name = symbol.getKey();
			parameterNames.add(name);

			parameterArrays.add(symbol.isArray());
			parameterIsPrimitivies.add(symbol.isPrimitive());
		}

		String functionSignature = GeneralJavaTaskPropertySection.getMethodSignature(methodName,
				parameterTypes.toArray(new String[parameterTypes.size()]), 
				parameterNames.toArray(new String[parameterNames.size()]),
				parameterArrays.toArray(new Boolean[parameterArrays.size()]),
				parameterIsPrimitivies.toArray(new Boolean[parameterIsPrimitivies.size()]));

	}
	
	private Map<String, String> validateJavaArgsUri(IMethod iMethod) {
		Map<String, String> uriMap = new HashMap<String, String>();
		try {

			// Get Args
			List<TypeSymbol> lst = getJavaTaskMethodArguments(iMethod);
			for (TypeSymbol ts : lst) {
				if (ts.getUri() != null) {
					uriMap.put(ts.getName(), ts.getUri());
				}
			}
			// get Return arg
			TypeSymbol ts = getJavaTaskReturnTypes(iMethod);
			if (ts.getUri() != null) {
				uriMap.put(ts.getName(), ts.getUri());
			}
		} catch (Exception e) {

		}
		return uriMap;
	}
	
	public List<TypeSymbol> getJavaTaskMethodArguments(IMethod iMethod) {
		List<TypeSymbol> argTypes = new ArrayList<>();
		try {
			for(ILocalVariable variable :iMethod.getParameters()) {
				
				IAnnotation annotation = variable.getAnnotation(BpmnCommonIndexUtils.JAVA_TASK_TYPE_ANNOTATION);
				String var = variable.getElementName();
				
				if (annotation.getElementName().equals(BpmnCommonIndexUtils.JAVA_TASK_MODEL_TYPE_MAP)) {
					
					boolean isArray = false;
					boolean isPrimitive = false; 
					
					if (StudioJavaUtil.isJavaArray(variable.getTypeSignature())) {
						isArray = true;
					}
					
					if (StudioJavaUtil.isJavaPrimitive(variable.getTypeSignature())) {
						isPrimitive = true;
					}
					
					String uri = "";
					String type = "";
					
					for (IMemberValuePair pair :annotation.getMemberValuePairs()) {
						if (pair.getMemberName().equals("type")) {
							type = pair.getValue().toString();
						}
					}
					
					type = type.replace(BpmnCommonIndexUtils.JAVA_TASK_MODEL_TYPE + BpmnIndexUtils.DOT, "");
					if (ModelType.valueOf(type) == ModelType.CONCEPT_REFERENCE
							|| ModelType.valueOf(type) == ModelType.CONTAINED_CONCEPT
							|| ModelType.valueOf(type) == ModelType.PROCESS) {
						for (IMemberValuePair pair :annotation.getMemberValuePairs()) {
							if (pair.getMemberName().equals("uri")) {
								uri = pair.getValue().toString();
							}
						}
					}
					
					TypeSymbol ts = new TypeSymbol();
					ts.setName(var);
					ts.setArray(isArray);
					ts.setPrimitive(isPrimitive);
					ts.setPropertyType(ModelTypeHelper.getPropertyType(ModelType.valueOf(type), isPrimitive));
					ts.setUri(uri);
					argTypes.add(ts);
					
				} 
			}
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return argTypes;
		
	}
	
}
