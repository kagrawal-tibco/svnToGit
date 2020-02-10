package com.tibco.cep.runtime.driver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;


/**
 * @author ssinghal
 *
 */
public abstract class DriverXmlParser {
	
	private final DocumentBuilderFactory FACTORY = DocumentBuilderFactory.newInstance();
	protected DocumentBuilder documentBuilder;
	protected Document doc;
	protected Element rootNode;

	String propName ;
	String displayName ;
	String type ;
	String defaultV;
	
	public abstract List<DriverPojo> loadDrivers(List<URL> urls) throws Exception ;
	
	protected void initRoot(URL url) throws SAXException, IOException, ParserConfigurationException {
		documentBuilder = FACTORY.newDocumentBuilder();
		doc = documentBuilder.parse(url.openStream());
		rootNode = doc.getDocumentElement(); //root node
	}
	
	protected String getElementData(String elementName) {
		NodeList typeNodes = rootNode.getElementsByTagName(elementName);
		Element typeNode = (Element)typeNodes.item(0);
		return readTextFromNode(typeNode);
	}
	
	protected List<DriverProperty> getXElementData(String xTagName) {
		
		NodeList typeNodesProperties = rootNode.getElementsByTagName(xTagName);
		Element typeNodeProperties = (Element)typeNodesProperties.item(0);
		
		List<DriverProperty> driverProperties = new ArrayList<DriverProperty>();
		
		//get all <property> children
		NodeList propertyNodes = typeNodeProperties.getElementsByTagName("property");
		for (int loop = 0, length = propertyNodes.getLength(); loop < length; loop++) {
			DriverProperty prop = new DriverProperty();
			Element propertyNode = (Element)propertyNodes.item(loop);
			if (propertyNode != null) {
				propName = propertyNode.getAttribute("name"); prop.setName(propName);
				displayName = propertyNode.getAttribute("displayName"); prop.setDisplayName(displayName);
				type = propertyNode.getAttribute("type"); prop.setType(type);
				defaultV = propertyNode.getAttribute("defaultV"); prop.setDefaultValue(defaultV);
				driverProperties.add(prop);
			}
		}
		return driverProperties;
	}
	
	
	private String readTextFromNode(Element node) {
		if (node != null) {
			NodeList children = node.getChildNodes();
			for (int loop = 0,length = children.getLength(); loop < length; loop++) {
				//Get text node
				Node item = (Node)children.item(loop);
				if (item instanceof Text) {
					return item.getNodeValue();
				}
			}
		}
		return null;
	}
	
	protected void populateData(DriverPojo cdp) {
		cdp.setType(getElementData("type"));
		cdp.setLabel(getElementData("label"));
		cdp.setClassName(getElementData("class"));
		cdp.setDescription(getElementData("description"));
		cdp.setVersion(getElementData("version"));
		
		cdp.setDriverProperties(getXElementData("properties"));
		cdp.setDriverSecurityProperties(getXElementData("security"));
		
	}

}
