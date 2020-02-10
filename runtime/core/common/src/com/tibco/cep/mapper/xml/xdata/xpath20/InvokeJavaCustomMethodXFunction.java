/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath20;

import java.util.Calendar;
import java.util.List;

import com.tibco.be.util.BECustomFunctionHelper.ParamTypeInfo;
import com.tibco.be.util.FunctionInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.xmodel.xpath.type.XType;

public final class InvokeJavaCustomMethodXFunction extends InvokeJavaXFunction
{
    protected FunctionInfo          mInfo;

    public InvokeJavaCustomMethodXFunction(String namespace, FunctionInfo method)
    {
//    	super(method.getFnName(), convertClassToXType(method.getReturnType()), getParameterXTypes(method));
    	super(method.getFnName(), getReturnType(method), getArgTypes(method));
        mInfo = method;
    }

    private static XType getReturnType(FunctionInfo method) {
    	String clazzName = method.getReturnType().getTypeClassName();
    	Class<?> forName = primitiveToClass(clazzName);
    	return convertClassToXType(forName);
	}

	private static Class<?> primitiveToClass(java.lang.String typeName) {
		if ("int".equals(typeName)) {
			return int.class;
		} else if ("boolean".equals(typeName)) {
			return boolean.class;
		} else if ("double".equals(typeName)) {
			return double.class;
		} else if ("long".equals(typeName)) {
			return long.class;
		} else if ("String".equals(typeName)) {
			return String.class;
		} else if ("Object".equals(typeName)) {
			return Object.class;
		} else if ("DateTime".equals(typeName)) {
			return Calendar.class;
		} else if ("Calendar".equals(typeName)) { // also accept Calendar
			return Calendar.class;
		}
		return Void.class;
	}

	private static XType[] getArgTypes(FunctionInfo method) {
		List<ParamTypeInfo> paramTypes = method.getArgs();
		if (paramTypes == null) {
			return new XType[0];
		}
		XType[] types = new XType[paramTypes.size()];
		for (int i = 0; i < paramTypes.size(); i++) {
			String clazzName = paramTypes.get(i).getTypeClassName();
			Class<?> forName = primitiveToClass(clazzName);
			types[i] = convertClassToXType(forName);
		}
		return types;
	}

//	private static XType[] getParameterXTypes(Method method) {
//    	Class<?>[] parameterTypes = method.getParameterTypes();
//    	XType[] types = new XType[parameterTypes.length];
//    	for (int i=0; i<parameterTypes.length; i++) {
//    		types[i] = convertClassToXType(parameterTypes[i]);
//    	}
//		return types;
//	}

	public com.tibco.xml.xquery.Expr getAsExpr(com.tibco.xml.xquery.Expr[] args)
    {
        return null;//new JavaMethodInvokeExpr(mInfo,getName(),args);
    }

    public FunctionInfo getInfo()
    {
        return mInfo;
    }

    protected final Class[] getParameterTypes()
    {
		List<ParamTypeInfo> paramTypes = mInfo.getArgs();
		if (paramTypes == null) {
			return new Class[0];
		}
		Class[] types = new Class[paramTypes.size()];
		for (int i = 0; i < paramTypes.size(); i++) {
			String clazzName = paramTypes.get(i).getTypeClassName();
			Class<?> forName = primitiveToClass(clazzName);
			types[i] = forName;
		}
		return types;
    }

    protected final String getMethodName()
    {
        return mInfo.getFnName();
    }

    protected final Class getJavaReturnType()
    {
        return null;//mInfo.getReturnType();
    }

    protected boolean isStatic()
    {
        return true;//Modifier.isStatic(mInfo.getModifiers());
    }

    public XType typeCheck(XType[] argsType, ExprContext context, EvalTypeInfo state, Expr functionCallExpr, Expr[] args) {
        return getReturnType();
    }
}
