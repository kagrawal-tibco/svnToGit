package com.tibco.cep.diagramming.tool;


import static com.tibco.cep.diagramming.tool.CreateToolUtils.handleOnPaletteToolSelection;
import static com.tibco.cep.diagramming.tool.CreateToolUtils.removeCreateToolListeners;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.TSCommandManager;
import com.tomsawyer.interactive.command.editing.TSEAddNodeCommand;
import com.tomsawyer.interactive.swing.TSEHitTesting;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateNodeTool;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;

/**
 * 
 * @author ggrigore
 *
 */
public class CreateNodeTool extends TSECreateNodeTool implements ICreateTool, IPartListener {

	private DiagramManager diagramManager;
	private CreateToolListener createDiagramToolKeyListener;
	
	/**
	 * @param diagramManager
	 */
	public CreateNodeTool (DiagramManager diagramManager) {
		this.diagramManager = diagramManager;
		createDiagramToolKeyListener = new CreateToolListener(diagramManager, this);
		diagramManager.getDiagramEditorControl().addKeyListener(createDiagramToolKeyListener);
		if (diagramManager.getEditor() != null)
			diagramManager.getEditor().getEditorSite().getPage().addPartListener(this);
		diagramManager.addCreateToolPartListener(this);
	}
	
	/**
	 * This method creates a new node at the specified <code>point</code>.
	 * @return 
	 */
	public void createNode(com.tomsawyer.drawing.geometry.shared.TSConstPoint point)
	{
		//Resetting the Cursor
		if(getActionCursor()!=null){
			Cursor c = new Cursor(Cursor.DEFAULT_CURSOR);
			this.setActionCursor(c);
			this.setCursor(c);
		}
		// At this point we know we want to add a node. Grab the virtual
		// node that the user has been dragging.
		TSENode virtualNode = this.getVirtualNode();

		// Find all edges that the node intersects.
		TSEHitTesting hitTesting = this.getHitTesting();

		List<Object> edges = new Vector<Object>();

		hitTesting.findObjectsIntersectingRect(
				virtualNode.getBounds(),
				this.getGraph(),
				true,
				null,
				edges,
				null,
				null,
				null,
				null,
				null,
				null);	

		// If we are not over an edge, we use the base class' createNode
		// method which manages undo/redo using the swing canvas's
		// addNode method. If we are over an edge, you could instead allocate
		// a custom command command and transmit it through the canvas.
		if (edges.isEmpty()) {
			if(supportEntityCreationDialogInvoke()){
				boolean isEntityCreated =  doEntityCreation();
				if(isEntityCreated){
					//						super.createNode(point);
					try{ 
						//						TSToolPreferenceTailor tailor = new TSToolPreferenceTailor(this.getSwingCanvas().getPreferenceData());
						//						TSEObject object = hitTesting.getGraphObjectAt(point, this.getGraph(), tailor.isNestedGraphInteractionEnabled());
						//						if (object instanceof TSENode) {
						//							TSENode newNode = (TSENode)object;
						createNodeCommand(point);
						//						}
					}catch(Exception e){
						e.printStackTrace();
					}

				}else{
					cancelAction();
				}
			}else{
				super.createNode(point);
			}
		}
		else {
//			TSEEdge edge = (TSEEdge) edges.get(0);

			// do something with the edge ...
			// in this case, we simply add a node, the default behavior.
			createNodeCommand(point);
			
		}
	}

	protected boolean supportEntityCreationDialogInvoke(){
		return false;
	}
	
	protected boolean doEntityCreation(){
		return false;
	}
	
	protected TSENode createNodeCommand(com.tomsawyer.drawing.geometry.shared.TSConstPoint point){
		TSEAddNodeCommand command = new TSEAddNodeCommand(getGraph() ,point.getX(),point.getY());
		TSCommandManager commandManager = getSwingCanvas().getCommandManager();
		commandManager.transmit(command);
		return command.getNode();
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.tomsawyer.interactive.swing.editing.tool.TSECreateNodeTool#onMousePressed(java.awt.event.MouseEvent)
	 */
	public void onMousePressed(MouseEvent event) {
		try{
			if (event.getButton() == MouseEvent.BUTTON3 || event.isPopupTrigger()) {
				//On right click, the default select tool reset. 
				getSwingCanvas().getToolManager().setActiveTool(TSViewingToolHelper.getSelectTool(getSwingCanvas().getToolManager()));
				//Resetting Palette selection as well here
				resetPaletteSelection();
			}else {
				if(event.getSource() != null){
					super.onMousePressed(event);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected boolean isValidGraph(TSENode node, TSEGraph graph){return true;};
	
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
	public void partDeactivated(IWorkbenchPart part) {
		removeCreateToolListeners(controlKeylistnerMap);
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetPaletteSelection() {
		// TODO Auto-generated method stub
		
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
}