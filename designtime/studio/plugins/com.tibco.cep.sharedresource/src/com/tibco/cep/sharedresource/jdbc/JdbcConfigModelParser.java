package com.tibco.cep.sharedresource.jdbc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.cep.sharedresource.model.AbstractSharedResourceModelParser;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.sharedresource.ssl.SslConfigJdbcModelParser;

/*
@author ssailapp
@date Dec 29, 2009 11:34:53 PM
 */

public class JdbcConfigModelParser extends AbstractSharedResourceModelParser {
	
	@Override
	public void loadModel(Document doc, SharedResModelMgr modelmgr) {
		Element root = doc.getDocumentElement();
		modelmgr.setRootAttributes(root.getAttributes());
		JdbcConfigModel model = (JdbcConfigModel) modelmgr.getModel();
		
		NodeList fileNodeList = root.getChildNodes();
		for (int n=0; n<fileNodeList.getLength(); n++) {
			Node fileNode = fileNodeList.item(n);
			if (fileNode == null || !isValidFileNode(fileNode)) {
				continue;
			}
			String fileNodeName = fileNode.getNodeName();
//			NamedNodeMap fileNodeAttr = fileNode.getAttributes();
			if (fileNodeName.equalsIgnoreCase("config")) {
				NodeList childFileNodeList = fileNode.getChildNodes();
				for (int c=0; c<childFileNodeList.getLength(); c++) {
					Node node = childFileNodeList.item(c);
					if (!isValidFileNode(node))
						continue;
					if ("ssl".equals(node.getLocalName()) || "ssl".equals(node.getNodeName()) ) {
						new SslConfigJdbcModelParser().loadModel(model.getSslConfigJdbcModel(), node);
					}
					else {
						model.values.put(node.getNodeName(), node.getTextContent());
					}
				}
			} else if (fileNodeName.equals("description")) {
				model.values.put("description", fileNode.getTextContent());
			}
		}
	}

	@Override
	public void saveModelParts(Document doc, SharedResModelMgr modelmgr) {
		Element rootNode = doc.getDocumentElement();
		JdbcConfigModel model = (JdbcConfigModel) modelmgr.getModel();
		createTextElementNode(doc, rootNode, "name", model.name);
		createMapElementNode(doc, rootNode, modelmgr, "resourceType");
		createMapElementNode(doc, rootNode, modelmgr, "description");
		Element configNode = doc.createElement("config");
		createMapNode(doc, configNode, model.values);
		new SslConfigJdbcModelParser().saveModelParts(doc, configNode, model.getSslConfigJdbcModel());
		rootNode.appendChild(configNode);
	}
}
