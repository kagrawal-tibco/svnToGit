package com.tibco.cep.impl.common.resource;

import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.Resource;
import com.tibco.cep.common.resource.ResourceProvider;

/*
* Author: Ashwin Jayaprakash Date: Jul 14, 2009 Time: 3:25:38 PM
*/
public class DefaultResourceProvider implements ResourceProvider {
    protected ConcurrentHashMap<Class<? extends Resource>, Resource> knownResources;

    protected ConcurrentHashMap<Id, Resource> idResources;

    /**
     * Creates it's own {@link DefaultResourceProvider}.
     */
    public DefaultResourceProvider() {
        this.knownResources = new ConcurrentHashMap<Class<? extends Resource>, Resource>();
        this.idResources = new ConcurrentHashMap<Id, Resource>();
    }

    public <R extends Resource> boolean registerResource(Class<R> type, R resource) {
        Object existing = knownResources.putIfAbsent(type, resource);

        return (existing == null);
    }

    public boolean registerResource(Id id, Resource resource) {
        Object existing = idResources.putIfAbsent(id, resource);

        return (existing == null);
    }

    public <R extends Resource> R unregisterResource(Class<R> type) {
        Resource r = knownResources.remove(type);

        return type.cast(r);
    }

    public Resource unregisterResource(Id id) {
        return idResources.remove(id);
    }

    public <R extends Resource> R fetchResource(Class<R> type) {
        Resource resource = knownResources.get(type);

        return type.cast(resource);
    }

    public <R extends Resource> R fetchResource(Class<R> type, Id id) {
        Resource resource = idResources.get(id);

        return type.cast(resource);
    }

    public void discard() {
        knownResources.clear();
        idResources.clear();
    }
}
