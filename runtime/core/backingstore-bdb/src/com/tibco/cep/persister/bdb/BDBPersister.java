package com.tibco.cep.persister.bdb;

import com.sleepycat.collections.StoredMap;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.SecondaryDatabase;
import com.sleepycat.je.SecondaryIntegrityException;
import com.tibco.as.space.*;
import com.tibco.as.space.persistence.*;
import com.tibco.cep.as.kit.map.KeyValueTupleAdaptor;
import com.tibco.cep.as.kit.map.SpaceMapConstants;
import com.tibco.cep.as.kit.tuple.DefaultTupleCodec;
import com.tibco.cep.as.kit.tuple.SerializableTupleCodec;
import com.tibco.cep.as.kit.tuple.TupleCodec;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.persister.ASPersistenceConstants;
import com.tibco.cep.persister.bdb.jmx.BDBPersisterMBeanImpl;
import com.tibco.cep.runtime.model.serializers.ObjectCodecHook;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASDaoProvider;
import com.tibco.cep.runtime.session.RuleServiceProvider;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: pgowrish
 * Date: 1/9/12
 * Time: 12:00 PM
 */
public abstract class BDBPersister implements Persister{

    public Logger logger = LogManagerFactory.getLogManager().getLogger(BDBPersister.class);
    protected Class keyClass = null;
    protected Class valueClass = null;
    protected Class secIndexClass = null;
    protected KeyValueTupleAdaptor kvAdaptor;
    protected Cluster cacheCluster;
    protected RuleServiceProvider rsp;
    protected BDBProvider provider = null;
    protected Database db = null;
    protected SecondaryDatabase secondaryDb = null;
    protected StoredMap persistenceMap = null;
    protected StoredMap secondaryMap = null;
    protected BDBPersisterMBeanImpl persisterMBean;

    private ObjectCodecHook objectcodecHook = null;
    private Metaspace metaspace = null;
    private int writeCount = 0;
    private int removeCount = 0;
    private int totalPutTime = 0, totalRemoveTime = 0;
    private long absoluteTimeBegin = 0;

    public BDBPersister(BDBProvider provider,Class keyClass,Class valueClass ) {
        this.provider = provider;
        this.metaspace = provider.getMetaspace();
        this.keyClass = keyClass;
        this.valueClass = valueClass;

        //Warning: Usage of this constructor implies that a default codec (ObjectCodecHook) will be used
        kvAdaptor = new KeyValueTupleAdaptor(SpaceMapConstants.TUPLE_COLUMN_NAME_KEY, keyClass,
                SpaceMapConstants.TUPLE_COLUMN_NAME_VALUE, valueClass, new SerializableTupleCodec());
        cacheCluster = CacheClusterProvider.getInstance().getCacheCluster();
        rsp = cacheCluster.getRuleServiceProvider();

        ASDaoProvider daoProvider = (ASDaoProvider)cacheCluster.getDaoProvider();
        if (daoProvider != null) {
			TupleCodec tupleCodec = daoProvider.getTupleCodec();
			if (tupleCodec instanceof DefaultTupleCodec) {
				objectcodecHook = ((DefaultTupleCodec) tupleCodec).getObjectCodec();
			}
		}
	}

    public BDBPersister(BDBProvider provider,KeyValueTupleAdaptor kvAdaptor,String spaceName) {
        this.provider = provider;
        this.metaspace = provider.getMetaspace();
        this.keyClass = kvAdaptor.getKeyType().getSupportedJavaClass();
        //this.valueClass = kvAdaptor.getValueType().getSupportedJavaClass();
        this.valueClass = Tuple.class;
        this.kvAdaptor = kvAdaptor;
        cacheCluster = CacheClusterProvider.getInstance().getCacheCluster();
        rsp = cacheCluster.getRuleServiceProvider();

        ASDaoProvider daoProvider = (ASDaoProvider)cacheCluster.getDaoProvider();
        if (daoProvider != null) {
			TupleCodec tupleCodec = daoProvider.getTupleCodec();
			if (tupleCodec instanceof DefaultTupleCodec) {
				objectcodecHook = ((DefaultTupleCodec) tupleCodec).getObjectCodec();
			}
		}

        //--------------

        persisterMBean = new BDBPersisterMBeanImpl();
        persisterMBean.setName(spaceName);

        try {
            persisterMBean.register();
        }
        catch (Exception e) {
            logger.log(Level.WARN, "Error occurred while registering mbean for persister", e);
        }
    }

