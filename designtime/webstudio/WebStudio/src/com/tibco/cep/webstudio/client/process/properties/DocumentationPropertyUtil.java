package com.tibco.cep.webstudio.client.process.properties;

import java.io.Serializable;
import java.util.List;

import com.tibco.cep.webstudio.server.model.process.Documentation;

/**
 * This is utility class used to get documentation of process, node and edge.
 * 
 * @author dijadhav
 * 
 */
public class DocumentationPropertyUtil {
	/**
	 * This method is used to get the documentation property of flow and
	 * sequence element.
	 * 
	 * @param commandData
	 * @param documentation
	 * @param elementType
	 */
	public static void populateDocumetationPropetry(
			List<Serializable> commandData, Documentation documentation,
			String elementType) {
		DocumentationProperty documentationProperty = new DocumentationProperty();
		if (null != documentation) {
			documentationProperty.setText(documentation.getText());
			documentationProperty.setDocId(documentation.getDocId());
			documentationProperty.setElementType(elementType);
		}
		commandData.add(documentationProperty);
	}}
