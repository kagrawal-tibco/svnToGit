package com.tibco.jxpath.functions;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XPathValueParseException;
import com.tibco.jxpath.objects.XTime;

/**
* XPath 2.0 function
*/
public class CreateTimeFunc implements Function {

    public static final Function FUNCTION = new CreateTimeFunc();
    public static final QName FUNCTION_NAME = new QName("create-time");

    private CreateTimeFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 3 && args.size() != 4) throw new XPathFunctionException("Expecting 3 or 4 arguments, received "+args.size());

        int hour = (int) args.get(0).num();
        int minutes = (int) args.get(1).num();
        double seconds = args.get(2).num();
        int milliseconds = 0;
        if (args.size() == 4) {
        	milliseconds = (int) args.get(3).num();
        }
        
        try {
			return XTime.create(hour, minutes, BigDecimal.valueOf(seconds+milliseconds/1000L), null);
		} catch (XPathValueParseException e) {
			throw new XPathFunctionException(e);
		}

    }
}
