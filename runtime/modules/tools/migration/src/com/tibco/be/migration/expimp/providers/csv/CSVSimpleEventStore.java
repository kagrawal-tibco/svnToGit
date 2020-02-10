package com.tibco.be.migration.expimp.providers.csv;


import java.util.HashMap;
import java.util.Iterator;

import com.tibco.be.migration.CSVReader;
import com.tibco.be.migration.CSVReaderImpl;
import com.tibco.be.migration.expimp.ExpImpContext;
import com.tibco.be.migration.expimp.ExpImpStats;
import com.tibco.be.migration.expimp.providers.db.DbStore;
import com.tibco.be.migration.expimp.providers.db.EntityStore;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 25, 2008
 * Time: 9:30:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class CSVSimpleEventStore extends CSVEntityStore {

    public CSVSimpleEventStore(CSVStore csvStore, ExpImpContext context, ExpImpStats stats, String storeName) {
        super(context, stats, csvStore, storeName);
    }

    public void load(EntityStore entityStore, String inputVersion, String outputVersion) throws Exception {
        CSVReader creader = new CSVReaderImpl(getStoreDir(), getStoreName());

        CSVSimpleEventDeserializer ed = new CSVSimpleEventDeserializer();
        HashMap emap = new HashMap();        

        for( String[] columns = creader.nextRow(); columns != null ; columns = creader.nextRow() ) {
            SimpleEventImpl e = ed.deserialize(columns,entityStore,inputVersion,outputVersion);
            emap.put(Long.valueOf(e.getId()),e);
        }
        // Add payload to each event
        CSVReader plreader = new CSVReaderImpl(getStoreDir(),getStoreName()+"-payload");
        CSVPayloadDeserializer pld = new CSVPayloadDeserializer(entityStore);
        for( String[] columns = plreader.nextRow(); columns != null ; columns = plreader.nextRow() ) {
            SimpleEventImpl e = (SimpleEventImpl)emap.get(Long.parseLong(columns[0]));
            if (e != null) {
                pld.deserialize(columns,e,inputVersion,outputVersion);
            } else {
                entityStore.getLogger().log(Level.ERROR,
                        "Loading SimpleEvent payload. Can not find event with id=%s. Payload ignored.",
                        columns[0]);
            }
        }

        if(emap.size() <= BATCH_SIZE) {
            entityStore.storeAll(emap);
            emap.clear();
        } else {
            HashMap storeMap = new HashMap(BATCH_SIZE);
            for (Iterator it = emap.keySet().iterator(); it.hasNext();) {
                storeMap.put(it.next(), emap.get(it.next()));

                if(storeMap.size() == BATCH_SIZE) {
                    entityStore.storeAll(storeMap);
                    storeMap.clear();
                }
            }
            // store the remaining events
            entityStore.storeAll(storeMap);
            storeMap.clear();
            emap.clear();
        }
    }

    public void loadProperties(DbStore dbStore, EntityStore entityStore, String inputVersion, String outputVersion) throws Exception {
    }
}
