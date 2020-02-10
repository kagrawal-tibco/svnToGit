package com.tibco.cep.sharedresource.identity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.cep.sharedresource.model.AbstractSharedResourceModelParser;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;

/*
@author ssailapp
@date Dec 28, 2009 1:10:08 PM
 */

public class IdentityConfigModelParser extends AbstractSharedResourceModelParser {

	@Override
	public void loadModel(Document doc, SharedResModelMgr modelmgr) {
        Element root = doc.getDocumentElement();
        modelmgr.setRootAttributes(root.getAttributes());
        IdentityConfigModel model = (IdentityConfigModel) modelmgr.getModel();
        
        NodeList fileNodeList = root.getChildNodes();
        for (int n=0; n<fileNodeList.getLength(); n++) {
        	Node fileNode = fileNodeList.item(n);
        	if (fileNode == null) {
        		continue;
        	}
        	String fileNodeName = fileNode.getNodeName();
//        	NamedNodeMap fileNodeAttr = fileNode.getAttributes();
        	if (isValidFileNode(fileNodeName) && fileNodeName.equalsIgnoreCase("identity")) {
        		NodeList childFileNodeList = fileNode.getChildNodes();
        		for (int c=0; c<childFileNodeList.getLength(); c++) {
        			Node node = childFileNodeList.item(c);
        			if (!isValidFileNode(node))
        				continue;
        			if (!node.getNodeName().equals("designer")) {
        				model.values.put(node.getNodeName(), node.getTextContent());
        			} else {
        				processDescriptionNode(node, model);
        			}
        		}
        	}
        }
	}
	
	/* Handle desc for identity
    <designer>
        <resourceDescriptions>
            <node>
                <description>descr123</description>
            </node>
        </resourceDescriptions>
    </designer>
	 */
	private static void processDescriptionNode(Node node, IdentityConfigModel model) {
		NodeList desChildNodes = node.getChildNodes();
		for (int c=0; c<desChildNodes.getLength(); c++) {
			Node desChildNode = desChildNodes.item(c);
			if (!isValidFileNode(desChildNode))
				continue;
			if (desChildNode.getNodeName().equals("resourceDescriptions")) {
				NodeList resChildNodes = desChildNode.getChildNodes();
				for (int r=0; r<resChildNodes.getLength(); r++) {
					Node resChildNode = resChildNodes.item(r);
					if (!isValidFileNode(resChildNode))
						continue;
					if (resChildNode.getNodeName().equals("node")) {
						NodeList nodeChildNodes = resChildNode.getChildNodes();
						for (int n=0; n<nodeChildNodes.getLength(); n++) {
							Node nodeChildNode = nodeChildNodes.item(n);
							if (!isValidFileNode(nodeChildNode))
								continue;
							if (nodeChildNode.getNodeName().equals("description")) {
								model.values.put(nodeChildNode.getNodeName(), nodeChildNode.getTextContent());
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void saveModelParts(Document doc, SharedResModelMgr modelmgr) {
		Element root = doc.getDocumentElement();
		IdentityConfigModel model = (IdentityConfigModel) modelmgr.getModel();
		Element configNode = doc.createElement("identity");
		saveDescription(doc, modelmgr, configNode);
		createMapNode(doc, configNode, model.values);
		root.appendChild(configNode);
	}

	private static void saveDescription(Document doc, SharedResModelMgr modelmgr, Element configNode) {
		Element desNode = doc.createElement("designer");
		Element resNode = doc.createElement("resourceDescriptions");
		Element nodeNode = doc.createElement("node");
		createMapElementNode(doc, nodeNode, modelmgr, "description");
		resNode.appendChild(nodeNode);
		desNode.appendChild(resNode);
		configNode.appendChild(desNode);
	}
}
