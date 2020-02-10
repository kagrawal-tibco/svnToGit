package com.tibco.cep.persister.bdb;

import com.sleepycat.collections.StoredMap;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.persistence.*;
import com.tibco.cep.as.kit.map.KeyValueTupleAdaptor;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.service.cluster.system.impl.ObjectTupleImpl;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.EntityDao;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: pgowrish
 * Date: 3/22/12
 * Time: 6:12 PM
 */
public class BDBControlPersister extends BDBPersister {

    protected ControlDaoType type;
    
    public BDBControlPersister(BDBProvider provider, KeyValueTupleAdaptor kvAdaptor,
                               ControlDaoType type, String spaceName) {
        super(provider, kvAdaptor, spaceName);
        this.type = type;
    }

    @Override
    public void createDbAndMap(String spaceName) {

        try {
            if( !provider.isInitialized()) {
                provider.initialize();
            }
        }   catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        //logger.log(Level.INFO, "Creating BDB persister for space "+spaceName);
        try {
            db = provider.createDatabase(spaceName);
            persistenceMap = provider.createStoredMap(db, keyClass,valueClass,true);
            //Create a secondary index only if the space is a data space
            //logger.log(Level.INFO, "Created BDB persister for space "+spaceName);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Tuple makeSpecificTuple(Map.Entry entry) {
        Tuple valueTuple = null;

        switch (this.type) {
            //case ObjectTableIds:
            case ObjectTableExtIds:
                //break;
                
            case MasterId:
            case WorkList$SchedulerId:
                /*
                Object keyMaster = entry.getKey();
                Object valueMaster = entry.getValue();

                valueTuple = Tuple.create();
                valueTuple.putBlob(SpaceMapConstants.TUPLE_COLUMN_NAME_KEY, (byte[])keyMaster);
                valueTuple.putBlob(SpaceMapConstants.TUPLE_COLUMN_NAME_VALUE, (byte[])valueMaster);
                */
                return ((Tuple) entry.getValue());

            default:
                break;
        }
        return valueTuple;
    }

    @Override
    public ActionResult onOpen(OpenAction openAction) {
        if (persistenceMap == null) {
            createDbAndMap(openAction.getSpaceName());
        }
        return ActionResult.create();
    }

    @Override
    public ActionResult onLoad(LoadAction loadAction) {
        return super.onLoad(loadAction);
    }

    @Override
    public ActionResult onClose(CloseAction closeAction) {
        return super.onClose(closeAction);
    }

    @Override
    public ActionResult onRead(ReadAction readAction) {
        if (persistenceMap == null) {
            createDbAndMap(readAction.getSpaceName());
        }

        Tuple keyTuple = readAction.getTuple();
        Object key = this.kvAdaptor.extractKey(keyTuple);
        Tuple valueTuple = null;

        switch( this.type ) {
            case ObjectTableIds:
            case MasterId:
            case WorkList$SchedulerId:
                valueTuple = (Tuple)this.persistenceMap.get(key);
                if( valueTuple != null ) {
                    if( logger.isEnabledFor(Level.TRACE)) {
                        logger.log(Level.TRACE, "Read tuple from BDB persistence for space "+readAction.getSpaceName()+" for key="+key);
                    }
                }
                break;

            case ObjectTableExtIds:
                String uri = cacheCluster.getObjectTableCache().getURI((String) key);
                if (uri != null) {
                    TypeManager.TypeDescriptor td = rsp.getTypeManager().getTypeDescriptor(uri);
                    if (td != null) {
                        EntityDao dao = cacheCluster.getMetadataCache().getEntityDao(td.getImplClass());
    
                        //Get the storedmap that corresponds to the secondary database for this data space
                        StoredMap secMap = provider.getStoredMapForDatabase(dao.getName() + BDBPersistenceConstants.SecondaryDbNameSuffix);
                        Object blobValue = null;
                        if (secMap != null) {
                            blobValue = secMap.get(key);
                        }
    
                        if (blobValue != null) {
                            //then deserialize it into a concept and get the id from the concept
                            long id = getIdFromEntity(blobValue);
    
                            if (id != -1) {
                                // Create the ObjecTuple from the id, the key(extid)
                                Object objectTuple = (id == -1) ? null : new ObjectTupleImpl(id, (String) key, td.getTypeId());
    
                                valueTuple = kvAdaptor.makeTuple(key);
                                kvAdaptor.setValue(valueTuple, objectTuple);
                                return ActionResult.create(valueTuple);
                            } else {
                                if (logger.isEnabledFor(Level.TRACE)) {
                                    logger.log(Level.TRACE, "Concept has invalid id=" + id + "for key=" + key + " for " + db.getDatabaseName());
                                }
                                return ActionResult.create();
                            }
                        } else {
                            return ActionResult.create();
                        }
                    }
                }
                break;

            default:
                break;
        }
        return ActionResult.create(valueTuple);
    }

    @Override
    public Object extractKey(Tuple tuple) {
        switch (type) {
            case MasterId:
            case ObjectTableIds:
            case WorkList$SchedulerId:
                return ( tuple.getBlob(kvAdaptor.getKeyColumnName()) );
            
            default:
                return null;
        }
    }

    @Override
    public Object extractValue(Tuple tuple) {
        switch (type) {
            case MasterId:
            case ObjectTableIds:
            case WorkList$SchedulerId:
                return ( tuple.getBlob(kvAdaptor.getValueColumnName()) );

            default:
                return null;
        }
    }

    @Override
    public ActionResult onWrite(WriteAction writeAction) {

        //Do not persist ExtIds
        if (type != ControlDaoType.ObjectTableExtIds)  {
            return super.onWrite(writeAction);
        }
        return ActionResult.create();
    }

    @Override
    public ActionResult onAlter(AlterAction alterAction) {
        return null;
    }

    @Override
    public boolean shouldProcessTakeOp() {
        
        if ( type == ControlDaoType.ObjectTableExtIds ) {
            return false;
        } else {
            return true;
        }
    }
}
