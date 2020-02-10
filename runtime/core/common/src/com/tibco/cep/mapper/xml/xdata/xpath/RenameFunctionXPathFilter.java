package com.tibco.cep.mapper.xml.xdata.xpath;

import com.tibco.cep.mapper.xml.nsutils.NamespaceManipulationUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * A recursive {@link XPathFilter} where the expression at a certain location is replaced with a new one.
 */
public class RenameFunctionXPathFilter implements XPathFilter
{
    private final TextRange m_range;
    private final ExpandedName m_newName;
    private final String m_optionalSuggestedPrefix;

    public RenameFunctionXPathFilter(TextRange range, ExpandedName newName)
    {
        this(range,newName,null);
    }

    public RenameFunctionXPathFilter(TextRange range, ExpandedName newName, String optionalSuggestedPrefix)
    {
        m_range = range;
        m_newName = newName;
        m_optionalSuggestedPrefix = optionalSuggestedPrefix;
    }

    public Expr filter(Expr expr, NamespaceContextRegistry namespaceContextRegistry)
    {
        return XPathFilterSupport.applyFilterRecursively(expr, new XPathFilter()
        {
            public Expr filter(Expr exprCandidate, NamespaceContextRegistry nsContextRegistry)
            {
                if (exprCandidate.getExprTypeCode()==ExprTypeCode.EXPR_FUNCTION_CALL && m_range.equals(exprCandidate.getTextRange()))
                {
                    if (nsContextRegistry!=null)
                    {
                        String nn = NamespaceManipulationUtils.getOrAddQNameFromExpandedName(m_newName,nsContextRegistry,m_optionalSuggestedPrefix).toString();
                        return Expr.create(ExprTypeCode.EXPR_FUNCTION_CALL,exprCandidate.getChildren(),nn,exprCandidate.getWhitespace(),exprCandidate.getRepresentationClosure());
                    }
                }
                return exprCandidate;
            }
        },namespaceContextRegistry);
    }
}
