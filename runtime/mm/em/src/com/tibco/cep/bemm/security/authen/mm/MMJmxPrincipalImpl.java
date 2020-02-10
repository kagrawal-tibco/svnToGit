package com.tibco.cep.bemm.security.authen.mm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.management.remote.JMXPrincipal;

import com.tibco.cep.runtime.service.management.jmx.principals.MMJmxPrincipal;
import com.tibco.cep.security.tokens.Role;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 1/28/11
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
 public class MMJmxPrincipalImpl extends JMXPrincipal implements MMJmxPrincipal {
    private LinkedList<String> roles = new LinkedList<String>();
    private LinkedList<String> validRoles = new LinkedList<String>();
    private boolean isSecurityEnabled;
    private boolean isAuthenticSysOperation;
    private String name;
    private String authTime;

    public MMJmxPrincipalImpl(String name, boolean isSecurityEnabled, boolean isAuthenticSysOperation) {
        super(name);
        setAuthTime();
        this.name = name;
        this.isSecurityEnabled = isSecurityEnabled;
        this.isAuthenticSysOperation = isAuthenticSysOperation;
        setValidRoles();
    }

    private void setAuthTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.authTime = dateFormat.format(date);
    }

    private void setValidRoles() {
        MMRoles[] roles = MMRoles.values();
        for (MMRoles role : roles) {
            validRoles.addLast(role.getName());
        }
    }

    public LinkedList<String> getValidRoles() {
        return validRoles;
    }

    public void setRoles(List<Role> roles) {
        if (roles == null) {
            this.roles.addLast("");
            return;
        }

        for (Role role: roles) {
            this.roles.addLast(role.getName().toLowerCase());
        }
    }

    public void setRoles(Iterator<Role> roles) {
        if (roles == null) {
            this.roles.addLast("");
            return;
        }

        while (roles.hasNext()) {
            this.roles.addLast(roles.next().getName().toLowerCase());
        }
    }

    public LinkedList<String> getRoles() {
        return roles;
    }

    public String getName() {
        return name;
    }

    public String getAuthTime() {
        return authTime;
    }

    /*public boolean isAdministrator(){
        return roles.contains(MMRoles.ADMINISTRATOR.getName());
    }*/

    public boolean isMMAdministrator(){
        return roles != null && roles.contains(MMRoles.MM_ADMINISTRATOR.getName());
    }

    /*public boolean isMMSuperUser(){
        return roles.contains(MMRoles.MM_SUPER_USER.getName());
    }*/

    public boolean isMMUser(){
        return roles != null && roles.contains(MMRoles.MM_USER.getName());
    }

    public boolean isSecurityEnabled() {
        return isSecurityEnabled;
    }

    public boolean isAuthenticSysOperation() {
        return isAuthenticSysOperation;
    }

    //todo validate in case the user role does not exist
}


