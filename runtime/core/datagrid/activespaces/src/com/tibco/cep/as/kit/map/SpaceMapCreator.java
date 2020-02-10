package com.tibco.cep.as.kit.map;

import com.tibco.as.space.*;
import com.tibco.as.space.Member.DistributionRole;
import com.tibco.as.space.RecoveryOptions.RecoveryPolicy;
import com.tibco.as.space.SpaceDef.*;
import com.tibco.as.space.impl.data.spacedef.ASFullSpaceDef;
import com.tibco.as.space.listener.Listener;
import com.tibco.as.space.persistence.Persister;
import com.tibco.cep.as.kit.tuple.DataType;
import com.tibco.cep.as.kit.tuple.DataTypeRefMap;
import com.tibco.cep.as.kit.tuple.TupleCodec;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASConstants;
import com.tibco.cep.runtime.session.BEManagedThread;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.util.annotation.Optional;

import java.util.List;

import static com.tibco.cep.as.kit.map.SpaceMapConstants.TUPLE_COLUMN_NAME_KEY;
import static com.tibco.cep.as.kit.map.SpaceMapConstants.TUPLE_COLUMN_NAME_VALUE;

/*
 * Author: Ashwin Jayaprakash Date: Apr 24, 2009 Time: 11:37:57 PM
 */
public class SpaceMapCreator {
    
    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(SpaceMapCreator.class);

