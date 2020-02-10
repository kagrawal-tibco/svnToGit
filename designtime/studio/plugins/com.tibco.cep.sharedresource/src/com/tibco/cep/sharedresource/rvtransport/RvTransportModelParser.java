package com.tibco.cep.sharedresource.rvtransport;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.cep.sharedresource.model.AbstractSharedResourceModelParser;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.sharedresource.ssl.SslConfigRvModelParser;

/*
@author ssailapp
@date Dec 29, 2009 5:21:29 PM
 */

public class RvTransportModelParser extends AbstractSharedResourceModelParser {

	@Override
	public void loadModel(Document doc, SharedResModelMgr modelmgr) {
        Element root = doc.getDocumentElement();
        modelmgr.setRootAttributes(root.getAttributes());
		RvTransportModel model = (RvTransportModel) modelmgr.getModel();
        
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
        			if (!"ssl".equals(node.getLocalName()) && !"ssl".equals(node.getNodeName()) ) {
        				model.values.put(node.getNodeName(), node.getTextContent());
        			} else {
        				new SslConfigRvModelParser().loadModel(model.sslConfigRvModel, node);
        			}
        		}
        	}
        }
	}
	
	@Override
	public void saveModelParts(Document doc, SharedResModelMgr modelmgr) {
		Element root = doc.getDocumentElement();
		RvTransportModel model = (RvTransportModel) modelmgr.getModel();
		Element configNode = doc.createElement("config");
		createMapNode(doc, configNode, model.values);
		new SslConfigRvModelParser().saveModelParts(doc, configNode, model.sslConfigRvModel);
		root.appendChild(configNode);
	}
}
