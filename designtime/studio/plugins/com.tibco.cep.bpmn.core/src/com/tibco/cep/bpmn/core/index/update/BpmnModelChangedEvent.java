package com.tibco.cep.bpmn.core.index.update;

public class BpmnModelChangedEvent {

	private BpmnModelDelta fDelta;

	public BpmnModelChangedEvent(BpmnModelDelta delta) {
		this.fDelta = delta;
	}

	public BpmnModelDelta getDelta() {
		return fDelta;
	}

}
