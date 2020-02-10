package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.type.XTypeAxisSupport;
import com.tibco.xml.schema.SmSequenceType;

/**
 */
public final class DescendantOrSelfAxisExpr extends AxisExpr
{
    public DescendantOrSelfAxisExpr(TextRange range, String axiswhitespace, String trailingWhitespace) {
        super(range,axiswhitespace,trailingWhitespace);
    }

    public boolean isMultiLevel() {
        return true;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
        return XTypeAxisSupport.getDescendantOrSelfAxis(context.getInput());
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_DESCENDANT_OR_SELF_AXIS;
    }

    public boolean hasNonSubTreeTraversal() {
        return false;
    }

    public String getAxisName() {
        return "descendant-or-self";
    }
}
