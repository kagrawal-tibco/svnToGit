package com.tibco.cep.sharedresource.jms;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.tibco.cep.sharedresource.model.SharedResModel;
import com.tibco.cep.sharedresource.ssl.SslConfigJmsModel;

/*
@author ssailapp
@date Dec 30, 2009 11:52:14 PM
 */

public class JmsConModel extends SharedResModel {
	SslConfigJmsModel sslConfigJmsModel;
	ArrayList<LinkedHashMap<String, String>> jndiProps;

	public JmsConModel(String name) {
		super();
		this.name = name;
		sslConfigJmsModel = new SslConfigJmsModel();
		jndiProps = new ArrayList<LinkedHashMap<String, String>>();
	}

	public SslConfigJmsModel getSslConfigJmsModel() {
		return sslConfigJmsModel;
	}
}
