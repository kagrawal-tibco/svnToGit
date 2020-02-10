package com.tibco.cep.decision.table;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author sasahoo
 *
 */
public class MetaDataPropertyCatalog {

	private static MetaDataPropertyCatalog metaDataCatalog;	
	private static HashMap<String, String> metaDataPropertyMap;

	private MetaDataPropertyCatalog() {
	}

	synchronized public static MetaDataPropertyCatalog getInstance() {
		if (metaDataCatalog == null) {
			metaDataCatalog = new MetaDataPropertyCatalog();
			processMetaDataCatalogs();
		}
		return metaDataCatalog;
	}

	private static void processMetaDataCatalogs() {

		try {
			metaDataPropertyMap = new HashMap<String, String>();
			InputStream stream = MetaDataPropertyCatalog.class.getClassLoader().getResourceAsStream("metadata.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stream);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("table");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					NodeList nlList = eElement.getElementsByTagName("property");
					for (int tmp = 0; tmp < nlList.getLength(); tmp++) {
						Node node = nlList.item(tmp);
						Element element = (Element) node;
						NamedNodeMap nameNodeMap = element.getAttributes();
						metaDataPropertyMap.put(nameNodeMap.getNamedItem("name").getNodeValue(), nameNodeMap.getNamedItem("type").getNodeValue());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param sTag
	 * @param eElement
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getTagValue(String sTag, Element eElement)
	{
		NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0); 
		return nValue.getNodeValue();
	}

	/**
	 * @return
	 */
	public HashMap<String, String> getMetaDataPropertyMap() {
		return metaDataPropertyMap;
	}

}