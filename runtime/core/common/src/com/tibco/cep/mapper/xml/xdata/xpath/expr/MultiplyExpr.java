package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;

/**
 */
public final class MultiplyExpr extends NumericOpExpr
{
    public MultiplyExpr(Expr left, Expr right, TextRange range, String whitespace) {
        super(left,right,range,whitespace);
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_MULTIPLY;
    }

    public String getOperatorName() {
        return "*";
    }
}
