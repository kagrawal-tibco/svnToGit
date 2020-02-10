package com.tibco.be.rms.tools;

//import com.tibco.cep.studio.rms.client.RMSProxy;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.studio.util.RMSConfig;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Nov 20, 2008
 * Time: 4:59:10 PM
 * Place to insert admin operations for RMS.
 */
public class RMSAdminOps implements RMSAdminOpsMBean {

    /**
     * Allows for checking in an external <b>Decision Table</b> in the specified project
     * without having to go through the Decision Manager.
     * <p>
     * The project name specified should exist in the base location specified in the TRA
     * </p>
     * @param projectName: The name of the project located in this base directory.
     * @param extFilePath: The fully qualified path of the file representing the decision table.
     * @throws Exception: If the project does not exist, or if the base location is not set
     */
    public void checkinExtEntity(String projectName, String extFilePath) throws Exception {
        if (containsProject(projectName)) {
            //RMSProxy.checkinExtEntities(null, projectName, extFilePath);
        }
    }

    /**
     * Allows for checking in all external <b>Decision Table</b> in the specified project
     * without having to go through the Decision Manager.
     * <p/>
     * The project name specified should exist in the base location specified in the TRA
     * </p>
     *
     * @param projectName: The name of the project located in this base directory.
     * @throws Exception: If the project does not exist, or if the base location is not set
     */
    public void checkinExtEntities(String projectName) throws Exception {
        if (containsProject(projectName)) {
            //RMSProxy.checkinExtEntities(null, projectName, null);
        }
    }

    private boolean containsProject(String projectName) throws Exception {
        BEProperties beProperties = RMSConfig.getBranch("java.property.rms");
        if (beProperties != null) {
            String baseLocation = beProperties.getProperty("projects.baselocation");
            if (baseLocation == null) {
                baseLocation = beProperties.getProperty("project.location");
                if (baseLocation == null) {
                    throw new Exception("Either the rms.projects.baselocation or rms.project.location is mandatory");
                }
            }

            String projectPath = baseLocation + File.separator + projectName;
            File f = new File(projectPath);
            if (!f.exists()) {
                throw new Exception("The project does not exist. Please use a valid project name");
            }
            return true;
        }
        return false;
    }

    public String getBackingStoreLocation() {
        return RMSConfig.getBranch("tibco.clientVar").getProperty("RMS/storageLocation");
    }
}
