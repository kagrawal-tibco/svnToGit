package com.tibco.cep.sharedresource.ssl;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * @author ssinghal
 *
 */

public class SslConfigMqttModelParser extends SslConfigModelParser{
	
	@Override
	public void loadAdvancedNodes(Node node, String prefix, SslConfigModel sslConfigModel) {
		SslConfigMqttModel sslConfigMqttModel = (SslConfigMqttModel) sslConfigModel;
		if (node.getNodeName().equals(prefix + SslConfigMqttModel.ID_CLIENT_AUTH)) {
			sslConfigMqttModel.clientAuth = node.getTextContent();
		}
	}

	@Override
	public void saveAdvancedNodes(Document doc, Node sslNode, SslConfigModel sslConfigModel) {
		SslConfigMqttModel sslConfigMqttModel = (SslConfigMqttModel) sslConfigModel;
		createTextElementNode(doc, sslNode, SslConfigMqttModel.ID_CLIENT_AUTH, sslConfigMqttModel.clientAuth);
	}

}
