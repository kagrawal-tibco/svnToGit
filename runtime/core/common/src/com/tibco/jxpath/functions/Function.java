package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:27 PM
*/
public interface Function {

    /**
     * returns the QName of the function. The name matches exactly to the xpath functions, or user-defined functions
     * @return
     */
    QName name();

    /**
     * Execute the function with the context and the args.
     * @param context
     * @param args
     * @return
     */
    XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException;
}
