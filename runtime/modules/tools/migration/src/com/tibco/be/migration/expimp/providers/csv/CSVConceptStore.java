package com.tibco.be.migration.expimp.providers.csv;

import java.util.HashMap;

import com.tibco.be.migration.CSVReader;
import com.tibco.be.migration.CSVReaderImpl;
import com.tibco.be.migration.expimp.ExpImpContext;
import com.tibco.be.migration.expimp.ExpImpStats;
import com.tibco.be.migration.expimp.providers.db.DbStore;
import com.tibco.be.migration.expimp.providers.db.EntityStore;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.element.Concept;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 25, 2008
 * Time: 9:30:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class CSVConceptStore extends CSVEntityStore {

    public CSVConceptStore(CSVStore csvStore, ExpImpContext context, ExpImpStats stats, String storeName) {
        super(context, stats, csvStore, storeName);
    }

    public void load(EntityStore entityStore, String inputVersion, String outputVersion) throws Exception {

        CSVReader creader = new CSVReaderImpl(getStoreDir(), getStoreName());

        CSVConceptDeserializer ds = new CSVConceptDeserializer();
        HashMap cmap = new HashMap();

        for (String[] columns = creader.nextRow(); columns != null; columns = creader.nextRow()) {

            if (columns.length == 0) {
                continue;
            }
            
            Concept c = null;
            try {
                c = ds.deserialize(columns, entityStore, inputVersion, outputVersion);
            }
            catch (Exception e) {
                entityStore.getLogger().log(Level.ERROR, e, e.getMessage());
                continue;
            }

            cmap.put(new Long(c.getId()), c);
            if (cmap.size() == BATCH_SIZE) {
                entityStore.storeAll(cmap);
                cmap.clear();
            }

        }
        entityStore.storeAll(cmap);
        cmap.clear();


    }

    public void loadProperties(DbStore dbStore, EntityStore entityStore, String inputVersion, String outputVersion) throws Exception {
        // read the properties
        CSVReader preader = new CSVReaderImpl(getStoreDir(), getStoreName() + "-properties");
        CSVPropertyDeserializer pd = new CSVPropertyDeserializer();
        HashMap cmap = new HashMap();

        for (String[] columns = preader.nextRow(); columns != null; columns = preader.nextRow()) {
            if(columns.length < 8) {
                entityStore.getLogger().log(Level.ERROR,
                        "Incorrect concept property CSV data structure for concept id: %s. Ignored.",
                        columns[0]);
                continue;
            }

            Concept c = null;
            long ceptId = Long.parseLong(columns[0]);
            if (ceptId > 0) {
                try {
                    c = pd.deserialize(getLogger(), columns, entityStore, inputVersion, outputVersion);
                }
                catch (Exception e) {
                    entityStore.getLogger().log(Level.ERROR, e, "%s. Property: %s of concept id: %s. Ignored.",
                            e.getMessage(), columns[1], columns[0]);
                    continue;
                }
            } else {
                entityStore.getLogger().log(Level.ERROR, "Concept id: %s is not valid. Property name: %s. Ignored.",
                        columns[0], columns[1]);
                continue;
            }

            if (c != null) {
                cmap.put(columns[0], c);
            } else continue;
            
            if (cmap.size() == BATCH_SIZE) {
                entityStore.storeAll(cmap);
                cmap.clear();
            }
        }

        // store the remaining data
        entityStore.storeAll(cmap);
        cmap.clear();
    }
}
