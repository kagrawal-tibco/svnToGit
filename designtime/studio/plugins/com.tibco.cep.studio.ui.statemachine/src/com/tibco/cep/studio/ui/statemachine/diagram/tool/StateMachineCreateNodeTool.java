package com.tibco.cep.studio.ui.statemachine.diagram.tool;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.diagramming.tool.CreateNodeTool;
import com.tibco.cep.diagramming.utils.TSImages;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.RegionNodeCreator;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEAddNodeCommand;
import com.tomsawyer.interactive.command.editing.TSEInsertNodeCommand;
import com.tomsawyer.interactive.swing.TSEHitTesting;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;

/**
 * 
 * @author sasahoo
 *
 */
public class StateMachineCreateNodeTool extends CreateNodeTool{

	private boolean isEditorEnabled = true;
	private StateMachineDiagramManager stateMachineDiagramManager;
	
	public StateMachineCreateNodeTool(StateMachineDiagramManager stateMachineDiagramManager , boolean isEditorEnabled){
		super(stateMachineDiagramManager);
		this.isEditorEnabled = isEditorEnabled;
		this.stateMachineDiagramManager = stateMachineDiagramManager;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		stateMachineDiagramManager = null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateNodeTool#createNode(com.tomsawyer.drawing.geometry.shared.TSConstPoint)
	 */
	public void createNode(com.tomsawyer.drawing.geometry.shared.TSConstPoint point) {
		if (!EditorUtils.checkPermissions((AbstractSaveableEntityEditorPart) stateMachineDiagramManager.getEditor())) {
			return;
		}
		if (isEditorEnabled) {
			super.createNode(point);
		}
		else {
			if (this.stateMachineDiagramManager.isResetToolOnChange()) {
				getSwingCanvas().getToolManager().setActiveTool(TSViewingToolHelper.getSelectTool(getSwingCanvas().getToolManager()));
				resetPaletteSelection();
			}
		}
	}
	
	
	@Override
	protected TSEInsertNodeCommand newInsertNodeCommand(TSEGraph arg0,
			TSENode arg1) {
		// TODO Auto-generated method stub
		return super.newInsertNodeCommand(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see com.tomsawyer.interactive.swing.editing.tool.TSECreateNodeTool#onMousePressed(java.awt.event.MouseEvent)
	 */
	public void onMousePressed(MouseEvent event) {
		try {
			if (event.getButton() == MouseEvent.BUTTON3 || event.isPopupTrigger()) {
				// On right click, the default select tool reset. 
				getSwingCanvas().getToolManager().setActiveTool(
					TSViewingToolHelper.getSelectTool(getSwingCanvas().getToolManager()));
				resetPaletteSelection();
			} else {
				if (event.getSource() != null) {
					// if we are adding regions, don't call super if it's not legal...
					TSConstPoint point = this.getWorldPoint(event);
					TSEHitTesting hitTesting = this.getSwingCanvas().getHitTesting();
					TSEGraph graph = hitTesting.getGraphAt(point, this.getGraph());
					
					if (this.getGraphManager().getNodeBuilder() instanceof RegionNodeCreator) {
						
						if (this.isValidRegionGraph(graph)) {
							super.onMousePressed(event);
						}
						else {
							this.cancelAddRegionAction();
						}
					}					// we're not adding a region node, just act as straight pass-thru
					else {
						super.onMousePressed(event);
					/*
					 * Following has been written so that 
					 * user should not be able to add anything outside a region in a concurrent node.
					 */
						if (graph.getUserObject() instanceof StateComposite) {
							if (((StateComposite)graph.getUserObject()).isConcurrentState()) {
								this.cancelAddRegionAction();
							}
						}
					}
					/*if(this.getGraphManager().getNodeBuilder() instanceof SimpleStateNodeCreator||
							this.getGraphManager().getNodeBuilder() instanceof ConcurrentStateNodeCreator)
					{
						if (graph.getUserObject() instanceof StateComposite) {
							if (!((StateComposite)graph.getUserObject()).isRegion()) {
								this.cancelAddRegionAction();
							}
						}
					}*/
				}
				else {
					super.onMousePressed(event);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}	
	
	@Override
	protected TSEAddNodeCommand newAddNodeCommand(TSEGraph arg0, double arg1,
			double arg2) {
		// TODO Auto-generated method stub
		return super.newAddNodeCommand(arg0, arg1, arg2);
	}

	private void cancelAddRegionAction() {
		this.cancelAction();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = TSImages.createIcon("icons/Invalid10x10.png").getImage();
		Cursor c = tk.createCustomCursor(image, new Point(0,0),"invalid");
		this.setActionCursor(c);
		this.setCursor(c);
	}
	
	private boolean isValidRegionGraph(TSEGraph graph) {
		if (graph.getUserObject() instanceof StateComposite) {
			if (((StateComposite)graph.getUserObject()).isConcurrentState()) {
				return true;
			}
		}		
		return false;
	}

	
	// this is not used at all.
	/*
	protected boolean isValidGraph(TSENode node, TSEGraph graph){
		if(graph.getUserObject() instanceof StateComposite){
			if(((StateComposite)graph.getUserObject()).isConcurrentState()){
				if(node.getAttributeValue(StateMachineUtils.STATE_TYPE).equals(STATE.REGION)){
					return true;	
				}
				return false;
			}
			
			//When diagram switch, resetting active select tool 
			if(node.getAttributeValue(StateMachineUtils.STATE_TYPE) == null){
				getSwingCanvas().getToolManager().setActiveTool(TSViewingToolHelper.getSelectTool(getSwingCanvas().getToolManager()));
				return false;
			}
			if(node.getAttributeValue(StateMachineUtils.STATE_TYPE).equals(STATE.REGION)){
				return false;	
			}
		}
		return true;
	}
	*/

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateNodeTool#resetPaletteSelection()
	 */
	@Override
	public void resetPaletteSelection(){
		StudioUIUtils.resetPaletteSelection();
	}
}
