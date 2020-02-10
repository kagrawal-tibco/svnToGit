package com.tibco.cep.query.stream.impl.rete.integ;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.impl.rete.integ.standalone.QueryObjectManager;
import com.tibco.cep.query.stream.impl.rete.service.AgentService;
import com.tibco.cep.query.stream.monitor.ResourceId;

/*
* Author: Ashwin Jayaprakash / Date: Jan 11, 2010 / Time: 2:14:39 PM
*/
public class SeparateThreadedQueryOM extends QueryObjectManager {
    protected ThreadPoolExecutor threadPoolExecutor;

    public SeparateThreadedQueryOM(ResourceId parentId, String regionName,
                                   AgentService agentService,
                                   Cache primaryCache, Cache deadPoolCache,
                                   ThreadPoolExecutor threadPoolExecutor) {
        super(parentId, regionName, agentService, primaryCache, deadPoolCache, false);

        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    public Element getElement(final long id, final Class ignoredClass) {
        Future<Element> future = threadPoolExecutor.submit(new Callable<Element>() {
            public Element call() throws Exception {
                return SeparateThreadedQueryOM.super.getElement(id, ignoredClass);
            }
        });

        try {
            return future.get();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Event getEvent(final long id, final Class ignoredClass) {
        Future<Event> future = threadPoolExecutor.submit(new Callable<Event>() {
            public Event call() throws Exception {
                return SeparateThreadedQueryOM.super.getEvent(id, ignoredClass);
            }
        });

        try {
            return future.get();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Event getEvent(final String extId) {
        Future<Event> future = threadPoolExecutor.submit(new Callable<Event>() {
            public Event call() throws Exception {
                return SeparateThreadedQueryOM.super.getEvent(extId);
            }
        });

        try {
            return future.get();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Element getElement(final String extId) {
        Future<Element> future = threadPoolExecutor.submit(new Callable<Element>() {
            public Element call() throws Exception {
                return SeparateThreadedQueryOM.super.getElement(extId);
            }
        });

        try {
            return future.get();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Entity getEntity(final String extId) {
        Future<Entity> future = threadPoolExecutor.submit(new Callable<Entity>() {
            public Entity call() throws Exception {
                return SeparateThreadedQueryOM.super.getEntity(extId);
            }
        });

        try {
            return future.get();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Element getElement(final long id) {
        return doGetElement(id);
    }

    @Override
    public Element getElement(long id, boolean ignoreRetractedOrMarkedDelete) {
        return doGetElement(id);
    }

    private Element doGetElement(final long id) {
        Future<Element> future = threadPoolExecutor.submit(new Callable<Element>() {
            public Element call() throws Exception {
                return SeparateThreadedQueryOM.super.getElement(id);
            }
        });

        try {
            return future.get();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Event getEvent(final long id) {
        Future<Event> future = threadPoolExecutor.submit(new Callable<Event>() {
            public Event call() throws Exception {
                return SeparateThreadedQueryOM.super.getEvent(id);
            }
        });

        try {
            return future.get();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
