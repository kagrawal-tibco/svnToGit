package com.tibco.cep.mapper.xml.xdata.xpath;

import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * Utility function for workig with {@link XPathFilter} and {@link Expr}
 */
public class XPathFilterSupport
{
    /**
     * Applies the filter recursively to every sub-expression.<br>
     * The filter is first applied to the expression itself, then applied, recursively,
     * to all the children of the resulting expression.<br>
     * It is optimized for the case where a filter does nothing (and returns the same expression) so that
     * unchanged expressions aren't generated.
     * @param expr The expression.
     * @param filter The filter to apply.
     * @return The updated expression, never null.
     */
    public static Expr applyFilterRecursively(Expr expr, XPathFilter filter, NamespaceContextRegistry namespaceContextRegistry)
    {
        expr = filter.filter(expr, namespaceContextRegistry);
        Expr[] ch = expr.getChildren();
        if (ch.length==0)
        {
            return expr;
        }
        Expr[] newchildren = null;
        for (int i=0;i<ch.length;i++)
        {
            Expr nc = applyFilterRecursively(ch[i],filter,namespaceContextRegistry);
            if (nc!=ch[i])
            {
                // lazily create a replacement array:
                if (newchildren==null)
                {
                    newchildren = new Expr[ch.length];
                    for (int x=0;x<i;x++)
                    {
                        // fill in previous ones (which are identical):
                        newchildren[x] = ch[x];
                    }
                }
            }
            if (newchildren!=null)
            {
                newchildren[i] = nc;
            }
        }
        // No change:
        if (newchildren==null)
        {
            return expr;
        }
        int tc = expr.getExprTypeCode();
        String v = expr.getExprValue();
        return Expr.create(tc,newchildren,v,expr.getWhitespace(),expr.getRepresentationClosure());
    }
}
