package com.tibco.cep.sharedresource.mqtt;

import java.util.LinkedHashMap;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.w3c.dom.Document;

import com.tibco.cep.sharedresource.model.SharedResModelMgr;
import com.tibco.cep.studio.core.util.PersistenceUtil;

import static com.tibco.cep.driver.mqtt.MqttProperties.MQTT_CHANNEL_PROPERTY_USESSL;
import static com.tibco.cep.driver.mqtt.MqttProperties.MQTT_CHANNEL_PROPERTY_BROKER_URLS;
import static com.tibco.cep.driver.mqtt.MqttProperties.MQTT_CHANNEL_PROPERTY_DESCRIPTION;
import static com.tibco.cep.driver.mqtt.MqttProperties.MQTT_CHANNEL_PROPERTY_USERNAME;
import static com.tibco.cep.driver.mqtt.MqttProperties.MQTT_CHANNEL_PROPERTY_PASSWORD;

/**
 * @author ssinghal
 *
 */
public class MqttModelMgr extends SharedResModelMgr {
	private MqttModel model;
	private String encodedKeys[] = new String[] { MQTT_CHANNEL_PROPERTY_PASSWORD }; 

	public MqttModelMgr(IProject project, MqttEditor editor) {
		super(project, editor);
	}
	
	public MqttModelMgr(IResource resource) {
		super(resource);
	}

	@Override
	public MqttModel getModel() {
		return this.model;
	}

	@Override
	public LinkedHashMap<String, String> getPropertyNames() {
		LinkedHashMap<String, String> propertyNames = new LinkedHashMap<String, String>();
		propertyNames.put(MQTT_CHANNEL_PROPERTY_DESCRIPTION, MQTT_CHANNEL_PROPERTY_DESCRIPTION);
		propertyNames.put(MQTT_CHANNEL_PROPERTY_BROKER_URLS, MQTT_CHANNEL_PROPERTY_BROKER_URLS);
		propertyNames.put(MQTT_CHANNEL_PROPERTY_USERNAME, MQTT_CHANNEL_PROPERTY_USERNAME);
		//propertyNames.put(MQTT_CHANNEL_PROPERTY_PASSWORD, MQTT_CHANNEL_PROPERTY_PASSWORD);
		return propertyNames;
	}

	@Override
	public void parseModel() {
		initModel();
		MqttModelParser parser = new MqttModelParser();
		if (getFilePath() != null) {
			parser.loadModel(getFilePath(), this);
		} else if (getEditor().getDocument() != null) {
			parser.loadModel(getEditor().getDocument(), this);	
		}
		decodeFields(encodedKeys);
	}
	
	@Override
	public String saveModel() {
		encodeFields(encodedKeys);
		Document doc = MqttModelParser.getSaveDocument(getRootAttributes(), "MqttSharedResource");
		new MqttModelParser().saveModelParts(doc, this);
		decodeFields(encodedKeys);	// do this, so that the model always contains the decoded version of the values
		String docString = PersistenceUtil.getDocumentString(doc);
		return docString;
	}
	
	public void initModel() {
		this.model = new MqttModel();
		this.model.values.put(MQTT_CHANNEL_PROPERTY_DESCRIPTION, "");
		this.model.values.put(MQTT_CHANNEL_PROPERTY_BROKER_URLS, "tcp://localhost:1883");
		this.model.values.put(MQTT_CHANNEL_PROPERTY_USERNAME, "");
		this.model.values.put(MQTT_CHANNEL_PROPERTY_PASSWORD, "");
		this.model.values.put(MQTT_CHANNEL_PROPERTY_USESSL, "false");
	}

}
