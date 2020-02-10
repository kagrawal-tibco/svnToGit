package com.tibco.cep.bpmn.ui.graph.model.command;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author pdhar
 *
 */
public class PoolInsertCommand extends AbstractInsertNodeCommand {

	private static final long serialVersionUID = -6680301680443332152L;
	@SuppressWarnings("unused")
	private EClass nodeType;
	@SuppressWarnings("unused")
	private String nodeName;
	@SuppressWarnings("unused")
	private TSGraph rootGraph;
	@SuppressWarnings("unused")
	private TSEGraph currentGraph;
	@SuppressWarnings("unused")
	private EObjectWrapper<EClass, EObject> process;
	@SuppressWarnings("unused")
	private EObjectWrapper<EClass, EObject> lane;

	
	
	public PoolInsertCommand(int type, ModelController controller,
			EClass modelType, ENamedElement extType, TSEGraph graph,
			TSENode node) {
		super(type, controller, modelType, extType, graph, node);
		// TODO Auto-generated constructor stub
	}

	

	protected void doAction() throws Throwable {
		super.doAction();

	}

	protected void undoAction() throws Throwable {
		super.undoAction();		
	}	
	
	protected void finalize() throws Throwable {
		nodeType = null;
		nodeName = null;
		rootGraph = null;
		currentGraph = null;
		process = null;
		lane = null;
		super.finalize();
	}

}