    public static <K, V> SpaceMap<K, V> create(Metaspace metaspace, Parameters<K, V> parameters) throws ASException {

        SpaceDef spacedef = SpaceDef.create();

        spacedef.setName(parameters.getSpaceName());

        spacedef.setDistributionPolicy(parameters.getDistributionPolicy());

        spacedef.setReplicationCount(parameters.getReplicationCount());

        spacedef.setReplicationPolicy(
                parameters.isSyncReplication() ? ReplicationPolicy.SYNC : ReplicationPolicy.ASYNC);

        KeyValueTupleAdaptor<K, V> tupleAdaptor = prepareFieldsAndCodec(parameters, spacedef);

        prepareLocks(parameters, spacedef);

        Tuple contextTuple = parameters.getContextTuple();
        if (contextTuple != null) {
            spacedef.setContext(contextTuple);
        }

        Persister persister = parameters.getPersister();

        spacedef.setPersistenceType(parameters.getPersistenceType());
        spacedef.setPersistencePolicy(parameters.getPersistencePolicy());

        spacedef.setPersistenceDistributionPolicy(parameters.getPersistenceDistributionPolicy());

        spacedef.setCachePolicy(parameters.getCachePolicy());
        spacedef.setForgetOldValue(parameters.isForgetOldValue());

        // Removed phase configuration - as per Tom Jul132012
        //spacedef.setPhaseCount(1);
        //spacedef.setPhaseInterval(0);
        
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        String readTimeout, writeTimeout, fileSyncInterval;
        if (cluster != null) {
        	GlobalVariables gVs = cluster.getRuleServiceProvider().getGlobalVariables();
        	readTimeout = gVs.substituteVariables(System.getProperty(ASConstants.PROP_AS_SPACE_READ_TIMEOUT, SpaceDef.create().getReadTimeout() + "")).toString();
        	writeTimeout = gVs.substituteVariables(System.getProperty(ASConstants.PROP_AS_SPACE_WRITE_TIMEOUT, SpaceDef.create().getWriteTimeout() + "")).toString();
        	fileSyncInterval = gVs.substituteVariables(System.getProperty(ASConstants.PROP_AS_FILE_SYNC_INTERVAL, SpaceDef.create().getFileSyncInterval() + "")).toString();
        } else {
        	readTimeout = System.getProperty(ASConstants.PROP_AS_SPACE_READ_TIMEOUT, SpaceDef.create().getReadTimeout() + "");
        	writeTimeout = System.getProperty(ASConstants.PROP_AS_SPACE_WRITE_TIMEOUT, SpaceDef.create().getWriteTimeout() + "");
        	fileSyncInterval = System.getProperty(ASConstants.PROP_AS_FILE_SYNC_INTERVAL, SpaceDef.create().getFileSyncInterval() + "");
        }
        
        spacedef.setReadTimeout(Long.parseLong(readTimeout));
        spacedef.setWriteTimeout(Long.parseLong(writeTimeout));
        spacedef.setFileSyncInterval(Long.parseLong(fileSyncInterval));

        long ttl = parameters.getTtl();
        if (ttl != SpaceDef.TTL_FOREVER) {
            spacedef.setTTL(ttl);
        }
        LOGGER.log(Level.INFO, "%s Entry TTL: %s", spacedef.getName(), spacedef.getTTL());
        
        // safe gaurd against the recent AS 2.3 change to enforce minSeeder > 0
        int minSeeder = parameters.getMinSeeders();
        if (minSeeder < 1) minSeeder = 1;
        spacedef.setMinSeederCount(minSeeder);

        List<IndexDef> indexDefs = parameters.getIndexDefs();
        if (indexDefs != null) {
            for (IndexDef indexDef : indexDefs) {
                spacedef.addIndexDef(indexDef);
            }
        }

        //--- HostAware Replication handling
        boolean hostAware = Boolean.valueOf(System.getProperty(SystemProperty.AS_HOST_AWARE_REPLICATION_ENABLE.getPropertyName(),
                SystemProperty.AS_HOST_AWARE_REPLICATION_ENABLE.getValidValues()[0].toString()));

        spacedef.setHostAwareReplication(hostAware);

        //-------------

        Space space = null;
        String operation = "created";
        long begin_time = System.currentTimeMillis();
        try {
            space = metaspace.getSpace(spacedef.getName(), parameters.getRole());
        } catch (ASException e) {
        }
        if (parameters.isRedefine()) {
            operation = "altered";
            metaspace.alterSpace(spacedef);
        }
        else {
            if (space == null) {
               	metaspace.defineSpace(spacedef);
            }
            else {
                operation = "already exists";
                space.getSpaceDef();
            }
        }
        long end_time = System.currentTimeMillis();
        LOGGER.log(Level.DEBUG, "Space %s %s (completed in %s ms)", spacedef.getName(), operation, (end_time - begin_time));

        space = metaspace.getSpace(spacedef.getName(), parameters.getRole());

        //-------------

        if (persister != null) {
            space.setPersister(persister);
        }

        ASFullSpaceDef def = (ASFullSpaceDef) space.getSpaceDef();
        LOGGER.log(Level.DEBUG, "Space [%s] defined as [%s]", parameters.getSpaceName(), def.export());

        return new SpaceMap<K, V>(space, tupleAdaptor);
    }
    
	/*
	 * TODO: This function is not used yet (might be added to the end of recovery to
     *		 secure quorum per space)
	 */
    public static void establishQuorum(Metaspace metaspace, Space space) throws ASException {
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        int quorum = cluster.getClusterConfig().getMinCacheServers();
        int memberCount = metaspace.getSpaceMembers(space.getName()).size();
    	while (memberCount < quorum) {
    		try {
				Thread.sleep(1000);
	            LOGGER.log(Level.INFO, "Establishing quorum for space [%s] with %s members currently available", 
	                    space.getName(), memberCount);
			} catch (InterruptedException e) {
			}
            memberCount = metaspace.getSpaceMembers(space.getName()).size();
    	}
        LOGGER.log(Level.INFO, "Established quorum for space [%s] with %s members currently available", 
                		space.getName(), memberCount);
    }   

