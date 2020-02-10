/**
 * 
 */
package com.tibco.cep.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tibco.be.util.BEStringUtilities;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.impl.ObjectPayload;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValue;
import com.tibco.xml.data.primitive.XmlNode;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.values.XsBoolean;
import com.tibco.xml.data.primitive.values.XsDecimal;
import com.tibco.xml.data.primitive.values.XsInteger;
import com.tibco.xml.data.primitive.values.XsLong;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSupport;
import com.tibco.xml.tns.TargetNamespace;
import com.tibco.xml.tns.impl.TargetNamespaceCache;

/**
 * Conversion functions to and from JSON to XINode and back.
 * 
 * @author vpatil
 */
public class JSONXiNodeConversionUtil {
	private static int namespaceCnt = 1;
	private static Map<String, String> namespaceKeyToTypeMap = new HashMap<String,String>();
	
	public static EventPayload convertXiNodeToJSON(XiNode rootNode, boolean includeNamespace) throws Exception {
		String baseName = rootNode.getName().toString();
		String defaultNamespace = getNameSpace(rootNode, null);
		
		// if the above fails, do a final string check for {}
		if ((defaultNamespace == null || defaultNamespace.isEmpty()) && baseName.indexOf("}") != -1) {
			defaultNamespace = baseName.substring(1, baseName.indexOf("}"));
		}
		if (defaultNamespace != null && !defaultNamespace.isEmpty()) {
			baseName = baseName.substring(defaultNamespace.length()+2); // 2 for {}
		}
		
		boolean ignoreJSONRootElement = Boolean.parseBoolean(System.getProperty(SystemProperty.HTTP_JSON_IGNORE_ROOT_ELEMENT.getPropertyName(), Boolean.FALSE.toString()));
		Map<String, Object> xmlMap = new LinkedHashMap<String, Object>();
		if (isSingleElement(rootNode)) {
			xmlMap.put(baseName,rootNode.getFirstChild().getStringValue());
			if (defaultNamespace != null && !defaultNamespace.isEmpty() && includeNamespace && !ignoreJSONRootElement) {
				Map<String, Object> attributeMap = new HashMap<String, Object>();
				xmlMap.put("attributes", attributeMap);
				attributeMap.put("type", defaultNamespace);
			}
		} else {
			Concept rootEntity = getEntityByUri(rootNode.getName());
			if (ignoreJSONRootElement) {
				xmlMap = createMap(rootNode, defaultNamespace, !ignoreJSONRootElement, includeNamespace, rootEntity);
			} else {
				xmlMap.put(baseName, createMap(rootNode, defaultNamespace, !ignoreJSONRootElement, includeNamespace, rootEntity));
			}
		}

		ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		jsonMapper.setSerializationInclusion(Include.NON_EMPTY);
		String jsonPayload = jsonMapper.writeValueAsString(xmlMap);
		
		return new ObjectPayload(jsonPayload);
	}
	
	private static XiNode getFirstChild(XiNode node) {
		XiNode child = node.getFirstChild();
		while (child.getName() == null) {
			child = child.getNextSibling();
		}
		return child;
	}
	
