package com.tibco.cep.sharedresource.jndi;

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
@date Dec 29, 2009 8:34:15 PM
 */

public class JndiConfigModelParser extends AbstractSharedResourceModelParser {

	public JndiConfigModelParser() {
		super();
	}

	@Override
	public void loadModel(Document doc, SharedResModelMgr modelmgr) {
		Element root = doc.getDocumentElement();
		modelmgr.setRootAttributes(root.getAttributes());
		JndiConfigModel model = (JndiConfigModel) modelmgr.getModel();
		
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
					if (node.getNodeName().equals("OptionalJNDIProperties")) {
        				processJndiProps(model, node);
					} else {	
						model.values.put(node.getNodeName(), node.getTextContent());
					}
				}
			} else if (fileNodeName.equals("description")) {
				model.values.put(fileNodeName, fileNode.getTextContent());
			}
		}
	}

	private void processJndiProps(JndiConfigModel model, Node node) {
		NodeList childFileNodeList = node.getChildNodes();
		model.jndiProps.clear();
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
			model.jndiProps.add(map);
		}
	}
	
	@Override
	public void saveModelParts(Document doc, SharedResModelMgr modelmgr) {
		Element rootNode = doc.getDocumentElement();
		JndiConfigModel model = (JndiConfigModel) modelmgr.getModel();
		createTextElementNode(doc, rootNode, "name", model.name);
		createMapElementNode(doc, rootNode, modelmgr, "resourceType");
		createMapElementNode(doc, rootNode, modelmgr, "description");
		Element configNode = doc.createElement("config");
		createMapNode(doc, configNode, model.values);
		Element jndiPropsNode = saveJndiProps(doc, model);
		configNode.appendChild(jndiPropsNode);
		rootNode.appendChild(configNode);
	}
	
	private Element saveJndiProps(Document doc, JndiConfigModel model) {
		Element connectionNode = doc.createElement("OptionalJNDIProperties");
		for (LinkedHashMap<String, String> map: model.jndiProps) {
			Element rowNode = doc.createElement("row");
			for (Map.Entry<String, String> connectionEntry: map.entrySet()) {
				createTextElementNode(doc, rowNode, connectionEntry.getKey(), connectionEntry.getValue());
			}
			connectionNode.appendChild(rowNode);
		}
		return connectionNode;
	}
}
