package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XNumber;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XString;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:54 PM
*/
public class StringLengthFunc implements Function {

    public static final Function FUNCTION = new StringLengthFunc();
    public static final QName FUNCTION_NAME = new QName("string-length");

    private StringLengthFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
       if (args.size() == 0) throw new XPathFunctionException("Expecting 1 argument, received 0");

        XString str = XString.class.cast( args.get(0));

        return new XNumber(str.str().length());
    }
}
