/**
 * 
 */
package com.tibco.cep.runtime.channel;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * @author vpatil
 *
 */
public interface ChannelProperties {
	public static final String RESERVED_EVENT_PROP_PAYLOAD 					= "payload";
	public static final String RESERVED_EVENT_PROP_EXT_ID 					= "extId";
	public static final String RESERVED_EVENT_PROP_EVENT_ID 				= "id";
	public static final String ENTITY_NS 									= "www.tibco.com/be/ontology";
	public static final String BE_NAMESPACE 								= "_ns_";
	public static final String BE_NAME 										= "_nm_";
	
	public static final String DEFAULT_EVENT_URI 							= "DefaultEventUri";
	public static final String KEY_DESTINATION_INCLUDE_EVENTTYPE 			= "IncludeEventType";
	
	public static final String AEMETA_SERVICES_2002_NS 						= "http://www.tibco.com/xmlns/aemeta/services/2002";
	public static final ExpandedName XML_NODE_CONFIG 						= ExpandedName.makeName("config");
}
