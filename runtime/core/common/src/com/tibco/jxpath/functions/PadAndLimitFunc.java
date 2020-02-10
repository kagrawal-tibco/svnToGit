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
public class PadAndLimitFunc implements Function {

    public static final Function FUNCTION = new PadAndLimitFunc();
    public static final QName FUNCTION_NAME = new QName("pad-and-limit");

    private PadAndLimitFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 2 && args.size() != 3) throw new XPathFunctionException("Expecting 2 or 3 arguments, received "+args.size());

        String s0 = args.get(0).str();
        int padCount = (int) args.get(1).num();
        String padString = " ";
        if (args.size() == 3) {
        	padString = args.get(2).str();
        }
        if (padCount < s0.length()) {
        	throw new XPathFunctionException(new IllegalArgumentException("Given pad length is less than the initial string"));
        }
        padCount -= s0.length();
        final StringBuffer buffer = new StringBuffer(padString.length() * padCount);

        for (int i = 0; i < padCount; i++)
        {
            buffer.append(padString);
        }

        return new XString(s0 + buffer.toString());
    }
}
