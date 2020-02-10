package com.tibco.cep.studio.ui.statemachine.diagram.tool;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.openError;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseEvent;
import java.io.IOException;

import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.Messages;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.STATE;
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
 * @author sasahoo
 *
 */
public class StateMachinePasteTool extends TSEPasteTool{

	private StateMachineDiagramManager stateMachineDiagramManager;
	
	public StateMachinePasteTool(StateMachineDiagramManager stateMachineDiagramManager){
		this.stateMachineDiagramManager = stateMachineDiagramManager;
	}
	
	public void dispose() {
		stateMachineDiagramManager = null;
	}

	/* (non-Javadoc)
	 * @see com.tomsawyer.interactive.swing.editing.tool.TSECreateNodeTool#onMousePressed(java.awt.event.MouseEvent)
	 */
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
							if(!node.getName().toString().trim().equals("")){
								if(node.getAttributeValue(StateMachineUtils.STATE_TYPE).equals(STATE.REGION)){
									if(hiteTestingGraph.getUserObject() instanceof StateComposite){
										StateComposite stateComposite =(StateComposite) hiteTestingGraph.getUserObject();
										if(!stateComposite.isConcurrentState()){
											openError(stateMachineDiagramManager.getEditor().getSite().getShell(), Messages.getString("error_title"), Messages.getString("error_message"));
											cancelAction();
										}
									}
								}
							}
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
	
	/* (non-Javadoc)
	 * @see com.tomsawyer.interactive.swing.editing.tool.TSEPasteTool#onMouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void onMouseReleased(MouseEvent event){
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