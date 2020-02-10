package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XNumber;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:40 PM
*/
public class CountFunc implements NodeSetFunction {

    public static final Function FUNCTION = new CountFunc();
    public static final QName FUNCTION_NAME = new QName("count");

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) {
        return new XNumber(context.getCurrentContextCount());
    }
}
