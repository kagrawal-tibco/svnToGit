package com.tibco.be.migration.expimp.providers.csv;

import com.tibco.be.migration.expimp.providers.db.EntityStore;
import com.tibco.cep.runtime.model.element.Concept;


/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 25, 2008
 * Time: 2:25:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class CSVConceptDeserializer {

    public Concept deserialize(String[] columns, EntityStore entityStore, String inputVersion, String outputVersion) throws Exception {
        Concept c;
        long id = 0L;
        String extId = null;
        int status = 0;
        long timestamp = 0L;
        boolean retractedFlag = false;

        //#"id\,extId\,status\,timestamp\,retractedFlag"
        if(columns.length != 5) {
            throw new Exception("Incorrect CSV data structure");
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
        c = (Concept) entityStore.newEntity(id,extId);        
        return c;
    }
}
