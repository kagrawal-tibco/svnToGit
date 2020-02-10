package com.tibco.cep.sharedresource.ftl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.cep.sharedresource.model.AbstractSharedResourceModelParser;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.sharedresource.ssl.SslConfigFtlModelParser;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_DESCRIPTION;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_REALMSERVER;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_USERNAME;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_PASSWORD;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_SECONDARY;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_USESSL;

public class FTLModelParser extends AbstractSharedResourceModelParser {
	public void loadModel(Document doc, SharedResModelMgr modelmgr) {
		Element root = doc.getDocumentElement();
		modelmgr.setRootAttributes(root.getAttributes());
		FTLModel model = (FTLModel) modelmgr.getModel();

		NodeList fileNodeList = root.getChildNodes();
		for (int n = 0; n < fileNodeList.getLength(); n++) {
			Node fileNode = fileNodeList.item(n);
			if (fileNode == null) {
				continue;
			}
			String fileNodeName = fileNode.getNodeName();
			fileNode.getAttributes();
			if ((isValidFileNode(fileNodeName)) && (fileNodeName.equalsIgnoreCase("config"))) {
				NodeList childFileNodeList = fileNode.getChildNodes();
				for (int c = 0; c < childFileNodeList.getLength(); c++) {
					Node node = childFileNodeList.item(c);
					if (!isValidFileNode(node))
						continue;
					if ("ssl".equals(node.getLocalName()) || "ssl".equals(node.getNodeName()) ) {
						new SslConfigFtlModelParser().loadModel(model.sslConfigFtlModel, node);
					}else
						model.values.put(node.getNodeName(), node.getTextContent());
				}
			}
		}
	}

	public void saveModelParts(Document doc, SharedResModelMgr modelmgr) {
		Element root = doc.getDocumentElement();
		FTLModel model = (FTLModel) modelmgr.getModel();
		Element configNode = doc.createElement("config");
		createMapElementNode(doc, configNode, modelmgr, CHANNEL_PROPERTY_DESCRIPTION);
		createMapElementNode(doc, configNode, modelmgr, CHANNEL_PROPERTY_REALMSERVER);
		createMapElementNode(doc, configNode, modelmgr, CHANNEL_PROPERTY_USERNAME);
		createMapElementNode(doc, configNode, modelmgr, CHANNEL_PROPERTY_PASSWORD);
		createMapElementNode(doc, configNode, modelmgr, CHANNEL_PROPERTY_SECONDARY);
		createMapElementNode(doc, configNode, modelmgr, CHANNEL_PROPERTY_USESSL);
		//createMapNode(doc, configNode, model.values);
		new SslConfigFtlModelParser().saveModelParts(doc, configNode, model.sslConfigFtlModel);
		root.appendChild(configNode);
	}
}