    public static boolean performSpaceRecovery(Metaspace metaspace, Space space) throws ASException {

        if (space.getSpaceState() == SpaceState.READY) {
            LOGGER.log(Level.INFO, "Skipping space [%s] recovery due to ready space state=%s", 
            		space.getName(), space.getSpaceState());
            return false;
        }
        if (space.getSpaceDef().getPersistenceType() != PersistenceType.SHARE_NOTHING) {
            LOGGER.log(Level.DEBUG, "Skipping space [%s] recovery due to persistence type=%s", 
                    space.getName(), space.getSpaceDef().getPersistenceType());
            return false;
        }
        /** 
		BE-22526: Role transition to SEEDER might be delayed in AS, and therefore this call is not 
                  dependable (especially right after getSpace returns)
        if (metaspace.getSelfMember().getDistributionRole(space.getName()) != DistributionRole.SEEDER) {
            LOGGER.log(Level.DEBUG, "Skipping space [%s] recovery due to distribution role=%s", 
                    space.getName(), metaspace.getSelfMember().getDistributionRole(space.getName()));
            return false;
        }
        */

        LOGGER.log(Level.DEBUG, "Recovering space [%s] recovery persistence-type=%s distribution-role=%s", space.getName(), 
                space.getSpaceDef().getPersistenceType(), metaspace.getSelfMember().getDistributionRole(space.getName()));

        LOGGER.log(Level.INFO, "Space [%s] recovery is about to begin state=%s", space.getName(), space.getSpaceState());

        RecoveryOptions recoveryOptions = RecoveryOptions.create().setLoadWithData(true);

        String strategy = System.getProperty(SystemProperty.DATAGRID_RECOVERY_GENERALIZED_STRATEGY.getPropertyName(), /* alternative */
                          System.getProperty(SystemProperty.DATAGRID_RECOVERY_DISTRIBUTED_STRATEGY.getPropertyName(), "no_data_loss"));
        // Validate settings to avoid exception
        if ("NO_DATA_LOSS,EXACT_CLUSTER,FAST_LOAD_ONLY,ROBUST_LOAD_ONLY,FORCE_LOAD".indexOf(strategy.toUpperCase()) < 0) {
            strategy = "no_data_loss";
        }
        
        if (("EXACT_CLUSTER".equalsIgnoreCase(strategy) || "FAST_LOAD_ONLY".equalsIgnoreCase(strategy)) && 
        	(space.getSpaceDef().getReplicationCount() < 0)) {
            // Replicated spaces
            recoveryOptions.setRecoveryPolicy(RecoveryPolicy.NO_DATA_LOSS);
        } else {
            // Distributed spaces
            recoveryOptions.setRecoveryPolicy(RecoveryPolicy.valueOf(strategy.toUpperCase()));
        }
        
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        int quorum = cluster.getClusterConfig().getMinCacheServers();
        recoveryOptions.setQuorum(quorum);
        LOGGER.log(Level.INFO, "Setting quorum to [%s] for %s recovery with policy=%s state=%s members=%s",
                quorum, space.getName(), recoveryOptions.getRecoveryPolicy(), space.getSpaceState(), space.getMembers().size());

        long begin_time = System.currentTimeMillis();
        if (BEManagedThread.tryAcquireReadLock()) {
            int loadTries = 0;
            boolean loadNeeded = true;
            while (loadNeeded) {
                try {
                    metaspace.recoverSpace(space.getName(), recoveryOptions);
	                for ( ; !space.waitForReady(60 * 1000) ; ) {
                        LOGGER.log(Level.INFO, "Space [%s] recovery in progress. Current space state [%s]",
                                space.getName(), space.getSpaceState());
                    }
                    loadNeeded = false;
                }
                catch (ASException asex) {
                    // Try upto 3 times, in order to handle false exceptions
                    // from ActiveSpaces due to timings - before giving up. 
                    if (loadTries++ > 2) {
                        BEManagedThread.releaseReadLock();
                        throw asex;
                    }
                    LOGGER.log(Level.WARN, "Space [%s] recovery failed. Exception: %s [retry=%s]",
                            space.getName(), asex.getMessage(), loadTries);
                    try {
                        Thread.sleep(30 * 1000);
                    } catch (Exception ignore) {
                    }
                }
            }
            BEManagedThread.releaseReadLock();
        }
        else {
            LOGGER.log(Level.ERROR,
                    "Space [%s] recovery has been aborted as the BE shared managed lock could not be acquired",
                    space.getName());
            return false;
        }
        long end_time = System.currentTimeMillis();

        try {
	        LOGGER.log(Level.INFO, "Space [%s] recovery completed in [%s]ms. Current number of items in space [%d]",
	                space.getName(), (end_time - begin_time), space.size());
		}
		catch (ASException asex) {
			// When there are many nodes, calling space.size() causes OPERATION_TIMEOUT
	        LOGGER.log(Level.INFO, "Space [%s] recovery completed in [%s]ms. Current number of items in space [%s]",
	                space.getName(), (end_time - begin_time), "unknown");
		}
        
        return true;
    }

