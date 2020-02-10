package com.tibco.cep.studio.core.index.update;

public class StudioModelChangedEvent {

	private StudioModelDelta fDelta;

	public StudioModelChangedEvent(StudioModelDelta delta) {
		this.fDelta = delta;
	}

	public StudioModelDelta getDelta() {
		return fDelta;
	}

}
