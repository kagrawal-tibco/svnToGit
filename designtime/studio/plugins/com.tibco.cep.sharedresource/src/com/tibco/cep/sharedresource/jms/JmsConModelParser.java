package com.tibco.cep.sharedresource.jms;

import java.util.LinkedHashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.cep.sharedresource.model.AbstractSharedResourceModelParser;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.sharedresource.ssl.SslConfigJmsModelParser;

/*
@author ssailapp
@date Dec 31, 2009 12:05:07 AM
 */

public class JmsConModelParser extends AbstractSharedResourceModelParser {
	
	@Override
	public void loadModel(Document doc, SharedResModelMgr modelmgr) {
		Element root = doc.getDocumentElement();
		modelmgr.setRootAttributes(root.getAttributes());
		JmsConModel model = (JmsConModel) modelmgr.getModel();
		
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
						new SslConfigJmsModelParser().loadModel(model.sslConfigJmsModel, node);
					} else if ("NamingEnvironment".equals(node.getNodeName()) ||
							"ConnectionAttributes".equals(node.getNodeName())) {
						processChildNodes(node, model);
					} else if (node.getNodeName().equals("JNDIProperties")) {
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
	
	private static void processChildNodes(Node parentNode, JmsConModel model) {
		NodeList childFileNodeList = parentNode.getChildNodes();
		for (int c=0; c<childFileNodeList.getLength(); c++) {
			Node node = childFileNodeList.item(c);
			if (!isValidFileNode(node))
				continue;
			model.values.put(node.getNodeName(), node.getTextContent());
		}
	}

	private static void processJndiProps(JmsConModel model, Node node) {
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
		JmsConModel model = (JmsConModel) modelmgr.getModel();
		createTextElementNode(doc, rootNode, "name", model.name);
		createMapElementNode(doc, rootNode, modelmgr, "resourceType");
		createMapElementNode(doc, rootNode, modelmgr, "description");
		Element configNode = doc.createElement("config");
		Element namingNode  = doc.createElement("NamingEnvironment");
		createMapElementNode(doc, namingNode, modelmgr, "UseJNDI");
		createMapElementNode(doc, namingNode, modelmgr, "ProviderURL");		
		createMapElementNode(doc, namingNode, modelmgr, "NamingURL");		
		createMapElementNode(doc, namingNode, modelmgr, "NamingInitialContextFactory");		
		createMapElementNode(doc, namingNode, modelmgr, "TopicFactoryName");		
		createMapElementNode(doc, namingNode, modelmgr, "QueueFactoryName");
		createMapElementNode(doc, namingNode, modelmgr, "NamingPrincipal");
		createMapElementNode(doc, namingNode, modelmgr, "NamingCredential");
		configNode.appendChild(namingNode);
		Element conNode  = doc.createElement("ConnectionAttributes");
		createMapElementNode(doc, conNode, modelmgr, "username");
		createMapElementNode(doc, conNode, modelmgr, "password");
		createMapElementNode(doc, conNode, modelmgr, "clientID");
		createMapElementNode(doc, conNode, modelmgr, "autoGenClientID");
		configNode.appendChild(conNode);
		createMapElementNode(doc, configNode, modelmgr, "UseXACF");
		createMapElementNode(doc, configNode, modelmgr, "useSsl");
		createMapElementNode(doc, configNode, modelmgr, "UseSharedJndiConfig");
		createMapElementNode(doc, configNode, modelmgr, "AdmFactorySslPassword");
		new SslConfigJmsModelParser().saveModelParts(doc, configNode, model.sslConfigJmsModel);
		createMapElementNode(doc, configNode, modelmgr, "JndiSharedConfiguration");
		Element jndiPropsNode = saveJndiProps(doc, model);
		configNode.appendChild(jndiPropsNode);
		rootNode.appendChild(configNode);
	}

	private static Element saveJndiProps(Document doc, JmsConModel model) {
		Element connectionNode = doc.createElement("JNDIProperties");
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
