package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;

/**
 */
public final class ParenExpr extends Expr
{
    private final Expr mExpr;
    private final String mLeftWhitespace;

    public ParenExpr(Expr expr, TextRange range, String leftTrailingwhitepace, String rightTrailingWhitespace) {
        super(range, rightTrailingWhitespace); // use '-' to separate whitespace pieces.
        mExpr = expr;
        if (leftTrailingwhitepace==null) {
            mLeftWhitespace = "";
        } else {
            mLeftWhitespace = leftTrailingwhitepace;
        }
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info) {
        // nice & easy!
        SmSequenceType ret = mExpr.evalType(context,info);
        return info.recordReturnType(this,ret);
    }

    public String getRepresentationClosure() {
        return mLeftWhitespace;
    }

    public String getExprValue() {
        return null;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_PAREN;
    }

    public Expr[] getChildren() {
        return new Expr[] {mExpr};
    }

    public void format(StringBuffer toBuffer, int style) {
        boolean showp = style==STYLE_TO_UNABBREVIATED_STRING || style==STYLE_TO_DEBUG_STRING;
        boolean isExact = style==STYLE_TO_EXACT_STRING;
        if (showp) {
            toBuffer.append("<lparen>");
        } else {
            toBuffer.append('(');
        }
        if (isExact) {
            toBuffer.append(mLeftWhitespace);
        }
        mExpr.format(toBuffer,style);
        if (showp) {
            toBuffer.append("<rparen>");
        } else {
            toBuffer.append(')');
        }
        if (isExact) {
            toBuffer.append(getWhitespace());
        }        
    }
}
