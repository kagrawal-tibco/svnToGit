package com.tibco.be.oracle.impl;

import java.util.Map;

import oracle.jdbc.OracleConnection;
import oracle.sql.StructDescriptor;

import com.tibco.cep.designtime.model.event.Event;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Dec 1, 2006
 * Time: 3:24:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class OracleTimeEventMap extends OracleEventMap {

    /**
     *
     * @param entityModel
     * @param typeDescriptor
     * @param oracle
     * @throws Exception
     */
    public OracleTimeEventMap(Event entityModel, StructDescriptor typeDescriptor, String tableName, OracleConnection oracle) throws Exception{
        super(entityModel, typeDescriptor, tableName, oracle);
        //configure();
    }

    public OracleTimeEventMap(OracleAdapter.TimeEventDescription entityModel, StructDescriptor typeDescriptor, String tableName, OracleConnection oracle, Map aliases) throws Exception{
        super(entityModel, typeDescriptor, tableName, oracle, aliases);
        //configure();
    }
}
