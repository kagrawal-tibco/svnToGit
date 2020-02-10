package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XObjectList;
import com.tibco.jxpath.objects.XString;

/*
* Author: Ryan Hollom
*/
public class TokenizeAllowEmptyFunc implements Function {

    public static final Function FUNCTION = new TokenizeAllowEmptyFunc();
    public static final QName FUNCTION_NAME = new QName("tokenize-allow-empty");

    private TokenizeAllowEmptyFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {
        if (args.size() != 1 && args.size() != 2) throw new XPathFunctionException("Expecting 1 or 2 arguments, received "+args.size());

        String str = args.get(0).str();
        String delim = " \r\n\t";
        if (args.size() == 2) {
        	delim = args.get(1).str();
        }
        
        return tokenize(str, delim);
    }

	private XObject tokenize(String str, String delims) {
		XObjectList objList = new XObjectList();
		String[] arr = java.util.regex.Pattern.compile("["+delims+"]").split(str);
		for (String token : arr) {
			objList.add(new XString(token));
		}
		return objList;
	}

}
