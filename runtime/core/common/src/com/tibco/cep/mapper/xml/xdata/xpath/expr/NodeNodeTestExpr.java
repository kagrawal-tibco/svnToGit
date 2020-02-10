package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;

/**
 */
public final class NodeNodeTestExpr extends NodeTestExpr
{
    public NodeNodeTestExpr(TextRange range, String trailingWhitespace) {
        super(range,trailingWhitespace);
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_NODE_NODE_TEST;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info) {
        // very simple:
        return context.getInput();
    }

    public ExprContext assertEvaluatesTo(ExprContext ec, SmSequenceType type, SmCardinality optionalSpecifiedCardinality)
    {
        return ec.createWithInput(type); // (Could do more...)
    }

    public ExprContext assertMatch(ExprContext ec)
    {
        // can't add anything (except that it is a node, but no method for that yet...)
        return ec;
    }

    public String getExprValue() {
        return null;
    }

    public String getTestString() {
        return "node()";
    }

    public void format(StringBuffer toBuffer, int style) {
        toBuffer.append("node()");
        if (style==STYLE_TO_EXACT_STRING) {
            toBuffer.append(getWhitespace());
        }
    }
}
