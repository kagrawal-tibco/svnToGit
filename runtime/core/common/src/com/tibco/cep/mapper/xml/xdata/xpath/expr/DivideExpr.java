package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;

/**
 */
public final class DivideExpr extends NumericOpExpr
{
    public DivideExpr(Expr left, Expr right, TextRange range, String whitespace) {
        super(left,right,range,whitespace);
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_DIVIDE;
    }

    public String getOperatorName() {
        return "div";
    }
}
