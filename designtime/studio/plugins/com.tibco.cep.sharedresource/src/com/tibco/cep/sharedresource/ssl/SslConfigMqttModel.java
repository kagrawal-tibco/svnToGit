package com.tibco.cep.sharedresource.ssl;

//import static com.tibco.cep.driver.mqtt.MqttProperties.MQTT_CHANNEL_PROPERTY_REQUIRES_CLIENT_AUTH;

/**
 * @author ssinghal
 *
 */
public class SslConfigMqttModel extends SslConfigModel{
	
	public static final String ID_CLIENT_AUTH = "requiresClientAuthentication" ; /*MQTT_CHANNEL_PROPERTY_REQUIRES_CLIENT_AUTH;*/
	
	public String clientAuth;
	
	public String getClientAuth() {
		return clientAuth;
	}

	public SslConfigMqttModel() {
		super();
	}

}
