package bdb;

import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.service.om.impl.datastore.DataStoreFactory;
import com.tibco.cep.runtime.service.om.impl.datastore.DataStoreEnv;
import com.tibco.cep.runtime.service.om.impl.datastore.DataStore;
import com.tibco.cep.runtime.service.om.impl.datastore.berkeleydb.BerkeleyDBFactory;
import com.tibco.cep.runtime.service.om.OMException;
import com.tibco.cep.runtime.service.om.OMInitException;
import com.tibco.cep.runtime.service.om.DefaultSerializer;
import com.tibco.cep.cep_commonVersion;

import java.util.LinkedList;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Nov 16, 2006
 * Time: 2:06:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBSample implements Loader {
    //BDB stuff
    DataStoreEnv     env;
    DataStoreFactory dbfactory;

    final DefaultSerializer   defaultSerializer;
    final ConceptLoader       conceptLoader;
    final RuleServiceProvider provider;
    final Properties          dbConfig;



    DBSample(String traFile, String repoUrl, String dbDir) throws Exception {
        Properties env = new Properties();
        env.put("tibco.repourl", repoUrl);
        env.put("be.bootstrap.property.file", traFile);

        provider = RuleServiceProviderManager.getInstance().newProvider("MannersTest", env);
        provider.configure(RuleServiceProvider.MODE_LOAD_ONLY);

        dbConfig = new Properties();
        dbConfig.put("omDbEnvDir", dbDir);

        defaultSerializer = new DefaultSerializer(provider);
        conceptLoader     = new ConceptLoader(this);

        conceptLoader.init();
    }

    public void load() throws OMException {
        conceptLoader.load();
    }

    public void shutdown() throws OMException {
        conceptLoader.shutdown();

        env.close();
    }

    public static void main(String[] args) {
        try {
            String traFile  = args[0];
            String repourl  = args[1];
            String dbDir   = args[2];

            DBSample dbsample = new DBSample(traFile, repourl, dbDir);

            //peform load
            dbsample.load();

            //done - shutdown
            dbsample.shutdown();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    DataStoreFactory dbFactory() throws OMException {
        if (dbfactory != null)
            return dbfactory;
        else {
            String dbType = provider.getProperties().getProperty("be.engine.om.datastore.factory", BerkeleyDBFactory.class.getName());

            try {
                Class dbFactoryClass = Class.forName(dbType);
                dbfactory = (DataStoreFactory) dbFactoryClass.newInstance();
                dbfactory.setLogger(provider.getLogger());
            } catch (Exception e) {
                provider.getLogger().logError("", e);
                throw new OMInitException(e);
            }

            env = dbfactory.createDataStoreEnv(dbConfig);
//            checkDbVersion();
            return dbfactory;
        }
    }

    void checkDbVersion() throws OMException {
        if( dbfactory.checkBEVersionDSExists(dbConfig) ) {
            // dbversion >= 1.1 already.
            checkVersionRecord();
        } else {
            if(dbfactory.checkConceptDSExists(dbConfig)) {
                // No BEVersion table but Concept table already exists. dbversion == 1.0
                throw new OMException("Engine and Database Version mismatch!. Engine Version = " + cep_commonVersion.version + " Database Version = 1.0.0" );
            } else {
                // looks like the db is being created anew. No problem.
                createVersionRecord();
            }
        }
    }


    static class VersionRecord {
        private static final String BE_VERSION_RECORD="BEVersionRecord";
        public String db_be_version;
    }

    void createVersionRecord() throws OMException {
        DataStore versionds = dbfactory.createBEVersionDataStore(dbConfig, defaultSerializer);
        versionds.init();
        versionds.add(null, VersionRecord.BE_VERSION_RECORD, cep_commonVersion.version);
        versionds.close();
    }

    void checkVersionRecord() throws OMException {
        DataStore versionds = dbfactory.createBEVersionDataStore(dbConfig, defaultSerializer);
        versionds.init();
        VersionRecord versionrec = new VersionRecord();
        versionds.fetch(null, VersionRecord.BE_VERSION_RECORD, versionrec);
        versionds.close();
        if( ! compareVersions(cep_commonVersion.version, versionrec.db_be_version)) {
            //if( ! versionrec.db_be_version.equals(be_engineVersion.version)) {
            // Version mismatch!
            throw new OMException("Engine and Database Version mismatch and versions are incompatible!. Engine Version = " + cep_commonVersion.version + " Database Version = " + versionrec.db_be_version);
        }
    }

    boolean compareVersions(String engine_version, String db_version) {
        String[] engineVersions = {"1.0.0",
                "1.1.0",
                "1.1.1",
                "1.2.0",
                "1.2.1",
                "1.3.0",
                "1.3.1",
                "1.4.0",
                "2.0.0" };
        String[][] compatibleDbVersions = {{}, // 1.0.0 only compatible with itself.
                {}, // 1.1.0 only compatible with itself.
                {"1.1.0"},
                {"1.1.0", "1.1.1"},
                {"1.1.0", "1.1.1", "1.2.0" },
                {"1.1.0", "1.1.1", "1.2.0", "1.2.1"},
                {"1.1.0", "1.1.1", "1.2.0", "1.2.1", "1.3.0"},
                {"1.1.0", "1.1.1", "1.2.0", "1.2.1", "1.3.0", "1.3.1"},
                {"1.1.0", "1.1.1", "1.2.0", "1.2.1", "1.3.0", "1.3.1", "1.4.0"} };
        assert (engineVersions.length == compatibleDbVersions.length) : ("engineVersions.length=" + engineVersions.length + " compatibleDbVersions.length=" + compatibleDbVersions.length) ;

        // Simple case first.
        if(db_version.equals(engine_version))
            return true;

        int i,j;
        for (i = 0; i < engineVersions.length; i++) {
            if(engineVersions[i].equals(engine_version))
                break;
        }

        if(i == engineVersions.length)
            return false;

        for(j = 0; j < compatibleDbVersions[i].length; j++) {
            if(compatibleDbVersions[i][j].equals(db_version))
                return true;
        }

        return false;
    }
}
