package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;

public final class FollowingSiblingAxisExpr extends AxisExpr
{
    public FollowingSiblingAxisExpr(TextRange range, String axiswhitespace, String trailingWhitespace) {
        super(range,axiswhitespace,trailingWhitespace);
    }

    public boolean isMultiLevel() {
        return true;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_FOLLOWING_SIBLING_AXIS;
    }

    public boolean hasNonSubTreeTraversal() {
        return true;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
        return context.getInput().followingSiblingAxis();
    }

    public String getAxisName() {
        return "following-sibling";
    }
}
