/**
 * 
 */
package com.tibco.cep.webstudio.client.request.model;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;

/**
 * Generic interface to be implemented by all objects which require
 * a string conversion.
 * @author aathalye
 *
 */
public interface ISerializableObject {
	
	/**
	 * Any parent node. Convert itself and any children to String
	 * @param rootNode
	 * @return
	 */
	public void serialize(Document rootDocument, Node rootNode);
	
}
