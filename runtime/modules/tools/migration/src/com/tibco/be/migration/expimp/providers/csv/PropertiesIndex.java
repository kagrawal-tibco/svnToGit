package com.tibco.be.migration.expimp.providers.csv;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 25, 2008
 * Time: 9:25:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertiesIndex {
    String conceptName;
    String propertyName;
    int propertyIndex;

    public PropertiesIndex() {
    }

    public PropertiesIndex(String conceptName, int propertyIndex, String propertyName) {
        this.conceptName = conceptName;
        this.propertyIndex = propertyIndex;
        this.propertyName = propertyName;
    }

    public String getConceptName() {
        return conceptName;
    }

    public void setConceptName(String conceptName) {
        this.conceptName = conceptName;
    }

    public int getPropertyIndex() {
        return propertyIndex;
    }

    public void setPropertyIndex(int propertyIndex) {
        this.propertyIndex = propertyIndex;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

}
