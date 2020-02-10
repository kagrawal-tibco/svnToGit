package com.tibco.cep.decision.tree.ui.tool;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseEvent;
import java.io.IOException;

import com.tibco.cep.decision.tree.ui.editor.DecisionTreeDiagramManager;
import com.tomsawyer.drawing.TSDGraph;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.swing.TSEHitTesting;
import com.tomsawyer.interactive.swing.editing.tool.TSEPasteTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEditingToolHelper;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;

public class DecisionTreePasteTool extends TSEPasteTool{

	@SuppressWarnings("unused")
	private DecisionTreeDiagramManager decisionTreeDiagramManager;
	
	public DecisionTreePasteTool(DecisionTreeDiagramManager decisionTreeDiagramManager){
		this.decisionTreeDiagramManager = decisionTreeDiagramManager;
	}
	
	public void dispose() {
		decisionTreeDiagramManager = null;
	}

	/* (non-Javadoc)
	 * @see com.tomsawyer.interactive.swing.editing.tool.TSECreateNodeTool#onMousePressed(java.awt.event.MouseEvent)
	 */
	@SuppressWarnings("unused")
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
								/*
								if(node.getAttributeValue(DecisionTreeUtils.NODE_TYPE).equals(NODE.SIMPLE)){
									if(hiteTestingGraph.getUserObject() instanceof StateComposite){
										StateComposite stateComposite =(StateComposite) hiteTestingGraph.getUserObject();
										if(!stateComposite.isConcurrentState()){
											openError(decisionTreeDiagramManager.getEditor().getSite().getShell(), Messages.getString("error_title"), Messages.getString("error_message"));
											cancelAction();
										}
									}
								}
								*/
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
			//For resetting the paste tool after each paste operation
			getSwingCanvas().getToolManager().setActiveTool(TSEditingToolHelper.getPasteTool(getSwingCanvas().getToolManager()));
		}
	}
}