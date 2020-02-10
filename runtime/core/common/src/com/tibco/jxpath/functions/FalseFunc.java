package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XBoolean;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:58 PM
*/
public class FalseFunc implements Function {

    public static final Function FUNCTION = new FalseFunc();
    public static final QName FUNCTION_NAME = new QName("false");

     private FalseFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) {
        return XBoolean.FALSE;
    }
}
