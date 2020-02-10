package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XDate;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XPathValueParseException;

/**
* XPath 2.0 function
*/
public class CreateDateFunc implements Function {

    public static final Function FUNCTION = new CreateDateFunc();
    public static final QName FUNCTION_NAME = new QName("create-date");

    private CreateDateFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 3) throw new XPathFunctionException("Expecting 3 arguments, received "+args.size());

        int year = (int) args.get(0).num();
        int month = (int) args.get(1).num();
        int day = (int) args.get(2).num();
        
        try {
			return XDate.create(year, month, day, null);
		} catch (XPathValueParseException e) {
			throw new XPathFunctionException(e);
		}

    }
}
