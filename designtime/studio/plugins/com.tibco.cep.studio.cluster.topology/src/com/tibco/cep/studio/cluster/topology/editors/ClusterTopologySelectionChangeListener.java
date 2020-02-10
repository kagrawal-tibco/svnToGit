package com.tibco.cep.studio.cluster.topology.editors;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.setWorkbenchSelection;

import java.util.Iterator;

import org.eclipse.ui.part.EditorPart;

import com.tibco.cep.diagramming.drawing.SelectionChangeListener;
import com.tibco.cep.studio.cluster.topology.tools.ClusterTopologyCreateEdgeTool;
import com.tibco.cep.studio.cluster.topology.tools.ClusterTopologyCreateNodeTool;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.events.TSESelectionChangeEvent;
import com.tomsawyer.graphicaldrawing.events.TSESelectionChangeEventData;
import com.tomsawyer.interactive.tool.TSTool;


/**
 * @author ggrigore
 *
 *
 * This class is a selection listener registered by the DrawingManager.
 * It listens for events and whenever objects get selected or unselected,
 * it updates the property sheet and/or source window (as appropriate) to
 * show the information associated with the selected object.
 */

// TODO:
// This is way too similar to StateMachineSelectionChangeListener,
// must abstract out to base class and put in diagramming plugin
//

@SuppressWarnings("serial")
public class ClusterTopologySelectionChangeListener extends SelectionChangeListener
{
	private EditorPart editor;
	private ClusterTopologyDiagramManager manager;
    private TSEGraph selectedGraph;

	public ClusterTopologySelectionChangeListener(ClusterTopologyDiagramManager manager) {
		super(manager);
		this.manager = manager;
		this.editor = manager.getEditor();
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.SelectionChangeListener#selectionChanged(com.tomsawyer.graphicaldrawing.event.TSESelectionChangeEventData)
	 */
	@Override
	public void selectionChanged(TSESelectionChangeEventData eventData) {
		try{
			if (manager == null) {
				return;
			}
			if (manager.isSelectedMultipleGraph() || eventData.getSource() == null) {
				return;
			}
			
			boolean isGraphSelected = isGraphSelected();
			if (manager.getDrawingCanvas() == null) {
				return;
			}
			if (!manager.getDrawingCanvas().isDisplayable()) {
				return;
			}
			if (manager.getDrawingCanvas().getToolManager() ==  null) {
				return;
			}
			if (manager.getDrawingCanvas().getToolManager().getActiveTool() ==  null) {
				return;
			}
			TSTool tool = manager.getDrawingCanvas().getToolManager().getActiveTool();
			if (tool != null && 
					(tool instanceof ClusterTopologyCreateNodeTool || 
							tool instanceof ClusterTopologyCreateEdgeTool)) {
				return;
			}
			if (eventData.getType() == TSESelectionChangeEvent.NODE_SELECTION_CHANGED) {
				TSENode node = (TSENode) eventData.getSource();
				if (node.isSelected()) {
					onNodeSelected(node);
				} else {
					TSEGraphManager graphMgr = manager.getGraphManager();
					if ((!graphMgr.hasSelected(true) &&
							((TSEGraph)graphMgr.getMainDisplayGraph()).isSelected()) ||
							(isGraphSelected &&
									node.getOwnerGraph() == selectedGraph
									&& !graphMgr.hasSelected(true)) ||
									(isGraphSelected && !graphMgr.hasSelected(true))) {
						onGraphSelected(selectedGraph);
					}
				}
			}
			else if (eventData.getType() == TSESelectionChangeEvent.EDGE_SELECTION_CHANGED) {
				TSEEdge edge = (TSEEdge) eventData.getSource();
				if (edge.isSelected()) {
					onEdgeSelected(edge);
				}
				else
				{
					if (!manager.getGraphManager().hasSelected(true) && !isGraphSelected)
					{
						onGraphSelected((TSEGraph) edge.getOwnerGraph());// Nothing is left selected
					}
					if(!manager.getGraphManager().hasSelected(true) && isGraphSelected()){
						if(selectedGraph!=null)
							onGraphSelected(selectedGraph);
					}
				}
			}
			else if (eventData.getType() == TSESelectionChangeEvent.GRAPH_SELECTION_CHANGED)
			{
				TSEGraph graph = (TSEGraph) eventData.getSource();
				if (graph.isSelected() && !manager.getGraphManager().hasSelected(true)) {
					onGraphSelected(graph);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
    }
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.SelectionChangeListener#onNodeSelected(com.tomsawyer.graphicaldrawing.TSENode)
	 */
	protected void onNodeSelected(final TSENode tsNode) {
		//setWorkbenchSelection(tsNode, editor);
		/*if ("gtk".equals(SWT.getPlatform())) {
			super.onNodeSelected(tsNode);
		} else */{
			super.onNodeSelected(tsNode);
			setWorkbenchSelection(tsNode, editor);
			((ClusterTopologyEditor)editor).enableEdit(true);
			
		}
	}

    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.SelectionChangeListener#onEdgeSelected(com.tomsawyer.graphicaldrawing.TSEEdge)
     */
    protected void onEdgeSelected(TSEEdge tsEdge) {
    	setWorkbenchSelection(tsEdge, editor);
    	((ClusterTopologyEditor)editor).enableEdit(true);
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.SelectionChangeListener#onGraphSelected(com.tomsawyer.graphicaldrawing.TSEGraph)
     */
    protected void onGraphSelected(TSEGraph tsGraph) {
    	setWorkbenchSelection(tsGraph, editor);
    	((ClusterTopologyEditor)editor).enableEdit(false);
    }
    
	private boolean isGraphSelected() {
    	selectedGraph = null;
    	Iterator<?> i = manager.getGraphManager().graphs().iterator();
    	while (i.hasNext()) {
    		selectedGraph = (TSEGraph) i.next();
    		if (selectedGraph.isSelected()) {
    			return true;
    		}
    	}
    	selectedGraph = null;
    	return false;
    }
}