package com.tibco.jxpath.functions;

import java.util.HashSet;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunctionException;

import com.tibco.jxpath.NodeResolver;
import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XObjectList;

/*
* Author: Suresh Subramani / Date: 11/4/11 / Time: 5:41 PM
*/
public class IdFunc implements NodeSetFunction {

     public static final Function FUNCTION = new IdFunc();
    static final QName FUNCTION_NAME = new QName("id");

    private IdFunc() { }

    @Override
    public QName name() {
        return FUNCTION_NAME;
    }

    @Override
    public XObject execute(XPathContext context, List<XObject> args) throws XPathFunctionException {

        if (args.size() == 0) throw new XPathFunctionException("Expecting atleast one argument");
        XObject contextNode = context.getCurrentContextNode();
        NodeResolver nodeResolver = context.getNodeResolver();

        String idlist = args.get(0).str();

        String[] ids = idlist.split(" \t\n\r\f");
        XObjectList set = new XObjectList();
        HashSet idset = new HashSet();


        for (String id : ids)
        {
            if (idset.contains(id)) continue;
            XObject xobj = nodeResolver.getChildById(contextNode, id);
            if (xobj != null) {
                set.add(xobj);
            }
        }

        return set;



    }
}
