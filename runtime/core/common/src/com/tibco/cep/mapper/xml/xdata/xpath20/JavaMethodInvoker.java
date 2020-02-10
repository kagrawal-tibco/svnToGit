package com.tibco.cep.mapper.xml.xdata.xpath20;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.genxdm.typed.TypedContext;
import org.genxdm.xpath.v10.ExprException;
import org.genxdm.xpath.v20.expressions.DynamicContext;
import org.genxdm.xpath.v20.expressions.Expr;
import org.genxdm.xpath.v20.expressions.Focus;

import com.tibco.be.model.types.ConversionException;
import com.tibco.be.model.types.TypeConverter;
import com.tibco.be.model.types.TypeRenderer;
import com.tibco.be.model.types.xsd.XSDTypeRegistry;
import com.tibco.be.util.XiSupport;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValue;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.helpers.XmlSequenceSupport;
import com.tibco.xml.data.primitive.values.XsDateTime;
import com.tibco.xml.data.primitive.values.XsDouble;
import com.tibco.xml.datamodel.XiFactory;

/**
 * Provides stateless invoke() method of a Java method.
 */
final class JavaMethodInvoker
{
    static XSDTypeRegistry registry = XSDTypeRegistry.getInstance();
    static TypeConverter xsd2java_dt_conv= registry.nativeToForeign(XsDateTime.class,  GregorianCalendar.class);
    static TypeRenderer java2xsd_dt_conv= registry.foreignToNative(XsDateTime.class, GregorianCalendar.class);
    
    private JavaMethodInvoker()
    {
    }

    public static <N, A> N invoke(Focus<N, A> focus, DynamicContext<N, A> dynContext,
			TypedContext<N, A> typedContext, final Method m, final Expr[] args) throws ExprException {

        final Class[] parameterTypes = m.getParameterTypes();

        final Object[] methodArgs = new Object[args.length];

        for (int i = 0; i < args.length; i++)
        {
            final Object[] methodArg = evaluateToType(args[i], focus, dynContext, typedContext, parameterTypes[i]);

            methodArgs[i] = methodArg[0];
        }

        try
        {
            final Object retval = m.invoke(null, methodArgs);

            // Must special case null because an item cannot be the empty sequence.
            if (null != retval)
            {
                return getItemFromObject(retval, dynContext);
            }
            else
            {
                return null;//ItemSequence.EMPTY;
            }
        }
        catch (final IllegalAccessException e)
        {
            throw new ExprException("IllegalAccessException calling method " + m.getName());//, e);
        }
        catch (final InvocationTargetException e)
        {
            throw new ExprException("InvocationTargetException calling method " + m.getName());//, e);
        }
        catch (final IllegalArgumentException e)
        {
            throw new ExprException("IllegalArgumentException calling method " + m.getName());//, e);
        }
        catch (final RuntimeException e)
        {
            throw new ExprException("RuntimeException calling method " + m.getName());//, e);
        }
    }
    
    private static <N, A> N getItemFromObject(final Object object, DynamicContext<N, A> dynContext) throws ExprException
    {
    	XiFactory xiFactory = XiSupport.getXiFactory();
        if (dynContext.isCompatibleMode())
        {
        	
            // In XPath 1.0, all numbers are of type double.
            if (object instanceof Number)
            { 
                return (N) xiFactory.createElement(ExpandedName.NAME, XsDouble.makeDouble(((Number)object).doubleValue()));
            }
            else
            {
                return (N) xiFactory.createElement(ExpandedName.NAME, XmlSequenceSupport.getItemFromObject(object).getTypedValue());
            }
        }
        else
        {
        	if (object instanceof Calendar) {
        		// special case for DateTimes, since XmlSequenceSupport does not know how to handle them
        		try {
        			XmlTypedValue typedVal = java2xsd_dt_conv.convertToTypedValue(object);
        			return (N) xiFactory.createElement(ExpandedName.NAME, typedVal);
        		} catch (ConversionException e) {
        	        throw new ExprException(e);
        		}
        	}
            return (N) xiFactory.createElement(ExpandedName.NAME, XmlSequenceSupport.getItemFromObject(object).getTypedValue());
        }
    }

