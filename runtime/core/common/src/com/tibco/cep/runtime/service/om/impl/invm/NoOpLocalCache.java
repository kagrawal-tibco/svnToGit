package com.tibco.cep.runtime.service.om.impl.invm;

import java.io.PrintStream;
import java.util.Collection;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.service.om.api.invm.LocalCache;

/*
* Author: Ashwin Jayaprakash Date: Oct 29, 2008 Time: 1:43:09 PM
*/
public class NoOpLocalCache implements LocalCache {
    public String getId() {
        return getClass().getSimpleName() + ":" + System.identityHashCode(this);
    }

    public void init(Configuration configuration, Object... otherArgs) throws Exception {
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
    }

    public void clear() {
    }

    public void put(Entity entity) {
    }

    public boolean directPut(Entity entity) {
        return true;
    }

    public Entry getEntry(long id) {
        return null;
    }

    public boolean hasEntity(long id) {
        return false;
    }

    public Entity getV2(Long id) {
        return null;
    }

    public Entity get(long id) {
        return null;
    }

    public void remove(long id) {
    }

    public void removeV2(Long id) {
    }

    public boolean remove(Entity entity) {
        return true;
    }

    public Entity directRemove(long id) {
        return null;
    }

    public boolean isPendingRemoval(long id) {
        return false;
    }

    public boolean isPendingWrite(long id) {
        return false;
    }

    public int getVersion(long id) {
        return 0;
    }

    public boolean markSaved(long id) {
        return true;
    }

    public boolean markDeleted(Entity entity) {
        return true;
    }

    public int size() {
        return 0;
    }

    public void printContents(PrintStream printStream) {
    }

    public void printToLog(Logger logger) {
    }

    public int getNumRequests() {
        return 0;
    }

    public int getNumHits() {
        return 0;
    }

	@Override
	public void entitiesChanged(Collection<Class<Entity>> changedClasses) {}
}
