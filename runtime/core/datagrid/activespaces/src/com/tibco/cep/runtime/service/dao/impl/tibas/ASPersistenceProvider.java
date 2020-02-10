package com.tibco.cep.runtime.service.dao.impl.tibas;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.Space;
import com.tibco.as.space.persistence.Persister;
import com.tibco.cep.as.kit.map.KeyValueTupleAdaptor;
import com.tibco.cep.runtime.service.cluster.backingstore.GenericBackingStore;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.cluster.Cluster;

/**
 * Created by IntelliJ IDEA.
 * User: pgowrish
 * Date: 1/15/12
 * Time: 2:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ASPersistenceProvider {

    public void initializeControlSpace(Metaspace metaspace,Cluster cluster,String dataStorePath) throws ASException;
    public void startMembershipListener() throws ASException;
    public boolean initDBProvider() throws ASException;
    public GenericBackingStore getBackingStore();
    public Persister createDataPersister(KeyValueTupleAdaptor keyValueTupleAdaptor,String spaceName);
    public Persister createControlPersister(KeyValueTupleAdaptor keyValueTupleAdaptor,ControlDaoType type,String spaceName);
    public KeyValueTupleAdaptor getControlSpaceKVTupleAdaptor();
    public Space getControlSpace();
    public void setControlSpaceOwner(ControlSpaceOwnershipStatus status);
    public ControlSpaceOwnershipStatus isControlSpaceOwner();
    public void shutdown();
    public enum ControlSpaceOwnershipStatus {NO_CONTROL_SPACE,INIT,TRYING,OWNER,NOT_OWNER};
}