	private static Map<String, Object> createMap(XiNode node, String defaultNamespace, boolean processRootNode, boolean includeNamespace, Concept parentEntity) throws Exception {        
        Map<String, Object> nodeMap = new LinkedHashMap<String, Object>();
        
        if (nonArrayAttributesExists(node) || (defaultNamespace != null && !defaultNamespace.isEmpty() && processRootNode)) {
        	Map<String, Object> attributeMap = new HashMap<String, Object>();
        	nodeMap.put("attributes", attributeMap);
        	Iterator<XiNode> attributeIterator = node.getAttributes();
        	if (defaultNamespace != null && !defaultNamespace.isEmpty() && processRootNode && includeNamespace) {
        		attributeMap.put("type", defaultNamespace);
        		processRootNode = false;
        	}
        	if (node.hasAttributes()) {
        		while (attributeIterator.hasNext()) {
        			XiNode attributeItem = attributeIterator.next();
        			if (!attributeItem.getName().toString().equals("isArray")) {
        				attributeMap.put(attributeItem.getName().toString(), attributeItem.getStringValue());
        			}
        		}
        	} 
        }
    	
    	Iterator<XiNode> nodeList = node.getChildren();
    	while (nodeList.hasNext()) {
    		XiNode currentNode = nodeList.next();
    		if (currentNode.getName() == null) continue;
    		boolean isArray = isArrayType(currentNode, parentEntity);
    		defaultNamespace = getNameSpace(currentNode, defaultNamespace);
    		ExpandedName currentNodeName = currentNode.getName();
    		String nodeName = getNodeName(currentNodeName.toString(), defaultNamespace);
    		if (currentNode.getFirstChild() != null) {
    			if (currentNode.getFirstChild().getNodeKind() == XmlNodeKind.ELEMENT) {
    				//String nodeKey = getNodeName(currentNode.getName().toString(), defaultNamespace);
    				Object nodeValue = nodeMap.get(nodeName);
    				
    				Object valueItem = createMap(currentNode, defaultNamespace, processRootNode, includeNamespace, getEntityByProperty(parentEntity, currentNode.getName().localName));
    				if (isArray || isArrayElement(currentNode)) {
    					if (nodeValue == null) {
    						nodeValue = new ArrayList<Object>();
    						nodeMap.put(nodeName, nodeValue);
    					}
    					((List<Object>) nodeValue).add(valueItem);
    				} else {
    					nodeValue = valueItem;
    					nodeMap.put(nodeName, nodeValue);
    				}
    			} else if (currentNode.getFirstChild().getNodeKind() == XmlNodeKind.TEXT) {
    				Object nodeValue = nodeMap.get(nodeName);
    				if (isArray || isArrayElement(currentNode)) {
    					if (nodeValue == null) {
    						nodeValue = new ArrayList<String>();
    						nodeMap.put(getNodeName(nodeName, defaultNamespace), nodeValue);
    					}
    					((List<String>)nodeValue).add(currentNode.getStringValue());
    				} else {
    					//cast as typed object using xml typed value
    					XmlTypedValue typedValue = currentNode.getTypedValue();
    					if(typedValue != null) {
    						XmlAtomicValue atomicValue = typedValue.getAtom(0);
    						if(typedValue instanceof XsInteger) {
    							nodeValue = atomicValue.castAsInt();
    						} else if(typedValue instanceof XsLong) {
    							nodeValue = atomicValue.castAsLong();
    						} else if(typedValue instanceof XsDecimal) {
    							nodeValue = atomicValue.castAsDecimal();
    						} else if(typedValue instanceof XsBoolean) {
    							nodeValue = atomicValue.castAsBoolean();
    						} else {
    							//if not one of the atomic types, use string value
    							//e.g. date/time, binary(base64/hex)
        						nodeValue = atomicValue.toString();
    						}
    					}
    					nodeMap.put(getNodeName(nodeName, defaultNamespace), nodeValue);
    				}
    			}
    		} else {	// Set Explicit Nil brings the control flow here
    			nodeMap.put(nodeName, null);
    		}
    	}
        return nodeMap;
    }
	
	private static boolean isArrayType(XiNode currentNode, Concept parentEntity) {
		XiNode parentNode = (currentNode.getParentNode().getNodeKind() != XmlNodeKind.DOCUMENT) ? currentNode.getParentNode() : currentNode;

		boolean isArray = false;
		if (parentNode.getName().namespaceURI == null) {
			isArray = isArrayProperty(parentEntity, currentNode.getName().localName);
		} else {
			RuleServiceProvider RSP = RuleServiceProviderManager.getInstance().getDefaultProvider();
			final TargetNamespaceCache tnsCache = RSP.getProject().getTnsCache();
			
			TargetNamespace namespace = tnsCache.getNamespaceProvider().getNamespace(parentNode.getName().namespaceURI, SmNamespace.class);
			if (namespace != null) {
				SmModelGroup grp = null; boolean depthSearch = false;
				SmElement component = (SmElement) namespace.getComponent(parentNode.getName().getLocalName(), SmComponent.ELEMENT_TYPE);
				// one more try with complete name(i.e. with namespace)
				if (component == null) component = (SmElement) namespace.getComponent(parentNode.getName().toString(), SmComponent.ELEMENT_TYPE);
				if (component != null) {
					grp = component.getType().getContentModel();
				} else {
					TypeManager.TypeDescriptor descriptor = RSP.getTypeManager().getTypeDescriptor(stripNSPrefix(namespace.getNamespaceURI()));
					SmElement elementNS = descriptor.getSmElement();
					SmElement elementPayload = SmSupport.getElementInContext(elementNS.getType(), null, EventPayload.PAYLOAD_PROPERTY);
                    if (elementPayload != null) {
    					grp = elementPayload.getType().getContentModel();
    					depthSearch = true;
                    }
				}
				
				SmParticle particle = getMatchingParticle(grp, currentNode.getName(), depthSearch);
				if (particle != null && particle.getMaxOccurrence() == SmParticle.UNBOUNDED) {
					isArray = true;
				}
			}
		}

		return isArray;
	}
	
