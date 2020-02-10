package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * A paren with nothing in it; () for XPath 2.0
 */
public final class EmptySequenceExpr extends Expr
{
    public EmptySequenceExpr(TextRange range, String whitespace) {
        super(range, whitespace);
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
        // nice & easy!
        SmSequenceType ret = SMDT.VOID;
        return info.recordReturnType(this,ret);
    }

    public String getRepresentationClosure() {
        return null;
    }

    public String getExprValue() {
        return null;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_EMPTY_SEQUENCE;
    }

    public Expr[] getChildren()
    {
        return NO_CHILDREN;
    }

    public void format(StringBuffer toBuffer, int style) {
        boolean isExact = style==STYLE_TO_EXACT_STRING;
        toBuffer.append("(");
        if (isExact)
        {
            toBuffer.append(getWhitespace());
        }
        toBuffer.append(")");
    }
}
