package com.tibco.be.functions.object;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Stack;

import org.xml.sax.SAXException;

import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.Property.PropertyConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyArrayConceptReference;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmType;

public class SAX5ConceptInstance extends AbstractContentHandler {
	
    Concept ci;
    Property pi;
    String uri;
    Stack elements;
    RuleSession session;
    
    static boolean removeTimestamp = false;

    boolean propertySet = false;
    HashSet propertyMap = new HashSet();
    
    Class entityClass;
    String extId;
    boolean isTypeSet;
    boolean isRootElement;
    int elementCnt;
    
    static {
    	String sysProperty =
    		System.getProperty("TIBCO.BE.datetime.timestamp.remove");
    	removeTimestamp =
    		((sysProperty == null || sysProperty.length() == 0) ? false :
    			((sysProperty.intern() == "true") ? true : false));
    }

    public SAX5ConceptInstance(RuleSession session) {
        super();
        elements = new Stack();
        this.session = session;
        this.isRootElement = true;
    }

    public void attribute(ExpandedName expandedName, XmlTypedValue value, SmAttribute smAttribute) throws SAXException {
        try {
            if (expandedName.getLocalName().equals("extId")) {
                if (ci == null)  {
                	if (uri == null) {
                		ci = (Concept) session.getObjectManager().getElement(value.toString());
                	} else {
                		ci = (Concept) session.getObjectManager().getElementByUri(value.toString(), uri);
                	}
                	
                    if (ci == null) {
                        throw new RuntimeException("Concept with extId=" + value.toString() + " uri=" + uri + " not found in working memory");
                    }
                    elements.push(ci);
                } else {
                    String extId = value.toString();
                    Object o = elements.peek();
                    
                    if (o instanceof PropertyAtomContainedConcept) {
                        // No need to do anything here
                        PropertyAtomContainedConcept prop = (PropertyAtomContainedConcept) o;
                        ci = prop.getContainedConcept();
                        if (ci == null) {
                        	entityClass = ((PropertyAtomContainedConcept) pi).getType();
                        	this.extId = extId;
                        } else {
                        	isTypeSet = true;
                        }
                    } else if (o instanceof PropertyArrayContainedConcept) {
                        PropertyArrayContainedConcept childProperties = (PropertyArrayContainedConcept) o;
                        if ((extId != null)) {
                            for (int i = 0; i < childProperties.length(); i++) {
                                PropertyAtomContainedConcept child = (PropertyAtomContainedConcept) childProperties.get(i);
                                String thisExtId = child.getContainedConcept().getExtId();
                                if (thisExtId != null) {
                                    if (thisExtId.equals(extId)) {
                                        ci = child.getContainedConcept();
                                        isTypeSet = true;
                                        return;
                                    }
                                }
                            }
                        }
                        
                        // if not match found, set necessary details, entity will be created in startElement
                        entityClass = ((PropertyArrayContainedConcept) pi).getType();
                    	this.extId = extId;
                    	if (isTypeSet) isTypeSet = false;
                    }
                }
                return;
            } else if (expandedName.getLocalName().equals("type")) {
            	if (!isRootElement) { // && !isTypeSet) {
            		createAndInitializeInstance(getClass(value.toString()), extId);
            		entityClass = null;
            		extId = null;

            		isTypeSet = true;
            	}
            	
            	return;
            }

            if (!elements.isEmpty()) {
	            Object o = elements.peek();
	            if (o instanceof Concept) {
	                Concept cur = (Concept) o;
	                if (expandedName.getLocalName().equals("parent")) {
	                    if (cur instanceof ContainedConcept) {
	                        long id = value.getAtom(0).castAsLong();
                            Concept parent = (Concept) session.getObjectManager().getElement(id);
	                        ((ContainedConcept) ci).setParent(parent);
	                    }
	                }
	            } else if (o instanceof Property) {
	                if (expandedName.getLocalName().equals("ref")) {
	                    Property.PropertyConceptReference refFrom = (Property.PropertyConceptReference) elements.peek();
	                    long id = value.getAtom(0).castAsLong();
	                    if (refFrom instanceof PropertyAtomConceptReference) {
	                        addReference((PropertyAtomConceptReference) refFrom, id);
	                    } else if (refFrom instanceof PropertyArrayConceptReference) {
	                        addReference((PropertyArrayConceptReference) refFrom, id);
	                    }
	                } else if (expandedName.getLocalName().equals("refExtId")) {
	                    Property.PropertyConceptReference refFrom = (Property.PropertyConceptReference) elements.peek();
	                    String extId = value.getAtom(0).castAsString();
	                    if (refFrom instanceof PropertyAtomConceptReference) {
	                        addReference((PropertyAtomConceptReference) refFrom, extId);
	                    } else if (refFrom instanceof PropertyArrayConceptReference) {
	                        addReference((PropertyArrayConceptReference) refFrom, extId);
	                    }
	                } else if (expandedName.getLocalName().equals("nil")) {
	                    text((XmlTypedValue) null, true);
	                    propertyMap.add(o);
	                }
	            }
            }
        } catch (Exception e) {
            throw new SAXException(e);
        }
    }
    