	private static SmParticle getMatchingParticle(SmModelGroup grp, ExpandedName currentNodeName, boolean depthSearch) {
		if (grp != null) {
			Iterator<?> itr = grp.getParticles();
			while (itr.hasNext()) {
				SmParticle particle = (SmParticle) itr.next();
				if (particle.getTerm().getExpandedName().equals(currentNodeName)) {
					return particle;
				} else if (depthSearch) {
					SmElement element = (SmElement) particle.getTerm();
					if (element != null && element.getType() != null && element.getType().getAtomicType() == null) {
						return getMatchingParticle(element.getType().getContentModel(), currentNodeName, depthSearch);
					}
				}
			}
		}
		return null;
	}
	
	private static boolean isArrayProperty(Concept parentEntity, String propertyName) {
		if (parentEntity != null) {
			PropertyDefinition prop = parentEntity.getPropertyDefinition(propertyName, false);
			if (prop != null) {
				return prop.isArray();
			}
		}
		return false;
	}
	
	private static Concept getEntityByProperty(Concept parentEntity, String propertyName) throws ClassNotFoundException {
		if (parentEntity != null) {
			PropertyDefinition prop = parentEntity.getPropertyDefinition(propertyName, false);
			if (prop != null) {
				Concept propConcept = prop.getConceptType();
				if (propConcept != null) return propConcept;
			}
		}
		return parentEntity;
	}
	
	private static Concept getEntityByUri(ExpandedName entityURI) {
        if (entityURI.namespaceURI == null) return null;
		RuleServiceProviderManager RSPM = RuleServiceProviderManager.getInstance();
		Entity entity = RSPM.getDefaultProvider().getProject().getOntology().getEntity(entityURI);
		return (entity instanceof Concept) ? (Concept) entity : null;
	}
	
	private static boolean nonArrayAttributesExists(XiNode node) {
		boolean hasArray = false;
		int attributeCnt = 0;
		if (node.hasAttributes()) {
			Iterator<XiNode> attributeIterator = node.getAttributes();
			while (attributeIterator.hasNext()) {
    			XiNode attributeItem = attributeIterator.next();
    			if (attributeItem.getName().toString().equals("isArray")) hasArray = true;
    			attributeCnt++;
			}
			if (attributeCnt == 1 && hasArray) return false;
			return true;
		}
		return false;
	}
	
	private static boolean isArrayElement(XiNode node) {
		if (node.hasAttributes()) {
			Iterator<XiNode> attributeIterator = node.getAttributes();
			while (attributeIterator.hasNext()) {
    			XiNode attributeItem = attributeIterator.next();
    			if (attributeItem.getName().toString().equals("isArray") && attributeItem.getStringValue().equals("true")) return true;
			}
		}
		return false;
	}
    
    private static boolean isSingleElement(XiNode rootNode) {
    	if (rootNode != null) {
    		XiNode firstChild = getFirstChild(rootNode);
    		if (firstChild != null && firstChild.getNodeKind() == XmlNodeKind.TEXT) return true;
    	}
    	return false;
    }
    
    private static String getNodeName(String name, String namespace) {
    	return (namespace != null && name.indexOf(namespace) != -1) ? name.substring(namespace.length()+2) : name;
    }
    
