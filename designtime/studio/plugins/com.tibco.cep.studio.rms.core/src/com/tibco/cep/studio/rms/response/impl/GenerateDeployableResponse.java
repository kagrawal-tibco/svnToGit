package com.tibco.cep.studio.rms.response.impl;

import com.tibco.cep.studio.rms.response.IResponse;

public class GenerateDeployableResponse implements IResponse {
	
	private String response;
	
	@Override
	public Object getResponseObject() {
		return response;
	}

	@Override
	public void holdResponseObject(Object responseObject) {
		this.response = (String)responseObject;
	}
}
