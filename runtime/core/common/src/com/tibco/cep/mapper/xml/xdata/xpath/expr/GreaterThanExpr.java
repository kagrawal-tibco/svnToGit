package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;

/**
 */
public final class GreaterThanExpr extends ComparisonExpr
{
    public GreaterThanExpr(Expr left, Expr right, TextRange range, String trailingWhitespace) {
        super(left,right,range,trailingWhitespace);
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_GREATER_THAN;
    }

    public String getOperatorName() {
        return ">";
    }
}
