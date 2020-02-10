package com.tibco.rta.model.runtime;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/4/14
 * Time: 11:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class DimensionHierarchyConfiguration {

    private String schemaName;

    private String cubeName;

    private String hierarchyName;

    private boolean enabled;

    public DimensionHierarchyConfiguration() {
    }

    public DimensionHierarchyConfiguration(String schemaName, String cubeName, String hierarchyName, boolean enabled) {
        this.schemaName = schemaName;
        this.cubeName = cubeName;
        this.hierarchyName = hierarchyName;
        this.enabled = enabled;
    }

    public String getHierarchyName() {
        return hierarchyName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getCubeName() {
        return cubeName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public void setCubeName(String cubeName) {
        this.cubeName = cubeName;
    }

    public void setHierarchyName(String hierarchyName) {
        this.hierarchyName = hierarchyName;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
