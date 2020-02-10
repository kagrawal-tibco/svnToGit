package com.tibco.cep.runtime.service.cluster.filters;

import java.util.Map;

import com.tibco.cep.runtime.service.om.api.Invocable;

public class TransferEvent implements Invocable {

	private static final long serialVersionUID = -604593797399847858L;

	int agentId;
    int transferTo;
    
    public TransferEvent(int fromAgent, int toAgent) {
        this.agentId=agentId;
        this.transferTo=transferTo;
    }

	@Override
	public Object invoke(Map.Entry key) throws Exception {
		//4.0 impl also returns null..
		return null;
	}
}
