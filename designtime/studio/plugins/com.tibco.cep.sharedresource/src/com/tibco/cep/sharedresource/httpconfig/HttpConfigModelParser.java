package com.tibco.cep.sharedresource.httpconfig;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.cep.sharedresource.model.AbstractSharedResourceModelParser;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.sharedresource.ssl.SslConfigHttpModelParser;

/*
@author ssailapp
@date Dec 22, 2009 5:56:24 PM
 */

public class HttpConfigModelParser extends AbstractSharedResourceModelParser {

	@Override
	public void loadModel(Document doc, SharedResModelMgr modelmgr) {
        Element root = doc.getDocumentElement();
        modelmgr.setRootAttributes(root.getAttributes());
		HttpConfigModel model = (HttpConfigModel) modelmgr.getModel();
        
        NodeList fileNodeList = root.getChildNodes();
        for (int n=0; n<fileNodeList.getLength(); n++) {
        	Node fileNode = fileNodeList.item(n);
        	if (fileNode == null) {
        		continue;
        	}
        	String fileNodeName = fileNode.getNodeName();
//        	NamedNodeMap fileNodeAttr = fileNode.getAttributes();
        	if (isValidFileNode(fileNodeName) && fileNodeName.equalsIgnoreCase("config")) {
        		NodeList childFileNodeList = fileNode.getChildNodes();
        		for (int c=0; c<childFileNodeList.getLength(); c++) {
        			Node node = childFileNodeList.item(c);
        			if (!isValidFileNode(node))
        				continue;
        			if ("ssl".equals(node.getLocalName()) || "ssl".equals(node.getNodeName()) ) {
        				new SslConfigHttpModelParser().loadModel(model.sslConfigHttpModel, node);
        			// } else if ("HttpConnectionProperties".equals(node.getLocalName()) || "HttpConnectionProperties".equals(node.getNodeName()) ) {
        			//	processHttpConnectionProps(model, node);
        			} else {
        				model.values.put(node.getNodeName(), node.getTextContent());
        			}
        		}
        	}
        }
	}

	/*
	private static void processHttpConnectionProps(HttpConfigModel model, Node node) {
		NodeList childFileNodeList = node.getChildNodes();
		model.connectionProps.clear();
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
			model.connectionProps.add(map);
		}
	}
	*/

	@Override
	public void saveModelParts(Document doc, SharedResModelMgr modelmgr) {
		Element root = doc.getDocumentElement();
		HttpConfigModel model = (HttpConfigModel) modelmgr.getModel();
		Element configNode = doc.createElement("config");
		new SslConfigHttpModelParser().saveModelParts(doc, configNode, model.sslConfigHttpModel);
		createMapNode(doc, configNode, model.values);
		//Element connectionNode = saveHttpConnectionProps(doc, model);
		//configNode.appendChild(connectionNode);
		root.appendChild(configNode);
	}

	/*
	private static Element saveHttpConnectionProps(Document doc, HttpConfigModel model) {
		Element connectionNode = doc.createElement("HttpConnectionProperties");
		for (LinkedHashMap<String, String> map: model.connectionProps) {
			Element rowNode = doc.createElement("row");
			for (Map.Entry<String, String> connectionEntry: map.entrySet()) {
				createTextElementNode(doc, rowNode, connectionEntry.getKey(), connectionEntry.getValue());
			}
			connectionNode.appendChild(rowNode);
		}
		return connectionNode;
	}
	*/
}
