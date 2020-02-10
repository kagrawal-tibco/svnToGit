package com.tibco.cep.runtime.service.om.impl.mem;

import com.tibco.cep.kernel.core.base.*;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.entity.NamedInstance;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateException;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.service.Filter;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*
* Author: Suresh Subramani / Date: 8/29/12 / Time: 5:16 AM
*/
public class ConcurrentInMemoryObjectManager extends InMemoryObjectManager  {

    ConcurrentHashMap<Long, BaseHandleWrapper> p_eventHandleMap;
    ConcurrentHashMap<Long, BaseHandleWrapper> p_elementHandleMap;
    ConcurrentHashMap<String, BaseHandle> p_extIdHandleMap;

    final static Logger logger = LogManagerFactory.getLogManager().getLogger(ConcurrentInMemoryObjectManager.class);


    ConcurrentHashMap<Long, BaseHandleWrapper> get_p_eventHandleMap() {
        return p_eventHandleMap;
    }

    ConcurrentHashMap<Long, BaseHandleWrapper> get_p_elementHandleMap() {
        return p_elementHandleMap;
    }

    ConcurrentHashMap<String, BaseHandle> get_p_extIdHandleMap() {
        return p_extIdHandleMap;
    }



    public ConcurrentInMemoryObjectManager(String name) {
        super(name);

    }


    public synchronized void init(WorkingMemory workingMemory) throws Exception {
        super.init(workingMemory);
        p_eventHandleMap =  new ConcurrentHashMap<Long, BaseHandleWrapper>();
        p_elementHandleMap = new ConcurrentHashMap<Long, BaseHandleWrapper>();
        p_extIdHandleMap = new ConcurrentHashMap<String, BaseHandle>();
    }





    public void reset() {
        super.reset();
        get_p_elementHandleMap().clear();
        get_p_eventHandleMap().clear();
        get_p_extIdHandleMap().clear();
    }

    /**
     * Overriden handle methods from BaseObjectManager *
     */

    public BaseHandle getElementHandle(long id) {
        BaseHandleWrapper wrap = get_p_elementHandleMap().get(id);
        return wrap == null ? null : wrap.getBaseHandle();
    }



    public BaseHandle getElementHandle(String extId,Class elementClass) {
        if (extId != null && extId.length() > 0) {
            BaseHandle ret = get_p_extIdHandleMap().get(extId);
            if(ret instanceof AbstractElementHandle) return ret;
        }
        return null;
    }

    public BaseHandle getEventHandle(long id) {
        BaseHandleWrapper wrap = get_p_eventHandleMap().get(id);
        return wrap == null ? null : wrap.getBaseHandle();
    }

    public BaseHandle getEventHandle(String extId,Class eventClass) {
        if (extId != null && extId.length() > 0) {
            BaseHandle ret = get_p_extIdHandleMap().get(extId);
            if(ret instanceof AbstractEventHandle) return ret;
        }
        return null;
    }

    public int numOfElement() {
        return get_p_elementHandleMap().size();
    }

    public int numOfEvent() {
        return get_p_eventHandleMap().size();
    }



    public List getObjects() {
        List retList = new LinkedList();
        Enumeration<BaseHandleWrapper> ite = get_p_elementHandleMap().elements();
        while (ite.hasMoreElements()) {
            BaseHandleWrapper wrapper = ite.nextElement();
            Object o = wrapper.getBaseHandle().getObject();
            if (o != null)
                retList.add(o);
        }
        ite = get_p_eventHandleMap().elements();
        while (ite.hasMoreElements()) {
            BaseHandleWrapper wrapper = ite.nextElement();
            Object o = wrapper.getBaseHandle().getObject();
            if (o != null)
                retList.add(o);
        }
        return retList;
    }


    public List getObjects(Filter filter) {
        if (filter == null) return getObjects();
        List retList = new LinkedList();
        Enumeration<BaseHandleWrapper> ite = get_p_elementHandleMap().elements();
        while (ite.hasMoreElements()) {
            BaseHandleWrapper wrapper = ite.nextElement();
            Object o = wrapper.getBaseHandle().getObject();
            if ((o != null) && (filter.evaluate(o)))
                retList.add(o);
        }
        ite = get_p_eventHandleMap().elements();
        while (ite.hasMoreElements()) {
            BaseHandleWrapper wrapper = ite.nextElement();
            Object o = wrapper.getBaseHandle().getObject();
            if ((o != null) && (filter.evaluate(o)))
                retList.add(o);
        }
        return retList;
    }



    public List getHandles() {
        List retList = new LinkedList();
        retList.addAll(get_p_eventHandleMap().values());
        retList.addAll(get_p_elementHandleMap().values());
        return retList;
    }

