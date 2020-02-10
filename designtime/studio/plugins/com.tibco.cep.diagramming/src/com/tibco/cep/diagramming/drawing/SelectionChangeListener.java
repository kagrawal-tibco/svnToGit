package com.tibco.cep.diagramming.drawing;

import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.events.TSESelectionChangeAdapter;
import com.tomsawyer.graphicaldrawing.events.TSESelectionChangeEvent;
import com.tomsawyer.graphicaldrawing.events.TSESelectionChangeEventData;


/**
 * @author ggrigore
 *
 *
 * This class is a selection listener registered by the DrawingManager.
 * It listens for events and whenever objects get selected or unselected,
 * it updates the property sheet and/or source window (as appropriate) to
 * show the information associated with the selected object.
 */
public class SelectionChangeListener extends TSESelectionChangeAdapter
{
	private static final long serialVersionUID = 1L;

	protected DiagramManager manager;

	/**
	 * @param manager
	 */
	public SelectionChangeListener(DiagramManager manager) {
		this.manager = manager;
	}

	/* (non-Javadoc)
	 * @see com.tomsawyer.graphicaldrawing.event.TSESelectionChangeAdapter#selectionChanged(com.tomsawyer.graphicaldrawing.event.TSESelectionChangeEventData)
	 */
	public void selectionChanged(TSESelectionChangeEventData eventData)
	{
		try{
			if (manager.isSelectedMultipleGraph() || eventData.getSource() == null) {
				return;
			}
			if (eventData.getType() == TSESelectionChangeEvent.NODE_SELECTION_CHANGED)
			{
				TSENode node = (TSENode) eventData.getSource();
				// if we clicked on a node and that node is selected.
				if (node.isSelected())
				{
					onNodeSelected(node);
				}
				else
				{
					if (!((TSEGraph) node.getOwnerGraph()).hasSelected())
					{
						// Nothing is left selected
						this.onGraphSelected((TSEGraph) node.getOwnerGraph());
					}
				}
			}
			else if (eventData.getType() == TSESelectionChangeEvent.EDGE_SELECTION_CHANGED)
			{
				TSEEdge edge = (TSEEdge) eventData.getSource();

				// if we clicked on an edge and that edge is selected.
				if (edge.isSelected())
				{
					this.onEdgeSelected(edge);
				}
				else
				{
					if (!((TSEGraph) edge.getOwnerGraph()).hasSelected())
					{
						// Nothing is left selected
						this.onGraphSelected((TSEGraph) edge.getOwnerGraph());
					}
				}
			}
			else if (eventData.getType() == TSESelectionChangeEvent.GRAPH_SELECTION_CHANGED)
			{
				TSEGraph graph = (TSEGraph) eventData.getSource();
				this.onGraphSelected(graph);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void onNodeSelected(TSENode tsNode)
	{
	}

	protected void onEdgeSelected(TSEEdge tsEdge)
	{
	}

	protected void onGraphSelected(TSEGraph tsGraph)
	{
	}

}
