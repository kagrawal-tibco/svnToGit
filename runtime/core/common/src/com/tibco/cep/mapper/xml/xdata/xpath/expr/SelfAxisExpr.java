package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

/**
 */
public final class SelfAxisExpr extends AxisExpr
{
    public SelfAxisExpr(TextRange range, String axiswhitespace, String trailingWhitespace) {
        super(range,axiswhitespace,trailingWhitespace);
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_SELF_AXIS;
    }

    public boolean isMultiLevel() {
        return false;
    }

    public boolean hasNonSubTreeTraversal() {
        return false;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
        return info.recordReturnType(this,context.getInput().selfAxis());
    }

    public ExprContext assertEvaluatesTo(ExprContext ec, SmSequenceType type, SmCardinality optionalSpecifiedCardinality)
    {
        // That's easy:
        return ec.createWithInput(type);
    }

    public String getAxisName() {
        return "self";
    }
}
