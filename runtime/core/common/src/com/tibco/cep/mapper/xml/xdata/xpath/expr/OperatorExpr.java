package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public abstract class OperatorExpr extends Expr
{
    protected final Expr mLeft;
    protected final Expr mRight;

    public OperatorExpr(Expr left, Expr right, TextRange range, String whitespace) {
        super(range, whitespace);
        mLeft = left;
        mRight = right;
    }

    public final String getExprValue() {
        return null;
    }

    public final SmSequenceType evalType(ExprContext context, EvalTypeInfo info) {
        boolean hasCC = info.getCodeComplete()!=null;
        SmSequenceType prev = info.getCodeCompleteReturnType();
        info.setCodeCompleteReturnType(getLeftArgType());
        SmSequenceType lxt = mLeft.evalType(context,info);
        info.setCodeCompleteReturnType(getRightArgType());
        SmSequenceType rxt = mRight.evalType(context,info);
        SmSequenceType ret = evalOpType(lxt,rxt,context,info);

        info.setCodeCompleteReturnType(prev);
        return ret;
    }

    public SmSequenceType getLeftArgType() {return SMDT.REPEATING_NODE;}

    public SmSequenceType getRightArgType() {return SMDT.REPEATING_NODE;}

    public abstract SmSequenceType evalOpType(SmSequenceType lhs, SmSequenceType rhs, ExprContext context, EvalTypeInfo info);

    public final Expr[] getChildren() {
        return new Expr[] {mLeft,mRight};
    }

    public abstract String getOperatorName();

    public final void format(StringBuffer toBuffer, int style) {
        if (style==STYLE_TO_DEBUG_STRING) {
            toBuffer.append('(');
        }
        mLeft.format(toBuffer,style);
        if (style!=STYLE_TO_EXACT_STRING) {
            toBuffer.append(' ');
        }
        toBuffer.append(getOperatorName());
        if (style!=STYLE_TO_EXACT_STRING) {
            toBuffer.append(' ');
        } else {
            toBuffer.append(getWhitespace());
        }
        mRight.format(toBuffer,style);
        if (style==STYLE_TO_DEBUG_STRING) {
            toBuffer.append(')');
        }
    }
}
