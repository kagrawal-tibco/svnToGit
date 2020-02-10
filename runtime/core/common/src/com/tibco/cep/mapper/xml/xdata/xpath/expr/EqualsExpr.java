package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

public final class EqualsExpr extends ComparisonExpr
{
    public EqualsExpr(Expr left, Expr right, TextRange range, String trailingWhitespace) {
        super(left,right,range,trailingWhitespace);
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_EQUALS;
    }

    public String getOperatorName() {
        return "=";
    }

    public ExprContext assertBooleanValue(ExprContext ec, boolean isTrue)
    {
        if (isTrue)
        {
            EvalTypeInfo t = new EvalTypeInfo();
            // great, we know these two things are equals, however, because the expressions may be arbitrarily
            // complicated, we need to look for some simple cases:
            SmSequenceType lt = mLeft.evalType(ec,t);
            SmSequenceType rt = mRight.evalType(ec,t);
            boolean lIsNodeSet = SmSequenceTypeSupport.isNodeSet(lt);
            boolean rIsNodeSet = SmSequenceTypeSupport.isNodeSet(rt);
            if (lIsNodeSet && !rIsNodeSet)
            {
                // left hand side is being checked:
                return mLeft.assertTypedValueEvaluatesTo(ec,rt);
            }
            if (rIsNodeSet && !lIsNodeSet)
            {
                // right hand side is being checked:
                return mRight.assertTypedValueEvaluatesTo(ec,rt);
            }

            // both node sets or (worse) both strings, can't really add anything; give up:
            return ec;
        }
        else
        {
            // can't add any more info:
            return ec;
        }
    }
}
