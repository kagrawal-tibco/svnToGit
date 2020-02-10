package com.tibco.cep.sharedresource.ascon;

import org.w3c.dom.*;

import com.tibco.cep.sharedresource.model.AbstractSharedResourceModelParser;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;

/*
@author Huabin Zhang (huzhang@tibco-support.com)
@date Dec 22, 2011 10:57:32 AM
*/

public class ASConnectionModelParser extends AbstractSharedResourceModelParser {
	
	@Override
	public void loadModel(Document doc, SharedResModelMgr modelmgr) {
        Element root = doc.getDocumentElement();
        modelmgr.setRootAttributes(root.getAttributes());
		ASConnectionModel model = (ASConnectionModel) modelmgr.getModel();

        NodeList fileNodeList = root.getChildNodes();
        for (int n=0; n<fileNodeList.getLength(); n++) {
        	Node fileNode = fileNodeList.item(n);
        	if (fileNode == null) {
        		continue;
        	}
        	String fileNodeName = fileNode.getNodeName();
        	if (isValidFileNode(fileNodeName) && fileNodeName.equalsIgnoreCase("config")) {
        		NodeList childFileNodeList = fileNode.getChildNodes();
        		for (int c=0; c<childFileNodeList.getLength(); c++) {
        			Node node = childFileNodeList.item(c);
        			if (isValidFileNode(node)) {
        				model.values.put(node.getNodeName(), node.getTextContent());
        			}
        		}
        	}
        }
	}

	@Override
	public void saveModelParts(Document doc, SharedResModelMgr modelmgr) {
		Element root = doc.getDocumentElement();
		ASConnectionModel model = (ASConnectionModel) modelmgr.getModel();
		Element configNode = doc.createElement("config");
		createMapNode(doc, configNode, model.values);
		root.appendChild(configNode);
	}

}
