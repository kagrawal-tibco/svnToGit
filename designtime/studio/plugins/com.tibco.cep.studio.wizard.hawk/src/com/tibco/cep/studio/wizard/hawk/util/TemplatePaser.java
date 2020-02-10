package com.tibco.cep.studio.wizard.hawk.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tibco.cep.studio.wizard.hawk.HawkWizardConstants;

public class TemplatePaser {

	public static Properties getProperties(String eventType) throws URISyntaxException, SAXException, IOException,
			ParserConfigurationException {
		Properties props = new Properties();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// support the namespace
		factory.setNamespaceAware(true);

		Bundle bundle = Platform.getBundle("com.tibco.cep.studio.wizard.hawk");
		URL url = bundle.getResource(HawkWizardConstants.FILE_EVENT_TEMPLATES);
		InputStream in = FileLocator.toFileURL(url).openStream();

		// Get the instance
		Document document = factory.newDocumentBuilder().parse(in);
		NodeList temp = document.getElementsByTagName(eventType);
		if (temp != null && temp.getLength() > 0) {

			NodeList propNodes = temp.item(0).getChildNodes();
			for (int i = 0; i < propNodes.getLength(); i++) {
				Node node = propNodes.item(i);
				if (HawkWizardConstants.PROPERTY_NODE_NAME.equals(node.getNodeName())) {
					props.put(node.getAttributes().getNamedItem(HawkWizardConstants.PROPERTY_NAME).getNodeValue(), node
							.getAttributes().getNamedItem(HawkWizardConstants.PROPERTY_TYPE).getNodeValue());
				}
			}
		}
		return props;
	}

}
