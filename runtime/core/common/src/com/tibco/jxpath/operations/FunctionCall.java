package com.tibco.jxpath.operations;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathFunction;
import javax.xml.xpath.XPathFunctionException;
import javax.xml.xpath.XPathFunctionResolver;

import com.tibco.jxpath.JXPathFunctionRegistry;
import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.functions.Function;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XObjectFactory;

public class FunctionCall extends NaryOperation {

    private QName functionName;

    public QName getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
    	setFunctionName("", functionName, "");
    }

    public void setFunctionName(String nsURI, String functionName, String prefix) {
    	this.functionName = new QName(nsURI, functionName, prefix);
    }
    
    public void setFunctionName(String functionName, String prefix) {
    	setFunctionName("", functionName, prefix);
    }
    
    @Override
    public XObject operate(XPathContext context, List<XObject> args) throws TransformerException {

        try {
            Function func = JXPathFunctionRegistry.getInstance().getFunction(functionName);
            if (func != null) {
                return func.execute(context, args);
            }
            else {
                XPathFunctionResolver resolver = context.getFunctionResolver();
                if (resolver != null) {
                    XPathFunction xfunc = resolver.resolveFunction(functionName, args.size());
                    if (xfunc != null) {
                        return XObjectFactory.create(xfunc.evaluate(args));
                    }
                }
            }
        }
        catch (XPathFunctionException xfe) {
            throw new TransformerException(xfe);
        }

        String msg = String.format("Function name %s not available in the library. BE ProcessOrchestrator addon supports only XPath 1.0 function specs at runtime. Map the value and use a script task to accomplish the same result", this.functionName);
        throw new TransformerException(msg);

    }

}
