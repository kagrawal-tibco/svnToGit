package com.tibco.cep.kernel.core.base;

import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.service.ResourceManager;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 25, 2006
 * Time: 4:57:01 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Mar 21, 2005
 * Time: 7:54:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExtIdMap
{
    transient ExtIdHandle[] extIdTable;
    transient int               extIdNumEntry;
    transient int               extIdThreshold;

    static final int   DEFAULT_INITIAL_CAPACITY = 16;
    static final int   MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    transient float    loadFactor;
//    transient WorkingMemoryImpl workingMemory;

    public ExtIdMap() {
        loadFactor = DEFAULT_LOAD_FACTOR;

        extIdTable      = new ExtIdHandle[DEFAULT_INITIAL_CAPACITY];
        extIdThreshold  = (int)(DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
        extIdNumEntry   = 0;

//        workingMemory   = wm;
    }

    static int indexForByExtId(String extId, int length) {
        int h = extId.hashCode();
        h += ~(h << 9);
        h ^=  (h >>> 14);
        h +=  (h << 4);
        h ^=  (h >>> 10);
        return h & (length-1);
    }

    public synchronized void reset() {
        if(extIdNumEntry > 0) {
        for(int i =0; i < extIdTable.length; i++) {
                extIdTable[i] = null;
        }
            extIdNumEntry = 0;
    }
    }

    public int size() {
        return extIdNumEntry;
    }

    /*
    public EntityHandle addExtId(Entity entity) throws DuplicateExtIdException {
        String extId = entity.getExtId();
        if(extId == null || extId.length() == 0) return null;
        int indexExtIdTable = indexForByExtId(extId, extIdTable.length);
        for(EntityExtHandle ex =  extIdTable[indexExtIdTable]; ex != null; ex = ex.nextExtIdEntry) {
            if(ex.entity.getExtId().equals(extId) && !ex.isMarkedDelete()) {
                throw new DuplicateExtIdException(ResourceManager.formatString("entity.duplicate.extId", workingMemory, entity, ex.entity));
            }
        }
        long id = entity.getId();
        int i = indexForById(id, objectTable.length);
        EntityHandle prev = objectTable[i];
        EntityHandle e = prev;

        while (e != null) {
            EntityHandle next = e.nextObject;
            if (e.entity.getId() == id) {
                //found!!!!
                if(e instanceof EntityExtHandle) {
                    return null;  // ext Id already present
                }
                EntityExtHandle newEntry = new EntityExtHandle(e.entity, e.nextObject);   //todo - this can only call at creation time

                if (prev == e)
                    objectTable[i] = newEntry;
                else
                    prev.nextObject = newEntry;

                newEntry.nextExtIdEntry = extIdTable[indexExtIdTable];
                extIdTable[indexExtIdTable] = newEntry;
                if(extIdNumEntry++ >= extIdThreshold) {
                    resizeExtIdTable(2*extIdTable.length);
                }
                return newEntry;
            }
            prev = e;
            e = next;
        }
        return null;
    }          */

    public synchronized void add(ExtIdHandle newHandle) throws DuplicateExtIdException {
        String extId = newHandle.getExtId();
        if(extId != null && extId.length() > 0) {
            int indexExtIdTable = indexForByExtId(extId, extIdTable.length);
            for(ExtIdHandle ex =  extIdTable[indexExtIdTable]; ex != null; ex = ex.getNextExtIdEntry()) {
                if(ex.getExtId().equals(extId) && !ex.getBaseHandle().isMarkedDelete()) {
                    throw new DuplicateExtIdException(ResourceManager.formatString("entity.duplicate.extId", newHandle.getBaseHandle().getObject(), ex.getBaseHandle().getObject()));
                }
            }
            newHandle.setNextExtIdEntry(extIdTable[indexExtIdTable]);
            extIdTable[indexExtIdTable] = newHandle;
            if(extIdNumEntry++ >= extIdThreshold) {
                resizeExtIdTable(2*extIdTable.length);
            }
        }
    }

//    public synchronized EntityHandle getAdd(Entity entity) throws DuplicateExtIdException {
//        int indexObjectTable = indexForById(entity.getId(), objectTable.length);
//        for (EntityHandle e = objectTable[indexObjectTable]; e != null; e = e.nextObject) {
//            if (e.entity.equals(entity)) {
//                if(e.getTypeInfo() == null)  e.setTypeInfo(workingMemory.getTypeInfo(entity.getClass()));
//                return e;
//            }
//        }
//        String extId = entity.getExtId();
//        if(extId != null && extId.length() > 0) {
//            int indexExtIdTable = indexForByExtId(extId, extIdTable.length);
//            for(EntityExtHandle ex =  extIdTable[indexExtIdTable]; ex != null; ex = ex.nextExtIdEntry) {
//                if(ex.entity.getExtId().equals(extId) && !ex.isMarkedDelete()) {
//                    throw new DuplicateExtIdException(ResourceManager.formatString("entity.duplicate.extId", entity, ex.entity));
//                }
//            }
//            EntityExtHandle newHandle = new EntityExtHandle(entity, objectTable[indexObjectTable], workingMemory.getTypeInfo(entity.getClass()));
//            objectTable[indexObjectTable] = newHandle;
//            if (objectNumEntry++ >= objectThreshold)
//                resizeObjectTable(2*objectTable.length);
//
//            newHandle.nextExtIdEntry = extIdTable[indexExtIdTable];
//            extIdTable[indexExtIdTable] = newHandle;
//            if(extIdNumEntry++ >= extIdThreshold) {
//                resizeExtIdTable(2*extIdTable.length);
//            }
//            return newHandle;
//        }
//        else {
//            EntityHandle newHandle = new EntityHandle(entity, objectTable[indexObjectTable], workingMemory.getTypeInfo(entity.getClass()));
//            objectTable[indexObjectTable] = newHandle;
//            if (objectNumEntry++ >= objectThreshold)
//                resizeObjectTable(2*objectTable.length);
//            return newHandle;
//        }
//    }

    synchronized public void remove(ExtIdHandle handle) {
        String extId =  handle.getExtId();
        if(extId == null) return;
        int i = indexForByExtId(extId, extIdTable.length);
        ExtIdHandle prev = extIdTable[i];
        ExtIdHandle e = prev;
        while (e != null) {
            ExtIdHandle next = e.getNextExtIdEntry();
            if (e == handle) {
                extIdNumEntry--;
                if (prev == e)
                    extIdTable[i] = next;
                else
                    prev.setNextExtIdEntry(next);
            }
            prev = e;
            e = next;
        }
    }

//    private EntityExtHandle _remove(String extId, boolean removeIdEntry) {
//        if(extId == null) return null;
//        int i = indexForByExtId(extId, extIdTable.length);
//        EntityExtHandle prev = extIdTable[i];
//        EntityExtHandle e = prev;
//
//        while (e != null) {
//            EntityExtHandle next = e.nextExtIdEntry;
//            if (e.entity.getExtId().equals(extId)) {
//                extIdNumEntry--;
//                if(removeIdEntry) {
//                    _remove(e.entity.getId(), false);
//                }
//                if (prev == e)
//                    extIdTable[i] = next;
//                else
//                    prev.nextExtIdEntry = next;
//                e.status &= ~BaseHandle.RETRACTED;
//                return e;
//            }
//            prev = e;
//            e = next;
//        }
//        return null;
//    }

    public synchronized ExtIdHandle get(String extId) {
        if(extId == null || extId.length() == 0) return null;
        int i = indexForByExtId(extId, extIdTable.length);
        ExtIdHandle cursor = extIdTable[i];
        ExtIdHandle mkDel = null;
        while (cursor != null) {
            if(cursor.getExtId().equals(extId)) {
                if(cursor.getBaseHandle().isMarkedDelete())
                    mkDel = cursor;
                else
                    return cursor;
            }
            cursor = cursor.getNextExtIdEntry();
        }
        return mkDel;
    }

    private void resizeExtIdTable(int newCapacity) {
        int oldCapacity = extIdTable.length;
        if (oldCapacity == MAXIMUM_CAPACITY) {
            extIdThreshold = Integer.MAX_VALUE;
            return;
        }
        ExtIdHandle[] newTable = new ExtIdHandle[newCapacity];
        for (int j = 0; j < extIdTable.length; j++) {
            ExtIdHandle e = extIdTable[j];
            if (e != null) {
                extIdTable[j] = null;
                do {
                    ExtIdHandle nextExtIdEntry = e.getNextExtIdEntry();
                    int i = indexForByExtId(e.getExtId(), newCapacity);
                    e.setNextExtIdEntry(newTable[i]);
                    newTable[i] = e;
                    e = nextExtIdEntry;
                } while (e != null);
            }
        }
        extIdTable = newTable;
        extIdThreshold = (int)(newCapacity * loadFactor);
    }

    public String contentToString(ExtIdHandle.HandleType type) {
        String ret = ">>> ExtIdMap " + Format.BRK;
        ret +=  "\tExtIdTable length=" + extIdTable.length + " numEntry=" + extIdNumEntry + " <<<" + Format.BRK;

        for(int i = 0; i < extIdTable.length; i++) {
            if(extIdTable[i] != null) {
                ExtIdHandle cursor = extIdTable[i];
                if(type == null || type.isType(cursor)) {
                    ret += "\tExtIdTable[" + i + "]:" + Format.BRK + "\t\t";
                    while(cursor != null) {
                        ret += "[Handle " + cursor.getBaseHandle().printInfo() + "] ";
                        cursor = cursor.getNextExtIdEntry();
                    }
                    ret += Format.BRK;
                }
            }
        }
        ret += Format.BRK;
        return ret;
    }
}