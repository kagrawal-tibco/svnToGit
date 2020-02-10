package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XString;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:51 PM
*/
public class SubstringBeforeLastFunc implements Function {

    public static final Function FUNCTION = new SubstringBeforeLastFunc();
    public static final QName FUNCTION_NAME = new QName("substring-before");

    private SubstringBeforeLastFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
         if (args.size() != 2) throw new XPathFunctionException("Expecting 2 arguments");

        String s0 = args.get(0).str();
        String s1 = args.get(1).str();

        int indexOf = s0.lastIndexOf(s1);

        if (indexOf == -1) return new XString("");

        return new XString(s0.substring(0, indexOf ));
    }
}
