package com.tibco.cep.query.api;

import java.util.HashMap;
import java.util.Map;

/*
* Author: Ashwin Jayaprakash Date: Mar 21, 2008 Time: 1:49:42 PM
*/

/**
 * Simple class used to describe the Query execution policy and other settings.
 */
public interface QueryPolicy {

    
    public static class Continuous implements QueryPolicy {
    }


    public static class Snapshot implements QueryPolicy {
    }


    public static class SnapshotThenContinuous implements QueryPolicy {

        private Map<String, Boolean> snapshotRequiredFlags = new HashMap<String, Boolean>();

        public void setSnapshotRequired(String sourceName, boolean isRequired) {
            this.snapshotRequiredFlags.put(sourceName, isRequired);
        }

        public boolean getSnapshotRequired(String sourceName) {
            final Boolean isRequired = this.snapshotRequiredFlags.get(sourceName);
            return (null != isRequired) && isRequired;
        }

        public void clearSnapshotRequired() {
            snapshotRequiredFlags.clear();
        }
    }

}
 
