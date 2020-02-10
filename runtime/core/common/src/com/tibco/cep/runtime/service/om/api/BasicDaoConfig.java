package com.tibco.cep.runtime.service.om.api;

import com.tibco.cep.util.annotation.Optional;

/*
* Author: Ashwin Jayaprakash / Date: Sep 8, 2010 / Time: 1:34:01 PM
*/
public class BasicDaoConfig {
    protected String name;

    @Optional
    protected DataCacheConfig dataCacheConfig;

    @Optional
    protected DatabaseConfig databaseConfig;

    public BasicDaoConfig() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataCacheConfig getDataCacheConfig() {
        return dataCacheConfig;
    }

    public void setDataCacheConfig(DataCacheConfig dataCacheConfig) {
        this.dataCacheConfig = dataCacheConfig;
    }

    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }
}
