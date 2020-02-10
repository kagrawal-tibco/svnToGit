package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public abstract class AbstractNameXFunction extends DefaultLastArgOptionalXFunction
{
    public AbstractNameXFunction(ExpandedName name, SmSequenceType retType)
    {
        super(name,retType,SMDT.REPEATING_NODE);
    }

    public final boolean isIndependentOfFocus(int numArgs)
    {
        if (numArgs==0)
        {
            return false; // implicit '.' if there are no args.
        }
        return true;
    }
    
    public SmSequenceType typeCheck(SmSequenceType[] argsType, ExprContext context, EvalTypeInfo state, Expr functionCallExpr, Expr[] args)
    {
        // WCETODO could check if it's a constant or non-sense (non-element/attribute)...
        return getReturnType();
    }
}
