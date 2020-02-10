package com.tibco.cep.kernel.core.base;

import java.util.Iterator;

import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateException;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.service.ResourceManager;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 25, 2006
 * Time: 5:52:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObjectHandleMap {
    transient ObjectHandle[] objectTable;
    transient int            objectThreshold;
    transient int            objectNumEntry;

    static final int   DEFAULT_INITIAL_CAPACITY = 16;
    static final int   MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    transient float    loadFactor;
    transient WorkingMemoryImpl workingMemory;

    public ObjectHandleMap(WorkingMemoryImpl wm) {
        loadFactor = DEFAULT_LOAD_FACTOR;

        objectTable     = new ObjectHandle[DEFAULT_INITIAL_CAPACITY];
        objectThreshold = (int)(DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
        objectNumEntry  = 0;

        workingMemory   = wm;
    }

    static int indexForObject(Object obj, int length) {
        int h = obj.hashCode();
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
                ObjectHandle handle = (ObjectHandle) ite.next();
                handle.setRetracted();
            }
            for(int i =0; i < objectTable.length; i++) {
                objectTable[i] = null;
            }
            objectNumEntry = 0;
        }
    }

    public int size() {
        return objectNumEntry;
    }

    public synchronized ObjectHandle add(Object object) throws DuplicateException {
        int indexObjectTable = indexForObject(object, objectTable.length);
        for (ObjectHandle e = objectTable[indexObjectTable]; e != null; e = e.nextObject) {
            if (e.object.equals(object)) {
                throw new DuplicateException(ResourceManager.formatString("object.duplicate", object));
            }
        }
        ObjectHandle newHandle = new ObjectHandle(object, objectTable[indexObjectTable], workingMemory.getTypeInfo(object.getClass()));
        objectTable[indexObjectTable] = newHandle;
        if (objectNumEntry++ >= objectThreshold)
            resizeObjectTable(2*objectTable.length);
        return newHandle;
    }

    public synchronized ObjectHandle getAdd(Object object) {
        int indexObjectTable = indexForObject(object, objectTable.length);
        for (ObjectHandle e = objectTable[indexObjectTable]; e != null; e = e.nextObject) {
            if (e.object.equals(object)) {
                if(e.getTypeInfo() == null) e.setTypeInfo(workingMemory.getTypeInfo(object.getClass()));
                return e;
            }
        }
        ObjectHandle newHandle = new ObjectHandle(object, objectTable[indexObjectTable], workingMemory.getTypeInfo(object.getClass()));
        objectTable[indexObjectTable] = newHandle;
        if (objectNumEntry++ >= objectThreshold)
            resizeObjectTable(2*objectTable.length);
        return newHandle;
    }


    public synchronized ObjectHandle remove(Object object) {
        int i = indexForObject(object, objectTable.length);
        ObjectHandle prev = objectTable[i];
        ObjectHandle e = prev;

        while (e != null) {
            ObjectHandle next = e.nextObject;
            if (e.object.equals(object)) {
                objectNumEntry--;
                if (prev == e)
                    objectTable[i] = next;
                else
                    prev.nextObject = next;
                e.status |= BaseHandle.RETRACTED;                                
                return e;
            }
            prev = e;
            e = next;
        }
        return null;
    }

    public synchronized ObjectHandle get(Object object) {
        int i = indexForObject(object, objectTable.length);
        ObjectHandle cursor = objectTable[i];
        while (cursor != null) {
            if(cursor.object.equals(object)) return cursor;
            cursor = cursor.nextObject;
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
        ObjectHandle[] newTable = new ObjectHandle[newCapacity];
        for (int j = 0; j < objectTable.length; j++) {
            ObjectHandle e = objectTable[j];
            if (e != null) {
                objectTable[j] = null;
                do {
                    ObjectHandle next = e.nextObject;
                    int i        = indexForObject(e.object, newCapacity);
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
        String ret = new String(">>> ObjectHandleMap " + Format.BRK);
        ret +=  "\tObjectTable lenght=" + objectTable.length + " numEntry=" + objectNumEntry + Format.BRK;

        for(int i = 0; i < objectTable.length; i++) {
            if(objectTable[i] != null) {
                ObjectHandle cursor = objectTable[i];
                ret += "\tObjectTable[" + i + "]:" + Format.BRK + "\t\t";
                while(cursor != null) {
                    ret += "[Handle " + cursor.printInfo() + "] ";
                    cursor = cursor.nextObject;
                }
                ret += Format.BRK;
            }
        }
        ret += Format.BRK;
        return ret;
    }

    static public class ObjectHandle extends BaseHandle {
        public final Object object;
        public ObjectHandle nextObject;

        private ObjectHandle(Object _object,  ObjectHandle _next, TypeInfo _typeInfo) {
            super(_typeInfo);
            object     = _object;
            nextObject = _next;
        }

        public Object getObject() {
            return object;
        }

        public boolean hasRef() {
            return object != null;
        }
    }

    class ObjectIterator implements Iterator {
        ObjectHandle  m_cursor;
        int          m_currIndex;

        ObjectIterator() {
            m_currIndex = 0;
            m_cursor    = toNextTableEntry();
        }

        private ObjectHandle toNextTableEntry() {
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
            Object ret = m_cursor.object;
            m_cursor  = m_cursor.nextObject;
            if(m_cursor == null) {
                m_cursor = toNextTableEntry();
            }
            return ret;
        }

        public void remove() {
            throw new RuntimeException("ObjectIterator.remove() is not implemented, should use ObjectHandleMap.remove() to remove an object");
        }
    }

    class ObjectTypeIterator implements Iterator {
        Class         m_type;
        ObjectHandle  m_cursor;
        int           m_currIndex;

        ObjectTypeIterator(Class type) {
            m_type      = type;
            m_currIndex = -1;
            m_cursor    = null;
            toNextEntityEntry();
        }

        private void toNextEntityEntry() {
            while (m_cursor != null) {
                if(m_type.isAssignableFrom(m_cursor.object.getClass())) {
                    return;
                }
                m_cursor = m_cursor.nextObject;
            }
            m_currIndex++;
            while(m_currIndex < objectTable.length) {
                m_cursor = objectTable[m_currIndex];
                while (m_cursor != null) {
                    if(m_type.isAssignableFrom(m_cursor.object.getClass())) {
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
            Object ret = m_cursor.object;
            m_cursor   = m_cursor.nextObject;
            toNextEntityEntry();
            return ret;
        }

        public void remove() {
            throw new RuntimeException("ObjectTypeIterator.remove() is not implemented, should use ObjectHandleMap.remove() to remove an object");
        }
    }

    class HandleIterator implements Iterator {
        ObjectHandle  m_cursor;
        int           m_currIndex;

        HandleIterator() {
            m_currIndex = 0;
            m_cursor    = toNextTableEntry();
        }

        private ObjectHandle toNextTableEntry() {
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
            ObjectHandle ret = m_cursor;
            m_cursor  = m_cursor.nextObject;
            if(m_cursor == null) {
                m_cursor = toNextTableEntry();
            }
            return ret;
        }

        public void remove() {
            throw new RuntimeException("ObjectIterator.remove() is not implemented, should use ObjectHandleMap.remove() to remove an object");
        }
    }



}
