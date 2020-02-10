package com.tibco.cep.dashboard.psvr.alerts;

import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;

public interface AlertActionable {
	
	public AlertResult execute(PresentationContext ctx) throws ExecException;

}