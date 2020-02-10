package com.tibco.cep.bpmn.ui.graph.model.factory.events;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.factory.ui.StartEventNodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public class StartEventNodeUIFactory extends AbstractCatchEventNodeUIFactory {
	
	private static final long serialVersionUID = 6608545318847856239L;

	public StartEventNodeUIFactory(String name, String referredBEResource, String toolId,
			BpmnLayoutManager layoutManager, EClass eventDefType) {
		super(name, referredBEResource, toolId, layoutManager, BpmnModelClass.START_EVENT, eventDefType);
		if(getNodeName() == null){
			if(eventDefType == null) {
				setNodeName(Messages.getString("title.start.event"));//$NON-NLS-1$
			} else	if(BpmnModelClass.MESSAGE_EVENT_DEFINITION.isSuperTypeOf(eventDefType)) {
				setNodeName(Messages.getString("title.message.start.event"));//$NON-NLS-1$
			} else if(BpmnModelClass.TIMER_EVENT_DEFINITION.isSuperTypeOf(eventDefType)){
				setNodeName(Messages.getString("title.timer.start.event"));//$NON-NLS-1$
			} else if(BpmnModelClass.SIGNAL_EVENT_DEFINITION.isSuperTypeOf(eventDefType)){
				setNodeName(Messages.getString("title.signal.start.event"));//$NON-NLS-1$
			}else {
				setNodeName(Messages.getString("title.message.start.event"));//$NON-NLS-1$
			}
		}
	}
	
	@Override
	public TSEObjectUI initGraphUI(Object ...args) {
		return new StartEventNodeUI((EClass)args[0]);
	}
	

}