    public List getTimerFiredEventHandles() {
        List retList = new LinkedList();
        Iterator ite = getEventHandleIterator();
        while (ite.hasNext()) {
            AbstractEventHandle h = (AbstractEventHandle) ((BaseHandleWrapper)ite.next()).getBaseHandle();
            if (h.isTimerFired())
                retList.add(h);
        }
        return retList;
    }

    private void _removeExtIdEntry(String extId, BaseHandle h) {
        if ((extId != null) && (extId.length() > 0)) {
            if(h == null) {
                get_p_extIdHandleMap().remove(extId);
            } else {
                get_p_extIdHandleMap().remove(extId, h);
            }
        }
    }

    protected void _addExtIdEntry(String extId, BaseHandle h) throws DuplicateExtIdException {
        BaseHandle h1 = get_p_extIdHandleMap().putIfAbsent(extId, h);
        //h1 != null means the old entry in the map was not replaced
        if (h1 != null) {
            if (h1 != h && !h1.isRetracted_OR_isMarkedDelete()) {
                throw new DuplicateExtIdException("Attempt to assert duplicate extIds " + h.printInfo() + ", existing entry " + h1.printInfo());
            }
            if(logger.isEnabledFor(Level.DEBUG)) {
                logger.log(Level.DEBUG, "_addExtIdEntry: replacing existing handle retracted or marked delete %s new handle %s", h1.printInfo(), h.printInfo());
            }
            //if the previous handle is deleted then replace it
            get_p_extIdHandleMap().put(extId, h);
        }
    }

    private BaseHandle getExtAddEventHandle(Event event, boolean forceSet) throws DuplicateExtIdException {
        return new BaseHandleInitWrapper().initExtEventHandle(event, forceSet, this);
    }

    private BaseHandle getSimpleAddEventHandle(Event event, boolean forceSet) {
        return new BaseHandleInitWrapper().initSimpleEventHandle(event, forceSet, this);
    }

    private BaseHandle getExtAddElementHandle(final Element element, final boolean forceSet) throws DuplicateExtIdException {
        return new BaseHandleInitWrapper().initExtElementHandle(element, forceSet, this);
    }

    private BaseHandle getSimpleAddElementHandle(final Element element, final boolean forceSet) throws DuplicateExtIdException {
        return new BaseHandleInitWrapper().initSimpleElementHandle(element, forceSet, this);
    }

    public BaseHandle getAddHandle(Object object) throws DuplicateExtIdException {
        return getAddHandle(object, false);
    }

    private BaseHandle getAddHandle(Object object, boolean forceSet) throws DuplicateExtIdException {
        if(object instanceof Event) {
            return getAddEventHandle((Event)object, forceSet);
        } else if(object instanceof Element) {
            return getAddElementHandle((Element)object, forceSet);
        } else {
            return super.getAddHandle(object);
        }
    }

    public boolean handleExists(Object object) {
        if (object instanceof Concept)
            return get_p_elementHandleMap().containsKey(((Concept) object).getId());
        else if (object instanceof Event) {
            return get_p_eventHandleMap().containsKey(((Event) object).getId());
        } else
            throw new RuntimeException("Unknown Type " + object);
    }

    public BaseHandle createHandle(Object object) throws DuplicateExtIdException {
        return getAddHandle(object, true);
    }

    public BaseHandle getHandle(Object object) {

        if (object instanceof Entity) {
            Entity en = (Entity) object;
            if (en.isLoadedFromCache()) {
                try {
                    return getAddHandle(object);
                } catch (DuplicateExtIdException dex) {
                    logger.log(Level.WARN, dex, "DuplicateExtId in getHandle %s", en);
                    // Should never happen for objects loaded from the cache
                    return null;
                }
            }
        }

        if (object instanceof Event) {
            BaseHandleWrapper wrap = get_p_eventHandleMap().get(((Event) object).getId());
            return wrap == null ? null : wrap.getBaseHandle();
        } else if (object instanceof Element) {
            BaseHandleWrapper wrap = get_p_elementHandleMap().get(((Element) object).getId());
            return wrap == null ? null : wrap.getBaseHandle();
        } else {
            return super.getHandle(object);
        }
    }


    // protected methods -  called by the WorkingMemory.  should be called in a synchronized workingMemory!
    protected Iterator getNamedInstanceIterator() {  //should be used only in hot deploy
        return new _NamedInstanceIterator();
    }

    protected Iterator getEventHandleIterator() {   //should be used only in hot deploy
        return get_p_eventHandleMap().values().iterator();
    }

    protected Iterator getElementHandleIterator() {  //should be used only in hot deploy
        return get_p_elementHandleMap().values().iterator();
    }

    protected BaseHandle addHandle(Object object) throws DuplicateExtIdException, DuplicateException {
        return getAddHandle(object);
    }

