package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import org.w3c.dom.Node;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

public final class NamespaceAxisExpr extends AxisExpr
{
    public NamespaceAxisExpr(TextRange range, String axiswhitespace, String trailingWhitespace) {
        super(range,axiswhitespace,trailingWhitespace);
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_NAMESPACE_AXIS;
    }

    public boolean isMultiLevel() {
        return true;
    }

    public boolean hasNonSubTreeTraversal() {
        return true;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo state)
    {
        // not done yet.
        return SMDT.PREVIOUS_ERROR;
    }

    public int getPrincipalNodeType() {
        return Node.ATTRIBUTE_NODE; // hacky...
    }

    public String getAxisName() {
        return "namespace";
    }
}
