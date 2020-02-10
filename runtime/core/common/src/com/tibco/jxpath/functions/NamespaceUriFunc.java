package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XString;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:44 PM
*/
public class NamespaceUriFunc implements Function {

    public static final Function FUNCTION = new NamespaceUriFunc();
    public static final QName FUNCTION_NAME = new QName("namespace-uri");

    private NamespaceUriFunc() {}

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) {
        XObject ctxNode = args.size() == 0 ? context.getCurrentContextNode() : args.get(0);

        return new XString(context.getNodeResolver().name(ctxNode).getNamespaceURI());
    }
}
