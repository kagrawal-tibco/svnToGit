package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XBoolean;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 6:14 PM
*/
public class NotFunc implements Function {

    public static final Function FUNCTION = new NotFunc();
    public static final QName FUNCTION_NAME = new QName("not");

    private NotFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() == 0) throw new XPathFunctionException("Expecting 1 argument, received 0");

        boolean bool = args.get(0).bool();

        return new XBoolean(!bool);

    }
}
