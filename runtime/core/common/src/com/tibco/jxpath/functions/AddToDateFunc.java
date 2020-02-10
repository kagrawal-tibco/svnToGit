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
public class AddToDateFunc implements Function {

    public static final Function FUNCTION = new AddToDateFunc();
    public static final QName FUNCTION_NAME = new QName("add-to-date");

    private AddToDateFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 4) throw new XPathFunctionException("Expecting 4 arguments, received "+args.size());

        String dateString = args.get(0).str();
        int year = (int) args.get(1).num();
        int month = (int) args.get(2).num();
        int day = (int) args.get(3).num();
        
        try {
        	XDate date = XDate.compile(dateString);
			return XDate.create(date.getYear()+year, date.getMonth()+month, date.getDayOfMonth()+day, null);
		} catch (XPathValueParseException e) {
			throw new XPathFunctionException(e);
		}

    }
}
