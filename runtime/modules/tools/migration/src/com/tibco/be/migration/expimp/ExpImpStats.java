package com.tibco.be.migration.expimp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Feb 17, 2008
 * Time: 2:22:24 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ExpImpStats {
    List errorList = new ArrayList();
    List warnList = new ArrayList();
    int instanceCount = 0;
    int eventCount = 0;

    public void addErrorString(String s) {
        errorList.add(s);
    }

    public void addWarningString(String s) {
        warnList.add(s);
    }

    public void incrementInstances() {
        ++instanceCount;
    }

    public void incrementsEvents() {
        ++eventCount;        
    }
    
    public Iterator getErrors() {
    	return errorList.iterator();
    }

    public Iterator getWarnings() {
    	return warnList.iterator();
    }

    public int getInstanceCount() {
    	return instanceCount;
    }
    
    public int getEventCount() {
    	return eventCount;
    }
    
    public int getErrorCount() {
    	return errorList.size();
    }
    
    public int getWarningCount() {
    	return warnList.size();
    }
}
