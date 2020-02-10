package com.tibco.rta.service.admin;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/9/14
 * Time: 2:35 PM
 *
 * MBean for admin service.
 */
public interface AdminServiceImplMBean {

    public String getCurrentActivationStatus();

    public void changeHierarchyStatus(String schemaName,
                                      String cubeName,
                                      String hierarchyName,
                                      boolean enabled);
}
