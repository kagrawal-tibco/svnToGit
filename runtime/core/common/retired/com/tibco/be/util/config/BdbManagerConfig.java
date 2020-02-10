package com.tibco.be.util.config._retired_;

import com.tibco.be.util.config.AbstractArtifactConfig;
import com.tibco.be.util.config.EvictionConfig;
import com.tibco.be.util.config.IdResolver;
import com.tibco.be.util.config.ObjectManagerConfig;
import com.tibco.be.util.config.PropertyGroupConfig;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.runtime.service.om.impl.datastore._retired_.PersistentStore;

import java.util.Properties;

/*
* @author pdhar
*
*/


public class BdbManagerConfig
        extends AbstractArtifactConfig
        implements ObjectManagerConfig {


    private String deleteRetracted;
    private String checkpointInterval;
    private String checkpointOpsLimit;
    private String dbDir;
    private EvictionConfig eviction;
    private String propertyCacheSize;
    private PropertyGroupConfig propertyGroup;
    private String skipRecovery;


    public BdbManagerConfig(
            IdResolver idResolver,
            String id,
            String name,
            String checkpointInterval,
            String checkpointOpsLimit,
            String dbDir,
            String deleteRetracted,
            EvictionConfig eviction,
            String propertyCacheSize,
            PropertyGroupConfig propertyGroup,
            String skipRecovery) {
        super(idResolver, id, name);
        this.checkpointInterval = checkpointInterval;
        this.checkpointOpsLimit = checkpointOpsLimit;
        this.dbDir = dbDir;
        this.deleteRetracted = deleteRetracted;
        this.eviction = eviction;
        this.propertyCacheSize = propertyCacheSize;
        this.propertyGroup = propertyGroup;
        this.skipRecovery = skipRecovery;
    }


    public EvictionConfig getEviction() {
        return this.eviction;
    }


    public long getCheckpointInterval() {
        return Long.valueOf(this.checkpointInterval);
    }


    public long getCheckpointOpsLimit() {
        return Long.valueOf(this.checkpointOpsLimit);
    }


    public String getDbDir() {
        return this.dbDir;
    }


    public long getPropertyCacheSize() {
        return Long.valueOf(this.propertyCacheSize);
    }


    public PropertyGroupConfig getPropertyGroup() {
        return this.propertyGroup;
    }


    public boolean isDeleteRetracted() {
        return Boolean.valueOf(this.deleteRetracted);
    }


    public boolean isSkipRecovery() {
        return Boolean.valueOf(skipRecovery);
    }


    public Properties toProperties() {
        final Properties p = new Properties();
        if (null != this.propertyGroup) {
            p.putAll(this.propertyGroup.toProperties());
        }
        p.setProperty(Constants.PROPERTY_NAME_OM_ENABLE, Constants.PROPERTY_NAME_OM_BDB);
        p.setProperty(Constants.PROPERTY_NAME_OM_CLASS, PersistentStore.class.getName());
        p.setProperty(Constants.PROPERTY_NAME_OM_BDB_CHECKPOINT_INTERVAL, this.checkpointInterval);
        p.setProperty(Constants.PROPERTY_NAME_OM_BDB_CHECKPOINT_OPS_LIMIT, this.checkpointOpsLimit);
        p.setProperty(Constants.PROPERTY_NAME_OM_BDB_DB_ENV_DIR, this.dbDir);
        p.setProperty(Constants.PROPERTY_NAME_OM_BDB_DELETE_POLICY, this.deleteRetracted);
        //todo: eviction properties?
        p.setProperty(Constants.PROPERTY_NAME_OM_BDB_PROPERTY_CACHE_SIZE, this.propertyCacheSize);
        p.setProperty(Constants.PROPERTY_NAME_OM_BDB_NO_RECOVERY_REQD, this.skipRecovery);
        return p;
    }
}
