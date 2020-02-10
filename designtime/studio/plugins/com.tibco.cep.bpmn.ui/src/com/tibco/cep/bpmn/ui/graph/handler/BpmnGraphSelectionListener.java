package com.tibco.cep.bpmn.ui.graph.handler;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramHelper;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.editor.BpmnEditor;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tibco.cep.diagramming.drawing.SelectionChangeListener;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.events.TSESelectionChangeEvent;
import com.tomsawyer.graphicaldrawing.events.TSESelectionChangeEventData;

public class BpmnGraphSelectionListener extends SelectionChangeListener {

	private static final long serialVersionUID = 8639327727319477662L;
	private BpmnDiagramManager manager;
	private BpmnEditor editor;
	private TSEGraph selectedGraph;

	public BpmnGraphSelectionListener(BpmnDiagramManager manager) {
		super(manager);
		this.manager = manager;
		this.editor = manager.getEditor();
	}
	
	/* (non-Javadoc)
	 * @see com.tomsawyer.graphicaldrawing.event.TSESelectionChangeAdapter#selectionChanged(com.tomsawyer.graphicaldrawing.event.TSESelectionChangeEventData)
	 */
	public void selectionChanged(TSESelectionChangeEventData eventData) {
		try{			
			if (eventData.getSource() == null) {
				return;
			} else {
				enableWindowActions(eventData);
				if (manager.isSelectedMultipleGraph()) {
					BpmnGraphUtils.setWorkBenchMultiSelection(manager,eventData,editor,false);
					return;
				}
			}
			
			boolean isGraphSelected = BpmnGraphUtils.isGraphSelected(manager);
			
			if(isGraphSelected)
				selectedGraph = BpmnGraphUtils.getSelectedGraph(manager);
			
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
			}else if (eventData.getType() == TSESelectionChangeEvent.CONNECTOR_SELECTION_CHANGED){
				TSEConnector connector = (TSEConnector) eventData.getSource();
				if (connector.isSelected())
				{
					onConnectorSelected(connector);
				}else
				{
					TSEGraphManager graphMgr = manager.getGraphManager();
					if ((!graphMgr.hasSelected(true) &&
							((TSEGraph)graphMgr.getMainDisplayGraph()).isSelected()) ||
							(isGraphSelected &&
									connector.getOwnerGraph() == selectedGraph
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
					if(!manager.getGraphManager().hasSelected(true) && BpmnGraphUtils.isGraphSelected(manager)){
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
			BpmnUIPlugin.log(e);
		}
	}

	private void enableWindowActions(TSESelectionChangeEventData eventData){
		if (eventData.getType() == TSESelectionChangeEvent.NODE_SELECTION_CHANGED)
		{
			editor.enableActions();
		}
	}
	
	protected void onNodeSelected(final TSENode tsNode)
    {
		tsNode.setTooltipText(BpmnDiagramHelper.getNodeToolTip(tsNode));
		BpmnGraphUtils.setWorkbenchSelection(tsNode, editor, false);
		super.onNodeSelected(tsNode);

    }
	
	protected void onConnectorSelected(final TSEConnector tsConnector)
    {
		tsConnector.setTooltipText(BpmnDiagramHelper.getNodeToolTip(tsConnector));
		BpmnGraphUtils.setWorkbenchSelection(tsConnector, editor, false);

    }
	
    protected void onEdgeSelected(TSEEdge tsEdge)
    {
    	BpmnGraphUtils.setWorkbenchSelection(tsEdge, editor, false);
    }

    protected void onGraphSelected(TSEGraph tsGraph)
    {
    	BpmnGraphUtils.setWorkbenchSelection(tsGraph, editor, false);
    }
    

   
   
	


}
