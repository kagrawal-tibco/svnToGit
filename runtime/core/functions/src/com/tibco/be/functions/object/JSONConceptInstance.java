package com.tibco.be.functions.object;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Stack;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.Property.PropertyConcept;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyArrayConceptReference;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;
import com.tibco.cep.runtime.model.element.PropertyAtomInt;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;

/**
 * Parsing JSON as a document structure and converting it into a Concept
 * 
 * @author vpatil
 */
public class JSONConceptInstance {
	private Concept concept;
	private Property property;
	private String uri;
	private Stack<Object> elements;
	private RuleSession session;
	private static boolean removeTimeStamp;
	private boolean parseEmbeddedObject;
	
	private boolean isAttribute;
	private boolean isExtIdEntry;
	private boolean isTypeEntry;
	private boolean isRefId;
	private boolean isRefExtId;
	private boolean isUpdate;
	
	private Class<?> entityClass;
	private String extId;

	
	/**
	 * Constructor
	 * 
	 * @param session
	 * @param uri
	 */
	public JSONConceptInstance(RuleSession session, String uri) {
		this.session = session;
		this.uri = uri;
		RuleServiceProvider serviceProvider = RuleServiceProviderManager.getInstance().getDefaultProvider();
		removeTimeStamp = serviceProvider == null ? false : serviceProvider.getProperties().getProperty("TIBCO.BE.datetime.timestamp.remove", "false").
				equals("true");
		
		elements = new Stack<Object>();
		parseEmbeddedObject = true;
		isUpdate = true;
		
	}
	
	/**
	 * Constructor
	 * 
	 * @param cept
	 * @param session
	 */
	public JSONConceptInstance(Concept cept, RuleSession session) {
		this(session, null);
		this.concept = cept;
		elements.push(concept);
		isUpdate = false;
	}
	
	/**
	 * Parse JSON data, token by token
	 * 
	 * @param reader
	 * @throws Exception 
	 */
	public void parseJSON(JsonParser parser) throws Exception {
        while (!parser.isClosed()) {
            JsonToken token = parser.nextToken();

            if (token == null)
                break;
            
            switch(token) {
            
            case START_OBJECT:
            	if (!parseEmbeddedObject) continue;
            	
            	if (!isAttribute) {
            		handleEntityValue();
            	}
            	
               	parseJSON(parser);
                break;
                
            case END_OBJECT:
            	if (!parseEmbeddedObject) {
            		parseEmbeddedObject = !parseEmbeddedObject;
            	} else {
            		if (!elements.isEmpty() && !isAttribute) {
            			if (!(elements.peek() instanceof PropertyArray)) {
                    		endJSONEntry(true);
                    	} else {
                    		endJSONEntry(false);
                    	}
            		}
            		if (isAttribute) {
            			isAttribute = !isAttribute;
            			if (isUpdate) lookupConceptInstance();
            			// case of new or additions in update
            			if (!isUpdate || concept == null) createAndInitializeInstance();
            		}
            	}
                break;
                
            case START_ARRAY:
                break;
                
            case END_ARRAY:
            	endJSONEntry(true);
  
                break;
                
            case FIELD_NAME:
            	if (!parseEmbeddedObject) continue;
            	
            	boolean attributeCheck = parser.getCurrentName().equals("attributes");
            	if (isAttribute) attributeCheck = (attributeCheck || isAttribute);
            	
            	if (!isTypeEntry && entityClass != null && !attributeCheck) {
            		if (isUpdate) lookupConceptInstance();
            		// case of new or additions in update
            		if (!isUpdate || concept == null) createAndInitializeInstance();
            	}
            	parseEmbeddedObject = parseJSONEntry(parser.getCurrentName());
            	break;
                
            case VALUE_STRING:
            case VALUE_NUMBER_INT:
            case VALUE_NUMBER_FLOAT:
            case VALUE_FALSE:
            case VALUE_TRUE:
            	if (!parseEmbeddedObject) continue;
            	handlePrimitiveValue(parser, token);
                break;
                
            case VALUE_NULL:
                break;
            default:
                throw new RuntimeException("Invalid Token");
            }
        }
	}
	
	/**
	 * Check and add entities of specific types
	 * 
	 * @throws Exception
	 */
	private void handleEntityValue() throws Exception {		
		if (concept != null) {
			Object prop = elements.peek();
			if (prop instanceof Property) {
				if (prop instanceof PropertyArrayContainedConcept) {
					entityClass = ((PropertyArrayContainedConcept)prop).getType();
    				
				} else if (prop instanceof PropertyArrayConceptReference) {
    				if (((ConceptImpl)((PropertyArrayConceptReference) prop).getParent()).expandPropertyRefs()) {
    					entityClass = ((PropertyArrayConceptReference) prop).getType();
    				}
    				
				} else if (prop instanceof PropertyAtomContainedConcept) {
					entityClass = ((PropertyAtomContainedConcept)prop).getType();
    				
				} else if (prop instanceof PropertyAtomConceptReference) {
					if (((ConceptImpl)((PropertyAtomConceptReference)prop).getParent()).expandPropertyRefs()) {
						entityClass = ((PropertyAtomConceptReference)prop).getType();
					}
				}
			}
		}
	}
	
