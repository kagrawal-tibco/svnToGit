package com.tibco.cep.kernel.core.base;

import java.util.Iterator;

import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateException;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.service.ResourceManager;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 13, 2006
 * Time: 3:05:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventHandleMap {
    transient AbstractEventHandle[]     objectTable;
    transient int               objectThreshold;
    transient int               objectNumEntry;

    static final int   DEFAULT_INITIAL_CAPACITY = 16;
    static final int   MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    transient float    loadFactor;
    final transient WorkingMemoryImpl workingMemory;
    private final ExtIdMap          extIdMap;

    public EventHandleMap(WorkingMemoryImpl wm, ExtIdMap extIdMap) {
        loadFactor = DEFAULT_LOAD_FACTOR;

        objectTable     = new AbstractEventHandle[DEFAULT_INITIAL_CAPACITY];
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
                AbstractEventHandle handle = (AbstractEventHandle) ite.next();
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


    public synchronized AbstractEventHandle add(long id, String extId, Class type) throws DuplicateException, DuplicateExtIdException {
        int indexObjectTable = indexForById(id, objectTable.length);
        for (AbstractEventHandle e = objectTable[indexObjectTable]; e != null; e = e.nextHandle) {
            if (e.getEventId() == id) {
                String objStr = type.getName() + "@id=" + id + "@extId=" + extId;
                throw new DuplicateException(ResourceManager.formatString("entity.duplicate", objStr));
            }
        }
        if(extId != null && extId.length() > 0) {
            AbstractEventHandle newHandle = ((BaseObjectManager)workingMemory.getObjectManager()).getNewEventExtHandle(new DummyEvent(id, extId), objectTable[indexObjectTable], workingMemory.getTypeInfo(type));
            extIdMap.add((ExtIdHandle) newHandle);

            objectTable[indexObjectTable] = newHandle;
            if (objectNumEntry++ >= objectThreshold)
                resizeObjectTable(2*objectTable.length);
            return newHandle;
        }
        else {
            AbstractEventHandle newHandle = ((BaseObjectManager)workingMemory.getObjectManager()).getNewEventHandle(new DummyEvent(id, extId), objectTable[indexObjectTable], workingMemory.getTypeInfo(type));
            objectTable[indexObjectTable] = newHandle;
            if (objectNumEntry++ >= objectThreshold)
                resizeObjectTable(2*objectTable.length);
            return newHandle;
        }
    }


    public synchronized AbstractEventHandle add(Event event) throws DuplicateException, DuplicateExtIdException {
        int indexObjectTable = indexForById(event.getId(), objectTable.length);
        for (AbstractEventHandle e = objectTable[indexObjectTable]; e != null; e = e.nextHandle) {
            if (e.getEventId() == event.getId()) {
                throw new DuplicateException(ResourceManager.formatString("entity.duplicate", event));
            }
        }
        String extId = event.getExtId();
        if(extId != null && extId.length() > 0) {
            AbstractEventHandle newHandle = ((BaseObjectManager)workingMemory.getObjectManager()).getNewEventExtHandle(event, objectTable[indexObjectTable], workingMemory.getTypeInfo(event.getClass()));
            extIdMap.add((ExtIdHandle) newHandle);
            
            objectTable[indexObjectTable] = newHandle;
            if (objectNumEntry++ >= objectThreshold)
                resizeObjectTable(2*objectTable.length);
            return newHandle;
        }
        else {
            AbstractEventHandle newHandle = ((BaseObjectManager)workingMemory.getObjectManager()).getNewEventHandle(event, objectTable[indexObjectTable], workingMemory.getTypeInfo(event.getClass()));
            objectTable[indexObjectTable] = newHandle;
            if (objectNumEntry++ >= objectThreshold)
                resizeObjectTable(2*objectTable.length);
            return newHandle;
        }
    }

    public synchronized AbstractEventHandle getAdd(Event event) throws DuplicateExtIdException {
        int indexObjectTable = indexForById(event.getId(), objectTable.length);
        for (AbstractEventHandle e = objectTable[indexObjectTable]; e != null; e = e.nextHandle) {
            if (e.getEventId() == event.getId()) {
                if(e.getTypeInfo() == null)  e.setTypeInfo(workingMemory.getTypeInfo(event.getClass()));
                return e;
            }
        }
        String extId = event.getExtId();
        if(extId != null && extId.length() > 0) {
            AbstractEventHandle newHandle = ((BaseObjectManager)workingMemory.getObjectManager()).getNewEventExtHandle(event, objectTable[indexObjectTable], workingMemory.getTypeInfo(event.getClass()));
            extIdMap.add((ExtIdHandle) newHandle);
            
            objectTable[indexObjectTable] = newHandle;
            if (objectNumEntry++ >= objectThreshold)
                resizeObjectTable(2*objectTable.length);
            return newHandle;
        }
        else {
            AbstractEventHandle newHandle = ((BaseObjectManager)workingMemory.getObjectManager()).getNewEventHandle(event, objectTable[indexObjectTable], workingMemory.getTypeInfo(event.getClass()));
            objectTable[indexObjectTable] = newHandle;
            if (objectNumEntry++ >= objectThreshold)
                resizeObjectTable(2*objectTable.length);
            return newHandle;
        }
    }

    private AbstractEventHandle _remove(long id, boolean removeExtIdEntry, boolean notifyOM) {
        int i = indexForById(id, objectTable.length);
        AbstractEventHandle prev = objectTable[i];
        AbstractEventHandle e = prev;

        while (e != null) {
            AbstractEventHandle next = e.nextHandle;
            if (e.getEventId() == id) {
                objectNumEntry--;
                if(removeExtIdEntry && (e instanceof ExtIdHandle)) {
                    _removeExtEntry((ExtIdHandle)e);
                }
                if (prev == e)
                    objectTable[i] = next;
                else
                    prev.nextHandle = next;
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

    private void _removeExtEntry(ExtIdHandle entry) {
        extIdMap.remove(entry);
    }

//    private EventExtHandle _remove(String extId, boolean removeIdEntry) {
//        if(extId == null) return null;
//        int i = indexForByExtId(extId, extIdTable.length);
//        EventExtHandle prev = extIdTable[i];
//        EventExtHandle e = prev;
//
//        while (e != null) {
//            EventExtHandle next = e.nextExtIdEntry;
//            if (e.event.getExtId().equals(extId)) {
//                extIdNumEntry--;
//                if(removeIdEntry) {
//                    _remove(e.event.getId(), false);
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


    public synchronized AbstractEventHandle remove(Event event) {
        return _remove(event.getId(), true, true);
    }

    public synchronized AbstractEventHandle remove(long id) {
        return _remove(id, true, true);
    }

//    public synchronized AbstractEventHandle cleanup(long id) {
//        return _remove(id, true, false);
//    }

//    public synchronized EventExtHandle remove(String extId) {
//        return _remove(extId, true);
//    }

    public synchronized AbstractEventHandle get(long id) {
        int i = indexForById(id, objectTable.length);
        AbstractEventHandle cursor = objectTable[i];
        while (cursor != null) {
            if(cursor.getEventId() == id) return cursor;
            cursor = cursor.nextHandle;
        }
        return null;
    }

    public AbstractEventHandle get(String extId) {
        ExtIdHandle ehandle = extIdMap.get(extId);
        if(ExtIdHandle.HandleType.EVENT.isType(ehandle)) {
            return (AbstractEventHandle) ehandle.getBaseHandle();
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
        AbstractEventHandle[] newTable = new AbstractEventHandle[newCapacity];
        for (int j = 0; j < objectTable.length; j++) {
            AbstractEventHandle e = objectTable[j];
            if (e != null) {
                objectTable[j] = null;
                do {
                    AbstractEventHandle next = e.nextHandle;
                    int i        = indexForById(e.getEventId(), newCapacity);
                    e.nextHandle = newTable[i];
                    newTable[i]  = e;
                    e = next;
                } while (e != null);
            }
        }
        objectTable = newTable;
        objectThreshold = (int)(newCapacity * loadFactor);
    }

    public String contentToString() {
        String ret = ">>> EventHandleMap " + Format.BRK;
        ret +=  "\tObjectTable lenght=" + objectTable.length + " numEntry=" + objectNumEntry + Format.BRK;

        for(int i = 0; i < objectTable.length; i++) {
            if(objectTable[i] != null) {
                AbstractEventHandle cursor = objectTable[i];
                ret += "\tObjectTable[" + i + "]:" + Format.BRK + "\t\t";
                while(cursor != null) {
                    ret += "[Handle " + cursor.printInfo() + "] ";
                    cursor = cursor.nextHandle;
                }
                ret += Format.BRK;
            }
        }
        ret += Format.BRK;
        ret += extIdMap.contentToString(ExtIdHandle.HandleType.EVENT);
        return ret;
    }

    static public class EventHandle extends AbstractEventHandle {
        public Event event;

        public EventHandle(Event _event,  AbstractEventHandle _next, TypeInfo _typeInfo) {
            super(_next, _typeInfo);
            event     = _event;
        }

        public Object getObject() {
            return event;
        }

        public boolean hasRef() {
            return event != null;
        }

        public long getEventId() {
            return event.getId();  //To change body of implemented methods use File | Settings | File Templates.
        }
        
        public void removeRef() {
            event = null;
        }
    }

    static public class EventExtHandle extends EventHandle implements ExtIdHandle
    {
        ExtIdHandle nextExtIdEntry;

        public EventExtHandle(Event _event, AbstractEventHandle _next, TypeInfo _typeInfo) {
            super(_event, _next, _typeInfo);
        }

        public Object getObject() {
            return event;
        }

        public boolean hasRef() {
            return event != null;
        }

        public long getEventId() {
            return event.getId();
        }

        public void removeRef() {
            event = null;
        }

        public String getExtId() {
            return event.getExtId();
        }

        public ExtIdHandle getNextExtIdEntry() {
            return nextExtIdEntry;
        }

        public void setNextExtIdEntry(ExtIdHandle next) {
            nextExtIdEntry = next;
        }

        public HandleType getHandleType() {
            return HandleType.EVENT;
        }
    }

    class ObjectIterator implements Iterator {
        AbstractEventHandle  m_cursor;
        int          m_currIndex;

        ObjectIterator() {
            m_currIndex = 0;
            m_cursor    = toNextTableEntry();
        }

        private AbstractEventHandle toNextTableEntry() {
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
            Event ret = (Event) m_cursor.getObject();
            m_cursor  = m_cursor.nextHandle;
            if(m_cursor == null) {
                m_cursor = toNextTableEntry();
            }
            return ret;
        }

        public void remove() {
            throw new RuntimeException("ObjectIterator.remove() is not implemented, should use EventHandleMap.remove() to remove an event");
        }
    }

    class ObjectTypeIterator implements Iterator {
        Class        m_type;
        AbstractEventHandle  m_cursor;
        int          m_currIndex;

        ObjectTypeIterator(Class type) {
            m_type      = type;
            m_currIndex = -1;
            m_cursor    = null;
            toNextEntityEntry();
        }

        private void toNextEntityEntry() {
            while (m_cursor != null) {
                Object obj = m_cursor.getObject();
                if(obj != null && m_type.isAssignableFrom(obj.getClass())) {
                    return;
                }
                m_cursor = m_cursor.nextHandle;
            }
            m_currIndex++;
            while(m_currIndex < objectTable.length) {
                m_cursor = objectTable[m_currIndex];
                while (m_cursor != null) {
                    Object obj = m_cursor.getObject();
                    if(obj != null && m_type.isAssignableFrom(obj.getClass())) {
                        return;
                    }
                    m_cursor = m_cursor.nextHandle;
                }
                m_currIndex++;
            }
            m_cursor = null;
        }

        public boolean hasNext() {
            return m_cursor != null;
        }

        public Object next() {
            Event ret = (Event) m_cursor.getObject();
            m_cursor   = m_cursor.nextHandle;
            toNextEntityEntry();
            return ret;
        }

        public void remove() {
            throw new RuntimeException("ObjectTypeIterator.remove() is not implemented, should use EventHandleMap.remove() to remove an event");
        }
    }

    class HandleIterator implements Iterator {
        AbstractEventHandle   m_cursor;
        int           m_currIndex;

        HandleIterator() {
            m_currIndex = 0;
            m_cursor    = toNextTableEntry();
        }

        private AbstractEventHandle toNextTableEntry() {
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
            AbstractEventHandle ret = m_cursor;
            m_cursor  = m_cursor.nextHandle;
            if(m_cursor == null) {
                m_cursor = toNextTableEntry();
            }
            return ret;
        }

        public void remove() {
            throw new RuntimeException("HandleIterator.remove() is not implemented, should use EventHandleMap.remove() to remove an event");
        }
    }

    private static class DummyEvent implements Event {
        long id;
        String extId;

        DummyEvent(long id_, String extId_) {
            id = id_;
            extId = extId_;
        }

        public boolean getRetryOnException() {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public boolean isLoadedFromCache() {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void setLoadedFromCache() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public void setTTL(long ttl) {
        }

        public long getTTL() {
            return 0;
        }

        public boolean hasExpiryAction() {
            return false;
        }

        public void onExpiry() {
        }

        public String getExtId() {
            return extId;
        }

        public long getId() {
            return id;
        }

        public void start(Handle handle) {
            }

        public Object getPropertyValue(String name) throws NoSuchFieldException {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getType() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void setPropertyValue(String name, Object value) throws Exception {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

}