    private static Object isJSONPayload(Object payload) {
    	String jsonPayload = null;
    
    	if (payload instanceof String) {
    		jsonPayload = (String) payload;
    	} else if (payload instanceof byte[]) {
    		jsonPayload = BEStringUtilities.convertByteArrayToString(payload, "UTF-8");		
    	}
    	
    	try {
    		final ObjectMapper mapper = new ObjectMapper();
    		return mapper.readValue(jsonPayload, JsonNode.class);
    	} catch (IOException ioException) {
    		return null;
    	}
    }
    
    public static Object convertJSONToXiNode(Object payload, String namespace) {
    	JsonNode rootJsonNode = (JsonNode) isJSONPayload(payload);
    	if (rootJsonNode != null) {
    		StringBuffer xmlBuffer = new StringBuffer();
    		boolean ignoreJSONRootElement = Boolean.parseBoolean(System.getProperty(SystemProperty.HTTP_JSON_IGNORE_ROOT_ELEMENT.getPropertyName(), Boolean.FALSE.toString()));
    		getXMLPart(rootJsonNode, xmlBuffer, namespace, true, !ignoreJSONRootElement);
    		if (xmlBuffer.length() > 0) {
    			String xiPayload = xmlBuffer.toString();
//    			xiPayload =  ignoreJSONRootElement ? wrapInRootElement(xiPayload, payTerm) : xiPayload;
    			clearNamespaceReferences();
    			return (payload instanceof byte[]) ? xiPayload.getBytes() : xiPayload;
    		}
    	}
    	
    	return null;
    }
    
    public static Object convertJSONToXiNode(Object payload, SmParticleTerm payTerm) {
    	String defaultRootNameSpace = payTerm.getNamespace();
    	JsonNode rootJsonNode = (JsonNode) isJSONPayload(payload);
    	if (rootJsonNode != null) {
    		StringBuffer xmlBuffer = new StringBuffer();
    		boolean ignoreJSONRootElement = Boolean.parseBoolean(System.getProperty(SystemProperty.HTTP_JSON_IGNORE_ROOT_ELEMENT.getPropertyName(), Boolean.FALSE.toString()));
    		getXMLPart(rootJsonNode, xmlBuffer, defaultRootNameSpace, isEntityPayload(defaultRootNameSpace), !ignoreJSONRootElement);
    		if (xmlBuffer.length() > 0) {
    			String xiPayload = xmlBuffer.toString();
    			xiPayload =  ignoreJSONRootElement ? wrapInRootElement(xiPayload, payTerm) : xiPayload;
    			clearNamespaceReferences();
    			return (payload instanceof byte[]) ? xiPayload.getBytes() : xiPayload;
    		}
    	}
    	
    	return null;
    }
    
    private static boolean isEntityPayload(String defaultNameSpace) {
    	if (defaultNameSpace == null) return true;
    	String baseName = defaultNameSpace.substring(defaultNameSpace.lastIndexOf("/")+1);
    	ExpandedName entityExpandedName = ExpandedName.makeName(defaultNameSpace, baseName);
    	RuleServiceProvider RSP = RuleServiceProviderManager.getInstance().getDefaultProvider();
    	return (RSP.getTypeManager().getTypeDescriptor(entityExpandedName) != null);
    }
    
    private static void clearNamespaceReferences() {
    	namespaceCnt = 1;
    	namespaceKeyToTypeMap.clear();
    }
    
    private static String wrapInRootElement(String xiPayload, SmParticleTerm payTerm) {
    	String baseName = payTerm.getName();
    	String nsPrefix = getPrefixByNamespace(payTerm.getNamespace());
    	if (nsPrefix == null) {
    		nsPrefix = "ns" + namespaceCnt++;
    		namespaceKeyToTypeMap.put(nsPrefix, payTerm.getNamespace());
    	}
    	
    	StringBuffer returnPayload =  new StringBuffer();
    	returnPayload.append("<"); 
    	returnPayload.append(nsPrefix); 
    	returnPayload.append(":");
    	returnPayload.append(baseName);
    	returnPayload.append(" xmlns:");
    	returnPayload.append(nsPrefix);
    	returnPayload.append("=\"");
    	returnPayload.append(payTerm.getNamespace());
    	returnPayload.append("\""); 
    	returnPayload.append(">"); 
    	returnPayload.append(xiPayload);
    	returnPayload.append("</");
    	returnPayload.append(nsPrefix);
    	returnPayload.append(":");
    	returnPayload.append(baseName);
    	returnPayload.append(">");
    	
    	return returnPayload.toString();
    }
    
