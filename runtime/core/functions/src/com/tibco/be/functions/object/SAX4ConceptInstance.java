package com.tibco.be.functions.object;

import java.lang.reflect.Constructor;
import java.util.Stack;

import org.xml.sax.SAXException;

import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.runtime.model.PropertyNullValues;
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
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmType;


public class SAX4ConceptInstance extends AbstractContentHandler {
    Concept ci;
    Property pi;
    Stack elements;
    RuleSession session;
    static boolean removeTimeStamp;

    // temp variables for storing values before concept is created
    XmlTypedValue extId;
    XmlTypedValue parentExtId;
    XmlTypedValue parentId;
    Class entityClass;
    String refEntityPath;
    boolean isRootElement;
    int elementCnt;
	private boolean inlineAssert;

    public SAX4ConceptInstance(RuleSession session) {
        super();
        elements = new Stack();
        isRootElement = true;
        this.session = session;
        removeTimeStamp = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties().getProperty("TIBCO.BE.datetime.timestamp.remove", "false").
        		equals("true");
    }

    public SAX4ConceptInstance(Concept cept, RuleSession session) {
    	this(session);
        ci = cept;
        elements.push(ci);

    }

    public void attribute(ExpandedName expandedName, XmlTypedValue value, SmAttribute smAttribute) throws SAXException {
        try {
        	if (expandedName.getLocalName().equals("inlineAssert")) {
        		inlineAssert = Boolean.parseBoolean(value.toString());
        	} else if (expandedName.getLocalName().equals("entityPath")) {
                if (!isRootElement) {
                	refEntityPath = value.toString();
                }
         	} else if (expandedName.getLocalName().equals("type")) {
         		if (!isRootElement) {
         			createAndInitializeInstance(getClass(value.toString()));
         			entityClass = null;
         		}
            } else if (expandedName.getLocalName().equals(("extId"))) {
            	if (isRootElement) {
            		ci.setExtId(value.toString());
            	} else {
            		extId = value;
            	}
            } else if (expandedName.getLocalName().equals("ref")) {
                Property.PropertyConceptReference refFrom=(Property.PropertyConceptReference) elements.peek();
                long id= value.getAtom(0).castAsLong();
                if (refFrom instanceof PropertyAtomConceptReference) {
                    addReference((PropertyAtomConceptReference) refFrom, id);
                } else if (refFrom instanceof PropertyArrayConceptReference) {
                    addReference((PropertyArrayConceptReference) refFrom, id);
                }

            } else if (expandedName.getLocalName().equals("refExtId")) {
                Property.PropertyConceptReference refFrom=(Property.PropertyConceptReference) elements.peek();
                String extId= value.getAtom(0).castAsString();
                if (refFrom instanceof PropertyAtomConceptReference) {
                    addReference((PropertyAtomConceptReference) refFrom, extId);
                } else if (refFrom instanceof PropertyArrayConceptReference) {
                    addReference((PropertyArrayConceptReference) refFrom, extId);
                }

            } else if (expandedName.getLocalName().equals("parentId")) {
                parentId = value;
            } else if (expandedName.getLocalName().equals("parentExtId")) {
                parentExtId = value;
            } else if (expandedName.getLocalName().equals("nil")) {
            	//TODO if property is instanceof PropertyArray or PropertyConcept
            	Property property = (Property)elements.peek();
            	if (property instanceof PropertyAtom) {
            		PropertyAtom prop = (PropertyAtom) property;
	            	String defValue = PropertyNullValues.getNullValue(prop);
            		prop.setValue(defValue);
            	}
            }
        } catch (Exception e) {
            throw new SAXException(e);
        }
    }
    void addReference(PropertyAtomConceptReference refFrom, long id) throws Exception {
        Concept refTo= (Concept)session.getObjectManager().getElement(id);
        if (refTo != null) {
            refFrom.setConcept(refTo);
        }
    }

    void addReference(PropertyArrayConceptReference refFrom, long id) throws Exception {
        Concept refTo= (Concept)session.getObjectManager().getElement(id);
        if (refTo != null) {
            refFrom.add(refTo);
        }
    }

    void addReference(PropertyAtomConceptReference refFrom, String extId) throws Exception {
        Concept refTo= (Concept)session.getObjectManager().getElement(extId);
        if (refTo != null) {
            refFrom.setConcept(refTo);
        }
    }

