/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 19/8/2010
 */

package com.tibco.cep.runtime.service.om.impl.coherence.cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 3, 2008
 * Time: 2:35:02 PM
 * To change this template use File | Settings | File Templates.
 */
class ExtendConfig {
    public static final String EXTEND_CONFIG_PREFIX = SystemProperty.CLUSTER_EXTEND.getPropertyName();
    private Map partnerMap = new HashMap<String, RemoteConfig>();
    private ProxyConfig proxyConfig;
    private boolean hasProxyConfig;
    private boolean cacheServer;
    private boolean storageEnabled;

    private ExtendConfig(boolean hasProxyConfig, boolean isCacheServer, boolean isStorageEnabled, BEProperties props) {
        this.hasProxyConfig = hasProxyConfig;
        if (hasProxyConfig) {
            this.proxyConfig = ProxyConfig.loadConfig(props.getBranch("proxy"));
        }
        this.cacheServer = isCacheServer;
        this.storageEnabled = isStorageEnabled;
        String[] partners = props.getString("partner", "").split(",");
        for (String partner : partners) {
            if (partner.length() > 0) {
                partnerMap.put(partner.trim(), RemoteConfig.loadRemoteScheme(partner.trim(), props));
            }
        }
    }

    public static ExtendConfig loadConfig(BEProperties props) {
        BEProperties extProps = props.getBranch(EXTEND_CONFIG_PREFIX);
        boolean hasProxyConfig = extProps.getBoolean("proxy.enabled", false);
        final boolean isCacheServer = props.getBoolean(SystemProperty.CACHE_SERVER.getPropertyName(), false);
        boolean isStorageEnabled = props.getBoolean("tangosol.coherence.distributed.localstorage", false);
        ExtendConfig instance = new ExtendConfig(hasProxyConfig, isCacheServer, isStorageEnabled, extProps);
        return instance;
    }

    public ProxyConfig getProxyConfig() {
        return proxyConfig;
    }

    public Map<String, RemoteConfig> getPartnerMap() {
        return partnerMap;
    }

    public RemoteConfig[] getRemoteConfigs() {
        return (RemoteConfig[]) partnerMap.values().toArray(new RemoteConfig[0]);
    }

    public String[] getRemoteClusterNames() {
        return (String[]) partnerMap.keySet().toArray(new String[0]);
    }

    static Node genLocalProxyScheme(Document doc, ProxyConfig pscheme) {
        Element proxy = doc.createElement("proxy-scheme");
        Element svcname = doc.createElement("service-name");
        svcname.setTextContent("tcp-extend-" + pscheme.getLocalClusterName());
        proxy.appendChild(svcname);
        Element threadCnt = doc.createElement("thread-count");
        //threadCnt.setAttribute("system-property","be.engine.extend.proxy.thread.count");
        threadCnt.setTextContent(String.valueOf(pscheme.getThreadCount()));
        proxy.appendChild(threadCnt);
        Element aconfig = doc.createElement("acceptor-config");
        proxy.appendChild(aconfig);
        Element tcpAcc = doc.createElement("tcp-acceptor");
        aconfig.appendChild(tcpAcc);
//        Element serializer = doc.createElement("serializer");
//        Element className = doc.createElement("class-name");
//        serializer.appendChild(className);
//        className.setTextContent("com.tibco.cep.runtime.service.om.impl.coherence.cluster.CacheSerializer");
//        aconfig.appendChild(serializer);
        Element lAddr = doc.createElement("local-address");
        tcpAcc.appendChild(lAddr);
        Element Addr = doc.createElement("address");
        //Addr.setAttribute("system-property","be.engine.extend.proxy.localhost");
        Addr.setTextContent(pscheme.getHost());
        lAddr.appendChild(Addr);
        Element Port = doc.createElement("port");
        //Port.setAttribute("system-property","be.engine.extend.proxy.localport");
        Port.setTextContent(String.valueOf(pscheme.getPort()));
        lAddr.appendChild(Port);

        if (pscheme.getRecvBufSize() != null && pscheme.getRecvBufSize().length() > 0) {
            Element rbfSize = doc.createElement("receive-buffer-size");
            //rbfSize.setAttribute("system-property","be.engine.extend.proxy.recvbuffersize");
            tcpAcc.appendChild(rbfSize);
        }

        if (pscheme.getSendBufSize() != null && pscheme.getSendBufSize().length() > 0) {
            Element sbfSize = doc.createElement("send-buffer-size");
            //sbfSize.setAttribute("system-property","be.engine.extend.proxy.sendbuffersize");
            tcpAcc.appendChild(sbfSize);
        }
        Element pconfig = doc.createElement("proxy-config");
        proxy.appendChild(pconfig);
        Element csproxy = doc.createElement("cache-service-proxy");
        pconfig.appendChild(csproxy);
        Element lock = doc.createElement("lock-enabled");
        //lock.setAttribute("system-property","be.engine.extend.proxy.lock-enabled");
        lock.setTextContent(String.valueOf(pscheme.isLockEnabled()));
        csproxy.appendChild(lock);
        Element ronly = doc.createElement("read-only");
        //ronly.setAttribute("system-property","be.engine.extend.proxy.read-only");
        ronly.setTextContent(String.valueOf(pscheme.isReadOnly()));
        csproxy.appendChild(ronly);
        Element astart = doc.createElement("autostart");
        //astart.setAttribute("system-property","be.engine.extend.proxy.autostart");
        astart.setTextContent(String.valueOf(pscheme.isAutoStart()));
        proxy.appendChild(astart);
        return proxy;
    }

