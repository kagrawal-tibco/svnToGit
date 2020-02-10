/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.func;

import java.lang.reflect.Constructor;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;

public final class InvokeJavaConstructorXFunction extends InvokeJavaXFunction
{
    protected transient Constructor mConstructor;

    public InvokeJavaConstructorXFunction(String namespace, Constructor constructor)
    {
        super(constructor.getClass(),ExpandedName.makeName(namespace,constructor.getName()));
        mConstructor = constructor;
    }

    public Constructor getConstructor()
    {
        return mConstructor;
    }

    protected final Class[] getParameterTypes()
    {
        return mConstructor.getParameterTypes();
    }

    protected final String getMethodName()
    {
        return mConstructor.getName();
    }

    protected final Class getJavaReturnType()
    {
        return mConstructor.getClass();
    }

    protected boolean isStatic()
    {
        return true;    // LAMb: should this be static? Probably, since it does not require an object parameter.
    }

    public SmSequenceType typeCheck(SmSequenceType[] argsType, ExprContext context, EvalTypeInfo state, Expr functionCallExpr, Expr[] args) {
        return getReturnType();
    }
}