    void addReference(PropertyArrayConceptReference refFrom, String extId) throws Exception {
        Concept refTo= (Concept)session.getObjectManager().getElement(extId);
        if (refTo != null) {
            refFrom.add(refTo);
        }
    }

    public void startElement(ExpandedName expandedName, SmElement smElement, SmType smType) throws SAXException {
        //TODO Validations - Can only be called from objectHelper
        try {
            if (isRootElement && elementCnt > 0) isRootElement = false;
            if (!isRootElement && entityClass != null) {
                createAndInitializeInstance(entityClass);
                entityClass = null;
            }

            String name=expandedName.getLocalName();
            if (ci == null) {
                TypeManager.TypeDescriptor entityType= session.getRuleServiceProvider().getTypeManager().getTypeDescriptor(expandedName);
                ci=(Concept) this.newInstance(entityType.getImplClass());
                elements.push(ci);
                return;
            }

            if ((ci != null)) {
                pi = ci.getProperty(name);

                //TODO: need puneet to verify this is for containedConcept only
                if (pi instanceof PropertyArrayContainedConcept) {
                    entityClass = ((PropertyArrayContainedConcept)pi).getType();
                    elements.push(pi);
                } else if (pi instanceof PropertyAtomContainedConcept) {
                    entityClass = ((PropertyAtomContainedConcept)pi).getType();
                    elements.push(pi);
                } else if (pi instanceof PropertyArrayConceptReference) {
					if (((ConceptImpl)ci).expandPropertyRefs()) {
						entityClass = ((PropertyArrayConceptReference) pi).getType();
						elements.push(pi);
					} else { // default behavior
						ci = pi.getParent();
						elements.push(pi);
					}
                } else if (pi instanceof PropertyAtomConceptReference) {
                	if (((ConceptImpl)ci).expandPropertyRefs()) {
                		entityClass = ((PropertyAtomConceptReference)pi).getType();
                        elements.push(pi);
                	} else { //default behavior
                		ci = pi.getParent();
                		elements.push(pi);
                	}
                } else if (pi instanceof PropertyArray) {
                    PropertyAtom ti= ((PropertyArray) pi).add();
                    elements.push(ti);
                } else {
                    if (pi != null) {
                        ci = pi.getParent();
                        elements.push(pi);
                    } else {
                        elements.push(ci);
                    }
                }
            } else {
                pi = ci.getProperty(name);
                if (pi != null) {
                    elements.push(pi);
                    //return;
                } else {
                    elements.push(expandedName);
                }
            }

            elementCnt++;
        } catch (Exception e) {
            throw new SAXException(e);
        }
    }

    public void endElement(ExpandedName expandedName, SmElement smElement, SmType smType) throws SAXException {
    	try {
    		// last check to create instance if one is pending
    		if (!isRootElement && entityClass != null) {
    			createAndInitializeInstance(entityClass);
    			entityClass = null;
    		}

    		Object o = elements.pop();
    		if (o instanceof Property) {
    			pi = ((Property)o);
    			ci = pi.getParent();
    		}

    		else if (o instanceof Concept) {
    			ci = (Concept)o;
    		}
    	} catch(Exception exception) {
    		throw new SAXException(exception);
    	}        
    }

