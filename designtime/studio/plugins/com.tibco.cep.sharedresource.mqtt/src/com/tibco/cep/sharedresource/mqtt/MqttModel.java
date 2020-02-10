package com.tibco.cep.sharedresource.mqtt;

import com.tibco.cep.sharedresource.model.SharedResModel;
import com.tibco.cep.sharedresource.ssl.SslConfigMqttModel;

/**
 * @author ssinghal
 *
 */
public class MqttModel extends SharedResModel {
	SslConfigMqttModel sslConfigMqttModel;
	
	public MqttModel() {
		super();
		sslConfigMqttModel = new SslConfigMqttModel();
	}

	public SslConfigMqttModel getSslConfigMqttModel() {
		return sslConfigMqttModel;
	}

}
