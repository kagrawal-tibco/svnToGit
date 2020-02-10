package com.tibco.jxpath;

import javax.xml.namespace.QName;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.InputSource;

import com.tibco.jxpath.compiler.JXPathCompiler;
import com.tibco.jxpath.objects.XObject;

/*
* Author: Suresh Subramani / Date: 10/29/11 / Time: 9:40 PM
*/
public class JXPathExpression implements XPathExpression {

    JXPath jxPath;
    String xpathStr;
    Expression expr;

    JXPathExpression(JXPath jxPath, String xpathStr)
    {
        this.jxPath = jxPath;
        this.xpathStr = xpathStr;
    }

    public void compile() throws XPathExpressionException
    {
        JXPathCompiler compiler = new JXPathCompiler();
        try {
            expr = compiler.compile(xpathStr);
        }
        catch (Exception e) {
            throw new XPathExpressionException(e);
        }
    }

    /**
     *
     * @param item
     * @param returnType
     * @return
     * @throws XPathExpressionException
     */
    public Object evaluate(Object item, QName returnType) throws XPathExpressionException {


        JXPathContext context = new JXPathContext();
        context.setNodeResolver(NodeResolver.class.cast(item));
        context.setXPathFunctionResolver(this.jxPath.getXPathFunctionResolver());
        context.setCurrentContextNode(null);

        try{
            XObject xobj = expr.eval(context);
            return returnAsType(xobj, returnType);

        }
        catch (TransformerException te) {
            throw new XPathExpressionException(te);
        }

    }

    //ss:todo
    private Object returnAsType(XObject xobj, QName returnType) throws TransformerException {

        if (xobj == null) return null;

        if (XPathConstants.BOOLEAN == returnType) {
            return xobj.bool();
        }
        else if (XPathConstants.NUMBER == returnType)
        {
           return xobj.num();
        }

        else if (XPathConstants.STRING == returnType)
        {
            return xobj.str();
        }
        else if (XPathConstants.NODE == returnType)
        {
           return xobj.object();
        }

        else if (XPathConstants.NODESET == returnType)
        {
           return xobj.object();
        }
        throw new TransformerException("invalid QName specified for returnType");
    }

    public String evaluate(Object item) throws XPathExpressionException {
        return evaluate(item, XPathConstants.STRING).toString();
    }

    public Object evaluate(InputSource source, QName returnType) throws XPathExpressionException {
        throw new XPathExpressionException("JXPathExpression cannot process xml dom objects - should not come here.");
    }

    public String evaluate(InputSource source) throws XPathExpressionException {
        throw new XPathExpressionException("JXPathExpression cannot process xml dom objects - should not come here.");
    }
}
