package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XBoolean;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:57 PM
*/
public final class BooleanFunc implements Function {

    public static final Function FUNCTION = new BooleanFunc();
    public static final QName FUNCTION_NAME = new QName("boolean");

    private BooleanFunc() {}

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) {

        if (args.size() == 0) return XBoolean.FALSE;

        XObject arg0 = args.get(0);
        
        if (arg0 == null) {
        	return new XBoolean(false);
        }
        return new XBoolean(arg0.bool());
    }
}