    void addReference(PropertyAtomConceptReference refFrom, long id) throws Exception {
        Concept refTo = (Concept)session.getObjectManager().getElement(id);
        if (refTo != null) {
            refFrom.setConcept(refTo);
        }
    }

    void addReference(PropertyArrayConceptReference refFrom, long id) throws Exception {
        Concept refTo = (Concept)session.getObjectManager().getElement(id);
        if (refTo != null) {
            refFrom.add(refTo);
        }
    }
    
    void addReference(PropertyAtomConceptReference refFrom, String extId) throws Exception {
        Concept refTo = (Concept)session.getObjectManager().getElement(extId);
        if (refTo != null) {
            refFrom.setConcept(refTo);
        }
    }

    void addReference(PropertyArrayConceptReference refFrom, String extId) throws Exception {
        Concept refTo = (Concept)session.getObjectManager().getElement(extId);
        if (refTo != null) {
            refFrom.add(refTo);
        }
    }

    public void startElement(ExpandedName expandedName, SmElement smElement, SmType smType) throws SAXException {
        try {
        	if (isRootElement && elementCnt > 0) isRootElement = false;
        	
        	if (!isRootElement && !isTypeSet) {        		
        		createAndInitializeInstance(entityClass, extId);
        		entityClass = null;
        		extId = null;
        	}
        	
            propertySet = false; 
            String name = expandedName.getLocalName();
            Property prop = null;
            if (ci != null) {
                pi = prop = ci.getProperty(name);
            } else {
            	TypeManager.TypeDescriptor entityType = session.getRuleServiceProvider().getTypeManager().getTypeDescriptor(expandedName);
            	if(entityType != null) uri = entityType.getURI();
            }
            if (prop == null) {
                elements.push(name);
            } else {
                elements.push(prop);
            }
            
            elementCnt++;
        } catch (Exception e) {
            throw new SAXException(e);
        }
    }
    
    public void endElement(ExpandedName expandedName, SmElement smElement, SmType smType) throws SAXException {
        Object o = elements.pop();
        if (o instanceof Property) {
            ci = ((Property)o).getParent();
        }
        if (o instanceof PropertyConcept) {
            return;
        }
        if (o instanceof PropertyAtom) {
            if (propertySet == false) {
            	if ((ci != null) && ((ConceptImpl)ci).includeNullProps()) {
            		((PropertyAtom) o).setValue((Object) null);
            	}
            }
        }
        if (isTypeSet) isTypeSet = false;
    }

