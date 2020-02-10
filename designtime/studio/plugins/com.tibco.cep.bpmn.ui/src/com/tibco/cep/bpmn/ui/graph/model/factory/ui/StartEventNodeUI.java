package com.tibco.cep.bpmn.ui.graph.model.factory.ui;

import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.MESSAGE_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SIGNAL_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.TIMER_EVENT_DEFINITION;

import org.eclipse.emf.ecore.EClass;

import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;

/**
 * 
 * @author ggrigore
 *
 */
public class StartEventNodeUI extends EventNodeUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5070267162401626308L;



	public StartEventNodeUI(EClass eventDefType ) {
		super(eventDefType);
		this.setOuterBorderMultiplier(4);
	}
	
	

	public void drawExtended(TSEGraphics graphics) {
		if(getEventDefinitionType() == null){
			return;
		} else	if (MESSAGE_EVENT_DEFINITION.isSuperTypeOf(getEventDefinitionType())) {
			super.drawMessageBadge(graphics);
		} else if (TIMER_EVENT_DEFINITION.isSuperTypeOf(getEventDefinitionType())) {
			super.drawTimerBadge(graphics);
		} else if (SIGNAL_EVENT_DEFINITION.isSuperTypeOf(getEventDefinitionType())) {
			super.drawSignalBadge(graphics);
		}
		
	}	
}
