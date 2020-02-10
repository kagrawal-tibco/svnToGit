/*
 * Copyright (c) 2012.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 */

package com.tibco.cep.persister;

/**
 * 
 * @author ggrigore
 *
 */
public enum PersistenceProperty {

    PERSISTENCE_PROVIDER_DBD_DIRECTORY("be.persistenceprovider.bdb.directory");

    

    private final String propertyName;
    private final Object[] validValues;

    PersistenceProperty(String propertyName, Object... validValues) {
        this.propertyName = propertyName;
        this.validValues = validValues;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object[] getValidValues() {
        return validValues;
    }
}
