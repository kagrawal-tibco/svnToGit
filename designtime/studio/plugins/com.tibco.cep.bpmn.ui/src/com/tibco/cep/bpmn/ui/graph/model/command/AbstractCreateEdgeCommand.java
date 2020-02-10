package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.List;

import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEAddEdgeCommand;

/**
 * 
 * @author majha
 *
 */
public abstract class AbstractCreateEdgeCommand extends TSEAddEdgeCommand {
	
	private static final long serialVersionUID = -7415145009960370106L;
	protected boolean createEmfModelOnly;
	
	@SuppressWarnings("rawtypes")
	public AbstractCreateEdgeCommand(TSENode sourceNode, TSENode targetNode, List bendList) {
		super(sourceNode, targetNode, bendList);
	}
	
	
	protected void doAction() throws Throwable {
		if (!createEmfModelOnly)
			super.doAction();
	}

	protected void undoAction() throws Throwable {
		if (!createEmfModelOnly)
			super.undoAction();
	}

	public void redoAction() throws Throwable {
		if (!createEmfModelOnly)
			super.redoAction();
	}
	
	public void setCreateEmfModelOnly(boolean deleteEmfModelOnly) {
		this.createEmfModelOnly = deleteEmfModelOnly;
	}


}