    protected AbstractElementHandle getElementHandle(Element element) {

        BaseHandleWrapper wrap = get_p_elementHandleMap().get(element.getId());
        return (AbstractElementHandle) (wrap == null ? null : wrap.getBaseHandle());
    }

    protected AbstractEventHandle getEventHandle(Event event) {
        BaseHandleWrapper wrap = get_p_eventHandleMap().get(event.getId());
        return (AbstractEventHandle) (wrap == null ? null : wrap.getBaseHandle());
    }


    public AbstractElementHandle getAddElementHandle(Element element) throws DuplicateExtIdException {
        return getAddElementHandle(element, false);
    }

    public AbstractEventHandle getAddEventHandle(Event event) throws DuplicateExtIdException {
        return getAddEventHandle(event, false);
    }

    public AbstractElementHandle getAddElementHandle(Element element, boolean forceSet) throws DuplicateExtIdException {
        if ((element.getExtId() != null) && (element.getExtId().length() > 0)){
            return (AbstractElementHandle) getExtAddElementHandle(element, forceSet);
        } else {
            return (AbstractElementHandle) getSimpleAddElementHandle(element, forceSet);
        }
    }

    public AbstractEventHandle getAddEventHandle(Event event, boolean forceSet) throws DuplicateExtIdException {
        if ((event.getExtId() != null) && (event.getExtId().length() > 0)) {
            return (AbstractEventHandle) getExtAddEventHandle(event, forceSet);
        } else {
            return (AbstractEventHandle) getSimpleAddEventHandle(event, forceSet);
        }
    }




    protected void removeElementHandle(AbstractElementHandle handle) {
        if (handle instanceof ExtIdHandle) {
            _removeExtIdEntry(((ExtIdHandle)handle).getExtId(), handle);
        }
        BaseHandleWrapper ret = get_p_elementHandleMap().remove(handle.getElementId());
        //previous implementation took only the id as the argument so it would have
        //marked what was in the map instead of the handle that was passed in.
        if(ret != null) {
            BaseHandle h = ret.getBaseHandle();
            h.setRetracted();
            deleteEntity(h);
        }
    }

    protected void removeEventHandle(AbstractEventHandle handle) {
        if (handle instanceof ExtIdHandle) {
            _removeExtIdEntry(((ExtIdHandle)handle).getExtId(), handle);
        }
        BaseHandleWrapper ret = get_p_eventHandleMap().remove(handle.getEventId());
        //previous implementation took only the id as the argument so it would have
        //marked what was in the map instead of the handle that was passed in.
        if(ret != null) {
            BaseHandle h = ret.getBaseHandle();
            h.setRetracted();
            deleteEntity(h);
        }
    }




    public Event getEvent(String extId)  {
        return getEvent(extId, null);
    }

    public Event getEvent(long id) {
        return getEvent(id, null);
    }

    public Event getEventByUri(String extId, String uri) {
        return this.getEvent(extId);
    }

    public Event getEventByUri(long id, String uri) {
        return this.getEvent(id);
    }


    private Event getEvent(String extId, Class eventClass)  {

        BaseHandle chandle = getEventHandle(extId,null);
        if ((chandle == null) || chandle.getObject() == null) {
            return this.getEventFromPreprocess(extId);
        }
        return (Event)chandle.getObject();

    }

    @Override
    public Event getEvent(long id, Class eventClz) {
        BaseHandle chandle = getEventHandle(id);
        if ((chandle == null) || chandle.getObject() == null) {
            return this.getEventFromPreprocess(id);
        }
        return (Event)chandle.getObject();
    }

    public Element getElement(String extId) {
        return getElement(extId, null);
    }

    public Element getElement(long id) {
        return getElement(id, false);
    }

    public Element getElement(long id, boolean ignoreRetractedOrMarkedDelete) {
        return getElement(id, null, false, ignoreRetractedOrMarkedDelete);
    }


    @Override
    public Element getElement(long id, Class cls) {
        return getElement(id, cls, false, false);
    }



    public Element getElement(long id, Class elementClz, boolean isClzAccurate, boolean ignoreRetractedOrMarkedDelete) {
        BaseHandle h = getElementHandle(id);
        if(h == null || h.getObject() == null){  //handle not found
            return this.getElementFromPreprocess(id);
        }
        return (!ignoreRetractedOrMarkedDelete && h.isRetracted_OR_isMarkedDelete()) ? null : (Element)h.getObject();
    }


    public Element getElementByUri(String extId, String uri) {
        return this.getElement(extId);
    }


    public Element getElementByUri(long id, String uri) {
        return this.getElement(id);
    }

