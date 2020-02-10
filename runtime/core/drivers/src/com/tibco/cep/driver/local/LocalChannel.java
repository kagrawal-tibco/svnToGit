package com.tibco.cep.driver.local;


import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.impl.AbstractChannel;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.util.ResourceManager;



public class LocalChannel extends AbstractChannel<LocalQueueDestination> {

    protected LocalChannel(ChannelManager manager, String uri, ChannelConfig config) {
        super(manager, uri, config);
        Iterator i = config.getDestinations().iterator();
        while (i.hasNext()) {
            LocalQueueDestination dest = createDestination((DestinationConfig) i.next());
            this.destinations.put(dest.getURI(), dest);
        }
    }


    public void init() throws Exception {
        this.getLogger().log(Level.INFO,
                ResourceManager.getInstance().formatMessage("channel.connecting.local", this.getURI()));

        Iterator<LocalQueueDestination> i = destinations.values().iterator();
        while (i.hasNext()) {
            (i.next()).init();
        }
        super.init();
        this.getLogger().log(Level.INFO,
                ResourceManager.getInstance().formatMessage("channel.connected", this.getURI()));

    }

    public void start(int mode) throws Exception {
        Iterator<LocalQueueDestination> i = destinations.values().iterator();
        while (i.hasNext()) {
            (i.next()).start(mode);
        }
        super.start(mode);
        this.getLogger().log(Level.INFO,
                ResourceManager.getInstance().formatMessage("channel.started", this.getURI()));
    }

    public void stop()  {
        super.stop();
    }

    public void shutdown() throws Exception {
        close();
        super.shutdown();
    }

    public void close() throws Exception {
        for (Iterator<LocalQueueDestination> i = destinations.values().iterator(); i.hasNext(); ) {
            (i.next()).close();
        }
    }

    public void send(SimpleEvent event, String destURI, Map overrideData) throws Exception {
        LocalQueueDestination destination = destinations.get(destURI);
        if (destination == null) {
            throw new Exception("Destination not found: " + destURI);
        } else {
            destination.send(event, overrideData);
        }
    }


    public void connect() throws Exception {
    }

    public LocalQueueDestination createDestination (DestinationConfig destConfig) {
        return new LocalQueueDestination(this, destConfig);
    }

    /**
     * //TODO Not sure what to do with this one.
     * @return
     */
    public boolean isAsync() {
        return true;
    }
}


