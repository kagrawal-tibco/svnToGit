package com.tibco.jxpath;

import javax.xml.xpath.XPathFunctionResolver;

import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 10/31/11 / Time: 2:43 PM
*/
public interface XPathContext {

    NodeResolver getNodeResolver();

    XPathFunctionResolver getFunctionResolver();

    XObject getCurrentContextNode();

    boolean isAbbreviatedStep();

    int getCurrentContextPosition();

    int getCurrentContextCount();
}