    private static String getPrefixByNamespace(String targetNamespace) {
    	String nsPrefix = null;
    	if (targetNamespace != null && namespaceKeyToTypeMap.containsValue(targetNamespace)) {
    		for (Entry<String,String> namespaceEntry: namespaceKeyToTypeMap.entrySet()) {
    			if (targetNamespace.equalsIgnoreCase(namespaceEntry.getValue())) {
    				nsPrefix = namespaceEntry.getKey();
    				break;
    			}
    		}
    	}
    	return nsPrefix;
    }
    
    private static void getXMLPart(JsonNode jsonNode, StringBuffer xmlBuffer, String defaultRootNameSpace, boolean isEntityPayload, boolean isRoot) {
    	Iterator<Entry<String, JsonNode>> fieldIterator = jsonNode.fields();
    	while (fieldIterator.hasNext()) {
    		Entry<String, JsonNode> field = fieldIterator.next();
    		if (!field.getKey().equals("attributes")) {
    			JsonNode nextNode = ((JsonNode)field.getValue());
    			String nsPrefix = null;
    			boolean addNSPrefix = true;
    			
    			if (!nextNode.isArray()) {
    				String attributes = null;
    				if (nextNode.isObject()) {
    					attributes = attributesNodeXML(nextNode);
    					if ((attributes == null || attributes.isEmpty()) && (defaultRootNameSpace != null && !defaultRootNameSpace.isEmpty())) {
    						nsPrefix = "ns"+namespaceCnt++;
    						attributes = " xmlns:"+ nsPrefix + "=\"" + defaultRootNameSpace + "\"";
    						namespaceKeyToTypeMap.put(nsPrefix, defaultRootNameSpace);
    						defaultRootNameSpace = null;
    					} else {
    						if (attributes.indexOf(":") != -1) nsPrefix = attributes.substring(attributes.indexOf(":")+1, attributes.indexOf("=", attributes.indexOf(":")+1)).trim();
    					}
    				}
    				
    				if (nsPrefix == null) nsPrefix = getPrefixByNamespace(defaultRootNameSpace);
    				
    				if ((nsPrefix != null && isEntityPayload && !isRoot) || (nsPrefix == null)) { 
    					addNSPrefix = false;
    				}
    				
    				xmlBuffer.append("<");
    				if (addNSPrefix) xmlBuffer.append(nsPrefix + ":");
    				xmlBuffer.append(field.getKey());
    				if (attributes != null) xmlBuffer.append(attributes);
    				xmlBuffer.append(">");
    			}
    			
    			if (nextNode.isNull()) {
    	    		xmlBuffer.append("");
    			} else if (nextNode.isObject()) {
    				getXMLPart(nextNode, xmlBuffer, defaultRootNameSpace, isEntityPayload, false);
    			} else if (nextNode.isArray()) {
    				xmlBuffer.append(parseArray(nextNode, field.getKey(), defaultRootNameSpace, isEntityPayload, false));
    			} else if (nextNode.isValueNode()) {
    				xmlBuffer.append(nextNode.asText());
    			}
    			if (!nextNode.isArray())  {
    				xmlBuffer.append("</");
    				if (addNSPrefix) xmlBuffer.append(nsPrefix + ":");
    				xmlBuffer.append(field.getKey() + ">");
    			}
    		}
    	}
     }
    
