package com.tibco.be.ws.scs.impl.filter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tibco.be.ws.scs.IArtifactFilter;


public class CheckoutCacheFilter implements IArtifactFilter<DefaultFilterContext> {

	List<String> checkoutArtifacts = new ArrayList<String>();

	public void add(String artifact) {
		checkoutArtifacts.add(artifact);
	}
	
	@Override
	public boolean shouldFilter(DefaultFilterContext filterContext, String artifactCanonicalPath) {
        File fileContainerFile = filterContext.getContainerPathFile();
        try {
            String containerCanonicalPath = fileContainerFile.getCanonicalPath();
            if (fileContainerFile.isFile()) {
                //If the 2 paths match return false. No need to filter.
                return containerCanonicalPath.equals(artifactCanonicalPath);
            } else {
                return checkoutArtifacts.contains(artifactCanonicalPath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
