/**
 * 
 */
package com.tibco.cep.webstudio.client.process;

import static com.tibco.cep.webstudio.client.process.ProcessPersistenceManager.getInstance;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;

/**
 * @author dijadhav
 * 
 */
public class ProcessDataItem implements IRequestDataItem {
	private String process;
    private String artifactPath;
	/**
	 * @param process
	 */
	public ProcessDataItem(String process,String artifactPath) {
		this.process = process;
		this.artifactPath =artifactPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.webstudio.client.request.model.ISerializableObject#serialize
	 * (com.google.gwt.xml.client.Document, com.google.gwt.xml.client.Node)
	 */
	@Override
	public void serialize(Document rootDocument, Node rootNode) {
		getInstance(rootDocument, rootNode, process,artifactPath);

	}

	/**
	 * @return the process
	 */
	public String getProcess() {
		return process;
	}

	/**
	 * @param process the process to set
	 */
	public void setProcess(String process) {
		this.process = process;
	}

}
