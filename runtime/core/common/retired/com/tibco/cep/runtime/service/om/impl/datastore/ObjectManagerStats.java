package com.tibco.cep.runtime.service.om.impl.datastore;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Oct 31, 2004
 * Time: 4:44:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class ObjectManagerStats {

    public int numConcepts;
    public int numNewConcepts;
    public int numEvents;
    public int numNewEvents;
    public int numDirtyProps;
    public int propCacheMaxSize;
    public int propCacheCurrSize;
    public long checkpointInterval;
    public long lastCheckpointFinished;
    public int dbinternalcachepercent;

}
