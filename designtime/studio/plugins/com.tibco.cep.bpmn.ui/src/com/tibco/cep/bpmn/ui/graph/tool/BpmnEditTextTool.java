package com.tibco.cep.bpmn.ui.graph.tool;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.DocumentEvent;

import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.editor.IDiagramManager;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.swing.editing.tool.TSEEditTextTool;

/**
 * 
 * @author sasahoo
 *
 */
public class BpmnEditTextTool extends TSEEditTextTool {
	
	private boolean isEditorEnabled = true;
	private BpmnDiagramManager bpmnGraphDiagramManager;
	
	public BpmnEditTextTool(BpmnDiagramManager bpmnGraphDiagramManager, boolean isEditorEnabled) {
		this.bpmnGraphDiagramManager = bpmnGraphDiagramManager;
		this.isEditorEnabled = isEditorEnabled;
	}
	
	public IDiagramManager getBpmnGraphDiagramManager() {
		return bpmnGraphDiagramManager;
	}
	
	public boolean isEditorEnabled() {
		return isEditorEnabled;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		super.actionPerformed(event);
	}

	@Override
	public void cancelAction() {
		// TODO Auto-generated method stub
		super.cancelAction();
	}

	@Override
	public void changedUpdate(DocumentEvent event) {
		// TODO Auto-generated method stub
		super.changedUpdate(event);
	}

	/* (non-Javadoc)
	 * @see com.tomsawyer.interactive.swing.editing.tool.TSEEditTextTool#commitChanges()
	 */
	@Override
	public void commitChanges() {
		super.commitChanges();
		if (bpmnGraphDiagramManager.getSelectedNodes().size() > 0) {
			TSENode node = bpmnGraphDiagramManager.getSelectedNodes().get(0);
			node.setName(getNewText());

			Map<String, Object> updateList = new HashMap<String, Object>();
			updateList.put(BpmnUIConstants.ATTR_ANOOTATION_TEXT, getNewText());

			BpmnGraphUtils
					.fireUpdate(updateList, node, bpmnGraphDiagramManager);
		}
	}

	@Override
	public String getNewText() {
		// TODO Auto-generated method stub
		return super.getNewText();
	}

	@Override
	public String getOldTextValue() {
		// TODO Auto-generated method stub
		return super.getOldTextValue();
	}

	@Override
	public void onMouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		super.onMouseMoved(event);
	}

	@Override
	public void onMousePressed(MouseEvent event) {
		// TODO Auto-generated method stub
		super.onMousePressed(event);
	}

	@Override
	protected void setText(Object arg0) {
		// TODO Auto-generated method stub
		super.setText(arg0);
	}

	@Override
	public void updateText() {
		// TODO Auto-generated method stub
		super.updateText();
	}
}