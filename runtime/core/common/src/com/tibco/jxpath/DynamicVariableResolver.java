package com.tibco.jxpath;

import javax.xml.xpath.XPathVariableResolver;

/*
* Author: Suresh Subramani / Date: 11/3/11 / Time: 6:36 AM
*/
public interface DynamicVariableResolver extends XPathVariableResolver {




    /**
     *
     * @return
     */
    NodeResolver getNodeResolver();
}
