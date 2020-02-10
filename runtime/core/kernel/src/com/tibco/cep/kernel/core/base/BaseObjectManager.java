package com.tibco.cep.kernel.core.base;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateException;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.service.Filter;
import com.tibco.cep.kernel.service.ObjectManager;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Aug 17, 2006
 * Time: 10:48:45 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class BaseObjectManager implements ObjectManager {

    protected EventHandleMap   m_eventHandleMap;
    protected ElementHandleMap m_elementHandleMap;
    protected EntityHandleMap  m_entityHandleMap;
    protected ObjectHandleMap  m_objectHandleMap;

    protected WorkingMemoryImpl m_workingMemory;
    protected String            m_name;
    
    protected BaseObjectManager(String name) {
        m_name = name;
    }

    abstract public void applyChanges(RtcOperationList rtcList);

    abstract public void init(WorkingMemory workingMemory) throws Exception;

    abstract public void start() throws Exception;

    abstract public void stop() throws Exception;

    abstract public void shutdown() throws Exception;

    public String getName() {
        return m_name;
    }

    public BaseTimeManager getTimeManager() {
        return null;
    }

    public void reset() {
        //remove all the object from map
        m_eventHandleMap.reset();
        m_elementHandleMap.reset();
        m_entityHandleMap.reset();
        m_objectHandleMap.reset();
    }

    public void createElement(Element entity) throws DuplicateExtIdException {
    }

    public void createEvent(Event event) throws DuplicateExtIdException {
    }

    @Override
    public BaseHandle createHandle(Object object) throws DuplicateExtIdException {
        throw new RuntimeException("Method not implemented... Please check who is calling");
    }

    @Override
    public boolean handleExists(Object object) {
        throw new RuntimeException("Method not implemented... Please check who is calling");
    }

    public void deleteEntity(Handle handle){}
    
    public void setActiveMode(boolean mode) {}

    //should remove these methods when each ObjectManager has its own Map++
    abstract protected AbstractElementHandle getNewElementHandle(Element obj, AbstractElementHandle _next, TypeInfo _typeInfo);

    abstract protected AbstractElementHandle getNewElementExtHandle(Element obj, AbstractElementHandle _next, TypeInfo _typeInfo);

    abstract protected AbstractEventHandle getNewEventHandle(Event obj, AbstractEventHandle _next, TypeInfo _typeInfo);

    abstract protected AbstractEventHandle getNewEventExtHandle(Event obj, AbstractEventHandle _next, TypeInfo _typeInfo);

    public Event getEvent(String extId) {
        AbstractEventHandle ehandle = m_eventHandleMap.get(extId);
        Event ev = getEventFromHandle(ehandle);
        if(ev == null) ev = getEventFromPreprocess(extId);
        return ev;
    }

    private Event getEventFromHandle(AbstractEventHandle ehandle) {
        if(ehandle != null && !ehandle.isRetracted_OR_isMarkedDelete()) {
            return (Event) ehandle.getObject();
        }
        return null;
    }
    
    public Event getEvent(long id) {
        AbstractEventHandle ehandle = m_eventHandleMap.get(id);
        Event ev = getEventFromHandle(ehandle);
        if(ev == null) ev = getEventFromPreprocess(id);
        return ev; 
    }

    public Event getEvent(long id, Class eventClz) {
        return getEvent(id);
    }
    
    public Element getElement(String extId) {
        AbstractElementHandle chandle = m_elementHandleMap.get(extId);
        Element elem = getElementFromHandle(chandle);
        if(elem == null) elem = getElementFromPreprocess(extId);
        return elem;
    }

    private Element getElementFromHandle(AbstractElementHandle chandle) {
        return getElementFromHandle(chandle, false);
    }
    private Element getElementFromHandle(AbstractElementHandle chandle, boolean ignoreRetractedOrMarkedDelete) {
        if(chandle != null && (!chandle.isRetracted_OR_isMarkedDelete() || ignoreRetractedOrMarkedDelete)) {
            return (Element) chandle.getObject();
        }
        return null;
    }
    
    public Element getElement(long id) {
        return getElement(id, false);
    }
    public Element getElement(long id, boolean ignoreRetractedOrMarkedDelete) {
        AbstractElementHandle chandle = m_elementHandleMap.get(id);
        Element elem = getElementFromHandle(chandle, ignoreRetractedOrMarkedDelete);
        if(elem == null) elem = getElementFromPreprocess(id);
        return elem;
    }

    public Element getElement(long id, Class cls) {
        return getElement(id, cls, false, false);
    }
    public Element getElement(long id, Class cls, boolean isClsAccurate, boolean ignoreRetractedOrMarkedDelete) {
        return getElement(id, ignoreRetractedOrMarkedDelete);
    }


    public BaseHandle getElementHandle(long id) {
        return m_elementHandleMap.get(id);
    }

    public BaseHandle getElementHandle(String extId,Class elementClass) {
        return m_elementHandleMap.get(extId);
    }

    public BaseHandle getEventHandle(long id) {
        return m_eventHandleMap.get(id);
    }

     public BaseHandle getEventHandle(String extId,Class eventClass) {
         return m_eventHandleMap.get(extId);
    }

    public BaseHandle getEntityHandle(long id) {
        return m_entityHandleMap.get(id);
    }

    public BaseHandle getEntityHandle(String extId) {
        return m_entityHandleMap.get(extId);
    }

    public Entity getEntity(String extId) {
        BaseHandle chandle = m_entityHandleMap.get(extId);
        if (chandle != null && !chandle.getBaseHandle().isRetracted_OR_isMarkedDelete()) {
            return (Entity) chandle.getBaseHandle().getObject();
        }
        return null;
    }

    public Object getObject(Object obj) {
        BaseHandle ohandle = m_objectHandleMap.get(obj);
        if( ohandle == null)
            return null;
        else {
            if (!ohandle.isRetracted_OR_isMarkedDelete())
                return (Element) ohandle.getObject();
            else
                return null;
        }        
    }

    public int numOfElement() {
        return m_elementHandleMap.size();
    }

    public int numOfEvent() {
        return m_eventHandleMap.size();
    }

    public boolean isLenient() {
        return false;
    }

    public List getObjects() {
        List retList = new LinkedList();
        synchronized(m_objectHandleMap) {
            synchronized(m_entityHandleMap) {
                synchronized(m_eventHandleMap) {
                    synchronized(m_elementHandleMap) {
                        Iterator ite = objectIterator();
                        while(ite.hasNext()) {
                            Object next = ite.next();
                            if(next != null) {
                                retList.add(next);
                            }
                        }
                    }
                }
            }
        }
        return retList;
    }

    public List getObjects(Filter filter) {
        if(filter == null) return getObjects();
        List retList = new LinkedList();
        synchronized(m_objectHandleMap) {
            synchronized(m_entityHandleMap) {
                synchronized(m_eventHandleMap) {
                    synchronized(m_elementHandleMap) {
                        Iterator ite = objectIterator();
                        while(ite.hasNext()) {
                            Object next = ite.next();
                            if(next != null) {
                                if(filter.evaluate(next))
                                    retList.add(next);
                            }
                        }
                    }
                }
            }
        }
        return retList;
    }
    

    public List getHandles() {
        List retList = new LinkedList();
        synchronized(m_objectHandleMap) {
            synchronized(m_entityHandleMap) {
                synchronized(m_eventHandleMap) {
                    synchronized(m_elementHandleMap) {
                        Iterator ite = handleIterator();
                        while(ite.hasNext()) {
                            retList.add(ite.next());
                        }
                    }
                }
            }
        }
        return retList;
    }

    public List getTimerFiredEventHandles() {
        List retList = new LinkedList();
        Iterator ite = getEventHandleIterator();
        while(ite.hasNext()) {
            AbstractEventHandle h = (AbstractEventHandle) ite.next();
            if(h.isTimerFired())
                retList.add(h);
        }
        return retList;
    }

    public BaseHandle getAddHandle(Object object) throws DuplicateExtIdException {
        BaseHandle h;

        if(object instanceof Event) {
            createEvent((Event) object);
            h = m_eventHandleMap.getAdd((Event) object);
        }
        else if(object instanceof Element) {
            createElement((Element) object);
            h = m_elementHandleMap.getAdd((Element) object);
        }
        else if(object instanceof Entity) {
            h = m_entityHandleMap.getAdd((Entity) object);
        }
        else {  //obj
            h = m_objectHandleMap.getAdd(object);
        }
//        if(h.typeInfo == null)
//            h.typeInfo = m_workingMemory.getTypeInfo(object.getClass());

        return h;
    }

    public Element getNamedInstance(String uri, Class entityClz) {
        return null;
    }

//    public BaseHandle loadEvictedElementHandle(long id, String extId, Class type) throws DuplicateExtIdException, DuplicateException {
//        throw new RuntimeException("Not supporting loadEvictedElementHandle for this OM " + this);
//    }

//    public boolean isDeleted(Object object) {
//        return false;
//    }
//    public BaseHandle loadEvictedEventHandle(long id, String extId, Class type) throws DuplicateExtIdException, DuplicateException {
//        throw new RuntimeException("Not supporting loadEvictedEventHandle for this OM " + this);
//    }

//    protected void setHandleTypeInfo(BaseHandle h, Class clz) {
//        if(h.typeInfo == null)
//            h.typeInfo = m_workingMemory.getTypeInfo(clz);
//    }

    public BaseHandle getHandle(Object object) {
        if(object instanceof Event) {
            return m_eventHandleMap.get(((Event) object).getId());
        }
        else if(object instanceof Element) {
            return m_elementHandleMap.get(((Element) object).getId());
        }
        else if(object instanceof Entity) {
            return m_entityHandleMap.get(((Entity) object).getId());
        }
        else {  //obj
            return m_objectHandleMap.get(object);
        }
    }    


    // protected methods -  called by the WorkingMemory.  should be called in a synchronized workingMemory!
    protected Iterator getNamedInstanceIterator() {  //should be used only in hot deploy
        return m_elementHandleMap.namedInstanceIterator();
    }

    protected Iterator getEventHandleIterator() {   //should be used only in hot deploy
        return m_eventHandleMap.handleIterator();
    }

    protected Iterator getElementHandleIterator() {  //should be used only in hot deploy
        return m_elementHandleMap.handleIterator();
    }

    protected Iterator getEntityHandleIterator() {   //should be used only in hot deploy
        return m_entityHandleMap.handleIterator();
    }

    protected Iterator getObjectHandleIterator() {  //should be used only in hot deploy
        return m_objectHandleMap.handleIterator();
    }

    protected BaseHandle addHandle(Object object) throws DuplicateExtIdException, DuplicateException {
        BaseHandle h;
        if(object instanceof Event) {
            h = m_eventHandleMap.add((Event) object);
        }
        else if(object instanceof Element) {
            h = m_elementHandleMap.add((Element) object);
        }
        else if(object instanceof Entity) {
            h = m_entityHandleMap.add((Entity) object);
        }
        else {  //obj
            h = m_objectHandleMap.add(object);
        }
//        h.typeInfo = m_workingMemory.getTypeInfo(object.getClass());
        return h;
    }

    protected AbstractElementHandle getElementHandle(Element element) {
        return m_elementHandleMap.get(element.getId());
    }

    protected AbstractEventHandle getEventHandle(Event event) {
        return m_eventHandleMap.get(event.getId());
    }

    protected EntityHandleMap.EntityHandle getEntityHandle(Entity entity) {
        return m_entityHandleMap.get(entity.getId());
    }

    protected ObjectHandleMap.ObjectHandle getObjectHandle(Object object) {
        return m_objectHandleMap.get(object);
    }


    public AbstractElementHandle getAddElementHandle(Element element) throws DuplicateExtIdException {
        createElement(element);
        AbstractElementHandle h = m_elementHandleMap.getAdd(element);
//        if(h.typeInfo == null)
//            h.typeInfo = m_workingMemory.getTypeInfo(element.getClass());
        return h;
    }

    public AbstractEventHandle getAddEventHandle(Event event) throws DuplicateExtIdException {
        createEvent(event);
        AbstractEventHandle h = m_eventHandleMap.getAdd(event);
//        if(h.typeInfo == null)
//            h.typeInfo = m_workingMemory.getTypeInfo(event.getClass());
        return h;
    }

    protected EntityHandleMap.EntityHandle getAddEntityHandle(Entity entity) throws DuplicateExtIdException {
        EntityHandleMap.EntityHandle h = m_entityHandleMap.getAdd(entity);
//        if(h.typeInfo == null)
//            h.typeInfo = m_workingMemory.getTypeInfo(entity.getClass());
        return h;
    }

    protected ObjectHandleMap.ObjectHandle getAddObjectHandle(Object object) {
        ObjectHandleMap.ObjectHandle h = m_objectHandleMap.getAdd(object);
//        if(h.typeInfo == null)
//            h.typeInfo = m_workingMemory.getTypeInfo(object.getClass());
        return h;
    }

//    protected BaseHandle removeHandle(Object object) {
//        if(object instanceof Event) {
//            return m_eventHandleMap.remove((Event) object);
//        }
//        else if(object instanceof Element) {
//            return m_elementHandleMap.remove((Element) object);
//        }
//        else if(object instanceof Entity) {
//            return m_entityHandleMap.remove((Entity) object);
//        }
//        else {  //obj
//            return m_objectHandleMap.remove(object);
//        }
//    }

//    protected BaseHandle cleanupHandle(Object object) {
//        if(object instanceof Event) {
//            return m_eventHandleMap.cleanup(((Event) object).getId());
//        }
//        else if(object instanceof Element) {
//            return m_elementHandleMap.cleanup(((Element) object).getId());
//        }
//        else if(object instanceof Entity) {
//            return m_entityHandleMap.cleanup(((Entity) object).getId());
//        }
//        else {  //obj
//            return m_objectHandleMap.remove(object);
//        }
//    }

    protected void removeElementHandle(AbstractElementHandle handle) {
        m_elementHandleMap.remove(handle.getElementId());
    }

    protected void removeEventHandle(AbstractEventHandle handle) {
        m_eventHandleMap.remove(handle.getEventId());
    }

    protected EntityHandleMap.EntityHandle removeEntityHandle(long id) {
        return m_entityHandleMap.remove(id);
    }

    protected ObjectHandleMap.ObjectHandle removeObjectHandle(Object object) {
        return m_objectHandleMap.remove(object);
    }

    //protected methods for sub class object manager
    protected Iterator objectIterator() {
        return new ObjectIterator();
    }

    protected Iterator handleIterator() {
        return new HandleIterator();
    }

//    protected void setHandleTypeInfo(BaseHandle h, Entity entity) {
//        if(h.typeInfo == null)
//            h.typeInfo = m_workingMemory.getTypeInfo(entity.getClass());
//    }

    class ObjectIterator implements Iterator {
        Iterator iterator;
        int      currentMap;
        // m_eventHandleMap   = 1;
        // m_elementHandleMap = 2;
        // m_entityHandleMap  = 3;
        // m_objectHandleMap  = 4;

        ObjectIterator() {
            currentMap = 0;
            getNextIterator();
        }

        private Iterator getNextIterator() {
            currentMap++;
            if(currentMap == 1)
                iterator = m_eventHandleMap.iterator();
            else if (currentMap == 2)
                iterator = m_elementHandleMap.iterator();
            else if (currentMap == 3)
                iterator = m_entityHandleMap.iterator();
            else if (currentMap == 4)
                iterator = m_objectHandleMap.iterator();
            else
                iterator = null;

            if(iterator== null)
                return null;
            else if(iterator.hasNext())
                return iterator;
            else
                return getNextIterator();
        }

        public boolean hasNext() {
            if(iterator == null) return false;
            if(iterator.hasNext()) return true;
            getNextIterator();
            return hasNext();
        }

        public Object next() {
            return iterator.next();
        }

        public void remove() {
            throw new RuntimeException("ObjectIterator.remove() is not implemented, should use WorkingMemory.retractObject() to remove an object");
        }
    }

    class HandleIterator implements Iterator {
        Iterator iterator;
        int      currentMap;
        // m_eventHandleMap   = 1;
        // m_elementHandleMap = 2;
        // m_entityHandleMap  = 3;
        // m_objectHandleMap  = 4;

        HandleIterator() {
            currentMap = 0;
            getNextIterator();
        }

        private Iterator getNextIterator() {
            currentMap++;
            if(currentMap == 1)
                iterator = m_eventHandleMap.handleIterator();
            else if (currentMap == 2)
                iterator = m_elementHandleMap.handleIterator();
            else if (currentMap == 3)
                iterator = m_entityHandleMap.handleIterator();
            else if (currentMap == 4)
                iterator = m_objectHandleMap.handleIterator();
            else
                iterator = null;

            if(iterator== null)
                return null;
            else if(iterator.hasNext())
                return iterator;
            else
                return getNextIterator();
        }

        public boolean hasNext() {
            if(iterator == null) return false;
            if(iterator.hasNext()) return true;
            getNextIterator();
            return hasNext();
        }

        public Object next() {
            return iterator.next();
        }

        public void remove() {
            throw new RuntimeException("ObjectIterator.remove() is not implemented, should use WorkingMemory.retractObject() to remove an object");
        }
    }

    public EntitySharingLevel getLocalSharingLevel(Class entityCls) {
        return EntitySharingLevel.DEFAULT;
    }
    
    public Element getElementByUri(String extId, String uri) {
    	return getElement(extId);
    }
    public Event getEventByUri(String extId, String uri) {
    	return getEvent(extId);
    }
    public Element getElementByUri(long id, String uri) {
        return getElement(id);
    }
    public Event getEventByUri(long id, String uri) {
        return getEvent(id);
    }

    protected Element getElementFromPreprocess(String extId) {
        return null;
    }
    protected Event getEventFromPreprocess(String extId) {
        return null;
    }
    protected Element getElementFromPreprocess(long id) {
        return null;
    }
    protected Event getEventFromPreprocess(long id) {
        return null;
    }
    
    public boolean isConcurrent() {
    	return m_workingMemory.isConcurrent();
    }
}
