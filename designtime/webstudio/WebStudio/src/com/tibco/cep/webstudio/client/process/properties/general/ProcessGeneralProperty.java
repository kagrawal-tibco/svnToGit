package com.tibco.cep.webstudio.client.process.properties.general;

import com.tibco.cep.webstudio.client.process.properties.ProcessProperty;

/**
 * This class holds general properties of process.
 * 
 * @author dijadhav
 * 
 */
public class ProcessGeneralProperty extends GeneralProperty implements ProcessProperty{
	
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -5786908462067377746L;
	
	
	/**
	 * Type of the process
	 */
	private String processType;
	/**
	 * Revision of process
	 */
	private int revision;

	/**
	 * Author of process
	 */
	private String author;

	/**
	 * @return the processType
	 */
	public String getProcessType() {
		return processType;
	}

	/**
	 * @param processType
	 *            the processType to set
	 */
	public void setProcessType(String processType) {
		this.processType = processType;
	}

	/**
	 * @return the revision
	 */
	public int getRevision() {
		return revision;
	}

	/**
	 * @param revision
	 *            the revision to set
	 */
	public void setRevision(int revision) {
		this.revision = revision;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
}
