package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;

public final class PrecedingSiblingAxisExpr extends AxisExpr
{
    public PrecedingSiblingAxisExpr(TextRange range, String axiswhitespace, String trailingWhitespace) {
        super(range, axiswhitespace, trailingWhitespace);
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_PRECEDING_SIBLING_AXIS;
    }

    public boolean hasNonSubTreeTraversal() {
        return true;
    }

    public boolean isMultiLevel() {
        return true;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
        return context.getInput().precedingSiblingAxis();
    }

    public String getAxisName() {
        return "preceding-sibling";
    }
}
