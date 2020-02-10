package com.tibco.be.migration.expimp.providers.csv;

import com.tibco.be.migration.expimp.providers.db.EntityStore;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 28, 2008
 * Time: 10:53:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class CSVSimpleEventDeserializer {

    public SimpleEventImpl deserialize(String[] columns, EntityStore entityStore, String inputVersion, String outputVersion) throws Exception {
        SimpleEventImpl e;
        long id = 0L;
        String extId = null;
        int status = 0;
        long timestamp = 0L;
        boolean retractedFlag = false;

        //#"id;extId;status;timestamp;retractedFlag;p_String;p_int;p_long;p_double;p_boolean;p_DateTime"
        if(columns.length < 5) {
            throw new Exception("Incorrect CSV data structure: not enough columns.");
        }
        if(null != columns[0]) {
            id = Long.valueOf(columns[0]).longValue();
        }
        if(null != columns[1]) {
            extId = columns[1];
        }
        if(null != columns[2]) {
            status = Integer.valueOf(columns[2]).intValue();
        }
        if(null != columns[3]) {
            timestamp = Long.valueOf(columns[3]).longValue();
        }
        if(null != columns[4]) {
            retractedFlag = Boolean.valueOf(columns[4]).booleanValue();
        }
        e =  (SimpleEventImpl) entityStore.newEntity(id,extId);


        String[] propNames = e.getPropertyNames();
        for(int i = 0, col=5 ; i < propNames.length ; i++,col++ ) {
            e.setProperty(propNames[i],columns[col]);
        }
        return e;

    }
}
