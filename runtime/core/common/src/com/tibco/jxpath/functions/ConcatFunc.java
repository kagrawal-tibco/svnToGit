package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XString;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:48 PM
*/
public class ConcatFunc implements Function {

    public static final Function FUNCTION = new ConcatFunc();
    public static final QName FUNCTION_NAME = new QName("concat");

    private ConcatFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) {

        StringBuilder builder = new StringBuilder();

        for(XObject obj : args) {
        	if (obj == null) {
        		continue; // seems to omit this from the concatenated string, rather than appending 'null' (?)
        	}
            builder.append(obj.str());
        }

        return new XString(builder.toString());
    }
}
