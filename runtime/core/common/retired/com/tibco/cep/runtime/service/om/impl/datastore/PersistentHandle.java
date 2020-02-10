package com.tibco.cep.runtime.service.om.impl.datastore;

import com.tibco.cep.runtime.service.om.ObjectStore;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Nov 21, 2006
 * Time: 7:33:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PersistentHandle extends ObjectStore.ObjectStoreHandle {

    void removeRef();

}