	/**
	 * Handle primitive Values
	 *
	 * @param reader
	 * @param token
	 * @throws IOException
	 */
	private void handlePrimitiveValue(JsonParser parser, JsonToken token) throws Exception {
		if (token.equals(JsonToken.VALUE_STRING)) {
			parseJSONValue(parser.getValueAsString());
		} else if (token == JsonToken.VALUE_NUMBER_INT) {
			Object numericValue = null;
			if (elements.size() > 0) {
				Object o = elements.peek();
				if (o instanceof PropertyAtom) {
					numericValue = (o instanceof PropertyAtomInt) ? parser.getValueAsInt() : parser.getValueAsLong();
				} else { // case for Id of reference concepts, default to 
					numericValue = parser.getValueAsLong();
				}
			} else {
				numericValue = parser.getValueAsInt();
			}
			parseJSONValue(numericValue);
		} else if(token == JsonToken.VALUE_NUMBER_FLOAT) {
			parseJSONValue(parser.getValueAsDouble());
		} else if (token == JsonToken.VALUE_FALSE || token == JsonToken.VALUE_TRUE) {
			parseJSONValue(parser.getValueAsBoolean());
		}
	}
	
	/**
	 * Parse JSON entry
	 * 
	 * @param name
	 * 
	 * @return
	 */
	private boolean parseJSONEntry(String name) {
		try {
			isExtIdEntry = false;
			isTypeEntry = false;
			
			boolean propFound = false;
    		if (concept != null && !isAttribute) {
    			property = concept.getProperty(name);
    			
    			if (property != null) {
    				elements.push(property);
    				propFound = true;
    			}
    		}
    		if (!propFound) {
				if (name.equals("attributes")) {
					isAttribute = true;
				} else if (name.equals("extId") && isAttribute) {
					isExtIdEntry = true;
				} else if (name.equals("type") && isAttribute) {
					isTypeEntry = true;
				} else if (name.equals("ref") && isAttribute) {
					isRefId = true;
				} else if (name.equals("refExtId") && isAttribute) {
					isRefExtId = true;
				}
    		}
    	} catch (Exception exception) {
    		throw new RuntimeException(exception);
    	}
		
		return true;
	}
	
	/**
	 * Parse the JSON value
	 * 
	 * @param value
	 */
	private void parseJSONValue(Object value) throws Exception {
		if (!elements.empty()) {
			Object o = elements.peek();
			if (o instanceof PropertyConcept) {
				if (isExtIdEntry) extId = (String)value;
				else if (isTypeEntry) {
					entityClass = getClass((String) value);
					uri = ((String) value).substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length());
				}
				else if (isRefId) {
					Property.PropertyConceptReference refFrom = (Property.PropertyConceptReference) elements.peek();
			        if (refFrom instanceof PropertyAtomConceptReference) {
			            addReference((PropertyAtomConceptReference) refFrom, (Long)value);
			        } else if (refFrom instanceof PropertyArrayConceptReference) {
			            addReference((PropertyArrayConceptReference) refFrom, (Long)value);
			        }
			        isRefId = false;
				} else if (isRefExtId) {
					Property.PropertyConceptReference refFrom = (Property.PropertyConceptReference) elements.peek();
	                if (refFrom instanceof PropertyAtomConceptReference) {
	                    addReference((PropertyAtomConceptReference) refFrom, (String)value);
	                } else if (refFrom instanceof PropertyArrayConceptReference) {
	                    addReference((PropertyArrayConceptReference) refFrom, (String)value);
	                }
	                isRefExtId = false;
				}
				return;
			}
			if (o instanceof PropertyAtom) {
				try {
					if (o instanceof PropertyAtomDateTime) {
						PropertyAtomDateTime padt = (PropertyAtomDateTime)o;
						//Chop off the timestamp part if system property set to true
						if (removeTimeStamp) {
							if (value != null) {
								String s = (String) value;
								int index = s.indexOf('T');
								s = s.substring(0, index);
								padt.setValue(s);
							} else {
								padt.setValue((String) null);
							}
						} else {
							padt.setValue((String)value);
						}

					} else {
						((PropertyAtom)o).setValue(value);
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} else if (o instanceof PropertyArray) {
				PropertyAtom ti = ((PropertyArray) property).add();
				ti.setValue(value);

			} else if (o instanceof Concept) {
				if (isExtIdEntry) ((Concept) o).setExtId((String)value);
				// Else Ignore the text
			} else {
				throw new RuntimeException("Unknown Type - " + o.getClass() + " ***" + o);
			}

			// pop off the element if not Array
			if (!(o instanceof PropertyArray) && !isAttribute) {
				endJSONEntry(true);
			}
		} else {
			// case for updateFromJSON 
			if (concept == null && isExtIdEntry) {
				extId = value.toString();
				
				lookupConceptInstance();
				
				if (concept == null) {
		            throw new RuntimeException("Concept with extId=" + extId + " uri=" + uri + " not found in working memory");
		        }
				
				if (concept != null) elements.push(concept);
			}
		}
    }
	
	private void lookupConceptInstance() {
		if (uri == null) {
			concept = (Concept) session.getObjectManager().getElement(extId);
    	} else {
    		concept = (Concept) session.getObjectManager().getElementByUri(extId, uri);
    	}
	}
	
	/**
	 * End JSON entry to pop the parsed entry 
	 */
	private void endJSONEntry(boolean pop) {
		if (!elements.empty() && elements.size() > 1) {
			Object o = (pop) ? elements.pop() : elements.peek();
			if (o instanceof Property) {
				property = ((Property)o);
				concept = property.getParent();
			}

			else if (o instanceof Concept) {
				concept = ((Concept)o).getParent() != null ? ((Concept)o).getParent() : ((Concept)o);
			}
		} else {
			concept = (Concept) elements.peek();
		}
    }
	
	/**
	 * Create a new Instance
	 * 
	 * @param clz
	 * @return
	 * @throws Exception
	 */
	private Object newInstance (Class<?> clz) throws Exception {
		long id = session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz);
		Constructor<?> cons = clz.getConstructor(new Class[] {long.class});
		Object o = cons.newInstance(new Object[] {new Long(id)});
		return o;
	}
	
