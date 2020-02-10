package com.tibco.cep.runtime.service.om.api;

/*
* Author: Ashwin Jayaprakash / Date: Sep 8, 2010 / Time: 4:45:59 PM
*/
public class DatabaseConfig {
    
    // Preload handles (objecttable entries)
    protected boolean recoverOnStartup;
    
    // Preload entity itself
    protected boolean loadOnStartup;

    protected long loadFetchSize;

    public DatabaseConfig() {
    }

    public boolean isRecoverOnStartup() {
        return this.recoverOnStartup;
    }

    public void setRecoverOnStartup(boolean recoverOnStartup) {
        this.recoverOnStartup = recoverOnStartup;
    }

    public boolean isLoadOnStartup() {
        return this.loadOnStartup;
    }

    public void setLoadOnStartup(boolean loadOnStartup) {
        this.loadOnStartup = loadOnStartup;
    }

    public long getLoadFetchSize() {
        return this.loadFetchSize;
    }

    public void setLoadFetchSize(long loadFetchSize) {
        this.loadFetchSize = loadFetchSize;
    }
}
