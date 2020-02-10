package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;

public final class CommentNodeTestExpr extends NodeTestExpr
{
    public CommentNodeTestExpr(TextRange range, String trailingWhitespace) {
        super(range, trailingWhitespace);
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_COMMENT_NODE_TEST;
    }

    public String getExprValue() {
        return null;
    }

    public String getTestString() {
        return "comment()";
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo state) {
        return context.getInput().commentTest();
    }

    public void format(StringBuffer toBuffer, int style) {
        toBuffer.append("comment()");
        if (style==STYLE_TO_EXACT_STRING) {
            toBuffer.append(getWhitespace());
        }
    }
}
