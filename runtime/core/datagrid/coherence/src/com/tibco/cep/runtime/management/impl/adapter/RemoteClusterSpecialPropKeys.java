package com.tibco.cep.runtime.management.impl.adapter;

/*
* Author: Ashwin Jayaprakash Date: Mar 11, 2009 Time: 10:05:39 AM
*/

/**
 * Sample Java properties to provide to connect to the main cluster:
 * <pre>
 * -Dbe.agent.query.querydataprovider.classname=com.tibco.cep.query.stream.impl.rete.integ.metrics.CoherenceMetricDataListener
 * -Dbe.agent.query.querydataprovider.metricobjecttransformer.classname=com.tibco.cep.query.stream.impl.rete.integ.metrics.DefaultMetricObjectTransformer
 * <p/>
 * -Dbe.metric.cluster.property.tangosol.coherence.localhost=localhost
 * -Dbe.metric.cluster.property.tangosol.coherence.localport=9901
 * -Dbe.metric.cluster.property.tangosol.coherence.cluster=darknet
 * -Dbe.metric.cluster.property.tangosol.coherence.clusteraddress=224.3.3.6
 * -Dbe.metric.cluster.property.tangosol.coherence.clusterport=3333
 * -Dbe.metric.cluster.property.tangosol.coherence.ttl=0
 * -Dbe.metric.cluster.property.tangosol.coherence.cacheconfig=Q:/be/config/coherence-cache-config.xml
 * <p/>
 * -Dbe.metric.cluster.property.broker.rmi.port=11200
 * -Dbe.metric.cluster.property.broker.log.file=c:/temp/broker.log
 * </pre>
 * <p/>
 * Optionally, if WKA is to be used, then:
 * <pre>
 * -Dbe.metric.cluster.property.tangosol.coherence.wka=198.163.10.3
 * -Dbe.metric.cluster.property.tangosol.coherence.wka.port=8700
 * </pre>
 */
public enum RemoteClusterSpecialPropKeys {
    CACHE_CONFIG("tangosol.coherence.cacheconfig"),
    CLUSTER("tangosol.coherence.cluster"),
    CLUSTER_ADDRESS("tangosol.coherence.clusteraddress"),
    CLUSTER_PORT("tangosol.coherence.clusterport"),
    COHERENCE_EDITION("tangosol.coherence.edition"),
    LICENSE_MODE("tangosol.coherence.mode"),
    LOCAL_HOST("tangosol.coherence.localhost"),
    LOCAL_PORT("tangosol.coherence.localport"),
    LOCAL_STORAGE("tangosol.coherence.distributed.localstorage", "false"),
    LOG_LEVEL("tangosol.coherence.log.level","9"),
    MANAGEMENT("tangosol.coherence.management", "all"),
    TTL("tangosol.coherence.ttl"),
    WKA_ADDRESS("tangosol.coherence.wka"),
    WKA_PORT("tangosol.coherence.wka.port"),

    //Broker properties
    BROKER_PORT("be.mm.broker.rmi.port"),
    BROKER_LOG_FILE("be.mm.broker.log.file", "logs/mm-broker.log"),
    BROKER_PROCESS_KILL("be.mm.broker.killoldbroker", "false"),

    //Broker Debug properties
    BROKER_DEBUG_PORT("be.mm.broker.debug.port"),
    BROKER_DEBUG_SUSPEND("be.mm.broker.debug.suspend");

    protected String key;

    protected String defaultValue;

    /**
     * {@value}.
     */

    public static String TANGOSOL_PREFIX = "tangosol.coherence.";
    public static String JAVA_PREFIX = "java.";
    public static String BROKER_PREFIX = "be.mm.broker.";

    //Deprecated Broker properties
    public static String DEPRECATED_REMOTE_CLUSTER_PROPERTY = "be.metric.cluster.property.";
    public static String DEPRECATED_BROKER_PROPERTY = "broker.";

    private RemoteClusterSpecialPropKeys(String key) {
        this.key = key;
    }

    RemoteClusterSpecialPropKeys(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String getKey() {
        return key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
