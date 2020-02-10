package com.tibco.be.migration.expimp.providers.csv;

import com.tibco.be.migration.expimp.providers.db.EntityStore;
import com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl;
import com.tibco.cep.runtime.model.event.TimeEvent;

/**
 * Created by IntelliJ IDEA.
 * User: kpang
 * Date: Apr 19, 2008
 * Time: 6:50:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class CSVStateTimeoutEventDeserializer extends CSVTimeEventDeserializer {
    public TimeEvent deserialize(String[] columns, EntityStore entityStore, String inputVersion, String outputVersion) throws Exception {
        StateMachineConceptImpl.StateTimeoutEvent e;
        long id = 0L;
        String extId = null;
        int status = 0;
        long timestamp = 0L;
        boolean retractedFlag = false;
        long scheduledTime = 0L;
        String closure = null;
        long ttl = 0L;
        long smId = 0L;
        String propName = null;

        // #"id,extId,status,timestamp,retractedFlag,scheduledTime,closure,TTL,SMId,PropertyName"
        if(columns.length < 10) {
            throw new Exception("Incorrect CSV data structure: not enough columns.");
        }
        else {
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
            if(null != columns[8]) {
                smId = Long.valueOf(columns[8]).longValue();
            }
            if(null != columns[9]) {
                propName = columns[9];
            }
        }

        e =  (StateMachineConceptImpl.StateTimeoutEvent) entityStore.newEntity(id, smId, propName, scheduledTime);

//        String[] propNames = e.getPropertyNames();
//        for(int i = 0, col=5 ; i < propNames.length ; i++,col++ ) {
//            e.setProperty(propNames[i],columns[col]);
//        }
        return e;
     }
}
