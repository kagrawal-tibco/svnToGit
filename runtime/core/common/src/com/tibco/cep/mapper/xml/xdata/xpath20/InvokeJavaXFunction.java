/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath20;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Calendar;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.xmodel.schema.INativeTypeConsts;
import com.tibco.xml.xmodel.schema.NativeType;
import com.tibco.xml.xmodel.xpath.func.XFunction;
import com.tibco.xml.xmodel.xpath.type.SimpleXTypes;
import com.tibco.xml.xmodel.xpath.type.XType;
import com.tibco.xml.xmodel.xpath.type.XTypeFactory;

public abstract class InvokeJavaXFunction extends XFunction
{
    protected Class mClass;
    
    public InvokeJavaXFunction(String name, XType ret, XType[] args) {
		super(name, ret, args);
	}

    /*
     * XFunction interface implementation.
     */
    public XType getArgType(int argNum, int totalArgs)
    {
        Class params[] = this.getParameterTypes();
        if (argNum >= params.length || argNum < 0)
        {
            throw new ArrayIndexOutOfBoundsException(argNum);
        }
        return convertClassToXType(params[argNum]);
    }

    public int getMinimumNumArgs()
    {
        return getNumArgs();
    }

    public boolean getLastArgRepeats()
    {
        return false;
    }

    public ExpandedName getName()
    {
    	return ExpandedName.makeName(super.getFunctionName());
    }

//    public int getNumArgs()
//    {
//        Class params[] = this.getParameterTypes();
//        return params.length;
//    }
//
//    public XType getReturnType()
//    {
//        Class retClass = this.getJavaReturnType();
//        return convertClassToXType(retClass);
//    }

    public String[] getHelpStrings()
    {
        try
        {
            Field helpStringsField = mClass.getField("HELP_STRINGS");
            if (helpStringsField != null)
            {
                Object obj = helpStringsField.get(null); // its a static so it can be null.
                if (obj instanceof String[][])
                {
                    String methodName = this.getMethodName();
                    String[][] helpStrings = (String[][]) obj;
                    for (int i = 0; i < helpStrings.length; i++)
                    {
                        String[] methodNameStringPair = helpStrings[i];
                        if(methodNameStringPair != null && methodNameStringPair.length >= 2)
                        {
                            // Found the entry in the array which has the same name.
                            if (methodName.equals(methodNameStringPair[0]))
                            {
                                return methodNameStringPair;
                            }
                        }
                    }
                }
            }
        }
        catch (Throwable e) // not just exceptions, must be prepared for any classloading issue
        {
        }
        return null;
    }

    /**
     * Returns the drag string for the XFuction.
     */
    public String generateDragString(String suggestedPrefix)
    {
        XType type;
        int numArgs = this.getNumArgs();
        StringBuffer dragString = new StringBuffer();
        if (suggestedPrefix != null)
        {
            dragString.append(suggestedPrefix);
            dragString.append(":");
        }
        dragString.append(this.getName().getLocalName());
        dragString.append("(");
        if (numArgs > 0)
        {
            type = this.getArgType(0, numArgs);
            dragString.append("<< ");
            dragString.append(getDragName(type));
            dragString.append(" >>");
            for (int i=1; i < numArgs; i++)
            {
                type = this.getArgType(i, numArgs);
                dragString.append(", << ");
                dragString.append(getDragName(type));
                dragString.append(" >>");
            }
        }
        dragString.append(")");
//        return XMLNameUtilities.xmlStringFormat(dragString.toString());
        return dragString.toString();
    }

    private String getDragName(XType t) {
    	return "MyDragName";
//        return t.formatAsSequenceType(new NamespaceToPrefixResolver() {
//            public String getPrefixForNamespaceURI(String s) throws NamespaceToPrefixResolver.NamespaceNotFoundException {
//                return "";
//            }
//        });
    }

    public boolean hasNonSubTreeTraversal()
    {
        return false;
    }

    /**
     * Returns the paramter types as a class array. Just like Method.getParameterTypes()
     */
    protected abstract Class[] getParameterTypes();

    /**
     * Gets the name of the method/constructor.
     */
    protected abstract String getMethodName();

    /**
     * Returns the Java type which this function returns.
     */
    protected abstract Class getJavaReturnType();

    /**
     * Returns true if the method is static.
     */
    protected abstract boolean isStatic();

