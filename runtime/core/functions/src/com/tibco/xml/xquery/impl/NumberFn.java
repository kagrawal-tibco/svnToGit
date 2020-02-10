package com.tibco.xml.xquery.impl;

import com.tibco.xml.data.primitive.UT;
import com.tibco.xml.data.primitive.XmlAtomicValue;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.data.primitive.XmlAtomicValueParseException;
import com.tibco.xml.data.primitive.XmlItem;
import com.tibco.xml.data.primitive.XmlNodeTest;
import com.tibco.xml.data.primitive.XmlSequence;
import com.tibco.xml.data.primitive.XmlTreeNode;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.XmlUberType;
import com.tibco.xml.data.primitive.values.XsByte;
import com.tibco.xml.data.primitive.values.XsDecimal;
import com.tibco.xml.data.primitive.values.XsDouble;
import com.tibco.xml.data.primitive.values.XsFloat;
import com.tibco.xml.data.primitive.values.XsInt;
import com.tibco.xml.data.primitive.values.XsInteger;
import com.tibco.xml.data.primitive.values.XsLong;
import com.tibco.xml.data.primitive.values.XsNumber;
import com.tibco.xml.data.primitive.values.XsShort;

/**
 * The number() function converts its arguments to a number.
 * <p/>
 * A Number is one of the allowed data types for the value of an XPath expression. In XPath 1.0
 * it is a floating point number as defined by IEEE 754. In XPath 2.0 it is any one of the numeric
 * XML Schema datatypes.
 *
 * @author David Holmes
 */
public class NumberFn
{
	private static String USE_BIG_DECIMAL_PROP = "com.tibco.xml.xpath.function.NumberFunction.StringAsDecimal";

	public static XsNumber evaluate(XmlSequence srcval, XmlNodeTest view, boolean isLegacyMode)
    {
        if (srcval.isEmpty())
        {
            return XsDouble.NaN;
        }
        else if (srcval.isNodeSet())
        {
            try
            {
                return evaluate(StringFn.evaluate(srcval, isLegacyMode, view, null));
            }
            catch (RuntimeException e)
            {
                return XsDouble.NaN;
            }
        }
        else if (srcval.isAtomSet())
        {
            XmlItem item = srcval.getItem(0);

            return evaluateItem(item, view, isLegacyMode);
        }
        else
        {
            // Heterogenous srcval?
            throw new RuntimeException();
        }
    }

    private static XsNumber evaluateItem(XmlItem item, XmlNodeTest view, boolean isLegacyMode)
    {
        if (item instanceof XmlTreeNode)
        {
            XmlTreeNode node = (XmlTreeNode)item;

            return evaluateNode(node, view, isLegacyMode);
        }
        else
        {
            XmlAtomicValue atom = (XmlAtomicValue)item;

            return evaluateAtom(atom, isLegacyMode);
        }
    }

    private static XsNumber evaluateNode(final XmlTreeNode node, final XmlNodeTest view, final boolean isLegacyMode)
    {
        final XmlTypedValue value = AtomizeFn.evaluate(node, view, isLegacyMode);

        if (value.isEmpty())
        {
            return XsDouble.NaN;
        }
        else if (value.isSingleton())
        {
            final XmlAtomicValue atom = value.getAtom(0);

            return evaluateAtom(atom, isLegacyMode);
        }
        else
        {
            // The string value would never parse because of the spaces.
            return XsDouble.NaN;
        }
    }

