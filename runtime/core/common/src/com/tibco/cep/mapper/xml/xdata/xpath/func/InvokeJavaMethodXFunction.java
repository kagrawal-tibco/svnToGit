/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.func;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.xquery.extensions.JavaMethodInvokeExpr;

public final class InvokeJavaMethodXFunction extends InvokeJavaXFunction
{
    protected Method          mMethod;

    public InvokeJavaMethodXFunction(String namespace, Method method)
    {
        super(method.getDeclaringClass(),ExpandedName.makeName(namespace,method.getName()));
        mMethod = method;
    }

    public com.tibco.xml.xquery.Expr getAsExpr(com.tibco.xml.xquery.Expr[] args)
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

    public SmSequenceType typeCheck(SmSequenceType[] argsType, ExprContext context, EvalTypeInfo state, Expr functionCallExpr, Expr[] args) {
        return getReturnType();
    }
}
