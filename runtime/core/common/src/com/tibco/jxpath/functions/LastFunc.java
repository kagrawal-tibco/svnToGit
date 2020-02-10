package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XObjectFactory;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:30 PM
*/

/**
 * NodeSet function
 */
public class LastFunc implements NodeSetFunction {
     public static final Function FUNCTION = new LastFunc();
    static QName FUNCTION_NAME = new QName("last");

    private LastFunc() {}

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) {

        int count = context.getCurrentContextCount();
        return XObjectFactory.create(count);

    }
}
