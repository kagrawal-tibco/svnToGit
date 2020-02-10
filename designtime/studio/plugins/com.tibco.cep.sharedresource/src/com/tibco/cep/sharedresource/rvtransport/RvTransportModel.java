package com.tibco.cep.sharedresource.rvtransport;

import com.tibco.cep.sharedresource.model.SharedResModel;
import com.tibco.cep.sharedresource.ssl.SslConfigRvModel;

/*
@author ssailapp
@date Dec 29, 2009 5:17:52 PM
 */

public class RvTransportModel extends SharedResModel {
	SslConfigRvModel sslConfigRvModel;  
	
	public RvTransportModel() {
		sslConfigRvModel = new SslConfigRvModel();
	}

	public SslConfigRvModel getSslConfigRvModel() {
		return sslConfigRvModel;
	}
}
