package com.tibco.cep.studio.debug.core.model;

public class MappedSourceInfo {
	
	String fileURI;
	int lineNumber;
	
	public MappedSourceInfo(String fileURI, int lineNumber) {
		super();
		this.fileURI = fileURI;
		this.lineNumber = lineNumber;
	}
	/**
	 * @return the fileURI
	 */
	public String getFileURI() {
		return fileURI;
	}
	/**
	 * @return the lineNumber
	 */
	public int getLineNumber() {
		return lineNumber;
	};
	
	

}