    public void text(String s, boolean b) throws SAXException {
        propertySet = true;
        Object o = elements.peek();
        if (o instanceof PropertyConcept) {
        	return;
        }
        if (o instanceof PropertyAtom) {
            try {
                if (propertyMap.contains(o)) {
                    return;
                }
                /***************************************************
                 * Added to remove the timestamp part of a date
                 ***************************************************/
                if (o instanceof PropertyAtomDateTime) {
                    PropertyAtomDateTime padt = (PropertyAtomDateTime)o;
                    s = getDataTimeString(s);
                    padt.setValue(s);
                } else {
                    ((PropertyAtom)o).setValue(s);
                }
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if (o instanceof Concept) {
            // Ignore the text
        }
        // Need to allow this to not throw exceptions when ci is never set
        else if (ci != null) {
            throw new SAXException("Unknown Type - " + o.getClass() + " ***" + o);
        }
    }

    public void text(XmlTypedValue val, boolean b) throws SAXException {
        propertySet = true;
        Object o = elements.peek();
        if (o instanceof PropertyConcept) {
        	return;
        }
        if (o instanceof PropertyAtom) {
            try {
                if (propertyMap.contains(o)) {
                    return;
                }
                /***************************************************
                 * Added to remove the timestamp part of a date
                 ***************************************************/
                if (o instanceof PropertyAtomDateTime) {
                    PropertyAtomDateTime padt = (PropertyAtomDateTime)o;
                    if (val != null) {
                    	String s = val.getAtom(0).castAsString();
                    	s = getDataTimeString(s);
                    	padt.setValue(val);
                    } else {
                    	padt.setValue(val);
                    }
                } else {
                    ((PropertyAtom)o).setValue(val);
                }
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if (o instanceof PropertyArray) {
        	// Not handling property array for now
            /* if (propertyMap.contains(o)) {
                return;
            }
            try {
	        	PropertyArray paa = (PropertyArray)o;
                if (paa instanceof PropertyArrayDateTime) {
                    if(val != null){
                    	String s = val.getAtom(0).castAsString();
                    	s = getDataTimeString(s);
                    	paa.add(s);
                    } else {
                    	paa.add(val);
                    }
                } else {
                	paa.add(val);
                }
            } catch(Exception e) {
            }*/
        } else if (o instanceof Concept) {
            // Ignore the text
        } 
        // Need to allow this to not throw exceptions when ci is never set
        else if (ci != null) {
            throw new SAXException("Unknown Type - " + o.getClass() + " ***" + o);
        }
    }
    
    private String getDataTimeString(String s) {
    	if (removeTimestamp) {
    		int index = s.indexOf('T');
    		if (index != -1) {
    			s = s.substring(0, index);
    		}
    	}
    	return s;
    }

    private Object newInstance(Class clz, String extId) throws Exception {
        long id = session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz);
        Constructor cons = clz.getConstructor(new Class[] {long.class, String.class});
        Object o = cons.newInstance(new Object[] { new Long(id), extId });
        return o;
    }
    
    private void createAndInitializeInstance(Class clz, String extId) throws Exception {
    	if (clz != null) {
    		ci = (Concept) newInstance(clz, extId);
    		
    		Object o = elements.peek();
            if (o instanceof PropertyAtomContainedConcept) {
                // No need to do anything here
                PropertyAtomContainedConcept prop = (PropertyAtomContainedConcept) o;
                prop.setValue(ci);
                session.assertObject(ci, false);
            } else if (o instanceof PropertyArrayContainedConcept) {
                PropertyArrayContainedConcept childProperties = (PropertyArrayContainedConcept) o;
                childProperties.add(ci);
                session.assertObject(ci, false);
            }
    	}
    }
    
    private Class getClass(String type) throws Exception {
    	final String TIBCO_NAMESPACE_PREFIX = "www.tibco.com/be/ontology";
    	final String TIBCO_CODE_GEN_PACKAGE = "be.gen";
    	
    	if (type.startsWith(TIBCO_NAMESPACE_PREFIX)) {
    		type = type.substring(TIBCO_NAMESPACE_PREFIX.length());
    	}
    	
    	type = TIBCO_CODE_GEN_PACKAGE + type.replaceAll("/", ".");
    	
    	return Class.forName(type, true, session.getRuleServiceProvider().getClassLoader());
    }
}
