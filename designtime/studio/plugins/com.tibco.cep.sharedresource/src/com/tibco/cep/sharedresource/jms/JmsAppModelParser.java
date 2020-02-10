package com.tibco.cep.sharedresource.jms;

import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.cep.sharedresource.model.AbstractSharedResourceModelParser;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;

/*
@author ssailapp
@date Dec 31, 2009 12:05:07 AM
 */

public class JmsAppModelParser extends AbstractSharedResourceModelParser {
	
	@Override
	public void loadModel(Document doc, SharedResModelMgr modelmgr) {
		Element root = doc.getDocumentElement();
		modelmgr.setRootAttributes(root.getAttributes());
		JmsAppModel model = (JmsAppModel) modelmgr.getModel();
		
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
					if ("Properties".equals(node.getLocalName()) || "Properties".equals(node.getNodeName()) ) {
        				processJmsAppProps(model, node);
					}
				}
			} else if (fileNodeName.equals("description")) {
				model.values.put(fileNodeName, fileNode.getTextContent());
			}
		}
	}
	
	private static void processJmsAppProps(JmsAppModel model, Node node) {
		NodeList childFileNodeList = node.getChildNodes();
		model.appProps.clear();
		for (int c=0; c<childFileNodeList.getLength(); c++) {
			Node chdNode = childFileNodeList.item(c);
			if (!isValidFileNode(chdNode))
				continue;
			NodeList propNodeList = chdNode.getChildNodes();
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			for (int p=0; p<propNodeList.getLength(); p++) {
				Node propNode = propNodeList.item(p);
				if (!isValidFileNode(propNode))
					continue;
				map.put(propNode.getNodeName(), propNode.getTextContent());
			}
			model.appProps.add(map);
		}
	}
	
	@Override
	public void saveModelParts(Document doc, SharedResModelMgr modelmgr) {
		Element rootNode = doc.getDocumentElement();
		JmsAppModel model = (JmsAppModel) modelmgr.getModel();
		createTextElementNode(doc, rootNode, "name", model.name);
		createMapElementNode(doc, rootNode, modelmgr, "resourceType");
		createMapElementNode(doc, rootNode, modelmgr, "description");
		Element configNode = doc.createElement("config");
		Element appPropsNode = saveJmsAppProps(doc, model);
		configNode.appendChild(appPropsNode);
		rootNode.appendChild(configNode);
	}

	private static Element saveJmsAppProps(Document doc, JmsAppModel model) {
		Element connectionNode = doc.createElement("Properties");
		for (LinkedHashMap<String, String> map: model.appProps) {
			Element rowNode = doc.createElement("row");
			for (Map.Entry<String, String> connectionEntry: map.entrySet()) {
				createTextElementNode(doc, rowNode, connectionEntry.getKey(), connectionEntry.getValue());
			}
			connectionNode.appendChild(rowNode);
		}
		return connectionNode;
	}
}
