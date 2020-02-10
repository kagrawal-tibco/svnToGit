package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XDateTime;
import com.tibco.jxpath.objects.XGregorianComparator;
import com.tibco.jxpath.objects.XNumber;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XPathValueParseException;

/**
* XPath 2.0 function
*/
public class CompareDateTimeFunc implements Function {

    public static final Function FUNCTION = new CompareDateTimeFunc();
    public static final QName FUNCTION_NAME = new QName("compare-dateTime");

    private CompareDateTimeFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 2) throw new XPathFunctionException("Expecting 2 arguments, received "+args.size());

        String dateTimeString1 = args.get(0).str();
        String dateTimeString2 = args.get(1).str();
        
        try {
        	XDateTime date1 = XDateTime.compile(dateTimeString1);
        	XDateTime date2 = XDateTime.compile(dateTimeString2);
        	
			return new XNumber(XGregorianComparator.INSTANCE.compare(date1, date2));
		} catch (XPathValueParseException e) {
			throw new XPathFunctionException(e);
		}

    }
}
