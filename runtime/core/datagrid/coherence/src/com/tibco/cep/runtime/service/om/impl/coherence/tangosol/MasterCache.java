/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 11/8/2010
 */

package com.tibco.cep.runtime.service.om.impl.coherence.tangosol;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.CacheService;
import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.net.DefaultCacheServer;
import com.tangosol.net.DefaultConfigurableCacheFactory;
import com.tangosol.net.Member;
import com.tangosol.net.MemberListener;
import com.tangosol.net.NamedCache;
import com.tangosol.util.ConcurrentMap;
import com.tangosol.util.Filter;
import com.tangosol.util.MapListener;
import com.tangosol.util.TransactionMap;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.ft.FTNodeManager;


/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Sep 29, 2006
 * Time: 5:36:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class MasterCache {
    private static String BRK = "\r\n";
    private static String INDENT = "\t\t\t\t\t\t\t";
    public static final String READ_LOCK = new String("READ-LOCK");
    public static final String WRITE_LOCK = new String("WRITE-LOCK");
    private CacheService m_service;
    private NamedCache m_cache;
    private static HashMap masterCaches = new HashMap();
    private TransactionMap txnmap;
    private String m_name;
    private boolean fLite;
    private Filter filter = null;
    private MapListener mapListener;
    private MemberListener memberListener;
    private static Logger m_logger;
//    private FTNode m_currentNode;
//    private Member m_currentMember;

    //private static Logger m_logManager;

    private MasterCache(String config, String name) throws Exception {

        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        ByteArrayOutputStream os = new ByteArrayOutputStream(8192);
        boolean bConfigInit = Boolean.valueOf(
                System.getProperty("be.ft.config.init", "false"))
                .booleanValue();
        boolean bCacheDump = Boolean.valueOf(
                System.getProperty("be.cache.dump", "false"))
                .booleanValue();
        try {
            PrintStream ps = new PrintStream(os);
            if (!bCacheDump) {
                System.setOut(ps);
                System.setErr(ps);
            }
            if (bConfigInit) {
                if (config != null) {
                    ConfigurableCacheFactory cfactory = null;
                    cfactory = new DefaultConfigurableCacheFactory(config,
                            getClass().getClassLoader());
                    CacheFactory.setConfigurableCacheFactory(cfactory);
                }
                m_cache = CacheFactory.getCache(name, getClass()
                        .getClassLoader());
            } else {
                DefaultCacheServer.startDaemon();
                m_cache = CacheFactory.getCache("$FT" + name, Thread.currentThread().getContextClassLoader());
                m_service = m_cache.getCacheService();

//                Member member = m_service.getCluster().getLocalMember();
//                FTNode node = new FTNodeImpl(GUIDGenerator.getGUID(),
//                        member.getId(),
//                        m_service.getCluster().getClusterName(),
//                        member.getUid().toString(),
//                        (short) 0,
//                        member.getTimestamp(),
//                        member.getMachineId(),
//                        member.getAddress(),
//                        member.getPort(),
//                        "",
//                        member.getProcessName(),
//                        member.getRackName(),
//                        member.getRoleName(),
//                        member.getSiteName());
//
//                this.m_currentNode = node;
//                this.m_currentMember = m_service.getCluster().getLocalMember();
            }
            m_name = name;

        } finally {
            if (!bCacheDump) {
                System.setOut(stdout);
                System.setErr(stderr);
            }
        }
    }

    private NamedCache getCache() {

        synchronized (m_cache) {
            if (isInitialized()) {
                return m_cache;
            }
        }
        return null;
    }

    private CacheService getService() {

        synchronized (m_service) {
            if (isInitialized()) {
                return m_service;
            }
        }
        return null;
    }


    private boolean isInitialized() {
        synchronized (m_service) {
            synchronized (m_cache) {
                if (this.m_service == null || this.m_cache == null) {
                    return false;
                } else {
                    if (!m_service.isRunning()) { // check if the service is running
                        return false;
                    } else {
                        if (!m_cache.isActive()) { // check if the cache is active
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
    }

    public String getName() {
        return m_name;
    }

    public static MasterCache getMasterCache(Object caller,
                                             Properties cacheProperties, String name) throws Exception {
        String tangosolConfig = null;
        String cacheName = null;
        BEProperties props = (BEProperties) cacheProperties;

        if (caller instanceof FTNodeManager) {
            // this is called in a FT mode
            cacheName = props.getString("be.ft.cluster.name", name);
            // be.ft.cluster.config needs to be specified in the BEProperties.
            tangosolConfig = props.getString("be.ft.cluster.config");
            m_logger = ((FTNodeManager) caller).getLogger();
        }
//        else if (caller instanceof CacheBasedIdGenerator) {
//            cacheName = props
//                    .getString(
//                            "com.tibco.cep.kernel.service.idgenerator.cache.name",
//                            name);
//            tangosolConfig = props
//                    .getString("com.tibco.cep.kernel.service.idgenerator.cache.config");
//            if (cacheName == null) {
//                cacheName = props.getProperty("be.ft.cluster.name", name);
//                tangosolConfig = props.getProperty("be.ft.cluster.config");
//
//            }
//            m_logManager = ((CacheBasedIdGenerator) caller).getLogger();
//
//        }

        if (cacheName == null)
            throw new RuntimeException(
                    "Cluster name not specified in a FT configuration");
        MasterCache mcache = (MasterCache) masterCaches.get(cacheName);
        if (mcache == null) {
            mcache = new MasterCache(tangosolConfig, cacheName);
            masterCaches.put(cacheName, mcache);
        }
        return mcache;
    }

    public void release() {
        getCache().release();
    }

    private ConcurrentMap getMap() {
        return txnmap == null ? getCache() : (ConcurrentMap) txnmap;
    }

    public final void startTxn() {
        if (txnmap == null) {
            txnmap = CacheFactory.getLocalTransaction(getCache());
            txnmap.setConcurrency(TransactionMap.CONCUR_PESSIMISTIC);
            txnmap.setTransactionIsolation(TransactionMap.TRANSACTION_SERIALIZABLE);
            txnmap.begin();
        }
    }

    public final void rollbackTxn() {
        if (txnmap != null) {
            txnmap.rollback();
            txnmap = null;
        }
    }

    public final void commitTxn() {
        if (txnmap != null) {
            txnmap.prepare();
            txnmap.commit();
            txnmap = null;
        }
    }

// ----- concurrency ----------------------------------------------------

    /**
     * Lock the object in the cluster, and wait until the lock is available.
     * <p/>
     * This is the cluster equivalent to the MONITORENTER Java opcode that
     * results from the Java synchronized keyword.
     * <p/>
     * The non-clustered code would look like:
     * <pre><code>
     *   synchronized (Singleton.INSTANCE) {
     *     ...
     *   }
     * </code></pre>
     * <p/>
     * The clustered equivalent would look like:
     * <pre><code>
     *   Singleton.INSTANCE.lock();
     *   try {
     *     ...
     *   } finally {
     *     Singleton.INSTANCE.unlock();
     *   }
     * </code></pre>
     */
    public final void lock(String _lockID) {
        getCache().lock(_lockID, -1);
    }

    /**
     * Unlock the object in the cluster.
     * <p/>
     * This is the cluster equivalent to the MONITOREXIT Java opcode that
     * results from the Java synchronized keyword.
     */
    public final void unlock(String _lockID) {
        if (getCache() != null) {
            getCache().unlock(_lockID);
        }
    }

    public final void put(Object key, Object obj) {
        getMap().put(key, obj);
    }

    public final Object get(Object key) {
        return getMap().get(key);
    }

    public int getNodeCacheId() {
        return getCache().getCacheService().getCluster().getLocalMember().getId();
    }

    public String getNodeCacheUid() {
        return getCache().getCacheService().getCluster().getLocalMember().getUid().toString();
    }

    public boolean isAlive(String uid) {
        Set memberset = getCache().getCacheService().getCluster().getMemberSet();
        Iterator iter = memberset.iterator();
        while (iter.hasNext()) {
            Member m = (Member) iter.next();
            if (m.getUid().toString().equals(uid))
                return true;
        }
        return false;
    }

    public void addMemberListener(MemberListener lsnr) {
        this.memberListener = lsnr;
        getCache().getCacheService().addMemberListener(lsnr);
    }

    public void addMapListener(MapListener lsnr, Filter filter, boolean fLite) {
        this.mapListener = lsnr;
        this.filter = filter;
        this.fLite = fLite;
        getCache().addMapListener(lsnr, filter, fLite);
    }

    public long getClusterTime() {
        return getCache().getCacheService().getCluster().getTimeMillis();
    }

    public long getTimeNodeCreated() {
        return getCache().getCacheService().getCluster().getLocalMember().getTimestamp();
    }

    public int getMachineId() {
        return getCache().getCacheService().getCluster().getLocalMember().getMachineId();
    }

    public InetAddress getAddress() {
        return getCache().getCacheService().getCluster().getLocalMember().getAddress();
    }

    public int getPort() {
        return getCache().getCacheService().getCluster().getLocalMember().getPort();
    }

    public String getClusterName() {
        return getCache().getCacheService().getCluster().getClusterName();
    }

    public String[] getClusterInfo() {
        String addressline = null;
        String clusterinfo = getCache().getCacheService().getCluster().toString();
        String[] infolines = clusterinfo.split("\n");
//    	for (int i = 0; i < infolines.length; i++) {
//			if(infolines[i].startsWith("Group")) {
//				addressline = infolines[i];
//				break;
//			}
//		}
//    	addressline = addressline.substring("Group{".length(),addressline.length()-1);
//    	infolines = addressline.split(",");
//    	Map m = new HashMap();
//    	for (int i = 0; i < infolines.length; i++) {
//			String [] kv = infolines[i].split("=");
//			m.put(kv[0].trim(), kv[1].trim());
//		}
        return infolines;
    }

    Iterator getMemberIterator() {
        return getCache().getCacheService().getCluster().getMemberSet().iterator();
    }

    Member getLocalMember() {
        return getCache().getCacheService().getCluster().getLocalMember();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(BRK + INDENT);
        sb.append(" Fault Tolerant Cluster name=" + m_name).append(BRK).append(INDENT);
        sb.append(" NodeId=" + Integer.toString(getNodeCacheId())).append(BRK).append(INDENT);
        sb.append(" NodeUID=" + getNodeCacheUid()).append(BRK).append(INDENT);
        sb.append(" MachineId=" + Integer.toString(getMachineId())).append(BRK).append(INDENT);
        sb.append(" Hostname" + getAddress().getCanonicalHostName()).append(BRK).append(INDENT);
        sb.append(" Local Port=" + Integer.toString(getPort())).append(BRK).append(INDENT);
        sb.append(" TimeJoined=" + new Date(getTimeNodeCreated())).append(BRK).append(INDENT);
        if (getClusterName() != null && getClusterName().length() > 0) {
            sb.append(" Cache Cluster Name=" + getClusterName()).append(BRK).append(INDENT);
        } else {
            sb.append(" Cache Cluster Name=<Not specified using property \"tangosol.coherence.cluster\">").append(BRK).append(INDENT);
        }
        String[] clusterInfo = getClusterInfo();
        for (int i = 0; i < clusterInfo.length; i++) {
            if (clusterInfo[i].length() > 0) {
                sb.append(clusterInfo[i]).append(BRK).append(INDENT);
            }
        }
//    	sb.append(" Cluster Address="+ getClusterInfo().get("Address")).append(BRK).append(INDENT);
//    	sb.append(" Cluster Port="+ getClusterInfo().get("Port")).append(BRK).append(INDENT);
//    	sb.append(" Cluster TTL="+ getClusterInfo().get("TTL")).append(BRK).append(INDENT);

        return sb.toString();

//        return "Name=" + m_name + " NodeId=" + Integer.toString(getNodeId()) + " NodeUID=" + getNodeUid() +
//               " MachineId=" + Integer.toString(getMachineId()) + " Address=" + getAddress().getCanonicalHostName() +
//               " Port=" + Integer.toString(getPort()) + " TimeJoined=" + new Date(getTimeNodeCreated());
    }

    public String getProcessName() {
        return getCache().getCacheService().getCluster().getLocalMember().getProcessName();
    }

    public String getRackName() {
        return getCache().getCacheService().getCluster().getLocalMember().getRackName();
    }

    public String getRoleName() {
        return getCache().getCacheService().getCluster().getLocalMember().getRoleName();
    }

    public String getSiteName() {
        return getCache().getCacheService().getCluster().getLocalMember().getSiteName();
    }
}
