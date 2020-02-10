package com.tibco.cep.sharedresource.ssl;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/*
@author ssailapp
@date Mar 2, 2010 2:15:30 PM
 */

public class SslConfigHttpModelParser extends SslConfigModelParser {

	@Override
	public void loadAdvancedNodes(Node node, String prefix, SslConfigModel sslConfigModel) {
		SslConfigHttpModel sslConfigHttpModel = (SslConfigHttpModel) sslConfigModel;
		if (node.getNodeName().equals(prefix + SslConfigHttpModel.ID_CLIENT_AUTH)) {
			sslConfigHttpModel.clientAuth = node.getTextContent();
		} else if (node.getNodeName().equals(prefix + SslConfigHttpModel.ID_CIPHER_SUITE)) {
			sslConfigHttpModel.cipherSuites = node.getTextContent();
		}
	}
	
	@Override
	public void saveAdvancedNodes(Document doc, Node sslNode, SslConfigModel sslConfigModel) {
		SslConfigHttpModel sslConfigHttpModel = (SslConfigHttpModel) sslConfigModel;
		createTextElementNode(doc, sslNode, "strongCipherSuitesOnly", sslConfigHttpModel.cipherSuites);
		createTextElementNode(doc, sslNode, "requiresClientAuthentication", sslConfigHttpModel.clientAuth);
	}

}
