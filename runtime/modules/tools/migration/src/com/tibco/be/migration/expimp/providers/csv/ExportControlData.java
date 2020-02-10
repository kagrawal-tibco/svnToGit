package com.tibco.be.migration.expimp.providers.csv;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 25, 2008
 * Time: 4:09:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExportControlData {
    String projectName;
    String projectConfigVersion;
    String dataVersion;
    long lastInternalId;
    int numInstances;
    int numEvents;
    int numErrors;
    int numWarnings;

    public ExportControlData() {
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public long getLastInternalId() {
        return lastInternalId;
    }

    public void setLastInternalId(long lastInternalId) {
        this.lastInternalId = lastInternalId;
    }

    public int getNumErrors() {
        return numErrors;
    }

    public void setNumErrors(int numErrors) {
        this.numErrors = numErrors;
    }

    public int getNumEvents() {
        return numEvents;
    }

    public void setNumEvents(int numEvents) {
        this.numEvents = numEvents;
    }

    public int getNumInstances() {
        return numInstances;
    }

    public void setNumInstances(int numInstances) {
        this.numInstances = numInstances;
    }

    public int getNumWarnings() {
        return numWarnings;
    }

    public void setNumWarnings(int numWarnings) {
        this.numWarnings = numWarnings;
    }

    public String getProjectConfigVersion() {
        return projectConfigVersion;
    }

    public void setProjectConfigVersion(String projectConfigVersion) {
        this.projectConfigVersion = projectConfigVersion;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

}
