package com.tibco.cep.runtime.service.om.api;

import java.util.List;

/*
 * Author: Fatih Ildiz / Date: Nov 14, 2014 / Time: 04:41:42 PM
 */
public class EncryptionConfig {
    protected List<String> fieldNames;

    public EncryptionConfig() {
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(List<String> fieldNames) {
        this.fieldNames = fieldNames;
    }
}
