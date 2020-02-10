package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;

/**
 * Purposefully package private.
 */
public final class AddExpr extends NumericOpExpr
{
    public AddExpr(Expr left, Expr right, TextRange range, String whitespace) {
        super(left,right,range,whitespace);
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_ADD;
    }

    public String getOperatorName() {
        return "+";
    }
}
