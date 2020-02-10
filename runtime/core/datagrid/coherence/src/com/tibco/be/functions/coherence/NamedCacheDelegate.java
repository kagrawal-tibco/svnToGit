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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.CacheService;
import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.net.DefaultConfigurableCacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.run.xml.SimpleDocument;
import com.tangosol.run.xml.XmlDocument;
import com.tangosol.run.xml.XmlElement;
import com.tangosol.run.xml.XmlHelper;
import com.tangosol.util.TransactionMap;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

public class NamedCacheDelegate {
    private static XmlDocument CACHE_CONFIG;
    private static Map cacheMap = Collections.synchronizedMap(new HashMap());
    public static String CACHE_API_LOCK="CACHE_API_LOCK";
    public static boolean serializationEnabled = false;
    public static Class serializationClass;
    public static boolean classNameIsCacheName = false;

    static class CacheTxnMap {
        String m_name;
        NamedCache m_cache;
        List m_txnq = Collections.synchronizedList(new LinkedList());
        boolean pojo;
        Constructor constructor;
        Class serializer;

        public Constructor getConstructor() {
            return constructor;
        }

        public void setConstructor(Constructor constructor) {
            this.constructor = constructor;
        }

        public boolean isPojo() {
            return pojo;
        }

        public void setPojo(boolean pojo) {
            this.pojo = pojo;
        }

        public CacheTxnMap(String name,NamedCache cache) {
            m_name = name;
            m_cache = cache;
        }
        public void addTxn(TransactionMap txmap) throws InterruptedException {
            m_txnq.add(txmap);
        }
        public NamedCache getCache() {
            if(m_txnq.size() > 0) {
                return (NamedCache) m_txnq.get(m_txnq.size()-1);
            } else {
                return m_cache;
            }
        }
        public Collection getTxns() throws Exception {
            if(m_txnq != null && m_txnq.size() > 0)
                return m_txnq;
            else
                throw new RuntimeException("Transactions have not been created on cache :"+m_name);
        }

        public void clearTxns() {
            m_txnq.clear();
        }
    }

    private static void initInternal() throws Exception {
        RuleSession m_session = RuleSessionManager.getCurrentRuleSession();
        BEProperties properties = (BEProperties) m_session.getRuleServiceProvider().getProperties();
        if (properties != null) {
            serializationEnabled = properties.getBoolean("TIBCO.BE.namedcache.serialization.enable", false);
            String sclass = properties.getProperty("TIBCO.BE.namedcache.serialization.class");
            if (sclass != null) {
                serializationClass = Class.forName(sclass);
            }
            classNameIsCacheName = properties.getBoolean("TIBCO.BE.namedcache.classnameiscachename",false);
        }
    }

    private static String getClassName(String cacheName) {
        final Logger logger = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider()
                .getLogger(NamedCacheDelegate.class);
        String ontlogypath = cacheName;
        TypeManager tm = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getTypeManager();
        String clazzName = ModelNameUtil.modelPathToGeneratedClassName(ontlogypath);
        TypeManager.TypeDescriptor td = tm.getTypeDescriptor(clazzName);
        if (td == null) {
            logger.log(Level.WARN, "Class not found for %s", ontlogypath);
            return cacheName;
        } else
            return clazzName;
    }

