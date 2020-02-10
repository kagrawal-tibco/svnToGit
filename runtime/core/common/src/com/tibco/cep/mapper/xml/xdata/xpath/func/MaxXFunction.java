package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 */
public final class MaxXFunction extends AbstractNumericGroupXFunction
{
    public static final ExpandedName NAME = ExpandedName.makeName(TibXPath20Functions.NAMESPACE, "max");

    /**
     * For FunctionList
     */
    MaxXFunction()
    {
        super(NAME);
    }
}
