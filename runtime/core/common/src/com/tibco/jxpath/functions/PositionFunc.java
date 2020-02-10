package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XNumber;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:38 PM
*/
public class PositionFunc implements NodeSetFunction {

    public static final Function FUNCTION = new PositionFunc();
    public static QName FUNCTION_NAME = new QName("position");

    private PositionFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) {
        return new XNumber(context.getCurrentContextPosition());
    }
}
