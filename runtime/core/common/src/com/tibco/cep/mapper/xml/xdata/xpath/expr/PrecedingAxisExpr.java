package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.type.XTypeAxisSupport;
import com.tibco.xml.schema.SmSequenceType;

public final class PrecedingAxisExpr extends AxisExpr
{
    public PrecedingAxisExpr(TextRange range, String axiswhitespace, String trailingWhitespace) {
        super(range, axiswhitespace, trailingWhitespace);
    }

    public boolean hasNonSubTreeTraversal()
    {
        return true;
    }

    public boolean isMultiLevel() {
        return true;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
        return XTypeAxisSupport.getPrecedingAxis(context.getInput());
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_PRECEDING_AXIS;
    }

    public String getAxisName() {
        return "preceding";
    }
}
