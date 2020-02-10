package com.tibco.be.ws.scs.impl.filter;

import com.tibco.be.ws.scs.IArtifactFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/5/12
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultArtifactFilter implements IArtifactFilter<DefaultFilterContext> {

    private List<IArtifactFilter> childFilters = new ArrayList<IArtifactFilter>();

    public DefaultArtifactFilter() {
        childFilters.add(new DefaultArtifactSVNRegexFilter());
        childFilters.add(new FileContainerMatchingFilter());
    }

    /**
     * @param artifactCanonicalPath
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean shouldFilter(DefaultFilterContext filterContext, String artifactCanonicalPath) {
        //Presently the strategy is OR
        boolean filter = false;
        for (IArtifactFilter artifactFilter : childFilters) {
            boolean tempFilter = artifactFilter.shouldFilter(filterContext, artifactCanonicalPath);
            filter = filter | tempFilter;
            if (filter) {
                break;
            }
        }
        return filter;
    }
}
