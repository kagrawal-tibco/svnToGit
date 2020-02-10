/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.func;

import java.lang.reflect.Method;

public final class InvokeJavaMethodRuntimeExpr
{
    protected InvokeJavaMethodXFunction mXFunction;
    protected Object                    mObject;

    public InvokeJavaXFunction getInvokeJavaXFunction()
    {
        return mXFunction;
    }

    protected final Method getJavaMethod()
    {
        return mXFunction.getMethod();
    }
}