    private static <K, V> KeyValueTupleAdaptor<K, V> prepareFieldsAndCodec(Parameters<K, V> parameters,
                                                                           SpaceDef spacedef) {
        String keyName =
                parameters.getKeyColumnName() == null ? TUPLE_COLUMN_NAME_KEY : parameters.getKeyColumnName();
        DataType keyType = DataTypeRefMap.mapToDataType(parameters.getKeyClass());
        FieldDef nameField = FieldDef.create(keyName, keyType.getFieldType());
        spacedef.putFieldDef(nameField);
        //spacedef.setKey(nameField.getName());
        spacedef.setKeyDef(KeyDef.create().setIndexType(IndexDef.IndexType.HASH).setFieldNames(nameField.getName()));

        TupleCodec tupleCodec = parameters.getTupleCodec();
        KeyValueTupleAdaptor<K, V> tupleAdaptor = null;
        List<FieldDef> fieldDefs = parameters.getExplicitFieldDefs();
        if (fieldDefs == null) {
            String valueName =
                    parameters.getValueColumnName() == null ? TUPLE_COLUMN_NAME_VALUE : parameters.getValueColumnName();

            DataType valueType = DataTypeRefMap.mapToDataType(parameters.getValueClass());
            FieldDef valueField = FieldDef.create(valueName, valueType.getFieldType());
            valueField.setNullable(true);
            spacedef.putFieldDef(valueField);

            if (tupleCodec instanceof KeyValueTupleAdaptor) {
                tupleAdaptor = (KeyValueTupleAdaptor<K, V>) tupleCodec;
            }
            else {
                tupleAdaptor = new KeyValueTupleAdaptor<K, V>(nameField.getName(), keyType,
                        valueField.getName(), valueType, tupleCodec);
            }
        }
        else {
            String[] fieldNames = new String[fieldDefs.size()];
            DataType[] dataTypes = new DataType[fieldDefs.size()];
            int i = 0;

            for (FieldDef fieldDef : fieldDefs) {
                spacedef.putFieldDef(fieldDef);

                fieldNames[i] = fieldDef.getName();
                dataTypes[i++] = DataTypeRefMap.mapToDataType(fieldDef.getType());
            }

            tupleAdaptor = (KeyValueTupleAdaptor<K, V>) tupleCodec;
        }

        return tupleAdaptor;
    }

    private static <K, V> void prepareLocks(Parameters<K, V> parameters, SpaceDef spacedef) {
    	Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
    	String lockTTL;
    	if (cluster != null) {
    		GlobalVariables gVs = cluster.getRuleServiceProvider().getGlobalVariables();
    		lockTTL = gVs.substituteVariables(System.getProperty(ASConstants.PROP_LOCK_TTL, SpaceDef.DEFAULT_LOCK_TTL + "")).toString();
    	} else {
    		lockTTL = System.getProperty(ASConstants.PROP_LOCK_TTL, SpaceDef.DEFAULT_LOCK_TTL + "");
    	}
        spacedef.setLockTTL(Long.parseLong(lockTTL));

        if (parameters.getLimitedCacheCapacity() > SpaceDef.NO_CAPACITY) {
            /*
             * ActiveSpaces Rules:
             * Handle miss is only done with following conditions
             * - Capacity is set [ Capacity > SpaceDef.NO_CAPACITY ]
             * - LRU eviction policy is used
             * - Shared-all persistence is used
             * Note: When capacity is set but eviction policy is none,
             * AS will throw exception if user attempts to go over capacity.
             * Since there is no need for eviction input is rejected,
             * handle miss (load-from-persistence) will not happen.
             */
            spacedef.setCapacity(parameters.getLimitedCacheCapacity());
            spacedef.setEvictionPolicy(EvictionPolicy.LRU);
        } else {
            spacedef.setCapacity(SpaceDef.NO_CAPACITY);
        }

        String lockScope = System.getProperty(ASConstants.PROP_LOCK_SCOPE, LockScope.PROCESS.name());
        spacedef.setLockScope(LockScope.valueOf(lockScope));
    }

