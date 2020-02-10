package com.tibco.rta.client;

import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.query.Browser;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 18/2/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractClientBrowser<T> implements Browser<T> {

    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());

    protected String id;

    /**
     * The client session used for subsequent server interactions.
     */
    protected DefaultRtaSession session;

    public void setId(String id) {
        this.id = id;
    }
    
    public String getId(){
    	return id;
    }

    public void setSession(DefaultRtaSession session) {
        this.session = session;
    }

    public DefaultRtaSession getSession() {
        return session;
    }


    @Override
    public void stop() {
        try {
            session.stopBrowser(id);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }


    @Override
    public void remove() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
