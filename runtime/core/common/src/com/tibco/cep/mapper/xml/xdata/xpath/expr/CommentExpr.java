package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;

public final class CommentExpr extends Expr
{
    private final String mCommentText;
    private final boolean mIsLeft;
    private final Expr mContained;

    public CommentExpr(String comment, Expr contained, boolean isLeft, TextRange range, String trailingWhitespace) {
        super(range, trailingWhitespace);
        mIsLeft = isLeft;
        mContained = contained;
        mCommentText = comment;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info) {
        SmSequenceType t = mContained.evalType(context,info);
        return info.recordReturnType(this,t);
    }

    public String getExprValue() {
        return mCommentText;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_COMMENT;
    }

    public Expr[] getChildren() {
        return new Expr[] {mContained};
    }

    public String getRepresentationClosure() {
        return mIsLeft ? "left" : "right";
    }

    public void format(StringBuffer toBuffer, int style) {
        if (mIsLeft) {
            toBuffer.append(mCommentText);
            boolean isExact = style==STYLE_TO_EXACT_STRING;
            if (isExact) {
                toBuffer.append(getWhitespace());
            }
            mContained.format(toBuffer,style);
        } else {
            boolean isExact = style==STYLE_TO_EXACT_STRING;
            if (isExact) {
                toBuffer.append(getWhitespace());
            }
            mContained.format(toBuffer,style);
            toBuffer.append(mCommentText);
        }
    }
}
