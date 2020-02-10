package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public abstract class NumericOpExpr extends OperatorExpr
{
    protected NumericOpExpr(Expr left, Expr right, TextRange range, String whitespace) {
        super(left,right,range, whitespace);
    }

    public SmSequenceType getLeftArgType() {
        return SMDT.DOUBLE;
    }

    public SmSequenceType getRightArgType() {
        return SMDT.DOUBLE;
    }

    public final SmSequenceType evalOpType(SmSequenceType lhs, SmSequenceType rhs, ExprContext context, EvalTypeInfo info)
    {
        boolean usesTypedNodes = info.getXPathCheckArguments().getUsesTypedNodes();
        info.addErrors(TypeChecker.checkConversion(lhs.typedValue(usesTypedNodes),SMDT.DOUBLE,mLeft.getTextRange(),info.getXPathCheckArguments()));
        info.addErrors(TypeChecker.checkConversion(rhs.typedValue(usesTypedNodes),SMDT.DOUBLE,mRight.getTextRange(),info.getXPathCheckArguments()));
        return info.recordReturnType(this,SMDT.DOUBLE);
    }
}
