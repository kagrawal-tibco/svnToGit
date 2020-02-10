package com.tibco.cep.bpmn.core.refactoring;

import java.util.ArrayList;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.Messages;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonModelUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.studio.core.builder.AbstractBPMNProcessor;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.Process;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.ProcessElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.refactoring.StudioRefactoringParticipant;
import com.tibco.cep.studio.core.validation.DefaultSharedResourceValidator;

/**
 * 
 * @author sasahoo
 *
 */
public class BPMNProcessor extends AbstractBPMNProcessor {

	public static final String PROCESS_TYPE_PRIVATE = "Private";
	public static final String PROCESS_TYPE_PUBLIC = "Public";

	@Override
	public List<String> getProcesses(IProject project) {
		List<String> publicProcesses = new ArrayList<String>();
		List<String> privateProcesses = new ArrayList<String>();
		startupShutdownProcesses.clear();
		List<EObject> proObjects = BpmnIndexUtils.getAllProcesses(project.getName());
		for (EObject object : proObjects) {
			EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(object);
			Object attribute = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_PROCESS_TYPE);
			String path =  BpmnIndexUtils.getElementPath(object);
			path = path.replace(CommonIndexUtils.DOT + CommonIndexUtils.PROCESS_EXTENSION, "");
			if (attribute != null) {
				EEnumLiteral type =(EEnumLiteral)attribute;
				if (type!= null) {
					if (getAccessIdentifier() != null) {
						if (type.equals(BpmnModelClass.ENUM_PROCESS_TYPE_PUBLIC)) {
							getStartupShutdownProcesses(processWrapper, path);
							publicProcesses.add(path);
						} 
						else {
							privateProcesses.add(path);
						}
					} 
				}
			}
		}
		if (getAccessIdentifier().equals(PROCESS_TYPE_PUBLIC)) {
			return publicProcesses;
		} else {
			return privateProcesses;
		}
	}
	
	/**
	 * @param processWrapper
	 * @param path
	 */
	private void getStartupShutdownProcesses(EObjectWrapper<EClass, EObject> processWrapper, String path) {
		if(isStartupOrShutDownProcess(processWrapper)){
			path = path.replace(CommonIndexUtils.DOT + CommonIndexUtils.PROCESS_EXTENSION, "");
			startupShutdownProcesses.add(path);
		}
	}
	
	
	private boolean isStartupOrShutDownProcess(EObjectWrapper<EClass, EObject> processWrapper){
		boolean isStartShutDownProcess = true;
		Collection<EObject> flowNodes = BpmnCommonModelUtils.getFlowNodes(processWrapper, BpmnModelClass.START_EVENT);
		for (EObject object : flowNodes ) {
			EObjectWrapper<EClass, EObject> startEventWrapper = EObjectWrapper.wrap(object);
			EList<EObject> list = startEventWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
			if (list != null && list.size() > 0) {
				isStartShutDownProcess = false;
				break;
			}
		}
		
		
		if(isStartShutDownProcess){
			flowNodes = BpmnCommonModelUtils.getFlowNodes(processWrapper, BpmnModelClass.RECEIVE_TASK);
			isStartShutDownProcess = (flowNodes.size() == 0);
		}
		
		if(isStartShutDownProcess){
			flowNodes = BpmnCommonModelUtils.getFlowNodes(processWrapper, BpmnModelClass.PARALLEL_GATEWAY);
			isStartShutDownProcess = (flowNodes.size() == 0);
		}
		
		if(isStartShutDownProcess){
			flowNodes = BpmnCommonModelUtils.getFlowNodes(processWrapper, BpmnModelClass.INCLUSIVE_GATEWAY);
			isStartShutDownProcess = (flowNodes.size() == 0);
		}
	
		if(isStartShutDownProcess){
			flowNodes = BpmnCommonModelUtils.getFlowNodes(processWrapper, BpmnModelClass.INFERENCE_TASK);
			isStartShutDownProcess = (flowNodes.size() == 0);
		}
		
		if (isStartShutDownProcess) {
			flowNodes = BpmnCommonModelUtils.getFlowNodes(processWrapper,
					BpmnModelClass.CALL_ACTIVITY);
			for (EObject eObject : flowNodes) {
				if (isStartShutDownProcess) {
					Object attachedResource = BpmnModelUtils
							.getAttachedResource(eObject);
					if (attachedResource != null
							&& attachedResource instanceof EObject) {
						EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
								.wrap((EObject) attachedResource);
						isStartShutDownProcess = isStartupOrShutDownProcess(wrap);
					}
				}
			}
		}

		return isStartShutDownProcess;
	}

	@Override
	public boolean refactor(Object elementToRefactor,
			ArrayList<ProcessElement> processElements,
			StudioRefactoringParticipant refactoringParticipant) {
		if (elementToRefactor != null && elementToRefactor instanceof IFile) {
			IFile file = (IFile) elementToRefactor;
			if (file.getFileExtension().equals(
					CommonIndexUtils.PROCESS_EXTENSION)) {
				IPath path = file.getProjectRelativePath().removeFileExtension();
				String portableString = path.toPortableString();
				if(!portableString.startsWith("/"))
					portableString = "/"+portableString;
				for (ProcessElement processElement : processElements) {
					if (processElement instanceof Process) {
						Process proc = (Process) processElement;
						String uri = proc.uri;
						if (uri.equals(portableString)) {
							if (refactoringParticipant.isRenameRefactor()) {
								String newElementName = refactoringParticipant
										.getNewElementName();
								int lastIndexOf = portableString
										.lastIndexOf(path.lastSegment());
								if (lastIndexOf != -1) {
									String substring = portableString
											.substring(0, lastIndexOf);
									proc.uri = substring + newElementName;
									return true;
								}
								break;
							} else if (refactoringParticipant.isMoveRefactor()) {
								String newElementPath = refactoringParticipant
										.getNewElementPath();
								proc.uri = newElementPath+path.lastSegment();
								return true;
							} else if (refactoringParticipant
									.isDeleteRefactor()) {
								processElements.remove(processElement);
								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}

	@Override
	public void validate(List<String> processURIs, IResource resource, boolean isAgentClass, DefaultSharedResourceValidator validator) {

		IProject project = resource.getProject();
		List<String> procList = new ArrayList<String>();
		Map<String, EObject> map = new HashMap<String, EObject>();
		for (EObject object : BpmnIndexUtils.getAllProcesses(project.getName())) {
			String path =  BpmnIndexUtils.getElementPath(object);
			path = path.replace(CommonIndexUtils.DOT + CommonIndexUtils.PROCESS_EXTENSION, "");
			map.put(path, object);
			procList.add(path);
		}
		Set<EObject> procObjs = new HashSet<EObject>();
		for (String uri : processURIs) {
			if(uri.isEmpty()){
				validator.reportValidateProblem(resource, Messages.getString("bpmn.process.uri.empty"), DefaultSharedResourceValidator.SEVERITY_ERROR);
			}
			String path = uri.replace(CommonIndexUtils.DOT + CommonIndexUtils.PROCESS_EXTENSION, "");
			if (!procList.contains(path)) {
				validator.reportValidateProblem(resource, Messages.getString("bpmn.valid.process.uri", uri), DefaultSharedResourceValidator.SEVERITY_ERROR);
			} else {
				procObjs.add(map.get(path));
			}
		}
		if (isAgentClass) {
			if (procObjs.size() > 0) {
				Set<String> callActSet = new HashSet<String>();
//				for (EObject object: procObjs) {
//					callActivityProcesses(object, map, callActSet);
//				}
				for (String callActuri : callActSet) {
					if (!processURIs.contains(callActuri)) {
						validator.reportValidateProblem(resource, Messages.getString("bpmn.valid.call.act.process.uri", callActuri), DefaultSharedResourceValidator.SEVERITY_ERROR);
					}
				}
			}
		} 
	}
	
//	/**
//	 * @param object
//	 * @param map
//	 * @param callActSet
//	 */
//	private void callActivityProcesses(EObject object, Map<String, EObject> map, Set<String> callActSet) {
//		EObjectWrapper<EClass, EObject> callActObjWrapper = EObjectWrapper.wrap(object);
//		Collection<EObject> collections = BpmnCommonModelUtils.getFlowNodes(callActObjWrapper, BpmnModelClass.CALL_ACTIVITY);
//		String callerPath =  BpmnIndexUtils.getElementPath(object);
//		callerPath = callerPath.replace(CommonIndexUtils.DOT + CommonIndexUtils.PROCESS_EXTENSION, "");
//		if (collections != null) {
//			for (EObject ob : collections) {
//				EObjectWrapper<EClass, EObject> rcallActObjWrapper = EObjectWrapper.wrap(ob);
//				EObject attribute = (EObject) rcallActObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_CALLED_ELEMENT);
//				if (attribute != null) {
//					String calledPath =  BpmnIndexUtils.getElementPath(attribute);
//					calledPath = calledPath.replace(CommonIndexUtils.DOT + CommonIndexUtils.PROCESS_EXTENSION, "");
//					if (calledPath.equals(callerPath) 
//							|| callActSet.contains(calledPath)) {
//						continue;
//					}
//					callActSet.add(calledPath);
//					EObject calledPathobject = map.get(calledPath);
//					callActivityProcesses(calledPathobject, map, callActSet);
//				} 
//			}
//		}
//	}

}