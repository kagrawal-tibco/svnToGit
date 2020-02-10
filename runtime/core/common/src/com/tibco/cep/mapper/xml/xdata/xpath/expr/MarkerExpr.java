package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class MarkerExpr extends Expr
{
    private final String mText;

    public MarkerExpr(String text, TextRange range, String trailingWhitespace) {
        super(range, trailingWhitespace);
        mText = text;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
        return SMDT.PREVIOUS_ERROR;
    }

    public String getExprValue() {
        return mText;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_MARKER;
    }

    public Expr[] getChildren() {
        return NO_CHILDREN;
    }

    public void format(StringBuffer toBuffer, int style) {
        boolean showMarker = style==STYLE_TO_DEBUG_STRING || style==STYLE_TO_UNABBREVIATED_STRING;
        if (showMarker) {
            toBuffer.append("<marker>");
        } else {
            toBuffer.append("<<");
        }
        toBuffer.append(mText);
        if (showMarker) {
            toBuffer.append("</marker>");
        } else {
            toBuffer.append(">>");
        }
        if (style==STYLE_TO_EXACT_STRING)
        {
            toBuffer.append(getWhitespace());
        }
    }
}
