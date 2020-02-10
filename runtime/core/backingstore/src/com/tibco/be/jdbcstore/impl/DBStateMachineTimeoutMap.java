package com.tibco.be.jdbcstore.impl;

import java.util.Map;

public class DBStateMachineTimeoutMap extends DBTimeEventMap {

    //FIX THIS - do we have to use Event?
    //FIX THIS - This class seems to be not used at all
    public DBStateMachineTimeoutMap(StateMachineTimeoutDescription entityDescription, String tableName, Map aliases) throws Exception {
        super(entityDescription, tableName, aliases);
    }

    /*
    public DBStateMachineTimeoutMap(Event entityModel) throws Exception {
        super(entityModel);
    }

    void configure() throws Exception {
        OracleTypeADT oracleADT= typeDescriptor.getOracleTypeADT();

        properties = new PropertyMap[6];
        properties[0]= new EventPropertyMap(null, 4, "now", null, Types.NUMERIC);
        properties[1]= new EventPropertyMap(null, 5, "next", null,Types.NUMERIC);
        properties[2]= new EventPropertyMap(null, 6, "closure", null,Types.VARCHAR);
        properties[3]= new EventPropertyMap(null, 7, "ttl", null, Types.NUMERIC);
        properties[4]= new EventPropertyMap(null, 8, "sm_id", null,Types.NUMERIC);
        properties[5]= new EventPropertyMap(null, 9, "property_name", null,Types.VARCHAR);
    }
    */
}
