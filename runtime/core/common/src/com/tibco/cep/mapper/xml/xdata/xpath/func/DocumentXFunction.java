package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Xslt 1.0 'document' function.
 */
public final class DocumentXFunction extends DefaultLastArgRequiredXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("document"); // xslt 1.0

    DocumentXFunction()
    {
        super(NAME,SMDT.REPEATING_NODE,SMDT.STRING);
    }

    public boolean hasNonSubTreeTraversal() 
    {
        return true;
    }

    public SmSequenceType typeCheck(SmSequenceType[] argsType, ExprContext context, EvalTypeInfo state, Expr functionCallExpr, Expr[] args)
    {
        return SMDT.PREVIOUS_ERROR; // WCETODO this makes it work for now, in a stronger type system, do something more intelligent.
    }
}
