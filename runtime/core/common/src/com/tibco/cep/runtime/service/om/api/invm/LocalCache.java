package com.tibco.cep.runtime.service.om.api.invm;

import java.io.PrintStream;
import java.util.Collection;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.Service;

/*
* Author: Ashwin Jayaprakash Date: Oct 29, 2008 Time: 1:08:37 PM
*/
public interface LocalCache extends Service {
    void clear();

    void put(Entity entity);

    /**
     * @param id
     * @return
     * @deprecated Since 3.0.1. Use {@link #getV2(Long)}
     */
    Entity get(long id);

    Entity getV2(Long id);

    /**
     * @param id
     * @deprecated Since 3.0.1. Use {@link #removeV2(Long)}.
     */
    void remove(long id);

    void removeV2(Long id);

    //-----------

    int size();

    void printContents(PrintStream printStream);

    void printToLog(Logger logger);

    int getNumRequests();

    int getNumHits();

    //-----------

    boolean directPut(Entity entity);

    Entry getEntry(long id);

    boolean remove(Entity entity);

    Entity directRemove(long id);

    boolean hasEntity(long id);

    boolean isPendingRemoval(long id);

    boolean isPendingWrite(long id);

    int getVersion(long id);

    boolean markSaved(long id);

    boolean markDeleted(Entity entity);
    
    //Delete any entries whose properties have been changed by hotdeploy 
    //or else objects with the old set of properties from before the hotdeploy could be returned    void entitiesChanged(Collection<Class<Entity>> changedClasses);

    //-----------

    public static interface Entry {
        boolean isSaved();

        boolean isPendingRemoval();

        Entity getRef();
    }
}
