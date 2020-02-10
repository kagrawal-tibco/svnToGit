package com.tibco.cep.persister.bdb;

import com.tibco.as.space.Tuple;
import com.tibco.as.space.persistence.*;
import com.tibco.cep.as.kit.map.KeyValueTupleAdaptor;
import com.tibco.cep.kernel.service.logging.Level;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: pgowrish
 * Date: 3/22/12
 * Time: 6:12 PM
 */
public class BDBDataPersister extends BDBPersister {

    public BDBDataPersister(BDBProvider provider, KeyValueTupleAdaptor kvAdaptor, String spaceName) {
        super(provider, kvAdaptor, spaceName);
        //The secondary index is the extId string
        secIndexClass = String.class;
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

        logger.log(Level.INFO, "Creating BDB persister for space "+spaceName);
        try {
            db = provider.createDatabase(spaceName);
            persistenceMap = provider.createStoredMap(db, keyClass,valueClass,true);
            //Create a secondary index only if the space is a data space
            secondaryDb = provider.createSecondaryDatabase(spaceName+BDBPersistenceConstants.SecondaryDbNameSuffix,db,keyClass,valueClass,secIndexClass);
            //also create a secondary storedmap
            secondaryMap = provider.createSecondaryStoredMap(secondaryDb,secIndexClass,valueClass,true);
            BDBSecondaryKeyCreator keyCreator = (BDBSecondaryKeyCreator)secondaryDb.getConfig().getKeyCreator();
            if (keyCreator != null) {
                keyCreator.setSecondaryStoredMap(secondaryMap);
            }
            logger.log(Level.INFO, "Created BDB persister for space "+spaceName);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Tuple makeSpecificTuple(Map.Entry entry) {

        /*
        Tuple valueTuple = null;
        Long key = (Long)entry.getKey();
        byte[] value = (byte[])entry.getValue();
        valueTuple = Tuple.create();
        valueTuple.putLong(SpaceMapConstants.TUPLE_COLUMN_NAME_KEY, key);
        valueTuple.putBlob(SpaceMapConstants.TUPLE_COLUMN_NAME_VALUE,value);
        return valueTuple;
        */
        return( (Tuple) entry.getValue());
    }

    @Override
    public ActionResult onOpen(OpenAction openAction) {
        if(persistenceMap == null) {
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
        Tuple valueTuple = (Tuple)this.persistenceMap.get(key);
        if( valueTuple != null ) {

            if (logger.isEnabledFor(Level.TRACE)) {
                logger.log(Level.TRACE, "Read tuple from BDB persistence for space "+readAction.getSpaceName());
            }

            //Increment the read count in the mbean
            persisterMBean.incrementGets();
        }
        return ActionResult.create(valueTuple);
    }

    @Override
    public Object extractKey(Tuple tuple) {
        return( tuple.getLong(kvAdaptor.getKeyColumnName()) );
    }

    @Override
    public Object extractValue(Tuple tuple) {
        return tuple.getBlob(kvAdaptor.getValueColumnName());
    }

    @Override
    public ActionResult onWrite(WriteAction writeAction) {
        return super.onWrite(writeAction);
    }

    @Override
    public ActionResult onAlter(AlterAction alterAction) {
        return ActionResult.create();
    }

    @Override
    public boolean shouldProcessTakeOp() {
        return true;
    }
}
