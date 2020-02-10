package com.tibco.be.ws.scs.impl.filter;

import com.tibco.be.ws.scs.IArtifactFilter;

import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/5/12
 * Time: 12:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileContainerMatchingFilter implements IArtifactFilter<DefaultFilterContext> {

    /**
     * @param filterContext
     * @param artifactCanonicalPath
     * @return
     */
    @Override
    public boolean shouldFilter(DefaultFilterContext filterContext, String artifactCanonicalPath) {
    	File fileContainerFile = filterContext.getContainerPathFile();
    	if (fileContainerFile != null) {
    		try {
    			String containerCanonicalPath = fileContainerFile.getCanonicalPath();
    			if (fileContainerFile.isFile()) {
    				//If the 2 paths match return false. No need to filter.
    				return containerCanonicalPath.equals(artifactCanonicalPath);
    			} else {
    				//If regions do not match
    				return !artifactCanonicalPath.regionMatches(0, containerCanonicalPath, 0, containerCanonicalPath.length());
    			}
    		} catch (IOException e) {
    			throw new RuntimeException(e);
    		}
    	}

    	return false;
    }
}
