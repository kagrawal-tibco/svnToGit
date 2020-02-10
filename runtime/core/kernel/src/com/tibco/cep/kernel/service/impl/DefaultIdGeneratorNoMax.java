package com.tibco.cep.kernel.service.impl;

import java.util.concurrent.atomic.AtomicLong;

import com.tibco.cep.kernel.service.IdGenerator;
import com.tibco.cep.kernel.service.ResourceManager;

public class DefaultIdGeneratorNoMax implements IdGenerator {
    private final AtomicLong currentId = new AtomicLong(1);
    private static final boolean useCAS = !useGetAndInc();
    
    //future versions of jdk will use LOCK XADD on intel for getAndIncrement which is faster that CAS
    private static boolean useGetAndInc() {
        try {
            String arch = System.getProperty("os.arch");

            if(arch != null && (arch.contains("x86") || arch.contains("amd64"))) {
                Class unsf = DefaultIdGeneratorNoMax.class.getClassLoader().loadClass("sun.misc.Unsafe");
                return unsf != null && null != unsf.getMethod("getAndAddLong", Object.class, long.class, long.class);
            }
        } catch (ClassNotFoundException cnfe) {
        } catch (NoSuchMethodException nsme) {}
        return false;
    }
    
    @Override
    public long nextEntityId() {
        return checkMax(getAndInc());
    }
    
    private long getAndInc() {
        if(useCAS) {
            long current;
            while(!currentId.compareAndSet(current = currentId.get(), current + 1)) {
                //if there's contention back off momentarily
                Thread.yield();
            }
            return current;
        } else {
            return currentId.getAndIncrement();
        }
    }
    
    protected long checkMax(long id) {
        //check overflow
        if(id < 0) {
            throw new RuntimeException(ResourceManager.formatString("entity.factory.id.max.exception", Long.toString(Long.MAX_VALUE)));
        }
        return id;
    }
    
    @Override
    public void setMaxEntityId(long max) {
        throw new RuntimeException("DefaultIdGeneratorNoMax does not support setMaxEntityId()");
    }

    @Override
    public long lastEntityId() {
        return currentId.get();
    }

    @Override
    public void setMinEntityId(long min) {
        long current;
        while(((current = currentId.get()) < min) && !currentId.compareAndSet(current, min)) {
            //if there's contention back off momentarily 
            Thread.yield();
        }
    }

    @Override
    public long nextEntityId(Class clz) {
        //ignore the class
        return nextEntityId();
    }
}