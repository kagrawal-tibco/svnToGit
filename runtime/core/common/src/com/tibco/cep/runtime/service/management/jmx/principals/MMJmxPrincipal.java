package com.tibco.cep.runtime.service.management.jmx.principals;

import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 1/28/11
 * Time: 6:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MMJmxPrincipal {
    //this interface is used here to expose in the module common the security
    //functionality provided in module security

    public LinkedList<String> getRoles();
    public LinkedList<String> getValidRoles();

    public String getName();
    public String getAuthTime();

//    public boolean isAdministrator();
    public boolean isMMAdministrator();
//    public boolean isMMSuperUser();
    public boolean isMMUser();
    public boolean isSecurityEnabled();
    public boolean isAuthenticSysOperation();
}