    private static XsNumber evaluateAtom(XmlAtomicValue atom, boolean isLegacyMode)
    {
        XmlUberType type = atom.getUberType();

        // Preformance: Check exact types first before checking dervied types.
        if (UT.DOUBLE == type)
        {
            if (atom instanceof XsDouble)
            {
                return (XsDouble)atom;
            }
            else
            {
                try
                {
                    return XsDouble.makeDouble(atom.castAsDouble());
                }
                catch (XmlAtomicValueCastException e)
                {
                    return XsDouble.NaN;
                }
            }
        }
        else if (type.isA(UT.BYTE))
        {
            if (atom instanceof XsByte)
            {
                return (XsByte)atom;
            }
            else
            {
                try
                {
                    return new XsByte(atom.castAsByte());
                }
                catch (XmlAtomicValueCastException e)
                {
                    return XsDouble.NaN;
                }
            }
        }
        else if (type.isA(UT.SHORT))
        {
            if (atom instanceof XsShort)
            {
                return (XsShort)atom;
            }
            else
            {
                try
                {
                    return new XsShort(atom.castAsShort());
                }
                catch (XmlAtomicValueCastException e)
                {
                    return XsDouble.NaN;
                }
            }
        }
        else if (type.isA(UT.INT))
        {
            if (atom instanceof XsInt)
            {
                return (XsInt)atom;
            }
            else
            {
                try
                {
                    return new XsInt(atom.castAsInt());
                }
                catch (XmlAtomicValueCastException e)
                {
                    return XsDouble.NaN;
                }
            }
        }
        else if (type.isA(UT.LONG))
        {
            if (atom instanceof XsLong)
            {
                return (XsLong)atom;
            }
            else
            {
                try
                {
                    return new XsLong(atom.castAsLong());
                }
                catch (XmlAtomicValueCastException e)
                {
                    return XsDouble.NaN;
                }
            }
        }
        else if (type.isA(UT.INTEGER))
        {
            if (atom instanceof XsInteger)
            {
                return (XsInteger)atom;
            }
            else
            {
                try
                {
                    return new XsInteger(atom.castAsInteger());
                }
                catch (XmlAtomicValueCastException e)
                {
                    return XsDouble.NaN;
                }
            }
        }
        else if (type.isA(UT.DECIMAL))
        {
            if (atom instanceof XsDecimal)
            {
                return (XsDecimal)atom;
            }
            else
            {
                try
                {
                    return new XsDecimal(atom.castAsDecimal());
                }
                catch (XmlAtomicValueCastException e)
                {
                    return XsDouble.NaN;
                }
            }
        }
        else if (type.isA(UT.DOUBLE))
        {
            if (atom instanceof XsDouble)
            {
                return (XsDouble)atom;
            }
            else
            {
                try
                {
                    return XsDouble.makeDouble(atom.castAsDouble());
                }
                catch (XmlAtomicValueCastException e)
                {
                    return XsDouble.NaN;
                }
            }
        }
        else if (type.isA(UT.FLOAT))
        {
            if (atom instanceof XsFloat)
            {
                return (XsFloat)atom;
            }
            else
            {
                try
                {
                    return new XsFloat(atom.castAsFloat());
                }
                catch (XmlAtomicValueCastException e)
                {
                    return XsDouble.NaN;
                }
            }
        }
        else if (type.isA(UT.BOOLEAN))
        {
            try
            {
                if (isLegacyMode)
                {
                    return atom.castAsBoolean() ? XsDouble.ONE : XsDouble.ZERO;
                }
                else
                {
                    return atom.castAsNumber();
                }
            }
            catch (XmlAtomicValueCastException e)
            {
                return XsDouble.NaN;
            }
        }
        else if (type.isA(UT.STRING))
        {
            return evaluate(atom.castAsString());
        }
        else
        {
            return evaluate(atom.castAsString());
        }
    }

    private static XsNumber evaluate(String srcval)
    {
        try
        {
        	// check every time to allow for the case where the user wants to treat a string as a
        	// decimal in some cases, but not all
        	if (Boolean.getBoolean(USE_BIG_DECIMAL_PROP)) {
        		return XsDecimal.compile(srcval);
        	} 
        	return XsDouble.compile(srcval);
        }
        catch (XmlAtomicValueParseException e)
        {
            return XsDouble.NaN;
        }
    }
}
