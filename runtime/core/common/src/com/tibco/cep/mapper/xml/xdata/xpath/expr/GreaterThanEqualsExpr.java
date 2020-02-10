package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;

/**
 */
public final class GreaterThanEqualsExpr extends ComparisonExpr
{
    public GreaterThanEqualsExpr(Expr left, Expr right, TextRange range, String trailingWhitespace) {
        super(left,right,range,trailingWhitespace);
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_GREATER_THAN_EQUALS;
    }

    public String getOperatorName() {
        return ">=";
    }
}
