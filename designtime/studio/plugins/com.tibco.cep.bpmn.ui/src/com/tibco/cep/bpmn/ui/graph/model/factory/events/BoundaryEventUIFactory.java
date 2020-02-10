package com.tibco.cep.bpmn.ui.graph.model.factory.events;

import java.util.List;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.editor.IBpmnDiagramManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.ConnectorNodeUI;
import com.tibco.cep.diagramming.drawing.BaseDiagramManager;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

/**
 * @author majha
 *
 */
public class BoundaryEventUIFactory  extends AbstractConnectorUIFactory {
	private static final long serialVersionUID = -1443402185065302213L;
	
	public BoundaryEventUIFactory(TSENode parentNode, String name,String referredBEResource , String toolId, BpmnLayoutManager layoutManager, EClass eventDefType) {
		super(parentNode, name, referredBEResource, toolId, layoutManager, BpmnModelClass.BOUNDARY_EVENT, eventDefType);
	}
	
	public TSEObjectUI initGraphUI(Object ...args) {
		return new ConnectorNodeUI((EClass)args[0]);
	}


	/**
	 * @param node
	 */
	public void layoutNode(TSEConnector connector){
		BaseDiagramManager diagramManager = getLayoutManager().getDiagramManager();
		if(diagramManager instanceof IBpmnDiagramManager)
			diagramManager.refreshNode(parentNode);
	}

	/**
	 * @param node
	 */
	public void decorateNode(TSEConnector connector){
		connector.setUI(getConnectorUI());
		@SuppressWarnings("unchecked")
		List<TSEConnector> connList = parentNode.connectors();
		connector.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		// this is assuming we want to stack them at the bottom
		// x: 0 means center, -0.5 is left border, 0.5 is right border
		// y: 0 means center, 0.5 is top border, -0.5 is bottom border
		connector.setProportionalYOffset(0.5);
		double size = 20.0;
		int topSideConnector = 3;
		if(connList.size() > 9){
			size = 20*3/ (connList.size() -6);
			topSideConnector =(connList.size() -6);
		}else if(connList.size() <= 3){
			topSideConnector = connList.size();
		}
		
		// reshuffle existing connectors as well. We need to see how many
		// connectors node has, then evenly distribute them. So for example,
		// if we had 2, that means there are 3 equal sections, so the first
		// one would have x offset of (-0.5 + 1/3), second (-0.5 + 2/3).
		double increment = 1.0 / (topSideConnector + 1);
		double i = 0;
		int connectorCounter = 0;
		for (TSEConnector conn : connList) {
			connectorCounter++;
			conn.setSize(size, size);
			if(connectorCounter <= topSideConnector){
				i += increment;
				conn.setProportionalXOffset(-0.5 + i);
				conn.setProportionalYOffset(0.5 );
			}
			if(connectorCounter == (topSideConnector + 1)){
				conn.setProportionalXOffset(-0.5);
				conn.setProportionalYOffset(0.25);
			}
			
			if(connectorCounter == (topSideConnector + 2)){
				conn.setProportionalXOffset(0.5);
				conn.setProportionalYOffset(0.25);
			}
			
			if(connectorCounter == (topSideConnector + 3)){
				conn.setProportionalXOffset(-0.5);
				conn.setProportionalYOffset(-0.25);
			}
			
			if(connectorCounter == (topSideConnector + 4)){
				conn.setProportionalXOffset(0.5);
				conn.setProportionalYOffset(-0.25);
			}
			
			if(connectorCounter == (topSideConnector + 5)){
				conn.setProportionalXOffset(-0.25);
				conn.setProportionalYOffset(-0.5);
			}
			
			if(connectorCounter == (topSideConnector + 6)){
				conn.setProportionalXOffset(0.25);
				conn.setProportionalYOffset(-0.5);
			}
			
			
		}
	}

}