    /**
     * @param entityClass Can be <code>null</code>.
     * @param extId
     * @return
     */
    private Element getElement(String extId, Class entityClass) {


        BaseHandle chandle = getElementHandle(extId,null);
        if ((chandle == null) || chandle.getObject() == null) {
            return this.getElementFromPreprocess(extId);
        }
        //BE-20570: return element only if handle is not retracted or marked for delete
        return chandle.isRetracted_OR_isMarkedDelete() ? null : (Element)chandle.getObject();
    }

    @Override
    public boolean isConcurrent() {
        return true;
    }


//    public boolean isDeleted(Object object) {
//        return false;
//    }

    public boolean isLenient() {
        return false;
    }


    protected static class BaseHandleInitWrapper implements BaseHandleWrapper
    {
        protected BaseHandle handle = null;

        //Store only explicitly calls init...Handle()
        //Callers to getBaseHandle() will only call it after the initial call to init...Handle()
        public synchronized BaseHandle getBaseHandle() {
            return handle;
        }

        public synchronized BaseHandle initExtElementHandle(Element element, boolean forceSet, ConcurrentInMemoryObjectManager cstore) throws DuplicateExtIdException {
            Long id = element.getId();
            BaseHandleWrapper wrapper = cstore.get_p_elementHandleMap().putIfAbsent(id, this);

            if(wrapper == null) {
                TypeInfo typeInfo = cstore.m_workingMemory.getTypeInfo(element.getClass());
                BaseHandle h = cstore.getNewElementExtHandle(element, null, typeInfo);
                try {
                    cstore._addExtIdEntry(element.getExtId(), h);
                } catch (DuplicateExtIdException dupEx) {
                    cstore.get_p_elementHandleMap().remove(id);
                    throw dupEx;
                }

                //replace the wrapper with the handle
                cstore.get_p_elementHandleMap().put(id, h);
                handle = h;
            } else {
                handle = wrapper.getBaseHandle();
            }
            return handle;
        }

        public synchronized BaseHandle initSimpleElementHandle(Element element, boolean forceSet, ConcurrentInMemoryObjectManager cstore) {
            Long id = element.getId();
            BaseHandleWrapper wrapper = cstore.get_p_elementHandleMap().putIfAbsent(id, this);

            if(wrapper == null) {
                TypeInfo typeInfo = cstore.m_workingMemory.getTypeInfo(element.getClass());
                BaseHandle h = cstore.getNewElementHandle(element, null, typeInfo);
                //replace the wrapper with the handle
                cstore.get_p_elementHandleMap().put(id, h);
                handle = h;
            } else {
                handle = wrapper.getBaseHandle();
            }
            return handle;
        }

        public synchronized BaseHandle initExtEventHandle(Event event, boolean forceSet, ConcurrentInMemoryObjectManager cstore) throws DuplicateExtIdException {
            Long id = event.getId();
            BaseHandleWrapper wrapper = cstore.get_p_eventHandleMap().putIfAbsent(id, this);

            if(wrapper == null) {
                TypeInfo typeInfo = cstore.m_workingMemory.getTypeInfo(event.getClass());
                BaseHandle h= cstore.getNewEventExtHandle(event, null, typeInfo);
                try {
                    cstore._addExtIdEntry(event.getExtId(), h);
                } catch (DuplicateExtIdException dupEx) {
                    cstore.get_p_eventHandleMap().remove(id);
                    throw dupEx;
                }

                //replace the wrapper with the handle
                cstore.get_p_eventHandleMap().put(id, h);
                handle = h;
            } else {
                handle = wrapper.getBaseHandle();
            }
            return handle;
        }

        public synchronized BaseHandle initSimpleEventHandle(Event event, boolean forceSet,ConcurrentInMemoryObjectManager cstore) {
            Long id = event.getId();
            BaseHandleWrapper wrapper = cstore.get_p_eventHandleMap().putIfAbsent(id, this);

            if(wrapper == null) {
                TypeInfo typeInfo = cstore.m_workingMemory.getTypeInfo(event.getClass());
                BaseHandle h= cstore.getNewEventHandle(event, null, typeInfo);
                //replace the wrapper with the handle
                cstore.get_p_eventHandleMap().put(id, h);
                handle = h;
            } else {
                handle = wrapper.getBaseHandle();
            }
            return handle;
        }
    }

    class _NamedInstanceIterator implements Iterator {
        Iterator ret = null;

        _NamedInstanceIterator() {
            List ni = new ArrayList();
            Iterator all_elements = get_p_elementHandleMap().values().iterator();
            while (all_elements.hasNext()) {
                Object obj = all_elements.next();
                if (obj instanceof NamedInstance) {
                    ni.add(obj);
                }
            }
            ret = ni.iterator();
        }


        public boolean hasNext() {
            return ret.hasNext();
        }

        public Object next() {
            return ret.next();
        }

        public void remove() {
            throw new RuntimeException("NamedInstanceIterator.remove() is not implemented, should use ElementHandleMap.remove() to remove an instance");
        }
    }


}
