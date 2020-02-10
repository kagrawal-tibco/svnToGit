package com.tibco.cep.webstudio.client.process.properties.general;

import com.tibco.cep.webstudio.client.process.properties.TextAnnotationProperty;

/**
 * This class is used to hold the general properties of text annotation.
 * 
 * @author dijadhav
 * 
 */
public class TextAnnotationGeneralProperty extends GeneralProperty implements TextAnnotationProperty{

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -694674290910629641L;

	private String text;

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
}
