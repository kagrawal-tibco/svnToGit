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
public class LowerCaseFunc implements Function {

    public static final Function FUNCTION = new LowerCaseFunc();
    public static final QName FUNCTION_NAME = new QName("lower-case");

    private LowerCaseFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 1) throw new XPathFunctionException("Expecting 1 argument");

        String s0 = args.get(0).str();
        
        return new XString(s0.toLowerCase());
    }
}
