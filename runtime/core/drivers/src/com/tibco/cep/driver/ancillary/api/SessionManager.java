package com.tibco.cep.driver.ancillary.api;

import java.util.Collection;

import com.tibco.cep.runtime.service.ManagedResource;

/*
* Author: Ashwin Jayaprakash Date: Mar 19, 2009 Time: 1:43:35 PM
*/

public interface SessionManager extends ManagedResource {
    Collection<? extends Session> listSessions();

    void stopSession(Session session) throws Exception;

    /**
     * Must be set before the call to {@link #start()}.
     *
     * @param listener
     */
    void setListener(SessionListener listener);

    SessionListener getListener();

    //-----------

    public static interface SessionListener {
        void onNewSession(Session session) throws Exception;

        void removeSession(Session session);
    }
}
