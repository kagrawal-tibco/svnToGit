package com.tibco.cep.kernel.core.base;

import java.util.Iterator;
import java.util.List;

import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.NamedInstance;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateException;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.service.ResourceManager;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 13, 2006
 * Time: 3:23:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ElementHandleMap {
    transient AbstractElementHandle[]    objectTable;
    transient int                objectThreshold;
    transient int                objectNumEntry;

    static final int   DEFAULT_INITIAL_CAPACITY = 16;
    static final int   MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    transient float    loadFactor;
    final transient WorkingMemoryImpl workingMemory;
    private final ExtIdMap          extIdMap;

    public ElementHandleMap(WorkingMemoryImpl wm, ExtIdMap extIdMap) {
        loadFactor = DEFAULT_LOAD_FACTOR;

        objectTable     = new AbstractElementHandle[DEFAULT_INITIAL_CAPACITY];
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
                AbstractElementHandle handle = (AbstractElementHandle) ite.next();
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

    //this mehtod is only for the coherence store OM!!!!!!
    public synchronized AbstractElementHandle add(long id, String extId, Class type) throws DuplicateException, DuplicateExtIdException {
        int indexObjectTable = indexForById(id,  objectTable.length);
        for (AbstractElementHandle e = objectTable[indexObjectTable]; e != null; e = e.nextHandle) {
            if (e.getElementId() == id) {
                String objStr = type.getName() + "@id=" + id + "@extId=" + extId;
                throw new DuplicateException(ResourceManager.formatString("entity.duplicate", objStr));
            }
        }
        if(extId != null && extId.length() > 0) {
            //AbstractElementExtHandle newHandle = new ElementExtHandle(instance, objectTable[indexObjectTable]);
            AbstractElementHandle newHandle = ((BaseObjectManager)workingMemory.getObjectManager()).getNewElementExtHandle(new DummyElement(id, extId), objectTable[indexObjectTable], workingMemory.getTypeInfo(type));
            extIdMap.add((ExtIdHandle)newHandle);
            
            objectTable[indexObjectTable] = newHandle;
            if (objectNumEntry++ >= objectThreshold)
                resizeObjectTable(2*objectTable.length);
            return newHandle;
        }
        else {
            //ElementHandle newHandle = new ElementHandle(instance, objectTable[indexObjectTable]);
            AbstractElementHandle newHandle = ((BaseObjectManager)workingMemory.getObjectManager()).getNewElementHandle(new DummyElement(id, extId), objectTable[indexObjectTable], workingMemory.getTypeInfo(type));
            objectTable[indexObjectTable] = newHandle;
            if (objectNumEntry++ >= objectThreshold)
                resizeObjectTable(2*objectTable.length);
            return newHandle;
        }
    }


    public synchronized AbstractElementHandle add(Element instance) throws DuplicateException, DuplicateExtIdException {
        int indexObjectTable = indexForById(instance.getId(), objectTable.length);
        for (AbstractElementHandle e = objectTable[indexObjectTable]; e != null; e = e.nextHandle) {
            if (e.getElementId() == instance.getId()) {
                throw new DuplicateException(ResourceManager.formatString("entity.duplicate", instance));
            }
        }
        String extId = instance.getExtId();
        if(extId != null && extId.length() > 0) {
            //AbstractElementExtHandle newHandle = new ElementExtHandle(instance, objectTable[indexObjectTable]);
            AbstractElementHandle newHandle = ((BaseObjectManager)workingMemory.getObjectManager()).getNewElementExtHandle(instance, objectTable[indexObjectTable], workingMemory.getTypeInfo(instance.getClass()));
            extIdMap.add((ExtIdHandle)newHandle);
            
            objectTable[indexObjectTable] = newHandle;
            if (objectNumEntry++ >= objectThreshold)
                resizeObjectTable(2*objectTable.length);
            return newHandle;
        }
        else {
            //ElementHandle newHandle = new ElementHandle(instance, objectTable[indexObjectTable]);
            AbstractElementHandle newHandle = ((BaseObjectManager)workingMemory.getObjectManager()).getNewElementHandle(instance, objectTable[indexObjectTable], workingMemory.getTypeInfo(instance.getClass()));
            objectTable[indexObjectTable] = newHandle;
            if (objectNumEntry++ >= objectThreshold)
                resizeObjectTable(2*objectTable.length);
            return newHandle;
        }
    }

    public synchronized AbstractElementHandle getRemoteAdd(String clusterName, Element instance) throws DuplicateExtIdException {
        int indexObjectTable = indexForById(instance.getId(), objectTable.length);
        for (AbstractElementHandle e = objectTable[indexObjectTable]; e != null; e = e.nextHandle) {
            if (e.getElementId() == instance.getId()) {
                if(e.getTypeInfo() == null) e.setTypeInfo(workingMemory.getTypeInfo(instance.getClass()));
                return e;
            }
        }
        String extId = clusterName+ "$"+ instance.getExtId();
        if(extId != null && extId.length() > 0) {
            //ElementExtHandle newHandle = new ElementExtHandle(instance, objectTable[indexObjectTable]);
            AbstractElementHandle newHandle = ((RemoteObjectManager)workingMemory.getObjectManager()).getRemoteNewElementExtHandle(clusterName, instance, objectTable[indexObjectTable]);
            extIdMap.add((ExtIdHandle)newHandle);
            
            objectTable[indexObjectTable] = newHandle;
            if (objectNumEntry++ >= objectThreshold)
                resizeObjectTable(2*objectTable.length);
            return newHandle;
        }
        else {
            //ElementHandle newHandle = new ElementHandle(instance, objectTable[indexObjectTable]);
            AbstractElementHandle newHandle = ((RemoteObjectManager)workingMemory.getObjectManager()).getRemoteNewElementHandle(clusterName, instance, objectTable[indexObjectTable]);
            objectTable[indexObjectTable] = newHandle;
            if (objectNumEntry++ >= objectThreshold)
                resizeObjectTable(2*objectTable.length);
            return newHandle;
        }
    }

    public synchronized AbstractElementHandle getAdd(Element instance) throws DuplicateExtIdException {
        int indexObjectTable = indexForById(instance.getId(), objectTable.length);
        for (AbstractElementHandle e = objectTable[indexObjectTable]; e != null; e = e.nextHandle) {
            if (e.getElementId() == instance.getId()) {
                if(e.getTypeInfo() == null)  e.setTypeInfo(workingMemory.getTypeInfo(instance.getClass()));
                return e;
            }
        }
        String extId = instance.getExtId();
        if(extId != null && extId.length() > 0) {
            //ElementExtHandle newHandle = new ElementExtHandle(instance, objectTable[indexObjectTable]);
            AbstractElementHandle newHandle = ((BaseObjectManager)workingMemory.getObjectManager()).getNewElementExtHandle(instance, objectTable[indexObjectTable], workingMemory.getTypeInfo(instance.getClass()));
            extIdMap.add((ExtIdHandle)newHandle);
            
            objectTable[indexObjectTable] = newHandle;
            if (objectNumEntry++ >= objectThreshold)
                resizeObjectTable(2*objectTable.length);
            return newHandle;
        }
        else {
            //ElementHandle newHandle = new ElementHandle(instance, objectTable[indexObjectTable]);
            AbstractElementHandle newHandle = ((BaseObjectManager)workingMemory.getObjectManager()).getNewElementHandle(instance, objectTable[indexObjectTable], workingMemory.getTypeInfo(instance.getClass()));
            objectTable[indexObjectTable] = newHandle;
            if (objectNumEntry++ >= objectThreshold)
                resizeObjectTable(2*objectTable.length);
            return newHandle;
        }
    }

    private AbstractElementHandle _remove(long id, boolean removeExtIdEntry, boolean notifyOM) {
        int i = indexForById(id, objectTable.length);
        AbstractElementHandle prev = objectTable[i];
        AbstractElementHandle e = prev;

        while (e != null) {
            AbstractElementHandle next = e.nextHandle;
            if (e.getElementId() == id) {
                objectNumEntry--;
                if(removeExtIdEntry && (e instanceof ExtIdHandle)) {
                    extIdMap.remove((ExtIdHandle)e);
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

    public synchronized AbstractElementHandle remove(Element element) {
        return _remove(element.getId(), true, true);
    }

    public synchronized AbstractElementHandle remove(long id) {
        return _remove(id, true, true);
    }

//    public synchronized AbstractElementHandle cleanup(long id) {
//        return _remove(id, true, false);
//    }

    public synchronized AbstractElementHandle get(long id) {
        int i = indexForById(id, objectTable.length);
        AbstractElementHandle cursor = objectTable[i];
        while (cursor != null) {
            if(cursor.getElementId() == id) return cursor;
            cursor = cursor.nextHandle;
        }
        return null;
    }

    public AbstractElementHandle get(String extId) {
        ExtIdHandle ret = extIdMap.get(extId);
        if(ExtIdHandle.HandleType.ELEMENT.isType(ret)) {
            return (AbstractElementHandle) ret.getBaseHandle();
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

    public Iterator namedInstanceIterator() {
        return new NamedInstanceIterator();
    }

    private void resizeObjectTable(int newCapacity) {
        int oldCapacity = objectTable.length;
        if (oldCapacity == MAXIMUM_CAPACITY) {
            objectThreshold = Integer.MAX_VALUE;
            return;
        }
        AbstractElementHandle[] newTable = new AbstractElementHandle[newCapacity];
        for (int j = 0; j < objectTable.length; j++) {
            AbstractElementHandle e = objectTable[j];
            if (e != null) {
                objectTable[j] = null;
                do {
                    AbstractElementHandle next = e.nextHandle;
                    int i        = indexForById(e.getElementId(), newCapacity);
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
        String ret = ">>> ElementHandleMap " + Format.BRK;
        ret +=  "\tObjectTable lenght=" + objectTable.length + " numEntry=" + objectNumEntry + Format.BRK;
        for(int i = 0; i < objectTable.length; i++) {
            if(objectTable[i] != null) {
                AbstractElementHandle cursor = objectTable[i];
                ret += "\tObjectTable[" + i + "]:" + Format.BRK + "\t\t";
                while(cursor != null) {
                    ret += "[Handle " + cursor.printInfo() + "] ";
                    cursor = cursor.nextHandle;
                }
                ret += Format.BRK;
            }
        }
        
        ret += Format.BRK;
        ret += extIdMap.contentToString(ExtIdHandle.HandleType.ELEMENT);
        return ret;
    }

    static public class ElementHandle extends AbstractElementHandle {
        public final Element element;

        public ElementHandle(Element _element,  AbstractElementHandle _next, TypeInfo _typeInfo) {
            super(_next, _typeInfo);
            element     = _element;
        }

        public Object getObject() {
            return element;
        }

        public long getElementId() {
            return element.getId();
        }

        public boolean hasRef() {
            return element != null;
        }
    }

    static public class ElementExtHandle extends ElementHandle implements ExtIdHandle
    {
        ExtIdHandle nextExtIdEntry;
        
        public ElementExtHandle(Element _element, AbstractElementHandle _next, TypeInfo _typeInfo) {
            super(_element, _next, _typeInfo);
        }

        public Object getObject() {
            return element;
        }

        public long getElementId() {
            return element.getId();
        }

        public boolean hasRef() {
            return element != null;
        }

        public String getExtId() {
            return element.getExtId();
        }

        public ExtIdHandle getNextExtIdEntry() {
            return nextExtIdEntry;
        }

        public void setNextExtIdEntry(ExtIdHandle next) {
            nextExtIdEntry = next;
        }

        public HandleType getHandleType() {
            return HandleType.ELEMENT;
        }
    }

    class ObjectIterator implements Iterator {
        AbstractElementHandle m_cursor;
        int           m_currIndex;

        ObjectIterator() {
            m_currIndex = 0;
            m_cursor    = toNextTableEntry();
        }

        private AbstractElementHandle toNextTableEntry() {
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
            Element ret = (Element) m_cursor.getObject();
            m_cursor  = m_cursor.nextHandle;
            if(m_cursor == null) {
                m_cursor = toNextTableEntry();
            }
            return ret;
        }

        public void remove() {
            throw new RuntimeException("ObjectIterator.remove() is not implemented, should use ElementHandleMap.remove() to remove an element");
        }
    }

    class ObjectTypeIterator implements Iterator {
        Class         m_type;
        AbstractElementHandle m_cursor;
        int           m_currIndex;

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
            Element ret = (Element) m_cursor.getObject();
            m_cursor    = m_cursor.nextHandle;
            toNextEntityEntry();
            return ret;
        }

        public void remove() {
            throw new RuntimeException("ObjectTypeIterator.remove() is not implemented, should use ElementHandleMap.remove() to remove an element instance");
        }
    }

    class HandleIterator implements Iterator {
        AbstractElementHandle m_cursor;
        int           m_currIndex;

        HandleIterator() {
            m_currIndex = 0;
            m_cursor    = toNextTableEntry();
        }

        private AbstractElementHandle toNextTableEntry() {
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
            AbstractElementHandle ret = m_cursor;
            m_cursor = m_cursor.nextHandle;
            if(m_cursor == null) {
                m_cursor = toNextTableEntry();
            }
            return ret;
        }

        public void remove() {
            throw new RuntimeException("HandleIterator.remove() is not implemented, should use ElementHandleMap.remove() to remove an element instance");
        }
    }

    class NamedInstanceIterator implements Iterator {
        AbstractElementHandle m_cursor;
        int           m_currIndex;

        NamedInstanceIterator() {
            m_currIndex = -1;
            m_cursor    = null;
            toNextEntityEntry();
        }

        private void toNextEntityEntry() {
            while (m_cursor != null) {
                if(m_cursor.getObject() instanceof NamedInstance) {
                    return;
                }
                m_cursor = m_cursor.nextHandle;
            }
            m_currIndex++;
            while(m_currIndex < objectTable.length) {
                m_cursor = objectTable[m_currIndex];
                while (m_cursor != null) {
                    if(m_cursor.getObject() instanceof NamedInstance) {
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
            Element ret = (Element) m_cursor.getObject();
            m_cursor    = m_cursor.nextHandle;
            toNextEntityEntry();
            return ret;
        }

        public void remove() {
            throw new RuntimeException("NamedInstanceIterator.remove() is not implemented, should use ElementHandleMap.remove() to remove an instance");
        }
    }


    private static class DummyElement implements Element {
        long id;
        String extId;
        DummyElement(long id_, String extId_) {
            id = id_;
            extId = extId_;
        }

        public boolean isLoadedFromCache() {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void setLoadedFromCache() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public void clearChildrenDirtyBits() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public boolean lock(long waitTimeInNanos) {
            return true;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public boolean isMarkedDeleted() {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public List getChildren() {
            return null;
        }

        public List getChildIds() {
            return null;
        }

        public void delete() {
        }

        public String getExtId() {
            return extId;
        }

        public long getId() {
            return id;
        }

        public void start(Handle handle) {
        }

        public int[] getDirtyBitArray() {
            return new int[0];
        }

        public void clearDirtyBitArray() {
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
