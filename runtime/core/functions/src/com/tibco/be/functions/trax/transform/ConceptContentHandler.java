package com.tibco.be.functions.trax.transform;

import java.lang.reflect.Constructor;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyArrayConceptReference;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.session.RuleSession;

public class ConceptContentHandler implements ContentHandler {
	
	Concept ci;
	Property pi;
	Stack<Object> elements;
	RuleSession session;
	
    public ConceptContentHandler(Concept rootConcept, RuleSession session) {
        super();
        this.ci = rootConcept;
        this.elements = new Stack<Object>();
        this.session = session;
    }

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String s = String.valueOf(ch, start, length);
        Object o = elements.peek();
        if (o instanceof Property) {
            try {
                ((PropertyAtom)o).setValue(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new SAXException("Unknown Type");
        }
		
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("endDocument");		
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
        Object o = elements.pop();

        if (o instanceof Property) {
            ci = ((Property)o).getParent();
        }
		
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		System.out.println("endPrefixMapping:" + prefix);
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		System.out.println("ignorableWhitespace:" + String.valueOf(ch, start, length));
	}

	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		System.out.println("processingInstruction:" + target + ":" + data);
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		System.out.println("setDocumentLocator");
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		System.out.println("skippedEntity:" + name);
		
	}

	@Override
	public void startDocument() throws SAXException {
		System.out.println("startDocument");
		
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes atts) throws SAXException {
        try {
        	
        	handleAttributes(atts);
        	
            if ((ci != null) && elements.size() >=2) {
                pi = ci.getProperty(localName);
                if (pi == null) {
                    elements.push(localName);
                } else {
                    //TODO: need puneet to verify this is for containedConcept only
                    if (pi instanceof PropertyArrayContainedConcept) {
                        ci = (Concept) newInstance(((PropertyArrayContainedConcept)pi).getType());
                        ((PropertyArrayContainedConcept)pi).add(ci);
                        elements.push(pi);
                    } else if (pi instanceof PropertyAtomContainedConcept) {
                        ci = (Concept) newInstance(((PropertyAtomContainedConcept)pi).getType());
                        ((PropertyAtomContainedConcept)pi).setContainedConcept((ContainedConcept)ci);
                        elements.push(pi);
                    }
                    else if (pi instanceof Property.PropertyConceptReference) {
                        ci = pi.getParent();
                        elements.push(pi);
                        //throw new RuntimeException("Support for this type??  Need to ask Puneet");
                    } else if (pi instanceof PropertyArray) {
                        PropertyAtom ti= ((PropertyArray) pi).add();
                        elements.push(ti);
                    }
                    else {
                        ci = pi.getParent();
                        elements.push(pi);
                    }
                }
            }
            else {
                if (ci!= null) {
                    pi = ci.getProperty(name);
                    if (pi != null) {
                        elements.push(pi);
                        return;
                    }
                }
                elements.push(localName);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		System.out.println("startPrefixMapping:" + prefix + ":" + uri);
	}
	
	private void handleAttributes(Attributes atts){
		int attLength = atts.getLength();
		for(int i=0; i < attLength; i++){
			String attLocalName = atts.getLocalName(i);
			String value = atts.getValue(i);
            if (attLocalName.equals(("extId"))) {
                ci.setExtId(value);
            } else if (attLocalName.equals("ref")) {
                Property.PropertyConceptReference refFrom=(Property.PropertyConceptReference) elements.peek();
                long id= Long.parseLong(value);
                if (refFrom instanceof PropertyAtomConceptReference) {
                    addReference((PropertyAtomConceptReference) refFrom, id);
                } else if (refFrom instanceof PropertyArrayConceptReference) {
                    addReference((PropertyArrayConceptReference) refFrom, id);
                }
            } else if (attLocalName.equals("parent")) {
                if (ci instanceof ContainedConcept) {
                    long id= Long.parseLong(value);
                    Concept parent= (Concept)session.getObjectManager().getElement(id);
                    ((ContainedConcept) ci).setParent(parent);
                }
            }
		}
	}
	
    private Object newInstance (Class<?> clz) throws Exception{
        long id = session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz);
        Constructor<?> cons = clz.getConstructor(new Class[] {long.class});
        Object o=cons.newInstance(new Object[] {new Long(id)});
        return o;
    }
    
    void addReference(PropertyAtomConceptReference refFrom, long id) {
        Concept refTo= (Concept)session.getObjectManager().getElement(id);
        if (refTo != null) {
            //refFrom. Need to check with Nick
            refFrom.setConcept(refTo);
        }
    }

    void addReference(PropertyArrayConceptReference refFrom, long id) {
        Concept refTo= (Concept)session.getObjectManager().getElement(id);
        if (refTo != null) {
            //refFrom. Need to check with Nick
            refFrom.add(refTo);
        }
    }

}
