/**
 * 
 */
package com.tibco.be.functions.event;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.ObjectPayload;
import com.tibco.cep.runtime.model.event.impl.XiNodePayload;
import com.tibco.cep.util.JSONXiNodeConversionUtil;

/**
 * @author vpatil
 *
 */
public class JSONEventHelper {

	/**
	 * Serialize Event object to a JSON string.
	 * 
	 * @param instance
	 * @param pretty
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String serializeToJSON(SimpleEvent event, boolean pretty) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		if (pretty) mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		
		Map<String, Object> jsonStructure = new LinkedHashMap<String, Object>();
		
		// system properties
		Map<String, Object> attributesMap = new LinkedHashMap<String, Object>();
		attributesMap.put("Id", event.getId());
		if (event.getExtId() != null) attributesMap.put("extId", event.getExtId());
		
		jsonStructure.put("attributes", attributesMap);
		
		// user properties
		for (String propertyName : event.getPropertyNames()) {
			Object propValue = event.getPropertyValue(propertyName);
			if (propValue != null) jsonStructure.put(propertyName, propValue);
		}
		
		//payload
		EventPayload payload = event.getPayload();
		if (payload != null && payload instanceof XiNodePayload) {
			XiNodePayload payloadNode = (XiNodePayload)payload;
			EventPayload jsonPayload = JSONXiNodeConversionUtil.convertXiNodeToJSON(payloadNode.getNode(), true);
			TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
			HashMap<String,Object> jsonMap = mapper.readValue(jsonPayload.toString(), typeRef);
			jsonStructure.put("payload", jsonMap);
		} else if (payload instanceof ObjectPayload){
			jsonStructure.put("payload", ((ObjectPayload)payload).toString());
		}
		
		return mapper.writeValueAsString(jsonStructure);
	}
}
