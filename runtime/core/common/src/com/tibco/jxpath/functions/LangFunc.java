package com.tibco.jxpath.functions;

import java.util.List;

import javax.xml.namespace.QName;

import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:58 PM
*/
public class LangFunc implements Function {

     public static final Function FUNCTION = new LangFunc();
    static final QName FUNCTION_NAME = new QName("lang");

    private LangFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
