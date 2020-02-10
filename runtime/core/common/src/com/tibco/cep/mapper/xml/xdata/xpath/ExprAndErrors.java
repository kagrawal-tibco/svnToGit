package com.tibco.cep.mapper.xml.xdata.xpath;

/**
 * Parses XPath tokens into Expr.
 */
public final class ExprAndErrors
{
    private final Expr mExpr;
    private final ErrorMessageList mErrorMessageList;

    public ExprAndErrors(Expr expr, ErrorMessageList eml)
    {
        mExpr = expr;
        mErrorMessageList = eml;
    }

    public Expr getExpr()
    {
        return mExpr;
    }

    public ErrorMessageList getErrorMessageList()
    {
        return mErrorMessageList;
    }
}
