package com.tibco.cep.query.service.impl;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.functions.ConnectionCache;
import com.tibco.cep.query.functions.QueryUtilFunctions;
import com.tibco.cep.query.service.QueryProperty;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.query.stream.cache.CacheBuilder;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.impl.Master;
import com.tibco.cep.query.stream.impl.cache.SimpleCacheBuilder;
import com.tibco.cep.query.stream.impl.rete.integ.Manager;
import com.tibco.cep.query.stream.impl.rete.service.AgentService;
import com.tibco.cep.query.stream.impl.rete.service.RegionManager;
import com.tibco.cep.query.stream.impl.rete.service.StandAloneAgentService;
import com.tibco.cep.query.stream.impl.util.DelegatedLogger;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Mar 25, 2008
* Time: 6:46:03 PM
*/

public class MasterHelper {
    private static Master master;

    private static final String BQL_CONSOLE_CLASS = "com.tibco.cep.query.client.console.swing.BQLConsole";

    private static final String SHOW_CONSOLE_METHOD = "showConsole";

    /**
     * @param queryAgentServices
     * @param beProperties
     * @param logger
     * @throws Exception
     */
    public static synchronized void start(Collection<AgentService> queryAgentServices,
                                          Properties beProperties,
                                          Logger logger) throws Exception {
        if (null != master) {
            return;
        }

        boolean cachePrefetchAggressive = isCachePrefetchAggressive(beProperties, logger);
        int maxCQThreads = getMaxThreadsForCQ(beProperties, logger);
        boolean isFilterOptimizerEnabled = isFilterOptimizerEnabled(beProperties, logger);
        boolean standaloneMode = isStandaloneMode(beProperties, logger);
        boolean dynamicAgentPresent = false;

        LinkedList<QuerySession> querySessions = new LinkedList<QuerySession>();
        LinkedList<Manager.ManagerInput.RegionInput> regionInputs = new LinkedList<Manager.ManagerInput.RegionInput>();
        LinkedList<String> regionManagerNames = new LinkedList<String>();

        for (AgentService agentService : queryAgentServices) {
            Manager.ManagerInput.RegionInput regionInput =
                    new Manager.ManagerInput.RegionInput(agentService, cachePrefetchAggressive, standaloneMode);

            regionInputs.add(regionInput);

            regionManagerNames.add(regionInput.getName());

            querySessions.add((QuerySession) agentService.getRuleSession());

            if (agentService.getName().equals(QueryProperty.NAME_DYNAMIC_QUERY_SESSION.getPropName())) {
                dynamicAgentPresent = true;
            }
        }

        initBQLConsole(querySessions, beProperties, logger);

        Manager.ManagerInput managerInput = new Manager.ManagerInput(regionInputs);

        SimpleCacheBuilder.BuilderInput builderInput = makeCacheBuilder(beProperties, logger, dynamicAgentPresent);

        DelegatedLogger.DelegatedLoggerInput loggerInput =
                new DelegatedLogger.DelegatedLoggerInput(logger);

        Properties properties = new Properties(beProperties);
        properties.put(Manager.ManagerInput.KEY_INPUT, managerInput);
        properties.put(CacheBuilder.BuilderInput.KEY_INPUT, builderInput);
        properties.put(DelegatedLogger.DelegatedLoggerInput.KEY_INPUT, loggerInput);
        properties.put(QueryProperty.CONTINUOUS_QUERY_MAX_THREADS.getPropName(),
                String.valueOf(maxCQThreads));
        properties.put(QueryProperty.FILTER_OPTIMIZER.getPropName(), isFilterOptimizerEnabled);

        master = new Master();
        master.start(properties);

        //---------

        Iterator<QuerySession> sessionIterator = querySessions.iterator();
        Iterator<AgentService> agentIterator = queryAgentServices.iterator();

        Manager manager = Registry.getInstance().getComponent(Manager.class);
        Map<String, RegionManager> regionManagers = manager.getRegionManagers();

        for (; sessionIterator.hasNext(); ) {
            QuerySession querySession = sessionIterator.next();
            AgentService agentService = agentIterator.next();

            String name = agentService.getName();
            RegionManager regionManager = regionManagers.get(name);

            querySession.setChangeListener(regionManager.getDispatcher());
            querySession.setPrimaryCache(regionManager.getPrimaryCache());
            querySession.setSharedObjectSourceRepository(regionManager.getSOSRepository());

            if (querySession instanceof RuleSessionImpl) {
                RuleSessionImpl rs = (RuleSessionImpl) querySession;

                QueryUtilFunctions.registerQueryRuleSession(name, rs);
            }
            else {
                logger.log(Level.WARN,
                        "Query session [" + name + "] is not an instance of" +
                                " [" + RuleSessionImpl.class.getSimpleName() + "]");
            }
        }

        //---------

        ConnectionCache.init(querySessions, regionManagerNames);

        if (standaloneMode) {
            for (AgentService agentService : queryAgentServices) {
                if (agentService instanceof StandAloneAgentService) {
                    ((StandAloneAgentService) agentService).start();
                }
            }
        }
    }

