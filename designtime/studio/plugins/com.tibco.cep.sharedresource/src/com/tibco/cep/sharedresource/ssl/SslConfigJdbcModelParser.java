package com.tibco.cep.sharedresource.ssl;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * 
 * @author moshaikh
 */
public class SslConfigJdbcModelParser extends SslConfigModelParser {

	@Override
	public void loadAdvancedNodes(Node node, String prefix, SslConfigModel sslConfigModel) {
		SslConfigJdbcModel sslConfigJdbcModel = (SslConfigJdbcModel) sslConfigModel;
		if (node.getNodeName().equals(prefix + SslConfigJdbcModel.ID_CLIENT_AUTH)) {
			sslConfigJdbcModel.setClientAuth(node.getTextContent());
		} else if (node.getNodeName().equals(prefix + SslConfigJdbcModel.ID_VERIFY_HOST_NAME)) {
			sslConfigJdbcModel.setVerifyHostName(node.getTextContent());
		} else if (node.getNodeName().equals(prefix + SslConfigJdbcModel.ID_EXPECTED_HOSTNAME)) {
			sslConfigJdbcModel.setExpectedHostName(node.getTextContent());
		}
	}
	
	@Override
	public void saveAdvancedNodes(Document doc, Node sslNode, SslConfigModel sslConfigModel) {
		SslConfigJdbcModel sslConfigJdbcModel = (SslConfigJdbcModel) sslConfigModel;
		createTextElementNode(doc, sslNode, SslConfigJdbcModel.ID_CLIENT_AUTH, sslConfigJdbcModel.getClientAuth());
		createTextElementNode(doc, sslNode, SslConfigJdbcModel.ID_VERIFY_HOST_NAME, sslConfigJdbcModel.getVerifyHostName());
		createTextElementNode(doc, sslNode, SslConfigJdbcModel.ID_EXPECTED_HOSTNAME, sslConfigJdbcModel.getExpectedHostName());
	}
}
