package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XDateTime;
import com.tibco.jxpath.objects.XNumber;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XPathValueParseException;

/**
* XPath 2.0 function
*/
public class GetMinutesFromDateTimeFunc implements Function {

    public static final Function FUNCTION = new GetMinutesFromDateTimeFunc();
    public static final QName FUNCTION_NAME = new QName("get-minutes-from-dateTime");

    private GetMinutesFromDateTimeFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 1) throw new XPathFunctionException("Expecting 1 argument, received "+args.size());
        String dateString = args.get(0).str();
        try {
			XDateTime dateTime = XDateTime.compile(dateString);
			return new XNumber(dateTime.getMinutes());
		} catch (XPathValueParseException e) {
			e.printStackTrace();
			throw new XPathFunctionException(e);
		}

    }
}
