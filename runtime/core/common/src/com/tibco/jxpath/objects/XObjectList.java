package com.tibco.jxpath.objects;

import java.util.ArrayList;

import javax.xml.transform.TransformerException;

import com.tibco.jxpath.XPathContext;

/*
* Author: Suresh Subramani / Date: 11/10/11 / Time: 4:10 PM
*/
public class XObjectList extends ArrayList<XObject> implements XObject {

    @Override
    public boolean bool() {
        return size() > 0;
    }

    @Override
    public double num() {
        return new XNumber(this.str()).num();
    }

    @Override
    public String str() {
        return size() > 0 ? get(0).str() : "";
    }

    @Override
    public Object object() {
        return this;
    }

    @Override
    public boolean equals(XObject other) {
        if (!(other instanceof XObjectList)) return false;

        XObjectList otherlist = (XObjectList) other;

        return super.equals((ArrayList)otherlist);


    }

    @Override
    public XObject eval(XPathContext context) throws TransformerException {
        return this;
    }
}
