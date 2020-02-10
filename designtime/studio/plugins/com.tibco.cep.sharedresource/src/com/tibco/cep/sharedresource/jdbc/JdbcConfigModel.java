package com.tibco.cep.sharedresource.jdbc;

import com.tibco.cep.sharedresource.model.SharedResModel;
import com.tibco.cep.sharedresource.ssl.SslConfigJdbcModel;

/*
@author ssailapp
@date Dec 29, 2009 11:29:55 PM
 */

public class JdbcConfigModel extends SharedResModel {

	private SslConfigJdbcModel sslConfigJdbcModel;

	public JdbcConfigModel(String name) {
		super();
		sslConfigJdbcModel = new SslConfigJdbcModel();
		this.name = name;
	}
	
	public SslConfigJdbcModel getSslConfigJdbcModel() {
		return sslConfigJdbcModel;
	}
}
