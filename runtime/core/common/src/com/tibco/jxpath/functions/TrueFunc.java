package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XBoolean;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:57 PM
*/
public class TrueFunc implements Function {

    public static final Function FUNCTION = new TrueFunc();
    public static final QName FUNCTION_NAME = new QName("true");

    private TrueFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) {
        return XBoolean.TRUE;
    }
}
