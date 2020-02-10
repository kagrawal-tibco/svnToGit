package com.tibco.cep.kernel.service.impl;

import java.util.concurrent.atomic.AtomicLong;

import com.tibco.cep.kernel.service.ResourceManager;

public class DefaultIdGenerator extends DefaultIdGeneratorNoMax {
    private final AtomicLong maxId = new AtomicLong(Long.MAX_VALUE);

    @Override
    protected long checkMax(long id) {
        if(id >= maxId.get() || id < 0) {
            throw new RuntimeException(ResourceManager.formatString("entity.factory.id.max.exception", Long.toString(maxId.get())));
        }
        return id;
    }
    
    @Override
    public void setMaxEntityId(long max) {
        long oldMax;
        while(((oldMax = maxId.get()) < max) && !maxId.compareAndSet(oldMax, max)) {
            //if there's contention back off momentarily 
            Thread.yield();
        }
    }
}
