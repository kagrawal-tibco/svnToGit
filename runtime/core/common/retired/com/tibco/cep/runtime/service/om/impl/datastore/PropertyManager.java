package com.tibco.cep.runtime.service.om.impl.datastore;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.service.om.exception.OMException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Aug 23, 2004
 * Time: 5:19:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyManager implements CacheCallBack {

    private DataStore ds;
    private DataStore propertyIndexDs;
    private LRUCache propertiesmap;
    private HashMap dirtyprops;
    private HashMap towritelist = null;
    private ArrayList deletelist = null;
    private ArrayList todeletelist = null;
    private int cachesize;
    private int propmapsize = 0;
    private PersistentStore om;

    private static final float dirtyfactor = 2.0F/3.0F;
    private Logger m_logger;


    public PropertyManager(PersistentStore om) {
        this.om = om;
        m_logger = om.m_logger;
    }

    public void init() throws Exception {
        Properties props = om.omConfig;
        cachesize = Integer.parseInt(props.getProperty("omPropCacheSize", "-1"));

        m_logger.log(Level.INFO, "Property cachesize = " + cachesize);
//        propertiesmap = new LRUCache(cachesize, this);
        propertiesmap = new LRUCache(cachesize);
        dirtyprops = new HashMap();
        if(om.immediateDeletePolicy)
            deletelist = new ArrayList();
        ds = om.dbFactory().createPropertiesDataStore(props, om.defaultSerializer);
        ds.init();
        propertyIndexDs = om.dbFactory().createPropertyIndexDataStore(props, om.defaultSerializer);
        propertyIndexDs.init();
    }

    public void start() throws Exception {

    }

    public void shutdown() throws Exception {
        if(ds != null) {
            ds.close();
            ds = null;
        }

        if(propertyIndexDs != null) {
            propertyIndexDs.close();
            propertyIndexDs = null;
        }
    }

    public void stop() throws Exception {
    }

    /**
     * Fetch the property from internal caches or from the database.
     *
     * @param subject
     * @param className
     * @return Property
     */
    public Property getProperty(Concept subject, String className) {
        if(subject instanceof ConceptImpl) {
            return getProperty(subject.getId(), (ConceptImpl)subject, className);
        } else {
            return getProperty(subject.getId(), className);
        }
    }

    public Property getProperty(long subjectId, String className) {
        return getProperty(subjectId, null, className);
    }

    private Property getProperty(long subjectId, ConceptImpl subject, String className) {
        PropertyKey propkey = new PropertyKey(subjectId, className);

        // Look in memory caches first.
        Property p = searchInternalCaches(propkey);

        if (p != null) {
            return p;
        } else { // Not found. Fetch it from the database.
            if(subject == null) {
                subject = (ConceptImpl) om.getElement(subjectId, true);
            }
            return getFromDataStore(subject, className, propkey);
        }
    }

    // Fetched the property from the db. If not present, creates a new one, adds it to the cache,
    // sets it dirty and returns that.
    private Property getFromDataStore(ConceptImpl subject, String propertyClassName, PropertyKey propkey) {
        Property p = null;
        Property newprop = subject.newProperty(propertyClassName);

        try {
            PropertyBDBKey bdbkey = new PropertyBDBKey(propertyClassName, subject.getId(), propertyIndexDs, null);
            p = (Property) ds.fetch(null, bdbkey, newprop);
        } catch (OMException e) {
            m_logger.log(Level.ERROR, "", e);
        }

        if(p != null) {
            propertiesmap.put(propkey, p);
            return p;
        }
        /* Todo: this was a bit of optimization in 1.x which is not available in 2.0.
           Todo: Is there another way?
        if (subject.isPersisted()) {
            // The property is possibly in the db only if the containing
            // concept instance has been persisted.
            try {
                p = (AbstractPropertyImpl) ds.fetch(null, propkey, newprop);
            } catch (OMFetchException e) {
                mTrace.traceException(e);
                throw new RuntimeException(e);
            }
            if (p != null) {
                // Found in the db. Add to the cache and return.
                propertiesmap.put(propkey, p);
                return p;
            } else {
                // Use the newly created property (Looks like this property is newly added!).
                // Falls to the add(newprop) below.
            }
        } else {
            // The concept instance is not yet persisted.
            // So we are not going to find the property also in the db.
            // No point making a database access.
            // Falls to the add(newprop) below.
        }
        */

        //add(propkey, newprop);  //todo - by nick
        propertiesmap.put(propkey, newprop);
        return newprop;
    }

    /**
     * Fetch the property from internal caches
     * This will also maintain the LRU for the internal cache by virtue of
     * the propertiesmap.get() call near the top.
     *
     * @param propkey
     * @return Property
     */
    private Property searchInternalCaches(PropertyKey propkey) {

        Property p;
        p = (Property) propertiesmap.get(propkey);

        if( p == null) {
            p = (Property) dirtyprops.get(propkey);
            if(p == null) {
                synchronized (this) { // Could be in doCheckpoint right now
                    if (towritelist != null) {
                        p = (Property) towritelist.get(propkey);
                    }
                }
            }

            if(p != null) // Found it in one of the above. Reinsert back at the head of main cache.
                propertiesmap.put(propkey, p);
        }

        return p;
    }


    /**
     * Add a new property to the cache and dirty list.
     *
     * @param prop
     */
    public void add(Property prop) {
        if(prop == null)
            return;
        add(new PropertyKey(prop.getParent().getId(), prop.getClass().getName()), prop);
    }

    /**
     * Add a new property to the cache and dirty list.
     *
     * @param propKey
     * @param prop
     */
    private void add(PropertyKey propKey, Property prop) {
        if(prop == null)
            return;
        propertiesmap.put(propKey, prop);
        //TODO: prop.setDirty();
        dirtyprops.put(propKey, prop);
        om.incrementOutstandingOps();
    }

    /**
     * Remove a property from the cache and possibly later from the db.
     * @param subject
     * @param className
     */
    public void remove(Concept subject, String className) {

        if(om.immediateDeletePolicy) {
            PropertyKey propkey = new PropertyKey(subject.getId(), className);
    //        Property p = null;
    //        p = searchInternalCaches(propkey);   //todo - by nick
    
            // If the call above returns non-null our main property cache already has the property in question
            // at the top and gone from every other cache. We just need to remove it from there.
    
    //        if(p != null) {         //todo - by nick
                propertiesmap.remove(propkey);
                dirtyprops.remove(propkey);
    //        }
    
            deletelist.add(propkey);
            om.incrementOutstandingOps();
        }
    }

    public void remove(Property prop) {
        remove(prop.getParent(), prop.getClass().getName());
    }

    public void modify(Property prop) {
        PropertyKey propKey = new PropertyKey(prop.getParent().getId(), prop.getClass().getName());
        if(dirtyprops.get(propKey) == null) {
            dirtyprops.put(propKey, prop);
            om.incrementOutstandingOps();
        }
    }


    /**
     *
//   * @param omstats
     */
//    public void updateStats(ObjectManagerStats omstats) {              //todo - by nick
//        omstats.numDirtyProps = dirtyprops.size();
//        omstats.propCacheMaxSize = cachesize;
//        omstats.propCacheCurrSize = propertiesmap.size();
//    }

    public void prepareCheckpoint() {
        towritelist = dirtyprops;
        todeletelist = deletelist;
        if(om.m_logger.isEnabledFor(Level.INFO)) {
            if(towritelist.size() >0)   om.m_logger.log(Level.INFO, "Num of properties to write = " + towritelist.size());
            if(om.immediateDeletePolicy && todeletelist.size() > 0) om.m_logger.log(Level.INFO, "Num of properties to delete = " + todeletelist.size());
        }
        dirtyprops = new HashMap();
        if(om.immediateDeletePolicy)
            deletelist = new ArrayList();
    }

    public void doCheckpoint(DBTransaction txn) throws OMException {
        Iterator it = null;
        if(om.immediateDeletePolicy) {
            it = todeletelist.iterator();
            while(it.hasNext()) {
                PropertyKey propkey = (PropertyKey) it.next();
                PropertyBDBKey bdbkey = new PropertyBDBKey(propkey, propertyIndexDs, txn);
                ds.delete(txn, bdbkey);
            }
            todeletelist = null;
        }

        it = towritelist.values().iterator();
        while(it.hasNext()) {
            Property p = (Property) it.next();
            PropertyBDBKey bdbid = new PropertyBDBKey(p.getClass().getName(), p.getParent().getId(), propertyIndexDs, txn);
            ds.add(txn, bdbid, p);
        }

        towritelist = null;

        /* Deliberately not forgetting the towritelist yet. Will be done via the method below
         * once checkpoint has committed. This is so that these properties can still be searched
         * by the working memory before db actually commits.
         */
    }

    synchronized public void evictOldProperties() {
//        towritelist = null;   //todo - by nick
        // This will evict any properties that were dirty and had not
                            // been evicted yet for that reason.
    }

    public boolean EntryFallOff(Object key, Object value) {     //todo - by nick
//        todo - we need this any more?
//        Property p = (Property) value;
//
//        ConceptImpl subject = (ConceptImpl) p.getParent();
//        subject.evictProperty(p.getName());
        return true;
    }
}
