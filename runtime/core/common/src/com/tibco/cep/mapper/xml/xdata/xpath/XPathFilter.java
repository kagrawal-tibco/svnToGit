package com.tibco.cep.mapper.xml.xdata.xpath;

import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * A 'fix' for an xpath.  Applying this will adjust return a new fixed xpath.<br>
 * A filter can be applied recursively using {@link XPathFilterSupport#applyFilterRecursively} if that is required.
 */
public interface XPathFilter
{
    Expr filter(Expr expr, NamespaceContextRegistry namespaceContextRegistry);
}
