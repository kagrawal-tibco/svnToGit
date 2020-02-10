package com.tibco.cep.as.kit.map;

import java.lang.reflect.Method;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.tibco.as.space.ASException;
import com.tibco.as.space.ASStatus;
import com.tibco.as.space.LockOptions;
import com.tibco.as.space.LogLevel;
import com.tibco.as.space.PutOptions;
import com.tibco.as.space.ResultList;
import com.tibco.as.space.RuntimeASException;
import com.tibco.as.space.Space;
import com.tibco.as.space.SpaceResult;
import com.tibco.as.space.SpaceResultList;
import com.tibco.as.space.TakeOptions;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;
import com.tibco.as.space.browser.BrowserDef;
import com.tibco.as.space.browser.BrowserDef.BrowserType;
import com.tibco.as.space.browser.BrowserDef.DistributionScope;
import com.tibco.as.space.browser.BrowserDef.TimeScope;
import com.tibco.cep.as.kit.collection.Discardable;
import com.tibco.cep.as.kit.collection.DiscardableCollection;
import com.tibco.cep.as.kit.collection.DiscardableIterator;
import com.tibco.cep.as.kit.collection.DiscardableSet;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.ASMetaspaceReconnectHandler;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASConstants;
import com.tibco.cep.runtime.util.DBNotAvailableException;
import com.tibco.cep.runtime.util.SystemProperty;

/*
 * Author: Ashwin Jayaprakash Date: Apr 24, 2009 Time: 4:21:17 PM
 */
public class SpaceMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, Discardable {
    
    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(SpaceMap.class);

    protected static boolean once = true;

    protected static long STAT_INTERVAL = 4000L;
    protected static long statTime = 0L;
    public static Space statSpace;
    public static Map<String, AtomicLong> statMap;

    protected Space space;
    protected String spaceName;

    protected KeyValueTupleAdaptor<K, V> kvTupleAdaptor;

    protected boolean setForget;
    
    protected long aggregatePrefetch;
    protected long browserPrefetch;
    protected Long browserTimeout;
    protected long lookupPrefetch;
    
    protected TimeScope aggregateTimescope;
    protected TimeScope browserTimescope;
    protected TimeScope lookupTimescope;
    
    protected boolean setJoin;

    public SpaceMap(Space space, KeyValueTupleAdaptor<K, V> kvTupleAdaptor) {
        this.space = space;
        this.spaceName = space.getName();
        this.kvTupleAdaptor = kvTupleAdaptor;

        if (statSpace == null) {
        	try {
				statSpace = space.getMetaspace().getSpace("$space_stats");
				if (statMap == null) {
					statMap = new ConcurrentHashMap<String, AtomicLong>();
				}
                statMap.put(spaceName, new AtomicLong(0L));
        	} catch (ASException e) {
				LOGGER.log(Level.WARN, "Failed connecting to statistics space. Error: %s", e.getMessage());
				resetSpaceConnection(e);
			}
        }

        // Property to make setForget true/false.
        // By default it is true
        // Set this to false only in case of bugs with true.
        //@todo - this property needs to be removed at some point in the future
        Properties properties = System.getProperties();
        String setForgetStr = properties.getProperty(SystemProperty.PROP_AS_SET_FORGET.getPropertyName(), Boolean.TRUE.toString());
        this.setForget = Boolean.parseBoolean(setForgetStr);
        
        this.aggregatePrefetch = Long.parseLong(properties.getProperty(ASConstants.PROP_AS_AGGREGATE_PREFETCH_SIZE, "-1"));
        this.browserPrefetch = Long.parseLong(properties.getProperty(ASConstants.PROP_AS_BROWSER_PREFETCH_SIZE, "-1"));
        String browserTimeoutProp = properties.getProperty(ASConstants.PROP_AS_BROWSER_TIMEOUT);
        if (browserTimeoutProp != null) {
        	this.browserTimeout = Long.parseLong(browserTimeoutProp);
        }
        this.lookupPrefetch = Long.parseLong(properties.getProperty(ASConstants.PROP_AS_LOOKUP_PREFETCH_SIZE, "0"));
        
        this.aggregateTimescope = TimeScope.valueOf(properties.getProperty(ASConstants.PROP_AS_AGGREGATE_TIME_SCOPE, "SNAPSHOT").toUpperCase());
        this.browserTimescope = TimeScope.valueOf(properties.getProperty(ASConstants.PROP_AS_BROWSER_TIME_SCOPE, "SNAPSHOT").toUpperCase());
        this.lookupTimescope = TimeScope.valueOf(properties.getProperty(ASConstants.PROP_AS_LOOKUP_TIME_SCOPE, "SNAPSHOT").toUpperCase());
        
        this.setJoin = Boolean.parseBoolean(properties.getProperty(ASConstants.PROP_AS_SPACE_BROWSER_JOIN, "false"));
        
        if (once) {
        	once = false;
        	LOGGER.log(Level.INFO, "Space maps are defined with prefetch (aggregate: %s browser: %s lookup: %s), timescope (aggregate: %s browser: %s lookup: %s) and browserJoin(%s)",
        			aggregatePrefetch, browserPrefetch, lookupPrefetch, aggregateTimescope, browserTimescope, lookupTimescope, setJoin);
        	if (browserTimeout != null) {
        		LOGGER.log(Level.DEBUG, "Configured Browser timeout as " + browserTimeout + " milli");
        	}
        }
        
        ASMetaspaceReconnectHandler.getInstance().registerSpaceMap(this);
    }

