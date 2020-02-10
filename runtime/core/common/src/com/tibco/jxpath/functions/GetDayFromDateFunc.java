package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XDate;
import com.tibco.jxpath.objects.XNumber;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XPathValueParseException;

/**
* XPath 2.0 function
*/
public class GetDayFromDateFunc implements Function {

    public static final Function FUNCTION = new GetDayFromDateFunc();
    public static final QName FUNCTION_NAME = new QName("get-day-from-date");

    private GetDayFromDateFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 1) throw new XPathFunctionException("Expecting 1 argument, received "+args.size());
        String dateString = args.get(0).str();
        try {
			XDate date = XDate.compile(dateString);
			return new XNumber(date.getDayOfMonth());
		} catch (XPathValueParseException e) {
			e.printStackTrace();
			throw new XPathFunctionException(e);
		}

    }
}
