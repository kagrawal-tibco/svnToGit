package com.tibco.jxpath.objects;

import javax.xml.transform.TransformerException;

import com.tibco.jxpath.XPathContext;

/*
* Author: Suresh Subramani / Date: 11/3/11 / Time: 6:11 AM
*/
public class XObjectWrapper implements XObject {

    Object wrapped;
    public XObjectWrapper(Object obj)
    {
        wrapped = obj;
    }

    public boolean bool() {
        return false;
    }

    public double num() {
        return 0;
    }

    public String str() {
        return wrapped.toString();
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
