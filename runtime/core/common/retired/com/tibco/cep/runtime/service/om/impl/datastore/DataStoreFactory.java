package com.tibco.cep.runtime.service.om.impl.datastore;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.om.exception.OMException;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Jul 13, 2004
 * Time: 4:06:27 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DataStoreFactory {

    DataStoreEnv createDataStoreEnv(Properties props) throws OMException;

    DataStoreEnv getDataStoreEnv(Properties props);

    DataStore createDataStore(String dbname, String secondarydbname, Serializer serializer);

    DataStore createConceptDataStore(Properties props, Serializer serializer)throws OMException;

    DataStore createPropertiesDataStore(Properties props, Serializer serializer) throws OMException;

    DataStore createEventDataStore(Properties props, Serializer serializer) throws OMException;

    //DataStore createPayloadDataStore(Properties props, Serializer serializer) throws OMException;
    //
    //DataStore createNamedInstanceMapDataStore(Properties props, Serializer serializer) throws OMException;

    DataStore createPropertyIndexDataStore(Properties props, Serializer serializer) throws OMException;

    DataStore createBEVersionDataStore(Properties beprops, Serializer serializer) throws OMException;

    DataStore createMigratedActiveStatesDataStore(Properties beprops, Serializer serializer) throws OMException;

    void setLogger(Logger logger);

    boolean checkDSExists(Properties beprops, String dsName) throws OMException;

    boolean checkConceptDSExists(Properties beprops) throws OMException;

    boolean checkPropertiesDSExists(Properties beprops) throws OMException;

    boolean checkEventDSExists(Properties beprops) throws OMException;

    //boolean checkPaylodDSExists(Properties beprops) throws OMException;
    //
    //boolean checkNamedInstanceMapDSExists(Properties beprops) throws OMException;

    boolean checkPropertyIndexDSExists(Properties beprops) throws OMException;

    boolean checkBEVersionDSExists(Properties beprops) throws OMException;

    boolean checkMigratedActiveStatesDSExists(Properties beprops) throws OMException;
}
