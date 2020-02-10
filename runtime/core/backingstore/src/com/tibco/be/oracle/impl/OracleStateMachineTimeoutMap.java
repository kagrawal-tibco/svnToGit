package com.tibco.be.oracle.impl;

import java.sql.Types;

import oracle.jdbc.OracleConnection;
import oracle.sql.StructDescriptor;

import com.tibco.be.oracle.EventPropertyMap;
import com.tibco.be.oracle.PropertyMap;
import com.tibco.cep.designtime.model.event.Event;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Dec 2, 2006
 * Time: 11:15:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class OracleStateMachineTimeoutMap extends OracleTimeEventMap {
    public OracleStateMachineTimeoutMap(Event entityModel, StructDescriptor typeDescriptor, String tableName, OracleConnection oracle) throws Exception {
        super(entityModel, typeDescriptor, tableName, oracle);
    }

    void configure() throws Exception {
        //OracleTypeADT oracleADT= typeDescriptor.getOracleTypeADT();
        properties = new PropertyMap[6];
        properties[0]= new EventPropertyMap(oracle, null, 4, "now", null, Types.NUMERIC);
        properties[1]= new EventPropertyMap(oracle, null, 5, "next", null,Types.NUMERIC);
        properties[2]= new EventPropertyMap(oracle, null, 6, "closure", null,Types.VARCHAR);
        properties[3]= new EventPropertyMap(oracle, null, 7, "ttl", null, Types.NUMERIC);
        properties[4]= new EventPropertyMap(oracle, null, 8, "sm_id", null,Types.NUMERIC);
        properties[5]= new EventPropertyMap(oracle, null, 9, "property_name", null,Types.VARCHAR);
    }
}
