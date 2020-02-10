package com.tibco.jxpath;

import java.util.concurrent.ConcurrentHashMap;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFunctionResolver;
import javax.xml.xpath.XPathVariableResolver;

import org.xml.sax.InputSource;

/*
* Author: Suresh Subramani / Date: 10/29/11 / Time: 8:16 PM
*/

/**
 * This class support Java Object specifically BE Concepts and Events, Scorecards, Events.
 * If it can't then it provides it to to the Java framework
 */
public class JXPath implements XPath {

    XPath delegateXPath;
    ConcurrentHashMap<String, XPathExpression> expressions ;


    JXPath(XPath delegateXPath)
    {
        this.delegateXPath = delegateXPath;
        expressions = new ConcurrentHashMap<String, XPathExpression>();
    }

    public void reset() {

        this.delegateXPath.reset();
    }

    public void setXPathVariableResolver(XPathVariableResolver resolver) {

        delegateXPath.setXPathVariableResolver(resolver);
    }

    public XPathVariableResolver getXPathVariableResolver() {
        return delegateXPath.getXPathVariableResolver();
    }

    public void setXPathFunctionResolver(XPathFunctionResolver resolver) {
        this.delegateXPath.setXPathFunctionResolver(resolver);
    }

    public XPathFunctionResolver getXPathFunctionResolver() {
        return this.delegateXPath.getXPathFunctionResolver();
    }

    public void setNamespaceContext(NamespaceContext nsContext) {
        delegateXPath.setNamespaceContext(nsContext);
    }

    public NamespaceContext getNamespaceContext() {
        return delegateXPath.getNamespaceContext();
    }

    public XPathExpression compile(String expr) throws XPathExpressionException {
        return compile(expr, false);
    }

    private XPathExpression compile(String expr, boolean forceUseJaxp) throws XPathExpressionException
    {
        XPathExpression xpathExpr = this.expressions.get(expr);

        if (xpathExpr != null) return xpathExpr;

        if ((forceUseJaxp) || (expr.contains("@payload"))) { //todo
            xpathExpr =  delegateXPath.compile(expr);
        }
        else {
            xpathExpr = new JXPathExpression(this, expr);
            ((JXPathExpression)xpathExpr).compile();
        }

        this.expressions.putIfAbsent(expr, xpathExpr);

        return xpathExpr;
    }

    public Object evaluate(String expr, Object item, QName returnType) throws XPathExpressionException {

        XPathExpression xPathExpr = compile(expr, false);
        return xPathExpr.evaluate(item, returnType);
    }

    public String evaluate(String expr, Object item) throws XPathExpressionException {
        XPathExpression xPathExpr = compile(expr, false);
        return xPathExpr.evaluate(item);
    }

    public Object evaluate(String expr, InputSource source, QName returnType) throws XPathExpressionException {

        XPathExpression xpathExpr = compile(expr, true);

        return xpathExpr.evaluate(source, returnType);

    }

    public String evaluate(String expr, InputSource source) throws XPathExpressionException {
        XPathExpression xpathExpr = compile(expr, true);

        return xpathExpr.evaluate(source);
    }
}
