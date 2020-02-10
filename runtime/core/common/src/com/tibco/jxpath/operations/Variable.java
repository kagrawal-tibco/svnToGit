package com.tibco.jxpath.operations;

import javax.xml.namespace.QName;
import javax.xml.transform.TransformerException;

import com.tibco.jxpath.Expression;
import com.tibco.jxpath.XPathContext;
import com.tibco.jxpath.objects.XObject;
import com.tibco.jxpath.objects.XObjectFactory;

/*
* Author: Suresh Subramani / Date: 10/31/11 / Time: 5:17 PM
*/
public class Variable implements Expression {

	private QName variableName;
	


	public void setVariableName(java.lang.String variableName) {
		this.variableName = new QName(variableName);
	}

	public XObject eval(XPathContext context) throws TransformerException {
        Object obj = context.getNodeResolver().resolveVariable(variableName);
        XObject xobj =  XObjectFactory.create(obj);
        return xobj;

    }
}
