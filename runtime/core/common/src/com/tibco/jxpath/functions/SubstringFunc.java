package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XString;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:53 PM
*/
public class SubstringFunc implements Function {

     public static final Function FUNCTION = new SubstringFunc();
    public static final QName FUNCTION_NAME = new QName("substring");

    private SubstringFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
         if (args.size() < 2) throw new XPathFunctionException("Expecting minimum of 2 args");

        String s0 = args.get(0).str();
        int  start = (int) args.get(1).num();
        int end = s0.length();
        if (args.size() == 3) {
            end = (int) args.get(2).num();
        }

        return new XString(s0.substring(start, end));

    }
}
