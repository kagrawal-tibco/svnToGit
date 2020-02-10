package com.tibco.cep.runtime.session.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.event.SimpleEvent;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Oct 15, 2006
 * Time: 5:33:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class PreprocessContext {
    private static final ThreadLocal current = new ThreadLocal();

    static public PreprocessContext getContext() {
        return (PreprocessContext) current.get();
    }

    static public PreprocessContext newContext(RuleSessionImpl session) {
        PreprocessContext context = (PreprocessContext) current.get();
        if (context != null)
            throw new RuntimeException("Context already exist");
        context = new PreprocessContext(session);
        current.set(context);
        return context;
    }

    static public void setContext(PreprocessContext preContext) {
        current.set(preContext);
    }

    static public void cleanContext() {
        PreprocessContext context = (PreprocessContext) current.get();
        if(context == null) return;
        context.created = null;
        context.entityKeys=null;
        context.deleted = null;
        context.reloaded= null;
        context.session = null;
        context.reevaluate= null;
        context.clusterSubscriptionReloadOnly = false;
        context.endWork();
        current.set(null);
    }

    static public void clear() {
        PreprocessContext context = (PreprocessContext) current.get();
        if(context == null) return;
        context.created = null;
        context.entityKeys=null;
        context.deleted = null;
        context.reloaded= null;
        context.session = null;
        context.reevaluate= null;
        context.clusterSubscriptionReloadOnly = false;
        context.endWork();
    }
    


    private LinkedHashSet created;
    private HashMap entityKeys;
    private LinkedHashSet deleted; //retract - remove from handleMap, cleantimer, call delete, retract from rete, remove from OM
    private LinkedHashSet reloaded;
    private LinkedHashSet reevaluate;
    private boolean clusterSubscriptionReloadOnly;


    private RuleSessionImpl session;
    private long startTime;
    private long endTime;
    private Object trigger;
    //if trigger is deleted in the preprocessor, trigger is set to null
    //rtcTrigger should not be set to null since it is checked by RuleTriggerManager
    private Object rtcTrigger;

    PreprocessContext(RuleSessionImpl session_) {
        startTime=System.currentTimeMillis();
        created = new LinkedHashSet();
        entityKeys=new HashMap();
        deleted = new LinkedHashSet();
        reloaded=new LinkedHashSet();
        reevaluate= new LinkedHashSet();

        session = session_;
        this.clusterSubscriptionReloadOnly = false;
    }


    public void setTrigger(Object trigger) {
        this.trigger=trigger;
        rtcTrigger = trigger;
    }
    
    public Object getRtcTrigger() {
    	return rtcTrigger;
    }

    public void clean() {
        created = null;
        entityKeys=null;
        deleted = null;
        reloaded= null;
        session = null;
        trigger=null;
        rtcTrigger = null;
        reevaluate=null;
        clusterSubscriptionReloadOnly = false;

    }

    public void endWork() {
        endTime = System.currentTimeMillis();
    }

    public long getPreRTCTime() {
        if (endTime > 0) {
            return endTime - startTime;
        } else {
            return 0L;
        }
    }

    public boolean hasWork() {
        return ((trigger != null) || (created.size() > 0) || (deleted.size() > 0) || (reevaluate.size() > 0) || ((reloaded != null) && (reloaded.size() > 0)) || clusterSubscriptionReloadOnly );
    }



    private static final List emptyList = new LinkedList();

    public List getAsserted() {
        int size = created.size() + (trigger == null ? 0 : 1);
        if (size <= 0) return emptyList;

        ArrayList ret = new ArrayList(size);
        if (trigger != null) {
            ret.add(trigger);
        }

        if (created.size() > 0)
            ret.addAll(created);

        return ret;
    }


    public LinkedHashSet getDeleted() {
        return deleted;
    }

    public List getReloaded() {
        if (reloaded.size() == 0)
            return emptyList;

        return new ArrayList(reloaded);
    }

    public List getReevaluate() {
        if (reevaluate.size() == 0)
            return emptyList;

        return new ArrayList(reevaluate);
    }

    public boolean getClusterSubscriptionReloadOnly() {
        return clusterSubscriptionReloadOnly;
    }

    public void setClusterSubscriptionReloadOnly() {
        clusterSubscriptionReloadOnly = true;
    }

    private static boolean hasExtId(Entity entity) {
        return entity.getExtId() != null && entity.getExtId().length() > 0;
    }
    
    private Object checkDupExtId(Entity entity) {
        Object obj=entityKeys.get(entity.getExtId());
        if(obj == null && trigger instanceof Entity) {
            String ext = entity.getExtId();
            if(ext.equals(((Entity) trigger).getExtId())) return trigger;
        }
        return obj;
    }

    private void _add(Entity entity ) throws DuplicateExtIdException{
        if (hasExtId(entity)) {
            Object obj=checkDupExtId(entity);
            if (obj == null) {
                created.add(entity);
                entityKeys.put(entity.getExtId(), entity);
                entityKeys.put(entity.getId(), entity);
            } else {
                throw new DuplicateExtIdException("Attempt to assert duplicate extIds " + entity + ", existing entry " + obj);
            }
        } else {
            created.add(entity);
            entityKeys.put(entity.getId(), entity);
        }
    }

    private void _remove(Entity entity) {
        _remove(entity, true);
    }
    private void _remove(Entity entity, boolean addToDeleted) {
        if (hasExtId(entity)) {
            //can create an event with the same extId as an asserted object
            //then remove the event without ever asserting it
            //thereby removing the other object from createdWithKey unless
            //this check is performed
            Object obj = entityKeys.remove(entity.getExtId());
            if(obj != null && obj != entity) {
                entityKeys.put(entity.getExtId(), obj);
                addToDeleted = false;
            } else {
                created.remove(entity);
                entityKeys.remove(entity.getId());
                addToDeleted &= true;
            }
        } else {
            addToDeleted &= !created.remove(entity);
            entityKeys.remove(entity.getId());
        }
        
        //won't come here if the entity was loaded from cache.
        //other options are memory-only entities in local WM (which should be added to deleted)
        //and events that were never asserted (should not be added)
        //trigger can either be a new event (don't add to deleted)
        //or can be an event in WM that was sent via local channel.
        //its status can change during the course of the PP by its ttl expiring 
        //and the ttl deletion happening on another thread
        //the latter case is not handled properly here
        //because consumeEvent(trigger) is done for performance
        //so starting an RTC to check if the trigger is in the WM would be a perf regression
        if(addToDeleted && entity != trigger) {
		    deleted.add(entity);
        }
    }

    public void add(Object obj) throws DuplicateExtIdException {
        List<Concept> children = null;
        
        if (obj instanceof Concept) {
            Concept cept= (Concept) obj;
            if (!cept.isLoadedFromCache()) {
                children = cept.getChildren();
            }
        }

        if(obj instanceof Entity) {
            Entity en= (Entity) obj;
            _add(en);
            if (children != null) {
                try {
                    for(Concept child : children) {
                        _add(child);
                    }
                } catch(DuplicateExtIdException ex) {
                    for(Concept child : children) {
                        _remove(child, false);
                    }
                    _remove(en, false);
                    throw ex;
                }
            }
        } else if(obj != null){
            created.add(obj);
        }
    }


    public void delete(Object obj) {
        if (obj instanceof Concept) {
            Concept cept= (Concept) obj;
            List children = cept.getChildren();
            if (children != null) {
                Iterator ite = children.iterator();
                while (ite.hasNext()) {
                    Concept child = (Concept) ite.next();
                    if (child.isLoadedFromCache()) {
                        deleted.add(child);
                        reloaded.remove(child);
                    } else {
                        _remove(child);
                    }
                }
            }
            if (cept.isLoadedFromCache()) {
                deleted.add(cept);
                reloaded.remove(cept);
            } else {
                _remove(cept);
            }
        } else {
            Entity entity= (Entity) obj;
            if (entity.isLoadedFromCache()) {
                deleted.add(entity);
                reloaded.remove(entity);
            } else {
                if (entity instanceof SimpleEvent) {
                    SimpleEvent se= (SimpleEvent) obj;
                    if (se.getContext() != null) {
                        se.acknowledge();
                    }
                }
                _remove(entity);
            }
        }

        if (obj == trigger) {
            trigger=null;
        }

    }

    void forceRefresh(long id, String extId, Object oldObj, Object newObj) {
    	if(newObj == null) {
    		Object oldId = entityKeys.get(id);
    		entityKeys.remove(id);
    		
    		Object oldExtId = entityKeys.get(extId);
    		if(oldExtId == oldObj || (oldExtId instanceof Entity && ((Entity)oldExtId).getId() == id)) {
    			entityKeys.remove(extId);
    		} else {
    			oldExtId = null;
    		}
    		deleted.remove(oldObj);
    		if(oldId != null) deleted.remove(oldId);
    		if(oldExtId != null) deleted.remove(oldExtId);
    		
    		reloaded.remove(oldObj);
    		if(oldId != null) reloaded.remove(oldId);
    		if(oldExtId != null) reloaded.remove(oldExtId);
    		
    		reevaluate.remove(oldObj);
    		if(oldId != null) reevaluate.remove(oldId);
    		if(oldExtId != null) reevaluate.remove(oldExtId);
    		
    		if(trigger == oldObj || (trigger instanceof Entity && ((Entity)trigger).getId() == id)) {
    			trigger = null;
    		}
    	} else {
    		Object oldId = entityKeys.get(id);
    		if(oldId != null) entityKeys.put(id, newObj);
    		
    		Object oldExtId = entityKeys.get(extId);
    		if(oldExtId == oldObj || (oldExtId instanceof Entity && ((Entity)oldExtId).getId() == id)) {
    			entityKeys.put(extId, newObj);
    		} else {
    			oldExtId = null;
    		}
    		
    		if(deleted.remove(oldObj) || oldId != null && deleted.remove(oldId) 
    				|| oldExtId != null && deleted.remove(oldExtId)) deleted.add(newObj);
    		if(reloaded.remove(oldObj) || oldId != null && reloaded.remove(oldId) 
    				|| oldExtId != null && reloaded.remove(oldExtId)) reloaded.add(newObj);
    		if(reevaluate.remove(oldObj) || oldId != null && reevaluate.remove(oldId) 
    				|| oldExtId != null && reevaluate.remove(oldExtId)) reevaluate.add(newObj);
    		
    		
    		if(trigger == oldObj || (trigger instanceof Entity && ((Entity)trigger).getId() == id)) {
    			trigger = newObj;
    		}
    	}
    }
    
    void reload(Object obj) {
         if(obj instanceof List) {
            Iterator it=((List)obj).iterator();
            while (it.hasNext()) {
                Entity en= (Entity) it.next();
                if (en.isLoadedFromCache())
                    reloaded.add(en);
            }
         } else {
            Entity en= (Entity) obj;
            if (en.isLoadedFromCache())
                reloaded.add(obj);
         }
    }

    public void reset() {
        endWork();
        if (created != null) created.clear();
        if (deleted != null) deleted.clear();
        if (reloaded != null) reloaded.clear();
        if (entityKeys != null) entityKeys.clear();

        trigger=null;
        rtcTrigger = null;
        this.clusterSubscriptionReloadOnly = false;
    }


    public Object getByExtId(String extId) {
        Object obj = null;
        if(extId != null && entityKeys != null) {
		    obj = entityKeys.get(extId);
        }
        return obj;
    }

    public Object getById(long id) {
        Object obj = null;
        if (entityKeys != null) {
            obj = entityKeys.get(id);
        }
        return obj;
    }

    public void reevaluateObject(Object obj) {
        reevaluate.add(obj);
    }

    //to be called from implementations of BaseObjectManager.getElementFromPreprocess
    public static Element getElementFromPreprocess(String extId) {
        PreprocessContext pc= PreprocessContext.getContext();
        if (pc != null) {
            Object o = pc.getByExtId(extId);
            if(o instanceof Element) return (Element) o;
        }
        return null;
    }
    //to be called from implementations of BaseObjectManager.getEventFromPreprocess
    public static Event getEventFromPreprocess(String extId) {
        PreprocessContext pc= PreprocessContext.getContext();
        if (pc != null) {
            Object o = pc.getByExtId(extId);
            if(o instanceof Event) return (Event) o;
        }
        return null;
    }
    //to be called from implementations of BaseObjectManager.getElementFromPreprocess
    public static Element getElementFromPreprocess(long id) {
        PreprocessContext pc= PreprocessContext.getContext();
        if (pc != null) {
            Object o = pc.getById(id);
            if(o instanceof Element) return (Element) o;
        }
        return null;
    }
    //to be called from implementations of BaseObjectManager.getEventFromPreprocess
    public static Event getEventFromPreprocess(long id) {
        PreprocessContext pc= PreprocessContext.getContext();
        if (pc != null) {
            Object o = pc.getById(id);
            if(o instanceof Event) return (Event) o;
        }
        return null;
    }
}
