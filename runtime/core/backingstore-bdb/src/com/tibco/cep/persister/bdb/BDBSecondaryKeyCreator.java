package com.tibco.cep.persister.bdb;

import com.sleepycat.bind.serial.ClassCatalog;
import com.sleepycat.bind.serial.SerialSerialKeyCreator;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.SecondaryDatabase;
import com.sleepycat.je.SecondaryKeyCreator;
import com.tibco.as.space.Tuple;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.as.kit.map.SpaceMapConstants;
import com.tibco.cep.as.kit.tuple.DefaultTupleCodec;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojo;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoConstants;
import com.tibco.cep.runtime.model.serializers.ObjectCodecHook;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASDaoProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

/**
 * Created by IntelliJ IDEA.
 * User: pgowrish
 * Date: 2/13/12
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */

public class BDBSecondaryKeyCreator extends SerialSerialKeyCreator {

    private Logger _logger = LogManagerFactory.getLogManager().getLogger(BDBSecondaryKeyCreator.class);
    private String _databaseName;
    private ObjectCodecHook _objectcodecHook = null;
    private Cluster _cluster = null;
    private SecondaryDatabase _secondaryDb = null;
    private StoredMap _secondaryStoredMap = null;

    public BDBSecondaryKeyCreator(String databaseName,ClassCatalog catalog,
                                  Class primaryKeyClass,
                                  Class valueClass,
                                  Class indexKeyClass) {

        super(catalog, primaryKeyClass, valueClass, indexKeyClass);
        _databaseName = databaseName;
        _cluster = RuleServiceProviderManager.getInstance().getDefaultProvider().getCluster();
        ASDaoProvider provider = (ASDaoProvider)_cluster.getDaoProvider();
        DefaultTupleCodec defaultTupleCodec = (DefaultTupleCodec)provider.getTupleCodec();

        if( defaultTupleCodec != null ) {
            _objectcodecHook = defaultTupleCodec.getObjectCodec();
        }
    }

    @Override
    public Object createSecondaryKey(Object key, Object value) {
        //
        // Extract the secondary key from the primary key and
        // data, and set the secondary key into the result parameter.
        //

        Entity deserEntity= null;
        Tuple valueTuple = (Tuple)value;
        int numFields = valueTuple.size();
        String extId = null;
        if( valueTuple.size() == BDBPersistenceConstants.defaultCodecNumFields ) {
            if( _objectcodecHook!= null ) {
            
                try {
                    byte[] blob = valueTuple.getBlob(SpaceMapConstants.TUPLE_COLUMN_NAME_VALUE);
                    deserEntity= (Entity)_objectcodecHook.decode(blob, null);
                } catch (Exception e) {
                    _logger.log(Level.ERROR,"Unable to decode object for secondary index insert for ["+_databaseName+"]");
                    throw new RuntimeException(e.getMessage());
                }

                if( deserEntity!=null ) {
                    extId = deserEntity.getExtId();
                }
            }

        } else if (valueTuple.size() > BDBPersistenceConstants.defaultCodecNumFields ) {
            /* Need to use a different adaptor to get the extId */
            extId = valueTuple.getString(PortablePojoConstants.PROPERTY_NAME_EXT_ID);
            if( extId == null ) {
                String msg = "Unable to decode object for secondary index insert for ["+_databaseName+"]";
                _logger.log(Level.ERROR,PortablePojoConstants.PROPERTY_NAME_EXT_ID+" not found");
                _logger.log(Level.ERROR,msg);
                throw new RuntimeException(msg);
            }
        }

        if ( _logger.isEnabledFor(Level.TRACE)) {
            _logger.log(Level.TRACE,"Creating secondary index "+extId+" for database ["+_databaseName+"]");
        }
        return extId;
    }
    
    public void setSecondaryDatabase(SecondaryDatabase secondaryDb) {
         _secondaryDb = secondaryDb;
    }

    public void setSecondaryStoredMap(StoredMap secondaryStoredMap) {
        _secondaryStoredMap = secondaryStoredMap;
    }
}
