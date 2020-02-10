package com.tibco.cep.persister;

import com.sleepycat.je.EnvironmentLockedException;
import com.tibco.as.space.*;
import com.tibco.as.space.persistence.Persister;
import com.tibco.cep.as.kit.map.KeyValueTupleAdaptor;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.as.kit.map.SpaceMapCreator;
import com.tibco.cep.as.kit.tuple.SerializableTupleCodec;
import com.tibco.cep.impl.service.DefaultThreadFactory;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.persister.bdb.BDBControlPersister;
import com.tibco.cep.persister.bdb.BDBDataPersister;
import com.tibco.cep.persister.bdb.BDBProvider;
import com.tibco.cep.persister.membership.ASGMPListener;
import com.tibco.cep.persister.membership.SpaceOwnership;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMembershipService;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASPersistenceProvider;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.util.SystemProperty;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by IntelliJ IDEA.
 * User: pgowrish
 * Date: 1/10/12
 * Time: 11:21 AM
 * This class implements a persistence interface for ActiveSpaces
 * The following is the sequence in which the methods in this class must be called
 * 1) Constructor
 * 2) initializeControlSpace
 * 3) startMembershipListener
 * 4) initDBProvider
 * 5) createPersisterImpl
 */
public class ASBDBPersistenceProviderImpl implements ASPersistenceProvider {

    public static final Logger logger = LogManagerFactory.getLogManager().getLogger(ASBDBPersistenceProviderImpl.class);

    private static ASBDBPersistenceProviderImpl _manager = null;
    private volatile ControlSpaceOwnershipStatus _status;
    private volatile boolean _dbInitialized = false;
    private BDBProvider _bdbProvider = null;

    protected Cluster cluster = null;
    protected Metaspace metaspace = null;
    protected ASGMPListener listener = null;
    protected Space persistenceSpace = null;
    protected KeyValueTupleAdaptor kvTupleAdaptor;
    protected ExecutorService executorService;
    protected ReentrantLock reLock;
    protected Condition listenerInitializedCondition;
    protected Condition dbInitializedCondition;
    protected String dataStorePath;

    protected int dbInitNumRetries = 0;
    protected int dbInitNumRetriesMax = 0;
    protected int dbInitRetryInterval = 0;

    public ASBDBPersistenceProviderImpl() {
        this.reLock = new ReentrantLock();
        this.listenerInitializedCondition = reLock.newCondition();
        this.dbInitializedCondition = reLock.newCondition();
        this._status =  ControlSpaceOwnershipStatus.INIT;
    }

    @Override
    public void initializeControlSpace(Metaspace metaspace, Cluster cluster,String storePath) throws ASException {

        String s = System.getProperty(SystemProperty.CLUSTER_NODE_ISSEEDER.getPropertyName(), "false");
        boolean storageEnabled = Boolean.parseBoolean(s);
        if( storageEnabled ) {
            SpaceMapCreator.Parameters parameters =
            new SpaceMapCreator.Parameters()
                    .setReplicationCount(SpaceDef.REPLICATE_ALL)
                    .setSpaceName(ASPersistenceConstants.PersistenceMembershipSpace)
                    .setRole(Member.DistributionRole.SEEDER)
                    .setDistributionPolicy(SpaceDef.DistributionPolicy.DISTRIBUTED)
                    .setKeyClass(String.class)
                    .setValueClass(SpaceOwnership.class)
                    .setTupleCodec(new SerializableTupleCodec());

            SpaceMap spaceMap = SpaceMapCreator.create(metaspace, parameters);
            persistenceSpace = spaceMap.getSpace();
            kvTupleAdaptor = spaceMap.getTupleAdaptor();
            this.metaspace = metaspace;
            this.cluster = cluster;
            this.dataStorePath = storePath;

            Properties properties = cluster.getRuleServiceProvider().getProperties();
            String retryInterval = properties.getProperty(SystemProperty.CLUSTER_AS_BDB_RETRY_INTERVAL.getPropertyName(),"300" );
            this.dbInitRetryInterval  = Integer.parseInt(retryInterval);

            String numRetries = properties.getProperty(SystemProperty.CLUSTER_AS_BDB_NUM_RETRIES.getPropertyName(),"10");
            this.dbInitNumRetriesMax  = Integer.parseInt(numRetries);

            logger.log(Level.INFO, "Created ASBDBPersistenceProviderImpl.");
        }
    }

    @Override
    public void startMembershipListener() throws ASException {

        String s = System.getProperty(SystemProperty.CLUSTER_NODE_ISSEEDER.getPropertyName(), "false");
        boolean storageEnabled = Boolean.parseBoolean(s);
        if( storageEnabled ) {
            //Create a BE GMP listener for the persistenceSpace
            GroupMembershipService gmp = cluster.getGroupMembershipService();
            listener = new ASGMPListener(cluster,metaspace);

            //this thread will kick-start the ASGMPListener
            String poolName = getClass().getSimpleName() + ".MembershipServiceProcessor";
            this.executorService = Executors.newSingleThreadExecutor(new DefaultThreadFactory(poolName));
            this.executorService.submit(listener);
            gmp.addGroupMemberServiceListener(listener);
        }
    }

