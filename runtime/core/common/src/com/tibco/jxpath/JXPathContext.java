package com.tibco.jxpath;

import java.util.Stack;

import javax.xml.xpath.XPathFunctionResolver;

import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 11/3/11 / Time: 6:42 AM
*/
public class JXPathContext implements MutableXPathContext {

    NodeResolver nodeResolver;
    XPathFunctionResolver xPathFunctionResolver;
    Stack stack;
    XObject currentContextNode;
    boolean isAbbreviated;
    int currentContextCount;
    int currentContextPosition;


    public JXPathContext()
    {
    }


    public NodeResolver getNodeResolver() {
        return nodeResolver;
    }

    public XPathFunctionResolver getFunctionResolver() {
        return xPathFunctionResolver;
    }

    public void setNodeResolver(NodeResolver nodeResolver) {
        this.nodeResolver = nodeResolver;
    }

    public void setXPathFunctionResolver(XPathFunctionResolver xPathFunctionResolver) {
        this.xPathFunctionResolver = xPathFunctionResolver;
    }

    @Override
    public void setCurrentContextNode(XObject pathContext) {
        this.currentContextNode = pathContext;
    }

    @Override
    public XObject getCurrentContextNode() {
        return currentContextNode;
    }

    @Override
    public boolean isAbbreviatedStep() {
        return this.isAbbreviated;
    }

    @Override
    public void setAbbreviatedStep(boolean abbr) {
        this.isAbbreviated = abbr;
    }

    @Override
    public void setCurrentContextCount(int count) {
        this.currentContextCount = count;
    }

    @Override
    public void setCurrentContextPosition(int pos) {
        this.currentContextPosition = pos;
    }

    @Override
    public int getCurrentContextPosition() {
        return this.currentContextPosition;
    }

    @Override
    public int getCurrentContextCount() {
        return getNodeResolver().count(this.currentContextNode);
    }
}
