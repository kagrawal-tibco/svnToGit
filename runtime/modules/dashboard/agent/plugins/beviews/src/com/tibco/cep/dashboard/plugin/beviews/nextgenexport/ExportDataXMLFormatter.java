package com.tibco.cep.dashboard.plugin.beviews.nextgenexport;

import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tibco.cep.dashboard.common.utils.XMLUtil;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.tree.DrillDownDataNode;
import com.tibco.cep.dashboard.plugin.beviews.nextgenexport.tree.DrillDownDataTree;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;

public class ExportDataXMLFormatter implements ExportDataFormatter {

	@Override
	public String convert(DrillDownDataTree tree, boolean includeSystemFields) throws NonFatalException {
		if (tree == null || tree.getRoots().isEmpty() == true) {
			return "";
		}
		try {
			// get the default document builder factory
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			// create a new builder
			DocumentBuilder builder = factory.newDocumentBuilder();
			// create a new document
			Document document = builder.newDocument();
			// create the root element
			Element rootNode = document.createElement("data");
			// go through all the roots of the tree
			for (DrillDownDataNode root : tree.getRoots()) {
				Element rootXMLNode = createNode(document, root, includeSystemFields);
				rootNode.appendChild(rootXMLNode);
				// populate the document
				generateDOM(document, rootXMLNode, root, includeSystemFields);
			};
			// return xml
			return XMLUtil.toCompactString(rootNode);
		} catch (ParserConfigurationException e) {
			throw new NonFatalException(e);
		}
	}

	private void generateDOM(Document document, Element parentXMLElement, DrillDownDataNode dataNode, boolean includeSystemFields) {
		for (DrillDownDataNode childDataNode : dataNode.getChildren()) {
			Element childXMLNode = createNode(document, childDataNode, includeSystemFields);
			parentXMLElement.appendChild(childXMLNode);
			generateDOM(document, childXMLNode, childDataNode, includeSystemFields);
		}
	}

	private Element createNode(Document document, DrillDownDataNode dataNode, boolean includeSystemFields) {
		Element elementNode = null;
		switch (dataNode.getKind()) {
			case TYPE:
				elementNode = document.createElement("type");
				elementNode.setAttribute("name", dataNode.getName());
				break;
			case GROUP_BY:
				elementNode = document.createElement("group");
				elementNode.setAttribute("name", dataNode.getName());
				break;
			case INSTANCE:
				elementNode = document.createElement("instance");
				Iterator<String> fieldNames = dataNode.getFieldNames(includeSystemFields);
				while (fieldNames.hasNext()) {
					String fieldName = (String) fieldNames.next();
					Element fieldNode = document.createElement("field");
					fieldNode.setAttribute("name", dataNode.getDisplayName(fieldName));
					if (includeSystemFields == true) {
						fieldNode.setAttribute("isSystem", String.valueOf(dataNode.isSystemField(fieldName)));
					}
					Node fieldValueTextNode = document.createTextNode(dataNode.getFormattedValue(fieldName));
					fieldNode.appendChild(fieldValueTextNode);
					elementNode.appendChild(fieldNode);
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported node kind [" + dataNode.getKind() + "]");
		}
		return elementNode;
	}

}
