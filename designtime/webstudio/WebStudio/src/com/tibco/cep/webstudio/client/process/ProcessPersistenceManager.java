package com.tibco.cep.webstudio.client.process;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;
import com.tibco.cep.webstudio.client.request.model.XMLRequestBuilderConstants;

public class ProcessPersistenceManager {
	private static Document document;
	
	private static ProcessPersistenceManager manager;
	
	private static Node persistedRootNode;
	
	private ProcessPersistenceManager() {
		
	}
	
	/**
	 * @param rootDocument
	 * @param table
	 * @param decisionTableChangeModel
	 * @param incremental
	 * @return
	 */
	public static ProcessPersistenceManager getInstance(Document rootDocument, Node rootNode, String process,String processPath) {
		if (manager == null) {
			manager  = new ProcessPersistenceManager();
		}
		document =  rootDocument;
		persistedRootNode = rootNode;
		processSaveModel(process, processPath);

		return manager;
	}

	private static void processSaveModel(String process,String processPath) {
		//Table level
		Node artifactItemElement = document.createElement(XMLRequestBuilderConstants.ARTIFACT_ITEM_ELEMENT);
		persistedRootNode.appendChild(artifactItemElement);
		
		Node artifactNameElement = document.createElement(XMLRequestBuilderConstants.ARTIFACT_PATH_ELEMENT);
		Text artifactNameText = document.createTextNode(processPath);
		artifactNameElement.appendChild(artifactNameText);
		artifactItemElement.appendChild(artifactNameElement);
		
		Node artifactTypeElement = document.createElement(XMLRequestBuilderConstants.ARTIFACT_TYPE_ELEMENT);
		Text artifactTypeText = document.createTextNode("beprocess");
		artifactTypeElement.appendChild(artifactTypeText);
		artifactItemElement.appendChild(artifactTypeElement);
		
		Node artifactExtnElement = document.createElement(XMLRequestBuilderConstants.ARTIFACT_FILE_EXTN_ELEMENT);
		Text artifactExtnText = document.createTextNode("beprocess");
		artifactExtnElement.appendChild(artifactExtnText);
		artifactItemElement.appendChild(artifactExtnElement);
		
		Text artifactContentsText = document.createTextNode(process);

		Element artifactContentElement = document.createElement(XMLRequestBuilderConstants.ARTIFACT_CONTENTS_ELEMENT);
		artifactContentElement.appendChild(artifactContentsText);
		artifactItemElement.appendChild(artifactContentElement);
	}

}
