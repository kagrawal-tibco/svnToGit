package bdb;

import com.tibco.cep.runtime.service.om.impl.datastore.DataStoreFactory;
import com.tibco.cep.runtime.service.om.impl.datastore.DataStore;
import com.tibco.cep.runtime.service.om.OMException;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Nov 16, 2006
 * Time: 2:10:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConceptLoader implements Loader {
    DataStore ds;
    DBSample dbsample;


    ConceptLoader(DBSample _dbsample) {
        dbsample = _dbsample;
    }

    void init() throws OMException {
        ds = dbsample.dbFactory().createConceptDataStore(dbsample.dbConfig, dbsample.defaultSerializer);
        ds.init();
    }

    void shutdown() {
        if (ds != null) {
            ds.close();
            ds = null;
        }
    }

    public void load() throws OMException {
        final int[] count = new int[1];
        dbsample.provider.getLogger().logInfo("Loading concept instances...");
        ds.readAll(new DataStore.ReadCallback() {
            public void readObj(Object obj) throws Exception {
                count[0]++;
                ConceptImpl c = (ConceptImpl) obj;
                //do your SQL stuff here
                if (dbsample.provider.getLogger().isDebug())
                    dbsample.provider.getLogger().logDebug("instance: " + c);

                if (count[0] % 1000 == 0) {
                    dbsample.provider.getLogger().logInfo("Loaded " + count[0] + " Concept Instances");
                }
            }
        });
        dbsample.provider.getLogger().logInfo("Finished loading concept instances");
    }
}
