package com.tibco.cep.runtime.service.om.impl.invm;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.map.LRUMap;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.model.element.VersionedObject;
import com.tibco.cep.runtime.service.om.api.invm.LocalCache;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Ashwin Jayaprakash / Date: Sep 1, 2010 / Time: 5:34:27 PM
*/

@SuppressWarnings("deprecation")
public class DefaultLocalCache implements LocalCache {
    /**
     * {@value}.
     */
    public static final int DEF_MAX_ITEMS = 10000;

    /**
     * {@value}.
     */
    public static final long DEF_EXPIRY_TIME_MILLIS = 30 * 60 * 1000 /*30 mins.*/;

    protected LRUMap lruMap;

    protected volatile int hits;

    protected volatile int misses;

    public DefaultLocalCache() {
    }

    public String getId() {
        return getClass().getSimpleName() + ":" + System.identityHashCode(this);
    }

    /**
     * @param configuration Expects {@link com.tibco.cep.runtime.util.SystemProperty#VM_LOCALCACHE_SIZE} and {@link
     *                      com.tibco.cep.runtime.util.SystemProperty#VM_LOCALCACHE_EXPIRY_MILLIS}.
     * @param otherArgs
     * @throws Exception
     */
    public void init(Configuration configuration, Object... otherArgs) throws Exception {
        String s = configuration
                .getPropertyRecursively(SystemProperty.VM_LOCALCACHE_SIZE.getPropertyName());
        if (s == null || s.length() == 0) {
            s = DEF_MAX_ITEMS + "";
        }
        int maxItems = Integer.parseInt(s);

        //-----------

        s = configuration
                .getPropertyRecursively(
                        SystemProperty.VM_LOCALCACHE_EXPIRY_MILLIS.getPropertyName());
        if (s == null || s.length() == 0) {
            s = DEF_EXPIRY_TIME_MILLIS + "";
        }
        long expiryTimeMillis = Long.parseLong(s);

        //-----------

        actualInit(maxItems, expiryTimeMillis);
    }

    protected void actualInit(int maxItems, long expiryTimeMillis) {
        //todo Expiry time is ignored.

        lruMap = new LRUMap(maxItems);
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
        clear();
    }

    //------------

    @Override
    public void clear() {
        lruMap.clear();
    }

    @Override
    public synchronized void put(Entity entity) {
        lruMap.put(entity.getId(), entity);
    }

    private Entity getAndUpdateStats(Long id) {
        Entity e = (Entity) lruMap.get(id);

        if (e == null) {
            misses++;
        }
        else {
            hits++;
        }

        return e;
    }

    private Entity getPlain(Long id) {
        return (Entity) lruMap.get(id);
    }

    @Override
    public synchronized Entity get(long id) {
        return getAndUpdateStats(id);
    }

    @Override
    public synchronized Entity getV2(Long id) {
        return getAndUpdateStats(id);
    }

    @Override
    public synchronized void remove(long id) {
        lruMap.remove(id);
    }

    @Override
    public synchronized void removeV2(Long id) {
        lruMap.remove(id);
    }

    @Override
    public synchronized int size() {
        return lruMap.size();
    }

    @Override
    public synchronized void printContents(PrintStream printStream) {
        Set<Map.Entry> entrySet = lruMap.entrySet();

        printStream
                .println("-------------------------- L1Cache contents --------------------------");
        printStream.println("Size     : " + entrySet.size());
        printStream.println("Contents : ");

        for (Map.Entry entry : entrySet) {
            Object key = entry.getKey();
            Object value = entry.getValue();

            printStream.println("           " + key + " :: " + value);
        }

        printStream
                .println("-------~-------~-------~-------~-------~-------~-------~-------~------");
    }

    @Override
    public synchronized void printToLog(Logger logger) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        PrintStream ps = new PrintStream(bos);
        printContents(ps);

        ps.flush();
        ps.close();

        String s = bos.toString();
        logger.log(Level.WARN, s);
    }

    @Override
    public int getNumRequests() {
        return misses + hits;
    }

    @Override
    public int getNumHits() {
        return hits;
    }

    @Override
    public synchronized boolean directPut(Entity entity) {
        put(entity);

        return false;
    }

    @Override
    public synchronized boolean remove(Entity entity) {
        remove(entity.getId());

        return false;
    }

    @Override
    public synchronized Entity directRemove(long id) {
        Entity e = getPlain(id);

        if (e != null) {
            remove(id);
        }

        return e;
    }

    @Override
    public synchronized boolean hasEntity(long id) {
        Entity e = getPlain(id);

        return (e != null);
    }

    @Override
    public synchronized Entry getEntry(long id) {
        return null;
    }

    @Override
    public synchronized boolean isPendingRemoval(long id) {
        //todo
        return false;
    }

    @Override
    public synchronized boolean isPendingWrite(long id) {
        //todo
        return false;
    }

    @Override
    public synchronized int getVersion(long id) {
        Entity e = getPlain(id);

        if (e == null) {
            return -1;
        }

        return readVersionOfCloakedObject(e);
    }

    /**
     * @param entity
     * @return -1 if the value is <code>null</code>. 0 if it is not a {@link com.tibco.cep.runtime.model.element.VersionedObject}.
     */
    private int readVersionOfCloakedObject(Entity entity) {
        if (entity == null) {
            return -1;
        }

        if (entity instanceof VersionedObject) {
            VersionedObject versionedObject = (VersionedObject) entity;

            return versionedObject.getVersion();
        }

        return 0;
    }

    @Override
    public synchronized boolean markSaved(long id) {
        Entity e = getPlain(id);

        if (e == null) {
            return false;
        }

//todo
//        Object value = entry.getValue();
//        incrementVersionOfCloakedObject(value);
//        entry.setSaved(true);

        return true;
    }

    @Override
    public synchronized boolean markDeleted(Entity entity) {
        remove(entity.getId());

        return true;
    }

    //Delete any entries whose properties have been changed by hotdeploy 
    //or else objects with the old properties from before the hotdeploy could be returned
	@Override
	public void entitiesChanged(Collection<Class<Entity>> changedClasses) {
		clear();
		
		/*
		HashSet<Class<Entity>> changedTypes = new HashSet(changedClasses);
		ArrayList<Long> changedIds = new ArrayList();
		for(Map.Entry entry : (Set<Map.Entry>)lruMap.entrySet()) {
			if(entry.getValue() != null && changedTypes.contains(entry.getValue().getClass())) {
				changedIds.add((Long)entry.getKey());
			}
		}
		for(Long id : changedIds) {
			removeV2(id);
		}
		*/
	}
}
