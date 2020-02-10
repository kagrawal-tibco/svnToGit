package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

public final class ChildAxisExpr extends AxisExpr
{
    public ChildAxisExpr(TextRange range, String axiswhitespace, String trailingWhitespace) {
        super(range,axiswhitespace,trailingWhitespace);
    }

    public boolean isMultiLevel() {
        return false;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_CHILD_AXIS;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
        return context.getInput().childAxis();
    }

    public boolean hasNonSubTreeTraversal() {
        return false;
    }

    public ExprContext assertEvaluatesTo(ExprContext ec, SmSequenceType type, SmCardinality optionalSpecifiedCardinality)
    {
        SmSequenceType ca = ec.getInput().assertChildAxis(type);
        return ec.createWithInput(ca);
    }

    public String getAxisShortName() {
        // default, so nameless:
        return "";
    }

    public String getAxisName() {
        return "child";
    }

}
