package com.tibco.xdc;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Space;
import com.tibco.as.space.event.PutEvent;
import com.tibco.as.space.event.TakeEvent;
import com.tibco.as.space.listener.ListenerDef;
import com.tibco.as.space.listener.ListenerDef.DistributionScope;
import com.tibco.as.space.listener.ListenerDef.TimeScope;
import com.tibco.as.space.listener.PutListener;
import com.tibco.as.space.listener.TakeListener;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.xdc.Collector.SpaceListener;

/*
 * Author: Ashwin Jayaprakash / Date: 1/11/13 / Time: 5:27 PM
 */
public class Collector extends AbstractReplicator<SpaceListener> {
    public static final Collector INSTANCE = new Collector();

    private Collector() {
    }

    public void start(Cluster cluster) throws Exception {
        start(cluster, true);
    }

    @Override
    SpaceListener doRegister(EntityDao entityDao) {
        if (listeners.contains(entityDao) || !isSeeder()) {
            return null;
        }

        SpaceMap spaceMap = (SpaceMap) entityDao.getInternal();
        Space space = spaceMap.getSpace();
        SpaceListener listener = new SpaceListener(space);

        try {
            space.listen(listener, ListenerDef.create(TimeScope.ALL).setDistributionScope(DistributionScope.SEEDED));
        }
        catch (ASException e) {
            throw new RuntimeException("Error occured while adding listener to " + space.getName());
        }

        LOGGER.log(Level.INFO, "Registered [%s] on [%s]", getClass().getName(), space.getName());

        return listener;
    }

    @Override
    void doStop(EntityDao entityDao, SpaceListener listener) {
        try {
            listener.space.stopListener(listener);

            LOGGER.log(Level.INFO, "Unregistered [%s] on [%s]", getClass().getName(), listener.spaceName);
        }
        catch (ASException e) {
            LOGGER.log(Level.WARN, e, "Error occurred while disconnecting [%s] from [%s]",
                    getClass().getName(), listener.spaceName);
        }
    }

    //--------------

    class SpaceListener implements PutListener, TakeListener {
        final Space space;

        final String spaceName;

        SpaceListener(Space space) {
            this.space = space;
            this.spaceName = space.getName();
        }

        @Override
        public void onPut(PutEvent putEvent) {
            try {
                send(spaceName, true, putEvent.getTuple());
            }
            catch (Exception e) {
                LOGGER.log(Level.ERROR, e, "Error occurred while relaying put-event on space [%s]", spaceName);
            }
        }

        @Override
        public void onTake(TakeEvent takeEvent) {
            try {
                send(spaceName, true, takeEvent.getTuple());
            }
            catch (Exception e) {
                LOGGER.log(Level.ERROR, e, "Error occurred while relaying take-event on space [%s]", spaceName);
            }
        }
    }
}
