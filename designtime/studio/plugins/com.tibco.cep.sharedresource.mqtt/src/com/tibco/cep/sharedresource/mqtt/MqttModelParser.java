package com.tibco.cep.sharedresource.mqtt;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.cep.sharedresource.model.AbstractSharedResourceModelParser;
import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.sharedresource.ssl.SslConfigMqttModelParser;

import static com.tibco.cep.driver.mqtt.MqttProperties.MQTT_CHANNEL_PROPERTY_USESSL;
import static com.tibco.cep.driver.mqtt.MqttProperties.MQTT_CHANNEL_PROPERTY_BROKER_URLS;
import static com.tibco.cep.driver.mqtt.MqttProperties.MQTT_CHANNEL_PROPERTY_DESCRIPTION;
import static com.tibco.cep.driver.mqtt.MqttProperties.MQTT_CHANNEL_PROPERTY_USERNAME;
import static com.tibco.cep.driver.mqtt.MqttProperties.MQTT_CHANNEL_PROPERTY_PASSWORD;

/**
 * @author ssinghal
 *
 */
public class MqttModelParser extends AbstractSharedResourceModelParser{
	
	
	public void loadModel(Document doc, SharedResModelMgr modelmgr) {
		Element root = doc.getDocumentElement();
		modelmgr.setRootAttributes(root.getAttributes());
		MqttModel model = (MqttModel) modelmgr.getModel();

		NodeList fileNodeList = root.getChildNodes();
		for (int n = 0; n < fileNodeList.getLength(); n++) {
			Node fileNode = fileNodeList.item(n);
			if (fileNode == null) {
				continue;
			}
			String fileNodeName = fileNode.getNodeName();
			fileNode.getAttributes();
			if ((isValidFileNode(fileNodeName)) && (fileNodeName.equalsIgnoreCase("config"))) {
				NodeList childFileNodeList = fileNode.getChildNodes();
				for (int c = 0; c < childFileNodeList.getLength(); c++) {
					Node node = childFileNodeList.item(c);
					if (!isValidFileNode(node))
						continue;
					if ("ssl".equals(node.getLocalName()) || "ssl".equals(node.getNodeName()) ) {
						new SslConfigMqttModelParser().loadModel(model.sslConfigMqttModel, node);
					}else
						model.values.put(node.getNodeName(), node.getTextContent());
				}
			}
		}
	}
	
	public void saveModelParts(Document doc, SharedResModelMgr modelmgr) {
		Element root = doc.getDocumentElement();
		MqttModel model = (MqttModel) modelmgr.getModel();
		Element configNode = doc.createElement("config");
		createMapElementNode(doc, configNode, modelmgr, MQTT_CHANNEL_PROPERTY_DESCRIPTION);
		createMapElementNode(doc, configNode, modelmgr, MQTT_CHANNEL_PROPERTY_BROKER_URLS);
		createMapElementNode(doc, configNode, modelmgr, MQTT_CHANNEL_PROPERTY_USERNAME);
		createMapElementNode(doc, configNode, modelmgr, MQTT_CHANNEL_PROPERTY_PASSWORD);
		createMapElementNode(doc, configNode, modelmgr, MQTT_CHANNEL_PROPERTY_USESSL);
		//createMapNode(doc, configNode, model.values);
		new SslConfigMqttModelParser().saveModelParts(doc, configNode, model.sslConfigMqttModel);
		root.appendChild(configNode);
	}

}
