package com.tibco.cep.dashboard.psvr.alerts;

import com.tibco.cep.dashboard.psvr.mal.model.MALAlert;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandler;

public abstract class AbstractAlertEvaluator extends AbstractHandler implements AlertEvaluator {
	
	protected MALAlert alert;

	protected void setAlert(MALAlert alert) {
		this.alert = alert;
	}

	@Override
	protected final void init() {
		
	}
	
}