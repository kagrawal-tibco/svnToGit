package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * For when the parser had an error, leaves this as a marker.
 */
public final class ErrorExpr extends Expr
{
    private String mActualString;
    private String mErrorMessage;
    private Expr m_optionalChild;

    private static final Expr[] EMPTY_ARRAY = new Expr[0];

    public ErrorExpr(TextRange range, String errMsg, String actualString, String trailingWhitespace)
    {
        super(range,trailingWhitespace);
        mActualString = actualString;
        mErrorMessage = errMsg;
        m_optionalChild = null;
    }

    public ErrorExpr(TextRange range, String errMsg, String actualString, String trailingWhitespace, Expr optionalChild)
    {
        super(range,trailingWhitespace);
        mActualString = actualString;
        mErrorMessage = errMsg;
        m_optionalChild = optionalChild;
    }

    public boolean isMultiLevel() {
        return false;
    }

    public boolean hasNonSubTreeTraversal() {
        return false;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo state)
    {
        // (Will need to record error here, too, however currently the lexer does that, add this when that is switched off)
        // but return previous error.
        
        if (m_optionalChild!=null)
        {
            // Forward here,
            return m_optionalChild.evalType(context,state);
        }
        return SMDT.PREVIOUS_ERROR;
    }

    public Expr[] getChildren()
    {
        if (m_optionalChild==null)
        {
            return EMPTY_ARRAY;
        }
        return new Expr[] {m_optionalChild};
    }

    public String getExprValue() {
        return mActualString;
    }

    public void format(StringBuffer toBuffer, int style) {
        if (style==STYLE_TO_DEBUG_STRING) {
            toBuffer.append('(');
        }
        toBuffer.append(mActualString);
        if (style==STYLE_TO_DEBUG_STRING) {
            toBuffer.append(')');
        }
        if (style==STYLE_TO_EXACT_STRING)
        {
            toBuffer.append(getWhitespace());
        }
    }

    public String getRepresentationClosure() {
        return mErrorMessage;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_ERROR;
    }
}
