package com.tibco.cep.runtime.service.dao.impl.tibas;

/*
* Author: Ashwin Jayaprakash / Date: Nov 29, 2010 / Time: 11:02:33 AM
*/

import com.tibco.cep.runtime.util.SystemProperty;

public interface ASConstants {

    final String be = "be";
    final String as = "as";
    final String logs = "logs";
    final String datastore = "datastore";

    /**
     * {@value}
     */
    String PROP_LISTEN_URL = SystemProperty.AS_LISTEN_URL.getPropertyName();

    /**
     * {@value}
     */
    String PROP_REMOTE_LISTEN_URL = SystemProperty.AS_REMOTE_LISTEN_URL.getPropertyName();

    /**
     * {@value}
     */
    String PROP_DISCOVER_URL = SystemProperty.AS_DISCOVER_URL.getPropertyName();

    /**
     * {@value}
     */
    String PROP_AS_SECURITY_ENABLE = SystemProperty.AS_SECURITY_ENABLE.getPropertyName();

    /**
     * {@value} The security role and security file property is to be set per Processing Unit ( per PU )
     */
    String PROP_AS_SECURITY_MODE_ROLE = SystemProperty.AS_SECURITY_MODE_ROLE.getPropertyName();

    String AS_SECURITY_ROLE_REQUESTER = "Requester";

    String AS_SECURITY_ROLE_CONTROLLER = "Controller";

    String PROP_AS_SECURITY_FILE =  SystemProperty.AS_SECURITY_FILE.getPropertyName();

    /**
     * system property used by AS for timing out backing store calls.
     */
    String PROP_AS_PROTOCOL_TIMEOUT = SystemProperty.AS_PROTOCOL_TIMEOUT.getPropertyName();

    /**
     * system property used by AS for how long it waits for a member to reconnect
     */
    String PROP_AS_MEMBER_TIMEOUT = SystemProperty.AS_MEMBER_TIMEOUT.getPropertyName();
    
    /**
     * system property used by AS for how long it waits for a remote member to reconnect
     */
    String PROP_AS_REMOTE_MEMBER_TIMEOUT = SystemProperty.AS_REMOTE_MEMBER_TIMEOUT.getPropertyName();
    
    String PROP_AS_REMOTE_MEMBER_ALLOW_RECONNECT = SystemProperty.AS_REMOTE_MEMBER_ALLOW_RECONNECT.getPropertyName();
    
    /**
     * system property used by AS for connections that can be lost before suspending cluster
     */
    String PROP_AS_CLUSTER_SUSPEND_THRESHOLD = SystemProperty.AS_CLUSTER_SUSPEND_THRESHOLD.getPropertyName();

    /**
     * system property used by AS for reconnection attempts to the cluster. See BE-18839
     */
    String PROP_AS_CONNECTION_TIMEOUT = SystemProperty.AS_CONNECTION_TIMEOUT.getPropertyName();

    /**
     * {@value}
     */
    String PROP_TUPLE_EXPLICIT = SystemProperty.PROP_TUPLE_EXPLICIT.name();

    /**
     * {@value}
     */
    String PROP_LOCK_TTL = SystemProperty.AS_LOCK_TTL.getPropertyName();
    String PROP_LOCK_SCOPE = SystemProperty.AS_LOCK_SCOPE.getPropertyName();

    /**
     * {@value}
     */
    String PROP_METASPACE_SHUTDOWN_WAIT_MILLIS = SystemProperty.AS_SHUTDOWN_WAIT_MILLIS.getPropertyName();
    String PROP_METASPACE_SHUTDOWN_STRATEGY = SystemProperty.CLUSTER_SHUTDOWN_STRATEGY.getPropertyName();

    /**
     * {@value}
     * ActiveSpaces default is 32, BusinessEvents defaults to 4
     */
    String PROP_MEMBER_WORKERTHREADS_COUNT = SystemProperty.AS_WORKERTHREADS_COUNT.getPropertyName();

    /**
     * {@value}
     */
    String PROP_AS_LOG_DIR = SystemProperty.AS_LOG_DIR.getPropertyName();

