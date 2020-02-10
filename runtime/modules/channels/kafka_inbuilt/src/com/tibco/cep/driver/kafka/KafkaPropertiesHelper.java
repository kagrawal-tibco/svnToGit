package com.tibco.cep.driver.kafka;

import java.util.Properties;
import java.util.Map.Entry;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;

public class KafkaPropertiesHelper {

	public static void initSslProperties(Properties channelProps, Properties clientProperties) {

		putValueIfNotEmpty(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG,
				channelProps.getProperty("javax.net.ssl.trustStore"), clientProperties);
		putValueIfNotEmpty(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG,
				channelProps.getProperty("javax.net.ssl.trustStorePassword"), clientProperties);
		putValueIfNotEmpty(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG,
				channelProps.getProperty("javax.net.ssl.trustStoreType"), clientProperties);
		putValueIfNotEmpty(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, channelProps.getProperty("javax.net.ssl.keyStore"),
				clientProperties);
		putValueIfNotEmpty(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG,
				channelProps.getProperty("javax.net.ssl.keyStorePassword"), clientProperties);
		putValueIfNotEmpty(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, channelProps.getProperty("javax.net.ssl.keyStoreType"),
				clientProperties);
		putValueIfNotEmpty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,
				channelProps.getProperty(KafkaProperties.KEY_CHANNEL_SECURITY_PROTOCOL), clientProperties);
		putValueIfNotEmpty(SaslConfigs.SASL_MECHANISM,
				channelProps.getProperty(KafkaProperties.KEY_CHANNEL_SASL_MECHANISM), clientProperties);

		if ("SASL_PLAINTEXT".equals(channelProps.getProperty(KafkaProperties.KEY_CHANNEL_SECURITY_PROTOCOL))
				|| "SASL_SSL".equals(channelProps.getProperty(KafkaProperties.KEY_CHANNEL_SECURITY_PROTOCOL))) {
			putValueIfNotEmpty(SaslConfigs.SASL_MECHANISM,
					channelProps.getProperty(KafkaProperties.KEY_CHANNEL_SASL_MECHANISM), clientProperties);
		}
	}

	/**
	 * Puts the key and value in passed Properties instance only if the value is not
	 * empty (not null and not blank string in case of string)
	 * 
	 * @param key
	 * @param value
	 * @param props
	 */
	public static void putValueIfNotEmpty(String key, Object value, Properties props) {
		if (value != null
				&& ((value instanceof String && !((String) value).trim().isEmpty()) || !(value instanceof String))) {
			props.put(key, value);
		}
	}

	/**
	 * Loads the overridden kafka client properties (if any).
	 * 
	 * @param beProperties
	 * @param destinationProps
	 * @param channelUri
	 * @param destUri
	 */
	public static void loadOverridenProperties(String basePrefix, Properties beProperties, Properties destinationProps,
			String channelUri, String destUri) {
		if (beProperties == null) {
			return;
		}
		// Read properties one level of override at a time, so that proper priorities
		// are maintained.
		String prefix = basePrefix + ".";
		for (Entry<Object, Object> prop : beProperties.entrySet()) {// Load all global kafka properties
			if (((String) prop.getKey()).startsWith(prefix)) {
				destinationProps.put(((String) prop.getKey()).substring(prefix.length()), prop.getValue());
			}
		}
		if (channelUri != null) {
			prefix = basePrefix + channelUri + ".";
			for (Entry<Object, Object> prop : beProperties.entrySet()) {// Load all channel level kafka properties
				if (((String) prop.getKey()).startsWith(prefix)) {
					destinationProps.put(((String) prop.getKey()).substring(prefix.length()), prop.getValue());
				}
			}
		}
		if (destUri != null) {
			prefix = basePrefix + destUri + ".";
			for (Entry<Object, Object> prop : beProperties.entrySet()) {// Load all destination level kafka properties
				if (((String) prop.getKey()).startsWith(prefix)) {
					destinationProps.put(((String) prop.getKey()).substring(prefix.length()), prop.getValue());
				}
			}
		}
	}

}
