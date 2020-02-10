/**
 * 
 */
package com.tibco.cep.webstudio.server.ui.tools;

import java.util.List;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.server.model.ProcessElementTypes;
import com.tibco.cep.webstudio.server.ui.ProcessActiveToolRepository;
import com.tibco.cep.webstudio.server.ui.utils.ProcessDiagramUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graph.TSGraphMember;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;
import com.tomsawyer.interactive.command.TSCommand;
import com.tomsawyer.model.TSModelElement;
import com.tomsawyer.model.schema.TSSchema;
import com.tomsawyer.view.behavior.toolcustomization.rule.action.TSWebToolRuleAction;
import com.tomsawyer.view.drawing.TSModelDrawingView;
import com.tomsawyer.view.drawing.command.TSInsertModelElementAtCommand;
import com.tomsawyer.web.server.drawing.TSWebCanvas;

/**
 * @author dijadhav
 * 
 */
public class NewFlowElementToolRuleAction implements TSWebToolRuleAction {
	ProcessActiveToolRepository activeToolManager = ProcessActiveToolRepository
			.getInstance();

	@Override
	public TSCommand onAction(Object object, TSModelDrawingView view,
			TSConstPoint mouseLocation) {

		String type = getType();
		String elementType = activeToolManager.getActiveToolType();
		String extendedType = getExtendedType();
		TSModelElement flowElement = null;
		String typename = elementType.trim().replaceAll(" ", "_");
		BpmnModelElement modelElement = new BpmnModelElement();
		modelElement.setMouseLocation(mouseLocation);
		
		if ("SubProcess".equals(typename)) {
			flowElement = view.getModel().newModelElement("Flow");

			TSSchema schema = view.getViewDefinition().getSchema();

			schema.initModelElement(flowElement);
			int i = 0;

			flowElement.setAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID,
					activeToolManager.getArtifactName() + "." + typename + "_"
							+ i);
			while (!schema.isUniqueIdentifiers(flowElement, view.getModel())) {
				++i;
				flowElement.setAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID,
						activeToolManager.getArtifactName() + "." + typename
								+ "_" + i);
			}
			flowElement.setAttribute(
					ProcessConstants.PROCESS_FLOW_ATTRIBUTE_TYPE, type);

			flowElement.setAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_LABEL,
					activeToolManager.getArtifactName() + "." + typename + "_"
							+ i);
			flowElement.setAttribute(
					ProcessConstants.PROCESS_FLOW_ATTRIBUTE_PARENT,
					"Main_Display_Graph");

			TSModelElement subprocessTask = view.getModel().newModelElement(
					"Flow");

			subprocessTask.setAttribute(
					ProcessConstants.PROCESS_FLOW_ATTRIBUTE_PARENT,
					flowElement.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID));
			subprocessTask.setAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID,
					"Tab1.Sub_Process_Script_0");
			subprocessTask.setAttribute(ProcessConstants.ATTRIBUTE_LABEL,
					"Script_0");

			TSEObject startNodeObject = null;
			view.getModel().addElement(subprocessTask);
			view.updateView(subprocessTask);
			List<TSGraphObject> graphObjects = view.getMapper()
					.getGraphObjects(subprocessTask);
			if (null != graphObjects && !graphObjects.isEmpty()) {
				startNodeObject = (TSEObject) graphObjects.get(0);
			}
			TSEGraph childGraph = (TSEGraph) startNodeObject.getOwnerGraph();
			TSGraphMember owner = childGraph.getParent();
			if (owner instanceof TSENode) {
				TSENode node = (TSENode) owner;
				node.setSize(100, 20);
				node.setExpandedResizability(TSESolidObject.RESIZABILITY_NO_FIT);
				node.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
				TSENestingManager.expand(node);

				ProcessDiagramUtils.setLayoutOptionsForSubProcess(
						(TSEGraph) node.getChildGraph(), view);
				ProcessDiagramUtils.setFitNestedGraph(view, node, false);
			}

		}
		if (ProcessElementTypes.TextAnnotation.getName().equals(type)) {
			modelElement.setNewElement(true);
			modelElement.setElementType(type);
			modelElement.setElementName(typename);
			modelElement.setExtendedType(extendedType);
			modelElement.setTextValue(typename);
			flowElement = ProcessDiagramUtils.addAnnotationElement(view, activeToolManager,modelElement);

		} else {
			
			modelElement.setNewElement(true);
			modelElement.setElementType(type);
			modelElement.setElementName(typename);
			modelElement.setExtendedType(extendedType);
			flowElement = ProcessDiagramUtils.addFlowElement(view,activeToolManager,modelElement);
		}

		// Set the Current tool name to Select Tool
		((TSWebCanvas) view.getCanvas()).setCurrentToolName("Select Tool");
		((TSWebCanvas) view.getCanvas()).fitInCanvas(true);
		((TSWebCanvas) view.getCanvas()).setSyncViewportInClient(true);
		return new TSInsertModelElementAtCommand(view, flowElement,
				mouseLocation);
	}

	private String getType() {
		String emfTypeAttribute = activeToolManager.getActiveToolEmfType();
		if (emfTypeAttribute != null) {
			String emfType = emfTypeAttribute.toString();
			EClass emfTypeClass = BpmnMetaModel.getInstance().getEClass(
					ExpandedName.parse(emfType));
			if (null != emfTypeClass) {
				return emfTypeClass.getName();
			}
		}
		return null;

	}

	private String getExtendedType() {
		String extendedTypeAttribute = activeToolManager
				.getActiveToolExtendedType();
		if (extendedTypeAttribute != null) {
			String extendedType = extendedTypeAttribute.toString();
			if (!extendedType.isEmpty()) {
				EClass extendedTypeClass = BpmnMetaModel.getInstance()
						.getEClass(ExpandedName.parse(extendedType));
				if (null != extendedTypeClass) {
					return extendedTypeClass.getName();
				}
			}
		}
		return null;
	}
}
