package com.tibco.cep.designtime.model.mutable.impl;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: May 23, 2006
 * Time: 7:41:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class GlobalVariable {


    private long modTime;
    private String name;
    private String path;
    private String type;
    private String value;
    private boolean deploymentSettable;
    private boolean serviceSettable;


    public GlobalVariable(String name, String path, String value, String type, boolean deploymentSettable, boolean serviceSettable, long modTime) {
        this.name = name;
        this.path = path;
        this.value = value;
        this.type = type;
        this.deploymentSettable = deploymentSettable;
        this.serviceSettable = serviceSettable;
        this.modTime = modTime;
    }


    public long getModificationTime() {
        return modTime;
    }


    public String getFullName() {
        return this.path + this.name;
    }


    public String getName() {
        return name;
    }


    public String getPath() {
        return path;
    }


    public String getType() {
        return type;
    }


    public String getValueAsString() {
        return value;
    }


    public boolean isDeploymentSettable() {
        return deploymentSettable;
    }


    public boolean isServiceSettable() {
        return serviceSettable;
    }


}
