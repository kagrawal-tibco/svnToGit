package com.tibco.cep.dashboard.psvr.mal.model;

/**
 * <code>PropertyTracker</code> tracks the changes to a propery. Used by
 * <code>MALElement</code> to keep track of property changes.
 *
 * @author apatil
 */
class PropertyTracker {

    String propertyName;
    Object oldValue;
    Object newValue;

    /**
     * Creates an instance of <code>PropertyTracker</code>
     * @param propertyName The name of the property
     * @param oldValue The old value. Can be <code>null</code>
     * @param newValue The new value. Can be <code>null</code>
     */
    PropertyTracker(String propertyName, Object oldValue, Object newValue) {
        this.propertyName = propertyName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * Returns the new value. Can be <code>null</code>
     * @return Returns the newValue.
     */
    public Object getNewValue() {
        return newValue;
    }

    /**
     * Returns the old value. Can be <code>null</code>
     * @return Returns the oldValue.
     */
    public Object getOldValue() {
        return oldValue;
    }

    /**
     * Returns the property name
     * @return Returns the propertyName.
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Checks the equality of two <code>PropertyTracker</code>.
     * @return <code>true</code> iff the property name are the same else <code>false</code>
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj instanceof PropertyTracker) == false) {
            return false;
        }
        PropertyTracker castedObj = (PropertyTracker) obj;
        return propertyName.equals(castedObj.propertyName);
    }

    /**
     * Returns the hashcode for the <code>PropertyTracker</code>.
     * @return the hashcode of the property name
     */
    public int hashCode() {
        return propertyName.hashCode();
    }

    /**
     * Returns a string representation of the <code>PropertyTracker</code>
     * @return The <code>String</code> representation
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer("com.tibco.cep.dashboard.psvr.mal.model.PropertyTracker[");
        buffer.append("propertyname="+propertyName);
        buffer.append(",oldvalue="+oldValue);
        buffer.append(",newvalue="+newValue);
        buffer.append("]");
        return buffer.toString();
    }
}