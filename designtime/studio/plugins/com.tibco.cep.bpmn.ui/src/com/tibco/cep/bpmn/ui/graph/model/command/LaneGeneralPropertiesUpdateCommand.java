package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;

import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author majha
 *
 */
public class LaneGeneralPropertiesUpdateCommand extends NodeGeneralPropertiesUpdateCommand {

	private static final long serialVersionUID = -439168385571854781L;

	public LaneGeneralPropertiesUpdateCommand(int type,ModelController controller,EClass modelType, ENamedElement extType,TSENode node, Map<String, Object> updateList) {
		super(type,controller,modelType,extType,node, updateList);
	}	
	
	@Override
	protected void doAction() throws Throwable {
		// TODO Auto-generated method stub
		super.doAction();
//		if (elementWrapper != null)
//			getModelController().getModelChangeAdapterFactory().adapt(
//					elementWrapper, ModelChangeListener.class);
	}
	
	@Override
	protected void undoAction() throws Throwable {
		super.undoAction();
//		if(elementWrapper != null)
//			getModelController().getModelChangeAdapterFactory().adapt(elementWrapper, ModelChangeListener.class);
	}
	
	@Override
	protected void redoAction() throws Throwable {
		doAction();
	}
}
