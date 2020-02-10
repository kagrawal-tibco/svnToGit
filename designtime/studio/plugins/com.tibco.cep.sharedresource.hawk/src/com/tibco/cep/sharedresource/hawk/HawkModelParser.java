package com.tibco.cep.sharedresource.hawk;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.cep.sharedresource.model.AbstractSharedResourceModelParser;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;

public class HawkModelParser extends AbstractSharedResourceModelParser {
	public void loadModel(Document doc, SharedResModelMgr modelmgr) {
		Element root = doc.getDocumentElement();
		modelmgr.setRootAttributes(root.getAttributes());
		HawkModel model = (HawkModel) modelmgr.getModel();

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
					if (isValidFileNode(node)) {
						model.values.put(node.getNodeName(), node.getTextContent());
					}
				}
			}
		}
	}

	public void saveModelParts(Document doc, SharedResModelMgr modelmgr) {
		Element root = doc.getDocumentElement();
		HawkModel model = (HawkModel) modelmgr.getModel();
		Element configNode = doc.createElement("config");
		createMapNode(doc, configNode, model.values);
		root.appendChild(configNode);
	}
}