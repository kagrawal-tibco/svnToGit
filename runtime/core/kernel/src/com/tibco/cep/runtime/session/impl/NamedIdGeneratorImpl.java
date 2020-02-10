package com.tibco.cep.runtime.session.impl;

import com.tibco.cep.kernel.service.NamedIdGenerator;
import com.tibco.cep.kernel.service.ResourceManager;
import com.tibco.cep.kernel.service.impl.DefaultIdGenerator;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Aug 28, 2008
 * Time: 1:44:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class NamedIdGeneratorImpl implements NamedIdGenerator {

    private long currentId = 0;
    private long maxId = Long.MAX_VALUE;
    private static DefaultIdGenerator instance;
    private String name;

    public NamedIdGeneratorImpl(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long nextEntityId() {
        if(currentId == maxId) {
            throw new RuntimeException(ResourceManager.formatString("entity.factory.id.max.exception", Long.toString(maxId)));
        }
        synchronized (this){
            return ++currentId;  //Id start from 1
        }
    }

    synchronized  public long lastEntityId() {
        return currentId;
    }

    public void setMinEntityId(long min) {
        if(currentId >= min) return;
        synchronized(this) {
            if(currentId < min) {
                currentId = min;
            }
        }
    }

    synchronized public void setMaxEntityId(long max) {
        if(maxId > max) {
            maxId = max;
        }
    }

    public void remove() {
        
    }

	public long nextEntityId(Class clz) {
		//dont use class
		return nextEntityId();
	}
}

