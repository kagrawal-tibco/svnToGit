package com.tibco.cep.decision.tree.ui.tool;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import com.tibco.cep.decision.tree.common.model.edge.Edge;
import com.tibco.cep.decision.tree.common.model.node.NodeElement;
import com.tibco.cep.decision.tree.common.model.node.condition.BoolCond;
import com.tibco.cep.decision.tree.ui.editor.DecisionTreeDiagramManager;
import com.tibco.cep.decision.tree.ui.editor.DecisionTreeEditor;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeImages;
import com.tibco.cep.decision.tree.ui.util.DecisionTreeUiUtil;
import com.tibco.cep.diagramming.tool.ReconnectEdgeTool;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tomsawyer.graphicaldrawing.TSENode;

public class DecisionTreeReconnectEdgeTool extends ReconnectEdgeTool {

	private DecisionTreeDiagramManager decisionTreeDiagramManager;

	public DecisionTreeReconnectEdgeTool(DecisionTreeDiagramManager decisionTreeDiagramManager) {
		this.decisionTreeDiagramManager = decisionTreeDiagramManager;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.CreateEdgeTool#isActionAllowed()
	 */
	public boolean isActionAllowed() {
		if (!((DecisionTreeEditor)decisionTreeDiagramManager.getEditor()).isEnabled()) {
			return false;
		}
		TSENode srcTSNode = this.getSourceNode();
		//TSENode tgtTSNode = this.getTargetNode();
		
		NodeElement srcElement = (NodeElement) srcTSNode.getUserObject();
		//NodeElement tgtElement = (NodeElement) tgtTSNode.getUserObject();
		
		boolean isAllowed = true;
		
		int degree = DecisionTreeUiUtil.getOutDegree(srcElement);
		if ((srcElement instanceof BoolCond) && degree > 2) {
			isAllowed = false;
		} else if (degree > 1) {
			isAllowed = false;
		}
		
		if (!isAllowed) {
			setCursorUIChange();
		}
		return isAllowed;
	}
	
	/**
	 * set invalid mouse cursor change
	 */
	private void setCursorUIChange() {
		try {
			Toolkit tk = Toolkit.getDefaultToolkit();
			Image image = DecisionTreeImages.createIcon(DecisionTreeImages.INVALID).getImage();
			Cursor c = tk.createCustomCursor(image, new Point(0,0),"invalid");
			this.setActionCursor(c);
			this.setCursor(c);
			this.cancelAction();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.tool.ReconnectEdgeTool#fireModelChanged()
	 */
	public void fireModelChanged(){
		if (!EditorUtils.checkPermissions((AbstractSaveableEntityEditorPart) decisionTreeDiagramManager.getEditor())) {
			((AbstractSaveableEntityEditorPart) decisionTreeDiagramManager.getEditor()).doRevert();
			return;
		}
		TSENode oldNode = getOldNode();
		TSENode newNode = getNewNode();
		if (oldNode!=null && oldNode.getUserObject()!=null) {
			NodeElement oldElement = (NodeElement)oldNode.getUserObject();
			NodeElement newElement = (NodeElement)newNode.getUserObject();
			Edge edge = (Edge) getEdge().getUserObject();

			((DecisionTreeEditor)decisionTreeDiagramManager.getEditor()).getEditingDomain();

			if (edge.getSrc().getId() == oldElement.getId()) {
				// edge was moved at the source end
				newElement.setOutEdge(edge);
				oldElement.setOutEdge(null);
				edge.setSrc(newElement);
			} else {
				// edge was moved at the target end
				newElement.getInEdges().add(edge);
				oldElement.getInEdges().remove(edge);
				edge.setTgt(newElement);
			}
			
			/*
			if(isReconnectingSource()){
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) decisionTreeDiagramManager.getEditor(), 
						(SetCommand)SetCommand.create(editingDomain,stateTransition , StatesPackage.eINSTANCE.getStateTransition_FromState(),newState));
				
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) decisionTreeDiagramManager.getEditor(), 
						(RemoveCommand)RemoveCommand.create(editingDomain, oldState, StatesPackage.eINSTANCE.getStateVertex_OutgoingTransitions(),stateTransition));
				
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) decisionTreeDiagramManager.getEditor(), 
						(AddCommand)AddCommand.create(editingDomain, newState, StatesPackage.eINSTANCE.getStateVertex_IncomingTransitions(),stateTransition));
				
			}else{
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) decisionTreeDiagramManager.getEditor(), 
						(SetCommand)SetCommand.create(editingDomain,stateTransition , StatesPackage.eINSTANCE.getStateTransition_ToState(),newState));
				
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) decisionTreeDiagramManager.getEditor(), 
						(RemoveCommand)RemoveCommand.create(editingDomain, oldState, StatesPackage.eINSTANCE.getStateVertex_IncomingTransitions(),stateTransition));
				
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) decisionTreeDiagramManager.getEditor(), 
						(AddCommand)AddCommand.create(editingDomain, newState, StatesPackage.eINSTANCE.getStateVertex_OutgoingTransitions(),stateTransition));
			}
			*/
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		decisionTreeDiagramManager = null;
	}
	
	
}