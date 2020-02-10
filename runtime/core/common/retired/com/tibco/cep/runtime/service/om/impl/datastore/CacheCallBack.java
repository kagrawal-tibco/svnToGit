package com.tibco.cep.runtime.service.om.impl.datastore;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Aug 4, 2004
 * Time: 3:21:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CacheCallBack {
    boolean EntryFallOff(Object key, Object value);
}