    private static CacheTxnMap getTxnMap(String cacheName) {
        return (CacheTxnMap) cacheMap.get(cacheName);
    }

   
    public static void initFromCachedOM() {
        String strXml = null;
        String m_strURI;
        byte[] data;
        XmlDocument xmlDoc = null;
        ConfigurableCacheFactory cfactory;
        Properties config;
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;

        try {
            redirectStdErrAndStdOut();
            config = RuleSessionManager.getCurrentRuleSession().getConfig().getCacheConfig();
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            RuleServiceProvider rsp = session.getRuleServiceProvider();
            initInternal();

            String uri = config.getProperty("cacheConfigFilePath");
            // Check for File Path
            if (null != uri && uri.length() > 0) {
                m_strURI = rsp.getGlobalVariables().substituteVariables(uri).toString();
                File file = new File(m_strURI);
                if (file.exists()) {
                    FileInputStream fis = new FileInputStream(file);
                    DataInputStream dis = new DataInputStream(fis);
                    data = new byte[dis.available()];
                    dis.readFully(data);
                    strXml = new String(data);
                } else
                    throw new Exception("DefaultDistributedCacheBasedStore:Cache config file not found :" + m_strURI);
            } else if (uri == null) { // look for Internal shared resource
                uri = config.getProperty("cacheConfigResourceUri");
                if (null != uri && uri.length() > 0) {
                    m_strURI = rsp.getGlobalVariables().substituteVariables(uri).toString();
                    data = rsp.getProject().getSharedArchiveResourceProvider().getResourceAsByteArray(m_strURI);
                    if (data.length > 0)
                        strXml = new String(data);
                }
            }
            if (strXml != null && !strXml.equals("")) {
                if(CACHE_CONFIG == null)
                    CACHE_CONFIG = XmlHelper.loadXml(strXml);
            }
            if (CACHE_CONFIG != null) {
                cfactory = new DefaultConfigurableCacheFactory(CACHE_CONFIG);
                CacheFactory.setConfigurableCacheFactory(cfactory);
            } else {
                // use the default configuration from the the coherence.jar
                cfactory = new DefaultConfigurableCacheFactory();
                CacheFactory.setConfigurableCacheFactory(cfactory);
            }

        } catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }
        finally {
            System.setOut(stdout);
            System.setErr(stderr);

        }

    }

    public static void init(String strURL) {
        URL url;
        ConfigurableCacheFactory cfactory;
        XmlDocument xmlDoc;
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;

        BEProperties beProperties;
        try {
            redirectStdErrAndStdOut();
            initInternal();
            if (strURL.startsWith("mem://")) {
                String resourcePath = strURL.substring("mem://".length());
                RuleServiceProvider rsp = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
                ArchiveResourceProvider sar = rsp.getProject().getSharedArchiveResourceProvider();
                byte[] data = sar.getResourceAsByteArray(resourcePath);
                String strXml = new String(data);
                if(CACHE_CONFIG == null)
                    CACHE_CONFIG = XmlHelper.loadXml(strXml);
                cfactory = new DefaultConfigurableCacheFactory(CACHE_CONFIG);
                CacheFactory.setConfigurableCacheFactory(cfactory);
            } else {
                url = new URL(strURL);
                if(CACHE_CONFIG == null)
                    CACHE_CONFIG = XmlHelper.loadXml(url.openStream());
                cfactory = new DefaultConfigurableCacheFactory(CACHE_CONFIG);
                CacheFactory.setConfigurableCacheFactory(cfactory);
            }
        } catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            System.setOut(stdout);
            System.setErr(stderr);
            System.out.println("Initialized Cache Factory successfully using "+strURL);
        }
    }

    private static void redirectStdErrAndStdOut() {
        ByteArrayOutputStream os = new ByteArrayOutputStream(8192);
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        System.setErr(ps);
    }


    public static Object getNamedCache(String cacheName, String classType) {
        NamedCache cache;
        cache = getCache(cacheName);

        if (classType == null || classType.length() == 0) return cache;

        CacheTxnMap txnmap = (CacheTxnMap) cacheMap.get(cacheName);
        if (txnmap == null) txnmap = new CacheTxnMap(cacheName, cache);
        cacheMap.put(cacheName, txnmap);

        try {
            TypeManager.TypeDescriptor typeDescriptor = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getTypeManager().getTypeDescriptor(classType);
            Class clazz = typeDescriptor.getImplClass();
            if(Externalizable.class.isAssignableFrom(clazz)) {
                txnmap.setConstructor(clazz.getConstructor(new Class[0]));
                txnmap.setPojo(true);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return cache;
    }

    private static NamedCache getCache(String cacheName) {
        NamedCache cache = null;
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        ByteArrayOutputStream os = new ByteArrayOutputStream(8192);
        PrintStream ps = new PrintStream(os);
        try {

            System.setOut(ps);
            System.setErr(ps);
            if (cacheMap.containsKey(cacheName)) {
                CacheTxnMap txnmap = (CacheTxnMap) cacheMap.get(cacheName);
                return txnmap.getCache();
            } else {
                cache = CacheFactory.getCache(cacheName,RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader());
            }

        } finally {
            System.setOut(stdout);
            System.setErr(stderr);
            os = null;
            ps = null;
        }
        return cache;
    }

    public static void releaseCache(String cacheName) {
        try {
            NamedCache cache = (NamedCache) getCache(cacheName);
            cache.release();
        } catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }


    public static void startTxn(String cacheName) {
        TransactionMap txnmap = null;
        try {
            NamedCache cache = (NamedCache) getCache(cacheName);
            if (cache != null) {
                CacheTxnMap ctxnmap = (CacheTxnMap) cacheMap.get(cacheName);
                txnmap = CacheFactory.getLocalTransaction(cache);
                txnmap.setConcurrency(TransactionMap.CONCUR_PESSIMISTIC);
                txnmap.setTransactionIsolation(TransactionMap.TRANSACTION_SERIALIZABLE);
                txnmap.begin();
                ctxnmap.addTxn(txnmap);
            }
        } catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }
        return;
    }

    public static void rollbackTxn(String[] cacheNames) {
        Collection txnlist = new ArrayList();
        try {
            for (int i = 0; i < cacheNames.length; i++) {
                CacheTxnMap ctxnmap = (CacheTxnMap) cacheMap.get(cacheNames[i]);
                if (ctxnmap != null && ctxnmap.getTxns() != null) {
                    try {
                        txnlist.addAll(ctxnmap.getTxns());

                    } finally {
                        ctxnmap.clearTxns();
                    }
                }
            }
            CacheFactory.rollbackTransactionCollection(txnlist);
        } catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public static void commitTxn(String[] cacheNames,int cRetry) {
        Collection  txnlist = new ArrayList();
        try {
            for (int i = 0; i < cacheNames.length; i++) {
                CacheTxnMap ctxnmap = (CacheTxnMap) cacheMap.get(cacheNames[i]);
                if (ctxnmap != null && ctxnmap.getTxns() != null) {
                    try {
                        Iterator it = ctxnmap.getTxns().iterator();
                        while(it.hasNext()){
                            ((TransactionMap) it.next()).prepare();
                        }
                        txnlist.addAll(ctxnmap.getTxns());

                    } finally {
                        ctxnmap.clearTxns();
                    }

                }
            }
            CacheFactory.commitTransactionCollection(txnlist, cRetry);
        } catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }


    public static boolean lock(String cacheName, long waitMillis) {
        try {
            NamedCache cache = (NamedCache) getCache(cacheName);
            return cache.lock(NamedCacheDelegate.CACHE_API_LOCK, waitMillis);
        } catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public static boolean unlock(String cacheName) {
        try {
            NamedCache cache = (NamedCache) getCache(cacheName);
            return cache.unlock(NamedCacheDelegate.CACHE_API_LOCK);
        } catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    
    public static void put(String cacheName, Object key, Object value) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader beClassLoader = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader();
            Thread.currentThread().setContextClassLoader(beClassLoader);
            NamedCache cache = (NamedCache) getCache(cacheName);
            CacheTxnMap txnmap = getTxnMap(cache.getCacheName());

            if(txnmap.isPojo()) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(bos);
                if (Externalizable.class.isAssignableFrom(value.getClass())) {
                    ((Externalizable) value).writeExternal(os);
                    os.close();
                    value = bos.toByteArray();
                }
            }


            HashMap h = new HashMap();
            h.put(key, value);
            cache.putAll(h);
        } catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }
        finally {
            Thread.currentThread().setContextClassLoader(loader);
        }
    }

    public static Object get(String cacheName, Object key) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader beClassLoader = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader();
            Thread.currentThread().setContextClassLoader(beClassLoader);
            NamedCache cache = (NamedCache) getCache(cacheName);
            CacheTxnMap txnmap = getTxnMap(cache.getCacheName());
            Object obj = cache.get(key); if(obj == null) return null;

            if (txnmap.isPojo() && (txnmap.getConstructor() != null)) {
                ByteArrayInputStream bis = new ByteArrayInputStream((byte[]) obj);
                ObjectInputStream is = new ObjectInputStream(bis);
                Object instance = txnmap.getConstructor().newInstance(new Object[0]);
                if (Externalizable.class.isAssignableFrom(instance.getClass()))
                    ((Externalizable) instance).readExternal(is);
                return instance;
            }

            return obj;
        } catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }
        finally {
            Thread.currentThread().setContextClassLoader(loader);
        }
    }


    public static void clear(String cacheName) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader beClassLoader = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader();
            Thread.currentThread().setContextClassLoader(beClassLoader);
            NamedCache cache = (NamedCache) getCache(cacheName);
            cache.clear();
        }  catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }  finally {
          Thread.currentThread().setContextClassLoader(loader);
        }
    }

    public static int size(String cacheName) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader beClassLoader = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader();
            Thread.currentThread().setContextClassLoader(beClassLoader);
            NamedCache cache = (NamedCache) getCache(cacheName);
            return cache.size();
        }  catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }  finally {
          Thread.currentThread().setContextClassLoader(loader);
        }
    }

    public static boolean isEmpty(String cacheName) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader beClassLoader = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader();
            Thread.currentThread().setContextClassLoader(beClassLoader);
            NamedCache cache = (NamedCache) getCache(cacheName);
            return cache.isEmpty();
        }  catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }  finally {
          Thread.currentThread().setContextClassLoader(loader);
        }
    }

    public static boolean containsKey(String cacheName,Object key) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader beClassLoader = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader();
            Thread.currentThread().setContextClassLoader(beClassLoader);
            NamedCache cache = (NamedCache) getCache(cacheName);
            return cache.containsKey(key);
        }  catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            Thread.currentThread().setContextClassLoader(loader);
        }
    }

    public static boolean containsValue(String cacheName,Object value) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader beClassLoader = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader();
            Thread.currentThread().setContextClassLoader(beClassLoader);
            NamedCache cache = (NamedCache) getCache(cacheName);
            return cache.containsValue(value);
        }  catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            Thread.currentThread().setContextClassLoader(loader);
        }
    }

    public static Object[] keySet(String cacheName) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader beClassLoader = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader();
            Thread.currentThread().setContextClassLoader(beClassLoader);
            NamedCache cache = (NamedCache) getCache(cacheName);
            return cache.keySet().toArray(new Object[cache.size()]);
        }  catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            Thread.currentThread().setContextClassLoader(loader);
        }
    }


    public static Object[] values(String cacheName) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader beClassLoader = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader();
            Thread.currentThread().setContextClassLoader(beClassLoader);
            NamedCache cache = (NamedCache) getCache(cacheName);
            return cache.values().toArray(new Object[cache.size()]);
        }  catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            Thread.currentThread().setContextClassLoader(loader);
        }
    }


    public static String getCacheConfig(String cacheName) {
        try {

            NamedCache cache = (NamedCache) getCache(cacheName);
            //System.out.println("Cache:"+cache.toString());
            CacheService cs = cache.getCacheService();
            //System.out.println("CACHESERVICE:"+cs.toString());
            //System.out.println("BMM:"+cs.getBackingMapManager().getContext().getConfig());
            XmlDocument doc = new SimpleDocument();
            doc.setName("Cache");
            doc.addAttribute("Name").setString(cache.getCacheName());
            doc.addAttribute("Class").setString(cache.getClass().getName());
            doc.addAttribute("ClassLoader").setString(cache.getClass().getClassLoader().toString());
            doc.addAttribute("ServiceName").setString(cache.getCacheService().getInfo().getServiceName());
            XmlElement xservice = doc.addElement("Service");
            CacheService service = cache.getCacheService();
            xservice.addAttribute("Name").setString(service.getInfo().getServiceName());
            xservice.addAttribute("isRunning").setBoolean(service.isRunning());
            xservice.addAttribute("Id").setInt(cache.getCacheService().getCluster().getLocalMember().getId());
            xservice.addAttribute("Version").setString(service.getInfo().getServiceVersion(cache.getCacheService().getCluster().getLocalMember()));
            xservice.addAttribute("OldestMemberId").setInt(cache.getCacheService().getCluster().getOldestMember().getId());
            xservice.addAttribute("Class").setString(service.getClass().getName());
            xservice.addAttribute("ClassLoader").setString(service.getClass().getClassLoader().toString());
            XmlHelper.overrideElement(xservice.addElement(service.getInfo().getServiceName())
                    ,CacheFactory.getServiceConfig(cache.getCacheService().getInfo().getServiceName()));
            //System.out.println(doc.toString());
            return doc.toString();
        } catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public static String getCoherenceConfigXml() {
        URL url;
        try {
            if (CACHE_CONFIG != null)
                return CACHE_CONFIG.toString();
            else {
                String config = System.getProperty("tangosol.coherence.cacheconfig");
                if (config != null)
                    url = Thread.currentThread().getContextClassLoader().getResource(config);
                else
                    url = Thread.currentThread().getContextClassLoader().getResource("coherence-cache-config.xml");
                if (url != null) {
                    byte[] data;
                    DataInputStream dis = new DataInputStream(url.openStream());
                    data = new byte[dis.available()];
                    dis.readFully(data);
                    return new String(data);
                } else {
                    return new String("");
                }

            }
        } catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public static String getClusterConfig() {
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        ByteArrayOutputStream os = new ByteArrayOutputStream(8192);
        PrintStream ps = new PrintStream(os);
        try {

            System.setOut(ps);
            System.setErr(ps);
            return CacheFactory.getClusterConfig().toString();
        } finally {
            System.setOut(stdout);
            System.setErr(stderr);
            os = null;
            ps = null;
        }

    }


    public static Entity getAsEntity(String cacheName, Object key) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader beClassLoader = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader();
            Thread.currentThread().setContextClassLoader(beClassLoader);
            NamedCache cache = (NamedCache) getCache(cacheName);
            return (Entity)cache.get(key);
        } catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            Thread.currentThread().setContextClassLoader(loader);
        }
    }


    public static boolean remove(String cacheName, Object key) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader beClassLoader = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getClassLoader();
            Thread.currentThread().setContextClassLoader(beClassLoader);
            NamedCache cache = (NamedCache) getCache(cacheName);
            Set s = cache.keySet();
            return s.remove(key);
        } catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            Thread.currentThread().setContextClassLoader(loader);
        }
    }


}
