package com.tibco.cep.bpmn.ui.validation;

import static com.tibco.cep.studio.core.util.CommonUtil.showWarnings;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultProcessIndex;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpoint;
import com.tibco.xml.data.primitive.ExpandedName;

public class DefaultBpmnResourceValidator extends DefaultResourceValidator {

	protected Map<String, Object> customeAttribMap = new HashMap<String, Object>();
	IResource resource = null;

	/**
	 * returns Model Object for an IResource
	 * @param resource
	 * @return
	 */
	protected EObject getBpmnModelObject(IResource resource){
		String resourcePath = BpmnIndexUtils.getFullPath(resource);
		EObject resourceElement = 
			BpmnIndexUtils.getElement(resource.getProject().getName(), resourcePath);
		return resourceElement;
		
	}
	
	
	public void setResource(IResource resource) {
		this.resource = resource;
	}
	
	public IResource getResource() {
		return resource;
	}
	
	
	protected int getNumNodes(DefaultProcessIndex pi) {
		return pi.getAllFlowNodes().size();
	}
	
	protected String getNodeType(String nodeId, DefaultProcessIndex pi) {		
		EObject eObj = pi.getElementById(nodeId);
		if(eObj == null)
			return null;
		ExpandedName en = BpmnMetaModel.getInstance().getExpandedName(eObj.eClass());
		return en.toString();
	}
	
	/**
	 * @param resource
	 * @param message
	 * @param location
	 * @param severity
	 * @param type
	 * @param customAttributes
	 */
	protected void reportGraphProblem(IResource resource, String message, String location, int severity, String type, String nodeType, int uniqueId, Map<String,Object> customAttributes) {
		if (!showWarnings(severity, resource.getFileExtension())) {
			return;
		}
		if(customAttributes == null) {
			customAttributes = new HashMap<String,Object>();
		}
//		EObject modelObj = getBpmnModelObject(resource);
//		if(modelObj == null)
//			modelObj = loadBpmnProcess(resource);
//		DefaultProcessIndex pi = new DefaultProcessIndex(modelObj);
		customAttributes.put(IProcessBreakpoint.PROCESS_BREAKPOINT_PROCESS_URI, resource.getFullPath().removeFirstSegments(1).makeAbsolute().removeFileExtension().toPortableString());
		customAttributes.put(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_ID, location);
		customAttributes.put(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_LOCATION, IProcessBreakpoint.TASK_BREAKPOINT_LOCATION.START.name());
		customAttributes.put(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_TASK_TYPE, nodeType);
		customAttributes.put(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_UNIQUE_ID, uniqueId);
		addMarker(resource, message, location, -1, -1, -1, severity, type, customAttributes);
	}
	
	protected EObject loadBpmnProcess(IResource processResource){
		EObject process = null;
		try {
			EList<EObject> resources = ECoreHelper.deserializeModelXMI(processResource, true);
			process = resources.get(0);
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
		return process;
	}

}
