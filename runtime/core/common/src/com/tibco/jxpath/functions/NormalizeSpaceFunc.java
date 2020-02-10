package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XString;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:55 PM
*/
public class NormalizeSpaceFunc implements Function {

     public static final Function FUNCTION = new NormalizeSpaceFunc();
    static final QName FUNCTION_NAME = new QName("normalize-space");

    private NormalizeSpaceFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) {

        XObject arg = args.size() == 1 ? args.get(0) : context.getCurrentContextNode();
        if (arg == null) return null;

        XString denormalizeStr = new XString(arg.str());

        return denormalizeStr.fixWhiteSpace(true, true, false);
    }


}
