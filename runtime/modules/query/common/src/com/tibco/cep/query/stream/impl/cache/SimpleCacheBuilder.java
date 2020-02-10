package com.tibco.cep.query.stream.impl.cache;

import java.util.Properties;

import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.cache.CacheBuilder;
import com.tibco.cep.query.stream.impl.rete.integ.FakeCache;
import com.tibco.cep.query.stream.monitor.ResourceId;

/*
 * Author: Ashwin Jayaprakash Date: Mar 18, 2008 Time: 7:31:32 PM
 */

public class SimpleCacheBuilder implements CacheBuilder {
    protected final ResourceId resourceId;

    protected BuilderInput builderInput;

    public SimpleCacheBuilder() {
        this.resourceId = new ResourceId(SimpleCacheBuilder.class.getName());
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    /**
     * @param properties Expects {@link com.tibco.cep.query.stream.impl.cache.SimpleCacheBuilder.BuilderInput}
     *                   under the key {@link com.tibco.cep.query.stream.impl.cache.SimpleCacheBuilder.BuilderInput#KEY_INPUT}.
     * @throws Exception
     */
    public void init(Properties properties) throws Exception {
        builderInput = (BuilderInput) properties.get(BuilderInput.KEY_INPUT);
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
    }

    public void discard() throws Exception {
        builderInput = null;

        resourceId.discard();
    }

    //-----------

    public BuilderInput getBuilderInput() {
        return builderInput;
    }

    public Cache build(String name, ResourceId parent, Type type) {
        final int maxItems = this.builderInput.getPrimaryMaxItems();
        if (maxItems < 1) {
            return new FakeCache(parent);
        }
        switch (type) {
            case PRIMARY:
                return new PrimaryLocalCache(parent, name, maxItems,
                        builderInput.getPrimaryExpiryTimeMillis());

            case DEADPOOL:
            default:
                return new SimpleLocalCache(parent, name, maxItems,
                        builderInput.getDeadpoolExpiryTimeMillis());
        }
    }
}
