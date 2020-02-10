package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XNumber;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 6:02 PM
*/
public class RoundFunc implements Function{

    public static final Function FUNCTION = new RoundFunc();
    public static final QName FUNCTION_NAME = new QName("round");

    private RoundFunc() { }


    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() == 0) throw new XPathFunctionException("Expecting 1 argument, received 0");

        XNumber number = XNumber.class.cast( args.get(0));

        return number.round();
    }
}
