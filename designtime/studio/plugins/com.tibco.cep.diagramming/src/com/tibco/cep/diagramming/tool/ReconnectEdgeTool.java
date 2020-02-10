package com.tibco.cep.diagramming.tool;

import java.awt.Cursor;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.swing.editing.tool.TSEReconnectEdgeTool;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;

/**
 * 
 * @author sasahoo
 *
 */
public class ReconnectEdgeTool extends TSEReconnectEdgeTool{
	
	boolean inFirstClick = false;
	boolean mouseMoved = false;
	

	/* (non-Javadoc)
	 * @see com.tomsawyer.interactive.swing.editing.tool.TSEBuildEdgeTool#onMouseReleased(java.awt.event.MouseEvent)
	 */
	@SuppressWarnings("unused")
	public void onMouseReleased(MouseEvent event) {
		
		//Resetting the Cursor
		if(getActionCursor()!=null){
			Cursor c = new Cursor(Cursor.DEFAULT_CURSOR);
			this.setActionCursor(c);
			this.setCursor(c);
		}
		// We consume left mouse button events
		if ((event.getModifiers() & MouseEvent.BUTTON1_MASK) != 0
			&& !event.isPopupTrigger()) {
//			inFirstClick = true; // Direct Reconnect.
            // Temporary till we solve bend points issue for reconnect
			if (this.isReconnecting()) {
				TSConstPoint point = this.getWorldPoint(event);
				boolean foundTarget = this.possibleTargetAt(point);
				if (foundTarget) {
					boolean canConnectEdge = this.canConnectEdge(point);
					if (canConnectEdge||true) {
						// if found a possible target while
						// building and can connect the edge then connect the edge
						if(this.getTargetNode() == null) return; // Fixed NPE Null Target Selection
						if (this.getTargetNode().getUserObject() == null) {
							// the above picked the wrong target node, look for one with a user object set
							List nodeList = this.getHitTesting().getNodeListAt(point, getGraph(), true);
							for (Iterator iterator = nodeList.iterator(); iterator.hasNext();) {
								TSENode node = (TSENode) iterator.next();
								if (node.getUserObject() != null) {
									setNewNode(node);
									break;
								}
							}
						}
						if (this.getTargetNode().getUserObject() == null) {
							return;
						}
						this.addDirtyRegion(this.getTargetNode());
						if ((!event.isShiftDown()) && (!event.isControlDown())) {
							this.getSwingCanvas().deselectAll(false);
						}
						if (this.isActionAllowed()) {
//							fireModelChanged(); //commented for testing purpose
							this.connectEdge();
//							this.setBuildingEdge(false);
						}
					}
					// allow user to add a bend in the
					// source node by dragging from the source connector
					else if (this.inFirstClick ) {
						this.addBendPoint(point);
					}					
				}
				// allow user to add a bend by dragging from the source node
				else if (this.inFirstClick) {
					if (this.mouseMoved) {
						this.addBendPoint(point);
					}
					else {
						this.cancelAction();
					}
				}
				this.inFirstClick = false;
			}
		}
		else {
			super.onMouseReleased(event);
		}
	}
	public boolean isActionAllowed(){return false;};
	
	public TSEEdge connectEdge() {
		if (this.isActionAllowed()) {
//			this.setBuildingEdge(false);
			return super.connectEdge();
		}
		else {
			this.cancelAction();
			return null;
		}
	}
	
	/**
	 * This method responds to the mouse cursor being moved. If this
	 * tool is in the process of building the edge, a paint
	 * request to the Swing canvas is issued.
	 */
	public void onMouseMoved(MouseEvent event)
	{
		super.onMouseMoved(event);		
	}

	/**
	 * This method responds to the mouse being dragged. If this tool
	 * is in the process of reconnecting the edge, a paint request to
	 * the Swing canvas is issued.
	 */
	public void onMouseDragged(MouseEvent event)
	{
		super.onMouseDragged(event);
	}

	/**
	 * This method responds to the mouse cursor being moved into
	 * the Swing canvas. If the auto-scrolling timer is running,
	 * this method stops it.
	 */
	public void onMouseEntered(MouseEvent event)
	{
		super.onMouseEntered(event);
	}

	/**
	 * This method responds to the mouse cursor being moved outside
	 * the canvas. If the tool is in the middle of adding an edge,
	 * it starts the autoscrolling timer.
	 */
	public void onMouseExited(MouseEvent event)
	{
		super.onMouseExited(event);
	}
	
	/* (non-Javadoc)
	 * @see com.tomsawyer.interactive.swing.editing.tool.TSECreateNodeTool#onMousePressed(java.awt.event.MouseEvent)
	 */
	public void onMousePressed(MouseEvent event) {
		if (event.getButton() == MouseEvent.BUTTON3 || event.isPopupTrigger()) {
			//On right click, the default select tool reset. 
			getSwingCanvas().getToolManager().setActiveTool(TSViewingToolHelper.getSelectTool(getSwingCanvas().getToolManager()));
		}else {
			super.onMousePressed(event);
		}
	}
	
	/**
	* This method returns whether or not the input event has grid alignment enabled.
	*/
	public boolean isAlignmentEnabled(InputEvent event) {
		return false;
	}
	
	/**
	 * Model Changes due to Edge ReConnect
	 */
	public void fireModelChanged(){
		//Override this
	}
	
	public void dispose() {
		this.otherNode = null;
		this.oldNode = null;
		this.dummyNode = null;
	}

}