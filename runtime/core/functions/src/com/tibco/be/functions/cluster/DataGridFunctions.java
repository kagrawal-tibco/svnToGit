package com.tibco.be.functions.cluster;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.be.model.functions.Enabled;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.ManagedObjectManager;

/*
* Author: Ashwin Jayaprakash / Date: Nov 10, 2010 / Time: 10:47:57 AM
*/
@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Cluster.DataGrid",
        synopsis = "Functions to operate on the cluster")
public class DataGridFunctions {
	
	protected static boolean uriRequired;
    protected static DataGridFunctionsProvider provider;

    public static DataGridFunctionsProvider getProvider() {
        return provider;
    }

    public static void setProvider(DataGridFunctionsProvider provider) {
        DataGridFunctions.provider = provider;
        DataGridFunctions.uriRequired = ManagedObjectManager.isOn();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "CacheName",
        synopsis = "Returns the cache name for the specified <code>entityPath</code>",
        signature = "String CacheName(String entityPath)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityPath", type = "String", desc = "Full path of the entity model")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The cache name matching the <code>entityPath</code>"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the cache name for the specified <code> entityPath</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/> String cName = Cluster.DataGrid.CacheName($1/Concepts/Customer$1);<br/><br/>"
    )
    public static String CacheName(String entityPath) {
        return provider.getCacheName(entityPath);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "ClassName",
        synopsis = "Returns the generated class name",
        signature = "String ClassName(String entityPath)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityPath", type = "String", desc = "Full path of the entity model")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The name of the class generated for <code>entityPath</code>"),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the generated class name",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static String ClassName(String entityPath) {
        return provider.getClassName(entityPath);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "Index",
        synopsis = "Creates an index on the property",
        signature = "void Index(String cacheName, Object property, boolean isOrdered)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "Name of the targeted cache returned by <code>CacheName</code>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "property", type = "Object", desc = "Field name(s) or definition returned by AtomGetter. Use String[] when creating composite indexes using multiple field names"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "isOrdered", type = "boolean", desc = "true if the contents of the indexed information should be ordered; false otherwise")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates an index on the property",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void Index(String cacheName, Object property, boolean isOrdered) {
        provider.setIndex(cacheName, property, isOrdered);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "EnableCacheUpdate",
        synopsis = "Set to true if the current RTC changes be replicated to the cache else RTC changes will lead to cache eviction instead of updates",
        signature = "void EnableCacheUpdate(boolean updateCache)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "updateCache", type = "boolean", desc = "true if the current RTC changes be replicated to the cache else RTC changes will lead to cache eviction instead of updates")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set to true if the current RTC changes be replicated to the cache else RTC changes will lead to cache eviction instead of updates",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void EnableCacheUpdate(boolean updateCache) {
        provider.setEnableCacheUpdate(updateCache);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "TransactionProperties",
        synopsis = "Set the transaction properties for the current RTC",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.coherence", value=false),
        signature = "void TransactionProperties(boolean autoCommit, int concurrency, int isolation)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "autoCommit", type = "boolean", desc = "true if the cache updates are applied within a transaction boundary"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "concurrency", type = "CONCUR_PESSIMISTIC=1,", desc = "CONCUR_OPTIMISTIC=2"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "isolation", type = "TRANSACTION_GET_COMMITTED", desc = "= 1, TRANSACTION_REPEATABLE_GET = 2, TRANSACTION_SERIALIZABLE = 3")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set the transaction properties for the current RTC",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void TransactionProperties(boolean autoCommit, int concurrency, int isolation) {
        provider.setTransactionProperties(autoCommit, concurrency, isolation);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "Flush",
        synopsis = "Flushes the write behind queue for all the distributed cache engines",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.datagrid.ext", value=false),
        signature = "void Flush()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Flushes the write behind queue for all the distributed cache engines",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void Flush() {
        provider.flush();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "CacheLoadEntity",
        synopsis = "Load the entity into RETE",
        signature = "void CacheLoadEntity(Entity entity)",
        params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entity", type = "Entity", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Load the entity into RETE",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void CacheLoadEntity (Entity entity) {
        provider.cacheLoadEntity(entity);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "CacheReevaluate",
        synopsis = "Reload the entity into RETE and reevaluate all rules (allowed only in preprocessor)",
        signature = "void CacheReevaluate(Entity entity)",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Loads the entity into RETE and reevaluates all rules (allowed only in preprocessor)",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void CacheReevaluate (Entity entity) {
        provider.cacheReevaluate(entity);
    }

   /* @com.tibco.be.model.functions.BEFunction(
        name = "CacheGetEntityById",
        synopsis = "Get an entity from the cache and load the entity into RETE",
        signature = "Concept CacheGetEntityById(long id, String uri, boolean load)",
        enabled = @Enabled(value=false),
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "id", type = "long", desc = "Identifier for the targeted entity"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of the class")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Entity", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get an entity from the cache and load the entity into RETE",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )*/
    public static Entity CacheGetEntityById (long id, String uri, boolean load) {
        return provider.cacheGetEntityById(id, uri, load);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "CacheLoadConceptById",
        synopsis = "Retrieve from the cache the concept whose identifier is specified",
        signature = "Concept CacheLoadConceptById(long id, boolean includeContained)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "id", type = "long", desc = "Identifier for the targeted concept"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "includeContained", type = "boolean", desc = "If true load the entire complex concept; if false load only the root concept")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve from the cache the concept whose identifier is specified",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Concept CacheLoadConceptById (long id, boolean includeContained) {
        return provider.cacheLoadConceptById(id, includeContained);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "CacheLoadEventById",
        synopsis = "Retrieve from the cache the event whose identifier is specified",
        signature = "Event CacheLoadEventById(long id)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "id", type = "long", desc = "Identifier for the targeted event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Event", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve from the cache the event whose identifier is specified",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Event CacheLoadEventById (long id) {
        return provider.cacheLoadEventById(id);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "CacheLoadEventByExtId",
        synopsis = "Retrieve from the cache the event whose external identifier is specified",
        signature = "Event CacheLoadEventByExtId(String extId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "External identifier for the targeted event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Event", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve from the cache the event whose external identifier is specified",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Event CacheLoadEventByExtId (String extId) {
    	if (DataGridFunctions.uriRequired) {
    		throw new RuntimeException("Function CacheLoadEventByExtId() is not supported in Shared Nothing mode. Use CacheLoadEventByExtIdByUri() instead.");
    	}
        return provider.cacheLoadEventByExtId(extId);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "CacheLoadConceptIndexedByExtId",
        synopsis = "Retrieve from the cache the concept whose external identifier is specified.\nThis method relies on an index that should be added on the concept's extId. See <code>Index(String, Object, boolean)</code> and the extractor to be used for the index creation -\nEntityExtIdGetter().",
        signature = "Concept CacheLoadConceptIndexedByExtId(String extId, boolean includeContained,\nString className)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "External identifier for the targeted concept"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "includeContained", type = "boolean", desc = "If true load the entire complex concept; if false load only the root concept"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "className", type = "String", desc = "Concept class name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve from the cache the concept whose external identifier is specified.\nThis method relies on an index that should be added on the concept's extId. See <code>Index(String, Object, boolean)</code> and the extractor to be used for the index creation -\nEntityExtIdGetter().",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Concept CacheLoadConceptIndexedByExtId(String extId, boolean includeContained,
                                                           String className) {
        return provider.cacheLoadConceptIndexedByExtId(extId, includeContained, className);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "CacheLoadConceptByExtId",
        synopsis = "Retrieve from the cache the concept whose external identifier is specified",
        signature = "Concept CacheLoadConceptByExtId(String extId, boolean includeContained)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "External identifier for the targeted concept"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "includeContained", type = "boolean", desc = "If true load the entire complex concept; if false load only the root concept")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve from the cache the concept whose external identifier is specified",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Concept CacheLoadConceptByExtId (String extId, boolean includeContained) {
    	if (DataGridFunctions.uriRequired) {
    		throw new RuntimeException("Function CacheLoadConceptByExtId() is not supported in Shared Nothing mode. Use CacheLoadConceptByExtIdByUri() instead.");
    	}
        return provider.loadConceptUsingExtId(extId, includeContained, null);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "CacheLoadParent",
        synopsis = "Loads from cache the parent concepts of the given concept.",
        signature = "void CacheLoadParent(Concept cept, boolean recursive)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cept", type = "Concept", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Loads from cache the parent concepts of the given concept.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void CacheLoadParent (Concept cept, boolean recursive) {
        provider.cacheLoadParent(cept, recursive);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "CacheLoadConceptsByExtId",
        synopsis = "Bulk retrieval of Concepts from the Cache.",
        signature = "Object[] CacheLoadConceptsByExtId(String[] extIds)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extIds", type = "String[]", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = ""),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve Concepts from the Cache whose extIds are specified.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object[] CacheLoadConceptsByExtId(String[] extIds) {
    	if (DataGridFunctions.uriRequired) {
    		throw new RuntimeException("Function CacheLoadConceptsByExtId() is not supported in Shared Nothing mode. Use CacheLoadConceptsByExtIdByUri() instead.");
    	}
        return provider.cacheLoadConceptsByExtId(extIds);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "CacheLoadConceptsByExtIdByUri",
        synopsis = "Bulk retrieval of Concepts from the Cache.",
        signature = "Concept[] CacheLoadConceptsByExtId(String[] extIds, String uri)",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.getbyuri", value=false),
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extIds", type = "String[]", desc = ""),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of the concepts to load")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept[]", desc = ""),
        version = "5.1.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve Concepts from the Cache whose extIds and uri are specified. Use this function when querying by extid.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Concept[] CacheLoadConceptsByExtIdByUri(String[] extIds, String uri) {
        return provider.cacheLoadConceptsByExtIdByUri(extIds, uri);
    }    
    
    @com.tibco.be.model.functions.BEFunction(
        name = "Lock",
        synopsis = "Locks the object represented by the key within a rule session",
        signature = "boolean Lock(String key, long timeout, boolean localOnly)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "A key that uniquely identifies a lock."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "timeout", type = "long", desc = "Specify in milliseconds the time to wait for the lock."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "localOnly", type = "boolean", desc = "true if the lock is local to the local session, false if the lock is cluster wide")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Locks the object within a rule session. This call will wait for <tt>timeout</tt>. The <tt> timeout </tt> value of zero indicates wait indefinitely",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static boolean Lock (String key, long timeout, boolean localOnly) {
    	if (provider != null) {
    		return provider.lock(key, timeout, localOnly);
    	} else {
    		return DataGridFunctionsProvider.lockImpl(key, timeout, localOnly);
    	}
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "UnLock",
        synopsis = "Unlocks the object represented by the key within a rule session",
        signature = "void UnLock(String key, boolean localOnly)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "A key that uniquely identifies a lock"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "localOnly", type = "boolean", desc = "true if the lock is local to the local session, false if the lock is cluster wide")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "UnLocks the object represented by the key within a rule session.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void UnLock (String key, boolean localOnly) {
    	if (provider != null) {
    		provider.unLock(key, localOnly);
    	} else {
    		DataGridFunctionsProvider.unLockImpl(key, localOnly);
    	}
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "CacheLoadConceptByExtIdByUri",
        synopsis = "Retrieve from the cache the concept whose external identifier is specified",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.getbyuri", value=false),
        signature = "Concept CacheLoadConceptByExtIdByUri(String extId, boolean includeContained, String uri)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "External identifier for the targeted concept"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "includeContained", type = "boolean", desc = "If true load the entire complex concept; if false load only the root concept"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of the concept to load")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve from the cache the concept whose external identifier is specified. Use this function when querying by extid.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Concept CacheLoadConceptByExtIdByUri (String extId, boolean includeContained, String uri) {
    	return provider.cacheLoadConceptByExtIdByUri(extId, includeContained, uri);
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "CacheLoadEventByExtIdByUri",
        synopsis = "Retrieve from the cache the event whose external identifier is specified",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.getbyuri", value=false),
        signature = "Event CacheLoadEventByExtIdByUri(String, String)(String extId, String uri)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extId", type = "String", desc = "External identifier for the targeted event"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of the event")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Event", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve from the cache the event whose external identifier is specified. Use this function when querying by extid.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Event CacheLoadEventByExtIdByUri (String extId, String uri) {
        return provider.cacheLoadEventByExtIdByUri(extId, uri);
    }
    
    @com.tibco.be.model.functions.BEFunction(
    		enabled = @Enabled(property = "TIBCO.BE.function.catalog.cluster.datagrid.evict.cache", value = true),
            name = "EvictCache",
            synopsis = "Execute a query/filter based eviction from cache. Strictly to be used for entities with cache-only object management.<br/>It will work only if \"Store Properties As Individual Fields\" is enabled in the CDD cluster setting.<br/>It will not work with the history based properties.",
            signature = "void EvictCache(String cacheName, Object filter, boolean deleteFromPersistence)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "Name of the targeted cache returned by <code>CacheName</code>"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "filter", type = "Object", desc = "Filter string with eviction condition. Use <code>null</code> if all instances in the cache needs to be evicted."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "deleteFromPersistence", type = "boolean", desc = "Flag specifying whether or not evicted entities should be deleted from persistence. However, write-behind and shared-nothing modes require this flag to be set <code>true</code>.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            //freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Number of entities that were evicted. Return count of <code>-1</code> signifies an error condition."),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Execute a query/filter based eviction from cache. Strictly to be used for entities with cache-only object management.<br/>It will work only if \"Store Properties As Individual Fields\" is enabled in the CDD cluster setting.<br/>It will not work with the history based properties.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
    )
    public static int EvictCache (String cacheName, Object filter, boolean deleteFromPersistence) {
		return provider.evictCache(cacheName, filter, deleteFromPersistence);
    }
}
