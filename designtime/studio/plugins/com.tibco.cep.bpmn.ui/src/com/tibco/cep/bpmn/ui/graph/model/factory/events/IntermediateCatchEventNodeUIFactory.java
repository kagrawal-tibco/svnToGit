package com.tibco.cep.bpmn.ui.graph.model.factory.events;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.IntermediateEventNodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

/**
 * @author pdhar
 *
 */
public class IntermediateCatchEventNodeUIFactory extends AbstractCatchEventNodeUIFactory {
	
	private static final long serialVersionUID = -3827753149370710206L;

	public IntermediateCatchEventNodeUIFactory(String name, String referredBEResource, String toolId, BpmnLayoutManager layoutManager, EClass eventDefType) {
		super(name, referredBEResource,  toolId, layoutManager,BpmnModelClass.INTERMEDIATE_CATCH_EVENT, eventDefType);
		if(getNodeName() == null){
			if(BpmnModelClass.MESSAGE_EVENT_DEFINITION.isSuperTypeOf(eventDefType)) {
				setNodeName(Messages.getString("title.catch.message.intermediate"));//$NON-NLS-1$
			} else if(BpmnModelClass.TIMER_EVENT_DEFINITION.isSuperTypeOf(eventDefType)){
				setNodeName(Messages.getString("title.catch.timer.intermediate"));//$NON-NLS-1$
			} else if(BpmnModelClass.ERROR_EVENT_DEFINITION.isSuperTypeOf(eventDefType)){
				setNodeName(Messages.getString("title.catch.error.intermediate"));//$NON-NLS-1$
			} else if(BpmnModelClass.SIGNAL_EVENT_DEFINITION.isSuperTypeOf(eventDefType)){
				setNodeName(Messages.getString("title.catch.signal.intermediate"));//$NON-NLS-1$
			} else {
				setNodeName(Messages.getString("title.catch.message.intermediate"));//$NON-NLS-1$
			}
		}
	}
	
	@Override
	public TSEObjectUI initGraphUI(Object ...args) {
		return new IntermediateEventNodeUI((EClass)args[0]);
	}
	
	
}
