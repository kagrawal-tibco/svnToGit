package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 */
public final class MinXFunction extends AbstractNumericGroupXFunction
{
    public static final ExpandedName NAME = ExpandedName.makeName(TibXPath20Functions.NAMESPACE, "min");

    /**
     * For FunctionList
     */
    MinXFunction()
    {
        super(NAME);
    }
}
