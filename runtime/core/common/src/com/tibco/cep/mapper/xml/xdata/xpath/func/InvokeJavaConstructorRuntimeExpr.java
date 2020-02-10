/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.func;


public final class InvokeJavaConstructorRuntimeExpr 
{
    protected InvokeJavaConstructorXFunction mXFunction;
    protected Object                         mObject;

    public InvokeJavaConstructorRuntimeExpr(InvokeJavaConstructorXFunction function)
    {
        mXFunction = function;
    }

    public InvokeJavaXFunction getInvokeJavaXFunction()
    {
        return mXFunction;
    }
}
