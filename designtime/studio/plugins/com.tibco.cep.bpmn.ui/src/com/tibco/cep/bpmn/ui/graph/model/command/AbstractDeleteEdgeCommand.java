package com.tibco.cep.bpmn.ui.graph.model.command;

import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.interactive.command.editing.TSEDeleteEdgeCommand;

/**
 * 
 * @author majha
 *
 */
public abstract class AbstractDeleteEdgeCommand extends TSEDeleteEdgeCommand {

	private static final long serialVersionUID = -491096596865595351L;
	protected boolean deleteEmfModelOnly;
	
	public AbstractDeleteEdgeCommand(TSEEdge paramTSEEdge) {
		super(paramTSEEdge);
	}
	
	
	protected void doAction() throws Throwable {
		if (!deleteEmfModelOnly)
			super.doAction();
	}

	protected void undoAction() throws Throwable {
		if (!deleteEmfModelOnly)
			super.undoAction();
	}

	protected void redoAction() throws Throwable {
		if (!deleteEmfModelOnly)
			super.redoAction();
	}
	
	public void setDeleteEmfModelOnly(boolean deleteEmfModelOnly) {
		this.deleteEmfModelOnly = deleteEmfModelOnly;
	}


}
