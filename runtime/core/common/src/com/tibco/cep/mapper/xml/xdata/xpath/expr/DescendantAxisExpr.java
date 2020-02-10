package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;

/**
 */
public final class DescendantAxisExpr extends AxisExpr
{
    public DescendantAxisExpr(TextRange range, String axiswhitespace, String trailingWhitespace) {
        super(range,axiswhitespace,trailingWhitespace);
    }

    public boolean isMultiLevel() {
        return true;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_DESCENDANT_AXIS;
    }

    public boolean hasNonSubTreeTraversal() {
        return false;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
        return context.getInput().descendantAxis();
    }

    public String getAxisName() {
        return "descendant";
    }
}
