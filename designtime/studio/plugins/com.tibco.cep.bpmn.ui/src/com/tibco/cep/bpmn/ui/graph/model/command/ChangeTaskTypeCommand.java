package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.LinkedList;
import java.util.List;

import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.TSCommand;

/**
 * 
 * @author ggrigore
 *
 */
public class ChangeTaskTypeCommand extends TSCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6036159848534244324L;
	private TSENode node = null;
	private String oldType;
	private String newType;
	
	public ChangeTaskTypeCommand(TSENode node, String newType) {
		this.node = node;
		this.newType = newType;
	}

	protected void doAction() throws Throwable {
		this.oldType = (String) node.getAttributeValue("type");
		node.setAttribute("type", this.newType);
	}

	protected void undoAction() throws Throwable {
		node.setAttribute("type", this.oldType);
	}

	protected void redoAction() throws Throwable {
		node.setAttribute("type", this.newType);
	}

	public List<TSENode> getAffectedObjects() {
		List<TSENode> objects = new LinkedList<TSENode>();
		objects.add(this.node);
		return objects;
	}

	/**
	 * This method performs the cleanup of the command if it is in the UNDONE state.
	 */
	protected void undoCleanup() {
	}
}
