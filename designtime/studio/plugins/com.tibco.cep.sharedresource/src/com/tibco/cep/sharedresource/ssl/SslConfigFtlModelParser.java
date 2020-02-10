package com.tibco.cep.sharedresource.ssl;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_TRUST_TYPE;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_TRUST_FILE_LOCATION;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_TRUST_STRING_TEXT;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_TRUST_TYPE_FILE;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_TRUST_TYPE_STRING;

public class SslConfigFtlModelParser extends SslConfigModelParser{

	@Override
	public void loadAdvancedNodes(Node node, String prefix, SslConfigModel sslConfigModel) {
		SslConfigFtlModel sslConfigFtlModel = (SslConfigFtlModel) sslConfigModel;
		if (node.getNodeName().equals(prefix + CHANNEL_PROPERTY_TRUST_TYPE)) {
			sslConfigFtlModel.trust_type = node.getTextContent();
		}else if(node.getNodeName().equals(prefix + CHANNEL_PROPERTY_TRUST_FILE_LOCATION)) {
			sslConfigFtlModel.trust_file = node.getTextContent();
		}else if(node.getNodeName().equals(prefix + CHANNEL_PROPERTY_TRUST_STRING_TEXT)) {
			sslConfigFtlModel.trust_string = node.getTextContent();
		}
	}

	@Override
	public void saveAdvancedNodes(Document doc, Node sslNode, SslConfigModel sslConfigModel) {
		SslConfigFtlModel sslConfigFtlModel = (SslConfigFtlModel) sslConfigModel;
		createTextElementNode(doc,sslNode,CHANNEL_PROPERTY_TRUST_TYPE, sslConfigFtlModel.trust_type);
		if(sslConfigFtlModel.trust_type.equals(CHANNEL_PROPERTY_TRUST_TYPE_FILE)){
			createTextElementNode(doc,sslNode,CHANNEL_PROPERTY_TRUST_FILE_LOCATION, sslConfigFtlModel.trust_file);
		}else if(sslConfigFtlModel.trust_type.equals(CHANNEL_PROPERTY_TRUST_TYPE_STRING)){
			createTextElementNode(doc,sslNode,CHANNEL_PROPERTY_TRUST_STRING_TEXT, sslConfigFtlModel.trust_string);
		}
	}

}
