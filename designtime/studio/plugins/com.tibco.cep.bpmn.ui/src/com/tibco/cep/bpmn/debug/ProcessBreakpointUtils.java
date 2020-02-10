package com.tibco.cep.bpmn.debug;


import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessAdapter;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultProcessIndex;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpoint;
import com.tibco.cep.studio.debug.core.process.IProcessBreakpointInfo;
import com.tibco.cep.studio.debug.core.process.ProcessBreakpointInfo;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.util.shared.TSAttributedObject;

public class ProcessBreakpointUtils {

	@SuppressWarnings("rawtypes")
	public static void addBreakpointAttributesWithMemberDetails(Map attributes, String typeName, IProcessBreakpointInfo info) {
//		attributes.put(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_ID, info.getGraphObjectId());
//		attributes.put(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_LOCATION, info.getLocation().ordinal());

	}

	public static IProcessBreakpointInfo getProcessBreakpointInfo(TSEObject graphObject) {
		final EObject userObject = (EObject) graphObject.getUserObject();
		final EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
		final String nodeId = userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		final int uniqueId = userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
		@SuppressWarnings("unused")
		final String nodeName = userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		final String nodeTaskType = BpmnMetaModel.getInstance().getExpandedName(userObject.eClass()).toString();
		TSGraph rootGraph = ((TSEGraph)graphObject.getOwnerGraph()).getGreatestAncestor();
		final EObject process = (EObject)rootGraph.getUserObject();
		DefaultProcessIndex pi = new DefaultProcessIndex(process);
		Collection<EObject> flowNodes = pi.getAllFlowNodes();
		ProcessAdapter p = new ProcessAdapter(process, null,(Object[])null);
		IProcessBreakpoint.TASK_BREAKPOINT_LOCATION breakPointLocation = IProcessBreakpoint.TASK_BREAKPOINT_LOCATION.START;
		if(graphObject instanceof TSAttributedObject){
			TSAttributedObject node = (TSAttributedObject) graphObject;
			IProcessBreakpoint.TASK_BREAKPOINT_LOCATION loc = (IProcessBreakpoint.TASK_BREAKPOINT_LOCATION) node.getAttributeValue(IProcessBreakpoint.PROCESS_BREAKPOINT_NODE_LOCATION);
			if(loc != null) {
				breakPointLocation = loc;
			}
		}
		final IProcessBreakpointInfo bi = new ProcessBreakpointInfo(p.getFullPath(),nodeId,nodeTaskType,breakPointLocation,uniqueId);
		return bi;
	}
	
	

}
