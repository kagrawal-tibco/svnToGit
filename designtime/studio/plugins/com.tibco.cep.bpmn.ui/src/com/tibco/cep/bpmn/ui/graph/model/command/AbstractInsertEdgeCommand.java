package com.tibco.cep.bpmn.ui.graph.model.command;

import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.interactive.command.editing.TSEInsertEdgeCommand;

/**
 * 
 * @author majha
 *
 */
public abstract class AbstractInsertEdgeCommand extends TSEInsertEdgeCommand {
	private static final long serialVersionUID = -7415145009960370106L;
	protected boolean insertEmfModelOnly;
	
	public AbstractInsertEdgeCommand(TSEEdge edge) {
		super(edge);
	}
	
	
	protected void doAction() throws Throwable {
		if (!insertEmfModelOnly)
			super.doAction();
	}

	protected void undoAction() throws Throwable {
		if (!insertEmfModelOnly)
			super.undoAction();
	}

	public void redoAction() throws Throwable {
		if (!insertEmfModelOnly)
			super.redoAction();
	}
	
	public void setInsertEmfModelOnly(boolean deleteEmfModelOnly) {
		this.insertEmfModelOnly = deleteEmfModelOnly;
	}


}
