package com.tibco.be.ws.scs;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 22/11/11
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class SCSConstants {

    public static final ExpandedName MANAGED_PROJECTS_NAME = ExpandedName.makeName("ManagedProjects");
    public static final ExpandedName MANAGED_PROJECTS_ENTRY_NAME = ExpandedName.makeName("entry");
    public static final ExpandedName MANAGED_PROJECTS_ENTRY_PATH_NAME = ExpandedName.makeName("path");
    public static final ExpandedName ATTR_MANAGED_PROJECTS_ENTRY_TYPE = ExpandedName.makeName("kind");

    public static final ExpandedName MANAGED_PROJECT_ARTIFACTS_NAME = ExpandedName.makeName("projectArtifacts");
    public static final ExpandedName MANAGED_PROJECT_ARTIFACT_NAME = ExpandedName.makeName("artifact");
    public static final ExpandedName MANAGED_PROJECT_ARTIFACT_TYPE_NAME = ExpandedName.makeName("artifactType");
    public static final ExpandedName MANAGED_PROJECT_ARTIFACT_PATH_NAME = ExpandedName.makeName("artifactPath");
    public static final ExpandedName MANAGED_PROJECT_ARTIFACT_LAST_UPDATED = ExpandedName.makeName("lastUpdated");
    
    public static final String PROP_SCS_IMPLICIT_LOCK="ws.scs.implicitlocking.enable";
    public static final String LF = "\n";
}
