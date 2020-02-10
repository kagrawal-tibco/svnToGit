/**
 * 
 */
package com.tibco.cep.designtime.core.model.service.channel.util;

/**
 * @author aathalye
 *
 */
public class DriverManagerConstants {
	
	public static final String ELEMENT_DRIVER_NAME = "driver";
	public static final String ELEMENT_DRIVER_TYPE_NAME = "type";
	public static final String ELEMENT_DRIVER_LABEL_NAME = "label";
	public static final String ELEMENT_DRIVER_DESC_NAME = "description";
	public static final String ELEMENT_DRIVER_VERSION_NAME = "version";
	public static final String ELEMENT_DRIVER_EXTENDED_CONFIGURATION = "extendedConfiguration";
	public static final String ELEMENT_DRIVER_PROPERTIES_NAME = "properties";
	public static final String ELEMENT_DRIVER_SSL_PROPERTIES_NAME = "sslProperties";
	
	public static final String ELEMENT_DRIVER_DESTINATIONS_NAME = "destinations";
	public static final String ELEMENT_DRIVER_PROPERTY_NAME = "property";
	public static final String ELEMENT_DRIVER_PROPERTY_PARENT_NAME = "parent";
	
	public static final String ELEMENT_NAME = "name";
	public static final String ELEMENT_DRIVER_REFRENCES_NAME = "references";
	public static final String ELEMENT_REFRENCES_TYPE_NAME = "type";
	public static final String ELEMENT_DRIVER_CONFIGURATION_NAME = "configuration";
	public static final String ELEMENT_DRIVER_CONFIGURATION_CHOICES_NAME = "choices";
	public static final String ELEMENT_DRIVER_CONFIGURATION_CHOICE_NAME = "choice";
	public static final String ELEMENT_SERIALIZERS_NAME = "serializers";
	public static final String ELEMENT_SERIALIZER_NAME = "serializer";
	
	public static final String ATTR_DRIVER_PROPERTY_NAME = "name";
	public static final String ATTR_DRIVER_PROPERTY_TYPE = "type";
	public static final String ATTR_DRIVER_PROPERTY_DEFAULT = "default";
	public static final String ATTR_DRIVER_PROPERTY_MANDATORY = "mandatory";
	public static final String ATTR_DRIVER_PROPERTY_PATTERN_NAME = "pattern";
	
	public static final String ATTR_DRIVER_PROPERTY_CHOICE_DISPLAYED = "displayed";
	public static final String ATTR_DRIVER_PROPERTY_CHOICE_VALUE = "value";
	public static final String ATTR_SERIALIZER_TYPE = ATTR_DRIVER_PROPERTY_TYPE;
	public static final String ATTR_SERIALIZER_CLASS = "class";
	public static final String ATTR_SERIALIZER_DEFAULT = ATTR_DRIVER_PROPERTY_DEFAULT;
	
	public static final String ATTR_DRIVER_PROPERTY_MASK = "mask";
	public static final String ELEMENT_CHOICE = "choice";
	public static final String ATTR_DRIVER_PROPERTY_DISPLAY_NAME = "displayName";
	public static final String ATTR_DRIVER_PROPERTY_GV_TOGGLE = "gvToggle";
	
	/**
	 * Specific Driver names bundled out of the box
	 */
	public static final String DRIVER_JMS = "JMS";
	public static final String DRIVER_KAFKA = "Kafka";
	public static final String DRIVER_KAFKA_STREAMS = "Kafka Streams";
	public static final String DRIVER_LOCAL = "Local";
	public static final String DRIVER_RV = "RendezVous";
	public static final String DRIVER_HTTP = "HTTP";
	public static final String DRIVER_AS = "ActiveSpaces";
	public static final String DRIVER_HAWK = "Hawk";
	public static final String DRIVER_FTL = "FTL";
	public static final String DRIVER_SB = "StreamBase";
	public static final String DRIVER_AS3 = "ActiveSpaces 3.x";
	public static final String DRIVER_MQTT = "MQTT";
	public static final String DRIVER_KINESIS = "Kinesis";
	
}