    public void text(String s, boolean b) throws SAXException {
        Object o = elements.peek();
        if (o instanceof PropertyConcept) {
        	return;
        }
        if (o instanceof PropertyAtom) {
            try {
                /***************************************************
                 * Added to remove the timestamp part of a date
                 ***************************************************/
                if (o instanceof PropertyAtomDateTime) {
                    PropertyAtomDateTime padt = (PropertyAtomDateTime)o;
                    //Chop off the timestamp part if system property set to true
                    if (removeTimeStamp) {
                        //Chop timestamp portion
                        int index = s.indexOf('T');
                        s = s.substring(0, index);
                    }
                    padt.setValue(s);
                } else {
                    ((PropertyAtom)o).setValue(s);
                }
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if (o instanceof Concept) {
            // Ignore the text
        } else {
            throw new SAXException("Unknown Type - " + o.getClass() + " ***" + o);
        }
    }

    public void text(XmlTypedValue val, boolean b) throws SAXException {
        Object o = elements.peek();

        if (o instanceof PropertyConcept) {
        	return;
        }

        if (o instanceof PropertyAtom) {
            try {
                /***************************************************
                 * Added to remove the timestamp part of a date
                 ***************************************************/

                if (o instanceof PropertyAtomDateTime) {
                    PropertyAtomDateTime padt = (PropertyAtomDateTime)o;
                    //Chop off the timestamp part if system property set to true
                    if (removeTimeStamp) {
                        //Chop timestamp portion
                        if (val != null) {
                            String s= val.getAtom(0).castAsString();
                            int index = s.indexOf('T');
                            s = s.substring(0, index);
                            padt.setValue(s);
                        } else {
                            padt.setValue((XmlTypedValue) null);
                        }
                    } else {
                        padt.setValue(val);
                    }

                } else {
                    ((PropertyAtom)o).setValue(val);
                }
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if (o instanceof Concept) {
            // Ignore the text
        } else {
            throw new SAXException("Unknown Type - " + o.getClass() + " ***" + o);
        }
    }

    private Object newInstance (Class clz) throws Exception{
        long id = session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz);
        Constructor cons = clz.getConstructor(new Class[] {long.class});
        Object o = cons.newInstance(new Object[] {new Long(id)});
        return o;
    }

    private void createAndInitializeInstance(Class clz) throws Exception {
    	if (clz != null) {
    		ci = (Concept) newInstance(clz);

    		if (pi instanceof PropertyArrayContainedConcept) ((PropertyArrayContainedConcept)pi).add(ci);
    		else if (pi instanceof PropertyAtomContainedConcept) ((PropertyAtomContainedConcept)pi).setContainedConcept((ContainedConcept)ci);
    		else if (pi instanceof PropertyArrayConceptReference) ((PropertyArrayConceptReference) pi).add(ci);
    		else if (pi instanceof PropertyAtomConceptReference) ((PropertyAtomConceptReference)pi).setConcept(ci);

    		if ((pi instanceof PropertyAtomConceptReference || pi instanceof PropertyArrayConceptReference) && session.getObjectManager() instanceof BaseObjectManager) {
    			// if the parent concept is expanding reference properties, we need to ensure that the inline ref concept is added to WM
    			if (inlineAssert && ((ConceptImpl)pi.getParent()).expandPropertyRefs()) {
    				Concept concept = ci;
    				Element element = session.getObjectManager().getElement(concept.getId());
    				if (element == null) {
    					((BaseObjectManager)session.getObjectManager()).getAddElementHandle(concept);
    				}
    			}
    		}
    		if (extId != null) {
    			ci.setExtId(extId.toString());
    			extId = null;
    		}

    		if (parentId != null) {
    			if (ci instanceof ContainedConcept) {
    				long id= parentId.getAtom(0).castAsLong();
    				Concept parent= (Concept)session.getObjectManager().getElement(id);
    				if (parent != null) {
    					((ContainedConcept) ci).setParent(parent);
    				}
    			}
    		}

    		if (parentExtId != null) {
    			if (ci instanceof ContainedConcept) {
    				long id = parentExtId.getAtom(0).castAsLong();
    				Concept parent= (Concept)session.getObjectManager().getElement(id);
    				if (parent != null) {
    					((ContainedConcept) ci).setParent(parent);
    				}
    				parentExtId = null;
    			}
    		}
    	}
    }
    
    private Class getClass(String type) throws Exception {
        final String TIBCO_CODE_GEN_PACKAGE = "be.gen";
        
        if (type.startsWith(TypeManager.DEFAULT_BE_NAMESPACE_URI)) {
            type = type.substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length());
            type = TIBCO_CODE_GEN_PACKAGE + type.replaceAll("/", ".");
            return Class.forName(type, true, session.getRuleServiceProvider().getClassLoader());
        
        // This is the case for asserting contained and reference concepts form tester/debugger.
        } else if (type.equalsIgnoreCase("ContainedConcept") || type.equalsIgnoreCase("ConceptReference")) {
        	// the code assumes that the 'entityPath' attribute is processed prior to the 'type' attribute,
        	// the TestDataAsserter is responsible for ensuring that
        	if (refEntityPath != null && !refEntityPath.isEmpty()) {	
        		type = TIBCO_CODE_GEN_PACKAGE + refEntityPath.replaceAll("/", ".");
        		refEntityPath=null;
        		return Class.forName(type, true, session.getRuleServiceProvider().getClassLoader());
        	}
        }
        return null;
    }
}
