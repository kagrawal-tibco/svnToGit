package com.tibco.cep.runtime.service.om.impl.datastore;

import com.tibco.cep.runtime.service.om.exception.OMException;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Sep 20, 2004
 * Time: 3:00:58 AM
 * To change this template use File | Settings | File Templates.
 */
public interface DBTransaction {

    public void commit() throws OMException;

    public void abort() throws OMException;
}
