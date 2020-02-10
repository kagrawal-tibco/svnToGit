package com.tibco.be.migration.expimp.providers.csv;

import com.tibco.be.migration.expimp.providers.db.EntityStore;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.event.impl.TimeEventImpl;
import com.tibco.cep.runtime.model.event.impl.VariableTTLTimeEventImpl;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 1, 2008
 * Time: 8:23:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class CSVTimeEventDeserializer {

     public TimeEvent deserialize(String[] columns, EntityStore entityStore, String inputVersion, String outputVersion) throws Exception {
        TimeEventImpl e;
        long id = 0L;
        String extId = null;
        int status = 0;
        long timestamp = 0L;
        boolean retractedFlag = false;
        long scheduledTime = 0L;
        String closure = null;
        long ttl = 0L;

        //#"id,extId,status,timestamp,retractedFlag,scheduledTime,closure,TTL"
        if(columns.length < 8) {
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
        if(null != columns[5]) {
            scheduledTime = Long.valueOf(columns[5]).longValue();
        }
        if(null != columns[6]) {
            closure = columns[6];
        }
        if(null != columns[7]) {
            ttl = Long.valueOf(columns[7]).longValue();
        }

        e =  (TimeEventImpl) entityStore.newEntity(id,extId);
        e.setScheduledTime(scheduledTime);
        e.setClosure(closure);
        if (e instanceof VariableTTLTimeEventImpl)
           ((VariableTTLTimeEventImpl)e).setTTL(ttl);

//        String[] propNames = e.getPropertyNames();
//        for(int i = 0, col=5 ; i < propNames.length ; i++,col++ ) {
//            e.setProperty(propNames[i],columns[col]);
//        }
        return e;
     }
}