    public abstract void createDbAndMap(String spaceName) ;

    public ActionResult onOpen(OpenAction openAction) {
        return ActionResult.create();
    }
    
    public abstract Tuple makeSpecificTuple(Map.Entry entry);

    public ActionResult loadTuples(LoadAction loadAction) {

        Space space = null;
        long capacity;
        try {
            space = metaspace.getSpace(loadAction.getSpaceName());
            capacity = space.getSpaceDef().getCapacity();
        } catch (ASException e) {
            throw new RuntimeException(e.getMessage());
        }

        if ((capacity == SpaceDef.NO_CAPACITY) || (capacity == Long.MAX_VALUE)) {
            Collection<Tuple> tuples = new ArrayList<Tuple>();

            //TODO: - make the batch size a property for the module
            int batchSize = ASPersistenceConstants.readBatch;
            int tupleCount = 0;
            Set entries = this.persistenceMap.entrySet();
            Iterator itr = entries.iterator();

            persisterMBean.setTupleRecoveryBatchSize(batchSize);

            logger.log(Level.INFO, "Recovering from BDB persistence for space ["+loadAction.getSpaceName()+"]");
            logger.log(Level.INFO, "BDB persistence for space ["+loadAction.getSpaceName()+"] has "+this.persistenceMap.size()+" entries");
            long startTime = System.currentTimeMillis();
            while(itr.hasNext()) {
                Map.Entry entry = (Map.Entry) itr.next();
                Tuple tuple = makeSpecificTuple(entry);
                if (tuple != null) {
                    tuples.add(tuple);
                    tupleCount++;
                    persisterMBean.incrementRecovered();
                }

                if (tupleCount == batchSize) {
                    ResultList list = space.loadAll(tuples);
                    logger.log(Level.INFO, "Loaded "+ batchSize +" tuples into space "+loadAction.getSpaceName()+" in "+(System.currentTimeMillis()-startTime) +" ms" );
                    persisterMBean.setTupleRecoveryThroughput(batchSize/(System.currentTimeMillis()-startTime));
                    if (list.hasError()) {
                        logger.log(Level.INFO, "OnLoad completed with errors for space "+loadAction.getSpaceName());
                        //Todo-throw an exception here
                        //throw( list.getError());
                    }

                    tuples.clear();
                    tupleCount = 0;
                    startTime = System.currentTimeMillis();
                }
            }

            // Load the remaining tuples
			if (tuples.size() > 0) {
                logger.log(Level.INFO, "Loaded remaining "+ tupleCount +" tuples into space "+loadAction.getSpaceName()+" in "+(System.currentTimeMillis()-startTime) +" ms" );
                ResultList list = space.loadAll(tuples);
                if (list.hasError()) {
                    logger.log(Level.INFO, "OnLoad completed with errors for space "+loadAction.getSpaceName());
                    //Todo-throw an exception here
                    //throw(list.getError());
                }
            }
        } else {
            logger.log(Level.INFO,"Space ["+loadAction.getSpaceName()+"] has limited capacity. Not loading from persistence.");
        }
        return ActionResult.create();
    }

    public ActionResult onLoad(LoadAction loadAction) {

        if (persistenceMap == null) {
            createDbAndMap(loadAction.getSpaceName());
        }
        loadTuples(loadAction);
        logger.log(Level.INFO,"Completed onLoad for Space ["+loadAction.getSpaceName()+"].");
        return ActionResult.create();
    }

    public ActionResult onClose(CloseAction closeAction) {
        return ActionResult.create();
    }

    public abstract ActionResult onRead(ReadAction readAction);
    
    public abstract Object extractKey(Tuple tuple);

    public abstract Object extractValue(Tuple tuple);
    
    public abstract boolean shouldProcessTakeOp();

