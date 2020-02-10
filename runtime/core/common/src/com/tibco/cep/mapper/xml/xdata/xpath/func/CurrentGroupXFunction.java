package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Xslt 2.0 'current-group' function.
 */
public final class CurrentGroupXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("current-group");

    public CurrentGroupXFunction()
    {
        super(NAME,SMDT.REPEATING_NODE);
    }

    public SmSequenceType typeCheck(SmSequenceType[] argsType, ExprContext context, EvalTypeInfo state, Expr functionCallExpr, Expr[] args)
    {
        // current-group is a magic function --- it operates off of information in the context:
        return context.getCurrentGroup();
    }
}
