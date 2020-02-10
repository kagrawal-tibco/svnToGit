package com.tibco.cep.diagramming.drawing;

import java.util.Iterator;

import com.tomsawyer.drawing.TSLabel;
import com.tomsawyer.drawing.TSPNode;
import com.tomsawyer.drawing.events.TSDrawingChangeEvent;
import com.tomsawyer.drawing.events.TSDrawingChangeEventData;
import com.tomsawyer.drawing.events.TSDrawingChangeListener;
import com.tomsawyer.graph.events.TSGraphChangeAdapter;
import com.tomsawyer.graph.events.TSGraphChangeEvent;
import com.tomsawyer.graph.events.TSGraphChangeEventData;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;
import com.tomsawyer.util.events.TSGroupEventData;


/**
 * @author ggrigore
 * 
 * This class is a drawing change listener registered by the DrawingManager.
 */
@SuppressWarnings("serial")
public class DiagramChangeListener<D extends DiagramManager> extends TSGraphChangeAdapter implements TSDrawingChangeListener {
	
	protected D manager;
	protected boolean LAYOUT_CHANGE_FLAG = true;
	
	/**
	 * @param manager
	 */
	public DiagramChangeListener(D manager) {
		this.manager = manager;
	}

	/* (non-Javadoc)
	 * @see com.tomsawyer.graph.event.TSGraphChangeAdapter#graphChanged(com.tomsawyer.graph.event.TSGraphChangeEvent)
	 */
	public void graphChanged(TSGraphChangeEvent event) {
		  if (event.isGroupEvent()) {
			   Iterator<?> dataIter = ((TSGroupEventData) event.getData()).getDataList().iterator();
			   while (dataIter.hasNext()) {
				   this.graphChanged((TSGraphChangeEventData) dataIter.next());
			   }
		  } else {
			  this.graphChanged((TSGraphChangeEventData) event.getData());
		  }
	}
	
