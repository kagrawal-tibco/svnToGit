package com.tibco.cep.studio.ui.statemachine.diagram;


import java.util.Iterator;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.EditorPart;

import com.tibco.cep.diagramming.drawing.SelectionChangeListener;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditor;
import com.tibco.cep.studio.ui.statemachine.diagram.tool.StateMachineCreateEdgeTool;
import com.tibco.cep.studio.ui.statemachine.diagram.tool.StateMachineCreateNodeTool;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
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
@SuppressWarnings("serial")
public class StateMachineSelectionChangeListener extends SelectionChangeListener
{
	private EditorPart editor;
	private StateMachineDiagramManager manager;
    private TSEGraph selectedGraph;

	/**
	 * @param manager
	 */
	public StateMachineSelectionChangeListener(StateMachineDiagramManager manager) {
		super(manager);
		this.manager = manager;
		this.editor = manager.getEditor();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.SelectionChangeListener#selectionChanged(com.tomsawyer.graphicaldrawing.event.TSESelectionChangeEventData)
	 */
	@Override
	public void selectionChanged(TSESelectionChangeEventData eventData)
	{
		try {
			if (manager.isSelectedMultipleGraph() || eventData.getSource() == null) {
				return;
			}
			boolean isGraphSelected = isGraphSelected();

			TSTool tool = manager.getDrawingCanvas().getToolManager().getActiveTool();
			if(tool != null && (tool instanceof StateMachineCreateNodeTool || tool instanceof StateMachineCreateEdgeTool)){
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

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.SelectionChangeListener#onNodeSelected(com.tomsawyer.graphicaldrawing.TSENode)
	 */
	protected void onNodeSelected(final TSENode tsNode)
    {
		//setWorkbenchSelection(tsNode, editor);
		super.onNodeSelected(tsNode);
		setWorkbenchSelection(tsNode, editor);
		((StateMachineEditor)editor).enableEdit(true);
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.SelectionChangeListener#onEdgeSelected(com.tomsawyer.graphicaldrawing.TSEEdge)
     */
    protected void onEdgeSelected(TSEEdge tsEdge)
    {
    	super.onEdgeSelected(tsEdge);
    	setWorkbenchSelection(tsEdge, editor);
    	((StateMachineEditor)editor).enableEdit(true);
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.SelectionChangeListener#onGraphSelected(com.tomsawyer.graphicaldrawing.TSEGraph)
     */
    protected void onGraphSelected(TSEGraph tsGraph)
    {
    	super.onGraphSelected(tsGraph);
    	setWorkbenchSelection(tsGraph, editor);
    	((StateMachineEditor)editor).enableEdit(false);
    	((StateMachineEditor)editor).enablePaste(true); // paste is still valid when the graph is selected
    	((StateMachineEditor)editor).enableUndoRedoContext();
    }

    private boolean isGraphSelected(){
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

    protected void setWorkbenchSelection(Object object, IEditorPart editor) {
    	StudioUIUtils.setWorkbenchSelection(object, editor);
    }
}
