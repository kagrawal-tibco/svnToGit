package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XString;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:50 PM
*/
public class RightFunc implements Function {

    public static final Function FUNCTION = new RightFunc();
    public static final QName FUNCTION_NAME = new QName("right");

    private RightFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 2) throw new XPathFunctionException("Expecting 2 arguments");

        String s0 = args.get(0).str();
        int numChars = (int) args.get(1).num();
        
        if (numChars <= 0) {
        	return new XString("");
        }
        if (numChars > s0.length()) {
        	return args.get(0);
        }
        return new XString(s0.substring(s0.length()-numChars));
    }
}