    public Space getSpace() {
        return space;
    }
    public void setSpace(Space space) {
    	this.space = space;
    }
    
	public String getSpaceName() {
    	return this.spaceName;
    }

    public KeyValueTupleAdaptor<K, V> getTupleAdaptor() {
        return kvTupleAdaptor;
    }

    public void waitUntilReady() {
        try {
            while (!space.waitForReady(10 * 1000)) {
                LOGGER.log(Level.INFO, "Waiting for space [" + space.getName() + "] to be ready (state: " + space.getSpaceState().name() + ")");
            }
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Space [" + space.getName() + "] is ready (state: " + space.getSpaceState().name() + ") with " + space.size() + " items");
            }
        }
        catch (ASException e) {
            traceAsException(e, "Space or metaspace may be closed.");
        }
    }

    public Level translateAsLogLevel2BELogLevel(LogLevel asLogLevel) {
        // AsLogLevels: NONE, FATAL, ERROR, WARN, INFO, FINE, FINER, FINEST
        switch (asLogLevel) {
            case NONE:
                return Level.OFF;
            case ERROR:
                return Level.ERROR;
            case FATAL:
                return Level.FATAL;
            case INFO:
                return Level.INFO;
            case WARN:
                return Level.WARN;
            case FINE:
                return Level.DEBUG;
            case FINER:
                return Level.TRACE;
            case FINEST:
                return Level.ALL;
            default:
                return Level.DEBUG;
        }
    }

    public static void traceAsException(ASException ase, String msg) {
    	ASMetaspaceReconnectHandler.getInstance().resetAllConnections(ase);
        if (ase.getStatus() == ASStatus.INVALID_OBJECT) {
            LOGGER.log(Level.TRACE, ase, msg);
        } 
        else if (ase.getStatus() == ASStatus.OPERATION_TIMEOUT && ase.getMessage().contains("seeder_count=0")) {
            LOGGER.log(Level.TRACE, ase, msg);
        } 
        else if (ase.getStatus() == ASStatus.PERSISTER_OFFLINE) {
            throw new DBNotAvailableException(ase);
        }
        else {
            throw new RuntimeException(ase);
        }
    }

    public static void traceAsException(RuntimeASException rase, String msg) {
    	ASMetaspaceReconnectHandler.getInstance().resetAllConnections(rase);
        if (rase.getStatus() == ASStatus.INVALID_OBJECT) {
            LOGGER.log(Level.TRACE, rase, msg);
        }
        else if (rase.getStatus() == ASStatus.OPERATION_TIMEOUT && rase.getMessage().contains("seeder_count=0")) {
            LOGGER.log(Level.TRACE, rase, msg);
        } 
        else if (rase.getStatus() == ASStatus.PERSISTER_OFFLINE) {
            throw new DBNotAvailableException(rase);
        }
        else {
            throw new RuntimeException(rase);
        }
    }

    //--------------

    public boolean lock(K key, long timeoutMillis) {
        Tuple queryTuple = kvTupleAdaptor.makeTuple(key);
        try {
            LockOptions lockOptions = LockOptions.create();
            lockOptions.setLockWait(timeoutMillis);
            // setForget true to avoid a deserialization for tuple on return
            lockOptions.setForget(true);

            space.lock(queryTuple, lockOptions);
        }
        catch (ASException e) {
            // Examine the exception status
            if (e.getStatus() == ASStatus.LOCKED) {
                return false;
            }
            traceAsException(e, "Space or metaspace may be closed.");
        }
        return true;
    }

    public boolean unlock(K key) {
        Tuple queryTuple = kvTupleAdaptor.makeTuple(key);

        try {
            space.unlock(queryTuple);
        }
        catch (ASException e) {
            traceAsException(e, "Space or metaspace may be closed.");
        }
        return true;
    }

    //--------------

    public int size_disabled() {
    	int size = 0;
        try {
            size = (int) space.size();
        }
        catch (ASException e) {
        	resetSpaceConnection(e);
            throw new RuntimeException(e);
        }
        return size;
    }

    @Override
    public int size() {
    	return (int)sizeFromStats();
    }
    
    private synchronized long sizeFromStats() {
    	long size = 0L;
    //	LOGGER.log(Level.INFO, "SpaceMap sizeFromStats() method is called ...");
        if ((System.currentTimeMillis()-statTime) < STAT_INTERVAL
        		&& statMap.containsKey(this.spaceName)) {
        	// Return cached results...
        	size = statMap.get(this.spaceName).get();
    		return size;
        }
        
        // Recompute from $space_stats table...
        statTime = System.currentTimeMillis();
        statMap = new ConcurrentHashMap<String, AtomicLong>();
        statMap.put(spaceName, new AtomicLong(0L));
        try {
            Tuple result = null;
        	Browser browser = statSpace.browse(BrowserType.GET);
        	while (true) {
                result = browser.next();
                if (result == null) {
                    break;
                }
                String spaceName = result.getString("space_name");
                if (spaceName.charAt(0) == '$') {
                    continue;
                }
                long memberSize = result.getLong("original_count");
                AtomicLong currSize = statMap.get(spaceName);
                if (currSize == null) {
                    statMap.put(spaceName, new AtomicLong(memberSize));
                }
                else {
                    currSize.addAndGet(memberSize);
                }
            }
        	size = statMap.get(this.spaceName).get();
        }
        catch (ASException e) {
    		LOGGER.log(Level.WARN, "Computing size from space stats failed - %s", e.getMessage());
    		resetSpaceConnection(e);
        	return 0L;
        }
		return size;
    }
    
    public boolean isEmpty() {
        return ((size() == 0));
    }

    protected Tuple getTuple(K key) {
        Tuple queryTuple = kvTupleAdaptor.makeTuple(key);
        Tuple result = null;
        try {
            result = space.get(queryTuple);
        }
        catch (ASException e) {
            traceAsException(e, "Space or metaspace may be closed.");
        }
        return result;
    }

    public boolean containsKey(Object key) {
        return getTuple((K) key) != null;
    }

    public boolean containsValue(Object value) {
        //todo
        return false;
    }

    @SuppressWarnings("unchecked")
    public V get(Object key) {
        Tuple tuple = getTuple((K) key);
        // This can return null for objects not yet committed to cache also?
        // Putting null check avoids NPE.
        if (tuple == null) {
            return null;
        }
        return kvTupleAdaptor.extractValue(tuple);
    }

    public Collection<V> getAll(Collection<K> keys) {
        // Convert all the keys to tuples
        LinkedList<Tuple> keyTuples = new LinkedList<Tuple>();
        for (K key : keys) {
            keyTuples.add(kvTupleAdaptor.makeTuple(key));
        }

        // Get the values for the key tuples
        SpaceResultList tupleResults = space.getAll(keyTuples);

        LinkedList<V> values = new LinkedList<V>();
        for (SpaceResult tupleResult : tupleResults) {
            values.add(kvTupleAdaptor.extractValue(tupleResult.getTuple()));
        }
        return values;
    }

    /**
     * @param key
     * @param value
     * @return Previous value if key already exists, else null.
     */
    public V put(K key, V value) {
        return put(key, value, this.setForget);
    }

    /**
     * @param key
     * @param value
     * @param forget , true = do not return old value, false = return old value
     * @return Returns no value if key already exists, else null.
     */
    public V put(K key, V value, boolean forget) {
        Tuple tuple = kvTupleAdaptor.makeTuple(key);
        Tuple result = null;
        kvTupleAdaptor.setValue(tuple, value);

        try {
            // If forget = true, the return value will not be constructed by AS.
            PutOptions options = PutOptions.create().setForget(forget);
            result = space.put(tuple, options);
        }
        catch (ASException e) {
            traceAsException(e, "Space or metaspace may be closed.");
        }

        if(!forget) {
            return (result == null) ? null : kvTupleAdaptor.extractValue(result);
        }
        return null;
    }

    public void putAll(Map<? extends K, ? extends V> t) {
        ArrayList<Tuple> tuples = new ArrayList<Tuple>(t.size());

        for (Entry<? extends K, ? extends V> entry : t.entrySet()) {
            Tuple tuple = kvTupleAdaptor.makeTuple(entry.getKey());

            kvTupleAdaptor.setValue(tuple, entry.getValue());

            tuples.add(tuple);
        }

        ResultList resultList = null;
        try {
            //if forget = true, the return value will not be constructed by AS.
            PutOptions options = PutOptions.create().setForget(true);
            resultList = space.putAll(tuples, options);
        } catch (RuntimeASException ex) {
            traceAsException(ex, "Space or metaspace may be closed.");
        }

        if (resultList!=null && resultList.hasError()) {
            for (int i = resultList.size() - 1; i >= 0; i--) {
                ASException e = resultList.getError(i);
                if (e != null) {
                    if (t.size() == 1) {
                        throw new RuntimeException("PutAll resulted in an error", e);
                    }
                    else {
                        throw new RuntimeException(
                                "PutAll resulted in error(s)." +
                                        " Only the first error is reported. There may be other errors", e);
                    }
                }
            }
        }

        tuples.clear();
    }

    public V remove(Object key) {
        Tuple queryTuple = kvTupleAdaptor.makeTuple((K) key);
        Tuple result = null;

        try {
            //set forget ??
            result = space.take(queryTuple);
        }
        catch (ASException e) {
        	resetSpaceConnection(e);
            throw new RuntimeException(e);
        }

        return kvTupleAdaptor.extractValue(result);
    }
    
    public void removeAll(Collection<K> ks) {
        ArrayList<Tuple> tuples = new ArrayList<Tuple>(ks.size());

        try {
            for (K key : ks) {
                Tuple queryTuple = kvTupleAdaptor.makeTuple(key);
                tuples.add(queryTuple);
            }

            //if forget = true, the return value will not be constructed by AS.
            TakeOptions options = TakeOptions.create().setForget(true);
            space.takeAll(tuples, options);
        }
        finally {
            tuples.clear();
        }
    }

    public DiscardableSet<K> keySet() {
        return new SpaceMapKeySet();
    }

    public DiscardableSet<K> keySet(String query) {
        return new SpaceMapKeySet(query);
    }

    public DiscardableCollection<V> values() {
        return new SpaceMapValueCollection();
    }

    // Used by - GetLastTypeId,StartCluster,RefreshGlobalView
    public DiscardableSet<Map.Entry<K, V>> entrySet() {
        return new SpaceMapEntrySet(BrowserType.GET, TimeScope.SNAPSHOT, DistributionScope.ALL, BrowserDef.PREFETCH_ALL, browserTimeout, setJoin);
    }
    
    // Used by - BQL(Query),FetchByExtId(Inference)
    public DiscardableSet<Map.Entry<K, V>> entrySet(String query) {
        if ((query != null) && query.startsWith("extId = ")) {
            return new SpaceMapEntrySet(BrowserType.GET, this.lookupTimescope, DistributionScope.ALL, query, this.lookupPrefetch, browserTimeout, setJoin);
        } else {
            return new SpaceMapEntrySet(BrowserType.GET, this.browserTimescope, DistributionScope.ALL, query, this.browserPrefetch, browserTimeout, setJoin);
        }
    }

    // Used by - InvokeMember(Cache)
    public DiscardableSet<Map.Entry<K, V>> entrySetSeeded() {
        return new SpaceMapEntrySet(BrowserType.GET, this.browserTimescope, DistributionScope.SEEDED, this.browserPrefetch, browserTimeout, setJoin);
    }

    // Used by - Unknown
    public DiscardableSet<Map.Entry<K, V>> entrySetSeeded(String query) {
        return new SpaceMapEntrySet(BrowserType.GET, this.browserTimescope, DistributionScope.SEEDED, query, this.browserPrefetch, browserTimeout, setJoin);
    }

    public int clear(String filter) {
    	try {
        	long total = space.size();
	    	if (filter == null) {
	    		space.clear();
	    	}
	    	else {
	    		space.clear(filter);
	    	}
	    	return (int) (total - space.size());
        }
        catch (ASException e) {
            traceAsException(e, "Space or metaspace may be closed.");
        }
    	return -1;
    }

    //----------------

    public void discard() {
        try {
            space.close();
            space = null;
        }
        catch (ASException e) {
            throw new RuntimeException(e);
        }
    }

    //----------------

    protected class SpaceMapEntrySet extends AbstractSet<Map.Entry<K, V>> implements DiscardableSet<Map.Entry<K, V>> {
        
        protected BrowserType browserType;

        protected TimeScope timeScope;

        protected DistributionScope distributionScope;

        protected long prefetch = BrowserDef.PREFETCH_ALL;
        protected Long timeout = null;
        
        protected String query;
        
        protected boolean setJoin = false;
        
        public SpaceMapEntrySet(BrowserType browserType, TimeScope timeScope, DistributionScope distributionScope,
                                long prefetchSize, Long timeout, boolean setJoin) {
            this(browserType, timeScope, distributionScope, null, prefetchSize, timeout, setJoin);
        }

        public SpaceMapEntrySet(BrowserType browserType, TimeScope timeScope, DistributionScope distributionScope,
                                String query, long prefetchSize, Long timeout, boolean setJoin) {
            this.timeScope = timeScope;
            this.browserType = browserType;
            this.distributionScope = distributionScope;
            this.prefetch = prefetchSize;
            this.timeout = timeout;
            this.query = query;
            this.setJoin = setJoin;
        }

        public DiscardableIterator<Map.Entry<K, V>> iterator() {

            // Setting query limit to -1 explicitly. The default query limit is 10000. This default can cause query results to be wrong.
            BrowserDef browserDef = BrowserDef.create()
                    .setTimeScope(timeScope)
                    .setDistributionScope(distributionScope)
                    .setPrefetch(prefetch)
                  //.setTimeout(BrowserDef.NO_WAIT) // TODO: causes exception with PF=0
                    .setQueryLimit(-1);
            
            if (timeout != null) {
            	browserDef.setTimeout(timeout);
            }
            
            if (setJoin) {
            	try {
					Method setJoinMethod = browserDef.getClass().getMethod("setJoin", boolean.class);
					if (setJoinMethod != null) setJoinMethod.invoke(browserDef, setJoin);
				} catch (Exception e) {
					LOGGER.log(Level.WARN, e, "Error invoking setJoin method. This method is available only post AS 2.4 version.");
				}            	
            }

            Browser browser = null;
            try {
                browser = (query == null) ? space.browse(browserType, browserDef) :
                        space.browse(browserType, browserDef, query);
            }
            catch (ASException e) {
                traceAsException(e, "Space or metaspace may be closed.");
            }

            return new SpaceMapEntryIterator<K, V>(space, kvTupleAdaptor, browser);
        }

        public int size() {
            return SpaceMap.this.size();
        }

        //------------

        public void discard() {
        }
    }

    protected class SpaceMapKeySet extends AbstractSet<K> implements DiscardableSet<K> {
        protected String query;

        public SpaceMapKeySet() {
        }

        public SpaceMapKeySet(String query) {
            this.query = query;
        }

        public DiscardableIterator<K> iterator() {
            final DiscardableIterator<Map.Entry<K, V>> entryIterator = (query == null)
                    ? SpaceMap.this.entrySet().iterator() : SpaceMap.this.entrySet(query).iterator();

            return new DiscardableIterator<K>() {
                public boolean hasNext() {
                    boolean b = entryIterator.hasNext();
                    if (!b) {
                        entryIterator.discard();
                    }

                    return b;
                }

                public K next() {
                    return entryIterator.next().getKey();
                }

                public void remove() {
                    entryIterator.remove();
                }

                //-----------

                public void discard() {
                    entryIterator.discard();
                }
            };
        }

        public int size() {
            return SpaceMap.this.size();
        }

        @Override
        public boolean contains(Object k) {
            return SpaceMap.this.containsKey(k);
        }

        //-----------

        public void discard() {
        }
    }

    protected class SpaceMapValueCollection extends AbstractCollection<V> implements DiscardableCollection<V> {

        public DiscardableIterator<V> iterator() {
            final DiscardableIterator<Map.Entry<K, V>> entryIterator = SpaceMap.this.entrySet().iterator();

            return new DiscardableIterator<V>() {
                public boolean hasNext() {
                    boolean b = entryIterator.hasNext();
                    if (!b) {
                        entryIterator.discard();
                    }

                    return b;
                }

                public V next() {
                    return entryIterator.next().getValue();
                }

                public void remove() {
                    entryIterator.remove();
                }

                //-----------

                public void discard() {
                    entryIterator.discard();
                }
            };
        }

        public int size() {
            return SpaceMap.this.size();
        }

        @Override
        public boolean contains(Object v) {
            return SpaceMap.this.containsValue(v);
        }

        //-----------

        public void discard() {
        }
    }
    
    /**
     * Resets all space connection if needed.
     * @param ase
     */
    public void resetSpaceConnection(Exception ase) {
    	ASMetaspaceReconnectHandler.getInstance().resetAllConnections(ase);
    }
}
