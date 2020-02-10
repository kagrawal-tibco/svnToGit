package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.type.XTypeAxisSupport;
import com.tibco.xml.schema.SmSequenceType;

public final class FollowingAxisExpr extends AxisExpr
{
    public FollowingAxisExpr(TextRange range, String axiswhitespace, String trailingWhitespace) {
        super(range,axiswhitespace,trailingWhitespace);
    }

    public boolean isMultiLevel() {
        return true;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_FOLLOWING_AXIS;
    }

    public boolean hasNonSubTreeTraversal() {
        return true;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
        return XTypeAxisSupport.getFollowingAxis(context.getInput());
    }

    public String getAxisName() {
        return "following";
    }
}
