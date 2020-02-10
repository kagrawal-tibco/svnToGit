package com.tibco.cep.sharedresource.httpconfig;

import com.tibco.cep.sharedresource.model.SharedResModel;
import com.tibco.cep.sharedresource.ssl.SslConfigHttpModel;

/*
@author ssailapp
@date Dec 22, 2009 5:53:08 PM
 */

public class HttpConfigModel extends SharedResModel {
	public final static String HTTP_CONFIG_EXTENSION = "sharedhttp";
	
	SslConfigHttpModel sslConfigHttpModel; 
	
	public HttpConfigModel() {
		super();
		sslConfigHttpModel = new SslConfigHttpModel();
	}

	public SslConfigHttpModel getSslConfigHttpModel() {
		return sslConfigHttpModel;
	}
}
