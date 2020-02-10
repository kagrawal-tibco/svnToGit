package com.tibco.rta.model.runtime;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 12/3/14
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerConfigurationCollection implements Iterable<ServerConfiguration> {

    private Collection<ServerConfiguration> serverConfigurations;

    public ServerConfigurationCollection() {
    }

    public ServerConfigurationCollection(Collection<ServerConfiguration> serverConfigurations) {
        this.serverConfigurations = serverConfigurations;
    }

    public void setServerConfigurations(Collection<ServerConfiguration> serverConfigurations) {
        this.serverConfigurations = serverConfigurations;
    }

    public Collection<ServerConfiguration> getServerConfigurations() {
        return Collections.unmodifiableCollection(serverConfigurations);
    }

    @Override
    public Iterator<ServerConfiguration> iterator() {
        return serverConfigurations.iterator();
    }

    public int size() {
        return serverConfigurations.size();
    }
}
