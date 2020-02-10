package com.tibco.cep.sharedresource.as3;

import com.tibco.cep.sharedresource.model.SharedResModel;
import com.tibco.cep.sharedresource.ssl.SslConfigFtlModel;

public class AS3Model extends SharedResModel {
    SslConfigFtlModel sslConfigFtlModel;
	public AS3Model() {
		super();
		sslConfigFtlModel = new SslConfigFtlModel();
	}
	
	public SslConfigFtlModel getSslConfigFtlModel() {
		return sslConfigFtlModel;
	}
	
}