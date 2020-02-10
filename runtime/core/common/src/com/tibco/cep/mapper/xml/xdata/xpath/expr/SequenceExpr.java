package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

/**
 * The new sequence constructor, i.e. 1,2,3 in XPath 2.0 (does so in a binary way, though)
 */
public final class SequenceExpr extends Expr
{
    private final Expr mLeft;
    private final Expr mRight;

    public SequenceExpr(Expr l, Expr r, TextRange range, String whitespace) {
        super(range, whitespace);
        mLeft = l;
        mRight = r;
        if (l==null || r==null)
        {
            throw new NullPointerException();
        }
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
        // nice & easy!
        SmSequenceType r1 = mLeft.evalType(context,info);
        SmSequenceType r2 = mRight.evalType(context,info);
        SmSequenceType ret = SmSequenceTypeFactory.createSimplifiedSequence(r1,r2);
        return info.recordReturnType(this,ret);
    }

    public String getRepresentationClosure() {
        return null;
    }

    public String getExprValue() {
        return null;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_SEQUENCE;
    }

    public Expr[] getChildren()
    {
        return new Expr[] {mLeft, mRight};
    }

    public void format(StringBuffer toBuffer, int style) {
        boolean isExact = style==STYLE_TO_EXACT_STRING;
        mLeft.format(toBuffer,style);
        toBuffer.append(",");
        if (isExact)
        {
            toBuffer.append(getWhitespace());
        }
        mRight.format(toBuffer,style);
    }
}
