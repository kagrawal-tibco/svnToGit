package com.tibco.be.ws.scs.impl.filter;

import com.tibco.be.ws.scs.IArtifactFilter;

import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 1/4/12
 * Time: 12:23 PM
 * Filter svn internal files if found.
 */
public class DefaultArtifactSVNRegexFilter implements IArtifactFilter<DefaultFilterContext> {

    private static Pattern SVN_FILES_PATTERN = Pattern.compile("(.*)\\.svn(.*)");

    /**
     * @param filterContext
     * @param artifactCanonicalPath
     * @return
     */
    @Override
    public boolean shouldFilter(DefaultFilterContext filterContext, String artifactCanonicalPath) {
        return SVN_FILES_PATTERN.matcher(artifactCanonicalPath).matches();
    }
}
