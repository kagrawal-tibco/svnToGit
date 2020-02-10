package com.tibco.jxpath;

import com.tibco.jxpath.objects.XObject;

public interface MutableXPathContext extends XPathContext {

    void setNodeResolver(NodeResolver nodeResolver);

    void setCurrentContextNode(XObject pathContext);

    void setAbbreviatedStep(boolean abbr);

    void setCurrentContextCount(int count);

    void setCurrentContextPosition(int i);
}
