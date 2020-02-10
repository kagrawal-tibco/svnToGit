/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.functions.coherence;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.BUI;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;
import static com.tibco.be.model.functions.FunctionDomain.QUERY;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.tibco.be.model.functions.Enabled;
import com.tibco.cep.kernel.model.entity.Entity;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Coherence",
        category = "NamedCache",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.namedcache",value=false),
        synopsis = "Functions for NamedCache")
public class NamedCacheAPI {
    
    @com.tibco.be.model.functions.BEFunction(
        name = "initFromCachedOM",
        synopsis = "Initialize the cache factory using the cache-config.xml file <br/> specified by the resource or file described in the <br/> Object Manager cache configuration",
        signature = "void initFromCachedOM()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Initialize the cache factory using the cache-config.xml file <br/> specified by the resource or file described in the <br/> Object Manager cache configuration",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void initFromCachedOM() {
        NamedCacheDelegate.initFromCachedOM();

    }
    @com.tibco.be.model.functions.BEFunction(
        name = "init",
        synopsis = "Initialize the cache factory using the cache-config.xml file specified by the <code>strURL</code>",
        signature = "void  initWithURL(String strURL)",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Initialize the cache factory using the cache-config.xml file specified by the <code>strURL</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = "<br/> NamedCacheAPI.init($1C:/coherence-config/coherence-cache-config.xml$1);<br/><br/>"
    )
    public static void init(String strURL) {
       NamedCacheDelegate.init(strURL);
    }

    private static void redirectStdErrAndStdOut() {
        ByteArrayOutputStream os = new ByteArrayOutputStream(8192);
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        System.setErr(ps);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getNamedCache",
        synopsis = "Return the named cache using the specified <code>cacheName</code>",
        signature = "NamedCache getNamedCache(String cacheName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "(Cache Name)"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "classType", type = "String", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the named cache using the specified <code>cacheName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/> NamedCacheAPI.getNamedCache($1Cache.A.B.C$1);<br/><br/>"
    )
    public static Object getNamedCache(String cacheName, String classType) {
       return NamedCacheDelegate.getNamedCache(cacheName, classType);
    }

   
    @com.tibco.be.model.functions.BEFunction(
        name = "releaseCache",
        synopsis = "Release the cache memory and reference to the <code>cacheName</code>",
        signature = "void releaseCache(NamedCache cache)",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Release the cache memory and reference to the <code>cacheName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void releaseCache(String cacheName) {
        NamedCacheDelegate.releaseCache(cacheName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "startTxn",
        synopsis = "Start cache transaction on a specified <code>cacheName</code>",
        signature = "void startTxn(String cacheName)",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Starts a transaction for cache updates on a specified <code>cacheName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = "<br/>NamedCacheAPI.startTxn($1Cache.A.B.C$1);<br/><br/>"
    )
    public static void startTxn(String cacheName) {
        NamedCacheDelegate.startTxn(cacheName);
    }
    @com.tibco.be.model.functions.BEFunction(
        name = "rollbackTxn",
        synopsis = "Rollback cache transaction for the specified <code>cacheName</code>",
        signature = "void rollbackTxn(String[] cacheNames)",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Rollback cache transaction for the specified <code>cacheName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = "<br/>NamedCacheAPI.rollbackTxn($1Cache.A.B.C$1);<br/><br/>"
    )
    public static void rollbackTxn(String[] cacheNames) {
       NamedCacheDelegate.rollbackTxn(cacheNames);
    }
    @com.tibco.be.model.functions.BEFunction(
        name = "commitTxn",
        synopsis = "Commit cache transaction for the specified array of <code>cacheNames</code>",
        signature = "void commitTxn(TransactionMap txnmap)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheNames", type = "String[]", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Commit cache transaction for the specified array of <code>cacheNames</code><br/> and retry <code>cRetry</code> times.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = "<br/>NamedCacheAPI.commitTxn($1Cache.A.B.C$1,3);<br/><br/>"
    )
    public static void commitTxn(String[] cacheNames,int cRetry) {
       NamedCacheDelegate.commitTxn(cacheNames, cRetry);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "lock",
        synopsis = "Lock the cache specified by the <code>cacheName</code><br/> for <code>waitMillis</code> milliseconds.",
        signature = "boolean lock(NamedCache cache,long waitMillis)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Lock the cache specified by the <code>cacheName</code><br/> for <code>waitMillis</code> milliseconds.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = "<br/>NamedCacheAPI.lock($1Cache.A.B.C$1,1000);<br/><br/>"
    )
    public static boolean lock(String cacheName, long waitMillis) {
       return NamedCacheDelegate.lock(cacheName, waitMillis);
    }
    @com.tibco.be.model.functions.BEFunction(
        name = "unlock",
        synopsis = "Unlock the cache specified by the <code>cacheName</code>",
        signature = "boolean unlock(NamedCache cache)",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Unlock the cache specified by the <code>cacheName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = "<br/>NamedCacheAPI.unlock($1Cache.A.B.C$1);<br/><br/>"
    )
    public static boolean unlock(String cacheName) {
        return NamedCacheDelegate.unlock(cacheName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "put",
        synopsis = "Put <code>key</code>-<code>value</code> pair in the specified <code>cacheName</code>",
        signature = "void putKeyValue(NamedCache cache,Object key,Object value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "(Cache to put object in)"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "Object", desc = "(key )")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Put <code>key</code>-<code>value</code> pair in the specified <code>cacheName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = "<br/>NamedCacheAPI.put($1Cache.A.B.C$1,(Object)key,(Object)value);<br/><br/>"
    )
    public static void put(String cacheName, Object key, Object value) {
        NamedCacheDelegate.put(cacheName, key, value);
    }
    @com.tibco.be.model.functions.BEFunction(
        name = "get",
        synopsis = "Return the cached value for the given <code>key</code> from the specified <code>cacheName</code>",
        signature = "Object getValue(NamedCache cache,Object key)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = ""),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "Object", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the cached value for the given <code>key</code> from the specified <code>cacheName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>Object x = (Object) NamedCacheAPI.get($1Cache.A.B.C$1,(Object)key);<br/><br/>"
    )
    public static Object get(String cacheName, Object key) {
        return NamedCacheDelegate.get(cacheName, key);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "clear",
        synopsis = "Clear objects from the specified <code>cacheName</code>",
        signature = "void clear(NamedCache cache)",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Clear objects from the specified <code>cacheName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = "<br/>NamedCacheAPI.clear($1Cache.A.B.C$1);<br/><br/>"
    )
    public static void clear(String cacheName) {
         NamedCacheDelegate.clear(cacheName);
    }
    @com.tibco.be.model.functions.BEFunction(
        name = "size",
        synopsis = "Return number of key-value mapping from the given <code>cacheName</code>",
        signature = "int size(NamedCache cache)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return number of key-value mapping from the given <code>cacheName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>NamedCacheAPI.size($1Cache.A.B.C$1);<br/><br/>"
    )
    public static int size(String cacheName) {
        return NamedCacheDelegate.size(cacheName);
    }
    @com.tibco.be.model.functions.BEFunction(
        name = "isEmpty",
        synopsis = "Returns true if this cache specified by the <code>cacheName</code><br/> contains no key-value mappings.",
        signature = "boolean isEmpty(NamedCache cache)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns true if this cache specified by the <code>cacheName</code><br/> contains no key-value mappings.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>NamedCacheAPI.isEmpty($1Cache.A.B.C$1);<br/><br/>"
    )
    public static boolean isEmpty(String cacheName) {
        return NamedCacheDelegate.isEmpty(cacheName);
    }
    @com.tibco.be.model.functions.BEFunction(
        name = "containsKey",
        synopsis = "Returns true if this cache specified by the <code>cacheName</code><br/> contains a mapping for the specified <code>key</code>.",
        signature = "boolean containsKey(NamedCache cache,Object key)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns true if this cache specified by the <code>cacheName</code><br/> contains a mapping for the specified <code>key</code>.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>NamedCacheAPI.containsKey($1Cache.A.B.C$1,(Object)key);<br/><br/>"
    )
    public static boolean containsKey(String cacheName,Object key) {
        return NamedCacheDelegate.containsKey(cacheName, key);
    }
    @com.tibco.be.model.functions.BEFunction(
        name = "containsValue",
        synopsis = "Returns true if this cache specified by the <code>cacheName</code><br/> contains a mapping for the specified <code>value</code>.",
        signature = "boolean containsValue(String cacheName,Object value)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = ""),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns true if this cache specified by the <code>cacheName</code><br/> contains a mapping for the specified <code>value</code>.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>NamedCacheAPI.containsValue($1Cache.A.B.C$1,(Object)value);<br/><br/>"
    )
    public static boolean containsValue(String cacheName,Object value) {
        return NamedCacheDelegate.containsValue(cacheName, value);
    }
    @com.tibco.be.model.functions.BEFunction(
        name = "keySet",
        synopsis = "Returns a array of the key object contained in this cache specified by the <code>cacheName</code>",
        signature = "Set keySet(NamedCache cache)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a array of the key object contained in this cache specified by the <code>cacheName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = "<br/>Object[] x = (Object[]) NamedCacheAPI.keySet($1Cache.A.B.C$1);<br/><br/>"
    )
    public static Object[] keySet(String cacheName) {
        return NamedCacheDelegate.keySet(cacheName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "values",
        synopsis = "Returns a array of the value object contained in this cache specified by the <code>cacheName</code>",
        signature = "Collection values(NamedCache cache)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a array of the value object contained in this cache specified by the <code>cacheName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>Object[] x = (Object[]) NamedCacheAPI.values($1Cache.A.B.C$1);<br/><br/>"
    )
    public static Object[] values(String cacheName) {
       return NamedCacheDelegate.values(cacheName);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getCacheConfig",
        synopsis = "Returns the cache configuration xml for the specified <code>cacheName</code>",
        signature = "String getCacheConfig(String cacheName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the cache configuration xml for the specified <code>cacheName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>String xmlStr = NamedCacheAPI.getCacheConfig($1Cache.A.B.C$1);<br/><br/>"
    )
    public static String getCacheConfig(String cacheName) {
        return NamedCacheDelegate.getCacheConfig(cacheName);
    }
    @com.tibco.be.model.functions.BEFunction(
        name = "getCoherenceConfigXml",
        synopsis = "Returns the coherence configuration xml",
        signature = "String getCoherenceConfigXml()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the coherence configuration xml",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>String xmlStr = NamedCacheAPI.getCoherenceConfigXml();<br/><br/>"
    )
    public static String getCoherenceConfigXml() {
        return NamedCacheDelegate.getCoherenceConfigXml();
    }
    @com.tibco.be.model.functions.BEFunction(
        name = "getClusterConfig",
        synopsis = "Returns the coherence Cluster configuration xml",
        signature = "String getClusterConfig()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the coherence Cluster configuration xml",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>String xmlStr = NamedCacheAPI.getClusterConfig();<br/><br/>"
    )
    public static String getClusterConfig() {
        return NamedCacheDelegate.getClusterConfig();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getAsEntity",
        synopsis = "Return the cached value for the given <code>key</code> from the specified <code>cacheName</code>",
        signature = "Entity getValue(NamedCache cache,Object key)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = ""),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "Object", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Entity", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the cached value for the given <code>key</code> from the specified <code>cacheName</code>",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY},
        example = "<br/>CustomerConcept c = (CustomerConcept) NamedCacheAPI.getAsEntity($1Cache.A.B.C$1,(Object)key);<br/><br/>"
    )
    public static Entity getAsEntity(String cacheName, Object key) {
        return NamedCacheDelegate.getAsEntity(cacheName, key);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "remove",
        synopsis = "Remove the cached value for the given <code>key</code><br/> from the specified <code>cacheName</code>, and return true if successful else return false",
        signature = "boolean remove(String cacheName, Object key)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cacheName", type = "String", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Remove the cached value for the given <code>key</code><br/> from the specified <code>cacheName</code>, and return true if successful else return false",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = "<br/>boolean removed = NamedCacheAPI.remove($1Cache.A.B.C$1,(Object)key);<br/><br/>"
    )
    public static boolean remove(String cacheName, Object key) {
        return NamedCacheDelegate.remove(cacheName, key);
    }


}
