package com.tibco.cep.runtime.session;


import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.tibco.be.util.BEProperties;
import com.tibco.be.util.BEPropertiesFactory;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.util.SystemProperty;


/**
 * Manages the {@link RuleServiceProvider RuleServiceProvider} instances.
 * This is the entry point for most API operations.
 * <p>You can obtain an instance of this class with the static method {@link #getInstance() getInstance}.</p>
 *
 * @version 2.0
 * @see RuleServiceProvider
 * @since 2.0
 * @.category public-api
 */
public class RuleServiceProviderManager {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }

    private static RuleServiceProviderManager gManager = null;
    private static Thread debuggerThread = null;


    static {
        System.setProperty("com.tibco.util.Debug.enabled", "false");
        //Please SEE XMLSDK com.tibco.util.Debug.java
        //Please see Element.java which uses a stupid debug level validation
    }


    /**
     * Dictionary of all <code>RuleServiceProvider</code> objects known to this <code>RuleServiceProviderManager</code>.
     * <p>Maps the </code>String<code> name of the <code>RuleServiceProvider</code>
     * to the <code>RuleServiceProvider</code>.</p>
     *
     * @since 2.0
     */
    protected HashMap providerMap = new HashMap();

    private RuleServiceProvider defaultProvider = null;


    private static final Thread getDebuggerThread() {
        if (debuggerThread == null) {
            debuggerThread = new BEDebuggerThread();
            debuggerThread.setDaemon(true); // else JVM will not shutdown
            debuggerThread.start();
        }

        return debuggerThread;
    }

    private synchronized RuleServiceProvider getFirstProvider() {
        if (providerMap.size() == 0) {
            return null;
        }

        return (RuleServiceProvider) providerMap.values().iterator().next();
    }

    /**
     * Gets the singleton instance of this class.
     *
     * @return An instance of <code>RuleServiceProviderManager</code>.
     * @since 2.0
     * @.category public-api
     */
    public static synchronized RuleServiceProviderManager getInstance() {
        if (gManager != null) {
            return gManager;
        }
        gManager = new RuleServiceProviderManager();
        return gManager;
    }
    
    /**
     * The shutdown hook needs to be added when BE is running as a standalone JVM process
     */
    public static void addShutdownHook() {
    	Runtime.getRuntime().addShutdownHook(new ShutdownThread());
    }

    /**
     * Gets by name a <code>RuleServiceProvider</code> managed by this <code>RuleServiceProviderManager</code>.
     *
     * @param instanceName Name of the <code>RuleServiceProvider</code> to get.
     * @return The <code>RuleServiceProvider</code> of the given name if found
     *         in this <code>RuleServiceProviderManager</code>, else null.
     * @since 2.0
     * @.category public-api
     */
    public synchronized RuleServiceProvider getProvider(String instanceName) {
        return (RuleServiceProvider) providerMap.get(instanceName);
    }


    /**
     * Gets all the <code>RuleServiceProvider</code> contained in this <code>RuleServiceProviderManager</code>.
     *
     * @return A Collection of <code>RuleServiceProvider</code>.
     * @.category public-api
     * @since 2.0
     */
    public synchronized Collection getRuleServiceProviders() {
        return providerMap.values();
    }

    /**
     * Has a side-effect - sets {@link SystemProperty#CLUSTER_NODE_ISSEEDER} on prop and System.
     */
    static public boolean isConfigAsCacheServer(Properties prop) {
        final String cacheServerConfg = prop.getProperty(SystemProperty.CACHE_SERVER.getPropertyName(), "false");
        if (cacheServerConfg.equalsIgnoreCase("true")) {
            prop.setProperty(SystemProperty.CLUSTER_NODE_ISSEEDER.getPropertyName(), Boolean.TRUE.toString());
            System.setProperty(SystemProperty.CLUSTER_NODE_ISSEEDER.getPropertyName(), Boolean.TRUE.toString());

            return true;
        }
        //if(cacheServerConfg.equalsIgnoreCase("true|channels")) return true;
        return false;
    }

    static public int getCacheServerMode(Properties prop) {
        boolean isCacheServer = isConfigAsCacheServer(prop);
        if (!isCacheServer) throw new RuntimeException("BE property <be.engine.cacheServer> is not configured as cacheServer mode");

        String disableChannel = prop.getProperty("be.engine.cacheServer.channel.disable", "false");
        boolean bDisable =  (disableChannel.equalsIgnoreCase("true"));

        if (bDisable) {
        	return RuleServiceProvider.MODE_CACHESERVER; // Disabled Channel
        }

        return RuleServiceProvider.MODE_CACHESERVER_W_CHANNEL;
    }

    static public boolean isConfigAsQueryAgent(RuleServiceProvider rsp) {
    	try {
	        RuleSession[] sessions = rsp.getRuleRuntime().getRuleSessions();
	        if (sessions.length == 1) {
	            Class queryClass = Class.forName("com.tibco.cep.query.service.impl.QueryRuleSessionImpl");
	            if (queryClass.isAssignableFrom(sessions[0].getClass())) {
	            	return true;
	            }
	        }
    	}
    	catch (Exception e) {
    		return false;
    	}
        return false;
    }

    static public int getQueryAgentMode(Properties prop) {
        String disableChannel = prop.getProperty("be.engine.queryAgent.channel.disable", "false");
        boolean bDisable = (disableChannel.equalsIgnoreCase("true"));

        if (bDisable) {
        	return RuleServiceProvider.MODE_QUERYAGENT; // Disabled Channel
        }

        return RuleServiceProvider.MODE_QUERYAGENT_W_CHANNEL;
    }
    
    /**
     * Creates and registers a new <code>RuleServiceProvider</code> in API mode.
     * <p>You must provide the name of the configuration file in the property <code>"be.bootstrap.property.file"</code>.
     * For example:</p>
     * <p><code>Properties p = new Properties;<br/>
     * p.put("be.bootstrap.property.file", "my_engine.tra");<br/>
     * RuleServiceProviderManager.getInstance().newProvider("my_rsp", p);
     * </code></p>
     *
     * @param instanceName Name given to the new <code>RuleServiceProvider</code>.
     * @param env          <code>Properties</code> used to build the new <code>RuleServiceProvider</code>.
     * @return A new <code>RuleServiceProvider</code>.
     * @throws Exception when <code>instanceName</code> is the name of an existing <code>RuleServiceProvider</code>.
     * @.category public-api
     * @since 2.0
     */
    public synchronized RuleServiceProvider newProvider(String instanceName, Properties env) throws Exception {

        final BEProperties beProperties = BEPropertiesFactory.getInstance().makeBEProperties(env, instanceName);
        RuleServiceProvider provider = null;

        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        registry.initBasic();
        registry.init(beProperties);

        if (instanceName == null) {
            if (beProperties == null) {
                provider = new RuleServiceProviderImpl(null, beProperties);
                if (providerMap.get(provider.getName()) != null) {
                    //todo - destroy the provider?
                    throw new Exception("RuleServiceProvider of name " + instanceName + " already exist");
                }
                providerMap.put(provider.getName(), provider);
                return provider;
            } else {
                if (isConfigAsCacheServer(beProperties)) {
                    provider = new RuleServiceProviderImpl(null, beProperties);
                    if (providerMap.get(provider.getName()) != null) {
                        //todo - destroy the provider?
                        throw new Exception("RuleServiceProvider of name " + instanceName + " already exist");
                    }
                    providerMap.put(provider.getName(), provider);
                    return provider;
                } else if (beProperties.getBoolean("be.ft.enabled", false) ||
                        	beProperties.getBoolean("Engine.FT.UseFT", false)) {
                    String ftRuleServiceProviderClass = beProperties.getString("com.tibco.cep.runtime.session.ruleserviceprovider");
                    Class clazz = null;
                    clazz = Class.forName(ftRuleServiceProviderClass);
                    Constructor constructor = clazz.getConstructor(new Class[]{String.class, Properties.class});
                    provider = (RuleServiceProvider) constructor.newInstance(new Object[]{null, beProperties});
                    providerMap.put(provider.getName(), provider);
                    return provider;
                } else {
                    provider = new RuleServiceProviderImpl(null, beProperties);
                    if (providerMap.get(provider.getName()) != null) {
                        //todo - destroy the provider?
                        throw new Exception("RuleServiceProvider of name " + instanceName + " already exist");
                    }
                    providerMap.put(provider.getName(), provider);
                    return provider;
                }
            }
        } else {
            if (providerMap.get(instanceName) != null) {
                throw new Exception("RuleServiceProvider of name " + instanceName + " already exist");
            }
            provider = new RuleServiceProviderImpl(instanceName, beProperties);
            providerMap.put(instanceName, provider);
            return provider;
        }
    }


    /**
     * Removes the <code>RuleServiceProvider</code> of the given name, if it exists,
     * from this <code>RuleServiceProviderManager</code>.
     *
     * @param instanceName Name of the <code>RuleServiceProvider</code> to remove.
     * @.category public-api
     * @since 2.0
     */
    public synchronized void removeProvider(String instanceName) {
        providerMap.remove(instanceName);
    }

    public void setDefaultProvider(RuleServiceProvider provider) {
        this.defaultProvider = provider;
    }

    public RuleServiceProvider getDefaultProvider() {
        return this.defaultProvider;
    }

    private static class BEDebuggerThread extends Thread {
        public void run() {
            while (true) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }

    private static class ShutdownThread extends Thread {
        public ShutdownThread() {
            super("CTRL-C");
        }
        public void run() {
            RuleServiceProviderManager mgr = getInstance();
            Set shutDownRsps = new HashSet();
            //synchronized(mgr) {
                Collection c = mgr.getRuleServiceProviders();
                Object[] rsps = c.toArray();
                for(int i = 0; i < rsps.length; i++) {
                    RuleServiceProvider rsp = (RuleServiceProvider) rsps[i];
                    rsp.getLogger(this.getClass()).log(Level.INFO,
                            "Shutting down service provider: %s", rsp.getName());
                    rsp.shutdown();
                    shutDownRsps.add(rsp);
                }
                RuleServiceProvider defaultRsp = mgr.getDefaultProvider();
                if(defaultRsp != null && !shutDownRsps.contains(defaultRsp)) {
                    defaultRsp.getLogger(this.getClass()).log(Level.INFO,
                            "Shutting down default service provider %s", defaultRsp.getName());
                    defaultRsp.shutdown();
                }
          //}
        }
    }
}
