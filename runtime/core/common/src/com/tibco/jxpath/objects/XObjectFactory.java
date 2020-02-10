package com.tibco.jxpath.objects;

import java.util.GregorianCalendar;

import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.xml.data.primitive.XmlAtomicValue;
import com.tibco.xml.datamodel.nodes.Element;

/*
* Author: Suresh Subramani / Date: 11/3/11 / Time: 5:59 AM
*/
public class XObjectFactory {

    public static XObject create(Object obj)
    {
        if (obj == null) return null;

        if (obj instanceof String) {
           return new XString(String.class.cast(obj));
        }

        if (obj instanceof Number) {
            return new XNumber(Number.class.cast(obj));
        }

        if (obj instanceof Boolean) {
            return new XBoolean(Boolean.class.cast(obj));
        }

        if (obj instanceof Element) {
        	return new XElementWrapper(Element.class.cast(obj));
//        	return new XmlAtomicValueWrapper(XmlAtomicValue.class.cast(((Element) obj).getAtomicValue()));
        }
        
        if (obj instanceof XmlAtomicValue) {
        	return new XmlAtomicValueWrapper(XmlAtomicValue.class.cast(obj));
        }
        
        if (obj instanceof GregorianCalendar) {
        	return new XDateTime(((GregorianCalendar) obj).getTime(), ((GregorianCalendar) obj).getTimeZone());
        }
        
        if (obj instanceof PropertyAtom) {
        	return create(((PropertyAtom) obj).getValue());
        }
        
        if (obj instanceof XObject) {
        	return (XObject) obj;
        }
        else {
            return new XObjectWrapper(obj);
        }




    }

}
