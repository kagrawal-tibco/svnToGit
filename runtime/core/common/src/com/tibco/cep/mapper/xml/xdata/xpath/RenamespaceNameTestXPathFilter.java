package com.tibco.cep.mapper.xml.xdata.xpath;

import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.QName;

/**
 * A recursive {@link XPathFilter} where the expression at a certain location is replaced with a new one.
 */
public class RenamespaceNameTestXPathFilter implements XPathFilter
{
    private final TextRange m_range;
    private final String m_newnamespace;
    private final String m_oldnamespace;

    public RenamespaceNameTestXPathFilter(TextRange range, String oldNamespace, String newNamespace)
    {
        m_range = range;
        m_oldnamespace = oldNamespace;
        m_newnamespace = newNamespace;
    }

    public String getOldNamespace()
    {
        return m_oldnamespace;
    }

    public String getNewNamespace()
    {
        return m_newnamespace;
    }

    public Expr filter(Expr expr, NamespaceContextRegistry namespaceContextRegistry)
    {
        return XPathFilterSupport.applyFilterRecursively(expr, new XPathFilter()
        {
            public Expr filter(Expr exprCandidate, NamespaceContextRegistry nsContextRegistry)
            {
                if (exprCandidate.getExprTypeCode()==ExprTypeCode.EXPR_NAME_TEST && m_range.equals(exprCandidate.getTextRange()))
                {
                    String str = exprCandidate.getExprValue();
                    if (nsContextRegistry!=null)
                    {
                        String pfx = nsContextRegistry.getOrAddPrefixForNamespaceURI(m_newnamespace);
                        String nn = new QName(pfx,new QName(str).getLocalName()).toString();
                        return Expr.create(ExprTypeCode.EXPR_NAME_TEST,new Expr[0],nn,exprCandidate.getWhitespace(),exprCandidate.getRepresentationClosure());
                    }
                }
                return exprCandidate;
            }
        },namespaceContextRegistry);
    }
}
