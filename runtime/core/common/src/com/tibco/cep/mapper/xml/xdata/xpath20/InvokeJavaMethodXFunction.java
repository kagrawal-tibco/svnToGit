/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath20;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.genxdm.xpath.v20.expressions.Expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.xmodel.xpath.type.XType;

public final class InvokeJavaMethodXFunction extends InvokeJavaXFunction
{
    protected Method          mMethod;

    public InvokeJavaMethodXFunction(String namespace, Method method)
    {
    	super(method.getName(), convertClassToXType(method.getReturnType()), getParameterXTypes(method));
        mMethod = method;
    }

    private static XType[] getParameterXTypes(Method method) {
    	Class<?>[] parameterTypes = method.getParameterTypes();
    	XType[] types = new XType[parameterTypes.length];
    	for (int i=0; i<parameterTypes.length; i++) {
    		types[i] = convertClassToXType(parameterTypes[i]);
    	}
		return types;
	}

	public Expr getAsExpr(Expr[] args)
    {
        return new JavaMethodInvokeExpr(mMethod,getName(),args);
    }

    public Method getMethod()
    {
        return mMethod;
    }

    protected final Class[] getParameterTypes()
    {
        return mMethod.getParameterTypes();
    }

    protected final String getMethodName()
    {
        return mMethod.getName();
    }

    protected final Class getJavaReturnType()
    {
        return mMethod.getReturnType();
    }

    protected boolean isStatic()
    {
        return Modifier.isStatic(mMethod.getModifiers());
    }

    public XType typeCheck(XType[] argsType, ExprContext context, EvalTypeInfo state, Expr functionCallExpr, Expr[] args) {
        return getReturnType();
    }
}
