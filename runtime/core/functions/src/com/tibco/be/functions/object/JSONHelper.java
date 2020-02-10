/**
 * 
 */
package com.tibco.be.functions.object;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tibco.be.functions.event.JSONEventInstance;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyArrayConceptReference;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * Helper class to serialize/deserialize to/from JSON
 * 
 * @author vpatil
 */
public class JSONHelper {
	private static final String ATTRIBUTES_TEXT = "attributes";
	private static String ISO_8601_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	
	private static final ThreadLocal<HashSet<Concept>> serializedEntities = new ThreadLocal<HashSet<Concept>>() {
        public HashSet<Concept> initialValue() {
            return new HashSet<Concept>();
        }
    };
	
	/**
	 * Serialize Concept object to a JSON string.
	 * 
	 * @param instance
	 * @param pretty
	 * @param root
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String serializeToJSON(Concept instance, boolean pretty, String root) throws Exception {
		Map<String, Object> jsonStructure = null;
		Map<String, Object> instanceProperties = new LinkedHashMap<String, Object>();
		
		boolean ignoreJSONRootElement = Boolean.parseBoolean(System.getProperty(SystemProperty.HTTP_JSON_IGNORE_ROOT_ELEMENT.getPropertyName(), Boolean.FALSE.toString()));
		
		if (!ignoreJSONRootElement) {
			Map<String, Object> baseStructure = new LinkedHashMap<String, Object>();
			String rootElement = (root != null && !root.isEmpty()) ? root : instance.getExpandedName().localName;
			baseStructure.put(rootElement, instanceProperties);
			jsonStructure = baseStructure;
		} else {
			jsonStructure = instanceProperties;
		}
		try {
			getProperties(instanceProperties, instance, true, !ignoreJSONRootElement);
		} finally {
			Set<Concept> serializedEntitySet = serializedEntities.get();
			serializedEntitySet.clear();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		if (pretty) mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
        
        return mapper.writeValueAsString(jsonStructure);
	}
	
	/**
	 * Deserialize a JSON string to a Concept
	 * 
	 * @param concept
	 * @param session
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static Concept deserializeToConcept(Concept concept, RuleSession session, String json) throws Exception {
		JsonFactory factory = new JsonFactory();
		JsonParser parser = factory.createParser(json);

		JSONConceptInstance jsonConceptInstance = new JSONConceptInstance(concept, session);
		jsonConceptInstance.parseJSON(parser);
		
		Concept ci = jsonConceptInstance.getConcept();

		return ci;
	}
	
	
	/**
	 * Deserialize a JSON string to a Concept
	 * 
	 * @param session
	 * @param uri
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static Concept deserializeToConcept(RuleSession session, String uri, String json) throws Exception {
		JsonFactory factory = new JsonFactory();
		JsonParser parser = factory.createParser(json);

		JSONConceptInstance jsonConceptInstance = new JSONConceptInstance(session, uri);
		jsonConceptInstance.parseJSON(parser);
		
		Concept ci = jsonConceptInstance.getConcept();

		return ci;
	}
	
	/**
	 * Fetch entity properties recursively
	 * 
	 * @param instanceProperties
	 * @param instance
	 * @param expandRefConcept
	 * @param addAttributes
	 * @throws Exception
	 */
	private static void getProperties(Map<String, Object> instanceProperties, Concept instance, boolean expandRefConcept, boolean addAttributes) throws Exception {
		if (instanceProperties != null && instance != null) {
			Set<Concept> serializedEntitySet = serializedEntities.get();
			if (serializedEntitySet.contains(instance)) {
				getAttributes(instanceProperties, instance, false);
				return;
			}
			serializedEntitySet.add(instance);
			
			if (addAttributes) getAttributes(instanceProperties, instance, expandRefConcept);
			
			if (expandRefConcept) {
				Property[] properties = null;
				if(((ConceptImpl)instance).excludeNullProps()) {
					properties = ((ConceptImpl)instance).getPropertiesNullOK();
				} else {
					properties = instance.getProperties();
				}
				for (Property property : properties) {
					if (property != null) {
						if (property instanceof PropertyArrayContainedConcept) {
							int totalContainedConcepts = ((PropertyArrayContainedConcept)property).length();
							if (totalContainedConcepts > 0) {
								List<Object> entries = new ArrayList<Object>();
								for (int i = 0; i < totalContainedConcepts; i++) {
									Concept containedConcept = (Concept)((PropertyArrayContainedConcept)property).get(i).getValue();
									if (containedConcept != null) {
										Map<String, Object> containedConceptProperties = new LinkedHashMap<String, Object>();
										instanceProperties.put(property.getName(), containedConceptProperties);
										getProperties(containedConceptProperties, containedConcept, true, true);

										entries.add(containedConceptProperties);
									}
								}
								instanceProperties.put(property.getName(), entries);
							}

						} else if (property instanceof PropertyAtomContainedConcept) {
							Concept containedConcept = ((PropertyAtomContainedConcept)property).getContainedConcept();
							if (containedConcept != null) {
								Map<String, Object> containedConceptProperties = new LinkedHashMap<String, Object>();
								getProperties(containedConceptProperties, containedConcept, true, true);

								instanceProperties.put(property.getName(), containedConceptProperties);
							}

						} else if (property instanceof PropertyArrayConceptReference) {
							int totalReferencedConcepts = ((PropertyArrayConceptReference)property).length();
							if (totalReferencedConcepts > 0) {
								List<Object> entries = new ArrayList<Object>();
								for (int i = 0; i < totalReferencedConcepts; i++) {
									Concept referencedConcept = (Concept)((PropertyArrayConceptReference)property).get(i).getValue();
									if (referencedConcept != null) {
										Map<String, Object> referencedConceptProperties = new LinkedHashMap<String, Object>();
										instanceProperties.put(property.getName(), referencedConceptProperties);
										getProperties(referencedConceptProperties, referencedConcept, ((ConceptImpl)instance).expandPropertyRefs(), true);

										entries.add(referencedConceptProperties);
									}
								}
								instanceProperties.put(property.getName(), entries);
							}

						} else if (property instanceof PropertyAtomConceptReference) {
							Concept referencedConcept = ((PropertyAtomConceptReference)property).getConcept();
							if (referencedConcept != null) {
								Map<String, Object> referencedConceptProperties = new LinkedHashMap<String, Object>();
								getProperties(referencedConceptProperties, referencedConcept, ((ConceptImpl)instance).expandPropertyRefs(), true);

								instanceProperties.put(property.getName(), referencedConceptProperties);
							}

						} else if (property instanceof PropertyArray) { 
							PropertyAtom[] propertyArray = ((PropertyArray)property).toArray();
							if (propertyArray.length > 0) {
								List<Object> entries = new ArrayList<Object>();
								for (int i=0; i < propertyArray.length; i++) {
									entries.add(propertyArray[i].getValue());
								}
								instanceProperties.put(property.getName(), entries);
							}

						} else {
							String propName = property.getName();
							Object propValue = null; 
							if (property instanceof PropertyAtomDateTime) {
								Calendar cal = ((PropertyAtomDateTime)property).getDateTime();
								if (cal != null) {
									SimpleDateFormat sdf = new SimpleDateFormat(ISO_8601_FULL_FORMAT);
									sdf.setTimeZone(cal.getTimeZone());
									propValue = sdf.format(cal.getTime());
								}
							} else {
								propValue = instance.getPropertyValue(propName);
							}
							if (propValue != null) {
								instanceProperties.put(propName, propValue);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Get Instance attributes
	 * 
	 * @param instanceAttributes
	 * @param instance
	 */
	private static void getAttributes(Map<String, Object> instanceProperties, Concept instance, boolean expandRefConcept) {
		Map<String, Object> attributes = new LinkedHashMap<String, Object>();
		
		if (!expandRefConcept) {
			attributes.put("ref", instance.getId());

		} else {
			attributes.put(Concept.ATTRIBUTE_ID, instance.getId());

			if (instance.getExtId() != null) {
				attributes.put(Concept.ATTRIBUTE_EXTID, instance.getExtId());
			}
			attributes.put(Concept.ATTRIBUTE_TYPE, instance.getType());
		}
		
		instanceProperties.put(ATTRIBUTES_TEXT, attributes);
	}
	
	/**
	 * Deserialize a Json string to an event
	 * 
	 * @param event
	 * @param session
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static SimpleEvent deserializeToEvent(SimpleEvent event, RuleSession session, String json) throws Exception {
		JsonFactory factory = new JsonFactory();
		JsonParser parser = factory.createParser(json);
		
        JSONEventInstance jsonEventInstance = new JSONEventInstance(session, event);
        jsonEventInstance.parseJSON(parser);
        
        return jsonEventInstance.getEvent();
	}
}
