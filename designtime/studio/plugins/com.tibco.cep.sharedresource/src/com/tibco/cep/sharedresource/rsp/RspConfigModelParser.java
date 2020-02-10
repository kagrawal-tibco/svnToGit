package com.tibco.cep.sharedresource.rsp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.cep.sharedresource.model.AbstractSharedResourceModelParser;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;

/*
@author ssailapp
@date Feb 22, 2010 7:31:27 PM
 */

public class RspConfigModelParser extends AbstractSharedResourceModelParser {

	public RspConfigModelParser() {
		super();
	}

	@Override
	public void loadModel(Document doc, SharedResModelMgr modelmgr) {
		Element root = doc.getDocumentElement();
		modelmgr.setRootAttributes(root.getAttributes());
		RspConfigModel model = (RspConfigModel) modelmgr.getModel();
		
		NodeList fileNodeList = root.getChildNodes();
		for (int n=0; n<fileNodeList.getLength(); n++) {
			Node fileNode = fileNodeList.item(n);
			if (fileNode == null) {
				continue;
			}
			String fileNodeName = fileNode.getNodeName();
//			NamedNodeMap fileNodeAttr = fileNode.getAttributes();
			if (isValidFileNode(fileNodeName) && fileNodeName.equalsIgnoreCase("config")) {
				NodeList childFileNodeList = fileNode.getChildNodes();
				for (int c=0; c<childFileNodeList.getLength(); c++) {
					Node node = childFileNodeList.item(c);
					if (!isValidFileNode(node))
						continue;
					if (node.getNodeName().equals("repourl")) {
						model.values.put(node.getNodeName(), node.getTextContent());
					}
				}
			} else if (fileNodeName.equals("description")) {
				model.values.put(fileNodeName, fileNode.getTextContent());
			}
		}
	}

	@Override
	public void saveModelParts(Document doc, SharedResModelMgr modelmgr) {
		Element rootNode = doc.getDocumentElement();
		RspConfigModel model = (RspConfigModel) modelmgr.getModel();
		createTextElementNode(doc, rootNode, "name", model.name);
		createMapElementNode(doc, rootNode, modelmgr, "resourceType");
		createMapElementNode(doc, rootNode, modelmgr, "description");
		Element configNode = doc.createElement("config");
		createMapElementNode(doc, configNode, modelmgr, "repourl");
		rootNode.appendChild(configNode);
	}
}