	/**
	 * Fetch the parsed Concept
	 * 
	 * @return
	 */
	public Concept getConcept() {
		return (Concept) elements.pop();
	}
	
	/**
	 * Create class based on the entity type
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private Class<?> getClass(String type) throws Exception {
    	final String TIBCO_CODE_GEN_PACKAGE = "be.gen";
    	
    	if (type.startsWith(TypeManager.DEFAULT_BE_NAMESPACE_URI)) {
    		type = type.substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length());
    	}
    	
    	type = TIBCO_CODE_GEN_PACKAGE + type.replaceAll("/", ".");
    	
    	return Class.forName(type, true, session.getRuleServiceProvider().getClassLoader());
    }
	
	/**
	 * Create and Initialize and entity instance
	 * 
	 * @throws Exception
	 */
	private void createAndInitializeInstance() throws Exception {
		if (entityClass != null) {
			concept = (Concept) newInstance(entityClass);
			entityClass = null;
			
			if (extId != null && !extId.isEmpty()) { 
				concept.setExtId(extId);
				extId = null;
			}
			
			Object prop = elements.peek();
			if (prop instanceof Property) {
				if (prop instanceof PropertyArrayContainedConcept) {
					((PropertyArrayContainedConcept)prop).add(concept);
					
				} else if (prop instanceof PropertyArrayConceptReference) {
					if (((ConceptImpl)((PropertyArrayConceptReference) prop).getParent()).expandPropertyRefs()) {
						((PropertyArrayConceptReference) prop).add(concept);
					}
					
				} else if (prop instanceof PropertyAtomContainedConcept) {
					((PropertyAtomContainedConcept)prop).setContainedConcept((ContainedConcept)concept);
					
				} else if (prop instanceof PropertyAtomConceptReference) {
					if (((ConceptImpl)((PropertyAtomConceptReference)prop).getParent()).expandPropertyRefs()) {
						((PropertyAtomConceptReference)prop).setConcept(concept);
					}
				}
			}
		}
	}

	/**
	 * Set a reference, lookup by Id
	 * 
	 * @param refFrom
	 * @param id
	 * @throws Exception
	 */
	private void addReference(PropertyAtomConceptReference refFrom, long id) throws Exception {
        Concept refTo= (Concept)session.getObjectManager().getElement(id);
        if (refTo != null) {
            refFrom.setConcept(refTo);
        }
    }

	/**
	 * Add a reference to an array, lookup by Id
	 * 
	 * @param refFrom
	 * @param id
	 * @throws Exception
	 */
    private void addReference(PropertyArrayConceptReference refFrom, long id) throws Exception {
        Concept refTo = (Concept)session.getObjectManager().getElement(id);
        if (refTo != null) {
            refFrom.add(refTo);
        }
    }

    /**
     * Set a reference, lookup by ExtId
     * 
     * @param refFrom
     * @param extId
     * @throws Exception
     */
    private void addReference(PropertyAtomConceptReference refFrom, String extId) throws Exception {
        Concept refTo = (Concept)session.getObjectManager().getElement(extId);
        if (refTo != null) {
            refFrom.setConcept(refTo);
        }
    }

    /**
     * Add reference to an array, lookup by ExtId
     * 
     * @param refFrom
     * @param extId
     * @throws Exception
     */
    private void addReference(PropertyArrayConceptReference refFrom, String extId) throws Exception {
        Concept refTo = (Concept)session.getObjectManager().getElement(extId);
        if (refTo != null) {
            refFrom.add(refTo);
        }
    }
}