    private static String parseAttributeNode(JsonNode attributeNode) {
    	if (attributeNode != null) {
    		StringBuffer attributeBuffer = new StringBuffer(" ");
    		
    		Iterator<Entry<String, JsonNode>> attributeIterator = attributeNode.fields();
    		while(attributeIterator.hasNext()) {
    			Entry<String, JsonNode> attribute = attributeIterator.next();
    			
    			String attributeKey = null;
    			String type = null;
    			if (attribute.getKey().equals("type")) {
    				attributeKey = "ns"+namespaceCnt++;
    				type = namespaceKeyToTypeMap.get(attributeKey);
    			} else {
    				attributeKey = attribute.getKey();
    			}
    			if (type == null) {
    				type = ((JsonNode)attribute.getValue()).asText();
    				if (attribute.getKey().equals("type")) namespaceKeyToTypeMap.put(attributeKey, type);
    			}
    			
    			String prefix = (attribute.getKey().equals("type")) ? "xmlns:" : null;
    			if (prefix != null) attributeBuffer.append(prefix);
    			attributeBuffer.append(attributeKey + "=" + "\"");
    			attributeBuffer.append(type + "\" ");
    		}
    		if (attributeBuffer.length() > 0) {
    			return attributeBuffer.substring(0, attributeBuffer.length()-1);
    		}

    		return null;
    	} 
    	return null;
    }
    
    private static String attributesNodeXML(JsonNode jsonNode) {
    	JsonNode attributesNode = jsonNode.get("attributes");
    	if (attributesNode != null) {
    		String attributesXML = parseAttributeNode(attributesNode);
			if (attributesXML != null && !attributesXML.isEmpty()) return attributesXML;
    	}
    	return "";
    }
    
    private static String parseArray(JsonNode jsonNode, String key, String defaultRootNameSpace, boolean isEntityPayload, boolean isRootNode) {
    	StringBuffer arrayBuffer = new StringBuffer();
    	String nsPrefix = getPrefixByNamespace(defaultRootNameSpace);
    	if (nsPrefix == null) {
    		nsPrefix = "ns"+namespaceCnt++;
    		namespaceKeyToTypeMap.put(nsPrefix, defaultRootNameSpace);
    	}
    	boolean addNSPrefix = true;
    	if ((nsPrefix != null && isEntityPayload && !isRootNode) || (nsPrefix == null)) { 
			addNSPrefix = false;
		}
    	
    	if (jsonNode.size() > 0) {
	    	Iterator<JsonNode> arrayIterator = jsonNode.elements();
	    	while (arrayIterator.hasNext()) {
	    		JsonNode arrayNode = (JsonNode) arrayIterator.next();
	    		
	    		arrayBuffer.append("<");
	    		if (addNSPrefix) arrayBuffer.append(nsPrefix + ":");
	    		arrayBuffer.append(key + " isArray=\"true\"");
	    		arrayBuffer.append(attributesNodeXML(arrayNode));
	    		arrayBuffer.append(">");
	    		
	    		if (arrayNode.isObject()) {
	    			getXMLPart(arrayNode, arrayBuffer, defaultRootNameSpace, isEntityPayload, false);
	    		} else {
	    			arrayBuffer.append(arrayNode.asText());
	    		}
	    		arrayBuffer.append("</");
	    		if (addNSPrefix) arrayBuffer.append(nsPrefix + ":");
	    		arrayBuffer.append(key + ">");
	    	}
    	} else {	// Array w/ no members should at least include key/element - similar to XML
    		arrayBuffer.append("<");
    		if (addNSPrefix) arrayBuffer.append(nsPrefix + ":");
    		arrayBuffer.append(key + " isArray=\"true\"");
    		arrayBuffer.append("/>");
    	}
    	
    	return arrayBuffer.toString();
    }
    
    private static String getNameSpace(XiNode rootNode, String defaultNameSpace) throws Exception {
    	String namespace = null;
    	
    	String baseName = null;
    	if (rootNode.getName() != null) baseName = rootNode.getName().toString();
    	
		String prefix = rootNode.getPrefix();
		if (prefix == null || prefix.isEmpty()) {
			 Iterator r = rootNode.getNamespaces();
                while (r.hasNext()) {
                    namespace = ((XmlNode)r.next()).getStringValue();
                    if (baseName != null && namespace.indexOf(baseName) != -1) {
                    	break;
                    }
                }
		} else {
			namespace = rootNode.getNamespaceURIForPrefix(prefix);
		}
		
		return (namespace != null && !namespace.isEmpty()) ? namespace : defaultNameSpace;  
    }
    
    private static String stripNSPrefix(String ns) {
    	String nsPrefix = TypeManager.DEFAULT_BE_NAMESPACE_URI;
    	if (ns.indexOf(nsPrefix) != -1) return ns.substring(nsPrefix.length());
    	return ns;
    }
}
