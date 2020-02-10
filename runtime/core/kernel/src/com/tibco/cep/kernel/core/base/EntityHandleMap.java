package com.tibco.cep.kernel.core.base;

import java.util.Iterator;

import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateException;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
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
public class EntityHandleMap {
    transient EntityHandle[]    objectTable;
    transient int               objectThreshold;
    transient int               objectNumEntry;

    static final int   DEFAULT_INITIAL_CAPACITY = 16;
    static final int   MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    final transient float    loadFactor;
    final transient WorkingMemoryImpl workingMemory;
    private final ExtIdMap          extIdMap;

    public EntityHandleMap(WorkingMemoryImpl wm, ExtIdMap extIdMap) {
        loadFactor = DEFAULT_LOAD_FACTOR;

        objectTable     = new EntityHandle[DEFAULT_INITIAL_CAPACITY];
        objectThreshold = (int)(DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
        objectNumEntry  = 0;

        workingMemory   = wm;
        if(extIdMap == null) {
            this.extIdMap = new ExtIdMap();
        } else {
            this.extIdMap = extIdMap;
        }
    }

    static int indexForById(long Id, int length) {
        int h = (int)(Id ^ (Id >>> 32));
        h += ~(h << 9);
        h ^=  (h >>> 14);
        h +=  (h << 4);
        h ^=  (h >>> 10);
        return h & (length-1);
    }

    public synchronized void reset() {
        if(objectNumEntry > 0) {
            Iterator ite = this.handleIterator();
            while(ite.hasNext()) {
                EntityHandle handle = (EntityHandle) ite.next();
                handle.setRetracted();
            }
            for(int i =0; i < objectTable.length; i++) {
                objectTable[i] = null;
            }
            objectNumEntry = 0;
        }
        extIdMap.reset();
    }

    public int size() {
        return objectNumEntry;
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

    public synchronized EntityHandle add(Entity entity) throws DuplicateException, DuplicateExtIdException {
        int indexObjectTable = indexForById(entity.getId(), objectTable.length);
        for (EntityHandle e = objectTable[indexObjectTable]; e != null; e = e.nextObject) {
            if (e.entity.equals(entity)) {
                throw new DuplicateException(ResourceManager.formatString("entity.duplicate", entity, workingMemory));
            }
        }
        String extId = entity.getExtId();
        if(extId != null && extId.length() > 0) {
            EntityExtHandle newHandle = new EntityExtHandle(entity, objectTable[indexObjectTable], workingMemory.getTypeInfo(entity.getClass()));
            
            extIdMap.add(newHandle);
            
            objectTable[indexObjectTable] = newHandle;
            if (objectNumEntry++ >= objectThreshold)
                resizeObjectTable(2*objectTable.length);
            return newHandle;
        }
        else {
            EntityHandle newHandle = new EntityHandle(entity, objectTable[indexObjectTable], workingMemory.getTypeInfo(entity.getClass()));
            objectTable[indexObjectTable] = newHandle;
            if (objectNumEntry++ >= objectThreshold)
                resizeObjectTable(2*objectTable.length);
            return newHandle;
        }
    }

    public synchronized EntityHandle getAdd(Entity entity) throws DuplicateExtIdException {
        int indexObjectTable = indexForById(entity.getId(), objectTable.length);
        for (EntityHandle e = objectTable[indexObjectTable]; e != null; e = e.nextObject) {
            if (e.entity.equals(entity)) {
                if(e.getTypeInfo() == null)  e.setTypeInfo(workingMemory.getTypeInfo(entity.getClass()));
                return e;
            }
        }
        String extId = entity.getExtId();
        if(extId != null && extId.length() > 0) {
            EntityExtHandle newHandle = new EntityExtHandle(entity, objectTable[indexObjectTable], workingMemory.getTypeInfo(entity.getClass()));
            extIdMap.add(newHandle);
            
            objectTable[indexObjectTable] = newHandle;
            if (objectNumEntry++ >= objectThreshold)
                resizeObjectTable(2*objectTable.length);
            return newHandle;
        }
        else {
            EntityHandle newHandle = new EntityHandle(entity, objectTable[indexObjectTable], workingMemory.getTypeInfo(entity.getClass()));
            objectTable[indexObjectTable] = newHandle;
            if (objectNumEntry++ >= objectThreshold)
                resizeObjectTable(2*objectTable.length);
            return newHandle;
        }
    }

    private EntityHandle _remove(long id, boolean removeExtIdEntry, boolean notifyOM) {
        int i = indexForById(id, objectTable.length);
        EntityHandle prev = objectTable[i];
        EntityHandle e = prev;

        while (e != null) {
            EntityHandle next = e.nextObject;
            if (e.entity.getId() == id) {
                objectNumEntry--;
                if(removeExtIdEntry && (e instanceof EntityExtHandle)) {
                    _removeExtEntry((EntityExtHandle)e);
                }
                if (prev == e)
                    objectTable[i] = next;
                else
                    prev.nextObject = next;
                e.status |= BaseHandle.RETRACTED;
                if (notifyOM) {
                    if(workingMemory != null && workingMemory.getObjectManager() != null) workingMemory.getObjectManager().deleteEntity(e);
                }
                return e;
            }
            prev = e;
            e = next;
        }
        return null;
    }

    private void _removeExtEntry(EntityExtHandle entry) {
        extIdMap.remove(entry);
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


    public synchronized EntityHandle remove(Entity entity) {
        return _remove(entity.getId(), true, true);
    }

    public synchronized EntityHandle remove(long id) {
        return _remove(id, true, true);
    }

//    public synchronized EntityHandle cleanup(long id) {
//        return _remove(id, true, false);
//    }

//    public synchronized EntityExtHandle remove(String extId) {
//        return _remove(extId, true);
//    }

    public synchronized EntityHandle get(long id) {
        int i = indexForById(id, objectTable.length);
        EntityHandle cursor = objectTable[i];
        while (cursor != null) {
            if(cursor.entity.getId() == id) return cursor;
            cursor = cursor.nextObject;
        }
        return null;
    }

    public EntityHandle get(String extId) {
        ExtIdHandle ret = extIdMap.get(extId);
        if(ExtIdHandle.HandleType.ENTITY.isType(ret)) {
            return (EntityHandle) ret.getBaseHandle();
        }
        return null;
    }

    public Iterator iterator() {
        return new ObjectIterator();
    }

    public Iterator iterator(Class type) {
        return new ObjectTypeIterator(type);
    }

    public Iterator handleIterator() {
        return new HandleIterator();
    }

    private void resizeObjectTable(int newCapacity) {
        int oldCapacity = objectTable.length;
        if (oldCapacity == MAXIMUM_CAPACITY) {
            objectThreshold = Integer.MAX_VALUE;
            return;
        }
        EntityHandle[] newTable = new EntityHandle[newCapacity];
        for (int j = 0; j < objectTable.length; j++) {
            EntityHandle e = objectTable[j];
            if (e != null) {
                objectTable[j] = null;
                do {
                    EntityHandle next = e.nextObject;
                    int i        = indexForById(e.entity.getId(), newCapacity);
                    e.nextObject = newTable[i];
                    newTable[i]  = e;
                    e = next;
                } while (e != null);
            }
        }
        objectTable = newTable;
        objectThreshold = (int)(newCapacity * loadFactor);
    }

    public String contentToString() {
        String ret = ">>> EntityHandleMap " + Format.BRK;
        ret +=  "\tObjectTable lenght=" + objectTable.length + " numEntry=" + objectNumEntry + Format.BRK;

        for(int i = 0; i < objectTable.length; i++) {
            if(objectTable[i] != null) {
                EntityHandle cursor = objectTable[i];
                ret += "\tObjectTable[" + i + "]:" + Format.BRK + "\t\t";
                while(cursor != null) {
                    ret += "[Handle " + cursor.printInfo() + "] ";
                    cursor = cursor.nextObject;
                }
                ret += Format.BRK;
            }
        }
        ret += Format.BRK;
        ret += extIdMap.contentToString(ExtIdHandle.HandleType.ENTITY);
        return ret;
    }

    static public class EntityHandle extends BaseHandle {
        public final Entity entity;
        public EntityHandle nextObject;

        private EntityHandle(Entity _entity,  EntityHandle _next, TypeInfo _typeInfo) {
            super(_typeInfo);
            entity     = _entity;
            nextObject = _next;
        }

        public Object getObject() {
            return entity;
        }

        public boolean hasRef() {
            return entity != null;
        }
    }

    static class EntityExtHandle extends EntityHandle implements ExtIdHandle {
        public ExtIdHandle nextExtIdEntry;

        private EntityExtHandle(Entity _entity,  EntityHandle _next, TypeInfo _typeInfo) {
            super(_entity, _next, _typeInfo);
        }

        public String getExtId() {
            return entity.getExtId();
        }

        public ExtIdHandle getNextExtIdEntry() {
            return nextExtIdEntry;
        }

        public void setNextExtIdEntry(ExtIdHandle next) {
            nextExtIdEntry = next;
        }

        public HandleType getHandleType() {
            return HandleType.ENTITY;
        }
    }

    class ObjectIterator implements Iterator {
        EntityHandle m_cursor;
        int          m_currIndex;

        ObjectIterator() {
            m_currIndex = 0;
            m_cursor    = toNextTableEntry();
        }

        private EntityHandle toNextTableEntry() {
            while(m_currIndex != objectTable.length) {
                if(objectTable[m_currIndex] != null) {
                    return objectTable[m_currIndex++];
                }
                m_currIndex++;
            }
            return null;
        }

        public boolean hasNext() {
            return m_cursor != null;
        }

        public Object next() {
            Entity ret = m_cursor.entity;
            m_cursor  = m_cursor.nextObject;
            if(m_cursor == null) {
                m_cursor = toNextTableEntry();
            }
            return ret;
        }

        public void remove() {
            throw new RuntimeException("ObjectIterator.remove() is not implemented, should use EntityHandleMap.remove() to remove an entity");
        }
    }

    class ObjectTypeIterator implements Iterator {
        Class        m_type;
        EntityHandle  m_cursor;
        int          m_currIndex;

        ObjectTypeIterator(Class type) {
            m_type      = type;
            m_currIndex = -1;
            m_cursor    = null;
            toNextEntityEntry();
        }

        private void toNextEntityEntry() {
            while (m_cursor != null) {
                if(m_type.isAssignableFrom(m_cursor.entity.getClass())) {
                    return;
                }
                m_cursor = m_cursor.nextObject;
            }
            m_currIndex++;
            while(m_currIndex < objectTable.length) {
                m_cursor = objectTable[m_currIndex];
                while (m_cursor != null) {
                    if(m_type.isAssignableFrom(m_cursor.entity.getClass())) {
                        return;
                    }
                    m_cursor = m_cursor.nextObject;
                }
                m_currIndex++;
            }
            m_cursor = null;
        }

        public boolean hasNext() {
            return m_cursor != null;
        }

        public Object next() {
            Entity ret = m_cursor.entity;
            m_cursor   = m_cursor.nextObject;
            toNextEntityEntry();
            return ret;
        }

        public void remove() {
            throw new RuntimeException("ObjectTypeIterator.remove() is not implemented, should use EntityHandleMap.remove() to remove an entity");
        }
    }

    class HandleIterator implements Iterator {
        EntityHandle  m_cursor;
        int           m_currIndex;

        HandleIterator() {
            m_currIndex = 0;
            m_cursor    = toNextTableEntry();
        }

        private EntityHandle toNextTableEntry() {
            while(m_currIndex != objectTable.length) {
                if(objectTable[m_currIndex] != null) {
                    return objectTable[m_currIndex++];
                }
                m_currIndex++;
            }
            return null;
        }

        public boolean hasNext() {
            return m_cursor != null;
        }

        public Object next() {
            EntityHandle ret = m_cursor;
            m_cursor  = m_cursor.nextObject;
            if(m_cursor == null) {
                m_cursor = toNextTableEntry();
            }
            return ret;
        }

        public void remove() {
            throw new RuntimeException("HandleIterator.remove() is not implemented, should use EntityHandleMap.remove() to remove an entity");
        }
    }



}