    /**
     * {@value}
     */
    String PROP_AS_LOG_FILE_NAME = SystemProperty.AS_LOG_FILE_NAME.getPropertyName();

    /**
     * {@value}
     */
    String PROP_AS_LOG_LEVEL = SystemProperty.AS_LOG_LEVEL.getPropertyName();

    /**
     * Space read and write timeouts for AS
     */
    String PROP_AS_SPACE_READ_TIMEOUT = SystemProperty.AS_READ_TIMEOUT.getPropertyName();
    String PROP_AS_SPACE_WRITE_TIMEOUT = SystemProperty.AS_WRITE_TIMEOUT.getPropertyName();
    
    /**
     * File sync interval for AS
     */
    String PROP_AS_FILE_SYNC_INTERVAL = SystemProperty.AS_FILE_SYNC_INTERVAL.getPropertyName();
    
    /**
     * Without the optimization [join=true], Space.browse() will first check to see if we are joined to a space.  
     * If we are not joined, we then join the space and will later leave the space when Browser.close() is called.  
     * If we are joined, we increment the reference of being joined to the space, so even if Space.close() is called 
     * the Browser will still be holding a reference and we will remained joined to the space until Browser.close() 
     * is called.
     * 
     * With the optimization enabled [join=false] by calling BrowserDef.setJoin(false), we will no longer join automatically.  
     * Therefore it is up to BE to ensure that Space.close() is never called or is garbage collected while the Browser is in use.
     * To ensure Space is never garbage collected, it is recommended you store them in some kind of global container since there 
     * have been instances where Space is still in scope as a local reference but the underlying object is garbage collected since 
     * the JVM thinks it is no longer in use when in reality its state is in use by the Browser.
     */
    String PROP_AS_SPACE_BROWSER_JOIN = "be.engine.cluster.as.browser.join";

    String PROP_AS_AGGREGATE_PREFETCH_SIZE = "be.engine.cluster.as.aggregate.prefetch.size"; // Used by Aggregate funcs. (default -1)
    String PROP_AS_BROWSER_PREFETCH_SIZE = "be.engine.cluster.as.browser.prefetch.size"; // Used by Lookup qrys. (default -1)
    String PROP_AS_BROWSER_TIMEOUT = "be.engine.cluster.as.browser.timeout";// When NOT specified fallsback to AS default i.e BrowserDef.NO_WAIT
    String PROP_AS_LOOKUP_PREFETCH_SIZE = "be.engine.cluster.as.lookup.prefetch.size"; // Used by get/load-ByExtId funcs. (default 0)
    String PROP_AS_INVOCATION_TIMEOUT = "be.engine.cluster.as.invocation.timeout";// When NOT specified fallsback to AS default i.e. -1

    /**
     * BE-22747: When client does query, if there are entries to be read; it registers itself to all peer members, including the killed
	 * node. Therefore inference node gets blocked until killed node is declared dead (30 secs).
	 * Timescope's like CURRENT should avoid this problem (as well as pf = -1). 
	 *  -1 SNAP = 300
	 *   0 SNAP = 300
	 *  -1 CURR = 550
	 *   0 CURR = 700
     */
    String PROP_AS_AGGREGATE_TIME_SCOPE = "be.engine.cluster.as.aggregate.time.scope"; // Used by Aggregate funcs. (default TimeScope.SNAPSHOT)
    String PROP_AS_BROWSER_TIME_SCOPE = "be.engine.cluster.as.browser.time.scope"; // Used by Lookup qrys. (default TimeScope.SNAPSHOT)
    String PROP_AS_LOOKUP_TIME_SCOPE = "be.engine.cluster.as.lookup.time.scope"; // Used by get/load-ByExtId funcs. (default TimeScope.CURRENT)

    String PROP_AS_LOGFILE_COUNT = SystemProperty.AS_LOGFILE_COUNT.getPropertyName();

    String PROP_AS_LOGFILE_SIZE = SystemProperty.AS_LOGFILE_SIZE.getPropertyName();

    String PROP_AS_LOGFILE_APPEND = SystemProperty.AS_LOGFILE_APPEND.getPropertyName();

}
