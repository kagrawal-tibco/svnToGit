/**
 * 
 */
package com.tibco.cep.studio.rms.client.builder.impl;

import java.io.StringWriter;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tibco.cep.studio.rms.client.IRequestBuilderInput;

/**
 * @author aathalye
 *
 */
public class ApprovalsRequestBuilderInput implements IRequestBuilderInput {
	
	private Vector<Map<String, String>> input;
	
	public ApprovalsRequestBuilderInput(final Vector<Map<String, String>> input) {
		this.input = input;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.mgmtserver.rms.IRequestBuilderInput#buildRequest()
	 */
	public Object buildRequest() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		Element root = doc.createElementNS(
				"www.tibco.com/be/ontology/Approval/Events/RouterEvent",
				"entities");
		doc.appendChild(root);

		for (Map<String, String> entityMap : input) {
			Element versionableEntityElement = doc
					.createElement("versionableEntity");
			//versionableEntityElement.setAttribute("Id", "ID---8");
			versionableEntityElement.setAttribute("extId", entityMap
					.get("ExtID"));
			root.appendChild(versionableEntityElement);

			Element statusElement = doc.createElement("status");
			statusElement.appendChild(doc.createTextNode(entityMap
					.get("Status")));
			versionableEntityElement.appendChild(statusElement);

			Element entityNameElement = doc.createElement("entityName");
			entityNameElement.appendChild(doc.createTextNode(entityMap
					.get("EntityName")));
			versionableEntityElement.appendChild(entityNameElement);

			Element entityIDElement = doc.createElement("entityID");
			entityIDElement.appendChild(doc.createTextNode(entityMap
					.get("EntityID")));
			versionableEntityElement.appendChild(entityIDElement);

			Element versionElement = doc.createElement("version");
			versionElement.appendChild(doc.createTextNode(entityMap
					.get("Version")));
			versionableEntityElement.appendChild(versionElement);
			
			Element rmsProjectElement = doc.createElement("referredProject");
			rmsProjectElement.appendChild(doc.createTextNode(entityMap
					.get("RMSProject")));
			versionableEntityElement.appendChild(rmsProjectElement);
			
			Element commentsElement = doc.createElement("approverComments");
			String comments = entityMap.get("Approver Comments");
			if (comments != null) {
				commentsElement.appendChild(doc.createTextNode(comments));
			}
			versionableEntityElement.appendChild(commentsElement);
		}
		StringWriter sw = new StringWriter();
		// XMLSerializer ser = new XMLSerializer(sw, new OutputFormat(doc));
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(sw);
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		transformer.transform(source, result);
		String entityStr = sw.toString();
		return entityStr;
	}
}