    //-------------

    public static class Parameters<K, V> {
        protected String spaceName;

        protected DistributionPolicy distributionPolicy;

        protected int minSeeders;

        protected int replicationCount;

        protected boolean syncReplication;

        protected DistributionRole role;

        //@Optional
        protected String keyColumnName;

        protected Class<K> keyClass;

        //@Optional
        protected String valueColumnName;

        protected Class<V> valueClass;

        //@Optional
        protected List<FieldDef> explicitFieldDefs;

        //@Optional
        protected List<IndexDef> indexDefs;

        protected TupleCodec tupleCodec;

        protected long limitedCacheCapacity;

        //@Optional
        protected Persister persister;

        //@Optional
        protected Listener listener;

        protected Tuple contextTuple;

        protected long ttl;

        /**
         * Default set to none
         */
        protected PersistenceType persistenceType = PersistenceType.NONE;

        /**
         * Default set to sync. Should we allow this to be overridden?
         */
        protected PersistencePolicy persistencePolicy = PersistencePolicy.ASYNC;

        /**
         * Policy affects how share-all persister in AS functions. Set this policy to dist by default.
         */
        protected DistributionPolicy persistenceDistributionPolicy = DistributionPolicy.DISTRIBUTED;

        @Optional
        protected String localStorePath;

        protected CachePolicy cachePolicy = CachePolicy.READ_WRITE_THROUGH;

        protected boolean forgetOldValue = false;

        protected boolean redefine = false;


        public Parameters() {
            this.syncReplication = true;
            this.replicationCount = 0;
            this.role = DistributionRole.NO_ROLE;
            this.limitedCacheCapacity = SpaceDef.NO_CAPACITY;
            this.ttl = SpaceDef.TTL_FOREVER;
        }

        public String getSpaceName() {
            return spaceName;
        }

        public Parameters<K, V> setSpaceName(String spaceName) {
            this.spaceName = spaceName;

            return this;
        }

        public DistributionPolicy getDistributionPolicy() {
            return distributionPolicy;
        }

        public Parameters<K, V> setDistributionPolicy(DistributionPolicy distributionPolicy) {
            this.distributionPolicy = distributionPolicy;

            return this;
        }

        public Parameters<K, V> setPersistenceDistributionPolicy(DistributionPolicy distributionPolicy) {
            this.persistenceDistributionPolicy = distributionPolicy;
            return this;
        }

        public DistributionPolicy getPersistenceDistributionPolicy() {
            return this.persistenceDistributionPolicy;
        }

        public PersistencePolicy getPersistencePolicy() {
            return persistencePolicy;
        }

        public Parameters<K, V> setPersistencePolicy(PersistencePolicy persistencePolicy) {
            this.persistencePolicy = persistencePolicy;

            return this;
        }

        public PersistenceType getPersistenceType() {
            return persistenceType;
        }

        public Parameters<K, V> setPersistenceType(PersistenceType persistenceType) {
            this.persistenceType = persistenceType;

            return this;
        }

        @Optional
        public String getLocalStorePath() {
            return localStorePath;
        }

        @Optional
        /**
         * Required for shared nothing
         */
        public Parameters<K, V> setLocalStorePath(String localStorePath) {
            this.localStorePath = localStorePath;

            return this;
        }

        public int getMinSeeders() {
            return minSeeders;
        }

        public Parameters<K, V> setMinSeeders(int minSeeders) {
            this.minSeeders = minSeeders;

            return this;
        }