    public ActionResult onWrite(WriteAction writeAction) {

        if (persistenceMap == null) {
            createDbAndMap(writeAction.getSpaceName());
        }

        long timeBegin = 0, timeEnd = 0;
        Object key = null, value = null;

        if (persistenceMap != null) {
            if (logger.isEnabledFor(Level.TRACE)) {
                logger.log(Level.TRACE, "Writing tuple to BDB persistence for space " + writeAction.getSpaceName());
            }
            for (WriteOp operation : writeAction.getOps())
            {
                Tuple tuple = null;
                WriteOp.OpType t = operation.getType();
                switch (operation.getType())
                {
                    case PUT:
                        tuple = ((PutOp) operation).getTuple();
                        if( absoluteTimeBegin == 0 ) {
                            absoluteTimeBegin = System.currentTimeMillis();
                        }
                        timeBegin = System.currentTimeMillis();
                        
                        key = extractKey(tuple);
                        //value = extractValue(tuple);
                        value = tuple;

                        if (key != null && value != null) {
                            try {
                                this.persistenceMap.put(key,value );
                            } catch ( com.sleepycat.je.UniqueConstraintException u) {
                                logger.log(Level.ERROR,"UniqueConstraintException for database ["+u.getSecondaryDatabaseName()+"] for key="+key+" and value="+value);
                                throw new RuntimeException(u);
                            }

                            timeEnd = System.currentTimeMillis();

                            if( logger.isEnabledFor(Level.TRACE)) {
                                logger.log(Level.TRACE, "Writing tuple to BDB persistence for space "+writeAction.getSpaceName()+" for key="+key);
                            }
                            //totalPutTime += (timeEnd - timeBegin );
                            ++this.writeCount;

                            //Increment the put count in the mbean
                            persisterMBean.incrementPuts();
                        }
                        break;

                    case TAKE:
                        if (!shouldProcessTakeOp()) {
                            break;
                        }

                        tuple = ((TakeOp) operation).getTuple();
                        key = kvAdaptor.extractKey(tuple);
                        timeBegin = System.currentTimeMillis();
                        
                        try {
                            this.persistenceMap.remove(key);
                        } catch (SecondaryIntegrityException e) {
                            logger.log(Level.ERROR," Exception at Primary key="+e.getPrimaryKey ()+",Secondary key="+e.getSecondaryKey() );
                            throw new RuntimeException(e);
                        }
                        timeEnd = System.currentTimeMillis();
                        logger.log(Level.TRACE, "Take operation on BDB persistence for space "+writeAction.getSpaceName()+" for key="+key);
                        ++this.removeCount;

                        //Increment the take count in the mbean
                        persisterMBean.incrementTake();
                        break;
                }
            }

            if (writeCount == ASPersistenceConstants.writeBatch) {
                logger.log(Level.INFO, writeCount+" put operations on BDB persister for space "+writeAction.getSpaceName());
                logger.log(Level.TRACE,writeCount+" put operations on BDB persister for space "+writeAction.getSpaceName() +
                        " took "+(totalPutTime)+" ms out of a total batch time of "+ (System.currentTimeMillis() - absoluteTimeBegin) +" ms");
                writeCount=0;
                totalPutTime=0;
                absoluteTimeBegin = 0;
            }

            if (removeCount == ASPersistenceConstants.takeBatch) {
                logger.log(Level.INFO, removeCount+" take operations on BDB persister for space "+writeAction.getSpaceName());
                logger.log(Level.TRACE, removeCount+" take operations on BDB persister for space "+writeAction.getSpaceName()
                +" took "+(totalRemoveTime/writeCount)+" ms");
                removeCount=0;
                totalRemoveTime=0;
            }
            return ActionResult.create();
        }
        return ActionResult.create();
    }
    
    public long getIdFromEntity(Object value) {

        if (value != null) {
            if (value instanceof Tuple) {
                Tuple t = (Tuple)value;
                return t.getLong(kvAdaptor.getKeyColumnName());
            } else {
                String msg = "Unable to decode entity for ["+ db.getDatabaseName()+"]";
                logger.log(Level.ERROR,msg);
                throw new RuntimeException(msg);
            }
        }
        return -1;

        /*
        Entity deserEntity= null;

        if (value!=null) {
            try {
                deserEntity = (Entity)objectcodecHook.decode((byte[])value,null,"v");
            } catch (Exception e) {
                logger.log(Level.ERROR,"Unable to decode concept for ["+ db.getDatabaseName()+"]");
                throw new RuntimeException(e.getMessage());
            }
            if (deserEntity != null) {
                return deserEntity.getId();
            } else {
                return -1;
            }
        } else {
            return -1;
        }
        */
    }

    public void finalize() {
    	try {
            logger.log(Level.INFO, "Closing Berkeley DB store ["+db.getDatabaseName()+"]");
            db.close();
    	} catch (DatabaseException e) {
           logger.log(Level.WARN, "Closing Berkeley DB store failed ["+db+"]");
    	}
    }
}
