package com.tibco.be.ws.scs;

import com.tibco.be.ws.scs.impl.filter.IFilterContext;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 1/4/12
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IArtifactFilter<F extends IFilterContext> {

    /**
     * @param filterContext
     * @param artifactCanonicalPath
     * @return
     */
    public boolean shouldFilter(F filterContext, String artifactCanonicalPath);
}
