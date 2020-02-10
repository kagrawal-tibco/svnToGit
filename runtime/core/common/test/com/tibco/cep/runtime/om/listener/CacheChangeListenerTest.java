package com.tibco.cep.runtime.om.listener;

import com.tibco.cep.runtime.service.om.coherence.cluster.MetadataCache;
import com.tibco.cep.runtime.service.om.listener.CacheChangeEvent;
import com.tibco.cep.runtime.service.om.listener.CacheChangeListener;
import com.tibco.cep.runtime.session.RuleSession;

/*
* Author: Ashwin Jayaprakash Date: Nov 12, 2008 Time: 2:32:42 PM
*/
public class CacheChangeListenerTest implements CacheChangeListener {
    public String getId() {
        return CacheChangeListenerTest.class.getName();
    }

    public void init(RuleSession ruleSession, MetadataCache metadataCache) throws Exception {
        System.err.println(
                "Init listener: " + getClass().getName() + " for: " + ruleSession.getName());
    }

    public Config getConfig() {
        return new Config() {
            public int[] getInterestedTypeIds() {
                return null;
            }
        };
    }

    public void start() throws Exception {
        System.err.println("Started");
    }

    public void onEvent(CacheChangeEvent event) {
        System.err.println("onEvent()::" +
                " ChangeType: " + event.getChangeType()
                + ", Version: " + event.getEntityChangeVersion()
                + ", Class: " + event.getEntityClass()
                + ", DirtyBits: " +
                (event.getEntityDirtyBits() == null ? "<null>" : event.getEntityDirtyBits())
                + ", ExtId: " + event.getEntityExtId()
                + ", Id: " + event.getEntityId()
                + ", TypeId: " + event.getEntityTypeId()

        );
    }

    public void stop() throws Exception {
        System.err.println("Stopped");
    }
}
