package com.tibco.cep.dashboard.plugin.beviews.export;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tibco.cep.dashboard.common.utils.XMLUtil;

/**
 * @author rajesh
 * 
 */
public class XMLExportContentFormatter implements ExportContentFormatter {

	public String transform(ExportContentNode root, boolean exportSystemFields) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();
		Element dataElement = createDOMNode(document, root, exportSystemFields);
		generateDOM(root, document, dataElement, exportSystemFields);
		return XMLUtil.toString(dataElement);
	}

	@SuppressWarnings("unchecked")
	private void generateDOM(ExportContentNode contentNode, Document document, Element dataElement, boolean exportSystemFields) {
		Enumeration enumChildNodes = contentNode.children();
		while (enumChildNodes.hasMoreElements()) {
			ExportContentNode contentChildNode = (ExportContentNode) enumChildNodes.nextElement();
			Element dataChildElement = createDOMNode(document, contentChildNode, exportSystemFields);
			dataElement.appendChild(dataChildElement);
			if (!contentChildNode.IsDeleted()) {
				generateDOM(contentChildNode, document, dataChildElement, exportSystemFields);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Element createDOMNode(Document document, ExportContentNode contentChildNode, boolean exportSystemFields) {
		Element elementTag = null;
		if (contentChildNode.getNodeType().intern() == ExportContentNode.TYPE_HEADER_ROW) {
			elementTag = document.createElement("type");
			elementTag.setAttribute("name", contentChildNode.getUserObject().toString());
		} else if (contentChildNode.getNodeType().intern() == ExportContentNode.TYPE_GROUP_ROW) {
			elementTag = document.createElement("group");
			elementTag.setAttribute("name", contentChildNode.getUserObject().toString());
		} else {
			elementTag = document.createElement("instance");
			List<TupleFieldExportData> fields = (List<TupleFieldExportData>) contentChildNode.getUserObject();
			Iterator<TupleFieldExportData> fieldsIterator = fields.iterator();
			while (fieldsIterator.hasNext()) {
				TupleFieldExportData field = fieldsIterator.next();
				if (field.isSystem() == true && exportSystemFields == false) {
					continue;
				}
				Element fieldTag = document.createElement("field");
				fieldTag.setAttribute("name", field.getName());
				if (exportSystemFields == true) {
					fieldTag.setAttribute("isSystem", String.valueOf(field.isSystem()));
				}
				Node fieldNameText = document.createTextNode(String.valueOf(field.getValue()));
				fieldTag.appendChild(fieldNameText);
				elementTag.appendChild(fieldTag);
			}
		}
		return elementTag;
	}

	public void transform(ExportContentNode root, boolean isSystemFieldSupported, PrintWriter printWriter) throws Exception {
		printWriter.print(transform(root, isSystemFieldSupported));
	}

}