    static Node genRemoteScheme(Document doc, RemoteConfig rconfig, boolean isInvocation) {
        Element sname = doc.createElement("scheme-name");
        Element svcname = doc.createElement("service-name");
        Element parentScheme = null;
        if (!isInvocation) {
            Element rcscheme = doc.createElement("remote-cache-scheme");
            rcscheme.appendChild(sname).setTextContent("tcp-extend-" + rconfig.getClusterName());
            rcscheme.appendChild(svcname).setTextContent("ExtendTcpCacheService-" + rconfig.getClusterName());
            parentScheme = rcscheme;
        } else {
            Element invScheme = doc.createElement("remote-invocation-scheme");
            invScheme.appendChild(sname).setTextContent("tcp-extend-invocation-" + rconfig.getClusterName());
            invScheme.appendChild(svcname).setTextContent("ExtendTcpInvocationService-" + rconfig.getClusterName());
            parentScheme = invScheme;
        }
        parentScheme.appendChild(addInitiatorConfig(doc, rconfig));


        return parentScheme;
    }

    public boolean isCacheServer() {
        return cacheServer;
    }

    public boolean isStorageEnabled() {
        return storageEnabled;
    }

    public static Node addInitiatorConfig(Document doc, RemoteConfig rconfig) {
        Element initConf = doc.createElement("initiator-config");

        Element tcpInit = doc.createElement("tcp-initiator");
        initConf.appendChild(tcpInit);
        Element remAdd = doc.createElement("remote-addresses");
        tcpInit.appendChild(remAdd);
        for (int i = 0; i < rconfig.getNodeCount(); i++) {
            Element sockAdd = doc.createElement("socket-address");
            Element addr = doc.createElement("address");
            //addr.setAttribute("system-property", "tcp.extend." + rconfig.getClusterName() +  ".host");
            Element port = doc.createElement("port");
            //port.setAttribute("system-property", "tcp.extend." + rconfig.getClusterName() +  ".port");
            sockAdd.appendChild(addr).setTextContent(rconfig.getHost(i));
            sockAdd.appendChild(port).setTextContent(String.valueOf(rconfig.getPort(i)));
            remAdd.appendChild(sockAdd);
        }
        Element ctimeout = doc.createElement("connect-timeout");
        tcpInit.appendChild(ctimeout).setTextContent("15s");
        Element omhandler = doc.createElement("outgoing-message-handler");
        initConf.appendChild(omhandler);
        Element rtimeout = doc.createElement("request-timeout");
        omhandler.appendChild(rtimeout).setTextContent("15s");
//        Element serializer = doc.createElement("serializer");
//        Element className = doc.createElement("class-name");
//        serializer.appendChild(className);
//        className.setTextContent("com.tibco.cep.runtime.service.om.impl.coherence.cluster.CacheSerializer");
//        initConf.appendChild(serializer);
        return initConf;
    }

    static Node genRemoteSchemeMapping(Document doc, Node csmappings, String cacheNamePattern, String schemeName) {
        Element csmapping = doc.createElement("cache-mapping");
        Element cname = doc.createElement("cache-name");
        cname.setTextContent(cacheNamePattern);
        csmapping.appendChild(cname);
        Element sname = doc.createElement("scheme-name");
        sname.setTextContent(schemeName);
        csmapping.appendChild(sname);
//        csmappings.insertBefore(csmapping,csmappings.getFirstChild());
        return csmapping;
    }

    static class ProxyConfig {
        private int threadCount;
        private String host;
        private int port;
        private String recvBufSize;
        private String sendBufSize;
        private boolean lockEnabled;
        private boolean readOnly;
        private boolean autostart;
        private String localClusterName;

        public static ProxyConfig loadConfig(BEProperties bprops) {
            ProxyConfig pinstance = new ProxyConfig(bprops);
            return pinstance;
        }