	/* (non-Javadoc)
	 * @see com.tomsawyer.graph.event.TSGraphChangeAdapter#graphChanged(com.tomsawyer.graph.event.TSGraphChangeEventData)
	 */
	public void graphChanged(TSGraphChangeEventData eventData)
	{
		try {
			if (eventData.getType() == TSGraphChangeEvent.NODE_INSERTED)
			{
				TSENode node = (TSENode) eventData.getSource();
				this.onNodeAdded(node);
			}
			else if (eventData.getType() == TSGraphChangeEvent.NODE_DISCARDED ||
					eventData.getType() == TSGraphChangeEvent.NODE_REMOVED)
			{
				TSENode node = (TSENode) eventData.getSource();
				this.onNodeDeleted(node);
			}        
			else if(eventData.getType() == TSGraphChangeEvent.NODE_RENAMED){
				TSENode node = (TSENode) eventData.getSource();
				this.onNodeRenamed(node);
			}
			else if (eventData.getType() == TSGraphChangeEvent.EDGE_INSERTED)
			{
				TSEEdge edge = (TSEEdge) eventData.getSource();
				this.onEdgeAdded(edge);
			}
			else if (eventData.getType() == TSGraphChangeEvent.EDGE_DISCARDED ||
					eventData.getType() == TSGraphChangeEvent.EDGE_REMOVED)
			{
				TSEEdge edge = (TSEEdge) eventData.getSource();
				this.onEdgeDeleted(edge);
			}else if(eventData.getType() == TSGraphChangeEvent.EDGE_ENDNODE_CHANGED){
				TSEEdge edge = (TSEEdge) eventData.getSource();
				this.onEdgeReconnected(edge);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.tomsawyer.drawing.event.TSDrawingChangeListener#drawingChanged(com.tomsawyer.drawing.event.TSDrawingChangeEvent)
	 */
	public void drawingChanged(TSDrawingChangeEvent event) {
		 if (event.isGroupEvent()) {
			 Iterator<?> dataIter = ((TSGroupEventData) event.getData()).getDataList().iterator();
			 while (dataIter.hasNext()) {
				 this.drawingChanged((TSDrawingChangeEventData) dataIter.next());
			 }
		 } else {
			   this.drawingChanged((TSDrawingChangeEventData) event.getData());
		 }	
	}

	/* (non-Javadoc)
	 * @see com.tomsawyer.drawing.event.TSDrawingChangeListener#drawingChanged(com.tomsawyer.drawing.event.TSDrawingChangeEventData)
	 */
	public void drawingChanged(TSDrawingChangeEventData eventData) {
		try {
			if(eventData.getSource() == null ) return;
			if (eventData.getType() == TSDrawingChangeEvent.NODE_MOVED) {
				TSENode node = (TSENode) eventData.getSource();
				this.onNodeMoved(node);
			}else if(eventData.getType() == TSDrawingChangeEvent.NODE_RESIZED){
				TSENode node = (TSENode) eventData.getSource();
				this.onNodeResized(node);
			}
			else if (eventData.getType() == TSDrawingChangeEvent.BEND_INSERTED ||
					eventData.getType() == TSDrawingChangeEvent.BEND_DISCARDED ||
					eventData.getType() == TSDrawingChangeEvent.BEND_MOVED ||
					eventData.getType() == TSDrawingChangeEvent.BEND_REMOVED) {
				TSPNode node = (TSPNode)eventData.getSource();
				TSEEdge edge = null;
				if(node != null) {
					edge = (TSEEdge) node.getOwner();
					this.onEdgeMoved(edge);
				}
			}
			else if (eventData.getType() == TSDrawingChangeEvent.LABEL_RENAMED ||
					eventData.getType() == TSDrawingChangeEvent.LABEL_ATTRIBUTE_CHANGED) {
				// it's either TSEEdgeLabel or TSENodeLabel
				this.onLabelChanged((TSLabel) eventData.getSource());
			}
			else if (eventData.getType() == TSDrawingChangeEvent.EDGE_ENDCONNECTOR_CHANGED) {
//				TSEEdge edge = (TSEEdge) eventData.getSource();
//				this.onEdgeReconnected(edge);
			}
			else if (eventData.getType() == TSDrawingChangeEvent.LABEL_MOVED) {
				 Object source = eventData.getSource();
				 if(source instanceof TSENodeLabel){
					 this.onNodeLabelMoved((TSENodeLabel)source);
				 }
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void onNodeLabelMoved(TSENodeLabel source) {
		manager.onNodeLabelMoved(source);
		
	}

	/**
	 * This method is designed to be overridden but such methods
	 * should always call super first.
	 */
	protected void onNodeAdded(TSENode tsNode) {
		manager.onNodeAdded(tsNode);
		layoutDiagramOnChange();
		
    }

	/**
	 * This method is designed to be overridden but such methods
	 * should always call super first.
	 */
    protected void onNodeDeleted(TSENode tsNode)
    {
    	
		layoutDiagramOnChange();
		
    }

	/**
	 * This method is designed to be overridden but such methods
	 * should always call super first.
	 */
    protected void onNodeRenamed(TSENode tsNode)
    {
    	if(LAYOUT_CHANGE_FLAG) {
    	//	layoutDiagramOnChange();
    	}
    }
    
	/**
	 * This method is designed to be overridden but such methods
	 * should always call super first.
	 */
    protected void onEdgeAdded(TSEEdge tsEdge)
    {
    	if(LAYOUT_CHANGE_FLAG) {
    		layoutDiagramOnChange();
    	}
    }

	/**
	 * This method is designed to be overridden but such methods
	 * should always call super first.
	 */
    protected void onEdgeDeleted(TSEEdge tsEdge)
    {
    	if(LAYOUT_CHANGE_FLAG) {
    		layoutDiagramOnChange();
    	}
    }

	/**
	 * This method is designed to be overridden but such methods
	 * should always call super first.
	 */
    protected void onNodeResized(TSENode tsNode)
    {
    	if(LAYOUT_CHANGE_FLAG) {
    	//	layoutDiagramOnChange();
    	}
    }

    /**
	 * This method is designed to be overridden but such methods
	 * should always call super first.
	 */
    protected void onNodeMoved(TSENode tsNode)
    {
    	manager.onNodeMoved(tsNode);
    }
    
	/**
	 * This method is designed to be overridden but such methods
	 * should always call super first.
	 */
    protected void onEdgeMoved(TSEEdge tsEdge)
    {
    	manager.onEdgeMoved(tsEdge);
//    	if(LAYOUT_CHANGE_FLAG) {
//    		layoutDiagramOnChange();
//        	}
    }

	/**
	 * This method is designed to be overridden but such methods
	 * should always call super first.
	 */
    protected void onEdgeReconnected(TSEEdge tsEdge)
    {
    	if(LAYOUT_CHANGE_FLAG) {
        		layoutDiagramOnChange();
        	}
    }
    
	/**
	 * This method is designed to be overridden but such methods
	 * should always call super first.
	 */
    protected void onLabelChanged(TSLabel label)
    {
    	/*if(LAYOUT_CHANGE_FLAG) {
    		layoutDiagramOnChange();
    	}*/
    }
    
    /**
     * Layout Diagram On Change call in a Swing Utilities.. 
     */
    protected void layoutDiagramOnChange(){
    	manager.layoutDiagramOnChange();
    }
    
    public void resetToSelectToolAfterChange() {
    	// if (manager.isResetToolOnChange()) {
    		manager.getDrawingCanvas().getToolManager().setActiveTool(
				TSViewingToolHelper.getSelectTool(manager.getDrawingCanvas().getToolManager()));
		//	StudioUIUtils.resetPaletteSelection();
    		
    	// }
    }
    
   
    
	public void setLAYOUT_CHANGE_FLAG(boolean layout_change_flag) {
		LAYOUT_CHANGE_FLAG = layout_change_flag;
	}
	

}

