package com.tibco.be.rms.tools;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Nov 20, 2008
 * Time: 4:57:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RMSAdminOpsMBean {

    String getBackingStoreLocation();
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
    void checkinExtEntity(String projectName, String extFilePath) throws Exception;

    /**
     * Allows for checking in all external <b>Decision Table</b> in the specified project
     * without having to go through the Decision Manager.
     * <p>
     * The project name specified should exist in the base location specified in the TRA
     * </p>
     * @param projectName: The name of the project located in this base directory.
     * @throws Exception: If the project does not exist, or if the base location is not set
     */
    void checkinExtEntities(String projectName) throws Exception;
}
