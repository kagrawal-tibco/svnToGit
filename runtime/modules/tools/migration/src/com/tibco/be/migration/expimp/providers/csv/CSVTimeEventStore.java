package com.tibco.be.migration.expimp.providers.csv;

import java.util.HashMap;

import com.tibco.be.migration.CSVReader;
import com.tibco.be.migration.CSVReaderImpl;
import com.tibco.be.migration.expimp.ExpImpContext;
import com.tibco.be.migration.expimp.ExpImpStats;
import com.tibco.be.migration.expimp.providers.db.DbStore;
import com.tibco.be.migration.expimp.providers.db.EntityStore;
import com.tibco.cep.kernel.model.entity.Event;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 26, 2008
 * Time: 3:00:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class CSVTimeEventStore extends CSVEntityStore {

    public CSVTimeEventStore(CSVStore csvStore, ExpImpContext context, ExpImpStats stats, String storeName) {
        super(context,stats,csvStore,storeName);
    }


    public void load(EntityStore entityStore, String inputVersion, String outputVersion) throws Exception {
        if(inputVersion.startsWith("1.4")) {
          return;
        }
        CSVReader creader = new CSVReaderImpl(getStoreDir(), getStoreName());

        CSVTimeEventDeserializer ed = null;
        if (getStoreName() == CSVStore.STATE_TIMEOUT_EVENT_FILE_NAME)
            ed = new CSVStateTimeoutEventDeserializer();
        else
            ed = new CSVTimeEventDeserializer();

        HashMap emap = new HashMap();

        
        for( String[] columns = creader.nextRow(); columns != null ; columns = creader.nextRow() ) {

            Event e = ed.deserialize(columns,entityStore,inputVersion,outputVersion);

            emap.put(Long.valueOf(e.getId()),e);
            if(emap.size() == BATCH_SIZE) {
              entityStore.storeAll(emap);
              emap.clear();
            }

        }
        entityStore.storeAll(emap);
        emap.clear();
    }

    public void loadProperties(DbStore dbStore, EntityStore entityStore, String inputVersion, String outputVersion) throws Exception {

    }


}
