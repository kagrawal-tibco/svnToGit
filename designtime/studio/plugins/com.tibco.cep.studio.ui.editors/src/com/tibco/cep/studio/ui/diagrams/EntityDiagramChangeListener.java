package com.tibco.cep.studio.ui.diagrams;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.diagramming.drawing.DiagramChangeListener;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;


/**
 * @author ggrigore
 * 
 * This class is a selection listener registered by the DrawingManager.
 */
public class EntityDiagramChangeListener extends DiagramChangeListener<DiagramManager> {

	private static final long serialVersionUID = 1L;
	boolean layout_If_Not_Entity=false;
	public EntityDiagramChangeListener(DiagramManager manager) {
		super(manager);
	}

	protected void onNodeAdded(TSENode tsNode)
    {
		//super.onNodeAdded(tsNode);
   		Entity entity = this.getEntity(tsNode); 
    	/*System.out.println("Entity added. " + entity);*/
    }

    protected void onNodeDeleted(TSENode tsNode)
    {
    	//super.onNodeDeleted(tsNode);
    	Entity entity = this.getEntity(tsNode); 
    	/*System.out.println("Entity deleted: " + entity);*/
   		// Delete this concept from EMF model as well.
    }
    
    protected void onEdgeAdded(TSEEdge tsEdge)
    {
    	//super.onEdgeAdded(tsEdge);
    	if(tsEdge==null)
    		return;
    	Entity srcEntity = this.getEntity((TSENode) tsEdge.getSourceNode());
    	Entity tgtEntity = this.getEntity((TSENode) tsEdge.getTargetNode());
    	
    	int type = -1;
    	if (tsEdge.getUserObject() == null) {
    		if ((srcEntity != null) && (tgtEntity != null)){
    		/*System.out.println("Added edge of an unknown type between " + srcEntity.getName() + " and " + tgtEntity.getName());	*/
    		}
    	}
    	else {
    		type = ((Integer) tsEdge.getUserObject()).intValue();
    	}

    	if (type == DrawingCanvas.INHERITANCE_LINK_TYPE) {
    		/*System.out.println("Inheritance link added between " +
    			srcEntity.getName() + " and " + tgtEntity.getName());*/
    		// we know srcConcept extends tgtConcept...
   		}
    	else if (type == DrawingCanvas.CONTAINMENT_LINK_TYPE) {
    		// TODO: more work to do here, because right now users can't
    		// interactively specify which attribute they're linking...
    		// same problem for reference link below.
   		}
    	else if (type == DrawingCanvas.REFERENCE_LINK_TYPE) {
   		}
    }

    protected void onEdgeDeleted(TSEEdge tsEdge)
    {
    	//super.onEdgeDeleted(tsEdge);
//    	Entity srcEntity = this.getEntity((TSENode) tsEdge.getSourceNode());
//    	Entity tgtEntity = this.getEntity((TSENode) tsEdge.getTargetNode());

//   		System.out.println("Edge deleted between " +
//   			srcEntity.getName() + " and " + tgtEntity.getName());

   		// TODO: update EMF model
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onNodeMoved(com.tomsawyer.graphicaldrawing.TSENode)
     */
    protected void onNodeMoved(TSENode tsNode) {
    	super.onNodeMoved(tsNode);
    	String name = "unknown";
    	if (this.getEntity(tsNode) != null)
    		name = this.getEntity(tsNode).getName();
    /*	System.out.println("Moved node: " + name);*/
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onNodeResized(com.tomsawyer.graphicaldrawing.TSENode)
     */
    protected void onNodeResized(TSENode tsNode) {
    	super.onNodeResized(tsNode);
    	String name = "unknown";
    	if (this.getEntity(tsNode) != null)
    		name = this.getEntity(tsNode).getName();
    	/*System.out.println("Moved node: " + name);*/
    }

    
    protected void onEdgeMoved(TSEEdge tsEdge)
    {
    	super.onEdgeMoved(tsEdge);
    }

    protected void onEdgeReconnected(TSEEdge tsEdge)
    {
    	// TODO: update EMF model
    	super.onEdgeReconnected(tsEdge);
    }
    
    private Entity getEntity(TSENode tsNode) {
    	Entity entity = null;
    	if(tsNode!=null)
    		if (tsNode.getUserObject() instanceof EntityNodeData) {
    			if(tsNode.getUserObject()!=null){
    				EntityNodeData entityNodeData = (EntityNodeData)tsNode.getUserObject();
    				if(entityNodeData.getEntity()!=null)
    					entity = (Entity) entityNodeData.getEntity().getUserObject();
    			}
    		}    	
		return entity;
	}
    
}