        private ProxyConfig(BEProperties cprops) {
            autostart = cprops.getBoolean("autostart", true);
            threadCount = cprops.getInt("thread.count", 8);
            recvBufSize = cprops.getString("recvbuffersize");
            sendBufSize = cprops.getString("sendbuffersize");
            lockEnabled = cprops.getBoolean("lock-enabled", false);
            readOnly = cprops.getBoolean("read-only", false);
            host = cprops.getString("localhost", "localhost");
            port = cprops.getInt("localport", 9099);
            localClusterName = cprops.getString("localclustername", System.getProperty("tangosol.coherence.cluster", ""));
            if (!localClusterName.equals(System.getProperty("tangosol.coherence.cluster", ""))) {
                throw new RuntimeException("Property be.engine.extend.proxy.localclustername :" + localClusterName + " should match the specified coherence cluster name:" + System.getProperty("tangosol.coherence.cluster", ""));
            }
        }


        public boolean isAutoStart() {
            return autostart;
        }

        public String getHost() {
            return host;
        }

        public boolean isLockEnabled() {
            return lockEnabled;
        }

        public int getPort() {
            return port;
        }

        public boolean isReadOnly() {
            return readOnly;
        }

        public String getRecvBufSize() {
            return recvBufSize;
        }

        public String getSendBufSize() {
            return sendBufSize;
        }

        public int getThreadCount() {
            return threadCount;
        }

        public String getLocalClusterName() {
            return localClusterName;
        }
    }

    static class RemoteConfig {
//        private Map agentMap = new HashMap<String, AgentConfig>();
        private String clusterName;
        private String connectionTimeout;
        private String requestTimeout;
        private ArrayList<String> host = new ArrayList<String>();
        private ArrayList<Integer> port = new ArrayList<Integer>();
        private int siteId;
        private int nodeCount;

        public RemoteConfig(String partner, BEProperties props) {
            clusterName = partner;
            connectionTimeout = props.getString("connect-timeout");
            requestTimeout = props.getString("request-timeout");
            siteId = props.getInt("siteid", 0);
            nodeCount = props.getInt("nodecount", 0);
            for (int i = 0; i < nodeCount; i++) {
                BEProperties aprop = props.getBranch(String.valueOf(i));
                host.add(aprop.getString("host", "localhost"));
                port.add(aprop.getInt("port", 9099));
            }

//            String [] agents = props.getString("agents").split(",");
//            for(String agent:agents) {
//                agentMap.put(agent.trim(),AgentConfig.loadConfig(agent.trim(),props));
//            }
        }

        public static RemoteConfig loadRemoteScheme(String partner, BEProperties branch) {
            RemoteConfig instance = new RemoteConfig(partner, branch.getBranch(partner));
            return instance;
        }

//        public Map getAgentMap() {
//            return agentMap;
//        }
//
//        public AgentConfig[] getAgentConfigs() {
//            return (AgentConfig[]) agentMap.values().toArray(new AgentConfig[0]);
//        }
//
//        public String[] getAgentNames() {
//            return (String[]) agentMap.keySet().toArray(new String[0]);
//        }

        public String getClusterName() {
            return clusterName;
        }

        public String getConnectionTimeout() {
            return connectionTimeout;
        }

        public String getRequestTimeout() {
            return requestTimeout;
        }

        public String getHost(int index) {
            return host.get(index);
        }

        public int getPort(int index) {
            return port.get(index);
        }

        public int getSiteId() {
            return siteId;
        }

        public int getNodeCount() {
            return nodeCount;
        }
    }

//    static class AgentConfig {
//        private String agentName;
//        private ArrayList<String> host = new ArrayList<String>();
//        private ArrayList<Integer> port = new ArrayList<Integer>();
//        private int nodeCount;
//
//        public AgentConfig(String agent, BEProperties props) {
//            this.agentName = agent;
//            nodeCount = props.getInt("nodes",0);
//            for(int i=0 ; i < nodeCount; i++ ) {
//                BEProperties aprop = props.getBranch(String.valueOf(i));
//                host.add(aprop.getString("host","localhost"));
//                port.add(aprop.getInt("port",9099));
//            }
//        }
//
//        public static AgentConfig loadConfig(String agent, BEProperties props) {
//            AgentConfig instance = new AgentConfig(agent,props.getBranch(agent));
//            return instance;
//        }
//
//        public String getAgentName() {
//            return agentName;
//        }
//
//        public String getHost(int index) {
//            return host.get(index);
//        }
//
//        public int getPort(int index) {
//            return port.get(index);
//        }
//
//        public int getNodeCount() {
//            return nodeCount;
//        }
//    }
}
