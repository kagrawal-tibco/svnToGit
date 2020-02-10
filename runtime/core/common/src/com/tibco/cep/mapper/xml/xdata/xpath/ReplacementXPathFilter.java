package com.tibco.cep.mapper.xml.xdata.xpath;

import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * A recursive {@link XPathFilter} where the expression at a certain location is replaced with a new one.
 */
public class ReplacementXPathFilter implements XPathFilter
{
    private final TextRange m_replacementRange;
    private final Expr m_replacement;
    private final Expr m_expression;

    public ReplacementXPathFilter(Expr expression, Expr replacement)
    {
        m_expression = expression;
        m_replacement = replacement;
        m_replacementRange = null;
    }

    public ReplacementXPathFilter(TextRange replaceRange, Expr replacement)
    {
        m_expression = null;
        m_replacement = replacement;
        m_replacementRange = replaceRange;
    }

    public Expr filter(Expr expr, NamespaceContextRegistry namespaceContextRegistry)
    {
        return XPathFilterSupport.applyFilterRecursively(expr, new XPathFilter()
        {
            public Expr filter(Expr exprCandidate, NamespaceContextRegistry nsContextRegistry)
            {
                if (exprCandidate==m_expression)
                {
                    return m_replacement;
                }
                if (exprCandidate.getTextRange()!=null && exprCandidate.getTextRange().equals(m_replacementRange))
                {
                    return m_replacement;
                }
                return exprCandidate;
            }
        },namespaceContextRegistry);
    }
}
