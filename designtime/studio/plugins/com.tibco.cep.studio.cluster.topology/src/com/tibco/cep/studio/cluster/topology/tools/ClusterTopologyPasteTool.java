package com.tibco.cep.studio.cluster.topology.tools;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.openError;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseEvent;
import java.io.IOException;

import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyDiagramManager;
import com.tibco.cep.studio.cluster.topology.model.impl.DeploymentUnitImpl;
import com.tibco.cep.studio.cluster.topology.utils.ClusterTopologyConstants;
import com.tibco.cep.studio.cluster.topology.utils.Messages;
import com.tomsawyer.drawing.TSDGraph;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.swing.TSEHitTesting;
import com.tomsawyer.interactive.swing.editing.tool.TSEPasteTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEditingToolHelper;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;

/**
 * 
 * @author mgoel
 *
 */
public class ClusterTopologyPasteTool extends TSEPasteTool{
	ClusterTopologyDiagramManager clusterTopologyDiagramManager;

	public ClusterTopologyPasteTool(
			ClusterTopologyDiagramManager clusterTopologyDiagramManager) {
				this.clusterTopologyDiagramManager = clusterTopologyDiagramManager;
	}

	@Override
	public void onMousePressed(MouseEvent event) {
		if (event.getButton() == MouseEvent.BUTTON3 || event.isPopupTrigger()) {
			//On right click, the default select tool reset. 
			getSwingCanvas().getToolManager().setActiveTool(TSViewingToolHelper.getSelectTool(getSwingCanvas().getToolManager()));
		}else {
			//For Region Paste Validation
			TSEHitTesting hitTesting = this.getHitTesting();
			// get the point where the mouse is pressed.
			TSConstPoint point = this.getNonalignedWorldPoint(event);
			TSEGraph hiteTestingGraph = hitTesting.getGraphAt(point, this.getGraph());
			for(DataFlavor fv:getSwingCanvas().getClipboard().getAvailableDataFlavors()){
				if(fv.getRepresentationClass() == TSDGraph.class){
					try {
						Object object = getSwingCanvas().getClipboard().getData(fv);
						TSEGraphManager manager = (TSEGraphManager)object;
						TSDGraph graph = (TSDGraph)manager.getAnchorGraph();
						for(Object obj: graph.nodes()){
							TSENode node = (TSENode)obj;
							//System.out.println(node);
							//if(!node.getName().toString().trim().equals("")){
								if(node.getAttributeValue(ClusterTopologyConstants.NODE_TYPE) != null) {
									if(node.getAttributeValue(ClusterTopologyConstants.NODE_TYPE).equals(ClusterTopologyConstants.PU_NODE)){
								
									if(!(hiteTestingGraph.getUserObject() instanceof DeploymentUnitImpl)){
											openError(clusterTopologyDiagramManager.getEditor().getSite().getShell(), Messages.getString("error_title"),"Processing Units cannot exist outside a Deployment Unit");
											cancelAction();
										
									}/*else{
										DeploymentUnitImpl duImpl=(DeploymentUnitImpl) hiteTestingGraph.getUserObject();
										ProcessingUnitConfigImpl puImpl=(ProcessingUnitConfigImpl)obj;
										puImpl.setDeploymentUnitImpl(duImpl);
									}*/
										
									}
									else if(node.getAttributeValue(ClusterTopologyConstants.NODE_TYPE).equals(ClusterTopologyConstants.HOST_NODE)||
											node.getAttributeValue(ClusterTopologyConstants.NODE_TYPE).equals(ClusterTopologyConstants.DEPLOYMENT_UNIT_NODE)){
										
										if((hiteTestingGraph.getUserObject() instanceof DeploymentUnitImpl)){
											openError(clusterTopologyDiagramManager.getEditor().getSite().getShell(), Messages.getString("error_title"),"Only Processing Unit can exist inside a Deployment Unit");
											cancelAction();
										
									}
									}
								}
							//}
						}
					} catch (UnsupportedFlavorException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			
		}
		super.onMousePressed(event);
		}
	}

	@Override
	public void onMouseReleased(MouseEvent event) {
		if (event.getButton() == MouseEvent.BUTTON3 || event.isPopupTrigger()) {
			//On right click, the default select tool reset. 
			getSwingCanvas().getToolManager().setActiveTool(TSViewingToolHelper.getSelectTool(getSwingCanvas().getToolManager()));
		}else {
			super.onMouseReleased(event);
			//For resetting the state machine paste tool after each paste operation
			getSwingCanvas().getToolManager().setActiveTool(TSEditingToolHelper.getPasteTool(getSwingCanvas().getToolManager()));
		}
		
	}
	

}
