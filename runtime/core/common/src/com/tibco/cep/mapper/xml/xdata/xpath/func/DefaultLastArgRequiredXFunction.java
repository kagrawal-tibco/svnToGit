package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;

/**
 * Default implementation of {@link XFunction} where the last argument is required and does not repeat.<br>
 * Should be sufficient for most, but not all, functions to override.
 */
public class DefaultLastArgRequiredXFunction extends DefaultXFunction
{
    /**
     * No arguments constructor.
     */
    public DefaultLastArgRequiredXFunction(ExpandedName name, SmSequenceType ret)
    {
        super(name,ret);
    }

    public DefaultLastArgRequiredXFunction(ExpandedName name, SmSequenceType ret, SmSequenceType[] args)
    {
        super(name,ret,args);
    }

    public DefaultLastArgRequiredXFunction(ExpandedName name, SmSequenceType ret, SmSequenceType singleArg)
    {
        super(name,ret,singleArg);
    }

    public DefaultLastArgRequiredXFunction(ExpandedName name, SmSequenceType ret, SmSequenceType firstArg, SmSequenceType secondArg)
    {
        super(name,ret,firstArg,secondArg);
    }

    public DefaultLastArgRequiredXFunction(ExpandedName name, SmSequenceType ret, SmSequenceType firstArg, SmSequenceType secondArg, SmSequenceType thirdArg)
    {
        super(name,ret,firstArg,secondArg,thirdArg);
    }

    public final boolean getLastArgRepeats()
    {
        return false;
    }

    public final int getMinimumNumArgs()
    {
        return getNumArgs();
    }
}
