package com.tibco.cep.bpmn.ui.graph.tool;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.List;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.editor.IDiagramManager;
import com.tibco.cep.bpmn.ui.graph.BpmnImages;
import com.tibco.cep.bpmn.ui.graph.model.command.BpmnNodeTransferCommand;
import com.tibco.cep.bpmn.ui.graph.rule.DiagramRuleSet;
import com.tibco.cep.bpmn.ui.graph.rule.TransferRule;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.interactive.command.editing.TSETransferGroupCommand;
import com.tomsawyer.interactive.swing.TSEHitTesting;
import com.tomsawyer.interactive.swing.editing.tool.TSETransferSelectedTool;

public class BpmnTransferSelectedTool extends TSETransferSelectedTool {
	private BpmnDiagramManager bpmnGraphDiagramManager;
	private boolean isEditorEnabled;
	private DiagramRuleSet ruleSet;
	public static BpmnTransferSelectedTool tool;
	
	public BpmnTransferSelectedTool(BpmnDiagramManager bpmnDiagramManager,
			boolean enabled) {
		this.isEditorEnabled = enabled;
		this.bpmnGraphDiagramManager = bpmnDiagramManager;
		tool = this;
		this.ruleSet = new DiagramRuleSet();
		this.addAllRules();
	}
	
	public IDiagramManager getBpmnGraphDiagramManager() {
		return bpmnGraphDiagramManager;
	}
	
	/**
	 * 
	 */
	private void addAllRules() {
		getRuleSet().addRule(new TransferRule(this.ruleSet));

	}
	
	public boolean isEditorEnabled() {
		return isEditorEnabled;
	}
	
	/**
	 * @return
	 */
	public DiagramRuleSet getRuleSet() {
		return ruleSet;
	}

	
	
	@SuppressWarnings("rawtypes")
	@Override
	protected TSETransferGroupCommand newTransferCommand(List arg0, List arg1,
			List arg2, List arg3, List arg4, List arg5, List arg6,
			TSGraphObject arg7, TSConstPoint arg8, TSConstPoint arg9) {
		return new BpmnNodeTransferCommand(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7,
				arg8, arg9, bpmnGraphDiagramManager.getController());
		
	}
	
	private TSEGraph getGraphAtPoint(TSConstPoint point) {
		TSEHitTesting hitTesting = this.getSwingCanvas().getHitTesting();
		TSEGraph graph = hitTesting.getGraphAt(point, this.getGraph());
		return graph;
	}
	

	
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public void commitMove(TSConstPoint point) {
		if (isEditorEnabled) {
			TSEGraph graph = getGraphAtPoint(point);
			
			List draggedObjects = getAllDraggedObjects();
			if (this.isActionAllowed(graph,draggedObjects)) {
				super.commitMove(point);
			} else {
				setInvalidCursor();
			}
			
		} 
	}
	
	
	
	/**
	 * @param graph
	 * @param draggedObjects
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean isActionAllowed(TSEGraph graph, List draggedObjects) {
		return  getRuleSet().isAllowed(graph,draggedObjects);
	}
	
	/**
	 * 
	 */
	public void setInvalidCursor() {
		
		try {
			Toolkit tk = Toolkit.getDefaultToolkit();
			Image image = BpmnImages.createIcon(
			"icons/Invalid10x10.png").getImage();
			Cursor c = tk.createCustomCursor(image, new Point(0, 0),
			BpmnMessages.getString("invalid_label"));
			this.setActionCursor(c);
			this.setCursor(c);
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}

	}
	
	/**
	 * 
	 */
	public void setDefaultCursor() {
		//Resetting the Cursor
		if(getActionCursor()!=null){
			Cursor c = new Cursor(Cursor.DEFAULT_CURSOR);
			this.setActionCursor(c);
			this.setCursor(c);
		}
	}
	

	
}
