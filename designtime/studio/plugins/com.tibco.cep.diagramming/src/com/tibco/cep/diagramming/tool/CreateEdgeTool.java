package com.tibco.cep.diagramming.tool;

import static com.tibco.cep.diagramming.tool.CreateToolUtils.handleOnPaletteToolSelection;
import static com.tibco.cep.diagramming.tool.CreateToolUtils.removeCreateToolListeners;

import java.awt.Cursor;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateEdgeTool;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;

public class CreateEdgeTool extends TSECreateEdgeTool  implements ICreateTool, IPartListener {

	private DiagramManager diagramManager;
	private CreateToolListener createDiagramToolKeyListener;
	protected boolean inFirstClick = false;
	protected boolean mouseMoved = false;
    protected Cursor createEdgeCursor;
    
	/**
	 * @param diagramManager
	 */
	public CreateEdgeTool(DiagramManager diagramManager) {
		this.diagramManager = diagramManager;
		createDiagramToolKeyListener = new CreateToolListener(diagramManager, this);
		controlKeylistnerMap.clear();
		paletteEntryList.clear();
		diagramManager.getDiagramEditorControl().addKeyListener(createDiagramToolKeyListener);
		if (diagramManager.getEditor() != null)
			diagramManager.getEditor().getEditorSite().getPage().addPartListener(this);
		else
			System.err.println("NULL EDITOR!");

		diagramManager.addCreateToolPartListener(this);
	}
    
	/* (non-Javadoc)
	 * @see com.tomsawyer.interactive.swing.editing.tool.TSEBuildEdgeTool#onMouseReleased(java.awt.event.MouseEvent)
	 */
	public void onMouseReleased(MouseEvent event) {
		
		//Resetting the Cursor
		if(getActionCursor()!= null && getActionCursor().getName().equalsIgnoreCase("Invalid")) {
//			Cursor c = new Cursor(Cursor.DEFAULT_CURSOR);
			this.setActionCursor(createEdgeCursor);
			this.setCursor(createEdgeCursor);
		}
		
		// We consume left mouse button events
		if ((event.getModifiers() & MouseEvent.BUTTON1_MASK) != 0
			&& !event.isPopupTrigger()) {
			if (this.isBuildingEdge()) {
				TSConstPoint point = this.getWorldPoint(event);
				boolean foundTarget = this.possibleTargetAt(point); // this sets the target node, but doesn't always pick the correct one
 				if (foundTarget) {
					//boolean canConnectEdge = this.canConnectEdge(point);
					// if found a possible target while
					// building and can connect the edge then connect the edge
					if(this.getTargetNode() == null) return; // Fixed NPE Null Target Selection
					if (this.getTargetNode().getUserObject() == null) {
						// the above picked the wrong target node, look for one with a user object set
						List nodeList = this.getHitTesting().getNodeListAt(point, getGraph(), true);
						for (Iterator iterator = nodeList.iterator(); iterator.hasNext();) {
							TSENode node = (TSENode) iterator.next();
							if (node.getUserObject() != null) {
								setTargetNode(node);
								break;
							}
						}
					}
					
					this.addDirtyRegion(this.getTargetNode());
											
					if ((!event.isShiftDown()) && (!event.isControlDown())) {
						this.getSwingCanvas().deselectAll(false);
					}

					if (this.isActionAllowed()) {
						this.connectEdge();
						this.setBuildingEdge(false);
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
			this.setBuildingEdge(false);
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
		if (this.isBuildingEdge())
		{
		}
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
		
		if (this.isBuildingEdge())
		{
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tomsawyer.interactive.swing.editing.tool.TSECreateNodeTool#onMousePressed(java.awt.event.MouseEvent)
	 */
	public void onMousePressed(MouseEvent event) {
		if (event.getButton() == MouseEvent.BUTTON3 || event.isPopupTrigger()) {
			//On right click, the default select tool reset. 
			getSwingCanvas().getToolManager().setActiveTool(TSViewingToolHelper.getSelectTool(getSwingCanvas().getToolManager()));
			//Resetting Palette selection as well here
			resetPaletteSelection();
		}else {
			//First we need to cache the edge cursor, other wise it would have default cursor 
			if(getActionCursor() != null &&	getActionCursor().getName().equalsIgnoreCase("CreateEdge32x32")) {
				if(createEdgeCursor == null){
					createEdgeCursor = getActionCursor();
				}
			}
			super.onMousePressed(event);
		}
	}
	
	/**
	* This method returns whether or not the input event has grid alignment enabled.
	*/
	public boolean isAlignmentEnabled(InputEvent event) {
		return false;
	}
	
	public DiagramManager getDiagramManager() {
		return diagramManager;
	}

	@Override
	public void partActivated(IWorkbenchPart part) {
		controlKeylistnerMap.clear();
		paletteEntryList.clear();
		handleOnPaletteToolSelection(part, diagramManager, this, paletteEntryList, controlKeylistnerMap);
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		removeCreateToolListeners(controlKeylistnerMap);
	}

	@Override
	public void dispose() {
		if (!diagramManager.getDiagramEditorControl().isDisposed()) {
			diagramManager.getDiagramEditorControl().removeKeyListener(createDiagramToolKeyListener);
		}
		createDiagramToolKeyListener = null;
		if (diagramManager.getEditor() != null) {
			diagramManager.getEditor().getEditorSite().getPage().removePartListener(this);
		}
		diagramManager = null;
	}
	
	@Override
	public void partDeactivated(IWorkbenchPart part) {
		removeCreateToolListeners(controlKeylistnerMap);
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}
	//Override this for reseting palette selection
	public void resetPaletteSelection(){}
}
