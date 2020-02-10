package com.tibco.be.ws.scs.impl.filter;

import java.io.File;
import java.io.IOException;

import com.tibco.be.ws.scs.IArtifactFilter;

public class TestDataArtifactFilter implements IArtifactFilter<DefaultFilterContext> {

	@Override
	public boolean shouldFilter(DefaultFilterContext filterContext, String artifactCanonicalPath) {
        File fileContainerFile = filterContext.getContainerPathFile();
        try {
            String containerCanonicalPath = fileContainerFile.getCanonicalPath();
            if (fileContainerFile.isFile()) {
                //If the 2 paths match return false. No need to filter.
                return containerCanonicalPath.equals(artifactCanonicalPath);
            } else {
        		return !(artifactCanonicalPath.endsWith(".concepttestdata") 
        				|| artifactCanonicalPath.endsWith(".eventtestdata"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}

}