    private static <N, A> Object[] evaluateToType(final Expr expr, Focus<N, A> focus, DynamicContext<N, A> dynContext,
			TypedContext<N, A> typedContext, final Class type) throws ExprException
    {
        if (String.class.isAssignableFrom(type))
        {
            return makeArray(expr.stringValue(focus, dynContext, typedContext));
        }
        else if (Boolean.class.isAssignableFrom(type) || boolean.class.isAssignableFrom(type))
        {
            try
            {
                return makeArray(expr.booleanValue(focus, dynContext, typedContext) ? Boolean.TRUE : Boolean.FALSE);
            }
            catch (NullPointerException e)
            {
                return makeArray(null);
            }
        }
        else if (Double.class.isAssignableFrom(type) || double.class.isAssignableFrom(type))
        {
            return makeArray(new Double(expr.doubleValue(focus, dynContext, typedContext)));
        }
        else if (BigDecimal.class.isAssignableFrom(type))
        {
            BigDecimal result = new BigDecimal(expr.stringValue(focus, dynContext, typedContext));
            return makeArray(result);
        }
        else if (BigInteger.class.isAssignableFrom(type))
        {
            BigInteger result = new BigInteger(expr.stringValue(focus, dynContext, typedContext));
            return makeArray(result);
        }
        else if (Integer.class.isAssignableFrom(type) || int.class.isAssignableFrom(type))
        {
            Double result = new Double(expr.doubleValue(focus, dynContext, typedContext));
            checkIntegerBounds(result);
            return makeArray(new Integer(result.intValue()));
        }
        else if (Float.class.isAssignableFrom(type) || float.class.isAssignableFrom(type))
        {
            Double result = new Double(expr.doubleValue(focus, dynContext, typedContext));
            checkFloatBounds(result);
            return makeArray(new Float(result.floatValue()));
        }
        else if (Long.class.isAssignableFrom(type) || long.class.isAssignableFrom(type))
        {
            Double result = new Double(expr.doubleValue(focus, dynContext, typedContext));
            checkLongBounds(result);
            return makeArray(new Long(result.longValue()));
        }
        else if (Short.class.isAssignableFrom(type) || short.class.isAssignableFrom(type))
        {
            Double result = new Double(expr.doubleValue(focus, dynContext, typedContext));
            checkShortBounds(result);
            return makeArray(new Short(result.shortValue()));
        }
        else if (Byte.class.isAssignableFrom(type) || byte.class.isAssignableFrom(type))
        {
            Double result = new Double(expr.doubleValue(focus, dynContext, typedContext));
            checkByteBounds(result);
            return makeArray(new Byte(result.byteValue()));
        }
        else if (Character.class.isAssignableFrom(type) || char.class.isAssignableFrom(type))
        {
            String str = expr.stringValue(focus, dynContext, typedContext);
            return makeArray(new Character(str.charAt(0)));
        }
        else if (Calendar.class.isAssignableFrom(type))
        {
        	XmlAtomicValue atom = (XmlAtomicValue) expr.atom(focus, dynContext, typedContext);
    		try {
    			if (atom == null) {
    				return makeArray(null);
    			}
    			Calendar c = (Calendar) xsd2java_dt_conv.convertSimpleType(atom.castAsDateTime());
    			return makeArray(c);
    		} catch (XmlAtomicValueCastException e) {
    	        throw new ExprException(e);
    		} catch (ConversionException e) {
    	        throw new ExprException(e);
    		}

        }
        throw new ExprException("Conversion to Java type '" + type.getName() + "' is not available.");
    }

    private static Object[] makeArray(final Object object)
    {
        final Object[] objects = new Object[1];

        objects[0] = object;

        return objects;
    }

    static void checkIntegerBounds(final Double value) throws ExprException
    {
        if (MAX_INTEGER.compareTo(value) < 0 || MIN_INTEGER.compareTo(value) > 0)
        {
            throw new ExprException("integer overflow: " + value);
        }
    }

    static void checkShortBounds(final Double value) throws ExprException
    {
        if (MAX_SHORT.compareTo(value) < 0 || MIN_SHORT.compareTo(value) > 0)
        {
            throw new ExprException("short overflow: " + value);
        }
    }

    static void checkLongBounds(final Double value) throws ExprException
    {
        if (MAX_LONG.compareTo(value) < 0 || MIN_LONG.compareTo(value) > 0)
        {
            throw new ExprException("long overflow: " + value);
        }
    }

    static void checkByteBounds(final Double value) throws ExprException
    {
        if (MAX_BYTE.compareTo(value) < 0 || MIN_BYTE.compareTo(value) > 0)
        {
            throw new ExprException("byte overflow: " + value);
        }
    }

    static void checkFloatBounds(final Double value) throws ExprException
    {
        if (MAX_FLOAT.compareTo(value) < 0 || MIN_FLOAT.compareTo(value) > 0)
        {
            throw new ExprException("float overflow: " + value);
        }
    }

    private static final Double MAX_INTEGER = new Double(Integer.MAX_VALUE);
    private static final Double MIN_INTEGER = new Double(Integer.MIN_VALUE);
    private static final Double MAX_LONG = new Double(Long.MAX_VALUE);
    private static final Double MIN_LONG = new Double(Long.MIN_VALUE);
    private static final Double MAX_SHORT = new Double(Short.MAX_VALUE);
    private static final Double MIN_SHORT = new Double(Short.MIN_VALUE);
    private static final Double MAX_BYTE = new Double(Byte.MAX_VALUE);
    private static final Double MIN_BYTE = new Double(Byte.MIN_VALUE);
    private static final Double MAX_FLOAT = new Double(Float.MAX_VALUE);

    /**
     * Beware, the {@link Float#MIN_VALUE} is small and positive!
     * That is not what we want for range checking so we contrive a new constant...
     */
    private static final Double MIN_FLOAT = new Double(MAX_FLOAT.doubleValue() * -1);
}
