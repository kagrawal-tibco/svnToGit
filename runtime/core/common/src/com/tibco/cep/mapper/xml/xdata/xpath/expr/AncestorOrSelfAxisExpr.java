package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.type.XTypeAxisSupport;
import com.tibco.xml.schema.SmSequenceType;

public final class AncestorOrSelfAxisExpr extends AxisExpr
{
    public AncestorOrSelfAxisExpr(TextRange range, String axiswhitespace, String trailingWhitespace) {
        super(range, axiswhitespace,trailingWhitespace);
    }

    public boolean isMultiLevel() {
        return true;
    }

    public boolean hasNonSubTreeTraversal() {
        return true;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_ANCESTOR_OR_SELF_AXIS;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
        return XTypeAxisSupport.getAncestorOrSelfAxis(context.getInput());        
    }

    public String getAxisName() {
        return "ancestor-or-self";
    }
}
