package com.tibco.be.jdbcstore.impl;

import java.util.Map;

public class DBTimeEventMap extends DBEventMap {

    public DBTimeEventMap(TimeEventDescription entityDescription, String tableName, Map aliases) throws Exception {
        super(entityDescription, tableName, aliases);
        //configure();
    }
}