    /**
     * Returns true if the method is void.
     */
    protected boolean isVoid()
    {
        Class clazz = this.getJavaReturnType();
        if (Void.class.isAssignableFrom(clazz) || void.class.isAssignableFrom(clazz))
        {
            return true;
        }
        return false;
    }

    /**
     * Handles converting primitive class types and Node into an XType.
     */
    protected static XType convertClassToXType(Class clazz)
    {
        if (String.class.isAssignableFrom(clazz))
        {
        	try {
				return SimpleXTypes.STRING;
			} catch (Throwable e) {
			}
        	return XTypeFactory.getFactory().createAtomic(INativeTypeConsts.NativeString);
        }
        else if (Boolean.class.isAssignableFrom(clazz) || boolean.class.isAssignableFrom(clazz))
        {
        	try {
				return SimpleXTypes.BOOLEAN;
			} catch (Throwable e) {
			}
        	return XTypeFactory.getFactory().createAtomic(INativeTypeConsts.NativeBoolean);
        }
        else if (Integer.class.isAssignableFrom(clazz) || int.class.isAssignableFrom(clazz))
        {
        	try {
        		return SimpleXTypes.INTEGER;
        	} catch (Throwable e) {
        	}
        	return XTypeFactory.getFactory().createAtomic(INativeTypeConsts.NativeInteger);
        } 
        else if (Long.class.isAssignableFrom(clazz) || long.class.isAssignableFrom(clazz))
        {
        	try {
        		return SimpleXTypes.LONG;
        	} catch (Throwable e) {
        	}
        	return XTypeFactory.getFactory().createAtomic(new NativeType(INativeTypeConsts.Long, INativeTypeConsts.NativeDouble));
        }
        else if (Number.class.isAssignableFrom(clazz) ||
            byte.class.isAssignableFrom(clazz) ||
            short.class.isAssignableFrom(clazz) ||
            float.class.isAssignableFrom(clazz) ||
            double.class.isAssignableFrom(clazz) ||
            char.class.isAssignableFrom(clazz))
        {
        	try {
				return SimpleXTypes.DOUBLE;
			} catch (Throwable e) {
			}
        	return XTypeFactory.getFactory().createAtomic(INativeTypeConsts.NativeDouble);
//        	return SimpleXTypes.DOUBLE;
        }
        else if (Calendar.class.isAssignableFrom(clazz)) {
        	try {
				return SimpleXTypes.DATETIME;
			} catch (Throwable e) {
			}
        	return XTypeFactory.getFactory().createAtomic(new NativeType(INativeTypeConsts.DateTime, INativeTypeConsts.NativeAnyAtomic));
        }
/*        else if (NodeIterator.class.isAssignableFrom(clazz))
        {
            return SimpleXTypes.ANY_NODE;
        }*/
        else if (Void.class.isAssignableFrom(clazz) || void.class.isAssignableFrom(clazz))
        {
            return null;//?SMDT.VOID;
        }
//        else if (clazz.isAssignableFrom(Node.class))
//        {
//            throw new RuntimeException("LAMb: FINISH ME!");
//        }

        throw new RuntimeException("Unable to convert class " + clazz.getName() + " to an XType.");
    }

    /**
     * Indicates if this is a valid xpath function.<BR>.
     * WCETODO --- need to add a version which returns a why-not-reason to attach to the docs.
     * @param method The method.
     * @return If it is.
     */
    public static boolean isValidXPathFunction(Method method)
    {
        int methodMods = method.getModifiers();
        if (Modifier.isStatic(methodMods) && Modifier.isPublic(methodMods))
        {
            try
            {
                // Return type must be convertable.
                convertClassToXType(method.getReturnType());

                // All parameters must be convertable. This condition will change
                // when opaque objects are allowed.
                Class[] params = method.getParameterTypes();
                for (int i = 0; i < params.length; i++)
                {
                    Class param = params[i];
                    XType t = convertClassToXType(param);
                    if (t == null)
                    {
                        return false; // not ok.
                    }
                }

                // No exceptions are allowed to be tossed.
                if (method.getExceptionTypes().length == 0)
                {
                    return true;    // passed all tests.
                }
            }
            catch (RuntimeException ex)
            {
            }
        }
        return false;
    }

    public boolean isIndependentOfFocus(int numArgs) {
        return true;
    }

}
