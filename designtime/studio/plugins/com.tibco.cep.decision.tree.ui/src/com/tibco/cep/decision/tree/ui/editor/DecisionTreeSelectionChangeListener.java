package com.tibco.cep.decision.tree.ui.editor;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.setWorkbenchSelection;

import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.ui.part.EditorPart;

import com.tibco.cep.diagramming.drawing.SelectionChangeListener;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

/*
@author ssailapp
@date Sep 14, 2011
 */

@SuppressWarnings("serial")
public class DecisionTreeSelectionChangeListener extends SelectionChangeListener
{
	private EditorPart editor;
	private DecisionTreeDiagramManager manager;
    private TSEGraph selectedGraph;

	/**
	 * @param manager
	 */
	public DecisionTreeSelectionChangeListener(DecisionTreeDiagramManager manager) {
		super(manager);
		this.manager = manager;
		this.editor = manager.getEditor();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.SelectionChangeListener#selectionChanged(com.tomsawyer.graphicaldrawing.event.TSESelectionChangeEventData)
	 */
	/* // TODO - TSV 9.2
	public void selectionChanged(TSESelectionChangeEventData eventData)
	{
		try {
			if (manager.isSelectedMultipleGraph() || eventData.getSource() == null) {
				return;
			}
			boolean isGraphSelected = isGraphSelected();

			TSTool tool = manager.getDrawingCanvas().getToolManager().getActiveTool();
			if(tool != null && (tool instanceof DecisionTreeCreateNodeTool || tool instanceof DecisionTreeCreateEdgeTool)){
				return;
			}

			if (eventData.getType() == TSESelectionChangeEvent.NODE_SELECTION_CHANGED)
			{
				TSENode node = (TSENode) eventData.getSource();
				if (node.isSelected())
				{
					onNodeSelected(node);
				}
				else
				{
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
			else if (eventData.getType() == TSESelectionChangeEvent.EDGE_SELECTION_CHANGED)
			{
				TSEEdge edge = (TSEEdge) eventData.getSource();
				if (edge.isSelected())
				{
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
	*/
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.SelectionChangeListener#onNodeSelected(com.tomsawyer.graphicaldrawing.TSENode)
	 */
	protected void onNodeSelected(final TSENode tsNode)
    {
		//setWorkbenchSelection(tsNode, editor);
		if ("gtk".equals(SWT.getPlatform())) {
			super.onNodeSelected(tsNode);
		} else {
			setWorkbenchSelection(tsNode, editor);
		}
		
		((DecisionTreeEditor)editor).enableEdit(true);
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.SelectionChangeListener#onEdgeSelected(com.tomsawyer.graphicaldrawing.TSEEdge)
     */
    protected void onEdgeSelected(TSEEdge tsEdge)
    {
    	setWorkbenchSelection(tsEdge, editor);
    	
    	((DecisionTreeEditor)editor).enableEdit(true);
    	
    }
 
    
    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.SelectionChangeListener#onGraphSelected(com.tomsawyer.graphicaldrawing.TSEGraph)
     */
    protected void onGraphSelected(TSEGraph tsGraph)
    {
    	setWorkbenchSelection(tsGraph, editor);
    	
    	((DecisionTreeEditor)editor).enableEdit(false);
    }
    
    @SuppressWarnings({ "unused", "rawtypes" })
	private boolean isGraphSelected(){
    	selectedGraph = null;
    	Iterator i = manager.getGraphManager().graphs().iterator();
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

