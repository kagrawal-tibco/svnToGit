package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;

/**
 */
public abstract class NodeTestExpr extends Expr
{
    protected NodeTestExpr(TextRange range, String trailingWhitespace) {
        super(range, trailingWhitespace);
    }

    public final Expr[] getChildren()
    {
        return NO_CHILDREN;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
        return context.getInput().nodeTest();
    }

    public abstract String getTestString();
}