    private static void initBQLConsole(Collection<QuerySession> querySessions,
                                       final Properties beProperties, final Logger logger) {
        List<String> showBQLNames = shouldShowBQLConsole(beProperties);
        if (showBQLNames == null || showBQLNames.size() == 0) {
            return;
        }
        final RuleServiceProvider rsProvider = RuleServiceProviderManager.getInstance().getDefaultProvider();
        for (int i = 0, I = showBQLNames.size(); i < I; i++) {
            final String name = showBQLNames.get(i);
            final QuerySession session = getQuerySession(querySessions, name);
            if (session == null) {
                continue;
            }
            Thread bqlThread = new Thread(new Runnable() {
                public void run() {
                    Thread.currentThread().setContextClassLoader(new BEClassLoader(rsProvider, beProperties));
                    try {
                        Class consoleClass = Class.forName(BQL_CONSOLE_CLASS);
                        Object consoleObject = consoleClass.newInstance();
                        Method method = consoleClass.getDeclaredMethod(SHOW_CONSOLE_METHOD, String.class,
                                QuerySession.class);
                        method.invoke(consoleObject, new Object[]{name, session});
                    }
                    catch (Throwable th) {
                        logger.log(Level.WARN, "BQL Console could not be displayed.", th);
                    }
                }
            });
            bqlThread.start();
        }
    }

    private static boolean isCachePrefetchAggressive(Properties properties, Logger logger) {
        String aggressiveStr =
                properties.getProperty(QueryProperty.LOCALCACHE_PREFETCHAGGRESSIVE.getPropName(),
                        "false");

        logger.log(Level.INFO, "Local cache using %s: %s",
                QueryProperty.LOCALCACHE_PREFETCHAGGRESSIVE.getPropName(), aggressiveStr);

        return Boolean.parseBoolean(aggressiveStr);
    }

    private static int getMaxThreadsForCQ(Properties properties, Logger logger) {
        String maxThreads =
                properties.getProperty(QueryProperty.CONTINUOUS_QUERY_MAX_THREADS.getPropName(), "0");
        logger.log(Level.INFO, "Continuous Query Threadpool Size %s: %s",
                QueryProperty.CONTINUOUS_QUERY_MAX_THREADS.getPropName(), maxThreads);
        return Integer.parseInt(maxThreads);
    }

    private static List<String> shouldShowBQLConsole(Properties properties) {
        String showBQLCSV = properties.getProperty(QueryProperty.SHOW_BQL_CONSOLE.getPropName());
        if (showBQLCSV == null || showBQLCSV.trim().length() == 0) {
            return null;
        }
        String[] names = showBQLCSV.split(",");
        return Arrays.asList(names);
    }

    private static QuerySession getQuerySession(Collection<QuerySession> querySessions, String name) {
        for (QuerySession session : querySessions) {
            if (session.getRuleSession().getName().equals(name)) {
                return session;
            }
        }
        return null;
    }

    private static boolean isFilterOptimizerEnabled(Properties properties, Logger logger) {
        String isEnabled = properties.getProperty(QueryProperty.FILTER_OPTIMIZER.getPropName(),
                Boolean.FALSE.toString());
        boolean advancedFilterOptimizer = Boolean.parseBoolean(isEnabled);

        logger.log(Level.INFO, "Filter Optimizer enabled: %s", advancedFilterOptimizer);
        return advancedFilterOptimizer;
    }

    private static boolean isStandaloneMode(Properties properties, Logger logger) {
        String s =
                properties.getProperty(SystemProperty.VM_NETWORK_MODE_STANDALONE.getPropertyName(),
                        Boolean.FALSE.toString());
        boolean standaloneMode = Boolean.parseBoolean(s);

        logger.log(Level.INFO, "Query using %s: %s",
                SystemProperty.VM_NETWORK_MODE_STANDALONE.getPropertyName(), standaloneMode);

        return standaloneMode;
    }

    private static SimpleCacheBuilder.BuilderInput makeCacheBuilder(Properties properties, Logger logger,
                                                                    boolean dynamicAgentPresent) {
        String sizeStr =
                properties.getProperty(QueryProperty.LOCALCACHE_MAXELEMENTS.getPropName(),
                        (dynamicAgentPresent ? "0" : "50000"));
        String timeStr =
                properties.getProperty(QueryProperty.LOCALCACHE_EVICTSECONDS.getPropName(),
                        (dynamicAgentPresent ? "900000" : "900"));

        logger.log(Level.INFO, "Local cache using %s: %s",
                QueryProperty.LOCALCACHE_MAXELEMENTS.getPropName(), sizeStr);
        logger.log(Level.INFO, "Local cache using %s: %s",
                QueryProperty.LOCALCACHE_EVICTSECONDS.getPropName(), timeStr);

        int size = Integer.parseInt(sizeStr);
        int timeMillis = Integer.parseInt(timeStr) * 1000;

        int deadSize = Math.min(size, 500);
        int deadTimeMillis = Math.min(timeMillis, 60 * 1000);

        SimpleCacheBuilder.BuilderInput builderInput =
                new SimpleCacheBuilder.BuilderInput(size, timeMillis, deadSize, deadTimeMillis);

        return builderInput;
    }

    public static synchronized void stop() throws Exception {
        if (null != master) {
            master.stop();
            master = null;

            ConnectionCache.discard();
        }
    }
}