        public int getReplicationCount() {
            return replicationCount;
        }

        public Parameters<K, V> setReplicationCount(int replicationCount) {
            this.replicationCount = replicationCount;

            return this;
        }

        public boolean isSyncReplication() {
            return syncReplication;
        }

        public Parameters<K, V> setSyncReplication(boolean syncReplication) {
            this.syncReplication = syncReplication;

            return this;
        }

        public DistributionRole getRole() {
            return role;
        }

        public Parameters<K, V> setRole(DistributionRole role) {
            this.role = role;

            return this;
        }

        public String getKeyColumnName() {
            return keyColumnName;
        }

        //@Optional
        public Parameters<K, V> setKeyColumnName(String keyColumnName) {
            this.keyColumnName = keyColumnName;

            return this;
        }

        public Class<K> getKeyClass() {
            return keyClass;
        }

        public Parameters<K, V> setKeyClass(Class<K> keyClass) {
            this.keyClass = keyClass;

            return this;
        }

        public String getValueColumnName() {
            return valueColumnName;
        }

        //@Optional
        public Parameters<K, V> setValueColumnName(String valueColumnName) {
            this.valueColumnName = valueColumnName;

            return this;
        }

        public Class<V> getValueClass() {
            return valueClass;
        }

        public Parameters<K, V> setValueClass(Class<V> valueClass) {
            this.valueClass = valueClass;

            this.explicitFieldDefs = null;

            return this;
        }

        public List<FieldDef> getExplicitFieldDefs() {
            return explicitFieldDefs;
        }

        public Parameters<K, V> setExplicitFieldDefs(List<FieldDef> explicitFieldDefs) {
            this.explicitFieldDefs = explicitFieldDefs;

            this.valueClass = null;
            this.valueColumnName = null;

            return this;
        }

        public List<IndexDef> getIndexDefs() {
            return indexDefs;
        }

        public Parameters<K, V> setIndexDefs(List<IndexDef> indexDefs) {
            this.indexDefs = indexDefs;

            return this;
        }

        public TupleCodec getTupleCodec() {
            return tupleCodec;
        }

        /**
         * @param tupleCodec If this is a {@link KeyValueTupleAdaptor} then it will be used directly.
         * @return
         */
        public Parameters<K, V> setTupleCodec(TupleCodec tupleCodec) {
            this.tupleCodec = tupleCodec;

            return this;
        }

        public long getLimitedCacheCapacity() {
            return limitedCacheCapacity;
        }

        public Parameters<K, V> setLimitedCacheCapacity(long limitedCacheCapacity) {
            this.limitedCacheCapacity = limitedCacheCapacity;

            return this;
        }

        public Persister getPersister() {
            return persister;
        }

        public Parameters<K, V> setPersister(Persister persister) {
            this.persister = persister;

            return this;
        }

        public Tuple getContextTuple() {
            return contextTuple;
        }

        public Parameters<K, V> setContextTuple(Tuple contextTuple) {
            this.contextTuple = contextTuple;

            return this;
        }

        public long getTtl() {
            return ttl;
        }

        public Parameters<K, V> setTtl(long ttl) {
            this.ttl = ttl;

            return this;
        }

        public Listener getListener() {
            return listener;
        }

        public Parameters<K, V> setListener(Listener listener) {
            this.listener = listener;

            return this;
        }

        public Parameters<K, V> setCachePolicy(CachePolicy cachePolicy) {
            this.cachePolicy = cachePolicy;

            return this;
        }

        public CachePolicy getCachePolicy() {
            return cachePolicy;
        }

        public Parameters<K, V> setForgetOldValue(boolean forgetOldValue) {
            this.forgetOldValue = forgetOldValue;

            return this;
        }

        public boolean isForgetOldValue() {
            return forgetOldValue;
        }

        public boolean isRedefine() {
            return redefine;
        }

        public Parameters<K, V> setRedefine(
                boolean redefine) {

            this.redefine = redefine;
            return this;
        }
    }
}
