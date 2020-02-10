package com.tibco.cep.sharedresource.ftl;

import com.tibco.cep.sharedresource.model.SharedResModel;
import com.tibco.cep.sharedresource.ssl.SslConfigFtlModel;

public class FTLModel extends SharedResModel {
    SslConfigFtlModel sslConfigFtlModel;
	public FTLModel() {
		super();
		sslConfigFtlModel = new SslConfigFtlModel();
	}
	
	public SslConfigFtlModel getSslConfigFtlModel() {
		return sslConfigFtlModel;
	}
	
}