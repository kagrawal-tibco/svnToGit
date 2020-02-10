package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Purposefully package private.
 */
public abstract class LogicalOpExpr extends OperatorExpr
{
    public LogicalOpExpr(Expr left, Expr right, TextRange range, String whitespace) {
        super(left,right,range, whitespace);
    }

    public final SmSequenceType evalOpType(SmSequenceType lhs, SmSequenceType rhs, ExprContext context, EvalTypeInfo info)
    {
        info.addErrors(TypeChecker.checkConversion(lhs,SMDT.BOOLEAN,mLeft.getTextRange(),info.getXPathCheckArguments()));
        info.addErrors(TypeChecker.checkConversion(rhs,SMDT.BOOLEAN,mRight.getTextRange(),info.getXPathCheckArguments()));
        return info.recordReturnType(this,SMDT.BOOLEAN);
    }

    public final SmSequenceType getLeftArgType() {
        return SMDT.BOOLEAN;
    }

    public final SmSequenceType getRightArgType() {
        return SMDT.BOOLEAN;
    }
}
