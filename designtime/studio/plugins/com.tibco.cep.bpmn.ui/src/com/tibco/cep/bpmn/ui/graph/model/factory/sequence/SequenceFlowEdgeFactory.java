package com.tibco.cep.bpmn.ui.graph.model.factory.sequence;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.editor.IBpmnDiagramManager;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tibco.cep.bpmn.ui.graph.model.AbstractEdgeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.BPMNCurvedEdgeUI;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.ui.TSObjectUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public class SequenceFlowEdgeFactory extends AbstractEdgeUIFactory {

	private static final long serialVersionUID = -1593046621228451403L;


	public SequenceFlowEdgeFactory(String name, EClass type, BpmnLayoutManager layoutManager) {
		super(name, type, layoutManager);
	}
	
	@Override
	public TSEObjectUI initGraphUI(Object ...args) {
		BPMNCurvedEdgeUI edgeUI = new BPMNCurvedEdgeUI();
		edgeUI.setAntiAliasingEnabled(true);
		edgeUI.setCurvature(100);
		// temporary for testing:
		return edgeUI;
	}
	
	
	public static void refreshEdgeUiAttachededToGateway(TSENode sourceNode, IBpmnDiagramManager manager){
		EObject userObject = (EObject)sourceNode.getUserObject();
		if(userObject != null){
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject);
			if(BpmnModelClass.EXCLUSIVE_GATEWAY.equals(wrap.getEClassType()) ||
					BpmnModelClass.INCLUSIVE_GATEWAY.equals(wrap.getEClassType())){
				String defaultSeqId = "";
				EObject attribute = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_DEFAULT);
				if(attribute != null){
					EObjectWrapper<EClass, EObject> seqWrap = EObjectWrapper.wrap(attribute);
					defaultSeqId = (String)seqWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				}
				TSEEdge defaultEdge = null;
				if(defaultSeqId != null && !defaultSeqId.trim().isEmpty())
					defaultEdge = BpmnGraphUtils.getEdge(sourceNode, defaultSeqId);
				List<?> outEdges = sourceNode.outEdges();
				for (Object object : outEdges) {
					if(object instanceof TSEEdge)
						setDefaultUi((TSEEdge)object,manager, false);
				}
				
				if(defaultEdge != null)
					setDefaultUi(defaultEdge,manager, true);

			}
		}
	}
	
	private static void setDefaultUi(TSEEdge edge , IBpmnDiagramManager manager, boolean def){
		TSObjectUI ui = edge.getUI();
		if(ui instanceof BPMNCurvedEdgeUI){
			BPMNCurvedEdgeUI curveUi = (BPMNCurvedEdgeUI)ui;
			curveUi.setAsDefault(def);
			if(manager != null)
				manager.refreshEdge(edge);
		}
	}
}
