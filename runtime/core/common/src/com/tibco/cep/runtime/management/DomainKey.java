package com.tibco.cep.runtime.management;

import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Jan 27, 2009 Time: 5:34:29 PM
*/
public enum DomainKey {
    NAME(FQName.class),

    HOST_IP_ADDRESS(String.class),

    HOST_PROCESS_ID(String.class),

    DESCRIPTION_CSV(String.class),

    JMX_PROPS_CSV(String.class),

    HAWK_PROPS_CSV(String.class),

    FQ_NAME(FQName.class);
    //-------------

    Class valueClass;

    DomainKey(Class valueClass) {
        this.valueClass = valueClass;
    }

    public Class getValueClass() {
        return valueClass;
    }
}
