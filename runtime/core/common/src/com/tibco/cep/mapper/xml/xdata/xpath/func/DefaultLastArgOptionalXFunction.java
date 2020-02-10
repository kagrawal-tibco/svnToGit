package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;

/**
 * Default implementation of {@link XFunction} where the last argument is optional.<br>
 * Should be sufficient for most, but not all, functions to override.
 */
public class DefaultLastArgOptionalXFunction extends DefaultXFunction
{
    public DefaultLastArgOptionalXFunction(ExpandedName name, SmSequenceType ret, SmSequenceType[] args)
    {
        super(name,ret,args);
        if (args.length==0)
        {
            throw new IllegalArgumentException("Last arg optional must have at least 1 arg");
        }
    }

    public DefaultLastArgOptionalXFunction(ExpandedName name, SmSequenceType ret, SmSequenceType singleArgs)
    {
        super(name,ret,singleArgs);
    }

    public DefaultLastArgOptionalXFunction(ExpandedName name, SmSequenceType ret, SmSequenceType firstArg, SmSequenceType secondArg)
    {
        super(name,ret,firstArg,secondArg);
    }

    public DefaultLastArgOptionalXFunction(ExpandedName name, SmSequenceType ret, SmSequenceType firstArg, SmSequenceType secondArg, SmSequenceType thirdArg)
    {
        super(name,ret,firstArg,secondArg,thirdArg);
    }

    public final boolean getLastArgRepeats()
    {
        return false;
    }

    public final int getMinimumNumArgs()
    {
        return getNumArgs()-1;
    }
}
