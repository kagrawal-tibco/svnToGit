package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XDate;
import com.tibco.jxpath.objects.XGregorianComparator;
import com.tibco.jxpath.objects.XNumber;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XPathValueParseException;

/**
* XPath 2.0 function
*/
public class CompareTimeFunc implements Function {

    public static final Function FUNCTION = new CompareTimeFunc();
    public static final QName FUNCTION_NAME = new QName("compare-date");

    private CompareTimeFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 2) throw new XPathFunctionException("Expecting 2 arguments, received "+args.size());

        String timeString1 = args.get(0).str();
        String timeString2 = args.get(1).str();
        
        try {
        	XDate date1 = XDate.compile(timeString1);
        	XDate date2 = XDate.compile(timeString2);
        	
			return new XNumber(XGregorianComparator.INSTANCE.compare(date1, date2));
		} catch (XPathValueParseException e) {
			throw new XPathFunctionException(e);
		}

    }
}