    public boolean initDBProvider() {

        reLock.lock();
        //Create the BDB persister if you are the owner of the control space
        if( _status == ControlSpaceOwnershipStatus.OWNER ) {
            try{
                if(_bdbProvider == null)  {
                        _bdbProvider = new BDBProvider(this.metaspace,dataStorePath);
                }

                if( dbInitNumRetries < dbInitNumRetriesMax ) {
                    if(!_dbInitialized){
                        _dbInitialized = _bdbProvider.initialize();
                    }
                } else {
                    logger.log(Level.ERROR, "Unable to create Berkeley DB environment after multiple attempts!!");
                }

            } catch (EnvironmentLockedException e) {
                logger.log(Level.ERROR, e.getMessage());
                dbInitNumRetries++;
                logger.log(Level.ERROR, "Retrying Berkeley DB environment creation..["+dbInitNumRetries+"] time/s");

                try {
                    Thread.sleep(dbInitRetryInterval);
                } catch (Exception i) {
                    throw new RuntimeException(i);
                }
                //try again
                initDBProvider();
            }
            catch (Exception eOther) {
                throw new RuntimeException(eOther);
            }
            catch (Throwable t){
                logger.log(Level.ERROR, "Unable to initialize Berkeley DB! Please verify your Berkeley DB configuration.", t);
                throw new RuntimeException(t);
            }

        }
        if( _dbInitialized ) {
            dbInitializedCondition.signal();
            //Reset the retries
            dbInitNumRetries = 0;
        }
        reLock.unlock();
        return _dbInitialized;
    }

    @Override
    public GenericBackingStore getBackingStore() {
        return _bdbProvider;
    }

    public boolean isPersistenceSpaceEmpty() throws ASException{
        return ( persistenceSpace.size() == 0 );
    }

    @Override
    public KeyValueTupleAdaptor getControlSpaceKVTupleAdaptor() {
        return this.kvTupleAdaptor;
    }

    @Override
    public Space getControlSpace() {
        return this.persistenceSpace;
    }

    @Override
    public void setControlSpaceOwner(ControlSpaceOwnershipStatus status) {
        reLock.lock();
        _status = status;
        if( _status == ControlSpaceOwnershipStatus.OWNER ) {
            listenerInitializedCondition.signal();
        }
        logger.log(Level.INFO,"Setting control space ownership status to:"+_status);
        reLock.unlock();
    }

    //Determine from the com.tibco.cep.persister.bdb directory if this node
    //was the most recent persister
    public ControlSpaceOwnershipStatus isControlSpaceOwner() {
        return _status;
    }

    private boolean waitForDBInit() throws InterruptedException {
        reLock.lock();
        while(!_dbInitialized) {
            logger.log(Level.INFO,"Waiting for bdb provider to be initialized");
            dbInitializedCondition.await();
        }
        reLock.unlock();
        return _dbInitialized;
    }


    private boolean shouldCreatePersister() {
        //First check if you own the control space
        reLock.lock();
        while( (_status == ControlSpaceOwnershipStatus.INIT) || (_status == ControlSpaceOwnershipStatus.TRYING) ) {
            logger.log(Level.INFO,"Waiting for persister control space to be initialized");
            try {
                listenerInitializedCondition.await(ASPersistenceConstants.ListenerTimeout, TimeUnit.MILLISECONDS);

                if( _status==ControlSpaceOwnershipStatus.NOT_OWNER) {
                    break;
                }
            } catch (InterruptedException e) {
                reLock.unlock();
                throw new RuntimeException(e.getMessage());
            }
        }
        reLock.unlock();

        //then check if the BDB provider has been initialized
        if(_status==ControlSpaceOwnershipStatus.OWNER) {
            try {
                //if(_bdbProvider !=null && _bdbProvider.waitForInitialized() ){
                if( waitForDBInit() ) {
                    return ( true );
                }
            } catch (InterruptedException e) {
                logger.log(Level.ERROR,"Unable to create persister");
                throw new RuntimeException(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public Persister createDataPersister(KeyValueTupleAdaptor keyValueTupleAdaptor,String spaceName) {
        //First check if you own the control space
        if( shouldCreatePersister() ) {
            return ( new BDBDataPersister(_bdbProvider,keyValueTupleAdaptor,spaceName) );
        } else {
            return null;
        }
    }

    @Override
    public Persister createControlPersister(KeyValueTupleAdaptor keyValueTupleAdaptor,ControlDaoType type,String spaceName) {
        if( shouldCreatePersister() ) {
            return ( new BDBControlPersister(_bdbProvider,keyValueTupleAdaptor,type,spaceName) );
        } else {
            return  null;
        }
    }

    @Override
    public void shutdown() {
        _bdbProvider.close();
    }
}
