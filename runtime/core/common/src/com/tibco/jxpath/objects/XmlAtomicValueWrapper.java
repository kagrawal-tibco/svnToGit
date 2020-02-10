package com.tibco.jxpath.objects;

import javax.xml.transform.TransformerException;

import com.tibco.jxpath.XPathContext;
import com.tibco.xml.data.primitive.XmlAtomicValue;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;

/*
* Author: Suresh Subramani / Date: 11/3/11 / Time: 6:11 AM
*/
public class XmlAtomicValueWrapper implements XObject {

	XmlAtomicValue wrapped;
    public XmlAtomicValueWrapper(XmlAtomicValue obj)
    {
        wrapped = obj;
    }

    public boolean bool() {
		try {
			return wrapped.castAsBoolean();
		} catch (XmlAtomicValueCastException e) {
			e.printStackTrace();
		}
        return false;
    }

    public double num() {
        try {
			return wrapped.castAsDouble();
		} catch (XmlAtomicValueCastException e) {
			e.printStackTrace();
		}
        return 0;
    }

    public String str() {
        return wrapped.castAsString();
    }

    public Object object() {
        return wrapped;
    }



    public boolean equals(XObject other) {
        return (this==other) || this.wrapped.equals(other.object());
    }

    public XObject eval(XPathContext context) throws TransformerException {
        return this;
    }
}
