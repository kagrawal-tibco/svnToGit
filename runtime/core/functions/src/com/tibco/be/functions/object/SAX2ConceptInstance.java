package com.tibco.be.functions.object;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Stack;

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
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmType;

/**
 * User: ssubrama
 * Date: Sep 2, 2004
 * Time: 10:08:46 PM
 */
public class SAX2ConceptInstance extends AbstractContentHandler {
    Concept ci;
    Property pi;
    Stack elements;
    HashMap arguments = new HashMap();
    RuleSession session;

    static ExpandedName CREATEOBJECT_NM = ExpandedName.makeName(ObjectHelper.class.getName(), "createObject");
    static ExpandedName MODIFYOBJECT_NM = ExpandedName.makeName(ObjectHelper.class.getName(), "modifyObject");
    static ExpandedName OBJECT_NM = ExpandedName.makeName("object");


    public SAX2ConceptInstance(Concept rootConcept, RuleSession session) {
        super();
        ci = rootConcept;
        elements = new Stack();
        this.session = session;
    }

    public void attribute(ExpandedName expandedName, XmlTypedValue value, SmAttribute smAttribute) throws SAXException {
        try {

            if (expandedName.getLocalName().equals(("extId")))
            {
                ci.setExtId(value.toString());

            } else if (expandedName.getLocalName().equals("ref"))
            {
                Property.PropertyConceptReference refFrom=(Property.PropertyConceptReference) elements.peek();
                long id= value.getAtom(0).castAsLong();
                if (refFrom instanceof PropertyAtomConceptReference)
                {
                    addReference((PropertyAtomConceptReference) refFrom, id);
                } else if (refFrom instanceof PropertyArrayConceptReference)
                {
                    addReference((PropertyArrayConceptReference) refFrom, id);
                }

            } else if (expandedName.getLocalName().equals("parent"))
            {
                if (ci instanceof ContainedConcept)
                {
                    long id= value.getAtom(0).castAsLong();
                    Concept parent= (Concept)session.getObjectManager().getElement(id);
                    ((ContainedConcept) ci).setParent(parent);
                }

            }
        }catch (Exception e) {
            throw new SAXException(e);
        }
    }
    void addReference(PropertyAtomConceptReference refFrom, long id) throws Exception {
        Concept refTo= (Concept)session.getObjectManager().getElement(id);
        if (refTo != null) {
            //refFrom. Need to check with Nick
            refFrom.setConcept(refTo);
        }
    }

    void addReference(PropertyArrayConceptReference refFrom, long id) throws Exception {
        Concept refTo= (Concept)session.getObjectManager().getElement(id);
        if (refTo != null) {
            //refFrom. Need to check with Nick
            refFrom.add(refTo);
        }
    }

    public void startElement(ExpandedName expandedName, SmElement smElement, SmType smType) throws SAXException {
        //TODO Validations - Can only be called from objectHelper
        try {
            String name = expandedName.getLocalName();
            if ((ci != null) && elements.size() >=2) {
                pi = ci.getProperty(name);
                if (pi == null) {
                    elements.push(name);
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
                elements.push(expandedName);
            }

        } catch (Exception e) {
            throw new SAXException(e);
        }
    }



    public void endElement(ExpandedName expandedName, SmElement smElement, SmType smType) throws SAXException {
        Object o = elements.pop();

        if (o instanceof Property) {
            ci = ((Property)o).getParent();
        }
    }

    public void text(String s, boolean b) throws SAXException {
        Object o = elements.peek();
        if (o instanceof Property) {
            try {
                ((PropertyAtom)o).setValue(s);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else {
            throw new SAXException("Unknown Type");
        }
    }

    public void text(XmlTypedValue val, boolean b) throws SAXException {
        Object o = elements.peek();
        if (o instanceof Property) {
            try {
                ((PropertyAtom)o).setValue(val);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if (o instanceof ExpandedName) {
            arguments.put(((ExpandedName)o).getLocalName(), val.toString());
        } else if (o instanceof java.lang.String) {
            if (((String)o).equals(("_extId"))) {

            }
        }
    }


    public HashMap getArguments() {
        return arguments;
    }


    Object newInstance (Class clz) throws Exception{
        long id = session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz);
        Constructor cons = clz.getConstructor(new Class[] {long.class});
        Object o=cons.newInstance(new Object[] {new Long(id)});
        return o;
    }
}
