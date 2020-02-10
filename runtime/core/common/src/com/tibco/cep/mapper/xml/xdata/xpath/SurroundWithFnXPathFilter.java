package com.tibco.cep.mapper.xml.xdata.xpath;

import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * A recursive {@link XPathFilter} where the expression at a certain location is replaced with a new one.
 */
public class SurroundWithFnXPathFilter implements XPathFilter
{
    private final TextRange m_surroundRange;
    private final String m_function;

    public SurroundWithFnXPathFilter(TextRange m_range, String functionName)
    {
        m_surroundRange = m_range;
        m_function = functionName;
    }

    public Expr filter(Expr expr, NamespaceContextRegistry namespaceContextRegistry)
    {
        return XPathFilterSupport.applyFilterRecursively(expr, new XPathFilter()
        {
            public Expr filter(Expr exprCandidate, NamespaceContextRegistry nsContextRegistry)
            {
                if (m_surroundRange.equals(exprCandidate.getTextRange()))
                {
                    String str = exprCandidate.toExactString();
                    return Parser.parse(m_function + "(" + str + ")");
                }
                return exprCandidate;
            }
        },namespaceContextRegistry);
    }
}
