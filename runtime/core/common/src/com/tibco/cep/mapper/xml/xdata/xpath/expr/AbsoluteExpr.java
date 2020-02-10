package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

public final class AbsoluteExpr extends Expr
{
    private final Expr mRight;

    public AbsoluteExpr(Expr right, TextRange range, String trailingWhitespace) {
        super(range, trailingWhitespace);
        mRight = right;
    }

    public String getExprValue() {
        return null;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_ABSOLUTE;
    }

    public Expr[] getChildren() {
        if (mRight==null) {
            return NO_CHILDREN;
        }
        return new Expr[] {mRight};
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info) {
        SmSequenceType current = context.getInput().getDocumentType();
        if (mRight==null) {
            return info.recordReturnType(this,current);
        }
        if (current==null)
        {
            // Sanity, only happens if there's a bug:
            System.err.println("Get doc type on " + context.getInput().getClass().getName() + " returned null");
            current=SMDT.PREVIOUS_ERROR;
        }
        SmSequenceType ret = mRight.evalType(context.createWithInput(current),info);
        return info.recordReturnType(this,ret);
    }

    // check for '//' <-> 'descendant-or-self::node()'
    private Expr getRightAbbreviation() {
        if (!(mRight instanceof LocationPathExpr)) {
            return null;
        }
        Expr step = mRight.getChildren()[0];
        if (!(step instanceof StepExpr)) {
            return null;
        }
        Expr lstep = step.getChildren()[0];
        if (!(lstep instanceof DescendantOrSelfAxisExpr)) {
            return null;
        }
        Expr rstep = step.getChildren()[1];
        if (!(rstep instanceof NodeNodeTestExpr)) {
            return null;
        }
        return mRight.getChildren()[1];
    }

    public void format(StringBuffer toBuffer, int style) {
        if (mRight==null) {
            toBuffer.append("/");
            if (style==STYLE_TO_EXACT_STRING) {
                toBuffer.append(getWhitespace());
            }
        } else {
            if ((style==STYLE_TO_STRING || style==STYLE_TO_STRING_NO_PREFIX || style==STYLE_TO_EXACT_STRING) && getRightAbbreviation()!=null) {
                toBuffer.append("//");
                toBuffer.append(getRightAbbreviation());
            } else {
                toBuffer.append("/");
                if (style==STYLE_TO_DEBUG_STRING) {
                    toBuffer.append("(");
                }
                mRight.format(toBuffer,style);
            }
            if (style==STYLE_TO_DEBUG_STRING) {
                toBuffer.append(")");
            }
        }
    }
}
