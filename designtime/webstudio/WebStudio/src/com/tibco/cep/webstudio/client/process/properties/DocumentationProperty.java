package com.tibco.cep.webstudio.client.process.properties;

/**
 * This class is used to hold the documentation property data of process and its
 * component.
 * 
 * @author dijadhav
 * 
 */
public class DocumentationProperty extends Property {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -8274411814070445017L;
	/**
	 * Variable for documentation id.
	 */
	private String docId;
	/**
	 * Variable for documentation text.
	 */
	private String text;

	private String elementType;
	/**
	 * @return the docId
	 */
	public String getDocId() {
		return docId;
	}

	/**
	 * @param docId
	 *            the docId to set
	 */
	public void setDocId(String docId) {
		this.docId = docId;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the elementType
	 */
	public String getElementType() {
		return elementType;
	}

	/**
	 * @param elementType the elementType to set
	 */
	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

}
