package com.tibco.xdc;

import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.EntityDao;

import java.util.concurrent.ConcurrentHashMap;

/*
 * Author: Ashwin Jayaprakash / Date: 1/14/13 / Time: 3:25 PM
 */
public class Receiver extends AbstractReplicator<Object> {
    public static final Receiver INSTANCE = new Receiver();

    public final ConcurrentHashMap<String, Space> spaces;

    private Receiver() {
        this.spaces = new ConcurrentHashMap<String, Space>();
    }

    public void start(Cluster cluster) throws Exception {
        start(cluster, false);
    }

    @Override
    public Object doRegister(EntityDao entityDao) {
        if (listeners.contains(entityDao) || !isSeeder()) {
            return null;
        }

        SpaceMap spaceMap = (SpaceMap) entityDao.getInternal();
        Space space = spaceMap.getSpace();
        spaces.put(space.getName(), space);

        return this;
    }

    @Override
    void onMessage(String spaceName, boolean putOrTake, byte[] bytes) throws Exception {
        Space space = null;
        for (int i = 0; ; i++) {
            space = spaces.get(spaceName);
            if (space == null) {
                if (i == 0) {
                    LOGGER.log(Level.WARN, "Message received for unregistered space [%s]", spaceName);
                }
                LOGGER.log(Level.INFO, "Waiting for space [%s] to initialize", spaceName);

                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    if (i > 10) {
                        throw e;
                    }
                }
            }
            else {
                break;
            }
        }

        Tuple tuple = Tuple.create();
        tuple.deserialize(bytes);

        if (putOrTake) {
            space.put(tuple);
        }
        else {
            space.take(tuple);
        }
    }

    @Override
    void doStop(EntityDao entityDao, Object o) {
        SpaceMap spaceMap = (SpaceMap) entityDao.getInternal();
        Space space = spaceMap.getSpace();
        spaces.remove(space.getName());
    }
}
