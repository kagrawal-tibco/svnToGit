package com.tibco.be.functions.xpath;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import javax.xml.namespace.QName;

import org.genxdm.exceptions.GenXDMException;

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
import com.tibco.xml.data.primitive.XmlAtomicValue;

public class ConceptSequenceHandler extends AbstractSequenceHandler {

    private Concept ci;
    private Property pi;
    private Stack elements;
    private HashMap arguments = new HashMap();
	private RuleSession session;

	public ConceptSequenceHandler(Concept rootConcept, RuleSession session) {
		this.ci = rootConcept;
		this.session = session;
		elements = new Stack<>();
	}

	@Override
	public void endElement() throws GenXDMException {
        Object o = elements.pop();

        if (o instanceof Property) {
            ci = ((Property)o).getParent();
        }
    }

	@Override
	public void text(String s) throws GenXDMException {
        Object o = elements.peek();
        if (o instanceof Property) {
            try {
                ((PropertyAtom)o).setValue(s);
            } catch (Exception e) {
            	e.printStackTrace();
                throw new GenXDMException(e);
            }
        } else {
        	throw new GenXDMException("Unknown Type");
        }
    }

	@Override
	public void attribute(String paramString1, String paramString2,
			String paramString3, List<? extends XmlAtomicValue> paramList,
			QName paramQName) throws GenXDMException {
		if (paramList.size() == 0) {
			return;
		}
		XmlAtomicValue value = paramList.get(0);
        try {

            if (paramString2.equals(("extId")))
            {
                ci.setExtId(value.toString());

            } else if (paramString2.equals("ref"))
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

            } else if (paramString2.equals("parent"))
            {
                if (ci instanceof ContainedConcept)
                {
                    long id= value.getAtom(0).castAsLong();
                    Concept parent= (Concept)session.getObjectManager().getElement(id);
                    ((ContainedConcept) ci).setParent(parent);
                }

            }
        }catch (Exception e) {
            throw new GenXDMException(e);
        }
    }

	@Override
	public void startElement(String ns, String elementName,
			String prefix, QName paramQName) throws GenXDMException {
        //TODO Validations - Can only be called from objectHelper
        try {
            String name = elementName;
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
                elements.push(name);
            }

        } catch (Exception e) {
        	e.printStackTrace();
            throw new GenXDMException(e);
        }
    }

	@Override
	public void text(List<? extends XmlAtomicValue> paramList) throws GenXDMException {
		if (paramList.size() == 0) {
			return;
		}
		XmlAtomicValue val = paramList.get(0);
        Object o = elements.peek();
        if (o instanceof Property) {
            try {
                ((PropertyAtom)o).setValue(val);
            } catch (Exception e) {
            	e.printStackTrace();
                throw new GenXDMException(e);
            }
        } else if (o instanceof ExpandedName) {
            arguments.put(((ExpandedName)o).getLocalName(), val.toString());
        } else if (o instanceof java.lang.String) {
            if (((String)o).equals(("_extId"))) {

            }
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

    Object newInstance (Class clz) throws Exception{
        long id = session.getRuleServiceProvider().getIdGenerator().nextEntityId(clz);
        Constructor cons = clz.getConstructor(new Class[] {long.class});
        Object o=cons.newInstance(new Object[] {new Long(id)});
        return o;
    }
}
