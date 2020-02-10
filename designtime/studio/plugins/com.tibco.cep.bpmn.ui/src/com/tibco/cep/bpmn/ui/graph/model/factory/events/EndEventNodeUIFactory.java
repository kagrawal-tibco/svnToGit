package com.tibco.cep.bpmn.ui.graph.model.factory.events;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.EndEventNodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public class EndEventNodeUIFactory extends AbstractThrowEventNodeUIFactory {
	
	private static final long serialVersionUID = 6088607883104945897L;

	public EndEventNodeUIFactory(String name,  String referredBEResource, String toolId,BpmnLayoutManager layoutManager, EClass eventDefType) {
		super(name, referredBEResource, toolId, layoutManager, BpmnModelClass.END_EVENT, eventDefType);
		if(getNodeName() == null){
			if(eventDefType == null) {
				setNodeName(Messages.getString("title.end.event"));//$NON-NLS-1$
			}else if(BpmnModelClass.MESSAGE_EVENT_DEFINITION.isSuperTypeOf(eventDefType)){
				setNodeName(Messages.getString("title.message.end.event"));//$NON-NLS-1$
			} else if(BpmnModelClass.ERROR_EVENT_DEFINITION.isSuperTypeOf(eventDefType)) {
				setNodeName(Messages.getString("title.error.end.event"));//$NON-NLS-1$
			}else if(BpmnModelClass.SIGNAL_EVENT_DEFINITION.isSuperTypeOf(eventDefType)) {
				setNodeName(Messages.getString("title.signal.end.event"));//$NON-NLS-1$
			} else {
				setNodeName(Messages.getString("title.message.end.event"));//$NON-NLS-1$
			}
		}
	}
	
	@Override
	public TSEObjectUI initGraphUI(Object ...args) {
		return new EndEventNodeUI((EClass)args[0]);
	}
}
