package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

/**
 */
public final class AttributeAxisExpr extends AxisExpr
{
    public AttributeAxisExpr(TextRange range, String axiswhitespace, String trailingWhitespace) {
        super(range,axiswhitespace,trailingWhitespace);
    }

    public boolean isMultiLevel() {
        return false;
    }

    public int getPrincipalNodeType()
    {
        return org.w3c.dom.Node.ATTRIBUTE_NODE;
    }

    public boolean hasNonSubTreeTraversal() {
        return false;
    }

    public ExprContext assertEvaluatesTo(ExprContext ec, SmSequenceType type, SmCardinality optionalSpecifiedCardinality)
    {
        SmSequenceType ca = ec.getInput().assertAttributeAxis(type);
        return ec.createWithInput(ca);
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
        return context.getInput().attributeAxis();
    }


    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_ATTRIBUTE_AXIS;
    }

    public String getAxisName() {
        return "attribute";
    }
}
