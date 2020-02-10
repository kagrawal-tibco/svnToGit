package com.tibco.cep.sharedresource.ssl;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/*
@author ssailapp
@date Mar 2, 2010 2:16:02 PM
 */

public class SslConfigJmsModelParser extends SslConfigModelParser {

	@Override
	public void loadAdvancedNodes(Node node, String prefix, SslConfigModel sslConfigModel) {
		SslConfigJmsModel sslConfigJmsModel = (SslConfigJmsModel) sslConfigModel;
		if (node.getNodeName().equals(prefix + SslConfigJmsModel.ID_TRACE)) {
			sslConfigJmsModel.trace = node.getTextContent();
		} else if (node.getNodeName().equals(prefix + SslConfigJmsModel.ID_DEBUG_TRACE)) {
			sslConfigJmsModel.debugTrace = node.getTextContent();
		} else if (node.getNodeName().equals(prefix + SslConfigJmsModel.ID_VERIFY_HOST_NAME)) {
			sslConfigJmsModel.verifyHostName = node.getTextContent();
		} else if (node.getNodeName().equals(prefix + SslConfigJmsModel.ID_EXPECTED_HOSTNAME)) {
			sslConfigJmsModel.expectedHostName = node.getTextContent();
		} else if (node.getNodeName().equals(prefix + SslConfigJmsModel.ID_CIPHER_SUITE)) {
			sslConfigJmsModel.cipherSuites = node.getTextContent();
		}
	}

	@Override
	public void saveAdvancedNodes(Document doc, Node sslNode, SslConfigModel sslConfigModel) {
		SslConfigJmsModel sslConfigJmsModel = (SslConfigJmsModel) sslConfigModel;
		createTextElementNode(doc, sslNode, SslConfigJmsModel.ID_TRACE, sslConfigJmsModel.trace);
		createTextElementNode(doc, sslNode, SslConfigJmsModel.ID_DEBUG_TRACE, sslConfigJmsModel.debugTrace);
		createTextElementNode(doc, sslNode, SslConfigJmsModel.ID_VERIFY_HOST_NAME, sslConfigJmsModel.verifyHostName);
		createTextElementNode(doc, sslNode, SslConfigJmsModel.ID_EXPECTED_HOSTNAME, sslConfigJmsModel.expectedHostName);
		createTextElementNode(doc, sslNode, SslConfigJmsModel.ID_CIPHER_SUITE, sslConfigJmsModel.cipherSuites);
	}
}
