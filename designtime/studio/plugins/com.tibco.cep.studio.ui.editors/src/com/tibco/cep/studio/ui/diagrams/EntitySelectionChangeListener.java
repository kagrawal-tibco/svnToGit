package com.tibco.cep.studio.ui.diagrams;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.SelectionChangeListener;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;


/**
 * @author ggrigore
 *
 *
 * This class is a selection listener registered by the DrawingManager.
 * It listens for events and whenever objects get selected or unselected,
 * it updates the property sheet and/or source window (as appropriate) to
 * show the information associated with the selected object.
 */
public class EntitySelectionChangeListener extends SelectionChangeListener
{
	public EntitySelectionChangeListener(DiagramManager manager) {
		super(manager);
	}

	private static final long serialVersionUID = 1L;


	protected void onNodeSelected(TSENode tsNode)
    {
		Entity entity = this.getEntity(tsNode);
		// System.out.println("Selected entity: " + entity.getName());
    }

    protected void onEdgeSelected(TSEEdge tsEdge)
    {
		System.out.println("Selected entity link.");
    }

    protected void onGraphSelected(TSEGraph tsGraph)
    {
    }
    
    private Entity getEntity(TSENode tsNode) {
    	Entity entity = null;
    	if (tsNode.getUserObject() instanceof EntityNodeData) {
    		entity = (Entity) 
    			((EntityNodeData)
    				tsNode.getUserObject()).getEntity().getUserObject();
    	}    	
		return entity;
	}    

}
